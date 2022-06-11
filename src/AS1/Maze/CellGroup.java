package AS1.Maze;

import com.sun.jdi.InvalidTypeException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;

public class CellGroup
{
    ArrayList<MazeCell> Grouped = new ArrayList<>();
    public boolean InGroup() { return Grouped.size() > 0; }

    /**
     * @param NewFriend The node to add to the group
     */
    public void AddGroupCell(MazeCell NewFriend){
        Grouped.add(NewFriend);
        NewFriend.SetGroup(this);
    }

    /**
     * @param OldFriend The node to remove from the group
     */
    public void RemoveGroupCell(MazeCell OldFriend){
        Grouped.remove(OldFriend);
        OldFriend.SetGroup(null);
    }

    public  ArrayList<MazeCell> GetCells(){ return Grouped; }

    /**
     * Removes all cells from the group
     */
    public void ClearGroup(){
        for (MazeCell mcell: Grouped) {
            mcell.SetGroup(null);
        }
        Grouped.clear();
    }

    /**
     * @param v The MazeCell to test for
     * @return if the MazeCell is apart of this group
     */
    public boolean isInGroup(MazeCell v){return Grouped.contains(v);}

    BufferedImage GroupsImage = null;

    /**
     * Provided a complete file path, will evaluate and set the image if valid
     * @param FilePath The complete file path, extension included
     * @throws InvalidPathException
     * @throws InvalidTypeException
     * @throws IOException
     */
    public void SetImage(String FilePath) throws InvalidPathException, InvalidTypeException, IOException
    {
        File f = new File(FilePath);
        if(!f.exists())
            throw new InvalidPathException(FilePath, "Image at selected path %s does not exist.".formatted(FilePath));

        switch (GetExtension(f.getName()))
        {
            case ".png":
            case ".jpg":
            case ".jpeg":
                break;

            default:
                throw new InvalidTypeException("Invalid file type requested.");
        }

        try{
            GroupsImage = ImageIO.read(f);
        }
        catch (IOException ioE){
            throw  ioE;
        }
    }

    /**
     * @return Returns a {@link BufferedImage} of the groups image
     */
    public BufferedImage GetImage(){ return GroupsImage; }

    static String GetExtension(String CompleteName){
        String[] r = CompleteName.split("\\.");
        return ".".concat(r[r.length-1]).toLowerCase();
    }
}
