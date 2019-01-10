package H2Interaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private DataSourceManager dataSource;
    private QueryExecutor executor;
    private SchemaUpgrader upgrader;

    public Main(String properties) throws IOException {
//        File theFile = new File("some path");
//        if(theFile.isDirectory())
//        {
//            theFile.get
//        }

        Upgrade initial = new Upgrade(1, "Sample.sql", "Version 1.0.0 - initial schema");
        Upgrade upgrade1 = new Upgrade(2, "upgrade.sql", "Version 1.0.1 - Add age to user details");

        List<Upgrade> upgrades = new ArrayList<Upgrade>();
        upgrades.add(initial);
        upgrades.add(upgrade1);

        dataSource = new DataSourceManager(properties);
        upgrader = new SchemaUpgrader(dataSource);
        upgrader.initialiseConnection();
        upgrader.applyUpgrade(upgrades);


        executor = new QueryExecutor(dataSource.getConnection());
        executor.backup();
        closeResources();
    }

    public void closeResources(){
        executor.closeStatement();
        dataSource.closeConnection();
    }

    public static void main(String[] args) throws IOException {
        Main main = new Main("config.properties");
    }
}
