package service;

import model.User;
import dao.UserRepository;
import sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserService {
    private final UserRepository userRepository;

    public UserService(){
        userRepository = DependencyInjectionImitator.get_UserRepository();
    }

    public User getUser(Integer id){
        try {
            return userRepository.getFullUserInfoById(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers(){
        try {
            return userRepository.getFullUsersInfo().get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void DeleteUser(Integer id) throws SQLException {
        userRepository.deleteEntityById(id);
    }

    public void UpdateUser(User newUser) throws SQLException {
        userRepository.updateEntity(newUser);
    }

    public void AddUser(User newUser) throws SQLException {
        userRepository.createEntity(newUser);
    }
}
