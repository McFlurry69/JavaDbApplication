package utils;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.sql.*;
import java.util.Arrays;
import java.util.List;

public class DbSimpleOperations {
    private final DependencyInjectionImitator IOC = new DependencyInjectionImitator();
    private final ConnectionProvider helper = IOC.getHelper();
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Deprecated
    public boolean EnsureConnectionExists(){
        try (Connection _connection = helper.getConnection()) {
            if(!_connection.isClosed()) logger.info("Db connection successfully opened!");
            _connection.close();
            if(_connection.isClosed()) logger.info("Db connection closed!");
            return true;
        } catch ( SQLException e) {
            logger.info("Db connection failed! ", e);
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
            logger.info("Data was seeded successfully");
            return true;
        } catch (SQLException e) {
            logger.info("Data was not seeded ", e);
        }
        return false;
    }
}
