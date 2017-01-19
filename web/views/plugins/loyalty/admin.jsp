<!-- Include the lid -->
<%@ include file="../../includes/lid.jsp" %>      
    
<!-- Include the header -->
<%@ include file="../../includes/header.jsp" %>      

<%@ include file="../../includes/sidebar.jsp" %>   


<!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>

            <a href="${pageContext.request.contextPath}/Plugins" class="blacktext">Plugins</a>

          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-md-3">
                <%@ include file="../plugins.jsp" %>
                <a href="${pageContext.request.contextPath}/Plugins" class="blacktext"><i class="fa fa-angle-double-left"></i> Plugins</a>
            </div>
              
            <div class="col-md-9">
                <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
                <%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

                <form name="projectform" id="projectform" method="POST" action="Project?action=${action}&id=${id}"> 
                    <div class="box box-primary">
                      <div class="box-header with-border">
                        <h3 class="box-title">
                            Loyalty Plugin
                        </h3>
                      </div><!-- /.box-header -->

                      <div class="box-body">
                          <c:if test="${success}">
                              <div class="row">
                                    <div class="col-md-12 ">
                                        <p class="bg-success padding10" style="width:60%">
                                          <i class="fa fa-check"></i>Saved Successfully
                                          <span class="pull-right">
                                              <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Project">Back to list</a>
                                          </span>
                                        </p>
                                    </div>
                                </div>
                          </c:if>

                          <div class="form-group">
                            <label>Plugin Name</label>
                            <input type="text" name="pname" id="pname" class="form-control medium" value="${project.name}" readonly="readonly">
                          </div>

                          <!-- textarea -->
                          <div class="form-group">
                                <div class="col-sm-2">
                                    <label class="checkbox-inline">
                                      <input class="minimal" type="checkbox" name="install_status" id="install_status">Installed
                                    </label>
                                </div>
                              
                                <div class="col-sm-1"></div>
                              
                              <div class="col-sm-2">
                                    <label class="checkbox-inline">
                                      <input class="minimal" type="checkbox" name="active" id="active">Active
                                    </label>
                                </div>
                                <label></label> <!--used to ensure block display-->
                          </div>
                          <hr>
                          
                          
                          <div class="form-group">
                              <div class="col-sm-12">
                                  <label>Reward Points</label>
                              </div>
                                
                                <div class="col-sm-2">
                                    1 reward point  
                                </div>
                                <div class="col-sm-1">=</div>
                                <div class="col-sm-2" style="margin-top: -5px;">
                                    <input type="text" name="settings" id="settings" class="form-control medium" value="${project.name}">
                                    <small>Enter amount per reward point in Naira</small>
                                </div>
                                <label></label>
                          </div>
                          
                          <div class="form-group">
                              <label></label>
                              <div class="col-sm-12 margintop20">
                                <!--<div class="box-footer">-->
                                    <button type="submit" class="btn btn-primary">Submit</button>
                                 <!--</div>-->
                              </div>
                          </div>
                                
                      </div>

                    </div><!-- /. box -->
                </form>
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->


<!-- Include the footer -->
<%@ include file="../../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../../includes/bottom.jsp" %>