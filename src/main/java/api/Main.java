package api;


import dao.DatabasePersonalInfoRepository;
import model.PersonalInfo;
import service.UserService;
import sturtup.DbSimpleOperations;
import sturtup.Helper;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class Main {
    public static void main(String[] args)
    {
        DatabasePersonalInfoRepository s = new DatabasePersonalInfoRepository();
        try {
            CompletableFuture<List<PersonalInfo>> entitiesAsync = s.getEntitiesAsync();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            CompletableFuture<List<PersonalInfo>> entitiesAsync = s.getEntitiesAsync();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
