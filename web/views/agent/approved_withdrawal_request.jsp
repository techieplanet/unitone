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
              <a href="Agent"> Approved Withdrawal Request </a>
              
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
                     Withdrawal Request List
                     <c:if test="${fn:contains(sessionScope.user.permissions, 'agent_commission_disbursement')}">
                     <a href="${pageContext.request.contextPath}/Agent?action=makePayout" class="btn btn-success pull-right" onclick="makePayout()">Pay out and download GAPs csv <i class="fa fa-download"> </i></a>
                     </c:if>
                     </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                 <!--<div class="permissions block">-->
                  <table id="entitylist" class="table table-bordered table-striped"  style="font-size: 14px;">
                    <thead>
                      <tr>
                        <th>SN</th>
                        <th>Name</th>
                        <th>Phone No</th>
                         <th>Date</th>
                        <th>Requested Amount</th>
                       
                      </tr>
                    </thead>
                    <tbody>
                        <c:set var="total" value="${0}" />
                        <c:forEach items="${withdrawals}" var="withdrawal" varStatus="pointer">
                            <tr>
                                <td>${pointer.count}</td>
                                <td>${withdrawal.getAgent().getFullName()}</td>
                                <td>${withdrawal.getAgent().getPhone()}</td>
                                <td><fmt:formatDate value="${withdrawal.getDate()}" /></td>
                                <td style="text-align:right"><fmt:formatNumber value="${withdrawal.getAmount()}" currencySymbol="N" type="currency"  /></td>
                                
                            </tr>
                            <c:set var="total" value="${total + withdrawal.getAmount()}" />
                        </c:forEach>
                            
                  </tbody>
                    <tfoot>
                       <tr>
                           <td colspan="4" style="text-align:right"><b>Total</b></td>
                           <td style="text-align:right"><fmt:formatNumber value="${total}" currencySymbol="N" type="currency" /></td>
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
                    { "width":"100px", "targets": 0 },
                    { "sortable": true, "width":"80px" }
                ]
        });
                       
});

function makePayout(){
var table = $('#entitylist').DataTable();
 table.clear().draw();
}
  </script>
  
