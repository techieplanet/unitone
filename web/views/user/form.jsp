<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="userform" id="userform" method="POST" action="User?action=${action}&id=${user.userId}" class="form-horizontal"> 
        <div class="box box-primary" >
          <div class="box-header with-border">
            <h3 class="box-title">
                <c:choose>
                    <c:when test="${user.userId != null}">
                        Edit User
                    </c:when>    
                    <c:otherwise>
                        New User
                    </c:otherwise>
                </c:choose>

                <!--${user.userId == null ? "New User" : "Edit User"}-->
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
                                    <input type="text" name="firstname" id="firstname" class="form-control col-md-10" value="${user.firstname}">
                                </div>

                                <div class="form-group">
                                    <label for="email" class="control-label">Email*</label>
                                      <input type="text" name="email" id="email" class="form-control" value="${user.email}">
                                </div>
                        </div>
                        <div class="col-md-1"></div>

                    
                        <div class="col-md-3">
                                <div class="form-group">
                                    <label for="middlename" class="control-label">Middle Name*</label>
                                    <input type="text" name="middlename" id="middlename" class="form-control col-md-4" value="${user.middlename}">
                                </div>

                                <div class="form-group">
                                    <label for="phone" class="control-label">Phone</label>
                                      <input type="text" name="phone" id="phone" class="form-control medium" value="${user.phone}">
                                      <small>e.g:080xxxxxxxx, 01xxxxxx</small>
                                </div>
                        </div>
                        <div class="col-md-1"></div>

                        <div class="col-md-3">
                                <div class="form-group">
                                    <label for="lastname" class="control-label">Last Name*</label>
                                      <input type="text" name="lastname" id="lastname" class="form-control" value="${user.lastname}">
                                </div>

                                <div class="form-group">
                                    <label for="role" class="control-label">System Role*</label>
                                        <select name="role_id" id="role_id" style="width: 80%;" class="form-control">
                                            <option value="0">--Choose--</option>
                                            <c:forEach items="${rolesList}" var="role">
                                                <option value="${role.roleId}" <c:if test="${user.role.roleId == role.roleId}"> selected </c:if> >${role.title}</option>
                                            </c:forEach>
                                        </select>
                                </div>    
                        </div>
                    </div>

                          
                    
<!--                    <div class="col-md-6">
                        <h3 class="box-title">System Access</h3>
                                            
                        
                        <div class="form-group">
                            <label for="username" class="col-sm-3 control-label">User Name*</label>
                            <div class="col-sm-9">
                              <input type="text" name="username" id="username" class="form-control medium" value="${user.username}">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="username" class="col-sm-3 control-label">Password*</label>
                            <div class="col-sm-9">
                                <input type="password" name="password" id="password" class="form-control medium" value="">
                            </div>
                        </div>
                        
                        <div class="form-group">
                            <label for="confirm" class="col-sm-3 control-label">Confirm Password*</label>
                            <div class="col-sm-9 margintop10">
                              <input type="password" name="confirm" id="confirm" class="form-control medium" value="">
                            </div>
                        </div>
                        
                    </div>-->
                    
                    
                    <div class="col-md-12">
                        <input type="hidden" name="id" id="id" value="${user.userId}">
                        <div class="box-footer">
                            <button type="submit" class="btn btn-primary">Submit</button>
                         </div>
                    </div>

                </div>
              </div>
          <!--</div>-->
          
              

        </div><!-- /. box -->
</form>