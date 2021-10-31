package sturtup;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DbSimpleOperations {
    private final DependencyInjectionImitator IOC;
    private final Helper helper;

    public DbSimpleOperations() {
        IOC = new DependencyInjectionImitator();
        helper = IOC.getHelper();

    }
    static Logger log = LogManager.getLogger("JavaDbApplication");


    public boolean EnsureConnectionExists(){
        try (Connection _connection = helper.getConnection()) {
            if(!_connection.isClosed()) log.info("dasd");
            _connection.close();
            if(_connection.isClosed()) IOC.getConsolePrint().accept("Closed!");
            return true;
        } catch ( SQLException e) {
            IOC.getConsolePrint().accept("Connection failed ");
            e.printStackTrace();
        }
        return false;
    }

    public boolean SeedData(){
        try (Connection _connection = helper.getConnection())
        {
            List<BufferedReader> readers = Arrays.asList(
                    helper.getFile("/seedQueries/PersonalInfoSeed.sql").get(),
                    helper.getFile("/seedQueries/UserInfoSeed.sql").get(),
                    helper.getFile("/seedQueries/VehicleSeed.sql").get());
            ScriptRunner _script = new ScriptRunner(_connection);
            for(BufferedReader reader: readers)
                _script.runScript(reader);
            return true;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
