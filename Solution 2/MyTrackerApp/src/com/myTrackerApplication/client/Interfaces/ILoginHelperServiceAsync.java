package com.myTrackerApplication.client.Interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ILoginHelperServiceAsync {
	void login(String userid, String password, AsyncCallback<String> callback) throws IllegalArgumentException ;

	void signUp(String userid, String password, AsyncCallback<String> callback);
}
