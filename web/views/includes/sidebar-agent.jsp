


              
                <!-- Admin Side Menu -->
                <ul class="sidebar-menu">
                  <li class="header"></li>
                  <!-- Optionally, you can add icons to the links -->
                  
                  <li class="<c:out  value='${sideNav eq "Dashboard" ? "active":""}' />""><a href="#"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
                  <li class="<c:out  value='${sideNav eq "Project" ? "active":""}' />""><a href="${pageContext.request.contextPath}/Project"><i class="fa fa-home"></i> <span>Projects</span></a></li>
                  
                  

                  <!--CUSTOMER-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_customer')}">
                    <li class="treeview <c:out  value='${sideNav eq "Customer" ? "active":""}' />">
                          <a href="${pageContext.request.contextPath}/Customer"><i class="fa fa-user-plus"></i><span>Customers</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Customer">All Customers</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_customer')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=new">New Customer</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=current">Currently Paying</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=completed">Completed Payment</a></li>
                            </c:if>
                          </ul>
                      </li>
                  </c:if>
                  
                  
                  <!--AGENT-->
                  <c:if test="${sessionScope.user.getSystemUserTypeId() == 2}">
                        <li class="treeview <c:out  value='${sideNav eq "Agent" ? "active":""}' />">
                          <a href="#"><i class="fa fa-users"></i><span>My Wallet</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Agent?action=account_statement">Account Statement</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=credit_history">Credit History</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=debit_history">Withdrawal History</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=withdrawal">Withdraw Funds</a></li>
                          </ul>
                      </li>
                  </c:if>
                      
                      
                  <!--ORDERS-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                        <li class="treeview <c:out  value='${sideNav eq "Order" ? "active":""}' />">
                          <a href="#"><i class="fa fa-users"></i><span>Orders</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Order">All Orders</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=new">New Order</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=approved">Approved</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=declined">Declined</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=processing">Processing</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=current">Ongoing Payments</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=completed">Completed Payments</a></li>
                            </c:if>
                          </ul>
                        </li>
                  </c:if>
                        
                   <!--Lodgement-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                        <li class="treeview <c:out value='${sideNav eq "Lodgement" ? "active":""}' />" >
                          <a href="#"><i class="fa fa-users"></i><span>Lodgement</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Lodgement">All</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_pending">Pending Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=new">Make Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_approved">Approved Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_unapproved">Declined Lodgment</a></li>
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
                  
                 
                    
                    <li class="<c:out  value='${sideNav eq "profile" ? "active":""}' />"><a href="${pageContext.request.contextPath}/Agent?action=profile&id=${sessionScope.user.getSystemUserId()}"><i class="fa fa-users"></i> <span>My Profile</span></a></li>
                    
                </ul><!-- /.sidebar-menu -->
  