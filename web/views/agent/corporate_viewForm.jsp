<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<div class="row">
    <!-- Customer Registration Form -->
    <div class="col-md-12">
        <!-- general form elements -->

        <!-- form start -->
        <div class="box box-primary">
            <div class="box-header with-border">
                <h3 class="box-title">Agent Registration Form 



                </h3>
            </div><!-- /.box-header -->
            <!-- form start -->
            <div style="background:#ecf0f5 !important;">
                <form role="form" name="agentRegistration" method="POST" action="Agent" enctype="multipart/form-data" id="agentForm" onSubmit="return false;">
                    <div class="box-body">

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

                        <c:if test="${waitingroute}">
                            <a class="btn btn-primary btn-sm margintop5negative" user="button" href="${pageContext.request.contextPath}/Agent?action=waiting"><i class="fa fa-arrow-left"></i>&nbsp;&nbsp;&nbsp;&nbsp;Back to list</a>
                        </c:if>
                        <c:if test="${!waitingroute}">
                            <a class="btn btn-primary btn-sm margintop5negative" user="button" href="${pageContext.request.contextPath}/Agent"><i class="fa fa-arrow-left"></i>&nbsp;&nbsp;&nbsp;&nbsp;Back to list</a>
                        </c:if>

                        <span class="pull-right">
                            <c:if test="${fn:contains(sessionScope.user.permissions, 'approve_agent') && waitingroute}">
                                <a id="approve-agent-btn" class="btn btn-success btn-sm margintop5negative" href="#" role="button" onClick="showActivateModal('${pageContext.request.contextPath}', 'Agent', <c:out value = '${agent.agentId}' /> , 1); return false;"><i class="fa fa-check-square-o"></i>&nbsp;&nbsp;&nbsp;&nbsp;Approve Agent</a>
                            </c:if>
                        </span>

