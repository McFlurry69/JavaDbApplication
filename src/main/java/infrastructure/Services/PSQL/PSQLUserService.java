package infrastructure.Services.PSQL;

import domain.User;
import application.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import utils.DependencyInjectionImitator;
import utils.ServiceAllowedOperation;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PSQLUserService {
    private final UserRepository userRepository;
    private final Logger logger = LogManager.getLogger(this.getClass());

    public PSQLUserService(){
        userRepository = DependencyInjectionImitator.getUserRepository();
    }

    public PSQLUserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Get User by id", AllowedOperationDescription = "Provide user id to get available info about user")
    public User getUserById(Integer id){
        try {
            logger.info("getUser from service layer");
            return userRepository.getFullUserInfoByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getUser", e);
        }
        return null;
    }

    //TODO Implement method
    @ServiceAllowedOperation(AllowedOperationName = "Get User by name", AllowedOperationDescription = "Provide user name (first name, second name or both) to get available info about user")
    public List<User> getUserByFullName(String name){
        try {
            logger.info("getUser from service layer by name");
            return userRepository.getFullUserInfoByNameAsync(name).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getUser", e);
        }
        return null;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Get all Users", AllowedOperationDescription = "get all available info about users")
    public List<User> getUsers(){
        try {
            logger.info("getUsers from service layer");
            return userRepository.getFullUsersInfoAsync().get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while getUsers", e);
        }
        return null;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Delete User by id", AllowedOperationDescription = "provide id to delete user")
    public Integer deleteUser(Integer id) throws SQLException {
        try {
            logger.info("deleteUser from service layer");
            return userRepository.deleteEntityByIdAsync(id).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while deleteUser", e);
        }
        return null;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Update User by id", AllowedOperationDescription = "provide id to update user")
    public Integer updateUser(User newUser) throws SQLException {
        try {
            logger.info("updateUser from service layer");
            return userRepository.updateEntityAsync(newUser).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while updateUser", e);
        }
        return null;
    }

    @ServiceAllowedOperation(AllowedOperationName = "Add new User", AllowedOperationDescription = "provide full info about user to add")
    public Integer addUser(User newUser) throws SQLException {
        try {
            logger.info("addUser from service layer");
            return userRepository.createEntityAsync(newUser).get(1, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("Error occurred while addUser", e);
        }
        return null;
    }
}
