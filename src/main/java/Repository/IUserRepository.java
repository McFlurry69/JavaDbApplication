package Repository;

import Models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public abstract class IUserRepository extends IGeneralRepositoryCommonImplementation<User> {
    public IUserRepository(Class<User> typeParameterClass) {
        super(User.class);
    }

    public abstract CompletableFuture<User> getFullUserInfoById(int id) throws SQLException;
    public abstract CompletableFuture<List<User>> getFullUsersInfo() throws SQLException;
}
