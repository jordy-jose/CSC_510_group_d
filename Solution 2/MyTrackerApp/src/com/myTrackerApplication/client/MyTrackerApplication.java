package com.myTrackerApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.impl.AsyncFragmentLoader;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.myTrackerApplication.UserAuthentication.LoginHelper;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MyTrackerApplication implements EntryPoint {

    /**
     * Create a remote service proxy to talk to the server-side service.
     */
    private final MyLoginHelperServiceAsync loginService = GWT.create(MyLoginHelperService.class);

    /* Create Root Logger */
    private static Logger rootLogger = Logger.getLogger("MyTrackerApplication");


    public void onModuleLoad() {
        // Create a FormPanel and point it at a service.
        final FormPanel form = new FormPanel();
        form.setAction("/loginFormHandler");
        form.setMethod(FormPanel.METHOD_POST);
        // Create a panel to hold all of the form widgets.
        VerticalPanel panel = new VerticalPanel();
        form.setWidget(panel);

        final Label titleLabel = new Label("Log In");
        final Label emailLabel = new Label("Email:");
        final TextBox emailBox = new TextBox();
        final Label passwordLabel = new Label("Password:");
        final TextBox passwordBox = new TextBox();
        Grid loginGrid = new Grid(2, 2);
        loginGrid.setWidget(0, 0, emailLabel);
        loginGrid.setWidget(0, 1, emailBox);
        loginGrid.setWidget(1, 0, passwordLabel);
        loginGrid.setWidget(1, 1, passwordBox);
        panel.add(loginGrid);
        // Add a 'submit' button.
        panel.add(new Button("Log in", new ClickHandler() {
            public void onClick(ClickEvent event) {
                form.submit();
            }
        }));
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
                String userid = emailBox.getText();
                String password = passwordBox.getText();

                loginService.login(userid, password, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        rootLogger.log(Level.SEVERE, "LOGIN ATTEMPT FAIL");
                    }

                    public void onSuccess(String result) {
                        if (result.equals("success")) {
                            form.removeFromParent();
                            RootPanel.get("leftnav").add(MainPage.getNavBar());
                            HashMap map = new HashMap();

                            map.put("AAAA", "AAAA");
                            map.put("BBBB", "BBBB");
                            map.put("CCCC", "CCCC");
                            map.put("DDDD", "DDDD");

                            RootPanel.get("content").add(MainPage.getContentGrid(map));
                        }
                        else{
                            Window.alert(result);
                            rootLogger.log(Level.SEVERE, "LOGIN ATTEMPT FAIL");
                        }
                    }
                });
            }
        });
        RootPanel.get("header").add(titleLabel);
        RootPanel.get("content").add(form);
    }
}
