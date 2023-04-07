package org.iespring1402.pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/404")
public class NotFoundPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String notFoundPageName = "/notFound.jsp";
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(notFoundPageName);
        requestDispatcher.forward(req, resp);
    }
}
