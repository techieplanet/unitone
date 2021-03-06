<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<form name="projectunitform1" id="projectunitform" method="POST" action="ProjectUnit" class="form-horizontal" role="form" >
    <div class="">
      <div class="box-header with-border">
        <h3 class="box-title">
            <span class="title-text"></span>
            
                    
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
              

          <div class="form-group marginbottom10">
            <label for="title" class="col-sm-4 control-label">Unit Title*</label>
            <div class="col-sm-8">
                <input type="text" name="title" id="title" class="form-control" value="">
            </div>
          </div>
          <div class="form-group marginbottom10">
            <label for="quantity" class="col-sm-4 control-label">Quantity Available*</label>
            <div class="col-sm-7">            
                <input type="text" name="quantity" id="quantity" class="form-control medium text-right" value="">
            </div>
          </div>

          <div class="form-group marginbottom10">
            <label for="unittype" class="col-sm-4 control-label">Unit Type*</label>
            <div class="col-sm-7">      
                <select class="form-control" name="unittype" id="unittype">
                    <option value="0" selected="selected">--Select--</option>
                    <c:forEach items="${unitTypes}" var="unitType">
                        <option value="${unitType.id}">
                            ${unitType.title}
                        </option>
                    </c:forEach>
                </select>
            </div>
          </div>
          <div class="form-group marginbottom10 ">
            <label for="cpu" class="col-sm-4 control-label">Selling Price*</label>
            <div class="col-sm-8">
                <input type="text" name="cpu" id="cpu" class="col-sm-6 text-right" value="" onfocus="showElement('cpuFormat' , this.value)" onkeyup="formatAmount('cpuFormat' , this.value)">
                <span id="cpuFormat" class=" col-sm-6 text-right" ></span>
            </div>
            </div>
          <div class="form-group marginbottom10">
            <label for="building_cost" class="col-sm-4 control-label">Building/Land Cost*</label>
            <div class="col-sm-8"> 
                <input type="text" name="building_cost" id="building_cost" class="col-sm-6 text-right" value="" onfocus="showElement('building_costFormat' , this.value)" onkeyup="formatAmount('building_costFormat' , this.value)">
               <span  id="building_costFormat" class=" col-sm-6 text-right"></span>
            </div>
          </div>
          
          <div class="form-group marginbottom10">
            <label for="service_value" class="col-sm-4 control-label">Service Value*</label>
            <div class="col-sm-8">            
                <input type="text" name="service_value" id="service_value" class="col-sm-6 text-right text-right" value="" onfocus="showElement('service_valueFormat' , this.value)" onkeyup="formatAmount('service_valueFormat' , this.value)">
                <span  id="service_valueFormat" class="col-sm-6 text-right" ></span>
            </div>
          </div>
          
          <div class="form-group marginbottom10">
            <label for="discount" class="col-sm-4 control-label">Discount Percentage*</label>
            <div class="col-sm-7">            
                <input type="text" name="discount" id="discount" class="form-control medium text-right" value="">
            </div>
          </div>
          
          <div class="form-group marginbottom10">
            <label for="commp" class="col-sm-4 control-label">Commission Percentage*</label>
            <div class="col-sm-7">            
                <input type="text" name="commp" id="commp" class="form-control medium text-right" value="">
            </div>
          </div>
           <div class="form-group marginbottom10">
            <label for="amp" class="col-sm-4 control-label">Agent Annual Maintenance Percentage*</label>
            <div class="col-sm-7">            
                <input type="text" name="amp" id="amp" class="form-control medium text-right" value="">
            </div>
          </div>
          
          <div class="form-group marginbottom10">
            <label for="vatp" class="col-sm-4 control-label">V.A.T Percentage*</label>
            <div class="col-sm-7">            
                <input type="text" name="vatp" id="vatp" class="form-control medium text-right" value="5.0">
            </div>
          </div>
          
          <div class="form-group marginbottom10">
            <label for="reward_points" class="col-sm-4 control-label">Reward Points</label>
            <div class="col-sm-7">            
                <input type="text" name="reward_points" id="reward_points" class="form-control medium text-right" value="">
            </div>
          </div>
          
          
          
          <div class="form-group marginbottom10">
            <label for="income" class="col-sm-4 control-label">Income</label>
            <div class="col-sm-8">            
                <input type="text" name="income" id="income" readonly="readonly" class="col-sm-6 text-right" value="">
                <span  id="incomeFormat" class="col-sm-6 text-right" ></span>
            </div>
          </div>
          
          <br/>
          <h4 style="border-bottom: 1px solid #cccccc;">Payment</h4>
          <div class="form-group marginbottom10">
            <label for="amt_payable" class="col-sm-4 control-label">Amount Payable*</label>
            <div class="col-sm-8">            
                <input type="text" name="amt_payable" id="amt_payable" readonly="readony" class="col-sm-6 text-right" value="" >
                <span  id="amt_payableFormat" class="col-sm-6 text-right "></span>
            </div>
          </div>
          
          <div class="form-group marginbottom10">
            <label for="lid" class="col-sm-4 control-label">Least Initial Deposit*</label>
            <div class="col-sm-8">            
                <input type="text" name="lid" id="lid" class="col-sm-6 text-right" value="" onfocus="showElement('lidFormat' , this.value)" onkeyup="formatAmount('lidFormat' , this.value)">
                <span  id="lidFormat" class="col-sm-6 text-right"></span>
            </div>
          </div>
          
          <div class="form-group marginbottom10">
            <label for="mpd" class="col-sm-4 control-label">Max. Payment Duration*</label>
            <div class="col-sm-7">            
                <input type="text" name="mpd" id="mpd" class="form-control medium text-right" value="">
            </div>
          </div>
          
          <div class="form-group marginbottom10">
            <label for="mpd" class="col-sm-4 control-label">Monthly Payment*</label>
            <div class="col-sm-8">            
                <input type="text" name="monthly_pay" id="monthly_pay" readonly="readony" class="col-sm-6 text-right" value="" >
                <span  id="monthly_payFormat" class="col-sm-6 text-right" ></span>
            </div>
          </div>
          
          

        </div>
            
          <input type="hidden" name="projectid" id="projectid" value="${project.id}">
          <input type="hidden" name="id" id="id" value="">

          <div class="box-footer text-center">
              <a role="button" class="btn btn-default pull-left" onclick="$('#myModal').modal('hide'); return false;" >Close</a>
              <a role="button" class="btn btn-primary pull-right" onclick="sendData();return false;">Save</a>
              <span id="loading2" class="hidden" >
                <small>Saving... &nbsp;&nbsp;&nbsp;</small> 
                <img src="${pageContext.request.contextPath}/images/uploadProgress.gif" width="80" height="15" />
            </span>
           </div>
      </div>

