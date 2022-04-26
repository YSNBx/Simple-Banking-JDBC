package JDBCDatabase;

import org.sqlite.SQLiteDataSource;
import java.sql.*;

public class JDBC {
    private static int ID_COUNTER = 1;

    public static void initializeDatabase(String url) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl(url);

        try (Connection con = dataSource.getConnection()) {
            if (con.isValid(5)) {
                System.out.println("Connection to local Database established!\n");
                JDBC.createTable(con, dataSource);
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

    public static void showBalance(String cardNumber) {
        SQLiteDataSource dataSource = new SQLiteDataSource();
        dataSource.setUrl("jdbc:sqlite:card.s3db");

        String getBalance = "SELECT balance FROM card WHERE number = ?";

        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement balancePreparedStatement = connection.prepareStatement(getBalance)) {
                balancePreparedStatement.setString(1, cardNumber);
                ResultSet rs = balancePreparedStatement.executeQuery();
                while (rs.next()) {
                    System.out.println("\nBalance: " + rs.getInt("balance"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
}
