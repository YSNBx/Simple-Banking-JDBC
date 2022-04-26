package Behavior;

import AccountManagement.BankAccount;
import AccountManagement.BankAccountCollection;
import MenuConstants.MainMenuChoices;
import JDBCDatabase.JDBC;

import java.util.Scanner;

public class MainMenu implements MenuInterface {
    private Scanner scanner;
    private BankAccountCollection bankAccounts;
    private MenuController controller;

    public MainMenu(BankAccountCollection bankAccounts, MenuController controller) {
        this.scanner= new Scanner(System.in);
        this.bankAccounts = bankAccounts;
        this.controller = controller;
    }

    @Override
    public void start() {
        while (true) {
            printChoices();
            String choice = scanner.nextLine();

            if (choice.equals(MainMenuChoices.EXIT_PROGRAM.getChoice())) {
                System.out.println("\nBye!");
                System.exit(0);
            } else {
                evaluateChoice(choice);
            }
        }
    }

    public void evaluateChoice(String choice) {
        if (choice.equals(MainMenuChoices.CREATE_ACCOUNT.getChoice())) {
            createAnAccount();
        } else if (choice.equals(MainMenuChoices.LOGIN.getChoice())) {
            logIntoAccount();
        } else {
            System.out.println("\nError, wrong input! Please select from given options.");
        }
    }

    public void printChoices() {
        System.out.println("1. Create an account\n2. Log into account\n0. Exit");
    }

    public void createAnAccount() {
        this.bankAccounts.addBankAccountToCollection(
                new BankAccount()
        );

        BankAccount lastAdded = this.bankAccounts.getLastAddedAccount();

        System.out.println("\nYour card has been created\nYour card number: \n"
                + lastAdded.getCardNumber()
                + "\nYour card PIN: \n"
                + lastAdded.getPinNumber()
                + "\n"
        );

        JDBC.addCardToDatabase(
                lastAdded.getCardNumber(),
                lastAdded.getPinNumber(),
                lastAdded.getBalance()
        );
    }

    private void logIntoAccount() {
        System.out.println("\nEnter your card number: ");
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN: ");
        String pinNumber = scanner.nextLine();

        if (checkValidity(cardNumber, pinNumber)) {
            System.out.println("\nYou have successfully logged in!");
            logIn(this.bankAccounts.searchByCardNumber(cardNumber));
        } else {
            System.out.println("\nWrong card number or PIN!\n");
        }
    }

    public boolean checkValidity(String cardNumber, String pinNumber) {
        if (this.bankAccounts.searchByCardNumber(cardNumber) != null) {
            return this.bankAccounts.searchByCardNumber(cardNumber).getPinNumber().equals(pinNumber);
        }
        return false;
    }

    private void logIn(BankAccount bankAccount) {
        LoggedInMenu loggedInMenu = new LoggedInMenu(this.scanner, bankAccount, bankAccounts, this.controller);
        this.controller.setMenuInterface(loggedInMenu);
        this.controller.execute();
    }
}
