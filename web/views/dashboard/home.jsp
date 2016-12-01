    
<%@ include file="../includes/lid.jsp" %>      
 
<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>      

<%@ include file="../includes/sidebar.jsp" %>   


<!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
              <a href="">
                  <c:choose>
                      <c:when test="${sessionScope.userType == 'ADMIN'}">
                        Admin Dashboard
                      </c:when>
                        
                      <c:when test="${sessionScope.userType == 'AGENT'}">
                        Agent Dashboard
                      </c:when>
                  </c:choose>
                  </a>
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
        
            <c:choose>
                  <c:when test="${sessionScope.userType == 'ADMIN'}">
                        <%@ include file="dashcontent.jsp" %>
                  </c:when>

                  <c:when test="${sessionScope.userType == 'AGENT'}">
                        <%@ include file="agentdashcontent.jsp" %>
                  </c:when>
            </c:choose>
            
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->


<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
     
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
