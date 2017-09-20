<%@include file="header.jsp" %>              
            
        <div class="container-fluid" style="margin-top: 120px">
          
           <div class="section" style="width: 95%;margin: 10px auto 0 auto">
                        <c:if test="${projectUnits.size() > 0}">  
                            <c:set var="project" value="${projectUnits.get(0).getProject()}" />
                         <div class="panel panel-default row">   
                         <div class="panel-body">    
                         <div class="col-md-2">
                             <c:if test="${project.getImage() == null}" >
                                    <img src="${pageContext.request.contextPath}/images/img/project.jpg"  alt="No Preview Image" class="img img-thumbnail" />
                              </c:if>
                             <c:if test="${project.getImage() != null}" >
                                       <img src="/uploads/NeoForce/images/${project.getImage()}" alt="No Preview Image" class="img img-thumbnail" >
                               </c:if>
                         </div>
                         
                         <div class="col-md-8">
                             
                             
                             <div class="well-lg">
                                 <div class="col-md-2"><b>Name</b></div><div class="col-md-10"><p>${project.getName()}</p></div>
                                 <div class="col-md-2"><b>Location</b></div><div class="col-md-10"><p>${project.getLocation()}</p></div>
                                 <div class="col-md-2"><b>Description</b></div><div class="col-md-10"><p>${project.getDescription()}</p></div>
                                 
                             </div>
                           
                         </div>
                         </div>
                         </div>        
                        </c:if>
           </div>  
            
           <form action="${pageContext.request.contextPath}/Project?action=addToCart&loggedin=no" method="post">
            <section class="well-lg">        
             
             <div class="row">
                 
                 <c:set var="count" value="1" />
                 <c:forEach items="${projectUnits}" var="unit">     

                      <div class="col-md-3">
                        <div class="thumbnail">
                          <c:if test="${unit.getImage() == null}" >
                                    <img src="${pageContext.request.contextPath}/images/img/unit.jpg" class="img" style="height: 200px"  alt="No Preview">
                          </c:if>
                             <c:if test="${unit.getImage() != null}" >
                                       <img src="/uploads/NeoForce/images/${unit.getImage()}" alt="No Preview Image" class="img img-thumbnail" >
                               </c:if>
                          <div class="caption text-center">
                            <h4>${unit.getTitle()}</h4>
                            <span><fmt:formatNumber value="${unit.getCpu()}" type="currency" currencySymbol="N" /></span><br />
                            <span>In Stock : ${unit.getQuantity()}</span> 
                            <p class="text-center">
                                <input type="checkbox"  class="unit_check" name="unit_id" value="${unit.getId()}" autocomplete="off"

                                       <c:forEach items="${unit_cart}" var="cartUnit">
                                           <c:if test="${cartUnit.getId() == unit.getId()}">
                                               disabled checked
                                           </c:if>
                                       </c:forEach>
                                >
                                    <label>Select</label>
                            </p>
                          </div>
                        </div>
                      </div>

                    <c:if test="${count == 4}">
                        <span class="clearfix"></span>
                        <c:set var="count" value="0" />
                    </c:if>

                    <c:set var="count" value="${count + 1}" />    

                 </c:forEach>   
             </div>    
                
             <div class="row">
                 <button type="submit" class="btn btn-success pull-right"><i class="fa fa-cart-plus"></i> Add to cart</button>
                 <a href="${pageContext.request.contextPath}/" class="btn btn-primary pull-left"><i></i> Back</a>
             </div>    
                 
            </section>
            </form>   
        </div>
        
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <!-- iCheck 1.0.1 -->
        <script src="plugins/iCheck/icheck.min.js"></script>
        <script>
            
            var ecommerce = {
                
                initCheckBox : function(){
                    $('.unit_check').each(function(){
                      var self = $(this);
                      label = self.next();
                      label_text = label.text();

                    label.remove();
                    self.iCheck({
                      checkboxClass: 'icheckbox_line-blue',
                      radioClass: 'iradio_line-blue',
                      insert: '<div class="icheck_line-icon"></div>' + label_text
                    });
                  });
                }
                
            } 
            
            ecommerce.initCheckBox();
        </script>
    </body>
</html>
