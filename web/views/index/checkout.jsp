<%@include file="header.jsp" %> 

    <!-- Main content -->
<section class="container" style="margin-top:120px;background-color:#fff">                              
<div class="row">

  <div class="col-md-12">
  <!-- general form elements -->

    <!-- form start -->
    <form role="form" name="new_order" method="POST" action="CustomerRegistration" enctype="multipart/form-data" onsubmit="return submitForm()" >

    <input type="hidden" name="agent_id" id="agent_id" value="" />

    <div style="">
    <!--<form role="form" name="customerRegistration" method="POST" action="Order" enctype="multipart/form-data">-->
      
    
     <div class="row" id="step1">
      <div class="col-md-12">
                <!-- form start -->
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
                        
                        <div class="col-md-4" style="padding-top:50px">
                       
                          <fieldset>
                              
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
                         
                    
                    </div>
                    <div class="row">
                      <div class="col-md-4">
                                 <fieldset>
                                    <div class="col-md-6" style="padding-top:0px">

                                        <div class="form-group">
                                          <div class="btn-group btn-group-xs">
                                              <label for="customerPhoto">Customer Photo</label>
                                              <div class="btn btn-primary">
                                                <input type="file" name="customerPhoto" accept="image/gif, image/jpeg, image/png" id="customerPhoto" >
                                                <input type="hidden" name="customerPhotoHidden" />
                                              </div>
                                          </div>

                                        </div>

                                    </div>
                                 </fieldset>
                             
                            </div>

                    </div>
                    </div>
                    
                   
                    
                    
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
                        <div class="row" >
                            <div class="col-md-6">
                                    
                                        <div class="form-group" style="padding-left:25px !important;">
                                          <label for="customerKinAddress" style="">Next of Kin Address</label>
                                           <input type="text" class="form-control" id="customerKinAddress" name="customerKinAddress" placeholder="Enter Kin Address" style="  width:100%;"
                                                  value=<c:if test="${fn:length(errors) > 0 }">"${param.customerKinAddress}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${customer.kinAddress}"</c:if> "/>
                                                  <br/>
                                        </div>
                                    
                            </div>
                         </div>    
                                                  
                            <div  class="row">
                                    <div class="col-md-4" style="padding-top:0px;padding-left:25px !important;" >
                                     
                                            <div class="form-group" >
                                                <div class="btn-group btn-group-xs">  
                                                  <label for="customerKinPhoto" style="">Next of Kin Picture</label>
                                                  <div class="btn btn-primary">
                                                   <input type="file" id="customerKinPhoto" name="customerKinPhoto" accept="image/gif, image/jpeg, image/png" />
                                                   <input type="hidden" name="customerKinPhotoHidden" />
                                                  </div>
                                                </div>
                                            </div>

                                    </div>
                            </div>
                                                  
                        
                        </fieldset>
                    </div>
                    </div>
                                                  
                    <div class="col-md-12">
                        <a class="btn btn-primary" href="#" onclick="return ecommerce.validateCustomerRegForm(event)" role="button">Proceed to Order <i class="fa fa-long-arrow-right"></i></a>
                    </div>
        </div>
    
      
      <!--
       Step 2 starts here {Product Order}
      -->
      <div class="row" id="step2" style="display:none">
      <div class="col-md-12">

      <div id="cart_list" style="padding-top: 20px">
          
          <div class="panel panel-primary" id="cart_panel">
              <div class="panel-heading">
                <h3 class="panel-title">Selected Unit List</h3>
              </div>
              <div class="panel-body table-responsive" >
                  <table class="table table-hover table-striped" id="cart_table">
                      
                      <thead>
                          <tr>
                            <th>Product</th>
                            <th>Product Unit</th>
                            <th>Quantity</th>
                            <th>Amount</th>
                            <th>Initial Amount</th>
                            <th>Balance to complete</th>
                            <th>Duration</th>
                            <th>Monthly Pay</th>

                          </tr>
                      </thead>
                      
                      <tbody>
                      
                      <c:set value="0" var="total" />
                      <c:forEach items="${unit_cart}" var="unit" varStatus="pointer">
                          <c:set value="${total + unit.getLeastInitDep()}" var="total" />
                          <tr id="row${pointer.count}">
                              
                              <td>${unit.getProject().getName()}<input type="hidden" class="project" value="${unit.getProject().getName()}" /></td>
                              <td>${unit.getTitle()}<input type="hidden" class="unit_id" value="${unit.getId()}" /><input type="hidden" class="unit_name" value="${unit.getTitle()}" /></td>
                              <td><input type="number" style="width:60px" class="qty" value="1" min="1" max="${unit.getQuantity()}" onchange="ecommerce.updateAll('#row${pointer.count}')" /></td>
                              <td>
                                  <span class="amount_text"><fmt:formatNumber value='${unit.getAmountPayable()}' type="currency" currencySymbol="N" /></span>
                                  <input type="hidden" class="amount" value="<fmt:formatNumber value='${unit.getAmountPayable()}' type="number" maxFractionDigits="2" groupingUsed="" />" />
                                  <input type="hidden" class="cpu" value="${unit.getAmountPayable()}" />
                              </td>
                              <td>
                                  <input type="text" class="initial_deposit" value="<fmt:formatNumber value='${unit.getLeastInitDep()}' type="number" maxFractionDigits="2" groupingUsed=""  />" onchange="ecommerce.updateBalance('#row${pointer.count}')" />
                                  <br /><span class="initial_deposit_text"><fmt:formatNumber value='${unit.getLeastInitDep()}' type="currency" currencySymbol="N"  /></span>
                                  <input type="hidden" class="lid" value="${unit.getLeastInitDep()}" />
                              </td>
                              <td><span class="balance_text"><fmt:formatNumber value='${(unit.getCpu() * 1) - unit.getLeastInitDep()}' type="currency" currencySymbol="N"  /></span></td>
                              <td><input type="number" style="width:60px" class="duration" value="1" min="1" max="${unit.getMaxPaymentDuration()}" onchange="ecommerce.updateMonthlyPay('#row${pointer.count}')"  /></td>
                              <td><span class="monthly_pay_text"><fmt:formatNumber value='${(unit.getCpu() * 1) - unit.getLeastInitDep()}' type="currency" currencySymbol="N" /></span></td>
                              
                          </tr>
                      </c:forEach>
                          
                      </tbody>
                      <tfoot>
                          <tr>
                              <td colspan="4" style="text-align: right"><b>Total Amount to Pay : </b></td>
                              <td id="total" colspan="4"><fmt:formatNumber value="${total}" currencySymbol="N" type="currency" /></td>
                          </tr>
                      </tfoot>
                  </table>
              </div>
              <div class="panel-footer">
                  <a class="btn btn-success"  onclick="ecommerce.proceedToPayment(event)">Proceed to Payment</a> &nbsp;&nbsp;
                  <a href="${pageContext.request.contextPath}/Project?action=listprojects"  class="btn btn-primary">Continue shopping</a>
              </div>
         </div>
          
      </div>
      
  <div class="box box-default" id="paymentCheckout" style="display:none">
      <input type="hidden" name="dataHidden" id="dataHidden" />
      <div class="row" style="padding-top:10px;">
      
                        
        <div class="col-md-12" >
                      
                    	<fieldset>
                        <legend style="padding-left:20px !important;">Payment</legend>
                                
                            <div class="col-md-11" >
                            	
                                <!-- Start of Payment Method Container -->
                                <div class="row" > 
                                    <div class="col-md-12">
                                        <span style="color:green;font-weight:bold;font-size: 21px">You'd be paying <span id='paySum'></span></span>
                                    	<div class="form-group">
                                            <label for="paymentMethod">Payment method:</label><br/>
                                            <input type="radio" name="paymentMethod" value="1" id="bankdep" onclick="showNecessaryMenu(1)"/>&nbsp;<label for="bankdep" style="display:inline !important;">Bank Deposit</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                            
                                            <input type="radio" name="paymentMethod" value="2" id="paywithcard" onclick="showNecessaryMenu(2)"/>&nbsp; <label for="paywithcard" style="display:inline !important;cursor:pointer !important;">Credit/Debit Card <img src="${pageContext.request.contextPath}/images/img/paywithcard.png" /></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            
                                            <input type="radio" name="paymentMethod" value="3" id="paywithcash" onclick="showNecessaryMenu(3)"/>&nbsp;<label for="paywithcash" style="display:inline !important;"> Cash</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="paymentMethod" value="4" id="bankTransfer" onclick="showNecessaryMenu(4)"/>&nbsp; <label for="bankTransfer" style="display:inline !important;cursor:pointer !important;">Bank Transfer </label>
                                        </div>
                                    </div>
                                            
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="companyAccount">Company Account</label>
                                            <select name="companyAccount" id="companyAccount" class="form-control select2" style="width: 100%;">
                                                <option value="">--Select Account--</option>
                                                <c:forEach items="${companyAccount}" var="CA">
                                                    <option value="${CA.getId()}">${CA.getAccountName()}</option>
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
                                
                                
                                
                                
                                 <!-- Pay with Card Div Container -->
                                <div class='row' id='pwCard'>
                                	<div class="col-md-6">
                                            <div class="form-group">
                                                <label for="tellerNumber"></label>
                                                <!--<a href="${pageContext.request.contextPath}/images/img/webpay.png" target="_blank" class="btn btn-success"><i class="fa fa-angle-double-right"></i> Pay Now</a>
                                                --> <button type="submit"  name="Pay" class="btn btn-success"  style="vertical-align:bottom !important;"><i class="fa fa-angle-double-right"></i> Pay Now</button>
                                                <input type="hidden" class="form-control amount-box"  name="cardAmount" >
                                            </div> 
                                        </div>
                                </div>
                                <!-- End of Pay with Card Div Container -->
                              
                                
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
            </div> <!-- Cart Process Ends here -->
              
      </div>
                          
      </div><!-- /.row -->
              
      <input type="hidden" name="customer_id" id="id" value="${customerId}">
      <input type="hidden" name="id" id="id" value="${customerId}">
      <input type="hidden" name="cartDataJson" id="cartDataJson" />
      
      </form>

  </div>
      
  
    <div class="col-md-12">
                
          <section class="invoice" style="min-height: 600px; display: none">
                <table class="table">
          <!-- title row -->
                  <tr>
                    <td>
                        <i class="fa fa-globe"></i> NEOFORCE, SFA.
                    </td>
                    <td style="text-align:right">
                        <small class="invoice_date"></small>
                    </td><!-- /.col -->
                  </tr>
          <!-- info row -->
                  <tr>
                      <td>
                      From
                      <address>
                        <strong>Techieplanet, Ltd.</strong><br>
                        795 Folsom Ave, Suite 600<br>
                        Ikeja, Lagos <br>
                        Phone: (+234) 816-4334-657<br>
                        Email: info@techieplanetltd.com
                      </address>
                      </td>
                    <td>
                      To
                      <address>
                          <strong id="invoice_customer_fullname"> </strong><br>
                          <span id="invoice_customer_street"></span><br>
                          <span id="invoice_customer_state"></span><br>
                          <span id="invoice_customer_phone"></span><br>
                          <span id="invoice_customer_email"></span>
                      </address>
                    </td><!-- /.col -->
                    
                  </tr>
              </table>
            
            
            
                  <!-- Table row -->
                  <div class="row">
                    <div class="col-xs-12 table-responsive">
                      <table class="table table-striped" id="invoice_table">
                        <thead>
                          <tr>
                            <th>S/N</th>
                            <th>Project</th>
                            <th>Product</th>
                            <th style="text-align: center">Qty</th>
                            <th style="text-align: center">Subtotal</th>
                          </tr>
                        </thead>
                        <tbody>

                        </tbody>
                        <tfoot style="border:solid 1px #eee">
                            <tr>
                                <td colspan="4" style="text-align: right">Total</td>
                                <td class="invoice_total" style="text-align: right"></td>
                            </tr>
                            <tr>
                                <td colspan="4" style="text-align: right">VAT</td>
                                <td style="text-align: right">N0.00</td>
                            </tr>
                            <tr>
                                <td colspan="4" style="text-align: right">Gateway charge</td>
                                <td style="text-align: right">N0.00</td>
                            </tr>
                            <tr>
                                <td colspan="4" style="text-align: right">Grand Total</td>
                                <td style="text-align: right" class="invoice_grand_total"></td>
                            </tr>
                        </tfoot>
                      </table>
                    </div><!-- /.col -->


                  </div><!-- /.row -->

                  <div class="row">
                      
                      <a href="#" onclick="ecommerce.pay(event)" class="btn btn-success">Proceed to Pay</a>&nbsp;&nbsp;
                      <a href="${pageContext.request.contextPath}/Project?action=listprojects"  class="btn btn-primary">Continue shopping</a>
                  </div>
        </section>
              
     </div>
