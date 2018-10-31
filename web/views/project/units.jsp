
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--<a href="mailbox.html" class="btn btn-primary btn-block margin-bottom">Back to Inbox</a>-->
<div class="box box-solid">
<div class="box-header with-border">
  <h3 class="box-title">Units</h3>
  
  <c:if test="${project.id != null }">
    <a class="pull-right text-center" onclick="newUnit()" style="width: 25px; cursor: pointer; padding: 2px;" ><i class="fa fa-plus"></i></a>
  </c:if>
  
</div>
    <div class="box-body no-padding">
        <ul class="list-group">
            <c:forEach items="${units}" var="unit">
                <li id="row<c:out value="${unit.id}" />" class="list-group-item noborder">
                    <span class="" style="width: 60%; display: inline-table;">${unit.title}<span class="badge marginleft5">${unit.quantity}</span><span class="marginleft5"><a href="#" style="text-decoration:underline" onclick="showImage('${unit.getImage()}' , 1)" >view image</a></span></span>
                    <a class="pull-right btn btn-danger btn-xs marginleft5" href="#" onclick="showDeleteModal('${pageContext.request.contextPath}', 'ProjectUnit', ${unit.id})" role="button"><i class="fa fa-remove"></i></a>
                    <a class="pull-right btn btn-success btn-xs marginleft5" onclick="uploadUnitImage(${unit.id})" role="button"><i class="fa fa-upload" ></i></a>
                    <a class="pull-right btn btn-success btn-xs" onclick="launchEditUnitForm(${unit.id},'${pageContext.request.contextPath}')" role="button"><i class="fa fa-pencil"></i></a>
                </li>
            </c:forEach>
         </ul>
    </div>
   
</div><!-- /. box -->


<!--MODAL-->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog form-modal" style="width:750px !important;">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">${project.name}</h4>
            </div>
            <div class="modal-body" style="padding-top:0px;">
                <%@ include file="unitform.jsp" %>
            </div>
