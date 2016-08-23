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
            Users
            <!--<small>Optional description</small>-->
          </h1>
<!--          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
            <li class="active">Here</li>
          </ol>-->
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Your Page Content Here -->
          <div class="box">
                <div class="box-header">
                  <h3 class="box-title block">
                      Users List
                      <span class="pull-right">
                          <a class="btn btn-primary" href="User?action=new" user="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New User</a>
                      </span>
                  </h3>
                </div><!-- /.box-header -->
                
                <div class="box-body">
                  <table id="entitylist" class="table table-bordered table-striped">
                    <thead>
                      <tr>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th class="text-center">Action</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${users}" var="user">
                            <tr id="row<c:out value="${user.userId}" />">
                                <td><c:out value="${user.userId}" /></td>
                                <td><c:out value="${user.firstname}" /></td>
                                <td><c:out value="${user.lastname}" /></td>
                                <td><c:out value="${user.email}" /></td>
                                <td><c:out value="${user.role.title}" /></td>
                                <td class="text-center">
                                    <a class="btn btn-success btn-xs" href="User?action=edit&id=<c:out value="${user.userId}"/>" user="button"><i class="fa fa-pencil"></i></a>
                                    <a class="btn btn-danger btn-xs" href="#" onclick="showDeleteModal('${pageContext.request.contextPath}', 'User', <c:out value="${user.userId}"/>)" user="button"><i class="fa fa-remove"></i></a>
                                </td>
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                      <tr>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Email</th>
                        <th>Role</th>
                        <th class="text-center">Action</th>
                      </tr>
                    </tfoot>
                  </table>
                  
                    <div class="box-header">
                        <h3 class="box-title block">
                            <span class="pull-right">
                                <a class="btn btn-primary" href="User?action=new" user="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New User</a>
                            </span>
                                
                        </h3>
                    </div><!-- /.box-header -->
                        
                        
                </div><!-- /.box-body -->
              </div><!-- /.box -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <!--MODAL-->
      <div class="modal fade " id="deleteModal" tabindex="-1" user="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog delete-modal">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">${APP_NAME}</h4>
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

      
<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


<script>
        
        $(function () {
            $("#entitylist").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "width":"50px", "targets": 0 },
                    { "width":"200px", "targets": 3 },
                    { "sortable": false, "width":"100px", "targets": 5 }
                ]
        });
    
      
          });
    </script>