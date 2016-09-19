<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>      

<%@ include file="../includes/sidebar.jsp" %>   


<!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
              <a href="#">Customers</a>
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
      

<script>
      $(function () {
        //Add text editor
        $("#compose-textarea").wysihtml5();
      });
    </script>