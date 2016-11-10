<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   


<div class="content-wrapper">
    
    
    <section class="content-header">
        
        <h1>
            <a href="#">Agent Credit History</a>
        </h1>
        
    </section>
    
    
    <div class="content">
        <div class="box">
            
            <div class="box-header with-border"> 
                <div class="callout callout-success" style="margin-bottom: 0;">
                    <h4 style="margin-bottom: 0;">Credit History</h4>
                </div>
     
            </div>
            
            <div class="box-body">
               
                <table class="table table-hover table-striped" id="credit_history_table">
                    <thead>
                        <tr>
                            <th>S/N</th>
                            <th>ID</th>
                            <th>Amount</th>
                            <th>Date</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${transactions}" var="transaction" varStatus="cursor">
                            <tr>
                                <td>${cursor.count}</td>
                                <td>${transaction.getId()}</td>
                                <td><fmt:formatNumber type="currency" currencySymbol="N" maxFractionDigits="2" value="${transaction.getAmount()}" /></td>
                                <td><fmt:formatDate value="${transaction.getTransactionDate()}" type="both" /></td>
                            </tr>
                        </c:forEach>
                    </tbody>
                    <tfoot>
                        <tr>
                            <th>S/N</th>
                            <th>ID</th>
                            <th>Amount</th>
                            <th>Date</th>
                        </tr>
                    </tfoot>
                </table>
                
            </div>
        </div>
        
    </div>
    
    <input type="hidden" id="agent_id" value="${agent.getAgentId()}" />
    <input type="hidden" id="contextPath" value="${pageContext.request.contextPath}" />
</div>


<div class="modal fade" id="withdrawSuccessModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
         <div class="vertical-alignment-helper">
          <div class="modal-dialog vertical-align-center">
              <div class="modal-content">
                <div class="modal-header">
                  <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                  <h4 class="modal-title">Agent Withdrawal Request </h4>
                </div>

                <div class="modal-body">
                    <p>Your withdrawal request is successful</p>
                </div>
              </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
</div>

<div class="modal fade" id="withdrawLoading" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
         <div class="vertical-alignment-helper">
          <div class="modal-dialog vertical-align-center">
              <div class="modal-content">
                <div class="modal-body text-center padding10">
                    <p>Requesting Withdrawal...</p>
                    <img src="${pageContext.request.contextPath}/images/uploadProgress.gif" />
                </div>
              </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
</div>


<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


<script>

    $(function(){
        
    
     $("#credit_history_table").DataTable();
        
     })


</script>