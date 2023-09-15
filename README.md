# CAB302 | Java Software Development
**This is now archived**
The goal of this assignment was to utilize Java to create a desktop application capable of meeting several specific features, whilst maintaining appropriate unit testing.

The application utilizes a binary representation of walls per cell. Given the time constraints of the assignment at the time, each cell saved to the file maintained a 4bit binary string as opposed to a character representation. Relevant data was saved to a local SQL database which contained the dates for creation & edited, the mazes saved name, the height & width, the difficulty & the binary string of the entire maze for future.

Features
- Maze Generation
- Manually draw/edit a maze
- Save the maze as an image (This extends to saving the displayed solution)
- Solve the provided maze & display the solution
- Save & load the maze from a specified file
- Include & manage an SQL database containing relevant information
- Location to insert an image into the maze on a later date
  - The feature wasn't achieved natively, but was instead supplemented with a grouping feature explained below.

These features were achived utilizing a number of systems.
- GUI portion of the maze was achieved by creating a custom render element & manually handling the relation of the selected cell to the mouse position when completing an action
- Solving was achieved utilizing an __A-star Algorithm__
- Generation utilized a __Randomized Depth-first Search Algorithm (Aka. Recursive Backtracking Algorithm)__
- Saving & Loading was handled by a simplification of the maze walls into binary segments represented as *0001* where 1 represents an open wall
- Image insertion wasn't achieved natively
  - Alternatively we designed a grouping system compatible with the A-star algorithm that allowed a group of cells to act as a single cell, but also to define an area for an image to be inserted following the mazes conversion to png. This meet the outlined criteria, but also allowed for the images inserted to be an active part of the maze.

A side effect of the save systems binary representation resulted in the creation of 1 way walls within the program, however, this is never utilized in any scope.
