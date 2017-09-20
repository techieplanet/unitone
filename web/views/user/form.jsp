<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="userform" id="userform" method="POST" action="User?action=${action}&id=${reqUser.userId}" class="form-horizontal"> 
        <div class="box box-primary" >
          <div class="box-header with-border">
            <h3 class="box-title">
                <c:choose>
                    <c:when test="${reqUser.userId != null}">
                        Edit User
                    </c:when>    
                    <c:otherwise>
                        New User
                    </c:otherwise>
                </c:choose>

                <!--${reqUser.userId == null ? "New User" : "Edit User"}-->
            </h3>
          </div><!-- /.box-header -->

          <!--<div class="box" style="border-top:1px !important; box-shadow: none;">-->
              <div class="box-body" style="padding-top: 0;">
                <div class="row nopadding">
                    <div class="box-body col-md-12">
                        <c:if test="${fn:length(errors) > 0 }">
                            <div class="row">
                                <div class="col-md-12 ">
                                    <p class="bg-danger padding10">
                                      <c:forEach items="${errors}" var="error">
                                          <c:out value="${error.value}" /><br/>
                                      </c:forEach>
                                    </p>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${success}">
                            <div class="row">
                                  <div class="col-md-12 ">
                                      <p class="bg-success padding10">
                                        <i class="fa fa-check"></i>Saved Successfully
                                        <span class="pull-right">
                                            <a class="btn btn-primary btn-sm margintop5negative" user="button" href="${pageContext.request.contextPath}/User"><i class="fa fa-arrow-left"></i>&nbsp;&nbsp;&nbsp;&nbsp;Back to list</a>
                                            <a class="btn btn-primary btn-sm margintop5negative marginleft10" href="User?action=new" user="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New User</a>
                                        </span>
                                      </p>
                                  </div>
                              </div>
                        </c:if>
                    </div>
                </div>
                    
                    
                    <div class="row" style="padding: 0 15px 0px 25px;">
                        <div class="col-md-3">    
                                <!--the inputs-->
                                <!--<h3 class="box-title">Personal Information</h3>-->
                                <div class="form-group">
                                    <label for="firstname" class="control-label">First Name*</label>
                                    <input type="text" name="firstname" id="firstname" class="form-control col-md-10" value="${reqUser.firstname}">
                                </div>
                                
                                <div class="form-group">
                                    <label for="middlename" class="control-label">Middle Name</label>
                                    <input type="text" name="middlename" id="middlename" class="form-control col-md-4" value="${reqUser.middlename}">
                                </div>

                                <div class="form-group">
                                    <label for="lastname" class="control-label">Last Name*</label>
                                      <input type="text" name="lastname" id="lastname" class="form-control" value="${reqUser.lastname}">
                                </div>
                        </div>
                        <div class="col-md-1"></div>

                    
                        <div class="col-md-3">
                                
                                <div class="form-group">
                                    <label for="email" class="control-label">Email*</label>
                                      <input type="text" name="email" id="email" class="form-control" value="${reqUser.email}">
                                </div>

                                <div class="form-group">
                                    <label for="phone" class="control-label">Phone</label>
                                      <input type="text" name="phone" id="phone" class="form-control medium" value="${reqUser.phone}">
                                      <small>e.g:080xxxxxxxx, 01xxxxxx</small>
                                </div>
                        </div>
                        <div class="col-md-1"></div>

                        <div class="col-md-3">

                                <div class="form-group">
                                    <label for="role" class="control-label">System Role*</label>
                                    <select name="role_id" id="role_id" style="width: 80%;" class="form-control" >
                                            <option value="0">--Choose--</option>
                                            <c:forEach items="${rolesList}" var="role">
                                                <option value="${role.roleId}" <c:if test="${reqUser.role.roleId == role.roleId}"> selected </c:if> >${role.title}</option>
                                            </c:forEach>
                                        </select>
                                </div>    
                        </div>
                        
                    </div>

                    
                                
                    <!--PERMISSIONS--> 
                 <% 
                 //   <div class="row">
                //<div class="col-md-12">
                //
                //    <div class="panel panel-default">
                //        <div class="panel-heading">
                //            <h3 class="panel-title">Permissions <span class="pull-right"><input class="minimal" type="checkbox" name="select-all" id="select-all"> SELECT ALL</span></h3>
                //        </div>
//
  //                      <!-- Table -->
    //                    <table class="table" id='permissions_table'>                            
      //                    <tbody>
//
  //                            <c:forEach items="${permissionsList}" var="entity">
    //                          <tr>
     //                             <td class="paddingtop20 bold bgeee" style="width:15%;">
       //                               ${entity.key}
         //                         </td>
//
  //                                <td>
    //                                  <c:forEach items="${entity.value}" var="permission">
      //                                    <div class="col-sm-2">
        //                                    <label class="checkbox-inline">
          //                                    <input class="minimal" type="checkbox" name="permissions" 
            //                                         ${selectedPermissions.contains(permission.alias) ? "checked" : ""}
              //                                       id="${permission.alias}" value="${permission.alias}">
                //                                     ${permission.action}
                  //                          </label>
                    //                      </div>
                      //                </c:forEach>
                        //          </td>
                          //    </tr>
                            //  </c:forEach>
                        //  </tbody>
                        //</table>
               // </div>
                  
                  
              //</div><!-- /.col -->
              //       </div> <!-- /.row -->
                    %>
                    
                    <div class="col-md-12">
                        <input type="hidden" name="id" id="id" value="${reqUser.userId}">
                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary">Submit</button>
                         </div>
                    </div>

                </div>
              </div>
          <!--</div>-->
          
              

        </div><!-- /. box -->
</form>
                        
<script>
    /*$(document).ready(function(){
        $("#role_id").on("change",function(){
           var selectedValue = $("#role_id").val();
           var data = {"role_id": selectedValue, "action":"rolechange","mode":"ajax"};
           genericAjax('${pageContext.request.contextPath}', 'Role', "GET", data, updatePermissionListDOM);
        });
       
        $('#select-all').on('ifChecked', function(event){
            $('#permissions_table input[type="checkbox"].minimal').iCheck('check')
        });
        
        $('#select-all').on('ifUnchecked', function(event){
            $('#permissions_table input[type="checkbox"].minimal').iCheck('uncheck')
        });
    });*/
    
    function updatePermissionListDOM(permissionsDetails){
        //console.log(permissionsDetails);
        var permissionsDetailsObj = JSON.parse(permissionsDetails);
        var allPermissions = permissionsDetailsObj["all"];
        var selectedPermissions = permissionsDetailsObj["selected"];
        console.log("selectedPermissions: " + selectedPermissions);
        
           <c:forEach items="allPermissions" var="sspermission">
                   //console.log(<c:out value="${sspermission}" />);
           </c:forEach>
        
            <c:forEach items="${permissionsList}" var="entity">
                                <c:forEach items="${entity.value}" var="permission">
                                    var permissionAlias = '${permission.alias}';
                                    console.log("alias: " + permissionAlias);
                                    if(selectedPermissions.indexOf(permissionAlias) != -1){
                                        $("#"+permissionAlias).iCheck('check');
                                    }
                                    else{
                                        $("#"+permissionAlias).iCheck('uncheck');
                                    }
                                </c:forEach>
            </c:forEach>
                
    }
    
</script>