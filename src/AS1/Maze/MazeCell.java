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
    private BitSet CurrentCellWalls =new BitSet(5);
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


    //Self explanatory
    public CellPosition GetPosition(){ return CurrentPos;}


    //1 bit means there is a wall, aka true
    public boolean CheckWall(CellWall wall){return !CurrentCellWalls.get(wall.value);}

    //AddWall calls AddNeighborWall, AddWall considers its neighbor cell, AddNeighborWall only add its own wall
    public void AddWall(CellWall wall){
        if(!CurrentCellWalls.get(wall.value))
            CurrentCellWalls.set(wall.value,true);

        if(Inbounds((int) CurrentPos.X + wall.Direction.x, (int) CurrentPos.Y + wall.Direction.y))
            Parent.MazeMap[(int) CurrentPos.Y + wall.Direction.y][(int) CurrentPos.X + wall.Direction.x].AddNeighborWall(InverseWall(wall));
    }
    public void AddNeighborWall(CellWall wall){
        if(!CurrentCellWalls.get(wall.value))
            CurrentCellWalls.set(wall.value,true);
    }

    //RemoveWall calls RemoveNeighborWall, RemoveWall considers its neighbor cell, RemoveNeighborWall only removes its own wall
    public void RemoveWall(CellWall wall){
        if(CurrentCellWalls.get(wall.value))
            CurrentCellWalls.set(wall.value,false);

        if(Inbounds((int) CurrentPos.X + wall.Direction.x, (int) CurrentPos.Y + wall.Direction.y))
            Parent.MazeMap[(int) CurrentPos.Y + wall.Direction.y][(int) CurrentPos.X + wall.Direction.x].RemoveNeighborWall(InverseWall(wall));
    }
    public void RemoveNeighborWall(CellWall wall){
        if(CurrentCellWalls.get(wall.value))
            CurrentCellWalls.set(wall.value,false);
    }


    //as the name describes
    public CellWall InverseWall(CellWall wall){
        switch (wall){
            case DOWN : { return CellWall.UP; }
            case UP : { return CellWall.DOWN; }
            case LEFT : { return CellWall.RIGHT; }
            case RIGHT : { return CellWall.LEFT; }
        }
        return null;
    }

    boolean Inbounds(int x, int y){
        return x >= 0 && x < Parent.Width && y >= 0 && y < Parent.Height;
    }

    //this just converts a point direction to a wall, its used for the UI system to turn dragging from cell to cell into a wall to be used
    public static CellWall Point2Wall(Point Direction){
        for (CellWall w: CellWall.values()) {
            if(w.Direction.equals(Direction))
                return w;
        }
        return null;
    }


    //Utility Methods
    public void ResetCell(){ CurrentCellWalls.clear(); }
    public boolean IsActive(){return !CurrentCellWalls.get(0);}




    //region A⭐ integration
    @Override
    public AStNode[] NeighborsNodes(MazeCell[][] Map) {
        List<AStNode> CardinalNeighbors = new ArrayList<>();

        if(CurrentPos.Y - 1 >= 0) {//directly above
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

        return CardinalNeighbors.toArray(AStNode[]::new);
    }


    float G = 0; //G is our Distance to this node
    float H = 0; //H is our distance to the end
    float F = G + H;

    @Override
    public float GetHeuristic() { return F; }


    @Override
    public void UpdateVal(float G) { this.G = G; }
    //endregion
}
