package com.testing.blankbank.service;

import com.testing.blankbank.domain.Account;

import java.util.List;

public class TaxReport {

    public String WriteTaxReport(Account account){
        if (account != null) {
            String report = "";
            String accountType = account.toString() != null ? account.toString() : "";
            String accNumber = account.getAccountNumber() != null ? account.getAccountNumber() : "";
            double tax = account.getTax() != 0 ? account.getTax() : 0;
            double balance = account.getBalance() != 0 ? account.getBalance() : 0;

            report += "----------Report for " + accountType + " - " + accNumber +  "----------";
            report += "\nBalance: " + balance;
            report += "\nTax: " + tax;

            return report;
        }
        else return null;
    }

    public void printBalances(List<? extends Account> accounts) {
        for (Account account : accounts ) {
            System.out.println(account.toString());
        }
    }
}
