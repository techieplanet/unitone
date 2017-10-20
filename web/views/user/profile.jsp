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
            User Profile
          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
          
          
            <div class="panel">
                
                <div class="panel-body">
                    
                    <div class="row">
                        
                        
                        
                        <div class="col-md-12">
                            
                            <table class="table table-profile">
                                
                                <thead>
                                    <tr>
                                        <th></th>
                                        <th>
                                            <div class="col-xs-12 col-md-8">
                                                <h4>${user.getUserFullName()}</h4>
                                            </div>
                                            <div class=" col-xs-12 col-md-4 text-right">
                                                <a href="#" onclick="userProfile.showProfileEditModal(event)" style="text-decoration: none; border-bottom: 1px dotted blue;"><i class="fa fa-edit"></i> Edit Profile</a>&nbsp;
                                                <a href="#" onclick="userProfile.showPasswordModal(event)" style="text-decoration: none; border-bottom: 1px dotted blue;">Change password</a>
                                            </div>
                                        </th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr class="highlight">
                                        <td class="field">Email</td>
                                        <td><i class="fa fa-envelope-o"></i> ${user.getEmail()} </td>
                                        
                                    </tr>
                                    <tr class="divider">
                                        <td colspan="2"></td>
                                    </tr>
                                    <tr>
                                        <td class="field">Last name</td>
                                        <td> ${user.getLastname()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Middle name</td>
                                        <td> ${user.getMiddlename()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">First name</td>
                                        <td> ${user.getFirstname()}</td>
                                    </tr>
                                    <tr class="highlight">
                                        <td class="field">Mobile</td>
                                        <td><i class="fa fa-mobile-phone"></i> ${user.getPhone()}</td>
                                    </tr>
                                    <tr>
                                        <td class="field">Role</td>
                                        <td> ${user.getRole().getTitle()}</td>
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
              
              <button id="ok" type="button" onclick="userProfile.changePassword('${user.getUserId()}')"  class="btn btn-primary">Submit</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
      
      
      <!--MODAL-->
      <div class="modal fade" id="profile_edit_modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title"><i class="fa fa-edit"></i> Profile Edit</h4>
            </div>
            <div class="modal-body">
                <img src="${pageContext.request.contextPath}/images/uploadProgress.gif" class="img img-responsive hidden vertical-align-center" id="progressLoader" /><br />
                <div class="alert alert-error alert-dismissible fade in hidden">
                </div>
                <div class="alert alert-success alert-dismissible fade in hidden">
                    <span class="close" data-dismiss="alert">&times;</span>
                    Profile was edited successfully 
                </div>
                <div class="form-group">
                    <label for="fname">First name</label>
                    <input type="text" class="form-control" name="fname" id="fname" value="${user.getFirstname()}" />
                </div>
                <div class="form-group">
                    <label for="lname">Last name</label>
                    <input type="text" class="form-control" name="lname" id="lname" value="${user.getLastname()}" />
                </div>
                <div class="form-group">
                    <label for="mname">Middle name</label>
                    <input type="text" class="form-control" name="mname" id="mname" value="${user.getMiddlename()}" />
                </div>
                <div class="form-group">
                    <label for="email">Email</label>
                    <input type="text" class="form-control" name="email" id="email" value="${user.getEmail()}" />
                </div>
                <div class="form-group">
                    <label for="mobile">Mobile no</label>
                    <input type="text" class="form-control" name="mobile" id="mobile" value="${user.getPhone()}" />
                </div>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              
              <button id="ok" type="button" onclick="userProfile.editProfile('${user.getUserId()}')"  class="btn btn-primary">Submit</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div>
      <!-- /.modal -->
      
      
      
<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


<script>
      
     var userProfile = {
         
         changePassword : function(id){
             
             //Start the loader
             $("#password_change_modal #progressLoader").removeClass("hidden");
             $("#password_change_modal #ok").prop("disabled",true);
             
             var old_password = $("#old_password").val();
             var pwd1 = $("#new_password").val();
             var pwd2 = $("#new_password2").val();
             
             var errors = [];
             
             if($.trim(old_password).length < 1){
                 errors.push("Old password is required");
             }
             
             if($.trim(pwd1).length < 1){
                 errors.push("New password is required");
             }
             else if($.trim(pwd2).length < 1)
             {
                 errors.push("Please re-enter new password");
             }
             else if($.trim(pwd1) != $.trim(pwd2)){
                 errors.push("Password and Re-entered password must match");
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
                 $("#password_change_modal #progressLoader").addClass("hidden");
                 $("#password_change_modal #ok").prop("disabled",false);
                 
             }else{
                 
                 $.ajax({
                     url : "${pageContext.request.contextPath}/User?action=password_change",
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
                         $("#password_change_modal #progressLoader").addClass("hidden");
                         $("#password_change_modal #ok").prop("disabled",false);
                     },
                     error : function(xhr,error_code,error_text){
                         console.log(xhr.responseText);
                         //Stop the loader
                         $("#password_change_modal #progressLoader").addClass("hidden");
                         $("#password_change_modal #ok").prop("disabled",false);
                     }
                 });
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
         },
         
         showProfileEditModal : function(evt){
         
            evt.preventDefault();
            
            $("#profile_edit_modal  .modal-body div.alert-error").html("");
            $("#profile_edit_modal .modal-body div.alert-error").addClass("hidden");
            $("#profile_edit_modal .modal-body div.alert-success").addClass("hidden");
             
            $("#profile_edit_modal").modal({
                backDrop : "static"
            });
         },
         
         editProfile : function(id){
             
             //Start the loader
             $("#profile_edit_modal #progressLoader").removeClass("hidden");
             $("#profile_edit_modal #ok").prop("disabled",true);
             
             var fname = $("#fname").val();
             var lname = $("#lname").val();
             var mname = $("#mname").val();
             var email = $("#email").val();
             var phone = $("#mobile").val();
             
             var errors = [];
             
             if($.trim(fname).length === 0)
                 errors.push("First name is required");
             if($.trim(lname).length === 0)
                 errors.push("Last name is required");
             if($.trim(email).length === 0)
                 errors.push("Email is required");
             if($.trim(phone).length === 0)
                 errors.push("Mobile no is required");
             
             if(errors.length > 0){
                 
                 $("#profile_edit_modal .modal-body div.alert-error").html("");
                 $("#profile_edit_modal .modal-body div.alert-error").addClass("hidden");
                 $("#profile_edit_modal .modal-body div.alert-success").addClass("hidden");
                 
                 for(var k=0; k < errors.length; k++){
                     
                     $("#profile_edit_modal .modal-body div.alert-error").append("<span>" + errors[k] + "</span><br />");
                 }
                 
                 $("#profile_edit_modal .modal-body div.alert-error").removeClass("hidden");
                 
                 //Stop the loader
                 $("#profile_edit_modal #progressLoader").addClass("hidden");
                 $("#profile_edit_modal #ok").prop("disabled",false);
             }
             else{
                 
                 var postData = {fname : fname, lname : lname, mname : mname, email : email, phone : phone, id : id};
                 $.ajax({
                     url : "${pageContext.request.contextPath}/User?action=edit_profile",
                     method : "POST",
                     data : postData,
                     success : function(data){
                         
                         //Clear error and sucess alert
                         $("#profile_edit_modal .modal-body div.alert-error").html("");
                         $("#profile_edit_modal  .modal-body div.alert-error").addClass("hidden");
                         $("#profile_edit_modal  .modal-body div.alert-success").addClass("hidden");
                         
                         var json = JSON.parse(data);
                         
                         if(json.success){
                             $("#profile_edit_modal  .modal-body div.alert-success").removeClass("hidden");
                         }
                         else{
                             if(json.fname_error)
                                $("#profile_edit_modal  .modal-body div.alert-error").append("<span>" + json.fname_error + "</span><br />");
                             if(json.lname_error)
                                $("#profile_edit_modal  .modal-body div.alert-error").append("<span>" + json.lname_error + "</span><br />");
                             if(json.phone_error)
                                $("#profile_edit_modal  .modal-body div.alert-error").append("<span>" + json.phone_error + "</span><br />");
                             if(json.email_error)
                                $("#profile_edit_modal  .modal-body div.alert-error").append("<span>" + json.email_error + "</span><br />");
                            
                             $("#profile_edit_modal .modal-body div.alert-error").removeClass("hidden");
                         }
                         
                         //Stop the loader
                         $("#profile_edit_modal #progressLoader").addClass("hidden");
                         $("#profile_edit_modal #ok").prop("disabled",false);
                     },
                     error : function(xhr,error_code,error_text){
                         console.log(xhr.responseText);
                         //Stop the loader
                         $("#profile_edit_modal #progressLoader").addClass("hidden");
                         $("#profile_edit_modal #ok").prop("disabled",false);
                     }
                 });
                 
             }
             
         }
         
         
         
         
     };
          
 </script>         
