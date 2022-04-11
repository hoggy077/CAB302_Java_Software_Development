package AS1.Maze;

public class Maze {
    public MazeCell[][] MazeMap;
    public final int Height;
    public final int Width;

    public Maze(int Height, int Width){
        if(Height > 100 || Width > 100)
            System.out.println("Bruh, you exceeded 100"); //--This needs tp be replaced with an exception throw

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
