package controller;
import model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/register")
public class register extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("uid");
        String passwd = req.getParameter("passw");
        String verification = req.getParameter("passw2");
        if (passwd.equals(verification)){
            //Connect to Database
            //input code. If succeed
            User user = new User();
            boolean ret = user.register(username, passwd);
            if (ret) {
                resp.getWriter().println("Successful registration");
                //resp.sendRedirect("index.html");
            }else {
                resp.getWriter().println("Failed registration");
            }
        }else {
            //Password did not match
            resp.getWriter().println("Your password did not match!");
        }
    }
}
