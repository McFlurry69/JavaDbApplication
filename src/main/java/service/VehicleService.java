package service;

import model.Vehicle;
import dao.VehicleRepository;
import sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VehicleService {
    private final VehicleRepository vehicleRepository;

    public VehicleService(){
        vehicleRepository = DependencyInjectionImitator.get_VehicleRepository();
    }

    public Vehicle getVehicle(Integer id){
        try {
            return vehicleRepository.getEntityById(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByUser(Integer id){
        try {
            return vehicleRepository.getCarsByUserId(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehicles(){
        try {
            return vehicleRepository.getEntities().get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DeleteVehicle(Integer id) throws SQLException {
        vehicleRepository.deleteEntityById(id);
    }

    public void UpdateVehicle(Vehicle newVehicle) throws SQLException {
        vehicleRepository.updateEntity(newVehicle);
    }

    public void AddVehicle(Vehicle newVehicle) throws SQLException {
        vehicleRepository.createEntity(newVehicle);
    } 
}
