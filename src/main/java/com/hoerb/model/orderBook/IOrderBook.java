package com.hoerb.model.orderBook;

import com.hoerb.exception.SecurityNotFoundException;
import com.hoerb.model.Order;

import java.util.List;

/**
 * Interface for representing an order book that manages orders for various financial securities.
 */
public interface IOrderBook {

    /**
     * Adds an order to the order book.
     *
     * @param order The order to be added.
     * @return {@code true} if the order was successfully added, {@code false} if the order already exists.
     */
    boolean addOrder(Order order);

    /**
     * Retrieves a list of all orders in the order book.
     *
     * @return A list containing all orders in the order book.
     */
    List<Order> getAllOrders();

    /**
     * Retrieves a list of all orders for a specific financial security in the order book.
     *
     * @param symbol The symbol of the financial security.
     * @return A list containing all orders for the specified security.
     * @throws SecurityNotFoundException If the specified security is not found in the order book.
     */
    List<Order> getAllOrders(String symbol) throws SecurityNotFoundException;

    /**
     * Retrieves a list of open orders in the order book.
     *
     * @return A list containing all open orders in the order book.
     */
    List<Order> getOpenOrders();

    /**
     * Retrieves a list of open orders for a specific financial security in the order book.
     *
     * @param symbol The symbol of the financial security.
     * @return A list containing all open orders for the specified security.
     * @throws SecurityNotFoundException If the specified security is not found in the order book.
     */
    List<Order> getOpenOrders(String symbol) throws SecurityNotFoundException;
}
