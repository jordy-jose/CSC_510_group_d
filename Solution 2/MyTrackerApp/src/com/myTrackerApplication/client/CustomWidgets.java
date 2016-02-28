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
 * Created by Sameer Sharma on 23/02/16.
 */
public class CustomWidgets {

    /**
     * Create a remote service proxy to talk to the server-side service.
     */
    private static final ILoginHelperServiceAsync loginService = GWT.create(ILoginHelperService.class);
    private static final IDBHelperServiceAsync dbService = GWT.create(IDBHelperService.class);

    /* Create Root Logger */
    private static Logger rootLogger = Logger.getLogger("MyTrackerApplication");

    public static Panel getContentGrid(HashMap dataMap) {

        int noOfRows = dataMap.size();
        // Create a grid
        Grid grid = new Grid(noOfRows, 2);

        // Add items to the grid
        int row = 0;
        for (Object key : dataMap.keySet()) {
            grid.setText(row, 0, key.toString());
            grid.setText(row, 1, dataMap.get(key).toString());
            row++;
        }
        // Return the panel
        grid.ensureDebugId("cwGrid");
        return grid;
    }

    public static VerticalPanel getNavBar(String unityID){

        final String id = unityID;

        // Create a Vertical Panel
        VerticalPanel vPanel = new VerticalPanel();
        vPanel.setSpacing(10);

        // Add some content to the panel
        Anchor homeAnchor = new Anchor("Home");
        homeAnchor.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get("header").clear();
                RootPanel.get("content").clear();
                final Label titleLabel = new Label("Home");
                titleLabel.addStyleName("gwt-Heading");

                //TODO: Implement DB Queries for fetching user data from DB Service
                RootPanel.get("content").add(CustomWidgets.getHomeDataForm(id));

                RootPanel.get("header").add(titleLabel);
            }
        });
        homeAnchor.addStyleName("gwt-VAnchor");
        vPanel.add(homeAnchor);
        vPanel.setCellHeight(homeAnchor, String.valueOf(15));

        Anchor reportAnchor = new Anchor("Report");
        reportAnchor.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get("header").clear();
                RootPanel.get("content").clear();
                final Label titleLabel = new Label("Report");
                titleLabel.addStyleName("gwt-Heading");

                //TODO: Implement DB Queries for fetching user report data from DB Service
                dbService.readUserData(id, new AsyncCallback<HashMap<String, String>>() {
                    public void onFailure(Throwable caught){
                        rootLogger.log(Level.SEVERE, "Unable to fetch user data");
                    }
                    public void onSuccess(HashMap<String, String> result){
                        RootPanel.get("content").add(CustomWidgets.getReportDataForm(result));
                    }
                });
                RootPanel.get("header").add(titleLabel);
            }
        });
        reportAnchor.addStyleName("gwt-VAnchor");
        vPanel.add(reportAnchor);
//        vPanel.setCellHeight(reportAnchor, String.valueOf(15));


        Anchor logoutAnchor = new Anchor("Logout");
        logoutAnchor.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
                RootPanel.get("leftnav").clear();
                RootPanel.get("header").clear();
                RootPanel.get("content").clear();
                final Label titleLabel = new Label("Log In");
                titleLabel.addStyleName("gwt-Heading");
                FormPanel form = MainPage.getLoginPanel();
                RootPanel.get("header").add(titleLabel);
                RootPanel.get("content").add(form);
            }
        });
        logoutAnchor.addStyleName("gwt-VAnchor");
        vPanel.add(logoutAnchor);
