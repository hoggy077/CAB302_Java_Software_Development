package AS1.Maze;
import AS1.AStar.AStNode;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class MazeCell implements AStNode {
    //5 bit long byte, where the first bit refers to if the cell is active or inactive
    //00000 = no walls and active
    //00001 = Down wall
    //00010 = Up wall
    //00100 = Right wall
    //01000 = Left wall
    //10000 = Inactive. At this point the other bits have no value, design accordingly
    private BitSet CurrentCellWalls =new BitSet(5);
    private CellPosition Pos;


    public enum CellWall{
        DOWN(4),
        UP(3),
        RIGHT(2),
        LEFT(1);

        public int value;
        private CellWall(int relativeBit)throws IndexOutOfBoundsException {
            if(relativeBit > 4 || relativeBit < 1)
                throw new IndexOutOfBoundsException(relativeBit);
            else{
                value = relativeBit;
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


    public MazeCell(float position_X, float position_Y){
        Pos = new CellPosition(position_X, position_Y);
    }


    //Self explanatory
    public CellPosition getPos(){ return Pos;}


    //Wall related calls
    public MazeCell AddCellWallReturn(CellWall wall) { CurrentCellWalls.set(wall.value,true); return this;}
    public void AddCellWall(CellWall wall) { CurrentCellWalls.set(wall.value,true); }

    public MazeCell RemoveCellWallReturn(CellWall wall) { CurrentCellWalls.set(wall.value,false); return this;}
    public void RemoveCellWall(CellWall wall) { CurrentCellWalls.set(wall.value,false); }

    public MazeCell SetActiveReturn(CellState state){CurrentCellWalls.set(state.value,state.state); return this;}
    public void SetActive(CellState state){CurrentCellWalls.set(state.value,state.state);}

    public boolean CheckWall(CellWall hasWall){return CurrentCellWalls.get(hasWall.value);}


    //Utility Methods
    public void ResetCell(){ CurrentCellWalls.clear(); }
    public boolean IsActive(){return CurrentCellWalls.get(0);}


    //Astar shit
    @Override
    public AStNode[] NeighborsNodes() {
        List<AStNode> CardinalNeighbors = new ArrayList<>();

        if(Pos.Y - 1 >= 0)//directly above
            CardinalNeighbors.add(Maze.MazeMap[(int)Pos.Y-1][(int)Pos.X]);

        if(Pos.Y + 1 >= 0)//directly below
            CardinalNeighbors.add(Maze.MazeMap[(int)Pos.Y+1][(int)Pos.X]);

        if(Pos.X - 1 >= 0)//left
            CardinalNeighbors.add(Maze.MazeMap[(int)Pos.Y][(int)Pos.X-1]);

        if(Pos.Y + 1 >= 0)//right
            CardinalNeighbors.add(Maze.MazeMap[(int)Pos.Y][(int)Pos.X+1]);

        return CardinalNeighbors.toArray(AStNode[]::new);
    }



    @Override
    public String toString(){
        String res = "";
        for(int i = 0; i < 5; i++){
            res += CurrentCellWalls.get(i)?1:0;
        }
        return res;
    }
}
