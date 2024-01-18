package com.hoerb.model;

import com.hoerb.exception.InsufficientFundsException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * The {@code Depot} class represents a financial depot associated with a user account.
 * It holds a balance and provides methods for withdrawing, depositing, and checking sufficient funds.
 */
public class Depot {
    private static final Logger logger = LogManager.getLogger();
    private double balance;

    public Depot(double balance) {
        this.balance = balance;
        logger.info("Depot created with initial balance: €{}", balance);
    }


    /**
     * Withdraws the specified amount from the depot.
     *
     * @throws InsufficientFundsException If the withdrawal cannot be completed due to insufficient funds.
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        double newBalance = balance - amount;
        if (newBalance < 0) {
            String errorMessage = "Withdrawal failed: Insufficient funds. Your current balance is €" + balance;
            logger.error(errorMessage);
            throw new InsufficientFundsException(errorMessage);
        }
        balance = newBalance;
        logger.info("Withdrawal successful. Amount withdrawn: €{}, New balance: €{}", amount, balance);

    }

    /**
     * Deposits the specified amount into the depot.
     *
     * @param amount The amount to be deposited.
     */
    public void deposit(double amount) {
        balance += amount;
        logger.info("Deposit successful. Amount deposited: €{}, New balance: €{}", amount, balance);
    }

    /**
     * Checks if the depot has sufficient funds for a specified amount.
     *
     * @param amount The amount for which to check sufficiency.
     * @return {@code true} if the depot has sufficient funds, {@code false} otherwise.
     */
    public boolean hasSufficientFunds(double amount) {
        boolean sufficientFunds = balance - amount >= 0;
        logger.debug("Checking if depot has sufficient funds for €{}: {}", amount, sufficientFunds);
        return sufficientFunds;
    }

    public double getBalance() {
        logger.debug("Balance requested: €{}", balance);
        return balance;
    }
}
