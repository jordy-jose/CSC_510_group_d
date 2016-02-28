package com.myTrackerApplication.client.Interfaces;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.HashMap;

@RemoteServiceRelativePath("MyDBHelperService")
public interface IDBHelperService extends RemoteService {

    String insertUserData(HashMap<String, String> m) throws Exception;
    HashMap<String, String> readUserData(String unityID) throws Exception;
    String updateUserData(HashMap<String, String> m) throws Exception;
    HashMap<String, String> generateReportData(HashMap<String, String> m) throws Exception;

}