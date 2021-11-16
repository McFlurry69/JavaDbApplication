package infrastructure.Repository.PSQL;

import application.VehicleRepository;
import domain.Vehicle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DependencyInjectionImitator;
import utils.ConnectionProvider;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import application.Tables;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class PSQLVehicleRepository extends VehicleRepository {
    private static QueryRunner queryRunner = new QueryRunner();
    private ConnectionProvider connectionProvider = new DependencyInjectionImitator().getHelper();
    private Tables table = Tables.vehicle;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public PSQLVehicleRepository() {
        super(Vehicle.class);
    }

    @Override
    public CompletableFuture<Vehicle> getEntityByIdAsync(int id) throws SQLException {
        logger.info("getEntityByIdAsync from dao layer");
        return super.PerformGeneralSelectByIdAsync(table, id, connectionProvider, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityByIdAsync(int id) throws SQLException {
        logger.info("deleteEntityByIdAsync from dao layer");
        return super.PerformGeneralDeleteEntityByIdAsync(table, id, connectionProvider, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntityAsync(Vehicle entity) throws SQLException {
        logger.info("updateEntityAsync from dao layer");
        String updateQuery = "UPDATE vehicle SET carmodel=?, manufacturedate=?, userid=? where id = ?";
        var task = queryRunner.update(connectionProvider.getConnection(), updateQuery, entity.getCarModel(), entity.getManufactureDate(), entity.getUserId(), entity.getId());

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<Integer> createEntityAsync(Vehicle entity) throws SQLException {
        logger.info("createEntityAsync from dao layer");
        String insertQuery = "INSERT INTO vehicle(carmodel, manufacturedate, userid) VALUES (?,?,?) RETURNING id";
        var task = queryRunner.update(connectionProvider.getConnection(), insertQuery, entity.getCarModel(), entity.getManufactureDate(), entity.getUserId());

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<Vehicle>> getEntitiesAsync() throws SQLException {
        logger.info("getEntitiesAsync from dao layer");
        return super.PerformGeneralGetEntitiesAsync(table, connectionProvider, queryRunner);
    }

    @Override
    public CompletableFuture<List<Vehicle>> getCarsByUserIdAsync(int userId) throws SQLException {
        logger.info("getCarsByUserIdAsync from dao layer");
        ResultSetHandler<List<Vehicle>> resultHandler = new BeanListHandler<Vehicle>(Vehicle.class);

        var task = queryRunner.query(connectionProvider.getConnection(), "SELECT * FROM vehicle WHERE userid=?", resultHandler, userId);

        return CompletableFuture.supplyAsync(()->task);
    }
}