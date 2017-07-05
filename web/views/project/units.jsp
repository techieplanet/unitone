
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!--<a href="mailbox.html" class="btn btn-primary btn-block margin-bottom">Back to Inbox</a>-->
<div class="box box-solid">
<div class="box-header with-border">
  <h3 class="box-title">Units</h3>
  
  <c:if test="${project.id != null }">
    <a class="pull-right text-center" onclick="$('#myModal').modal({keyboard:false,backdrop:false}); $('.title-text').html('New Unit');" style="width: 25px; cursor: pointer; padding: 2px;" ><i class="fa fa-plus"></i></a>
  </c:if>
  
</div>
    <div class="box-body no-padding">
        <ul class="list-group">
            <c:forEach items="${units}" var="unit">
                <li id="row<c:out value="${unit.id}" />" class="list-group-item noborder">
                    <span class="" style="width: 60%; display: inline-table;">${unit.title}<span class="badge marginleft5">${unit.quantity}</span></span>
                    <a class="pull-right btn btn-danger btn-xs marginleft5" href="#" onclick="showDeleteModal('${pageContext.request.contextPath}', 'ProjectUnit', ${unit.id})" role="button"><i class="fa fa-remove"></i></a>
                    <a class="pull-right btn btn-success btn-xs" onclick="launchEditUnitForm(${unit.id},'${pageContext.request.contextPath}')" role="button"><i class="fa fa-pencil"></i></a>
                </li>
            </c:forEach>
         </ul>
    </div>
   
</div><!-- /. box -->


<!--MODAL-->
      <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
          <div class="modal-dialog form-modal" style="width:750px !important;">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">${project.name}</h4>
            </div>
            <div class="modal-body" style="padding-top:0px;">
                <%@ include file="unitform.jsp" %>
            </div>
<!--            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
              <button id="ok" type="button" onclick="" class="btn btn-primary">OK</button>
            </div>-->
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
      
      
<!--MODAL-->
      <div class="modal fade " id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog delete-modal">
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
      
      
      
      
<script>
    function launchEditUnitForm(unitId, context){
        $.get(context+'/ProjectUnit',{action:'edit',id:unitId},function(response){
                                //console.log(response);
                                unit = JSON.parse(response);
                                $('#title').val(unit.title);
                                $('#cpu').val(parseFloat(unit.cpu).toFixed(2));
                                $('#building_cost').val(parseFloat(unit.building_cost).toFixed(2));
                                $('#service_value').val(parseFloat(unit.service_value).toFixed(2));
                                $('#income').val(parseFloat(unit.income).toFixed(2));
                                $('#lid').val(parseFloat(unit.lid).toFixed(2));
                                $('#discount').val(unit.discount);
                                $('#mpd').val(unit.mpd);
                                $('#commp').val(unit.commp);
                                $('#vatp').val(unit.vatp);
                                $('#amp').val(unit.amp);
                                $('#reward_points').val(unit.reward_points);
                                $('#quantity').val(unit.quantity);

                                $('#monthly_pay').val(parseFloat(unit.monthly_pay).toFixed(2));
                                $('#amt_payable').val(parseFloat(unit.amt_payable).toFixed(2));

                                $('#unittype').val(unit.unit_type_id);
                                $('#loading').addClass("hidden");
        });
        
        $('#myModal #id').val(unitId);
        $('#myModal .title-text').html('Edit Unit');
        $('#loading').removeClass("hidden");
        $('#myModal').modal();
    }
</script>