import AccountManagement.BankAccountCollection;
import Behavior.MainMenu;
import Behavior.MenuController;
import JDBCDatabase.JDBC;

public class Main {
    public static void main(String[] args) {
        BankAccountCollection bankAccounts = new BankAccountCollection();
        JDBC.initializeDatabase("jdbc:sqlite:card.s3db", bankAccounts);

        MenuController controller = new MenuController();
        MainMenu basicMenu = new MainMenu(bankAccounts, controller);

        controller.setMenuInterface(basicMenu);
        controller.execute();
    }
}