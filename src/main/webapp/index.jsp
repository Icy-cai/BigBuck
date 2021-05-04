<%@ page import="model.User" %>
<%@ page import="java.util.Dictionary" %>
<%@ page import="java.util.Enumeration" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="yahoofinance.Stock" %>
<%@ page import="yahoofinance.YahooFinance" %>
<%@ page import="yahoofinance.quotes.stock.StockQuote" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Inconsolata">
<script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
<style>
    body, html {
        height: 100%;
        font-family: "Inconsolata", sans-serif;
    }

    .bgimg {
        background-position: center;
        background-size: cover;
        background-image: url("image/cover.jpeg");
        min-height: 30%;
    }

    .menu {
        display: none;
    }
</style>
<body>



<!-- Header with image -->
<header class="bgimg w3-display-container w3-grayscale-min" id="home">
    <div class="w3-display-bottomleft w3-center w3-padding-large w3-hide-small">
        <span class="w3-tag">demo</span>
    </div>
    <div class="w3-display-middle w3-center">
        <span class="w3-text-white" style="font-size:90px">Big<br>Bucks</span>
    </div>

</header>

<!-- Add a background color and large text to the whole page -->
<div class="w3-sand w3-grayscale w3-large">

    <!-- About Container -->
    <div class="w3-container" id="about">
        <div class="w3-content" style="max-width:700px">
            <br><br>
            <p>
                <br /><br />
                <%
                    String username = (String)session.getAttribute("username");
                    if (username != null && !username.isEmpty()) {
                %>
                Hi <%out.print(username);%>
                <a id="HyperLinkLogout" href="./logout">Logout</a>
            <br /><br />
                <h3>SPY:<%out.print(YahooFinance.get("spy").getQuote().getPrice());%></h3>
                <%} else {%>
                <a id="HyperLink7" href="login2.html">Login</a>
                <a id="HyperLink7" href="newUser2.html">Sign Up</a>
                <%}%>

            <h3>Portfolio Information</h3>
            <p>Userid:<span id="username"><%out.print(username);%></span></p>
            <%
                double total = 0;
                double cash = 0;
                if (username != null && !username.isEmpty()) {
                    User user = new User();
                    int userid = user.GetUserid(username);
                    cash = 1000000;
                    Dictionary<String, Integer> stocks = user.GetStocks(userid);
                    for (Enumeration k = stocks.keys(); k.hasMoreElements(); ) {
                        String stockname = (String) k.nextElement();
                        int sharesize = stocks.get(stockname);
                        Stock stock = YahooFinance.get(stockname);
                        double price = stock.getQuote().getPrice().doubleValue();
                        total += sharesize * price;
                    }
                }
            %>
            <p>Cash: <span>$<%out.print(cash);%></span></p>
            <p>Portfolio value:<span id="portfolio">$<%out.print(total);%></span></p>

            <head>
                <style>
                    table {
                        font-family: arial, sans-serif;
                        border-collapse: collapse;
                        width: 100%;
                    }

                    td, th {
                        border: 1px solid #dddddd;
                        text-align: left;
                        padding: 8px;
                    }

                    tr:nth-child(even) {
                        background-color: #dddddd;
                    }
                </style>
            </head>
            <body>

            <h5>Detailed Information</h5>
            <table id="table">
            <%
                if (username != null && !username.isEmpty()) {
                User user = new User();
                int userid = user.GetUserid(username);
                if (userid != 1) {
                    %>
                <tr>
                    <td>Stock</td><td>shares</td><td>Current Price</td>
                </tr>
            <%
                Dictionary<String, Integer> stocks = user.GetStocks(userid);
                for (Enumeration k = stocks.keys(); k.hasMoreElements();) {
                    String stockname = (String) k.nextElement();
                    int sharesize = stocks.get(stockname);
                    Stock stock = YahooFinance.get(stockname);
                    double price = stock.getQuote().getPrice().doubleValue();
                    %>
            <tr>
                <td><%out.print(stockname);%></td><td><%out.print(sharesize);%></td><td><%out.print(price);%></td>
            </tr>
            <%
                    }
                } else {
            %>
                <tr>
                    <td>Username</td><td>Stock</td><td>shares</td><td>Current Price</td>
                </tr>
                <%
                    ArrayList<Dictionary<String, String>> allStocks = user.GetAllStocks();
                    for (int i = 0; i < allStocks.size(); ++i) {
                        String owner = allStocks.get(i).get("username");
                        String stockname = allStocks.get(i).get("stock");
                        String sharesize = allStocks.get(i).get("sharesize");
                        double price = YahooFinance.get(stockname).getQuote().getPrice().doubleValue();
                        %>
                <tr>
                    <td><%out.print(owner);%></td><td><%out.print(stockname);%></td><td><%out.print(sharesize);%></td><td><%out.print(price);%></td>
                </tr>
                <%
                    }
                }
            %>
<%}%>
            </table>
            </body>


            <br /><br />
            <a id="HyperLink7" href="charting1.html"
            >Get historical data</a
            >
            <br /><br />
            <a id="HyperLink7" href="charting2.html"
            >Advanced Charting</a
            ><br /><br />
            <a id="HyperLink7" href="buysell2.html"
            >Submit Orders Here</a
            ><br /><br />


            <!-- End page content -->
        </div>

        <!-- Footer -->
        <footer class="w3-center w3-light-grey w3-padding-48 w3-large">

        </footer>




</body>
<script>
</script>
</html>
