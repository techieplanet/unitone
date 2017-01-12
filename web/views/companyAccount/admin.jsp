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
              <a href="${pageContext.request.contextPath}/CompanyAccount">Company Account</a>
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
                      Company's Accounts List
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                 <!--<div class="permissions block">-->
                  <table id="entitylist" class="table table-bordered table-striped"  style="font-size: 14px;">
                    <thead>
                      <tr>
                        <th>SN</th>
                        <th>Account Number</th>
                        <th>Account Name</th>
                        <!--<th>Active</th>-->
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'edit_company_account') || fn:contains(sessionScope.user.permissions, 'delete_company_account')}">
                            <th class="text-center">Action</th>
                        </c:if>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${companyAccounts}" var="account" varStatus="pointer">
                            <tr id="row<c:out value="${account.getId()}" />">
                                <td><c:out value="${pointer.count}" /></td>
                                <td><c:out value="${account.getAccountNumber()}" /></td>
                                <td><c:out value="${account.getAccountName()}" /></td>
                                
                                
                               <c:if test="${fn:contains(sessionScope.user.permissions, 'edit_company_account') || fn:contains(sessionScope.user.permissions, 'delete_company_account')}">
                                    <td class="text-center">
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'edit_company_account')}">
                                            <a class="btn btn-success btn-xs anti-rcswitchwer-buttons" href="CompanyAccount?action=edit&accountId=${account.getId()}" role="button"><i class="fa fa-pencil"></i></a> 
                                        </c:if>
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                                            <a class="btn btn-danger btn-xs anti-rcswitchwer-buttons" href="#" onclick="showDeleteModal('${pageContext.request.contextPath}', 'Agent', <c:out value="${account.getId()}"/>)" role="button"><i class="fa fa-remove"></i></a>
                                        </c:if>
                                    </td>
                                </c:if>
                                    
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                      <tr>
                        <th>SN</th>
                        <th>Account Number</th>
                        <th>Account Name</th>
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'edit_company_account') || fn:contains(sessionScope.user.permissions, 'delete_company_account')}">
                            <th class="text-center">Action</th>
                        </c:if>
                      </tr>
                    </tfoot>
                  </table>
                 
                    
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
                    { "width":"100px", "targets": 3 },
                    {"sort":"asc","targets":0},
                    <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent') || fn:contains(sessionScope.user.permissions, 'edit_agent') || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                        { "sortable": false, "width":"80px", "targets":7 }
                    </c:if>
                ]
        });
    
            
    
                                
    
                                
      
          });
          

      var companyAccount = {
          
          getAgentHistory : function(id,evt){
              
              evt.preventDefault();
              
              $.ajax({
                 
                 url : '${pageContext.request.contextPath}/Agent?action=wallet',
                 method : "GET",
                 data : {id : id},
                 success : function(data){
                     
                     console.log(data);
                     companyAccount.prepareAccountStatement(JSON.parse(data));
                     
                 },
                 error : function(xhr,status_code,status_text){
                     console.log(xhr.responseText);
                 }
                  
              });
          },
          
          prepareAccountStatement : function(jsonData){
            
             var balance = accounting.formatMoney(jsonData.agentDetail.balance,"N",2,",",".");
             var accountCode = jsonData.agentDetail.accountCode;
             var ledgerBalance = jsonData.agentDetail.ledgerBalance;
             
             var transactions = jsonData.transactions;
             
             $("#accountCode").text(accountCode);
             $("#accountBalance").text(accounting.formatMoney(balance,"N",2,",","."));
             $("#ledgerBalance").text(accounting.formatMoney(ledgerBalance,"N",2,",","."));
             
             var count = 1;
             
             //Clear the table body
             $("#accountStatementModal #account_statement_table tbody").html("");
             
             for(var k in transactions){
                 
                 var amount = accounting.formatMoney(transactions[k].amount,"N",2,",",".");
                 var date = transactions[k].date;
                 var type = transactions[k].type;
                 
                 var creditAmount = "";
                 var debitAmount = "";
                 
                 console.log("Type : " + type);
                 
                 if(type === "credit"){
                     creditAmount = amount;
                 }
                 else{
                     debitAmount = amount;
                 }
                 
                 var tr = "<tr>";
                 
                 tr += "<td>" + count + "</td>";
                 tr += "<td>" + date + "</td>";
                 tr += "<td>" + debitAmount + "</td>";
                 tr += "<td>" + creditAmount + "</td>";
                 
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
  
