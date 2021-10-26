package Services;

import DAO.IUserRepository;
import Models.User;
import org.mockito.Mockito;
import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class UserServiceTest {

    @Test
    public void testGetUser() {
        IUserRepository userRepo = Mockito.mock(IUserRepository.class);
    }

    @Test
    public void testGetUsers() {
    }

    @Test
    public void testDeleteUser() {
    }

    @Test
    public void testUpdateUser() {
    }

    @Test
    public void testAddUser() {
    }
}