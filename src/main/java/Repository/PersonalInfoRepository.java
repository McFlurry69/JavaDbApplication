package Repository;

import Models.PersonalInfo;

import Models.User;
import Sturtup.DependencyInjectionImitator;
import Sturtup.Helper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

public class PersonalInfoRepository extends IPersonalInfoRepository {
    private static QueryRunner _queryRunner = new QueryRunner();
    private Helper _helper;
    private Helper.Tables _table = Helper.Tables.personalinfo;

    public PersonalInfoRepository() {
        super(PersonalInfo.class);
        _helper = new DependencyInjectionImitator().get_helper();
    }

    @Override
    public CompletableFuture<PersonalInfo> getEntityById(int id) throws SQLException {
        return super.PerformGeneralSelectById(_table, id, _helper, _queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityById(int id) throws SQLException {
        return super.PerformGeneralDeleteEntityById(_table, id, _helper, _queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntity(PersonalInfo entity) throws SQLException {
        String updateQuery = "UPDATE personalInfo SET email=?, address=?, phoneNumber=? where id = ?";
        var task = _queryRunner.update(_helper.get_connection(), updateQuery, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber(), entity.getId());
        return CompletableFuture.supplyAsync(() -> task);
    }

    @Override
    public CompletableFuture<Integer> createEntity(PersonalInfo entity) throws SQLException {
        String insertQuery = "INSERT INTO personalinfo(email, address, phonenumber)  VALUES (?,?,?) RETURNING id";
        var result = _queryRunner.update(_helper.get_connection(), insertQuery, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber());

        return CompletableFuture.supplyAsync(() -> result);

    }

    @Override
    public CompletableFuture<List<PersonalInfo>> getEntities() throws SQLException {
        ResultSetHandler<List<PersonalInfo>> resultHandler = new BeanListHandler<PersonalInfo>(PersonalInfo.class);
        Connection _connection = _helper.get_connection();

         var result = _queryRunner.query(_connection, "SELECT * FROM personalinfo", resultHandler);
         return CompletableFuture.supplyAsync(()->result);
    }
}
