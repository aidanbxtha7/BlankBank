package com.testing.blankbank.domain;

public class FixedDepositAccount extends Account {

    @Override
    public boolean withdraw(double amount) {
        double bal = getBalance();
        if (bal <= 2500) {
            return false;
        }
        setBalance(bal - amount);
        return true;
    }

    @Override
    public String toString() {
        return "Fixed Deposit Account";
    }
}
