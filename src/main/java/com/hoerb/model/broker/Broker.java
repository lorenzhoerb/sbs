package com.hoerb.model.broker;

import com.hoerb.exception.AccountNotFoundException;
import com.hoerb.exception.OrderPlacementException;
import com.hoerb.exception.SecurityNotFoundException;
import com.hoerb.model.*;
import com.hoerb.model.securities.Security;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Broker implements IBroker {

    private static final Logger logger = LogManager.getLogger();
    private final Map<String, Account> accounts = new HashMap<>();
    private final Map<String, Security> securities = new HashMap<>();

    @Override
    public Order placeOrder(Order order) throws OrderPlacementException {
        return null;
    }

    @Override
    public Order placeOrder(Account account, OrderAction orderAction, OrderType orderType, int quantity, double price) throws OrderPlacementException {
        return null;
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
}