<!--<a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Agent"><i class="fa fa-angle-double-left"></i> Back to list</a>-->
                        <div class="box-footer " style="margin-right:10px !important;margin-top:-10px !important;margin-bottom:-8px !important; background-color:transparent;">

                            <!--                               <input type="submit" class="btn btn-primary pull-right" name="agentCreate" value="Save" id="agentCreate"/><a class="btn btn-primary" href="Agent?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Agent</a>
                            -->
                        </div>
                        <div class="box box-default">
                            <div class="row" style="padding-top:10px;">
                                <div class="col-md-6">
                                    <fieldset>
                                        <legend style="padding-left:10px !important;">Company Information</legend>
                                        <div class="row">
                                            <div class="col-md-12">
                                                <div class="form-group" style="padding-left:10px !important;">
                                                    <label for="agentFirstname">Company Name</label>
                                                    <input type="text"  name="agentFirstname" class="form-control" id="agentFirstname" readonly
                                                           value=<c:if test="${fn:length(errors) > 0 }">"${param.agentFirstname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.firstname}"</c:if>  
                                                    placeholder="Company Name" />
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                                    <label for="agentEmail">Email</label>
                                                    <input type="email" class="form-control" id="agentEmail" name="agentEmail" placeholder="Email"   readonly
                                                           value=<c:if test="${fn:length(errors) > 0 }">"${param.agentEmail}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.email}"</c:if>" 
                                                    <c:if test="${agent.agentId !='' && agent.agentId != null }">readonly="true"</c:if>
                                                    /> 
                                                </div>
                                            </div>
                                            <div class="col-md-12">
                                                <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                                                    <label for="agentPhone">Office Phone Number</label>

                                                    <input type="tel" class="form-control" id="agentPhone" name="agentPhone" placeholder="Phone Number" readonly
                                                           value=<c:if test="${fn:length(errors) > 0 }">"${param.agentPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.phone}"</c:if> "/>

                                                </div>
                                            </div>
                                        </div>

                                    </fieldset>
                                </div>

                                <div class="col-md-4">
                                    <div class="row">
                                        <div class="row text-center" id="imgholder">
                                            <div class="col-md-6 col-md-offset-3  col-xs-4 col-xs-offset-4 ">
                                                <div class="form-group text-center">
                                                    <c:if test="${agent.photoPath != null}">
                                                        <img src="${agentImageAccessDir}/${agent.photoPath}" class="img-responsive text-center" style="max-height:220px !important;" />
                                                    </c:if>
                                                </div>
                                            </div>

                                        </div>

                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="box box-default">

                            <div class="row" style="padding-top:10px;">
                                <div class="col-md-12" >
                                    <fieldset>
                                        <legend style="padding-left:10px !important;">Office Address</legend>
                                        <div class="row">

                                            <div class="col-md-12">
                                                <div class="row">
                                                    <div class="col-md-4">

                                                        <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                                                            <label for="agentStreet">Address</label>
                                                            <input type="text" class="form-control" id="agentStreet" name="agentStreet" placeholder="Street" readonly 
                                                                   value=<c:if test="${fn:length(errors) > 0 }">"${param.agentStreet}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.street}"</c:if> "
                                                            />
                                                        </div>
                                                    </div>

                                                    <div class="col-md-4">
                                                        <div class="form-group" style="padding-left:10px !important;">
                                                            <label for="agentCity">City</label>

                                                            <input type="text" class="form-control" id="agentCity" name="agentCity" placeholder="City"  readonly
                                                                   value=<c:if test="${fn:length(errors) > 0 }">"${param.agentCity}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.city}"</c:if> "
                                                            /></div>
                                                    </div>
                                                    <c:if test="${fn:length(errors) > 0 }"><c:set var="state" value="${param.agentState}" scope="session" /></c:if>
                                                    <c:if test="${fn:length(errors) <= 0 }"><c:set var="state" value="${agent.state}" scope="session" /></c:if>

                                                    <div class="col-md-4">
                                                        <div class="form-group" style="margin-right:24px !important;padding-left:20px !important;"  />
                                                        <label for="agentState">State</label>
                                                        <input type="text" class="form-control" id="agentState" name="agentState" placeholder="State"
                                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.agentState}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.state}"</c:if> />
                                                            <% //                             <select class="form-control" id="agentState" name="agentState" disabled >
                                                                //                                 <option value="">--choose--</option>
                                                                //                                 
                                                                //                                 <option value="ABUJA FCT" <c:if test="${state == 'ABUJA FCT'}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                                                //                                 <option value="Abia" <c:if test="${state == 'Abia'}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                                                //                                 <option value="Adamawa" <c:if test="${state == 'Adamawa'}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                                                //                                 <option value="Akwa Ibom" <c:if test="${state == 'Akwa Ibom'}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                                                //                                 <option value="Anambra" <c:if test="${state == 'Anambra'}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                                                //                                 <option value="Bauchi" <c:if test="${state == 'Bauchi'}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                                                //                                 <option value="Bayelsa" <c:if test="${state == 'Bayelsa'}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                                                //                                 <option value="Benue" <c:if test="${state == 'Benue'}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                                                //                                 <option value="Borno" <c:if test="${state == 'Borno'}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                                                //                                 <option value="Cross River" <c:if test="${state == 'Cross River'}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                                                //                                 <option value="Delta" <c:if test="${state == 'Delta'}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                                                //                                 <option value="Ebonyi" <c:if test="${state == 'Ebonyi'}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                                                //                                 <option value="Edo" <c:if test="${state == 'Edo'}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                                                //                                 <option value="Ekiti" <c:if test="${state == 'Ekiti'}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                                                //                                 <option value="Enugu" <c:if test="${state == 'Enugu'}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                                                //                                 <option value="Gombe" <c:if test="${state == 'Gombe'}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                                                //                                 <option value="Imo" <c:if test="${state == 'Imo'}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                                                //                                 <option value="Jigawa" <c:if test="${state == 'Jigawa'}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                                                //                                 <option value="Kaduna" <c:if test="${state == 'Kaduna'}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                                                //                                 <option value="Kano" <c:if test="${state == 'Kano'}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                                                //                                 <option value="Katsina" <c:if test="${state == 'Katsina'}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                                                //                                 <option value="Kebbi" <c:if test="${state == 'Kebbi'}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                                                //                                 <option value="Kogi" <c:if test="${state == 'Kogi'}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                                                //                                 <option value="Kwara" <c:if test="${state == 'Kwara'}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                                                //                                 <option value="Lagos" <c:if test="${state == 'Lagos'}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                                                //                                 <option value="Nassarawa" <c:if test="${state == 'Nassarawa'}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                                                //                                 <option value="Niger" <c:if test="${state == 'Niger'}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                                                //                                 <option value="Ogun" <c:if test="${state == 'Ogun'}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                                                //                                 <option value="Ondo" <c:if test="${state == 'Ondo'}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                                                //                                 <option value="Osun" <c:if test="${state == 'Osun'}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                                                //                                 <option value="Oyo" <c:if test="${state == 'Oyo'}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                                                //                                 <option value="Plateau" <c:if test="${state == 'Plateau'}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                                                //                                 <option value="Rivers" <c:if test="${state == 'Rivers'}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                                                //                                 <option value="Sokoto" <c:if test="${state == 'Sokoto'}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                                                //                                 <option value="Taraba" <c:if test="${state == 'Taraba'}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                                                //                                 <option value="Yobe" <c:if test="${state == 'Yobe'}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                                                //                                 <option value="Zamfara" <c:if test="${state == 'Zamfara'}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                                                            //                           </select> %>
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

                                        <div class="row">
                                            <div class="col-md-4">
                                                <div class="form-group" style="padding-left:10px !important;">
                                                    <label for="agentBankName">Bank Name</label>
                                                    <input type="text" class="form-control" id="agentBankName" name="agentBankName" placeholder="Enter Bank Name"  readonly
                                                           value=<c:if test="${fn:length(errors) > 0 }">"${param.agentBankName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.bank.bankName}"</c:if> "     />
                                                </div>
                                            </div>
                                            <div class="col-md-4">
                                                <div class="form-group" style="padding-left:10px !important;">
                                                    <label for="agentBankAccountName">Bank Account Name</label>

                                                    <input type="text" class="form-control" id="agentBankAccountName" name="agentBankAccountName" placeholder="Enter Bank Account Name"  readonly
                                                           value=<c:if test="${fn:length(errors) > 0 }">"${param.agentBankAccountName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.bankAcctName}"</c:if> "/>

                                                </div>
                                            </div>

                                            <div class="col-md-4" >
                                                <div class="row" >
                                                    <div class="form-group" style="padding-left:10px !important;padding-right:25px !important;">
                                                        <label for="agentBankAccountNumber">Bank Account Number</label>
                                                        <input type="text" class="form-control"  id="agentBankAccountNumber"  name="agentBankAccountNumber" placeholder="Enter Bank Account Number" readonly
                                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.agentBankAccountNumber}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.bankAcctNumber}"</c:if> "/><br/>
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
                                    <legend style="padding-left:20px !important;">Documents</legend>

                                    <div class="col-md-12">
                                        <div id="documents-accordion" class="panel-group"  role="tablist" aria-multiselectable="true" >
                                            <table class="table table-bordered table-striped table-hover">

                                                <thead>
                                                    <tr>
                                                        <th ><div >
                                                                <h4>sn</h4>
                                                            </div> </th>
                                                        <th ><div >
                                                                <h4>title</h4>
                                                            </div> </th>
                                                        <th >
                                                            <div >
                                                                <h4>file</h4>
                                                            </div>
                                                        </th>
                                                    </tr>

                                                </thead>
                                                <tbody>

                                                <c:forEach items="${documents}" var="document">
                                                    <c:if test="${!document.docTypeId.title.contains('Director')}">
                                                        <tr>
                                                            <td >${document.docTypeId.weight}</td>
                                                            <td >${document.docTypeId.title}</td>
                                                            <td ><a href="${documentDir}${document.path}" target="_blank" ><img src="${documentDir}${document.path}" class="img img-responsive img-thumbnail" style="max-width: 50px;max-height: 50px; overflow-y: auto"/></a>
                                                            </td>
                                                        </tr>
                                                    </c:if>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                                            
                                    <legend style="padding-left:20px !important;">Directors</legend>  
                                    <div class="col-md-12">
                                        <div id="documents-accordion" class="panel-group"  role="tablist" aria-multiselectable="true" >
                                    <table class="table table-bordered table-striped table-hover">

                                                <thead>
                                                    <tr>
                                                       
                                                        <th >
                                                                <h4>Director Name</h4>
                                                        </th>
                                                        <th >
                                                            <h4>Director PassPort</h4>
                                                        </th>
                                                        <th >
                                                            <h4>Director ID Card</h4>
                                                        </th>
                                                    </tr>

                                                </thead>
                                                <tbody>
                                                    <c:forEach items="${Directors}" var="director">
                                                        
                                                         
                                                        <c:forEach items="${documents}" var="document">
                                                    <c:if test="${document.docTypeId.title.equals('Director Passport') && (document.id == director.passport) }">
                                                       <c:set var="passport" value ="${document}"/>
                                                    </c:if>
                                                    <c:if test="${document.docTypeId.title.equals('Director IDCard')&&(document.id == director.iDCard)}">
                                                       <c:set var="IDCard" value ="${document}"/>
                                                    </c:if>
                                                </c:forEach>
                                                     <tr>
                                                            <td >${director.name}</td>
                                                            <td ><a href="${documentDir}${passport.path}" target="_blank"><img src="${documentDir}${passport.path}" class="img img-responsive img-thumbnail" style="max-width: 50px;max-height: 50px; overflow-y: auto"/></a>
                                                            <td ><a href="${documentDir}${IDCard.path}" target="_blank"><img src="${documentDir}${IDCard.path}" class="img img-responsive img-thumbnail" style="max-width: 50px;max-height: 50px; overflow-y: auto"/></a>
                                                            </td>
                                                        </tr>
                                                </c:forEach>
                                                </tbody>
                                    </table>
                                        </div>
                                    </div>
                                </fieldset>
                            </div>
                        </div>
                    </div>

                </form>
            </div><!-- /.box -->
        </div><!-- /.box -->
    </div>

</div>   <!-- /.row -->


<!--MODAL-->
<div class="modal fade" id="activateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close" id="cancel2"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">NEOFORCE</h4>
            </div>
            <div class="modal-body">
                You are about to activate this person as an agent. <br/><br/>

                Please be sure that you have verified their information and are satisfied as this person will become a representative of your organisation. If you are sure, please click 'Yes' or 'Cancel' if not sure.
                <br/><br/>
                Please note this record will be moved to the general agents list immediately after you click 'Yes'.
                <br/><br/>
                Are you sure you want to proceed?

            </div>
            <div class="modal-footer">
                <button type="button" id="cancel" class="btn btn-default pull-left" data-dismiss="modal">Cancel</button>
                <button id="ok" type="button" onclick="" class="btn btn-primary">Yes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->