package Repository;

import Models.PersonalInfo;

import Sturtup.DependencyInjectionImitator;
import Sturtup.Helper;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

public class PersonalInfoRepository implements IPersonalInfoRepository {
    private static AsyncQueryRunner _queryRunner = new AsyncQueryRunner(Executors.newCachedThreadPool());
    private Helper _helper;

    public PersonalInfoRepository(){
        _helper = new DependencyInjectionImitator().get_helper();
    }

    @Override
    public Future<PersonalInfo> getEntityById(int id) throws SQLException {
        ResultSetHandler<PersonalInfo> resultHandler = new BeanHandler<PersonalInfo>(PersonalInfo.class);
        Future<PersonalInfo> result;

            Connection _connection = _helper.get_connection();
            result = _queryRunner.query(_connection, "SELECT * FROM personalinfo WHERE id=?",
                    resultHandler, id);
            return result;

    }

    @Override
    public void deleteEntityById(int id) {
        String deleteQuery = "DELETE FROM personalInfo WHERE id=?";
        try {
            _queryRunner.update(_helper.get_connection(), deleteQuery, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntity(PersonalInfo entity) {
        String updateQuery ="UPDATE personalInfo SET email=?, address=?, phoneNumber=? where id = ?";
        try {
            _queryRunner.update(_helper.get_connection(), updateQuery, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber(), entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createEntity(PersonalInfo entity) {
        String insertQuery ="INSERT INTO personalinfo(email, address, phonenumber)  VALUES (?,?,?) RETURNING id";
        Future<Integer> future;
        try {
            future = _queryRunner.update(_helper.get_connection(), insertQuery, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber());
            return future.get();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public Future<List<PersonalInfo>> getEntities() {
        ResultSetHandler<List<PersonalInfo>> resultHandler = new BeanListHandler<PersonalInfo>(PersonalInfo.class);
        Future<List<PersonalInfo>> result;

        try {
            Connection _connection = _helper.get_connection();
            result = _queryRunner.query(_connection, "SELECT * FROM personalinfo", resultHandler);
            return result;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }
}
