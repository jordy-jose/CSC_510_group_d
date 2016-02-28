package com.myTrackerApplication.client.Interfaces;

import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.HashMap;

public interface ITrackerApplicationServiceAsync {
    void getMessage(String msg, AsyncCallback<String> async);

    void getUserInformation(String msg, AsyncCallback<HashMap> async);
}
