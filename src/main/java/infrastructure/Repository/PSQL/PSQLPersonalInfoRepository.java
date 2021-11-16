package infrastructure.Repository.PSQL;

import application.PersonalInfoRepository;
import domain.PersonalInfo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DependencyInjectionImitator;
import utils.ConnectionProvider;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import application.Tables;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.*;

public class PSQLPersonalInfoRepository extends PersonalInfoRepository {
    private static QueryRunner queryRunner = new QueryRunner();
    private ConnectionProvider connectionProvider = new DependencyInjectionImitator().getHelper();
    private Tables table = Tables.personalinfo;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public PSQLPersonalInfoRepository() {
        super(PersonalInfo.class);
    }

    @Override
    public CompletableFuture<PersonalInfo> getEntityByIdAsync(int id) throws SQLException {
        logger.info("getEntityByIdAsync from dao layer");
        return super.PerformGeneralSelectByIdAsync(table, id, connectionProvider, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityByIdAsync(int id) throws SQLException {
        logger.info("deleteEntityByIdAsync from dao layer");
        return super.PerformGeneralDeleteEntityByIdAsync(table, id, connectionProvider, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntityAsync(PersonalInfo entity) throws SQLException {
        logger.info("updateEntityAsync from dao layer");
        String updateQuery = "UPDATE personalInfo SET email=?, address=?, phoneNumber=? where id = ?";
        var task = queryRunner.update(connectionProvider.getConnection(), updateQuery, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber(), entity.getId());
        return CompletableFuture.supplyAsync(() -> task);
    }

    @Override
    public CompletableFuture<Integer> createEntityAsync(PersonalInfo entity) throws SQLException {
        logger.info("createEntityAsync from dao layer");
        String insertQuery = "INSERT INTO personalinfo(email, address, phonenumber)  VALUES (?,?,?) RETURNING id";
        var task = queryRunner.update(connectionProvider.getConnection(), insertQuery, entity.getEmail(), entity.getAddress(), entity.getPhoneNumber());

        return CompletableFuture.supplyAsync(() -> task);

    }

    @Override
    public CompletableFuture<List<PersonalInfo>> getEntitiesAsync() throws SQLException {
        logger.info("getEntitiesAsync from dao layer");
        ResultSetHandler<List<PersonalInfo>> resultHandler = new BeanListHandler<PersonalInfo>(PersonalInfo.class);
        Connection _connection = connectionProvider.getConnection();

         var task = queryRunner.query(_connection, "SELECT * FROM personalinfo", resultHandler);
         return CompletableFuture.supplyAsync(()-> task);
    }
}
