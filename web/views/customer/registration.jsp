<%-- 
    Document   : registration
    Created on : Oct 27, 2016, 11:25:19 AM
    Author     : Prestige
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>NeoForce | ${pageTitle}</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" type="text/css" />
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

    
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    
    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" href="plugins/iCheck/all.css">

    
    <!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.min.css" type="text/css" />
     
    
    <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
          page. However, you can choose any other skin. Make sure you
          apply the skin class to the body tag so the changes take effect.
    -->
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/skin-blue.min.css" type="text/css" />-->
    <link rel="stylesheet" href="css/skins/skin-blue.min.css" type="text/css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <!--<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery-2.1.4.min.js"></script>


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <style>
        #productCart th{
            
            font-weight: 500;
        }
    </style>
  </head>
  <!--
  BODY TAG OPTIONS:
  =================
  Apply one or more of the following classes to get the
  desired effect
  |---------------------------------------------------------|
  | SKINS         | skin-blue                               |
  |               | skin-black                              |
  |               | skin-purple                             |
  |               | skin-yellow                             |
  |               | skin-red                                |
  |               | skin-green                              |
  |---------------------------------------------------------|
  |LAYOUT OPTIONS | fixed                                   |
  |               | layout-boxed                            |
  |               | layout-top-nav                          |
  |               | sidebar-collapse                        |
  |               | sidebar-mini                            |
  |---------------------------------------------------------|
  -->
  <body style="background-color: #eee">
   
      <div class="container">
          
          <header style="background-color: #0073b7;color:#fff ">
              <div class="row">
                  <div class="col-md-3 text-center" style="margin:10px;">
                      <i class="fa fa-image fa-4x"></i>
                      <br/>
                      <span>Company logo</span>
                  </div>
                  <div class="col-md-8" style="padding-left: 80px;padding-top: 10px">
                      <h1>Customer Registration Form</h1>
                  </div>
              </div>
          </header>
          
          <div class="jumbotron" style="background-color: #fff">

<div class="stepwizard">
    <div class="stepwizard-row">
        <div class="stepwizard-step">
            <button type="button" id="process-step-1" class="btn btn-primary btn-circle" onclick="return showCustomerReg()">1</button>
            <p>Register</p>
        </div>
        <div class="stepwizard-step">
            <button type="button" id="process-step-2" class="btn btn-default btn-circle" disabled="disabled" onclick="return showOrderProduct()">2</button>
            <p>Order/Checkout</p>
        </div>
    </div>
</div>

