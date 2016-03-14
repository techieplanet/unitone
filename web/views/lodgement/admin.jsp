<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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
            Lodgements
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
                      Lodgment List
                      <span class="pull-right">
                          <a class="btn btn-primary" href="Lodgement?invoice_id=112023&lodge=declare" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Make New Lodgment</a>
                      </span>
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                  <table id="entitylist" class="table table-bordered table-striped table-hover">
                    <thead>
                      <tr>
                          <th>Agent Id</th>
                        <th>Customer Id</th>
                        <th>Amount</th>
                        <th>Payment Mode</th>
                        <th>Transaction Amount</th>
                        <th>Bank Name</th>
                        <th>Depositors Name</th>
                        <th>Teller Number</th>
                        <th>Lodgement Date</th>
                        <th>Verification Status</th>
                        
                                        
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${lodgements}" var="lodgement">
                            <tr id="row<c:out value="${lodgement.customerId}" />"
                                <c:if test="${verificationStatus==0}">class="danger"</c:if>
                                <c:if test="${verificationStatus==-1}">class="warning"</c:if>
                                <c:if test="${verificationStatus==-1}">class="success"</c:if>
                                >
                                 <td><a href="Agent?action=edit&agentId=${lodgement.agentId}&id=${lodgement.agentId}"><c:out value="${lodgement.agentId}" /></a></td>
                                
                                    <td><a href="Customer?action=edit&customerId=${lodgement.customerId}&id=${lodgement.customerId}"><c:out value="${lodgement.customerId}" /></a></td>
                                <td><c:out value="${lodgement.amount}" /></td>
                                <td>
                                    <c:if test="${lodgement.paymentMode==1}">Bank Deposit</c:if>
                                    <c:if test="${lodgement.paymentMode==2}">Credit/Debit Card</c:if>
                                    <c:if test="${lodgement.paymentMode==3}">Cash / Cheque</c:if>
                                   </td>
                                <td><c:out value="${lodgment.transAmount}" /></td>
                                <td><c:out value="${lodgment.bankName}" /></td>
                                <td><c:out value="${lodgment.depositorsName}" /></td>
                                <td><c:out value="${lodgement.tellerNo}" /></td>
                                <td><c:out value="${lodgement.lodgmentDate}" /></td>
                                <td>
                                    <c:if test="${verificationStatus==1}">
                                        <a class="btn btn-danger btn-xs" href="#"  role="button" title="deactivate"><i class="fa fa-remove"></i></a>
                                    
                                    </c:if>
                                         <c:if test="${verificationStatus==0 || verificationStatus==-1}">
                                        <a class="btn btn-success btn-xs" href="#"  role="button" title="activate"><i class="fa fa-check"></i></a>
                                    
                                    </c:if>
                                </td>
                              
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                      <tr>
                         <th>Agent Id</th>
                        <th>Customer Id</th>
                        <th>Amount</th>
                        <th>Payment Mode</th>
                        <th>Transaction Amount</th>
                        <th>Bank Name</th>
                        <th>Depositors Name</th>
                        <th>Teller Number</th>
                        <th>Lodgement Date</th>
                        <th>Verification Status</th>
                      </tr>
                    </tfoot>
                  </table>
                  
                    <div class="box-header">
                        <h3 class="box-title block">
                            <span class="pull-right"><a class="btn btn-primary" href="Lodgement?invoice_id=112023&lodge=declare" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Make New Lodgement</a></span>
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
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


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