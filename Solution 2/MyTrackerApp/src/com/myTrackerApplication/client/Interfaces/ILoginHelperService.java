package com.myTrackerApplication.client.Interfaces;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("MyLoginHelperService")
public interface ILoginHelperService extends RemoteService {
    // Sample interface method of remote interface
    String login(String userid, String password) throws IllegalArgumentException ;

    String signUp(String msg, String password);

}
