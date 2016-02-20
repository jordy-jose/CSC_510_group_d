package com.se.controller;

/**
 * Created by drrakeshkumarsharma on 15/02/16.
 */
public class Credentials {
    private String login;
    private String password;

    public Credentials() {
        super();
    }
    public Credentials(String login, String password) {
        super();
        this.login = login;
        this.password = password;
    }
    public String getLogin() {
        return login;
    }
    public void setLogin(String login) {
        this.login = login;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
