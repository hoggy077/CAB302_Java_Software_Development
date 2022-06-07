package AS1;

import AS1.Database.Database;
import AS1.GUI.Menu;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Menu main = new Menu();

        main.HomeGUI();

        Database db = new Database();

        db.createTable();





    }
}
