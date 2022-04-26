package AccountManagement;

import java.util.ArrayList;
import java.util.List;

public class BankAccountCollection {
    private List<BankAccount> bankAccounts;

    public BankAccountCollection() {
        this.bankAccounts = new ArrayList<>();
    }

    public void addBankAccountToCollection(BankAccount bankAccount) {
        if (this.bankAccounts.isEmpty()) {
            this.bankAccounts.add(bankAccount);
        } else if (!checkDuplicates(bankAccount)) {
            this.bankAccounts.add(bankAccount);
        } else {
            addBankAccountToCollection(bankAccount);
        }
    }

    public boolean checkDuplicates(BankAccount bankAccount) {
        return this.bankAccounts.stream()
                .anyMatch(x -> x.getCardNumber().equals(bankAccount.getCardNumber()));
    }

    public List<BankAccount> getBankAccountsCollection() {
        return this.bankAccounts;
    }

    public BankAccount getLastAddedAccount() {
        if (this.bankAccounts.size() == 1) {
            return this.bankAccounts.get(0);
        }
        return this.bankAccounts.get(this.bankAccounts.size() - 1);
    }

    public BankAccount searchByCardNumber(String cardNumber) {
        for (BankAccount b : bankAccounts) {
            if (b.getCardNumber().equals(cardNumber)) {
                return b;
            }
        }
        return null;
    }

    public void transferMoney(BankAccount firstAccount, String cardNumber, int amount) {
        BankAccount secondAccount = this.searchByCardNumber(cardNumber);

        if (!this.checkIfSameAccounts(firstAccount, secondAccount)) {
            if (firstAccount.getBalance() >= amount) {
                this.shuffleBalance(firstAccount, secondAccount, amount);
            } else {
                System.out.println("Not enough money!");
            }
        } else {
            System.out.println("You can't transfer money to the same account!");
        }
    }

    private boolean checkIfSameAccounts(BankAccount firstAccount, BankAccount secondAccount) {
        return firstAccount.equals(secondAccount);
    }

    private void shuffleBalance(BankAccount firstAccount, BankAccount secondAccount, int amount) {
        firstAccount.decreaseBalance(amount);
        secondAccount.increaseBalance(amount);
    }

    public void closeAccount(BankAccount bankAccount) {
        this.bankAccounts.remove(bankAccount);
    }
}
