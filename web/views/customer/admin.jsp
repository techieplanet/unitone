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
            Customers
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
                      Customer List
                      <span class="pull-right">
                          <a class="btn btn-primary" href="Customer?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Customer</a>
                      </span>
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                  <table id="customerList" class="table table-bordered table-striped table-hover">
                    <thead>
                      <tr>
                        <th>SN</th>
                        <th>Image</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Last Name</th>
                        <th>Phone No</th>
                        <th>Email</th>
                        <th>Street</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Action</th>
                        
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${customers}" var="customer" varStatus="pointer">
                            <tr id="row<c:out value="${pointer.count}" />">
                                <td><c:out value="${pointer.count}" /></td>
                                <td><img src="/uploads/NeoForce/images/customer/${customer.photoPath}" width='55' height='50'/></td>
                                <td><c:out value="${customer.firstname}" /></td>
                                <td><c:out value="${customer.middlename}" /></td>
                                <td><c:out value="${customer.lastname}" /></td>
                                <td><c:out value="${customer.phone}" /></td>
                                <td><c:out value="${customer.email}" /></td>
                                <td><c:out value="${customer.street}" /></td>
                                <td><c:out value="${customer.city}" /></td>
                                <td><c:out value="${customer.state}" /></td>
                              
                                <td style="width:100px">
                                    <c:if test="${sessionScope.user.getSystemUserTypeId() == 1}">
                                     <a class="btn btn-primary btn-xs" href="Customer?action=edit&customerId=${customer.customerId}&id=${customer.customerId}" role="button"><i class="fa fa-pencil"></i> </a>
                                     
                                    </c:if>
                                    
                                     <c:if test="${sessionScope.user.getSystemUserTypeId() <= 2}">
                                        <a class="btn btn-primary btn-xs" href="${pageContext.request.contextPath}/Customer?action=profile&customerId=${customer.customerId}" role="button"><i class="fa fa-user"></i> </a>
                                        <a class="btn btn-primary btn-xs" href="#" onclick="customer.getCustomerLodgements('${customer.customerId}',event)" role="button"><i class="fa fa-dollar"></i> </a>
                                     </c:if>
                                     
                                     <c:if test="${sessionScope.user.getSystemUserTypeId() == 1}"> 
                                     <a class="btn btn-danger btn-xs" href="#" onclick="showDeleteModal('${pageContext.request.contextPath}', 'Customer', <c:out value="${customer.customerId}"/>)" role="button"><i class="fa fa-remove"></i></a>
                                    </c:if>
                                    
                                </td>
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
                        <th>Email</th>
                        <th>Street</th>
                        <th>City</th>
                        <th>State</th>
                        <th>Action</th>
                      </tr>
                    </tfoot>
                  </table>
                  
                    <div class="box-header">
                        <h3 class="box-title block">
                            <span class="pull-right"><a class="btn btn-primary" href="Customer?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Customer</a></span>
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
      <div class="modal fade" id="customerLodgementsModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" style="width : 80% !important;">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Customer Lodgment's</h4>
            </div>
            <div class="modal-body">
                
                <h3 id="customerName"></h3>
                <div class="table-responsive">
                    
                    <table class="table table-striped table-hover" id="customer_lodgement_table">
                        
                        <thead>
                            <tr>
                                <td>SN</td>
                                <td>Depositor Name</td>
                                <td>Depositor Acct Name</td>
                                <td>Depositor Acct No</td>
                                <td>Payment Mode</td>
                                <td>Teller/Transaction ID</td>
                                <td>Date</td>
                                <td>Status</td>
                                <td>Amount</td>
                            </tr>
                        </thead>
                        
                        <tbody>
                            
                        </tbody>
                        
                    </table>
                    
                </div>
                
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary pull-right" data-dismiss="modal">Ok</button>
          </div><!-- /.modal-content -->
          
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
      </div>

      
<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


<script>
        
        $(function () {
            $("#customerList").DataTable({
                order :[[0,"asc"]]
            });
          });
          
          var customer = {
              
              getCustomerLodgements : function(id,evt){
                  
                 evt.preventDefault();
                          
                  $.ajax({
                      url : '${pageContext.request.contextPath}/Customer?action=customer_lodgements',
                      method : "GET",
                      data : {id : id},
                      success : function(data){
                          
                          console.log(data);
                          
                          customer.prepareLodgementTable(JSON.parse(data));
                      }
                  });
                  
              },
              
             prepareLodgementTable : function(data){
             
                var lodgements = data.lodgements;
                var customerName = data.customerName;
                
                $("#customer_lodgement_table tbody").html("");
                
                var counter = 1;
                
                $("#customerName").text(customerName)
                
                for(var k in lodgements){
                    
                  var tr = "<tr>";
                  
                  tr += "<td>" + counter + "</td>";
                  tr += "<td>" + lodgements[k].depositorName + "</td>";
                  tr += "<td>" + lodgements[k].depositorAcctName + "</td>";
                  tr += "<td>" + lodgements[k].depositorAcctNo + "</td>";
                  tr += "<td>" + lodgements[k].paymentMode + "</td>";
                  tr += "<td>" + lodgements[k].transactionId + "</td>";
                  tr += "<td>" + lodgements[k].date + "</td>";
                  tr += "<td>" + lodgements[k].status + "</td>";
                  tr += "<td>" + accounting.formatMoney(lodgements[k].amount,"N",2,",",".") + "</td>";
                  
                  tr += "</tr>";
                  
                  $("#customer_lodgement_table tbody").append(tr);
                  
                  counter++;
                }
                
                $("#customerLodgementsModal").modal({
                    backdrop : "static"
                });
             }
              
          };
          
 </script>         
