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
            Project Units
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
                    Project Unit List
                  </h3>
                  
                  <div class="row" style="margin-top:10px">
                        <c:if test="${projectUnits.size() > 0}">   
                         <div class="col-md-2">
                             <img src="${pageContext.request.contextPath}/images/img/boxed-bg.png"  alt="No Preview Image" class="img img-thumbnail" />
                         </div>
                         
                         <div class="col-md-8">
                             
                             <c:set var="project" value="${projectUnits.get(0).getProject()}" />
                             <div class="well-lg">
                                 <div class="col-md-2"><b>Name</b></div><div class="col-md-10"><p>${project.getName()}</p></div>
                                 <div class="col-md-2"><b>Location</b></div><div class="col-md-10"><p>${project.getLocation()}</p></div>
                                 <div class="col-md-2"><b>Description</b></div><div class="col-md-10"><p>${project.getDescription()}</p></div>
                                 
                             </div>
                           
                         </div>
                        </c:if>
                  </div>  
                    
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                  
                  <form action="${pageContext.request.contextPath}/Project?action=addToCart" method="post">
                     
                      
                    <section class="well-lg">         
                     <div class="row">
                            
                         <c:set var="count" value="1" />
                         <c:forEach items="${projectUnits}" var="unit">     
                         
                              <div class="col-md-3">
                                <div class="thumbnail">
                                  <img src="${pageContext.request.contextPath}/images/img/boxed-bg.png" class="img img-thumbnail" style="height: 200px"  alt="No Preview">
                                  <div class="caption">
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
                    </section>
                         
                    <div class="panel panel-default">
                      <div class="panel-body">
                          <button type="submit" class="btn btn-success pull-right"><i class="fa fa-cart-plus"></i> Add to cart</button>
                          <a href="${pageContext.request.contextPath}/Project?action=listprojects" class="btn btn-primary pull-left">Continue shopping</a>
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
        
    var ecommerce = {
        
        cart : {},
        
        addToCart : function(unit_id){
            
        },
        
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