<!--            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="" class="btn btn-primary">OK</button>
            </div>-->
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
      
      
<!--MODAL-->
      <div class="modal fade " id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog delete-modal">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE</h4>
            </div>
            <div class="modal-body">
              <p>Are you sure you want to delete?</p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="" class="btn btn-primary">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
    <div class="modal fade " id="ProjectUnitImageUpload" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog ">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Project Unit Image</h4>
            </div>
            <div class="modal-body">
              <form method="POST" action="${pageContext.request.contextPath}/ProjectUnit?action=imageUpload" enctype="multipart/form-data">
              <div class="form-group marginbottom10 margintop10">
            <label for="Image" class="col-sm-4 control-label">Upload Unit Image</label>
            <div class="col-sm-8">
                <input type="file" name="Image" id="Image" class="form-control" accept="image/gif, image/jpeg, image/png, image/bmp">
                <input type="hidden" name="imgUnitId" id="imgUnitId" class="form-control" >
            </div>
             </div>
             <div class="box-footer text-center marginbottom10">
              <button type="submit" role="button" class="btn btn-primary pull-right " >Submit</button>
              <span id="loadingImage" class="hidden">
                <small>Saving... &nbsp;&nbsp;&nbsp;</small> 
                <img src="${pageContext.request.contextPath}/images/uploadProgress.gif" width="80" height="15" />
             </span>
           </div>     
              </form>  
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
         
        <c:if test="${unitSuccess}">
        <div class="modal fade" id="successModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Project Unit Image</h4>
            </div>
            <div class="modal-body">
                Project Unit Image Uploaded Successfully
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary pull-right" data-dismiss="modal">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
        </div><!-- /.modal -->   
            <script>
               $( function(){ $("#successModal").modal(); }); 
                </script>
        <c:remove scope="session" var="success"  />
        </c:if> 
                
        <div class="modal fade" id="imageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" >
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title" id="imageType" ></h4>
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
        
        <!--MODAL-->
      <div class="modal fade" id="errorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Project Unit Validation Error</h4>
            </div>
            <div class="modal-body">
             
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
<script>
    var formData = [  ['quantity' , 'Quantity' , true , -1]
                     ,['cpu' , 'Sales proceed' , true , -1]
                    ,['building_cost' , 'Building Cost', false , -1]
                    ,['service_value' , ' Service Value' , false ,-1]
                    ,['discount' , ' Discount', false , 100]
                    ,['commp' , 'Agent Commission', false , 100]
                    ,['amp' , 'Agent Annual Maintenace', false , 100]
                    ,['vatp' , 'Value Added Tax', true , -1]
                    ,['reward_points' , 'Reward Point', false , 100]
                    //,['amt_payable' , 'Ammount Payable']
                    // ,['monthly_pay' , 'Montly Payment' , false , -1]
                   // ,['income' , 'Income']
                    ,['lid' , 'Least Initial Payment' , true , -1]
                    ,['mpd' , 'Montly Payment Duration' , false , -1]];
                
    function launchEditUnitForm(unitId, context){
        $.get(context+'/ProjectUnit',{action:'edit',id:unitId},function(response){
                                //console.log(response);
                                unit = JSON.parse(response);
                                $('#title').val(unit.title);
                                
                                $('#cpu').val(parseFloat(unit.cpu).toFixed(2)); 
                                showElement('cpuFormat' , parseFloat(unit.cpu).toFixed(2));
                                
                                $('#building_cost').val(parseFloat(unit.building_cost).toFixed(2));
                                showElement('building_costFormat' , parseFloat(unit.building_cost).toFixed(2));
                                
                                $('#service_value').val(parseFloat(unit.service_value).toFixed(2));
                                showElement('service_valueFormat' , parseFloat(unit.service_value).toFixed(2));
                                
                                $('#income').val(parseFloat(unit.income).toFixed(2));
                                showElement('incomeFormat' , parseFloat(unit.income).toFixed(2));
                                
                                $('#lid').val(parseFloat(unit.lid).toFixed(2));
                                showElement('lidFormat' , parseFloat(unit.lid).toFixed(2));
                                
                                $('#discount').val(unit.discount);
                                $('#mpd').val(unit.mpd);
                                $('#commp').val(unit.commp);
                                $('#vatp').val(unit.vatp);
                                $('#amp').val(unit.amp);
                                $('#reward_points').val(unit.reward_points);
                                $('#quantity').val(unit.quantity);

                                $('#monthly_pay').val(parseFloat(unit.monthly_pay).toFixed(2));
                                showElement('monthly_payFormat' , parseFloat(unit.monthly_pay).toFixed(2));
                                
                                $('#amt_payable').val(parseFloat(unit.amt_payable).toFixed(2));
                                showElement('amt_payableFormat' , parseFloat(unit.amt_payable).toFixed(2));

                                $('#unittype').val(unit.unit_type_id);
                                $('#loading').addClass("hidden");
        });
        
        $('#myModal #id').val(unitId);
        $('#myModal .title-text').html('Edit Unit');
        $('#loading').removeClass("hidden");
        $('#myModal').modal();
    }
    
    function uploadUnitImage(id){
        $("#imgUnitId").val(id);
        $("#ProjectUnitImageUpload").modal();
    }

    function showImage(url , imageType){
        if(imageType == 1)
        {
            $("#imageType").text("Project Unit Image")
        }
        else
        {
             $("#imageType").text("Project Image")
        }
        
        $("#ImageUrl").attr('src', "/uploads/NeoForce/images/" + url);
         $("#imageModal").modal();
    }

    function newUnit(){
        $('#myModal').modal({keyboard:false,backdrop:false}); 
        $('.title-text').html('New Unit');
        $("#quantity").val(0);
        $("#cpu").val(0);
        $("#cpuFormat").text("#0");
        $("#service_value").val(0);
        $("#service_valueFormat").text("#0");
        $("#discount").val(0);
        $("#amt_payable").val(0);
        $("#amt_payableFormat").text("#0");
        $("#lid").val(0);
        $("#lidFormat").text("#0");
        $("#mpd").val(0);
        $("#mpdFormat").text("#0");
        $("#monthly_pay").val(0);
        $("#monthly_payFormat").text("#0");
        $("#income").val(0);
        $("#incomeFormat").text("#0");
        $("#reward_points").val(0);
        $("#commp").val(0);
        $('#amp').val(0);
        $("#vatp").val(5);
        $("#building_cost").val(0);
        $("#building_costFormat").text("#0"); 
    }
    
    function validate(){
         $("#errorModal .modal-body").html("");
         //$("#errorModal .modal-body").append(errorText);
         //$("#errorModal").modal();
         var datalen = formData.length;
         for( var i =0 ; i < datalen; i++ )
        {
              var temp = $("#"+formData[i][0]).val();//get The input id value
              if(!temp || !$.isNumeric(temp))
              {
                  openModal("Please Enter a valid value for " + formData[i][1]);
                  return false;
              }
              else
              {
                  if(temp < 0 )
                  {
                  openModal(formData[i][1] + " can not be a negative value");
                  return false; 
                  }
                  
                  if(formData[i][3] != -1 && temp > formData[i][3])
                  {
                  openModal(formData[i][1] + " can not be more than 100 percentage");
                  return false;   
                  }
                  
                  if(formData[i][2] && temp==0)
                  {
                 openModal(formData[i][1] + " can not have a zero value");
                  return false;
                  }
                  
              }
        }
        var ammountPayable = $("#amt_payable").val();
        var lid = $("#lid").val();
        var duration = $("#mpd").val();
        if(ammountPayable != lid)
        {
           if(lid > ammountPayable) 
           {
              openModal("Least Initial Deposit cannot be more than ammount payable");
              return false; 
           }
           if(duration <=0)
           {
            openModal("Montly duration can not be Zero");
              return false;   
           }
        }
        
        var title = $("#title").val();
        if(!title)
        {
         openModal("Project Unit Title cannot be empty");
          return false;  
        }
        else if($.isNumeric(title))
        {
        openModal("Project Unit Title cannot be a Number");
          return false; 
        }
        
        var unittype = $("#unittype").val();
        if(unittype <= 0)
        {
            openModal("Please Select a Project Unit Type from Dropdown");
          return false; 
        }
        
        return true;
    }
    
    function openModal(message){
         $("#errorModal .modal-body").append(message);
             $("#errorModal").modal(); 
    }
</script>