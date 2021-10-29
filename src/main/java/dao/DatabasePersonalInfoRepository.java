package dao;

import model.PersonalInfo;

import sturtup.DependencyInjectionImitator;
import sturtup.Helper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

public class DatabasePersonalInfoRepository extends PersonalInfoRepository {
    private static QueryRunner queryRunner = new QueryRunner();
    private Helper helper;
    private Helper.Tables table = Helper.Tables.personalinfo;

    public DatabasePersonalInfoRepository() {
        super(PersonalInfo.class);
        helper = new DependencyInjectionImitator().getHelper();
    }

    @Override
    public CompletableFuture<PersonalInfo> getEntityById(int id) throws SQLException {
        return super.PerformGeneralSelectById(table, id, helper, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityById(int id) throws SQLException {
        return super.PerformGeneralDeleteEntityById(table, id, helper, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntity(PersonalInfo entity) throws SQLException {
        String updateQuery = "UPDATE personalInfo SET email=?, address=?, phoneNumber=? where id = ?";
        var task = queryRunner.update(helper.getConnection(), updateQuery, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber(), entity.getId());
        return CompletableFuture.supplyAsync(() -> task);
    }

    @Override
    public CompletableFuture<Integer> createEntity(PersonalInfo entity) throws SQLException {
        String insertQuery = "INSERT INTO personalinfo(email, address, phonenumber)  VALUES (?,?,?) RETURNING id";
        var task = queryRunner.update(helper.getConnection(), insertQuery, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber());

        return CompletableFuture.supplyAsync(() -> task);

    }

    @Override
    public CompletableFuture<List<PersonalInfo>> getEntities() throws SQLException {
        ResultSetHandler<List<PersonalInfo>> resultHandler = new BeanListHandler<PersonalInfo>(PersonalInfo.class);
        Connection _connection = helper.getConnection();

         var task = queryRunner.query(_connection, "SELECT * FROM personalinfo", resultHandler);
         return CompletableFuture.supplyAsync(()-> task);
    }
}
