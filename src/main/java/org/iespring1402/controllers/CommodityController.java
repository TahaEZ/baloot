package org.iespring1402.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang.StringUtils;
import org.iespring1402.Baloot;
import org.iespring1402.Comment;
import org.iespring1402.Commodity;
import org.iespring1402.response.Response;

import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/commodities/*")
public class CommodityController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Baloot.getInstance().getCurrentUser() == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String[] pathInfo = req.getPathInfo().split("/");
        System.out.println(pathInfo.length);
        if (pathInfo.length == 2) {
            int commodityId = Integer.parseInt(pathInfo[pathInfo.length - 1]);
            Commodity commodity = Baloot.getInstance().findCommodityById(commodityId);
            ArrayList<Comment> comments = Baloot.getInstance().getFilteredCommentsByCommodityId(commodityId);
            String commoditiesPageName = "/commodity.jsp";
            String notFoundPageName = "/notFound.jsp";
            RequestDispatcher requestDispatcher;
            if (commodity == null) {
                requestDispatcher = getServletContext().getRequestDispatcher(notFoundPageName);
                requestDispatcher.forward(req, resp);
            } else {
                req.setAttribute("commodity", commodity);
                req.setAttribute("comments", comments);
                requestDispatcher = getServletContext().getRequestDispatcher(commoditiesPageName);
                requestDispatcher.forward(req, resp);
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/404");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = Baloot.getInstance().getCurrentUser();
        if (username == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }
        String okPageName = "/ok.jsp";
        String notFoundPageName = "/notFound.jsp";
        String errorPageName = "/error.jsp";
        RequestDispatcher requestDispatcher;

        String commentId;

        String actionParam = req.getParameter("action");
        if (!StringUtils.isBlank(actionParam)) {
            int commodityId = Integer.parseInt(req.getParameter("commodity_id"));
            switch (actionParam) {
                case "rate":
                    int rate = Integer.parseInt(req.getParameter("quantity"));
                    Response rateCommodityResponse = Baloot.getInstance().rateCommodity(username, commodityId, rate);
                    if (rateCommodityResponse.success) {
                        requestDispatcher = getServletContext().getRequestDispatcher(okPageName);
                    } else {
                        req.setAttribute("message", rateCommodityResponse.data);
                        requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    }
                    break;
                case "add":
                    Response addToBuyListResponse = Baloot.getInstance().addToBuyList(username, commodityId);
                    if (addToBuyListResponse.success) {
                        requestDispatcher = getServletContext().getRequestDispatcher(okPageName);
                    } else {
                        req.setAttribute("message", addToBuyListResponse.data);
                        requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    }
                    break;
                case "like":
                    final int LIKE = 1;
                    commentId = req.getParameter("comment_id");
                    Response likeResponse = Baloot.getInstance().voteComment(username, commentId, LIKE);
                    if (likeResponse.success) {
                        requestDispatcher = getServletContext().getRequestDispatcher(okPageName);
                    } else {
                        req.setAttribute("message", likeResponse.data);
                        requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    }
                    break;
                case "dislike":
                    final int DISLIKE = -1;
                    commentId = req.getParameter("comment_id");
                    Response dislikeResponse = Baloot.getInstance().voteComment(username, commentId, DISLIKE);
                    if (dislikeResponse.success) {
                        requestDispatcher = getServletContext().getRequestDispatcher(okPageName);
                    } else {
                        req.setAttribute("message", dislikeResponse.data);
                        requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    }
                    break;
                case "comment":
                    String commentText = req.getParameter("comment");
                    Response commentResponse = Baloot.getInstance().addComment(username, commodityId, commentText);
                    if (commentResponse.success) {
                        requestDispatcher = getServletContext().getRequestDispatcher(okPageName);
                    } else {
                        req.setAttribute("message", commentResponse.data);
                        requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    }
                    break;
                default:
                    req.setAttribute("message", "This action is not valid!");
                    requestDispatcher = getServletContext().getRequestDispatcher(errorPageName);
                    break;
            }
            requestDispatcher.forward(req, resp);
        } else {
            requestDispatcher = getServletContext().getRequestDispatcher(notFoundPageName);
            requestDispatcher.forward(req, resp);
        }
    }
}
