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
    public void AddGroupCell(MazeCell NewFriend){
        Grouped.add(NewFriend);
        NewFriend.SetGroup(this);
    }
    public void RemoveGroupCell(MazeCell OldFriend){
        Grouped.remove(OldFriend);
        OldFriend.SetGroup(null);
    }

    public  ArrayList<MazeCell> GetCells(){ return Grouped; }

    public void ClearGroup(){
        for (MazeCell mcell: Grouped) {
            mcell.SetGroup(null);
        }
        Grouped.clear();
    }

    public boolean isInGroup(MazeCell v){return Grouped.contains(v);}

    BufferedImage GroupsImage = null;
    public void SetImage(String FilePath) throws InvalidPathException, InvalidTypeException, IOException
    {
        File f = new File(FilePath);
        if(!f.exists())
            throw new InvalidPathException(FilePath, "Image at selected path %s does not exist.".formatted(FilePath));

        switch (GetExtension(f.getName()))
        {
            case ".png":
                break;

            case ".jpg":
                break;

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

    public BufferedImage GetImage(){ return GroupsImage; }

    static String GetExtension(String CompleteName){
        String[] r = CompleteName.split(".");
        return ".".concat(r[r.length-1]).toLowerCase();
    }
}
