package Database;

import AS1.Database.Database;
import AS1.Database.DatabaseCalls;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
public class TestDatabaseCalls {

    DatabaseCalls dbc = new DatabaseCalls();



    LocalDateTime datecreate = LocalDateTime.now();
    LocalDateTime datemod = LocalDateTime.now();


//Do not run it will update the main db
    @Test
    public void Insertdatabase(){
        dbc.Insert("name", "persons", datecreate, datemod);

    }
    @Test
    public void asd(){
        dbc.Insert("name", "persons", datecreate, datemod);
    }

}
