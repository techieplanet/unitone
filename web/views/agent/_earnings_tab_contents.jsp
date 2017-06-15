<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row margintop10 marginbottom20">
    <div class="col-md-6 bg-green text-conspicuous"><span>Available Balance</span><br/> <strong><fmt:formatNumber value="${agentDetails.balance}" type="currency" currencySymbol="${nairaSymbol}" groupingUsed="TRUE" /></strong></div>
    <div class="col-md-6 bgeee text-conspicuous"><span>Ledger Balance </span><br/><strong><fmt:formatNumber value="${agentDetails.ledgerBalance}" type="currency" currencySymbol="${nairaSymbol}" groupingUsed="TRUE" /></strong></div>    
</div>


<div class="box-body">
 <!--<div class="permissions block">-->
  <table id="earningslist" class="table table-bordered table-striped margintop10"  style="font-size: 14px;">
    <thead>
      <tr>
        <th class="text-center">SN</th>
        <th class="text-center">Date</th>
        <th class="text-center">Debit</th>
        <th class="text-center">Credit</th>
      </tr>
    </thead>
    <tbody>
        
        <c:forEach items="${transactionMapsList}" var="transMap" varStatus="pointer">
            <tr id="">
                <td class="text-center"><c:out value="${pointer.count}" /></td>
                <td>${transMap.date}</td>
                <c:set var="amt" value="${transMap.amount}" />
                <td class="text-right"><c:if test="${transMap.type == 'debit'}"><fmt:formatNumber value="${amt}" type="currency" currencySymbol="${nairaSymbol}" groupingUsed="TRUE"/></c:if></td>
                <td class="text-right"><c:if test="${transMap.type == 'credit'}"><fmt:formatNumber value="${amt}" type="currency" currencySymbol="${nairaSymbol}" groupingUsed="TRUE"/></c:if></td>
            </tr>
        </c:forEach>
            
  </tbody>
    <tfoot>
      <tr>
        <th class="text-center">SN</th>
        <th class="text-center">Date</th>
        <th class="text-center">Debit</th>
        <th class="text-center">Credit</th>
      </tr>
    </tfoot>
  </table>

    <div class="box-header">
        <h3 class="box-title block">
            <span class="pull-right"><a class="btn btn-primary" href="Agent?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Print</a></span>
        </h3>
    </div><!-- /.box-header -->
</div><!-- /.box-body -->
                
<!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
                
<script>
        
        $(function () {
            $("#earningslist").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "sortable": false, "width":"20px", "targets": 0 }
                ]
            });
        });
</script>        