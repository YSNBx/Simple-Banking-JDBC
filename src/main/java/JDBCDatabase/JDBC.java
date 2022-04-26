package JDBCDatabase;

import AccountManagement.BankAccount;
import AccountManagement.BankAccountCollection;
import org.sqlite.SQLiteDataSource;
import java.sql.*;

public class JDBC {
    private static int ID_COUNTER = 1;

    public static void initializeDatabase(String url, BankAccountCollection bankAccounts) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                System.out.println("Connection to local Database established!\n");
                JDBC.createTable(con, dataSource);
                JDBC.importBankAccounts(con, dataSource, bankAccounts);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void createTable(Connection con, SQLiteDataSource dataSource) {
        try (Statement statement = con.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS card(" +
                    "id INTEGER," +
                    "number VARCHAR," +
                    "pin VARCHAR," +
                    "balance INTEGER DEFAULT 0)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void importBankAccounts(Connection con, SQLiteDataSource dataSource, BankAccountCollection bankAccounts) {
        String getAccounts = "SELECT * FROM card";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement balancePreparedStatement = connection.prepareStatement(getAccounts)) {
                ResultSet rs = balancePreparedStatement.executeQuery();
                while (rs.next()) {
                    String cardNumber = rs.getString("number");
                    String pinNumber = rs.getString("pin");
                    int balance = rs.getInt("balance");
                    bankAccounts.addBankAccountToCollection(new BankAccount(cardNumber, pinNumber, balance));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addCardToDatabase(String cardNumber, String pinNumber, int balance) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:card.s3db");

        try (Connection con = dataSource.getConnection()) {
            try (Statement statement = con.createStatement()) {
                statement.executeUpdate("INSERT INTO card VALUES " +
                        String.format(
                                "(%d, '%s', '%s', %d)",
                                ID_COUNTER, cardNumber, pinNumber, balance
                        )
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        JDBC.ID_COUNTER++;
    }

    public static int showBalance(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:card.s3db");

        String getBalance = "SELECT balance FROM card WHERE number = ?";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement balancePreparedStatement = connection.prepareStatement(getBalance)) {
                balancePreparedStatement.setString(1, cardNumber);
                ResultSet rs = balancePreparedStatement.executeQuery();
                while (rs.next()) {
                    return rs.getInt("balance");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public static void updateBalance(String cardNumber, int balance) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:card.s3db");

        String updateBalance = "UPDATE card SET balance = ? WHERE number = ?";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement balancePreparedStatement = connection.prepareStatement(updateBalance)) {
                balancePreparedStatement.setInt(1, balance);
                balancePreparedStatement.setString(2, cardNumber);
                balancePreparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void closeAccount(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:card.s3db");

        String closeAccount = "DELETE FROM card WHERE number = ?";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement closePreparedStatement = connection.prepareStatement(closeAccount)) {
                closePreparedStatement.setString(1, cardNumber);
                closePreparedStatement.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
