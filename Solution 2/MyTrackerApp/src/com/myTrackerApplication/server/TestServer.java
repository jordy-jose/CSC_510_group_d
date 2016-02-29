package com.myTrackerApplication.server;

import com.myTrackerApplication.UserAuthentication.DBHelperService;
import com.myTrackerApplication.UserAuthentication.LoginHelperService;

import java.util.HashMap;

public class TestServer {

	public static void main(String[] args) throws Exception {
//		LoginHelperService loginHelper = new LoginHelperService();
//		System.out.println(loginHelper.signUp("root", "root"));
//		System.out.println(loginHelper.signUp("ssharm20", "abc"));
//		System.out.println(loginHelper.login("root", "root"));
//
		DBHelperService dbHelper = new DBHelperService();
//
//		HashMap testMap = new HashMap();
//		testMap.put("Unity ID", "ssharm20");
//		testMap.put("Orientaion course (CSC600)", "1");
//		testMap.put("Thesis Research - credits", "6");
//		testMap.put("Core courses - Theory", "3");
//		testMap.put("Core courses - systems", "0");
//		testMap.put("Graduate Electives", "3");
//		testMap.put("Minor Courses - credits", "5");
//		testMap.put("Seminars attended", "2");


//		System.out.println(dbHelper.readUserData("ssharm20"));

		System.out.println(dbHelper.generateReportData(dbHelper.readUserData("ssharm20")));

//		testMap.put("Minor Courses - credits", "3");
//		System.out.println(dbHelper.updateUserData(testMap));
	}

}
