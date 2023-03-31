package org.iespring1402.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.iespring1402.Baloot;
import org.iespring1402.CategoryFilter;
import org.iespring1402.Commodity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

@WebServlet("/buyList")
public class BuyListController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Baloot.getInstance().getCurrentUser() == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            String username = Baloot.getInstance().getCurrentUser();
            String email = Baloot.getInstance().findUserByUsername(username).getEmail();
            String birthDate = Baloot.getInstance().findUserByUsername(username).getBirthDate();
            String address = Baloot.getInstance().findUserByUsername(username).getAddress();
            long credit = Baloot.getInstance().findUserByUsername(username).getCredit();
            long totalCost = Baloot.getInstance().findUserByUsername(username).getBuyList().totalCost();

            ArrayList<Commodity> buyList = new ArrayList<>();
            for (int commodityId : Baloot.getInstance().findUserByUsername(username).getBuyList().getList()) {
                buyList.add(Baloot.getInstance().findCommodityById(commodityId));
            }

            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("birthdate", birthDate);
            req.setAttribute("address", address);
            req.setAttribute("credit", credit);
            req.setAttribute("totalCost", totalCost);
            req.setAttribute("buyList", buyList);

            String commoditiesPageName = "/buyList.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commoditiesPageName);
            requestDispatcher.forward(req, resp);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Baloot.getInstance().getCurrentUser() == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            String buyListPage = "/buyList.jsp";
            String username = Baloot.getInstance().getCurrentUser();
            long credit = Baloot.getInstance().findUserByUsername(username).getCredit();
            long totalCost = Baloot.getInstance().findUserByUsername(username).getBuyList().totalCost();

            if (req.getParameter("remove") != null) {
                int commodityId = Integer.parseInt(req.getParameter("remove"));
                Baloot.getInstance().findUserByUsername(username).getBuyList().remove(commodityId);
            }

            if (req.getParameter("payment") != null) {
                if (credit < totalCost) {
                    String errorPageName = "/error.jsp";
                    req.setAttribute("message", "Insufficient Credit");
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    requestDispatcher.forward(req, resp);
                    return;
                } else {
                    Long remainedCredit = credit - totalCost;
                    Baloot.getInstance().findUserByUsername(username).setCredit(remainedCredit);
                    for (int commodityId : Baloot.getInstance().findUserByUsername(username).getBuyList().getList()) {
                        Commodity commodity = Baloot.getInstance().findCommodityById(commodityId);
                        Baloot.getInstance().findUserByUsername(username).addToPurchasedList(commodity);
                    }
                    Baloot.getInstance().findUserByUsername(username).resetBuyList();
                    String commoditiesPageName = "/buyList.jsp";
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commoditiesPageName);
                    requestDispatcher.forward(req, resp);
                }
            }


            req.setAttribute("username", username);
            req.setAttribute("credit", credit);
            req.setAttribute("totalCost", totalCost);

            String commoditiesPageName = "/buyList.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commoditiesPageName);
            requestDispatcher.forward(req, resp);
        }
    }
}

