package AS1;

import AS1.AStar.AStNode;
import AS1.Maze.Maze;
import AS1.Maze.MazeCell;
import AS1.Maze.MazeCell.CellState;

public class Main {
    public static void main(String[] args){
        Maze m = new Maze(20,45);

        AStNode[] neighbors = Maze.MazeMap[0][0].NeighborsNodes();


    }
}
