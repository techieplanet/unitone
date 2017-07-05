<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--
    Process Step
-->
<c:if test="${action == 'new'}">
<div class="stepwizard">
    <div class="stepwizard-row">
        <div class="stepwizard-step">
            <button type="button" id="process-step-1" class="btn btn-primary btn-circle" onclick="return showCustomerReg()">1</button>
            <p>Register Customer</p>
        </div>
         <div class="stepwizard-step">
            <button type="button" id="process-step-2" class="btn btn-default btn-circle" onclick="proceed()">2</button>
            <p>Customer Document</p>
        </div>
        <div class="stepwizard-step">
            <button type="button" id="process-step-3" class="btn btn-default btn-circle" disabled="disabled" onclick="return showOrderProduct()">3</button>
            <p>Order/Checkout</p>
        </div>
    </div>
</div>
</c:if>

 <c:if test='${userTypeId != null && action.equals("new") && userTypeId == "1" }'>
 <div class="row margin-bottom" id="agentListContainer">
    <div class="col-md-12">   
         <div class="panel panel-default">
                <div class="panel-heading">
                  <h2 class="panel-title">
                      <b>Select an agent</b>
                  </h2>
                </div><!-- /.box-header -->
                
                 <div class="panel-body" id="agentListPanelBody ">
                  <table id="agentList" class="table table-bordered table-striped table-hover">
                    <thead>
                      <tr>
                        <th>Photo</th>
                        <th>ID</th>
                        <th>First Name</th>
                        <th>Middle Name</th>
                        <th>Last Name</th>
                        <th>Phone No</th>
                        <th>Email</th>
                        <th>State</th>
                        <th>Action</th>
                        
                      </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${agents}" var="agent">
                            <tr id='row<c:out value="${agent.agentId}" />'>
                                <td><img alt="No Image" class="img-responsive img-thumbnail" width="55" height="55" src="/uploads/NeoForce/images/agents/<c:out value='${agent.photoPath}'></c:out>" /></td>
                                <td class="agentId"><c:out value="${agent.agentId}" /></td>
                                <td class="agentFname"><c:out value="${agent.firstname}" /></td>
                                <td class="agentMname"><c:out value="${agent.middlename}" /></td>
                                <td class="agentLname"><c:out value="${agent.lastname}" /></td>
                                <td class="agentPhone"><c:out value="${agent.phone}" /></td>
                                <td class="agentEmail"><c:out value="${agent.email}" /></td>
                                <td class="agentState"><c:out value="${agent.state}" /></td>
                              
                                <td>
                                    <input type="hidden" class="agentImg" value='/uploads/NeoForce/images/agents/<c:out value="${agent.photoPath}"></c:out>' />
                                    <a class="btn btn-primary" href="#" onclick="selectAgent('${agent.agentId}')" role="button">Choose</a>
                                </td>
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                    
                    </tfoot>
                  </table>
                </div><!-- /.panel-body -->
                
                <div class="panel-footer">
                    <span><a href="#" onclick="showSelectedAgent()">View selected agent</a></span>
                </div>
                
          </div>
  
    </div>
 </div>
                                
 <div class="row" id="agentSpinnerContainer" style='display:none'>
     <div class="spinner" >
         <img class='img-responsive' src="${pageContext.request.contextPath}/images/uploadProgress.gif" style="margin: 10px auto" />
     </div>
 </div>
     
 <div class="row" id="agentDetailContainer"  style='display:none'>
     
     <div class="col-md-6 pull-right">
         
         <div class="box box-solid">
             
             <div class="box-header with-border">
                 <h3 class="box-title">Agent Details</h3>
             </div>
             
             <div class="box-body ">
                 
                 <div class="row">
                     
                     <div class="col-md-3">
                         <img src="" alt="No image" class="agent_img img-responsive img-thumbnail" width="80" height="80" />
                     </div>
                     
                     <div class="col-md-9">
                         <span class="agent_name"></span><br/>
                         <span class="agent_moible"></span><br/>
                         <span class="agent_state"></span>
                     </div>
                     
                 </div>
                 
             </div>
             
             <div class="box-footer">
                 <a href="#" onclick="showAgentList()"><i class='fa fa-chevron-left'></i> Show agent List</a>
             </div>
             
         </div>
         
     </div>
     
 </div>
                                
