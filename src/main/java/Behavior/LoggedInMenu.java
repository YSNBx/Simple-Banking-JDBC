package Behavior;

import AccountManagement.BankAccount;
import AccountManagement.BankAccountCollection;
import MenuConstants.LoggedInMenuChoices;
import JDBCDatabase.JDBC;

import java.util.Scanner;

public class LoggedInMenu implements MenuInterface {
    private Scanner scanner;
    private BankAccount bankAccount;
    private BankAccountCollection bankAccounts;
    private MenuController controller;

    public LoggedInMenu(Scanner scanner, BankAccount bankAccount, BankAccountCollection bankAccounts, MenuController controller) {
        this.scanner = scanner;
        this.bankAccount = bankAccount;
        this.bankAccounts = bankAccounts;
        this.controller = controller;
    }

    @Override
    public void start() {
        while (true) {
            printChoices();
            String choice = scanner.nextLine();

            if (choice.equals(LoggedInMenuChoices.LOGOUT.getChoice())) {
                System.out.println("\nYou have successfully logged out!\n");
                break;
            } else {
                evaluateChoice(choice);
            }
        }
    }

    private void evaluateChoice(String choice) {
        if (choice.equals(LoggedInMenuChoices.SHOW_BALANCE.getChoice())) {
            showBalance();
        } else if (choice.equals(LoggedInMenuChoices.ADD_INCOME.getChoice())) {
            addIncome();
        } else if (choice.equals(LoggedInMenuChoices.DO_TRANSFER.getChoice())) {
            doTransfer();
        } else if (choice.equals(LoggedInMenuChoices.CLOSE_ACCOUNT.getChoice())) {
            closeAccount();
        } else if (choice.equals(LoggedInMenuChoices.EXIT_PROGRAM.getChoice())) {
            System.out.println("\nBye!");
            System.exit(0);
        } else {
            System.out.println("\nError, wrong input!");
        }
    }

    private void showBalance() {
        System.out.println(this.bankAccount.getBalance());
        //Update Database
        //JDBCDatabase.JDBC.showBalance(this.bankAccount.getCardNumber());
    }

    public void printChoices() {
        System.out.println("\n1. Balance\n2. Add Income\n3. Do Transfer\n4. Close Account\n5. Log out\n0. Exit");
    }

    public void addIncome() {
        System.out.println("\nEnter income: ");
        int balance = Integer.parseInt(scanner.nextLine());

        this.bankAccount.increaseBalance(balance);
        System.out.println("Income was added!");

        JDBC.updateBalance(this.bankAccount.getCardNumber(), this.bankAccount.getBalance());
    }

    /*
        UPDATE DATABASE
    */

    public void doTransfer() {

        System.out.println("\nTransfer\nEnter card number: ");
        String cardNumber = scanner.nextLine();

        if (this.bankAccounts.searchByCardNumber(cardNumber) != null) {
            if (this.checkCardNumberLuhns(cardNumber)) {
                System.out.println("Enter how much money you want to transfer: ");
                this.bankAccounts.transferMoney(this.bankAccount, cardNumber, Integer.parseInt(scanner.nextLine()));
            } else {
                System.out.println("You probably made a mistake in the card number. Please try again!");
            }
        } else {
            System.out.println("Such a card does not exist.");
        }
    }

    public boolean checkCardNumberLuhns(String cardNumber) {
        return this.bankAccount.checkCardValidity(cardNumber);
    }

    /*
        UPDATE DATABASE
    */

    public void closeAccount() {
        this.bankAccounts.closeAccount(this.bankAccount);
        System.out.println("\nThis account has been closed!\n");
        this.controller.setMenuInterface(new MainMenu(this.bankAccounts, this.controller));
        this.controller.execute();
    }
}
