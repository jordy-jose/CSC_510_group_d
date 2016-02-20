package com.se.DatabaseConnectionHelper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.se.Interfaces.IDatabaseConnection;

class DatabaseConnection implements IDatabaseConnection{

	private Connection m_Connection = null;
    static final String s_JdbcURL 
	= "jdbc:mysql://localhost/demo";
    static final String s_DbDriver
        = "com.mysql.jdbc.Driver";
    static final String s_User = "root";
    static final String s_Password = "root";
	
    @Override
	public void close() throws Exception {
		try{
	        if(m_Connection != null){
	            m_Connection.close();
	            m_Connection = null;
	        }
	    }
	    catch (SQLException e){
	        Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
	    }
	}
    
	@Override
	public Connection getDatabaseConnection() {
		if(m_Connection == null){
            m_Connection = connectToDatabase();
        }
        return m_Connection;
	}
	
	private Connection connectToDatabase() {
        try{
            Class.forName(s_DbDriver);
            return DriverManager.getConnection(s_JdbcURL, s_User, s_Password);
        }
        catch (SQLException e){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        catch (ClassNotFoundException e){
            Logger.getLogger(DatabaseConnection.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }
}
