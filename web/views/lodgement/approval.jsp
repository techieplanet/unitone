<%-- 
    Document   : approval
    Created on : Oct 14, 2016, 5:28:33 PM
    Author     : Prestige
--%>

<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!-- Include the lid -->
<%@ include file="../includes/lid.jsp" %>      

<!-- Include the header -->
<%@ include file="../includes/header.jsp" %>

<%@ include file="../includes/sidebar.jsp" %>   


<div class="content-wrapper">
    
    <section class="content-header">
        <h1>
            Lodgement
        </h1>
    </section>
    
    <section class="content">
        
        <div class="box">
            <div class="box-header">
                <h1 class="box-title">Waiting Lodgement</h1>
            </div>
            
            <div class="box box-body pad">
                <ul>
                <c:forEach items="${lodgements}" var="lodgement">
                    
                    <li>${lodgement.getDepositorName()}</li>
                    
                </c:forEach>
                </ul>
            </div>
        </div>
        
    </section>
    
</div>


<!-- Include the footer -->
<%@ include file="../includes/footer.jsp" %>      


<!-- Include the control sidebar -->
<%--<%@ include file="../includes/control-sidebar.jsp" %>--%>      
      
<!-- Include the bottom -->
<%@ include file="../includes/bottom.jsp" %>
