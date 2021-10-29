package dao;

import model.PersonalInfo;
import model.User;
import sturtup.DependencyInjectionImitator;
import sturtup.Helper;
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

public class DatabaseUserRepository extends UserRepository {
    private static QueryRunner queryRunner = new QueryRunner();
    private DependencyInjectionImitator IOC;
    private Helper helper;
    private Helper.Tables table = Helper.Tables.users;

    public DatabaseUserRepository() {
        super(User.class);
        IOC = new DependencyInjectionImitator();
        helper = IOC.getHelper();
    }

    @Override
    public CompletableFuture<User> getEntityById(int id) throws SQLException {
        return super.PerformGeneralSelectById(table, id, helper, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityById(int id) throws SQLException {
        return super.PerformGeneralDeleteEntityById(table, id, helper, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntity(User entity) throws SQLException {
        String updateQuery = "UPDATE users SET name=?, age=?, personalinfoid=? where id = ?";
        var task = queryRunner.update(helper.getConnection(), updateQuery, entity.getName(), entity.getAge(), entity.getPersonalInfoId(), entity.getId());

        return CompletableFuture.supplyAsync(() -> task);
    }

    @Override
    public CompletableFuture<Integer> createEntity(User entity) throws SQLException {
        Integer personalInfoId = entity.getPersonalInfo() == null ? entity.getPersonalInfoId() : entity.getPersonalInfo().getId();
        String insertQuery = "INSERT INTO users(Name, age, PersonalInfoId) VALUES (?,?,?) RETURNING id";

        var task = queryRunner.update(helper.getConnection(), insertQuery, entity.getName(), entity.getAge(), personalInfoId);
        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<User>> getEntities() throws SQLException {
        return super.PerformGeneralGetEntities(table, helper, queryRunner);
    }

    @Override
    public CompletableFuture<User> getFullUserInfoById(int id) throws SQLException {
        ResultSetHandler<User> resultHandler = new BeanHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = helper.getConnection();
        var task = queryRunner.query(_connection, "SELECT * FROM users AS u JOIN personalinfo AS pers ON personalinfoid=pers.id WHERE u.id = ?",
                resultHandler, id);

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<User>> getFullUsersInfo() throws SQLException {
        ResultSetHandler<List<User>> resultHandler = new BeanListHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = helper.getConnection();

        var task = queryRunner.query(_connection, "SELECT * FROM users AS u JOIN personalinfo AS pers ON personalinfoid=pers.id", resultHandler);

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
