package service;

import model.User;
import dao.UserRepository;
import sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserService {
    private UserRepository userRepository;

    public UserService(){
        userRepository = DependencyInjectionImitator.get_UserRepository();
    }

    public User getUser(Integer id){
        try {
            return userRepository.getFullUserInfoByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers(){
        try {
            return userRepository.getFullUsersInfoAsync().get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer deleteUser(Integer id) throws SQLException {
        try {
            return userRepository.deleteEntityByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer updateUser(User newUser) throws SQLException {
        try {
            return userRepository.updateEntityAsync(newUser).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer addUser(User newUser) throws SQLException {
        try {
            return userRepository.createEntityAsync(newUser).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
