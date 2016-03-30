<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->

<!--        <link type="text/css" rel="stylesheet" href="plugins/rcswitcher-master/css/style.min.css">-->
	<link type="text/css" rel="stylesheet" href="plugins/rcswitcher-master/css/rcswitcher.min.css">
	
    
  
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
                      Waiting Agent List<br/>
                      <div id="removeMessage"></div>
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                     <div class="permissions block">
                  <table id="entitylist" class="table table-bordered table-striped table-hover tabSwitch">
                    <thead>
                      <tr>
                        <th>Image</th>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Last Name</th>
                        <th>Phone No</th>
                        <th>Email</th>
                        <th>State</th>
                        <th>Active</th>
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
                                
                                <td><c:out value="${agent.state}" /></td>
                                <td style="text-align:center;">
                                    <input id="switch-state" type="checkbox" name="status" value="status" onChange="checkActivateSwitchWait('${pageContext.request.contextPath}', 'Agent',${agent.agentId});"  <c:if test="${agent.active!='' && agent.active!=null && agent.active=='1'}">checked </c:if>   />
                                   
                                </td>
                              
                                <td>
                                    
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
                        
                        <th>State</th>
                        <th>Active</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
                  </table>
                 </div>
<!--                  <div class="permissions block">
				<h4>Permissions</h4>
			
				<label >Access CP</label><input type="checkbox" name="access_cp" value="access_cp"><br />
				<label >Manage Users </label><input type="checkbox" name="manage_users" value="manage_users" checked >

				<div class="info">
					<ul class="clear-fix">
						<li>width<span>44</span></li>
						<li>height<span>16</span></li>
						<li>Theme<span>dark</span></li>
						<li>blobOffset<span>2</span></li>
						<li>autoStick<span>true</span></li>
						<li>onText<span>YES</span></li>
						<li>offText<span>NO</span></li>
					</ul>
				</div>
			</div>-->
                   
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

<!--      <script type="text/javascript" src="plugins/rcswitcher-master/js/jquery-2.1.3.min.js"></script>-->
	
<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
<!--       <script src="plugins/bootstrap-switch/docs/js/jquery.min.js"></script>
    <script src="plugins/bootstrap-switch/docs/js/bootstrap.min.js"></script>-->
     
<!-- Include the bottom -->
<!--<script src="plugins/ios-checkboxes/jquery/iphone-style-checkboxes.js"></script>
  <script src="plugins/ios-checkboxes/jquery/highlight.pack.js"></script>
  <script src="plugins/ios-checkboxes/jquery.js"></script>-->

<%@ include file="../includes/bottom.jsp" %>
<script type="text/javascript" src="plugins/rcswitcher-master/js/rcswitcher.min.js"></script>
<!--<script src="plugins/bootstrap-switch/docs/js/highlight.js"></script>
    <script src="plugins/bootstrap-switch/dist/js/bootstrap-switch.js"></script>
    <script src="plugins/bootstrap-switch/docs/js/main.js"></script>-->
<script>
        
        $(function () {
           
    
            $("#entitylist").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": false, "width":"50px", "targets": 4 }
                ]
        });
    
 <c:forEach items="${agents}" var="agent">
                       var id = "row"+ <c:out value="${agent.agentId}" />;
                       $('#'+id+' :checkbox').rcSwitcher({

					// reverse: true,
					// inputs: true,
					width: 65,
					height: 24,
					blobOffset: 2,
					onText: 'YES',
					offText: 'NO',
					theme: 'flat',
				        autoFontSize: false,
					

				});
  </c:forEach>
    
                                
    
                                
      
          });
          
          
//    
//    $(document).ready(function() {
//      $('#switch-state').iphoneStyle();
//    });
//
//    $('#switch-state').iphoneStyle({
//  checkedLabel: 'YES',
//  uncheckedLabel: 'NO'
//});
  </script>
  