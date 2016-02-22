package com.myTrackerApplication.DatabaseConnectionHelper;

import com.myTrackerApplication.Interfaces.*;

public class DatabaseConnectionService {
	public static IDatabaseConnection createDatabaseConnection(){
        return new DatabaseConnection();
    }
    
    public static IStatementExecutor createStatementExecutor(IDatabaseConnection connection){
        return new StatementExecutor(connection);
    }

    public static IHashService createHashService(){
        return new HashService();
    }
}
