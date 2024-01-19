import com.hoerb.exception.SecurityNotFoundException;
import com.hoerb.exception.UnsupportedSecurityException;
import com.hoerb.model.Order;
import com.hoerb.model.orderBook.IOrderBook;
import com.hoerb.model.orderBook.SimpleOrderBook;
import com.hoerb.model.securities.Security;
import com.hoerb.model.securities.Stock;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class OrderBookTest {

    private static Order order;
    private static Security security, security2;
    private static final String SYMBOL_1 = "NVDA", SYMBOL_2 = "TSLA";
    private IOrderBook orderBook;

    @BeforeAll
    public static void setUpClass() {
        security = new Stock(SYMBOL_1, 13);
        security2 = new Stock(SYMBOL_2, 13);
        order = new Order(null, security, null, 1, 23);
    }

    @BeforeEach
    public void setUp() {
        orderBook = new SimpleOrderBook();
    }

    @Test
    void testAddOrder_shouldAddOrderAndReturnTrue_whenOrderIsNotAlreadyInOrderBook() {
        orderBook.addSecurity(SYMBOL_1);
        var addOrderResult = orderBook.addOrder(order);
        var orders = orderBook.getAllOrders();
        assertAll(
                () -> assertTrue(addOrderResult),
                () -> assertEquals(1, orders.size()),
                () -> assertEquals(order, orders.getFirst())
        );
    }

    @Test
    void testAddOrder_shouldAddOrderAndReturnFalse_whenOrderInAlreadyInOrderBook() {
        orderBook.addSecurity(SYMBOL_1);
        orderBook.addOrder(order);
        var addOrderResult = orderBook.addOrder(order);
        var orders = orderBook.getAllOrders();
        assertAll(
                () -> assertFalse(addOrderResult),
                () -> assertEquals(1, orders.size()),
                () -> assertEquals(order, orders.getFirst())
        );
    }

    @Test
    void testAddOrder_shouldAddOrderInCorrectSecurityList_whenAddingToEmptySecurityList() throws Exception {
        orderBook.addSecurity(SYMBOL_1);
        var addOrderResult = orderBook.addOrder(order);
        var orders = orderBook.getAllOrders(security.getSymbol());
        assertAll(
                () -> assertTrue(addOrderResult),
                () -> assertEquals(1, orders.size()),
                () -> assertEquals(order, orders.getFirst())
        );
    }

    @Test
    void testAddOrder_shouldAddOrderInCorrectSecurityList_whenSecurityListExists() throws Exception {
        orderBook.addSecurity(SYMBOL_1);
        orderBook.addOrder(new Order(null, security, null, 1, 1));
        var addOrderResult = orderBook.addOrder(order);
        var orders = orderBook.getAllOrders(security.getSymbol());
        assertAll(
                () -> assertTrue(addOrderResult),
                () -> assertEquals(2, orders.size()),
                () -> assertTrue(orders.contains(order))
        );
    }

    @Test
    void testAddOrder_shouldThrowUnsupportedSecurityException_whenSecurityIsNotSupportedByOrderBook() {
        assertThrows(UnsupportedSecurityException.class, () -> orderBook.addOrder(order));
    }

    @Test
    void testGetAllOrders_shouldGetAllOrders_whenOrdersWithDifferentSecurities() {
        orderBook.addSecurity(SYMBOL_1);
        orderBook.addSecurity(SYMBOL_2);
        var order2 = new Order(null, security2, null, 1, 1);

        orderBook.addOrder(order);
        orderBook.addOrder(order2);

        var allOrders = orderBook.getAllOrders();
        assertAll(
                () -> assertNotNull(allOrders),
                () -> assertEquals(2, allOrders.size()),
                () -> assertTrue(allOrders.contains(order)),
                () -> assertTrue(allOrders.contains(order2))
        );
    }

    @Test
    void testGetAllOrders_shouldReturnEmptyList_whenNoOrdersExisting() {
        var allOrders = orderBook.getAllOrders();
        assertAll(
                () -> assertNotNull(allOrders),
                () -> assertEquals(0, allOrders.size())
        );
    }

    @Test
    void testGetAllOrdersOfSecurity_shouldOnlyReturnOrdersOfGivenSecurity() throws Exception {
        orderBook.addSecurity(SYMBOL_1);
        orderBook.addSecurity(SYMBOL_2);
        var order2 = new Order(null, security2, null, 1, 1);
        var order3 = new Order(null, security2, null, 1, 1);

        orderBook.addOrder(order);
        orderBook.addOrder(order2);
        orderBook.addOrder(order3);

        var ordersOfSecurity2 = orderBook.getAllOrders(security2.getSymbol());

        assertAll(
                () -> assertNotNull(ordersOfSecurity2),
                () -> assertEquals(2, ordersOfSecurity2.size()),
                () -> assertTrue(ordersOfSecurity2.contains(order2)),
                () -> assertTrue(ordersOfSecurity2.contains(order3)),
                () -> assertFalse(ordersOfSecurity2.contains(order))
        );
    }

    @Test
    void testGetAllOrdersOfSecurity_shouldThrowSecurityNotFoundException_whenSymbolNot() {
        assertThrows(SecurityNotFoundException.class, () -> orderBook.getAllOrders(SYMBOL_1));
    }

    @Test
    void testGetAllOrdersOfSecurity_shouldReturnEmptyList_whenNoOrdersForSecurityInOrderBook() throws Exception {
        orderBook.addSecurity(SYMBOL_1);
        orderBook.addSecurity(SYMBOL_2);
        orderBook.addOrder(new Order(null, security2, null, 1, 1));

        var resultOrdersOfSecurity1 = orderBook.getAllOrders(SYMBOL_1);

        assertAll(
                () -> assertNotNull(resultOrdersOfSecurity1),
                () -> assertEquals(0, resultOrdersOfSecurity1.size())
        );
    }

    @Test
    void testAddSecurity_shouldAddSecurityAndReturnTrue_whenSecurityNotAddedBefore() {
        assertTrue(orderBook.addSecurity(SYMBOL_1));
    }

    @Test
    void testAddSecurity_shouldAddSecurityAndReturnTrue_whenSecurityAddedBefore() {
        orderBook.addSecurity(SYMBOL_1);
        assertFalse(orderBook.addSecurity(SYMBOL_1));
    }

    @Test
    void testGetOpenOrders_shouldReturnOpenOrders() {
        Order s1Open = spy(order);
        Order o1S1 = new Order(null, security, null, 1, 1);
        Order o2s2 = new Order(null, security2, null, 1, 1);

        Order s1Closed = spy(o1S1);
        Order s2Open = spy(o2s2);

        when(s1Open.isOpen()).thenReturn(true);
        when(s1Closed.isOpen()).thenReturn(false);
        when(s2Open.isOpen()).thenReturn(true);

        orderBook.addSecurity(SYMBOL_1);
        orderBook.addSecurity(SYMBOL_2);

        orderBook.addOrder(s1Open);
        orderBook.addOrder(s1Closed);
        orderBook.addOrder(s2Open);

        var results = orderBook.getOpenOrders();

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(2, results.size()),
                () -> assertTrue(results.contains(s1Open)),
                () -> assertTrue(results.contains(s2Open))
        );
    }

    @Test
    void testGetOpenOrdersOfSecurity_shouldReturnOpenOrders() throws Exception {
        Order s1Open = spy(order);
        Order o1S1 = new Order(null, security, null, 1, 1);
        Order o2s2 = new Order(null, security2, null, 1, 1);

        Order s1Closed = spy(o1S1);
        Order s2Open = spy(o2s2);

        when(s1Open.isOpen()).thenReturn(true);
        when(s1Closed.isOpen()).thenReturn(false);
        when(s2Open.isOpen()).thenReturn(true);

        orderBook.addSecurity(SYMBOL_1);
        orderBook.addSecurity(SYMBOL_2);

        orderBook.addOrder(s1Open);
        orderBook.addOrder(s1Closed);
        orderBook.addOrder(s2Open);

        var results = orderBook.getOpenOrders(SYMBOL_1);

        assertAll(
                () -> assertNotNull(results),
                () -> assertEquals(1, results.size()),
                () -> assertTrue(results.contains(s1Open))
        );
    }

    @Test
    void testGetOpenOrdersOfSecurity_throwSecurityNotFoundException_whenSecurityNotAddedToOrderBook() {
        assertThrows(SecurityNotFoundException.class, () -> orderBook.getOpenOrders(SYMBOL_1));
    }
}
