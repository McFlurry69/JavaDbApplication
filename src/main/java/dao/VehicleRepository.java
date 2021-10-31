package dao;

import model.Vehicle;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class VehicleRepository extends GeneralRepositoryImplementation<Vehicle> {
    public VehicleRepository(Class<Vehicle> typeParameterClass) {
        super(Vehicle.class);
    }

    public abstract CompletableFuture<List<Vehicle>> getCarsByUserIdAsync(int userId) throws SQLException;
}
