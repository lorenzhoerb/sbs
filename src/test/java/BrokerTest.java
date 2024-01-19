import com.hoerb.exception.AccountNotFoundException;
import com.hoerb.exception.OrderPlacementException;
import com.hoerb.exception.SecurityNotFoundException;
import com.hoerb.model.Account;
import com.hoerb.model.Order;
import com.hoerb.model.OrderAction;
import com.hoerb.model.OrderType;
import com.hoerb.model.broker.Broker;
import com.hoerb.model.broker.IBroker;
import com.hoerb.model.securities.Security;
import com.hoerb.model.securities.Stock;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
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

    @Test
    void testAddSecurity_shouldAddSecurityAndReturnTrue_whenSecurityNotAlreadyExist() {
        final String SYMBOL = "NVDA";
        IBroker broker = new Broker();
        Security a = new Stock(SYMBOL, 10);

        assertAll(
                () -> assertTrue(broker.addSecurity(a)),
                () -> assertEquals(a, broker.getSecurity(SYMBOL))
        );
    }

    @Test
    void testAddSecurity_shouldAddSecurityAndReturnFalse_whenSecurityAlreadyExisted() {
        final String SYMBOL = "NVDA";
        IBroker broker = new Broker();
        Security a = new Stock(SYMBOL, 10);

        broker.addSecurity(a);

        assertAll(
                () -> assertFalse(broker.addSecurity(a)),
                () -> assertEquals(a, broker.getSecurity(SYMBOL))
        );
    }

    @Test
    void testGetSecurity_shouldGetSecurity_whenSecurityExists() throws Exception {
        final String SYMBOL = "NVDA";
        IBroker broker = new Broker();
        Security a = new Stock(SYMBOL, 10);

        broker.addSecurity(a);

        assertEquals(a, broker.getSecurity(a.getSymbol()));
    }

    @Test
    void testGetSecurity_shouldThrowSecurityNotFound_whenSecurityDoesNotExists() {
        IBroker broker = new Broker();
        assertThrows(SecurityNotFoundException.class, () -> broker.getSecurity("a1"));
    }

    @Test
    void testGetSecurities_shouldReturnNull_whenNoSecuritiesArePresent() {
        IBroker broker = new Broker();
        assertEquals(0, broker.getSecurities().size());
    }

    @Test
    void testGetSecurities_shouldReturnSecurities() {
        IBroker broker = new Broker();
        Security a = new Stock("a", 10);
        Security b = new Stock("b", 10);

        broker.addSecurity(a);
        broker.addSecurity(b);

        List<Security> securities = broker.getSecurities();

        assertAll(
                () -> assertNotNull(securities),
                () -> assertEquals(2, securities.size()),
                () -> assertTrue(securities.contains(a)),
                () -> assertTrue(securities.contains(b))
        );
    }

    @Test
    void testPlaceOrder_shouldThrowOrderPlacementException_whenOrderIsNull() {
        IBroker broker = new Broker();
        assertThrows(OrderPlacementException.class, () -> broker.placeOrder(null));
    }

    @Test
    void testPlaceOrder_shouldPlaceOrder_whenEverythingIsValid() throws Exception {
        IBroker broker = new Broker();
        Security s = new Stock("NVDA", 1);
        Account a = new Account("a");

        broker.addAccount(a);
        broker.addSecurity(s);

        Order o = new Order(a, s, OrderAction.SELL, OrderType.MARKET_ORDER, 1, 1);

        var orderPlaced = broker.placeOrder(o);

        assertAll(
                () -> assertNotNull(orderPlaced),
                () -> assertEquals(o, orderPlaced),
                () -> assertTrue(o.getPlacementTimestamp().isBefore(LocalDateTime.now()))
        );
    }
}
