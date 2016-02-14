package com.se.UserAuthentication;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.se.DatabaseConnectionHelper.DatabaseConnectionService;
import com.se.Interfaces.*;
import com.se.Interfaces.IHashService;

class LoginHelper {

	private static String errorMsg = "invalid username or password!"; 
	void signUp(String userName, String password){
		if(userName == null || userName.isEmpty()){
			System.out.println(errorMsg);
			return;
		}
		if(password == null || password.isEmpty()){
			System.out.println(errorMsg);
			return;
		}
		
		String userNameLower = userName.toLowerCase();
		
		try{
			if(!IsUserIdTaken(userNameLower)){
				InsertUserToDatabase(DatabaseConnectionService.createHashService(), userNameLower, password);
				System.out.println("sign up success!");
			}
			else{
				System.out.println("user name taken!");
			}
		}
		catch(Exception e){
			Logger.getLogger(LoginHelper.class.getName()).log(Level.SEVERE, null, e);
		}
	}
	
	private void InsertUserToDatabase(IHashService hashService, 
			String userName, String password)  throws Exception{
		try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String passwordHash = hashService.getHash(password);
                String insertIntoUser = String.format("INSERT INTO USER " +
                        "VALUES ('%s', '%s')", userName, passwordHash);
                stmtExecutor.executeUpdate(insertIntoUser);
            }
        }
		
	}
	
	private Boolean IsUserIdTaken(String userName)throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
            String selectUser = String.format("SELECT * FROM USER " +
                    "WHERE USERID= '%s'", userName);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(selectUser)){
                    return !resultSet.isEmpty();
                }
            }
        }
    }

	void login(String userName, String password){
		if(userName == null || userName.isEmpty()){
			System.out.println(errorMsg);
			return;
		}
		if(password == null || password.isEmpty()){
			System.out.println(errorMsg);
			return;
		}
		
		String userNameLower = userName.toLowerCase();
		try{
			if(IsValidUser(DatabaseConnectionService.createHashService(), userNameLower, password)){
				System.out.println("valid user!");
			}
			else{
				System.out.println(errorMsg);
			}
		}
		catch(Exception e){
			Logger.getLogger(LoginHelper.class.getName()).log(Level.SEVERE, null, e);
		}
	}

	private boolean IsValidUser(IHashService hashService, 
			String userName, String password) throws Exception{
		try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){           
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String findUserQuery = String.format("SELECT U.USERID, U.PASSWORD " + 
                        "FROM USER U " +
                        "WHERE U.USERID = '%s'", userName);
                try (IQueryResultSet resultSet = stmtExecutor.executeQuery(findUserQuery)){
                    if (resultSet.isEmpty()) return false;
                    resultSet.moveToFirstRow();
                    String passwordHash = resultSet.getString("PASSWORD").trim();
                    return hashService.isMatch(password, passwordHash);
                }
            }
        }
	}
}
