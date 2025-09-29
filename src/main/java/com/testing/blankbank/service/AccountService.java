package com.testing.blankbank.service;

import com.testing.blankbank.domain.Account;
import com.testing.blankbank.domain.SavingsAccount;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;
import java.util.Map;

public class AccountService {

    public Account findByAccountNumber (String AccountNumber, Map<String, Account> accounts) throws AccountNotFoundException {
        Account account = accounts.get(AccountNumber);
        if (account == null) {
            throw new AccountNotFoundException(AccountNumber);
        }

        return account;
    }

    public void addSavings(List<? super SavingsAccount> accounts) {
        accounts.add(new SavingsAccount());
    }
}
