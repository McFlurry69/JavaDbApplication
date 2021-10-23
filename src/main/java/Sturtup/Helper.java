package Sturtup;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.Optional;
import java.util.stream.Collectors;

public class Helper {

    public Optional<ConnectionSettings> getConnectionInfo() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            BufferedReader s = getFile("/settings/application.json").get();
            return Optional.of(objectMapper.readValue(s.lines().collect(Collectors.joining()), ConnectionSettings.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

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
}
