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
       

<section class="content" id="customerListContainer">
<div class="row" >
<div class="box">
    <div class="box-header">
      <h3 class="box-title block">
          Select a customer
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
                    <td class="agentId"><c:out value="${customer.agentId.agentId}" /></td>
                    <td class="customerFname"><c:out value="${customer.firstname}" /></td>
                    <td class="customerMname"><c:out value="${customer.middlename}" /></td>
                    <td class="customerLname"><c:out value="${customer.lastname}" /></td>
                    <td class="customerPhone"><c:out value="${customer.phone}" /></td>
                    <td class="customerEmail"><c:out value="${customer.email}" /></td>
                    <td class="customerState"><c:out value="${customer.state}" /></td>

                    <td>
                        <input type="hidden" class="customerImg" value='<c:out value="/uploads/NeoForce/images/customer/${customer.photoPath}"></c:out>' />
                        <input type="hidden" class="agentImg" value='<c:out value="/uploads/NeoForce/images/agent/${customer.agentId.photoPath}"></c:out>' />
                        <input type="hidden" class="agentName" value='<c:out value="${customer.agentId.lastname} ${customer.agentId.firstname}"></c:out>' />
                        <input type="hidden" class="agentPhone" value='<c:out value="${customer.agentId.phone}"></c:out>' />
                        <a class="btn btn-primary" href="#" onclick="selectCustomer('${pageContext.request.contextPath}','${customer.customerId}')" role="button">Choose</a>
                    </td>
                </tr>
            </c:forEach>
      </tbody>
        <tfoot>

        </tfoot>
      </table>
      <div><span><a href="#" onclick="showSelectedCustomer()">View selected customer</a></span></div>
    </div><!-- /.box-body -->
  </div><!-- /.box -->
</div>
</section>
    
<!-- Spinner goes here -->
<div class="row" id="SpinnerContainer" style='display:none'>
<div class="spinner" >
<img class='img-responsive' src="${pageContext.request.contextPath}/images/uploadProgress.gif" style="margin: 10px auto" />
</div>
</div>


<section class="">
<div class="row" id="customerDetailContainer"  style='display:none'>
     
     <div class="col-md-7 pull-right">
         
         <div class="box box-solid">
             
             <div class="box-header with-border">
                 <h3 class="box-title">Customer Details</h3>
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
                 <a href="#" onclick="showCustomerList()">Show Customer List<<</a>
             </div>
             
         </div>
         
     </div>
     
 </div>
</section>


<div class="row" id="orderContainer"  style='display:none'>

<div class="col-md-12">

<div class="box box-solid">

 <div class="box-header with-border">
     <h3 class="box-title">Customer Orders</h3>
 </div>

 <div class="box-body ">

     <table id="orderList" class="table table-bordered table-striped table-hover">
        <thead>
          <tr>
            <th>ID</th>
            <th>Customer Name</th>
            <th>Agent Name</th>
          </tr>
        </thead>
        <tbody>
           
       </tbody>
        <tfoot>

        </tfoot>
      </table>

 </div>

 <div class="box-footer">
     
 </div>

</div>

</div>

</div>

<!-- Order items container goes here -->

<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
    
</div>

<!-- Main content -->
        <section class="content" id="lodgementForm" style="display:none">
          <!-- Your Page Content Here -->
          <div class="box">
                <div class="box-header">
                  <h3 class="box-title block">
                      Customer Lodgement
                      <span class="pull-right">
                          <a class="btn btn-primary" href="Order?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp; Place new Order</a>
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
                            
                  
                  
                       
                    <div class="box-header">
                        <h3 class="box-title block">
                            <span class="pull-right"><a class="btn btn-primary" href="Order?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Place new Order</a></span>
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

<script type="text/javascript" src="${pageContext.request.contextPath}/js/neoforce.lodgement.js"></script>
<script>
        
        $(function () {
            $("#customerList").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": false, "width":"50px", "targets": 4 }
                ]
        });
    
      
          });
          
          
    </script>