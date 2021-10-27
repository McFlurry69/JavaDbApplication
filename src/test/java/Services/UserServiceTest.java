package Services;

import DAO.IUserRepository;
import Models.PersonalInfo;
import Models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BaseTestCase {

    @BeforeEach
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
    }

}

class UserServiceTest extends BaseTestCase {

    public ArrayList<User> getDummies(){
        var perI1 = new PersonalInfo();
        perI1.setPhoneNumber("123");
        perI1.setAddress("Address2");
        perI1.setId(1);
        perI1.setEmail("Mail1");
        var perI2 = new PersonalInfo();
        perI1.setPhoneNumber("1234");
        perI1.setAddress("Address2");
        perI1.setId(2);
        perI1.setEmail("Mail2");



        var dummy1 = new User();
        dummy1.setName("Name");
        dummy1.setAge(1);
        dummy1.setId(1);
        dummy1.setPersonalInfo(perI1);
        dummy1.setPersonalInfoId(perI1.getId());

        var dummy2 = new User();
        dummy1.setName("Name2");
        dummy1.setAge(2);
        dummy1.setId(2);
        dummy1.setPersonalInfo(perI2);
        dummy1.setPersonalInfoId(perI2.getId());

        var listRes = new ArrayList<User>();
        listRes.add(dummy1);
        listRes.add(dummy2);

        return listRes;
    }

    @Mock IUserRepository userRepository;
    @InjectMocks UserService userService;

    @Test
    void getUser() {
        try {
            when(userRepository.getFullUserInfoById(getDummies().get(0).getId())).thenReturn(CompletableFuture.supplyAsync(()->getDummies().get(0)));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            assertEquals(userRepository.getFullUserInfoById(1), userService.getUser(getDummies().get(0).getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void getUsers() {
        try {
            when(userRepository.getFullUsersInfo()).thenReturn(CompletableFuture.supplyAsync(()->getDummies()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            assertEquals(userRepository.getFullUsersInfo().get(), userService.getUsers());
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void addUser() {
    }
}