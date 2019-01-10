package H2Interaction;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Main {


    DataSourceManager dataSource;
    Connection connection;
    QueryExecutor executor;
    ScriptInput scriptInput;
    Properties properties;

    public Main() throws IOException {
//        File theFile = new File("some path");
//        if(theFile.isDirectory())
//        {
//            theFile.get
//        }


        dataSource = new DataSourceManager("config.properties");
        SchemaUpgrader upgrader = new SchemaUpgrader(dataSource);
        upgrader.initialiseConnection();

    //    upgrader.upgradeAlreadyApplied(1);

        upgrader.applyUpgrade(1, "Sample.sql", "Version 1.0.0 - initial schema");


        executor = new QueryExecutor(dataSource.getConnection());
        executor.backup();
        closeResources();
    }

    public void closeResources(){
        executor.closeStatement();
        dataSource.closeConnection();
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main();
    }
}
