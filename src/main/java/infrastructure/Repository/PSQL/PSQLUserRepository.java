package infrastructure.Repository.PSQL;

import application.UserRepository;
import domain.PersonalInfo;
import domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DependencyInjectionImitator;
import utils.ConnectionProvider;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import application.Tables;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class PSQLUserRepository extends UserRepository {
    private static QueryRunner queryRunner = new QueryRunner();
    private DependencyInjectionImitator IOC = new DependencyInjectionImitator();
    private ConnectionProvider connectionProvider = IOC.getHelper();
    private Tables table = Tables.users;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public PSQLUserRepository() {
        super(User.class);
    }

    @Override
    public CompletableFuture<User> getEntityByIdAsync(int id) throws SQLException {
        logger.info("getEntityByIdAsync from dao layer");
        return super.PerformGeneralSelectByIdAsync(table, id, connectionProvider, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityByIdAsync(int id) throws SQLException {
        logger.info("deleteEntityByIdAsync from dao layer");
        return super.PerformGeneralDeleteEntityByIdAsync(table, id, connectionProvider, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntityAsync(User entity) throws SQLException {
        logger.info("updateEntityAsync from dao layer");
        String updateQuery = "UPDATE users SET name=?, age=?, personalinfoid=? where id = ?";
        var task = queryRunner.update(connectionProvider.getConnection(), updateQuery, entity.getName(), entity.getAge(), entity.getPersonalInfoId(), entity.getId());

        return CompletableFuture.supplyAsync(() -> task);
    }

    @Override
    public CompletableFuture<Integer> createEntityAsync(User entity) throws SQLException {
        logger.info("createEntityAsync from dao layer");
        Integer personalInfoId = entity.getPersonalInfo() == null ? entity.getPersonalInfoId() : entity.getPersonalInfo().getId();
        String insertQuery = "INSERT INTO users(Name, age, PersonalInfoId) VALUES (?,?,?) RETURNING id";

        var task = queryRunner.update(connectionProvider.getConnection(), insertQuery, entity.getName(), entity.getAge(), personalInfoId);
        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<User>> getEntitiesAsync() throws SQLException {
        logger.info("getEntitiesAsync from dao layer");
        return super.PerformGeneralGetEntitiesAsync(table, connectionProvider, queryRunner);
    }

    @Override
    public CompletableFuture<User> getFullUserInfoByIdAsync(int id) throws SQLException {
        logger.info("getFullUserInfoByIdAsync from dao layer");
        ResultSetHandler<User> resultHandler = new BeanHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = connectionProvider.getConnection();
        var task = queryRunner.query(_connection, "SELECT * FROM users AS u JOIN personalinfo AS pers ON personalinfoid=pers.id WHERE u.id = ?",
                resultHandler, id);

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<User>> getFullUsersInfoAsync() throws SQLException {
        logger.info("getFullUsersInfoAsync from dao layer");
        ResultSetHandler<List<User>> resultHandler = new BeanListHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = connectionProvider.getConnection();

        var task = queryRunner.query(_connection, "SELECT * FROM users AS u JOIN personalinfo AS pers ON personalinfoid=pers.id", resultHandler);

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<User>> getFullUserInfoByNameAsync(String name) throws SQLException {
        logger.info("getFullUserInfoByNameAsync from dao layer");
        ResultSetHandler<List<User>> resultHandler = new BeanListHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = connectionProvider.getConnection();
        String q = "SELECT * FROM users AS u JOIN personalinfo p ON p.id = u.personalinfoid where name LIKE '%$UserName%'".replace("$UserName", name);
        var task = queryRunner.query(_connection, q, resultHandler);

        return CompletableFuture.supplyAsync(()->task);
    }


    public class UserFullInfoHandler extends BasicRowProcessor {

        @Override
        public List toBeanList(ResultSet rs, Class clazz) {
            try {
                List newlist = new LinkedList();
                while (rs.next()) {
                    newlist.add(toBean(rs, clazz));
                }
                return newlist;
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }

        @Override
        public Object toBean(ResultSet rs, Class type) throws SQLException {
            PersonalInfo info = new PersonalInfo();
            info.setId(rs.getInt("id"));
            info.setAddress(rs.getString("address"));
            info.setEmail(rs.getString("email"));
            info.setPhoneNumber(rs.getString("phonenumber"));


            User user = new User();
            user.setId(rs.getInt("id"));
            user.setAge(rs.getInt("age"));
            user.setName(rs.getString("name"));
            user.setPersonalInfoId(rs.getInt("personalinfoid"));
            user.setPersonalInfo(info);

            return user;
        }
    }
}
