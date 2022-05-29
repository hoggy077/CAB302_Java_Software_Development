package AS1;

import AS1.AStar.AStNode;
import AS1.GUI.MainGUI;
import AS1.Maze.Maze;
import AS1.Maze.MazeCell;

public class Main {
    public static void main(String[] args){
        Maze m = new Maze(20,20);

        //m.MazeMap[0][0].RemoveWall(MazeCell.CellWall.RIGHT);

        MainGUI gui = new MainGUI(m);

        gui.HomeGUI();


    }
}
