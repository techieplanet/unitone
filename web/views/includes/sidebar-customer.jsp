



 <!-- Admin Side Menu -->
    <ul class="sidebar-menu">
      <li class="header"></li>
      <!-- Optionally, you can add icons to the links -->

     <%/** <li class="<c:out  value='${sideNav eq "Dashboard" ? "active":""}' />"><a href="${pageContext.request.contextPath}/Dashboard"><i class="fa fa-tachometer"></i> <span>Dashboard</span></a></li> */%>
      <li class="<c:out  value='${sideNav eq "Project" ? "active":""}' />"><a href="${pageContext.request.contextPath}/Project?action=listprojects"><i class="fa fa-home"></i> <span>Projects</span></a></li>
      
      <!--ORDERS-->
                 <%// <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">%>
                        <li class="treeview <c:out  value='${sideNav eq "Order" ? "active":""}' />">
                          <a href="#"><i class="fa fa-users"></i><span>Orders</span><i class="fa fa-angle-left pull-right"></i></a>
                          <ul class="treeview-menu">
                            <li><a href="${pageContext.request.contextPath}/Order" <c:if test="${sideNavAction eq '' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >All Orders</a></li>
                            <li><a href="${pageContext.request.contextPath}/Order?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >New Order</a></li>
                          <%//  <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">%>
                                <li><a href="${pageContext.request.contextPath}/Order?action=approved" <c:if test="${sideNavAction eq 'approved' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Approved</a></li>
                           <%// </c:if>%>
                           <%// <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">%>
                                <li><a href="${pageContext.request.contextPath}/Order?action=declined" <c:if test="${sideNavAction eq 'declined' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Declined</a></li>
                           <%// </c:if>%>
                           <%// <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">%>
                                <li><a href="${pageContext.request.contextPath}/Order?action=processing" <c:if test="${sideNavAction eq 'processing' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Processing</a></li>
                           <%// </c:if>%>
                           <%// <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">%>
                                <li><a href="${pageContext.request.contextPath}/Order?action=current" <c:if test="${sideNavAction eq 'current' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Ongoing Payments</a></li>
                           <%// </c:if>%>
                           <%// <c:if test="${fn:contains(sessionScope.user.permissions, 'view_order')}">%>
                                <li><a href="${pageContext.request.contextPath}/Order?action=completed"  <c:if test="${sideNavAction eq 'completed' && sideNav eq 'Order'}"> <c:out value='style=color:#fff' /></c:if> >Completed Payments</a></li>
                           <%// </c:if>%>
                          </ul>
                        </li>
                 <%// </c:if>%>
                            
                 
                  <!--LODGMENT-->
                  <li class="treeview <c:out  value='${sideNav eq "Lodgement" ? "active":""}' />">
                      <a href="#"><i class="fa fa-money"></i><span>Lodgment</span><i class="fa fa-angle-left pull-right"></i></a>
                      <ul class="treeview-menu">
                        <li><a href="${pageContext.request.contextPath}/Lodgement" <c:if test="${sideNavAction eq '' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >All</a></li>
                        <li><a href="${pageContext.request.contextPath}/Lodgement?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Make lodgment</a></li>
                        <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_approved" <c:if test="${sideNavAction eq 'list_approved' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Approved Lodgment</a></li>
                        <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_unapproved" <c:if test="${sideNavAction eq 'list_unapproved' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Declined Lodgment</a></li>
                        <li><a href="${pageContext.request.contextPath}/Lodgement?action=list_pending" <c:if test="${sideNavAction eq 'list_pending' && sideNav eq 'Lodgement'}"> <c:out value='style=color:#fff' /></c:if> >Pending Lodgment</a></li>
                      </ul>
                  </li>
                  
                  
                  <!--MESSAGE-->
                  <%
                 // <li class="treeview <c:out value='${sideNav eq "Message" ? "active":""}' /> ">
                 //     <a href="#"><i class="fa fa-envelope"></i><span>Messages</span><i class="fa fa-angle-left pull-right"></i></a>
                 //     <ul class="treeview-menu">
                 //       <li><a href="${pageContext.request.contextPath}/Message?action=new" <c:if test="${sideNavAction eq 'new' && sideNav eq 'Message'}"> <c:out value='style=color:#fff' /></c:if> >New</a></li>
                 //       <li><a href="${pageContext.request.contextPath}/Message?action=mailbox" <c:if test="${sideNavAction eq 'mailbox' && sideNav eq 'Message'}"> <c:out value='style=color:#fff' /></c:if> >MailBox</a></li>
                 //     </ul>
                 // </li>
                  %>
                  
                  <!-- Customer -->
                  <li class="<c:out  value='${sideNav eq "Customer" ? "active":""}' />"><a href="${pageContext.request.contextPath}/Customer?action=profile&customerId=${sessionScope.user.getSystemUserId()}"><i class="fa fa-users"></i> <span>My Profile</span></a></li>
                  
                  
      </ul><!-- /.sidebar-menu -->