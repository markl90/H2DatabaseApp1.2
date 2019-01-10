package H2Interaction;

import org.h2.jdbcx.JdbcDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataSourceManager {

    Connection connection;
    Properties properties;
    JdbcDataSource  dataSource;

    public DataSourceManager(String propertiesName) throws IOException {
        this.properties = importProperties(propertiesName);
        dataSource = new JdbcDataSource();
        dataSource.setURL(properties.getProperty("DB_URL"));
        dataSource.setUser(properties.getProperty("USERNAME"));
        dataSource.setPassword("");
    }

    public Connection getConnection(){
        System.out.println("Connecting to database.");
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void closeConnection(){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Properties importProperties(String propertiesName){
        try( InputStream is = getClass().getClassLoader().getResourceAsStream(propertiesName)) {
            properties = new Properties();
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }

    public Properties getProperties(){
        return properties;
    }


}
