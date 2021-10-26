package Repository;

import Models.PersonalInfo;
import Models.User;
import Sturtup.DependencyInjectionImitator;
import Sturtup.Helper;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class UserRepository extends IUserRepository {
    private static QueryRunner _queryRunner = new QueryRunner();
    private final PersonalInfoRepository _personalInfo;
    private DependencyInjectionImitator _IOC;
    private Helper _helper;
    private Helper.Tables _table = Helper.Tables.users;

    public UserRepository() {
        super(User.class);
        _IOC = new DependencyInjectionImitator();
        _helper = _IOC.get_helper();
        _personalInfo = _IOC.get_PersonalInfoRepository();
    }

    @Override
    public CompletableFuture<User> getEntityById(int id) throws SQLException {
        return super.PerformGeneralSelectById(_table, id, _helper, _queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityById(int id) throws SQLException {
        return super.PerformGeneralDeleteEntityById(_table, id, _helper, _queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntity(User entity) throws SQLException {
        String updateQuery = "UPDATE users SET name=?, age=?, personalinfoid=? where id = ?";
        var task = _queryRunner.update(_helper.get_connection(), updateQuery, entity.getName(), entity.getAge(), entity.getPersonalInfoId(), entity.getId());

        return CompletableFuture.supplyAsync(() -> task);
    }

    @Override
    public CompletableFuture<Integer> createEntity(User entity) throws SQLException {
        Integer personalInfoId = entity.getPersonalInfo() == null ? entity.getPersonalInfoId() : entity.getPersonalInfo().getId();
        String insertQuery = "INSERT INTO users(Name, age, PersonalInfoId) VALUES (?,?,?) RETURNING id";

        var task = _queryRunner.update(_helper.get_connection(), insertQuery, entity.getName(), entity.getAge(), personalInfoId);
        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<User>> getEntities() throws SQLException {
        return super.PerformGeneralGetEntities(_table, _helper, _queryRunner);
    }

    @Override
    public CompletableFuture<User> getFullUserInfoById(int id) throws SQLException {
        ResultSetHandler<User> resultHandler = new BeanHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = _helper.get_connection();
        var task = _queryRunner.query(_connection, "SELECT * FROM users AS u JOIN personalinfo AS pers ON personalinfoid=pers.id WHERE u.id = ?",
                resultHandler, id);

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<User>> getFullUsersInfo() throws SQLException {
        ResultSetHandler<List<User>> resultHandler = new BeanListHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = _helper.get_connection();

        var task = _queryRunner.query(_connection, "SELECT * FROM users AS u JOIN personalinfo AS pers ON personalinfoid=pers.id", resultHandler);

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
