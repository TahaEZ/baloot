<%@ page import="org.iespring1402.User" %>
<%@ page import="org.iespring1402.Baloot" %>
<%@ page import="org.iespring1402.Commodity" %>
<%@ page import="org.iespring1402.response.Response" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String[] pathInfo = request.getPathInfo().split("/");
    int rate = Integer.parseInt(pathInfo[pathInfo.length - 1]);
    int commodityId = Integer.parseInt(pathInfo[pathInfo.length - 2]);
    String username = pathInfo[pathInfo.length - 3];
    User user = Baloot.getInstance().findUserByUsername(username);
    if (user == null) response.sendRedirect(request.getContextPath() + "/not-found.jsp");
    else {
        Response rateCommodityResponse = Baloot.getInstance().rateCommodity(username, commodityId, rate);
        if (rateCommodityResponse.success) {
            response.sendRedirect(request.getContextPath() + "/ok.jsp");
        } else {
            response.sendRedirect(request.getContextPath() + "/not-found.jsp");
        }
    }
%>
