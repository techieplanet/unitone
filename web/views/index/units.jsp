
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" type="text/css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css" type="text/css" />
        <!-- Font Awesome -->
       <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
       
       <!-- ICheck fronteed skin css-->
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/line.css" type="text/css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/green.css" type="text/css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blue.css" type="text/css" />
        
        <style>
            label {
                color: #fff;
            }
        </style>
    </head>
    <body>
        <nav class="navbar navbar-inverse navbar-fixed-top">
          <div class="container-fluid">
              
            <!-- Brand and toggle get grouped for better mobile display -->
            <div class="navbar-header">
              <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
              </button>
              <a class="navbar-brand" href="#" style="color:#fff">RealtyPoint Limited</a>
            </div>
            
            
             <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
                
                <c:if test="${unit_cart != null}">
                    <ul class="nav navbar-nav navbar-right">
                 <li class="dropdown">
                     <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                         <i class="fa fa-cart-plus fa-2x"></i>
                         <sup><span class="label label-success">${unit_cart.size()}</span></sup>
                     </a>
                     
                     <ul class="dropdown-menu" style="min-width: 350px">
                    
                        <li class="header">
                        <div class="col-md-8">
                                <span>You have ${unit_cart.size()} item(s) in cart </span>
                        </div>
                        <div class="col-md-4" style="text-align:right">
                         <c:if test="${unit_cart.size() > 0}">   
                             <button onclick="location.href='${pageContext.request.contextPath}/Order?action=checkOut&loggedin=no'" class="btn btn-success btn-sm" style="color:#fff">Check out</button>
                         </c:if>
                        </div>
                        <span class="clearfix"></span>
                        </li>

                      <li>
                        <!-- Inner Menu: contains the notifications -->
                        <ul class="menu list-group">
                         <c:set var="cart_total" value="0.0" />   
                         <c:forEach items="${unit_cart}" var="cart">
                          <li class="list-group-item"><!-- start notification -->
                              <div class="row">  
                              <div class="col-md-8">
                                  <span>${cart.getProject().getName()}</span><br />
                                  <span>${cart.getTitle()}</span><br />
                                  <span><fmt:formatNumber value="${cart.getCpu()}" type="currency" currencySymbol="N" /></span><br />
                              </div>
                              <div clas="col-md-4" style="text-align: right; padding-right: 2px">
                                  <button class="btn btn-danger btn-sm" onclick="location.href='${pageContext.request.contextPath}/Project?action=removeFromCart&unit_id=${cart.getId()}'">
                                      <i class="fa fa-trash"></i>
                                  </button>
                              </div>
                              </div>
                          </li><!-- end notification -->
                          <c:set var="cart_total" value="${cart_total + cart.getCpu()}" /> 
                         </c:forEach>
                        </ul>
                      </li>
                      <li class="header text-right"><span>Total : </span> <fmt:formatNumber value="${cart_total}" currencySymbol="N" type="currency" /></li>
                    </ul>
                 </li>
             </ul> 
                </c:if>
                
                <form class="navbar-form navbar-right" action="${pageContext.request.contextPath}/Login" method="POST">
                    <label>Login as </label>
                    <select name="usertype" id="usertype" class="form-control select2" style="">
                      <option value="0">--Select--</option>
                      <option value="ADMIN" ${loginDetails.get("userType").equals("ADMIN") ? 'selected' : ''}>Admin Member</option>
                      <option value="AGENT" ${loginDetails.get("userType").equals("AGENT") ? 'selected' : ''}>Agent</option>
                      <option value="CUSTOMER" ${loginDetails.get("userType").equals("CUSTOMER") ? 'selected' : ''}>Customer</option>
                    </select>
                    
                    <label for="email"> Email Address</label>
                    <input type="email" class="form-control" name="email" id="email" placeholder="Email" value="${loginDetails.get("email")}">
                    
                    <label for="password"> Password</label>
                    <input type="password" class="form-control" name="password" id="password" placeholder="Password">
                    
                    <button type="submit" class="btn btn-primary">Sign In</button>
                              
               </form>
              
              
                    
            </div>
            
           
          </div>
        </nav>     
            
            
            
            
        <div class="container-fluid" style="margin-top: 60px">
          
           <div class="section" style="width: 95%;margin: 10px auto 0 auto">
                        <c:if test="${projectUnits.size() > 0}">   
                         <div class="panel panel-default row">   
                         <div class="panel-body">    
                         <div class="col-md-2">
                             <img src="${pageContext.request.contextPath}/images/img/project.jpg"  alt="No Preview Image" class="img img-thumbnail" />
                         </div>
                         
                         <div class="col-md-8">
                             
                             <c:set var="project" value="${projectUnits.get(0).getProject()}" />
                             <div class="well-lg">
                                 <div class="col-md-2"><b>Name</b></div><div class="col-md-10"><p>${project.getName()}</p></div>
                                 <div class="col-md-2"><b>Location</b></div><div class="col-md-10"><p>${project.getLocation()}</p></div>
                                 <div class="col-md-2"><b>Description</b></div><div class="col-md-10"><p>${project.getDescription()}</p></div>
                                 
                             </div>
                           
                         </div>
                         </div>
                         </div>        
                        </c:if>
           </div>  
            
           <form action="${pageContext.request.contextPath}/Project?action=addToCart&loggedin=no" method="post">
            <section class="well-lg">        
             
             <div class="row">
                 
                 <c:set var="count" value="1" />
                 <c:forEach items="${projectUnits}" var="unit">     

                      <div class="col-md-3">
                        <div class="thumbnail">
                          <img src="${pageContext.request.contextPath}/images/img/unit.jpg" class="img" style="height: 200px"  alt="No Preview">
                          <div class="caption text-center">
                            <h4>${unit.getTitle()}</h4>
                            <span><fmt:formatNumber value="${unit.getCpu()}" type="currency" currencySymbol="N" /></span><br />
                            <span>In Stock : ${unit.getQuantity()}</span> 
                            <p class="text-center">
                                <input type="checkbox"  class="unit_check" name="unit_id" value="${unit.getId()}" autocomplete="off"

                                       <c:forEach items="${unit_cart}" var="cartUnit">
                                           <c:if test="${cartUnit.getId() == unit.getId()}">
                                               disabled checked
                                           </c:if>
                                       </c:forEach>
                                >
                                    <label>Select</label>
                            </p>
                          </div>
                        </div>
                      </div>

                    <c:if test="${count == 4}">
                        <span class="clearfix"></span>
                        <c:set var="count" value="0" />
                    </c:if>

                    <c:set var="count" value="${count + 1}" />    

                 </c:forEach>   
             </div>    
                
             <div class="row">
                 <button type="submit" class="btn btn-success pull-right"><i class="fa fa-cart-plus"></i> Add to cart</button>
                 <a href="${pageContext.request.contextPath}/" class="btn btn-primary pull-left"><i></i> Back</a>
             </div>    
                 
            </section>
            </form>   
        </div>
        
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <!-- iCheck 1.0.1 -->
        <script src="plugins/iCheck/icheck.min.js"></script>
        <script>
            
            var ecommerce = {
                
                initCheckBox : function(){
                    $('.unit_check').each(function(){
                      var self = $(this);
                      label = self.next();
                      label_text = label.text();

                    label.remove();
                    self.iCheck({
                      checkboxClass: 'icheckbox_line-blue',
                      radioClass: 'iradio_line-blue',
                      insert: '<div class="icheck_line-icon"></div>' + label_text
                    });
                  });
                }
                
            } 
            
            ecommerce.initCheckBox();
        </script>
    </body>
</html>
