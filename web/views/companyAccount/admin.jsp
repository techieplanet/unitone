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
                      
                      <a href="${pageContext.request.contextPath}/CompanyAccount?action=new" class="btn btn-primary pull-right">New Company Account</a>
                  </h3>
                  
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                 <!--<div class="permissions block">-->
                  <table id="entitylist" class="table table-bordered table-striped"  style="font-size: 14px;">
                    <thead>
                      <tr>
                        <th>SN</th>
                        <th>Bank Name</th>
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
                                <td><c:out value="${account.getBankName()}" /></td>
                                <td><c:out value="${account.getAccountNumber()}" /></td>
                                <td><c:out value="${account.getAccountName()}" /></td>
                                
                                
                               <c:if test="${fn:contains(sessionScope.user.permissions, 'edit_company_account') || fn:contains(sessionScope.user.permissions, 'delete_company_account')}">
                                    <td class="text-center">
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'edit_company_account')}">
                                            <a class="btn btn-success btn-xs anti-rcswitchwer-buttons" href="CompanyAccount?action=edit&id=${account.getId()}" role="button"><i class="fa fa-pencil"></i></a> 
                                        </c:if>
                                        <c:if test="${fn:contains(sessionScope.user.permissions, 'delete_company_account')}">
                                            <a class="btn btn-danger btn-xs anti-rcswitchwer-buttons" href="#" onclick="companyAccount.showDeleteModal('${pageContext.request.contextPath}/CompanyAccount?action=delete&id=${account.getId()}',event)" role="button"><i class="fa fa-remove"></i></a>
                                        </c:if>
                                    </td>
                                </c:if>
                                    
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                      <tr>
                        <th>SN</th>
                        <th>Bank Name</th>
                        <th>Account Number</th>
                        <th>Account Name</th>
                        <c:if test="${fn:contains(sessionScope.user.permissions, 'edit_company_account') || fn:contains(sessionScope.user.permissions, 'delete_company_account')}">
                            <th class="text-center">Action</th>
                        </c:if>
                      </tr>
                    </tfoot>
                  </table>
                 
                    
                </div><!-- /.box-body -->
                
                <div class="box-footer">
                    <a href="${pageContext.request.contextPath}/CompanyAccount?action=new" class="btn btn-primary pull-right">New Company Account</a>
                </div>
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
              <button id="ok" type="button" onclick="companyAccount.deleteCompanyAccount()" class="btn btn-primary">OK</button>
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
                    {"sort":"asc","targets":0}
                ]
        });
    });
          

      var companyAccount = {
          
          url : "",
          
          showDeleteModal : function(url, evt){
            
            evt.preventDefault();
            
            companyAccount.url = url;
            
            $("#deleteModal").modal({
                
                backDrop : false
                
            });
            
            
          },
          
          deleteCompanyAccount : function(){
              
              $("#deleteModal").modal("hide");
              location.href = companyAccount.url;
          }
          
      };
  </script>
  
