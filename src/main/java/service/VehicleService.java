package service;

import model.Vehicle;
import dao.VehicleRepository;
import org.apache.logging.log4j.Logger;
import sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VehicleService {
    private VehicleRepository vehicleRepository;
    private final Logger logger;

    public VehicleService(){
        vehicleRepository = DependencyInjectionImitator.get_VehicleRepository();
        logger = DependencyInjectionImitator.get_Logger();
    }

    public Vehicle getVehicle(Integer id){
        try {
            logger.info("getVehicle from service layer");
            return vehicleRepository.getEntityByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getVehicle", e);
        }
        return null;
    }

    public List<Vehicle> getVehiclesByUser(Integer id){
        try {
            logger.info("getVehicle from service layer");
            return vehicleRepository.getCarsByUserIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getVehiclesByUser", e);
        }
        return null;
    }

    public List<Vehicle> getVehicles(){
        try {
            logger.info("getVehicles from service layer");
            return vehicleRepository.getEntitiesAsync().get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getVehicles", e);
        }
        return null;
    }

    public Integer deleteVehicle(Integer id) throws SQLException {
        try {
            logger.info("DeleteVehicle from service layer");
            return vehicleRepository.deleteEntityByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while deleteVehicle", e);
        }
        return null;
    }

    public Integer updateVehicle(Vehicle newVehicle) throws SQLException {
        try {
            logger.info("UpdateVehicle from service layer");
            return vehicleRepository.updateEntityAsync(newVehicle).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while updateVehicle", e);
        }
        return null;
    }

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
