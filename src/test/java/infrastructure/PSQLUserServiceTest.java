package infrastructure;

import application.UserRepository;
import domain.PersonalInfo;
import domain.User;
import infrastructure.Services.PSQL.PSQLUserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class PSQLUserServiceTest {/*
    private static Random random = new Random();

    @Before
    void init_mocks() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    PSQLUserService us;
    @Mock UserRepository userRepository;

    @Test
    void getUser_IsNotNull() throws SQLException {
        User testUser = CreateTestEntity();
        when(userRepository.getFullUserInfoByIdAsync(anyInt())).thenReturn(CompletableFuture.supplyAsync(()->testUser));

        assertEquals(testUser, us.getUser(anyInt()));
    }

    @Test
    void getUser_ReturnsNullWithoutException() throws SQLException {
        when(userRepository.getFullUserInfoByIdAsync(anyInt())).thenReturn(CompletableFuture.supplyAsync(()->null));

        assertEquals(null, us.getUser(anyInt()));
    }

    //us.getUser(1); verify(userRepository, times(1)).getFullUserInfoByIdAsync(1);

    @Test
    void getUsers_ReturnsListOfUsers() throws SQLException {
        User testUser1 = CreateTestEntity();
        User testUser2 = CreateTestEntity();
        User testUser3 = CreateTestEntity();
        List<User> userList = Arrays.asList(testUser1, testUser2, testUser3);
        when(userRepository.getFullUsersInfoAsync()).thenReturn(CompletableFuture.supplyAsync(()->userList));

        assertEquals(userList, us.getUsers());
    }

    @Test
    void deleteUser() throws SQLException {
        User testUser1 = CreateTestEntity();
        User testUser2 = CreateTestEntity();
        User testUser3 = CreateTestEntity();
        List<User> userList = Arrays.asList(testUser1, testUser2, testUser3);
        when(userRepository.deleteEntityByIdAsync(testUser1.getId())).thenReturn(CompletableFuture.supplyAsync(()->testUser1.getId()));

        assertEquals(testUser1.getId(), us.deleteUser(testUser1.getId()));
    }

    @Test
    void updateUser() {
    }

    @Test
    void addUser() {
    }

    private static User CreateTestEntity(){
        User newUser = new User();
        PersonalInfo pi = new PersonalInfo();
        pi.setId(random.nextInt());
        pi.setAddress("Address");
        pi.setEmail("Email");
        pi.setPhoneNumber("Phone");
        newUser.setName("te");
        newUser.setId(random.nextInt());
        newUser.setAge(random.nextInt());
        newUser.setPersonalInfoId(pi.getId());
        newUser.setPersonalInfo(pi);

        return newUser;
    }*/
}