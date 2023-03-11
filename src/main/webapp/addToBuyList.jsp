<%@ page import="org.iespring1402.Baloot" %>
<%@ page import="org.iespring1402.User" %>
<%@ page import="org.iespring1402.Commodity" %>
<%@ page import="org.iespring1402.response.Response" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    String[] pathInfo = request.getPathInfo().split("/");
    int commodityId = Integer.parseInt(pathInfo[pathInfo.length - 1]);
    String username = pathInfo[pathInfo.length - 2];
    User user = Baloot.getInstance().findUserByUsername(username);
    Commodity commodity = Baloot.getInstance().findCommodityById(commodityId);
    if (user == null || commodity == null) response.sendRedirect(request.getContextPath() + "/not-found.jsp");
    else {
        Response addToBuyListResponse = user.addToBuyList(commodityId);
        if (addToBuyListResponse.success) {
            response.sendRedirect(request.getContextPath() + "/ok.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/forbidden.jsp");
        }
    }
%>
