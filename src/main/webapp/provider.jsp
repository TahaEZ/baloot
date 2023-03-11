<%@ page import="org.iespring1402.Provider" %>
<%@ page import="org.iespring1402.Baloot" %>
<%@ page import="java.util.Map" %>
<%@ page import="org.iespring1402.Commodity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% String[] requestURI = request.getRequestURI().split("/"); %>
<% int providerId = Integer.parseInt(requestURI[requestURI.length - 1]); %>
<% Provider provider = Baloot.getInstance().findProviderByProviderId(providerId); %>

<% if (provider == null) response.sendRedirect(request.getContextPath() + "/not-found.jsp");
else { %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Provider</title>
    <style>
        li {
            padding: 5px
        }

        table {
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<ul>
    <li id="id">ID: <%= provider.getId() %>
    </li>
    <li id="name">Name: <%= provider.getName() %>
    </li>
    <li id="registryDate">Registry Date: <%= provider.getRegistryDate() %>
    </li>
</ul>
<table>
    <caption><h3>Provided Commodities</h3></caption>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
    </tr>
    <% for (Map.Entry<Integer, Float> ratingPair : provider.getRatings().entrySet()) { %>
    <tr>

        <% int commodityId = ratingPair.getKey(); %>
        <% float commodityRating = ratingPair.getValue(); %>
        <th><%=commodityId  %>
        </th>
        <% Commodity commodity = Baloot.getInstance().findCommodityById(commodityId); %>
        <th><%= commodity.getName() %>
        </th>
        <th><%= commodity.getPrice() %>
        </th>
        <th><%= String.join(", ", commodity.getCategories()) %>
        </th>
        <th><%= commodityRating %>
        </th>
        <th><%= commodity.getInStock()%>
        </th>
        <td><a href="<%= request.getContextPath() %>/commodities/<%= commodity.getId()%>">Link</a></td>
    </tr>
    <% } %>

</table>
</body>
</html>
<% } %>