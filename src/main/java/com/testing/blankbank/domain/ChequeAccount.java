package com.testing.blankbank.domain;

public class ChequeAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000;

    @Override
    public boolean withdraw (double amount) {
        if (amount <= 0) return false;
        double newBal = getBalance() - amount;
        if (newBal < -OVERDRAFT_LIMIT) return false;
        setBalance(newBal);
        return true;
    }

    @Override
    public String toString() {
        return "Cheque Account";
    }
}
