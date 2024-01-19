package com.hoerb.model.broker;

import com.hoerb.exception.AccountNotFoundException;
import com.hoerb.exception.OrderPlacementException;
import com.hoerb.exception.SecurityNotFoundException;
import com.hoerb.model.*;
import com.hoerb.model.orderBook.IOrderBook;
import com.hoerb.model.orderBook.SimpleOrderBook;
import com.hoerb.model.securities.Security;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker implements IBroker {

    private static final Logger logger = LogManager.getLogger();
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, Security> securities = new HashMap<>();
    private final IOrderBook orderBook = new SimpleOrderBook();

    @Override
    public Order placeOrder(Order order) throws OrderPlacementException {
        validateOrderPlacement(order);

        order.setPlacementTimestamp(LocalDateTime.now());
        order.setExecuted(false);

        boolean orderAddedSuccessfully = orderBook.addOrder(order);

        // manage references to security and account
        if (orderAddedSuccessfully) {
            order.getAccount().addOrder(order);
            order.getSecurity().addOrder(order);
        }
        return order;
    }

    @Override
    public Order placeOrder(String securitySymbol, Account account, OrderAction orderAction, OrderType orderType, int quantity, double price) throws OrderPlacementException {
        if (!securities.containsKey(securitySymbol)) {
            throw new OrderPlacementException("Order validation error: The security '" +
                    securitySymbol + "' is not tradable with this broker");
        }

        Security orderSecurity = securities.get(securitySymbol);
        return placeOrder(new Order(
                account,
                orderSecurity,
                orderAction,
                orderType,
                quantity,
                price
        ));
    }

    @Override
    public List<Order> getOrders() {
        return null;
    }

    @Override
    public List<Account> getAccounts() {
        return accounts.values().stream().toList();
    }

    @Override
    public boolean addAccount(Account account) {
        if (accounts.containsKey(account.getAccountName())) {
            logger.warn("Account with name '{}' already exists. Unable to add the account.", account.getAccountName());
            return false;
        }
        accounts.put(account.getAccountName(), account);
        logger.info("Adding new account: {}", account.getAccountName());
        return true;
    }

    @Override
    public List<Boolean> addAccounts(List<Account> accounts) {
        return accounts
                .stream()
                .map(this::addAccount)
                .toList();
    }

    @Override
    public Account getAccount(String accountName) throws AccountNotFoundException {
        if (!accounts.containsKey(accountName)) {
            throw new AccountNotFoundException();
        }
        return accounts.get(accountName);
    }

    @Override
    public List<Security> getSecurities() {
        return securities.values().stream().toList();
    }

    @Override
    public boolean addSecurity(Security security) {
        logger.info("Adding new security: {}", security.getSymbol());
        if (securities.containsKey(security.getSymbol())) {
            return false;
        }
        securities.put(security.getSymbol(), security);
        orderBook.addSecurity(security.getSymbol()); // Add supporter security to order book
        return true;
    }

    @Override
    public Security getSecurity(String symbol) throws SecurityNotFoundException {
        if (!securities.containsKey(symbol)) {
            throw new SecurityNotFoundException();
        }
        return securities.get(symbol);
    }

    @Override
    public MarketPrice getMarketPrice(String symbol) throws SecurityNotFoundException {
        return null;
    }

    private void validateOrderPlacement(Order order) throws OrderPlacementException {
        if (order == null) {
            throw new OrderPlacementException("Order validation error: Order cannot be null");
        }

        validateAccount(order);
        validateSecurity(order);
        validateQuantity(order);
        validatePrice(order);
        validateExpireTimestamp(order);
    }

    private void validateAccount(Order order) throws OrderPlacementException {
        Account orderAccount = order.getAccount();

        if (orderAccount == null) {
            throw new OrderPlacementException("Order validation error: An order must be associated with a valid account");
        }

        if (!accounts.containsKey(orderAccount.getAccountName())) {
            throw new OrderPlacementException("Order validation error: The specified account is not managed by the broker");
        }
    }

    private void validateSecurity(Order order) throws OrderPlacementException {
        Security orderSecurity = order.getSecurity();

        if (orderSecurity == null) {
            throw new OrderPlacementException("Order validation error: An order must have a valid security");
        }

        if (!securities.containsKey(orderSecurity.getSymbol())) {
            throw new OrderPlacementException("Order validation error: The security '" +
                    orderSecurity.getSymbol() + "' is not tradable with this broker");
        }
    }

    private void validateQuantity(Order order) throws OrderPlacementException {
        if (order.getQuantity() < 1) {
            throw new OrderPlacementException("Order validation error: Tradable quantity must be at least 1");
        }
    }

    private void validatePrice(Order order) throws OrderPlacementException {
        if (order.getPrice() < 0) {
            throw new OrderPlacementException("Order validation error: The price must be a positive value");
        }
    }

    private void validateExpireTimestamp(Order order) throws OrderPlacementException {
        if (order.getExpireTimestamp() != null && LocalDateTime.now().isAfter(order.getExpireTimestamp())) {
            throw new OrderPlacementException("Order expiration date must be in the future");
        }
    }
}
