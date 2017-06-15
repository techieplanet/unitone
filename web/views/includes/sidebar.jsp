<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!-- Left side column. contains the logo and sidebar -->
      <aside class="main-sidebar">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

          <!-- Sidebar user panel (optional) -->
          <div class="user-panel">
            <div class="pull-left image">
              <img src="${pageContext.request.contextPath}/images/img/boxed-bg.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">

              <p>${user.firstname} ${user.lastname}</p>

              <!-- Status -->
              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
          </div>


              
          <c:if test="${sessionScope.userType eq sessionScope.userTypes.ADMIN}">
                <!-- Admin Side Menu -->
                <%@ include file="sidebar-admin.jsp" %>
                
          </c:if>
          
                
          <c:if test="${sessionScope.userType eq sessionScope.userTypes.AGENT}">
              
              <%@ include file="sidebar-agent.jsp" %>
              
          </c:if>
          
          <c:if test="${sessionScope.userType eq sessionScope.userTypes.CUSTOMER}">
              <%@ include file="sidebar-customer.jsp" %>
          </c:if>

          
        </section>
        <!-- /.sidebar -->
      </aside>