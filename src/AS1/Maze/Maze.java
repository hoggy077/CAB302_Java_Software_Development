package AS1.Maze;

import AS1.AStar.AStNode;
import AS1.AStar.AstarSolver;
import AS1.GUI.MazeRenderPanel;

import java.awt.*;
import java.lang.annotation.Target;
import java.util.*;
import java.util.List;

public class Maze {
    //region General Maze
    public MazeCell[][] MazeMap;
    public final int Height;
    public final int Width;

    /**
     * Generates a maze of Height × Width, within 10 and 100. This will auto populate the maze with cells.
     * @param Height Integer in the bounds of 10 {@literal <}= Height {@literal <}= 100
     * @param Width Integer in the bounds of 10 {@literal <}= Width {@literal <}= 100
     */
    public Maze(int Height, int Width) throws IllegalArgumentException{
        if (Height < 10 || Height > 100 || Width > 100 || Width < 10){
            throw new IllegalArgumentException("Height of Width fails to fit within accepted bounds of 10 to 100");
        }

        this.Height = Height;
        this.Width = Width;
        MazeMap = new MazeCell[Height][Width];
        PopulateMap();
    }

    /**
     * Generates a maze of Height × Width, within 10 and 100. This maze is updated to match the data in CellBytes
     * @param Height Integer in the bounds of 10 {@literal <}= Height {@literal <}= 100
     * @param Width Integer in the bounds of 10 {@literal <}= Width {@literal <}= 100
     * @param CellBytes A string representative of the Maze. Refer to {@link MazeCell#CurrentCellWalls} for the layout of bits per cell
     */
    public Maze(int Height, int Width, String CellBytes)
    {
        if (Height < 10 || Height > 100 || Width > 100 || Width < 10){
            throw new IllegalArgumentException("Height of Width fails to fit within accepted bounds of 10 to 100");
        }

        this.Height = Height;
        this.Width = Width;
        MazeMap = new MazeCell[Height][Width];
        //total = height * width
        StringBuilder stb = new StringBuilder();
        for (int y = 0; y < Height; y++){
            for (int x = 0; x < Width; x++){
                stb.setLength(0);
                //bit strings are 5 characters per cell
                for(int cell = 0; cell < 5; cell++)
                {
                    int Index = (y * (Width * 5)) + (x * 5) + cell;

                    if(Index >= 490)
                        System.out.println("e");

                    stb.append(CellBytes.charAt(Index));

                }
                MazeMap[y][x] = new MazeCell(x, y, this, stb.toString());
            }
        }
    }

    /**
     * @return A string of bits. 5 bits refers to 1 cell. Refer to {@link MazeCell#CurrentCellWalls} for the layout of bits per cell
     */
    public String GetCellString(){
        String Result = "";
        for (int y = 0; y < Height; y++){
            for (int x = 0; x < Width; x++){
                Result += MazeMap[y][x].toString();
            }
        }
        return Result;
    }

    /**
     * Is used to populate the maze map with cells
     */
    private void PopulateMap(){
        for (int X = 0; X < Width; X++){
            for (int Y = 0; Y < Height; Y++){
                MazeMap[Y][X] = new MazeCell(X,Y, this);
            }
        }
    }

    //region Maze generation
    /**
     * Generates a Maze from the current map by using Recursive Backtracking
     * This function will reset the maze prior to generation
     */
    public void GeneratedMaze(){
        Random rnd = new Random();
        for (int y = 0; y < Height; y++){
            for (int x = 0; x < Width; x++){
                MazeMap[y][x].ResetCell();
            }
        }

        ArrayList<MazeCell> Visited = new ArrayList<>();
        ArrayDeque<MazeCell> HistoryStack = new ArrayDeque<>();
        HistoryStack.push(MazeMap[0][0]);
        Visited.add(MazeMap[0][0]);

        MazeCell target = HistoryStack.getFirst();
        while (HistoryStack.size() > 0){
            boolean UnUsedWall = false;
            ArrayList<MazeCell> relevantNeighbors = new ArrayList<>();
            for (AStNode asn:target.NeighborsNodes(false)) {
                MazeCell m = (MazeCell) asn;
                if (!Visited.contains(m)) {
                    relevantNeighbors.add(m);
                    UnUsedWall = true;
                }
            }

            if (!UnUsedWall) {
                target = HistoryStack.pop();
                continue;
            }
            else
            {
                MazeCell RandomNeigh = relevantNeighbors.get(rnd.nextInt(relevantNeighbors.size()));
                CellPosition direction = RandomNeigh.GetPosition().Difference(target.GetPosition());
                target.RemoveWall(MazeCell.Point2Wall(new Point((int)direction.X,(int)direction.Y)));

                Visited.add(RandomNeigh);
                HistoryStack.push(RandomNeigh);
                target = RandomNeigh;
            }
        }
    }
    //endregion

    //endregion

    //region Utility

    /**
     * @param x An Int referring to the position being tested
     * @param y An Int referring to the position being tested
     * @return True if the provided X and Y positions are within the bounds of the current maze
     */
    public boolean Inbounds(int x, int y){
        return x >= 0 && x < Width && y >= 0 && y < Height;
    }
    //endregion

    //region Solving

    /**
     * Provides an internal wrapper for {@link AstarSolver#FindPath(AStNode, AStNode)} that predefines the start and end goals as the upper left and lower right cells.
     * @return returns the result of {@link AstarSolver#FindPath(AStNode, AStNode)}
     */
    public AStNode FindSolution() {
        return AstarSolver.FindPath(MazeMap[0][0], MazeMap[Height-1][Width-1]);
    }
    //endregion
}
