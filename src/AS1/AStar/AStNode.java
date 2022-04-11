package AS1.AStar;

import AS1.Maze.MazeCell;

public interface AStNode {
    AStNode[] NeighborsNodes(MazeCell[][] Map);
    float GetHeuristic();
    void UpdateVal(float G);
}
