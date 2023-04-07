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
import org.iespring1402.DiscountCode;

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


            DiscountCode discountCode = Baloot.getInstance().findDiscountCodeByCode("HAPPY_NOWRUZ");
            Baloot.getInstance().findUserByUsername(username).getBuyList().setActiveDiscountCode(discountCode);
            DiscountCode activeDiscount = Baloot.getInstance().findUserByUsername(username).getBuyList().getActiveDiscountCode();

            ArrayList<Commodity> buyList = new ArrayList<>();
            for (int commodityId : Baloot.getInstance().findUserByUsername(username).getBuyList().getList()) {
                buyList.add(Baloot.getInstance().findCommodityById(commodityId));
            }

            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("birthDate", birthDate);
            req.setAttribute("address", address);
            req.setAttribute("credit", credit);
            req.setAttribute("totalCost", totalCost);
            req.setAttribute("buyList", buyList);
            if(Baloot.getInstance().findUserByUsername(username).getBuyList().isDiscountActive()){
                req.setAttribute("isDiscountActive",true);
                req.setAttribute("activeDiscount", activeDiscount.getCode());
            }else {
                req.setAttribute("isDiscountActive",false);
            }

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
                } else if (Baloot.getInstance().findUserByUsername(username).getBuyList().getList().isEmpty()) {
                    String errorPageName = "/error.jsp";
                    req.setAttribute("message", "Buy list is empty!");
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    requestDispatcher.forward(req, resp);
                } else {
                    Long remainedCredit = credit - totalCost;
                    Baloot.getInstance().findUserByUsername(username).setCredit(remainedCredit);
                    for (int commodityId : Baloot.getInstance().findUserByUsername(username).getBuyList().getList()) {
                        Commodity commodity = Baloot.getInstance().findCommodityById(commodityId);
                        Baloot.getInstance().quantityToChangeCommodityInStock(commodityId,-1);
                        Baloot.getInstance().findUserByUsername(username).addToPurchasedList(commodity);
                    }
                    Baloot.getInstance().findUserByUsername(username).addToUsedDiscounts(Baloot.getInstance().findUserByUsername(username).getBuyList().getActiveDiscountCode());
                    Baloot.getInstance().findUserByUsername(username).getBuyList().deactivateDiscountCode();
                    Baloot.getInstance().findUserByUsername(username).resetBuyList();
                    String errorPageName = "/ok.jsp";
                    req.setAttribute("message", "Payment was successful!");
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    requestDispatcher.forward(req, resp);
                }
            }

            else if (req.getParameter("discount") != null)
            {
                String discountCode  = req.getParameter("discount");
                if(Baloot.getInstance().discountCodeValidityCheck(discountCode))
                {
                    if(!Baloot.getInstance().findUserByUsername(username).isDiscountCodeUsed(discountCode))
                    {
                        Baloot.getInstance().findUserByUsername(username).getBuyList().setActiveDiscountCode(Baloot.getInstance().findDiscountCodeByCode(discountCode));
                        String errorPageName = "/ok.jsp";
                        req.setAttribute("message", "Discount Code submitted !" );
                        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                        requestDispatcher.forward(req, resp);
                    }
                    else {
                        String errorPageName = "/error.jsp";
                        req.setAttribute("message", "Discount Code used before!");
                        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                        requestDispatcher.forward(req, resp);
                    }
                }
                else {
                    String errorPageName = "/error.jsp";
                    req.setAttribute("message", "Invalid Discount Code!");
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    requestDispatcher.forward(req, resp);
                }
            } else if (req.getParameter("remove") != null) {
                try {
                    Baloot.getInstance().findUserByUsername(username).removeFromBuyList(Integer.parseInt(req.getParameter("remove")));
                    String errorPageName = "/ok.jsp";
                    req.setAttribute("message", "Commodity Removed!");
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    requestDispatcher.forward(req, resp);
                }
                catch (Exception e)
                {
                    String errorPageName = "/error.jsp";
                    req.setAttribute("message", "Invalid request !");
                    RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
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

