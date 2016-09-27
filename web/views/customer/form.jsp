<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--
    Process Step
-->
<div class="stepwizard">
    <div class="stepwizard-row">
        <div class="stepwizard-step">
            <button type="button" id="process-step-1" class="btn btn-primary btn-circle" onclick="return showCustomerReg()">1</button>
            <p>Register Customer</p>
        </div>
        <div class="stepwizard-step">
            <button type="button" id="process-step-2" class="btn btn-default btn-circle" disabled="disabled" onclick="return showOrderProduct()">2</button>
            <p>Order Product</p>
        </div>
        <div class="stepwizard-step">
            <button type="button" id="process-step-3" class="btn btn-default btn-circle" disabled="disabled">3</button>
            <p>Payment</p>
        </div> 
    </div>
</div>


 <c:if test="${userTypeId != null && userTypeId == 1 }">
 <div class="row" id="agentListContainer">
     
     <section class="content-header">
         
         
         <div class="box">
                <div class="box-header">
                  <h3 class="box-title block">
                      Select an agent
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
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
                                <td><img alt="No Image" class="img-responsive img-thumbnail" src="<c:out value='${agent.photoPath}'></c:out>" /></td>
                                <td class="agentId"><c:out value="${agent.agentId}" /></td>
                                <td class="agentFname"><c:out value="${agent.firstname}" /></td>
                                <td class="agentMname"><c:out value="${agent.middlename}" /></td>
                                <td class="agentLname"><c:out value="${agent.lastname}" /></td>
                                <td class="agentPhone"><c:out value="${agent.phone}" /></td>
                                <td class="agentEmail"><c:out value="${agent.email}" /></td>
                                <td class="agentState"><c:out value="${agent.state}" /></td>
                              
                                <td>
                                    <input type="hidden" class="agentImg" value='<c:out value="${agent.photoPath}"></c:out>' />
                                    <a class="btn btn-primary" href="#" onclick="selectAgent('${agent.agentId}')" role="button">Select</a>
                                </td>
                            </tr>
                        </c:forEach>
                  </tbody>
                    <tfoot>
                    
                    </tfoot>
                  </table>
                  <div><span><a href="#" onclick="showSelectedAgent()">View selected agent</a></span></div>
                </div><!-- /.box-body -->
              </div><!-- /.box -->
         
     </section>
     
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
                         <img src="" alt="No image" />
                     </div>
                     
                     <div class="col-md-9">
                         <span class="agent_name"></span><br/>
                         <span class="agent_moible"></span><br/>
                         <span class="agent_state"></span>
                     </div>
                     
                 </div>
                 
             </div>
             
             <div class="box-footer">
                 <a href="#" onclick="showAgentList()">Show agent List<<</a>
             </div>
             
         </div>
         
     </div>
     
 </div>
                                
