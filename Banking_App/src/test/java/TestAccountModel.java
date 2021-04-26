import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import com.revature.exceptions.InsufficientFundsException;
import com.revature.exceptions.InvalidAccountInputException;
import com.revature.models.Account;
import org.junit.Before;
import org.junit.Test;

public class TestAccountModel {

    private static Account account;

    @Before
    public void setUp() {
        List<Account> accountsList = new ArrayList<Account>();

        accountsList.add(new Account(2, 200.0, 1, 1));

        account = new Account(7, 560.45, 1, 2);
    }

    @Test
    public void testWithdraw() {
        double withdrawAmount = 60.45;

        try {
            account.withdraw(withdrawAmount);
            assertEquals(500.00, account.getAccountBalance(), 0.001);
        } catch (InsufficientFundsException | InvalidAccountInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDeposit() {
        double depositAmount = 39.55;

        try {
            account.deposit(depositAmount);
            assertEquals(600.0, account.getAccountBalance(), 0.001);
        } catch (InvalidAccountInputException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testRetrieveAccountBalance() {
        assertEquals(560.45, account.getAccountBalance(), 0.001);
    }
}