</c:if>  
<form role="form" name="customerRegistration" method="POST" action="Customer?action=new" enctype="multipart/form-data" onsubmit="return submitForm()">
  
    <input type="hidden" name="customer_id" value="" />
    <input type="hidden" name="agent_id" id="agent_id" value="" />
 
    <!-- Step One Customer Details -->
 <div class="row" id="step1">
           <div class="col-md-12">
              <!-- general form elements -->
               
                <!-- form start -->
               <div class="box box-primary" id="step1_box">
                <div class="box-header with-border">
                  <h3 class="box-title">Customer Registration Form 
                      
                      
                       </h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <div style="background:#ecf0f5 !important;">
                
                  <div class="box-body">
               <c:if test="${fn:length(errors) > 0 }">
                <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-danger padding10" style="width:100%; margin:0 auto !important">
                          <c:forEach items="${errors}" var="error">
                              <c:out value="${error.value}" /><br/>
                          </c:forEach>
                        </p>
                    </div>
                </div>
            </c:if>
                    <c:if test="${success}">
              <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-success padding10" style="width:95%">
                          <i class="fa fa-check"></i>Saved Successfully
                          <span class="pull-right">
                              
                              <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Customer">Back to list</a>
                              
                          </span>
                        </p>
                    </div>
                </div>
          </c:if>   
                              <c:if test='${action != "new" && userType != 3}' >
                                  <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Order?action=new&customer=${customer.customerId}">Buy Product</a>
                                  &nbsp;&nbsp;&nbsp;
                                  <a class="btn btn-primary" href="Customer?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Customer</a>
                              </c:if>
               
                    
                    <div class="box box-default">
                    <div class="row" style="padding-top:10px;margin: 10px">
                        <div class="col-md-12">
                      <fieldset>
                        <legend style="padding-left:10px !important;">Personal Information</legend>
                        
                              <div class="col-md-3">
                                  <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerTitle">Title</label>
                                     
                                      <input type="text"  name="customerTitle" class="form-control" id="customerTitle" placeholder="Title" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if>    
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerTitle}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.title}"</c:if> "/>
                                             
                                    </div>
                                </div>
                          
                              <div class="col-md-3">
                                  <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerFirstname">First Name</label>
                                      <input type="text"  name="customerFirstname" class="form-control" id="customerFirstname" placeholder="First Name" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if>    
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerFirstname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.firstname}"</c:if> "/>
                                             
                                    </div>
                                </div>
                         
                           
                              <div class="col-md-3">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerMiddlename">Middle Name</label>
                                      <input type="text" class="form-control" id="customerMiddlename" name="customerMiddlename" placeholder="Middle Name"
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                        value=<c:if test="${fn:length(errors) > 0 }">"${param.customerMiddlename}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.middlename}"</c:if> "/>
                                             
                                    </div>
                                </div>
                           
                            <div class="col-md-3">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerLastname">Last Name</label>
                                      <input type="text" class="form-control" name="customerLastname" id="customerLastname" placeholder="Last Name" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerLastname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.lastname}"</c:if> "/>
                                             
                                    </div>
                                </div>
                                  
                             
                              <div class="col-md-3">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerGender" >Gender</label><br/>
                                      <% //Work has to be done here also %>
                                      <select  name="customerGender" id="customerGender" class=" form-control"
                                               <c:if test='${action == "edit" && userType > 1}'>readonly</c:if>  >
                                          <option value="male" <jsp:text>selected</jsp:text> >Male</Option> 
                                          <option value="female">Female</Option> 
                                      </select>
                                    </div>
                                </div>
                            
                           
                              <div class="col-md-3">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerMaritalStatus" >Marital Status</label><br/>
                                      <% //Work has to be done here also %>
                                      <select  name="customerMaritalStatus" id="customerMaritalStatus" class=" form-control"
                                               <c:if test='${action == "edit" && userType > 1}'>readonly</c:if>  >
                                          <option value="Married" <jsp:text>selected</jsp:text> >Married</Option> 
                                          <option value="Single">Single</Option> 
                                          <option value="Divorced">Divorced</Option> 
                                      </select>
                                    </div>
                                </div>
                          
                             
                              <div class="col-md-3">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerDateOfBirth" >Date Of Birth</label>
                                      
                                       <input type="text" name="customerDateOfBirth" id="customerDateOfBirth" placeholder="dd/mm/yy" class="text-black form-control" 
                                               <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.customerDateOfBirth}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.dateOfBirth}"</c:if> "
                              />
                                       
                                       </div>
                                </div>
                              
                             <div class="col-md-3">
                            <div class="form-group" style="padding-left:10px !important;padding-right:10px !important">
                              <label for="customerEmail">Email address</label>
                              <input type="email" class="form-control" id="customerEmail" name="customerEmail" placeholder="your@email.com" 
                                     <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                     value=<c:if test="${fn:length(errors) > 0 }">"${param.customerEmail}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.email}"</c:if> "
                              <c:if test="${customer.customerId !='' && customer.customerId != null }">readonly="true"</c:if> />        
                            </div>
                        </div>
                          
                           
                        </fieldset>
                        </div>
                         
                          <div class="col-md-12">
                       
                            <fieldset>
                                <legend style="padding-left:10px !important;">Work Information</legend>
                        <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerOccupation">Occupation</label>
                                     
                                      <input type="text" class="form-control" name="customerOccupation" id="customerOccupation" placeholder="Occupation" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerOccupation}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.occupation}"</c:if> "/>
                                             
                                    </div>
                                </div>
                              <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerEmployer">Employer</label>
                                      
                                      <input type="text" class="form-control" name="customerEmployer" id="customerEmployer" placeholder="Employer" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerEmployer}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.employer}"</c:if> "/>
                                             
                                    </div>
                                </div>
                              
                              <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerOfficePhone">Office Phone</label>
                                      
                                      <input type="text" class="form-control" name="customerOfficePhone" id="customerOfficePhone" placeholder="Office Phone" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerOfficePhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.officePhone}"</c:if> "/>
                                             
                                    </div>
                                </div>
                            <div class="col-md-12">
                                    <div class="form-group" style="padding-left:10px !important;">
                                     <legend style="padding-left:10px !important;">Office Address</legend>
                                     
                                      <div class="col-md-12"> 
                                          <div class="col-md-3">
                                              <label for ="customerOfficeStreet">Street</label>
                                              <input type="text" class="form-control" name="customerOfficeStreet" id="customerOfficeStreet" placeholder="Street" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerOfficeStreet}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.officeStreet}"</c:if> "/>
                                       
                                          </div>
                                           <div class="col-md-3">
                                              <label for ="customerOfficeCity">City</label>
                                              <input type="text" class="form-control" name="customerOfficeCity" id="customerOfficeCity" placeholder="City" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerOfficeCity}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.officeCity}"</c:if> "/>
                                       
                                          </div>
                                           <div class="col-md-3">
                                              <label for ="customerOfficeState">State</label>
                                              <input type="text" class="form-control" name="customerOfficeState" id="customerOfficeState" placeholder="State" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerOfficeState}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.officeState}"</c:if> "/>
                                       
                                          </div>
                                           <div class="col-md-3">
                                              <label for ="customerOfficeCountry">Country</label>
                                              <%//work needed to be done here %>
                                              <select class="form-control" name="customerOfficeCountry" id="customerOfficeCountry" onchange="changePhoneCode()">
                                              <c:forEach items="${countries}" var="country">
                                                  <option value="${country.getName()}">${country.getName()}</option>
                                              </c:forEach>
                                              </select>
                                          </div>
                                      </div>      
                                    </div>
                                </div>
                              
                             <div class="col-md-12">
                                    <div class="form-group" style="padding-left:10px !important;">
                                     <legend style="padding-left:10px !important;">Employer Address</legend>
                                     
                                      <div class="col-md-12"> 
                                          <div class="col-md-3">
                                              <label for ="customerEmployerStreet">Street</label>
                                              <input type="text" class="form-control" name="customerEmployerStreet" id="customerEmployerStreet" placeholder="Street" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerEmployerStreet}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.employerStreet}"</c:if> "/>
                                              
                                          </div>
                                           <div class="col-md-3">
                                              <label for ="customerEmployerCity">City</label>
                                              <input type="text" class="form-control" name="customerEmployerCity" id="customerEmployerCity" placeholder="City" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerEmployerCity}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.employerCity}"</c:if> "/>
                                       
                                          </div>
                                           <div class="col-md-3">
                                              <label for ="customerEmployerState">State</label>
                                              <input type="text" class="form-control" name="customerEmployerState" id="customerEmployerState" placeholder="State" 
                                             <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerEmployerState}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.employerState}"</c:if> "/>
                                       
                                          </div>
                                           <div class="col-md-3">
                                              <label for ="customerEmployerCountry">Country</label>
                                                <select class="form-control" name="customerEmployerCountry" id="customerEmployerCountry">
                                              <c:forEach items="${countries}" var="country">
                                                  <option value="${country.getName()}">${country.getName()}</option>
                                              </c:forEach>
                                              </select>
                                          </div>
                                      </div>      
                                    </div>
                                </div>
                              
                            </fieldset>
                                         
                        </div>
                        
                        <div class="col-md-4">
                       
                            <fieldset>
                                <legend style="padding-left:10px !important;">System Information</legend>
                              
                        <!-- Password show decision area --> 
                        <c:if test="${userTypeId == null}"> <!-- show only when new customer is registering himself -->
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="customerPassword">Password</label>
                                      <input type="password" class="form-control" id="customerPassword" name="customerPassword" placeholder="Password" 
                                        
                                             />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="customerConfirmPassword">Confirm Password</label>
                                      <input type="password" class="form-control" id="customerConfirmPassword" name="customerConfirmPassword" placeholder="Confirm Password"  />
                                    </div>
                                </div>
                            </div>
                        </c:if>
                        
                        
                            </fieldset>
                        </div>
                        
                                  
                        </div>
                    </div>
                       <div class="box box-default">
                    
                    <div class="row" style="padding-top:10px;margin: 10px">
                    <div class="col-md-12" >
                        <fieldset>
                            <legend style="padding-left:10px !important;">Contact Information</legend>
                   <div class="row">
                        
                        <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-3">
                            
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="customerStreet">Street</label>
                                <input type="text" class="form-control" id="customerStreet" name="customerStreet" placeholder="Street"  
                                       <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerStreet}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.street}"</c:if> "/>
                            </div>
                        </div>
                        
                        <div class="col-md-3">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="customerCity">City</label>
                              
                                <input type="text" class="form-control" id="customerCity" name="customerCity" placeholder="City"  
                                       <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerCity}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.city}"</c:if> " />
                                  
                                
                            </div>
                        </div>
                        <c:if test="${fn:length(errors) > 0 }"><c:set var="state" value="${param.customerState}" scope="session" /></c:if>
                        <c:if test="${fn:length(errors) <= 0 }"><c:set var="state" value="${customer.state}" scope="session" /></c:if>
                      
                        <div class="col-md-3">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;"/>
                              <label for="customerState">State</label>
                              <select class="form-control" id="customerState" name="customerState" 
                              <c:if test='${action == "edit" && userType > 1}'>disabled="disabled"</c:if>  >
                                    <option value="">--choose--</option>
                                    
                                    <option value="ABUJA FCT" <c:if test="${state == 'ABUJA FCT'}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                    <option value="Abia" <c:if test="${state == 'Abia'}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                    <option value="Adamawa" <c:if test="${state == 'Adamawa'}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                    <option value="Akwa Ibom" <c:if test="${state == 'Akwa Ibom'}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                    <option value="Anambra" <c:if test="${state == 'Anambra'}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                    <option value="Bauchi" <c:if test="${state == 'Bauchi'}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                    <option value="Bayelsa" <c:if test="${state == 'Bayelsa'}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                    <option value="Benue" <c:if test="${state == 'Benue'}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                    <option value="Borno" <c:if test="${state == 'Borno'}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                    <option value="Cross River" <c:if test="${state == 'Cross River'}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                    <option value="Delta" <c:if test="${state == 'Delta'}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                    <option value="Ebonyi" <c:if test="${state == 'Ebonyi'}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                    <option value="Edo" <c:if test="${state == 'Edo'}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                    <option value="Ekiti" <c:if test="${state == 'Ekiti'}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                    <option value="Enugu" <c:if test="${state == 'Enugu'}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                    <option value="Gombe" <c:if test="${state == 'Gombe'}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                    <option value="Imo" <c:if test="${state == 'Imo'}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                    <option value="Jigawa" <c:if test="${state == 'Jigawa'}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                    <option value="Kaduna" <c:if test="${state == 'Kaduna'}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                    <option value="Kano" <c:if test="${state == 'Kano'}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                    <option value="Katsina" <c:if test="${state == 'Katsina'}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                    <option value="Kebbi" <c:if test="${state == 'Kebbi'}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                    <option value="Kogi" <c:if test="${state == 'Kogi'}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                    <option value="Kwara" <c:if test="${state == 'Kwara'}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                    <option value="Lagos" <c:if test="${state == 'Lagos'}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                    <option value="Nassarawa" <c:if test="${state == 'Nassarawa'}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                    <option value="Niger" <c:if test="${state == 'Niger'}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                    <option value="Ogun" <c:if test="${state == 'Ogun'}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                    <option value="Ondo" <c:if test="${state == 'Ondo'}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                    <option value="Osun" <c:if test="${state == 'Osun'}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                    <option value="Oyo" <c:if test="${state == 'Oyo'}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                    <option value="Plateau" <c:if test="${state == 'Plateau'}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                    <option value="Rivers" <c:if test="${state == 'Rivers'}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                    <option value="Sokoto" <c:if test="${state == 'Sokoto'}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                    <option value="Taraba" <c:if test="${state == 'Taraba'}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                    <option value="Yobe" <c:if test="${state == 'Yobe'}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                    <option value="Zamfara" <c:if test="${state == 'Zamfara'}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                              </select>
                            </div>
                        </div>
                     
                            <div class="col-md-3">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="customerCountry">Country</label>
                             <select class="form-control" name="customerCountry" id="customerCountry">
                                              <c:forEach items="${countries}" var="country">
                                                  <option value="${country.getName()}">${country.getName()}</option>
                                              </c:forEach>
                                              </select>
                            </div>
                        </div>  
                            <div class="col-md-4">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="customerPhone">Phone Number</label>
                               
                                <input type="tel" class="form-control" id="customerPhone" name="customerPhone" placeholder="Phone Number" 
                                       <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.phone}"</c:if> "/>
                                    
                            </div>
                           </div>
                         <div class="col-md-4">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="customerOtherPhone">Other Phone</label>
                               <% //work needed to be done here %>
                                <input type="tel" class="form-control" id="customerOtherPhone" name="customerOtherPhone" placeholder="Other Phone" 
                                       <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.phone}"</c:if> "/>
                                    
                            </div>
                           </div>
                       <div class="col-md-4">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="customerPostalAddress">Postal Address</label>
                               <% //work needed to be done here %>
                                <input type="tel" class="form-control" id="customerPostalAddress" name="customerPostalAddress" placeholder="Postal Address" 
                                       <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.phone}"</c:if> "/>
                                    
                            </div>
                        </div>   
                    </div>           
                    </div>
                        </fieldset>
                    </div>
                    </div>
                    </div>
                  
                    <div class="box box-default">
                    <div class="row" style="padding-top:10px;margin: 10px">
                    <div class="col-md-12">
                                <fieldset>
                            <legend style="padding-left:10px !important;">Next of Kin</legend>
                            <div class="col-md-12">
                            
                            <div class="row">
                              <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerKinNames">Next of Kin Name</label>
                                      <input type="text" class="form-control" id="customerKinNames" name="customerKinName" placeholder="Enter Kin Name"  
                                          <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                          value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinName}"</c:if> "/>
                                            
                                    </div>
                                </div>
                                 <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerKinRelationship">Relationship Of Next Of kin</label>
                                     
                                      <input type="text" class="form-control" id="customerKinRelationship" name="customerKinRelationship" placeholder="Relationship"  
                                          <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                          value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinRelationship}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinRelationship}"</c:if> "/>
                                            
                                    </div>
                                </div> 
                                <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerKinEmail">Email Of Next Of kin</label>
                                     
                                      <input type="text" class="form-control" id="customerKinEmail" name="customerKinEmail" placeholder="Email Of Next Of kin"  
                                          <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                          value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinEmail}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinEmail}"</c:if> "/>
                                            
                                    </div>
                                </div> 
                                <div class="col-md-4">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                    <label for="customerKinPhone">Next of Kin Phone Number</label>
                                       
                                        <input type="tel" class="form-control" id="customerKinPhone" name="customerKinPhone" placeholder="Enter Kin Phone Number" 
                                               <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinPhone}"</c:if> "/>
                                              
                                            
                                    </div>
                                </div>
                               <div class="col-md-4 " >
                                    <div class="row" >
                            <div class="form-group" style="padding-left:25px !important;">
                              <label for="customerKinAddress" style="">Next of Kin Address</label>
                               <input type="text" class="form-control" id="customerKinAddress" name="customerKinAddress" placeholder="Enter Kin Address" style="  width:100%;"
                                      <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                      value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinAddress}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinAddress}"</c:if> "/>
                                      <br/>
                            </div>
                                </div>
                            </div>
                            </div>
                            </div>
                            </fieldset>
                    </div>
                         </div>
                  </div><!-- /.box-body -->
                     
                  <div class="box box-default">
                    <div class="row" style="padding-top:10px;margin: 10px">
                    <div class="col-md-12">
                                <fieldset>
                            <legend style="padding-left:10px !important;">Bank Information</legend>
                            <div class="col-md-12">
                            
                            <div class="row">
                              <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerBanker">Banker</label>
                                     
                                      <input type="text" class="form-control" id="customerBanker" name="customerBanker" placeholder="Bank Name"  
                                          <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                          value=<c:if test="${fn:length(errors) > 0 }">"${param.customerBanker}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.banker}"</c:if> "/>
                                            
                                    </div>
                                </div>
                                 <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerAccountName">Account Name</label>
                                      <% //work needed to be done here %>
                                      <input type="text" class="form-control" id="customerAccountName" name="customerAccountName" placeholder="Account Name"  
                                          <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                          value=<c:if test="${fn:length(errors) > 0 }">"${param.customerAccountName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.accountName}"</c:if> "/>
                                            
                                    </div>
                                </div> 
                                <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerAccountNumber">Account Number</label>
                                      
                                      <input type="number" class="form-control" id="customerAccountNumber" name="customerAccountNumber" placeholder="Account Number"  
                                          <c:if test='${action == "edit" && userType > 1}'>readonly</c:if> 
                                          value=<c:if test="${fn:length(errors) > 0 }">"${param.customerAccountNumber}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.accountNumber}"</c:if> "/>
                                            
                                    </div>
                                </div> 
                         
                            </div>
                            </div>
                            </fieldset>
                    </div>
                         </div>
                  </div><!-- /.box-body -->
                    
      <c:if test="${action == 'edit'}">
      <div class="col-md-12">
          <c:if test="${userType == 1}">
          <button class="btn btn-success btn-lg" type="submit">Update</button>
          </c:if>
      </div>
      </c:if>
      <c:if test="${action == 'new'}">
      <div class="col-md-12">
          <a class="btn btn-primary" href="#" onclick="return proceed()" role="button">Proceed <i class="fa fa-long-arrow-right"></i></a>
      </div>
      </c:if>
                    </div>
                     
                        
                 </div><!-- /.col-md-4 -->
                </div><!-- /.row -->
                </div> 
      </div><!-- /.box -->
     
  <!-- Step two . File Upload -->    
 <div class="row" id="step2" style="display:none">
           <div class="col-md-12">
              <!-- general form elements -->
               
                <!-- form start -->
               <div class="box box-primary" id="step2_box">
                <div class="box-header with-border">
                  <h3 class="box-title">Customer Document
                      </h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <div style="background:#ecf0f5 !important;">
                <div class="box-body">
                  <div class="box box-default">
                    
                    <div class="row" style="padding-top:10px;margin: 10px">
                    <div class="col-md-12" >
                        <fieldset>
                            <legend style="padding-left:10px !important;">Passport Upload</legend>
                   <div class="row">
                        
                        <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-12">
                            <div class="col-md-4">
                            <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                           <label for="customerPhoto" ">Customer Passport</label>
                                  <div class="btn btn-primary">
                                  <input    class="form-control" type="file" name="customerPhoto" accept="image/gif, image/jpeg, image/png" id="customerPhoto" >
                                  <input type="hidden" name="customerPhotoHidden" 
                                      <c:if test="${customerPhotoHidden == null && customer.photoPath==null}"> value="" </c:if>
                                      <c:if test="${customerPhotoHidden != null}"> value="${customerPhotoHidden}"</c:if> 
                                      <c:if test="${customer.photoPath != null}"> value="${customer.photoPath}"</c:if> 
                                  />
                                 </div>
                        </div>
                        </div>
                       <div class="col-md-4">
                                  <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                            <label for="customerKinPhoto" ">Customer Kin Photo</label>
                                  <div class="btn btn-primary">
                                  <input    class="form-control" type="file" name="customerKinPhoto" accept="image/gif, image/jpeg, image/png" id="customerKinPhoto" >
                                  <input type="hidden" name="customerKinPhotoHidden" 
                                      <c:if test="${customerKinPhotoHidden == null && customer.photoPath==null}"> value="" </c:if>
                                                      <c:if test="${customerKinPhotoHidden != null}"> value="${customerKinPhotoHidden}"</c:if> 
                                                      <c:if test="${customer.kinPhotoPath != null}"> value="${customer.kinPhotoPath}"</c:if> 
                                                 />
                                 </div>
                        </div>
                         </div>  
                      <div class="col-md-4">
                     <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                         <label for="customerPhotoID">ID card/Driving License ,etc</label>
                                  <div class="btn btn-primary">
                                  <input    class="form-control" type="file" name="customerPhotoID" accept="image/gif, image/jpeg, image/png" id="customerPhotoID" >
                                  <input type="hidden" name="customerPhotoIDHidden" 
                                      <c:if test="${customerPhotoIDHidden == null && customer.photoPath==null}"> value="" </c:if>
                                      <c:if test="${customerPhotoIDHidden != null}"> value="${customerPhotoIDHidden}"</c:if> 
                                      <c:if test="${customer.photoPath != null}"> value="${customer.photoPath}"</c:if> 
                                  />
                                 </div>
                        </div>
                            </div>      
                    </div>
                        </div>
                      
                    </div>
                        </fieldset>
                    </div>
                    </div>
                    </div>
                   
                   
                    <div class="box box-default">
                    
                    <div class="row" style="padding-top:10px;margin: 10px">
                    <div class="col-md-12" >
                        <fieldset>
                            <legend style="padding-left:10px !important;">Bank Credentials</legend>
                   <div class="row">
                        
                        <div class="col-md-4">
                            <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                         <label for="customerBankStandingOrder">Bank Standing Order / Post Dated Cheques </label>
                                  <div class="btn btn-primary">
                                  <input    class="form-control" type="file" name="customerBankStandingOrder" accept="image/gif, image/jpeg, image/png" id="customerBankStandingOrder" >
                                  <input type="hidden" name="customerBankStandingOrder" 
                                      <c:if test="${customerBankStandingOrder == null && customer.customerBankStandingOrder==null}"> value="" </c:if>
                                      <c:if test="${customerBankStandingOrder != null}"> value="${customerBankStandingOrder}"</c:if> 
                                      <c:if test="${customer.customerBankStandingOrder != null}"> value="${customer.customerBankStandingOrder}"</c:if> 
                                  />
                                 </div>
                        </div>    
                        </div>
                    </div>
                        </fieldset>
                    </div>
                    </div>
                    </div>
                   
                 <c:if test="${action == 'edit'}">
      <div class="col-md-12">
          <c:if test="${userType == 1}">
          <button class="btn btn-success btn-lg" type="submit">Update</button>
          </c:if>
      </div>
      </c:if>
      <c:if test="${action == 'new'}">
      <div class="col-md-12">
          <a class="btn btn-primary" href="#" onclick="return validateCustomerRegForm()" role="button">Proceed <i class="fa fa-long-arrow-right"></i></a>
      </div>
      </c:if>   
                </div>     
                 </div><!-- /.col-md-4 -->
                </div><!-- /.row -->
                </div> 
      </div><!-- /.box -->
      
     
