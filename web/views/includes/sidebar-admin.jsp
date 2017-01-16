

              
                <!-- Admin Side Menu -->
                <ul class="sidebar-menu">
                  <li class="header"></li>
                  <!-- Optionally, you can add icons to the links -->
                  
                  <li class="<c:out  value='${sideNav eq "Dashboard" ? "active":""}' />"><a href="${pageContext.request.contextPath}/Dashboard"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
                  
                  <!--PROJECT-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_project')}">
                      <li class="treeview <c:out  value='${sideNav eq "Project" ? "active":""}' />"  >
                          <a href="#"><i class="fa fa-home"></i><span>Projects</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Project">All Projects</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_project')}">
                                <li><a href="${pageContext.request.contextPath}/Project?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Project'}"> <c:out value='style=color:#fff' /></c:if> >New Project</a></li>
                            </c:if>
                          </ul>
                      </li>
                  </c:if>
                      
                    <!--CUSTOMER-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_customer')}">
                    <li class="treeview <c:out value='${sideNav eq "Customer" ? "active":""}' />" >
                          <a href="${pageContext.request.contextPath}/Customer"><i class="fa fa-user-plus"></i><span>Customers</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Customer" <c:if test="${sideNavAction eq '' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if> >All Customers</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_customer')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if> >New Customer</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=current" <c:if test="${sideNavAction eq 'current' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if>>Currently Paying</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=completed" <c:if test="${sideNavAction eq 'completed' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if> >Completed Payment</a></li>
                            </c:if>
                          </ul>
                      </li>
                  </c:if>
                  
                  
                  <!--AGENT-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent')}">
                        <li class="treeview <c:out value='${sideNav eq "Agent" ? "active":""}' />" >
                          <a href="#"><i class="fa fa-users"></i><span>Agents</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Agent" <c:if test="${sideNavAction eq '' && sideNav eq 'Agent'}"> <c:out value='style=color:#fff' /></c:if>>Approved Agents</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_agent')}">
                                <li><a href="${pageContext.request.contextPath}/Agent?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Agent'}"> <c:out value='style=color:#fff' /></c:if> >New Agent</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_wallet')}">
                                <li><a href="${pageContext.request.contextPath}/Agent?action=withdrawApproval" <c:if test="${sideNavAction eq 'withdrawApproval' && sideNav eq 'Agent'}"> <c:out value='style=color:#fff' /></c:if> >Pending Withdrawal Request</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_wallet')}">
                                <li><a href="${pageContext.request.contextPath}/Agent?action=approvedWithdrawal" <c:if test="${sideNavAction eq 'approvedWithdrawal' && sideNav eq 'Agent'}"> <c:out value='style=color:#fff' /></c:if> >Approved Withdrawal Request</a></li>
                            </c:if>
                          </ul>
                      </li>
                  </c:if>
                      
                      
                  <!--ORDERS-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                        <li class="treeview <c:out value='${sideNav eq "Order" ? "active":""}' />" >
                          <a href="#"><i class="fa fa-users"></i><span>Orders</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Order" <c:if test="${sideNavAction eq '' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >All Orders</a></li>
                            <li><a href="${pageContext.request.contextPath}/Order?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if>>New Order</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=approved" <c:if test="${sideNavAction eq 'approved' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Approved</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=declined" <c:if test="${sideNavAction eq 'declined' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Declined</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=processing" <c:if test="${sideNavAction eq 'processing' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Processing</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=current" <c:if test="${sideNavAction eq 'current' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Ongoing Payments</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=completed" <c:if test="${sideNavAction eq 'completed' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Completed Payments</a></li>
                            </c:if>
                          </ul>
                        </li>
                  </c:if>
                            
                            
                  <!--Lodgement-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                        <li class="treeview <c:out value='${sideNav eq "Lodgement" ? "active":""}' />" >
                          <a href="#"><i class="fa fa-users"></i><span>Lodgement</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Lodgement" <c:if test="${sideNavAction eq '' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >All</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=approval" <c:if test="${sideNavAction eq 'approval' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Pending Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Make Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_approved" <c:if test="${sideNavAction eq 'list_approved' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Approved Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_unapproved" <c:if test="${sideNavAction eq 'list_unapproved' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Declined Lodgment</a></li>
                            </c:if>
                          </ul>
                        </li>
                  </c:if>
                        
                  
                
                  
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_role')}">
                    <li class="<c:out value='${sideNav eq "Role" ? "active":""}' />" ><a href="${pageContext.request.contextPath}/Role"><i class="fa fa-user"></i> <span>Roles</span></a></li>
                  </c:if>
                    
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_user')}">
                    <li class="<c:out value='${sideNav eq "User" ? "active":""}' />" ><a href="${pageContext.request.contextPath}/User"><i class="fa fa-users"></i> <span>Users</span></a></li>
                  </c:if>
                  
                  
                  
                  <!--Settings-->
<!--                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_settings')}">
                        <li class="treeview <c:out value='${sideNav eq "Settings" ? "active":""}' />" >
                          <a href="#"><i class="fa fa-users"></i><span>Settings</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">                            
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_company_account')}">
                                <li><a href="${pageContext.request.contextPath}/CompanyAccount" <c:if test="${sideNavAction eq 'approval' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Company Accounts</a></li>
                            </c:if>
                          </ul>
                        </li>
                  </c:if>-->
                            
                    
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_user')}">
                      <li class="<c:out value='${sideNav eq "Profile" ? "active":""}' />"><a href="${pageContext.request.contextPath}/User?action=profile&id=${sessionScope.user.getSystemUserId()}"><i class="fa fa-users"></i> <span>My Profile</span></a></li>
                  </c:if>
                  
                    
                  <!--<li class=""><a href="#"><i class="fa fa-microphone"></i> <span>Announcements</span><small class="label pull-right bg-yellow">2</small></a></li>-->
                </ul><!-- /.sidebar-menu -->