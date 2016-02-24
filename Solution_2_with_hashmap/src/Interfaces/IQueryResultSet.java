package Interfaces;

import java.sql.ResultSet;

public interface IQueryResultSet  extends AutoCloseable{
	String getString(String attributeName);
    Boolean isEmpty();
    Boolean moveToFirstRow();
    ResultSet  getResultSet();
}
