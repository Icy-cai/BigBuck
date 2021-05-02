package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Dictionary;
import java.util.Hashtable;

public class User {
    private static DB database;

    public User() {
        database = new DB();
        try {
            database.connect("jdbc:mysql://localhost:3306/fintech512project?serverTimezone=UTC");
        } catch (SQLException e) {
            int a = 0;
        }
    }

    public int register(String username, String password) {
        int result = 1;
        try {
            ResultSet resultset = database.executeQuery("select * from users where username = '" + username + "';");
            if (resultset.next()) {
                result = 2;
            }else {
                database.executeUpdate("DELETE FROM users WHERE username = '" + username + "';");
                database.executeUpdate("INSERT INTO users (username, password) VALUES ('" + username
                        + "', '" + password + "');");
                result = 0;
            }
        } catch (SQLException throwables) {
            result = 1;
            throwables.printStackTrace();
        } finally {
            //database.close();
        }
        return result;
    }

    public boolean login(String username, String password) {
        boolean result = false;

        try {
            ResultSet resultset = database.executeQuery("select * from users where username = '" + username + "';");
            if (!resultset.next()) {
                System.out.println("no result");
            } else {
                String passw = resultset.getString("password");
                if (passw.equals(password)) {
                    result = true;
                }
            }
        } catch (SQLException var10) {
            result = false;
        } finally {
            //database.close();
        }
        return result;

    }

    public int GetUserid(String username) {
        int userid = -1;

        try {
            ResultSet resultset = database.executeQuery("select * from users where username = '" + username + "';");
            if (!resultset.next()) {
                System.out.println("no result");
            } else {
                userid = resultset.getInt("userid");
            }
        } catch (SQLException var10) {

        } finally {
            //database.close();
        }
        return userid;

    }

    public Dictionary<String, Integer> GetStocks(int userid) {
        Dictionary<String, Integer> stocks = new Hashtable<String, Integer>();
        try {
            ResultSet resultset = database.executeQuery("select * from Assets where UserID = " + userid + ";");
            while (resultset.next()) {
                String stock = resultset.getString("Asset");
                int sharesize = resultset.getInt("Sharesize");
                stocks.put(stock, sharesize);
            }
        } catch (SQLException var10) {

        } finally {
            //database.close();
        }
        return stocks;
    }
}


