<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="projectunitform1" id="projectunitform" method="POST" action="ProjectUnit" class="form-horizontal" role="form"> 
    <div class="">
      <div class="box-header with-border">
        <h3 class="box-title">
            <span class="title-text"></span>
            <%--<c:choose>--%>
                <%--<c:when test="${project.id > 0}">--%>
                    <!--Edit Unit-->
                <%--</c:when>--%>    
                <%--<c:otherwise>--%>
                    <!--New Unit-->
                <%--</c:otherwise>--%>
            <%--</c:choose>--%>
                    
            <!--${project.id == null ? "New Project" : "Edit Project"}-->
            <span id="loading" class="hidden" style="float:right;">
                <small>Fetching Data... &nbsp;&nbsp;&nbsp;</small> 
                <img src="${pageContext.request.contextPath}/images/uploadProgress.gif" />
            </span>
        </h3>
      </div><!-- /.box-header -->

      <div class="box-body">
            <%--<c:if test="${fn:length(errors) > 0 }">--%>
                <div class="row hidden" id="error-box-wrapper">
                    <div class="col-md-12 ">
                        <p id="error-box" class="bg-danger padding10">
                          <c:forEach items="${errors}" var="error">
                              <c:out value="${error.value}" /><br/>
                          </c:forEach>
                        </p>
                    </div>
                </div>
            <%--</c:if>--%>
            
          <%--<c:if test="${success}">--%>
                <div class="row hidden" id="success-box-wrapper" >
                    <div class="col-md-12 ">
                        <p id="success-box" class="bg-success padding10">
                            <i class="fa fa-check"></i><span id="successMsg">Saved Successfully</span>
<!--                          <span class="pull-right">
                              <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Project">Back to list</a>
                          </span>-->
                        </p>
                    </div>
                </div>
          <%--</c:if>--%>
              
          <div class="form-group">
            <label for="title" class="col-sm-4 control-label">Unit Title*</label>
            <div class="col-sm-8">
                <input type="text" name="title" id="title" class="form-control marginbottom15" value="">
            </div>
          </div>

          <div class="form-group">
            <label for="cpu" class="col-sm-4 control-label">Project Cost Per Unit*</label>
            <div class="col-sm-7">
                <input type="text" name="cpu" id="cpu" class="form-control text-right medium marginbottom15" value="">
            </div>
          </div>
          
          <div class="form-group">
            <label for="lid" class="col-sm-4 control-label">Least Initial Deposit*</label>
            <div class="col-sm-7">            
                <input type="text" name="lid" id="lid" class="form-control medium text-right marginbottom15" value="">
            </div>
          </div>

          <div class="form-group">
            <label for="discount" class="col-sm-4 control-label">Discount*</label>
            <div class="col-sm-7">            
                <input type="text" name="discount" id="discount" class="form-control medium text-right marginbottom15" value="">
            </div>
          </div>
          
          <div class="form-group">
            <label for="mpd" class="col-sm-4 control-label">Max. Payment Duration*</label>
            <div class="col-sm-7">            
                <input type="text" name="mpd" id="mpd" class="form-control medium text-right marginbottom15" value="">
            </div>
          </div>
          
          <div class="form-group">
            <label for="commp" class="col-sm-4 control-label">Commission Percentage*</label>
            <div class="col-sm-7">            
                <input type="text" name="commp" id="commp" class="form-control medium text-right marginbottom15" value="">
            </div>
          </div>
          
          <div class="form-group">
            <label for="quantity" class="col-sm-4 control-label">Quantity Available*</label>
            <div class="col-sm-7">            
                <input type="text" name="quantity" id="quantity" class="form-control medium text-right marginbottom15" value="">
            </div>
          </div>
          
        </div>
            
          <input type="hidden" name="projectid" id="projectid" value="${project.id}">
          <input type="hidden" name="id" id="id" value="">

          <div class="box-footer text-center">
              <a role="button" class="btn btn-default pull-left" onclick="$('#myModal').modal('hide'); return false;" >Cancel</a>
              <a role="button" class="btn btn-primary pull-right" onclick="sendData();return false;">Save</a>
              <span id="loading2" class="hidden" >
                <small>Saving... &nbsp;&nbsp;&nbsp;</small> 
                <img src="${pageContext.request.contextPath}/images/uploadProgress.gif" width="80" height="15" />
            </span>
           </div>
      </div>

