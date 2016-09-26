<%-- 
    Document   : list
    Created on : Feb 17, 2016, 7:07:28 PM
    Author     : Swedge
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>


<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World from the inside!</h1>
        <table border=1>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Description</th>
                    <th>Location</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${projects}" var="project">
                        <tr>
                            <td><c:out value="${project.name}" /></td>
                            <td><c:out value="${project.description}" /></td>
                            <td><c:out value="${project.location}" /></td>
                        </tr>
                </c:forEach>
            </tbody>
        </table>
                    
    </body>
</html>