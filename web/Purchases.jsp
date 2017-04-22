<%-- 
    Document   : Purchases
    Created on : Apr 11, 2017, 3:08:06 PM
    Author     : ekk
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchases</title>
    </head>
    <body>
        <h1>Member Purchases${ from }</h1>
        <h2>${m.memID}</h2>
        <h2>${m.fname} ${m.lname}</h2>
        
        <table border="1">
            <tr>
                <th>Purchase Date</th>
                <th>Purchase Type</th>
                <th>Trans. Code</th>
                <th>Trans. Desc</th>
                <th>Amount</th>
            </tr>
            <c:forEach var="p" items="${purchases}">
                <tr>
                    <td align="left">${p.purchaseDate}</td>
                    <td align="left">${p.purchaseType}</td>
                    <td align="left">${p.transCode}</td>
                    <td align="left">${p.transDesc}</td>
                    <td align="righ">${p.amnt}</td>
                </tr>
            </c:forEach>
        </table>
        <br/>
        <p>${msg}</p>
        <a href="MemberScreen.jsp">Back to Member Screen</a>
    </body>
</html>
