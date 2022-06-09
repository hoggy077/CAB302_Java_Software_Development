package AS1.AStar;

import AS1.Maze.CellPosition;
import java.util.ArrayList;

public interface AStNode {
    ArrayList<AStNode> NeighborsNodes(boolean IsAccessible);    //IsAccessible means to only get neighbors without walls between

    float GetF();//Distance traveled + Distance from target
    float GetG();//Distance traveled
    float GetH();//Distance from target

    void UpdateHeuristic(CellPosition EndGoal, float NewG); //update H (distance from goal)

    boolean GetValid(); //refers to if the cell is active. NeighborsNodes should take this into account instead of using this

    void UpdateParent(AStNode P);
    boolean HasParent();
    AStNode GetParent();

    int compareTo(AStNode n);
    int comparePosTo(AStNode n);

    CellPosition GetPosition();
}
