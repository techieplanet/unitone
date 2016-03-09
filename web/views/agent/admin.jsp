<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
            Agents
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
                      Agent List
                      <span class="pull-right">
                          <a class="btn btn-primary" href="Agent?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Agent</a>
                      </span>
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                  <table id="entitylist" class="table table-bordered table-striped table-hover">
                    <thead>
                      <tr>
                        <th>Image</th>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Last Name</th>
                        <th>Phone No</th>
                        <th>Email</th>
                        <th>Street</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Status</th>
                        <th>Action</th>
                        
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${agents}" var="agent">
                            <tr id="row<c:out value="${agent.agentId}" />">
                                <td><img src="${pageContext.request.contextPath}/images/uploads/agents/${agent.photoPath}" width='55' height='50'/></td>
                                <td><c:out value="${agent.agentId}" /></td>
                                <td><c:out value="${agent.firstname}" /></td>
                                <td><c:out value="${agent.middlename}" /></td>
                                <td><c:out value="${agent.lastname}" /></td>
                                <td><c:out value="${agent.phone}" /></td>
                                <td><c:out value="${agent.email}" /></td>
                                <td><c:out value="${agent.street}" /></td>
                                <td><c:out value="${agent.city}" /></td>
                                <td><c:out value="${agent.state}" /></td>
                                <td>
                                    <input id="switch-state" type="checkbox" onChange="checkActivateSwitch('${pageContext.request.contextPath}', 'Agent',${agent.agentId});" data-switch-get="state" <c:if test="${agent.active!='' && agent.active!=null && agent.active=='1'}">checked </c:if> data-size="mini" data-on-text="Deactivate" data-off-text="Activate" data-on-color="danger" data-off-color="success" data-label-text="Status" /></td>
                              
                                <td>
                                    <a class="btn btn-success btn-xs" href="Agent?action=view&agentId=${agent.agentId}&id=${agent.agentId}" role="button"><i class="fa fa-search"></i></a>
                                    
                                    <a class="btn btn-primary btn-xs" href="Agent?action=edit&agentId=${agent.agentId}&id=${agent.agentId}" role="button"><i class="fa fa-pencil"></i></a>
                                     
                                     <a class="btn btn-danger btn-xs" href="#" onclick="showDeleteModal('${pageContext.request.contextPath}', 'Agent', <c:out value="${agent.agentId}"/>)" role="button"><i class="fa fa-remove"></i></a>
                                    
                                   
                                </td>
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                      <tr>
                        <th>Image</th>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Last Name</th>
                        <th>Phone No</th>
                        <th>Email</th>
                        <th>Street</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
                  </table>
                  
                    <div class="box-header">
                        <h3 class="box-title block">
                            <span class="pull-right"><a class="btn btn-primary" href="Agent?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Agent</a></span>
                        </h3>
                    </div><!-- /.box-header -->
                </div><!-- /.box-body -->
              </div><!-- /.box -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <!--MODAL-->
      <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE</h4>
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
       <script src="plugins/bootstrap-switch/docs/js/jquery.min.js"></script>
    <script src="plugins/bootstrap-switch/docs/js/bootstrap.min.js"></script>
     
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>

<script src="plugins/bootstrap-switch/docs/js/highlight.js"></script>
    <script src="plugins/bootstrap-switch/dist/js/bootstrap-switch.js"></script>
    <script src="plugins/bootstrap-switch/docs/js/main.js"></script>
<script>
        
        $(function () {
           
    
            $("#entitylist").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": false, "width":"50px", "targets": 4 }
                ]
        });
    
      
          });
    </script>