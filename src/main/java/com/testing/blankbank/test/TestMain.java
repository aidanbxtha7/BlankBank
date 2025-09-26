package com.testing.blankbank.test;

import com.testing.blankbank.domain.Account;
import com.testing.blankbank.domain.ChequeAccount;
import com.testing.blankbank.domain.Client;
import com.testing.blankbank.domain.FixedDepositAccount;
import com.testing.blankbank.domain.SavingsAccount;
import com.testing.blankbank.service.TaxReport;

import java.util.*;

public class TestMain {

    public static void main(String[] args) {
        // --- Setup demo client & simple collections ---
        Client client = new Client();
        client.setName("Aidan");
        client.setSurname("Botha");
        client.setId("9001015009087");
        client.setAccountNumber("1234567890");

        List<String> transactions = new ArrayList<>();
        transactions.add("Deposit 1,000");
        transactions.add("Withdraw 400");
        transactions.add("Deposit 2,000");

        // Intentionally includes duplicates to demonstrate Set behavior
        Set<String> accounts = new HashSet<>();
        accounts.add("1234567890");
        accounts.add("1111111111");
        accounts.add("1234567890"); // duplicate
        accounts.add("2222222222");

        Map<String, Double> balances = new HashMap<>();
        balances.put("1234567890", 1_000.00);
        balances.put("1111111111", 0.00);

        // --- Accounts under test ---
        Account savings = new SavingsAccount();
        Account cheque  = new ChequeAccount();
        Account fixed   = new FixedDepositAccount();

        section("Deposits & Withdrawals");
        savings.deposit(500);
        cheque.deposit(500);

        boolean sOk = savings.withdraw(600); // expect false (would go negative incl. fee)
        boolean cOk = cheque.withdraw(600);  // expect true (goes to -100)

        kv("Savings ok?", sOk + " | Balance: " + savings.getBalance());
        kv("Cheque ok?",  cOk + " | Balance: " + cheque.getBalance());

        // --- Tax checks at various balances (looped instead of repeated) ---
        section("Tax Calculations at Different Balances");
        double[] testBalances = {1_000, 10_000, 100_000};
        for (double b : testBalances) {
            savings.setBalance(b);
            cheque.setBalance(b);
            sub("Balance set to " + b);
            kv("Savings balance", savings.getBalance());
            kv("Cheque balance",  cheque.getBalance());
            kv("Tax on Savings",  savings.getTax());
            kv("Tax on Cheque",   cheque.getTax());
            line();
        }

        // --- Tax report demo ---
        section("Tax Reports");
        TaxReport report = new TaxReport();
        System.out.println(report.WriteTaxReport(savings));
        System.out.println(report.WriteTaxReport(cheque));
        System.out.println(report.WriteTaxReport(fixed));

        // --- Lists, Sets, Maps quick checks ---
        section("Transactions (by index, out of order to test access)");
        System.out.println("transaction 1: " + transactions.get(0));
        System.out.println("transaction 2: " + transactions.get(2));
        System.out.println("transaction 3: " + transactions.get(1));

        section("Accounts in Set (duplicates removed)");
        for (String accountNo : accounts) {
            System.out.println(accountNo);
        }

        section("Balances in Map (by key lookup)");
        System.out.println(balances.get("1234567890"));
        System.out.println(balances.get("1111111111"));

        // --- Comparable / sorting test ---
        section("Comparable Sorting (by Account balance)");
        savings.setBalance(800);
        cheque.setBalance(1200);
        fixed.setBalance(1_000);
        savings.setAccountNumber("2234567890");
        cheque.setAccountNumber("1334567890");
        fixed.setAccountNumber("4444567890");

        List<Account> accountsList = new ArrayList<>();
        accountsList.add(savings);
        accountsList.add(cheque);
        accountsList.add(fixed);

        Collections.sort(accountsList); // uses Account.compareTo (Double.compare on balance)

        for (Account account : accountsList) {
            System.out.println(account.toString());
        }

        System.out.println("\n");
        System.out.println("Sorting by Account Number");
        System.out.println("\n");

        Comparator<Account> byAccountNumber =
                Comparator.comparing(Account::getAccountNumber);

        Collections.sort(accountsList, byAccountNumber);

        for (Account account : accountsList) {
            System.out.println(account.toString());
        }

        System.out.println("\n");
        System.out.println("Sorting by Balance Descending");
        System.out.println("\n");

        Comparator<Account> byBalanceDesc = Comparator.comparing(Account::getBalance).reversed();

        Collections.sort(accountsList, byBalanceDesc);

        for (Account account : accountsList) {
            System.out.println(account.toString());
        }
    }

    // --------- tiny helpers for cleaner output ----------
    private static void section(String title) {
        System.out.println();
        System.out.println("==== " + title + " ====");
    }

    private static void sub(String title) {
        System.out.println("-- " + title + " --");
    }

    private static void kv(String key, Object val) {
        System.out.println(String.format("%s: %s", key, val));
    }

    private static void line() {
        System.out.println("----------------------------------------");
    }
}