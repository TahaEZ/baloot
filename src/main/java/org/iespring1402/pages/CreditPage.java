package org.iespring1402.pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/credit")
public class CreditPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String creditPageName = "/credit.jsp";
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(creditPageName);
        requestDispatcher.forward(req, resp);
    }
}