<c:if test="${customer.customerId=='' || customer.customerId== null}">
                    
<!-- step 3  product order -->

<div class="row" id="step3" style="display:none">
    
               <div class="col-md-12">
              <!-- general form elements -->
               <div class="box box-primary" id="productCartBox">
               
                <div class="box-header with-border">
                  <h3 class="box-title">Product Order Cart</h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <div style="background:#ecf0f5 !important;">
                <!--<form role="form" name="customerRegistration" method="POST" action="Order" enctype="multipart/form-data">-->
                  <div class="box-body">
                  <div class="row">
                  <div class="col-md-12">
                  <div class="box box-default">
                    <div class="row" style="padding-top:10px;">
                    <div class="col-md-12">
                        <input type="hidden" name="dataHidden" id="dataHidden" />
                    	<fieldset>
                        	<legend style="padding-left:20px !important;">Product Details</legend>
                            <div class="col-md-12">
                            	<div class="row">
                                   
                                              
                                   <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="selectProdcut">Select Product</label>
                                            <select class="form-control select2" id="selectProduct" style="width: 100%;" onchange="getProjectUnits('${pageContext.request.contextPath}', 'Project','')" >
                                                <option value="" selected="selected">-- choose --</option>
                                                
                                              <c:forEach items="${projects}" var="project" >
                                                <option  value="${project.id}">${project.name}</option>
                                              </c:forEach>
                                            </select>
                                        </div> 
