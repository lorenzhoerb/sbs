import com.hoerb.exception.AccountNotFoundException;
import com.hoerb.model.Account;
import com.hoerb.model.broker.Broker;
import com.hoerb.model.broker.IBroker;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BrokerTest {
    @Test
    void testAddAccount_shouldAddAccountAndReturnTrue_whenAccountNotAlreadyExist() {
        IBroker broker = new Broker();
        Account a = new Account("a1");

        assertAll(
                () -> assertTrue(broker.addAccount(a)),
                () -> assertEquals(a, broker.getAccount(a.getAccountName()))
        );
    }

    @Test
    void testAddAccount_shouldAddAccountAndReturnFalse_whenAccountAlreadyExisted() {
        IBroker broker = new Broker();
        Account a = new Account("a1");
        broker.addAccount(a);
        assertAll(
                () -> assertFalse(broker.addAccount(a)),
                () -> assertEquals(a, broker.getAccount(a.getAccountName()))
        );
    }

    @Test
    void testGetAccount_shouldGetAccount_whenAccountExists() throws Exception {
        IBroker broker = new Broker();
        Account a = new Account("a1");
        broker.addAccount(a);

        assertEquals(a, broker.getAccount(a.getAccountName()));
    }

    @Test
    void testGetAccount_shouldThrowAccountNotFound_whenAccountDoesNotExists() {
        IBroker broker = new Broker();
        assertThrows(AccountNotFoundException.class, () -> broker.getAccount("a1"));
    }

    @Test
    void testGetAccounts_shouldReturnNull_whenNoAccountsArePresent() {
        IBroker broker = new Broker();
        assertEquals(0, broker.getAccounts().size());
    }

    @Test
    void testGetAccounts_shouldReturnAccounts() {
        IBroker broker = new Broker();
        Account a = new Account("a");
        Account b = new Account("b");

        broker.addAccount(a);
        broker.addAccount(b);

        List<Account> accounts = broker.getAccounts();

        assertAll(
                () -> assertNotNull(accounts),
                () -> assertEquals(2, accounts.size()),
                () -> assertTrue(accounts.contains(a)),
                () -> assertTrue(accounts.contains(b))
        );
    }

    @Test
    void testAddAccounts_shouldAddAccounts() {
        IBroker broker = new Broker();
        Account a = new Account("a");
        Account b = new Account("b");

        List<Boolean> returns = broker.addAccounts(List.of(a, b));

        var accounts = broker.getAccounts();

        assertAll(
                () -> assertEquals(2, accounts.size()),
                () -> assertTrue(accounts.contains(a)),
                () -> assertTrue(accounts.contains(b)),
                () -> assertTrue(accounts.contains(b)),
                () -> assertEquals(2, returns.size()),
                () -> assertTrue(returns.getFirst()),
                () -> assertTrue(returns.get(1))
        );
    }

    @Test
    void testAddAccounts_shouldAddAccounts_whenAccountAlreadyExists() {
        IBroker broker = new Broker();
        Account a = new Account("a");
        Account b = new Account("b");

        broker.addAccount(b); // account that already exists

        List<Boolean> returns = broker.addAccounts(List.of(a, b));

        var accounts = broker.getAccounts();

        assertAll(
                () -> assertEquals(2, accounts.size()),
                () -> assertTrue(accounts.contains(a)),
                () -> assertTrue(accounts.contains(b)),
                () -> assertTrue(accounts.contains(b)),
                () -> assertEquals(2, returns.size()),
                () -> assertTrue(returns.getFirst()),
                () -> assertFalse(returns.get(1))
        );
    }
}
