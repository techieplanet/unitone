<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row margintop10 marginbottom20">
    <div class="col-md-12 bgeee text-conspicuous paddingbottom10">
        <span>Referral Code </span>
        <div><strong>${agent.account.accountCode}</strong></div>
        <c:if test="${sessionScope.userTypeString == 'AGENT'}">
            <div>
                <a class="btn btn-primary" onclick="$('#email_modal').modal({backdrop : 'static'})">Share via email</a>
                <a class="btn btn-primary marginleft10" onclick="$('#sms_modal').modal({backdrop : 'static'})">Share via SMS</a>
            </div>
        </c:if>
    </div>    
</div>


<div class="box-body">
 <!--<div class="permissions block">-->
  <table id="networklist" class="table table-bordered table-striped margintop10"  style="font-size: 14px;">
    <thead>
      <tr>
        <th class="text-center">SN</th>
        <th class="text-center">Picture</th>
        <th class="text-center">Account Code</th>
        <th class="text-center">Full Name</th>
        <th class="text-center">Phone</th>
        <th class="text-center">Email</th>
        <th class="text-center">State</th>
      </tr>
    </thead>
    <tbody>
        
        <c:forEach items="${networkList}" var="agent" varStatus="pointer">
                            <tr id="row<c:out value="${agent.agentId}" />">
                                <td><c:out value="${pointer.count}" /></td>
                                <td><img src="${agentImageAccessDir}/${agent.photoPath}" width='55' height='50'/></td>
                                <td><c:out value="${agent.account.accountCode}" /></td>
                                <td><c:out value="${agent.firstname} ${agent.middlename} ${agent.lastname}" /></td>
                                <td><c:out value="${agent.phone}" /></td>
                                <td><c:out value="${agent.email}" /></td>
                                <td><c:out value="${agent.state}" /></td>
                            </tr>
        </c:forEach>
            
  </tbody>
    
  </table>

    
</div><!-- /.box-body -->



<!--This will contain the referrer information--> 
<c:if test="${refAgent != null}">
    <div class="box-body margintop20">
     <div class="row marginbottom20">
        <div class="col-md-12 bgeee text-conspicuous paddingbottom10">
            <span>Referrer Information </span>
        </div>
     </div>

      <table id="" class="table table-bordered table-striped margintop10"  style="font-size: 14px;">
        <thead>
          <tr>
            <th class="text-center">SN</th>
            <th class="text-center">Picture</th>
            <th class="text-center">Account Code</th>
            <th class="text-center">Full Name</th>
            <th class="text-center">Phone</th>
            <th class="text-center">Email</th>
            <th class="text-center">State</th>
          </tr>
        </thead>
        <tbody>

        <tr id="row<c:out value="${refAgent.agentId}" />">
            <td><c:out value="1" /></td>
            <td><img src="${agentImageAccessDir}/${refAgent.photoPath}" width='55' height='50'/></td>
            <td><c:out value="${refAgent.account.accountCode}" /></td>
            <td><c:out value="${refAgent.firstname} ${refAgent.middlename} ${refAgent.lastname}" /></td>
            <td><c:out value="${refAgent.phone}" /></td>
            <td><c:out value="${refAgent.email}" /></td>
            <td><c:out value="${refAgent.state}" /></td>
        </tr>

      </tbody>

      </table>


    </div><!-- /.box-body -->
</c:if>

<!--MODAL-->
  <div class="modal fade" id="email_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">Send referral code via email</h4>
          <p class="statustext"></p>
        </div>
          
        <div class="modal-body">
            <input type="email" class="marginright10" name="email" id="email" size="40" style="height: 30px;" placeholder="Enter email..." />
            <button id="ok_email" type="button" onclick="codeSender.sendEmail(${agent.agentId})"  class="btn btn-primary">Go</button>
            <div><img class="hidden" src="${pageContext.request.contextPath}/images/uploadProgress.gif" /></div>
                
        </div>
        
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
                
  
  <!--MODAL FOR SMS -->
  <div class="modal fade" id="sms_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
          <h4 class="modal-title">Send referral code via SMS</h4>
        </div>
          
        <div class="modal-body">
            <input type="text" class="marginright10" name="sms" id="sms" size="40" style="height: 30px;" placeholder="Enter phone number..." />
            <button id="ok_sms" type="button" onclick="codeSender.sendSMS();"  class="btn btn-primary">Go</button>
        </div>
        
      </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
  </div><!-- /.modal -->
  
  
<!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
                
<script>
        
        $(function () {
            $("#networklist").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": false, "width":"20px", "targets": 0 }
                ]
            });
        });
        
        $('#email_modal').on('hidden.bs.modal', function (e) {
            $('#email_modal .statustext').removeClass('bg-red');
            $('#email_modal .statustext').removeClass('bg-green');
            $('#email_modal .statustext').text("");
            $('#email_modal #email').val("");
          })
        
        var codeSender = {
            sendEmail : function(agentId){
                if(!validateEmail($("#email").val())){
                    $('#email_modal .statustext').addClass('bg-red');
                    $('#email_modal .statustext').text("Enter valid email");
                    return;
                }
                    
                $('#email_modal img').removeClass("hidden");
                
                $.ajax({
                    type: "POST",
                    url: '${appContext}' + '/Agent?action=referer',
                    data: {referralMode:'email',refAgentId:agentId, recipientEmail:$("#email").val()},
                    success: function(result){codeSender.sharingResult('email_modal',result)},
                    error : function(xhr,status_code,status_text){
                        console.log(xhr.responseText);
                        $('#email_modal .statustext').addClass('bg-red');
                        $('#email_modal .statustext').text(xhr.responseText);
                        $('#email_modal img').addClass("hidden");
                    }
                });
            },
            
            sendSMS : function(){
                console.log("sending via SMS");
            },
            
            sharingResult: function(modalId, result){
                if(result == 'OK'){
                    $('#email_modal .statustext').addClass('bg-green');
                    $('#email_modal .statustext').text('Code shared successfully');
                    $('#email_modal img').addClass("hidden");
                    //setTimeout(function(){
                      //  $('#'+modalId).modal("hide");
                    //},2000);
                    
                }
                else{
                    $('#email_modal .statustext').addClass('bg-red');
                    $('#email_modal .statustext').text('Code NOT shared. Try again.');
                    $('#email_modal img').addClass("hidden");
                }    
            }
            
        }
        
</script>