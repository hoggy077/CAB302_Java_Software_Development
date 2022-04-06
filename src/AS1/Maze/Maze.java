package AS1.Maze;

public class Maze {
    public static MazeCell[][] MazeMap;
    static int Height;
    static int Width;

    public Maze(int Height, int Width){
        this.Height = Height;
        this.Width = Width;
        MazeMap = new MazeCell[Height][Width];
        PopulateMap();
    }

    static private void PopulateMap(){
        for (int X = 0; X < Width; X++){
            for (int Y = 0; Y < Height; Y++){
                MazeMap[Y][X] = new MazeCell(X,Y);
            }
        }
    }
}
