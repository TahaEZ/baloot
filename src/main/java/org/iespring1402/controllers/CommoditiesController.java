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

@WebServlet("/commodities")
public class CommoditiesController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Baloot.getInstance().getCurrentUser() == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            ArrayList<Commodity> allCommodities = Baloot.getInstance().getCommodities();
            ArrayList<Commodity> visibleCommodities = new ArrayList<>();

            String paramSearch = req.getParameter("search");
            String paramAction = req.getParameter("action");
            if (!StringUtils.isBlank(paramAction)) {
                switch (paramAction) {
                    case "search_by_category":
                        CategoryFilter filter = new CategoryFilter();
                        filter.setCategoryItem(paramSearch);
                        visibleCommodities = filter.applyFilter(allCommodities);
                        break;
                    case "search_by_name":
                        for (Commodity commodity : allCommodities) {
                            if (commodity.getName().toLowerCase().contains(paramSearch.toLowerCase())) {
                                visibleCommodities.add(commodity);
                            }
                        }
                        break;
                    case "sort_by_rate":
                        visibleCommodities = new ArrayList<>(allCommodities);
                        Collections.sort(visibleCommodities, Comparator.comparingInt(Commodity::getPrice));
                        break;
                    default:
                        visibleCommodities = allCommodities;
                        break;
                }
            } else {
                visibleCommodities = allCommodities;
            }
            req.setAttribute("visibleCommodities", visibleCommodities);
            String commoditiesPageName = "/commodities.jsp";
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(commoditiesPageName);
            requestDispatcher.forward(req, resp);
        }
    }
}
