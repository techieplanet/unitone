/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Agents by location table
 */
$(function () {
           
    
            $("#entitylist").DataTable({
                "autoWidth": false,
                "columnDefs": [
                    { "width":"100px", "targets": 3 },
                    <c:if test="${fn:contains(sessionScope.user.permissions, 'view_agent') || fn:contains(sessionScope.user.permissions, 'edit_agent') || fn:contains(sessionScope.user.permissions, 'delete_agent')}">
                        { "sortable": false, "width":"80px", "targets": 8 }
                    </c:if>
                ]
        });
    
            <c:forEach items="${agents}" var="agent">
                       var id = "row"+ <c:out value="${agent.agentId}" />;
                       $('#'+id+' :checkbox').rcSwitcher({
					width: 65,
					height: 24,
					blobOffset: 2,
					onText: 'YES',
					offText: 'NO',
					theme: 'flat',
				        autoFontSize: false,
				});
            </c:forEach>
    
                                
    
                                
      
          });