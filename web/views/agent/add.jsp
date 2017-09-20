<!-- Include the lid -->
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
            <a href="Agent"> Agents</a>
        </h1>
    </section>

    <!-- Main content -->
    <section class="content">

        <c:set var="isCorporate"  value="${(action.equals('edit') && agent.isCorporate())||(action.equals('new') && corporate == true)}" />
        <c:if test="${!isCorporate}"> <%@ include file="form.jsp" %> </c:if >
        <c:if test="${isCorporate}"> <%@ include file="corporate_form.jsp" %> </c:if>   

        </section><!-- /.content -->
    </div><!-- /.content-wrapper -->
 <!--Error Modal-->
        <div class="modal fade" id="agentErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">Agent Registration Error</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-primary pull-right" data-dismiss="modal">OK</button>
            </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
        </div>

    <!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      

<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
<script type="text/javascript" src="plugins/rcswitcher-master/js/rcswitcher.min.js"></script>


<script>
    $(function () {
        //Add text editor
        $("#compose-textarea").wysihtml5();

    <c:if test="${agent.agentId != null}">
        var id = "row" + <c:out value="${agent.agentId}" />;
        $('#' + id + ' :checkbox').rcSwitcher({
            // reverse: true,
            // inputs: true,
            width: 104,
            height: 26,
            blobOffset: 2,
            onText: 'Deactivate',
            offText: 'Activate',
            theme: 'flat',
            autoFontSize: false,
            fontSize: '20px',
        });
    </c:if>
    });
</script>