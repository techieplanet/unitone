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
              <a href="${pageContext.request.contextPath}/Customer">Customers</a>
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
                              
                                <td style="width:130px">
                                    <c:if test="${sessionScope.user.getSystemUserTypeId() == 1}">
                                     <a class="btn btn-success btn-xs" href="Customer?action=edit&customerId=${customer.customerId}&id=${customer.customerId}" role="button"><i class="fa fa-pencil"></i> </a>
                                     
                                    </c:if>
                                    
                                     <c:if test="${sessionScope.user.getSystemUserTypeId() <= 2}">
                                        <a class="btn btn-primary btn-xs" href="${pageContext.request.contextPath}/Customer?action=profile&customerId=${customer.customerId}" role="button"><i class="fa fa-user"></i> </a>
                                        <a class="btn btn-success btn-xs" href="#" onclick="customer.getCustomerOrders('${customer.customerId}',event)" role="button"><i class="fa fa-cart-plus"></i> </a>
                                        <a class="btn btn-success btn-xs" href="#" onclick="customer.getCustomerLodgements('${customer.customerId}',event)" role="button"><i class="fa fa-dollar"></i> </a>
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

              <h4 class="modal-title">Customer's Lodgments</h4>
            </div>
            <div class="modal-body">
                
                <h3 id="customerName"></h3>
                
                <div class="panel-group" id="lodgement-accordion" role="tablist" aria-multiselectable="true">
                    
                    
                </div>
                
                <!--
                <div class="table-responsive">
                    
                    <table class="" id="customer_lodgement_table">
                       
                        
                    </table>
                    
                </div>
                -->
                
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary pull-right" data-dismiss="modal">Ok</button>
          </div><!-- /.modal-content -->
          
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
      </div>
      
      
      <!--MODAL-->
      <div class="modal fade" id="customerOrdersModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" style="width : 80% !important;">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Customer's Orders</h4>
            </div>
            <div class="modal-body">
                
                <h3 id="customerName"></h3>
                
                <div class="panel-group" id="orders-accordion" role="tablist" aria-multiselectable="true">
                    
                    
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
                          
                          
                          
                          customer.prepareLodgementTable(JSON.parse(data));
                      }
                  });
                  
              },
              
              
             getCustomerOrders : function(id,evt){
                evt.preventDefault();
                
                $.ajax({
                      url : '${pageContext.request.contextPath}/Customer?action=customer_orders',
                      method : "GET",
                      data : {id : id},
                      success : function(data){
                          
                         customer.prepareOrderItemTable(JSON.parse(data));
                      },
                      error : function(xhr,code,text){
                          console.log(xhr.responseText)
                      }
                  });
             },
             

             prepareOrderItemTable : function(data){
             
                var orders = data;
                var orderApprovalUrl = "${pageContext.request.contextPath}";
                var counter = 1;
                
                $("#orders-accordion").html("");
                
                for(var k in orders){
                    
                    var items = orders[k].items;
                    
                    
                      var panel = document.createElement("div");
                      panel.setAttribute("class","panel panel-primary");

                      var panelHeader = document.createElement("div");
                      panelHeader.setAttribute("class","panel-heading");
                      panelHeader.setAttribute("role","tab");
                      panelHeader.setAttribute("id","heading"+counter);


                      var panelTitle = document.createElement("h4");
                      panelTitle.setAttribute("class","panel-title");

                      var titleAnchor = document.createElement("a");
                      titleAnchor.setAttribute("role","button");
                      titleAnchor.setAttribute("data-toggle","collapse");
                      titleAnchor.setAttribute("data-parent","#orders-accordion");
                      titleAnchor.setAttribute("href","#collapse" + counter);
                      titleAnchor.setAttribute("aria-expanded","true");
                      titleAnchor.setAttribute("aria-controls","collapse" + counter);
                      titleAnchor.style.display = "block"
                   
                      var anchorTextNode = document.createTextNode("Order #"+counter + " Date : " + orders[k].order_date);
                      
                      
                      titleAnchor.appendChild(anchorTextNode);
                      panelTitle.appendChild(titleAnchor);
                      panelHeader.appendChild(panelTitle);
                      
                      
                      //Panel Collapse
                      var panelCollapse = document.createElement("div");
                      panelCollapse.setAttribute("id","collapse" + counter);
                      if(counter == 1){
                          panelCollapse.setAttribute("class","panel-collapse collapse in");
                      }
                      else{
                          panelCollapse.setAttribute("class","panel-collapse collapse");
                      }

                      panelCollapse.setAttribute("role","tabpanel");
                      panelCollapse.setAttribute("aria-labelledby","heading"+counter);

                      //Panel Body
                      var panelBody = document.createElement("div");
                      panelBody.setAttribute("class","panel-body");

                      panelCollapse.appendChild(panelBody);
                      panel.appendChild(panelHeader);
                      panel.appendChild(panelCollapse);
                      
                      
                      var table = "<table class='table table-hover'>";
                      
                      table += "<thead>";
                      table += "<tr>";
                      table += "<th> # </th>";
                      table += "<th> Project Name </th>";
                      table += "<th> Unit Name </th>";
                      table += "<th> Cost per unit </th>";
                      table += "<th> Qty </th>";
                      table += "</tr>";
                      table += "</thead>";
                      var itemCount = 1;
                      for(var k2 in items){
                          
                          var tr = "<tr>";
                          
                          tr += "<td>" + itemCount + "</td>"; 
                          tr += "<td>" + items[k2].project_name + "</td>";
                          tr += "<td>" + items[k2].unit_name + "</td>";
                          tr += "<td>" + accounting.formatMoney(items[k2].cpu,"N",2,",",".") + "</td>";
                          tr += "<td>" + items[k2].qty + "</td>";
                          tr += "</tr>";
                          table += tr;
                          
                          itemCount++;
                      }
                      
                        
                            
                            var approvalButton = "<a class='btn btn-primary' href='${pageContext.request.contextPath}/Order?action=approval'>Approval</a>";
                            table += "<tfoot>"; 
                            table += "<tr><td colspan=5 class='text-right'>" + approvalButton + "</td></tr>";
                            table += "</tfoot>";
                                
                        
                            
                        table += "</table>";
                      
                      panelBody.innerHTML = table;
                      
                      document.getElementById("orders-accordion").appendChild(panel)
                      counter++;
                }
             
                $("#customerOrdersModal").modal({
                    backdrop : "static"
                });
             },


             prepareLodgementTable : function(data){
             
                var lodgements = data.lodgements;
                var customerName = data.customerName;
                
                $("#lodgement-accordion").html("");
                
                var counter = 1;
                
                $("#customerName").text(customerName)
                
                for(var k in lodgements){
                    
                  
                  var panel = document.createElement("div");
                  panel.setAttribute("class","panel panel-primary");
                  
                  var panelHeader = document.createElement("div");
                  panelHeader.setAttribute("class","panel-heading");
                  panelHeader.setAttribute("role","tab");
                  panelHeader.setAttribute("id","heading"+counter);
                  
            
                  var panelTitle = document.createElement("h4");
                  panelTitle.setAttribute("class","panel-title");
                  
                  var titleAnchor = document.createElement("a");
                  titleAnchor.setAttribute("role","button");
                  titleAnchor.setAttribute("data-toggle","collapse");
                  titleAnchor.setAttribute("data-parent","#lodgement-accordion");
                  titleAnchor.setAttribute("href","#collapse" + counter);
                  titleAnchor.setAttribute("aria-expanded","true");
                  titleAnchor.setAttribute("aria-controls","collapse" + counter);
                  
                  var anchorTextNode = document.createTextNode("Lodgement date : " + lodgements[k].date);
                  var anchorTextNode2 = document.createTextNode("Amount : " + accounting.formatMoney(lodgements[k].amount,"N",2,",","."));
                  
                  var printLodgementBtn = document.createElement("a");
                  printLodgementBtn.setAttribute("class","btn btn-success btn-sm");
                  printLodgementBtn.setAttribute("href","${pageContext.request.contextPath}/Customer?action=lodgement_invoice&id="+lodgements[k].id);
                  printLodgementBtn.innerText = "Print Invoice";
                  printLodgementBtn.style.position = "relative";
                  printLodgementBtn.style.zIndex = "10000";
                  
                  var anchorDiv = document.createElement("div");
                  anchorDiv.setAttribute("class","row");
                  
                  var col_md1 = document.createElement("div");
                  col_md1.setAttribute("class","col-md-6");
                  
                  var col_md2 = document.createElement("div");
                  col_md2.setAttribute("class","col-md-6");
                  
                  //PanelTitle Wrapper
                  var wrapper = document.createElement("div");
                  wrapper.setAttribute("class","row");
                  
                  var head_col_md10 = document.createElement("div");
                  head_col_md10.setAttribute("class","col-md-10");
                  head_col_md10.appendChild(panelTitle);
                  
                  var head_col_md2 = document.createElement("div");
                  head_col_md2.setAttribute("class","col-md-2 text-right");
                  head_col_md2.appendChild(printLodgementBtn);
                  
                  
                  col_md1.appendChild(anchorTextNode);
                  col_md2.appendChild(anchorTextNode2);
                  
                  anchorDiv.appendChild(col_md1);
                  anchorDiv.appendChild(col_md2);
                  
                  wrapper.appendChild(head_col_md10);
                  wrapper.appendChild(head_col_md2);
                  
                  titleAnchor.appendChild(anchorDiv);
                  panelTitle.appendChild(titleAnchor);
                  panelHeader.appendChild(wrapper);
                  
                  
                  //Panel Collapse
                  var panelCollapse = document.createElement("div");
                  panelCollapse.setAttribute("id","collapse" + counter);
                  if(counter == 1){
                      panelCollapse.setAttribute("class","panel-collapse collapse in");
                  }
                  else{
                      panelCollapse.setAttribute("class","panel-collapse collapse");
                  }
                  
                  panelCollapse.setAttribute("role","tabpanel");
                  panelCollapse.setAttribute("aria-labelledby","heading"+counter);
                  
                  //Panel Body
                  var panelBody = document.createElement("div");
                  panelBody.setAttribute("class","panel-body");
                  
                  panelCollapse.appendChild(panelBody);
                  panel.appendChild(panelHeader);
                  panel.appendChild(panelCollapse);
                  
                  panelCollapse.appendChild(panelBody);
                  
                  var table = "<table class='lodgment-tt-table'>";
                  
                  table += "<thead><tr data-tt-parent-id='" + k + o + "' data-tt-id='header" + k + "'>";
                  table += "<th> Project </th>";
                  table += "<th> Title </th>";
                  table += "<th> Qty </th>";
                  table += "<th> Monthly </th>";
                  table += "<th> Total paid </th>";
                  table += "<th> Balance </th>";
                  table += "<th> Advance </th>";
                  table += "<th> Start date </th>";
                  table += "<th> Payment Stage </th>";
                  table += "<th> Completion Date </th>";
                  table += "</tr></thead>";
                  
                  var orders = lodgements[k].Orders;
                  
                  var orderCount = 1;
                  for(var o in orders){
                      
                      items = orders[o];
                      
                      var trParent = "<tr class='expanded' data-tt-id='" + k + o + "'>";
                          trParent += "<td colspan=10> Order " + orderCount +   " -  Order value : " + accounting.formatMoney(items[0].orderValue,"N",2,",",".") + "</td>";
                          trParent += "</tr>";
                          
                          
                          
                      for(var i in items){
                          
                          var trchild = "<tr data-tt-id='c" + k + o + + i + "' data-tt-parent-id='" + k + o + "'>";
                          
                          trchild += "<td style='text-align:left;padding-left:0px'>" + items[i].project_name + "</td>";
                          trchild += "<td style='text-align:left'>" + items[i].title + "</td>";
                          trchild += "<td>" + items[i].quantity + "</td>";
                          trchild += "<td>" + accounting.formatMoney(items[i].monthly,"N",2,",",".") + "</td>";
                          trchild += "<td>" + accounting.formatMoney(items[i].total_paid,"N",2,",",".") + "</td>";
                          trchild += "<td>" + accounting.formatMoney(items[i].balance,"N",2,",",".") + "</td>";
                          trchild += "<td>" + accounting.formatMoney(items[i].advance,"N",2,",",".") + "</td>";
                          trchild += "<td>" + items[i].startDate + "</td>";
                          trchild += "<td>" + items[i].paymentStage + "</td>";
                          trchild += "<td>" + items[i].completionDate + "</td>";
                          
                          
                          trchild += "</tr>";
                          
                          trParent += trchild;
                          
                          //console.log("Child says : " + items[i].completionDate);
                      }
                      
                      table += trParent;
                      
                      orderCount++;
                  }
                  
                  table += "</table>";
                  
                  panelBody.innerHTML = table;
                  
                  document.getElementById("lodgement-accordion").appendChild(panel);
                  
                  counter++;
                }
                
                $(".lodgment-tt-table").treetable({ expandable: true,initialState: "expanded" },true);
                $("#customerLodgementsModal").modal({
                    backdrop : "static"
                });
             }
              
          };
          
 </script>         
