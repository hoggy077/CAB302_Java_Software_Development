package AS1.GUI;

import AS1.Maze.Maze;
import AS1.Maze.MazeCell;
import jdk.jshell.execution.JdiExecutionControlProvider;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

final public class MainGUI {
    JFrame ParentFrame;


    public MainGUI(Maze Target, int winwidth, int winheight){
        ParentFrame = new JFrame("Cells-to-Paint");
        ParentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Make the custom panel and set the background bc doing it in panel is a terrible idea
        MazeRenderPanel MazePanel = new MazeRenderPanel();
        MazePanel.setBackground(Color.BLACK);
        ParentFrame.add(MazePanel);

        MazePanel.addMouseListener(MazePanel);
        MazePanel.addMouseMotionListener(MazePanel);
        ParentFrame.setVisible(true);

        ParentFrame.setSize(winwidth, winheight);
        MazePanel.RenderGrid(Target);

    }



}
