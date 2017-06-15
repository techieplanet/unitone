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
              <a href="#">Customer</a>
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            
            <c:if test="${success}">
                
                <div class="alert alert-success">
                    <h5>Record was updated successfully</h5>
                </div>
            </c:if>
            
            <c:if test="${errors}">
                
                <div class="row">
                <div class="col-md-12">
                <c:forEach items="errors" var="error">
                    <span class="alert alert-success">${error.value}</span>
                </c:forEach>
                </div>
                </div>
            </c:if>

            <div class="box box-primary">
                
                <div class="box-header with-border">
                    <h3 class="box-title"></h3>
                </div>
                
                <div class="box-body">
                    
                    <form method="POST" action="${pageContext.request.contextPath}/Customer?action=update&id=${customer.getCustomerId()}" enctype="multipart/form-data">
                        
                      <div class="row">
                          
                          <div class="col-md-4 form-group">
                              <label for="fname">First Name</label>
                              <input type="text" name="fname" id="fname" class="form-control" value="${customer.getFirstname()}" />
                          </div>
                          
                          <div class="col-md-4 form-group">
                              <label for="mname">Middle Name</label>
                              <input type="text" name="mname" id="mname" class="form-control" value="${customer.getMiddlename()}" />
                          </div>
                          
                          <div class="col-md-4 form-group">
                              <label for="lname">Last Name</label>
                              <input type="text" name="lname" id="lname" class="form-control" value="${customer.getLastname()}" />
                          </div>
                          
                          <div class="col-md-4 form-group">
                              <label for="email">Email</label>
                              <input type="text" name="email" id="email" class="form-control" value="${customer.getEmail()}" />
                          </div>
                          
                          <div class="clearfix"></div>
                          
                          <div class="col-md-4 form-group">
                                
                                <div>
                                    <img width="150" height="150" src="${customerImageAccessDir}/${customer.getPhotoPath()}" class="img img-responsive img-thumbnail" />
                                </div>
                              
                                <div class="btn-group btn-group-xs">
                                  <label for="customerPhoto" style="display:block">Customer Photo</label>
                                  <div class="btn btn-primary">
                                  <input type="file" name="customerPhoto" accept="image/gif, image/jpeg, image/png" id="customerPhoto" >
                                  
                                 </div>
                                </div>
                          </div>
                          
                      </div>
                          
                      <hr />
                          
                      <div class="row"> 
                          
                          <div class="col-md-4 form-group">
                              <label for="street">Street</label>
                              <input type="text" name="street" id="street" class="form-control" value="${customer.getStreet()}" />
                          </div>
                          
                          <div class="col-md-4 form-group">
                              <label for="city">City</label>
                              <input type="text" name="city" id="city" class="form-control" value="${customer.getCity()}" />
                          </div>
                          
                          
                          <div class="col-md-4 form-group">
                              <label>State</label>
                              <select name="state" class="form-control">
                                  
                                  <option value="">--choose--</option>
                                    
                                    <option value="ABUJA FCT" <c:if test="${customer.getState() eq 'ABUJA FCT'}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                    <option value="Abia" <c:if test="${customer.getState() eq 'Abia'}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                    <option value="Adamawa" <c:if test="${customer.getState() eq 'Adamawa'}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                    <option value="Akwa Ibom" <c:if test="${customer.getState() eq 'Akwa Ibom'}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                    <option value="Anambra" <c:if test="${customer.getState() eq 'Anambra'}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                    <option value="Bauchi" <c:if test="${customer.getState() eq 'Bauchi'}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                    <option value="Bayelsa" <c:if test="${customer.getState() eq 'Bayelsa'}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                    <option value="Benue" <c:if test="${customer.getState() eq 'Benue'}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                    <option value="Borno" <c:if test="${customer.getState() eq 'Borno'}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                    <option value="Cross River" <c:if test="${customer.getState() eq 'Cross River'}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                    <option value="Delta" <c:if test="${customer.getState() eq 'Delta'}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                    <option value="Ebonyi" <c:if test="${customer.getState() eq 'Ebonyi'}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                    <option value="Edo" <c:if test="${customer.getState() eq 'Edo'}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                    <option value="Ekiti" <c:if test="${customer.getState() eq 'Ekiti'}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                    <option value="Enugu" <c:if test="${customer.getState() eq 'Enugu'}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                    <option value="Gombe" <c:if test="${customer.getState() eq 'Gombe'}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                    <option value="Imo" <c:if test="${customer.getState() eq 'Imo'}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                    <option value="Jigawa" <c:if test="${customer.getState() eq 'Jigawa'}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                    <option value="Kaduna" <c:if test="${customer.getState() eq 'Kaduna'}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                    <option value="Kano" <c:if test="${customer.getState() eq 'Kano'}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                    <option value="Katsina" <c:if test="${customer.getState() eq 'Katsina'}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                    <option value="Kebbi" <c:if test="${customer.getState() eq 'Kebbi'}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                    <option value="Kogi" <c:if test="${customer.getState() eq 'Kogi'}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                    <option value="Kwara" <c:if test="${customer.getState() eq 'Kwara'}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                    <option value="Lagos" <c:if test="${customer.getState() eq 'Lagos'}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                    <option value="Nassarawa" <c:if test="${customer.getState() eq 'Nassarawa'}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                    <option value="Niger" <c:if test="${customer.getState() eq 'Niger'}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                    <option value="Ogun" <c:if test="${customer.getState() eq 'Ogun'}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                    <option value="Ondo" <c:if test="${customer.getState() eq 'Ondo'}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                    <option value="Osun" <c:if test="${customer.getState() eq 'Osun'}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                    <option value="Oyo" <c:if test="${customer.getState() eq 'Oyo'}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                    <option value="Plateau" <c:if test="${customer.getState() eq 'Plateau'}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                    <option value="Rivers" <c:if test="${customer.getState() eq 'Rivers'}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                    <option value="Sokoto" <c:if test="${customer.getState() eq 'Sokoto'}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                    <option value="Taraba" <c:if test="${customer.getState() eq 'Taraba'}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                    <option value="Yobe" <c:if test="${customer.getState() eq 'Yobe'}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                    <option value="Zamfara" <c:if test="${customer.getState() eq 'Zamfara'}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                                  
                              </select>
                          </div>
                                    
                          <div class="col-md-4 form-group">
                              <label>Phone Number</label>
                              <input type="text" class="form-control" name="phone" id="phone" value="${customer.getPhone()}" />
                          </div>
                          
                      </div>
                          
                      
                          
                      <div class="row">
                          
                          <div class="col-md-4 form-group">
                              <label>Next of Kin Name</label>
                              <input type="text" class="form-control" name="customerKinNames" id="customerKinNames" value="${customer.getKinName()}" />
                          </div>
                          
                          <div class="col-md-4 form-group">
                              <label>Next of Kin Phone No</label>
                              <input type="text" class="form-control" name="customerKinPhone" id="customerKinPhone" value="${customer.getKinPhone()}" />
                          </div>
                          
                          <div class="col-md-4 form-group">
                              <label>Next of Kin Address</label>
                              <input type="text" class="form-control" name="customerKinAddress" id="customerKinAddress" value="${customer.getKinAddress()}" />
                          </div>
                          
                          <div class="col-md-4 form-group">
                              
                              <div>
                                    <img width="150" height="150" src="${customerKinImageAccessDir}/${customer.getKinPhotoPath()}" class="img img-responsive img-thumbnail" alt="No Preview Image" />
                              </div>
                              
                              <div class="btn-group btn-group-xs">
                                  <label for="customerKinPhoto" style="display:block">Customer Photo</label>
                                  <div class="btn btn-primary">
                                  <input type="file" name="customerKinPhoto" accept="image/gif, image/jpeg, image/png" id="customerPhoto" >
                                  
                                 </div>
                                </div>
                          </div>
                          
                      </div>
                          
                      <div class="row">
                          <div class="col-md-6">
                            <button type="submit" class="btn btn-primary">Update</button>
                          </div>
                      </div>
                        
                    </form>
                    
                </div>
                
                
            </div>
          
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->




<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
      

<script>
    
</script>