</c:if>  
<form role="form" name="customerRegistration" method="POST" action="Customer" enctype="multipart/form-data">
  
    <input type="hidden" name="customer_id" value="" />
 
 <div class="row" id="step1">
           <div class="col-md-12">
              <!-- general form elements -->
               
                <!-- form start -->
               <div class="box box-primary">
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
                              <c:if test="${action != 'new'}">
                                  <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Order?action=new&customer=${customer.customerId}">Buy Product</a>
                                  &nbsp;&nbsp;&nbsp;
                                  <a class="btn btn-primary" href="Customer?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Customer</a>
                              </c:if>
               
                    
                    <div class="box box-default">
                    <div class="row" style="padding-top:10px;">
                        <div class="col-md-4">
                      <fieldset>
                        <legend style="padding-left:10px !important;">Personal Information</legend>
                          <div class="row">
                              <div class="col-md-12">
                                  <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerFirstname">First Name</label>
                                      <input type="text"  name="customerFirstname" class="form-control" id="customerFirstname" placeholder="First Name" 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerFirstname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.firstname}"</c:if> "/>
                                             
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerMiddlename">Middle Name</label>
                                      <input type="text" class="form-control" id="customerMiddlename" name="customerMiddlename" placeholder="Middle Name"
                                        value=<c:if test="${fn:length(errors) > 0 }">"${param.customerMiddlename}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.middlename}"</c:if> "/>
                                             
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerLastname">Last Name</label>
                                      <input type="text" class="form-control" name="customerLastname" id="customerLastname" placeholder="Last Name" 
                                         value=<c:if test="${fn:length(errors) > 0 }">"${param.customerLastname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.lastname}"</c:if> "/>
                                             
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        </div>
                        
                        <div class="col-md-4">
                       
                            <fieldset>
                                <legend style="padding-right:10px !important;">System Information</legend>
                          <div class="row">
                             <div class="col-md-12">
                            <div class="form-group" style="padding-left:10px !important;padding-right:10px !important">
                              <label for="customerEmail">Email address</label>
                              <input type="email" class="form-control" id="customerEmail" name="customerEmail" placeholder="your@email.com" 
                                     value=<c:if test="${fn:length(errors) > 0 }">"${param.customerEmail}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.email}"</c:if> "
                              <c:if test="${customer.customerId !='' && customer.customerId != null }">readonly="true"</c:if>
                              />
                                     
                            </div>
                        </div>
                            </div>
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
                            </fieldset>
                        </div>
                         <div class="col-md-4">
                             <div class="row">
                             <div class="row text-center" id="imgholder">
                        <div class="col-md-6 col-md-offset-3  col-xs-4 col-xs-offset-4 ">
                            <div class="form-group text-center">
                              <img <c:if test="${customer.photoPath != null && customer.photoPath != "default"}"> src="${pageContext.request.contextPath}/images/uploads/customers/${customer.photoPath}" </c:if>
                               <c:if test="${customer.photoPath == "default"  || customer.photoPath  == null }"> src="${pageContext.request.contextPath}/images/img/avatar.png"
                    </c:if>
                                class=" img-responsive text-center" style="max-height:220px !important;"/>
                            </div>
                        </div>
                        <div class="col-md-6 col-md-offset-3  col-xs-4 col-xs-offset-4 ">
                            <div class="form-group">
                              <div class="btn-group btn-group-xs">
                                  <div class="btn btn-primary btn-file">
                      Change <span title="Change Profile Picture" class="glyphicon glyphicon-edit"></span> 
                      <input type="file" name="customerPhoto" accept="image/gif, image/jpeg, image/png" id="customerPhoto" >
                      <input type="hidden" name="customerPhotoHidden" 
                           <c:if test="${customerPhotoHidden == null && customer.photoPath==null}"> value=""
                    </c:if><c:if test="${customerPhotoHidden != null}"> value="${customerPhotoHidden}"
                    </c:if> <c:if test="${customer.photoPath != null}"> value="${customer.photoPath}"
                    </c:if> />
                    </div>
                                  
                              </div>
                                
                            </div>
                        </div>
                    </div>
                    
                  </div>
                         </div>
                    </div>
                    </div>
                   
                    <div class="box box-default">
                    
                    <div class="row" style="padding-top:10px;">
                    <div class="col-md-12" >
                        <fieldset>
                            <legend style="padding-left:10px !important;">Contact Information</legend>
                   <div class="row">
                        
                        <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-4">
                            
                            <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                            <label for="customerStreet">Street</label>
                                <input type="text" class="form-control" id="customerStreet" name="customerStreet" placeholder="Street"  
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerStreet}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.street}"</c:if> "
                                       />
                            </div>
                        </div>
                        
                        <div class="col-md-4">
                            <div class="form-group" style="padding-left:10px !important;">
                            <label for="customerCity">City</label>
                              
                                <input type="text" class="form-control" id="customerCity" name="customerCity" placeholder="City"  
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.customerCity}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.city}"</c:if> "
                                       />
                                  
                                
                            </div>
                        </div>
                        <c:if test="${fn:length(errors) > 0 }"><c:set var="state" value="${param.customerState}" scope="session" /></c:if>
                        <c:if test="${fn:length(errors) <= 0 }"><c:set var="state" value="${customer.state}" scope="session" /></c:if>
                      
                        <div class="col-md-4">
                            <div class="form-group" style="margin-right:24px !important;padding-left:20px !important;"  />
                              <label for="customerState">State</label>
                              <select class="form-control" id="customerState" name="customerState"  >
                                    <option value="">--choose--</option>
                                    
                                    <option value="ABUJA FCT" <c:if test="${state == "ABUJA FCT"}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                    <option value="Abia" <c:if test="${state == "Abia"}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                    <option value="Adamawa" <c:if test="${state == "Adamawa"}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                    <option value="Akwa Ibom" <c:if test="${state == "Akwa Ibom"}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                    <option value="Anambra" <c:if test="${state == "Anambra"}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                    <option value="Bauchi" <c:if test="${state == "Bauchi"}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                    <option value="Bayelsa" <c:if test="${state == "Bayelsa"}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                    <option value="Benue" <c:if test="${state == "Benue"}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                    <option value="Borno" <c:if test="${state == "Borno"}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                    <option value="Cross River" <c:if test="${state == "Cross River"}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                    <option value="Delta" <c:if test="${state == "Delta"}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                    <option value="Ebonyi" <c:if test="${state == "Ebonyi"}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                    <option value="Edo" <c:if test="${state == "Edo"}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                    <option value="Ekiti" <c:if test="${state == "Ekiti"}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                    <option value="Enugu" <c:if test="${state == "Enugu"}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                    <option value="Gombe" <c:if test="${state == "Gombe"}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                    <option value="Imo" <c:if test="${state == "Imo"}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                    <option value="Jigawa" <c:if test="${state == "Jigawa"}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                    <option value="Kaduna" <c:if test="${state == "Kaduna"}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                    <option value="Kano" <c:if test="${state == "Kano"}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                    <option value="Katsina" <c:if test="${state == "Katsina"}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                    <option value="Kebbi" <c:if test="${state == "Kebbi"}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                    <option value="Kogi" <c:if test="${state == "Kogi"}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                    <option value="Kwara" <c:if test="${state == "Kwara"}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                    <option value="Lagos" <c:if test="${state == "Lagos"}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                    <option value="Nassarawa" <c:if test="${state == "Nassarawa"}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                    <option value="Niger" <c:if test="${state == "Niger"}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                    <option value="Ogun" <c:if test="${state == "Ogun"}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                    <option value="Ondo" <c:if test="${state == "Ondo"}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                    <option value="Osun" <c:if test="${state == "Osun"}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                    <option value="Oyo" <c:if test="${state == "Oyo"}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                    <option value="Plateau" <c:if test="${state == "Plateau"}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                    <option value="Rivers" <c:if test="${state == "Rivers"}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                    <option value="Sokoto" <c:if test="${state == "Sokoto"}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                    <option value="Taraba" <c:if test="${state == "Taraba"}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                    <option value="Yobe" <c:if test="${state == "Yobe"}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                    <option value="Zamfara" <c:if test="${state == "Zamfara"}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                              </select>
                            </div>
                        </div>
                        
                        
                        
                    </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-6">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="customerPhone">Phone Number</label>
                               
                                <input type="tel" class="form-control" id="customerPhone" name="customerPhone" placeholder="Phone Number" 
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
                    <div class="row" style="padding-top:10px;">
                    <div class="col-md-12">
                                <fieldset>
                            <legend style="padding-left:20px !important;">Next of Kin</legend>
                            <div class="col-md-12">
                            
                            <div class="row">
                              <div class="col-md-6">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="customerKinNames">Next of Kin Name</label>
                                      <input type="text" class="form-control" id="customerKinNames" name="customerKinName" placeholder="Enter Kin Name"  
                                          value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinName}"</c:if> "/>
                                            
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                    <label for="customerKinPhone">Next of Kin Phone Number</label>
                                       
                                        <input type="tel" class="form-control" id="customerKinPhone" name="customerKinPhone" placeholder="Enter Kin Phone Number" 
                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinPhone}"</c:if> "/>
                                              
                                            
                                    </div>
                                </div>
                            </div>
                            </div>
                            <div class="col-md-12">
                                <div  class="row">
                             <div class="col-md-4" >
                                    <div class="row" >
                            <div class="form-group" style="padding-left:25px !important;padding-right:20px !important">
                              <label for="customerKinPhoto" style="">Next of Kin Picture</label>
                              <c:if test="${customer.customerId != ""}"> 
                               <img <c:if test="${customer.kinPhotoPath != null && customer.kinPhotoPath != "default"}"> src="${pageContext.request.contextPath}/images/uploads/customers/${customer.kinPhotoPath}" </c:if>
                               <c:if test="${customer.photoPath == "default"  || customer.photoPath  == null }"> src="${pageContext.request.contextPath}/images/img/avatar.png"
                                </c:if>
                                class="img-responsive text-center" width="50.33333333%"/>
                                </c:if>
                               <input type="file" class="form-control" id="customerKinPhoto" name="customerKinPhoto" accept="image/gif, image/jpeg, image/png" />
                               <input type="hidden" name="customerKinPhotoHidden" 
                           <c:if test="${customerKinPhotoHidden == null && customer.kinPhotoPath==null}"> value=""
                    </c:if><c:if test="${customerKinPhotoHidden != null}"> value="${customerKinPhotoHidden}"
                    </c:if> <c:if test="${customer.kinPhotoPath != null}"> value="${customer.kinPhotoPath}"
                    </c:if> />
                            </div>
                                </div>
                            </div>
                                    <div class="col-md-2" style="padding-right:50% !important;"></div>
                                <div class="col-md-6 " >
                                    <div class="row" >
                            <div class="form-group" style="padding-left:25px !important;">
                              <label for="customerKinAddress" style="">Next of Kin Address</label>
                               <input type="text" class="form-control" id="customerKinAddress" name="customerKinAddress" placeholder="Enter Kin Address" style="  width:100%;"
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
                         
                 </div><!-- /.col-md-4 -->
                </div><!-- /.row -->
                </div> 
                         
      </div><!-- /.box -->
      
      <div class="col-md-12">
          <a class="btn btn-primary" href="#" onclick="return showOrderProduct()" role="button">Process to Order <i class="fa fa-long-arrow-right"></i></a>
      </div>
 </div><!-- /.box -->
      
