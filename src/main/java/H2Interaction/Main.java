package H2Interaction;

import org.apache.ibatis.javassist.ClassPath;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;

public class Main {

    DatabaseConnection DBconnection;
    Connection connection;
    QueryExecutor executor;
    FileInput fileInput;

    String path = new File("src\\main\\resources\\backup4.sql").getAbsolutePath();
    String backup = "SCRIPT TO '"+path+"';";


    String properties = getClass().getClassLoader().getResource("config.properties").getPath();

            //this.getClass().getClassLoader().getResource("config.properties").getPath();

            //new File("src\\main\\resources\\config.properties").getAbsolutePath();

    public Main() throws IOException {

        DBconnection = new DatabaseConnection(properties);
        connection = DBconnection.getDataSource();


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
