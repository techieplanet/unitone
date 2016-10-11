<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
 <div class="row">
           <div class="col-md-12">
              <!-- general form elements -->
               
                <!-- form start -->
               <div class="box box-primary">
                <div class="box-header with-border">
                  <h3 class="box-title">Product Order Form 
                      
                      
                       </h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <div style="background:#ecf0f5 !important;">
                <form role="form" name="customerRegistration" method="POST" action="Customer" enctype="multipart/form-data">
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
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="selectProdcut">Select Customer</label>
                                            
                                            <select class="form-control select2" id="selectCustomer" style="width: 100%;" onchange="" <c:if test="${customer.customerId != null && customer.customerId!=''}">disabled</c:if>>
                                                <option value="" selected="selected">-- choose --</option>
                                                
                                              <c:forEach items="${customers}" var="customer" >  
                                              
                                              <option  value="${customer.customerId}"  <c:if test="${customerId == customer.customerId}"> <jsp:text>selected</jsp:text> </c:if> >${customer.firstname} ${customer.lastname}</option>
                                              </c:forEach>
                                            </select>
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
                                     <div class="col-md-1 pull-right">
                                         <div class="form-group">
                                            <a href="#" class="btn btn-success" name="checkOutToPay" id="checkOutToPay" onClick="return checkOutOfCart();"><i class="fa fa-cart-plus"></i> Proceed to payment</a>
                                        </div> 
                                     
                                     </div>
                                </div>
                                
                                             
                            </div> /.col-md-12 
                            <div class="col-md-1"></div>
                    	</fieldset>
                  	</div> /.col-md-12 
                        
                         <div class="col-md-12" id="paymentCheckout">
                      
                    	<fieldset>
                        	<legend style="padding-left:20px !important;">Check Out</legend>
                                
                            <div class="col-md-11" >
                            	
                                <div class="row" >
                                   
                                	<div class="col-md-12">
                                            <span style="color:green;font-weight:bold;">You'd be paying N<span id='paySum'></span></span>
                                    	<div class="form-group">
                                            <label for="paymentMethod">Payment method:</label><br/>
                                           
                                            <input type="radio" name="paymentMethod" value="bankdep" id="bankdep" onclick="showNecessaryMenu('bankdep')"/>&nbsp;<label for="bankdep" style="display:inline !important;">Bank Deposit</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="paymentMethod" value="paywithcard" id="paywithcard" onclick="showNecessaryMenu('paywithcard')"/>&nbsp; <label for="paywithcard" style="display:inline !important;cursor:pointer !important;">Credit/Debit Card <img src="${pageContext.request.contextPath}/images/img/paywithcard.png" /></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="paymentMethod" value="paywithcash" id="paywithcash" onclick="showNecessaryMenu('paywithcash')"/>&nbsp;<label for="paywithcash" style="display:inline !important;"> Cash</label>
                                            		
                                                </div>
                                        </div>
                                </div>
                                         <div class='row' id='pwBankdeposit'>
                                             <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerNumber">Bank Name</label>
                                            <input type="text" class="form-control" id="bankName" name="bankName" style="width: 100%;">
                                        </div> 
                                    </div>
                                             <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerNumber">Depositor's Name</label>
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
                                            <label for="tellerName">Teller Name</label>
                                            <input type="text" class="form-control" id="tellerName" name="tellerName" style="width: 100%;">
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
                                            
                                            <div class='row' id='pwCash'>
                                	
                             
                                
                                
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerAmount">Amount</label>
                                            <input type="text" class="form-control" id="tellerAmount" name="tellerAmount" style="width: 100%;">
                                        </div> 
                                            
                                    </div>
                                             <div class="col-md-2">
                                    	<div class="form-group" style="padding-top:25px !important;">
                                           
                                            <input type="submit"  name="Pay" class="btn btn-success" value="Pay with cash" style="vertical-align:bottom !important;"/>
                                        </div> 
                                            
                                    </div>
                                             
                                             
                                    	
                                            
                                        
                                    
                                             
                                             
                                         </div>
                                            
                                <div class='row' id='pwCard'>
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerNumber">Click to proceed to payment</label>
                                        <a href="${pageContext.request.contextPath}/images/img/webpay.png" target="_blank" class="btn btn-success"><i class="fa fa-angle-double-right"></i> Pay Now</a>
                                        </div> 
                                    </div>
                             
                                
                                	
                                    </div>
                                             
                                         </div>
                                            </div>
                                        </div>
                                 
                            <div class="col-md-1"></div>
                    	</fieldset>
                  	</div> <!--/.col-md-12 -->
                 	</div> <!--/.row -->
                  </div> <!--/.box box-default -->
                  </div> <!---/.col-md-4 -->
                  


                  </div><!-- /.row -->
              
                    <input type="hidden" name="customer_id" id="id" value="${customer.customerId}">
                        <input type="hidden" name="id" id="id" value="${customer.customerId}">
                  <div class="box-footer" style="background-color:transparent;">
                      <input type="submit" class="btn btn-primary" name="customerCreate" value="Save"/>
                  </div>
                  </form>
                </div>
                
              </div><!-- /.box -->
              </div><!-- /.box -->
            </div>
          </div>   <!-- /.row -->
<!--MODAL-->
      <div class="modal fade" id="deleteModalCart" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
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