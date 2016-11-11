/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var orderTable = null;
var cid = 1;
var countId = 1;
var cartData = {};

function selectCustomer(contextPath,id) {
   $("#customerListContainer:visible").toggle();
   $("#customerDetailContainer:visible").toggle();
   populateCustomerDetails(id);
    startLoading();
   
    var url = contextPath + "/Lodgement"; 
    $.ajax({
       type : 'GET',
       url : url,
       data : {customerId:id, action:'getOrders'},
       success: function(data){
           
           console.log(data);
           
           prepareOrderListTable(data);
       },
       error: function(){
           console.log("NOT Successful");
           alert("something went wrong");
            stopLoading("#customerListContainer:hidden");
       }
    });
}



function prepareOrderListTable(jsonString){
    
    $("#accordion").html("");
    
    refreshCartDetails();
    
    var orders = JSON.parse(jsonString);
    var count = 1;
    
    for(var k in orders) {
        
        var rows = "";
        
        var orderId = orders[k].id;
        var agentName = orders[k].agentName;
        var customerName = orders[k].customerName;
        
        var sales = JSON.parse(orders[k].sales);
        
        //Skip if orderItems is empty
        if(sales.length < 1){
            continue;
        }
        
        for(var j in sales) {
            
            var project = sales[j].project;
            var unitName = sales[j].unitName;
            var initialDeposit = parseFloat(sales[j].initialDeposit);
            var unitQty = sales[j].unitQty;
            var monthlyPay = sales[j].monthlyPay;
            var orderItemId = sales[j].saleId;
            var amountPaid = parseFloat(sales[j].amountPaid);
            var amountPayable = parseFloat(sales[j].amountPayable);
            
            var rowId = "row"+k+"_"+j;
            var tr = "<tr id='"+rowId+"' >";
            tr += "<td>" + project + "</td>";
            tr += "<td>" + unitName + "</td>";
            tr += "<td>" + accounting.formatMoney(initialDeposit,"N",2,",",".") + "</td>";
            tr += "<td>" + unitQty + "</td>";
            tr += "<td>" + accounting.formatMoney(monthlyPay,"N",2,",",".") + "</td>";
            tr += "<td>" + accounting.formatMoney(amountPaid,"N",2,",",".") + "</td>";
            tr += "<td>" + accounting.formatMoney(amountPayable,"N",2,",",".") + "</td>";
            tr += "<td><input type='hidden' class='sale-id' value='" + orderItemId + "' /><input type='text' class='lodgement-amount' value='' /></td>";
            tr += "<td><button class='btn btn-success' onclick='addToCart(\"" +project+"\", \""+unitName+"\",\""+unitQty+"\", \""+orderItemId+"\", \""+rowId+"\")'><i class='fa fa-cart-plus'></i> Add to Cart</button></td>";
            rows += tr;
        }
        
        var table = "<table class='table table-bordered table-striped table-hover'>";
        table += "<thead><tr>";
        table += "<th>Project</th><th>Unit Name</th><th>Initial Deposit</th><th>Unit Qty</th><th>monthly Pay</th><th>Amount Paid</th><th>Balance</th><th>Pay</th><th></th>";
        table += "</tr></thead>";
        table += rows  + "</table>";
     
        createOrderItemList(orderId,count,table,agentName);
       
        count += 1;
        
    }
    
    stopLoading("#customerDetailContainer:hidden, #orderItems:hidden, #lodgementCart:hidden");
  
}

function populateCustomerDetails(id){
    var row = "#row"+id;
    
    var customerName = $(row+" .customerLname").text() + " " + $(row+" .customerMname").text() + " " + $(row+" .customerFname").text();
    var customerPhone = $(row+" .customerPhone").text();
    var customerEmail = $(row+" .customerEmail").text();
    var customerState = $(row+" .customerState").text();
    var customerImgPath = $(row+" .customerImg").val();
    
    var agentName = $(row+" .agentName").val();
    var agentPhone = $(row+" .agentPhone").val();
    var agentImgPath = $(row+" .agentImg").val();
    
    $("#customerImage").attr("src",customerImgPath);
    $("#customerName").text(customerName.trim());
    $("#customerEmail").text(customerEmail.trim());
    $("#customerPhone").text(customerPhone.trim());
    $("#customerState").text(customerState.trim());
    
    $("#agentImage").attr("src",agentImgPath);
    $("#agentName").text(agentName.trim());
    $("#agentPhone").text(agentPhone.trim());
}
function showCustomerList() {
    $("#orderContainer:visible, #customerDetailContainer:visible, #orderItems:visible, #lodgementCart:visible").toggle();
    
    startLoading();
    stopLoading(function(){$("#customerListContainer:hidden").toggle();});
    
}

