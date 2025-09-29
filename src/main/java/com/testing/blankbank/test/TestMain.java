package com.testing.blankbank.test;

import com.testing.blankbank.domain.Account;
import com.testing.blankbank.domain.ChequeAccount;
import com.testing.blankbank.domain.FixedDepositAccount;
import com.testing.blankbank.domain.SavingsAccount;
import com.testing.blankbank.service.AccountService;
import com.testing.blankbank.service.TaxReport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class TestMain {

    public static void main(String[] args) {
        testDepositWithdraw();
        testTaxCalculations();
        testTaxReports();
        testCollections();
        testSorting();
        testAccountService();
        testPrintBalances();
    }

    private static void testDepositWithdraw() {
        section("Deposits & Withdrawals");

        Account savings = new SavingsAccount();
        Account cheque = new ChequeAccount();

        savings.deposit(500);
        cheque.deposit(500);

        boolean savingsOk = savings.withdraw(600);
        boolean chequeOk = cheque.withdraw(600);

        kv("Savings withdrawal ok?", savingsOk + " | Balance: " + savings.getBalance());
        kv("Cheque withdrawal ok?", chequeOk + " | Balance: " + cheque.getBalance());
    }

    private static void testTaxCalculations() {
        section("Tax Calculations at Different Balances");

        Account savings = new SavingsAccount();
        Account cheque = new ChequeAccount();

        double[] testBalances = {1_000, 10_000, 100_000};

        for (double balance : testBalances) {
            savings.setBalance(balance);
            cheque.setBalance(balance);

            sub("Balance set to " + balance);
            kv("Savings tax", savings.getTax());
            kv("Cheque tax", cheque.getTax());
            line();
        }
    }

    private static void testTaxReports() {
        section("Tax Reports");

        TaxReport report = new TaxReport();
        Account savings = new SavingsAccount();
        Account cheque = new ChequeAccount();
        Account fixed = new FixedDepositAccount();

        savings.setBalance(1000);
        cheque.setBalance(1500);
        fixed.setBalance(2000);

        System.out.println(report.WriteTaxReport(savings));
        System.out.println(report.WriteTaxReport(cheque));
        System.out.println(report.WriteTaxReport(fixed));
    }

    private static void testCollections() {
        section("Collections Demo");

        List<String> transactions = Arrays.asList("Deposit 1,000", "Withdraw 400", "Deposit 2,000");
        Set<String> accountNumbers = new HashSet<>(Arrays.asList("1234567890", "1111111111", "1234567890", "2222222222"));
        Map<String, Double> balances = Map.of("1234567890", 1_000.00, "1111111111", 0.00);

        sub("Transactions (accessed out of order)");
        System.out.println("Transaction 1: " + transactions.get(0));
        System.out.println("Transaction 3: " + transactions.get(2));
        System.out.println("Transaction 2: " + transactions.get(1));

        sub("Account Numbers in Set (duplicates removed)");
        accountNumbers.forEach(System.out::println);

        sub("Balances from Map");
        balances.forEach((key, value) -> System.out.println(key + ": " + value));
    }

    private static void testSorting() {
        section("Account Sorting Tests");

        List<Account> accounts = createTestAccounts();

        sub("Sort by Balance (Natural Order)");
        Collections.sort(accounts);
        accounts.forEach(System.out::println);

        sub("Sort by Account Number");
        accounts.sort(Comparator.comparing(Account::getAccountNumber));
        accounts.forEach(System.out::println);

        sub("Sort by Balance Descending");
        accounts.sort(Comparator.comparing(Account::getBalance).reversed());
        accounts.forEach(System.out::println);
    }

    private static void testAccountService() {
        section("Account Service Test");

        Map<String, Account> accountMap = new HashMap<>();
        Account testAccount = new ChequeAccount();
        testAccount.setAccountNumber("3333333333");
        testAccount.setBalance(2.00);
        accountMap.put(testAccount.getAccountNumber(), testAccount);

        AccountService accountService = new AccountService();

        try {
            Account found = accountService.findByAccountNumber("3333333333", accountMap);
            System.out.println("Found account: " + found);
        } catch (Exception e) {
            System.out.println("Account not found: " + e.getMessage());
        }
    }

    private static void testPrintBalances() {
        section("Print Balances Test");

        TaxReport report = new TaxReport();
        AccountService service = new AccountService();

        // Create test accounts
        SavingsAccount s1 = new SavingsAccount();
        s1.setBalance(500.50);
        s1.setAccountNumber("SAV001");

        SavingsAccount s2 = new SavingsAccount();
        s2.setBalance(1200.75);
        s2.setAccountNumber("SAV002");

        ChequeAccount c1 = new ChequeAccount();
        c1.setBalance(300.00);
        c1.setAccountNumber("CHQ001");

        ChequeAccount c2 = new ChequeAccount();
        c2.setBalance(850.25);
        c2.setAccountNumber("CHQ002");

        // Test printBalances with different account types
        List<SavingsAccount> savingsAccounts = Arrays.asList(s1, s2);
        List<ChequeAccount> chequeAccounts = Arrays.asList(c1, c2);
        List<Account> mixedAccounts = Arrays.asList(s1, c1, s2, c2);

        sub("Savings Accounts:");
        report.printBalances(savingsAccounts);

        sub("Cheque Accounts:");
        report.printBalances(chequeAccounts);

        sub("All Mixed Accounts:");
        report.printBalances(mixedAccounts);

        // Test addSavings method - adds a new SavingsAccount to the list
        sub("Testing addSavings - before and after:");
        List<Account> accountsForTesting = new ArrayList<>(Arrays.asList(s1, c1));
        System.out.println("Before addSavings: " + accountsForTesting.size() + " accounts");
        service.addSavings(accountsForTesting);
        System.out.println("After addSavings: " + accountsForTesting.size() + " accounts");
        report.printBalances(accountsForTesting);
    }

    private static List<Account> createTestAccounts() {
        Account savings = new SavingsAccount();
        Account cheque = new ChequeAccount();
        Account fixed = new FixedDepositAccount();

        savings.setBalance(800);
        savings.setAccountNumber("2234567890");

        cheque.setBalance(1200);
        cheque.setAccountNumber("1334567890");

        fixed.setBalance(1000);
        fixed.setAccountNumber("4444567890");

        return Arrays.asList(savings, cheque, fixed);
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
        System.out.println(key + ": " + val);
    }

    private static void line() {
        System.out.println("----------------------------------------");
    }
}