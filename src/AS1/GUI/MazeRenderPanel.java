package AS1.GUI;

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
    int WallWidth = 10; //Px / wall
    int CellSize = 45; //Px / cell, both width and height, bc im lazy

    Maze sharedMaze;

    //literally just renders the maze
    public void RenderGrid(Maze Target){//this needs to be replaced with the maze and not individual values
        sharedMaze = Target;

        int NewBufferH = (WallWidth * 2) * Target.Height + CellSize * Target.Height, NewBufferW = (WallWidth * 2) * Target.Width + CellSize * Target.Width;
        BufferedImage BImg2 = new BufferedImage(NewBufferW,NewBufferH,BufferedImage.TYPE_INT_ARGB);
        //BImg2.copyData(BImg.getRaster());
        BImg = BImg2;
        RenderingGraphics = BImg.createGraphics();

        for(int y = 0; y < Target.Height; y++){
            for(int x = 0; x < Target.Width; x++){
                int Cell_xs, Cell_ys;
                Cell_xs = ((WallWidth *2) * (x+1)) + (CellSize * x);
                Cell_ys = ((WallWidth *2) * (y+1)) + (CellSize * y);

                RenderingGraphics.fillRect(Cell_xs, Cell_ys, CellSize, CellSize);//Render the cell center

                for (MazeCell.CellWall wall : MazeCell.CellWall.values()){
                    if (Target.MazeMap[y][x].CheckWall(wall))
                        UpdateCellWall(Target.MazeMap[y][x], wall);
                }

                repaint();
            }
        }
    }

    //literally just adds the wall render in
    public void UpdateCellWall(MazeCell Cell, MazeCell.CellWall Direction){
        int Cell_xs, Cell_ys, x, y;
        x = (int) Cell.GetPosition().X;
        y = (int) Cell.GetPosition().Y;
        Cell_xs = ((WallWidth *2) * (x+1)) + (CellSize * x);
        Cell_ys = ((WallWidth *2) * (y+1)) + (CellSize * y);

        int WallW = 0, WallH = 0;
        if(Direction == MazeCell.CellWall.LEFT) {//Left
            Cell_xs -= WallWidth;
            WallW = WallWidth * 2;
            WallH = CellSize;
        }
        if(Direction == MazeCell.CellWall.RIGHT) {//Right
            Cell_xs += CellSize;
            WallW = WallWidth * 2;
            WallH = CellSize;
        }
        if(Direction == MazeCell.CellWall.UP) {//Up
            Cell_ys -= WallWidth;
            WallH = WallWidth * 2;
            WallW = CellSize;
        }
        if(Direction == MazeCell.CellWall.DOWN) {//Down
            Cell_ys += CellSize;
            WallH = WallWidth * 2;
            WallW = CellSize;
        }

        RenderingGraphics.setColor(Color.WHITE);
        RenderingGraphics.fillRect(Cell_xs, Cell_ys, WallW, WallH);
        repaint();
    }

    //literally just saves the buffer to an image file
    public void SaveBuffer2File(){
        try {
            File output = new File(".\\maze.png"); //--
            ImageIO.write(BImg, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //endregion




    //region Mouse Listener overrides
    boolean isDragging = false;
    Point LastCell = new Point();

    int TotalCell = CellSize + WallWidth * 2;




    @Override
    public void mouseClicked(MouseEvent e) {
        //region Debugging purposes
        System.out.println(String.format("Button:%s\nPosition: %sx %sy", e.getButton(), Math.floor(e.getX()/TotalCell), Math.floor(e.getY()/TotalCell)));
        //endregion
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if(e.getButton() == 1) {//Left mouse button
            LastCell = new Point((int) Math.floor(e.getX()/TotalCell),(int) Math.floor(e.getY()/TotalCell));
            isDragging = true;
        }
        else if(e.getButton() == 2)
            SaveBuffer2File();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if(e.getButton() == 1)//Left mouse button
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
            int Currentx = (int) Math.floor(e.getX() / TotalCell);
            int Currenty = (int) Math.floor(e.getY() / TotalCell);

            if (!LastCell.equals(new Point(Currentx, Currenty))) {
                if (Currentx < sharedMaze.Width && Currenty < sharedMaze.Height) {
                    //System.out.println(String.format("Button:%s\nPosition: %sx %sy", e.getButton(), Currentx - LastCell.x, Currenty - LastCell.y));
                    Point Dir = new Point(Currentx - LastCell.x, Currenty - LastCell.y);

                    MazeCell.CellWall tmpcw = MazeCell.Point2Wall(Dir);
                    if(tmpcw == null)
                        return;

                    sharedMaze.MazeMap[LastCell.y][LastCell.x].RemoveWall(tmpcw);

                    LastCell = new Point(Currentx, Currenty);

                    RenderGrid(sharedMaze);
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
