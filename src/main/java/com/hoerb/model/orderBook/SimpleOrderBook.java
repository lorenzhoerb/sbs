package com.hoerb.model.orderBook;

import com.hoerb.exception.SecurityNotFoundException;
import com.hoerb.exception.UnsupportedSecurityException;
import com.hoerb.model.Order;
import com.hoerb.model.securities.Security;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;

public class SimpleOrderBook implements IOrderBook {

    Logger logger = LogManager.getLogger();

    private final Map<String, Set<Order>> orders = new HashMap<>();
    private final Set<String> securitySymbols = new HashSet<>();

    @Override
    public boolean addOrder(Order order) {
        Security orderSecurity = order.getSecurity();
        Set<Order> securityOrderSet;

        if (orders.containsKey(orderSecurity.getSymbol())) {
            securityOrderSet = orders.get(orderSecurity.getSymbol());
        } else {
            logger.warn("Order {}, could not be added to order book. Security {} is not supported", order, orderSecurity.getSymbol());
            throw new UnsupportedSecurityException("Security with symbol " + orderSecurity.getSymbol() + " is not supported by the order book");
        }

        logger.info("Added order to order book");
        return securityOrderSet.add(order);
    }

    @Override
    public boolean addSecurity(String symbol) {
        if (!securitySymbols.contains(symbol)) {
            orders.put(symbol, new HashSet<>());
        }

        logger.info("Added security '{}' to order book", symbol);
        return securitySymbols.add(symbol);
    }

    @Override
    public List<Order> getAllOrders() {
        logger.trace("getAllOrders()");
        return orders.values().stream()
                .flatMap(Collection::stream)
                .toList();
    }

    @Override
    public List<Order> getAllOrders(String symbol) throws SecurityNotFoundException {
        logger.trace("getAllOrders({})", symbol);
        if (orders.containsKey(symbol)) {
            Set<Order> securityOrderSet = orders.get(symbol);
            return new ArrayList<>(securityOrderSet);
        }
        throw new SecurityNotFoundException("Security with symbol " + symbol + " does not exist in order book");
    }

    @Override
    public List<Order> getOpenOrders() {
        logger.trace("getOpenOrders()");
        return getAllOrders().stream()
                .filter(Order::isOpen)
                .toList();
    }

    @Override
    public List<Order> getOpenOrders(String symbol) throws SecurityNotFoundException {
        logger.trace("getOpenOrders({})", symbol);
        return getAllOrders(symbol).stream()
                .filter(Order::isOpen)
                .toList();
    }
}
