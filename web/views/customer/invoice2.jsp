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
          <table class="table">
          <!-- title row -->
          <tr>
            <td colspan="3">
                <i class="fa fa-globe"></i> NEOFORCE, SFA.
            </td>
            <td>
                <small>28/10/2016</small>
            </td><!-- /.col -->
          </tr>
          <!-- info row -->
          <tr>
              <td>
              From
              <address>
                <strong>Techieplanet, Ltd.</strong><br>
                795 Folsom Ave, Suite 600<br>
                Ikeja, Lagos <br>
                Phone: (+234) 816-4334-657<br>
                Email: info@techieplanetltd.com
              </address>
              </td>
            <td>
              To
              <address>
                <strong>Kehinde Odutan</strong><br>
                12 view park Ikotun egbe<br>
                Lagos<br>
                Phone: 08164334657<br>
                Email: kennyodutan@gmail.com
              </address>
            </td><!-- /.col -->
            <td>
              <b>Invoice #</b><br>
              <br>
              <b>Order ID:</b> ${productOrderInvoice.getId()}<br>
           </td><!-- /.row -->
          </tr>
          </table>
            
            
            
          <!-- Table row -->
          <div class="row">
            <div class="col-xs-12 table-responsive">
              <table class="table table-striped">
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
                  
                        <tr>
                            <td>1</td>
                            <td>2</td>
                            <td>Kali Homes - 3 bedroom flat apartment</td>
                            <td>Situated in a very friendly and peaceful neighborhood </td>
                            <td>1500000.00</td>
                        </tr>
                </tbody>
                <tfoot style="border:solid 1px #eee">
                    <tr>
                        <td colspan="4" style="text-align: right">Total</td>
                        <td>1500000.00</td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: right">VAT</td>
                        <td>0.00</td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: right">Gateway charge</td>
                        <td>0.00</td>
                    </tr>
                    <tr>
                        <td colspan="4" style="text-align: right">Grand Total</td>
                        <td>1500000.00</td>
                    </tr>
                </tfoot>
              </table>
            </div><!-- /.col -->
            
            
          </div><!-- /.row -->

          

          
        </section>
          
      </div>

  </body>