function showSelectedCustomer() {
    $("#customerListContainer:visible").toggle();
    $("#customerDetailContainer:hidden, #orderContainer:hidden, #orderItems:hidden, #lodgementCart:hidden").toggle();
}

function createOrderItemList(id, count, table, agentName) {
    
    var panel = document.createElement("div");
    panel.setAttribute("class","panel panel-default");
    
    var panelHeading = document.createElement("div");
    panelHeading.setAttribute("id","heading"+id);
    panelHeading.setAttribute("class","panel-heading");
    panelHeading.setAttribute("role","tab");
    panelHeading.setAttribute("style","background-color: #357CA5 !important;");
    
    var panelTitle = document.createElement("h4");
    panelTitle.setAttribute("class","panel-title");
    
    var titleButton = document.createElement("a");
    titleButton.setAttribute("role","button");
    titleButton.setAttribute("data-toggle","collapse");
    titleButton.setAttribute("data-parent","#accordion");
    titleButton.setAttribute("href","#collapse"+id);
    titleButton.setAttribute("style","display:block;color: #fff !important");
    titleButton.innerHTML = "Order id : " + id + ", Agent : " + agentName;
    if(count > 1) {
        titleButton.setAttribute("class","collapsed");
    }
    
    
    var panelCollapse = document.createElement("div");
    panelCollapse.setAttribute("id","collapse"+id);
    
    if(count == 1){
        panelCollapse.setAttribute("class","panel-collapse collapse in");
    }
    else {
        panelCollapse.setAttribute("class","panel-collapse collapse");
    }
    
    panelCollapse.setAttribute("role","tabpanel");
    
    var panelBody = document.createElement("div");
    panelBody.setAttribute("class","panel-body");
    
    panelBody.innerHTML = table;
    panelCollapse.appendChild(panelBody);
    panelTitle.appendChild(titleButton);
    panelHeading.appendChild(panelTitle);
    panel.appendChild(panelHeading);
    panel.appendChild(panelCollapse);
    
    $("#accordion").append(panel);
}



function addToCart(project,unitName,qty,orderItemId,rowId){
    
    
    
    var amountFieldSelector = "#" + rowId + " .lodgement-amount";
    var amount = $(amountFieldSelector).val();
    var amountFormatted = accounting.formatMoney(amount,"N",2,",",".");
    
    var item = {"orderItemId":orderItemId,"amount":amount};
    if(!cartData.lodgements)
        cartData.lodgements = [];
    
    if(isItemInCart(orderItemId)){
        alert("Item is already in cart, Please choose a different item");
        return;
    }
    
    cartData.lodgements.push(item);
    console.log("Cart : " + JSON.stringify(cartData));
    
    
    var id = $("#lodgementCartTable tbody tr:last").attr("id");
    
    if(id == null){
        id = 0;
    }
    if(isNaN(id)){
        id = 0;
    }
    
    var newId = parseInt(id) + 1;
    
    var tr = "<tr id='" + newId + "'>";
    tr += "<td>" + newId + "</td>";
    tr += "<td>" + project + "</td>";
    tr += "<td>" + unitName + "</td>";
    tr += "<td style='text-align: right'>" + qty + "</td>";
    tr += "<td style='text-align: right'>" + amountFormatted + "</td>";
    tr += "<td><button class='btn btn-sm btn-danger' onclick='removeFromCart("+ newId +","+ orderItemId +")'><i class='fa fa-remove'></i> Remove</button></td>";
    
    $("#lodgementCartTable tbody").append(tr);
    
    calculateLodgementCartTotal()
    
    if($("#checkOutBtn").prop("disabled")){
        $("#checkOutBtn").prop("disabled",false);
    }
}

