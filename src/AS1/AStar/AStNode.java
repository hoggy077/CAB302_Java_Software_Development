package AS1.AStar;

import AS1.Maze.CellPosition;
import java.util.ArrayList;

public interface AStNode {
    ArrayList<AStNode> NeighborsNodes(boolean IsAccessible);    //IsAccessible means to only get neighbors without walls between

    float GetF();
    float GetG();

    void UpdateHeuristic(CellPosition EndGoal, float NewG); //update H (distance from goal)
    void UpdateHeuristic(int X, int Y, float NewG); //update H (distance from goal)

    void UpdateParent(AStNode P);

    int compareFTo(AStNode n);
    int comparePosTo(AStNode n);

    CellPosition GetPosition();
}
