package AS1.Maze;

import AS1.AStar.AStNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class MazeCell implements AStNode {
    //region BitSet explanation
    //5 bit long byte, where the first bit refers to if the cell is active or inactive
    //00000 = no walls and active
    //00001 = Down wall
    //00010 = Up wall
    //00100 = Right wall
    //01000 = Left wall
    //10000 = Inactive. At this point the other bits have no value, design accordingly
    //endregion
    //region Class variables
    private BitSet CurrentCellWalls = new BitSet(5);
    private CellPosition CurrentPos;
    private Maze Parent;
    //endregion



    //region Cell related enums
    public enum CellWall{
        DOWN(4, new Point(0, 1)),
        UP(3, new Point(0, -1)),
        RIGHT(2, new Point(1, 0)),
        LEFT(1, new Point(-1, 0));

        public int value;
        public Point Direction;
        private CellWall(int relativeBit, Point direction)throws IndexOutOfBoundsException {
            if(relativeBit > 4 || relativeBit < 1)
                throw new IndexOutOfBoundsException(relativeBit);
            else{
                value = relativeBit;
                Direction = direction;
            }
        }
    }
    public enum CellState{
        ACTIVE(0, false),
        INACTIVE(0,true);

        public int value;
        public boolean state;
        private CellState(int relativeBit, boolean inactive) throws IndexOutOfBoundsException {
            if(relativeBit > 4 || relativeBit < -1)
                throw new IndexOutOfBoundsException(relativeBit);
            else {
                value = relativeBit;
                state = inactive;
            }
        }
    }
    //endregion

    //region Constructors
    /**
     * Constructs a mazcell at a designated position within the Maze class
     * @param position_X relative X position to the upper left cell
     * @param position_Y relative Y position to the upper left cell
     * @param Parent provides an internal reference to the parent maze for neighbor cell updating
     */
    public MazeCell(float position_X, float position_Y, Maze Parent){
        CurrentPos = new CellPosition(position_X, position_Y);
        this.Parent = Parent;

        //dont even ask, fromind, toind version of this wasn't setting up and down correctly
        CurrentCellWalls.set(1,true);
        CurrentCellWalls.set(2,true);
        CurrentCellWalls.set(3,true);
        CurrentCellWalls.set(4,true);
    }
    //endregion



    /**
     * Provides a CellPosition class referring to the internal stored reference
     * @return
     */
    public CellPosition GetPosition(){ return CurrentPos;}



    /**
     * Returns the cells wall state
     * @param wall refers to the wall desired
     * @return true = Wall, false = No wall
     */
    public boolean CheckWall(CellWall wall){return CurrentCellWalls.get(wall.value);}


    /**
     * Adds a wall to the cell in the designated direction. This should be used externally if looking to set a wall
     * @param wall Intended wall to add
     */
    public void AddWall(CellWall wall){
        if(!CurrentCellWalls.get(wall.value))
            CurrentCellWalls.set(wall.value,true);

        if(Inbounds((int) CurrentPos.X + wall.Direction.x, (int) CurrentPos.Y + wall.Direction.y))
            Parent.MazeMap[(int) CurrentPos.Y + wall.Direction.y][(int) CurrentPos.X + wall.Direction.x].AddNeighborWall(InverseWall(wall));
    }
    /**
     * SHOULD NOT BE CALLED. Unlike AddWall, this adds a cell wall without adding one of the neighboring cell. Only use this if desiring a 1 way wall effect
     * @param wall Wall being added
     */
    public void AddNeighborWall(CellWall wall){
        if(!CurrentCellWalls.get(wall.value))
            CurrentCellWalls.set(wall.value,true);
    }


    /**
     * Removes a wall to the cell in the designated direction. This should be used externally if looking to set a wall
     * @param wall Intended wall to add
     */
    public void RemoveWall(CellWall wall){
        if(CurrentCellWalls.get(wall.value))
            CurrentCellWalls.set(wall.value,false);

        if(Inbounds((int) CurrentPos.X + wall.Direction.x, (int) CurrentPos.Y + wall.Direction.y))
            Parent.MazeMap[(int) CurrentPos.Y + wall.Direction.y][(int) CurrentPos.X + wall.Direction.x].RemoveNeighborWall(InverseWall(wall));
    }
    /**
     * SHOULD NOT BE CALLED. Unlike RemoveWall, this removes a cell wall without removing one of the neighboring cell. Only use this if desiring a 1 way wall effect
     * @param wall Wall being added
     */
    public void RemoveNeighborWall(CellWall wall){
        if(CurrentCellWalls.get(wall.value))
            CurrentCellWalls.set(wall.value,false);
    }


    /**
     * Provides a cardinal opposite of the provided direction
     * @param wall
     * @return
     */
    public static CellWall InverseWall(CellWall wall){
        switch (wall){
            case DOWN : { return CellWall.UP; }
            case UP : { return CellWall.DOWN; }
            case LEFT : { return CellWall.RIGHT; }
            case RIGHT : { return CellWall.LEFT; }
        }
        return null;
    }

    /**
     * Provides an inbound check to determine if a wall being added/removed has an inbound neighbor
     * @param x X position of the target cell
     * @param y Y position of the target cell
     * @return
     */
    boolean Inbounds(int x, int y){
        return x >= 0 && x < Parent.Width && y >= 0 && y < Parent.Height;
    }

    //this just converts a point direction to a wall, its used for the UI system to turn dragging from cell to cell into a wall to be used
    /**
     * Provides a function to convert a Point vector into a CellWall, this should be only for UI to MazeCell methods
     * @param Direction Point in the format of -1 to 1. -1 refers to the left or up, 1 refers to right or down
     * @return
     */
    public static CellWall Point2Wall(Point Direction){
        for (CellWall w: CellWall.values()) {
            if(w.Direction.equals(Direction))
                return w;
        }
        return null;
    }


    //Utility Methods
    /**
     *Reset the cell to Active with all walls
     */
    public void ResetCell(){ CurrentCellWalls.set(0,CellState.ACTIVE.value); CurrentCellWalls.set(1,true); CurrentCellWalls.set(2,true); CurrentCellWalls.set(3,true); CurrentCellWalls.set(4,true); }

    /**
     * @return Returns the state of the cell.
     */
    public boolean IsActive(){return !CurrentCellWalls.get(0);}

    /**
     * Sets the cell to a specific state
     * @param state
     */
    public void SetActive(CellState state){ CurrentCellWalls.set(0, state.value); }

    public int GetWallCount(){
        int c = 0;
        for (int i = 1; i <= 4; i++) {
            if(CurrentCellWalls.get(i))
                c++;
        }
        return c;
    }


    //region A⭐ integration
    @Override
    public ArrayList<AStNode> NeighborsNodes(boolean IsAccessible) {
        MazeCell[][] Map = Parent.MazeMap;
        ArrayList<AStNode> CardinalNeighbors = new ArrayList<>();

        //#region Automated
        for ( CellWall wall : CellWall.values() )
        {
            //if target it out of bounds
            if(CurrentPos.Y + wall.Direction.y < 0 || CurrentPos.X + wall.Direction.x < 0)
                continue;


            MazeCell TemporaryCell = Map[(int) CurrentPos.Y + wall.Direction.y][(int) CurrentPos.X +  wall.Direction.x];
            if(!TemporaryCell.IsActive())   //If target isn't active
                continue;

            //If we want only accessible nodes, check the wall and add, else we want any, so add
            if(IsAccessible && !CheckWall(wall))
                CardinalNeighbors.add(TemporaryCell);
            else
                CardinalNeighbors.add(TemporaryCell);
        }

        return CardinalNeighbors;
        //#endregion

        //#region Per cell layout - Deprecated
        /*if(CurrentPos.Y - 1 >= 0) {//directly above
            MazeCell tmpCellUp = Map[(int) CurrentPos.Y - 1][(int) CurrentPos.X];
            if(tmpCellUp.IsActive())
                CardinalNeighbors.add(tmpCellUp);
        }

        if(CurrentPos.Y + 1 >= 0) {//directly below
            MazeCell tmpCellBelow = Map[(int) CurrentPos.Y + 1][(int) CurrentPos.X];
            if(tmpCellBelow.IsActive())
                CardinalNeighbors.add(tmpCellBelow);
        }

        if(CurrentPos.X - 1 >= 0) {//left
            MazeCell tmpCellLeft = Map[(int) CurrentPos.Y][(int) CurrentPos.X - 1];
            if(tmpCellLeft.IsActive())
                CardinalNeighbors.add(tmpCellLeft);
        }

        if(CurrentPos.Y + 1 >= 0) {//right
            MazeCell tmpCellRight = Map[(int) CurrentPos.Y][(int) CurrentPos.X + 1];
            if(tmpCellRight.IsActive())
                CardinalNeighbors.add(tmpCellRight);
        }

        return CardinalNeighbors;*/
        //#endregion
    }


    float G = 0; //G is our Distance to this node
    float H = 0; //H is our distance to the end
    float F = G + H;

    @Override
    public float GetHeuristic() { return F; }


    @Override
    public void UpdateVal(float G) { this.G = G; }

    AStNode ParentNode;
    @Override
    public void UpdateParent(AStNode P) {
        ParentNode = P;
    }
    //endregion
}
