package AS1.GUI;

import AS1.Maze.Maze;
import AS1.Maze.MazeCell;

import javax.swing.*;
import java.awt.*;

public class MainGUI {
    final JFrame MazeFrame;
    public final MazeRenderPanel MazeRPanel;

    public MainGUI(Maze Target){
        MazeFrame = new JFrame("Cells-to-Paint");
        MazeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Make the custom panel and set the background bc doing it in panel is a terrible idea
        MazeRenderPanel MazePanel = new MazeRenderPanel();
        MazePanel.setBackground(Color.BLACK);
        MazeRPanel = (MazeRenderPanel) MazeFrame.add(MazePanel);

        MazePanel.addMouseListener(MazePanel);
        MazePanel.addMouseMotionListener(MazePanel);
        MazeFrame.setVisible(true);

        Dimension d = new Dimension();
        d.height = (MazePanel.TotalCell * Target.Height) + + (MazePanel.WallWidth * 2);
        d.width = (MazePanel.TotalCell * Target.Width) + (MazePanel.WallWidth * 2);

        MazeRPanel.setPreferredSize(d);
        MazeFrame.pack();
        MazeRPanel.RenderGrid(Target);
    }
}
