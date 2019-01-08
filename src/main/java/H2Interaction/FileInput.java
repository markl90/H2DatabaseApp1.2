package H2Interaction;

import java.io.*;
import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;

public class FileInput {



    public FileInput(Connection connection, String file){
        InputStream is = null;
        try {
            is = new FileInputStream(getClass().getClassLoader().getResource(file).getPath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        new ScriptRunner(connection).runScript(new InputStreamReader(is));

    }

}
