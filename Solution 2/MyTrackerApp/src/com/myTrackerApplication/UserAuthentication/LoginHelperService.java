package com.myTrackerApplication.UserAuthentication;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myTrackerApplication.DatabaseConnectionHelper.DatabaseConnectionService;
import com.myTrackerApplication.Interfaces.*;
import com.myTrackerApplication.Interfaces.IHashService;
import com.myTrackerApplication.client.Interfaces.ILoginHelperService;

public class LoginHelperService extends RemoteServiceServlet implements ILoginHelperService {

	public static String errorMsg = "invalid username or password!";
	public String signUp(String userName, String password){
		if(userName == null || userName.isEmpty()){
			Logger.getLogger(LoginHelperService.class.getName()).log(Level.SEVERE, null, errorMsg);
			return errorMsg;
		}
		if(password == null || password.isEmpty()){
			Logger.getLogger(LoginHelperService.class.getName()).log(Level.SEVERE, null, errorMsg);
			return errorMsg;
		}

		String userNameLower = userName.toLowerCase();

		try{
			if(!IsUserIdTaken(userNameLower)){
				InsertUserToDatabase(DatabaseConnectionService.createHashService(), userNameLower, password);
				return "success";
			}
			else{
				Logger.getLogger(LoginHelperService.class.getName()).log(Level.SEVERE, null, "user name taken!");
				return ("user name taken");
			}
		}
		catch(Exception e){
			Logger.getLogger(LoginHelperService.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}

	public void InsertUserToDatabase(IHashService hashService,
			String userName, String password)  throws Exception{
		try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String passwordHash = hashService.getHash(password);
                String insertIntoUser = String.format("INSERT INTO StudentInfo " +
                        "VALUES ('%s', '%s')", userName, passwordHash);
                stmtExecutor.executeUpdate(insertIntoUser);
            }
        }

	}

	public Boolean IsUserIdTaken(String userName)throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
            String selectUser = String.format("SELECT * FROM StudentInfo " +
                    "WHERE `Unity ID`= '%s'", userName);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(selectUser)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }

	public String login(String userName, String password) throws IllegalArgumentException {
		if(userName == null || userName.isEmpty()){
			Logger.getLogger(LoginHelperService.class.getName()).log(Level.SEVERE, null, errorMsg);
			return errorMsg;
		}
		if(password == null || password.isEmpty()){
			Logger.getLogger(LoginHelperService.class.getName()).log(Level.SEVERE, null, errorMsg);
			return errorMsg;
		}

		String userNameLower = userName.toLowerCase();
		try{
			if(IsValidUser(DatabaseConnectionService.createHashService(), userNameLower, password)){
				return "success";
			}
			else{
				Logger.getLogger(LoginHelperService.class.getName()).log(Level.SEVERE, null, errorMsg);
				return errorMsg;
			}
		}
		catch(Exception e){
			Logger.getLogger(LoginHelperService.class.getName()).log(Level.SEVERE, null, e);
		}
		return null;
	}

	private boolean IsValidUser(IHashService hashService,
			String userName, String password) throws Exception{
		try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String findUserQuery = String.format("SELECT U.`Unity ID`, U.Password " +
                        "FROM StudentInfo U " +
                        "WHERE U.`Unity ID` = '%s'", userName);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(findUserQuery)){
                    if (resultSet.isEmpty()) return false;
                    resultSet.moveToFirstRow();
                    String passwordHash = resultSet.getString("Password").trim();
                    return hashService.isMatch(password, passwordHash);
                }
            }
        }
	}
}
