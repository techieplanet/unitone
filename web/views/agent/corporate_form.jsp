<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--
    Process Step
-->

<div class="stepwizard">
    <div class="stepwizard-row">
        <div class="stepwizard-step">
            <button type="button" id="process-step-1" class="btn btn-primary btn-circle"  onclick="return showCustomerReg()">1</button>
            <p>Agent Form</p>
        </div>
        <div class="stepwizard-step">
            <button type="button" id="process-step-2" class="btn btn-default btn-circle" disabled="disabled"  onclick="proceed()">2</button>
            <p>Documents</p>
        </div>
    </div>
</div>
<form role="form" name="agentRegistration" method="POST" action="${pageContext.request.contextPath}/Agent?action=${action}&agentId=${agent.agentId}" enctype="multipart/form-data" id="agentForm">

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

    <div class="row" id="step1" style="display:block">
        <div class="col-md-12">
            <!-- general form elements -->

            <!-- form start -->
            
            
            <div class="box box-primary" id="step1_box">
                <div class="box-header with-border">
                    <h3 class="box-title">Corporate Agent Registration Form
                    </h3>
                </div><!-- /.box-header -->
                <input type="hidden"  name="corporate" class="form-control" id="corporate" value="true"/>
                <input type="hidden"  name="from" class="form-control" value="${from}"/>
                <!-- form start -->
                
                
                <div style="background:#ecf0f5 !important;">
                    <div class="box-body">
                        
                    <c:if test="${action=='new'}">
                        <div class="box box-default">
                            <div class="row" style="padding-top:10px;">
                                <div class="col-md-12">
                                     <fieldset>
                                         <legend style="padding-left:10px !important;">Referral</legend>
                                <div class="col-md-4">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="ref_code">Referral Code (If Any)</label>
                                      <input type="text" class="form-control bold" name="refCode" id="refCode" placeholder="Refferal Code" style="height: 50px; font-size: 25px; text-transform: uppercase;"
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.refCode}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${refAgent.account.accountCode}"</c:if> 
              <% //<c:if test="${fn:length(errors) > 0 }">"${param.refCode}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${refAgent.account.accountCode}"</c:if> %>                                                 
                                             <c:if test='${referralMode == true}'>readonly</c:if> onchange="referral()" >
                                    </div>
                                </div>
                                    <div class="col-md-6">
                                    <div  class="form-group" id="referralInfo">
                                        <c:if test='${referralMode == true}'>
                                            <img src="${agentImageAccessDir}/${refAgent.photoPath}" class="img img-responsive img-thumbnail" width="50px" />
                                            <h3>${refAgent.firstname} ${refAgent.middlename} ${refAgent.lastname}</h3>
                                        </c:if>
                                    </div>
                                    </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        </c:if>


                        <div class="box box-default">
                            <div class="row" style="padding-top:10px;">
                                <div class="col-md-12">
                                    <fieldset>
                                        <legend style="padding-left:10px !important;">Company Information</legend>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <div class="form-group" style="padding-left:10px !important;">
                                                    <label for="agentFirstname">Company Name</label>
                                                    <input type="text"  name="agentFirstname" class="form-control" id="agentFirstname" 
                                                           value="<c:if test="${fn:length(errors) > 0 }">${param.agentFirstname}</c:if><c:if test="${fn:length(errors) <= 0 }">${agent.firstname}</c:if>"
                                                               placeholder="Company Name" />
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                <div class="form-group" style="padding-left:10px !important;">
                                                    <label for="agentRCNumber">RC Number</label>
                                                    <input type="text"  name="agentRCNumber" class="form-control" id="agentRCNumber" 
                                                           value="<c:if test="${fn:length(errors) > 0 }">${param.agentRCNumber}</c:if><c:if test="${fn:length(errors) <= 0 }">${agent.RCNumber}</c:if>"
                                                               placeholder="Company RC Number" />
                                                    </div>
                                                </div>
                                                <div class="col-md-6">
                                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                                        <label for="agentEmail">Email</label>
                                                        <input type="email" class="form-control" id="agentEmail" name="agentEmail" placeholder="Email"  
                                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.agentEmail}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.email}"</c:if>" 
                                                        <c:if test="${agent.agentId !='' && agent.agentId != null }">readonly="true"</c:if>
                                                            /> 
                                                    </div>
                                                </div>
                                                  <div class="col-md-6">
                                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                                        <label for="agentPhone">Office Phone Number</label>

                                                        <input type="tel" class="form-control" id="agentPhone" name="agentPhone" placeholder="Phone Number"  minlength="8" maxlength="11"
                                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.agentPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.phone}"</c:if> "/>

                                                        </div>
                                                    </div>
                              <%//              <c:if test="${agent.agentId =='' || agent.agentId==null}">
                                //
                                  //              <div class="col-md-6">
                                  //                  <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                    //                    <label for="agentPassword">Password</label>
                                    //                    <input type="password" class="form-control" id="agentPassword" name="agentPassword" placeholder="Password"  />
                                    //                </div>
                                    //            </div>
                                    //            <div class="col-md-6">
                                    //                <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                    //                    <label for="agentConfirmPassword">Confirm Password</label>
                                    //                    <input type="password" class="form-control" id="agentConfirmPassword" name="agentConfirmPassword" placeholder="Confirm Password"  />
                                    //                </div>
                                    //            </div>
                                    //        </c:if>
                                    %>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>

                        <div class="box box-default">

                            <div class="row" style="padding-top:10px;">
                                <div class="col-md-12" >
                                    <fieldset>
                                        <legend style="padding-left:10px !important;">Office Address</legend>
                                        <div class="row">

                                            <div class="col-md-12" style="padding:20px !important;">
                                                <div class="row">
                                                    <div class="col-md-4">

                                                        <div class="form-group" >
                                                            <label for="agentStreet">Address</label>
                                                            <input type="text" class="form-control" id="agentStreet" name="agentStreet" placeholder="Street"  
                                                                   value=<c:if test="${fn:length(errors) > 0 }">"${param.agentStreet}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.street}"</c:if>/>
                                                            </div>
                                                        </div>

                                                        <div class="col-md-4">
                                                            <div class="form-group" >
                                                                <label for="agentCity">City</label>

                                                                <input type="text" class="form-control" id="agentCity" name="agentCity" placeholder="City"  
                                                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.agentCity}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.city}"</c:if> "
                                                                    />


                                                            </div>
                                                        </div>
                                                    
                                                        <div class="col-md-4">
                                                            <div class="form-group"  >
                                                                <label for="agentState" >State</label>
                                                                <input type="text" class="form-control" id="agentState" name="agentState" placeholder="State"
                                                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.agentState}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.state}"</c:if> "
                                                                    />
                                                            <%  // <select class="form-control" id="agentState" name="agentState"  >
                                                                //     <option value="">--choose--</option>

                                                                //     <option value="ABUJA FCT" <c:if test="${state == 'ABUJA FCT'}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                                                //     <option value="Abia" <c:if test="${state == 'Abia'}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                                                //     <option value="Adamawa" <c:if test="${state == 'Adamawa'}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                                                //   <option value="Akwa Ibom" <c:if test="${state == 'Akwa Ibom'}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                                                //   <option value="Anambra" <c:if test="${state == 'Anambra'}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                                                //   <option value="Bauchi" <c:if test="${state == 'Bauchi'}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                                                //   <option value="Bayelsa" <c:if test="${state == 'Bayelsa'}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                                                //   <option value="Benue" <c:if test="${state == 'Benue'}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                                                //   <option value="Borno" <c:if test="${state == 'Borno'}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                                                //   <option value="Cross River" <c:if test="${state == 'Cross River'}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                                                //   <option value="Delta" <c:if test="${state == 'Delta'}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                                                //   <option value="Ebonyi" <c:if test="${state == 'Ebonyi'}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                                                //   <option value="Edo" <c:if test="${state == 'Edo'}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                                                //   <option value="Ekiti" <c:if test="${state == 'Ekiti'}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                                                //   <option value="Enugu" <c:if test="${state == 'Enugu'}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                                                //   <option value="Gombe" <c:if test="${state == 'Gombe'}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                                                //   <option value="Imo" <c:if test="${state == 'Imo'}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                                                //   <option value="Jigawa" <c:if test="${state == 'Jigawa'}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                                                //   <option value="Kaduna" <c:if test="${state == 'Kaduna'}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                                                //   <option value="Kano" <c:if test="${state == 'Kano'}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                                                //   <option value="Katsina" <c:if test="${state == 'Katsina'}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                                                //   <option value="Kebbi" <c:if test="${state == 'Kebbi'}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                                                //   <option value="Kogi" <c:if test="${state == 'Kogi'}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                                                //   <option value="Kwara" <c:if test="${state == 'Kwara'}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                                                //   <option value="Lagos" <c:if test="${state == 'Lagos'}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                                                //   <option value="Nassarawa" <c:if test="${state == 'Nassarawa'}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                                                //   <option value="Niger" <c:if test="${state == 'Niger'}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                                                //   <option value="Ogun" <c:if test="${state == 'Ogun'}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                                                //   <option value="Ondo" <c:if test="${state == 'Ondo'}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                                                //   <option value="Osun" <c:if test="${state == 'Osun'}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                                                //   <option value="Oyo" <c:if test="${state == 'Oyo'}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                                                //   <option value="Plateau" <c:if test="${state == 'Plateau'}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                                                //   <option value="Rivers" <c:if test="${state == 'Rivers'}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                                                // <option value="Sokoto" <c:if test="${state == 'Sokoto'}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                                                // <option value="Taraba" <c:if test="${state == 'Taraba'}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                                                // <option value="Yobe" <c:if test="${state == 'Yobe'}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                                                // <option value="Zamfara" <c:if test="${state == 'Zamfara'}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                                                                //</select>
