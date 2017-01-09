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
            Edit Prospective Customer
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
                      Prospective Customer Edit Form
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                  
                     <div class="row">
                         
                         <c:if test="${not empty errors}">
                             <div class="col-md-12">
                                 <div class="alert alert-danger alert-dismissible">
                                     <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                      <span aria-hidden="true">&times;</span>
                                    </button>
                                     <c:forEach items="${errors}" var="error">
                                         <p>${error.value}</p>
                                     </c:forEach>
                                 </div>
                             </div>
                         </c:if>
                         
                         <c:if test="${not empty success}">
                             <div class="col-md-12">
                                 <div class="alert alert-success alert-dismissible">
                                     <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                                      <span aria-hidden="true">&times;</span>
                                    </button>
                                         <p>${success}</p>
                                 </div>
                             </div>
                         </c:if>
                         
                     </div>
                     
                     <form method="post" action="${pageContext.request.contextPath}/Customer?action=edit_prospect">
                         
                         <div class="row">
                           
                          <input type="hidden" name="id" value="${prospect.id}" />   
                             
                          <div class="col-md-12">  
                             
                           <fieldset><legend>Personal Information</legend>  
                              
                             <div class="col-md-4">
                                 <div class="form-group">   
                                     <label>First Name</label>
                                     <input type="text" class="form-control" name="fname" placeholder="First Name" value="${prospect.firstName}" required />
                                 </div>
                             </div>
                             
                             <div class="col-md-4">
                                 <div class="form-group">   
                                     <label>Middle Name</label>
                                     <input type="text" class="form-control" name="mname" placeholder="Middle Name" value="${prospect.middleName}" required />
                                 </div>
                             </div>
                             
                             <div class="col-md-4">
                                 <div class="form-group">   
                                     <label>Last Name</label>
                                     <input type="text" class="form-control" name="lname" placeholder="Last Name" value="${prospect.lastName}" required />
                                 </div>
                             </div>
                             
                             <div class="clearfix"></div>
                             
                             <div class="col-md-4">
                                 <div class="form-group">   
                                     <label>Email</label>
                                     <input type="email" class="form-control" name="email" placeholder="Email" value="${prospect.email}" required />
                                 </div>
                             </div>
                             
                           </fieldset>
                          </div>
                             
                         </div>
                         
                         <div class="row">
                             
                             <div class="col-md-12">  
                             
                           <fieldset><legend>Contact Information</legend>
                             
                             <div class="col-md-4">
                                 <div class="form-group">   
                                     <label>Street</label>
                                     <input type="text" class="form-control" name="street" placeholder="Street" value="${prospect.street}" required />
                                 </div>
                             </div>
                             
                             <div class="col-md-4">
                                 <div class="form-group">   
                                     <label>City</label>
                                     <input type="text" class="form-control" name="city" placeholder="City" value="${prospect.city}" required />
                                 </div>
                             </div>
                             
                             <div class="col-md-4">
                                 <div class="form-group">   
                                     <label>State</label>
                                      <select class="form-control" id="customerState" name="state"  >
                                            <option value="">--choose--</option>

                                            <option value="ABUJA FCT" <c:if test="${prospect.state == 'ABUJA FCT'}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                            <option value="Abia" <c:if test="${prospect.state == 'Abia'}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                            <option value="Adamawa" <c:if test="${prospect.state == 'Adamawa'}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                            <option value="Akwa Ibom" <c:if test="${prospect.state == 'Akwa Ibom'}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                            <option value="Anambra" <c:if test="${prospect.state == 'Anambra'}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                            <option value="Bauchi" <c:if test="${prospect.state == 'Bauchi'}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                            <option value="Bayelsa" <c:if test="${prospect.state == 'Bayelsa'}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                            <option value="Benue" <c:if test="${prospect.state == 'Benue'}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                            <option value="Borno" <c:if test="${prospect.state == 'Borno'}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                            <option value="Cross River" <c:if test="${prospect.state == 'Cross River'}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                            <option value="Delta" <c:if test="${prospect.state == 'Delta'}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                            <option value="Ebonyi" <c:if test="${prospect.state == 'Ebonyi'}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                            <option value="Edo" <c:if test="${prospect.state == 'Edo'}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                            <option value="Ekiti" <c:if test="${prospect.state == 'Ekiti'}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                            <option value="Enugu" <c:if test="${prospect.state == 'Enugu'}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                            <option value="Gombe" <c:if test="${prospect.state == 'Gombe'}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                            <option value="Imo" <c:if test="${prospect.state == 'Imo'}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                            <option value="Jigawa" <c:if test="${prospect.state == 'Jigawa'}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                            <option value="Kaduna" <c:if test="${prospect.state == 'Kaduna'}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                            <option value="Kano" <c:if test="${prospect.state == 'Kano'}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                            <option value="Katsina" <c:if test="${prospect.state == 'Katsina'}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                            <option value="Kebbi" <c:if test="${prospect.state == 'Kebbi'}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                            <option value="Kogi" <c:if test="${prospect.state == 'Kogi'}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                            <option value="Kwara" <c:if test="${prospect.state == 'Kwara'}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                            <option value="Lagos" <c:if test="${prospect.state == 'Lagos'}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                            <option value="Nassarawa" <c:if test="${prospect.state == 'Nassarawa'}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                            <option value="Niger" <c:if test="${prospect.state == 'Niger'}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                            <option value="Ogun" <c:if test="${prospect.state == 'Ogun'}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                            <option value="Ondo" <c:if test="${prospect.state == 'Ondo'}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                            <option value="Osun" <c:if test="${prospect.state == 'Osun'}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                            <option value="Oyo" <c:if test="${prospect.state == 'Oyo'}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                            <option value="Plateau" <c:if test="${prospect.state == 'Plateau'}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                            <option value="Rivers" <c:if test="${prospect.state == 'Rivers'}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                            <option value="Sokoto" <c:if test="${prospect.state == 'Sokoto'}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                            <option value="Taraba" <c:if test="${prospect.state == 'Taraba'}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                            <option value="Yobe" <c:if test="${prospect.state == 'Yobe'}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                            <option value="Zamfara" <c:if test="${prospect.state == 'Zamfara'}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                                      </select>
                                 </div>
                             </div>
                             
                             <div class="col-md-4">
                                 <div class="form-group">   
                                     <label>Phone no</label>
                                     <input type="text" class="form-control" name="phone" placeholder="Phone number" value="${prospect.phoneNo}" required />
                                 </div>
                             </div>
                             
                           </fieldset>
                             </div>
                                      
                         </div>
                                      
                         <div class="row">
                             
                             <div class="col-md-12">
                                 
                                 <fieldset><legend>Job Information</legend>
                                     
                                     <div class="col-md-4">
                                         <div class="form-group">
                                             <label>Company</label>
                                             <input type="text" class="form-control" name="customer_comapany_name" placeholder="Company Name" value="${prospect.company}" required />
                                         </div>
                                     </div>
                                     
                                     <div class="col-md-4">
                                         <div class="form-group">
                                             <label>Post</label>
                                             <input type="text" class="form-control" name="customer_post" placeholder="Customer Post" value="${prospect.post}" required />
                                         </div>
                                     </div>
                                     
                                 </fieldset>   
                                 
                             </div>
                             
                         </div>
                                      
                         <div class="row-border">
                             
                             <div class="col-md-12">
                                 <input type="submit" class="btn btn-primary" name="btnSubmit" value="Update" />
                             </div>
                             
                         </div>
                         
                     </form>
                     
                  
                </div><!-- /.box-body -->
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
        
          
</script>         
