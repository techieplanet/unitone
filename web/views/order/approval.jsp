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
              <a href="#">Approve Order</a>
          </h1>
        </section>

        <!-- Main content -->
        
        <section class="content">
        <div class="row">
          <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              
              <c:forEach items="${pendingOrders}" var="order" varStatus="orderCount">
                        
                   <div class="panel panel-default" id="panel${orderCount.count}">
                            <div class="panel-heading row" role="tab" id="headingOne">
                              <div class="panel-title">
                                  <div class="col-md-4">
                                <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapse${orderCount.count}"  aria-controls="collapse${orderCount.count}">
                                    <span>Order id : </span>${order.getOrderId()} &nbsp;  ${order.getCustomerName()}
                                </a>
                                </div>
                                <div class="col-md-1 pull-right"><span>All</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" id="checkbox${orderCount.count}" onclick="checkAllOrderItem('panel${orderCount.count}','checkbox${orderCount.count}')" /></div>
                              </div>
                                
                            </div>
                                    <div id="collapse${orderCount.count}" class="panel-collapse collapse <c:out value='${orderCount.count == 1?"in":""}' />" 
                                         
                                    role="tabpanel" aria-labelledby="headingOne">
                                         
                              <div class="panel-body">
                                  <table class="table table-hover table-striped table-bordered">
                                      <thead>
                                      <tr>
                                          <th>SN</th> 
                                          <th>Initial Deposit</th>
                                          <th>Qty</th>
                                          <th>Action</th>
                                      </tr>
                                      </thead>
                                      <c:forEach items="${order.orderItems}" var="orderItem" varStatus="orderItemCount">
                                        <tr>
                                            <td>${orderItemCount.count}</td>
                                            <td>${orderItem.getInitialDep()}</td>
                                            <td>${orderItem.getQuantity()}</td>
                                            <td>
                                                <input type="checkbox" class="order-item" value="${orderItem.getId()}" />
                                            </td>
                                        </tr>
                                    </c:forEach>
                                  </table>
                                  <div class="col-md-4 pull-right text-right">
                                      
                                      <button class="btn btn-success ">Approve</button> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                      <button class="btn btn-danger ">Decline</button>
                                  </div>
                              </div> 
                            </div>
                            </div>
                    </c:forEach>     
            
              
          </div>
            </div>
        </section><!-- /.content -->
        
      </div><!-- /.content-wrapper -->





<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>

<script>
    
    function checkAllOrderItem(id,chkboxId){
        
        var isChecked = $("#"+chkboxId).prop('checked');
        console.log("Checked " + isChecked);
        
        $("#"+id+" table").find(".order-item").each(function(){
            
            if(isChecked === false){
              $(this).removeAttr("checked");
          }
          else{
              $(this).attr("checked","checked");
          }
        });
    }
    
</script>
    