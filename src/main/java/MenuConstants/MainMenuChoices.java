package MenuConstants;

public enum MainMenuChoices {
    EXIT_PROGRAM("0"),
    CREATE_ACCOUNT("1"),
    LOGIN("2");

    private String choice;

    MainMenuChoices(String choice) {
        this.choice = choice;
    }

    public String getChoice() {
        return this.choice;
    }
}
