package AS1.GUI;

import AS1.Maze.Maze;
import AS1.Maze.MazeCell;
import jdk.jshell.execution.JdiExecutionControlProvider;

import javax.swing.*;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    DummyClasses buttoncalls = new DummyClasses();
    public void HomeGUI() {



            ActionListener drawbutton = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //calls method from dummy classes to draw
                    buttoncalls.Draw(9, 9);

                }
            };
            ActionListener generatebutton = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //calls method from dummy classes to auto generate

                }
            };
            ActionListener checkbutton = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                }
            };
            ActionListener savebutton = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //calls method from dummy classes to save maze

                }
            };
            ActionListener generatepath = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //calls method from dummy classes to check path
                }
            };
            ActionListener placeimage = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //calls method from dummy classes to place image
                }
            };
            ActionListener autoplace = new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                   //calls method from dummy classes to auto place image
                }
            };



        ParentFrame = new JFrame("Mazebuilder3000");
        ParentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane pane=new JTabbedPane();


        //Gui for drawing a maze from scratch
        JPanel MfromScratch = new JPanel();

        JButton Draw = new JButton("Draw Maze!");
        Draw.addActionListener(drawbutton);
        MfromScratch.add(Draw);

        MfromScratch.add(new JLabel("Please Enter Height"));
        MfromScratch.add(new JTextField(2));
        MfromScratch.add(new JLabel("Please Enter Width"));
        MfromScratch.add(new JTextField(2));

        JButton Path = new JButton("Check Maze Path");
        Path.addActionListener(generatepath);
        MfromScratch.add(Path);

        MfromScratch.add(new JTextField("Maze Name"));
        MfromScratch.add(new JTextField("Author Name"));
        JRadioButton aLogo = new JRadioButton("Auto place logo");
        aLogo.addActionListener(autoplace);
        MfromScratch.add(aLogo);

        JButton placeLogo = new JButton("Place Image");
        placeLogo.addActionListener(placeimage);
        MfromScratch.add(placeLogo);

        JButton saveMaze = new JButton("Save Maze");
        saveMaze.addActionListener(savebutton);
        MfromScratch.add(saveMaze);

        //Gui for drawing a maze automatically
        JPanel MAutoGen = new JPanel();


        JButton autogenbut = new JButton("Generate a Maze!");
        autogenbut.addActionListener(generatebutton);
        MAutoGen.add(autogenbut);

        MAutoGen.add(new JLabel("Please Enter Height"));
        MAutoGen.add(new JTextField(2));
        MAutoGen.add(new JLabel("Please Enter Width"));
        MAutoGen.add(new JTextField(2));



        JButton AutoPath = new JButton("Check Maze Path");
        AutoPath.addActionListener(generatepath);
        MAutoGen.add(AutoPath);

        MAutoGen.add(new JTextField("Maze Name"));
        MAutoGen.add(new JTextField("Author Name"));

        JRadioButton agLogo = new JRadioButton("Auto place logo");
        agLogo.addActionListener(autoplace);
        MAutoGen.add(agLogo);

        JButton gplaceLogo = new JButton("Place Image");
        gplaceLogo.addActionListener(placeimage);
        MAutoGen.add(gplaceLogo);

        JButton gsaveMaze = new JButton("Save Maze");
        gsaveMaze.addActionListener(savebutton);
        MAutoGen.add(gsaveMaze);


        //Gui for maze browser

        //table names
        String[] columnNames = {"Maze Name", "Author","Time Created","Last Edited","Difficulty"};

        //temp set of objects for table
        Object[][] mazelist = {{"maze 1", "maze man","01/01/22","28/04/22", "Hard"}, {"maze 2", "maze woman","02/01/19","25/04/22", "Hard"}};
        JPanel MazeBrowser = new JPanel(new BorderLayout());
        JTable MazeTable = new JTable(mazelist, columnNames);
        MazeBrowser.add(MazeTable, BorderLayout.CENTER);
        MazeBrowser.add(MazeTable.getTableHeader(), BorderLayout.NORTH);
        JMenuBar mb = new JMenuBar();
        JMenu eo = new JMenu("Maze Export Options");
        JMenuItem exporttofile = new JMenuItem("Export Selected to File");
        JMenuItem exporttodb = new JMenuItem("Export Selected to DataBase");
        eo.add(exporttofile); eo.add(exporttodb);
        mb.add(eo);
        MazeBrowser.add(mb, BorderLayout.SOUTH);


        pane.add("Draw a Maze from Scratch",MfromScratch);
        pane.add("Generate a Maze", MAutoGen);
        pane.add("Maze List", MazeBrowser);

        ParentFrame.getContentPane().add(pane);
        ParentFrame.setPreferredSize(new Dimension(450,300));
        ParentFrame.pack();
        ParentFrame.setVisible(true);


    }

}
