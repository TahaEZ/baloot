<%@ page import="org.iespring1402.Comment" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="org.iespring1402.Commodity" %>
<%@ page import="org.iespring1402.Baloot" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <title>Commodity</title>
    <style>
        li {
            padding: 5px;
        }

        table {
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<%
    Commodity commodity = (Commodity) request.getAttribute("commodity");
%>
<body>
<span>username: Siri</span>
<br>
<ul>
    <li id="id">Id: <%=commodity.getId()%>
    </li>
    <li id="name">Name: <%=commodity.getName()%>
    </li>
    <li id="providerName">Provider
        Name: <%=Baloot.getInstance().findProviderByProviderId(commodity.getProviderId()).getName()%>
    </li>
    <li id="price">Price: <%=commodity.getPrice()%>
    </li>
    <li id="categories">Categories: <%=String.join(", ", commodity.getCategories())%>
    </li>
    <li id="rating">Rating: <%=commodity.getRating()%>
    </li>
    <li id="inStock">In Stock: <%=commodity.getInStock()%>
    </li>
</ul>

<label>Add Your Comment:</label>
<form action="commodity" method="POST">
    <input type="text" name="comment" value=""/>
    <input type="hidden" name="commodity_id" value="<%=commodity.getId()%>"/>
    <button name="action" value="comment" type="submit">submit</button>
</form>
<br>
<form action="commodity" method="POST">
    <label>Rate(between 1 and 10):</label>
    <input type="number" id="quantity" name="quantity" min="1" max="10">
    <input type="hidden" name="commodity_id" value="<%=commodity.getId()%>"/>
    <button name="action" value="rate" type="submit">Rate</button>
</form>
<br>
<form action="commodity" method="POST">
    <input type="hidden" name="commodity_id" value="<%=commodity.getId()%>"/>
    <button name="action" value="add" type="submit">Add to BuyList</button>
</form>
<br/>
<table>
    <caption><h2>Comments</h2></caption>
    <tr>
        <th>username</th>
        <th>comment</th>
        <th>date</th>
        <th>likes</th>
        <th>dislikes</th>
    </tr>
    <%
        ArrayList<Comment> comments = (ArrayList<Comment>) request.getAttribute("comments");
    %>

    <% for (Comment comment : comments) { %>
    <tr>
        <td><%=comment.getUserEmail()%>
        </td>
        <td><%=comment.getText()%>
        </td>
        <td><%=comment.getDate()%>
        </td>
        <form action="commodity" method="POST">
            <td>
                <label><%=comment.likesCount()%>
                </label>
                <input type="hidden" name="commodity_id" value="<%=commodity.getId()%>"/>
                <input
                        type="hidden"
                        name="comment_id"
                        value=<%=comment.getId()%>
                />
                <button name="action" value="like" type="submit">like</button>
            </td>
            <td>
                <label><%=comment.dislikesCount()%>
                </label>
                <button name="action" value="dislike" type="submit">dislike</button>
            </td>
        </form>
    </tr>
    <% } %>
</table>
<br><br>
<table>
    <caption><h2>Suggested Commodities</h2></caption>
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
    <tr>
        <td>2341</td>
        <td>Galaxy S22</td>
        <td>Phone Provider No.1</td>
        <td>34000000</td>
        <td>Technology, Phone</td>
        <td>8.3</td>
        <td>17</td>
        <td><a href="/commodities/2341">Link</a></td>
    </tr>
    <tr>
        <td>4231</td>
        <td>Galaxy S22 Plus</td>
        <td>Phone Provider No.1</td>
        <td>43000000</td>
        <td>Technology, Phone</td>
        <td>8.7</td>
        <td>12</td>
        <td><a href="/commodities/4231">Link</a></td>
    </tr>
    <tr>
        <td>1234</td>
        <td>Galaxy S22 Ultra</td>
        <td>Phone Provider No.2</td>
        <td>50000000</td>
        <td>Technology, Phone</td>
        <td>8.9</td>
        <td>5</td>
        <td><a href="/commodities/1234">Link</a></td>
    </tr>
    <tr>
        <td>4321</td>
        <td>Galaxy A53</td>
        <td>Phone Provider No.2</td>
        <td>16000000</td>
        <td>Technology, Phone</td>
        <td>8.7</td>
        <td>11</td>
        <td><a href="/commodities/4321">Link</a></td>
    </tr>
</table>
</body>
</html>