function calculateLodgementCartTotal(){
    
    var cart = cartData.lodgements;
    
    var total = 0;
    
    for(var k in cart){
        
        total = total +  parseFloat(cart[k].amount);
    }
    
    console.log("Total = " + total);
    
    var totalFormatted = accounting.formatMoney(total,"N",2,",",".");
    $("#lodgementCartTable tfoot td#cart-total").html("<b>Total = " + totalFormatted + "</b>");
}

function isItemInCart(itemId){
    
    var bool = false;
    
    var cart = cartData.lodgements;
    
    for(var k in cart){
        if(cart[k].orderItemId == itemId){
            bool = true;
            break;
        }
    }
    
    return bool;
}

function removeFromCart(id,itemId){
    
    var cart = cartData.lodgements;
    var pos = -1;
    
    for(var k in cart){
        
        if(cart[k].orderItemId == itemId){
            pos = k;
            break;
        }
    }
    
    $("#lodgementCartTable tbody #"+id).addClass('deleting');
    $("#lodgementCartTable tbody #"+id).fadeOut(1000);
    $("#lodgementCartTable tbody #"+id).remove();
    console.log("before : " + JSON.stringify(cartData));
    cartData.lodgements.splice(pos, 1);
    console.log("after : " + JSON.stringify(cartData));
    
    if(cartData.lodgements.length < 1){
        $("#checkOutBtn").prop("disabled",true);
    }
    
    calculateLodgementCartTotal();
}

function checkOut(){
    
    $("#orderItems:visible").toggle();
    $("#checkout:hidden").toggle();
    
    var orderItems = JSON.stringify(cartData.lodgements);
    
    $("#orderItemsJson").val(orderItems);
    console.log($("#orderItemsJson").val());
    
    $("#checkOutBtn").prop("disabled",true);
    
    var cart = cartData.lodgements;
    
    var total = 0;
    
    for(var k in cart){
        
        total = total +  parseFloat(cart[k].amount);
    }
    
    $("#checkout .amount-box").each(function(){
        $(this).val(total.toString());
        $(this).prop("readonly",true);
    });
    
}

function refreshCartDetails(){
    $("#lodgementCartTable tbody").html("");
    $("#lodgementCartTable tfoot td#cart-total").html("");
    $("#checkOutBtn").prop("disabled",true);
    cartData.lodgements = [];
}


function acceptOrder(id,chkboxId,declineId){
        
        var isChecked = $("#"+chkboxId).prop('checked');
        
        
        $("#"+id+" table").find(".order-item-approve").each(function(){
            
            var chkbox = $(this);
            if($("#"+chkboxId).is(":checked")){
                console.log("Checked true");
                chkbox.prop("checked",true);
                
                //Uncheck the decline checkbox
                $("#"+declineId).prop('checked',false);
                
                $(chkbox).parent().parent().find('.chkbox2').prop("checked",false);
            }
            else{
                console.log("Checked false");
                chkbox.prop("checked",false);
                
            }
            
        });
        
        isChecked = null;
    }
    
function declineOrder(id,chkboxId,acceptId){

    var isChecked = $("#"+chkboxId).prop('checked');


    $("#"+id+" table").find(".order-item-decline").each(function(){

        var chkbox = $(this);
        if($("#"+chkboxId).is(":checked")){
            console.log("Checked true");
            chkbox.prop("checked",true);

            //Uncheck the approve checkbox
            $("#"+acceptId).prop('checked',false);
            $(chkbox).parent().parent().find('.chkbox1').prop("checked",false);
        }
        else{
            console.log("Checked false");
            chkbox.prop("checked",false);
        }

    });
        
        isChecked = null;
    }

function stopLoading(elem){
    
    setTimeout(function(){
        $("#SpinnerContainer:visible").toggle();
        $(elem).toggle();
    },1500);
}

function startLoading(){
    
    $("#SpinnerContainer:hidden").toggle();
}