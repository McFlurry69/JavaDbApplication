package dao;

import model.User;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class UserRepository extends GeneralRepositoryImplementation<User> {
    public UserRepository(Class<User> typeParameterClass) {
        super(User.class);
    }

    public abstract CompletableFuture<User> getFullUserInfoByIdAsync(int id) throws SQLException;
    public abstract CompletableFuture<List<User>> getFullUsersInfoAsync() throws SQLException;
}
