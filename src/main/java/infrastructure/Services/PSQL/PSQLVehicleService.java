package infrastructure.Services.PSQL;

import application.UserRepository;
import domain.Vehicle;
import application.VehicleRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.StringFormatterMessageFactory;
import utils.DependencyInjectionImitator;
import utils.ServiceAllowedOperation;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PSQLVehicleService {
    private final VehicleRepository vehicleRepository;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public PSQLVehicleService() {
        vehicleRepository = DependencyInjectionImitator.getVehicleRepository();
    }

    public PSQLVehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Get Vehicle by id", AllowedOperationDescription = "Provide user id to get available info about vehicle")
    public Vehicle getVehicle(Integer id) {
        try {
            logger.info("getVehicle from service layer");
            return vehicleRepository.getEntityByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getVehicle", e);
        }
        return null;
    }

    public List<Vehicle> getVehiclesByUser(Integer id) {
        try {
            logger.info("getVehicle from service layer");
            return vehicleRepository.getCarsByUserIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getVehiclesByUser", e);
        }
        return null;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Get all Vehicles", AllowedOperationDescription = "get all available info about vehicles")
    public List<Vehicle> getVehicles() {
        try {
            logger.info("getVehicles from service layer");
            return vehicleRepository.getEntitiesAsync().get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getVehicles", e);
        }
        return null;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Delete Vehicle by id", AllowedOperationDescription = "provide id to delete vehicle")
    public Integer deleteVehicle(Integer id) throws SQLException {
        try {
            logger.info("DeleteVehicle from service layer");
            return vehicleRepository.deleteEntityByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while deleteVehicle", e);
        }
        return null;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Update Vehicle by id", AllowedOperationDescription = "provide id to update vehicle")
    public Integer updateVehicle(Vehicle newVehicle) throws SQLException {
        try {
            logger.info("UpdateVehicle from service layer");
            return vehicleRepository.updateEntityAsync(newVehicle).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while updateVehicle", e);
        }
        return null;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Add new User", AllowedOperationDescription = "provide full info about vehicle to add")
    public Integer addVehicle(Vehicle newVehicle) throws SQLException {
        try {
            logger.info("AddVehicle from service layer");
            return vehicleRepository.createEntityAsync(newVehicle).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while addVehicle", e);
        }
        return null;
    }
}
