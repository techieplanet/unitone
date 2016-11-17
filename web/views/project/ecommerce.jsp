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
            Projects
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
                    Project List
                  </h3>
                </div><!-- /.box-header -->
                
                 <div class="box-body">
                  
                  
                     <div class="row">
                         
                         <c:set var="count" value="1" />
                         <c:forEach items="${projects}" var="project">     
                         
                              <div class="col-md-4">
                                <div class="thumbnail">
                                    <img src="${pageContext.request.contextPath}/images/img/boxed-bg.png" class="img img-thumbnail" style="min-height: 200px" width="300px" height="200px" alt="No Preview">
                                  <div class="caption">
                                    <h3>${project.getName()}</h3>
                                    <p>
                                        ${fn:substring(project.getDescription(),0,47)}
                                        ${fn:length(project.getDescription()) > 47 ? "...":""}
                                    </p>
                                    <p><a href="${pageContext.request.contextPath}/Project?action=listunits&project_id=${project.getId()}" class="btn btn-primary" role="button">See Units</a></p>
                                  </div>
                                </div>
                              </div>
                              
                            <c:if test="${count == 3}">
                                <span class="clearfix"></span>
                                <c:set var="count" value="0" />
                            </c:if>
                                
                            <c:set var="count" value="${count + 1}" />    
                                        
                         </c:forEach>
                    </div>
                    
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
