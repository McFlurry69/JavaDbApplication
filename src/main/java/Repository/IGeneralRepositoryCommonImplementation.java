package Repository;

import Models.User;
import Sturtup.Helper;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;


public abstract class IGeneralRepositoryCommonImplementation<T> implements IGeneralRepository<T> {
    private Class<T> typeParameterClass;

    public IGeneralRepositoryCommonImplementation(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public CompletableFuture<T> PerformGeneralSelectById(Helper.Tables tableName, int id, Helper helper, QueryRunner queryRunner) throws SQLException {
        String query = "SELECT * FROM $tableName WHERE id=?".replace("$tableName", tableName.toString());
        ResultSetHandler<T> resultHandler = new BeanHandler<T>(typeParameterClass);
        Connection _connection = helper.get_connection();
        T result = queryRunner.query(_connection, query, resultHandler, id);
        return CompletableFuture.supplyAsync(() -> result);
    }

    public CompletableFuture<Integer> PerformGeneralDeleteEntityById(Helper.Tables tableName, int id, Helper helper, QueryRunner queryRunner) throws SQLException {
        String deleteQuery = "DELETE FROM ? WHERE id=?";
        Integer task = queryRunner.update(helper.get_connection(), deleteQuery, tableName.toString(), id);
        return CompletableFuture.supplyAsync(() -> task);
    }

    public CompletableFuture<List<T>> PerformGeneralGetEntities(Helper.Tables tableName, Helper helper, QueryRunner queryRunner) throws SQLException {
        ResultSetHandler<List<T>> resultHandler = new BeanListHandler<T>(typeParameterClass);
        Connection _connection = helper.get_connection();
        String query = "SELECT * FROM $tableName".replace("$tableName", tableName.toString());
        List<T> result = queryRunner.query(_connection, query, resultHandler);

        return CompletableFuture.supplyAsync(() -> result);
    }
}
