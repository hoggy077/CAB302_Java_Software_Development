package AS1.Maze;

import AS1.AStar.AStNode;

import java.awt.*;
import java.security.InvalidParameterException;
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
    public MazeCell(float position_X, float position_Y, Maze Parent, String WallBytes) throws InvalidParameterException {
        CurrentPos = new CellPosition(position_X, position_Y);
        this.Parent = Parent;

        for (int index = 0; index <= CellWall.values().length; index++){
            switch (WallBytes.charAt(index)){
                case '0':
                    CurrentCellWalls.set(index,false);
                    break;
                case '1':
                    CurrentCellWalls.set(index,true);
                    break;
                default:
                    throw new InvalidParameterException("Invalid Cell wall bit found in cell byte %s %s".formatted(position_X, position_Y));
            }
        }
    }
    //endregion

    @Override
    public String toString(){
        StringBuilder stb = new StringBuilder();
        for (int i = 0; i <= 4; i++)
            stb.append(CurrentCellWalls.get(i) ? "1" : "0");
        return  stb.toString();
    }

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
        if(Parent.Inbounds((int) CurrentPos.X + wall.Direction.x, (int) CurrentPos.Y + wall.Direction.y)) {
            if(!CurrentCellWalls.get(wall.value))
                CurrentCellWalls.set(wall.value,true);

            Parent.MazeMap[(int) CurrentPos.Y + wall.Direction.y][(int) CurrentPos.X + wall.Direction.x].AddNeighborWall(InverseWall(wall));
        }
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
        if(Parent.Inbounds((int) CurrentPos.X + wall.Direction.x, (int) CurrentPos.Y + wall.Direction.y)) {
            if(CurrentCellWalls.get(wall.value))
                CurrentCellWalls.set(wall.value,false);

            Parent.MazeMap[(int) CurrentPos.Y + wall.Direction.y][(int) CurrentPos.X + wall.Direction.x].RemoveNeighborWall(InverseWall(wall));
        }
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


    //region A⭐ integration
    /**
     * Provides a CellPosition class referring to the internal stored reference
     * @return
     */
    @Override
    public CellPosition GetPosition(){ return CurrentPos;}

    @Override
    public int compareTo(AStNode comp) {
        if(F() == comp.GetF())
            if (H < comp.GetH())
                return 1;
            else
                return 0;

        if(F() < comp.GetF())
            return -1;

        return 1;
    }

    @Override
    public int comparePosTo(AStNode n) {
        if(n.GetPosition() == CurrentPos)
            return 1;
        return 0;
    }

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

            if(CurrentPos.Y + wall.Direction.y >= Parent.Height || CurrentPos.X + wall.Direction.x >= Parent.Width)
                continue;

            MazeCell TemporaryCell = Map[(int) CurrentPos.Y + wall.Direction.y][(int) CurrentPos.X +  wall.Direction.x];
            if(!TemporaryCell.IsActive())   //If target isn't active
                continue;

            //If we want only accessible nodes, check the wall and add, else we want any, so add
            if(IsAccessible){
                if(CheckWall(wall))
                    continue;
                else
                    CardinalNeighbors.add(TemporaryCell);
            }
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

    @Override
    public float GetF() { return F(); }
    @Override
    public float GetG() { return G; }
    @Override
    public float GetH() { return H; }

    //G = 1 because the cost of moving from 1 cell to another should be constant cost,
    float G = 0; //G is our Distance to this node/our cost to move cells
    float H = 0; //H is our distance to the end, this is basically Cardinal Manhattan Distance
    float F(){ return  G+H; }

    @Override
    public void UpdateHeuristic(CellPosition EndGoal, float G)
    {
        this.G = G;
        CellPosition r = CurrentPos.Difference(EndGoal);
        H = Math.abs(r.X) + Math.abs(r.Y);
    }

    @Override
    public boolean GetValid() { return false; }

    AStNode ParentNode = null;
    @Override
    public void UpdateParent(AStNode P) {
        ParentNode = P;
    }

    @Override
    public boolean HasParent(){return ParentNode != null; }

    @Override
    public AStNode GetParent(){ return ParentNode; }
    //endregion
}
