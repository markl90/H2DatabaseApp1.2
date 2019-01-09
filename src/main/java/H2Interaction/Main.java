package H2Interaction;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class Main {


    DatabaseConnection dbConnection;
    Connection connection;
    QueryExecutor executor;
    ScriptInput scriptInput;
    Properties properties;

    public Main() throws IOException {
        establishConnection();
        executor = new QueryExecutor(connection);
        executor.executeUpdate(TestData.dropTable);
        scriptInput = new ScriptInput(connection, "Sample-SQL-File-100-Rows.sql");
        executor.executeUpdate(TestData.createTable);
        executor.executeUpdate(TestData.data);
        executor.executeUpdate(TestData.data2);
        executor.executeQuery(TestData.selectFromTable);
        executor.executeUpdate(TestData.updateRecord);
        executor.executeQuery(TestData.selectFromTable);
        executor.executeUpdate(TestData.deleteRecord);
        executor.executeQuery(TestData.selectFromTable);
        executor.executeQuery(backup());
        closeResources();
    }

    public void establishConnection(){
        try( InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            properties = new Properties();
            properties.load(is);
            dbConnection = new DatabaseConnection(properties);
        } catch (IOException e) {
            e.printStackTrace();
        }
        connection = dbConnection.getConnection();
    }

    public void closeResources(){
        executor.closeStatement();
        dbConnection.closeConnection();
    }

    public String backup(){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String formattedDate = formatter.format(LocalDate.now());

        String userDirectory = System.getProperty("user.dir");
        Path resourceDirectory = Paths.get("\\src","main","resources\\");
        String backupName = "\\backup" + formattedDate + ".sql";
        String path = userDirectory +resourceDirectory + backupName;

        String backupQuery = "SCRIPT TO '"+path+"';";
        return backupQuery;
    }


    public static void main(String[] args) throws IOException {
        Main main = new Main();
    }
}
