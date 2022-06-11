package AS1.AStar;

import AS1.Maze.CellGroup;
import AS1.Maze.CellPosition;
import java.util.ArrayList;

public interface AStNode {
    /**
     * This is used to acquire all cardinal neighbors of the node.
     * @param IsAccessible Determines if the function should take into account a wall between the nodes or not. True means it will only return nodes that do not have a wall between this node and the targeted node.
     * @return Returns an {@link ArrayList} of {@link AStNode AStNodes} dependant on IsAccessible.
     */
    ArrayList<AStNode> NeighborsNodes(boolean IsAccessible);    //IsAccessible means to only get neighbors without walls between

    /**
     * This is used to acquire all cardinal neighbors of a group of nodes.
     * @param IsAccessible Determines if the function should take into account a wall between the nodes or not. True means it will only return nodes that do not have a wall between this node and the targeted node.
     * @param Evaluated Refers to the nodes of the group that have previously had their cardinal neighbors requested.
     * @return Returns an {@link ArrayList} of {@link AStNode AStNodes} dependant on IsAccessible.
     */
    ArrayList<AStNode> NeighborsNodes(boolean IsAccessible, ArrayList<AStNode> Evaluated);    //IsAccessible means to only get neighbors without walls between

    /**
     * @return Returns Distance traveled + Distance from target
     */
    float GetF();//Distance traveled + Distance from target
    /**
     * @return Returns Distance traveled
     */
    float GetG();//Distance traveled
    /**
     * @return Returns Distance from target
     */
    float GetH();//Distance from target

    /**
     * Is used to update a cells G and H values
     * @param EndGoal Refers to the end target supplied by {@link AstarSolver#FindPath(AStNode, AStNode)}
     * @param NewG The new value of G, which equals the current nodes total covered distance + 1, where 1 is the distance from the current node to the node being updated
     */
    void UpdateHeuristic(CellPosition EndGoal, float NewG); //update H (distance from goal)

    /**
     * @return Returns if the cell is marked as Active
     */
    boolean GetValid(); //refers to if the cell is active. NeighborsNodes should take this into account instead of using this

    /**
     * Used to update the internal Parent reference
     * @param P The node which is now the parent of this node
     */
    void UpdateParent(AStNode P);

    /**
     * @return Returns if the current node has a set Parent or not
     */
    boolean HasParent();

    /**
     * @return Returns the Parent node
     */
    AStNode GetParent();

    /**
     * Is used to compare the F and H values of the node.
     * @param n Node in which to compare too.
     * @return Returns -1 to 1, where -1 means the compared node is a better option than the current node, and the opposing for 1
     */
    int compareTo(AStNode n);

    /**
     * Compares the X and Y position of the current node to n
     * @param n The node in which to compare to
     * @return Returns 1 if equal.
     */
    int comparePosTo(AStNode n);

    /**
     * @return Returns the {@link CellPosition} of the current node
     */
    CellPosition GetPosition();
}
