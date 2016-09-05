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
              <img src="images/img/boxed-bg.jpg" class="img-circle" alt="User Image">
            </div>
            <div class="pull-left info">

              <p>${user.firstname} ${user.lastname}</p>

              <!-- Status -->
              <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
            </div>
          </div>


              
          <c:if test="${sessionScope.userType eq sessionScope.userTypes.ADMIN}" >
                <!-- Admin Side Menu -->
                <ul class="sidebar-menu">
                  <li class="header"></li>
                  <!-- Optionally, you can add icons to the links -->
                  
                  <li class="active"><a href="#"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
                  <li class=""><a href="#"><i class="fa fa-microphone"></i> <span>Announcements</span><small class="label pull-right bg-yellow">2</small></a></li>
                  <li class=""><a href="#"><i class="fa fa-envelope"></i> <span>Mailbox</span><small class="label pull-right bg-red">5</small></a></li>
                  
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'create_customer')}">
                    <li class=""><a href="#"><i class="fa fa-user-plus"></i> <span>Ceate New Customer</span></a></li>
                  </c:if>
                  
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')}">
                        <li class=""><a href="#"><i class="fa fa-users"></i> <span>Agents</span></a></li>
                  </c:if>
                        
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_customer')}">
                        <li class=""><a href="#"><i class="fa fa-users"></i> <span>My Customers</span></a></li>
                  </c:if>
                  
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_sale')}">
                        <li class=""><a href="#"><i class="fa fa-level-up"></i> <span>My Sales</span></a></li>
                  </c:if>
                    
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_wallet')}">
                        <li class="treeview">
                          <a href="#"><i class="fa fa-briefcase"></i><span>My Wallet</span> <i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="#">Current Balance</a></li>
                            <li><a href="#">Credit History</a></li>
                            <li><a href="#">Withdrawal History</a></li>
                            <li><a href="#">Withdraw Funds</a></li>
                          </ul>
                        </li>
                  </c:if>
                        
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_project')}">
                    <li class=""><a href="${pageContext.request.contextPath}/Project"><i class="fa fa-home"></i><span>Projects</span></a></li>
                  </c:if>
                    
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_role')}">
                    <li class=""><a href="${pageContext.request.contextPath}/Role"><i class="fa fa-user"></i> <span>Roles</span></a></li>
                  </c:if>
                    
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_user')}">
                    <li class=""><a href="${pageContext.request.contextPath}/User"><i class="fa fa-users"></i> <span>Users</span></a></li>
                  </c:if>
                    
                </ul><!-- /.sidebar-menu -->
          </c:if>
          
                
          <c:if test="${sessionScope.userType eq sessionScope.userTypes.AGENT}">
          </c:if>
          
          <c:if test="${sessionScope.userType eq sessionScope.userTypes.CUSTOMER}">
          </c:if>

          
        </section>
        <!-- /.sidebar -->
      </aside>