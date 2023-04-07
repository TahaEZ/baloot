package org.iespring1402.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

@WebServlet("/DemoController")
public class DemoController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if(StringUtils.isBlank(action)) {
            String ForbiddenPageName = "/forbidden.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(ForbiddenPageName);
            requestDispatcher.forward(req, resp);
        } else if(action.equals("ok")) {
            String okPageName = "/ok.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(okPageName);
            requestDispatcher.forward(req, resp);
        } else {
            String notFoundPageName = "/notFound.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(notFoundPageName);
            requestDispatcher.forward(req, resp);
        }
    }
}
