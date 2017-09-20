<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script>
    
    var customersLoyaltyList = [];
    var customerPoints = 0;
    var isLoyaltyEnabled = "${plugins.containsKey('loyalty') ? 1 : 0}";
    var pointToCurrency = "${pointToCurrency}";
    
    var app = angular.module("app",["ngwidgets"]);
       
       app.controller("customerDropDownController",function($scope){
           
           $scope.customerId = "";
           
           var dataArray = [];
           
           dataArray.push({name:"--Select Customer--",id : "",img : ""})
           
           <c:forEach items="${customers}" var="customer">
           
                var fullName = "${customer.getFullName()}";
                var imgPath = "${customerImageAccessDir}" + "/" + "${customer.getPhotoPath()}";
                var customer_id = "${customer.getCustomerId()}";
                
                dataArray.push({name:fullName,id : customer_id,img : imgPath});
                
                var point = "${customer.getRewardPoints()}";
                customersLoyaltyList.push({"id":customer_id,"points":point});
                
           </c:forEach>
           
           $scope.selectItem = function(event){
               //console.log(event.args.item);
               $scope.customerId = event.args.item.value;
               $scope.updateCustomerPoint(event.args.item.value)
           };
           
           $scope.updateCustomerPoint = function(id){
               
               if(id == ""){
                   return;
               }
               
               for(var k in customersLoyaltyList){
                   if(customersLoyaltyList[k].id == id){
                       console.log("c.id : " + customersLoyaltyList[k].id + ", id : " + id);
                       customerPoints = customersLoyaltyList[k].points;
                       break;
                   } 
               }
               
               console.log("Customer Point : " + customerPoints);
           };
           
           $scope.settings = {
               
               filterable: true, selectedIndex: 0, source: dataArray, itemHeight: 70, height: 30, width : "100%",
               displayMember: 'name',
               valueMember: 'id',
               searchMode : 'containsignorecase',
                renderer: function (index, label, value) {
                    
                    if(index == 0){
                        return "<p style='padding:10px 20px 10px 20px'>" + label + "</p>";
                    }
                    
                    var img = '<img height="50" width="50" src="' + dataArray[index].img + '"/>';
                    var table = '<table style="min-width: 130px;"><tr><td style="width: 80px;">' + img + '</td><td>' + label + '</td></tr></table>';
                    return table;
                },
                selectionRenderer: function (element, index, label, value) {
                    var text = label.replace(/<br>/g, " ");
                    return "<span style='left: 4px; top: 6px; position: relative;'>" + text + "</span>";
                }
               
           };
           
       });
       
</script>


<c:if test="${userType != null && userType == 1 }">
  
                                
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
                         <img src="" class="agent_img img img-responsive img-thumbnail" width="100px" height="100px" alt="No image" />
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
     
     
 <!--

 Product Order Cart Form / Customer Registration starts
 
 -->
 
     
 <div class="row" ng-app="app">
    
           <div class="col-md-12" ng-controller="customerDropDownController">
              <!-- general form elements -->
               
                <!-- form start -->
               <div class="box box-primary">
  <form role="form" name="customerRegistration" method="POST" action="${pageContext.request.contextPath}/Order?action=new_order" enctype="multipart/form-data" onsubmit="return submitForm()">
                
                <input type="hidden" name="agent_id" id="agent_id" value="" />
                   
                <div class="box-header with-border">
                  <h3 class="box-title">Product Order Form 
                      
                      
                  </h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <div style="background:#ecf0f5 !important;">
                <!--<form role="form" name="customerRegistration" method="POST" action="Order" enctype="multipart/form-data">-->
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
                                    <div class="col-md-4">
                                    	<div class="form-group">
                                            <label for="selectProdcut">Select Customer</label>
                                            
                                            <input type="hidden" value="{{customerId}}" name="customer_id" id="customer_id"/>
                                            
                                            <ngx-drop-down-list ngx-on-select="selectItem(event)" ngx-settings="settings">
                                            </ngx-drop-down-list>
                                            
                                            <!--
                                            <select class="form-control select2" id="selectCustomer" name="customer_id" style="width: 100%;" onchange="" <c:if test="${customer.customerId != null && customer.customerId!=''}">disabled</c:if>>
                                              
                                              <c:if test="${userType != 3}">  
                                                <option value="" selected="selected">-- choose --</option>
                                              </c:if>  
                                              <c:forEach items="${customers}" var="customer" >  
                                              
                                              <option  value="${customer.customerId}"  <c:if test="${customerId == customer.customerId}"> <jsp:text>selected</jsp:text> </c:if> >${customer.firstname} ${customer.lastname}</option>
                                              </c:forEach>
                                            </select>
                                            -->
                                        </div>
<!--                                              /.form-group select product -->
                                    </div>
                                              
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
                                
                                <div class="col-md-2">
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
                                            <input type="text" class="form-control" id="productAmount" name="productAmount" style="width: 100%;" readonly>
                                             <span id="amountPerUnit">
                                                Amount per Unit: <span id="amountUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="amountTotalUnit"></span>
                                            </span>
                                        </div> 
<!--                                            /.form-group amount -->
                                    </div>
                                              
                               
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMinimumInitialAmount">Initial Amount(N)</label>
                                            <input type="text" class="form-control" id="productMinimumInitialAmount" name="productMinimumInitialAmount" style="width: 100%;"  onkeyup="calculateAmountToPay()" >
                                            <span id="amountPerUnit" >
                                                Min initial amt /unit: <span id="initialAmountPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="minInitialAmountSpan"></span><br/>
                                            </span>
                                            <span id="productMinimumInitialAmountFormat"></span>
                                        </div> 
