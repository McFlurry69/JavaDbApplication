package Services;

import Models.User;
import Repository.IUserRepository;
import Sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class UserService {
    private final IUserRepository _userRepository;

    public UserService(){
        _userRepository = new DependencyInjectionImitator().get_UserRepository();
    }

    public User getUser(Integer id){
        try {
            return (User) _userRepository.getFullUserInfoById(id).get(1, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers(){
        try {
            return (List<User>) _userRepository.getFullUsersInfo().get(1, TimeUnit.SECONDS);
        } catch (SQLException | InterruptedException | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DeleteUser(Integer id) throws SQLException {
        _userRepository.deleteEntityById(id);
    }

    public void UpdateUser(User newUser) throws SQLException {
        _userRepository.updateEntity(newUser);
    }

    public void AddUser(User newUser) throws SQLException {
        _userRepository.createEntity(newUser);
    }
}
