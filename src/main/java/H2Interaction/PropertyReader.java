package H2Interaction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

    Properties properties = new Properties();
    //FileInputStream input = new FileInputStream("C:\\Repos\\databaseApp1.2\\src\\main\\resources\\config.properties"); //C:\Repos\databaseApp1.2\src\main\resources\config.properties


    public static Properties loadFromFile(String file) throws IOException, FileNotFoundException {
        Properties properties = new Properties();
        FileInputStream stream = new FileInputStream(file);
        try {
            properties.load(stream);
        } finally {
            stream.close();
        }
        return properties;
    }



}
