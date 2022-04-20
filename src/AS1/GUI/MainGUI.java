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

    public void HomeGUI(){

        ParentFrame = new JFrame("Mazebuilder3000");
        ParentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane pane=new JTabbedPane();
        JPanel MfromScratch = new JPanel();

        MfromScratch.add(new JButton("Draw Maze!"));
        MfromScratch.add(new JLabel("Please Enter Height"));
        MfromScratch.add(new JTextField(2));
        MfromScratch.add(new JLabel("Please Enter Width"));
        MfromScratch.add(new JTextField(2));
        MfromScratch.add(new JButton("Check Maze Path"));
        MfromScratch.add(new JTextField("Maze Name"));
        MfromScratch.add(new JTextField("Author Name"));
        MfromScratch.add(new JRadioButton("Auto place logo"));
        MfromScratch.add(new JButton("Save Maze"));
        pane.add("Draw a Maze from Scratch",MfromScratch);
        ParentFrame.getContentPane().add(pane);
        ParentFrame.setPreferredSize(new Dimension(450,300));
        ParentFrame.pack();
        ParentFrame.setVisible(true);


    }

}
