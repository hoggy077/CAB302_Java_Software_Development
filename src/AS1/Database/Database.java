package AS1.Database;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database {

    private static Connection instance = null;

    /**
     * Method to initialize database with properties found in db props and create an instance
     */
    public Database(){

        Properties props = new Properties();
        FileInputStream in;

        try {
            // load db.props in to FileInputStream
            in = new FileInputStream("./db.props");
            props.load(in);
            in.close();

            // assign db.props properties
            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jbdc.password");
            String schema = props.getProperty("jdbc.schema");

            // get DB connection and establish JDBC manager with db properties
            instance = DriverManager.getConnection(url + "/" + schema, username,
                    password);
        } catch (SQLException | FileNotFoundException sqle) {
            System.err.println(sqle);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /***
     * Method to use an instance for connection to database to be used throughout the program
     * @return instance variable
     * @throws SQLException - throw SQL errors
     */

    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            new Database();
        }
        return instance;
    }

    // create SQL query string to create database
    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "id INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                    + "authorName VARCHAR(255),"
                    + "mazeName VARCHAR(255) UNIQUE,"
                    + "dateCreated DATE,"
                    + "dateEdited DATE,"
                    + "mazeByteString MEDIUMTEXT,"
                    + "Difficulty VARCHAR(255),"
                    + "mazeHeight VARCHAR(255),"
                    + "mazeWidth VARCHAR(255))";

    /**
     * Method to create database with SQL query string
     * @throws SQLException - throw SQL errors and display in terminal
     */
    public void createTable() throws SQLException {
        // establish connection
        Connection connection = Database.getInstance();
        try {
            // connect to DB and create statement
            Statement st = connection.createStatement();
            // execute statement
            st.execute(CREATE_TABLE);
            // catch SQL errors
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
