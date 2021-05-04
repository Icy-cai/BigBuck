package model;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Transaction {
    private static DB database;

    public Transaction() {
            database = new DB();
            try {
                database.connect("jdbc:mysql://localhost:3306/fintech512project?serverTimezone=UTC");
            }
            catch(SQLException e){
                int a = 0;
            }
    }

    public boolean Buy(int userID, String Stock, int Sharesize) throws IOException {
        Stock stock = YahooFinance.get(Stock);
        StockQuote price = stock.getQuote();
        boolean result = false;
        try {
            database.executeUpdate("INSERT INTO Transaction (Action, Stock, StockPrice, ShareSize, UserID) VALUES ("
                     + "'buy', '" + Stock + "', " + price.getPrice() + ", " + Sharesize + ", " + userID + ");");
            ResultSet resultset = database.executeQuery("select * from Assets where UserID = " + userID + " and Asset = '" + Stock + "';");
            if (!resultset.next()) {
                database.executeUpdate("INSERT INTO Assets values (" + userID + ", '" + Stock + "', " + Sharesize + ")");
            } else {
                int sharesize = resultset.getInt("Sharesize") + Sharesize;
                database.executeUpdate("UPDATE Assets set Sharesize=" + sharesize + " where UserID = " + userID + " and Asset = '" + Stock + "';");
            }
            result = true;
        } catch (SQLException throwables) {
            result = false;
            throwables.printStackTrace();
        } finally {
            database.close();
        }
        return result;
    }

    public boolean Sell(int userID, String Stock, int Sharesize) throws IOException {
        Stock stock = YahooFinance.get(Stock);
        StockQuote price = stock.getQuote();
        boolean result = false;
        try {
            database.executeUpdate("INSERT INTO Transaction (Action, Stock, StockPrice, ShareSize, UserID) VALUES ("
                    + "'sell', '" + Stock + "', " + price.getPrice() + ", " + Sharesize + ", " + userID + ");");
            ResultSet resultset = database.executeQuery("select * from Assets where UserID = " + userID + " and Asset = '" + Stock + "';");
            if (!resultset.next()) {
                result = false;
            } else {
                int sharesize = resultset.getInt("Sharesize") - Sharesize;
                if (sharesize > 0) {
                    database.executeUpdate("UPDATE Assets set Sharesize=" + sharesize + " where UserID = " + userID + " and Asset = '" + Stock + "';");
                } else {
                    database .executeUpdate("delete from Assets where UserID = " + userID + " and Asset = '" + Stock + "';");
                }
                result = true;
            }
        } catch (SQLException throwables) {
            result = false;
            throwables.printStackTrace();
        } finally {
            database.close();
        }
        return result;
    }
}
