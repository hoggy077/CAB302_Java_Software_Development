package AS1.AStar;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Dictionary;

public class AstarSolver {

    public static AStNode FindPath(AStNode start, AStNode End){
        ArrayList<AStNode> OpenHeap = new ArrayList<>();
        ArrayList<AStNode> ClosedHeap = new ArrayList<>();

        OpenHeap.add(start);

        while(OpenHeap.size() > 0)
        {
            OpenHeap.sort(new Comparator<AStNode>() {
                @Override
                public int compare(AStNode o1, AStNode o2) {
                    return  o1.compareFTo(o2);
                }
            });
            AStNode Current = OpenHeap.get(0);
            OpenHeap.remove(0);
            ClosedHeap.add(Current);


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
