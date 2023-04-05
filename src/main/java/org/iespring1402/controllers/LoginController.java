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

        String errorPageName = "/error.jsp";
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            String message = "";
            if (StringUtils.isBlank(username)) {
                message += "Username cannot be empty!";
            }
            if (StringUtils.isBlank(password)) {
                if (!message.isEmpty())
                    message += "<br>";
                message += "Password cannot be empty!";
            }
            req.setAttribute("message", message);
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
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
            req.setAttribute("message", "Wrong password or username!");
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
            requestDispatcher.forward(req, resp);
        }
    }
}
