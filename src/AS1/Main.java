package AS1;

import AS1.AStar.AStNode;
import AS1.Database.database;
import AS1.GUI.MainGUI;
import AS1.GUI.Menu;
import AS1.Maze.Maze;
import AS1.Maze.MazeCell;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {

        Menu main = new Menu();

        main.HomeGUI();

        database db = new database();

        db.createTable();





    }
}
