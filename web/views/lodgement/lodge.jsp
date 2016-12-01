<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   

<!-- Content Wrapper. Contains page content -->
<div class="content-wrapper">
          
   <section class="content-header">
      <h1>
        Lodgement
      </h1>
    </section>
       
<!-- Customer List goes here -->
<section class="content" id="customerListContainer">
<div class="box">
    <div class="box-header">
      <h3 class="box-title block">
          Select customer
      </h3>
    </div><!-- /.box-header -->

     <div class="box-body">
      <table id="customerList" class="table table-bordered table-striped table-hover">
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
            <c:forEach items="${customers}" var="customer">
                <tr id='row<c:out value="${customer.customerId}" />'>
                    <td><img alt="No Image" class="img-responsive img-thumbnail" width="55" height="50" src="<c:out value='/uploads/NeoForce/images/customer/${customer.photoPath}'></c:out>" /></td>
                    <td class="agentId"><c:out value="${customer.customerId}" /></td>
                    <td class="customerFname"><c:out value="${customer.firstname}" /></td>
                    <td class="customerMname"><c:out value="${customer.middlename}" /></td>
                    <td class="customerLname"><c:out value="${customer.lastname}" /></td>
                    <td class="customerPhone"><c:out value="${customer.phone}" /></td>
                    <td class="customerEmail"><c:out value="${customer.email}" /></td>
                    <td class="customerState"><c:out value="${customer.state}" /></td>

                    <td>
                        <input type="hidden" class="customerImg" value='<c:out value="/uploads/NeoForce/images/customer/${customer.photoPath}"></c:out>' />
                        <input type="hidden" class="agentImg" value='<c:out value="/uploads/NeoForce/images/agent/${customer.agent.photoPath}"></c:out>' />
                        <input type="hidden" class="agentName" value='<c:out value="${customer.agent.lastname} ${customer.agent.firstname}"></c:out>' />
                        <input type="hidden" class="agentPhone" value='<c:out value="${customer.agent.phone}"></c:out>' />
                        <a class="btn btn-primary" href="#" onclick="selectCustomer('${pageContext.request.contextPath}','${customer.customerId}')" role="button">Choose</a>
                    </td>
                </tr>
            </c:forEach>
      </tbody>
        <tfoot>

        </tfoot>
      </table>
      <div><span><a href="#" onclick="showSelectedCustomer()">View selected customer <i class="fa fa-chevron-right"></i></a> </span></div>
    </div><!-- /.box-body -->
  </div><!-- /.box -->
</section>
    
<!-- Spinner goes here -->
<div class="row" id="SpinnerContainer" style='display:none'>
<div class="spinner" >
<img class='img-responsive' src="${pageContext.request.contextPath}/images/uploadProgress.gif" style="margin: 10px auto" />
</div>
</div>


<section class="content" id="customerDetailContainer" style='display:none'>
     
     <div class="col-md-7 pull-right">
         
         <div class="box box-solid">
             
             <div class="box-header with-border">
                 <div class='col-md-6'>
                    <h4>Customer Details</h4>
                 </div>
                 <div class='col-md-6'>
                    <h4>Agent Details</h4>
                 </div>
             </div>
             
             <div class="box-body ">
                 
                 <div class="row">
                     
                     <!-- Customer Details Box-->
                     <div class="col-md-6">
                         
                         <div class="row">
                             <div class="col-md-3"><img src="" id="customerImage" alt="No Image" class="img-thumbnail img-responsive"></div>
                             <div class="col-md-9">
                                 <span id="customerName"></span> <br />
                                 <span id="customerPhone"></span> <br />
                                 <span id="customerEmail"></span> <br />
                                 <span id="customerState"></span> <br />
                             </div>
                         </div>
                         
                     </div>
                     
                     <!-- Agents Details Box -->
                     <div class="col-md-6">
                         <div class="row">
                             <div class="col-md-3"><img src="" id="agentImage" alt="No Image" class="img-thumbnail img-responsive"></div>
                             <div class="col-md-9">
                                 <span id="agentName"></span> <br />
                                 <span id="agentPhone"></span> <br />
                             </div>
                         </div>
                     </div>
                     
                 </div>
                 
             </div>
             
             <div class="box-footer">
                 <a href="#" onclick="showCustomerList()"><i class="fa fa-chevron-left"></i> Show Customer List</a>
             </div>
             
         </div>
         
     </div>
     
</section>

<!-- Order items container goes here -->
<Section class="content" id="orderItems" style="display:none">
<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    
</div>
</section>

<!-- Main content -->
        <section class="content" id="lodgementCart" style="display:none">
          <!-- Your Page Content Here -->
          <div class="box">
                <div class="box-header">
                  <h3 class="box-title block">
                      Lodgement Cart
                  </h3>
                </div><!-- /.box-header -->
                 <div class="box-body">
                     
                     <!-- Lodgement cart -->
                     <div>
                         <table id="lodgementCartTable" class='table table-bordered table-striped table-hover'>
                             <thead>
                                 <tr>
                                     <th>SN</th>
                                     <th>Project</th>
                                     <th>Unit</th>
                                     <th style="text-align: center">Qty</th>
                                     <th style="text-align: center">Amount</th>
                                     <th>Action</th>
                                 </tr>
                             </thead>
                             <tbody>
                                 
                             </tbody>
                             <tfoot>
                                 <tr>
                                     <td style="text-align: right" colspan="5" id="cart-total"></td>
                                     <td></td>
                                 </tr>
                             </tfoot>
                         </table>
                     </div>
                     <div class="text-right">
                         <button class="btn btn-orange" id="checkOutBtn" onclick="checkOut()" disabled="true"><i class="fa fa-cart-plus"></i> Checkout</button>
                     </div>
                      
                </div><!-- /.box-body -->
              </div><!-- /.box -->
          
        </section><!-- /.content -->
        
        
        
        <!-- Lodgement Payment checkout starts here --->
        <div class="row">
          <section class="content" id="checkout" style="display: none">
            
            <div class="col-md-12">
                      <div class="box box-default">
                    	<fieldset>
                        <legend style="padding-left:20px !important;">Check Out</legend>
                        <form name="lodgementCart" method="post" action="${pageContext.request.contextPath}/Lodgement?action=mortgage" >    
                            <div class="col-md-11" >
                            	
                                <!-- Start of Payment Method Container -->
                                <div class="row" > 
                                    <div class="col-md-12">
                                        
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
                                            <input type="text" class="form-control" id="transfer_accountNo" name="accountNo" style="width: 100%;">
                                        </div> 
                                    </div>
                                    
                                    <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="accountNo">Depositor's Account Name</label>
                                            <input type="text" class="form-control" id="transfer_accountName" name="accountName" style="width: 100%;">
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
                                <div><input type="hidden" name="orderItemsJson" id="orderItemsJson" value="" /></div>            
                              </div>
                        </form>
                        </fieldset>
                       </div>
            </div>
                
            
        </section>
        </div>
        
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/neoforce.lodgement.js"></script>
<script>
        
        $(function () {
            $("#customerList").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": false, "width":"50px", "targets": 4 }
                ]
            });
    
        $("#lodgementCartTable").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": false}
                ]
        });
      
          });
          
 