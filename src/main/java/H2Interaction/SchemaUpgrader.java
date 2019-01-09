package H2Interaction;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Created by U.8902078 on 09/01/2019.
 */
public class SchemaUpgrader {

    //Connection
    Connection connection;
    DatabaseConnection dataSource;
    Properties properties;





    public SchemaUpgrader(){}


    public void initialiseConnection(String propertiesFile) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            properties = new Properties();
            properties.load(is);
            dataSource = new DatabaseConnection(properties);
            connection = dataSource.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkLastUpgrade() {
        String checkTableExists = String.format("SELECT * FROM %s;", Constants.UPGRADE_TABLE);
        QueryExecutor executor = new QueryExecutor(connection);
        try {
            ResultSet result = executor.executeQuery(checkTableExists);
            result.wasNull();
        } catch (Exception e) {
            System.out.println(Constants.UPGRADE_TABLE + " not found...");
            System.out.println("creating table " + Constants.UPGRADE_TABLE +"...");
            createUpgradeTable();
        }
        executor.closeStatement();
    }

     public void applyUpgrade(int id, String scriptName, String description){
         QueryExecutor executor = new QueryExecutor(connection);
         String status = "STARTED";
         String insertIntoUpgradeTable = String.format("INSERT INTO %s VALUES (%s, '%s', '%s', '%s');", Constants.UPGRADE_TABLE, id, description, scriptName, status);
         executor.executeUpdate(insertIntoUpgradeTable);

         try {
             ScriptInput scriptInput = new ScriptInput(connection, "Sample.sql");
             String updateStatus = String.format("UPDATE %s SET upgrade_status = 'COMPLETE' where upgrade_id = %s;", Constants.UPGRADE_TABLE, id);
             executor.executeUpdate(updateStatus);
         }
         catch (Exception e){
             String updateStatus = String.format("UPDATE %s SET upgrade_status = 'FAILED' where upgrade_id = %s;", Constants.UPGRADE_TABLE, id);
             executor.executeUpdate(updateStatus);
             e.printStackTrace();
         }
         executor.closeStatement();
    }

    public void createUpgradeTable(){
        new ScriptInput(connection, "upgradeTableScript.sql");
    }


}
