package dao;

import sturtup.Helper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public abstract class GeneralRepositoryImplementation<T> implements GeneralRepository<T> {
    private Class<T> typeParameterClass;

    public GeneralRepositoryImplementation(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public CompletableFuture<T> PerformGeneralSelectById(Helper.Tables tableName, int id, Helper helper, QueryRunner queryRunner) throws SQLException {
        String query = "SELECT * FROM $tableName WHERE id=?".replace("$tableName", tableName.toString());
        ResultSetHandler<T> resultHandler = new BeanHandler<T>(typeParameterClass);
        Connection _connection = helper.getConnection();
        T task = queryRunner.query(_connection, query, resultHandler, id);
        return CompletableFuture.supplyAsync(() -> task);
    }

    public CompletableFuture<Integer> PerformGeneralDeleteEntityById(Helper.Tables tableName, int id, Helper helper, QueryRunner queryRunner) throws SQLException {
        String deleteQuery = "DELETE FROM ? WHERE id=?";
        Integer task = queryRunner.update(helper.getConnection(), deleteQuery, tableName.toString(), id);
        return CompletableFuture.supplyAsync(() -> task);
    }

    public CompletableFuture<List<T>> PerformGeneralGetEntities(Helper.Tables tableName, Helper helper, QueryRunner queryRunner) throws SQLException {
        ResultSetHandler<List<T>> resultHandler = new BeanListHandler<T>(typeParameterClass);
        Connection _connection = helper.getConnection();
        String query = "SELECT * FROM $tableName".replace("$tableName", tableName.toString());
        List<T> task = queryRunner.query(_connection, query, resultHandler);

        return CompletableFuture.supplyAsync(() -> task);
    }
}
