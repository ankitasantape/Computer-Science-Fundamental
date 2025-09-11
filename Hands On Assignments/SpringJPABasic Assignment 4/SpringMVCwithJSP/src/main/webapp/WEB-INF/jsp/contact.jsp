<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Contact List - Spring Boot Web Application Example</title>
</head>
<body>
    <h1 align="center">My Contact List</h1>
    <br/>
    <table border="1" cellpadding="10">
        <tr>
           <th>Name</th>
           <th>Email</th>
           <th>Country</th>
        </tr>
        <c:forEach var="contact" items="${contacts}">
        <tr>
            <td>${contact.name}</td>
            <td>${contact.email}</td>
            <td>${contact.country}</td>
        </tr>
        </c:forEach>
    </table>
</body>
</html>
