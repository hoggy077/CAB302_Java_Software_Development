package AS1.GUI;

import AS1.Maze.Maze;
import AS1.Maze.MazeCell;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

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


    public String RequestMazeCellString(){
        return Reference.GetCellString();
    }
}