<c:if test="${customer.customerId=='' || customer.customerId== null}">
                    
                    
             <div class="row" id="step2" style="display:none">
    
               <div class="col-md-12">
              <!-- general form elements -->
               <div class="box box-primary">
               
                <div class="box-header with-border">
                  <h3 class="box-title">Product Order Form 
                      
                      
                  </h3>
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
                                            <label for="amountLeft">Amount Payable(N)</label>
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
                                            <a class="btn btn-success" name="addToCart" id="addToCart" href="#" onClick=" return addToCart(this)" ><i class="fa fa-cart-plus"></i> Add to Cart</a>
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
                                
              <div class="col-md-11" >
                <div class="row" >
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
                        
                        <th>Action</th>
                        
                      </tr>
                    </thead>
                    <tfoot style="text-align:right !important;color:green !important; font-weight:bold !important;">
                      <tr>
                        <th colspan="8" align="right" style="text-align:right !important;">Total</th>
                        
                        <th style="text-align:right !important;" align="right"><span id="cartSum">0</span></th>
                        
                        <th></th>
                        
                      </tr>
                    </tfoot>
                    <tbody>
                       
                            
                        
                   </tbody>
                   
                  </table>
                                    
                                    
                 <!-- 
                   ***************************
                   Checkout Button starts Here
                   ***************************
                 -->
                 <div class="col-md-1 pull-right">
                     <div class="form-group">
                        <a href="#" class="btn btn-success" name="checkOutToPay" id="checkOutToPay" onClick="return checkOutOfCart();"><i class="fa fa-cart-plus"></i> Proceed to payment</a>
                    </div> 

                 </div>
                 
                 <!--
                  ****************************
                    Checkout Button ends Here
                  ****************************
                 -->
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
                                        <span style="color:green;font-weight:bold;">You'd be paying N<span id='paySum'></span></span>
                                    	<div class="form-group">
                                            <label for="paymentMethod">Payment method:</label><br/>
                                            <input type="radio" name="paymentMethod" value="1" id="bankdep" onclick="showNecessaryMenu(1)"/>&nbsp;<label for="bankdep" style="display:inline !important;">Bank Deposit</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="paymentMethod" value="2" id="paywithcard" onclick="showNecessaryMenu(2)"/>&nbsp; <label for="paywithcard" style="display:inline !important;cursor:pointer !important;">Credit/Debit Card <img src="${pageContext.request.contextPath}/images/img/paywithcard.png" /></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="paymentMethod" value="3" id="paywithcash" onclick="showNecessaryMenu(3)"/>&nbsp;<label for="paywithcash" style="display:inline !important;"> Cash</label>
                                        </div>
                                    </div>
                                </div>
                                <!-- End of Payment Method Container -->        
                                            
                                <!-- Pay via Bank Deposit Div Container -->            
                                <div class='row' id='pwBankdeposit'>
                                    
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="bankName">Bank Name</label>
                                            <input type="text" class="form-control" id="bankName" name="bankName" style="width: 100%;">
                                        </div> 
                                    </div>
                                    
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
                                            <input type="text" class="form-control" id="tellerAmount" name="tellerAmount" style="width: 100%;">
                                        </div>      
                                    </div>
                                    
                                    <div class="col-md-2">
                                    	<div class="form-group" style="padding-top:25px !important;">
                                            <input type="submit"  name="Pay" class="btn btn-success" value="Pay with Bank Deposit"/>
                                        </div>      
                                    </div>
                                    
                                </div>
                                <!-- End of Pay Via Bank Deposit Div Container -->
                                
                                
                                <!-- Pay with Cash Div Container -->
                                <div class='row' id='pwCash'>
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="cashAmount">Amount</label>
                                            <input type="text" class="form-control" id="cashAmount" name="cashAmount" style="width: 100%;">
                                        </div>      
                                    </div>
                                    <div class="col-md-2">
                                    	<div class="form-group" style="padding-top:25px !important;">
                                            <input type="submit"  name="Pay" class="btn btn-success" value="Pay with cash" style="vertical-align:bottom !important;"/>
                                        </div>      
                                    </div>
                                </div>
                                <!-- End of Pay with Cash Div Container -->
                                
                                 <!-- Pay with Card Div Container -->
                                <div class='row' id='pwCard'>
                                	<div class="col-md-2">
                                            <div class="form-group">
                                                <label for="tellerNumber">Click to proceed to payment</label>
    <!--                                        <a href="${pageContext.request.contextPath}/images/img/webpay.png" target="_blank" class="btn btn-success"><i class="fa fa-angle-double-right"></i> Pay Now</a>
                                                --> <button type="submit"  name="Pay" class="btn btn-success"  style="vertical-align:bottom !important;"><i class="fa fa-angle-double-right"></i> Pay Now</button>
                                            </div> 
                                        </div>
                                </div>
                                <!-- End of Pay with Cash Div Container -->
                                             
                              </div>
                             </div>
                            </div>
                                 
                            <div class="col-md-1"></div>
                    	</fieldset>
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
</form>
