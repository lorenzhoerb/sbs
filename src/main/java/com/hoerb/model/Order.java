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

    private final OrderAction orderAction;
    private final int quantity;
    private final double price;

    private final LocalDateTime creationTimestamp;
    private LocalDateTime placementTimestamp;
    private LocalDateTime executionTimestamp;
    private LocalDateTime expireTimestamp;
    private boolean executed = false;

    public Order(Account account, Security security, OrderAction orderAction, OrderType orderType, int quantity, double price) {
        this.account = account;
        this.security = security;
        this.orderAction = orderAction;
        this.orderType = orderType;
        this.quantity = quantity;
        this.price = price;
        creationTimestamp = LocalDateTime.now();
    }

    public boolean isOpen() {
        return !executed && LocalDateTime.now().isBefore(expireTimestamp);
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

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public LocalDateTime getOrderExecuted() {
        return executionTimestamp;
    }

    public boolean isExecuted() {
        return executed;
    }

    public LocalDateTime getPlacementTimestamp() {
        return placementTimestamp;
    }

    public void setPlacementTimestamp(LocalDateTime placementTimestamp) {
        this.placementTimestamp = placementTimestamp;
    }

    public LocalDateTime getExecutionTimestamp() {
        return executionTimestamp;
    }

    public void setExecutionTimestamp(LocalDateTime executionTimestamp) {
        this.executionTimestamp = executionTimestamp;
    }

    public LocalDateTime getExpireTimestamp() {
        return expireTimestamp;
    }

    public void setExpireTimestamp(LocalDateTime expireTimestamp) {
        this.expireTimestamp = expireTimestamp;
    }

    public void setExecuted(boolean executed) {
        this.executed = executed;
    }

    public OrderAction getOrderAction() {
        return orderAction;
    }
}
