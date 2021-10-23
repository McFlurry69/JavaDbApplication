package Repository;

import Models.User;
import Sturtup.Helper;

import java.sql.*;
import java.util.List;

public class UserRepository implements IGeneralRepository<User> {
    static Helper _helper = new Helper();
    static Helper.ConnectionSettings _settings = _helper.getConnectionInfo().get();

    @Override
    public User getEntityById(int id) {
        return new User();
    }

    @Override
    public boolean deleteEntityById(int id) {
        return false;
    }

    @Override
    public User updateEntity(User entity) {
        return null;
    }

    @Override
    public int createEntity(User entity) {
        return 0;
    }
}
