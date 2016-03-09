<!-- Include the lid -->
<link href="plugins/bootstrap-switch/docs/css/bootstrap.min.css" rel="stylesheet">
    <link href="plugins/bootstrap-switch/docs/css/highlight.css" rel="stylesheet">
    <link href="plugins/bootstrap-switch/dist/css/bootstrap3/bootstrap-switch.css" rel="stylesheet">
    <link href="http://getbootstrap.com/assets/css/docs.min.css" rel="stylesheet">
    <link href="plugins/bootstrap-switch/docs/css/main.css" rel="stylesheet">
    
<%@ include file="../includes/lid.jsp" %>      
 
<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>      

<%@ include file="../includes/sidebar.jsp" %>   


<!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
              <a href="Agent"> Agents</a>
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
        
          
                <%@ include file="form.jsp" %>      
            
          
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->


<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
     
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
      
<script src="plugins/bootstrap-switch/docs/js/highlight.js"></script>
    <script src="plugins/bootstrap-switch/dist/js/bootstrap-switch.js"></script>
    <script src="plugins/bootstrap-switch/docs/js/main.js"></script>

<script>
      $(function () {
        //Add text editor
        $("#compose-textarea").wysihtml5();
      });
    </script>