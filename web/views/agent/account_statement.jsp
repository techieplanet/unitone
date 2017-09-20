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
              
            <div class="row">
             <div class="col-md-8 col-md-offset-2">  
                 
                  <div class="table-responsive" id="printArea">
                  
                  <div class="row marginbottom20 padding10">
                     <div class="col-md-12"> 
                      <div class="panel panel-warning"> <!-- Panel starts here -->
                      
                      <div class="panel-body">
                          <div class="col-md-12"><b>Account Code</b> ${accountCode}</div>
                          <div class="col-md-6"><b>Available Balance</b> <fmt:formatNumber value="${balance}" type="currency" currencySymbol="N" /></div>
                          <div class="col-md-6"><b>Ledger Balance</b> <fmt:formatNumber value="${ledgerBalance}" type="currency" currencySymbol="N" /></div>
                      </div>
                      
                      </div> <!-- Panel ends here -->
                     </div>
                  </div>
                  
                  <table id="accStatement" class="table table-striped table-hover table-bordered table-condensed" >
                      
                      <thead>
                          <tr>
                              <td class="text-center">SN</td>
                              <td class="text-center">Date</td>
                              <td class="text-center">Debit</td>
                              <td class="text-center">Credit</td>
                              <td class="text-center">Balance</td>
                          </tr>
                      </thead>
                      
                      <tbody>
                          
                          <c:set var="totalCredit" value="0" />
                          <c:set var="totalDebit" value="0" />
                          
                          <c:forEach items="${transactions}" var="transaction" varStatus="pointer">
                              
                              <c:if test='${transaction["type"] eq "Credit"}'> 
                                  <c:set var="creditAmount" value="${transaction['amount']}"  />
                                  <c:set var="totalCredit" value="${transaction['amount'] + totalCredit}" />
                                  <tr>
                                  <td class="text-center">${pointer.count}</td>
                                  <td class="text-center">${transaction['date']}</td>
                                  <td class="text-right"></td>
                                  <td class="text-right"><fmt:formatNumber value="${creditAmount}" type="currency" currencySymbol="N" /></td> 
                                  <td class="text-right"><fmt:formatNumber value="${transaction['accbalance']}" type="currency" currencySymbol="N" /></td> 
                              </tr>
                              </c:if>
                              <c:if test='${transaction["type"] eq "Debit"}'> 
                                  <c:set var="debitAmount" value="${transaction['amount']}" />
                                  <c:set var="totalDedit" value="${transaction['amount'] + totalDebit}" />
                                  <tr>
                                  <td class="text-center">${pointer.count}</td>
                                  <td class="text-center">${transaction['date']}</td>
                                  <td class="text-right"><fmt:formatNumber value="${debitAmount}" type="currency" currencySymbol="N" /></td>
                                  <td class="text-right"></td> 
                                  <td class="text-right"><fmt:formatNumber value="${transaction['accbalance']}" type="currency" currencySymbol="N" /></td> 
                              </tr>
                              </c:if>
                              
                          </c:forEach>
                          
                      </tbody>
                      <tfoot>
                          <tr>
                              <td colspan="2" class="text-center"><b>Total</b></td>
                              <td><fmt:formatNumber value="${totalDedit}" type="currency" currencySymbol="N" /></td>
                              <td style="text-align: right"><fmt:formatNumber value="${totalCredit}" type="currency" currencySymbol="N" /></td>
                              
                          </tr>
                      </tfoot>
                      
                  </table>
                  
                  
              </div>
                              
              
                  <div class="margintop10">
                        <button class="btn btn-primary" onclick="agentHistory.printAccountStatement()"><i class="fa fa-print"></i> Print</button>
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
<%@ include file="../includes/bottom.jsp" %>
<script type="text/javascript" src="plugins/rcswitcher-master/js/rcswitcher.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.PrintArea.js"></script>
<script>
          
           $(function () {
            $("#accStatement").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": true, "width":"20px", "targets": 0 }
                ]
            });
        });

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
  