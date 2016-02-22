package com.myTrackerApplication.Interfaces;

public interface IStatementExecutor  extends AutoCloseable{
	void executeUpdate(String sqlStatement);
    IQueryResultSet executeQuery(String sqlQuery);
}
