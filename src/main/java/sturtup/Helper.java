package sturtup;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.LoggerContext;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class Helper {

    public class WrongFileContentException extends Exception{
        public WrongFileContentException(String errorMessage){
            super(errorMessage);
        }
    }

    public Optional<BufferedReader> getFile(String path){
        try {
            InputStream inputStream = this.getClass().getResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return Optional.ofNullable(bufferedReader);
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

    private Optional<ConnectionSettings> getConnectionInfo(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return Optional.of(objectMapper.readValue(getFile(path).get().lines().collect(Collectors.joining()), ConnectionSettings.class));

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getLog4JConfigFile() {
        LoggerContext context = (org.apache.logging.log4j.core.LoggerContext) LogManager.getContext(false);
        this.getClass().getResourceAsStream("/settings/log4j.properties");
        URL resource = this.getClass().getResource("/settings/log4j.properties");
        File file = new File(resource.toString());
        System.out.println(file);
            context.setConfigLocation(file.toURI());

    }

    public Connection getConnection() throws SQLException {
        Helper.ConnectionSettings _settings = getConnectionInfo("/settings/application.json").get();
        return DriverManager.getConnection(_settings.getDb_Url(), _settings.getUserName(), _settings.getPassword());
    }
}
