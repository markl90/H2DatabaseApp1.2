package H2Interaction;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by U.8902078 on 09/01/2019.
 */
public class SchemaUpgrader {

    //Connection
    Connection connection;
    DataSourceManager dataSource;
    Properties properties;


    public SchemaUpgrader(DataSourceManager dataSource){
        this.dataSource = dataSource;
    }

    public void initialiseConnection() {
        this.connection = dataSource.getConnection();
    }

    public void checkUpgradeTableExists() {
        String checkTableExists = String.format("SELECT * FROM %s;", Constants.UPGRADE_TABLE);
        QueryExecutor executor = new QueryExecutor(connection);
        try {
            ResultSet result = executor.executeQuery(checkTableExists);
            result.wasNull();
        } catch (SQLException e) {

            System.out.println(Constants.UPGRADE_TABLE + " not found...");
            System.out.println("creating table " + Constants.UPGRADE_TABLE +"...");
            createUpgradeTable();
        }
        executor.closeStatement();
    }

    public boolean upgradeAlreadyApplied(int id)  {
        String checkUpgrade = String.format("SELECT * FROM %s WHERE UPGRADE_ID = %s", Constants.UPGRADE_TABLE, id);
        QueryExecutor executor = new QueryExecutor(connection);
        ResultSet result = executor.executeQuery(checkUpgrade);
        Long results = null;
        boolean hasresult = false;
        try {
           hasresult = result.next();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        executor.closeStatement();
        return hasresult;
    }

     public void applyUpgrade(int id, String scriptName, String description){
        if (!upgradeAlreadyApplied(id)) {
            QueryExecutor executor = new QueryExecutor(connection);
            Status status = Status.STARTED;
            String insertIntoUpgradeTable = String.format("INSERT INTO %s VALUES (%s, '%s', '%s', '%s');", Constants.UPGRADE_TABLE, id, description, scriptName, status);
            executor.executeUpdate(insertIntoUpgradeTable);

            try {
                status = Status.COMPLETE;
                String completeStatus = String.format("UPDATE %s SET upgrade_status = '%s' where upgrade_id = %s;", Constants.UPGRADE_TABLE, status, id);
                executor.executeUpdate(completeStatus);
                new ScriptInput(connection, scriptName);
            } catch ( Exception e ) {
                status = Status.FAILED;
                String statusFailed = String.format("UPDATE %s SET upgrade_status = '%s' where upgrade_id = %s;", Constants.UPGRADE_TABLE, status, id);
                executor.executeUpdate(statusFailed);
                e.printStackTrace();
            }
            executor.closeStatement();
        }

    }

    public void createUpgradeTable(){
        new ScriptInput(connection, "upgradeTableScript.sql");
    }


}
