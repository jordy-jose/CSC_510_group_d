package com.myTrackerApplication.UserAuthentication;

public class Login {

	public static void main(String[] args) {
		LoginHelper loginHelper = new LoginHelper();
		System.out.println(loginHelper.signUp("root", "root"));
		System.out.println(loginHelper.signUp("sameer", "sameer"));
		System.out.println(loginHelper.login("root", "root"));
	}

}
