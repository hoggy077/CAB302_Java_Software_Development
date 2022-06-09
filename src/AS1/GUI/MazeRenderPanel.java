package AS1.GUI;

import AS1.AStar.AStNode;
import AS1.Maze.CellPosition;
import AS1.Maze.Maze;
import AS1.Maze.MazeCell;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MazeRenderPanel extends JPanel implements MouseListener, MouseMotionListener {

    //region Rendering exposure
    Insets insets;
    BufferedImage BImg = new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB);
    Graphics2D RenderingGraphics = BImg.createGraphics();

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(BImg,0,0,BImg.getWidth(),BImg.getHeight(),null);
        insets = getInsets();
    }
    //endregion


    //region Custom Functionality
    //These are automatically set on window creation, but just in case they are set
    //units are Px/value. Eg Px/Wall
    int WallWidth = 5; //All walls are twice the width when rendered, this is a 1 Wall per cell basis and not 1 Wall between 2 cells. Technically they're 2 walls
    int CellWidth = 45;
    int CellHeight = 45;

    public MazeRenderPanel(Maze shared){ sharedMaze = shared;  }

    public MazeRenderPanel(Dimension ParentSize, Maze shared){
        sharedMaze = shared;
        WindowUpdate(ParentSize.width, ParentSize.height);
    }

    public void WindowUpdate(int XSize, int YSize){
        XSize -= WallWidth * 2;
        YSize -= WallWidth * 2;
        CellWidth = (XSize / sharedMaze.Width) - WallWidth * 2;
        CellHeight = (YSize / sharedMaze.Height) - WallWidth * 2;

        if(sharedMaze.Height >= 50 || sharedMaze.Width >= 50)
            WallWidth = 2;


        TotalCellWidth = CellWidth + WallWidth * 2;
        TotalCellHeight = CellHeight + WallWidth * 2;

        RenderGrid();
    }



    Maze sharedMaze;

    boolean HasSolution = false;
    AStNode StartN, EndPath;
    int CurrentMinimumCells = 0;
    public int GetMinimumSolution() {return  CurrentMinimumCells;}
    public void RenderSolution(AStNode StartNode, AStNode Path){
        HasSolution = true;
        StartN = StartNode;
        EndPath = Path;
        CurrentMinimumCells = 0;

        AStNode Current = Path;
        int Cell_xs, Cell_ys;

        RenderingGraphics.setColor(new Color(138, 201, 38));
        while (Current.comparePosTo(StartNode) != 1){
            CurrentMinimumCells++;
            CellPosition pos = Current.GetPosition();
            Cell_xs = (int) (((WallWidth *2) * (pos.X+1)) + (CellWidth * pos.X));
            Cell_ys = (int) (((WallWidth *2) * (pos.Y+1)) + (CellHeight * pos.Y));

            RenderingGraphics.fillRect(Cell_xs, Cell_ys, CellWidth, CellHeight);

            Current = Current.GetParent();
        }

        CellPosition pos = Current.GetPosition();
        Cell_xs = (int) (((WallWidth *2) * (pos.X+1)) + (CellWidth * pos.X));
        Cell_ys = (int) (((WallWidth *2) * (pos.Y+1)) + (CellHeight * pos.Y));
        CurrentMinimumCells++; //Comment this to not include the start in the cell count
        RenderingGraphics.fillRect(Cell_xs, Cell_ys, CellWidth, CellHeight);

        RenderingGraphics.setColor(Color.WHITE);
        repaint();
    }

    //literally just renders the maze
    public void RenderGrid(){
        int NewBufferH = TotalCellHeight * sharedMaze.Height, NewBufferW = TotalCellWidth * sharedMaze.Width;
        BufferedImage BImg2 = new BufferedImage(NewBufferW,NewBufferH,BufferedImage.TYPE_INT_ARGB);

        BImg = BImg2;
        RenderingGraphics = BImg.createGraphics();


        for(int y = 0; y < sharedMaze.Height; y++){
            for(int x = 0; x < sharedMaze.Width; x++){
                int Cell_xs, Cell_ys;
                Cell_xs = ((WallWidth *2) * (x+1)) + (CellWidth * x);
                Cell_ys = ((WallWidth *2) * (y+1)) + (CellHeight * y);

                RenderingGraphics.fillRect(Cell_xs, Cell_ys, CellWidth, CellHeight);//Render the cell center

                for (MazeCell.CellWall wall : MazeCell.CellWall.values()){
                    if (!sharedMaze.MazeMap[y][x].CheckWall(wall)) //false
                        UpdateCellWall(sharedMaze.MazeMap[y][x], wall);
                }
            }
        }

        if(HasSolution)
            RenderSolution(StartN, EndPath);

        repaint();
    }

    //literally just adds the wall render in
    public void UpdateCellWall(MazeCell Cell, MazeCell.CellWall Direction){
        int Cell_xs, Cell_ys, x, y;
        x = (int) Cell.GetPosition().X;
        y = (int) Cell.GetPosition().Y;
        Cell_xs = ((WallWidth *2) * (x+1)) + (CellWidth * x);
        Cell_ys = ((WallWidth *2) * (y+1)) + (CellHeight * y);

        int WallW = 0, WallH = 0;
        if(Direction == MazeCell.CellWall.LEFT) {//Left
            Cell_xs -= WallWidth;
            WallW = WallWidth * 2;
            WallH = CellHeight;
        }
        if(Direction == MazeCell.CellWall.RIGHT) {//Right
            Cell_xs += CellWidth;
            WallW = WallWidth * 2;
            WallH = CellHeight;
        }
        if(Direction == MazeCell.CellWall.UP) {//Up
            Cell_ys -= WallWidth;
            WallH = WallWidth * 2;
            WallW = CellWidth;
        }
        if(Direction == MazeCell.CellWall.DOWN) {//Down
            Cell_ys += CellHeight;
            WallH = WallWidth * 2;
            WallW = CellWidth;
        }

        RenderingGraphics.setColor(Color.WHITE);
        RenderingGraphics.fillRect(Cell_xs, Cell_ys, WallW, WallH);
        repaint();
    }

    //literally just saves the buffer to an image file
    public void SaveBuffer2File(String CompletePath){
        HasSolution = false;
        RenderGrid();

        try {
            File output = new File(CompletePath);
            ImageIO.write(BImg, "png", output);
        } catch (IOException e) {
            e.printStackTrace();//--we need to replace this with an exception throw
        }
    }
    //endregion




    //region Mouse Listener overrides
    boolean isDragging = false;
    Point LastCell = new Point();
    int CurrentBtn = 0;

    int TotalCellWidth = CellWidth + WallWidth * 2;
    int TotalCellHeight = CellHeight + WallWidth * 2;

    @Override
    public void mouseClicked(MouseEvent e) {
        //region Debugging purposes
        System.out.println(String.format("Button:%s\nPosition: %sx %sy", e.getButton(), Math.floor(e.getX()/TotalCellWidth), Math.floor(e.getY()/TotalCellHeight)));
        //endregion
    }

    @Override
    public void mousePressed(MouseEvent e) {

        CurrentBtn = e.getButton();
        switch (CurrentBtn)
        {
            case 1:
                LastCell = new Point((int) Math.floor(e.getX()/TotalCellWidth),(int) Math.floor(e.getY()/TotalCellHeight));
                isDragging = true;
                break;

            case 2:
                System.out.println("mhm");
                AStNode solution = sharedMaze.FindSolution();
                if(solution == null){
                    System.out.println("Solution not found");
                    break;
                }
                RenderSolution(sharedMaze.MazeMap[0][0], solution);
                break;

            case 3:
                LastCell = new Point((int) Math.floor(e.getX()/TotalCellWidth),(int) Math.floor(e.getY()/TotalCellHeight));
                isDragging = true;
                break;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(CurrentBtn == 1 || CurrentBtn == 3)//Left mouse button & Right mouse button
            isDragging = false;
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }



    @Override
    public void mouseDragged(MouseEvent e) {
        if(isDragging) {
            int Currentx = (int) Math.floor(e.getX() / TotalCellWidth);
            int Currenty = (int) Math.floor(e.getY() / TotalCellHeight);

            if (!LastCell.equals(new Point(Currentx, Currenty))) {
                if (Currentx < sharedMaze.Width && Currenty < sharedMaze.Height) {
                    //System.out.println(String.format("Button:%s\nPosition: %sx %sy", e.getButton(), Currentx - LastCell.x, Currenty - LastCell.y));
                    Point Dir = new Point(Currentx - LastCell.x, Currenty - LastCell.y);

                    boolean IsValid = sharedMaze.Inbounds(Currentx, Currenty);
                    if (!IsValid)
                        return;

                    MazeCell.CellWall tmpcw = MazeCell.Point2Wall(Dir);
                    if(tmpcw == null)
                        return;

                    switch (CurrentBtn){
                        case 1:
                            HasSolution = false;
                            sharedMaze.MazeMap[LastCell.y][LastCell.x].RemoveWall(tmpcw);
                            break;

                        case 3:
                            HasSolution = false;
                            sharedMaze.MazeMap[LastCell.y][LastCell.x].AddWall(tmpcw);
                            break;
                    }

                    LastCell = new Point(Currentx, Currenty);

                    RenderGrid();
                } else {
                    isDragging = false;
                    return;
                }
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
    //endregion
}
