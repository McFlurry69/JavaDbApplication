package Repository;

import Models.Vehicle;
import Sturtup.DependencyInjectionImitator;
import Sturtup.Helper;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class VehicleRepository extends IVehicleRepository {
    private static QueryRunner _queryRunner = new QueryRunner();
    private Helper _helper;
    private Helper.Tables _table = Helper.Tables.vehicle;

    public VehicleRepository() {
        super(Vehicle.class);
        _helper = new DependencyInjectionImitator().get_helper();
    }

    @Override
    public CompletableFuture<Vehicle> getEntityById(int id) throws SQLException {
        return super.PerformGeneralSelectById(_table, id, _helper, _queryRunner);
    }

    @Override
    public CompletableFuture<Integer> deleteEntityById(int id) throws SQLException {
        return super.PerformGeneralDeleteEntityById(_table, id, _helper, _queryRunner);
    }

    @Override
    public CompletableFuture<Integer> updateEntity(Vehicle entity) throws SQLException {
        String updateQuery = "UPDATE vehicle SET carmodel=?, manufacturedate=?, userid=? where id = ?";
        var task = _queryRunner.update(_helper.get_connection(), updateQuery, entity.getCarModel(), entity.getManufactureDate(), entity.getUserId(), entity.getId());

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<Integer> createEntity(Vehicle entity) throws SQLException {
        String insertQuery = "INSERT INTO vehicle(carmodel, manufacturedate, userid) VALUES (?,?,?) RETURNING id";
        var task = _queryRunner.update(_helper.get_connection(), insertQuery, entity.getCarModel(), entity.getManufactureDate(), entity.getUserId());

        return CompletableFuture.supplyAsync(()->task);
    }

    @Override
    public CompletableFuture<List<Vehicle>> getEntities() throws SQLException {
        return super.PerformGeneralGetEntities(_table, _helper, _queryRunner);
    }

    @Override
    public CompletableFuture<List<Vehicle>> getCarsByUserId(int userId) throws SQLException {
        ResultSetHandler<List<Vehicle>> resultHandler = new BeanListHandler<Vehicle>(Vehicle.class);

        var task = _queryRunner.query(_helper.get_connection(), "SELECT * FROM vehicle WHERE userid=?", resultHandler, userId);

        return CompletableFuture.supplyAsync(()->task);
    }
}