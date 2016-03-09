/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/* global data */
 $( document ).ready(function() {
        $( "#pwBankdeposit").hide();
        $("#pwCard" ).hide();
        $("#pwCash").hide();
        $("#paymentCheckout").hide();
        setTimeout(function(){
   agreementStatusChecked();
}, 450);
       
        calculateSum();
    });
    
    
function deleteEntity(appName, entityName, id){
    url = appName + '/' + entityName;
    console.log("URL: " + url);
    
    $.ajax({
       type : 'GET',
       url : url,
       data : {id:id, action:'delete'},
       success: function(data){
           console.log("Delete Successful");
           $('#deleteModal').modal('hide');
           $('#row'+id + ',#row'+id + ' td').addClass("deleting");
           $('#row'+id).fadeOut(1500);
       },
       error: function(){
           console.log("Delete NOT Successful");
       }
    });
}



function calculateSum(){
    var sum = 0;
// iterate through each td based on class and add the values
$(".payOut").each(function() {

    var value = $(this).text();
    // add only if the value is number
    if(!isNaN(value) && value.length != 0) {
        sum += parseFloat(value);
    }
});
    if(sum==0){
       $("#checkOutToPay").attr("disabled",true);
    }else{
       $("#checkOutToPay").attr("disabled",false);
    }
    $("#cartSum").text(sum);
    
}
function addToCart(event){
   $("#shoppingCart:hidden").toggle();
   $("#paymentCheckout:visible").toggle();
  var productName = $("#selectProduct :selected").text();
  var productId = $("#selectProduct").val();
  var productUnitName = $("#selectUnit :selected").text();
  var productUnitId = $("#selectUnit").val();
  var productQuantity = $("#selectQuantity").val();
  var productAmount = $("#productAmount").val();
  var amountUnit = $("#amountUnit").text();
  var amountTotalUnit = $("#amountTotalUnit").text();
  var initialAmountPerUnit = $("#initialAmountPerUnit").text();
  var minInitialAmountSpan = $("#minInitialAmountSpan").text();
  var productMinimumInitialAmount = $("#productMinimumInitialAmount").val();
  var amountLeft = $("#amountLeft").val();
  var payDurationPerUnit = $("#payDurationPerUnit").text();
  var payDurationPerQuantity = $("#payDurationPerQuantity").text();
  var productMaximumDuration = $("#productMaximumDuration").val();
  var monthlyPayPerUnit = $("#monthlyPayPerUnit").text();
  var monthlyPayPerQuantity = $("#monthlyPayPerQuantity").text();
  var productMinimumMonthlyPayment = $("#productMinimumMonthlyPayment").val();
  
  //alert(productName+" "+productId+" "+productUnitName+" "+productUnitId+" "+productQuantity);
  
//  var dataArray = [];
//  dataArray.push(productName,productId,productUnitName,productUnitId,productQuantity,productAmount,amountUnit,amountTotalUnit,
//  initialAmountPerUnit,minInitialAmountSpan,productMinimumInitialAmount,amountLeft,payDurationPerUnit,payDurationPerQuantity,productMaximumDuration,monthlyPayPerUnit,monthlyPayPerQuantity,productMinimumMonthlyPayment);
//  
  
  var dataArray = {productName:productName, productId: productId,productUnitName:productUnitName,productUnitId:productUnitId,productQuanity:productQuantity,productAmount:productAmount,amountUnit:amountUnit,amountTotalUnit:amountTotalUnit,
  initialAmountPerUnit:initialAmountPerUnit,minInitialAmountSpan:minInitialAmountSpan,productMinimumInitialAmount:productMinimumInitialAmount,amountLeft:amountLeft,payDurationPerUnit:payDurationPerUnit,payDurationPerQuantity:payDurationPerQuantity,
  productMaximumDuration:productMaximumDuration,monthlyPayPerUnit:monthlyPayPerUnit,monthlyPayPerQuantity:monthlyPayPerQuantity,productMinimumMonthlyPayment:productMinimumMonthlyPayment}
  var jsonData = JSON.stringify(dataArray);
  //alert(jsonData);
  var id = $('#productCart tr:last').attr('id');
  if(id == null){
      id = 0;
  }
  if(id < 1 || isNaN(id)){
      id = 0;
  }
  
  if($("#editMode").val()=="" || $("#editMode").val()== null){
          var newId = parseInt(id) + 1;
      }else{
          var newId = $("#editMode").val();
      }
 // alert(id);
 
  //alert(newId);
  var buttonsData = '<a class="btn btn-primary btn-xs" href="#" role="button" onclick="return editDataFromCart('+newId+')" title="Edit product details"><i class="fa fa-pencil"></i></a>\n\
<a class="btn btn-danger btn-xs" href="#" title="Remove product from cart"  onclick="return showDeleteCartModal('+newId+')" role="button"><i class="fa fa-remove"></i></a>';

  //var newId = id + 1;
  var dataTr = "<tr id='"+newId+"' align='left'>";
      dataTr += "<td>"+productName+"</td>";
      dataTr += "<td>"+productUnitName+"</td>";
      dataTr += "<td>"+productQuantity+"</td>";
      dataTr += "<td>"+productAmount+"</td>";
      dataTr += "<td>"+productMinimumInitialAmount+"</td>";
      dataTr += "<td>"+amountLeft+"</td>";
      dataTr += "<td>"+productMaximumDuration+"</td>";
      dataTr += "<td>"+productMinimumMonthlyPayment+"</td>";
      dataTr += "<td class='payOut'>"+productMinimumInitialAmount+"</td>";
      
      var rowId ="tr"+newId;
      
      dataTr +="<td><input type='hidden' id='"+rowId+"' value='"+jsonData+"'/>"+buttonsData+"</td>";
      //alert($("#editMode").val());
    //$("#" + id).html();
      $("#productCart tbody").focus();
         if($("#editMode").val()==""){
          //no be edit mode
         // alert("This is where the data is lauched into the database");
       $("#productCart tbody").append(dataTr);
      }else{
          //alert("We do update the padi padi here");
          //na edit mode we dey
          
          $("tr#"+newId).replaceWith(dataTr);
      }
      
      $('#'+newId + ',#'+newId + ' td').addClass("adding",1000,"easeInOutBack");
      $('#'+newId).fadeIn(23500,function(){
          $('#'+newId + ',#'+newId + ' td').removeClass("adding");
          
      });
     // var sum = 0;
// iterate through each td based on class and add the values
     calculateSum();
//alert("The sum is "+sum);
      
      $("#editMode").val("");
     resetForm();
    // $("#editMode").val("");
     $("#selectProduct").val("");
     $("#productCart tbody").focus();
     $("#addToCart").text("");
     $("#addToCart").append("<i class='fa fa-cart-plus'></i> Add to Cart");
//      $('#'+newId).fadeIn(1500);
//      $('#'+newId + ',#'+newId + ' td').addClass("adding",1000,"easeInBack");
//$('#'+newId).fadeToggle(2000,"easeInBack",removeClass(newId));
         //  $('#'+newId).fadeIn(1500);
     
  //alert(productName);
    return false;
}
function showNecessaryMenu(pmtMethod){
    
    if(pmtMethod=="bankdep"){
        $("#pwCard:visible" ).toggle();
        $("#pwCash:visible").toggle();
         $( "#pwBankdeposit:hidden").toggle();
          
    }else if(pmtMethod=="paywithcard"){
       $("#pwCard:hidden" ).toggle();
       $("#pwCash:visible").toggle();
       $( "#pwBankdeposit:visible").toggle();
        //alert("You have selected pay online");
    }
    else if(pmtMethod=="paywithcash"){
        $("#pwCard:visible" ).toggle();
       $("#pwCash:hidden").toggle();
       $( "#pwBankdeposit:visible").toggle();
    }
}
function editDataFromCart(id){
    
    //alert("hhello");
    var datajson = $("#tr"+id).val();
    $("#editMode").val(id);
    //alert(datajson);
    var dataArray = JSON.parse(datajson);
    
    var productName =  dataArray.productName;
  var productId = dataArray.productId;
  var productUnitName = dataArray.productUnitName;
  var productUnitId = dataArray.productUnitId;
  var productQuantity = dataArray.productQuanity;
  var productAmount = dataArray.productAmount;
  var amountUnit = dataArray.amountUnit;
  var amountTotalUnit = dataArray.amountTotalUnit;
  var initialAmountPerUnit = dataArray.initialAmountPerUnit;
  var minInitialAmountSpan = dataArray.minInitialAmountSpan;
  var productMinimumInitialAmount = dataArray.productMinimumInitialAmount;
  //alert(productMinimumInitialAmount);
  var amountLeft = dataArray.amountLeft;
  var payDurationPerUnit = dataArray.payDurationPerUnit;
  var payDurationPerQuantity = dataArray.payDurationPerQuantity;
  var productMaximumDuration = dataArray.productMaximumDuration;
  var monthlyPayPerUnit = dataArray.monthlyPayPerUnit;
  var monthlyPayPerQuantity = dataArray.monthlyPayPerQuantity;
  var productMinimumMonthlyPayment = dataArray.productMinimumMonthlyPayment;
 // alert(dataArray[14]);
    
    var baseUrl = getBaseUrl().toString();
    //alert(productQuantity);
    //baseUrl.substring()
    baseUrl = baseUrl.substr(0, (baseUrl.length)-1)
//    alert(subString);
//alert(productUnitId);
    $("#addToCart").text("");
    $("#addToCart").append("<i class='fa fa-cart-plus'></i> Update Cart Item");
//    $("#addToCart").text("<i class='fa fa-cart-plus'></i> Update Cart Item");
    $("#selectProduct").val(productId);
    $("#selectUnit").val(productUnitId);
    $("#pUnitId").val(productUnitId);
    $("#productQuantity").val(productQuantity);
    $("#productAmount").val(productAmount);
    $("#amountUnit").text(amountUnit);
    $("#amountTotalUnit").text(amountTotalUnit);
    $("#initialAmountPerUnit").text(initialAmountPerUnit);
    $("#minInitialAmountSpan").text(minInitialAmountSpan);
    $("#productMinimumInitialAmount").val(productMinimumInitialAmount);
    $("#amountLeft").val(amountLeft);
    $("#payDurationPerUnit").text(payDurationPerUnit);
    $("#payDurationPerQuantity").text(payDurationPerQuantity);
    $("#productMaximumDuration").val(productMaximumDuration);
    $("#monthlyPayPerUnit").text(monthlyPayPerUnit);
    $("#monthlyPayPerQuantity").text(monthlyPayPerQuantity);
    $("#productMinimumMonthlyPayment").val(productMinimumMonthlyPayment);
    $("#addToCart").val("Update Cart");
    document.getElementById("selectUnit").value = productUnitId;
    $("#pUnitId").val(productUnitId);
    
    getProjectUnits(baseUrl, 'Project');
    
    
    //alert(productUnitId);
//    $("#selectUnit").val(productUnitId);
//    $("#selectUnit").val(productUnitId);
//    alert(productUnitId);
    getProjectQuantity(baseUrl, 'ProjectUnit');
    
    
    $("#productQuantity").val(productQuantity);
    $("#productMaximumDuration").val(productMaximumDuration);
    
    
    
    

setTimeout(function(){
    updateUnit(id)
}, 450);

  return false;
}
function updateUnit(id){
    
     var datajson = $("#tr"+id).val();
    //alert(datajson);
    var dataArray = JSON.parse(datajson);
    var productUnitId = dataArray.productUnitId;
    var productQuantity = dataArray.productQuanity;
    var productMinimumInitialAmount = dataArray.productMinimumInitialAmount;
    var productAmount = dataArray.productAmount;
    var productMaximumDuration = dataArray.productMaximumDuration;
    //alert(productMinimumInitialAmount);
    
//    var productUnitId = $("#pUnitId").val();
   //alert(productUnitId);
   
$('#selectUnit').val(productUnitId);
//getProjectQuantity(baseUrl, 'ProjectUnit');
$("#selectQuantity").val(productQuantity);
$("#productAmount").val(productAmount);
$("#productMaximumDuration").val(productMaximumDuration);
//$("#monthlyPayPerUnit").text(monthlyPayPerUnit);
$("#productMinimumInitialAmount").val(productMinimumInitialAmount);
monthlyPayCalculator();
//alert(productQuantity);
}
function getBaseUrl() {
    var re = new RegExp(/^.*\//);
    
    
    return re.exec(window.location.href);
}
function deleteDataFromCart(id){
    
    $("#productCart tbody").focus();
    
       console.log("Delete Successful");
       $('#deleteModalCart').modal('hide');
       $('#'+id + ',#'+id + ' td').addClass("deleting");
       $('#'+id).fadeOut(1000); 
       $("tr#"+id).remove();
       calculateSum();
    
    $("#productCart tbody").focus();
    return false;
}
function checkOutOfCart(){
    //allData
    
        
         calculateSum();
    var sum  = $("#cartSum").text();
    $("#paySum").text(sum);
    if(sum==0 || sum==null){
        return false;
    }
    $('#productCart > tbody').focus();
    var cartDataArray = [];
     var data = new Array();
       var dataArray  = "";
       var id = "";
       var dataCenter = [];
    $('#productCart > tbody  > tr').each(function() {
          id = $(this).attr("id");
         dataArray =$("#tr"+id).val();
         cartDataArray.push(dataArray);
         
    });
    alert(cartDataArray);
    $("#paymentCheckout:hidden").toggle();
    $("#shoppingCart:visible").toggle();
    
    
    
    
   
    return false;
}
function resetForm(){
    
   $('#selectQuantity').empty(); //select
   
$('#productMinimumInitialAmount').val("");
$("#productMinimumMonthlyPayment").val("");
$('#productMaximumDuration').empty(); //select
$('#productAmount').val("");
$("#monthlyPayPerUnit").text("");
$("#payDurationPerUnit").text("");
$("#minInitialAmountSpan").text("");
$("#initialAmountPerUnit").text("");
$("#amountTotalUnit").text("");
$("#amountUnit").text("");
 $('span[id="qty"]').text("");
$("#finalAmount").text("");
$("#amountLeft").val("");
$("#addToCart").attr("disabled",false);
$('#selectUnit').empty(); //select 
    
      $('#selectUnit').append($('<option>', {
    value: "",
    text: "-- choose --"
}));

$('#productMaximumDuration').append($('<option>', {
    value: "",
    text: "-- choose --"
}));

$('#selectQuantity').append($('<option>', {
    value: "",
    text: "-- choose --"
}));

}
function checkActivateSwitch(appName,entityName,agentId){
      
    
setTimeout(function(){
   var type;
    url = appName + '/' + entityName;
    console.log("URL: " + url);
      type = $("#row"+agentId+" [data-switch-get]").data("switch-get");
      var result = $("#row"+agentId+" #switch-" + type).bootstrapSwitch(type);
      var status;
      if(result == true){
          status = 1;
      }else{
          status = 0;
      }
      console.log("URL: " + url);
    
  
    $('#loading2').removeClass("hidden");
    
    $.ajax({
       type : 'POST',
       url : url,
       data : {updateStatus:status,agent_id:agentId},
       success: function(response){
           console.log("Successful: " + JSON.stringify(response));
           var resp = JSON.parse(response);
           alert(response);
       },
       error: function(xHr, status, error){
           console.log("NOT Successful: " + xHr.responseText);
           //processSubmitError(xHr.responseText);
       }
    });
    
}, 450);
   
}
function getProjectUnits(appName, entityName){
   // alert(punit);
    $("#addToCart").attr("disabled",false);
    var id =  $('#selectProduct').val();
   // alert("This is the project Id as the case may be"+id);
     url = appName + '/' + entityName;
    console.log("URL: " + url);
//    alert(id);
//    alert(url);
resetForm();

$("#")
    $.ajax({
       type : 'GET',
       url : url,
       data : {project_id:id, action:'punits'},
       success: function(data){
           
           //alert(data);
           var resp = JSON.parse(data);
           $('#selectUnit').empty();
           $('#selectUnit').append($('<option>', {
    value: "",
    text: "-- choose --"
}));

           $.each( resp, function( key, value ) {
              // $('#selectUnit').empty();
              //alert(value);
        
               $('#selectUnit').append($('<option>', {
    value: value.id,
    text: value.title,
    
    
}));

});

           console.log("Loading project Units Successful");

       },
       error: function(){
           console.log("Loading Project Units NOT Successful");
       }
    });
    
}


function getProjectQuantity(appName, entityName){
    $("#addToCart").attr("disabled",false);
    var id =  $('#selectUnit').val();
    if(id== null || id==""){
        id = $("#pUnitId").val();
    }
    
    
     url = appName + '/' + entityName;
    console.log("URL: " + url);
//    alert(id);
//    alert(url);
    $.ajax({
       type : 'GET',
       url : url,
       data : {id:id, action:'edit'},
       success: function(data){
           
           //alert(data);
           var resp = JSON.parse(data);
          
           
           $("#dataHidden").val(data);
           $('#selectQuantity').empty();
           
        
          
          for(var i=1;i<=resp.quantity;i++){
              // $('#selectUnit').empty();
               $('#selectQuantity').append($('<option>', {
    value: i,
    text: i
}));
          }
          calculateProductAmount();
          //monthlyPayCalculator();
       
               //alert(value.id+"--> "+value.title);


           console.log("Loading project Quantities Successful");
//           $('#deleteModal').modal('hide');
//           $('#row'+id + ',#row'+id + ' td').addClass("deleting");
//           $('#row'+id).fadeOut(1500);
       },
       error: function(){
           console.log("Loading Project Quantities NOT Successful");
       }
    });
    
}

function monthlyPayCalculator(){
    $("#addToCart").attr("disabled",false);
    var productAmount = $("#productAmount").val();
    //alert(productAmount);
    var quantity = $("#selectQuantity").val();
    var productMinimumInitialAmount = $("#productMinimumInitialAmount").val();
    //alert(productMinimumInitialAmount);
    var productMaximumDuration = $("#productMaximumDuration").val();
    //alert(productMaximumDuration);
    var payLeft = productAmount - productMinimumInitialAmount;
    $("#amountLeft").val(payLeft);
    var monthlyPay = payLeft / productMaximumDuration;
    $("#productMinimumMonthlyPayment").val(monthlyPay);
    $("#monthlyPayPerUnit").text(monthlyPay * 1);
    $("#monthlyPayPerQuantity").text(monthlyPay * quantity);
    calculateAmountToPay();
}

function calculateAmountToPay(){
   // alert("we are working here");
   
    $("#addToCart").attr("disabled",false);
    var userInitialAmount = $("#productMinimumInitialAmount").val();
     var data = $("#dataHidden").val();
    //alert(data);
    var resp = JSON.parse(data);
    var quantity = $("#selectQuantity").val();
//    alert(quantity);
//    alert(userInitialAmount);
    var lid = resp.lid;  
    var leastInitialDeposit = lid * quantity;
    //alert(leastInitialDeposit);
    
    var productAmount = $("#productAmount").val();
    if(userInitialAmount<leastInitialDeposit){
        //alert("The Initial Deposit must not be less than the default minimum initial amount specified by the system i.e ( N"+leastInitialDeposit+")");
        //jQuery.data( $("#errorText"), "errorDInitDep", "The Initial Deposit must not be less than the default minimum initial amount specified by the system i.e ( N"+leastInitialDeposit+")" );
        if($("#errorInitDep").length <= 0){
        var htmlMessage = $('<p id="errorInitDep">The Initial Deposit must not be less than the default minimum initial amount specified by the system i.e ( N'+leastInitialDeposit+')</p>')
        $("#errorText").append(htmlMessage);
    }
        //$("#errorText").text("<p id='errorInitDep'>The Initial Deposit must not be less than the default minimum initial amount specified by the system i.e ( N"+leastInitialDeposit+")</p>");
    
            $("#addToCart").attr("disabled",true);
        $("#productMinimumInitialAmount").focus();
    }else{
        $("#errorInitDep").remove();
        $("#errorText").text("");
        $("#amountLeft").val(productAmount - userInitialAmount);
        calculateDurationFromMonthlyPay();
    }
    
    
}
function calculateDurationFromMonthlyPay(){
    var data = $("#dataHidden").val();
    $("#addToCart").attr("disabled",false);
    var resp = JSON.parse(data);
    var duration = resp.mpd;
    
    
    var quantity = $("#selectQuantity").val();
    
    var quantityDuration = duration ;
    $("#finalAmount").text("");
    var productMinimumMonthlyPayment = $("#productMinimumMonthlyPayment").val();
    var amountLeft = $("#amountLeft").val();
    var monthLeft = Math.ceil(amountLeft / productMinimumMonthlyPayment);
    
    var finalAmount = amountLeft % productMinimumMonthlyPayment ;
  //  alert("hello this is where we work month-->"+monthLeft+" finalAmount "+ quantityDuration);
    
    if(monthLeft>quantityDuration){
       // alert("The duration has exceeded the maximum duration considering the monthly pay inputed ");
//       $("#errorText").append()
   //    jQuery.append( $("#errorText"), "errorDuration", "The duration has exceeded the maximum duration considering the monthly pay inputed" );
   if($("#errorDuration").length <= 0){
        var htmlMessage = $('<p id="errorDuration">The duration has exceeded the maximum duration considering the monthly pay inputed </p>')
        $("#errorText").append(htmlMessage);
    }
            $("#addToCart").attr("disabled",true);
        $("#productMinimumMonthlyPayment").focus();
       //alert($("#addToCart").val());
        
    }else{
        //$("#errorText").a
        $("#errorDuration").remove();
        $("#productMaximumDuration").val(monthLeft);
        if(finalAmount>0){
        $("#finalAmount").text("Final Month Amount: N "+finalAmount);
        }
    }
   
}
function calculateDiscountPerUnit(){
//  var data = $("#dataHidden").val();
//    //alert(data);
//    var resp = JSON.parse(data);
//    var quantity = $("#selectQuantity").val();
//    var lid = resp.lid;  
//    var discount = resp.discount;
//    var totalDiscount = discount * quantity;
//  
//    var defaultDiscount = lid * quantity;
}
function calculateProductAmount(){
    var data = $("#dataHidden").val();
    //alert(data);
    var resp = JSON.parse(data);
    
    var cpu = resp.cpu;
    //alert(cpu);
    var quantity = $("#selectQuantity").val();
    var duration = resp.mpd;
    //$( quantity ).prependTo( "#qty" );
    //alert("The quantity is "+quantity);
    $('span[id="qty"]').text( quantity);
    var discount = resp.discount;
    var totalDiscount = 0;
    if(discount>0){
        totalDiscount = discount * quantity;
    }
    //var totalDiscount = discount * quantity;
    //alert(quantity);
    var amount = quantity * cpu;
    var mpd = resp.mpd;
    var defaultDiscount = 0;
   
    
  defaultDiscount = (cpu * 1)- ((discount/100)*cpu);
    
    var totalDiscountAmount = 0;
    if(totalDiscount>0){
      totalDiscountAmount = amount * (totalDiscount/100);
    }
    var totalDuration  = duration ;
    var durationPerUnit = resp.mpd;

    var finalAmount = amount - totalDiscountAmount;
    $("#amountUnit").text(defaultDiscount);
    $("#amountTotalUnit").text(finalAmount)
    $("#productAmount").val(finalAmount);
    $("#initialAmountPerUnit").text(resp.lid *1);
    $("#minInitialAmountSpan").text(resp.lid * quantity);
    var durationPerUnit = durationPerUnit * 1;
    var alertMessage = "month";
    if(durationPerUnit>1){
        alertMessage = "months";
    }
    $("#payDurationPerUnit").text(durationPerUnit+" "+alertMessage);
     var durationPerQuantity = durationPerUnit * quantity;
    
    //var alertMessage = "month";
    if(durationPerQuantity>1){
        alertMessage = "months";
    }
    
    $("#payDurationPerQuantity").text(durationPerQuantity+" "+alertMessage);
    
    $("#productMinimumInitialAmount").val( resp.lid * quantity);
  
    
    
    //alert(totalDuration);
     var message = "month";
     
       $('#productMaximumDuration').empty();
           
           $('#productMaximumDuration').append($('<option>', {
    value: "",
    text: "-- choose --"
}));
          
          var message = "month";
          for(var i=1;i<=totalDuration;i++){
              // $('#selectUnit').empty();
              if(i>1){
                  message = "months";
              }
              
               $('#productMaximumDuration').append($('<option>', {
    value: i,
    text: i+" "+message,
    selected: true
}));
$(this).attr("selected","selected");

          }
          
   
    
    monthlyPayCalculator();
    
}
function modal_agree(){
    $("#agree").attr("checked",true);
    $('input[id="agentCreate"]').attr( "disabled",false);
    $('#agreementStatusModal').modal('hide');
    
}
function agreementStatusChecked(element){
    //alert(status);
    var button = $(element).val();
   
//    $("#agentForm input[name=agreement_document]").val();
//    alert("This is the agent id "+$("#agent_id").val());
//     alert("This is the agent id "+$("#id").val());
   if(element==null){
       if(($("#agent_id").val())!="" && ($("#agent_id").val())!=null){
//           alert("in the false place");
//           alert($("#agent_id").val());
      $('input[id="agentCreate"]').attr( "disabled",false);
  }else{
//      alert($("#agent_id").val());
//      alert("This is the operable thingy");
      $('input[id="agentCreate"]').attr( "disabled",true);
  }
    }
    else if(button=="agree"){
        $('input[id="agentCreate"]').attr( "disabled",false);
    }
    else{
        $('input[id="agentCreate"]').attr( "disabled",true);
    }
   
}
function showDeleteModal(context, entityName, id){    
    var args = "deleteEntity('" + context + "'," + "'" + entityName + "'," + "'" + id + "')";
    $("#deleteModal #ok").attr("onclick", args);
    $('#deleteModal').modal();
}

function showDeleteCartModal( id){    
    var args = "deleteDataFromCart("+id+")";
    $("#deleteModalCart #ok").attr("onclick", args);
    $('#deleteModalCart').modal();
}

function submitgetForm(url,formData){
     console.log("URL: " + url);
    
    console.log("Data: " + formData);
    $('#loading2').removeClass("hidden");
    $.ajax({
       type : 'GET',
       url : url,
       data : formData,
       success: function(response){
           console.log("Successful: " + JSON.stringify(response));
           var resp = JSON.parse(response);
           
           if(resp.STATUS == "OK")
               updateSubmitSuccess(response);
           else if(resp.STATUS == "ERROR")
               updateSubmitError(response);
       },
       error: function(xHr, status, error){
           console.log("NOT Successful: " + xHr.responseText);
           //processSubmitError(xHr.responseText);
       }
    });
}

function submitPostForm(url, formData){
    //url = appName + '/' + entityName;
    //$('#projectunitform').serialize()
    console.log("URL: " + url);
    
    console.log("Data: " + formData);
    $('#loading2').removeClass("hidden");
    
    $.ajax({
       type : 'POST',
       url : url,
       data : formData,
       success: function(response){
           console.log("Successful: " + JSON.stringify(response));
           var resp = JSON.parse(response);
           
           if(resp.STATUS == "OK")
               processSubmitSuccess(response);
           else if(resp.STATUS == "ERROR")
               processSubmitError(response);
       },
       error: function(xHr, status, error){
           console.log("NOT Successful: " + xHr.responseText);
           //processSubmitError(xHr.responseText);
       }
    });
}