<!--                                              /.form-group select product -->
                                    </div>
                               
                                   <div class="col-md-3">
                                    	<div class="form-group">
                                          <input type="hidden" id="pUnitId" />
                                            <label for="selectUnit">Select Unit</label>
                                            
                                            <select class="form-control select2" id="selectUnit" style="width: 100%;" onchange="getProjectQuantity('${pageContext.request.contextPath}', 'ProjectUnit')">
                                              <option value="#" selected="selected">-- choose --</option>
                                             
                                            </select>
                                        </div> 
<!--                                              /.form-group  select unit-->
                                    </div>
                                
                                	<div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="selectQuantity">Select Quantity</label>
                                            
                                            <select class="form-control select2" id="selectQuantity" style="width: 100%;" onchange="calculateProductAmount()">
                                              <option value="#" selected="selected">-- choose --</option>
                                             
                                            </select>
                                        </div> 
<!--                                            /.form-group select quantity -->
                                    </div>
                                </div>
                                    <div class="row">
                                
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productAmount">Amount</label>
                                            <span id="amountPerUnit" class="productSpan">
                                                Amount per Unit: <span id="amountUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="amountTotalUnit"></span>
                                            </span>
                                            <input type="text" class="form-control" id="productAmount" name="productAmount" style="width: 100%;" readonly>
                                        </div> 
