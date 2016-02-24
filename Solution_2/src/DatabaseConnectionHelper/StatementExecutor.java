package DatabaseConnectionHelper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import Interfaces.IDatabaseConnection;
import Interfaces.IQueryResultSet;
import Interfaces.IStatementExecutor;

class StatementExecutor implements IStatementExecutor{

	
	private Statement m_statement;
    
    public StatementExecutor(IDatabaseConnection connection){
        if(connection == null) throw new IllegalArgumentException("connection is null");
        createStatement(connection);
    }
    
    private void createStatement(IDatabaseConnection connection) {
        try{
            m_statement = connection.getDatabaseConnection().createStatement(
            ResultSet.TYPE_SCROLL_SENSITIVE,
            ResultSet.CONCUR_UPDATABLE);
        }
        catch (SQLException e){
            Logger.getLogger(StatementExecutor.class.getName()).log(Level.SEVERE, null, e);
        }
    }
	
	@Override
	public void close() throws Exception {
		try{
            if(m_statement != null){
                m_statement.close();
                m_statement = null;
            }
        }
        catch (SQLException e){
            Logger.getLogger(StatementExecutor.class.getName()).log(Level.SEVERE, null, e);
        }
	}

	@Override
	public void executeUpdate(String sqlStatement) {
		try{
            m_statement.executeUpdate(sqlStatement);
        }
        catch (SQLException e){
            Logger.getLogger(StatementExecutor.class.getName()).log(Level.SEVERE, null, e);
        }
	}

	@Override
	public IQueryResultSet executeQuery(String sqlQuery) {
		try{
            return new QueryResultSet(m_statement.executeQuery(sqlQuery));
        }
        catch (SQLException e){
            Logger.getLogger(StatementExecutor.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
	}

}
