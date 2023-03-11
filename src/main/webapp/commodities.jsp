<%@ page import="org.iespring1402.Baloot" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.iespring1402.Commodity" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<% ArrayList<Commodity> commodities = Baloot.getInstance().getCommodities(); %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Commodities</title>
    <style>
        table {
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<body>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Id</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th>Links</th>
    </tr>
    <% for (Commodity commodity : commodities) { %>
    <tr>
        <td><%= commodity.getId() %>
        </td>
        <td><%= commodity.getName() %>
        </td>
        <td><%= commodity.getProviderId() %>
        </td>
        <td><%= commodity.getPrice() %>
        </td>
        <% ArrayList<String> categories = commodity.getCategories(); %>
        <td>
            <%= String.join(", ", categories) %>
        </td>
        <td><%= commodity.getRating() %>
        </td>
        <td><%= commodity.getInStock() %>
        </td>
        <td><a href="<%= request.getContextPath() %>/commodities/<%= commodity.getId()%>">Link</a></td>
    </tr>
    <% } %>
</table>
</body>
</html>