<form role="form" name="customerRegistration" method="POST" action="CustomerRegistration" enctype="multipart/form-data" onsubmit="return submitForm()">
  
    <input type="hidden" name="customer_id" value="" />
    <input type="hidden" name="agent_id" id="agent_id" value="21" />
 
 <div class="row" id="step1">
      <div class="col-md-12">
                <!-- form start -->
               <c:if test="${fn:length(errors) > 0 }">
                <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-danger padding10" style="width:100%; margin:0 auto !important">
                          <c:forEach items="${errors}" var="error">
                              <c:out value="${error.value}" /><br/>
                          </c:forEach>
                        </p>
                    </div>
                </div>
            </c:if>
                    <c:if test="${success}">
              <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-success padding10" style="width:95%">
                          <i class="fa fa-check"></i>Saved Successfully
                          <span class="pull-right">
                              
                              <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Customer">Back to list</a>
                              
                          </span>
                        </p>
                    </div>
                </div>
          </c:if>   
                 
                    
                    <div class="row" style="padding-top:10px;">
                        <div class="col-md-4">
                      <fieldset>
                        <legend style="padding-left:10px !important;">Personal Information</legend>
                          <div class="row">
                              <div class="col-md-12">
                                  <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerFirstname">First Name</label>
                                      <input type="text"  name="customerFirstname" class="form-control" id="customerFirstname" placeholder="First Name" 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerFirstname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.firstname}"</c:if> "/>
                                             
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerMiddlename">Middle Name</label>
                                      <input type="text" class="form-control" id="customerMiddlename" name="customerMiddlename" placeholder="Middle Name"
                                        value=<c:if test="${fn:length(errors) > 0 }">"${param.customerMiddlename}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.middlename}"</c:if> "/>
                                             
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerLastname">Last Name</label>
                                      <input type="text" class="form-control" name="customerLastname" id="customerLastname" placeholder="Last Name" 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerLastname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.lastname}"</c:if> "/>
                                             
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        </div>
                        
                        <div class="col-md-4" style="padding-top:50px">
                       
                          <fieldset>
                                
                          <div class="row">
                             <div class="col-md-12">
                            <div class="form-group" style="padding-left:10px !important;padding-right:10px !important">
                              <label for="customerEmail">Email address</label>
                              <input type="email" class="form-control" id="customerEmail" name="customerEmail" placeholder="your@email.com" 
                                     value=<c:if test="${fn:length(errors) > 0 }">"${param.customerEmail}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.email}"</c:if> "
                              <c:if test="${customer.customerId !='' && customer.customerId != null }">readonly="true"</c:if>
                              />
                                     
                            </div>
                        </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="customerPassword">Password</label>
                                      <input type="password" class="form-control" id="customerPassword" name="customerPassword" placeholder="Password" 
                                        
                                             />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="customerConfirmPassword">Confirm Password</label>
                                      <input type="password" class="form-control" id="customerConfirmPassword" name="customerConfirmPassword" placeholder="Confirm Password"  />
                                    </div>
                                </div>
                            </div>
                            </fieldset>
                        </div>
                         
                    
                    </div>
                    <div class="row">
                      <div class="col-md-4">
                                 <fieldset>
                                    <div class="col-md-6" style="padding-top:0px">

                                        <div class="form-group">
                                          <div class="btn-group btn-group-xs">
                                              <label for="customerPhoto">Customer Photo</label>
                                              <div class="btn btn-primary">
                                                <input type="file" name="customerPhoto" accept="image/gif, image/jpeg, image/png" id="customerPhoto" >
                                                <input type="hidden" name="customerPhotoHidden" />
                                              </div>
                                          </div>

                                        </div>

                                    </div>
                                 </fieldset>
                             
                            </div>

                    </div>
                    </div>
                    
                   
                    
                    
                    <div class="row" style="padding-top:10px;">
                    <div class="col-md-12" >
                        <fieldset>
                            <legend style="padding-left:10px !important;">Contact Information</legend>
                        <div class="row">
                        
                        <div class="col-md-12">
                        <div class="row">
                            <div class="col-md-4">
                            
                                <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                                    <label for="customerStreet">Street</label>
                                    <input type="text" class="form-control" id="customerStreet" name="customerStreet" placeholder="Street"  
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerStreet}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.street}"</c:if> "
                                       />
                                </div>
                            </div>
                        
                            <div class="col-md-4">
                                <div class="form-group" style="padding-left:10px !important;">
                                <label for="customerCity">City</label>

                                    <input type="text" class="form-control" id="customerCity" name="customerCity" placeholder="City"  
                                           value=<c:if test="${fn:length(errors) > 0 }">"${param.customerCity}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.city}"</c:if> "
                                           />


                                </div>
                            </div>
                        <c:if test="${fn:length(errors) > 0 }"><c:set var="state" value="${param.customerState}" scope="session" /></c:if>
                        <c:if test="${fn:length(errors) <= 0 }"><c:set var="state" value="${customer.state}" scope="session" /></c:if>
                      
                        <div class="col-md-4">
                            <div class="form-group" style="margin-right:24px !important;padding-left:20px !important;"  />
                              <label for="customerState">State</label>
                              <select class="form-control" id="customerState" name="customerState"  >
                                    <option value="">--choose--</option>
                                    
                                    <option value="ABUJA FCT" <c:if test="${state == "ABUJA FCT"}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                    <option value="Abia" <c:if test="${state == "Abia"}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                    <option value="Adamawa" <c:if test="${state == "Adamawa"}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                    <option value="Akwa Ibom" <c:if test="${state == "Akwa Ibom"}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                    <option value="Anambra" <c:if test="${state == "Anambra"}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                    <option value="Bauchi" <c:if test="${state == "Bauchi"}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                    <option value="Bayelsa" <c:if test="${state == "Bayelsa"}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                    <option value="Benue" <c:if test="${state == "Benue"}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                    <option value="Borno" <c:if test="${state == "Borno"}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                    <option value="Cross River" <c:if test="${state == "Cross River"}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                    <option value="Delta" <c:if test="${state == "Delta"}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                    <option value="Ebonyi" <c:if test="${state == "Ebonyi"}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                    <option value="Edo" <c:if test="${state == "Edo"}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                    <option value="Ekiti" <c:if test="${state == "Ekiti"}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                    <option value="Enugu" <c:if test="${state == "Enugu"}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                    <option value="Gombe" <c:if test="${state == "Gombe"}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                    <option value="Imo" <c:if test="${state == "Imo"}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                    <option value="Jigawa" <c:if test="${state == "Jigawa"}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                    <option value="Kaduna" <c:if test="${state == "Kaduna"}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                    <option value="Kano" <c:if test="${state == "Kano"}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                    <option value="Katsina" <c:if test="${state == "Katsina"}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                    <option value="Kebbi" <c:if test="${state == "Kebbi"}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                    <option value="Kogi" <c:if test="${state == "Kogi"}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                    <option value="Kwara" <c:if test="${state == "Kwara"}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                    <option value="Lagos" <c:if test="${state == "Lagos"}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                    <option value="Nassarawa" <c:if test="${state == "Nassarawa"}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                    <option value="Niger" <c:if test="${state == "Niger"}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                    <option value="Ogun" <c:if test="${state == "Ogun"}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                    <option value="Ondo" <c:if test="${state == "Ondo"}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                    <option value="Osun" <c:if test="${state == "Osun"}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                    <option value="Oyo" <c:if test="${state == "Oyo"}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                    <option value="Plateau" <c:if test="${state == "Plateau"}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                    <option value="Rivers" <c:if test="${state == "Rivers"}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                    <option value="Sokoto" <c:if test="${state == "Sokoto"}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                    <option value="Taraba" <c:if test="${state == "Taraba"}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                    <option value="Yobe" <c:if test="${state == "Yobe"}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                    <option value="Zamfara" <c:if test="${state == "Zamfara"}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                              </select>
                            </div>
                        </div>
                        
                        </div>
                        </div>
                              
                        <div class="col-md-12">
                            <div class="col-md-6">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="customerPhone">Phone Number</label>
                               
                                <input type="tel" class="form-control" id="customerPhone" name="customerPhone" placeholder="Phone Number" 
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.phone}"</c:if> "/>
                                    
                            </div>
                            </div>
                            
                        </div>
                    </div>
                  </fieldset>
                </div>
                    </div>
                    
                   
                    
                    
                    <div class="row" style="padding-top:10px;">
                    <div class="col-md-12">
                        <fieldset>
                            <legend style="padding-left:20px !important;">Next of Kin</legend>
                            <div class="col-md-12">
                            
                            <div class="row">
                              <div class="col-md-6">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerKinNames">Next of Kin Name</label>
                                      <input type="text" class="form-control" id="customerKinNames" name="customerKinName" placeholder="Enter Kin Name"  
                                          value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinName}"</c:if> "/>
                                            
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                    <label for="customerKinPhone">Next of Kin Phone Number</label>
                                       
                                        <input type="tel" class="form-control" id="customerKinPhone" name="customerKinPhone" placeholder="Enter Kin Phone Number" 
                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinPhone}"</c:if> "/>
                                              
                                            
                                    </div>
                                </div>
                            </div>
                            </div>
                        <div class="row" >
                            <div class="col-md-6">
                                    
                                        <div class="form-group" style="padding-left:25px !important;">
                                          <label for="customerKinAddress" style="">Next of Kin Address</label>
                                           <input type="text" class="form-control" id="customerKinAddress" name="customerKinAddress" placeholder="Enter Kin Address" style="  width:100%;"
                                                  value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinAddress}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinAddress}"</c:if> "/>
                                                  <br/>
                                        </div>
                                    
                            </div>
                         </div>    
                                                  
                            <div  class="row">
                                    <div class="col-md-4" style="padding-top:0px;padding-left:25px !important;" >
                                     
                                            <div class="form-group" >
                                                <div class="btn-group btn-group-xs">  
                                                  <label for="customerKinPhoto" style="">Next of Kin Picture</label>
                                                  <div class="btn btn-primary">
                                                   <input type="file" id="customerKinPhoto" name="customerKinPhoto" accept="image/gif, image/jpeg, image/png" />
                                                   <input type="hidden" name="customerKinPhotoHidden" />
                                                  </div>
                                                </div>
                                            </div>

                                    </div>
                            </div>
                                                  
                        
                        </fieldset>
                    </div>
                    </div>
                                                  
                    <div class="col-md-12">
                        <a class="btn btn-primary" href="#" onclick="return showOrderProduct()" role="button">Proceed to Order <i class="fa fa-long-arrow-right"></i></a>
                    </div>
        </div>
                 
          
 <div class="row" id="step2" style="display:none">

   <div class="col-md-12">
               
  
         <div class="row">
                  <div class="col-md-12">
                    <div class="row" style="padding-top:10px;">
                    <div class="col-md-12">
                        <input type="hidden" name="dataHidden" id="dataHidden" />
                    	<fieldset>
                        	<legend style="padding-left:20px !important;">Product Details</legend>
                            <div class="col-md-12">
                            	<div class="row">
                                   
                                              
                                   <div class="col-md-4">
                                    	<div class="form-group">
                                            <label for="selectProdcut">Select Product</label>
                                            <select class="form-control select2" id="selectProduct" style="width: 100%;" onchange="getProjectUnits('${pageContext.request.contextPath}', 'Project','')" >
                                                <option value="" selected="selected">-- choose --</option>
                                                
                                              <c:forEach items="${projects}" var="project" >
                                                <option  value="${project.id}">${project.name}</option>
                                              </c:forEach>
                                            </select>
                                        </div> 
