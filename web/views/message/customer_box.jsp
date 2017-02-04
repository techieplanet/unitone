<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   
<script>
         
         var mainApp = angular.module("customerBox",['ngwidgets']);
        
         mainApp.factory("fetchMessages",["$http","$q",function($http,$q)
             {
                
                return { 
                  fetch : function(){
                             var deferred = $q.defer();
                             $http({
                               method : "GET",
                               url : "${pageContext.request.contextPath}/Message?action=message_thread",
                               
                               }
                             ).success(function(res){
                                 deferred.resolve(res);
                             }).error(function(errMsg,code){
                                 deferred.reject(errMsg);
                             });
                             return deferred.promise;
                        }
                }
                 
                 
             }]);
         
         mainApp.controller("custMailCtrl",function($scope,fetchMessages){
             
             
             $scope.getCustomerMessages = function(){
                 
                 var messageJson = fetchMessages.fetch()
                         .then(function(data){
                             console.log(JSON.stringify(data));
                            $scope.showMessageThread(data);
                          },function(error){
                              console.log(error);
                          })
                          
             };
             
             $scope.showMessageThread = function(messages){
                 
                 var messages = JSON.parse(JSON.stringify(messages));
                 var fullThread = "";
                 var userId = "${id}";
                 
                 for(var k in messages){
                     
                     var singleThread = "";
                     
                     var message = messages[k];
                     var replies = messages[k].replies;
                     
                     for(var j in replies){
                         
                         if(userId == replies[j].userId && replies[j].userType == 3){
                             var subject = "<b><u>RE: </u>" + replies[j].subject +  "</b>";
                         }
                         else{
                            var subject = "<b><u>RE: </u>" + replies[j].subject +  "</b> &nbsp<a href='${pageContext.request.contextPath}/Message?action=reply&id=" + replies[j].id + "'>Reply</a>";
                         }
                         var body = replies[j].body;
                         var id = replies[j].id;
                         var date = replies[j].date + "<br /><br />"; 
                         
                         singleThread += subject + body + date;
                     }
                         if(userId == message.userId && message.userType == 3){
                            var subject = "<b>" + message.subject +  "</b>";
                         }
                         else{
                            var subject = "<b>" + message.subject +  "</b>&nbsp<a href='${pageContext.request.contextPath}/Message?action=reply&id=" + message.id + "'>Reply</a>";
                         }
                         var body = message.body;
                         var id = message.id;
                         var date = message.date + "<br />"; 
                         
                         singleThread += subject + body + date + "<hr />";
                         
                         fullThread += singleThread;
                 }
                 
                 console.log(fullThread);
                 $("#message-box").html(fullThread);
               
             };
             
             $scope.getCustomerMessages();
         });
 
         
          
 </script> 

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper" ng-app="customerBox">

         
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
            Customer MailBox
          </h1>
        </section>

        <!-- Main content -->
        <section class="content" ng-controller="custMailCtrl">
            <div class="row">
                
                <div class="col-md-10">
                    
                    <div class="panel panel-primary">
                        <div class="panel-heading">
                            <h3 class="panel-title"></h3>
                        </div>
                        <div class="panel-body" id="message-box" style="height:450px;overflow-y: auto">
                        </div>
                    </div>
                    
                </div>
                
            </div>  
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->
      

<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>


  
