package com.hoerb.model.securities;

import com.hoerb.model.Order;
import com.hoerb.model.PriceEntry;
import com.hoerb.model.orderBook.IOrderBook;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * The {@code Security} class is an abstract base class representing a
 * financial security within a trading system.
 * <p>
 * This class provides a basic structure for implementing various financial
 * securities. It includes methods for retrieving information about the security,
 * such as symbol, current price, price history, and order-related details.
 * <p>
 * Concrete subclasses are expected to implement specific behaviors related to
 * their respective financial instruments.
 */
public abstract class Security {
    private double price;
    private final String symbol;
    private final List<PriceEntry> priceHistory = new ArrayList<>();

    private final IOrderBook orderBook;

    public Security(String symbol, double initialPrice) {
        this.symbol = symbol;
        this.price = initialPrice;
        orderBook = null;
    }

    public Security(String symbol, double initialPrice, IOrderBook orderBook) {
        this.symbol = symbol;
        this.price = initialPrice;
        this.orderBook = orderBook;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPrice() {
        return price;
    }

    public List<PriceEntry> getPriceHistory() {
        return priceHistory;
    }

    /**
     * Gets a list of all orders related to the security.
     *
     * @return A list of all orders.
     */
    public List<Order> getAllOrders() {
        //TODO: Implement
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Gets a list of open orders for the security.
     *
     * @return A list of open orders.
     */
    public List<Order> getOpenOrders() {
        //TODO: Implement
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Gets a list of bid prices for the financial security.
     *
     * @return A list of bid prices, representing the prices at which buyers are willing
     * to purchase the financial security.
     *
     * <p>The list may be empty if no bid prices are available.
     */
    public List<Double> getBidPrices() {
        //TODO: implement
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Gets the current bid price for the financial security.
     *
     * @return An {@code Optional} containing the current bid price if available,
     * or an empty {@code Optional} if the bid price is not set.
     *
     * <p>The bid price represents the highest price at which buyers are willing to purchase
     * the financial security. If the bid price is not available, the {@code Optional}
     * will be empty.
     */
    public Optional<Double> getBid() {
        //TODO: implement
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Gets a list of ask prices for the financial security.
     *
     * @return A list of ask prices, representing the prices at which sellers are willing
     * to sell the financial security.
     *
     * <p>The list may be empty if no ask prices are available.
     */
    public List<Double> getAskPrices() {
        //TODO: implement
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Gets the current ask price for the financial security.
     *
     * @return An {@code Optional} containing the current ask price if available,
     * or an empty {@code Optional} if the ask price is not set.
     *
     * <p>The ask price represents the lowest price at which sellers are willing to sell
     * the financial security. If the ask price is not available, the {@code Optional}
     * will be empty.
     */
    public Optional<Double> getAsk() {
        //TODO: implement
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    /**
     * Gets the spread for the financial security.
     *
     * <p>The spread represents the difference between the current ask price
     * (lowest price at which sellers are willing to sell) and the current bid price
     * (highest price at which buyers are willing to purchase) for the financial security.
     *
     * @return An {@code Optional} containing the spread if both bid and ask prices are available,
     * or an empty {@code Optional} if either the bid or ask price is not set.
     *
     * <p>If either bid or ask price is not available, the {@code Optional} will be empty.
     */
    public Optional<Double> getSpread() {
        //TODO: implement
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
}
