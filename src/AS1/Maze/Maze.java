package AS1.Maze;

public class Maze {
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
}
