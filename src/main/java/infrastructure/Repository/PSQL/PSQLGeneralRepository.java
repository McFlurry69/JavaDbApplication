package infrastructure.Repository.PSQL;

import application.GeneralRepository;
import utils.ConnectionProvider;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import application.Tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;


public abstract class PSQLGeneralRepository<T> implements GeneralRepository<T> {
    private Class<T> typeParameterClass;

    public PSQLGeneralRepository(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public CompletableFuture<T> PerformGeneralSelectByIdAsync(Tables tableName, int id, ConnectionProvider helper, QueryRunner queryRunner) throws SQLException {
        String query = "SELECT * FROM $tableName WHERE id=?".replace("$tableName", tableName.toString());
        ResultSetHandler<T> resultHandler = new BeanHandler<T>(typeParameterClass);
        Connection _connection = helper.getConnection();
        T task = queryRunner.query(_connection, query, resultHandler, id);
        return CompletableFuture.supplyAsync(() -> task);
    }

    public CompletableFuture<Integer> PerformGeneralDeleteEntityByIdAsync(Tables tableName, int id, ConnectionProvider helper, QueryRunner queryRunner) throws SQLException {
        String deleteQuery = "DELETE FROM $tableName WHERE id=?".replace("$tableName", tableName.toString());
        Integer task = queryRunner.update(helper.getConnection(), deleteQuery, id);
        return CompletableFuture.supplyAsync(() -> task);
    }

    public CompletableFuture<List<T>> PerformGeneralGetEntitiesAsync(Tables tableName, ConnectionProvider helper, QueryRunner queryRunner) throws SQLException {
        ResultSetHandler<List<T>> resultHandler = new BeanListHandler<T>(typeParameterClass);
        Connection _connection = helper.getConnection();
        String query = "SELECT * FROM $tableName".replace("$tableName", tableName.toString());
        List<T> task = queryRunner.query(_connection, query, resultHandler);

        return CompletableFuture.supplyAsync(() -> task);
    }
}
