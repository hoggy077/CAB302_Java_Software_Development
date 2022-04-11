package AS1.Maze;

public class CellPosition {
    public float X = 0;
    public float Y = 0;

    CellPosition(){}
    CellPosition(float X, float Y){ this.X = X; this.Y = Y;}

    public CellPosition Difference(CellPosition other){
        return new CellPosition(X - other.X, Y - other.Y);
    }
}
