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
              <a href="Agent"> Agents</a>
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
                      Agent List<br/><br/>
                      <c:if test="${fn:contains(sessionScope.user.permissions, 'waiting_agent')}">
                        <a href="Agent?action=waiting">View Waiting List</a>
                      </c:if>
                        
                      <span class="pull-right">
                          <c:if test="${fn:contains(sessionScope.user.permissions, 'create_agent')}">
                            <a class="btn btn-primary" href="Agent?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Agent</a>
                          </c:if>
                      </span>
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                 <!--<div class="permissions block">-->
                  <table id="entitylist" class="table table-bordered table-striped"  style="font-size: 14px;">
                    <thead>
                      <tr>
                        <th>SN</th>
                        <th>Image</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Last Name</th>
                        <th>Phone No</th>
                        <th>State</th>
                        <th>Active</th>
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent') || fn:contains(sessionScope.user.permissions, 'edit_agent') || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                            <th class="text-center">Action</th>
                        </c:if>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${agents}" var="agent" varStatus="pointer">
                            <tr id="row<c:out value="${agent.agentId}" />">
                                <td><c:out value="${pointer.count}" /></td>
                                <td><img src="${agentImageAccessDir}/${agent.photoPath}" width='55' height='50'/></td>
                                <td><c:out value="${agent.firstname}" /></td>
                                <td><c:out value="${agent.middlename}" /></td>
                                <td><c:out value="${agent.lastname}" /></td>
                                <td><c:out value="${agent.phone}" /></td>
                                <td><c:out value="${agent.state}" /></td>
                                <td style="text-align:center;">
                                    <input id="switch-state" type="checkbox" name="status" value="status" onChange="checkActivateSwitch('${pageContext.request.contextPath}', 'Agent',${agent.agentId});"  <c:if test="${agent.active!='' && agent.active!=null && agent.active=='1'}">checked </c:if>   />
                                </td>
                                
                                <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent') || fn:contains(sessionScope.user.permissions, 'edit_agent') || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                                    <td class="text-center">
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')}">
                                            <a class="btn btn-primary btn-xs anti-rcswitchwer-buttons" href="Agent?action=view&route=approved&agentId=${agent.agentId}" role="button"><i class="fa fa-search"></i></a>
                                        </c:if>   
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')}">
                                        <a class="btn btn-primary btn-xs anti-rcswitchwer-buttons" href="#" onclick="agentHistory.getAgentHistory('${agent.agentId}',event)" role="button"><i class="fa fa-dollar"></i></a>
                                        </c:if>
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'edit_agent')}">
                                            <a class="btn btn-success btn-xs anti-rcswitchwer-buttons" href="Agent?action=edit&agentId=${agent.agentId}" role="button"><i class="fa fa-pencil"></i></a> 
                                        </c:if>
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                                            <a class="btn btn-danger btn-xs anti-rcswitchwer-buttons" href="#" onclick="showDeleteModal('${pageContext.request.contextPath}', 'Agent', <c:out value="${agent.agentId}"/>)" role="button"><i class="fa fa-remove"></i></a>
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
                        <th>Active</th>
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent') || fn:contains(sessionScope.user.permissions, 'edit_agent') || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                            <th class="text-center">Action</th>
                        </c:if>
                      </tr>
                    </tfoot>
                  </table>
                 <!--</div>-->
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
                    <div class="box-header">
                        <h3 class="box-title block">
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_agent')}">
                                <span class="pull-right"><a class="btn btn-primary" href="Agent?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Agent</a></span>
                            </c:if>
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
      
      <!--MODAL-->
      <div class="modal fade" id="accountStatementModal"  tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog" >
          <div class="modal-content" >
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Account Statement</h4>
            </div>
            <div class="modal-body" id="printArea">
                <p>Account Code : <span id="accountCode"></span></p>
                <p>Account Balance : <span id="accountBalance"></span></p>
                
                <div class="table-responsive">
                    
                    <table class="table table-striped table-hover" id="account_statement_table">
                        <thead>
                            <tr>
                                <td>SN</td>
                                <td>Date</td>
                                <td>Amount</td>
                                <td>Type</td>
                            </tr>
                        </thead>
                        <tbody>
                            
                        </tbody>
                    </table>
                    
                </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="agentHistory.printAccountStatement()" class="btn btn-primary"><i class="fa fa-print"></i> Print</button>
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
                    { "width":"100px", "targets": 3 },
                    {"sort":"asc","targets":0},
                    <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent') || fn:contains(sessionScope.user.permissions, 'edit_agent') || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                        { "sortable": false, "width":"120px", "targets": 8 }
                    </c:if>
                ]
        });
    
            <c:forEach items="${agents}" var="agent">
                       var id = "row"+ <c:out value="${agent.agentId}" />;
                       $('#'+id+' :checkbox').rcSwitcher({
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
          

      var agentHistory = {
          
          getAgentHistory : function(id,evt){
              
              evt.preventDefault();
              
              $.ajax({
                 
                 url : '${pageContext.request.contextPath}/Agent?action=wallet',
                 method : "GET",
                 data : {id : id},
                 success : function(data){
                     
                     console.log(data);
                     agentHistory.prepareAccountStatement(JSON.parse(data));
                     
                 },
                 error : function(xhr,status_code,status_text){
                     console.log(xhr.responseText);
                 }
                  
              });
          },
          
          prepareAccountStatement : function(jsonData){
            
             var balance = accounting.formatMoney(jsonData.agentDetail.balance,"N",2,",",".");
             var accountCode = jsonData.agentDetail.accountCode;
             
             var transactions = jsonData.transactions;
             
             $("#accountCode").text(accountCode);
             $("#accountBalance").text(accounting.formatMoney(balance,"N",2,",","."));
             
             var count = 1;
             
             //Clear the table body
             $("#accountStatementModal #account_statement_table tbody").html("");
             
             for(var k in transactions){
                 
                 var amount = accounting.formatMoney(transactions[k].amount,"N",2,",",".");
                 var date = transactions[k].date;
                 var type = transactions[k].type;
                 
                 var tr = "<tr>";
                 
                 tr += "<td>" + count + "</td>";
                 tr += "<td>" + date + "</td>";
                 tr += "<td>" + amount + "</td>";
                 tr += "<td>" + type + "</td>";
                 
                 count++;
                 
                 $("#accountStatementModal #account_statement_table tbody").append(tr);
                 
             }
             
             $("#accountStatementModal").modal();
            
          },
          
          printAccountStatement : function(){
              
              var options = {
                  mode:"iframe",
                  popClose: true
              }
              $("#printArea").printArea(options);
              /**
              var divToPrint=document.getElementById('printArea');

              var newWin = window.open('','Print-Window');

              newWin.document.open();

              newWin.document.write('<html><body onload="window.print()">'+divToPrint.innerHTML+'</body></html>');

              newWin.document.close();

              setTimeout(function(){newWin.close();},10);
              **/
              
          }
          
      };
  </script>
  