</div>

</section>
<!--MODAL-->
      <div class="modal fade" id="customerErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
         <div class="vertical-alignment-helper">
          <div class="modal-dialog vertical-align-center">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">RPL</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
              <button id="ok" type="button" data-dismiss="modal" class="btn btn-primary">OK</button>
            </div>
          </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery-2.1.4.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/accounting.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/functions.js"></script>     
<script>
    
    $(".amount-box").attr("readonly",true);
    
    var ecommerce = {
       
       cartData : {},
       
       checkOut : function(){
           
       },
       
       /***
        * 
        * @param {String} rowid
        * @returns {undefined}
        * This method is called when the qty changes,
        * It updates Project unit Amount, Balance to complete, Monthly Payment field, and initial deposit
        * whenever any of these fields are changed : unit quantity, initial deposit, duration in case of monthly pay
        */
       updateAll : function(rowid){
           
           console.log("updateAll() Called");
           
           //Prepare constants for calculation
           var qty = $(rowid + " .qty").val();
           var duration = $(rowid + " .duration").val();
           
           var cpu = $(rowid + " .cpu").val();
           var lid = $(rowid + " .lid").val();
           
           console.log("Quantity : " + qty + ", Duration : " + duration + ", Cpu : " + cpu + ", Lid : " + lid);
           
           //Begin calculation
           var amount = qty * cpu;
           var initial_deposit = qty * lid;
           var balance = amount - initial_deposit;
           var monthly_payment = balance / duration;
           
           $(rowid + " .amount").val(amount);
           $(rowid + " .amount_text").html(accounting.formatMoney(amount,"N",2,",","."));
           
           $(rowid + " .balance_text").html(accounting.formatMoney(balance,"N",2,",","."));
           
           $(rowid + " .monthly_pay_text").html(accounting.formatMoney(monthly_payment,"N",2,",","."));
           
           $(rowid + " .initial_deposit").val(initial_deposit);
           $(rowid + " .initial_deposit_text").html(accounting.formatMoney(initial_deposit,"N",2,",","."));
           
           ecommerce.updateTotalAmount();
           
       },
       
       /**
        * 
        * @param {String} rowid
        * @returns {undefined}
        * This is method is called when just the initial deposit changes,
        * it updates the balance and monthly pay
        */
       updateBalance : function(rowid){
           
           //Prepare constants for calculation
           var amount = $(rowid + " .amount").val();
           var duration = $(rowid + " .duration").val();
           var initial_deposit = $(rowid + " .initial_deposit").val();
           
           //Begin calculation
           var balance = parseFloat(amount - initial_deposit);
           var monthly_payment = balance / duration;
           
           if(monthly_payment < 0 ){
               monthly_payment = 0;
               balance = 0;
           }
           
           $(rowid + " .balance_text").html(accounting.formatMoney(balance,"N",2,",","."));
           
           $(rowid + " .monthly_pay_text").html(accounting.formatMoney(monthly_payment,"N",2,",","."));
           
           $(rowid + " .initial_deposit_text").html(accounting.formatMoney(initial_deposit,"N",2,",","."));
           ecommerce.updateTotalAmount();
           
       },
       
       updateMonthlyPay : function(rowid){
           
           //Prepare constants for calculation
           var amount = $(rowid + " .amount").val();
           var duration = $(rowid + " .duration").val();
           var initial_deposit = $(rowid + " .initial_deposit").val();
           
           //Begin calculation
           var balance = amount - initial_deposit;
           var monthly_payment = balance / duration;
           
           if(monthly_payment < 0 ){
               monthly_payment = 0;
           }
           
           $(rowid + " .monthly_pay_text").html(accounting.formatMoney(monthly_payment,"N",2,",","."));
       },
       
       updateTotalAmount : function(){
           var total = 0.0;
           $("table .initial_deposit").each(function(index,element){
               
               total += parseFloat($(this).val());
           });
           
           $("#total").html(accounting.formatMoney(total,"N",2,",","."));
       },
       
       proceedToPayment : function(evt){
           
           evt.preventDefault();
           
           var  counter = 1;
           var total = 0.00;
           
           $("#cart_table tbody tr").each(function(){
               
               var project = $(this).find('.project').val();
               var unit_name = $(this).find('.unit_name').val();
               var qty = $(this).find('.qty').val();
               var initial_deposit = $(this).find('.initial_deposit').val();
               
               total = total + parseFloat(initial_deposit);
               
               var tr = "<tr>";
               tr += "<td>" + counter + "</td>";
               tr += "<td>" + project + "</td>";
               tr += "<td>" + unit_name + "</td>";
               tr += "<td style='text-align: center'>" + qty + "</td>";
               tr += "<td style='text-align: right'>" + accounting.formatMoney(initial_deposit,"N",2,",",".")+ "</td>";
               tr += "</tr>";
               
               $("#invoice_table tbody").append(tr);
               
               counter++;
           });
           
           $("#invoice_table tfoot .invoice_total, #invoice_table tfoot .invoice_grand_total").text(accounting.formatMoney(total,"N",2,",","."));
           
           var date = new Date();
           var day_of_month = date.getDate();
           var month = date.getMonth();
           var year = date.getFullYear();
           var dateString  = day_of_month + "/" + month + "/" + year;
           
           $(".invoice_date").text(dateString);
           
           $("#cart_panel:visible").toggle();
           $(".invoice:hidden").toggle();
           return false;
       },
       
       pay : function(evt){
           
           evt.preventDefault();
           
           var cartDataObject = {};
           var cartArray = [];
           
           var total = 0.00;
           
           $("#cart_table tbody tr").each(function(){
               
               var unit_id = $(this).find('.unit_id').val();
               var qty = $(this).find('.qty').val();
               var initial_deposit = $(this).find('.initial_deposit').val();
               
               total = total + parseFloat(initial_deposit);
               
               cartArray.push({productUnitId : unit_id, productQuantity : qty, productMinimumInitialAmount : initial_deposit});
               
           });
           
           cartDataObject.sales = cartArray;
           
           $("#cartDataJson").val(JSON.stringify(cartDataObject));
           
           //Set the amount textbox to the total amount
           $(".amount-box").each(function(){
               $(this).val(total);
           });
           
           $("#paySum").text(accounting.formatMoney(total,"N",2,",","."));
           
           
           $(".invoice:visible").toggle();
           $("#paymentCheckout:hidden").toggle();
           
           return false;
       },
       
       submitForm : function(){
           
           var submitOk = true;
           
           var payment_mode = $('input:radio[name=paymentMethod]:checked').val();
           
           var companyAccount = $("#companyAccount").val();
           
           if(companyAccount == ""){
               
               alert("Please select company account");
               submitOk = false;
           } 
           else if(payment_mode == 1){
               
               var depositorsName = $("#depositorsName").val();
               var tellerNumber = $("#tellerNumber").val();
                       
               if( $.trim(depositorsName) == ""){
                   alert("Please Enter depositors name");
                   submitOk = false;
               }
               else if($.trim(tellerNumber) == ""){
                   alert("Please enter teller number");
                   submitOk = false;
               }
           }
           else if(payment_mode == 4){
               
               var transfer_bankName = $("#transfer_bankName").val();
               var transfer_accountNo = $("#transfer_accountNo").val();
               var transfer_accountName = $("#transfer_accountName").val();
               
               if($.trim(transfer_bankName) == ""){
                   alert("Please enter Bank Name");
                   submitOk = false;
               }
               else if($.trim(transfer_accountNo) == ""){
                   alert("Please enter account number");
                   submitOk = false;
               }
               else if($.trim(transfer_accountName) == ""){
                   alert("Please enter account Name");
                   submitOk = false;
               }
               
           }
           
           return submitOk;
       },
       
       validateCustomerRegForm : function(evt){
                
                evt.preventDefault();
                
                var errors = [];

                if($("#customerFirstname").val().trim() == '')
                {
                    errors.push("Please enter first name");
                }
                if($("#customerMiddlename").val().trim() == '')
                {
                    errors.push("Please enter middle name");
                }
                if($("#customerLastname").val().trim() == '')
                {
                    errors.push("Please enter last name");
                }
                if($("#customerEmail").val().trim() == '')
                {
                    errors.push("Please enter email");
                }
                if($("#customerPassword").val().trim() == '')
                {
                    errors.push("Please enter password");
                }
                else
                {
                    if($("#customerPassword").val().trim() != $("#customerConfirmPassword").val().trim())
                    {
                        errors.push("Password mismatch");
                    }
                }
                if($("#customerStreet").val().trim() == '')
                {
                    errors.push("Please enter street");
                }
                if($("#customerCity").val().trim() == '')
                {
                    errors.push("Please enter city");
                }
                if($("#customerState").val().trim() == '')
                {
                    errors.push("Please select state");
                }
                if($("#customerPhone").val().trim() == '')
                {
                    errors.push("Please enter Phone Number");
                }
                if($("#customerKinNames").val().trim() == '')
                {
                    errors.push("Please enter kin name");
                }
                if($("#customerKinPhone").val().trim() == '')
                {
                    errors.push("Please enter kin Phone Number");
                }
                if($("#customerKinAddress").val().trim() == '')
                {
                    errors.push("Please enter kin Address");
                }

                $("#customerErrorModal .modal-body").html("");

                var url = "${pageContext.request.contextPath}";


                $.ajax({
                    url : url + "/Customer?action=email_validation",
                    method : 'GET',
                    data : {email : $("#customerEmail").val(), type : 'xmlhttp'},
                    success : function(data){

                        

                        console.log(data);

                        var res = JSON.parse(data);
                        if(res.code === "-1" || res.code === -1){
                            errors.push("Email already exist");
                        }

                        if(errors.length > 0)
                        {
                            var errorText = '';

                            for(var key in errors){
                                var errorText = '' + errors[key] + '<br />';
                                $("#customerErrorModal .modal-body").append(errorText);
                            }
                            $("#customerErrorModal").modal();

                            
                        }
                        else{
                            
                            var lname = $("#customerLastname").val();
                            var fname = $("#customerFirstname").val();
                            var mname = $("#customerMiddlename").val();
                            var street = $("#customerStreet").val();
                            var state = $("#customerState").val();
                            var phone = $("#customerPhone").val();
                            var email = $("#customerEmail").val();
                            
                            $("#invoice_customer_fullname").text(lname + " " + fname + " " + mname);
                            $("#invoice_customer_street").text(street);
                            $("#invoice_customer_state").text(state);
                            $("#invoice_customer_phone").text(phone);
                            $("#invoice_customer_email").text(email);
                            
                            $("#step1:visible").toggle();
                            $("#step2:hidden").toggle();
                        }

                    },
                    error : function(xhr,status_code,status_text){

                        console.log(status_code + " : " + status_text);
                    }
                });
            }
       
    }
    
</script>

    </body>
</html>