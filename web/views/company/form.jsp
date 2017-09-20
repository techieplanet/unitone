<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="roleform" id="companyAccount" method="POST" action="Company" class="form-horizontal" enctype="multipart/form-data"> 
   
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
              <label for="companyName">Company Name</label>
              <input type="text" class="form-control" name="companyName" id="companyName" value="${company.getName()}" required="" />
          </div>
        </div>
        
        <div class="row padding10"> 
          <div class="form-group col-md-5">
              <label for="companyEmail">Company Email</label>
              <input type="text" class="form-control" name="companyEmail" id="companyEmail" value="${company.getEmail()}" required="" />
          </div>
        </div>
          
        <div class="row padding10">   
          <div class="form-group col-md-5">
              <label for="companyPhone">Company Phone </label>
              <input type="text" class="form-control" name="companyPhone" id="companyPhone" value="${company.getPhone()}"  />
          </div>
        </div>   
          
           <div class="row padding10">   
          <div class="form-group col-md-5">
              <label for="companyAddress1">Company Address Line 1 </label>
              <input type="text" class="form-control" name="companyAddress1" id="companyAddress1" value="${company.getAddressLine1()}"  />
          </div>
        </div> 
          
           <div class="row padding10">   
          <div class="form-group col-md-5">
              <label for="companyAddress2">Company Address Line 2</label>
              <input type="text" class="form-control" name="companyAddress2" id="companyAddress2" value="${company.getAddressLine2()}"  />
          </div>
        </div> 
          
           <div class="row padding10">   
          <div class="form-group col-md-5">
              <label for="companyLogo">Company Logo </label>
               <span class="marginleft5"><a href="#" style="text-decoration:underline" onclick="showImage('${company.getLogoPath()}')" >view image</a></span>
              <input type="file" class="form-control" name="companyLogo" id="companyLogo"  accept="image/gif, image/jpeg, image/png, image/bmp"/>
          </div>
        </div> 
          
        <div class="row padding10"> 
          <div class="box-footer">
                      <button type="submit" class="btn btn-primary">Update</button>
          </div>
        </div>
         
          
      </div>

    </div><!-- /. box -->
</form>
          
           <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title" >Company Logo</h4>
            </div>
            <div class="modal-body">
                <img class="img-responsive" src="" alt="No image to Display" id="ImageUrl">
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary pull-right" data-dismiss="modal">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
        </div><!-- /.modal --> 
        <script>      
        function showImage(url ){
        
        $("#ImageUrl").attr('src', "/uploads/NeoForce/images/" + url);
         $("#imageModal").modal();
    }
    </script>