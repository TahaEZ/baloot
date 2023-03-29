<%@ page import="org.iespring1402.Baloot" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <title>Credit</title>
    <link rel="stylesheet" href="styles.css">
</head>

<body>
username: <span><%=Baloot.getInstance().getCurrentUser()%></span>
<br>
<form method="post" action="CreditController">
    <div class="form-group <%if(request.getAttribute("noCredit") == "true") {%>has-error<%}%>">
        <label>Credits:</label>
        <input name="credit" type="text"/>
    </div>
    <br>
    <button type="submit">Add credits</button>
</form>
</body>
</html>