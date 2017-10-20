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

            <a href="${pageContext.request.contextPath}/Project" class="blacktext">Roles and Permissions</a>

          </h1>
        </section>

        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-md-3">
                <!--this will be for the role-->
        <div class="box-body no-padding">
        <ul class="list-group">
            <c:forEach items="${supervisors}" var="supervisor">
                <li  class="list-group-item nav btn-default " onclick="update(${supervisor.roleId} ,'${supervisor.title}' )" style="cursor :pointer">
                    ${supervisor.title}
                </li>
            </c:forEach>
         </ul>
    </div>
            </div>
              
            <div class="col-md-9">
                <!--This will be for the permissions--> 
          <!--PERMISSIONS--> 
                  <div class="row">
                <div class="col-md-12">
                
                   <div class="panel panel-default">
                       <div class="panel-heading">
                           <h3 class="panel-title">Permissions <span class="pull-right" id="roleName"></span></h3>
                       </div>

                        <!-- Table -->
                      <table class="table" id='permissions_table'>                            
                         <tbody>
                             <c:forEach items="${permissionsList}" var="entity">
                           <tr>
                                <td class="paddingtop20 bold bgeee" style="width:15%;">
                                    ${entity.key}
                                </td>
                                <td>
                                     <c:forEach items="${entity.value}" var="permission">
                                        <div class="col-sm-5">
                                          <label class="checkbox-inline">
                                             <input class="minimal" type="checkbox" name="permissions" 
                                                    ${selectedPermissions.contains(permission.alias) ? "checked" : ""}
                                                    id="${permission.alias}" value="${permission.alias}" >
                                                    ${permission.action}
                                           </label>
                                         </div>
                                     </c:forEach>
                                 </td>
                             </tr>
                             </c:forEach>
                         </tbody>
                        </table>
                </div>
                  
                  
              </div><!-- /.col -->
                     </div> <!-- /.row -->
            </div><!-- /.col -->
          </div><!-- /.row -->
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->


<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
      

<script>
      $(function () {
        //Add text editor
        $("#compose-textarea").wysihtml5();
      });
     
    $(document).ready(function(){
        $('#select-all').on('ifChecked', function(event){
            $('#permissions_table input[type="checkbox"].minimal').iCheck('check')
        });
        
        $('#select-all').on('ifUnchecked', function(event){
            $('#permissions_table input[type="checkbox"].minimal').iCheck('uncheck')
        });
    });
    
    function update(id , name){
         var data = {"role_id": id, "action":"loadPermissions","mode":"ajax"};
         genericAjax('${pageContext.request.contextPath}', 'Role', "GET", data, updatePermissionListDOM);
         $("#roleName").text(name);
    }
    function updatePermissionListDOM(permissionsDetails){
        //console.log(permissionsDetails);
        var permissionsDetailsObj = JSON.parse(permissionsDetails);
        var allPermissions = permissionsDetailsObj["all"];
        var selectedPermissions = permissionsDetailsObj["selected"];
        var permissionAlias;
        //console.log("selectedPermissions: " + selectedPermissions);
        
           <c:forEach items="allPermissions" var="sspermission">
                   //console.log(<c:out value="${sspermission}" />);
           </c:forEach>
        
            <c:forEach items="${permissionsList}" var="entity">
                                <c:forEach items="${entity.value}" var="permission">
                                    permissionAlias = '${permission.alias}';
                                    //console.log("alias: " + permissionAlias);
                                    
                                    if(selectedPermissions && selectedPermissions.indexOf(permissionAlias) != -1){
                                        $("#"+permissionAlias).iCheck('check');
                                    }
                                    else{
                                        $("#"+permissionAlias).iCheck('uncheck');
                                    }
                                </c:forEach>
            </c:forEach>
                
    }
    
    </script>