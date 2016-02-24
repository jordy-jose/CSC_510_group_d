package Interfaces;

import java.util.HashMap;

public interface ImsHelp {

	void insertTable(HashMap<String, String> m);
    void executeUpdate(String sqlStatement);
    void updateTable(String key, String val);
	void readTable();
	//IQueryResultSet executeQuery(String sqlQuery);
}
