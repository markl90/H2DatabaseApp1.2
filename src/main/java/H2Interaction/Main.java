package H2Interaction;

import java.io.IOException;
import java.sql.Connection;

public class Main {

    DatabaseConnection DBconnection;
    Connection connection;
    QueryExecutor executor;
    FileInput fileInput;

    String createTable =  "CREATE TABLE   REGISTRATION " +
            "(`id` INTEGER not NULL, " +
            " first VARCHAR(255), " +
            " last VARCHAR(255), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";
    String data = "INSERT INTO Registration " + "VALUES (100, 'Mark', 'Ledwold', 28)";
    String data2 = "INSERT INTO Registration " + "VALUES (101, 'John', 'Smith', 25)";
    String dropTable = "DROP TABLE registration, user_details";
    String selectFromTable = "SELECT id, first, last, age FROM Registration";
    String updateRecord = "UPDATE registration SET age = 30 where id = 101";
    String deleteRecord = "DELETE FROM registration WHERE id = 101";

    String backup = "SCRIPT TO 'C:\\Repos\\databaseApp1.1\\src\\main\\resources\\backup3.sql';";

    String properties = "C:\\Repos\\databaseApp1.2\\src\\main\\resources\\config.properties";

    public Main() throws IOException {

        DBconnection = new DatabaseConnection(properties);
        connection = DBconnection.getDataSource();


        executor = new QueryExecutor(connection);
        executor.executeUpdate(dropTable);
        fileInput = new FileInput(connection, "C:\\Repos\\databaseApp1.1\\src\\main\\resources\\Sample-SQL-File-100-Rows.sql"); //Sample-SQL-File-100-Rows.sql
        executor.executeUpdate(createTable);
        executor.executeUpdate(data);
        executor.executeUpdate(data2);
        executor.executeQuery(selectFromTable);
        executor.executeUpdate(updateRecord);
        executor.executeQuery(selectFromTable);
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
