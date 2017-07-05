


              
                <!-- Admin Side Menu -->
                <ul class="sidebar-menu">
                  <li class="header"></li>
                  <!-- Optionally, you can add icons to the links -->
                  
                  <li class="<c:out  value='${sideNav eq "Dashboard" ? "active":""}' />""><a href="${pageContext.request.contextPath}/Dashboard"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
                  <li class="<c:out  value='${sideNav eq "Project" ? "active":""}' />""><a href="${pageContext.request.contextPath}/Project?action=listprojects"><i class="fa fa-home"></i> <span>Projects</span></a></li>
                  
                  

                  <!--CUSTOMER-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_customer')}">
                    <li class="treeview <c:out  value='${sideNav eq "Customer" ? "active":""}' />">
                          <a href="${pageContext.request.contextPath}/Customer"><i class="fa fa-user-plus"></i><span>Customers</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Customer" <c:if test="${sideNavAction eq '' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if>>All Customers</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_customer')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if> >New Customer</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_customer')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=new_prospect" <c:if test="${sideNavAction eq 'new_prospect' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if> >New Prospect</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'create_customer')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=list_prospects" <c:if test="${sideNavAction eq 'list_prospects' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if> >Prospects List</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=current" <c:if test="${sideNavAction eq 'current' && sideNav eq 'Customer'}"><c:out value='style=color:#fff' /></c:if> >Currently Paying</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Customer?action=completed"  <c:if test="${sideNavAction eq 'completed' && sideNav eq 'Customer'}"> <c:out value='style=color:#fff' /></c:if>>Completed Payment</a></li>
                            </c:if>
                          </ul>
                      </li>
                  </c:if>
                  
                  
                  <!--AGENT-->
                  <c:if test="${sessionScope.user.getSystemUserTypeId() == 2}">
                        <li class="treeview <c:out  value='${sideNav eq "Agent" ? "active":""}' />">
                          <a href="#"><i class="fa fa-users"></i><span>My Wallet</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Agent?action=account_statement" <c:if test="${sideNavAction eq 'account_statement' && sideNav eq 'Agent'}"> <c:out value='style=color:#fff' /></c:if>>Account Statement</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=credit_history" <c:if test="${sideNavAction eq 'credit_history' && sideNav eq 'Agent'}"> <c:out value='style=color:#fff' /></c:if> >Credit History</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=debit_history" <c:if test="${sideNavAction eq 'debit_history' && sideNav eq 'Agent'}"> <c:out value='style=color:#fff' /></c:if> >Withdrawal History</a></li>
                            <li><a href="${pageContext.request.contextPath}/Agent?action=withdrawal" <c:if test="${sideNavAction eq 'withdrawal' && sideNav eq 'Agent'}"> <c:out value='style=color:#fff' /></c:if>>Withdraw Funds</a></li>
                          </ul>
                      </li>
                  </c:if>
                      
                      
                  <!--ORDERS-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                        <li class="treeview <c:out  value='${sideNav eq "Order" ? "active":""}' />">
                          <a href="#"><i class="fa fa-users"></i><span>Orders</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Order" <c:if test="${sideNavAction eq '' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if>>All Orders</a></li>
                            
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Order?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >New Order</a></li>
                            </c:if>
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
                            <li><a href="${pageContext.request.contextPath}/Lodgement" <c:if test="${sideNavAction eq '' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if>>All</a></li>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_pending" <c:if test="${sideNavAction eq 'list_pending' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if>>Pending Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Make Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_approved" <c:if test="${sideNavAction eq 'list_approved' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Approved Lodgment</a></li>
                            </c:if>
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                                <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_unapproved" <c:if test="${sideNavAction eq 'list_unapproved' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if>  >Declined Lodgment</a></li>
                            </c:if>
                          </ul>
                        </li>
                  </c:if>     
                
                  <!--MESSAGE-->
                  <li class="treeview <c:out value='${sideNav eq "Message" ? "active":""}' /> ">
                      <a href="#"><i class="fa fa-envelope"></i><span>Messages</span><i class="fa fa-angle-left pull-right"></i></a>
                      <ul class="treeview-menu">
                        <li><a href="${pageContext.request.contextPath}/Message?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Message'}"> <c:out value='style=color:#fff' /></c:if> >New</a></li>
                        <li><a href="${pageContext.request.contextPath}/Message?action=mailbox" <c:if test="${sideNavAction eq 'mailbox' && sideNav eq 'Message'}"> <c:out value='style=color:#fff' /></c:if> >MailBox</a></li>
                      </ul>
                  </li>
                            
                  <!--ANNOUNCEMENT-->
                  <li class="treeview">
                      <a href="#"><i class="fa fa-microphone"></i><span>Announcements</span><i class="fa fa-angle-left pull-right"></i></a>
                      <ul class="treeview-menu">
                        <li><a href="${pageContext.request.contextPath}/Project" <c:if test="${sideNavAction eq '' && sideNav eq 'Announcement'}"> <c:out value='style=color:#fff' /></c:if> >View</a></li>
                        <%/**<li><a href="${pageContext.request.contextPath}/Project?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Announcement'}"> <c:out value='style=color:#fff' /></c:if> >Create</a></li> */%>
                      </ul>
                  </li>
                  
                 
                    
                    <li class="<c:out  value='${sideNav eq "profile" ? "active":""}' />"><a href="${pageContext.request.contextPath}/Agent?action=profile&id=${sessionScope.user.getSystemUserId()}"><i class="fa fa-users"></i> <span>My Profile</span></a></li>
                    
                </ul><!-- /.sidebar-menu -->
  