package org.iespring1402.pages;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.iespring1402.Baloot;
import org.iespring1402.Commodity;
import org.iespring1402.Provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

@WebServlet("/providers/*")
public class ProviderPage extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Baloot.getInstance().getCurrentUser() == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
        } else {
            String[] pathInfo = req.getPathInfo().split("/");
            RequestDispatcher requestDispatcher;
            if (pathInfo.length == 2) {
                int providerId = Integer.parseInt(pathInfo[pathInfo.length - 1]);
                Provider provider = Baloot.getInstance().findProviderByProviderId(providerId);
                if (provider == null) {
                    String errorPageName = "/error.jsp";
                    req.setAttribute("message", "No provider found with this provider id!");
                    requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                } else {
                    ArrayList<Commodity> commodities = Baloot.getInstance().getCommodities();
                    ArrayList<Commodity> providedCommodities = new ArrayList<>();
                    Set<Integer> providedCommodityIds = provider.getRatings().keySet();
                    for (Commodity commodity : commodities) {
                        if (providedCommodityIds.contains(commodity.getId())) {
                            providedCommodities.add(commodity);
                        }
                    }
                    req.setAttribute("provider", provider);
                    req.setAttribute("providedCommodities", providedCommodities);
                    String providerPageName = "/provider.jsp";
                    requestDispatcher = getServletContext().getRequestDispatcher(providerPageName);
                }
                requestDispatcher.forward(req, resp);
            } else {
                resp.sendRedirect(req.getContextPath() + "/404");
            }

        }
    }
}
