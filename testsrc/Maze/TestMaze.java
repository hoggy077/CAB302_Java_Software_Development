package Maze;
import AS1.Maze.Maze;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class TestMaze {


    //Normal test cases for Maze()
    @BeforeEach
    void Setup(){


    }

    //a maze of 10x10
    @Test
    void mazeTen(){
        Maze maze = new Maze(10, 10);
        assertEquals(10, maze.Height);
        assertEquals(10, maze.Width);

    }

    //a maze of 40x40
    @Test
    void mazeFourty(){
        Maze maze = new Maze(40, 40);
        assertEquals(40, maze.Height);
        assertEquals(40, maze.Width);
    }
    //a maze of 80x80
    @Test
    void mazeEighty(){
        Maze maze = new Maze(80, 80);
        assertEquals(80, maze.Height);
        assertEquals(80, maze.Width);
    }

    //Boundary test cases for Maze()

    //a maze of 100x100
    @Test
    void mazeOnehundred(){
        Maze maze = new Maze(100, 100);
        assertEquals(100, maze.Height);
        assertEquals(100, maze.Width);
    }
    //a maze of 1x1
    @Test
    void mazeOne(){
        Maze maze = new Maze(1, 1);
        assertEquals(1, maze.Height);
        assertEquals(1, maze.Width);
    }

    //Exception test cases for maze

    //a maze of 101x101
    @Test
    void mazeOnehundredandone(){
        Maze maze = new Maze(101, 101);
        assertEquals(0, maze.Height);
        assertEquals(0, maze.Width);
    }
    //a maze of 0x0
    @Test
    void mazeZero(){
        Maze maze = new Maze(0, 0);
        assertEquals(0, maze.Height);
        assertEquals(0, maze.Width);
    }
}
