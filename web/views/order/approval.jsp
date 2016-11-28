<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   



<!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper order-view">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
              <a href="#">Waiting Orders</a>
          </h1>
        </section>

        <!-- Main content -->
        
        <section class="content">
          <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              
              <c:forEach items="${pendingOrders}" var="order" varStatus="orderCount">
                  <form action="${pageContext.request.contextPath}/Order?action=approveOrder" method="post">      
                  <div class="panel panel-default" id="panel${orderCount.count}" >
                            <div class="panel-heading" style="height:40px; background-color: #357CA5 !important;" role="tab" id="heading${orderCount.count}">
                              <div class="panel-title">
                                <a role="button" style="display: block;color:#fff !important;" data-toggle="collapse" data-parent="#accordion" href="#collapse${orderCount.count}"  aria-controls="collapse${orderCount.count}">
                                    <span>Order id : </span>${order.getOrderId()} &nbsp;  ${order.getCustomerName()}
                                    
                                </a>
                               
                              </div>
                                
                            </div>
                                    <div id="collapse${orderCount.count}" class="panel-collapse collapse 
                                         <c:if test="${singleOrderId == 0}"> 
                                             <c:out value='${orderCount.count == 1?"in":""}' />
                                         </c:if>
                                         <c:if test="${singleOrderId > 0 && order.getOrderId() == singleOrderId}"> 
                                             <c:out value='in scrollHere' />
                                         </c:if>
                                         " 
                                role="tabpanel" aria-labelledby="heading${orderCount.count}" >
                                         
                              <div class="panel-body">
                                  <div class="col-md-4 pull-right" style="text-align: right">
                                       <span>Approve All</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="" name="approveBox" id="checkApprove${orderCount.count}" onclick="acceptOrder('panel${orderCount.count}','checkApprove${orderCount.count}','checkDecline${orderCount.count}')" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                       <span>Decline All</span>&nbsp;&nbsp;&nbsp;&nbsp;<input type="checkbox" class="" name="approveBox" id="checkDecline${orderCount.count}" onclick="declineOrder('panel${orderCount.count}','checkDecline${orderCount.count}','checkApprove${orderCount.count}')" />
                                   </div>
                                   
                                  <table class="table table-hover table-striped table-bordered" >
                                      <thead>
                                      <tr>
                                          <th>SN</th>
                                          <th>Project Name</th> 
                                          <th>Unit Title</th>
                                          <th>Initial Deposit</th>
                                          <th style="text-align: center">Qty</th>
                                          <th style="text-align: center">Approve</th>
                                          <th style="text-align: center">Decline</th>
                                      </tr>
                                      </thead>
                                      <c:forEach items="${order.orderItems}" var="orderItem" varStatus="orderItemCount">
                                        <tr>
                                            <td>${orderItemCount.count}</td>
                                            <td>${order.getProjectName(orderItemCount.index)}</td>
                                            <td>${order.getUnitTitle(orderItemCount.index)}</td>
                                            <td style="text-align: right"><fmt:formatNumber value='${orderItem.getInitialDep()}' type='currency' currencySymbol='N' /></td>
                                            <td style="text-align: center">${orderItem.getQuantity()}</td>
                                            <td style="background-color: #008D4C; text-align: center;">
                                                <input type="checkbox" name="order-item-approve" class="order-item-approve chkbox1" value="${orderItem.getId()}" />
                                            </td>
                                            <td style="background-color: #D73925; text-align: center;">
                                                <input type="checkbox" name="order-item-decline" class="order-item-decline chkbox2" value="${orderItem.getId()}" />
                                            </td>
                                        </tr>
                                     </c:forEach>
                                  </table>
                                   
                                  <div class="col-md-4 pull-right text-right">
                                      
                                      <button class="btn btn-primary btn" type="submit">Save</button>
                                  </div>
                              </div> 
                            </div>
                  </div>
                  </form>
              </c:forEach>     
            
              
          </div>
        </section><!-- /.content -->
        
      </div><!-- /.content-wrapper -->





<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/neoforce.lodgement.js"></script>
<script>
    
    $(document).ready(function(){
        $(".chkbox1").on('change',function(){
            console.log("chkbox1 changed");
            if($(this).is(":checked")){
                $(this).parent().parent().find('.chkbox2').prop("checked",false);
            }
            
        });
        
        $(".chkbox2").on('change',function(){
            console.log("chkbox1 changed");
            if($(this).is(":checked")){
                $(this).parent().parent().find('.chkbox1').prop("checked",false);
            }
        });
        
       
       //scroll to order if it was cliked from notification
       $('html, body').animate({
        scrollTop: $(".scrollHere").offset().top
     }, 2000);
      
    });
   
   
    
    
</script>
    