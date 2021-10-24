package Repository;

import Models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

public interface IUserRepository<T> extends IGeneralRepository<T> {
    Future<User> getFullUserInfoById(int id) throws SQLException;
    Future<List<User>> getFullUsersInfo() throws SQLException;
}
