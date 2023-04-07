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
import org.iespring1402.response.Response;

import java.io.IOException;

@WebServlet("/CreditController")
public class CreditController extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (StringUtils.isBlank(req.getParameter("credit"))) {
            req.setAttribute("noCredit", "true");
            String creditPageName = "/credit.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(creditPageName);
            requestDispatcher.forward(req, resp);
        } else {
            long credit = Long.parseLong(req.getParameter("credit"));
            User currentUser = Baloot.getInstance().findUserByUsername(Baloot.getInstance().getCurrentUser());
            Response addCreditResponse = currentUser.addCredit(credit);
            if (addCreditResponse.success) {
                System.out.println(currentUser.getCredit());
                String okPageName = "/ok.jsp";
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(okPageName);
                requestDispatcher.forward(req, resp);
            } else {
                String errorPageName = "/error.jsp";
                req.setAttribute("message", addCreditResponse.data);
                RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                requestDispatcher.forward(req, resp);
            }
        }
    }
}
