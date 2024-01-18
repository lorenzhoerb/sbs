package com.hoerb.model;

import com.hoerb.model.securities.Security;

import java.time.LocalDateTime;

/**
 * The {@code Order} class represents a financial order placed
 * by a user for a specific security within a trading system.
 */
public class Order {
    private final Account account;
    private final Security security;
    private final OrderType orderType;
    private final int quantity;
    private final double price;

    private final LocalDateTime orderCreated;
    private LocalDateTime orderExecuted;
    private boolean executed = false;

    public Order(Account account, Security security, OrderType orderType, int quantity, double price) {
        this.account = account;
        this.security = security;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
        orderCreated = LocalDateTime.now();
    }

    public Account getAccount() {
        return account;
    }

    public Security getSecurity() {
        return security;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }

    public LocalDateTime getOrderCreated() {
        return orderCreated;
    }

    public LocalDateTime getOrderExecuted() {
        return orderExecuted;
    }

    public boolean isExecuted() {
        return executed;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }
}
