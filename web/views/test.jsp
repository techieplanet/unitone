<%-- 
    Document   : test
    Created on : Oct 24, 2016, 7:09:25 PM
    Author     : Prestige
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        
        <c:forEach var="product" items="${products}">
            <p>${product.getId()} -  ${product.getCustomer().getFullName()}</p>
        </c:forEach>
        
    </body>
</html>
