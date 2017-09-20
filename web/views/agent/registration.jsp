<%-- 
    Document   : registration
    Created on : Oct 24, 2016, 10:24:47 AM
    Author     : Prestige
--%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->

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
                      <h1>Agent Registration Form</h1>
                  </div>
              </div>
          </header>
          
          <div class="jumbotron" style="background-color: #fff">
          
            <c:set var="isCorporate"  value="${(action.equals('edit') && agent.isCorporate())||(action.equals('new') && corporate == true)}" />
                <c:if test="${!isCorporate}"> <%@ include file="form.jsp" %> </c:if >
            <c:if test="${isCorporate}"> <%@ include file="corporate_form.jsp" %> </c:if>
         </div>
                            
       <!--Error Modal-->
        <div class="modal fade" id="agentErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Agent Registration Error</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary pull-right" data-dismiss="modal">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
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
               
                            
                            
                            
      <div class="modal fade" id="agreementStatusModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <%@ include file="_agreement.jsp" %>  
      </div><!-- /.modal -->
         
          
      
      
      
  
   
       
      
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
 
    <!-- iCheck 1.0.1 -->
    <script src="plugins/iCheck/icheck.min.js"></script>
    

    <!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
    
    
    <!-- AdminLTE App -->
    <!--<script src="dist/js/app.min.js"></script>-->
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
      </script>
      
  </body>    
</html>