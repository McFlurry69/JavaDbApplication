package Repository;

import Models.Vehicle;
import Sturtup.DependencyInjectionImitator;
import Sturtup.Helper;
import org.apache.commons.dbutils.AsyncQueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class VehicleRepository implements IVehicleRepository {
    private static AsyncQueryRunner _queryRunner = new AsyncQueryRunner(Executors.newCachedThreadPool());
    private Helper _helper;

    public VehicleRepository() {
        _helper = new DependencyInjectionImitator().get_helper();
    }

    @Override
    public Future<Vehicle> getEntityById(int id) throws SQLException {
        ResultSetHandler<Vehicle> resultHandler = new BeanHandler<Vehicle>(Vehicle.class);
        Future<Vehicle> result;

        Connection _connection = _helper.get_connection();
        return _queryRunner.query(_connection, "SELECT * FROM vehicle WHERE id=?",
                resultHandler, id);
    }

    @Override
    public void deleteEntityById(int id) {
        String deleteQuery = "DELETE FROM vehicle WHERE id=?";
        try {
            _queryRunner.update(_helper.get_connection(), deleteQuery, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateEntity(Vehicle entity) {
        String updateQuery = "UPDATE vehicle SET carmodel=?, manufacturedate=?, userid=? where id = ?";
        try {
            _queryRunner.update(_helper.get_connection(), updateQuery, entity.getCarModel(), entity.getManufactureDate(), entity.getUserId(), entity.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int createEntity(Vehicle entity) {
        String insertQuery = "INSERT INTO vehicle(carmodel, manufacturedate, userid) VALUES (?,?,?) RETURNING id";
        Future<Integer> future;
        try {
            future = _queryRunner.update(_helper.get_connection(), insertQuery, entity.getCarModel(), entity.getManufactureDate(), entity.getUserId());
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
    public Future<List<Vehicle>> getEntities() throws SQLException {
        ResultSetHandler<List<Vehicle>> resultHandler = new BeanListHandler<Vehicle>(Vehicle.class);

        Connection _connection = _helper.get_connection();
        return _queryRunner.query(_connection, "SELECT * FROM vehicle", resultHandler);
    }

    @Override
    public Future<List<Vehicle>> getCarsByUserId(int userId) throws SQLException {
        ResultSetHandler<List<Vehicle>> resultHandler = new BeanListHandler<Vehicle>(Vehicle.class);

        Connection _connection = _helper.get_connection();
        return _queryRunner.query(_connection, "SELECT * FROM vehicle WHERE userid=?", resultHandler, userId);
    }
}