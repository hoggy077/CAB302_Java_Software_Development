package AS1.Maze;

import AS1.AStar.AStNode;
import AS1.AStar.AstarSolver;

import java.util.*;

public class Maze {
    //region General Maze
    public MazeCell[][] MazeMap;
    public final int Height;
    public final int Width;

    /**
     * Generates a maze of Height × Width, within 100. This will auto populate the maze with cells.
     * @param Height Integer in the bounds of 0 < Height <= 100
     * @param Width Integer in the bounds of 0 < Width <= 100
     */
    public Maze(int Height, int Width) {
        if (Height <= 0 || Height > 100 || Width > 100 || Width <= 0){
            System.out.println("Values out of bounds"); //--This needs tp be replaced with an exception throw
            this.Height = 0;
            this.Width = 0;
            return;
        }

        this.Height = Height;
        this.Width = Width;
        MazeMap = new MazeCell[Height][Width];
        PopulateMap();
    }

    private void PopulateMap(){
        for (int X = 0; X < Width; X++){
            for (int Y = 0; Y < Height; Y++){
                MazeMap[Y][X] = new MazeCell(X,Y, this);
            }
        }
    }

    //region Maze generation
    //Based on Randomized Prim's algorithm
    /*public void GeneratedMaze(){

        Random rnd = new Random();
        int OriginX = rnd.nextInt(0,Width), OriginY = rnd.nextInt(0, Height);


        List<MazeCell> OpenHeap = new ArrayList<MazeCell>();
        List<MazeCell> ClosedHeap = new ArrayList<MazeCell>();

        ClosedHeap.add(MazeMap[OriginY][OriginX]);
        OpenHeap.addAll((Collection<? extends MazeCell>) MazeMap[OriginY][OriginX].NeighborsNodes());

        while(OpenHeap.size() > 0){
            int CellNum = rnd.nextInt(0, OpenHeap.size());
            MazeCell cell = OpenHeap.get(CellNum);

            //neighbors that are active
            ArrayList<MazeCell> neighborsOfRandom = new ArrayList<>();
            neighborsOfRandom.addAll((Collection<? extends MazeCell>) cell.NeighborsNodes());


        }
    }*/
    //endregion

    //endregion

    //region Solver
    public AStNode FindSolution() {
        return AstarSolver.FindPath(MazeMap[0][0], MazeMap[Height-1][Width-1]);
    }
    //endregion
}
