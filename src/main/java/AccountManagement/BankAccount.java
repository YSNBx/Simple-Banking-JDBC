package AccountManagement;

import java.util.Arrays;

public class BankAccount {
    private String cardNumber;
    private String pinNumber;
    private int balance;

    public BankAccount() {
        this.cardNumber = createNewCardNumber();
        this.pinNumber = createNewPinNumber();
        this.balance = 0;
    }

    public BankAccount(String cardNumber, String pinNumber, int balance) {
        this.cardNumber = cardNumber;
        this.pinNumber = pinNumber;
        this.balance = balance;
    }

    private String createNewCardNumber() {
        String cardNumber = "400000" + (((long) Math.floor(Math.random() * 9_000_000_000L) + 1_000_000_000L) + "");

        if (this.checkCardValidity(cardNumber)) {
            return cardNumber;
        }
        return new BankAccount().getCardNumber();
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public void increaseBalance(int amount) {
        this.balance += amount;
    }

    public void decreaseBalance(int amount) {
        this.balance -= amount;
    }

    public boolean checkCardValidity(String cardNumber) {
        int[] numbers = cardNumberToIntArray(cardNumber);
        luhnsAlgorithm(numbers);

        return Arrays.stream(numbers)
                .sum() % 10 == 0;
    }

    public int[] cardNumberToIntArray(String cardNumber) {
        int[] tmp = new int[cardNumber.length()];
        for (int i = 0; i < cardNumber.length(); i++) {
            tmp[i] = Character.getNumericValue(cardNumber.charAt(i));
        }

        return tmp;
    }

    public void luhnsAlgorithm(int[] numbers) {
        for (int i = 0; i < numbers.length - 1; i += 2) {
            numbers[i] *= 2;
            numbers[i] -= numbers[i] > 9 ? 9 : 0;
        }
    }

    private String createNewPinNumber() {
        return new String((int) (Math.random() * 9)
                + ""
                + (int) (Math.random() * 9)
                + "" + (int) (Math.random() * 9)
                + "" + (int) (Math.random() * 9)
                + ""
        );
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getPinNumber() {
        return this.pinNumber;
    }

    public int getBalance() {
        return this.balance;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (this == object) {
            return true;
        }

        if (!(object instanceof BankAccount)) {
            return false;
        }

        BankAccount tmp = (BankAccount) object;
        return this.cardNumber.equals(tmp.cardNumber)
                && this.pinNumber.equals(tmp.pinNumber)
                && this.balance == tmp.balance;
    }
}

