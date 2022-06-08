package AS1.GUI;


import AS1.Database.Database;
import AS1.Database.DatabaseCalls;
import AS1.Maze.Maze;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class Menu {

    JFrame GuiFrame;

    int hsp; //--evaluate these, i altered the int.parse calls, but do you really need to store them?
    int wsp;

    MainGUI MazeGUI = null; //--Dont change this. MazeGUI shouldn't exist if the maze panel hasn't been made yet



    public void HomeGUI() {

        //region Creation of GuiFrame
        GuiFrame = new JFrame("Mazebuilder3000");
        GuiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane pane=new JTabbedPane();
        //endregion

        //region control creation
        //Gui for drawing a maze from scratch
        JPanel MfromScratch = new JPanel();

        //Draw button on for scratch tab
        JButton Draw = new JButton("Draw Maze!");
        MfromScratch.add(Draw);

        //Maze height field for scratch tab
        MfromScratch.add(new JLabel("Please Enter Height"));
        JTextField HeightField = new JTextField(2);
        MfromScratch.add(HeightField);

        //Maze width field for scratch tab
        MfromScratch.add(new JLabel("Please Enter Width"));
        JTextField WidthField = new JTextField(2);
        MfromScratch.add(WidthField);

        //Maze check path button for scratch tab
        JButton Path = new JButton("Check Maze Path");
        MfromScratch.add(Path);


        //Name maze field for scratch tab
        JTextField mazeName = new JTextField("Maze Name", 6);
        MfromScratch.add(mazeName);


        //Author name field for scratch tab for db saving
        JTextField mazeAuthor = new JTextField("Author Name");
        MfromScratch.add(mazeAuthor);


        //Auto place logo on maze start radio button for scratch tab
        JRadioButton aLogo = new JRadioButton("Auto place logo");
        MfromScratch.add(aLogo);

        //Place logo button for scratch tab
        JButton placeLogo = new JButton("Place Image");
        MfromScratch.add(placeLogo);

        //Save maze to png button for scratch tab
        JButton saveMaze = new JButton("Save Maze To File");
        MfromScratch.add(saveMaze);

        //button to save maze data to database
        JButton saveMazeDB = new JButton("Save Maze To Database");
        MfromScratch.add(saveMazeDB);

        //Gui for drawing a maze automatically, will do properly at a later point
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

        JButton gsaveMaze = new JButton("Save Maze To File");
        MAutoGen.add(gsaveMaze);
        //endregion

        //region Listener Event stuff
        //region listener creation
        ActionListener drawbutton = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //calls method from dummy classes to draw

                //--wrap stuff like this. Those input fields allow text, text wont parse. you need to catch that exception
                try{
                    hsp = Integer.parseInt(HeightField.getText());
                    wsp = Integer.parseInt(WidthField.getText());

                    MazeGUI = new MainGUI(new Maze(hsp,wsp));//--changed MainGUI so it auto sizes the render panel to fit the cells
                }
                catch (NumberFormatException parseException){
                    JOptionPane.showMessageDialog(null, "Please enter both width and height in numbers");

                    //--do something about the exception, or ignore them.
                }
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

                if(MazeGUI == null) //--with the changes I made, this needs to be checked to avoid a "you pressed the button but there was no maze yet!"
                    return;

                String SaveName = (mazeName.getText() + ".png");
                MazeGUI.MazeRPanel.SaveBuffer2File(SaveName);//--Avoid making any "new" panels cause the MainGUI now provides the panel and frame as an accessible final variable
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
        ActionListener databaseSave = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = mazeName.getText();
                String author = mazeAuthor.getText();
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDateTime datecreate = LocalDateTime.now();
                LocalDateTime datemod = LocalDateTime.now();

                final String INSERT_NAME = "INSERT INTO maze(authorName, mazeName, dateCreated, dateEdited) VALUES (?, ?, ?, ?);";

                try {
                    DatabaseCalls.Insert(name, author, datecreate, datemod);
                }catch (IllegalArgumentException illegalArgumentException){
                    JOptionPane.showMessageDialog(null, "Please input the correct info into the relevant field");
                }
            }
        };

        //endregion

        //region listener assignment
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
        saveMazeDB.addActionListener(databaseSave);
        //endregion
        //endregion

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

        GuiFrame.getContentPane().add(pane);
        GuiFrame.setPreferredSize(new Dimension(450,300));
        GuiFrame.setSize(450, 300);

        GuiFrame.setVisible(true);
    }
}
