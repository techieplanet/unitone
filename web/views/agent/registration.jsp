<%-- 
    Document   : registration
    Created on : Oct 24, 2016, 10:24:47 AM
    Author     : Prestige
--%>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">

    <title>NeoForce | ${pageTitle}</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" type="text/css" />
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">

    
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    
    <!-- iCheck for checkboxes and radio inputs -->
    <link rel="stylesheet" href="plugins/iCheck/all.css">

    
    <!-- DataTables -->
    <link rel="stylesheet" href="plugins/datatables/dataTables.bootstrap.css">
    
    <!-- bootstrap wysihtml5 - text editor -->
    <link rel="stylesheet" href="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.min.css">
    
    <!-- Theme style -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/AdminLTE.min.css" type="text/css" />
     
    
    <!-- AdminLTE Skins. We have chosen the skin-blue for this starter
          page. However, you can choose any other skin. Make sure you
          apply the skin class to the body tag so the changes take effect.
    -->
    <!--<link rel="stylesheet" href="${pageContext.request.contextPath}/css/skin-blue.min.css" type="text/css" />-->
    <link rel="stylesheet" href="css/skins/skin-blue.min.css" type="text/css" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css" type="text/css" />
    <!-- jQuery 2.1.4 -->
    <!--<script src="plugins/jQuery/jQuery-2.1.4.min.js"></script>-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jQuery-2.1.4.min.js"></script>


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
        <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
        <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
  </head>
  <!--
  BODY TAG OPTIONS:
  =================
  Apply one or more of the following classes to get the
  desired effect
  |---------------------------------------------------------|
  | SKINS         | skin-blue                               |
  |               | skin-black                              |
  |               | skin-purple                             |
  |               | skin-yellow                             |
  |               | skin-red                                |
  |               | skin-green                              |
  |---------------------------------------------------------|
  |LAYOUT OPTIONS | fixed                                   |
  |               | layout-boxed                            |
  |               | layout-top-nav                          |
  |               | sidebar-collapse                        |
  |               | sidebar-mini                            |
  |---------------------------------------------------------|
  -->
  <body style="background-color: #eee">
   
      <div class="container">
          
          <header style="background-color: #0073b7;color:#fff ">
              <div class="row">
                  <div class="col-md-3 text-center" style="margin:10px;">
                      <i class="fa fa-image fa-4x"></i>
                      <br/>
                      <span>Company logo</span>
                  </div>
                  <div class="col-md-8" style="padding-left: 80px;padding-top: 10px">
                      <h1>Agent Registration Form</h1>
                  </div>
              </div>
          </header>
          
          <div class="jumbotron" style="background-color: #fff">
              
              <form role="form" name="agentRegistration" method="POST" action="AgentRegistration?from=agent_registration" enctype="multipart/form-data" id="agentForm">
              
                <c:if test="${fn:length(errors) > 0 }">
                <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-danger padding10" style="width:100%; margin:0 auto !important">
                          <c:forEach items="${errors}" var="error">
                              <c:out value="${error.value}" /><br/>
                          </c:forEach>
                        </p>
                    </div>
                </div>
                </c:if>
           
              <c:if test="${success}">
              <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-success padding15" style="vertical-align:center !important;" >
                          <i class="fa fa-check"></i>Saved Successfully
                          <span class="pull-right">
                              <a class="btn btn-primary btn-sm margintop5negative" user="button" href="${pageContext.request.contextPath}/Agent"><i class="fa fa-arrow-left"></i>&nbsp;&nbsp;&nbsp;&nbsp;Back to list</a>
                              <!--&nbsp;&nbsp;&nbsp;-->
                              <a class="btn btn-primary btn-sm margintop5negative marginleft10" href="Agent?action=new" user="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Agent</a>
                          </span>
                        </p>
                    </div>
              </div>
             </c:if>
                           
                        
                    
                    <div class="row">
                  
                      <fieldset>
                        <legend style="padding-left:10px !important;">Personal Information</legend>
                        
                         
                              <div class="col-md-5">
                                  <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentFirstname">First Name</label>
                                      <input type="text"  name="agentFirstname" class="form-control" id="agentFirstname" 
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentFirstname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.firstname}"</c:if>  
                                      placeholder="First Name" >
                                    </div>
                              </div>
                          
                                      
                           <div class="col-md-5">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentMiddlename">Middle Name</label>
                                      <input type="text" class="form-control" id="agentMiddlename" name="agentMiddlename" placeholder="Middle Name" 
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentMiddlename}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.middlename}"</c:if> ">
                                    </div>
                           </div>
                            
                            
                              <div class="col-md-5">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentLastname">Last Name</label>
                                      <input type="text" class="form-control" name="agentLastname" id="agentLastname" placeholder="Last Name" 
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentLastname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.lastname}"</c:if> ">
                                    </div>
                              </div>
                              
                              <div class="clearfix"></div>
                              
                              <div class="col-md-5">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentPhoto">Agent Photo</label><br />
                                      <div class="btn-group btn-group-xs">
                                          <div class="btn btn-primary">
                                                <!--Upload Picture  <i class="fa fa-upload" aria-holder="true"></i>--> 
                                                <input type="file" name="agentPhoto" accept="image/gif, image/jpeg, image/png" id="agentPhoto" />
                                          </div>
                                      </div>
                                    </div>
                              </div>
                            
                        </fieldset>
                </div>
                        
                    <div class="row" style="padding-top:10px">
                       
                            <fieldset>
                                <legend style="padding-right:10px !important;">Login Information</legend>
                          
                              <div class="col-md-5">
                                  <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="agentEmail">Email</label>
                                      <input type="email" class="form-control" id="agentEmail" name="agentEmail" placeholder="Email"  
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentEmail}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.email}"</c:if>" 
                                      <c:if test="${agent.agentId !='' && agent.agentId != null }">readonly="true"</c:if>
                                      /> 
                                    </div>
                                </div>
                                      
                               <div class="clearfix"></div>
                            
                                <div class="col-md-5">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="agentPassword">Password</label>
                                      <input type="password" class="form-control" id="agentPassword" name="agentPassword" placeholder="Password"  />
                                    </div>
                                </div>
                               
                                <div class="clearfix"></div>
                            
                                <div class="col-md-5">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="agentConfirmPassword">Confirm Password</label>
                                      <input type="password" class="form-control" id="agentConfirmPassword" name="agentConfirmPassword" placeholder="Confirm Password"  />
                                    </div>
                                </div>
                            
                            </fieldset>
                    </div>
                                      
                  
                    
                    
                    <div class="row" style="padding-top:10px;">
                        <fieldset>
                            <legend style="padding-left:10px !important;">Contact Information</legend>
                    
                        
                        <div class="col-md-5">
                            <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                            <label for="agentStreet">Street</label>
                                <input type="text" class="form-control" id="agentStreet" name="agentStreet" placeholder="Street" value=""/>
                            </div>
                        </div>
                        
                        <div class="col-md-4">
                            <div class="form-group" style="padding-left:10px !important;">
                            <label for="agentCity">City</label>
                                <input type="text" class="form-control" id="agentCity" name="agentCity" placeholder="City" /> 
                            </div>
                        </div>
                                       
                        <c:if test="${fn:length(errors) > 0 }"><c:set var="state" value="${param.agentState}" scope="session" /></c:if>
                        <c:if test="${fn:length(errors) <= 0 }"><c:set var="state" value="${agent.state}" scope="session" /></c:if>
                      
                        <div class="col-md-5">
                            <div class="form-group" style="margin-right:24px !important;padding-left:20px !important;"  >
                              <label for="agentState">State</label>
                              <select class="form-control" id="agentState" name="agentState"  >
                                    <option value="">--choose--</option>
                                    
                                    <option value="ABUJA FCT" <c:if test="${state == 'ABUJA FCT'}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                    <option value="Abia" <c:if test="${state == 'Abia'}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                    <option value="Adamawa" <c:if test="${state == 'Adamawa'}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                    <option value="Akwa Ibom" <c:if test="${state == 'Akwa Ibom'}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                    <option value="Anambra" <c:if test="${state == 'Anambra'}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                    <option value="Bauchi" <c:if test="${state == 'Bauchi'}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                    <option value="Bayelsa" <c:if test="${state == 'Bayelsa'}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                    <option value="Benue" <c:if test="${state == 'Benue'}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                    <option value="Borno" <c:if test="${state == 'Borno'}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                    <option value="Cross River" <c:if test="${state == 'Cross River'}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                    <option value="Delta" <c:if test="${state == 'Delta'}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                    <option value="Ebonyi" <c:if test="${state == 'Ebonyi'}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                    <option value="Edo" <c:if test="${state == 'Edo'}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                    <option value="Ekiti" <c:if test="${state == 'Ekiti'}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                    <option value="Enugu" <c:if test="${state == 'Enugu'}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                    <option value="Gombe" <c:if test="${state == 'Gombe'}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                    <option value="Imo" <c:if test="${state == 'Imo'}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                    <option value="Jigawa" <c:if test="${state == 'Jigawa'}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                    <option value="Kaduna" <c:if test="${state == 'Kaduna'}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                    <option value="Kano" <c:if test="${state == 'Kano'}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                    <option value="Katsina" <c:if test="${state == 'Katsina'}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                    <option value="Kebbi" <c:if test="${state == 'Kebbi'}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                    <option value="Kogi" <c:if test="${state == 'Kogi'}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                    <option value="Kwara" <c:if test="${state == 'Kwara'}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                    <option value="Lagos" <c:if test="${state == 'Lagos'}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                    <option value="Nassarawa" <c:if test="${state == 'Nassarawa'}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                    <option value="Niger" <c:if test="${state == 'Niger'}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                    <option value="Ogun" <c:if test="${state == 'Ogun'}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                    <option value="Ondo" <c:if test="${state == 'Ondo'}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                    <option value="Osun" <c:if test="${state == 'Osun'}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                    <option value="Oyo" <c:if test="${state == 'Oyo'}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                    <option value="Plateau" <c:if test="${state == 'Plateau'}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                    <option value="Rivers" <c:if test="${state == 'Rivers'}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                    <option value="Sokoto" <c:if test="${state == 'Sokoto'}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                    <option value="Taraba" <c:if test="${state == 'Taraba'}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                    <option value="Yobe" <c:if test="${state == 'Yobe'}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                    <option value="Zamfara" <c:if test="${state == 'Zamfara'}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                              </select>
                            </div>
                        </div>
                        
                        
                        <div class="col-md-5">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="agentPhone">Phone Number</label>
                                <input type="tel" class="form-control" id="agentPhone" name="agentPhone" placeholder="Phone Number"  minlength="8" maxlength="11" value=""/>
                                    
                            </div>
                        </div>
                           
                        </fieldset>
                       </div>
                   
                  
                    <div class="row" style="padding-top:10px">
                            <fieldset>
                        <legend style="padding-left:20px !important;">Account Information</legend>
                        
                          <div class="col-md-4">
                                <div class="form-group" style="padding-left:10px !important;">
                                  <label for="agentBankName">Bank Name</label>
                                  <input type="text" class="form-control" id="agentBankName" name="agentBankName" placeholder="Enter Bank Name"  
                                    value="" />
                                </div>
                          </div>
                                
                          <div class="col-md-4">
                                <div class="form-group" style="padding-left:10px !important;">
                                <label for="agentBankAccountName">Bank Account Name</label>
                                   
                                    <input type="text" class="form-control" id="agentBankAccountName" name="agentBankAccountName" placeholder="Enter Bank Account Name"  
                                           value="" />
                                       
                                </div>
                          </div>
                            
                        <div class="col-md-4" >
                        <div class="form-group" style="padding-left:10px !important;padding-right:25px !important;">
                          <label for="agentBankAccountNumber">Bank Account Number</label>
                           <input type="text" class="form-control"  id="agentBankAccountNumber"  name="agentBankAccountNumber" placeholder="Enter Bank Account Number" 
                                  value=""/>
                        </div>
                        </div>
                        
                        </fieldset>
                    </div>
                        
                    
                    <div class="row" style="padding-top:10px">
                            <fieldset>
                            <legend style="padding-left:20px !important;">Next of Kin</legend>
                            
                              <div class="col-md-6">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentKinNames">Next of Kin - Name</label>
                                      <input type="text" class="form-control" id="agentKinNames" name="agentKinName" placeholder="Enter Kin Name"  
                                             value=""/>
                                    </div>
                                </div>
                                    
                                <div class="col-md-6">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                    <label for="agentKinPhone">Next of Kin - Phone Number</label>
                                       
                                        <input type="tel" class="form-control" id="agentKinPhone" name="agentKinPhone" placeholder="Enter Kin Phone Number"  
                                               value=""/>
                                            
                                    </div>
                                </div>
                            
                            
                             <div class="col-md-6 " >
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentKinAddress" style="">Next of Kin - Address</label>
                                       <input type="text" class="form-control" id="agentKinAddress" name="agentKinAddress" placeholder="Enter Kin Address" style="  width:100%;" value=""/>
                                    </div>
                            </div>
                            
                            <div class="clearfix"></div>
                                    
                            <div class="col-md-6" >
                                <div class="form-group" style="padding-left:10px !important;padding-right:20px !important">
                                 <label for="agentKinPhoto" style="">Next of Kin Picture</label>
                                    <br/>
                                    <div class="btn btn-primary">
                                        <input type="file" id="agentKinPhoto" name="agentKinPhoto" accept="image/gif, image/jpeg, image/png" 
                                         style="max-height:220px !important;"
                                           />
                                    </div>
                                </div>
                           </div>
                            
                               
                            </fieldset>

                       </div>
                      
                        <div class="row" style="padding-top:10px;padding-bottom: 10px">
                            <div class="col-md-12" style="text-align:center !important;">
                                Do you agree to the terms of the <a href="#" onclick="$('#agreementStatusModal').modal();">agreement document?</a>
                            <br/>    <input type="radio" class="" name="agreement_document" id="agree" value="agree"  onclick="agreementStatusChecked(this)" <c:if test="${agent.agentId!=null && agent.agentId!=''}">checked</c:if>/> I agree  &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="agreement_document" id="decline"  onclick="agreementStatusChecked(this)" value="decline"/>I disagree
                            <br/><br/><br/>
                            </div>
                        </div>
                  
                  
                  <div class="col-md-12" style="margin-left:10px !important;margin-right:10px !important;margin-top:-20px !important; background-color:transparent;">
                      <input type="submit" class="btn btn-primary pull-right" name="agentCreate" value="Save" id="agentCreate" />
                  </div>
             </form>    
          </div>
                            
          
                            <footer class="main-footer" style="margin-left:0;">
        <!-- To the right -->
        <div class="pull-right hidden-xs">
          Powered by <b>Techie Planet</b>
        </div>
        <!-- Default to the left -->
        <!--<strong>Copyright &copy; 2016 <a href="#">Company</a>.</strong> All rights reserved.-->
        &nbsp;
        </footer>                     
         
      </div>     
               
         
          
      
      
      
  
   
       
      
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
 
    <!-- iCheck 1.0.1 -->
    <script src="plugins/iCheck/icheck.min.js"></script>
    

    <!-- DataTables -->
    <script src="plugins/datatables/jquery.dataTables.min.js"></script>
    <script src="plugins/datatables/dataTables.bootstrap.min.js"></script>
    
    
    <!-- AdminLTE App -->
    <!--<script src="dist/js/app.min.js"></script>-->
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/functions.js"></script>


    <script src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
    
    <script>
      $(function () {
            //iCheck for checkbox and radio inputs
            $('input[type="checkbox"].minimal, input[type="radio"].minimal').iCheck({
              checkboxClass: 'icheckbox_minimal-blue',
              radioClass: 'iradio_minimal-blue'
            });
            //Red color scheme for iCheck
            $('input[type="checkbox"].minimal-red, input[type="radio"].minimal-red').iCheck({
              checkboxClass: 'icheckbox_minimal-red',
              radioClass: 'iradio_minimal-red'
            });
            //Flat red color scheme for iCheck
            $('input[type="checkbox"].flat-red, input[type="radio"].flat-red').iCheck({
              checkboxClass: 'icheckbox_flat-green',
              radioClass: 'iradio_flat-green'
            });
        });
      </script>
      
  </body>    
</html>