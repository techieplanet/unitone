<%-- 
    Document   : success
    Created on : Oct 18, 2016, 3:03:57 PM
    Author     : Prestige
--%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Mortgage Payment Confirmation
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
            <div class='box'>
                <div class="box-header">
                    <h3 class='box-title'>Confirmation</h3>
                </div>
                <div class='box-body'>
                    <p>Congratulation!!! Your Lodgement for the sum of <fmt:formatNumber value='${sessionScope.invoice.get("total")}' type="currency" currencySymbol="N" /> was successful</p>
                    <h4>List of Items</h4>
                    <ul>
                        <c:forEach items="${sessionScope.invoice.get('items')}" var="item">
                            <li>${item.get("title")} - <fmt:formatNumber value='${item.get("amount")}' type="currency" currencySymbol="N" /></li>
                        </c:forEach>
                    </ul>
                    <c:remove var="invoice" scope="session" />
                </div>
                <div class="box-footer">
                    <a href="${pageContext.request.contextPath}/Lodgement?action=new" class="btn btn-primary">New Lodgement</a>
                </div>
            </div>
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <!--MODAL-->
      <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE</h4>
            </div>
            <div class="modal-body">
              <p>Are you sure you want to delete?</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="" class="btn btn-primary">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->

      
<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


<script>
        
 </script>       
          
          


