package org.iespring1402.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iespring1402.Baloot;

import java.io.IOException;

@WebServlet("/logout")
public class LogoutController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Baloot.getInstance().setCurrentUser(null);
        String loginPageName = "/login.jsp";
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(loginPageName);
        requestDispatcher.forward(req, resp);
    }
}
