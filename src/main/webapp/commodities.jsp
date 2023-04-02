<%@ page import="org.iespring1402.Baloot" %>
<%@ page import="org.iespring1402.Commodity" %>
<%@ page import="java.util.ArrayList" %>

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
<%
    ArrayList<Commodity> visibleCommodities = (ArrayList<Commodity>) request.getAttribute("visibleCommodities");
%>
<body>
<%if (Baloot.getInstance().getCurrentUser() == null) response.sendRedirect(request.getContextPath() + "/login");%>
<a href="<%=request.getContextPath()%>">Home</a>
<br>
username: <span><%=Baloot.getInstance().getCurrentUser()%></span>

<br><br>
<form action="commodities" method="GET">
    <label>Search:</label>
    <input type="text" name="search" value="">
    <button type="submit" name="action" value="search_by_category">Search By Category</button>
    <button type="submit" name="action" value="search_by_name">Search By Name</button>
    <button type="submit" name="action" value="clear">Clear Search</button>
</form>
<br><br>
<form action="commodities" method="GET">
    <label>Sort By:</label>
    <button type="submit" name="action" value="sort_by_rate">Rate</button>
</form>
<br><br>
<table>
    <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Provider Name</th>
        <th>Price</th>
        <th>Categories</th>
        <th>Rating</th>
        <th>In Stock</th>
        <th>Links</th>
    </tr>
    <% for (Commodity commodity : visibleCommodities) { %>
    <tr>
        <td><%=commodity.getId()%>
        </td>
        <td><%=commodity.getName()%>
        </td>
        <td><%=commodity.getProviderId()%>
        </td>
        <td><%=commodity.getPrice()%>
        </td>
        <td><%=String.join(", ", commodity.getCategories())%>
        </td>
        <td><%=commodity.getRating()%>
        </td>
        <td><%=commodity.getInStock()%>
        </td>
        <td><a href="<%=request.getContextPath()%>/commodities/<%=commodity.getId()%>">Link</a></td>
    </tr>
    <%}%>
</table>
</body>
</html>