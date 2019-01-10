package H2Interaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
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
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(checkTableExists);
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
             if (!upgradeAlreadyApplied(upgrade.getId())) {
                 QueryExecutor executor = new QueryExecutor(connection);
                 Status status = Status.STARTED;
                 String insertIntoUpgradeTable = String.format("INSERT INTO %s VALUES (%s, '%s', '%s', '%s');", Constants.UPGRADE_TABLE, upgrade.getId(), upgrade.getDescription(), upgrade.getFileName(), status);
                 executor.executeUpdate(insertIntoUpgradeTable);

                 try {
                     status = Status.COMPLETE;
                     String completeStatus = String.format("UPDATE %s SET upgrade_status = '%s' where upgrade_id = %s;", Constants.UPGRADE_TABLE, status, upgrade.getId());
                     ScriptInput script = new ScriptInput(connection);
                     script.runScript(upgrade.getFileName());
                     Statement statement = connection.createStatement();
                     statement.executeUpdate(completeStatus);
                     executor.executeUpdate(completeStatus);
                 } catch (SQLException e) {
                     status = Status.FAILED;
                     String statusFailed = String.format("UPDATE %s SET upgrade_status = '%s' where upgrade_id = %s;", Constants.UPGRADE_TABLE, status, upgrade.getId());
                     executor.executeUpdate(statusFailed);
                     e.printStackTrace();
                 }
                 executor.closeStatement();
             }
         }
    }

    public void createUpgradeTable(){
        ScriptInput script = new ScriptInput(connection);
        script.runScript("upgradeTableScript.sql");
    }


}
