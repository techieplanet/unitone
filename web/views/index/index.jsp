<%@include file="header.jsp" %>  

        <div class="container-fluid">
            <div class="row" style="display:none">
                <div class="col-md-12 homebackdrop"><img class="img-responsive" src="${pageContext.request.contextPath}/images/home-bg-with-neo.png"></div>

            </div>
            
            <div class="row" style="margin-top: 10px;display:none">
                <div class="col-md-8 col-md-offset-2">
                    <blockquote>
                        NEOFORCE is an advanced Sales Force Enterprise Portal Solution developed for 
                        organizations to manage mass subscription and instalmental sales schemes.
                    </blockquote>
                </div>
            </div>
                
            
            <div class="row" style="margin-top: 120px;">
                    
                <c:set var="count" value="1" />
                         <c:forEach items="${projects}" var="project">     
                         
                              <div class="col-md-3">
                                <div class="thumbnail">
                                    <img src="${pageContext.request.contextPath}/images/img/project.jpg" class="img" style="min-height: 200px" width="300px" height="200px" alt="No Preview">
                                  <div class="caption text-center">
                                    <h3>${project.getName()}</h3>
                                    <p>
                                        ${fn:substring(project.getDescription(),0,47)}
                                        ${fn:length(project.getDescription()) > 47 ? "...":""}
                                    </p>
                                    <p><a href="${pageContext.request.contextPath}/Project?action=listunits&project_id=${project.getId()}&loggedin=no" class="btn btn-primary" role="button">View</a></p>
                                  </div>
                                </div>
                              </div>
                              
                            <c:if test="${count == 4}">
                                <span class="clearfix"></span>
                                <c:set var="count" value="0" />
                            </c:if>
                                
                            <c:set var="count" value="${count + 1}" />    
                                        
                         </c:forEach>
                
            </div>    
                
        </div>
        
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
