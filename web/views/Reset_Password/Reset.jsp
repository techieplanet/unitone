<%-- 
    Document   : Reset
    Created on : Jul 20, 2017, 12:24:51 PM
    Author     : SWEDGE
--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
        <link href="${pageContext.request.contextPath}/css/bootstrap.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/css/font-awesome.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/css/AdminLTE.min.css" rel="stylesheet" type="text/css">
        <link href="${pageContext.request.contextPath}/css/skins/skin-blue.min.css" rel="stylesheet" type="text/css">
        <title>NeoForce |</title>
        
    </head>
    <body >
        <div class="login-box">
            <div class="form-box center-block " style="border: 2px solid #3c8dbc ;border-radius: 5px ;">  
        <%//User Type and Email Form%>
        <c:if test="${form == 1}">
        <div class="" >
            <div class="text-center" style="background-color: #3c8dbc;color:white; padding: 2px;width: 100%;">
                <h3>Forgot Password</h3>
                <p>Enter Your User Type And Email To Reset Your Password </p>
            </div>
            <form action="${pageContext.request.contextPath}/PasswordReset" method="post" style="padding: 10px">
                
                
                <%//errors that Occur during form filling %>
                <c:if test="${errors != null && errors.size() > 0}">
                <div class="form-group text-danger"><p >
                <c:forEach var="error" items="${errors}">
                    <c:out value="${error}" /><br/>
                </c:forEach></p>
                </div>
                 </c:if>
                
                <%//Success %>
                <c:if test="${succes}">
                    <div class="form-group ">
                        <div class=" bg-green " style="border-radius: 5px ; padding: 10px">
                            <h3>Success</h3>
                            <p>A Link has been Sent To your mail. Click the  link to Complete the Reset Process</p>
                        </div>
                        <p class="text-black"><b>Note: The Link Expires After 24 hours</b></p>
                    </div>
                </c:if>
                <div class="">
                    <div class="form-group">
                        <label for="userType">User Type</label>
                        <select  name="userType" class="form-control" >
                            <option value="-1" >------Choose------</option>
                            <option value="1">Admin Member</option>
                            <option value="2">Agent</option>
                            <option value="3">Customer</option>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="email">Email</label>
                        <input type="text" name="email" class="form-control" placeholder="Enter Your Email Here" required>
                        <input type="hidden" name="form" value="1"/>
                    </div>                    
                </div>
                <div class="form-group">
                    <input type="submit" name="submit" class="btn btn-block" value="Submit" style="background-color: #3c8dbc;color:white;">                    
                </div>
                <div class="form-group">
                    <a href="${pageContext.request.contextPath}/" style="color : black ;text-decoration: underline ; margin-top: 10px"><b>Back To Login</b></a>                    
                </div>
            </form>            
        </div>
        </c:if>      
       
        <%//New Password Form%> 
        <c:if test="${form == 2}">
           <div class="" >
               <div class="text-center" style="background-color: #3c8dbc;color:white; padding: 2px;width: 100%;" ><h3>New Password</h3>
                        <p>Fill In Your New Password</p>
               </div>
            <form action="${pageContext.request.contextPath}/PasswordReset" method="post" style="padding: 10px">
                <%//errors that Occur during form filling %>
                <c:if test="${errors != null && errors.size() > 0}">
                <div class="form-group text-danger"><p >
                <c:forEach var="error" items="${errors}">
                    <c:out value="${error}" /><br/>
                </c:forEach></p>
                </div>
                 </c:if>
                
                <div class="body">
                    <div class="form-group">
                         <label for="password">New Password</label>
                        <input type="password" name="password" class="form-control" placeholder="Enter Your New Password Here" required/>
                    </div>  
                    <div class="form-group">
                        <label for="confirmPassword">Confirm Password</label>
                        <input type="password" name="confirmPassword" class="form-control" placeholder="confirm Password Here" required/>
                    </div>
                    <div class="form-group">
                        <input type="hidden" name="t" value="${t}">
                        <input type="hidden" name="form" value="2"/>
                    </div>
                </div>
                <div class="form-group">
                    <input type="submit" name="submit" class="btn btn-block" value="Submit" style="background-color: #3c8dbc;color:white;">                    
                </div>
                
            </form>            
        </div>  
        </c:if>
        
        <%//Password Reset Completed%> 
        <c:if test="${form == 3}">
             <div class="header text-center" style="background-color: #3c8dbc;color:white; padding: 2px;width: 100%;"><h3>Password Reset Successful</h3></div>  
            
             <div class="form-box" style="padding: 10px">
               <div class="form-group bg-green" style="border-radius: 5px ; padding: 10px">
                  <p> Your Password Has Been Reset SuccessFully . You Can Now Log In with your new Password</p>
                </div>
                   <div class="form-group">
                       <a href="${pageContext.request.contextPath}/" class="btn btn-primary" style="background-color: #3c8dbc;color:white;">Login</a> 
                   </div>
               </div>
        </c:if>
             
     <c:if test="${form == 4}">
     <div class="header text-center" style="background-color: #3c8dbc;color:white; padding: 2px;width: 100%;"><h3>Success</h3></div>  
            
             <div class="form-box" style="padding: 10px">
               <div class="form-group bg-green" style="border-radius: 5px ; padding: 10px">
                  <p>A Link has been Sent To your mail. Click the  link to Complete the Reset Process</p>
                  <p class="text-black"><b>Note: The Link Expires After 24 hours</b></p>
                </div>
                   <div class="form-group">
                       <a href="${pageContext.request.contextPath}/" class="btn btn-primary" style="background-color: #3c8dbc;color:white;">Login</a> 
                   </div>
               </div>
        </c:if>
        <script src="${pageContext.request.contextPath}/js/jQuery-2.1.4.min.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" type="text/javascript"></script>
           </div> 
    </div>
    </body>
</html>
