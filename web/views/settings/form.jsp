<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="appNameForm" id="AppNameSetting" method="POST" action="Settings?action=appname" class="form-horizontal" > 
   
      <div class="box-body">
          <c:if test="${success}">
              <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-success padding10">
                          <i class="fa fa-check"></i>Saved Successfully
                        </p>
                    </div>
                </div>
          </c:if>
             
        <div class="row padding10">  
          <div class="form-group col-md-5">
              <label for="appName">App Name</label>
              <input type="text" class="form-control" name="appName" id="appName" value="${appName}" required/>
          </div>
        </div>
       
        <div class="row padding10 col-md-5"> 
          <div class="box-footer">
                      <button type="submit" class="btn btn-primary">Update</button>
          </div>
        </div>
         
          
      </div>

    </div><!-- /. box -->
</form>
    