package application;

import domain.Vehicle;
import infrastructure.Repository.PSQL.PSQLGeneralRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public abstract class VehicleRepository extends PSQLGeneralRepository<Vehicle> {
    public VehicleRepository(Class<Vehicle> typeParameterClass) {
        super(Vehicle.class);
    }

    public abstract CompletableFuture<List<Vehicle>> getCarsByUserIdAsync(int userId) throws SQLException;
}
