package AS1.GUI;


import AS1.Database.Database;
import AS1.Database.DatabaseCalls;
import AS1.Maze.Maze;
import com.sun.jdi.InvalidTypeException;
import jdk.jshell.execution.Util;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;


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
        JButton Path = new JButton("Solve Maze");
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
        JButton placeLogo = new JButton("Place an Image");
        MfromScratch.add(placeLogo);

        //Save maze to png button for scratch tab
        JButton saveMaze = new JButton("Save Maze To File");
        MfromScratch.add(saveMaze);

        //button to save maze data to database
        JButton saveMazeDB = new JButton("Save Maze To Database");
        MfromScratch.add(saveMazeDB);

        //Gui for drawing a maze automatically, will do properly at a later point

        JButton autogenbut = new JButton("Generate a Maze!");
        MfromScratch.add(autogenbut);

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
                if (MazeGUI.RequestSolution(true) == null){
                    JOptionPane.showMessageDialog(null, "No solution found, please try again");

                }else{
                    MazeGUI.RequestSolutionCount();
                }

            }
        };
        ActionListener placeimage = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //calls method to get file path as a string
                
               String path = getFilepathString();
               System.out.println(path);
               MazeGUI.SetGroupImg(path);
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
                String difficulty = requestDifficulty();
                String bits = MazeGUI.RequestMazeCellString();

                final String INSERT_NAME = "INSERT INTO maze(authorName, mazeName, dateCreated, dateEdited, mazeByteString, Difficulty, mazeHeight, mazeWidth) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

                try {
                    DatabaseCalls.Insert(name, author, datecreate, datemod, bits, difficulty, hsp, wsp);
                }catch (IllegalArgumentException illegalArgumentException){
                    JOptionPane.showMessageDialog(null, "Please input the correct info into the relevant field");
                }
            }
        };
        ActionListener autoGen = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MazeGUI.RandomGen();

            }
        };

        autogenbut.addActionListener(autoGen);



        //endregion

        //region listener assignment
        Draw.addActionListener(drawbutton);
        HeightField.addActionListener(drawbutton);
        Path.addActionListener(generatepath);

        placeLogo.addActionListener(placeimage);
        saveMaze.addActionListener(savebutton);


        saveMazeDB.addActionListener(databaseSave);
        //endregion
        //endregion

        //Gui for maze browser

        //table names
        String[] columnNames = {"Author", "Maze Name", "Date Created", "Date Edited", "Difficulty", "Mazebits", "Maze Height", "Maze Width"};

        //temp set of objects for table



        JPanel MazeBrowser = new JPanel(new BorderLayout());






        JMenuBar mb = new JMenuBar();
        JMenu eo = new JMenu("Options");
        JMenuItem exporttofile = new JMenuItem("Import / Refresh Database");
        JMenuItem OpenMaze = new JMenuItem("Open Selected Maze");
        eo.add(OpenMaze);
        eo.add(exporttofile);
        mb.add(eo);
        MazeBrowser.add(mb, BorderLayout.SOUTH);
        JTable table = new JTable(new DefaultTableModel(columnNames, 0));
        table.getColumnModel().getColumn(5).setPreferredWidth(0);
        table.getColumnModel().getColumn(6).setPreferredWidth(0);
        table.getColumnModel().getColumn(7).setPreferredWidth(0);

        ActionListener importDb = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Connection connection = null;
                ResultSet rs = null;
                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.setRowCount(0);
                try {
                    connection = Database.getInstance();
                    PreparedStatement st = connection.prepareStatement("SELECT id, authorName, mazeName, dateCreated, dateEdited, mazeByteString, Difficulty, mazeHeight, mazeWidth FROM maze");
                    rs = st.executeQuery();



                    while(rs.next()){

                        //assigning the column info to strings

                        String authorName = rs.getString("authorName");
                        String mazeName = rs.getString("mazeName");
                        String dateCreated = rs.getString("dateCreated");
                        String dateEdited = rs.getString("dateEdited");
                        String Difficulty = rs.getString("Difficulty");
                        String mazeByteString = rs.getString("mazeByteString");
                        String mazeHeight = rs.getString("mazeHeight");
                        String mazeWidth = rs.getString("mazeWidth");

                        Object[] mazelists = {authorName, mazeName, dateCreated, dateEdited, Difficulty, mazeByteString, mazeHeight, mazeWidth};
                        model.addRow(mazelists);


                    }


                    //DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.addRow(new Object[]{mazeName, mazeAuthor});

                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }

            }
        };
        ActionListener openFromList = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get maze details
                int row = table.getSelectedRow();
                String bytes = table.getModel().getValueAt(row, 5).toString();
                String height = table.getModel().getValueAt(row, 6).toString();
                String width = table.getModel().getValueAt(row, 7).toString();

                int Iheight = Integer.parseInt(height);
                int Iwidth = Integer.parseInt(width);


                MazeGUI = new MainGUI(new Maze(Iheight, Iwidth, bytes));
                hsp = Iheight;
                wsp = Iwidth;

                //open maze up in program using details


            }
        };
        OpenMaze.addActionListener(openFromList);


        MazeBrowser.add(table, BorderLayout.CENTER);
        MazeBrowser.add(table.getTableHeader(), BorderLayout.NORTH);


        exporttofile.addActionListener(importDb);
        pane.add("Draw a Maze from Scratch",MfromScratch);
        pane.add("Maze List", MazeBrowser);

        GuiFrame.getContentPane().add(pane);
        GuiFrame.setPreferredSize(new Dimension(450,300));
        GuiFrame.setSize(450, 300);

        GuiFrame.setVisible(true);
    }

    public String requestDifficulty(){
        int solution = MazeGUI.RequestSolutionCount();
        int dimension = hsp * wsp;
        String difficulty = "Please get solution for difficulty";
        int asd = solution / dimension;


        if (asd >= 0.75){
            difficulty = "Hard";
        } else if (asd < .75 && asd >= .5 ) {
            difficulty = "Medium";
        } else if (asd < .5 && asd >=.35) {
            difficulty = "Easy";
        } else if (asd < .35) {
            difficulty = "Beginner";

        }
        return difficulty;

    }

    //opens up window to select a file, returns the path as a string
    public String getFilepathString(){
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                switch (GetExtension(f.getName()))
                {
                    case ".png":
                    case ".jpg":
                    case ".jpeg":
                        return true;

                    default:
                        return false;
                }
            }

            @Override
            public String getDescription() {
                return "All JPEG,JPG,PNG Files";
            }
        });
        int response = fileChooser.showOpenDialog(null); //selects file to open
        String Path = "";

        if (response == JFileChooser.APPROVE_OPTION){
            File file = new File(fileChooser.getSelectedFile().getAbsolutePath());

            Path = file.toString();

        }
        return Path;
    }


    static String GetExtension(String CompleteName){
        String[] r = CompleteName.split("\\.");
        return ".".concat(r[r.length-1]).toLowerCase();
    }
}
