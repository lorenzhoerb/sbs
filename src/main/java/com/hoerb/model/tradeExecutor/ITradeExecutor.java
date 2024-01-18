package com.hoerb.model.tradeExecutor;

import com.hoerb.model.Order;

/**
 * Interface for a trade executor responsible for executing trades based on orders.
 */
public interface ITradeExecutor {

    /**
     * Executes all pending trades in the trade executor.
     */
    void executeTrades();

    /**
     * Executes a specific trade based on the provided order.
     *
     * @param order The order to be executed.
     * @return {@code true} if the trade was successfully executed, {@code false} otherwise.
     */
    boolean executeTrade(Order order);
}
