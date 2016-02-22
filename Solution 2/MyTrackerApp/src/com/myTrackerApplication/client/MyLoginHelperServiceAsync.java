package com.myTrackerApplication.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface MyLoginHelperServiceAsync {
	void login(String userid, String password, AsyncCallback<String> callback) throws IllegalArgumentException ;

	void signUp(String userid, String password, AsyncCallback<String> callback);
}
