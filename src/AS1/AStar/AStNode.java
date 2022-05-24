package AS1.AStar;

import AS1.Maze.MazeCell;

import java.util.ArrayList;

public interface AStNode {
    ArrayList<AStNode> NeighborsNodes(boolean IsAccessible);    //IsAccessible means to only get neighbors without walls between
    float GetHeuristic();
    void UpdateVal(float G);
    void UpdateParent(AStNode P);
}
