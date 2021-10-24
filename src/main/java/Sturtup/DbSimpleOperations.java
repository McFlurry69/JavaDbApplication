package Sturtup;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class DbSimpleOperations {
    private final DependencyInjectionImitator _IOC;
    private final Helper _helper;

    public DbSimpleOperations() {
        _IOC = new DependencyInjectionImitator();
        _helper = _IOC.get_helper();
    }

    public boolean EnsureConnectionExists(){
        try (Connection _connection = _helper.get_connection()) {
            if(!_connection.isClosed()) _IOC.getConsolePrint().accept("Connected to db!");
            _connection.close();
            if(_connection.isClosed()) _IOC.getConsolePrint().accept("Closed!");
            return true;
        } catch ( SQLException e) {
            _IOC.getConsolePrint().accept("Connection failed ");
            e.printStackTrace();
        }
        return false;
    }

    public boolean SeedData(){
        try (Connection _connection = _helper.get_connection())
        {
            List<BufferedReader> readers = Arrays.asList(
                    _helper.getFile("/seedQueries/PersonalInfoSeed.sql").get(),
                    _helper.getFile("/seedQueries/UserInfoSeed.sql").get(),
                    _helper.getFile("/seedQueries/VehicleSeed.sql").get());
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
