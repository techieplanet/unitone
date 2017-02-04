<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
          
          
              
              <div class="row padding15">
                  <c:if test="${not empty success}">
                  <div class="col-md-12">
                     <div class="alert alert-success alert-dismissible">
                         <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                             <p>${success}</p>
                     </div>
                 </div>
                   </c:if>  
                  <c:if test="${not empty error}">
                 <div class="col-md-12">
                     <div class="alert alert-error alert-dismissible">
                         <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                          <span aria-hidden="true">&times;</span>
                        </button>
                             <p>${error}</p>
                     </div>
                 </div>    
                  </c:if>   
              </div>
              
         
          
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Send Message
            <!--<small>Optional description</small>-->
          </h1>
<!--          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
            <li class="active">Here</li>
          </ol>-->
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Your Page Content Here -->
         
          <div class="row">
              
              <div class="col-md-12">
                  
                  <ul class="nav nav-tabs" role="tablist">
                    <li role="presentation" class="active"><a href="#email_box" aria-controls="email" role="tab" data-toggle="tab">Send Email</a></li>
                    <li role="presentation"><a href="#sms_box" aria-controls=sms_box" role="tab" data-toggle="tab">Send SMS</a></li>
                    
                  </ul>
                  
              </div>
              
          </div>
             
          <!-- Tab panes -->
        <div class="tab-content">    
           
          <div role="tabpanel" class="tab-pane active" id="email_box">
          <div class="box box-default" >
                
              <div class="box-header with-border">
                  <h3 class="box-title">Compose Email Message <i class=""></i></h3>
              </div>
              
              <div class="box-body">
                  
                  <form action="${pageContext.request.contextPath}/Message?action=customer_email" method="POST">
                      
                      <input type="hidden" name="agentId" class="ids" value="${agentId}" />
                      <div class="row">
                          <div class="col-md-6">
                              <div class="form-group">
                                  <label for="subject">Subject </label>
                                  <input class="form-control" id="subject" name="email_subject" />
                              </div>
                          </div>
                      </div>
                      <div class="row">
                          
                      <div class="col-md-10">    
                      <div class="form-group">
                          <label>Message Body</label>
                          <textarea class="form-control"  name="email_body" cols="100" rows="10" id="email_body">
                              
                          </textarea>
                      </div>
                      </div>
                     
                      <div class="col-md-12">
                          <input type="submit" class="btn btn-primary" value="Send Email" />
                      </div>
                          
                      </div>
                      
                  </form>
                  
              </div>
              
          </div>    
          </div>   
          
          <div role="tabpanel" class="tab-pane" id="sms_box">  
          <div class="box box-default">
                
              <div class="box-header with-border">
                  <h3 class="box-title">Compose SMS Message <i class=""></i></h3>
              </div>
              
              <div class="box-body">
                  
                  <form action="${pageContext.request.contextPath}/Message?action=customer_sms" method="POST">
                      
                      <input type="hidden" name="customersId" class="ids" value="" />
                      <c:choose>
                          <c:when test="${not empty reply}">
                              <input type="hidden" name="reply_msg_id" value="${reply}" />
                          </c:when>
                          <c:when test="${empty reply}">
                              <input type="hidden" name="reply_msg_id" value="0" />
                          </c:when>
                      </c:choose>
                      <div class="row">
                          
                          <div class="col-md-4">    
                          <div class="form-group">
                              <label>Message Body</label>
                              <textarea class="form-control"  name="sms_body" cols="100" rows="10" id="sms_body">

                              </textarea>
                          </div>
                          </div>
                          <div class="clearfix"></div>
                          <div class="col-md-4">
                              <input type="submit" class="btn btn-primary" value="Send SMS" />
                          </div>
                          
                      </div>
                      
                  </form>
                  
              </div>
              
          </div>     
          </div>
            
        </div>     
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      
      <!--MODAL-->
      <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
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
      
      

      
<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


