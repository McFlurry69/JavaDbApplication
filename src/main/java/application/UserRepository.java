package application;

import domain.User;
import infrastructure.Repository.PSQL.PSQLGeneralRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public abstract class UserRepository extends PSQLGeneralRepository<User> {
    public UserRepository(Class<User> typeParameterClass) {
        super(User.class);
    }

    public abstract CompletableFuture<User> getFullUserInfoByIdAsync(int id) throws SQLException;
    public abstract CompletableFuture<List<User>> getFullUsersInfoAsync() throws SQLException;

    public abstract CompletableFuture<List<User>> getFullUserInfoByNameAsync(String name) throws SQLException;
}
