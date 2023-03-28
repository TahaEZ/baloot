package org.iespring1402.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.iespring1402.Baloot;
import org.iespring1402.User;

import java.io.IOException;

@WebServlet("/LoginController")
public class LoginController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String loginPageName = "/login.jsp";
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            if (StringUtils.isBlank(username)) {
                req.setAttribute("badUsername", "true");
            }
            if (StringUtils.isBlank(password)) {
                req.setAttribute("badPassword", "true");
            }
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(loginPageName);
            requestDispatcher.forward(req, resp);
        } else {
            User user = Baloot.getInstance().findUserByUsername(username);
            if (user != null) {
                if (user.getPassword().equals(password)) {
                    Baloot.getInstance().setCurrentUser(username);
                    String okPageName = "/ok.jsp";
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(okPageName);
                    requestDispatcher.forward(req, resp);
                    return;
                }
            }
            req.setAttribute("wrongUserPass", "true");
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(loginPageName);
            requestDispatcher.forward(req, resp);
        }
    }
}
