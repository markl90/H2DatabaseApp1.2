package H2Interaction;

import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    Connection connection;
    Properties properties;
    JdbcDataSource  dataSource;

    public DatabaseConnection(Properties propertiesFile) throws IOException {

        this.properties = propertiesFile;
        System.out.println("Connecting to database.");

        dataSource = new JdbcDataSource();
        dataSource.setURL(properties.getProperty("DB_URL"));
        dataSource.setUser(properties.getProperty("USERNAME"));
        dataSource.setPassword("");

        try {
            connection = dataSource.getConnection();
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


}
