package MenuConstants;

public enum LoggedInMenuChoices {
    EXIT_PROGRAM("0"),
    SHOW_BALANCE("1"),
    ADD_INCOME("2"),
    DO_TRANSFER("3"),
    CLOSE_ACCOUNT("4"),
    LOGOUT("5");

    private String choice;

    LoggedInMenuChoices(String choice) {
        this.choice = choice;
    }

    public String getChoice() {
        return this.choice;
    }
}
