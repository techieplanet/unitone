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
            <a href="${pageContext.request.contextPath}" class="">NEOFORCE</a>
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
              
            <div class="col-md-12">
                <c:choose>
                    <c:when test="${action == 'forbidden'}">
                        You do not have access to perform that action.
                    </c:when>    
                    <c:otherwise>
                    </c:otherwise>
                </c:choose>
                
            </div><!-- /.col -->
          </div><!-- /.row -->
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