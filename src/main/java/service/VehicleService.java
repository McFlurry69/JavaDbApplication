package service;

import model.Vehicle;
import dao.VehicleRepository;
import sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class VehicleService {
    private VehicleRepository vehicleRepository;

    public VehicleService(){
        vehicleRepository = DependencyInjectionImitator.get_VehicleRepository();
    }

    public Vehicle getVehicle(Integer id){
        try {
            return vehicleRepository.getEntityByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByUser(Integer id){
        try {
            return vehicleRepository.getCarsByUserIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehicles(){
        try {
            return vehicleRepository.getEntitiesAsync().get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DeleteVehicle(Integer id) throws SQLException {
        vehicleRepository.deleteEntityByIdAsync(id);
    }

    public void UpdateVehicle(Vehicle newVehicle) throws SQLException {
        vehicleRepository.updateEntityAsync(newVehicle);
    }

    public void AddVehicle(Vehicle newVehicle) throws SQLException {
        vehicleRepository.createEntityAsync(newVehicle);
    } 
}
