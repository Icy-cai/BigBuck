package controller;
import com.google.gson.Gson;
import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import static yahoofinance.histquotes.Interval.DAILY;
import static yahoofinance.histquotes.Interval.MONTHLY;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Calendar;
import java.util.List;
import java.io.IOException;


@WebServlet("/search")
public class search extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String stockname = req.getParameter("stockname");
        Calendar from = Calendar.getInstance();
        Calendar to = Calendar.getInstance();
        from.add(Calendar.YEAR, -5);

        Stock stock = YahooFinance.get(stockname);
        List<HistoricalQuote> past = stock.getHistory(from, to, MONTHLY);
        String json = new Gson().toJson(past);
        resp.getWriter().println(json);
    }
}
