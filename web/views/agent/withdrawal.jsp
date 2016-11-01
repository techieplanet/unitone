<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   


<div class="content-wrapper">
    
    
    <section class="content-header">
        
        <h1>
            <a href="#">Agent Withdrawal Request</a>
        </h1>
        
    </section>
    
    
    <div class="content">
        <div class="box">
            
            <div class="box-header with-border"> 
                <div class="callout callout-success" style="margin-bottom: 0;">
                    <h4 style="margin-bottom: 0;">Balance  : </h4>
                </div>
     
            </div>
            
            <div class="box-body">
             <div class="row">
                <div class="col-xs-12">
                    <h4>Request Withdrawal</h4>
                </div>

                <div class="col-md-2">
                    <span>Amount</span>
                </div>

                <div class="col-md-2">
                    <input type="text" name="" id="withdraw_amount" />
                </div>

                <div class="col-md-12">
                    <a href="#" onclick="return withdrawalRequest(event)" class="btn btn-success"><i class="fa fa-money"></i>  Request</a>
                </div>
            </div>
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

  function withdrawalRequest(event){
      event.preventDefault();
 
      sendWithdrawRequest();
  }
  
  function sendWithdrawRequest(){
      
      var url = $("#contextPath").val();
      var amount = $("#withdraw_amount").val();
      var agentId = $("#agent_id").val();
      
      amount = amount.trim();
      
      
      
      if(isNaN(amount)){
          alert("Please enter a valid amount to withdraw");
          return;
      }
      
      if(amount.length < 4){
          alert("Minimum withdrawal amount is 1000");
          return;
      }
      
      $("#withdrawLoading").modal({
          backdrop : false
      });
      
      $.ajax({
          url : url + "/Agent?action=withdrawal",
          method : "post",
          data : {amount : amount, agent_id : agentId},
          success : function(data){
                    console.log("Response : " + 1);
                    $("#withdrawLoading").modal("hide");
                    $("#withdrawSuccessModal").modal({
                        backdrop : false
                     });
          },
          error : function(){
              alert("Ooops, something went wrong... ");
          }
      });
      
  }

</script>