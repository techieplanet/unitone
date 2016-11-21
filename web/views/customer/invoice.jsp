<%-- 
    Document   : success
    Created on : Oct 24, 2016, 3:33:16 PM
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

    <title>NeoForce | Invoice</title>
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
        tfoot {
            background-color: #eee;
            border: solid 1px #ccc;
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
          
          <section class="invoice" style="min-height: 600px">
          <!-- title row -->
          <div class="row">
            <div class="col-xs-12">
              <h2 class="page-header">
                <i class="fa fa-globe"></i> NEOFORCE, SFA.
                <small class="pull-right">${transactionDate}</small>
              </h2>
            </div><!-- /.col -->
          </div>
          <!-- info row -->
          <div class="row invoice-info">
            <div class="col-sm-4 invoice-col">
              From
              <address>
                <strong>Techieplanet, Ltd.</strong><br>
                795 Folsom Ave, Suite 600<br>
                Ikeja, Lagos <br>
                Phone: (+234) 816-4334-657<br>
                Email: info@techieplanetltd.com
              </address>
            </div><!-- /.col -->
            <div class="col-sm-4 invoice-col">
              To
              <address>
                <strong>${customerInvoice.getFullName()}</strong><br>
                ${customerInvoice.getStreet()}<br>
                ${customerInvoice.getState()}<br>
                Phone: ${customerInvoice.getPhone()}<br>
                Email: ${customerInvoice.getEmail()}
              </address>
            </div><!-- /.col -->
            <div class="col-sm-4 invoice-col">
              <b>Invoice #</b><br>
              <br>
              <b>Order ID:</b> ${productOrderInvoice.getId()}<br>
            </div><!-- /.col -->
          </div><!-- /.row -->

          <!-- Table row -->
          <div class="row">
            <div class="col-xs-12 table-responsive">
              <table class="table table-bordered">
                <thead>
                  <tr>
                      <th>S/N</th>
                    <th>Qty</th>
                    <th>Product</th>
                    <th>Description</th>
                    <th>Subtotal</th>
                  </tr>
                </thead>
                <tbody>
                  
                    <c:forEach items="${orderItemInvoice}" var="item" varStatus="pointer">
                        <tr>
                            <td>${pointer.count}</td>
                            <td>${item.getQuantity()}</td>
                            <td>${item.getUnit().getProject().getName()} - ${item.getUnit().getTitle()}</td>
                            <td>${item.getUnit().getProject().getDescription()}</td>
                            <td><fmt:formatNumber value="${item.getInitialDep()}" type="currency" currencySymbol="N" /></td>
                        </tr>
                    </c:forEach>
                    
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4" style="text-align: right">Total : </td>
                        <td><fmt:formatNumber value="${totalInvoice}" type="currency" currencySymbol="N" /></td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: right">VAT : </td>
                        <td><fmt:formatNumber value="${vatInvoice}" type="currency" currencySymbol="N" /></td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: right">Gateway charge : </td>
                        <td><fmt:formatNumber value="${gatewayChargeInvoice}" type="currency" currencySymbol="N" /></td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: right">Grand Total : </td>
                        <td><fmt:formatNumber value="${grandTotalInvoice}" type="currency" currencySymbol="N" /></td>
                    </tr>
                </tfoot>
              </table>
                    <c:remove var="customerInvoice" scope="session" />
                    <c:remove var="orderItemInvoice" scope="session" />
                    <c:remove var="productOrderInvoice" scope="session" />
                    <c:remove var="transactionDate" scope="session" />
                    <c:remove var="totalInvoice" scope="session" />
                    <c:remove var="vatInvoice" scope="session" />
                    <c:remove var="gatewayChargeInvoice" scope="session" />
                    <c:remove var="grandTotalInvoice" scope="session" />
            </div><!-- /.col -->
            
            <div class="col-md-12">
                <a href="${pageContext.request.contextPath}/Dashboard" class="btn btn-primary">Continue to Dashboard</a>
            </div>
          </div><!-- /.row -->

          

          
        </section>
          
      </div>

  </body>