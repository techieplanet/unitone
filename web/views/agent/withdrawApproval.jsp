<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   


<div class="content-wrapper">
    
    
    <section class="content-header">
        
        <h1>
            <a href="#">Agent Withdrawal Approval</a>
        </h1>
        
    </section>
    
    <div class="content">
        
        
        <div class="box">
            
            <div class="box-header with-border">
                <h4 class="box-title">Pending Withdrawal Request</h4>
            </div>
            
            <div class="box-body">
                
                <div class="panel-group" id="accordion2" role="tablist" aria-multiselectable="true">
              
              <c:forEach items="${withdrawals}" var="withdrawal" varStatus="withdrawalCount">
                  
                  <div class="panel panel-default" id="panel${withdrawalCount.count}">
                            <div class="panel-heading" style="height:40px; background-color: #357CA5 !important;" role="tab" id="heading${withdrawalCount.count}">
                              <h4 class="panel-title">
                                <a role="button" style="display: block;color:#fff !important;" data-toggle="collapse" data-parent="#accordion2" href="#collapse${withdrawalCount.count}"  aria-controls="collapse${withdrawalCount.count}">
                                    <span>ID : </span>${withdrawal.get("id")} 
                                    
                                </a>
                               
                              </h4>
                                
                            </div>
                                    <!-- Check if request is from notification click, 
                                    Then open and scroll to lodgement panel by default -->
                                    
                            <div id="collapse${withdrawalCount.count}" class="collapse  panel-collapse 
                                         <c:if test="${notificationwithdrawalId == 0}"> 
                                             <c:out value='${withdrawalCount.count == 1?"in":""}' />
                                         </c:if>
                                         <c:if test='${notificationwithdrawalId > 0 && withdrawal.get("id") == notificationwithdrawalId}'> 
                                             <c:out value='in scrollHere' />
                                         </c:if>
                                         " 
                                role="tabpanel" aria-labelledby="heading${withdrawalCount.count}" >
                                         
                              <div class="panel-body">
                                  
                                  <div class="row">
                                     
                                      <div class="col-md-6"> 
                                          <div class="col-md-4">
                                              <span class="text-bold">Name</span>
                                          </div>
                                          <div class="col-md-8">
                                              <span>${withdrawal.get("agentFullName")}</span>
                                          </div>    
                                          
                                          <div class="col-md-4">
                                              <span class="text-bold">Agent ID</span>
                                          </div>
                                          <div class="col-md-8">
                                              <span>${withdrawal.get("agentId")}</span>
                                          </div>    
                                      </div>
                                      
                                      <div class="col-md-6"> 
                                          <div class="col-md-4">
                                              <span class="text-bold">Balance</span>
                                          </div>
                                          <div class="col-md-8">
                                              <span><fmt:formatNumber value='${withdrawal.get("balance")}' type="currency" currencySymbol="N" /></span>
                                          </div>
                                          
                                          <div class="col-md-4">
                                              <span class="text-bold">Requested amount</span>
                                          </div>
                                          <div class="col-md-8">
                                              <span><fmt:formatNumber value="${withdrawal.get('amount')}" type="currency" currencySymbol="N" /></span>
                                          </div>  
                                      </div>
                                      
                                      <div class="col-md-12 margintop10">
                                          <a href="#" onclick="approveRequest(${withdrawal.get('id')},event,'panel${withdrawalCount.count}')" class="btn btn-success">Approve</a> &nbsp;&nbsp;&nbsp;&nbsp;
                                          <a href="#" onclick="declineRequest(${withdrawal.get('id')},event,'panel${withdrawalCount.count}')" class="btn btn-danger">Decline</a>
                                      </div>
                                  </div>
                              </div> 
                            </div>
                  </div>
                                      
              </c:forEach>     
            
              
          </div>
                
            </div>
            
        </div>
        
        
    </div>
    
    <input type="hidden" value="${pageContext.request.contextPath}" id="contextPath" />   
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
                    <p>Withdrawal Request has been approved</p>
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
                    <p>Approving Withdrawal...</p>
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
    
    
    function approveRequest(id,event,panelId){
        event.preventDefault();
        
        var url = $("#contextPath").val();
        
        $("#withdrawLoading").modal({
            backdrop : false
        });
        
        $.ajax({
            url : url + "/Agent?action=approveWithdrawal",
            method: "post",
            data : {withdrawal_id : id},
            success : function(data){
                
                console.log("Response : " + data);
                $("#"+panelId).remove();
                $("#withdrawLoading").modal("hide");
                $("#withdrawSuccessModal").modal();
                
            },
            error : function(xhrCode,status){
                console.log(xhrCode + " : " + status);
                $("#withdrawLoading").modal("hide");
                alert("Ooops something went wrong");
            }
        });
    }
    
    function declineRequest(id,event, panelId){
        
        event.preventDefault();
        
        var url = $("#contextPath").val();
        
        $("#withdrawLoading").modal({
            backdrop : false
        });
        
        $.ajax({
            url : url + "/Agent?action=declineWithdrawal",
            method: "post",
            data : {withdrawal_id : id},
            success : function(data){
                
                console.log(data);
                $("#"+panelId).remove();
                $("#withdrawLoading").modal("hide");
                $("#withdrawSuccessModal .modal-body p").html("Withdrawal Request has being Declined");
                $("#withdrawSuccessModal").modal();
                
            },
            error : function(xhrCode,status){
                console.log(xhrCode + " : " + status);
                $("#withdrawLoading").modal("hide");
                alert("Ooops something went wrong");
            }
        });
        
    }
</script>