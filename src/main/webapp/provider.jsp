<%@ page import="org.iespring1402.Baloot" %>
<%@ page import="org.iespring1402.Commodity" %>
<%@ page import="org.iespring1402.Provider" %>
<%@ page import="java.util.ArrayList" %>
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
<%if (Baloot.getInstance().getCurrentUser() == null) response.sendRedirect(request.getContextPath() + "/login");%>

username: <span><%=Baloot.getInstance().getCurrentUser()%></span>
<%
    Provider provider = (Provider) request.getAttribute("provider");
    ArrayList<Commodity> providedCommodities = (ArrayList<Commodity>) request.getAttribute("providedCommodities");
%>
<ul>
    <li id="id">Id: <%=provider.getId()%>
    </li>
    <li id="name">Name: <%=provider.getName()%>
    </li>
    <li id="registryDate">Registry Date: <%=provider.getRegistryDate()%>
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
    <% for (Commodity commodity : providedCommodities) { %>
    <tr>
        <td><%=commodity.getId()%>
        </td>
        <td><%=commodity.getName()%>
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
    <% } %>

</table>
</body>
</html>