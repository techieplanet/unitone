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
           ${title}
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
                      Order List
                      <span class="pull-right">
                          <a class="btn btn-primary" href="Order?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Order</a>
                      </span>
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body table-responsive">
                  <table id="entitylist" class="table table-bordered table-striped table-hover">
                    <thead>
                      <tr>
                        <th>S/N</th>
                        <th>Customer Name</th>
                        <th>Agent Name</th>
                        <th>Customer Phone No</th>
                        <th>Customer Email</th>
                        <th>Status</th>
                        <th>Action</th>
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${orders}" var="order" varStatus="pointer">
                            <tr id="row<c:out value="${order.id}" />">
                                
                                <td><c:out value="${pointer.count}" /></td>
                                <td><c:out value="${order.getCustomer().getLastname()} ${order.getCustomer().getFirstname()} " /></td>
                                <td><c:out value="${order.getAgent().getLastname()} ${order.getAgent().getFirstname()} " /></td>
                                <td><c:out value="${order.getCustomer().getPhone()}" /></td>
                                <td><c:out value="${order.getCustomer().getEmail()}" /></td>
                                <td>
                                    <c:set value="${order.getApprovalStatus()}" var="status"/>
                                    
                                        
                                    <c:if test="${status == 0}">
                                        <span class="label label-warning">UnAttended</span>
                                    </c:if>
                                    
                                    <c:if test="${status == 1}">
                                        <span class="label label-info">In Progress</span>
                                    </c:if>    
                                   
                                    <c:if test="${status == 2 && order.getMortgageStatus() == 0}">
                                        <span class="label label-primary">Approved <i class="fa fa-check"></i></span>
                                    </c:if>
                                        
                                    <c:if test="${status == 3}">
                                        <span class="label label-danger">Decline</span>
                                    </c:if>
                                    
                                    <c:if test="${status == 2 && order.getMortgageStatus() == 1}">
                                            <span class="label label-success">Completed</span>
                                    </c:if>
                                        
                                </td>
                                <td>
                                    <a class="btn btn-primary" onclick="getOrder(event,${order.id})" href="#" role="button">View <i class="fa fa-eye"></i> </a>
                                </td>
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                      <tr>
                        <th>S/N</th>
                        <th>Customer Name</th>
                        <th>Agent Name</th>
                        <th>Customer Phone No</th>
                        <th>Customer Email</th>
                        <th>Status</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
                  </table>
                  
                    <div class="box-header">
                        <h3 class="box-title block">
                            <span class="pull-right"><a class="btn btn-primary" href="Order?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Order</a></span>
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
      <div class="modal fade" id="orderItemsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-lg">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Project Unit Sales</h4>
            </div>
            <div class="modal-body table-responsive">
              
                <table class="table table-hover table-striped">
                    <thead>
                        <tr>
                            <th>S/N</th>
                            <th>ID</th>
                            <th>Project Name</th>
                            <th>Unit Name</th>
                            <th>Qty</th>
                            <th>CPU</th>
                            <th>Discount</th>
                            <th>Initial Deposit</th>
                            <th>Total Paid</th>
                            <th>Balance</th>
                            <th>Completion Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        
                    </tbody>
                </table>
                
            </div>
            <div class="modal-footer">
              <button  type="button"  class="btn btn-primary" data-dismiss="modal">OK</button>
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
                ],
                "order": [[ 0, "desc" ]]
        });
    
      
          });
          
        function getOrder(event,orderId){
            
            event.preventDefault();
            
            $.ajax({
                url : "${pageContext.request.contextPath}/Order",
                method : "GET",
                data : {order_id : orderId, action : "getOrder"},
                success : function(data){
                    console.log("Payload : " + data);
                    showOrderItems(data)
                },
                error : function(xhr,xhr_code,xhr_status){
                    console.log("Error Code : " + xhr_code + ", Status : " + xhr_status);
                    console.log("Response : " + xhr.responseText);
                }
            });
        }
        
        function showOrderItems(orderItems){
            
            var items = JSON.parse(orderItems);
            var sn = 1;
            
            //clear the orderItem ModalTable
            $("#orderItemsModal tbody").html("");
            
            for(var key in items){
                
                var tr = "<tr>";
                
                tr += "<td>" + sn + "</td>";
                tr += "<td>" + items[key].id + "</td>";
                tr += "<td>" + items[key].project_name + "</td>";
                tr += "<td>" + items[key].title + "</td>";
                tr += "<td>" + items[key].quantity + "</td>";
                tr += "<td>" + accounting.formatMoney(items[key].cpu,"N",2,",",".") + "</td>";
                tr += "<td>" + accounting.formatMoney(items[key].discount,"N",2,",",".") + "</td>";
                tr += "<td>" + accounting.formatMoney(items[key].initialDeposit,"N",2,",",".") + "</td>";    
                tr += "<td>" + accounting.formatMoney(items[key].total_paid,"N",2,",",".") + "</td>";
                tr += "<td>" + accounting.formatMoney(items[key].balance,"N",2,",",".") + "</td>";
                tr += "<td>" + items[key].completionDate + "</td>";
                
                tr += "</tr>";
                
                ++sn;
                
                $("#orderItemsModal tbody").append(tr);
            }
            
            $("#orderItemsModal").modal();
        }
        
</script>