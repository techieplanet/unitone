<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="roleform" id="roleform" method="POST" action="Role?action=${action}&id=${id}" class="form-horizontal"> 
    <div class="box box-primary">
      <div class="box-header with-border">
        <h3 class="box-title">
            <c:choose>
                <c:when test="${role.roleId != null}">
                    Edit Role
                </c:when>    
                <c:otherwise>
                    New Role
                </c:otherwise>
            </c:choose>
                    
            <!--${role.roleId == null ? "New Role" : "Edit Role"}-->
        </h3>
      </div><!-- /.box-header -->

      <div class="box-body">
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
                              <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Role"><i class="fa fa-arrow-left"></i>&nbsp;&nbsp;&nbsp;&nbsp;Back to list</a>
                              <!--&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
                              <a class="btn btn-primary btn-sm margintop5negative marginleft10" href="Role?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Role</a>
                          </span>
                        </p>
                    </div>
                </div>
          </c:if>
          <div class="col-md-12 col-lg-12">  
          <div class="form-group col-sm-6">
              <label for="title" class="col-sm-2 control-label">Title</label>
              <div class="col-sm-10">
                <input type="text"  name="title" id="title" class="form-control medium" value="${role.title}">
              </div>
          </div>
           <div class="form-group col-sm-6">
              <label for="tier" class="col-sm-2 control-label">Tier</label>
              <div class="col-sm-10">
                  <input type="number"  name="tier" id="tier" class="form-control medium" value="${role.tier}" onchange="updateSupervisorList()">
              </div>
          </div>
      </div>
              <div class="col-md-12 col-lg-12">
          <div class="form-group col-sm-6">
              <label for="alias" class="col-sm-2 control-label">Role Alias</label>
              <div class="col-sm-10">
                <input type="text"  name="alias" id="alias" class="form-control medium" value="${role.alias}">
              </div>
          </div>
          <div class="form-group col-sm-6">
              <label for="alias" class="col-sm-2 control-label">Supervisor</label>
              <div class="col-sm-10">
                  <select name="supervisor" id="supervisor" class="form-control medium" > 
                      <c:choose>
                          <c:when test="${supervisors != null && !supervisors.isEmpty()}" >
                              <option value="">-----choose-----</option> 
                          </c:when>
                          <c:when test="${role.tier != null}" >
                              <option value="">-----choose-----</option> 
                          </c:when>
                          <c:otherwise>
                            <option value="">--Please Input a Tier--</option>  
                          </c:otherwise>
                      </c:choose>
                    <c:forEach items="${supervisors}" var="supervisor" >
                        <option value="${supervisor.alias}" <c:if test="${role.supervisor == supervisor.alias}" > selected </c:if>>${supervisor.title}</option>
                    </c:forEach>
                </select>
              </div>
          </div>
      </div>
          <div class="form-group">
               <label for="desc" class="col-sm-2 control-label">Description</label>
                <div class="col-sm-10">
                  <input type="text" name="desc" id="desc" class="form-control medium" value="${role.description}">
                </div>
          </div>

          <div class="row">
              <div class="col-md-12">
                
                <div class="panel panel-default">
                    <div class="panel-heading">
                        <h3 class="panel-title">Permissions <span class="pull-right"><input class="minimal" type="checkbox" name="select-all" id="select-all"> SELECT ALL</span></h3>
                    </div>
                    <!-- Table -->
                    <table class="table" id='permissions_table'> 
                      <tbody>
                          <%--<c:set var="currentEntity" value="${permissionsList.value}" />--%>
                          <%--<c:out value="${currentEntity}" />--%>
                          
                          <c:forEach items="${permissionsList}" var="entity">
                          <tr>
                              <td class="paddingtop20 bold bgeee" style="width:15%;">
                                  ${entity.key}
                              </td>
                              
                              <td>
                                  <c:forEach items="${entity.value}" var="permission">
                                      <div class="col-sm-2">
                                        <label class="checkbox-inline">
                                          <input class="minimal" type="checkbox" name="permissions" 
                                                 ${selectedPermissions.contains(permission.alias) ? "checked" : ""}
                                                 id="${permission.alias}" value="${permission.alias}">
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
        </div>
          
            
          <input type="hidden" name="id" id="id" value="${role.roleId}">

          <div class="box-footer">
              <button type="submit" class="btn btn-primary">Submit</button>
           </div>
      </div>

    </div><!-- /. box -->
</form>
          
          <script>
               $(document).ready(function(){
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
    });
          
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
     
    function updateSupervisorList()
    {
        //tier //supervisor
        $("#supervisor").html("Loading...");
        $.post(window.location.pathname , "action=role_select&tier="+$("#tier").val() , function(data){
            data = $.parseJSON(data);
            if(Object.keys(data).length === 0)
            {
               $("#supervisor").html("");
               var option =  document.createElement("option");
               option.setAttribute("value" , "");
               option.textContent = "No higher hirachy for the selected tier";
               $("#supervisor").append(option);
            }
            else
            {
              $("#supervisor").html("");
              var option =  document.createElement("option");
              option.setAttribute("value" , "");
               option.textContent = "-----choose-----";
               $("#supervisor").append(option);
            $.each(data , function(key , value){
               var option =  document.createElement("option");
               option.setAttribute("value" , value);
               option.textContent = key;
               $("#supervisor").append(option);
            });
             }
        });
    }
          
          </script>