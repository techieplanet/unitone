<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="roleform" id="companyAccount" method="POST" action="CompanyAccount?action=${action}&id=${account.getId()}" class="form-horizontal"> 
    <div class="box box-primary">
      <div class="box-header with-border">
        <h3 class="box-title">
            <c:choose>
                <c:when test="${account.id != null}">
                    Edit Company Account
                </c:when>    
                <c:otherwise>
                    New Company Account
                </c:otherwise>
            </c:choose>
                    
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
                        </p>
                    </div>
                </div>
          </c:if>
             
        <div class="row padding10">  
          <div class="form-group col-md-5">
              <label for="bank_name">Bank Name</label>
              <input type="hidden" name="id" value="${account.getId()}" />
              <input type="text" class="form-control" name="bank_name" id="bank_name" value="${account.getBankName()}" required="" />
          </div>
        </div>
        
        <div class="row padding10"> 
          <div class="form-group col-md-5">
              <label for="account_no">Account Number</label>
              <input type="text" class="form-control" name="account_no" id="account_no" value="${account.getAccountNumber()}" required="" />
          </div>
        </div>
          
        <div class="row padding10">   
          <div class="form-group col-md-5">
              <label for="account_name">Account Name</label>
              <input type="text" class="form-control" name="account_name" id="account_name" value="${account.getAccountName()}"  />
          </div>
        </div>    
          
        <div class="row padding10"> 
          <div class="box-footer">
              
              <c:choose>
                  <c:when test="${action == 'edit'}">
                      <button type="submit" class="btn btn-primary">Update</button>
                  </c:when>
                  <c:when test="${action == 'new'}">
                      <button type="submit" class="btn btn-primary">Submit</button>
                  </c:when>
              </c:choose>
              
              
          </div>
        </div>
         
          
      </div>

    </div><!-- /. box -->
</form>