<!--                                              /.form-group select product -->
                                    </div>
                               
                                   <div class="col-md-4">
                                    	<div class="form-group">
                                          <input type="hidden" id="pUnitId" />
                                            <label for="selectUnit">Select Unit</label>
                                            
                                            <select class="form-control select2" id="selectUnit" style="width: 100%;" onchange="getProjectQuantity('${pageContext.request.contextPath}', 'ProjectUnit')">
                                              <option value="#" selected="selected">-- choose --</option>
                                             
                                            </select>
                                        </div> 
<!--                                              /.form-group  select unit-->
                                    </div>
                                
                                    <div class="col-md-4">
                                    	<div class="form-group">
                                            <label for="selectQuantity">Select Quantity</label>
                                            
                                            <select class="form-control select2" id="selectQuantity" style="width: 100%;" onchange="calculateProductAmount()">
                                              <option value="#" selected="selected">-- choose --</option>
                                             
                                            </select>
                                        </div> 
<!--                                            /.form-group select quantity -->
                                    </div>
                                </div>
                               <div class="row">
                                
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="productAmount">Amount</label>
                                            <span id="amountPerUnit" class="productSpan">
                                                Amount per Unit: <span id="amountUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="amountTotalUnit"></span>
                                            </span>
                                            <input type="text" class="form-control" id="productAmount" name="productAmount" style="width: 100%;" readonly>
                                        </div> 
