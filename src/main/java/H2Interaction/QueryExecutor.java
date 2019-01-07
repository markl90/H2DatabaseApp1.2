package H2Interaction;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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

    public void executeQuery(String query){
        try {
            ResultSet resultSet = statement.executeQuery(query);
            System.out.println("Executing Query. \n"+query);
            extractResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public void closeStatement(){
        try {
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}
