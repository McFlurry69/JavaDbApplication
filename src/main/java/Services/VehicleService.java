package Services;

import Models.Vehicle;
import DAO.IVehicleRepository;
import Sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class VehicleService {
    private final IVehicleRepository _vehicleRepository;

    public VehicleService(){
        _vehicleRepository = DependencyInjectionImitator.get_VehicleRepository();
    }

    public Vehicle getVehicle(Integer id){
        try {
            return _vehicleRepository.getEntityById(id).get(1, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehiclesByUser(Integer id){
        try {
            return _vehicleRepository.getCarsByUserId(id).get(1, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Vehicle> getVehicles(){
        try {
            return _vehicleRepository.getEntities().get(1, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DeleteVehicle(Integer id) throws SQLException {
        _vehicleRepository.deleteEntityById(id);
    }

    public void UpdateVehicle(Vehicle newVehicle) throws SQLException {
        _vehicleRepository.updateEntity(newVehicle);
    }

    public void AddVehicle(Vehicle newVehicle) throws SQLException {
        _vehicleRepository.createEntity(newVehicle);
    } 
}
