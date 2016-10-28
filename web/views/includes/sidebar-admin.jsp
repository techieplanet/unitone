

              
                <!-- Admin Side Menu -->
                <ul class="sidebar-menu">
                  <li class="header"></li>
                  <!-- Optionally, you can add icons to the links -->
                  
                  <li class="active"><a href="#"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
                  
                  <!--PROJECT-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_project')}">
                      <li class="treeview">
                          <a href="#"><i class="fa fa-home"></i><span>Projects</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Project">All Projects</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_project')}">
                                <li><a href="${pageContext.request.contextPath}/Project?action=new">New Project</a></li>
                            </c:if>
                          </ul>
                      </li>
                  </c:if>
                      
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
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')}">
                        <li class="treeview">
                          <a href="#"><i class="fa fa-users"></i><span>Agents</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Agent">All Agents</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_agent')}">
                                <li><a href="${pageContext.request.contextPath}/Agent?action=new">New Agent</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_wallet')}">
                                <li><a href="${pageContext.request.contextPath}/Agent?action=wallet">Agents Wallets</a></li>
                            </c:if>
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
                        
                        
                  <!--ANNOUNCEMENTS-->
                  <%--<c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">--%>
                        <li class="treeview">
                          <a href="#"><i class="fa fa-users"></i><span>Orders</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Order">All Orders</a></li>
                            <%--<c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">--%>
                                <li><a href="${pageContext.request.contextPath}/Order?action=current">Current Orders</a></li>
                            <%--</c:if>--%>
                            <%--<c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">--%>
                                <li><a href="${pageContext.request.contextPath}/Order?action=completed">Completed Orders</a></li>
                            <%--</c:if>--%>
                          </ul>
                        </li>
                  <%--</c:if>--%>
                
                  
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_role')}">
                    <li class=""><a href="${pageContext.request.contextPath}/Role"><i class="fa fa-user"></i> <span>Roles</span></a></li>
                  </c:if>
                    
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_user')}">
                    <li class=""><a href="${pageContext.request.contextPath}/User"><i class="fa fa-users"></i> <span>Users</span></a></li>
                  </c:if>
                    
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_user')}">
                    <li class=""><a href="${pageContext.request.contextPath}/User"><i class="fa fa-users"></i> <span>My Profile</span></a></li>
                  </c:if>
                    
                    
                  <li class=""><a href="#"><i class="fa fa-microphone"></i> <span>Announcements</span><small class="label pull-right bg-yellow">2</small></a></li>
                </ul><!-- /.sidebar-menu -->