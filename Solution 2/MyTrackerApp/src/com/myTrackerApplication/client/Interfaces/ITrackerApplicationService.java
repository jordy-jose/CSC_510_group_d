package com.myTrackerApplication.client.Interfaces;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.HashMap;

@RemoteServiceRelativePath("MyTrackerApplicationService")
public interface ITrackerApplicationService extends RemoteService {
    // Sample interface method of remote interface
    String getMessage(String msg);

    HashMap getUserInformation(String msg);

    /**
     * Utility/Convenience class.
     * Use ITrackerApplicationService.App.getInstance() to access static instance of MyTrackerApplicationServiceAsync
     */
    public static class App {
        private static ITrackerApplicationServiceAsync ourInstance = GWT.create(ITrackerApplicationService.class);

        public static synchronized ITrackerApplicationServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
