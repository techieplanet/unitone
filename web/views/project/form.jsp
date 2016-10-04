<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="projectform" id="projectform" method="POST" action="Project?action=${action}&id=${id}"> 
    <div class="box box-primary">
      <div class="box-header with-border">
        <h3 class="box-title">
            <c:choose>
                <c:when test="${project.id != null}">
                    Edit Project
                </c:when>    
                <c:otherwise>
                    New Project
                </c:otherwise>
            </c:choose>
                    
            <!--${project.id == null ? "New Project" : "Edit Project"}-->
        </h3>
      </div><!-- /.box-header -->

      <div class="box-body">
            <c:if test="${fn:length(errors) > 0 }">
                <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-danger padding10" style="width:60%">
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
                        <p class="bg-success padding10" style="width:60%">
                          <i class="fa fa-check"></i>Saved Successfully
                          <span class="pull-right">
                              <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Project">Back to list</a>
                          </span>
                        </p>
                    </div>
                </div>
          </c:if>
              
          <div class="form-group">
            <label>Project Name *</label>
            <input type="text" name="pname" id="pname" class="form-control medium" value="${project.name}">
          </div>

          <!-- textarea -->
          <div class="form-group">
            <label>Project Description</label>
            <textarea name="desc" id="desc" class="form-control medium" rows="3" placeholder="">${project.description}</textarea>
          </div>

          <div class="form-group">
            <label>Project Location *</label>
            <input type="text" name="location" id="location" class="form-control medium" value="${project.location}">
          </div>

        <div class="form-group">
          <label>Project Manager *</label>
          <select name="pmanager" id="pmanager" class="form-control select2" style="width: 50%;">
            <option value="0">--Select--</option>
            <option value="1" <c:if test="${project.projectManager == 1}"> <jsp:text>selected</jsp:text> </c:if> >Oladele Emmanuel</option>
            <option value="2" <c:if test="${project.projectManager == 2}"> <jsp:text>selected</jsp:text> </c:if> >Alex Ighalo</option>
            <option value="3" <c:if test="${project.projectManager == 3}"> <jsp:text>selected</jsp:text> </c:if> >Bada Johnson</option>
            <option value="4" <c:if test="${project.projectManager == 4}"> <jsp:text>selected</jsp:text> </c:if> >Muyiwa Osas</option>
            <option value="5" <c:if test="${project.projectManager == 5}"> <jsp:text>selected</jsp:text> </c:if> >Freghene Tajudeen</option>
          </select>
        </div>
          
            
          <input type="hidden" name="id" id="id" value="${project.id}">

          <div class="box-footer">
              <button type="submit" class="btn btn-primary">Submit</button>
           </div>
      </div>

    </div><!-- /. box -->
</form>