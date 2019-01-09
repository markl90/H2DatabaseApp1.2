package H2Interaction;

/**
 * Created by U.8902078 on 08/01/2019.
 */
public class TestData {

   static String createTable =  "CREATE TABLE user  " +
            "(`id` INTEGER not NULL, " +
            " first VARCHAR(255), " +
            " last VARCHAR(255), " +
            " age INTEGER, " +
            " PRIMARY KEY ( id ))";
    static String data = "INSERT INTO user VALUES (100, 'Mark', 'Ledwold', 28)";
    static String data2 = "INSERT INTO user " + "VALUES (101, 'John', 'Smith', 25)";
    static String dropTable = "DROP TABLE user, user_details IF EXISTS";
    static String selectFromTable = "SELECT id, first, last, age FROM user";
    static String updateRecord = "UPDATE registration SET age = 30 where id = 101";
    static String deleteRecord = "DELETE FROM registration WHERE id = 101";

}
