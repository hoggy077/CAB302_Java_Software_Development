package AS1.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DatabaseCalls {

    /**
     * Method to maze data in to database
     * @param name - Name of the maze
     * @param author - Author of maze
     * @param datecreate - maze date created
     * @param datemod - maze last modified
     * @param mazeByteString - maze byte string for retrieval
     * @param Difficulty - maze difficulty (Beginner, Easy, Medium, Hard)
     * @param height - height of maze
     * @param width - width of maze
     */
    public static void Insert(String name, String author, TemporalAccessor datecreate, TemporalAccessor datemod, String mazeByteString, String Difficulty, int height, int width){

        // format date correctly
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        try {
            // Establish connection
            Connection connection = Database.getInstance();
            // Create prepared statement for SQL query
            PreparedStatement st = connection.prepareStatement("INSERT OR REPLACE INTO maze(authorName, mazeName, dateCreated, " +
                                                                    "dateEdited, mazeByteString, Difficulty, mazeHeight, mazeWidth) " +
                                                                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            // first clear any parameters
            st.clearParameters();
            // set each method parameter to insert VALUES in to database
            st.setString(1, author);
            st.setString(2, name);
            st.setString(3, dtf.format(datecreate));
            st.setString(4, dtf.format(datemod));
            st.setString(5, mazeByteString);
            st.setString(6, Difficulty);
            st.setInt(7, height);
            st.setInt(8, width);
            // execute query
            st.executeUpdate();
            // catch SQL errors
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
