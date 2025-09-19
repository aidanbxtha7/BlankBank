package com.testing.blankbank.service;

import com.testing.blankbank.domain.Account;
import com.testing.blankbank.domain.ChequeAccount;
import com.testing.blankbank.domain.FixedDepositAccount;
import com.testing.blankbank.domain.SavingsAccount;

public class TaxService {

    public double calculateTax(Account account) {
        double balance = account.getBalance();
        if (account instanceof SavingsAccount) {
            balance = account.getBalance();
            if (balance > 50000) {
                return 0.25 * balance;
            }
            return 0.14 * balance;
        } else if (account instanceof ChequeAccount) {
            if (balance > 10000) {
                return 0.18 * balance;
            }
            return 0.1 * balance;
        } else if (account instanceof FixedDepositAccount) {
            if (balance > 25000) {
                return 0.15 * balance;
            }
            return 0.08 * balance;
        }
        throw new IllegalArgumentException("Account type not recognised");
    }
}
