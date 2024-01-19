package com.hoerb.model;

import com.hoerb.model.securities.Security;

import java.util.*;

/**
 * The {@code Account} class represents a user account within a financial system.
 * Each account has a unique name and may be associated with a depot.
 */
public class Account {
    private final String accountName;
    private final Depot depot;
    private final Set<Order> orderHistory = new HashSet<>();
    private final Map<Security, Integer> securityHoldings = new HashMap<>();

    public Account(String accountName) {
        this.accountName = accountName;
        depot = new Depot(0);
    }

    public Account(String accountName, double balance) {
        this.accountName = accountName;
        depot = new Depot(balance);
    }

    public String getAccountName() {
        return accountName;
    }

    public boolean addOrder(Order order) {
        return orderHistory.add(order);
    }

    public Depot getDepot() {
        return depot;
    }

    public List<Order> getOrderHistory() {
        return orderHistory.stream().toList();
    }

    public Map<Security, Integer> getSecurityHoldings() {
        return securityHoldings;
    }
}
