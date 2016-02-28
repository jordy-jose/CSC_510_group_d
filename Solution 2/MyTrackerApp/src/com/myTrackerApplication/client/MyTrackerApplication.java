package com.myTrackerApplication.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.*;

import java.util.logging.Logger;


/**
 * Entry point classes define <code>onModuleLoad()</code>
 */
public class MyTrackerApplication implements EntryPoint {

    /* Create Root Logger */
    private static Logger rootLogger = Logger.getLogger("MyTrackerApplication");


    public void onModuleLoad() {

        final Label titleLabel = new Label("Log In");
        titleLabel.addStyleName("gwt-Heading");
        FormPanel form = MainPage.getLoginPanel();
        RootPanel.get("header").add(titleLabel);
        RootPanel.get("content").add(form);

    }
}
