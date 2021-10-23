package Sturtup;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

public class ProjectSetup {
    static Helper _helper = new Helper();
    static Helper.ConnectionSettings _settings = _helper.getConnectionInfo().get();

    public static void EnsureConnectionExists(){
        try (Connection _connection = DriverManager.getConnection(_settings.getDb_Url(), _settings.getUserName(), _settings.getPassword()); Statement _statement = _connection.createStatement()) {
            /*ResultSet resultSet = _statement.executeQuery("SELECT count(*) from vehicle");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt(1));
            }*/

            if(!_connection.isClosed()) System.out.println("Connected to db!");
            _connection.close();
            if(_connection.isClosed()) System.out.println("Closed!");
        } catch ( SQLException e) {
            System.out.println("Connection failed ");
            e.printStackTrace();
        }
    }

    public static boolean SeedData(){
        try (Connection _connection = DriverManager.getConnection(_settings.getDb_Url(), _settings.getUserName(), _settings.getPassword());
             Statement _statement = _connection.createStatement())
        {
            List<BufferedReader> readers = Arrays.asList(
                    _helper.getFile("/seedQuerries/PersonalInfoSeed.sql").get(),
                    _helper.getFile("/seedQuerries/UserInfoSeed.sql").get(),
                    _helper.getFile("/seedQuerries/VehicleSeed.sql").get());
            ScriptRunner _script = new ScriptRunner(_connection);
            for(BufferedReader reader: readers)
                _script.runScript(reader);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return true;
    }
}
