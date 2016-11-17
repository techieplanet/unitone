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
            Lodgement
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
                      ${table_title}
                       <c:if test="${success}">
              <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-success padding10" style="width:98%">
                          <i class="fa fa-check"></i>Lodgement made Successfully
<!--                          <span class="pull-right">
                              
                              <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Customer">Back to list</a>
                              
                          </span>-->
                        </p>
                    </div>
                </div>
          </c:if> 
                        
                      
                      <span class="pull-right">
                          <a class="btn btn-primary" href="Lodgement?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Make New Lodgment</a>
                      </span>
                  </h3>
                </div><!-- /.box-header -->
               
                 <div class="box-body">
                  <table id="entitylist" class="table table-bordered table-striped table-hover">
                    <thead>
                      <tr>
                        <th>SN</th>  
                        <th>Transaction Amount</th>
                        <th>Payment Mode</th>
                        <th>Account number</th>
                        <th>Teller/Transaction Id</th>
                        <th>Depositors Name</th>
                        <th>Lodgement Date</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                        
                        <c:forEach items="${lodgements}" var="lodgement" varStatus="pointer">
                           
                            <tr id="row<c:out value="${lodgement.getId()}" />">
                                <td>${pointer.count}</td> 
                                <td><fmt:formatNumber value="${lodgement.amount}" type="currency" currencySymbol="N" /></td>
                                <td>
                                    <c:if test="${lodgement.paymentMode==1}">Bank Deposit</c:if>
                                    <c:if test="${lodgement.paymentMode==2}">Credit/Debit Card</c:if>
                                    <c:if test="${lodgement.paymentMode==3}">Cash / Cheque</c:if>
                                   </td>
                                <td><c:out value="${lodgement.originAccountNumber}" /></td>
                                <td><c:out value="${lodgement.getTransactionId()}" /></td>
                                <td><c:out value="${lodgement.depositorName}" /></td>
                                <td><fmt:formatDate value="${lodgement.createdDate}" type="date" /></td>
                                <td><a href='#' class="btn btn-primary " onclick="getLodgmentItem('${lodgement.getId()}','${pageContext.request.contextPath}',event)"><i class='fa fa-eye'></i> View</a></td>
                              
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                      <tr>
                        <th>SN</th>
                        <th>Transaction Amount</th>
                        <th>Payment Mode</th>
                        <th>Account number</th>
                        <th>Teller/Transaction Id</th>
                        <th>Depositors Name</th>
                        <th>Lodgement Date</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
                  </table>
                  
                    <div class="box-header">
                        <h3 class="box-title block">
                            <span class="pull-right"><a class="btn btn-primary" href="Lodgement?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Make New Lodgement</a></span>
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
      <div class="modal fade" id="lodgmentItemModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Lodgment Items</h4>
            </div>
            <div class="modal-body table-responsive">
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>SN</th>
                            <th>Project</th>
                            <th>Unit name</th>
                            <th>Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                    </tbody>
                    <tfoot>
                        <tr>
                            <th>SN</th>
                            <th>Project</th>
                            <th>Unit name</th>
                            <th>Amount</th>
                        </tr>
                    </tfoot>
                </table>
            </div>
            <div class="modal-footer">
              <button id="ok" type="button" onclick="" class="btn btn-primary" data-dismiss="modal">OK</button>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/neoforce.lodgement.js"></script>

<script>
        
        $(function () {
            $("#entitylist").DataTable();
          });
          
 </script>         

