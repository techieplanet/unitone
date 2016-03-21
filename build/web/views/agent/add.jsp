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
        
          
                <%@ include file="form.jsp" %>      
            
          
        </section><!-- /.content -->
      </div><!-- /.content-wrapper -->


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
        
        var id = "row"+ <c:out value="${agent.agentId}" />;
                       $('#'+id+' :checkbox').rcSwitcher({

					// reverse: true,
					// inputs: true,
					width: 104,
					height: 26,
					blobOffset: 2,
					onText: 'Deactivate',
					offText: 'Activate',
					theme: 'flat',
				        autoFontSize: false,
                                        fontSize:'20px',
					

				});
      });
    </script>