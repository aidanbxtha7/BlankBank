package com.testing.blankbank.domain;

public class SavingsAccount extends Account {

    @Override
    public boolean withdraw (double amount) {
        if (amount <= 0) return false;
        double fee = amount * 0.01;
        double newBal = getBalance() - amount - fee;
        if (newBal < 0) return false;
        setBalance(newBal);
        return true;
    }

    @Override
    public String toString() {
        return "Savings Account";
    }
}