</form>
          
          
      
<script>

    function showElement(element , value){
        $('#' + element).text("# "+ accounting.formatNumber(value , 2));
    }
    
    function formatAmount(element , value ){
        $('#' + element).text("# "+ accounting.formatNumber(value , 2));
    }
    
    $(document).ready(function(){
        
        //on key up for diacount and cost per unit, calculating amount payable
        $("#discount, #cpu, #lid, #mpd").on("change", function(){
            
            var cpu = parseFloat($("#cpu").val());
            cpu = !isNaN(cpu) ? cpu : 0; 
            $("#cpu").val(cpu);
            
            var discount = parseFloat($("#discount").val());
            discount = !isNaN(discount) ? discount : 0; 
            $("#discount").val(discount);
            
            var amt_payable = cpu - (discount/100*cpu);
            $("#amt_payable").val(amt_payable.toFixed(2));
            $("#amt_payableFormat").text("# "+ accounting.formatNumber(amt_payable , 2));
            
//            var amt_payable = parseFloat($("#amt_payable").val());
//            amt_payable = !isNaN(amt_payable) ? amt_payable : 0; 
//            $("#amt_payable").val(amt_payable);

            var lid = parseFloat($("#lid").val());
            lid = !isNaN(lid) ? lid : 0; 
            $("#lid").val(lid);
            $("#lidFormat").text("# "+ accounting.formatNumber(lid , 2));
            
            var mpd = parseInt($("#mpd").val());
            mpd = !isNaN(mpd) ? mpd : 0;
            $("#mpd").val(mpd);
            $("#mpdFormat").text("# "+accounting.formatNumber(mpd , 2));
            
            var monthly_pay = (amt_payable - lid) / mpd;
            monthly_pay = isFinite(monthly_pay) ? monthly_pay : 0;
            $("#monthly_pay").val(monthly_pay.toFixed(2));
            $("#monthly_payFormat").text("# "+ accounting.formatNumber(monthly_pay , 2));
            
            console.log("cpu: " + cpu, "discount: " + discount, "amt: " + amt_payable);
            console.log("lid " + lid, "mpd " + mpd, "amt_payable " + amt_payable, "monthly_pay: " + monthly_pay);
        });
        
        
        
        //this is used to calculate the income on keyup of relevant fields
        $("#discount, #cpu, #building_cost, #service_value, #commp, #vatp").on("blur", function(){
            var cpu = parseFloat($("#cpu").val());
            cpu = !isNaN(cpu) ? cpu : 0; 
            $("#cpu").val(cpu);
            
            var discountPercentage = parseFloat($("#discount").val());
            discountPercentage = !isNaN(discountPercentage) ? discountPercentage : 0; 
            $("#discount").val(discountPercentage);
            var discountValue = cpu * discountPercentage / 100;
            
            var buildingCost = parseFloat($("#building_cost").val());
            buildingCost = !isNaN(buildingCost) ? buildingCost : 0; 
            $("#building_cost").val(buildingCost);
            
            var serviceValue = parseFloat($("#service_value").val());
            serviceValue = !isNaN(serviceValue) ? serviceValue : 0; 
            $("#service_value").val(serviceValue);
            
            var commissionPercentage = parseFloat($("#commp").val());
            commissionPercentage = !isNaN(commissionPercentage) ? commissionPercentage : 0; 
            $("#commp").val(commissionPercentage);
            
            var VATPercentage = parseFloat($("#vatp").val());
            VATPercentage = !isNaN(VATPercentage) ? VATPercentage : 0; 
            $("#vatp").val(VATPercentage);
            
            var AMPercentage = parseFloat($("#amp").val());
            AMPercentage = !isNaN(AMPercentage) ? AMPercentage : 0; 
            $("#amp").val(AMPercentage);
            
            var rewardPoints = parseFloat($("#reward_points").val());
            rewardPoints = !isNaN(rewardPoints) ? rewardPoints : 0; 
            $("#reward_points").val(rewardPoints);
            
            //Vat should be calculated after deducting service value 
            var realCost = cpu - (serviceValue +discountValue);
            
            var VATValue = ((costMinusServiceValue) * VATPercentage / (100 + VATPercentage) );
            
            //var AMValue = (cpu * AMPercentage / 100);
            var commissionValue = ((costMinusServiceValue-VATValue) * commissionPercentage / 100);
            //AMValue = (commissionValue * AMPercentage / 100);
            //commissionValue -= AMValue;
            
            
           var income = cpu - (discountValue +buildingCost + serviceValue + commissionValue + VATValue) ;//- AMValue;
           
            income = !isNaN(income) ? income : 0; 
            $("#income").val(income.toFixed(2));
            $("#incomeFormat").text("# "+ accounting.formatNumber(income , 2));
            
        });
        
        
    });
    

    $('#myModal').on('hidden.bs.modal', function (e) {
        //console.log('Modal hiding');
        $('#projectunitform .form-control, #id').val("");
        $('#success-box-wrapper, #error-box-wrapper, #loading2').addClass("hidden");
     });
     
    function sendData(){        

//        var dataObject = {};
//        dataObject.title = $('#title').val();
//        dataObject.cpu = $('#cpu').val();
//        dataObject.lid = $('#lid').val();
//        dataObject.discount = $('#discount').val();
//        dataObject.mpd = $('#mpd').val();
//        dataObject.commp = $('#commp').val();
//        dataObject.id = $('#id').val();
//        dataObject.projectid = $('#projectid').val();

        
      //  console.log("send data: " + $('#projectunitform').serialize());
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
                        '<span class="marginleft5"><a href="#" style="text-decoration:underline" onclick="showImage(\'\' , 1)" >view image</a></span>'+
                        '<a class="pull-right btn btn-danger btn-xs marginleft5" href="#" onclick="showDeleteModal('+ "'/NeoForce', 'ProjectUnit'," + result.UNIT_ID + ')" role="button"><i class="fa fa-remove"></i></a>' +
                        '<a class="pull-right btn btn-success btn-xs marginleft5" onclick="uploadUnitImage('+ result.UNIT_ID +')" role="button"><i class="fa fa-upload" ></i></a>'+
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