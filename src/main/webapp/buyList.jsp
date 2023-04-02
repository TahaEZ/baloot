<html lang="en"><head>
    <meta charset="UTF-8">
    <title>User</title>
    <style>
        li {
        	padding: 5px
        }
        table{
            width: 100%;
            text-align: center;
        }
    </style>
</head>
<%
    ArrayList<Commodity> buyList = (ArrayList<Commodity>) request.getAttribute("buyList");
%>
<body>
    <ul>
        <li id="username">Username: <%=request.getAttribute("username")%></li>
        <li id="email">Email: <%=request.getAttribute("email")%></li>
        <li id="birthDate">Birth Date: <%=request.getAttribute("birthDate")%></li></li>
        <li id="address"><%=request.getAttribute("address")%></li></li>
        <li id="credit">Credit: <%=request.getAttribute("credit")%></li></li>
        <li>Current Buy List Price: <%=request.getAttribute("totalCost")%></li></li>
        <li>
            <a href="/credit">Add Credit</a>
        </li>
        <li>
            <form action="/buyList" method="POST">
                <label>Submit & Pay</label>
                <input id="form_payment" type="hidden" name="payment" value=<%=request.getAttribute("username")%>>
                <button type="submit">Payment</button>
            </form>
        </li>
    </ul>
    <table>
        <caption>
            <h2>Buy List</h2>
        </caption>
        <tbody><tr>
            <th>Id</th>
            <th>Name</th>
            <th>Provider Name</th>
            <th>Price</th>
            <th>Categories</th>
            <th>Rating</th>
            <th>In Stock</th>
            <th></th>
            <th></th>
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
                <td><%=String.join(", ", commodity.getCategories())%></td>
                <td><%=commodity.getRating()%>
                </td>
                <td><%=commodity.getInStock()%>
                </td>
                <td><a href="/commodities/<%=commodity.getId()%>">Link</a></td>
                 <td>
                                <form action="/buyList" method="POST">
                                    <input id="form_commodity_id" type="hidden" name="remove" value=<%=commodity.getId()%>>
                                    <button type="submit">Remove</button>
                                </form>
                 </td>
            </tr>
            <%}%>
    </tbody></table>
       <li>
              <form id="discount" action="/buyList" method="POST">
                    <label>Enter your Discount Code Here: </label>
                    <input type= "text" name="discount" />
                    <button type= "submit" >Add to credit</button>
              </form>
       </li>
</body></html>