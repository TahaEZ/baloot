<%@ page import="org.iespring1402.Baloot" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="css/main.css">
</head>

<body>
<form method="post" action="LoginController">
    <%=Baloot.getInstance().getCurrentUser()%>
    <%
        if (request.getAttribute("wrongUserPass") == "true") {%>
    <div class="has-error">
        <span>Wrong password or username!</span>
    </div>
    <br>
    <%
        }
    %>

    <div class="form-group <%if(request.getAttribute("badUsername") == "true") {%>has-error<%}%>">
        <label>Username:</label>
        <%String username = request.getParameter("username");%>
        <input name="username" type="text" value="<%=username != null ? username : ""%>"/>
    </div>
    <br>
    <div class="form-group <%if(request.getAttribute("badPassword") == "true") {%>has-error<%}%>">
        <label>Password:</label>
        <%String password = request.getParameter("password");%>
        <input name="password" type="text" value="<%=password != null ? password : ""%>"/>
    </div>
    <br>
    <button type="submit">Login!</button>
</form>
</body>
</html>