%>
                                                        </div>
                                                    </div>
                                                </div>
                                                
                                                </div>

                                            </div>
                                        </fieldset>
                                    </div>
                                </div>
                            </div>
                            
                         <div class="box box-default">
                                <div class="row" style="padding-top:10px;">
                                    <div class="col-md-12">
                                        <fieldset>
                                            <legend style="padding-left:20px !important;">Account Information</legend>

                                            <div class="col-md-12">

                                                <div class="row" style="padding: 10px">
                                                    <div class="col-md-4">
                                                        <div class="form-group">
                                                            <label for="agentBankName">Bank Name</label>
                                                            <select name="agentBankId" id="agentBankName" class="form-control">
                                                            <option value="select" >--choose--</option>
                                                       <c:forEach items="${Banks}" var="bank">
                                                           <option value="${bank.id}" <c:if test="${agent.bank.id == bank.id.toString() || param.agentBankId == bank.id.toString()}">selected</c:if>>${bank.bankName}</option>
                                                       </c:forEach>
                                                            </select></div>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <div class="form-group" >
                                                            <label for="agentBankAccountName">Bank Account Name</label>

                                                            <input type="text" class="form-control" id="agentBankAccountName" name="agentBankAccountName" placeholder="Enter Bank Account Name"  
                                                                   value=<c:if test="${fn:length(errors) > 0 }">"${param.agentBankAccountName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.bankAcctName}"</c:if> "/>

                                                        </div>
                                                    </div>

                                                    <div class="col-md-4" >
                                                        <div class="row" >
                                                            <div class="form-group" >
                                                                <label for="agentBankAccountNumber">Bank Account Number</label>
                                                                <input type="text" class="form-control"  id="agentBankAccountNumber"  name="agentBankAccountNumber" placeholder="Enter Bank Account Number" 
                                                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.agentBankAccountNumber}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.bankAcctNumber}"</c:if> "/><br/>
                                                            </div>
                                                        </div>
                                                    </div>


                                                </div>
                                            </div>




                                        </fieldset>
                                    </div>
                                </div>

                                    <input type="hidden" name="agent_id" id="agent_id" value="${agent.agentId}">
                            <input type="hidden" name="id" id="id" value="${agent.agentId}">
                            <div class="box-footer" style="margin-left:10px !important;margin-right:10px !important;margin-top:-20px !important; background-color:transparent;">
                               <c:if test="${action == 'new' }">  <button type="button" class="btn btn-primary pull-right" onclick="return validateAgentForm(1)" >Proceed</button></c:if>
                               <c:if test="${action == 'edit' }"> <a class="btn btn-primary" href="#" onclick="return proceedAgentUpdate()" role="button">Proceed <i class="fa fa-long-arrow-right"></i></a></c:if>
                            </div>
                        </div>

                    </div>
                </div><!-- /.box -->
            </div>
        </div>
    </div>

    <!-- Step two . File Upload -->    
    <div class="row" id="step2" style="display:none">
        <div class="col-md-12">
            <!-- general form elements -->

            <!-- form start -->
            <div class="box box-primary" id="step2_box">
                <div class="box-header with-border">
                    <h3 class="box-title">Document
                    </h3>
                </div><!-- /.box-header -->
                <!-- form start -->
                <div style="background:#ecf0f5 !important;">
                    <div class="box-body">
                        <div class="box box-default">

                            <div class="row" style="padding-top:10px;margin: 10px">
                                <div class="col-md-12" >
                                    <fieldset>
                                        <div class="row">

                                            <div class="col-md-12">
                                                <div class="row">
                                                    <div class="col-md-12">
                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="agentPhoto">Company Logo</label>
                                                                <input    class="form-control" type="file" name="agentPhoto" accept="image/gif, image/jpeg, image/png, image/bmp" id="agentPhoto" >
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="agentCertOfIncorporation">Certificate of Incorporation</label>
                                                                <input    class="form-control" type="file" name="agentCertOfIncorporation" accept="image/gif, image/jpeg, image/png, image/bmp" id="agentCertOfIncorporation" >
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="agentBoardResolution">Board Resolution/Letter Of Introduction For A Rep.</label>
                                                                <input    class="form-control" type="file" name="agentBoardResolution" accept="image/gif, image/jpeg, image/png, image/bmp" id="agentBoardResolution" >
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="agentPassportOfRep">PassPort of Rep</label>
                                                                <input    class="form-control" type="file" name="agentPassportOfRep" accept="image/gif, image/jpeg, image/png, image/bmp" id="agentPassportOfRep" >
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="agentIDCardOfRep">ID Card of Rep</label>
                                                                <input    class="form-control" type="file" name="agentIDCardOfRep" accept="image/gif, image/jpeg, image/png, image/bmp" id="agentIDCardOfRep" >
                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="agentUtilityBillOfRep">Utility Bill of Rep.</label>
                                                                <input    class="form-control" type="file" name="agentUtilityBillOfRep" accept="image/gif, image/jpeg, image/png, image/bmp" id="agentUtilityBillOfRep" >
                                                            </div>
                                                        </div>
                                                        <legend> Directors </legend>
                                                        <div >
                                                            <table class="table table-bordered table-striped table-hover">
                                                                 <thead>
                                                                <tr>
                                                                    <th>SN</th>
                                                                    <th>Name</th>
                                                                    <th>Passport</th>
                                                                    <th>ID Card</th>
                                                                </tr>
                                                                 </thead>
                                                                 <tbody id="directors">
                                                                     <tr>
                                                                         <td>1</td>
                                                                         <td>
                                                                             <input    class="form-control" type="text" name="agentDirectorName1"  placeholder="Director Name" id="agentDirectorName1">
                                                                        </td>
                                                                        <td>
                                                                            <input    class="form-control" type="file" name="agentDirectorPassport1" accept="image/gif, image/jpeg, image/png, image/bmp" id="agentDirectorPassport1" >
                                                                        </td>
                                                                         <td>
                                                                             <input    class="form-control" type="file" name="agentDirectorIDCard1" accept="image/gif, image/jpeg, image/png, image/bmp" id="agentDirectorIDCard1">
                                                                        </td>
                                                                     </tr> 
                                                                 </tbody>
                                                            </table>
                                                        </div>
                                                        
                                                        <div class="col-md-12">
                                                            <div class="col-md-6">
                                                                <div class="form-group" >
                                                                    <button class="btn btn-primary" type="button"  onclick="addNewDirector()">Add new Directors Particulars </button>
                                                                    <button class="btn btn-primary" type="button"  onclick="removeLastDirectorEntry()">Remove Last Row </button>
                                                                </div>
                                                            </div>
                                                            
                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>


                            <c:if test="${sessionScope.user.getSystemUserTypeId() == 2}">
                                <c:if test="${agent.agentId==null || agent.agentId==''}">
                                    <div class="col-md-12" style="text-align:center !important;">
                                        Do you agree to the terms of the <a href="#" onclick="$('#agreementStatusModal').modal();">agreement document?</a>
                                        <br/>    <input type="radio" class="" name="agreement_document" id="agree" value="agree"  onclick="agreementStatusChecked(this)" <c:if test="${agent.agentId!=null && agent.agentId!=''}">checked</c:if>/> I agree  &nbsp;&nbsp;&nbsp;&nbsp;
                                            <input type="radio" name="agreement_document" id="decline"  onclick="agreementStatusChecked(this)" value="decline"/>I disagree
                                            <br/><br/><br/>
                                        </div>
                                </c:if>

                            </c:if>
                            <div class="box-footer" style="margin-left:10px !important;margin-right:10px !important;margin-top:-20px !important; background-color:transparent;">
                               <c:if test="${action == 'new' }">  <button type="button" onclick="validateAgentForm(2)" class="btn btn-primary pull-right" >Save</button></c:if>
                               <c:if test="${action == 'edit' }">  <button type="submit"  class="btn btn-primary pull-right" >Save</button></c:if>
                               <input type="hidden" id="pageContext" value="${pageContext.request.contextPath}" />
                            </div>
    
      
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>
<script>
    var formFieldStage1 = [["agentFirstname", "", "Company Name"],
        ["agentRCNumber", "", "Company RC Number"],
        ["agentEmail", "", "Company Email"],
        ["agentPhone", "", "Company Office Phone Number"],
        ["agentStreet", "", "Company  Address"],
        ["agentCity", "", "Company's City"],
        ["agentState", "", "Company's State"],
        ["agentBankName", "select", "Company's Bank Name"],
        ["agentBankAccountName", "", "Company's Bank Account Name"],
        ["agentBankAccountNumber", "", "Company's Bank Account Number"]
        ];

    var formFieldStage2 = [["agentPhoto", "", "Company Logo"],
        ["agentCertOfIncorporation", "", "Company Certificate of Incorporation"],
        ["agentBoardResolution", "", "Company Board Resolution"],
        ["agentPassportOfRep", "", "Passport Of A Representative"],
        ["agentIDCardOfRep", "", "Representative IDCard"],
        ["agentUtilityBillOfRep", "", "Utility Bill Of Representative"],
        ["agentDirectorName1", "text", "Name Of A Director"],
        ["agentDirectorPassport1", "", "Director Passport"],
        ["agentDirectorIDCard1", "", "Director IDCard"]];


    function validateAgentForm(stage) {
        //Validating Elements on the first stage of validation
        //Array object have the format of {Field_Name , default_Value , error_text}
        var errors = [];
        if (stage === 1)
        {

            $("#agentErrorModal .modal-body").html("");

            for (var i = 0; i < formFieldStage1.length; i++)
            {
                var temp = $("#" + formFieldStage1[i][0]).val();
                if (temp === formFieldStage1[i][1])
                {
                    $("#" + formFieldStage1[i][0]).css("border", "1px solid red");
                    if (formFieldStage1[i][1] === "select") //If The default value is selec i.e Used for drop down
                    {
                        errors.push("Please Select " + formFieldStage1[i][2]); //
                    } else
                    {
                        errors.push("Please Enter " + formFieldStage1[i][2]);
                    }
                    break;
                    //border: 1px solid red;
                }
                //#d2d6de
                $("#" + formFieldStage1[i][0]).css("border", "1px solid #d2d6de");
            }

            if (errors.length > 0)
            {
                var errorText = '';

                for (var key in errors) {
                    var errorText = '' + errors[key] + '<br />';
                    $("#agentErrorModal .modal-body").append(errorText);
                }
                $("#agentErrorModal").modal();
                return;
            }


            var url = $("#pageContext").val();
            //Server Email validation needed_____(0^0)
            $.ajax({
                url: url + "${pageContext.request.contextPath}/Agent?action=email_validation",
                method: 'GET',
                data: {email: $("#agentEmail").val(), type: 'xmlhttp'},
                success: function (data) {
                    removeLoadingState();
                    console.log(data);

                    var res = JSON.parse(data);
                    if (res.code === "-1" || res.code === -1) {
                        errors.push("Email already exist");
                    }

                    if (errors.length > 0)
                    {
                        $("#agentEmail").css("border", "1px solid red");
                        $("#agentErrorModal .modal-body").append(errors[0])
                        $("#agentErrorModal").modal();
                    } else {
                        $("#step1").css("display", "none");
                        $("#step2").css("display", "block");

                        $("#process-step-1").removeClass('btn-primary').addClass('btn-default');
                        $("#process-step-2").removeClass('btn-default').addClass('btn-primary');
                        $("#process-step-2").removeAttr('disabled');
                    }

                },
                error: function (xhr, status_code, status_text) {
                    console.log(status_code + " : " + status_text);
                    $("#agentErrorModal .modal-body").append("Error While validating Email");
                    $("#agentErrorModal").modal();
                    removeLoadingState();
                }
            });

        } 
        else if (stage === 2)
        {
            // validate and setup properly
            $("#agentErrorModal .modal-body").html("");

            for (var i = 0; i < formFieldStage2.length; i++)
            {
                var temp = $("#" + formFieldStage2[i][0]).val();
                if (temp == "")
                {
                    if(formFieldStage2[i][1] == "text")
                    {
                    $("#" + formFieldStage2[i][0]).css("border", "1px solid red");

                    errors.push("Please Enter " + formFieldStage2[i][2]);
                    }
                    else
                    {
                    $("#" + formFieldStage2[i][0]).css("border", "1px solid red");

                    errors.push("Please Input File For " + formFieldStage2[i][2]);
                    }
                    break;
                    //border: 1px solid red;
                }
                //#d2d6de
                $("#" + formFieldStage2[i][0]).css("border", "1px solid #d2d6de");
            }

            if (errors.length > 0)
            {
                var errorText = '';

                for (var key in errors) {
                    var errorText = '' + errors[key] + '<br />';
                    $("#agentErrorModal .modal-body").append(errorText);
                }
                $("#agentErrorModal").modal();
                return;
            }
           document.getElementById("agentForm").submit();
        }
    }
    
    function proceedAgentUpdate(){
             $("#step1").css("display","none");
             $("#step2").css("display","block");
        
             $("#process-step-1").removeClass('btn-primary').addClass('btn-default');
             $("#process-step-2").removeClass('btn-default').addClass('btn-primary');
             $("#process-step-2").removeAttr('disabled');
  }

    function referral(){
        var refBox = $("#referralInfo");
        var refCode = $("#refCode").val();
        
        //Clear all child element of refBox
        refBox.html("");
        
        //Fetch the agent Infos
        var imgPath ="";
        var agentName = "";
        var temp;
        
        $.ajax({
            type: "GET",
            url:"${pageContext.request.contextPath}/Agent?action=referral&refCode=" + refCode,
            success: function(result){
                console.log(result);
                var jData = JSON.parse(result);
                imgPath = jData.imgPath;
                agentName = jData.name;
                //Create the Image Object
            temp = document.createElement("img");
            temp.setAttribute("src" , "/uploads/NeoForce/images/agents/" + imgPath);
            temp.setAttribute("class" , "img img-responsive img-thumbnail");
            temp.setAttribute("width" , "50px");
        
            refBox.append(temp);
             //Create The name
            temp = document.createElement("h3");
            temp.textContent = agentName;
        
            refBox.append(temp);
             },
            error: function(xhr , status_code , status_text){
                console.log(status_text);
                //Create The name
            temp = document.createElement("h3");
            temp.textContent = "Invalid Referral Code";
            temp.setAttribute("style" , "color:red");
            refBox.append(temp);
            }
        });
        
    }
</script>

<%
//<div class="modal fade" id="agreementStatusModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
//    <div class="vertical-alignment-helper">
//        <div class="modal-dialog vertical-align-center">
//            <div class="modal-content">
//                <div class="modal-header">
//                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
//                    <h4 class="modal-title">NEOFORCE </h4>
//                </div>
//
//                <div class="modal-body">
//
//                    <h5>The standard Lorem Ipsum passage, used since the 1500s</h5>
//                    <p>
//                        "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."
//
//                        Section 1.10.32 of "de Finibus Bonorum et Malorum", written by Cicero in 45 BC
//
//
//
//                        "On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
//                    </p>
//                </div>
//                <div class="modal-footer">
//                    <button type="button" class="btn btn-default pull-left" data-dismiss="modal"> Cancel</button>
//                    <button  type="button"  id="agreement" class="btn btn-success" onclick="modal_agree()"><i class="fa fa-check"></i> I agree</button>
//                </div>
//            </div>
//        </div><!-- /.modal-content -->
//    </div><!-- /.modal-dialog -->
//</div><!-- /.modal -->
%>