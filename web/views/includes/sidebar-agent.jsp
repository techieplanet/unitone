


              
                <!-- Admin Side Menu -->
                <ul class="sidebar-menu">
                  <li class="header"></li>
                  <!-- Optionally, you can add icons to the links -->
                  
                  <li class="active"><a href="#"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
                  <li class=""><a href="${pageContext.request.contextPath}/Project"><i class="fa fa-home"></i> <span>Projects</span><small class="label pull-right bg-yellow">2</small></a></li>
                  
                  

                  <!--CUSTOMER-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_customer')}">
                    <li class="treeview">
                          <a href="${pageContext.request.contextPath}/Customer"><i class="fa fa-user-plus"></i><span>Customers</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Customer">All Customers</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_customer')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=new">New Customer</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=new">Make Lodgement</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=new">Currently Paying</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=new">Completed Payment</a></li>
                            </c:if>
                          </ul>
                      </li>
                  </c:if>
                  
                  
                  <!--AGENT-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_project')}">
                        <li class="treeview">
                          <a href="#"><i class="fa fa-users"></i><span>My Wallet</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Agent">Current Balance</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=new">Credit History</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=wallet">Withdrawal History</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=wallet">Withdrawal Funds</a></li>
                          </ul>
                      </li>
                  </c:if>
                      
                      
                  <!--ORDERS-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                        <li class="treeview">
                          <a href="#"><i class="fa fa-users"></i><span>Orders</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Order">All Orders</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=current">Current Orders</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=completed">Completed Orders</a></li>
                            </c:if>
                          </ul>
                        </li>
                  </c:if>
                        
                        
                
                  <!--ANNOUNCEMENT-->
                  <li class="treeview">
                      <a href="#"><i class="fa fa-microphone"></i><span>Announcements</span><i class="fa fa-angle-left pull-right"></i></a>
                      <ul class="treeview-menu">
                        <li><a href="${pageContext.request.contextPath}/Project">View</a></li>
                        <li><a href="${pageContext.request.contextPath}/Project?action=new">Create</a></li>
                      </ul>
                  </li>
                  
                 
                    
                    <li class=""><a href="${pageContext.request.contextPath}/User"><i class="fa fa-users"></i> <span>My Profile</span></a></li>
                    
                </ul><!-- /.sidebar-menu -->
  