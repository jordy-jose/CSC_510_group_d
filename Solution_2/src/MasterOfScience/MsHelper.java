package MasterOfScience;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import DatabaseConnectionHelper.DatabaseConnection;
import Interfaces.ImsHelp;
import java.util.List;
import java.util.Map;
import Interfaces.IDatabaseConnection;
import Interfaces.IStatementExecutor;
import Interfaces.IQueryResultSet;

public class MsHelper implements ImsHelp{
	
	private Connection m_Connection = null;
    static final String s_JdbcURL 
	= "jdbc:mysql://localhost/demo";
    static final String s_DbDriver
        = "com.mysql.jdbc.Driver";
    static final String s_User = "root";
    static final String s_Password = "root";
    
	public MsHelper() {
	       try {
	    	   Class.forName(s_DbDriver);
	           return;
	       } 
	       catch (Exception ex) {
	          ex.printStackTrace();
	       }
	     }

	@Override
	public void insertTable(HashMap<String, String> m) {
		MsHelper db = new MsHelper();
		for (Map.Entry<String, String> e : m.entrySet()) {
		  db.executeUpdate("INSERT INTO MS (Key, Value) VALUES("+e.getKey()+",'"+e.getValue()+"');");
		}
	}
	
	@Override
	public void readTable() {
		MsHelper db = new MsHelper();

		ResultSet resSet = db.executeQuery("SELECT * FROM MS");
		HashMap<String, String> hMap = new HashMap<String, String>();
		while (resSet.next()) {
		  hMap.put(resSet.getString("Unity ID"), resSet.getString("Value"));
		}

		resSet.close();
	}
	
	@Override
	public void updateTable(String key, String val) {
			HashMap<String, String> m = new HashMap<String,String>();
		    if( !m.containsKey(key) ) 
		    	return;
		    m.put(key, val);
	}
	
	@Override
	public void close() throws Exception {
		try{
	        if(m_Connection != null){
	            m_Connection.close();
	            m_Connection = null;
	        }
	    }
	    catch (SQLException e){
	    }
	}
	
	@Override
	public void executeUpdate(String sqlStatement) {
		if (m_Connection == null)
	          throw new SQLException("Connection null!");
	       Statement statement = m_Connection.createStatement();
	       int res = statement.executeUpdate(sqlStatement);
	       statement.close();
	}
	
	public ResultSet executeQuery(String sql) throws SQLException {
	       if (m_Connection == null)
	          throw new SQLException("Connection null!");
	       Statement statement = m_Connection.createStatement();
	       ResultSet res = statement.executeQuery(sql);
	       statement.close();
	       return res;
	    }

	
	
}
