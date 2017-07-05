<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
          
        <c:if test="${success}">
              <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-success padding15" style="vertical-align:center !important;" >
                          <i class="fa fa-check"></i>Saved Successfully
                        </p>
                    </div>
                </div>
          </c:if>  
        
          
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Customer Profile
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
          
          
            <div class="panel panel-default">
                
                
                <div class="panel-heading" style="background-color: #ffffff">
                    <div class="row">
                        <div class="col-md-12">
                            <a href="${history}" class="btn btn-primary" /><i class="fa fa-chevron-left"></i> Back </a>
                        </div>
                    </div>
                </div>
                
                <div class="panel-body">
                  
                    
                    <div class="row">
                        <div class="col-md-12">
                            <ul class="nav nav-tabs" role="tablist">
                                <li role="presentation" class="active"><a href="#profile" aria-controls="profile" role="tab" data-toggle="tab">Profile</a></li>
                                <li role="presentation"><a href="#Orders" aria-controls="orders" role="tab" data-toggle="tab">Customer Orders</a></li>
                                <li role="presentation"><a href="#Lodgements" aria-controls="Lodgements" role="tab" data-toggle="tab">Customer Lodgements </a></li>
                                <li role="presentation"><a href="#Documents" aria-controls="Documents" role="tab" data-toggle="tab">Customer Documents </a></li>
                            </ul>
                        </div>
                    </div>
                    
                    <div class="tab-content">
                    
                    <div role="tabpanel" class="tab-pane active" id="profile">
                    
                    <div class="row">
                        
                        <div class="col-md-2">
                            
                            <h4>Customer Picture</h4>
                            <img src="/uploads/NeoForce/images/customers/${customer.photoPath}" class="img img-responsive img-thumbnail" />
                            
                            
                            
                        </div>
                        
                        <div class="col-md-10">
                            
                            <table class="table table-profile">
                                
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>
                                            <div class="col-md-9">
                                                <h4>${customer.getFullName()}</h4>
                                            </div>
                                
                                            <c:if test="${sessionScope.user.getSystemUserTypeId() == 3}">
                                            <div class="col-md-3">
                                                <a href="#" class="pull-right" onclick="customerProfile.showPasswordModal(event)" style="text-decoration: none; border-bottom: 1px dotted blue;">Change password</a>
                                            </div>
                                            </c:if>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="highlight">
                                        <td class="field">Email</td>
                                        <td><i class="fa fa-envelope-o"></i> ${customer.getEmail()} </td>
                                        
                                    </tr>
                                    <tr class="divider">
                                        <td colspan="2"></td>
                                    </tr>
                                    <tr>
                                        <td class="field">Title</td>
                                        <td>${customer.getTitle()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Gender</td>
                                        <td>${customer.getGender()}</td>
                                    </tr>
                                     <tr>
                                        <td class="field">Marital Status</td>
                                        <td>${customer.getMaritalStatus()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Date Of Birth</td>
                                        <td>${customer.getDateOfBirth()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Mobile</td>
                                        <td><i class="fa fa-mobile-phone"></i> ${customer.getPhone()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Other Phone</td>
                                        <td><i class="fa fa-mobile-phone"></i> ${customer.getOtherPhone()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Street</td>
                                        <td>${customer.getStreet()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">City</td>
                                        <td>${customer.getCity()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">State</td>
                                        <td>${customer.getState()}</td>
                                    </tr>
                                     <tr>
                                        <td class="field">Country</td>
                                        <td>${customer.getCountry()}</td>
                                    </tr>
                                     <tr>
                                        <td class="field">Postal Address</td>
                                        <td>${customer.getPostalAddress()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Account Code</td>
                                        <td>${customer.getAccountCode()}</td>
                                    </tr>
                                     
                                    <tr class="highlight">
                                        <td class="field" colspan="2" style="text-align: left">
                                            <h4>Work Information</h4>
                                        </td>
                                    </tr>
                                   <tr>
                                        <td class="field">Occupation</td>
                                        <td>${customer.getOccupation()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Employer</td>
                                        <td>${customer.getEmployer()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Office Phone</td>
                                        <td><i class="fa fa-mobile-phone"></i> ${customer.getOfficePhone()}</td>
                                    </tr>
                                    
                                    <tr class="highlight">
                                        <td class="field" colspan="2" style="text-align: left">
                                            <h4>Office Address</h4>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="field">Street</td>
                                        <td>${customer.getOfficeStreet()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">City</td>
                                        <td>${customer.getOfficeCity()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">State</td>
                                        <td>${customer.getOfficeState()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Country</td>
                                        <td>${customer.getOfficeCountry()}</td>
                                    </tr>
                                    
                                    <tr class="highlight">
                                        <td class="field" colspan="2" style="text-align: left">
                                            <h4>Employer Address</h4>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="field">Street</td>
                                        <td>${customer.getEmployerStreet()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">City</td>
                                        <td>${customer.getEmployerCity()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">State</td>
                                        <td>${customer.getEmployerState()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Country</td>
                                        <td>${customer.getEmployerCountry()}</td>
                                    </tr>
                                    
                                    <tr class="highlight">
                                        <td class="field" colspan="2" style="text-align: left">
                                            <h4>Next of Kin Information</h4>
                                        </td>
                                    </tr>
                                    <tr class="divider">
                                        <td colspan="2"></td>
                                    </tr>
                                    <tr>
                                        <td class="field">Name</td>
                                        <td>${customer.getKinName()}</td>
                                    </tr>
                                     <tr>
                                        <td class="field">Relationship</td>
                                        <td>${customer.getKinRelationship()}</td>
                                    </tr>
                                     <tr>
                                        <td class="field">Mobile</td>
                                        <td><i class="fa fa-mobile-phone"></i> ${customer.getKinPhone()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Email</td>
                                        <td><i class="fa fa-envelope-o"></i>${customer.getKinEmail()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Address</td>
                                        <td>${customer.getKinAddress()}</td>
                                    </tr>
                                    
                                    <tr class="highlight">
                                        <td class="field" colspan="2" style="text-align: left">
                                            <h4>Bank Information</h4>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="field">Banker</td>
                                        <td>${customer.getBanker()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Account Name</td>
                                        <td>${customer.getAccountName()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Account Number</td>
                                        <td>${customer.getAccountNumber()}</td>
                                    </tr>
                                    
                                    <tr class="highlight">
                                        <td class="field" colspan="2" style="text-align: left">
                                            <h4>Customer's Agent Information</h4>
                                        </td>
                                    </tr>
                                    <tr class="divider">
                                        <td colspan="2"></td>
                                    </tr>
                                    <tr>
                                        <td class="field">Name</td>
                                        <td>${customer.getAgent().getFullName()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Email</td>
                                        <td><i class="fa fa-envelope-o"></i> ${customer.getAgent().getEmail()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Mobile</td>
                                        <td><i class="fa fa-mobile-phone"></i> ${customer.getAgent().getPhone()}</td>
                                    </tr>
                                </tbody>
                            </table>
                            
                        </div>
                        
                    </div>
                    
                    </div> <!-- END OF PROFILE TAB -->
                    
                    <div role="tabpanel" class="tab-pane" id="Orders">
                        
                        <div class="row">
                            <div class="col-md-12">
                             
                                <div class="panel-group" id="accordion1" role="tablist" aria-multiselectable="true">
                                    <c:forEach items="${orders}" var="order" varStatus="pointer">
                                      <div class="panel panel-primary">
                                        <div class="panel-heading" role="tab" id="heading1${pointer.count}">
                                          <h4 class="panel-title">
                                            <a role="button" style="display:block" data-toggle="collapse" data-parent="#accordion1" href="#collapse1${pointer.count}" aria-expanded="true" aria-controls="collapse">
                                              Order date : ${order.get('order_date')}
                                            </a>
                                          </h4>
                                        </div>
                                        <div id="collapse1${pointer.count}" class="panel-collapse collapse ${pointer.count == 1 ? 'in' : ''}" role="tabpanel" aria-labelledby="heading1${pointer.count}">
                                          <div class="panel-body table-responsive">
                                           
                                              <table class="table table-hover table-striped table-bordered">
                                                  <thead>
                                                      <tr>
                                                          <th>Project</th><th>Unit</th><th>Qty</th><th>Cost per Unit</th>
                                                      </tr>
                                                  </thead>
                                                  <tbody>
                                                      <c:forEach items="${order.get('items')}" var="item">
                                                          <tr>
                                                              <td>${item.get("project_name")}</td>
                                                              <td>${item.get("unit_name")}</td>
                                                              <td>${item.get("qty")}</td>
                                                              <td><fmt:formatNumber currencySymbol="N" type="currency" maxFractionDigits="2" value="${item.get('cpu')}" /></td>
                                                          </tr>
                                                      </c:forEach>
                                                  </tbody>
                                              </table>
                                              
                                          </div>
                                        </div>
                                      </div>
                                    </c:forEach>
                                </div>
                              
                            </div>
                        </div>
                        
                    </div> <!-- END OF ORDERS TAB -->
                    
                    
                    <div role="tabpanel" class="tab-pane" id="Lodgements">
                        
                            <div class="row">
                            <div class="col-md-12">
                             
                                <div id="lodgement-accordion" class="panel-group"  role="tablist" aria-multiselectable="true" style="max-height: 400px; overflow-y: auto"></div>
                              
                            </div>
                        </div>
                        
                    </div> <!-- END OF LODGEMENTS TAB -->
                    
                    
                     <div role="tabpanel" class="tab-pane" id="Documents">
                        
                            <div class="row">
                            <div class="col-md-12">
                             
                                <div id="documents-accordion" class="panel-group"  role="tablist" aria-multiselectable="true" >
                                    <table class="table table-profile">
                                
                                <thead>
                                    <tr>
                                        <th ><div >
                                                <h4>sn</h4>
                                            </div> </th>
                                        <th ><div >
                                                <h4>title</h4>
                                            </div> </th>
                                        <th >
                                            <div >
                                                <h4>file</h4>
                                            </div>
                                        </th>
                                    </tr>
                                     <tr class="divider">
                                        <td colspan="3"></td>
                                    </tr>
                                </thead>
                                <tbody>
                           
                                    <c:forEach items="${documents}" var="document">
                                        <tr>
                                        <td >${document.docTypeId.weight}</td>
                                        <td >${document.docTypeId.title}</td>
                                        <td ><a href="${documentDir}${document.path}" ><img src="${documentDir}${document.path}" class="img img-responsive img-thumbnail" style="max-width: 100px;max-height: 100px; overflow-y: auto"/></a>
                                         </td>
                                    </tr>
                                       <tr class="divider">
                                           <td colspan="3"><hr/></td>
                                    </tr>
                                    </c:forEach>
                                </tbody>
                                    </table>
                                    
                                </div>
                              
                            </div>
                        </div>
                        
                    </div> <!-- END OF Documents TAB -->
                                    
                    </div> <!-- END OF TAB-CONTENT -->
                </div>
            </div>
            
            
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <!--MODAL-->
      <div class="modal fade" id="password_change_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Password Change</h4>
            </div>
            <div class="modal-body">
                <img src="${pageContext.request.contextPath}/images/uploadProgress.gif" class="img img-responsive hidden vertical-align-center" id="progressLoader" /><br />
                <div class="alert alert-error alert-dismissible fade in hidden">
                </div>
                <div class="alert alert-success alert-dismissible fade in hidden">
                    <span class="close" data-dismiss="alert">&times;</span>
                    Password change was successful
                </div>
                <div class="form-group">
                    <label>Old password</label>
                    <input type="password" class="form-control" name="old_password" id="old_password" />
                </div>
                <div class="form-group">
                    <label>New password</label>
                    <input type="password" class="form-control" name="new_password" id="new_password" />
                </div>
                <div class="form-group">
                    <label>Re-enter New password</label>
                    <input type="password" class="form-control" name="new_password2" id="new_password2" />
                </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              
              <button id="ok" type="button" onclick="customerProfile.changePassword('${customer.getCustomerId()}')"  class="btn btn-primary">Submit</button>
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
      
     var customerProfile = {
         
         changePassword : function(id){
             
             //Start the loader
             $("#progressLoader").removeClass("hidden");
             $("#ok").prop("disabled",true);
             
             var old_password = $("#old_password").val();
             var pwd1 = $("#new_password").val();
             var pwd2 = $("#new_password2").val();
             
             var errors = [];
             
             if($.trim(old_password).length < 1){
                 errors.push("Old password is required");
             }
             
             if($.trim(pwd1).length < 1){
                 errors.push("New Password is required")
             }
             else if($.trim(pwd1) != $.trim(pwd2)){
                 errors.push("Password and Re-enter password must match");
             }
             
             if(errors.length > 0){
                 //Clear error and sucess alert
                 $("#password_change_modal .modal-body div.alert-error").html("");
                 $("#password_change_modal .modal-body div.alert-error").addClass("hidden");
                 $("#password_change_modal .modal-body div.alert-success").addClass("hidden");
                 for(var k=0; k < errors.length; k++){
                     
                     $("#password_change_modal .modal-body div.alert-error").append("<span>" + errors[k] + "</span><br />");
                    
                 }
                 $("#password_change_modal .modal-body div.alert-error").removeClass("hidden");
                 
                 //Stop the loader
                 $("#progressLoader").addClass("hidden");
                 $("#ok").prop("disabled",false);
                 
             }else{
                 
                 $.ajax({
                     url : "${pageContext.request.contextPath}/Customer?action=password_change",
                     method : "POST",
                     data : {old_password : old_password, pwd1 : pwd1, pwd2 : pwd2, id : id},
                     success : function(data){
                         
                         //Clear error and sucess alert
                         $("#password_change_modal .modal-body div.alert-error").html("");
                         $("#password_change_modal .modal-body div.alert-error").addClass("hidden");
                         $("#password_change_modal .modal-body div.alert-success").addClass("hidden");
                         
                         var json = JSON.parse(data);
                         
                         if(json.success){
                             $("#password_change_modal .modal-body div.alert-success").removeClass("hidden");
                         }
                         else{
                             $("#password_change_modal .modal-body div.alert-error").append("<span>" + json.error + "</span><br />");
                             $("#password_change_modal .modal-body div.alert-error").removeClass("hidden");
                         }
                         
                         //Stop the loader
                         $("#progressLoader").addClass("hidden");
                         $("#ok").prop("disabled",false);
                     },
                     error : function(xhr,error_code,error_text){
                         console.log(xhr.responseText);
                         //Stop the loader
                         $("#progressLoader").addClass("hidden");
                         $("#ok").prop("disabled",false);
                     }
                 })
             }
             
         },
         
         showPasswordModal : function(evt){
             evt.preventDefault();
             $("#password_change_modal .modal-body div.alert-error").html("");
             $("#password_change_modal .modal-body div.alert-error").addClass("hidden");
             $("#password_change_modal .modal-body div.alert-success").addClass("hidden");
             $("#password_change_modal").modal({
                 backdrop : "static"
             });
         } ,
         
         prepareLodgementTable : function(data){
             
                var lodgements = data.lodgements;
                var customerName = data.customerName;
                
                $("#lodgement-accordion").html("");
                
                var counter = 1;
                
                //$("#customerName").text(customerName)
                
                for(var k in lodgements){
                    
                  
                  var panel = document.createElement("div");
                  panel.setAttribute("class","panel panel-primary");
                  
                  var panelHeader = document.createElement("div");
                  panelHeader.setAttribute("class","panel-heading");
                  panelHeader.setAttribute("role","tab");
                  panelHeader.setAttribute("id","heading2"+counter);
                  
            
                  var panelTitle = document.createElement("h4");
                  panelTitle.setAttribute("class","panel-title");
                  
                  var titleAnchor = document.createElement("a");
                  titleAnchor.setAttribute("role","button");
                  titleAnchor.setAttribute("data-toggle","collapse");
                  titleAnchor.setAttribute("data-parent","#lodgement-accordion");
                  titleAnchor.setAttribute("href","#collapse2" + counter);
                  titleAnchor.setAttribute("aria-expanded","true");
                  titleAnchor.setAttribute("aria-controls","collapse");
                  
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
                  panelCollapse.setAttribute("id","collapse2" + counter);
                  if(counter == 1){
                      panelCollapse.setAttribute("class","panel-collapse collapse in");
                  }
                  else{
                      panelCollapse.setAttribute("class","panel-collapse collapse");
                  }
                  
                  panelCollapse.setAttribute("role","tabpanel");
                  panelCollapse.setAttribute("aria-labelledby","heading2"+counter);
                  
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
                
             }
     }
     
     var jsonData = JSON.parse('${lodgements}');
     console.log(jsonData);
     customerProfile.prepareLodgementTable(jsonData);
 </script>         
