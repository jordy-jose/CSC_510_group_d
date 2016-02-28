package com.myTrackerApplication.UserAuthentication;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.myTrackerApplication.DatabaseConnectionHelper.DatabaseConnectionService;
import com.myTrackerApplication.Interfaces.*;
import com.myTrackerApplication.client.Interfaces.IDBHelperService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Sameer Sharma on 26/02/16.
 */
public class DBHelperService extends RemoteServiceServlet implements IDBHelperService {


    public String insertUserData(HashMap<String, String> m) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){
                String insertIntoMS = String.format("INSERT INTO MS (`Orientaion course (CSC600)`," +
                        "`Core courses - systems`," +
                        "`Minor Courses - credits`,`Seminars attended`,`Graduate Electives`," +
                        "`Thesis Research - credits`,`Unity ID`,`Core courses - Theory`) VALUES (");
                int counter = 0;
                for (Map.Entry<String, String> e : m.entrySet()) {
                    counter++;
                    if(counter<m.size()) {
                        insertIntoMS = String.format(insertIntoMS + "'" + e.getValue() + "',");
                    }
                    else{
                        insertIntoMS = String.format(insertIntoMS + "'" + e.getValue() + "'");
                    }
                }
                insertIntoMS = String.format(insertIntoMS + ");");
                stmtExecutor.executeUpdate(insertIntoMS);
                return "success";
            }
        }

    }


    public HashMap<String, String> readUserData(String unityID) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){

                String readUserDtata = String.format("SELECT * FROM MS " +
                        "WHERE `Unity ID`= '%s'", unityID);
                IQueryResultSet ns = stmtExecutor.executeQuery(readUserDtata);

                HashMap<String, String> hMap = new HashMap();
                ns.getResultSet().next();
                hMap.put("Unity ID", ns.getResultSet().getString("Unity ID"));
                hMap.put("Orientaion course (CSC600)", ns.getResultSet().getString("Orientaion course (CSC600)"));
                hMap.put("Thesis Research - credits", ns.getResultSet().getString("Thesis Research - credits"));
                hMap.put("Core courses - Theory", ns.getResultSet().getString("Core courses - Theory"));
                hMap.put("Core courses - systems", ns.getResultSet().getString("Core courses - systems"));
                hMap.put("Graduate Electives", ns.getResultSet().getString("Graduate Electives"));
                hMap.put("Minor Courses - credits", ns.getResultSet().getString("Minor Courses - credits"));
                hMap.put("Seminars attended", ns.getResultSet().getString("Seminars attended"));
                return hMap;
            }
        }
    }


    public String updateUserData(HashMap<String, String> m) throws Exception{
        try (IDatabaseConnection connection = DatabaseConnectionService.createDatabaseConnection()){
            try (IStatementExecutor stmtExecutor = DatabaseConnectionService.createStatementExecutor(connection)){

                for (Map.Entry<String, String> e : m.entrySet()) {
                    if(e.getKey() != "Unity ID") {
                        String updateIntoMS = String.format("UPDATE MS SET " + "`" + e.getKey() + '`' + "='" +
                                e.getValue() + "' WHERE `Unity ID`= '%s';", m.get("Unity ID"));
                        stmtExecutor.executeUpdate(updateIntoMS);
                    }
                }
                return "success";
            }
        }
    }

    public HashMap<String, String> generateReportData(HashMap<String, String> m) throws Exception{

        final int orientationCredits = 1 - Integer.parseInt(m.get("Orientaion course (CSC600)"));
        final int thesisCredits = 9 - Integer.parseInt(m.get("Thesis Research - credits"));
        final int coreTheory = 6 - Integer.parseInt(m.get("Core courses - Theory"));
        final int coreSystems = 6 - Integer.parseInt(m.get("Core courses - systems"));
        final int graduateElectives = 9 - Integer.parseInt(m.get("Graduate Electives"));
        final int minorCredits = 3 - Integer.parseInt(m.get("Minor Courses - credits"));
        final int seminarsAttended = 4 - Integer.parseInt(m.get("Seminars attended"));

        m.put("Orientaion course (CSC600)" , String.valueOf(orientationCredits));
        m.put("Thesis Research - credits", String.valueOf(thesisCredits));
        m.put("Core courses - Theory", String.valueOf(coreTheory));
        m.put("Core courses - systems", String.valueOf(coreSystems));
        m.put("Graduate Electives", String.valueOf(graduateElectives));
        m.put("Minor Courses - credits", String.valueOf(minorCredits));
        m.put("Seminars attended", String.valueOf(seminarsAttended));

        return m;
    }

}
