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
            Prospective Customer
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
                <div class="box-header with-border">
                  <h3 class="box-title block">
                      Prospective Customer List
                      
                      <a href="${pageContext.request.contextPath}/Customer?action=new_prospect" class="btn btn-primary pull-right"><i class="fa fa-plus"></i> Add New Prospect</a>
                        
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                  
                     <div class="table-responsive">
                         
                         <table class="table table-hover table-striped" id="prospective_customer_list">
                             
                             <thead>
                                 <tr>
                                     <th>#</th>
                                     <th>Last Name</th>
                                     <th>Middle Name</th>
                                     <th>First Name</th>
                                     <th>Email</th>
                                     <th>Phone no</th>
                                     <th>Street</th>
                                     <th>City</th>
                                     <th>State</th>
                                     <th>Action</th>
                                 </tr>
                             </thead>
                             
                             
                             <tbody>
                                 
                                 <c:forEach items="${prospects}" var="prospect" varStatus="pointer">
                                     
                                     <tr>
                                         <td>${pointer.count}</td>
                                         <td>${prospect.lastName}</td>
                                         <td>${prospect.middleName}</td>
                                         <td>${prospect.firstName}</td>
                                         <td>${prospect.email}</td>
                                         <td>${prospect.phoneNo}</td>
                                         <td>${prospect.street}</td>
                                         <td>${prospect.city}</td>
                                         <td>${prospect.state}</td>
                                         <td>
                                             <a href="${pageContext.request.contextPath}/Customer?action=edit_prospect&id=${prospect.id}" class="btn btn-success btn-xs"><i class="fa fa-edit"></i></a> &nbsp;
                                             <a href="#" onclick="prospectCustomer.showDeleteModal('${prospect.id}',event)" class="btn btn-danger btn-xs" onclick="prospectCustomer.showDeleteModal('${prospect.id}',event)"><i class="fa fa-remove"></i></a>
                                         </td>
                                     </tr>
                                     
                                 </c:forEach>
                                 
                             </tbody>
                             
                             <tfoot>
                                 <tr>
                                     <th>#</th>
                                     <th>Last Name</th>
                                     <th>Middle Name</th>
                                     <th>First Name</th>
                                     <th>Email</th>
                                     <th>Phone no</th>
                                     <th>State</th>
                                     <th>City</th>
                                     <th>Street</th>
                                     <th>Action</th>
                                 </tr>
                             </tfoot>
                             
                         </table>
                         
                     </div>
                     
                  
                </div><!-- /.box-body -->
                <div class="box-footer">
                    <a href="${pageContext.request.contextPath}/Customer?action=new_prospect" class="btn btn-primary pull-right"><i class="fa fa-plus"></i> Add New Prospect</a>
                </div>
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
              <button id="ok" type="button" onclick="prospectCustomer.delete()" class="btn btn-primary">OK</button>
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
        
        $(document).ready(function(){
            $("#prospective_customer_list").DataTable({
                order :[[0,"asc"]]
            });
        });
        
        var prospectCustomer = {
            
            delete_id : 0,
            
            showDeleteModal : function(id, evt){
                
                evt.preventDefault();
                $("#deleteModal").modal({
                    backdrop : "static"
                });
                
                prospectCustomer.delete_id = id;
                
            },
            
            delete : function(){
                alert("ID : " + prospectCustomer.delete_id);
            }
            
        }
          
</script>         

