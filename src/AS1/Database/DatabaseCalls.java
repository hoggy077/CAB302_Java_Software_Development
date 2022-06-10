package AS1.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class DatabaseCalls {

    public static void Insert(String name, String author, TemporalAccessor datecreate, TemporalAccessor datemod, String mazeByteString, String Difficulty){
        final String INSERT_NAME = "INSERT INTO maze(authorName, mazeName, dateCreated, dateEdited, mazeByteString, Difficulty) VALUES (?, ?, ?, ?, ?, ?);";
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");


        try {
            Connection connection = Database.getInstance();
            PreparedStatement st = connection.prepareStatement("INSERT INTO maze(authorName, mazeName, dateCreated, dateEdited, mazeByteString, Difficulty) VALUES (?, ?, ?, ?, ?, ?)");
            st.clearParameters();
            st.setString(1, name);
            st.setString(2, author);
            st.setString(3, dtf.format(datecreate));
            st.setString(4, dtf.format(datemod));
            st.setString(5, mazeByteString);
            st.setString(6, Difficulty);
            st.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}