</form>
          
<script>
    $('#myModal').on('hidden.bs.modal', function (e) {
        //console.log('Modal hiding');
        $('#projectunitform .form-control, #id').val("");
        $('#success-box-wrapper, #error-box-wrapper, #loading2').addClass("hidden");
     });
     
    function sendData(){        
        var dataObject = {};
        dataObject.title = $('#title').val();
        dataObject.cpu = $('#cpu').val();
        dataObject.lid = $('#lid').val();
        dataObject.discount = $('#discount').val();
        dataObject.mpd = $('#mpd').val();
        dataObject.commp = $('#commp').val();
        dataObject.id = $('#id').val();
        dataObject.projectid = $('#projectid').val();
        
        console.log("send data: " + $('#projectunitform').serialize());
        submitPostForm('${pageContext.request.contextPath}/ProjectUnit', 
                        $('#projectunitform').serialize()
                     );
    }
    
    function processSubmitSuccess(response){
       var result = JSON.parse(response);
       console.log("Successful 2: " + result.UNIT_ID, result.TITLE, result.QUANTITY);
       /*
        * We need to update the sidebar with new changes based on 
        * either insert or update mode.
        */
       if($('#id').val().length == 0){//insert mode
           var html = '<li id="' + 'row'+result.UNIT_ID + '" class="list-group-item noborder">' +
                        '<span class="" style="width: 60%; display: inline-table;">' + result.TITLE + '<span class="badge marginleft5">' + result.QUANTITY + '</span></span>' +
                        '<a class="pull-right btn btn-danger btn-xs marginleft5" href="#" onclick="showDeleteModal('+ "'/NeoForce', 'ProjectUnit'," + result.UNIT_ID + ') role="button"><i class="fa fa-remove"></i></a>' +
                        '<a class="pull-right btn btn-success btn-xs" onclick="launchEditUnitForm(' + result.UNIT_ID + ",'/NeoForce'" + ')" role="button"><i class="fa fa-pencil"></i></a>' +
                    '</li>';
            $('.list-group').append(html);
            $('#id').val(result.UNIT_ID);
            
            $('#loading2').addClass("hidden");
            $('#error-box-wrapper').addClass('hidden');
            $('#success-box-wrapper').removeClass('hidden');
       }
       else{ //update mode
           //console.log("update mode");
           $('#row'+result.UNIT_ID).addClass("updating");
           $('#row'+result.UNIT_ID + ' span').remove();
           var prependStrring = '<span class="" style="width: 60%; display: inline-table;">' + result.TITLE + 
                                '<span class="badge marginleft5">' + result.QUANTITY + '</span></span>';
           $('#row'+result.UNIT_ID).prepend(prependStrring);
           setTimeout(function(){ 
                            $('#row'+result.UNIT_ID).removeClass("updating"); 
                        },500);
           $('#id').val(result.UNIT_ID);
           
           $('#loading2').addClass("hidden");
           $('#error-box-wrapper').addClass('hidden');
           $('#success-box-wrapper').removeClass('hidden');
       }
       

    }
    
    function processSubmitError(response){
        var error = JSON.parse(response);
        var msg = '';
        console.log(error.MESSAGE);
        $('#error-box').html(error.MESSAGE);
        $('#success-box-wrapper').addClass('hidden');
        $('#error-box-wrapper').removeClass('hidden');
        $('#loading2').addClass("hidden");
    }
    
</script>