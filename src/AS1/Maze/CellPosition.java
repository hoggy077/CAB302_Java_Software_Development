package AS1.Maze;

public class CellPosition {
    public float X = 0;
    public float Y = 0;

    CellPosition(){}
    public CellPosition(float X, float Y){ this.X = X; this.Y = Y;}

    /**
     * @param other The other node to subtract with
     * @return Returns the difference from the current node to the other node
     */
    public CellPosition Difference(CellPosition other){
        return new CellPosition(X - other.X, Y - other.Y);
    }
}
