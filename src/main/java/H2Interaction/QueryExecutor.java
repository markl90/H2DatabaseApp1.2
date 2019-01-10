package H2Interaction;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class QueryExecutor {

    Statement statement;

    public QueryExecutor(Connection connection){
        try {
            statement  = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void executeUpdate(String query){
        try {
            statement.executeUpdate(query);
            System.out.println("Executing Update. \n"+query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet executeQuery(String query){
        ResultSet resultSet = null;
        try {
            resultSet = statement.executeQuery(query);
            System.out.println("Executing Query. \n"+query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public void extractResultSet(ResultSet resultSet) throws SQLException {
        while(resultSet.next()){
            int id = resultSet.getInt("id");
            int age = resultSet.getInt("age");
            String first = resultSet.getString("first");
            String last = resultSet.getString("last");
            System.out.print("ID: " + id);
            System.out.print(", Age: " + age);
            System.out.print(", First: " + first);
            System.out.println(", Last: " + last);
        }

    }

    public void backup(){
        DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
        String formattedDate = formatter.format(LocalDate.now());

        String userDirectory = System.getProperty("user.dir");
        Path resourceDirectory = Paths.get("\\src","main","resources\\");
        String backupName = "\\backup" + formattedDate + ".sql";
        String path = userDirectory +resourceDirectory + backupName;

        String backupQuery = "SCRIPT TO '"+path+"';";
        executeQuery(backupQuery);
    }

    public void closeStatement(){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
