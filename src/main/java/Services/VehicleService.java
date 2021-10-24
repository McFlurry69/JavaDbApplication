package Services;

import Models.Vehicle;
import Repository.IVehicleRepository;
import Repository.IVehicleRepository;
import Sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class VehicleService {
    private final IVehicleRepository _vehicleRepository;

    public VehicleService(){
        _vehicleRepository = new DependencyInjectionImitator().get_VehicleRepository();
    }

    public Vehicle getVehicle(int id){
        try {
            return (Vehicle) _vehicleRepository.getEntityById(id).get(1, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByUser(int id){
        try {
            return (List<Vehicle>) _vehicleRepository.getCarsByUserId(id).get(1, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehicles(){
        try {
            return (List<Vehicle>) _vehicleRepository.getEntities().get(1, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DeleteVehicle(int id) {
        _vehicleRepository.deleteEntityById(id);
    }

    public void UpdateVehicle(Vehicle newVehicle){
        _vehicleRepository.updateEntity(newVehicle);
    }

    public void AddVehicle(Vehicle newVehicle){
        _vehicleRepository.createEntity(newVehicle);
    } 
}
