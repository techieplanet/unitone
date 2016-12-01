<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->

<!--        <link type="text/css" rel="stylesheet" href="plugins/rcswitcher-master/css/style.min.css">-->
	<link type="text/css" rel="stylesheet" href="plugins/rcswitcher-master/css/rcswitcher.min.css">
	
    
  
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
          <h1>
              <a href="Agent"> Agent Account Statement</a>
          </h1>
<!--          <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
            <li class="active">Here</li>
          </ol>-->
        </section>

        <!-- Main content -->
        <section class="content">
          <!-- Your Page Content Here -->
          
          
          <div class="panel panel-default paddingtop20 paddingbottom20">
              
              <div class="table-responsive" id="printArea">
                  
                  <div class="row marginbottom20 padding10">
                      
                      <div class="col-md-12"><h4>Account Code : ${accountCode}</h4></div>
                      <div class="col-md-12"><h4>Account Balance : <fmt:formatNumber value="${balance}" type="currency" currencySymbol="N" /></h4></div>
                  </div>
                  
                  <table class="table table-striped table-hover table-bordered" >
                      
                      <thead>
                          <tr>
                              <td>SN</td>
                              <td class="text-center">Date</td>
                              <td class="text-center">Amount</td>
                              <td class="text-center">Type</td>
                          </tr>
                      </thead>
                      
                      <tbody>
                          
                          <c:forEach items="${transactions}" var="transaction" varStatus="pointer">
                              
                              <tr>
                                  <td>${pointer.count}</td>
                                  <td class="text-center">${transaction['date']}</td>
                                  <td class="text-right"><fmt:formatNumber value='${transaction["amount"]}' type="currency" currencySymbol="N" /></td>
                                  
                                  <c:if test='${transaction["type"] eq "Credit"}'>  
                                      <td class="text-center text-green">
                                          ${transaction["type"]}
                                      </td>
                                  </c:if>
                                  <c:if test='${transaction["type"] eq "Debit"}'>  
                                      <td class="text-center text-red">
                                          ${transaction["type"]}
                                      </td>
                                  </c:if>
                                      
                                  
                              </tr>
                              
                          </c:forEach>
                          
                      </tbody>
                      
                  </table>
                  
                  
              </div>
              
              <div class="row padding30">
                      <div class="col-md-12">
                          <button class="btn btn-primary" onclick="agentHistory.printAccountStatement()"><i class="fa fa-print"></i> Print</button>
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
<%@ include file="../includes/bottom.jsp" %>
<script type="text/javascript" src="plugins/rcswitcher-master/js/rcswitcher.min.js"></script>
<!--<script src="plugins/bootstrap-switch/docs/js/highlight.js"></script>
    <script src="plugins/bootstrap-switch/dist/js/bootstrap-switch.js"></script>
    <script src="plugins/bootstrap-switch/docs/js/main.js"></script>-->
<script>
          

      var agentHistory = {
          
          
          printAccountStatement : function(){
              
              var options = {
                  mode:"iframe",
                  popClose: true
              };
              $("#printArea").printArea(options);
              
          }
          
      };
  </script>
  