//        vPanel.setCellHeight(logoutAnchor, String.valueOf(15));


        // Return the content
        vPanel.ensureDebugId("cwVerticalPanel");
        vPanel.addStyleName("gwt-VerticalPanel");
        return vPanel;
    }

    public static FormPanel getRegisterForm(){

        // Create a FormPanel and point it at a service.
        final FormPanel registerForm = new FormPanel();
        registerForm.setAction("/signupFormHandler");
        registerForm.setMethod(FormPanel.METHOD_POST);
        // Create a panel to hold all of the form widgets.
        VerticalPanel panel = new VerticalPanel();
        registerForm.setWidget(panel);
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
        Button registerButton = new Button("Register", new ClickHandler() {
            public void onClick(ClickEvent event) {
                registerForm.submit();
            }
        });
        panel.add(registerButton);

        panel.addStyleName("gwt-LoginPanel");
        registerButton.addStyleName("gwt-LoginButton");

        // Add an event handler to the form.
        registerForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                if (emailBox.getText().length() == 0 || passwordBox.getText().length() == 0) {
                    Window.alert("Username and Password must not be empty");
                    rootLogger.log(Level.SEVERE, "Sign Up ATTEMPT FAIL");
                    event.cancel();
                }
            }
        });


        registerForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                final String userid = emailBox.getText();
                String password = passwordBox.getText();

                loginService.signUp(userid, password, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        rootLogger.log(Level.SEVERE, "Sign Up ATTEMPT FAIL");
                    }

                    public void onSuccess(String result) {
                        if (result.equals("success")) {

                            RootPanel.get("leftnav").clear();
                            RootPanel.get("header").clear();
                            RootPanel.get("content").clear();
                            RootPanel.get("leftnav").add(CustomWidgets.getNavBar(userid));

                            //TODO: Implement DB Queries for fetching show empty from DB
                            RootPanel.get("content").add(CustomWidgets.getUserDataForm());

                            final Label titleLabel = new Label("Home");
                            titleLabel.addStyleName("gwt-Heading");
                            RootPanel.get("header").add(titleLabel);
                        }
                        else{
                            Window.alert(result);
                            rootLogger.log(Level.SEVERE, "Sign Up ATTEMPT FAIL");
                        }
                    }
                });
            }
        });
        return registerForm;
    }


    public static FormPanel getUserDataForm(){

        // Create a FormPanel and point it at a service.
        final FormPanel userDataForm = new FormPanel();
        userDataForm.setAction("/userDataFormHandler");
        userDataForm.setMethod(FormPanel.METHOD_POST);
        // Create a panel to hold all of the form widgets.

        VerticalPanel panel = new VerticalPanel();
        userDataForm.setWidget(panel);
        panel.setSpacing(20);
        final Label idLabel = new Label("Unity ID");
        final TextBox idBox = new TextBox();
        final ListBox courseListBox = new ListBox();
        final Label courseLabel = new Label("Select Course");
        courseListBox.addItem("MR");
        courseListBox.addItem("PHD");
        final Label streamLabel = new Label("Stream Enrolled in");
        final ListBox streamListBox = new ListBox();
        streamListBox.addItem("Computer Science");
        streamListBox.addItem("Computer Networks");
        final Label orientationLabel = new Label("Orientation course (CSC600)");
        final TextBox orientationBox = new TextBox();
        final Label thesisCreditLabel = new Label("Thesis Research - credits");
        final TextBox thesisCreditBox = new TextBox();
        final Label coreTheoryLabel = new Label("Core courses - Theory");
        final TextBox coreTheoryBox = new TextBox();
        final Label coreSystemLabel = new Label("Core courses - systems");
        final TextBox coreSystemBox = new TextBox();
        final Label graduateElectivesLabel = new Label("Graduate Electives");
        final TextBox graduateElectivesBox = new TextBox();
        final Label minorCreditsLabel = new Label("Minor Courses - credits");
        final TextBox minorCreditsBox = new TextBox();
        final Label seminarsLabel = new Label("Seminars attended");
        final TextBox seminarsElectivesBox = new TextBox();
        final Label output = new Label("");


        Grid loginGrid = new Grid(11, 2);
        loginGrid.setWidget(0, 0, idLabel);
        loginGrid.setWidget(0, 1, idBox);
        loginGrid.setWidget(1, 0, courseLabel);
        loginGrid.setWidget(1, 1, courseListBox);
        loginGrid.setWidget(2, 0, streamLabel);
        loginGrid.setWidget(2, 1, streamListBox);
        loginGrid.setWidget(3, 0, orientationLabel);
        loginGrid.setWidget(3, 1, orientationBox);
        loginGrid.setWidget(4, 0, thesisCreditLabel);
        loginGrid.setWidget(4, 1, thesisCreditBox);
        loginGrid.setWidget(5, 0, coreTheoryLabel);
        loginGrid.setWidget(5, 1, coreTheoryBox);
        loginGrid.setWidget(6, 0, coreSystemLabel);
        loginGrid.setWidget(6, 1, coreSystemBox);
        loginGrid.setWidget(7, 0, graduateElectivesLabel);
        loginGrid.setWidget(7, 1, graduateElectivesBox);
        loginGrid.setWidget(8, 0, minorCreditsLabel);
        loginGrid.setWidget(8, 1, minorCreditsBox);
        loginGrid.setWidget(9, 0, seminarsLabel);
        loginGrid.setWidget(9, 1, seminarsElectivesBox);
        loginGrid.setWidget(10, 1, output);

        panel.add(loginGrid);
        // Add a 'submit' button.
        Button registerButton = new Button("Update Information", new ClickHandler() {
            public void onClick(ClickEvent event) {
                userDataForm.submit();
            }
        });
        panel.add(registerButton);

        panel.addStyleName("gwt-DataPanel");
        registerButton.addStyleName("gwt-LoginButton");

        // Add an event handler to the form.
        userDataForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                //TODO: Client side validations can be done here
            }
        });


        userDataForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                HashMap dataMap = new HashMap();
                dataMap.put("Unity ID", idBox.getValue());
                dataMap.put("Orientaion course (CSC600)", orientationBox.getValue());
                dataMap.put("Thesis Research - credits", thesisCreditBox.getValue());
                dataMap.put("Core courses - Theory", coreTheoryBox.getValue());
                dataMap.put("Core courses - systems", coreSystemBox.getValue());
                dataMap.put("Graduate Electives", graduateElectivesBox.getValue());
                dataMap.put("Minor Courses - credits", minorCreditsBox.getValue());
                dataMap.put("Seminars attended", seminarsElectivesBox.getValue());

                dbService.insertUserData(dataMap, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        rootLogger.log(Level.SEVERE, "Update Information ATTEMPT FAIL");
                    }

                    public void onSuccess(String result) {
                        if (result.equals("success")) {
                            output.setText("Your information is successfully updated...");
                        }
                        else{
                            Window.alert(result);
                            rootLogger.log(Level.SEVERE, "Update Information ATTEMPT FAIL");
                        }
                    }
                });
            }
        });
        return userDataForm;
    }

    public static FormPanel getHomeDataForm(String unityID){

        // Create a FormPanel and point it at a service.
        final FormPanel userDataForm = new FormPanel();
        userDataForm.setAction("/homeDataFormHandler");
        userDataForm.setMethod(FormPanel.METHOD_POST);
        // Create a panel to hold all of the form widgets.

        VerticalPanel panel = new VerticalPanel();
        userDataForm.setWidget(panel);
        panel.setSpacing(20);
        final Label idLabel = new Label("Unity ID");
        final TextBox idBox = new TextBox();
        final ListBox courseListBox = new ListBox();
        final Label courseLabel = new Label("Select Course");
        courseListBox.addItem("MR");
        courseListBox.addItem("PHD");
        final Label streamLabel = new Label("Stream Enrolled in");
        final ListBox streamListBox = new ListBox();
        streamListBox.addItem("Computer Science");
        streamListBox.addItem("Computer Networks");
        final Label orientationLabel = new Label("Orientaion course (CSC600)");
        final TextBox orientationBox = new TextBox();
        final Label thesisCreditLabel = new Label("Thesis Research - credits");
        final TextBox thesisCreditBox = new TextBox();
        final Label coreTheoryLabel = new Label("Core courses - Theory");
        final TextBox coreTheoryBox = new TextBox();
        final Label coreSystemLabel = new Label("Core courses - systems");
        final TextBox coreSystemBox = new TextBox();
        final Label graduateElectivesLabel = new Label("Graduate Electives");
        final TextBox graduateElectivesBox = new TextBox();
        final Label minorCreditsLabel = new Label("Minor Courses - credits");
        final TextBox minorCreditsBox = new TextBox();
        final Label seminarsLabel = new Label("Seminars attended");
        final TextBox seminarsElectivesBox = new TextBox();
        final Label output = new Label("");

        dbService.readUserData(unityID, new AsyncCallback<HashMap<String, String>>() {
            public void onFailure(Throwable caught) {
                rootLogger.log(Level.SEVERE, "Unable to fetch user data");
            }

            public void onSuccess(HashMap<String, String> result) {
                    idBox.setValue(result.get("Unity ID"));
                    orientationBox.setValue(result.get("Orientaion course (CSC600)"));
                    thesisCreditBox.setValue(result.get("Thesis Research - credits"));
                    coreTheoryBox.setValue(result.get("Core courses - Theory"));
                    coreSystemBox.setValue(result.get("Core courses - systems"));
                    graduateElectivesBox.setValue(result.get("Graduate Electives"));
                    minorCreditsBox.setValue(result.get("Minor Courses - credits"));
                    seminarsElectivesBox.setValue(result.get("Seminars attended"));
            }
        });

        Grid loginGrid = new Grid(11, 2);
        loginGrid.setWidget(0, 0, idLabel);
        loginGrid.setWidget(0, 1, idBox);
        loginGrid.setWidget(1, 0, courseLabel);
        loginGrid.setWidget(1, 1, courseListBox);
        loginGrid.setWidget(2, 0, streamLabel);
        loginGrid.setWidget(2, 1, streamListBox);
        loginGrid.setWidget(3, 0, orientationLabel);
        loginGrid.setWidget(3, 1, orientationBox);
        loginGrid.setWidget(4, 0, thesisCreditLabel);
        loginGrid.setWidget(4, 1, thesisCreditBox);
        loginGrid.setWidget(5, 0, coreTheoryLabel);
        loginGrid.setWidget(5, 1, coreTheoryBox);
        loginGrid.setWidget(6, 0, coreSystemLabel);
        loginGrid.setWidget(6, 1, coreSystemBox);
        loginGrid.setWidget(7, 0, graduateElectivesLabel);
        loginGrid.setWidget(7, 1, graduateElectivesBox);
        loginGrid.setWidget(8, 0, minorCreditsLabel);
        loginGrid.setWidget(8, 1, minorCreditsBox);
        loginGrid.setWidget(9, 0, seminarsLabel);
        loginGrid.setWidget(9, 1, seminarsElectivesBox);
        loginGrid.setWidget(10, 1, output);

        panel.add(loginGrid);
        // Add a 'submit' button.
        Button registerButton = new Button("Update Information", new ClickHandler() {
            public void onClick(ClickEvent event) {
                userDataForm.submit();
            }
        });
        panel.add(registerButton);

        panel.addStyleName("gwt-DataPanel");
        registerButton.addStyleName("gwt-LoginButton");

        // Add an event handler to the form.
        userDataForm.addSubmitHandler(new FormPanel.SubmitHandler() {
            public void onSubmit(FormPanel.SubmitEvent event) {
                //TODO: Client side validations can be done here
            }
        });


        userDataForm.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                HashMap dataMap = new HashMap();
                dataMap.put("Unity ID", idBox.getValue());
                dataMap.put("Orientaion course (CSC600)", orientationBox.getValue());
                dataMap.put("Thesis Research - credits", thesisCreditBox.getValue());
                dataMap.put("Core courses - Theory", coreTheoryBox.getValue());
                dataMap.put("Core courses - systems", coreSystemBox.getValue());
                dataMap.put("Graduate Electives", graduateElectivesBox.getValue());
                dataMap.put("Minor Courses - credits", minorCreditsBox.getValue());
                dataMap.put("Seminars attended", seminarsElectivesBox.getValue());

                dbService.updateUserData(dataMap, new AsyncCallback<String>() {
                    public void onFailure(Throwable caught) {
                        rootLogger.log(Level.SEVERE, "Update Information ATTEMPT FAIL");
                    }

                    public void onSuccess(String result) {
                        if (result.equals("success")) {
                            output.setText("Your information is successfully updated...");
                        }
                        else{
                            Window.alert(result);
                            rootLogger.log(Level.SEVERE, "Update Information ATTEMPT FAIL");
                        }
                    }
                });
            }
        });
        return userDataForm;
    }


    public static FormPanel getReportDataForm(HashMap map){

        // Create a FormPanel and point it at a service.
        final FormPanel userDataForm = new FormPanel();
        userDataForm.setAction("/reportDataFormHandler");
        userDataForm.setMethod(FormPanel.METHOD_POST);
        // Create a panel to hold all of the form widgets.

        VerticalPanel panel = new VerticalPanel();
        userDataForm.setWidget(panel);
        panel.setSpacing(20);
        final Label idLabel = new Label("Unity ID");
        final Label idBox = new Label();
        final Label courseValue = new Label("MR");
        final Label courseLabel = new Label("Select Course");
        final Label streamLabel = new Label("Stream Enrolled in");
        final Label streamValue = new Label("Computer Science");
        final Label orientationLabel = new Label("Orientaion course (CSC600)");
        final Label orientationBox = new Label();
        final Label thesisCreditLabel = new Label("Thesis Research - credits");
        final Label thesisCreditBox = new Label();
        final Label coreTheoryLabel = new Label("Core courses - Theory");
        final Label coreTheoryBox = new Label();
        final Label coreSystemLabel = new Label("Core courses - systems");
        final Label coreSystemBox = new Label();
        final Label graduateElectivesLabel = new Label("Graduate Electives");
        final Label graduateElectivesBox = new Label();
        final Label minorCreditsLabel = new Label("Minor Courses - credits");
        final Label minorCreditsBox = new Label();
        final Label seminarsLabel = new Label("Seminars attended");
        final Label seminarsElectivesBox = new Label();
        final Label output = new Label("These are the list of things you need to do to graduate...");

        dbService.generateReportData(map, new AsyncCallback<HashMap<String, String>>() {
            public void onFailure(Throwable caught) {
                rootLogger.log(Level.SEVERE, "Unable to fetch user data");
            }

            public void onSuccess(HashMap<String, String> result) {
                    idBox.setText(result.get("Unity ID"));
                    orientationBox.setText(result.get("Orientaion course (CSC600)"));
                    thesisCreditBox.setText(result.get("Thesis Research - credits"));
                    coreTheoryBox.setText(result.get("Core courses - Theory"));
                    coreSystemBox.setText(result.get("Core courses - systems"));
                    graduateElectivesBox.setText(result.get("Graduate Electives"));
                    minorCreditsBox.setText(result.get("Minor Courses - credits"));
                    seminarsElectivesBox.setText(result.get("Seminars attended"));
            }
        });

        Grid loginGrid = new Grid(11, 2);
        loginGrid.setWidget(0, 0, idLabel);
        loginGrid.setWidget(0, 1, idBox);
        loginGrid.setWidget(1, 0, courseLabel);
        loginGrid.setWidget(1, 1, courseValue);
        loginGrid.setWidget(2, 0, streamLabel);
        loginGrid.setWidget(2, 1, streamValue);
        loginGrid.setWidget(3, 0, orientationLabel);
        loginGrid.setWidget(3, 1, orientationBox);
        loginGrid.setWidget(4, 0, thesisCreditLabel);
        loginGrid.setWidget(4, 1, thesisCreditBox);
        loginGrid.setWidget(5, 0, coreTheoryLabel);
        loginGrid.setWidget(5, 1, coreTheoryBox);
        loginGrid.setWidget(6, 0, coreSystemLabel);
        loginGrid.setWidget(6, 1, coreSystemBox);
        loginGrid.setWidget(7, 0, graduateElectivesLabel);
        loginGrid.setWidget(7, 1, graduateElectivesBox);
        loginGrid.setWidget(8, 0, minorCreditsLabel);
        loginGrid.setWidget(8, 1, minorCreditsBox);
        loginGrid.setWidget(9, 0, seminarsLabel);
        loginGrid.setWidget(9, 1, seminarsElectivesBox);
        loginGrid.setWidget(10, 1, output);

        panel.add(loginGrid);
        panel.addStyleName("gwt-DataPanel");
        return userDataForm;
    }


}

