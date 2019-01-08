package H2Interaction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

public class Main {


    DatabaseConnection DBconnection;
    Connection connection;
    QueryExecutor executor;
    FileInput fileInput;
    Properties properties;
    FileInputStream input;

    String path = getClass().getClassLoader().getResource("backup4.sql").getPath();
    String backup = "SCRIPT TO '"+path+"';";

    public Main() throws IOException {

       try( FileInputStream fis = new FileInputStream(getClass().getClassLoader().getResource("config.properties").getPath())) {
           properties = new Properties();
           properties.load(fis);
       }
        DBconnection = new DatabaseConnection(properties);
        connection = DBconnection.getConnection();


        executor = new QueryExecutor(connection);
        executor.executeUpdate(TestData.dropTable);
        fileInput = new FileInput(connection, "Sample-SQL-File-100-Rows.sql");
        executor.executeUpdate(TestData.createTable);
        executor.executeUpdate(TestData.data);
        executor.executeUpdate(TestData.data2);
        executor.executeQuery(TestData.selectFromTable);
        executor.executeUpdate(TestData.updateRecord);
        executor.executeQuery(TestData.selectFromTable);
       // executor.executeUpdate(deleteRecord);
       // executor.executeQuery(selectFromTable);
        executor.executeQuery(backup);
        closeResources();
    }

    public void closeResources(){
        executor.closeStatement();
        DBconnection.closeConnection();
    }


    public static void main(String[] args) throws IOException {
        Main main = new Main();
    }
}
