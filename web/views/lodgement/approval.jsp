<%-- 
    Document   : approval
    Created on : Oct 14, 2016, 5:28:33 PM
    Author     : Prestige
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   


<div class="content-wrapper">
    
    <section class="content-header">
        <h1>
            Lodgement
        </h1>
    </section>
    
    <section class="content">
        
         <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
              
              <c:forEach items="${lodgements}" var="lodgement" varStatus="lodgementCount">
                  <form action="${pageContext.request.contextPath}/Lodgement?action=approveLodgement" method="post">      
                  <div class="panel panel-default"   >
                            <div class="panel-heading" style="height:40px; background-color: #357CA5 !important;" role="tab" id="heading${lodgementCount.count}">
                              <h4 class="panel-title">
                                <a role="button" style="display: block;color:#fff !important;" data-toggle="collapse" data-parent="#accordion" href="#collapse${lodgementCount.count}"  aria-controls="collapse${lodgementCount.count}">
                                    <span>Lodgement id : </span>${lodgement.getId()} 
                                    
                                </a>
                               
                              </h4>
                                
                            </div>
                                    <!-- Check if request is from notification click, 
                                    Then open and scroll to lodgement panel by default -->
                                    
                            <div id="collapse${lodgementCount.count}" class="panel-collapse collapse 
                                         <c:if test="${notificationLodgementId == 0}"> 
                                             <c:out value='${lodgementCount.count == 1?"in":""}' />
                                         </c:if>
                                         <c:if test="${notificationLodgementId > 0 && lodgement.getId() == notificationLodgementId}"> 
                                             <c:out value='in scrollHere' />
                                         </c:if>
                                         " 
                                role="tabpanel" aria-labelledby="heading${lodgementCount.count}" >
                                         
                              <div class="panel-body">
                                  
                                  <c:if test="${lodgement.getPaymentMode() == 1}">
                                      <div class="row">
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Payment Mode : </span>
                                          </div>
                                          <div class="col-md-9">
                                              <span>BANK DEPOSIT</span>
                                          </div>
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Depositors Name : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getDepositorName()}</span>
                                          </div>
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Amount : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getAmount()}</span>
                                          </div>
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Teller No : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getTransactionId()}</span>
                                          </div>
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Date : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getLodgmentDate()}</span>
                                          </div>

                                      </div>
                                  </c:if> 
                                  
                                  <c:if test="${lodgement.getPaymentMode() == 3}">
                                      <div class="row">

                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Payment Mode : </span>
                                          </div>
                                          <div class="col-md-9">
                                              <span>CASH</span>
                                          </div>

                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Amount : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getAmount()}</span>
                                          </div>

                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Date : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getLodgmentDate()}</span>
                                          </div>

                                      </div>
                                  </c:if> 
                                  
                                  <c:if test="${lodgement.getPaymentMode() == 4}">
                                      <div class="row">
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Payment Mode : </span>
                                          </div>
                                          <div class="col-md-9">
                                              <span>BANK TRANSFER</span>
                                          </div>
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Customer Account Name : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getOriginAccountName()}</span>
                                          </div>
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Customer Account Number : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getOriginAccountNumber()}</span>
                                          </div>
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Amount : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getAmount()}</span>
                                          </div>
                                          
                                          
                                          <div class="col-md-3">
                                              <span class="lodgementTitleSpan">Date : </span> 
                                          </div>
                                          <div class="col-md-9">
                                              <span>${lodgement.getLodgmentDate()}</span>
                                          </div>
                                          
                                          
                                      </div>
                                  </c:if> 
                                  
                                  <div class="col-md-12 margintop10">
                                      <a href="${pageContext.request.contextPath}/Lodgement" class="btn btn-success">Approve</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                              <a href="${pageContext.request.contextPath}/Lodgement" class="btn btn-danger">Decline</a>
                                  </div>
                                  
                              </div> 
                            </div>
                  </div>
                  </form>
              </c:forEach>     
            
              
          </div>
        
    </section>
    
</div>


<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
