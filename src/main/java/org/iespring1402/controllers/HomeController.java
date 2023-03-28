package org.iespring1402.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iespring1402.Baloot;

import java.io.IOException;

@WebServlet("/")
public class HomeController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(Baloot.getInstance().getCurrentUser() != null) {
            String homePageName = "/home.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(homePageName);
            requestDispatcher.forward(req, resp);
        } else {
            String loginPageName = "/login.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(loginPageName);
            requestDispatcher.forward(req, resp);
        }
    }
}
