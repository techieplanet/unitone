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
            Lodgement Approval
        </h1>
    </section>
    
    <section class="content">
        
            
            <c:if test="${fn:length(errors) > 0 }">
                <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-danger padding10" style="width:100%; margin:0 auto !important">
                          <c:forEach items="${errors}" var="error">
                              <c:out value="${error.value}" /><br/>
                          </c:forEach>
                        </p>
                    </div>
                </div>
            </c:if>
        
        
        <div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Lodgement Approval</h4>
            </div>
            <div class="modal-body">
                Lodgement Approval was Successful
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary pull-right" data-dismiss="modal">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->   
         
        <c:if test="${success}">
            <script>
               $( function(){ $("#successModal").modal(); }); 
                </script>
        <c:remove scope="session" var="success"  />
        </c:if> 
        
        
         <div class="panel-group" id="accordion2" role="tablist" aria-multiselectable="true">
              
              <c:forEach items="${lodgements}" var="lodgement" varStatus="lodgementCount">
                  <form action="${pageContext.request.contextPath}/Lodgement?action=approveLodgement" method="post">      
                  <div class="panel panel-default" id="panel${lodgementCount.count}"   >
                            <div class="panel-heading" style="height:40px; background-color: #357CA5 !important;" role="tab" id="heading${lodgementCount.count}">
                              <h4 class="panel-title">
                                <a role="button" style="display: block;color:#fff !important;" data-toggle="collapse" data-parent="#accordion2" href="#collapse${lodgementCount.count}"  aria-controls="collapse${lodgementCount.count}">
                                    <span>Lodgement id : </span>${lodgement.getId()} 
                                    
                                </a>
                               
                              </h4>
                                
                            </div>
                                    <!-- Check if request is from notification click, 
                                    Then open and scroll to lodgement panel by default -->
                                    
                            <div id="collapse${lodgementCount.count}" class="collapse  panel-collapse 
                                         <c:if test="${notificationLodgementId == 0}"> 
                                             <c:out value='${lodgementCount.count == 1?"in":""}' />
                                         </c:if>
                                         <c:if test="${notificationLodgementId > 0 && lodgement.getId() == notificationLodgementId}"> 
                                             <c:out value='in scrollHere' />
                                         </c:if>
                                         " 
                                role="tabpanel" aria-labelledby="heading${lodgementCount.count}" >
                                         
                              <div class="panel-body">
                                  
                                  <div class="row">
                                      <div class="col-md-6">
                                          <c:if test="${lodgement.getPaymentMode() == 1}">
                                              <div class="row">

                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Payment Mode : </span>
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>BANK DEPOSIT</span>
                                                  </div>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Depositors Name : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getDepositorName()}</span>
                                                  </div>

                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <c:if test="${lodgement.getRewardAmount() != 0}">
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Royalty Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getRewardAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Total Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getRewardAmount() + lodgement.getAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  </c:if>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Teller No : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getTransactionId()}</span>
                                                  </div>

                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Date : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getLodgmentDate()}</span>
                                                  </div>

                                              </div>
                                          </c:if> 
                                          
                                          <c:if test="${lodgement.getPaymentMode() == 2}">
                                              <div class="row">

                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Payment Mode : </span>
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>Card</span>
                                                  </div>

                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <c:if test="${lodgement.getRewardAmount() != 0}">
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Royalty Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getRewardAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Total Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getRewardAmount() + lodgement.getAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  </c:if>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Date : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getLodgmentDate()}</span>
                                                  </div>

                                              </div>
                                          </c:if> 
                                          
                                          <c:if test="${lodgement.getPaymentMode() == 3}">
                                              <div class="row">

                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Payment Mode : </span>
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>CASH</span>
                                                  </div>

                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <c:if test="${lodgement.getRewardAmount() != 0}">
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Royalty Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getRewardAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Total Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getRewardAmount() + lodgement.getAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  </c:if>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Date : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getLodgmentDate()}</span>
                                                  </div>

                                              </div>
                                          </c:if> 

                                          <c:if test="${lodgement.getPaymentMode() == 4}">
                                              <div class="row">

                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Payment Mode : </span>
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>BANK TRANSFER</span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Bank Name : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getDepositorBankName()}</span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Account Name : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getOriginAccountName()}</span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Account Number : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getOriginAccountNumber()}</span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  <c:if test="${lodgement.getRewardAmount() != 0}">
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Royalty Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getRewardAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Total Amount : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span><fmt:formatNumber value="${lodgement.getRewardAmount() + lodgement.getAmount()}" type="currency" currencySymbol="N" /></span>
                                                  </div>
                                                  <div class="clearfix"></div>
                                                  </c:if>
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Date : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getLodgmentDate()}</span>
                                                  </div>
                                                  <div class="clearfix"></div>

                                              </div>
                                          </c:if> 

                                          <div class="col-md-12 margintop10">
                                              <a href="${pageContext.request.contextPath}/Lodgement?action=approve&id=${lodgement.getId()}" class="btn btn-success">Approve</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                              <a href="${pageContext.request.contextPath}/Lodgement?action=decline&id=${lodgement.getId()}" class="btn btn-danger">Decline</a>
                                          </div>
                                      </div>
                                      <div class="col-md-6">
                                          <div class="row">
                                              
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Customer Name : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getCustomer().getFullName()}</span>
                                                  </div>
                                                  
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Customer Phone : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getCustomer().getPhone()}</span>
                                                  </div>
                                                  
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Agent Name : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getCustomer().getAgent().getFullName()}</span>
                                                  </div>
                                                  
                                                  <div class="col-md-4">
                                                      <span class="lodgementTitleSpan">Agent Phone : </span> 
                                                  </div>
                                                  <div class="col-md-8">
                                                      <span>${lodgement.getCustomer().getAgent().getPhone()}</span>
                                                  </div>
                                                  
                                          </div>
                                      </div>
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