<!--                                            /.form-group amount -->
                                    </div>
                                              
                               
                                   <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="productMinimumInitialAmount">Initial Amount(N)</label>
                                            <span id="amountPerUnit" class="productSpan">
                                                min initial amt /unit: <span id="initialAmountPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="minInitialAmountSpan"></span><br/>
                                            </span>
                                            <input type="text" class="form-control" id="productMinimumInitialAmount" name="productMinimumInitialAmount" style="width: 100%;"  onkeyup="calculateAmountToPay()">
                                        </div> 
<!--                                            /.form-group initial monthly amount -->
                                    </div>
                                              
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="amountLeft">Balance Payable(N)</label>
                                            <span id="amountPerUnit" class="productSpan">
                                               
                                            </span>
                                            <input type="text" class="form-control" id="amountLeft" name="amountLeft" style="width: 100%;" readonly >
                                        </div> 
<!--                                                  /.form-group initial monthly amount -->
                                    </div>
                                   
                                    <input type="hidden" id="editMode" value="" />
                                    
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="productMaximumDuration">Payment Duration</label>
                                            <span id="amountPerUnit" class="productSpan">
                                                max payment duration /unit: <span id="payDurationPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="payDurationPerQuantity"></span>
                                            </span>
                                            <div class="row">
                                            	<div class="col-md-12">
                                            		<select class="form-control select2"  id="productMaximumDuration" style="width: 100%;" onchange="monthlyPayCalculator('exsiting')">
                                                      <option value="#" selected="selected">-- choose --</option>
                                                    
                                                    </select>
                                                </div>
                                            </div>
                                        </div> <!-- /.form-group Duration -->
                                    </div>
                               
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="productMinimumMonthlyPayment">Monthly Payment(N)</label>
                                            <span id="amountPerUnit" class="productSpan">
                                                min monthly pay / unit: <span id="monthlyPayPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="monthlyPayPerQuantity"></span>
                                            </span>
                                            <input type="text" class="form-control" id="productMinimumMonthlyPayment" name="productMinimumMonthlyPayment" style="width: 100%;" onKeyup="calculateDurationFromMonthlyPay()">
                                            <span id="finalAmount" style="display:block"></span>
                                        </div> <!--/.form-group amount -->
                                    </div>
                                                
                                </div>
                  <div class="row">
                        <div class="col-md-12 box-footer">
                            <div class="row">
                                <div class="col-md-4">
                                <div id="errorText" style="color:#722F37 !important; font-weight:bold !important;"></div>
                                </div>
                                <div class="col-md-2 pull-right">
                                   <div id="addToCartLabel"  style="margin: 0 auto !important;" >
                                    	<div class="form-group">
                                            <a class="btn btn-success" name="addToCart" id="addToCart" href="#" onClick=" return addToCart(event)" ><i class="fa fa-cart-plus"></i> Add to Cart</a>
                                        </div> 
                                   </div>
                               </div>
                           </div>
                        </div>
                  </div>
                            </div> <!--/.col-md-12 -->
                    	</fieldset>
                  	</div> <!--/.col-md-12 -->
                 	</div> <!--/.row -->
                  </div> <!-- /.col-md-4 -->
                  
                 </div> 
                  
                  
                  
                  
                  
  <!-- 
    *****************************************
    Product Cart starts here
    *****************************************
  -->
 <div class="row">
  <div class="col-md-12">
      <input type="hidden" name="dataHidden" id="dataHidden" />
    <div class="row" style="padding-top:10px;">
    <div class="col-md-12" id="shoppingCart">
      <div class="panel panel-default panel-primary">
          <div class="panel-heading">
              <div class="panel-title"><i class="fa fa-cart-plus fa-2x"></i> Product Cart </div>
          </div>
          <div class="panel-body"> 
                <div class="row" >
                 <div class="col-md-12">
                 <table id="productCart" class="table table-bordered table-striped table-hover" style="text-align:right !important;">
                    <thead>
                      <tr>
                        <th>Product</th>
                        <th>Product Unit</th>
                        <th>Quantity</th>
                        <th>Amount</th>
                        <th>Initial Amount</th>
                        <th>Amount to Pay</th>
                        <th>Duration</th>
                        <th>Monthly Pay</th>
                        <th>Transaction Deduction</th>
                        
                        <th>Action</th>
                        
                      </tr>
                    </thead>
                    <tfoot style="text-align:right !important;color:green !important; font-weight:bold !important;">
                      <tr>
                        <th colspan="8" align="right" style="text-align:right !important;">Total</th>
                        
                        <th style="text-align:right !important;" align="right"><span id="cartSum">0</span></th>
                        
                        <th></th>
                        
                      </tr>
                    </tfoot>
                    <tbody>
                       
                            
                        
                   </tbody>
                   
                  </table>
                     
                  <input type="hidden" id="CartActualSum" value="" />              
                 </div>                   
                 <!-- 
                   ***************************
                   Checkout Button starts Here
                   ***************************
                 -->
                 <div class="col-md-2 col-md-offset-10">
                     <div class="form-group">
                        <a href="#" class="btn" name="checkOutToPay" id="checkOutToPay" onClick="return checkOutOfCart();"><i class="fa fa-cart-plus"></i>Checkout</a>
                    </div> 

                 </div>
                 
                 <!--
                  ****************************
                    Checkout Button ends Here
                  ****************************
                 -->
                </div>
          </div>
      </div>
    </div> 
                        
         <div class="col-md-12" id="paymentCheckout">

                <fieldset>
        <legend style="padding-left:20px !important;">Check Out</legend>

            <div class="col-md-12" >

                <!-- Start of Payment Method Container -->
                <div class="row" > 
                    <div class="col-md-12">
                        <span style="color:green;font-weight:bold;">You'd be paying <span id='paySum'></span></span>
                        <div class="form-group">
                            <label for="paymentMethod">Payment method:</label><br/>
                            <input type="radio" name="paymentMethod" value="1" id="bankdep" onclick="showNecessaryMenu(1)"/>&nbsp;<label for="bankdep" style="display:inline !important;">Bank Deposit</label>&nbsp;&nbsp;&nbsp;&nbsp;

                            <input type="radio" name="paymentMethod" value="2" id="paywithcard" onclick="showNecessaryMenu(2)"/>&nbsp; <label for="paywithcard" style="display:inline !important;cursor:pointer !important;">Credit/Debit Card <img src="${pageContext.request.contextPath}/images/img/paywithcard.png" /></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;

                            <input type="radio" name="paymentMethod" value="3" id="paywithcash" onclick="showNecessaryMenu(3)"/>&nbsp;<label for="paywithcash" style="display:inline !important;"> Cash</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="paymentMethod" value="4" id="bankTransfer" onclick="showNecessaryMenu(4)"/>&nbsp; <label for="bankTransfer" style="display:inline !important;cursor:pointer !important;">Bank Transfer </label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="companyAccount">Company Account</label>
                            <select name="companyAccount" id="companyAccount" class="form-control select2" style="width: 100%;">
                                <option value="">--Select Account--</option>
                                <c:forEach items="${companyAccount}" var="CA">
                                    <option value="${CA.getId()}">${CA.getAccountName()}</option>
                                </c:forEach>
                            </select>
                        </div> 
                    </div>
                </div>
                <!-- End of Payment Method Container -->        

                 <!-- Pay via Bank Deposit Div Container -->            
                <div class='row' id='pwBankdeposit'>



                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="depositorsName">Depositor's Name</label>
                            <input type="text" class="form-control" id="depositorsName" name="depositorsName" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="tellerNumber">Teller Number</label>
                            <input type="text" class="form-control" id="tellerNumber" name="tellerNumber" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="tellerAmount">Amount</label>
                            <input type="text" class="form-control amount-box" id="tellerAmount" name="tellerAmount" style="width: 100%;">
                        </div>      
                    </div>

                    <div class="col-md-2">
                        <div class="form-group" style="padding-top:25px !important;">
                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge"/>
                        </div>      
                    </div>

                </div>
                <!-- End of Pay Via Bank Deposit Div Container -->


                <!-- Pay with Cash Div Container -->
                <div class='row' id='pwCash'>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="cashAmount">Amount</label>
                            <input type="text" class="form-control amount-box" id="cashAmount" name="cashAmount" style="width: 100%;">
                        </div>      
                    </div>
                    <div class="col-md-2">
                        <div class="form-group" style="padding-top:25px !important;">
                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge" style="vertical-align:bottom !important;"/>
                        </div>      
                    </div>
                </div>
                <!-- End of Pay with Cash Div Container -->



                 <!-- Pay with Card Div Container -->
                <div class='row' id='pwCard'>


                        <div class="col-md-2">
                            <div class="form-group">
                                <label for="tellerNumber">Amount</label>
                                <input type="text" class="amount-box form-control" name="cardAmount" />
                            </div>
                            <div class="form-group">
                                <label for="tellerNumber">Click to proceed to payment</label>
                                <!--<a href="${pageContext.request.contextPath}/images/img/webpay.png" target="_blank" class="btn btn-success"><i class="fa fa-angle-double-right"></i> Pay Now</a>
                                --> <button type="submit"  name="Pay" class="btn btn-success"  style="vertical-align:bottom !important;"><i class="fa fa-angle-double-right"></i> Pay Now</button>
                            </div> 
                        </div>
                </div>
                <!-- End of Pay with Card Div Container -->

                 <!-- Pay with Card Div Container -->
                <div class='row' id='pwBankTransfer'>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="bankName">Depositor's Bank Name</label>
                            <input type="text" class="form-control" id="transfer_bankName" name="transfer_bankName" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="accountNo">Depositor's Account No</label>
                            <input type="text" class="form-control" id="transfer_accountNo" name="transfer_accountNo" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="accountNo">Depositor's Account Name</label>
                            <input type="text" class="form-control" id="transfer_accountName" name="transfer_accountName" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="tellerAmount">Amount</label>
                            <input type="text" class="form-control amount-box" id="transfer_amount" name="transfer_amount" style="width: 100%;">
                        </div>      
                    </div>

                    <div class="col-md-1">
                        <div class="form-group" style="padding-top:25px !important;">
                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge"/>
                        </div>      
                    </div>
                </div>
                <!-- End of Pay with Cash Div Container -->

              </div>
        </fieldset>
                                
         </div>
         </div>
                                 
   </div> <!--/.row -->
 </div> <!--/.box box-default -->
           
          <!-- 
            *****************************************
            Product Cart Ends here
            *****************************************
          -->

    
      <input type="hidden" name="cartDataJson" id="cartDataJson" />
      

  </div><!-- /.box -->
  
  <div class="col-md-12">
      <a class="btn btn-primary" href="#" onclick="return showCustomerReg()" role="button"><i class="fa fa-long-arrow-left"></i> Customer registration</a>
  </div>
  
