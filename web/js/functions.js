/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
           $('#row'+id).fadeOut(1500, function(){
               $('#row'+id).remove();
           });
       },
       error: function(){
           console.log("Delete NOT Successful");
       }
    });
}

function showDeleteModal(context, entityName, id){    
    var args = "deleteEntity('" + context + "'," + "'" + entityName + "'," + "'" + id + "')";
    $("#deleteModal #ok").attr("onclick", args);
    $('#deleteModal').modal();
}


function submitPostForm(url, formData){
    //url = appName + '/' + entityName;
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