<!--                                            /.form-group amount -->
                                    </div>
                                              
                               
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMinimumInitialAmount">Initial Amount(N)</label>
                                            <span id="amountPerUnit" class="productSpan">
                                                min initial amt /unit: <span id="initialAmountPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="minInitialAmountSpan"></span><br/>
                                            </span>
                                            <input type="text" class="form-control" id="productMinimumInitialAmount" name="productMinimumInitialAmount" style="width: 100%;"  onkeyup="calculateAmountToPay()">
                                        </div> 
<!--                                            /.form-group initial monthly amount -->
                                    </div>
                                              
                                              <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="amountLeft">Balance Payable(N)</label>
                                            <span id="amountPerUnit" class="productSpan">
                                               
                                            </span>
                                            <input type="text" class="form-control" id="amountLeft" name="amountLeft" style="width: 100%;" readonly >
                                        </div> 
<!--                                                  /.form-group initial monthly amount -->
                                    </div>
                               <input type="hidden" id="editMode" value="" />
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMaximumDuration">Payment Duration</label>
                                            <span id="amountPerUnit" class="productSpan">
                                                max payment duration /unit: <span id="payDurationPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="payDurationPerQuantity"></span>
                                            </span>
                                            <div class="row">
                                            	<div class="col-md-12">
                                            		<select class="form-control select2"  id="productMaximumDuration" style="width: 100%;" onchange="monthlyPayCalculator('exsiting')">
                                                      <option value="#" selected="selected">-- choose --</option>
                                                    
                                                    </select>
                                                </div>
                                            </div>
                                        </div> <!-- /.form-group Duration -->
                                    </div>
                               
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMinimumMonthlyPayment">Monthly Payment(N)</label>
                                            <span id="amountPerUnit" class="productSpan">
                                                min monthly pay / unit: <span id="monthlyPayPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="monthlyPayPerQuantity"></span>
                                            </span>
                                            <input type="text" class="form-control" id="productMinimumMonthlyPayment" name="productMinimumMonthlyPayment" style="width: 100%;" onKeyup="calculateDurationFromMonthlyPay()">
                                            <span id="finalAmount" style="display:block"></span>
                                        </div> <!--/.form-group amount -->
                                    </div>
                               
                                    <c:if test="${userTypeId != null && userTypeId < 3 }">     
                                          <div class="col-md-2">
                                              <div class="form-group">
                                                  <label>
                                                      Commission(%)
                                                  </label>
                                                  <span class="productSpan">This is the commission payable to an agent</span>
                                                  <input 
                                                      type="text" 
                                                      class="form-control" 
                                                      value="0" 
                                                      name="commp" 
                                                      id="commp" 
                                                      <c:if test="${userTypeId == 2}">readonly="readonly"</c:if> 
                                                  />
                                              </div>
                                          </div>
                                    </c:if>
                                                
                                </div>
                                              
                                
                                <div class="row">
                                      
                                      <div class="col-md-2">
                                              <div class="form-group">
                                                  <label>
                                                      Notification Day
                                                  </label>
                                                  <span class="productSpan">Select the day of the month to receive monthly notification</span>
                                                  <input type="number" class="form-control" min="1" max="31" value="1" name="day_of_notification" id="day_of_notification"/>
                                              </div>
                                      </div>
                                      
                                  </div>               
                                              
                                              
                  <div class="row">
                        <div class="col-md-12 box-footer">
                            <div class="row">
                                <div class="col-md-4">
                                <div id="errorText" style="color:#722F37 !important; font-weight:bold !important;"></div>
                                </div>
                                <div class="col-md-2 pull-right">
                                   <div id="addToCartLabel"  style="margin: 0 auto !important;" >
                                    	<div class="form-group">
                                            <a class="btn btn-success" name="addToCart" id="addToCart" href="#" onClick=" return addToCart(event)" ><i class="fa fa-cart-plus"></i> Add to Cart</a>
                                        </div> 
                                   </div>
                               </div>
                           </div>
                        </div>
                  </div>
                            </div> <!--/.col-md-12 -->
                    	</fieldset>
                  	</div> <!--/.col-md-12 -->
                 	</div> <!--/.row -->
                  </div> <!-- /.box box-default -->
                  </div> <!-- /.col-md-4 -->
                  
   </div> <!-- /.row -->
                  
                  
                  
                  
                  
  <!-- 
    *****************************************
    Product Cart starts here
    *****************************************
  -->
  <div class="row">
  <div class="col-md-12">
  <div class="box box-default">
      <input type="hidden" name="dataHidden" id="dataHidden" />
    <div class="row" style="padding-top:10px;">
    <div class="col-md-12" id="shoppingCart">

      <fieldset>
      <legend style="padding-left:20px !important;">Product Cart</legend>
                                
              <div class="col-md-12" >
                <div class="row" >
                  
                 <div class="col-md-12">   
                    
                 <table id="productCart" class="table table-bordered table-striped table-hover" style="text-align:right !important;">
                    <thead>
                      <tr>
                        <th>Product</th>
                        <th>Product Unit</th>
                        <th>Quantity</th>
                        <th>Amount</th>
                        <th>Initial Amount</th>
                        <th>Amount to Pay</th>
                        <th>Duration</th>
                        <th>Monthly Pay</th>
                        <th>Transaction Deduction</th>
                        
                        <th style="width: 80px">Action</th>
                        
                      </tr>
                    </thead>
                    <tbody>
                       
                            
                        
                   </tbody>
                    <tfoot style="text-align:right !important;color:green !important; font-weight:bold !important;">
                      <tr>
                        <th colspan="8" align="right" style="text-align:right !important;">Total</th>
                        
                        <th style="text-align:right !important;" align="right"><input type="hidden" id="CartActualSum" value="0" /><span id="cartSum">0</span></th>
                        
                        <th></th>
                        
                      </tr>
                    </tfoot>
                   
                  </table>
                 </div>
                </div>
               </div>  
                   <div class="col-md-2 pull-right">
                     <div class="form-group">
                        <a href="#" class="btn" name="checkOutToPay" id="checkOutToPay" onClick="return checkOutOfCart();"><i class="fa fa-cart-plus"></i>Checkout</a>
                    </div> 

                 </div>
                 <div class="col-md-1"></div>
               </fieldset>
              </div> 
                        
                         <div class="col-md-12" id="paymentCheckout">
                      
                    	<fieldset>
                        <legend style="padding-left:20px !important;">Check Out</legend>
                                
                            <div class="col-md-11" >
                            	
                                <!-- Start of Payment Method Container -->
                                <div class="row" > 
                                    <div class="col-md-12">
                                        <span style="color:green;font-weight:bold;">You'd be paying <span id='paySum'></span></span>
                                    	<div class="form-group">
                                            <label for="paymentMethod">Payment method:</label><br/>
                                            <input type="radio" name="paymentMethod" value="1" id="bankdep" onclick="showNecessaryMenu(1)"/>&nbsp;<label for="bankdep" style="display:inline !important;">Bank Deposit</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                            <c:if test="${userTypeId != null && userTypeId == 3 }">
                                            <input type="radio" name="paymentMethod" value="2" id="paywithcard" onclick="showNecessaryMenu(2)"/>&nbsp; <label for="paywithcard" style="display:inline !important;cursor:pointer !important;">Credit/Debit Card <img src="${pageContext.request.contextPath}/images/img/paywithcard.png" /></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            </c:if>
                                            <input type="radio" name="paymentMethod" value="3" id="paywithcash" onclick="showNecessaryMenu(3)"/>&nbsp;<label for="paywithcash" style="display:inline !important;"> Cash</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="paymentMethod" value="4" id="bankTransfer" onclick="showNecessaryMenu(4)"/>&nbsp; <label for="bankTransfer" style="display:inline !important;cursor:pointer !important;">Bank Transfer </label>
                                        </div>
                                    </div>
                                    
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="companyAccount">Company Account</label>
                                            <select name="companyAccount" id="companyAccount" class="form-control select2" style="width: 100%;">
                                                <option value="">--Select Account--</option>
                                                <c:forEach items="${companyAccount}" var="CA">
                                                    <option value="${CA.getId()}">${CA.getAccountDetails()}</option>
                                                </c:forEach>
                                            </select>
                                        </div> 
                                    </div>
                                </div>
                                <!-- End of Payment Method Container -->        
                                            
                                 <!-- Pay via Bank Deposit Div Container -->            
                                <div class='row' id='pwBankdeposit'>
                                    
                                    
                                    
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="depositorsName">Depositor's Name</label>
                                            <input type="text" class="form-control" id="depositorsName" name="depositorsName" style="width: 100%;">
                                        </div> 
                                    </div>
                                    
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerNumber">Teller Number</label>
                                            <input type="text" class="form-control" id="tellerNumber" name="tellerNumber" style="width: 100%;">
                                        </div> 
                                    </div>
                             
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerAmount">Amount</label>
                                            <input type="text" class="form-control amount-box" id="tellerAmount" name="tellerAmount" style="width: 100%;">
                                        </div>      
                                    </div>
                                    
                                    <div class="col-md-2">
                                    	<div class="form-group" style="padding-top:25px !important;">
                                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge"/>
                                        </div>      
                                    </div>
                                    
                                </div>
                                <!-- End of Pay Via Bank Deposit Div Container -->
                                
                                
                                <!-- Pay with Cash Div Container -->
                                <div class='row' id='pwCash'>
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="cashAmount">Amount</label>
                                            <input type="text" class="form-control amount-box" id="cashAmount" name="cashAmount" style="width: 100%;">
                                        </div>      
                                    </div>
                                    <div class="col-md-2">
                                    	<div class="form-group" style="padding-top:25px !important;">
                                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge" style="vertical-align:bottom !important;"/>
                                        </div>      
                                    </div>
                                </div>
                                <!-- End of Pay with Cash Div Container -->
                                
                                
                                <c:if test="${userTypeId != null && userTypeId == 3 }">
                                 <!-- Pay with Card Div Container -->
                                <div class='row' id='pwCard'>
                                	<div class="col-md-2">
                                            <div class="form-group">
                                                <label for="tellerNumber">Click to proceed to payment</label>
                                                <!--<a href="${pageContext.request.contextPath}/images/img/webpay.png" target="_blank" class="btn btn-success"><i class="fa fa-angle-double-right"></i> Pay Now</a>
                                                --> <button type="submit"  name="Pay" class="btn btn-success"  style="vertical-align:bottom !important;"><i class="fa fa-angle-double-right"></i> Pay Now</button>
                                            </div> 
                                        </div>
                                </div>
                                <!-- End of Pay with Card Div Container -->
                                </c:if>
                                
                                 <!-- Pay with Card Div Container -->
                                <div class='row' id='pwBankTransfer'>
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="bankName">Depositor's Bank Name</label>
                                            <input type="text" class="form-control" id="transfer_bankName" name="transfer_bankName" style="width: 100%;">
                                        </div> 
                                    </div>
                                    
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="accountNo">Depositor's Account No</label>
                                            <input type="text" class="form-control" id="transfer_accountNo" name="transfer_accountNo" style="width: 100%;">
                                        </div> 
                                    </div>
                                    
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="accountNo">Depositor's Account Name</label>
                                            <input type="text" class="form-control" id="transfer_accountName" name="transfer_accountName" style="width: 100%;">
                                        </div> 
                                    </div>
                                    
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerAmount">Amount</label>
                                            <input type="text" class="form-control amount-box" id="transfer_amount" name="transfer_amount" style="width: 100%;">
                                        </div>      
                                    </div>
                                    
                                    <div class="col-md-1">
                                    	<div class="form-group" style="padding-top:25px !important;">
                                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge"/>
                                        </div>      
                                    </div>
                                </div>
                                <!-- End of Pay with Cash Div Container -->
                                             
                              </div>
                        </fieldset>
                         </div>
         </div>
                                 
                            <div class="col-md-1"></div>
                  	</div> <!--/.col-md-12 -->
                 	</div> <!--/.row -->
 </div> <!--/.box box-default -->
           
          <!-- 
            *****************************************
            Product Cart Ends here
            *****************************************
          -->
          
      </div> <!---/.col-md-4 Box-Body class div ends here -->

      </div><!-- /.row -->
    
      <input type="hidden" name="cartDataJson" id="cartDataJson" />
    </div>

  </div><!-- /.box -->
  
  <div class="col-md-12">
      <a class="btn btn-primary" href="#" onclick="return showCustomerReg()" role="button"><i class="fa fa-long-arrow-left"></i> Customer registration</a>
  </div>
  
 </div><!-- /.box -->
                    
</c:if>
      
      <!--
      <div class="row">
          
          <div class="box-footer" style="background-color:transparent;">
                      <input type="submit" class="btn btn-primary" name="customerCreate" value="Save"/>
          </div>
          
      </div>
      -->
          <input type="hidden" id="pageContext" value="${pageContext.request.contextPath}" />
</form>
<script>
  $( function() {
    $( "#customerDateOfBirth" ).datepicker({changeMonth: true, changeYear: true , yearRange: "1930:2017" , dateFormat: "dd/mm/yy" });
  } );
  
  //onclick='$( "#customerDateOfBirth" ).datepicker({changeMonth: true, changeYear: true , yearRange: "1930:2017" });'
  </script>