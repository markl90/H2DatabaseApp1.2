package H2Interaction;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import org.apache.ibatis.jdbc.ScriptRunner;

public class FileInput {

    public FileInput(Connection connection, String filePath){
        try {
            new ScriptRunner(connection).runScript(new BufferedReader(new FileReader(filePath)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
