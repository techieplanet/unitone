<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->

<!--        <link type="text/css" rel="stylesheet" href="plugins/rcswitcher-master/css/style.min.css">-->
	<!--<link type="text/css" rel="stylesheet" href="plugins/rcswitcher-master/css/rcswitcher.min.css">-->
	
  
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
              <a href="Agent">Agents</a>
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
                        <th>SN</th>
                        <th>Image</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Last Name</th>
                        <th>Phone No</th>
                        <th>State</th>
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'approve_agent')}">
                            <th class="text-center">Approve</th>
                        </c:if>
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')  || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                            <th>Action</th>
                        </c:if>
                        
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${agents}" var="agent" varStatus="pointer">
                            <tr id="row<c:out value="${pointer.count}" />">
                                <td>${pointer.count}</td>
                                <td><%//<img src="/uploads/NeoForce/images/customers/customer_150166987523.png" width='55' height='50'/>%>
                                   <img src="${agentImageAccessDir}/${agent.photoPath}" alt="no image" width='55' height='50'/></td>
                                <td><c:out value="${agent.firstname}" /></td>
                                <td><c:out value="${agent.middlename}" /></td>
                                <td><c:out value="${agent.lastname}" /></td>
                                <td><c:out value="${agent.phone}" /></td>
                                
                                <td><c:out value="${agent.state}" /></td>
                                <c:if test="${fn:contains(sessionScope.user.permissions, 'approve_agent')}">
                                    <td style="text-align:center;">
                                        <input type="checkbox" class="minimal switch-state" value = '<c:out value="${agent.agentId}"/>' <c:if test="${agent.active!='' && agent.active!=null && agent.active=='1'}">checked </c:if>  />
                                    </td>
                                </c:if>
                              
                                <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')  || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                                    <td>
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')}">
                                            <a class="btn btn-primary btn-xs" title="View ${agent.firstname.trim()} Informations" href="Agent?action=view&route=waiting&agentId=${agent.agentId}" role="button" ><i class='fa fa-eye'></i></a>
                                        </c:if>

                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                                            <a class="btn btn-danger btn-xs" href="#" onclick="showDeleteModal('${pageContext.request.contextPath}', 'Agent', <c:out value="${agent.agentId}"/>)" role="button" title="Delete ${agent.firstname.trim()} "><i class="fa fa-remove"></i></a>
                                        </c:if>
                                    </td>
                                </c:if>
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                      <tr>
                        <th>SN</th>
                        <th>Image</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Last Name</th>
                        <th>Phone No</th>                        
                        <th>State</th>
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'approve_agent')}">
                            <th class="text-center">Approve</th>
                        </c:if>
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')  || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                            <th>Action</th>
                        </c:if>
                      </tr>
                    </tfoot>
                  </table>
                 </div>
                   
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

      
       <!--MODAL-->
      <div class="modal fade" id="activateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="cancel2"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE</h4>
            </div>
            <div class="modal-body">
              You are about to activate this person as an agent. <br/><br/>

Please be sure that you have verified their information and are satisfied as this person will become a representative of your organisation. If you are sure, please click 'Yes' or 'Cancel' if not sure.
<br/><br/>
Please note this record will be moved to the general agents list immediately after you click 'Yes'.
<br/><br/>
Are you sure you want to proceed?

            </div>
            <div class="modal-footer">
              <button type="button" id="cancel" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="" class="btn btn-primary">Yes</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
	
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
                    <c:choose>
                        <c:when test="${fn:contains(sessionScope.user.permissions, 'approve_agent') && (fn:contains(sessionScope.user.permissions, 'view_agent') || fn:contains(sessionScope.user.permissions, 'edit_agent') || fn:contains(sessionScope.user.permissions, 'delete_agent'))}">
                            { "sortable": false, "width":"50px", "targets": 8 }
                        </c:when>
                        <c:when test="${!fn:contains(sessionScope.user.permissions, 'approve_agent') && (fn:contains(sessionScope.user.permissions, 'view_agent') || fn:contains(sessionScope.user.permissions, 'edit_agent') || fn:contains(sessionScope.user.permissions, 'delete_agent'))}">
                            { "sortable": false, "width":"50px", "targets": 7 }
                        </c:when>
                    </c:choose>
                ]
        });
    
                
      
          });
          
          
          $(document).ready(function(){
                console.log("document ready");
                $('.switch-state').on('ifChecked', function(event){
                        var agentId = $(this).val();
                        var status = $(this).is(':checked') ? 1 : 0;
                        showActivateModal('${pageContext.request.contextPath}', 'Agent', agentId, status );
                        //console.log("inside switch-state");                        
               });
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
  