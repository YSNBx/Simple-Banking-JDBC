import AccountManagement.BankAccountCollection;
import Behavior.MainMenu;
import Behavior.MenuController;
import JDBCDatabase.JDBC;

public class Main {
    public static void main(String[] args) {
        JDBC.initializeDatabase(args[0] + args[1]);

        MenuController controller = new MenuController();
        MainMenu basicMenu = new MainMenu(new BankAccountCollection(), controller);

        controller.setMenuInterface(basicMenu);
        controller.execute();
    }
}