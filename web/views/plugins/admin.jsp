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

            <a href="${pageContext.request.contextPath}/Project" class="blacktext">Plugins</a>

          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-md-3">
                <%@ include file="plugins.jsp" %>
            </div>
              
            <div class="col-md-9">
                <form name="projectform" id="projectform" method="POST" action="Project?action=${action}&id=${id}"> 
                    <div class="box box-primary">
                      <div class="box-header with-border">
                        <h3 class="box-title">
                            Working With Plugins
                        </h3>
                      </div><!-- /.box-header -->

                      <div class="box-body">
                          Select a plugin on the left to modify its attributes. You can
                            <ul>
                                <li>Install a plugin</li>
                                <li>Activate/deactivate a plugin</li> 
                                <li>Change settings for a plugin</li>
                            </ul>
                      </div>

                    </div><!-- /. box -->
                </form>
                
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