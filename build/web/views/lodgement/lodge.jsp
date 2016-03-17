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
            Orders
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
                      Customer Lodgement
                      <span class="pull-right">
                          <a class="btn btn-primary" href="Customer?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp; Place new Order</a>
                      </span>
                  </h3>
                </div><!-- /.box-header -->
                <form name="lodgementForm" method="post" action="" onSubmit="return checkFormRequired()">
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
                        	<legend style="padding-left:20px !important;">#invoice ID  - ${invoiceId}</legend>
                               <input type="hidden" name="invoice_id" value="${invoiceId}" />
                            <div class="col-md-12">
                            	
                                              <div class="row">
                                
                                	<div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="productAmount">Amount to Pay</label>
                                           <input type='hidden' name="defAmount"  id="defAmount" value="${paymentPlan.monthlyPay}" required/>
                                           <span>
                                               <br/>Preset Monthly Pay: N${paymentPlan.monthlyPay}
                                            </span>
                                            <input type="text" class="form-control" id="productAmountToPay" name="productAmountToPay" onFocusOut="lodgePaymentCheck()" style="width: 100%;" value=<c:if test="${fn:length(errors) > 0 }">"${param.productAmountToPay}"</c:if> <c:if test="${fn:length(errors) <= 0 }">"${paymentPlan.monthlyPay}"</c:if>  />
                                        </div> 
<!--                                            /.form-group amount -->
                                    </div>
                                              
                                  
                                              <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="amountLeft">Amount Payable(N)</label>
                                            <br/>
                                            <input type="text" class="form-control" id="amountLeft" name="amountLeft" style="width: 100%;"  value=<c:if test="${fn:length(errors) > 0 }">"${param.amountLeft}"</c:if> <c:if test="${fn:length(errors) <= 0 }">"${paymentPlan.amountLeft}"</c:if>readonly />
                                        </div> 
