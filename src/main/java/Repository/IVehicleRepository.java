package Repository;

import Models.Vehicle;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class IVehicleRepository extends IGeneralRepositoryCommonImplementation<Vehicle> {
    public IVehicleRepository(Class<Vehicle> typeParameterClass) {
        super(Vehicle.class);
    }

    public abstract CompletableFuture<List<Vehicle>> getCarsByUserId(int userId) throws SQLException;
}
