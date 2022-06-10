package AS1.GUI;

import AS1.AStar.AStNode;
import AS1.Maze.CellPosition;
import AS1.Maze.Maze;
import AS1.Maze.MazeCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowListener;

public class MainGUI {
    final JFrame MazeFrame;
    public final MazeRenderPanel MazeRPanel;
    Maze Reference;

    public MainGUI(Maze Target){
        Reference = Target;
        MazeFrame = new JFrame("Cells-to-Paint");
        MazeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        MazeFrame.setMinimumSize(new Dimension(500,500));

        MazeFrame.getContentPane().addComponentListener(new ComponentListener() {
            @Override
            public void componentResized(ComponentEvent e) {
                MazeRPanel.WindowUpdate(MazeFrame.getContentPane().getWidth(), MazeFrame.getContentPane().getHeight());
                MazeRPanel.RenderGrid();
            }

            @Override
            public void componentMoved(ComponentEvent e) {

            }

            @Override
            public void componentShown(ComponentEvent e) {

            }

            @Override
            public void componentHidden(ComponentEvent e) {

            }
        });


        MazeFrame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();


        MazeRenderPanel MazePanel = new MazeRenderPanel(MazeFrame.getMinimumSize(), Reference);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 1;

        MazePanel.setBackground(Color.BLACK);
        MazeFrame.add(MazePanel,gbc);
        MazeRPanel = (MazeRenderPanel)MazeFrame.getContentPane().getComponent(0);

        MazePanel.addMouseListener(MazePanel);
        MazePanel.addMouseMotionListener(MazePanel);
        MazeFrame.setVisible(true);

        //Dimension d = new Dimension();
        //d.height = (MazePanel.TotalCellHeight * Target.Height) + (MazePanel.WallWidth * 2);
        //d.width = (MazePanel.TotalCellWidth * Target.Width) + (MazePanel.WallWidth * 2);

        //MazeRPanel.setPreferredSize(d);
        MazeRPanel.setPreferredSize(new Dimension(MazeFrame.getContentPane().getWidth(),MazeFrame.getContentPane().getHeight()));
        MazeFrame.pack();
        MazeRPanel.RenderGrid();
    }

    public MainGUI(Maze Target, boolean AutoGroupCellsStart_End){
        this(Target);
        //These are for auto creating start and end image spaces
        if(!AutoGroupCellsStart_End)
            return;

        MazeRPanel.CreateGroup(new CellPosition(0,0), new CellPosition(1,1));
        MazeRPanel.CreateGroup(new CellPosition(Reference.Width-2,Reference.Height-2), new CellPosition(Reference.Width-1,Reference.Height-1));
    }

    public void AddOnCloseListener(WindowListener WindowListen){
        MazeFrame.addWindowListener(WindowListen);
    }

    public String RequestMazeCellString(){
        return Reference.GetCellString();
    }

    public AStNode RequestSolution(boolean RenderSolution)
    {
        AStNode EndOfPath = Reference.FindSolution();
        if(RenderSolution && EndOfPath != null)
            MazeRPanel.RenderSolution(Reference.MazeMap[0][0], EndOfPath);//0,0 is top left, solution target is the bottom right
        return EndOfPath;
    }

    public int RequestSolutionCount(){ return MazeRPanel.GetMinimumSolution(); }

    public void SetGroupImg(String path){ MazeRPanel.SetGroupImg(path); }

    public void RandomGen(){ MazeRPanel.AutoGenWrap(); }
}
