/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var orderTable = null;
var cid = 1;
var countId = 1;
function selectCustomer(contextPath,id)
{
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

function prepareOrderListTable(jsonString)
{
    $("#accordion").html("");
    var orders = JSON.parse(jsonString);
    var count = 1;
    
    for(var k in orders)
    {
        var rows = "";
        
        var orderId = orders[k].id;
        var agentName = orders[k].agentName;
        var customerName = orders[k].customerName;
        var sales = JSON.parse(orders[k].sales);
        
        for(var j in sales) {
            var project = sales[j].project;
            var unitName = sales[j].unitName;
            var amountPayable = sales[j].amountPayable;
            var unitQty = sales[j].unitQty;
            var saleId = sales[j].saleId;
            
            var tr = "<tr id='row"+j+"' >";
            tr += "<td>" + project + "</td>";
            tr += "<td>" + unitName + "</td>";
            tr += "<td>" + amountPayable + "</td>";
            tr += "<td>" + unitQty + "</td>";
            tr += "<td><input type='checkbox' value='" + saleId + "' /></td>";

            rows += tr;
        }
        
        var table = "<table class='table table-bordered table-striped table-hover'>";
        table += "<thead><tr>";
        table += "<th>Project</th><th>Unit Name</th><th>Initial Deposit</th><th>Unit Qty</th><th>Action</th>";
        table += "</tr></thead>";
        table += rows  + "</table>";
        var div = "<div><button class='btn btn-primary pull-right'>Proceed</button></div>";
        
        table += div;
        createOrderItemList(orderId,count,table,agentName);
       
        count += 1;
        
    }
    
    
    
//    if(orderTable === null) {
//        if(orders.length > 0)
//        {
//            $("#orderList tbody").html("");
//            $("#orderList tbody").append(rows);      
//        }
//        else
//        {
//            //Empty the table
//            $("#orderList tbody").html("");
//        }
//        orderTable = $("#orderList").DataTable({
//                "autoWidth": false
//        });
//    }
//    else {
//        orderTable.destroy();
//        if(orders.length > 0)
//        {
//            $("#orderList tbody").html("");
//            $("#orderList tbody").append(rows);      
//        }
//        else
//        {
//            //Empty the table
//            $("#orderList tbody").html("");
//        }
//        orderTable = $("#orderList").DataTable({
//                "autoWidth": false
//        });
//    }
    
    
//    $('#accordion').collapse({
//      toggle: false
//    });
    stopLoading("#customerDetailContainer:hidden, #orderItems:hidden");
    
    
    
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
    $("#orderContainer:visible, #customerDetailContainer:visible, #orderItems:visible").toggle();
    startLoading();
    stopLoading(function(){$("#customerListContainer:hidden").toggle();});
    
}

function showSelectedCustomer() {
    
}

function createOrderItemList(id, count, table, agentName) {
    
    var panel = document.createElement("div");
    panel.setAttribute("class","panel panel-default");
    
    var panelHeading = document.createElement("div");
    panelHeading.setAttribute("id","heading"+id);
    panelHeading.setAttribute("class","panel-heading");
    panelHeading.setAttribute("role","tab");
    
    var panelTitle = document.createElement("h4");
    panelTitle.setAttribute("class","panel-title");
    
    var titleButton = document.createElement("a");
    titleButton.setAttribute("role","button");
    titleButton.setAttribute("data-toggle","collapse");
    titleButton.setAttribute("data-parent","#accordion");
    titleButton.setAttribute("href","#collapse"+id);
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

function stopLoading(elem){
    
    setTimeout(function(){
        $("#SpinnerContainer:visible").toggle();
        $(elem).toggle();
    },1500);
}

function startLoading(){
    
    $("#SpinnerContainer:hidden").toggle();
}