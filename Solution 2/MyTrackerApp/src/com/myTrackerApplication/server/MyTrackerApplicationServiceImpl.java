package com.myTrackerApplication.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myTrackerApplication.client.Interfaces.ITrackerApplicationService;

import java.util.HashMap;

public class MyTrackerApplicationServiceImpl extends RemoteServiceServlet implements ITrackerApplicationService {
    // Implementation of sample interface method
    public String getMessage(String msg) {
        return "Client said: \"" + msg + "\"<br>Server answered: \"Hi!\"";
    }

    public HashMap getUserInformation(String id) {
        HashMap infoMap = new HashMap();

        infoMap.put("Name", id);
        return infoMap;

    }

}