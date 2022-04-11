package AS1.GUI;

import AS1.Maze.Maze;
import AS1.Maze.MazeCell;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    JFrame ParentFrame;

    public MainGUI(Maze Target){
        ParentFrame = new JFrame("Cells-to-Paint");
        ParentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Make the custom panel and set the background bc doing it in panel is a terrible idea
        MazeRenderPanel MazePanel = new MazeRenderPanel();
        MazePanel.setBackground(Color.BLACK);
        ParentFrame.add(MazePanel);

        MazePanel.addMouseListener(MazePanel);
        MazePanel.addMouseMotionListener(MazePanel);


        ParentFrame.setSize(500,500);
        ParentFrame.setVisible(true);

        MazePanel.RenderGrid(Target);

    }
}
