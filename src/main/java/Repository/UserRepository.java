package Repository;

import Models.PersonalInfo;
import Models.User;
import Sturtup.DependencyInjectionImitator;
import Sturtup.Helper;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.BasicRowProcessor;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

public class UserRepository implements IUserRepository<User> {
    private static AsyncQueryRunner _queryRunner = new AsyncQueryRunner(Executors.newCachedThreadPool());
    private final PersonalInfoRepository _personalInfo;
    private DependencyInjectionImitator _IOC;
    private Helper _helper;

    public UserRepository() {
        _IOC = new DependencyInjectionImitator();
        _helper = _IOC.get_helper();
        _personalInfo = _IOC.get_PersonalInfoRepository();
    }

    @Override
    public Future<User> getEntityById(int id) throws SQLException {
        ResultSetHandler<User> resultHandler = new BeanHandler<User>(User.class);
        Connection _connection = _helper.get_connection();
        return _queryRunner.query(_connection, "SELECT * FROM users WHERE id=?",
                resultHandler, id);
    }

    @Override
    public void deleteEntityById(int id) {
        String deleteQuery = "DELETE FROM users WHERE id=?";
        try {
            _queryRunner.update(_helper.get_connection(), deleteQuery, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntity(User entity) {
        String updateQuery = "UPDATE users SET name=?, age=?, personalinfoid=? where id = ?";
        try {
            _queryRunner.update(_helper.get_connection(), updateQuery, entity.getName(), entity.getAge(), entity.getPersonalInfoId(), entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createEntity(User entity) {
        Integer personalInfoId = entity.getPersonalInfo() == null ? entity.getPersonalInfoId() : entity.getPersonalInfo().getId();
        String insertQuery = "INSERT INTO users(Name, age, PersonalInfoId) VALUES (?,?,?) RETURNING id";
        Future<Integer> future;
        try {
            future = _queryRunner.update(_helper.get_connection(), insertQuery, entity.getName(), entity.getAge(), personalInfoId);
            return future.get(1, TimeUnit.SECONDS);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Future<List<User>> getEntities() throws SQLException {
        ResultSetHandler<List<User>> resultHandler = new BeanListHandler<User>(User.class);
        Connection _connection = _helper.get_connection();

        return _queryRunner.query(_connection, "SELECT * FROM users", resultHandler);
    }

    @Override
    public Future<User> getFullUserInfoById(int id) throws SQLException {
        ResultSetHandler<User> resultHandler = new BeanHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = _helper.get_connection();
        return _queryRunner.query(_connection, "SELECT * FROM users AS u JOIN personalinfo AS pers ON personalinfoid=pers.id WHERE u.id = ?",
                resultHandler, id);
    }

    @Override
    public Future<List<User>> getFullUsersInfo() throws SQLException {
        ResultSetHandler<List<User>> resultHandler = new BeanListHandler<User>(User.class, new UserFullInfoHandler());
        Connection _connection = _helper.get_connection();

        return _queryRunner.query(_connection, "SELECT * FROM users AS u JOIN personalinfo AS pers ON personalinfoid=pers.id", resultHandler);
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
            info.setId(rs.getObject("id", Integer.class));
            info.setAddress(rs.getString("address"));
            info.setEmail(rs.getString("email"));
            info.setPhoneNumber(rs.getString("phonenumber"));


            User user = new User();
            user.setId(rs.getObject("id", Integer.class));
            user.setAge(rs.getObject("age", Integer.class));
            user.setName(rs.getString("name"));
            user.setPersonalInfoId(rs.getObject("personalinfoid", Integer.class));
            user.setPersonalInfo(info);

            return user;
        }
    }
}
