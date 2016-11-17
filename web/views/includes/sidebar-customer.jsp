



 <!-- Admin Side Menu -->
    <ul class="sidebar-menu">
      <li class="header"></li>
      <!-- Optionally, you can add icons to the links -->

      <li class="active"><a href="#"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li>
      <li class=""><a href="${pageContext.request.contextPath}/Project?action=listprojects"><i class="fa fa-home"></i> <span>Projects</span></a></li>
      
      <!--ORDERS-->
                  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">
                        <li class="treeview">
                          <a href="#"><i class="fa fa-users"></i><span>Orders</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Order">All Orders</a></li>
                            <li><a href="${pageContext.request.contextPath}/Order?action=new">New Order</a></li>
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
                            
                 
                  <!--LODGMENT-->
                  <li class="treeview">
                      <a href="#"><i class="fa fa-money"></i><span>Lodgment</span><i class="fa fa-angle-left pull-right"></i></a>
                      <ul class="treeview-menu">
                        <li><a href="${pageContext.request.contextPath}/Lodgement">All</a></li>
                        <li><a href="${pageContext.request.contextPath}/Lodgement?action=new">Make lodgment</a></li>
                        <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_approved">Approved Lodgment</a></li>
                        <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_unapproved">Declined Lodgment</a></li>
                        <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_pending">Pending Lodgment</a></li>
                      </ul>
                  </li>
                  
                  <!-- Customer -->
                  <li class=""><a href="${pageContext.request.contextPath}/Customer?action=edit&customerId=${sessionScope.user.getSystemUserId()}"><i class="fa fa-users"></i> <span>My Profile</span></a></li>
                  
                  
      </ul><!-- /.sidebar-menu -->