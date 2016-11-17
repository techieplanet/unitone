 <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
              
              
              <c:if test="${unit_cart != null}">
                  
                <li class="dropdown notifications-menu">
                
                <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  <i class="fa fa-cart-plus fa-2x"></i>
                  <span class="label label-success">${unit_cart.size()}</span>
                </a>
                <ul class="dropdown-menu">
                    <li class="header"><span>You have ${unit_cart.size()} item(s) in cart </span>
                    <a href="#" class="btn btn-success btn-sm pull-right" style="color:#fff">Check out</a>
                    </li>
                  <li>
                    <!-- Inner Menu: contains the notifications -->
                    <ul class="menu list-group">
                     <c:set var="cart_total" value="0.0" />   
                     <c:forEach items="${unit_cart}" var="cart">
                      <li><!-- start notification -->
                          <a class="list-group-item">
                          <span>${cart.getTitle()}</span><br />
                          <span><fmt:formatNumber value="${cart.getCpu()}" type="currency" currencySymbol="N" /></span>
                          </a>
                      </li><!-- end notification -->
                      <c:set var="cart_total" value="${cart_total + cart.getCpu()}" /> 
                     </c:forEach>
                    </ul>
                  </li>
                  <li class="header"><span>Total : </span> <fmt:formatNumber value="${cart_total}" currencySymbol="N" type="currency" /></li>
                </ul>
              </li>
                  
              </c:if>
              
              <!-- Notifications Menu -->
              <c:if test="${loggedInUser.getSystemUserTypeId() == 1}">
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
                            <c:if test='${notificationObject.getType().getAlias() == "ALERT_NEW_ORDER"}'>
                                <i class="fa fa-cart-plus text-aqua"></i> <c:out value="${notificationObject.getTitle()}"></c:out>
                            </c:if>
                            <c:if test='${notificationObject.getType().getAlias() == "ALERT_NEW_LODGE"}'>
                                <i class="fa fa-money text-green"></i> <c:out value="${notificationObject.getTitle()}"></c:out>
                            </c:if>
                            
                        </a>
                      </li><!-- end notification -->
                     </c:forEach>
                    </ul>
                  </li>
                
                </ul>
              </li>
              </c:if>
              
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