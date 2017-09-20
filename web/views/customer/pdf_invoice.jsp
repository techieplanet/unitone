<%-- 
    Document   : success
    Created on : Oct 24, 2016, 3:33:16 PM
    Author     : JP
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<html>
    <head>
        <meta charset="utf-8"/>
        <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

        <title>NeoForce | Invoice</title>
        <!-- Tell the browser to be responsive to screen width -->
        <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport"/>

        <link rel="stylesheet" href="css/bootstrap.css" type="text/css" />
        <!-- Font Awesome -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css"/>


        <!-- Ionicons -->
        <link rel="stylesheet" href="css/ionicons.min.css"/>

        <!-- iCheck for checkboxes and radio inputs -->
        <link rel="stylesheet" href="plugins/iCheck/all.css"/>


        <!-- DataTables -->
        <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css"/>

        <!-- bootstrap wysihtml5 - text editor -->
        <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css"/>

        <!-- Theme style -->
        <link rel="stylesheet" href="css/AdminLTE.min.css" type="text/css" />


        <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
              page. However, you can choose any other skin. Make sure you
              apply the skin class to the body tag so the changes take effect.
        -->
        <!--<link rel="stylesheet" href="/NeoForce/css/skin-blue.min.css" type="text/css" />-->
        <link rel="stylesheet" href="css/skins/skin-blue.min.css" type="text/css" />

        <link rel="stylesheet" href="css/custom.css" type="text/css" />
        <!-- jQuery 2.1.4 -->
        <!--<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>-->
        <script type="text/javascript" src="js/jQuery-2.1.4.min.js"></script>


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

    <body style="background-color: #eee">

        <div class="container">

            <section class="invoice" >
                <!-- title row -->
                <div class="row">
                    <div >
                        <h2 class="page-header">
                            ${companyName}
                            <small class="pull-right">${transactionDate}</small>
                        </h2>
                    </div><!-- /.col -->
                </div>
                <!-- info row -->
                <div class="row invoice-info">
                    <div class="col-sm-6 " style="float:left;width:40%;">
                        From
                        <address>
                            <strong>${companyName}</strong><br/>
                            ${companyAddress}<br/>
                            Phone: ${companyPhone}<br/>
                            Email: ${companyEmail} 
                        </address>
                    </div><!-- /.col -->
                    <div class="col-sm-6 " style="float:left;width:40%;">
                        To
                        <address>
                            <strong>${customerInvoice.getFullName()}</strong><br/>
                            ${customerInvoice.getStreet()}<br/>
                            ${customerInvoice.getState()}<br/>
                            Phone: ${customerInvoice.getPhone()}<br/> 
                            Email: ${customerInvoice.getEmail()}
                        </address>
                    </div><!-- /.col -->
                    <div class="col-sm-4 invoice-col">
                        <b>Invoice #${lodgement.getId()}</b><br/>
              <b>Transaction Date: ${lodgement.getCreatedDate()}</b><br/>
                        
                    </div><!-- /col -->
                </div><!-- /.row -->

                <!-- Table row -->
                <div class="row" >
                    <div class="table-responsive">
                        <table class="table table-bordered" >
                            <thead>
                                <tr>
                                    <th style="text-align:center">S/N</th>
                                    <th>Description</th>
                                    <th style="text-align:center">Qty</th>
                                    <th style="text-align:center">Amount (&#8358;)</th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:set var="L_ID" value="0" />
                                <c:forEach items="${orderItemInvoice}" var="LI" varStatus="pointer">
                                    <c:if test="${L_ID == 0}"><c:set var="L_ID" value="${LI.getLodgement().getId()}" /></c:if>
                                        <tr>
                                            <td style="text-align:center">${pointer.count}</td>
                                        <td>${LI.getItem().getUnit().getProject().getName()} - ${LI.getItem().getUnit().getTitle()}</td>
                                        <td style="text-align:center">${LI.getItem().getQuantity()}</td>
                                        <c:set var="rewardAmount" value="${LI.getRewardAmount() !=null ? LI.getRewardAmount() : 0}" />
                                        <td >${String.format("%1$,.2f",LI.getAmount() + rewardAmount)}</td>
                                    </tr>
                                </c:forEach>


                            </tbody>
                            <tfoot>
                                <tr>
                                    <td colspan="3" style="text-align:right">Total : </td>
                                    <td>${String.format("%1$,.2f",totalInvoice)}</td>
                                </tr>
                                <tr>                        <td colspan="3" style="text-align:right">VAT : </td>

                                    <td>${String.format("%1$,.2f",vatInvoice)}</td>
                                </tr>
                                <tr>
                                    <td colspan="3" style="text-align:right">Gateway charge : </td>
                                    <td>${String.format("%1$,.2f",gatewayChargeInvoice)}</td>
                                </tr>
                                <tr>
                                    <td colspan="3" style="text-align:right">Grand Total : </td>
                                    <td>${String.format("%1$,.2f",grandTotalInvoice)}</td>
                                </tr>
                            </tfoot>
                        </table>
                        
                    </div><!-- /.col -->


                </div><!-- /.row -->
                <div class="row pull-right">
                    <h3 >NEOFORCE SFA.</h3>
                    <h4>Powered By Techie Planet</h4>
                </div>
            </section>


        </div>
    </body>
</html>