<!--                                            /.form-group initial monthly amount -->
                                        </div>
                                      
                                      <c:if test="${plugins.containsKey('loyalty')}">
                                          <div class="col-md-2" >
                                              <div class="form-group">
                                                  <label for="productLoyaltyPoint">Loyalty Point</label>
                                                  <span>Amount of loyalty point to use for Item</span>
                                                  <input type="text" size="4" class="form-control" name="productLoyaltyPoint" id="productLoyaltyPoint" onkeyup="calculateAmountToPay()">
                                              </div>
                                          </div>         
                                      </c:if>        
                                      <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="amountLeft">Balance Payable(N)</label>
                                            <input type="text" class="form-control" id="amountLeft" name="amountLeft" style="width: 100%;" readonly >
                                            <span id="amountPerUnit"></span>
                                            <span id="amountLeftFormat"></span>
                                        </div> 
<!--                                                  /.form-group initial monthly amount -->
                                    </div>
                               <input type="hidden" id="editMode" value="" />
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMaximumDuration">Payment Duration</label>
                                            <div class="row">
                                            	<div class="col-md-12">
                                            		<select class="form-control select2"  id="productMaximumDuration" style="width: 100%;" onchange="monthlyPayCalculator('exsiting')">
                                                      <option value="#" selected="selected">-- choose --</option>
                                                    
                                                    </select>
                                                </div>
                                            </div>
                                             <span id="amountPerUnit">
                                                Max payment duration /unit: <span id="payDurationPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="payDurationPerQuantity"></span>
                                            </span>
                                        </div> <!-- /.form-group Duration -->
                                    </div>
                               
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMinimumMonthlyPayment">Monthly Payment(N)</label>
                                            <input type="text" class="form-control" id="productMinimumMonthlyPayment" name="productMinimumMonthlyPayment" style="width: 100%;" onKeyup="calculateDurationFromMonthlyPay()" readonly>
                                             <span id="amountPerUnit">
                                                Min monthly pay / unit: <span id="monthlyPayPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="monthlyPayPerQuantity"></span>
                                            </span>
                                            <span id="finalAmount" style="display:block"></span>
                                        </div> <!--/.form-group amount -->
                                    </div>
                               
                                    <c:if test="${userType != null && userType == 1 }">     
                                          <div class="col-md-2">
                                              <div class="form-group">
                                                  <label>
                                                      Commission(%)
                                                  </label>
                                                  <input type="text" class="form-control" value="0" name="commp" id="commp" readonly/>
                                              <span>This is the commission payable to an agent</span>
                                              </div>
                                          </div>
                                    </c:if>  
                                                
                               
                                      <div class="col-md-2">
                                              <div class="form-group">
                                                  <label>
                                                      Notification Day
                                                  </label>
                                                  <input type="number" class="form-control" min="1" max="31" name="day_of_notification" value="1" id="day_of_notification"/>
                                                <span >Select the day of the month to receive monthly notification</span>
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
                        
                        <th style="text-align:right !important;" align="right"><input type="hidden" id="CartActualSum" value="0" /><span id="cartSum">0</span></th>
                        
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
                        <a href="#" class="btn btn-success" name="checkOutToPay" id="checkOutToPay" onClick="return checkOutOfCart();"><i class="fa fa-cart-plus"></i> Checkout</a>
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
                                        <span style="color:green;font-weight:bold;">You'd be paying <span id='paySum'></span></span>
                                    	<div class="form-group">
                                            <label for="paymentMethod">Payment method:</label><br/>
                                            <input type="radio" name="paymentMethod" value="1" id="bankdep" onclick="showNecessaryMenu(1)"/>&nbsp;<label for="bankdep" style="display:inline !important;">Bank Deposit</label>&nbsp;&nbsp;&nbsp;&nbsp;
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
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="bankName">Depositor's Bank Name</label>
                                            <input type="text" class="form-control" id="transfer_bankName" name="transfer_bankName" style="width: 100%;">
                                        </div> 
                                    </div>
                                    
                                    <div class="col-md-2">
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
                                    
                                    <div class="col-md-2">
                                    	<div class="form-group" style="padding-top:25px !important;">
                                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge"/>
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
              
      
      <input type="hidden" name="id" id="id" value="${customerId}">
      <input type="hidden" name="cartDataJson" id="cartDataJson" />
      
      </form>
    </div>

  </div><!-- /.box -->
  </div><!-- /.box -->

<!--MODAL-->
      <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
         <div class="vertical-alignment-helper">
          <div class="modal-dialog vertical-align-center">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE</h4>
            </div>
            <div class="modal-body">
              <p>Are you sure you want to remove item from cart?</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="" class="btn btn-primary">OK</button>
            </div>
          </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
      
      
      <!--MODAL-->
      <div class="modal fade" id="rewardPointError" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE</h4>
            </div>
            <div class="modal-body">
              <p>You have exceeded your reward point</p>
            </div>
            <div class="modal-footer">
              <button id="ok" type="button" data-dismiss="modal" class="btn btn-primary pull-right">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
      
   
      
<script>
    
    $(document).ready(function(){
       
       $("#agentSpinnerContainer:visible").toggle();
       $("#agentDetailContainer:visible").toggle();
       
       
       $("#checkOutToPay").attr("disabled",true);
    });
    
</script>