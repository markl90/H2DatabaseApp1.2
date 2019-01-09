package H2Interaction;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * Created by U.8902078 on 09/01/2019.
 */
public class SchemaUpgrader {

    //Connection
    Connection connection;
    DatabaseConnection dbConnection;
    Properties properties;

    //Query
    QueryExecutor executor;



    public SchemaUpgrader(){}


    public void initialiseConnection() {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties = new Properties();
            properties.load(is);
            dbConnection = new DatabaseConnection(properties);
            connection = dbConnection.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkLastUpgrade(){
        String checkTableExists = "SELECT * FROM application_schema_upgrade";
        executor = new QueryExecutor(connection);
        executor.executeQuery(checkTableExists);

    }

    public void createUpgradeTable(){
        ScriptInput initialisationScript = new ScriptInput(connection, "upgradeTableScript.sql");
    }


}