<!--                                                  /.form-group initial monthly amount -->
                                    </div>
                             
                                	<div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="productMaximumDuration">Months Paid</label>
                                            <span>&nbsp;&nbsp;&nbsp;</span>
                                           <br/>
                                            <div class="row">
                                            	<div class="col-md-12">
                                            		<select class="form-control select2"  name="monthsPaid" id="monthspaid" style="width: 100%;" onchange="" disabled>
                                                      <option value="#" selected="selected">-- choose --</option>
                                                     
                                                      <c:if test="${fn:length(errors) <= 0 }">
                                                        <c:if test="${paymentPlan.monthsPaid != ''}"><option value="${paymentPlan.monthsPaid}" selected>${paymentPlan.monthsPaid}  
                                                                <c:if test="${paymentPlan.monthsPaid > 1}">months</c:if>
                                                                <c:if test="${paymentPlan.monthsPaid <=1}">month</c:if>
                                                            
                                                                </option></c:if>
                                                      </c:if>
                                                                
                                                        <c:if test="${fn:length(errors) >0 }">
                                                        <c:if test="${param.monthsPaid != ''}"><option value="${param.monthsPaid}" selected>${param.monthsPaid}  
                                                                <c:if test="${param.monthsPaid > 1}">months</c:if>
                                                                <c:if test="${param.monthsPaid <=1}">month</c:if>
                                                            
                                                                </option></c:if>
                                                      </c:if>
                                                    </select>
                                                </div>
                                            </div>
                                        </div> <!-- /.form-group Duration -->
                                    </div>
                               
                                                  <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="durationLeft">Duration Left</label>
                                            <span>&nbsp;&nbsp;&nbsp;</span>
                                          <br/>
                                            <div class="row">
                                            	<div class="col-md-12">
                                            		<select class="form-control select2"  name="durationLeft" id="durationLeft" style="width: 100%;" onchange="" disabled>
                                                      <option value="#" selected="selected">-- choose --</option>
                                                      
                                                      
                                                       <c:if test="${fn:length(errors) <= 0 }">
                                                         <c:if test="${paymentPlan.durationLeft != ''}"><option value="${paymentPlan.durationLeft}" selected>${paymentPlan.durationLeft}  
                                                                <c:if test="${paymentPlan.durationLeft > 1}">months</c:if>
                                                                <c:if test="${paymentPlan.durationLeft <=1}">month</c:if>
                                                            
                                                                </option></c:if>
                                                       </c:if>
                                                                
                                                        <c:if test="${fn:length(errors) > 0 }">
                                                         <c:if test="${param.durationLeft != ''}"><option value="${param.durationLeft}" selected>${param.durationLeft}  
                                                                <c:if test="${param.durationLeft > 1}">months</c:if>
                                                                <c:if test="${param.durationLeft <=1}">month</c:if>
                                                            
                                                                </option></c:if>
                                                       </c:if>
                                                                    
                                                    </select>
                                                </div>
                                            </div>
                                        </div> <!-- /.form-group Duration -->
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
                                            <a class="btn btn-success" name="payLodge" id="payLodge" href="#" onclick="return payLodge();" > Proceed to payment</a>
                                        </div> 
                                                   </div>
                                                   </div>
                                                   </div>
                            </div>
                                              </div>
                            </div> <!--/.col-md-12 -->
                    	</fieldset>
                  	</div> <!--/.col-md-12 -->
                        
                          <div class="col-md-12" id="paymentCheckout">
                      
                    	<fieldset>
                        	<legend style="padding-left:20px !important;">Check Out</legend>
                                
                            <div class="col-md-11" >
                            	
                                <div class="row" >
                                   
                                	<div class="col-md-12">
                                            <span style="color:green;font-weight:bold;">You'd be paying N<span id='paySum'></span></span>
                                    	<div class="form-group">
                                            <label for="paymentMethod">Payment method:</label><br/>
                                           
                                            <input type="radio" name="paymentMethod" value="1" id="bankdep" onclick="showNecessaryMenu(1)" required/>&nbsp;<label for="bankdep" style="display:inline !important;">Bank Deposit</label>&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="paymentMethod" value="2" id="paywithcard" onclick="showNecessaryMenu(2)" required/>&nbsp; <label for="paywithcard" style="display:inline !important;cursor:pointer !important;">Credit/Debit Card <img src="${pageContext.request.contextPath}/images/img/paywithcard.png" /></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="paymentMethod" value="3" id="paywithcash" onclick="showNecessaryMenu(3)" required/>&nbsp;<label for="paywithcash" style="display:inline !important;"> Cash</label>
                                            	
                                                </div>
                                        </div>
                                </div>
                                         <div class='row' id='pwBankdeposit'>
                                             <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="bankName">Bank Name</label>
                                            <input type="text" class="form-control" id="bankName" name="bankName" style="width: 100%;"  
                                                   value=<c:if test="${fn:length(errors) > 0 }">"${param.bankName}"</c:if> 
                                                   >
                                        </div> 
                                    </div>
                                             <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerNumber">Depositor's Name</label>
                                            <input type="text" class="form-control" id="depositorsName" name="depositorsName" style="width: 100%;" 
                                                    value='<c:if test="${fn:length(errors) > 0 }">"${param.depositorsName}"</c:if> '
                                                   >
                                        </div> 
                                    </div>
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerNumber">Teller Number</label>
                                            <input type="text" class="form-control" id="tellerNumber" name="tellerNumber" style="width: 100%;"  
                                                   value='<c:if test="${fn:length(errors) > 0 }">"${param.tellerNumber}"</c:if> '
                                                   />
                                        </div> 
                                    </div>
                             
                                
                                
                                
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="tellerAmount">Amount</label>
                                            <input type="text" class="form-control" id="tellerAmount" name="tellerAmount" style="width: 100%;" readonly  
                                                    value='<c:if test="${fn:length(errors) > 0 }">"${param.tellerAmount}"</c:if>' 
                                                   />
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
                                            <label for="cashAmount">Amount</label>
                                            <input type="text" class="form-control" id="cashAmount" name="cashAmount" style="width: 100%;" readonly
                                                   value=<c:if test="${fn:length(errors) > 0 }">"${param.cashAmount}"</c:if>
                                                   />
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
<!--                                        <a href="${pageContext.request.contextPath}/images/img/webpay.png" target="_blank" class="btn btn-success"><i class="fa fa-angle-double-right"></i> Pay Now</a>
                                        --><button type="submit"  name="Pay" class="btn btn-success"  style="vertical-align:bottom !important;"><i class="fa fa-angle-double-right"></i> Pay Now</button>
                                        </div> 
                                    </div>
                             
                                
                                	
                                    </div>
                                             
                                         </div>
                                            </div>
                 	</div> <!--/.row -->
                        
                        
                  </div> <!-- /.box box-default -->
                  </div> <!-- /.col-md-4 -->
                  
   </div> <!-- /.row -->
                  
                  
                       
                    <div class="box-header">
                        <h3 class="box-title block">
                            <span class="pull-right"><a class="btn btn-primary" href="Customer?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Place new Order</a></span>
                        </h3>
                    </div><!-- /.box-header -->
                </div><!-- /.box-body -->
                </form>
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

      
<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


<script>
        
        $(function () {
            $("#entitylist").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": false, "width":"50px", "targets": 4 }
                ]
        });
    
      
          });
          
          
    </script>