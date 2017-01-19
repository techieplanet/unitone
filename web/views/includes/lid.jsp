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
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/iCheck/all.css">

    <!--JQUERY UI CSS-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery-ui-1.12.1.css" type="text/css" />
    
    <!-- DataTables -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/datatables/dataTables.bootstrap.css">
    
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.min.css" type="text/css" />
     
    
    <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
          page. However, you can choose any other skin. Make sure you
          apply the skin class to the body tag so the changes take effect.
    -->
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/skin-blue.min.css" type="text/css" />-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/skins/skin-blue.min.css" type="text/css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css" type="text/css" />
    
    <!-- ICheck fronteed skin css-->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/line.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/green.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/blue.css" type="text/css" />
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.treetable.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/jquery.treetable.theme.default.css" type="text/css" />
    
    <!-- jQuery 2.1.4 -->
    <!--<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery-2.1.4.min.js"></script>
    
    
    <!--JQUERY UI JS-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-ui-1.12.1.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.treetable.js"></script>
    <!-- JQUERY PrintArea -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.PrintArea.js"></script>
    
    <!-- CKEditor plugin -->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ckeditor/ckeditor.js"></script>
    
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
    
    <!-- ngwidgets -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/js/ngwidgets/styles/ngx.base.css" type="text/css" />
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/angular.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngwidgets/ngxcore.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngwidgets/ngxbuttons.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngwidgets/ngxscrollbar.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngwidgets/ngxlistbox.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngwidgets/ngxdata.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngwidgets/ngxinput.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/ngwidgets/ngxdropdownlist.js"></script>
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
  <body class="hold-transition skin-blue sidebar-mini">
    <div class="wrapper">
