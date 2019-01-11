package H2Interaction;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by U.8902078 on 09/01/2019.
 */
public class SchemaUpgrader {

    //Connection
    private Connection connection;
    private DataSourceManager dataSource;


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
            Statement statement = connection.createStatement();
            statement.executeQuery(checkTableExists);
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
        boolean hasResult = false;
        try {
           hasResult = result.next();
        } catch ( SQLException e ) {
            e.printStackTrace();
        }
        executor.closeStatement();
        return hasResult;
    }

     public void applyUpgrade(List<Upgrade> upgrades){
         checkUpgradeTableExists();
         for(Upgrade upgrade : upgrades) {
             if(scriptExists(upgrade.getFileName())) {
                 if (!upgradeAlreadyApplied(upgrade.getId())) {
                     QueryExecutor executor = new QueryExecutor(connection);
                     String insertIntoUpgradeTable = String.format("INSERT INTO %s VALUES (%s, '%s', '%s', '%s');", Constants.UPGRADE_TABLE, upgrade.getId(), upgrade.getDescription(), upgrade.getFileName(), Status.STARTED);
                     executor.executeUpdate(insertIntoUpgradeTable);

                     try {
                         String completeStatus = String.format("UPDATE %s SET upgrade_status = '%s' where upgrade_id = %s;", Constants.UPGRADE_TABLE, Status.COMPLETE, upgrade.getId());
                         executor.executeUpdate(completeStatus);
                         ScriptInput script = new ScriptInput(connection);
                         script.runScript(upgrade.getFileName());

                     } catch ( Exception e ) {
                         String statusFailed = String.format("UPDATE %s SET upgrade_status = '%s' where upgrade_id = %s;", Constants.UPGRADE_TABLE, Status.FAILED, upgrade.getId());
                         executor.executeUpdate(statusFailed);
                         e.printStackTrace();
                     }
                     executor.closeStatement();
                 }
                 else{
                     existsAndHasFailed(upgrades, upgrade.getId());
                 }
             }
             else {
                 System.out.println(String.format("\n Script \"%s\" not found. \n upgrade id \"%s\" not applied", upgrade.getFileName(), upgrade.getId()));
             }
         }
    }

    public void createUpgradeTable(){
        ScriptInput script = new ScriptInput(connection);
        script.runScript("upgradeTableScript.sql");
    }

    public void backUp(){
        QueryExecutor  executor = new QueryExecutor(dataSource.getConnection());
        executor.backup();
    }

    public boolean scriptExists(String fileName){
        InputStream is  = getClass().getClassLoader().getResourceAsStream(fileName);
        boolean exists = (is != null) ? true : false;
        return exists;
    }

    private int count = 0;
    public void existsAndHasFailed(List<Upgrade> upgrades, int id) {
        String checkStatus = String.format("SELECT UPGRADE_STATUS FROM %s WHERE UPGRADE_ID = %s", Constants.UPGRADE_TABLE, id);
        QueryExecutor executor = new QueryExecutor(connection);
        ResultSet result = executor.executeQuery(checkStatus);
        boolean isComplete = false;
        try {
            result.next();
            String statusResult = result.getString("UPGRADE_STATUS");
            isComplete = statusResult.equals(String.valueOf(Status.COMPLETE)) ? true : false;
        } catch ( SQLException e ) {
            e.printStackTrace();
        }

        if (!isComplete) {
            while (count < 1) {
                String removeRecord = String.format("DELETE FROM %s WHERE UPGRADE_ID = %s", Constants.UPGRADE_TABLE, id);
                executor.executeUpdate(removeRecord);
                count++;
                applyUpgrade(upgrades);
            }
        }
    }
}
