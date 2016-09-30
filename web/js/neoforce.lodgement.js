/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var orderTable = null;

function selectCustomer(contextPath,id)
{
   
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
           console.log("Delete NOT Successful");
           alert("something went wrong");
       }
    });
}

function prepareOrderListTable(jsonString)
{
    var orders = JSON.parse(jsonString);
    
    var rows = "";
    for(var k in orders)
    {
        var orderId = orders[k].id;
        var agentName = orders[k].agentName;
        var customerName = orders[k].customerName;
        
        var tr = "<tr id='row"+orderId+"' >";
        tr += "<td>" + orderId + "</td>";
        tr += "<td>" + customerName + "</td>";
        tr += "<td>" + agentName + "</td>";
        
        
        rows += tr;
    }
    
    
    
    if(orderTable === null) {
        if(orders.length > 0)
        {
            $("#orderList tbody").html("");
            $("#orderList tbody").append(rows);      
        }
        else
        {
            //Empty the table
            $("#orderList tbody").html("");
        }
        orderTable = $("#orderList").DataTable({
                "autoWidth": false
        });
    }
    else {
        orderTable.destroy();
        if(orders.length > 0)
        {
            $("#orderList tbody").html("");
            $("#orderList tbody").append(rows);      
        }
        else
        {
            //Empty the table
            $("#orderList tbody").html("");
        }
        orderTable = $("#orderList").DataTable({
                "autoWidth": false
        });
    }
    
    $("#customerListContainer:visible").toggle();
    $("#orderContainer:hidden").toggle();
    
    
}


function showCustomerList() {
    
    
}

function showSelectedCustomer() {
    
}