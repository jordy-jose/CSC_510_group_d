package com.myTrackerApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.myTrackerApplication.client.Interfaces.IDBHelperService;
import com.myTrackerApplication.client.Interfaces.IDBHelperServiceAsync;
import com.myTrackerApplication.client.Interfaces.ILoginHelperService;
import com.myTrackerApplication.client.Interfaces.ILoginHelperServiceAsync;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Sameer Sharma on 20/02/16.
 */
public class MainPage {

    /**
     * Create a remote service proxy to talk to the server-side service.
     */
    private static final IDBHelperServiceAsync dbService = GWT.create(IDBHelperService.class);
    private static final ILoginHelperServiceAsync loginService = GWT.create(ILoginHelperService.class);

    /* Create Root Logger */
    private static Logger rootLogger = Logger.getLogger("MyTrackerApplication");

    public static FormPanel getLoginPanel(){
        // Create a FormPanel and point it at a service.
        final FormPanel form = new FormPanel();

        form.setAction("/loginFormHandler");
        form.setMethod(FormPanel.METHOD_POST);
        // Create a panel to hold all of the form widgets.
        VerticalPanel panel = new VerticalPanel();
        form.setWidget(panel);
        panel.setSpacing(20);
        final Label emailLabel = new Label("UnityID:");
        final TextBox emailBox = new TextBox();
        final Label passwordLabel = new Label("Password:");
        final PasswordTextBox passwordBox = new PasswordTextBox();

        Grid loginGrid = new Grid(2, 2);
        loginGrid.setWidget(0, 0, emailLabel);
        loginGrid.setWidget(0, 1, emailBox);
        loginGrid.setWidget(1, 0, passwordLabel);
        loginGrid.setWidget(1, 1, passwordBox);

        panel.add(loginGrid);
        // Add a 'submit' button.
        Button loginButton = new Button("Log in", new ClickHandler() {
            public void onClick(ClickEvent event) {
                form.submit();
            }
        });
        panel.add(loginButton);

        Anchor signUpLabel = new Anchor("-- New User: Sign up --");

        signUpLabel.addStyleName("gwt-RegisterAnchor");
        panel.addStyleName("gwt-LoginPanel");
        loginButton.addStyleName("gwt-LoginButton");

        signUpLabel.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {

                RootPanel.get("leftnav").clear();
                RootPanel.get("header").clear();
                RootPanel.get("content").clear();

                RootPanel.get("content").add(CustomWidgets.getRegisterForm());
                final Label titleLabel = new Label("Register");
                titleLabel.addStyleName("gwt-Heading");
                RootPanel.get("header").add(titleLabel);

            }
        });
        panel.add(signUpLabel);

        // Add an event handler to the form.
        form.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                if (emailBox.getText().length() == 0 || passwordBox.getText().length() == 0) {
                    Window.alert("Username and Password must not be empty");
                    rootLogger.log(Level.SEVERE, "LOGIN ATTEMPT FAIL");
                    event.cancel();
                }
            }
        });
        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                final String userid = emailBox.getText();
                String password = passwordBox.getText();

                loginService.login(userid, password, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        rootLogger.log(Level.SEVERE, "LOGIN ATTEMPT FAIL");
                    }

                    public void onSuccess(String result) {
                        if (result.equals("success")) {
                            RootPanel.get("leftnav").clear();
                            RootPanel.get("header").clear();
                            RootPanel.get("content").clear();

                            dbService.readUserData("ssharm20", new AsyncCallback<HashMap<String, String>>() {
                                public void onFailure(Throwable caught) {
                                    rootLogger.log(Level.SEVERE, "Failed reading data from server");
                                }

                                public void onSuccess(HashMap<String, String> result) {
                                    RootPanel.get("content").add(CustomWidgets.getHomeDataForm(userid));
                                }
                            });

                            RootPanel.get("leftnav").add(CustomWidgets.getNavBar(userid));
                            final Label titleLabel = new Label("Home");
                            titleLabel.addStyleName("gwt-Heading");
                            RootPanel.get("header").add(titleLabel);
                        }
                        else{
                            Window.alert(result);
                            rootLogger.log(Level.SEVERE, "LOGIN ATTEMPT FAIL");
                        }
                    }
                });
            }
        });
        return form;
    }



}