<script>
     var table;
     
     
     
     
     $(document).ready(function(){
         
         table = $("#customerList").DataTable({
             pageLength : 3,
             'columnDefs': [{
             'targets': 0,
             'searchable':false,
             'orderable':false
            }]
         });
         /**
         $.fn.dataTable.ext.search.push(
            function( settings, data, dataIndex ) {
                
                var tr = settings.aoData[dataIndex].nTr;
                var col = data[0];
                
                var filter = $('#customerFilter').val();
                
                if(filter == "all"){
                    $(tr).addClass('check');
                    return true;
                }
                
                
                if($(tr).hasClass('defaulter')){
                    $(tr).addClass('check');
                    return true;
                }
                else{
                    $(tr).removeClass('check');
                    return false;
                }
            }
        );
         **/
 
         $("#email_body").wysihtml5();;
         
         //Add on change Event Listener to the checkAll toggle box
         $(".toggle_chkbox").on('change',function(){
            
            if($(this).is(":checked")){
                
                table.rows().nodes().$('.customer_chkbox').each(function()
                {
                    $(this).prop("checked",true);
                });
                
                customerMessage.addContacts();
               
            }
            else{
                
                table.rows().nodes().$('.customer_chkbox').each(function(){
                    $(this).prop("checked",false);
                });
                
                customerMessage.addContacts();
            }
            
        });
        
        customerMessage.addCheckListner();
        
     });
          
      var customerMessage = {
            
            //Add on change Event Listener to the checkAll toggle box
            addCheckListner : function(){
                
                table.rows().nodes().$('.customer_chkbox').each(function()
                {
                    $(this).on('change',function(){
                        customerMessage.addContacts();
                    });
                });
            },
            
            addContacts : function(){
                
                var ids = [];
                
                table.rows().nodes().$('.customer_chkbox').each(function()
                {
                    if($(this).prop("checked"))
                        ids.push($(this).val());
                });
                
                $("#contact-count").text("Selected Contacts : " + ids.length);
                
                $('.ids').val(JSON.stringify(ids));
            },
            
            filter : function(){
                
                $.ajax({
                    url : "${pageContext.request.contextPath}/Message?action=filter_customer",
                    method : "get",
                    success : function(data){
                        
                        console.log(data);
                        customerMessage.redrawTable(JSON.parse(data))
                        table.draw();
                    },
                    error : function(xhr,code,status){
                        console.log(xhr.responseText);
                    }
                });
                  
            },
            
          redrawTable : function(data){
            
            var filterValue = $("#customerFilter").val();
            
            //Destroy DataTable and recreate
            table.destroy();
            $("#customerList tbody").html("");
            $(".toggle_chkbox").prop("checked",false);
            var count = 1;
            
            for(var k in data){
                
                if(filterValue == "defaulter" && data[k].defaulter == ""){
                    continue;
                }
                
                var tr = "<tr>";
                
                tr += "<td><input type='checkbox' class='customer_chkbox' value='" + data[k].id + "' /></td>";
                tr += "<td>" + count + "</td>";
                tr += "<td><img width='55' height='50' src='/uploads/NeoForce/images/customer/" + data[k].photoPath + "'/></td>";
                tr += "<td>" + data[k].firstname + "</td>";
                tr += "<td>" + data[k].middlename + "</td>";
                tr += "<td>" + data[k].lastname + "</td>";
                tr += "<td>" + data[k].phone + "</td>";
                tr += "<td>" + data[k].email + "</td>";
                tr += "<td>" + data[k].street + "</td>";
                tr += "<td>" + data[k].city + "</td>";
                tr += "<td>" + data[k].state + "</td>";
                
                tr += "</tr>";
                
                $("#customerList tbody").append(tr);
                
                count++;
            }
            
            //Re-Initialize the DataTable
            table = $("#customerList").DataTable({
                pageLength : 3
            });
            
            customerMessage.addCheckListner();
            customerMessage.addContacts();
            
          }
        
      };
          
 </script>         
