package AS1.AStar;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Dictionary;

public class AstarSolver {

    static ArrayList<AStNode> OpenHeap = new ArrayList<>();
    static ArrayList<AStNode> ClosedHeap = new ArrayList<>();

    /**
     * Uses an A* Pathfinding algorithm to traverse the next closest cell to the target.
     * @param start Is the stating node that extends {@link AStNode}
     * @param End Is the target node that extends {@link AStNode}
     * @return Returns a {@link AStNode} or null. If null, a path could not be found to the End, otherwise, the Node is the End and now has a parent accessible via GetParent(). By traversing each parent till the start node is reached you can create a path.
     */
    public static AStNode FindPath(AStNode start, AStNode End){
        OpenHeap.clear();
        ClosedHeap.clear();

        OpenHeap.add(start);

        while(OpenHeap.size() > 0)
        {
            OpenHeap.sort((o1, o2) -> o1.compareTo(o2));
            AStNode Current = OpenHeap.get(0);
            OpenHeap.remove(0);
            ClosedHeap.add(Current);

            //System.out.println("Cell: %s %s".formatted(Current.GetPosition().X, Current.GetPosition().Y));

            if(Current.comparePosTo(End) == 1)
                return Current;


            ArrayList<AStNode> Neighbors = Current.NeighborsNodes(true);
            for ( AStNode neighbor : Neighbors ) {
                if(ClosedHeap.contains(neighbor))
                    continue;

                if(neighbor.GetG() > Current.GetG() + 1 || !OpenHeap.contains(neighbor)){
                    neighbor.UpdateParent(Current);
                    neighbor.UpdateHeuristic(End.GetPosition(), Current.GetG() + 1);

                    if(!OpenHeap.contains(neighbor))
                        OpenHeap.add(neighbor);
                }
            }
        }

        return null;
    }
}
