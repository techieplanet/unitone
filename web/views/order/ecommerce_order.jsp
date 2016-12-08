<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
       ${title}
      </h1>
    </section>
    <!-- Main content -->
    <section class="content">                              
 <div class="row">

<div class="col-md-12">
  <!-- general form elements -->

    <!-- form start -->
    <form role="form" name="new_order" method="POST" action="${pageContext.request.contextPath}/Order" onsubmit="return ecommerce.submitForm()" >

    <input type="hidden" name="agent_id" id="agent_id" value="" />

   
    <!-- form start -->
    <div style="background:#ecf0f5 !important;">
    <!--<form role="form" name="customerRegistration" method="POST" action="Order" enctype="multipart/form-data">-->
      
      <div class="row">
      <div class="col-md-12">
          
          <c:if test="${sessionScope.user.getSystemUserTypeId() == 2}">
          <div class="panel panel-default">
              
              <div class="panel-body">
                  
                  <label>Select Customer </label> <br />
                  
                  <select name="customer_id">
                      <option value="">--Select a customer--</option>
                      <c:forEach items="${customers}" var="customer">
                          <option value="${customer.getCustomerId()}">${customer.getFullName()}</option>
                      </c:forEach>
                      
                  </select>
              </div>
              
          </div>
          </c:if>
          
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
                              <td colspan="7" style="text-align: right"><b>Total Amount to Pay : </b></td>
                              <td id="total"><fmt:formatNumber value="${total}" currencySymbol="N" type="currency" /></td>
                          </tr>
                      </tfoot>
                  </table>
              </div>
              <div class="panel-footer">
                  <a class="btn btn-success"  onclick="ecommerce.proceedToPayment(event)">Proceed to Payment</a> &nbsp;&nbsp;
                  <a href="${pageContext.request.contextPath}/Project?action=listprojects"  class="btn btn-primary">Continue shopping</a>
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
                                            <c:if test="${userType != null && userType == 3 }">
                                            <input type="radio" name="paymentMethod" value="2" id="paywithcard" onclick="showNecessaryMenu(2)"/>&nbsp; <label for="paywithcard" style="display:inline !important;cursor:pointer !important;">Credit/Debit Card <img src="${pageContext.request.contextPath}/images/img/paywithcard.png" /></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                            </c:if>
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
                                
                                
                                
                                <c:if test="${userType == 3}">
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
                    	
        </div> 
     </div> 
       </div>
                          
      </div><!-- /.row -->
      
      <c:if test="${sessionScope.user.getSystemUserTypeId() == 3}">
            <input type="hidden" name="customer_id" id="id" value="${customerId}">
      </c:if>
      <input type="hidden" name="id" id="id" value="${customerId}">
      <input type="hidden" name="cartDataJson" id="cartDataJson" />
      
      </form>

  </div><!-- /.box -->
  
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
                          <strong>${user.getFullName()} </strong><br>
                        ${user.getStreet()}<br>
                        ${user.getState()}<br>
                        Phone: ${user.getPhone()}<br>
                        Email: ${user.getEmail()}
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
  </div><!-- /.box -->

    </section>
  </div>
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
      
<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      
  
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
      
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
       }
       
    }
    
</script>
