<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
          
        <c:if test="${success}">
              <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-success padding15" style="vertical-align:center !important;" >
                          <i class="fa fa-check"></i>Saved Successfully
                        </p>
                    </div>
                </div>
          </c:if>  
        
          
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Agent Profile
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
          
          
            <div class="panel">
                
                <div class="panel-body">
                    
                    <div class="row">
                        
                        <div class="col-md-2">
                            
                            <h4>Agent Picture</h4>
                            <img src="${agentImageAccessDir}/${agent.photoPath}" class="img img-responsive img-thumbnail" />
                            
                            <h4>Next of Kin Picture</h4>
                            <img src="${agentKinImageAccessDir}/${agent.getKinPhotoPath()}" alt="No Preview Image" class="img img-responsive img-thumbnail" />
                            
                        </div>
                        
                        <div class="col-md-10">
                            
                            <table class="table table-profile">
                                
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>
                                            <div class="col-md-9">
                                                <h4>${agent.getFullName()}</h4>
                                            </div>
                                            <div class="col-md-3">
                                                <a href="#" class="pull-right" onclick="agentProfile.showPasswordModal(event)" style="text-decoration: none; border-bottom: 1px dotted blue;">Change password</a>
                                            </div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="highlight">
                                        <td class="field">Email</td>
                                        <td><i class="fa fa-envelope-o"></i> ${agent.getEmail()} </td>
                                        
                                    </tr>
                                    <tr class="divider">
                                        <td colspan="2"></td>
                                    </tr>
                                    <tr>
                                        <td class="field">Mobile</td>
                                        <td><i class="fa fa-mobile-phone"></i> ${agent.getPhone()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Street</td>
                                        <td>${agent.getStreet()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">City</td>
                                        <td>${agent.getCity()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">State</td>
                                        <td>${agent.getState()}</td>
                                    </tr>
                                    <tr class="highlight">
                                        <td class="field" colspan="2" style="text-align: left">
                                            <h4>Next of Kin Information</h4>
                                        </td>
                                    </tr>
                                    <tr class="divider">
                                        <td colspan="2"></td>
                                    </tr>
                                    <tr>
                                        <td class="field">Name</td>
                                        <td>${agent.getKinName()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Mobile</td>
                                        <td><i class="fa fa-mobile-phone"></i> ${agent.getKinPhone()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">State</td>
                                        <td>${agent.getKinAddress()}</td>
                                    </tr>
                                    
                                </tbody>
                            </table>
                            
                        </div>
                        
                    </div>
                    
                </div>
            </div>
            
            
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <!--MODAL-->
      <div class="modal fade" id="password_change_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Password Change</h4>
            </div>
            <div class="modal-body">
                <img src="${pageContext.request.contextPath}/images/uploadProgress.gif" class="img img-responsive hidden vertical-align-center" id="progressLoader" /><br />
                <div class="alert alert-error alert-dismissible fade in hidden">
                </div>
                <div class="alert alert-success alert-dismissible fade in hidden">
                    <span class="close" data-dismiss="alert">&times;</span>
                    Password change was successful
                </div>
                <div class="form-group">
                    <label>Old password</label>
                    <input type="password" class="form-control" name="old_password" id="old_password" />
                </div>
                <div class="form-group">
                    <label>New password</label>
                    <input type="password" class="form-control" name="new_password" id="new_password" />
                </div>
                <div class="form-group">
                    <label>Re-enter New password</label>
                    <input type="password" class="form-control" name="new_password2" id="new_password2" />
                </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              
              <button id="ok" type="button" onclick="agentProfile.changePassword('${agent.getAgentId()}')"  class="btn btn-primary">Submit</button>
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
      
     var agentProfile = {
         
         changePassword : function(id){
             
             //Start the loader
             $("#progressLoader").removeClass("hidden");
             $("#ok").prop("disabled",true);
             
             var old_password = $("#old_password").val();
             var pwd1 = $("#new_password").val();
             var pwd2 = $("#new_password2").val();
             
             var errors = [];
             
             if($.trim(old_password).length < 1){
                 errors.push("Old password is required");
             }
             
             if($.trim(pwd1).length < 1){
                 errors.push("New Password is required")
             }
             else if($.trim(pwd1) != $.trim(pwd2)){
                 errors.push("Password and Re-enter password must match");
             }
             
             if(errors.length > 0){
                 //Clear error and sucess alert
                 $("#password_change_modal .modal-body div.alert-error").html("");
                 $("#password_change_modal .modal-body div.alert-error").addClass("hidden");
                 $("#password_change_modal .modal-body div.alert-success").addClass("hidden");
                 for(var k=0; k < errors.length; k++){
                     
                     $("#password_change_modal .modal-body div.alert-error").append("<span>" + errors[k] + "</span><br />");
                    
                 }
                 $("#password_change_modal .modal-body div.alert-error").removeClass("hidden");
                 
                 //Stop the loader
                 $("#progressLoader").addClass("hidden");
                 $("#ok").prop("disabled",false);
                 
             }else{
                 
                 $.ajax({
                     url : "${pageContext.request.contextPath}/Agent?action=password_change",
                     method : "POST",
                     data : {old_password : old_password, pwd1 : pwd1, pwd2 : pwd2, id : id},
                     success : function(data){
                         
                         //Clear error and sucess alert
                         $("#password_change_modal .modal-body div.alert-error").html("");
                         $("#password_change_modal .modal-body div.alert-error").addClass("hidden");
                         $("#password_change_modal .modal-body div.alert-success").addClass("hidden");
                         
                         var json = JSON.parse(data);
                         
                         if(json.success){
                             $("#password_change_modal .modal-body div.alert-success").removeClass("hidden");
                         }
                         else{
                             $("#password_change_modal .modal-body div.alert-error").append("<span>" + json.error + "</span><br />");
                             $("#password_change_modal .modal-body div.alert-error").removeClass("hidden");
                         }
                         
                         //Stop the loader
                         $("#progressLoader").addClass("hidden");
                         $("#ok").prop("disabled",false);
                     },
                     error : function(xhr,error_code,error_text){
                         console.log(xhr.responseText);
                         //Stop the loader
                         $("#progressLoader").addClass("hidden");
                         $("#ok").prop("disabled",false);
                     }
                 })
             }
             
         },
         
         showPasswordModal : function(evt){
             evt.preventDefault();
             $("#password_change_modal .modal-body div.alert-error").html("");
             $("#password_change_modal .modal-body div.alert-error").addClass("hidden");
             $("#password_change_modal .modal-body div.alert-success").addClass("hidden");
             $("#password_change_modal").modal({
                 backdrop : "static"
             });
         }
     }
          
 </script>         
