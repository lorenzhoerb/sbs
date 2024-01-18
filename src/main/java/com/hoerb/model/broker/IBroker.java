package com.hoerb.model.broker;

import com.hoerb.exception.AccountNotFoundException;
import com.hoerb.exception.OrderPlacementException;
import com.hoerb.exception.SecurityNotFoundException;
import com.hoerb.model.*;
import com.hoerb.model.securities.Security;

import java.util.List;

/**
 * The {@code IBroker} class represents a generic broker that manages securities, user accounts, and facilitates the placement and execution of trades.
 * Implementing classes should provide specific functionality for interacting with a particular brokerage system.
 */
public interface IBroker {

    /**
     * Places the given order with the broker.
     *
     * @param order The order to be placed.
     * @return The order after being placed, including any updates or additional information provided by the broker.
     * @throws OrderPlacementException If the order placement fails.
     */
    Order placeOrder(Order order) throws OrderPlacementException;

    /**
     * Places a new order with the broker using the specified parameters.
     *
     * @param account     The account associated with the order.
     * @param orderAction The action to be performed (e.g., Buy or Sell).
     * @param orderType   The type of the order (e.g., Market or Limit).
     * @param quantity    The quantity of the asset to be bought or sold.
     * @param price       The price at which the asset should be bought or sold (applicable for Limit orders).
     * @return The order after being placed, including any updates or additional information provided by the broker.
     * @throws OrderPlacementException If the order placement fails.
     */
    Order placeOrder(Account account, OrderAction orderAction, OrderType orderType, int quantity, double price) throws OrderPlacementException;

    /**
     * Retrieves a list of all active orders within the broker.
     *
     * @return A list of active orders placed with the broker.
     */
    List<Order> getOrders();

    /**
     * Retrieves a list of all user accounts managed by the broker.
     *
     * @return A list of user accounts associated with the broker.
     */
    List<Account> getAccounts();

    /**
     * Adds a new user account to the broker.
     *
     * @param account The user account to be added.
     * @return {@code true} if the account is successfully added; {@code false} if the account already exists.
     */
    boolean addAccount(Account account);

    /**
     * Adds a list of user accounts to the broker.
     *
     * @param accounts The list of user accounts to be added.
     * @return A list of booleans indicating the success status for each account addition.
     * Each element in the list corresponds to the success status of adding the corresponding account
     * in the input list. The value {@code true} indicates successful addition, and {@code false} indicates
     * that the account already exists.
     */
    List<Boolean> addAccounts(List<Account> accounts);

    /**
     * Retrieves the {@link Account} associated with the specified account name.
     * <p>
     * This method searches for an account based on the provided {@code accountName}.
     *
     * @param accountName The name of the account to retrieve.
     * @return The {@code Account} object associated with the specified account name.
     * @throws AccountNotFoundException If no account is found with the given name.
     */
    Account getAccount(String accountName) throws AccountNotFoundException;

    /**
     * Retrieves a list of all securities available within the broker.
     *
     * @return A list of securities managed by the broker.
     */
    List<Security> getSecurities();

    /**
     * Adds a new security to the broker.
     *
     * @param security The security to be added.
     * @return {@code true} if the security is successfully added; {@code false} if the security already exists.
     */
    boolean addSecurity(Security security);

    /**
     * Retrieves the security with the specified symbol from the broker.
     *
     * @param symbol The symbol identifying the security.
     * @return The security with the specified symbol.
     * @throws SecurityNotFoundException If the security with the specified symbol is not found.
     */
    Security getSecurity(String symbol) throws SecurityNotFoundException;

    /**
     * Retrieves the market price for the security with the specified symbol from the broker.
     *
     * @param symbol The symbol identifying the security.
     * @return The market price of the security.
     * @throws SecurityNotFoundException If the security with the specified symbol is not found.
     */
    MarketPrice getMarketPrice(String symbol) throws SecurityNotFoundException;
}
