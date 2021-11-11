package service;

import model.User;
import dao.UserRepository;
import org.apache.logging.log4j.Logger;
import sturtup.DependencyInjectionImitator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class UserService {
    private UserRepository userRepository;
    private final Logger logger;

    public UserService(){
        userRepository = DependencyInjectionImitator.get_UserRepository();
        logger = DependencyInjectionImitator.get_Logger();
    }

    public User getUser(Integer id){
        try {
            logger.info("getUser from service layer");
            return userRepository.getFullUserInfoByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.info("Error occurred while getUser"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public List<User> getUsers(){
        try {
            logger.info("getUsers from service layer");
            return userRepository.getFullUsersInfoAsync().get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.info("Error occurred while getUsers"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Integer deleteUser(Integer id) throws SQLException {
        try {
            logger.info("deleteUser from service layer");
            return userRepository.deleteEntityByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.info("Error occurred while deleteUser"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Integer updateUser(User newUser) throws SQLException {
        try {
            logger.info("updateUser from service layer");
            return userRepository.updateEntityAsync(newUser).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.info("Error occurred while updateUser"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public Integer addUser(User newUser) throws SQLException {
        try {
            logger.info("addUser from service layer");
            return userRepository.createEntityAsync(newUser).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.info("Error occurred while addUser"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
