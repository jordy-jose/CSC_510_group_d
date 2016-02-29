package com.myTrackerApplication.client.Interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;

public interface IDBHelperServiceAsync{

	void insertUserData(HashMap<String, String> m, AsyncCallback<String> callback);
	void readUserData(String unityID, AsyncCallback<HashMap<String, String>> callback);
	void updateUserData(HashMap<String, String> m, AsyncCallback<String> callback);
	void generateReportData(HashMap<String, String> m, AsyncCallback<HashMap<String, String>> callback);

}
