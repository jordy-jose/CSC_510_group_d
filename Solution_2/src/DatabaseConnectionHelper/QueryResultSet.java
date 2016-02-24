package DatabaseConnectionHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import Interfaces.IQueryResultSet;

class QueryResultSet  implements IQueryResultSet {

	private ResultSet m_resultSet;
	
	public QueryResultSet(ResultSet resultSet){
        if(resultSet == null) throw new IllegalArgumentException("resultSet is null");
        m_resultSet = resultSet;
    }
	
	@Override
	public void close() throws Exception {
		try{
            if(m_resultSet != null){
                m_resultSet.close();
                m_resultSet = null;
            }
        }
        catch (SQLException e){
            Logger.getLogger(QueryResultSet.class.getName()).log(Level.SEVERE, null, e);
        }	
	}

	@Override
	public String getString(String attributeName) {
		try{
            return m_resultSet.getString(attributeName);
        }
        catch (SQLException e){
            Logger.getLogger(QueryResultSet.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
	}

	@Override
	public Boolean isEmpty() {
		try{
            m_resultSet.beforeFirst();
            return !m_resultSet.isBeforeFirst();
        }
        catch (SQLException e){
            Logger.getLogger(QueryResultSet.class.getName()).log(Level.SEVERE, null, e);
        }
        return true;
	}

	@Override
	public Boolean moveToFirstRow() {
		try{
            return m_resultSet.first();
        }
        catch (SQLException e){
            Logger.getLogger(QueryResultSet.class.getName()).log(Level.SEVERE, null, e);
        }
        return false;
	}

	@Override
	public ResultSet getResultSet() {
		return m_resultSet;
	}

}
