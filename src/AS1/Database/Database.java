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

    public Database() throws SQLException {

        Properties props = new Properties();
        FileInputStream in = null;

        try {
            in = new FileInputStream("./db.props");
            props.load(in);
            in.close();

            String url = props.getProperty("jdbc.url");
            String username = props.getProperty("jdbc.username");
            String password = props.getProperty("jbdc.password");
            String schema = props.getProperty("jdbc.schema");

            // get a connection
            instance = DriverManager.getConnection(url + "/" + schema, username,
                    password);
        } catch (SQLException sqle) {
            System.err.println(sqle);
        } catch (FileNotFoundException fnfe) {
            System.err.println(fnfe);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    /**
     * Provides global access to the singleton instance of the UrlSet.
     *
     * @return a handle to the singleton instance of the UrlSet.
     */
    public static Connection getInstance() throws SQLException {
        if (instance == null) {
            new Database();
        }
        return instance;
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE IF NOT EXISTS maze ("
                    + "id INTEGER PRIMARY KEY /*!40101 AUTO_INCREMENT */ NOT NULL UNIQUE,"
                    + "authorName VARCHAR(30),"
                    + "mazeName VARCHAR(20),"
                    + "dateCreated DATE,"
                    + "dateLastEdited DATE,"
                    + "image VARBINARY)";

    public void createTable() throws SQLException {
        Connection connection = Database.getInstance();
        try {
            Statement st = connection.createStatement();
            st.execute(CREATE_TABLE);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
