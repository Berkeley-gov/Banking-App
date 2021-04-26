import com.revature.dao.*;
import com.revature.models.Account;
import com.revature.models.Role;
import com.revature.models.User;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestDaoImplementation {

    AccountDao accountDao = new AccountDaoImplementation();
    UserDao userDao = new UserDaoImplementation();
    AdminDao adminDao = new AdminDaoImplementation();
    Account account;
    User user;
    Role role;

    @Before
    public void setUp() {

        role = new Role(3, "Employee");
        user = new User("James", "james12", "James", "Jacobson", "jacobson12@gmail.com", role);

        account = new Account(16, 500, 1, 1);

    }

    @Test
    public void insertUserTest() {
        assertEquals(1, userDao.insert(user));
    }

    @Test
    public void checkBalance() {
        assertEquals(500.0, accountDao.checkBalance(account.getUserId()), 0.001);
    }

}
