package H2Interaction;

import java.io.*;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

public class ScriptInput {



    public ScriptInput(Connection connection, String fileName){
        InputStream is  = getClass().getClassLoader().getResourceAsStream(fileName);
        new ScriptRunner(connection).runScript(new InputStreamReader(is));
    }

}
