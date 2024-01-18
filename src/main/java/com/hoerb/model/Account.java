package com.hoerb.model;

import com.hoerb.model.securities.Security;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The {@code Account} class represents a user account within a financial system.
 * Each account has a unique name and may be associated with a depot.
 */
public class Account {
    private final String accountName;
    private final Depot depot;
    private final List<Order> orderHistory = new ArrayList<>();
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

    public Depot getDepot() {
        return depot;
    }

    public List<Order> getOrderHistory() {
        return orderHistory;
    }

    public Map<Security, Integer> getSecurityHoldings() {
        return securityHoldings;
    }
}
