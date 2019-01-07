package H2Interaction;

import com.mysql.cj.jdbc.MysqlDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
//import static H2Interaction.Constants.*;

public class DatabaseConnection {

    Connection connection;
    Properties properties;
    MysqlDataSource dataSource;
    InitialContext initialContext;

    public DatabaseConnection(String propertiesFile) throws IOException {
        properties = PropertyReader.loadFromFile("C:\\Repos\\databaseApp1.2\\src\\main\\resources\\config.properties");
        System.out.println("Connecting to database.");
//        try {
//            Class.forName(properties.getProperty("JDBC_DRIVER"));
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            connection = DriverManager.getConnection(properties.getProperty("DB_URL"),properties.getProperty("USERNAME"),"");
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        try {
            initialContext = new InitialContext();
            dataSource = (MysqlDataSource) initialContext.lookup("jdbc:h2:tcp://localhost/~/test");
        } catch (NamingException e) {
            e.printStackTrace();
        }

        dataSource.setUser(properties.getProperty("USERNAME"));
        dataSource.setPassword("");
        dataSource.setDatabaseName("name");
        try {
            dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public Connection getConnection(){
        return connection;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getDataSource(){
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }



}
