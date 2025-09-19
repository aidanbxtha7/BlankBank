package com.testing.blankbank.domain;

import com.testing.blankbank.service.TaxService;

public abstract class Account implements Taxable {

    private String accountNumber;
    private double balance;

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void deposit (double amount) {
        if (amount <= 0) throw new IllegalArgumentException("Amount must be greater than zero");
        this.balance += amount;
    }

    public abstract boolean withdraw (double amount);

    @Override
    public double getTax() {
        TaxService taxService = new TaxService();
        return taxService.calculateTax(this);
    }
}
