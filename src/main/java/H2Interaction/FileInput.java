package H2Interaction;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import org.apache.ibatis.jdbc.ScriptRunner;

public class FileInput {

    public FileInput(Connection connection, String file){

        String path = new File("src\\main\\resources\\"+file).getAbsolutePath();
        try {
            new ScriptRunner(connection).runScript(new BufferedReader(new FileReader(path)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

}
