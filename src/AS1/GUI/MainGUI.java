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


        ParentFrame.setSize(1000,1000);
        ParentFrame.setVisible(true);

        MazePanel.RenderGrid(Target);

    }

    DummyClasses buttoncalls = new DummyClasses();
    public void HomeGUI() {



        ParentFrame = new JFrame("Mazebuilder3000");
        ParentFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane pane=new JTabbedPane();


        //Gui for drawing a maze from scratch
        JPanel MfromScratch = new JPanel();

        JButton Draw = new JButton("Draw Maze!");

        MfromScratch.add(Draw);


        MfromScratch.add(new JLabel("Please Enter Height"));
        JTextField HeightField = new JTextField(2);
        MfromScratch.add(HeightField);



        MfromScratch.add(new JLabel("Please Enter Width"));
        JTextField WidthField = new JTextField(2);
        MfromScratch.add(WidthField);

        JButton Path = new JButton("Check Maze Path");
        MfromScratch.add(Path);

        MfromScratch.add(new JTextField("Maze Name"));
        MfromScratch.add(new JTextField("Author Name"));
        JRadioButton aLogo = new JRadioButton("Auto place logo");

        MfromScratch.add(aLogo);

        JButton placeLogo = new JButton("Place Image");
        MfromScratch.add(placeLogo);

        JButton saveMaze = new JButton("Save Maze");
        MfromScratch.add(saveMaze);

        //Gui for drawing a maze automatically
        JPanel MAutoGen = new JPanel();


        JButton autogenbut = new JButton("Generate a Maze!");
        MAutoGen.add(autogenbut);

        MAutoGen.add(new JLabel("Please Enter Height"));
        MAutoGen.add(new JTextField(2));
        MAutoGen.add(new JLabel("Please Enter Width"));
        MAutoGen.add(new JTextField(2));



        JButton AutoPath = new JButton("Check Maze Path");
        MAutoGen.add(AutoPath);

        MAutoGen.add(new JTextField("Maze Name"));
        MAutoGen.add(new JTextField("Author Name"));

        JRadioButton agLogo = new JRadioButton("Auto place logo");
        MAutoGen.add(agLogo);

        JButton gplaceLogo = new JButton("Place Image");
        MAutoGen.add(gplaceLogo);

        JButton gsaveMaze = new JButton("Save Maze");
        MAutoGen.add(gsaveMaze);


        ActionListener drawbutton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //calls method from dummy classes to draw
                String hs = HeightField.getText();
                int hsp = Integer.parseInt(hs);

                String ws = WidthField.getText();
                int wsp = Integer.parseInt(ws);
                buttoncalls.Draw(hsp, wsp);


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

        Draw.addActionListener(drawbutton);
        HeightField.addActionListener(drawbutton);
        Path.addActionListener(generatepath);
        aLogo.addActionListener(autoplace);
        placeLogo.addActionListener(placeimage);
        saveMaze.addActionListener(savebutton);
        autogenbut.addActionListener(generatebutton);
        AutoPath.addActionListener(generatepath);
        agLogo.addActionListener(autoplace);
        gplaceLogo.addActionListener(placeimage);
        gplaceLogo.addActionListener(placeimage);
        gsaveMaze.addActionListener(savebutton);


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
