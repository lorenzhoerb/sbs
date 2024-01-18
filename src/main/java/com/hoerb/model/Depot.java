package com.hoerb.model;

import com.hoerb.exception.InsufficientFundsException;

/**
 * The {@code Depot} class represents a financial depot associated with a user account.
 * It holds a balance and provides methods for withdrawing, depositing, and checking sufficient funds.
 */
public class Depot {
    private double balance;

    public Depot(double balance) {
        this.balance = balance;
    }


    /**
     * Withdraws the specified amount from the depot.
     *
     * @throws InsufficientFundsException If the withdrawal cannot be completed due to insufficient funds.
     */
    public void withdraw(double amount) throws InsufficientFundsException {
        double newBalance = balance - amount;
        if (newBalance < 0)
            throw new InsufficientFundsException("Withdrawal failed: Insufficient funds. Your current balance is €" + balance);
        balance = newBalance;
    }

    /**
     * Deposits the specified amount into the depot.
     *
     * @param amount The amount to be deposited.
     */
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * Checks if the depot has sufficient funds for a specified amount.
     *
     * @param amount The amount for which to check sufficiency.
     * @return {@code true} if the depot has sufficient funds, {@code false} otherwise.
     */
    public boolean hasSufficientFunds(double amount) {
        //TODO: Implement
        throw new UnsupportedOperationException("This method is not implemented yet");
    }

    public double getBalance() {
        return balance;
    }
}
