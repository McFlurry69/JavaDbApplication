package Sturtup;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class Helper {

    private Connection _connection;

    public Optional<BufferedReader> getFile(String path){
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return Optional.of(bufferedReader);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static enum Tables {
        personalinfo,
        users,
        vehicle
    }

    public static class ConnectionSettings {
        private String db_Url;
        private String userName;
        private String password;

        public String getDb_Url() {
            return db_Url;
        }
        public String getUserName() {
            return userName;
        }
        public String getPassword() {
            return password;
        }
    }

    private Optional<ConnectionSettings> getConnectionInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Optional.of(objectMapper.readValue(getFile("/settings/application.json").get().lines().collect(Collectors.joining()), ConnectionSettings.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Connection get_connection() throws SQLException {
        Helper.ConnectionSettings _settings = getConnectionInfo().get();
        return _connection = DriverManager.getConnection(_settings.getDb_Url(), _settings.getUserName(), _settings.getPassword());
    }
}
