package dao;

import model.Vehicle;
import sturtup.DependencyInjectionImitator;
import sturtup.Helper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class DatabaseVehicleRepository extends VehicleRepository {
    private static QueryRunner queryRunner = new QueryRunner();
    private Helper helper;
    private Helper.Tables table = Helper.Tables.vehicle;

    public DatabaseVehicleRepository() {
        super(Vehicle.class);
        helper = new DependencyInjectionImitator().getHelper();
    }

    @Override
    public CompletableFuture<Vehicle> getEntityByIdAsync(int id) throws SQLException {
        return super.PerformGeneralSelectByIdAsync(table, id, helper, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityByIdAsync(int id) throws SQLException {
        return super.PerformGeneralDeleteEntityByIdAsync(table, id, helper, queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntityAsync(Vehicle entity) throws SQLException {
        String updateQuery = "UPDATE vehicle SET carmodel=?, manufacturedate=?, userid=? where id = ?";
        var task = queryRunner.update(helper.getConnection(), updateQuery, entity.getCarModel(), entity.getManufactureDate(), entity.getUserId(), entity.getId());

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<Integer> createEntityAsync(Vehicle entity) throws SQLException {
        String insertQuery = "INSERT INTO vehicle(carmodel, manufacturedate, userid) VALUES (?,?,?) RETURNING id";
        var task = queryRunner.update(helper.getConnection(), insertQuery, entity.getCarModel(), entity.getManufactureDate(), entity.getUserId());

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<Vehicle>> getEntitiesAsync() throws SQLException {
        return super.PerformGeneralGetEntitiesAsync(table, helper, queryRunner);
    }

    @Override
    public CompletableFuture<List<Vehicle>> getCarsByUserIdAsync(int userId) throws SQLException {
        ResultSetHandler<List<Vehicle>> resultHandler = new BeanListHandler<Vehicle>(Vehicle.class);

        var task = queryRunner.query(helper.getConnection(), "SELECT * FROM vehicle WHERE userid=?", resultHandler, userId);

        return CompletableFuture.supplyAsync(()->task);
    }
}