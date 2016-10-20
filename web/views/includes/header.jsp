 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Main Header -->
      <header class="main-header">

        <!-- Logo -->

        <a href="/NeoForce" class="logo">

          <!-- mini logo for sidebar mini 50x50 pixels -->
          <span class="logo-mini"><b>NEO</b></span>
          <!-- logo for regular state and mobile devices -->
          <span class="logo-lg"><b>NEOFORCE</b></span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
          <!-- Sidebar toggle button-->
          <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
            <span class="sr-only">Toggle navigation</span>
          </a>
          
          
          
          <!-- Navbar Right Menu -->
          <div class="navbar-custom-menu">
            <ul class="nav navbar-nav">
              <!-- Messages: style can be found in dropdown.less-->

              <!-- Notifications Menu -->
              <li class="dropdown notifications-menu">
                <!-- Menu toggle button -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-bell-o"></i>
                  <span class="label label-warning">${notifications.size()}</span>
                </a>
                <ul class="dropdown-menu">
                  <li class="header">You have ${notifications.size()} new notifications</li>
                  <li>
                    <!-- Inner Menu: contains the notifications -->
                    <ul class="menu">
                     <c:forEach items="${notifications}" var="notificationObject">
                      <li><!-- start notification -->
                        <a href="${notificationObject.getRoute()}">
                            <i class="fa fa-users text-aqua"></i> <c:out value="${notificationObject.getTitle()}"></c:out>
                        </a>
                      </li><!-- end notification -->
                     </c:forEach>
                    </ul>
                  </li>
                  <li class="footer"><a href="#">View all</a></li>
                </ul>
              </li>
              
              
              <!-- User Account Menu -->
              <li class="dropdown user user-menu">
                <!-- Menu Toggle Button -->
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <!-- The user image in the navbar-->
                  <img src="images/img/boxed-bg.jpg" class="user-image" alt="User Image">
                  <span class="hidden-xs">${user.firstname} ${user.lastname}</span>
                </a>
                <ul class="dropdown-menu">
                  <!-- The user image in the menu -->
                  <li class="user-header">
                    <img src="images/img/boxed-bg.jpg" class="img-circle" alt="User Image">
                    <p>
                      ${user.firstname} ${user.lastname}
                      <small>Member since Nov. 2012</small>
                    </p>
                  </li>
                  <!-- Menu Body -->
<!--                  <li class="user-body">
                    <div class="col-xs-4 text-center">
                      <a href="#">Followers</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="#">Sales</a>
                    </div>
                    <div class="col-xs-4 text-center">
                      <a href="#">Friends</a>
                    </div>
                  </li>-->
                  <!-- Menu Footer-->
                  <li class="user-footer">
                    <div class="pull-left">
                      <a href="#" class="btn btn-default btn-flat">Profile</a>
                    </div>
                    <div class="pull-right">
                      <a href="${pageContext.request.contextPath}/Login?action=logout" class="btn btn-default btn-flat">Sign out</a>
                    </div>
                  </li>
                </ul>
              </li>
              <!-- Control Sidebar Toggle Button -->
              <li>
                <a href="#" data-toggle="control-sidebar"><i class="fa fa-gears"></i></a>
              </li>
            </ul>
          </div>
        </nav>
      </header>