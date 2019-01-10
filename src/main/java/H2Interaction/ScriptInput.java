package H2Interaction;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

public class ScriptInput {

    private Connection connection;


    public ScriptInput(Connection connection){
       this.connection = connection;
    }

    public void runScript(String fileName){
        InputStream is  = getClass().getClassLoader().getResourceAsStream(fileName);
        new ScriptRunner(connection).runScript(new InputStreamReader(is));
    }

}
