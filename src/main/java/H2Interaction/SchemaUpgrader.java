package H2Interaction;

import com.sun.istack.internal.NotNull;
import org.h2.jdbc.JdbcSQLException;

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
    DatabaseConnection dbConnection;
    Properties properties;

    //Query
    QueryExecutor executor;



    public SchemaUpgrader(){}


    public void initialiseConnection(String propertiesFile) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesFile)) {
            properties = new Properties();
            properties.load(is);
            dbConnection = new DatabaseConnection(properties);
            connection = dbConnection.getConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void checkLastUpgrade(){
        String checkTableExists = "SELECT * FROM application_schema_upgrade;";
        executor = new QueryExecutor(connection);
        try {
            ResultSet result = executor.executeQuery(checkTableExists);
            result.wasNull();
        } catch (Exception e){
            System.out.println("application_schema_upgrade not found...");
            System.out.println("creating table application_schema_upgrade...");
            createUpgradeTable();
        }


    }

    public void createUpgradeTable(){
        ScriptInput initialisationScript = new ScriptInput(connection, "upgradeTableScript.sql");
    }


}
