package H2Interaction;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private DataSourceManager dataSource;
    private SchemaUpgrader upgrader;

    public Main(String properties) throws IOException {

        Upgrade initial = new Upgrade(1, "Sample.sql", "Version 1.0.0 - initial schema");
        Upgrade upgrade1 = new Upgrade(2, "upgrade.sql", "Version 1.0.1 - Add age to user details");
        Upgrade upgrade2 = new Upgrade(3, "upgrade2.sql", "Version 1.0.2 - Add address table");
        Upgrade upgrade3 = new Upgrade(4, "invalidFile.sql", "Version 1.0.3 - Add address table");

        List<Upgrade> upgrades = new ArrayList<>();
        upgrades.add(initial);
        upgrades.add(upgrade1);
        upgrades.add(upgrade2);
        upgrades.add(upgrade3);

        dataSource = new DataSourceManager(properties);
        upgrader = new SchemaUpgrader(dataSource);
        upgrader.initialiseConnection();
        upgrader.applyUpgrade(upgrades);
        dataSource.closeConnection();

    }

    public static void main(String[] args) throws IOException {
        Main main = new Main("config.properties");
    }
}