</div><!-- /.box -->
                    
      
      <!--
      <div class="row">
          
          <div class="box-footer" style="background-color:transparent;">
                      <input type="submit" class="btn btn-primary" name="customerCreate" value="Save"/>
          </div>
          
      </div>
      -->
</form>     
            
          </div>  
      </div>

   
         <footer class="main-footer" style="margin-left:0;">
        <!-- To the right -->
        <div class="pull-right hidden-xs">
          Powered by <b>Techie Planet</b>
        </div>
        <!-- Default to the left -->
        <!--<strong>Copyright &copy; 2016 <a href="#">Company</a>.</strong> All rights reserved.-->
        &nbsp;
        </footer>                     
         
      </div>     
               
         
      <!--MODAL-->
      <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE</h4>
            </div>
            <div class="modal-body">
              <p>Are you sure you want to delete?</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="" class="btn btn-primary">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
       
      
      <!--MODAL-->
      <div class="modal fade" id="deleteModalCart" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE</h4>
            </div>
            <div class="modal-body">
              <p>Are you sure you want to delete?</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="" class="btn btn-primary">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
      
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
 
    <!-- iCheck 1.0.1 -->
    <script src="plugins/iCheck/icheck.min.js"></script>
    

    <!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
    
    
    <!-- AdminLTE App -->
    <!--<script src="dist/js/app.min.js"></script>-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/accounting.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/functions.js"></script>


    <script src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    
    <script>
      $(function () {
            //iCheck for checkbox and radio inputs
            $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
              checkboxClass: 'icheckbox_minimal-blue',
              radioClass: 'iradio_minimal-blue'
            });
            //Red color scheme for iCheck
            $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
              checkboxClass: 'icheckbox_minimal-red',
              radioClass: 'iradio_minimal-red'
            });
            //Flat red color scheme for iCheck
            $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
              checkboxClass: 'icheckbox_flat-green',
              radioClass: 'iradio_flat-green'
            });
        });
        
        function submitForm(){
           
           var submitOk = true;
           
           var payment_mode = $('input:radio[name=paymentMethod]:checked').val();
           
           var companyAccount = $("#companyAccount").val();
           
           if(companyAccount == ""){
               
               alert("Please select company account");
               submitOk = false;
           } 
           else if(payment_mode == 1){
               
               var depositorsName = $("#depositorsName").val();
               var tellerNumber = $("#tellerNumber").val();
                       
               if( $.trim(depositorsName) == ""){
                   alert("Please Enter depositors name");
                   submitOk = false;
               }
               else if($.trim(tellerNumber) == ""){
                   alert("Please enter teller number");
                   submitOk = false;
               }
           }
           else if(payment_mode == 4){
               
               var transfer_bankName = $("#transfer_bankName").val();
               var transfer_accountNo = $("#transfer_accountNo").val();
               var transfer_accountName = $("#transfer_accountName").val();
               
               if($.trim(transfer_bankName) == ""){
                   alert("Please enter Bank Name");
                   submitOk = false;
               }
               else if($.trim(transfer_accountNo) == ""){
                   alert("Please enter account number");
                   submitOk = false;
               }
               else if($.trim(transfer_accountName) == ""){
                   alert("Please enter account Name");
                   submitOk = false;
               }
               
           }
           
           return submitOk;
       }
       
      </script>
                                      
   
  </body>

</html>
  

