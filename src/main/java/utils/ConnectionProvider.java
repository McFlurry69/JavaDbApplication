package utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;
import java.util.stream.Collectors;

public class ConnectionProvider {

    private final Logger logger = LogManager.getLogger(this.getClass());
    private ConnectionSettings connectionSettings;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Optional<BufferedReader> getFile(String path){
        try {
            logger.info("Creating filestream");
            InputStream inputStream = this.getClass().getResourceAsStream(path);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return Optional.of(bufferedReader);
        } catch (Exception e) {
            logger.error("Did not get file ", e);
            e.printStackTrace();
        }
        return Optional.empty();
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
        logger.info("Getting connection info from file");
        try {
            if (connectionSettings == null) {
                connectionSettings = objectMapper.readValue(getFile(path).get().lines().collect(Collectors.joining()), ConnectionSettings.class);
            }
            return Optional.of(connectionSettings);
        } catch (IOException e) {
            logger.error("Getting connection gone wrong!", e);
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public Connection getConnection() throws SQLException {
        ConnectionProvider.ConnectionSettings _settings = getConnectionInfo("/dbSettings.json").get();
        return DriverManager.getConnection(_settings.getDb_Url(), _settings.getUserName(), _settings.getPassword());
    }
}
