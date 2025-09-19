package com.testing.blankbank.test;

import com.testing.blankbank.domain.Account;
import com.testing.blankbank.domain.ChequeAccount;
import com.testing.blankbank.domain.Client;
import com.testing.blankbank.domain.FixedDepositAccount;
import com.testing.blankbank.domain.SavingsAccount;
import com.testing.blankbank.service.TaxReport;

public class TestMain {
    public static void main(String[] args) {
        Client client = new Client();
        client.setName("Aidan");
        client.setSurname("Botha");
        client.setId("123456789");
        client.setAccountNumber("123456789");

        Account savings = new SavingsAccount();
        Account cheque  = new ChequeAccount();
        Account fixed = new FixedDepositAccount();

        savings.deposit(500);
        cheque.deposit(500);

        boolean sOk = savings.withdraw(600); // expect false (would go negative incl. fee)
        boolean cOk = cheque.withdraw(600);  // expect true (goes to -100)

        System.out.println("Savings ok? " + sOk + " | Balance: " + savings.getBalance()); // 500.0
        System.out.println("Cheque ok?  " + cOk + " | Balance: " + cheque.getBalance());  // -100.0

        savings.setBalance(1000);
        cheque.setBalance(1000);

        System.out.println("Savings balance: " + savings.getBalance());
        System.out.println("Cheque balance: " + cheque.getBalance());

        System.out.println("Tax payable on Savings: " + savings.getTax());
        System.out.println("Tax payable on Cheque: " + cheque.getTax());

        savings.setBalance(10000);
        cheque.setBalance(10000);

        System.out.println("Savings balance: " + savings.getBalance());
        System.out.println("Cheque balance: " + cheque.getBalance());

        System.out.println("Tax payable on Savings: " + savings.getTax());
        System.out.println("Tax payable on Cheque: " + cheque.getTax());

        savings.setBalance(100000);
        cheque.setBalance(100000);

        System.out.println("Savings balance: " + savings.getBalance());
        System.out.println("Cheque balance: " + cheque.getBalance());

        System.out.println("Tax payable on Savings: " + savings.getTax());
        System.out.println("Tax payable on Cheque: " + cheque.getTax());

        TaxReport report = new TaxReport();
        System.out.println(report.WriteTaxReport(savings));
        System.out.println(report.WriteTaxReport(cheque));
        System.out.println(report.WriteTaxReport(fixed));
    }
}
