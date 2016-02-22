package com.myTrackerApplication.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.HashMap;

@RemoteServiceRelativePath("MyTrackerApplicationService")
public interface MyTrackerApplicationService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    HashMap getUserInformation(String msg);

    /**
     * Utility/Convenience class.
     * Use MyTrackerApplicationService.App.getInstance() to access static instance of MyTrackerApplicationServiceAsync
     */
    public static class App {
        private static MyTrackerApplicationServiceAsync ourInstance = GWT.create(MyTrackerApplicationService.class);

        public static synchronized MyTrackerApplicationServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
