package Repository;

import Models.Vehicle;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Future;

public interface IVehicleRepository extends IGeneralRepository<Vehicle> {
    Future<List<Vehicle>> getCarsByUserId(int userId) throws SQLException;
}
