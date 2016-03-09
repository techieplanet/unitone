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
                <form role="form" name="agentRegistration" method="POST" action="Agent" enctype="multipart/form-data" id="agentForm">
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
                    <c:if test="${success}">
              <div class="row">
                    <div class="col-md-12 ">
                        <p class="bg-success padding10" style="width:90%">
                          <i class="fa fa-check"></i>Saved Successfully
                          <span class="pull-right">
                              <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Agent"><i class="fa fa-angle-double-left"></i> Back to list</a>
                          </span>
                        </p>
                    </div>
                </div>
          </c:if>
                           <div class="box-footer " style="margin-right:10px !important;margin-top:-10px !important;margin-bottom:-8px !important; background-color:transparent;height:50px !important;">
                              
                           <input type="submit" class="btn btn-primary pull-right" name="agentCreate" value="Save" id="agentCreate"/>
                          
                           <a class="btn btn-primary" href="Agent?action=new" role="button"><i class="fa fa-plus"></i>&nbsp;&nbsp;&nbsp;&nbsp;Add New Agent</a>
                            <c:if test="${agent.agentId !='' && agent.agentId!=null}">
                                <span id="row${agent.agentId}">
                                <input id="switch-state" type="checkbox" onChange="checkActivateSwitch('${pageContext.request.contextPath}', 'Agent',${agent.agentId});"  class="pull-right" data-switch-get="state" data-size="mini" data-on-text="Deactivate" data-off-text="Activate" data-on-color="danger" data-off-color="success" data-label-text="Status" class="pull-right"  <c:if test="${agent.active!='' && agent.active!=null && agent.active=='1'}">checked </c:if>  />
                                </span>
                               </c:if> 
                  </div>
                    
            
              
                               
                                  
                    <div class="box box-default">
                    <div class="row" style="padding-top:10px;">
                        <div class="col-md-4">
                      <fieldset>
                        <legend style="padding-left:10px !important;">Personal Information</legend>
                          <div class="row">
                              <div class="col-md-12">
                                  <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentFirstname">First Name</label>
                                      <input type="text"  name="agentFirstname" class="form-control" id="agentFirstname" 
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentFirstname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.firstname}"</c:if>  
                                      placeholder="First Name" >
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentMiddlename">Middle Name</label>
                                      <input type="text" class="form-control" id="agentMiddlename" name="agentMiddlename" placeholder="Middle Name" 
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentMiddlename}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.middlename}"</c:if> ">
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentLastname">Last Name</label>
                                      <input type="text" class="form-control" name="agentLastname" id="agentLastname" placeholder="Last Name" 
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentLastname}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.lastname}"</c:if> ">
                                    </div>
                                </div>
                            </div>
                        </fieldset>
                        </div>
                        
                        <div class="col-md-4">
                       
                            <fieldset>
                                <legend style="padding-right:10px !important;">Login Information</legend>
                          <div class="row">
                              <div class="col-md-12">
                                  <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="agentEmail">Email</label>
                                      <input type="email" class="form-control" id="agentEmail" name="agentEmail" placeholder="Email"  
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentEmail}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.email}"</c:if>" 
                                      <c:if test="${agent.agentId !='' && agent.agentId != null }">readonly="true"</c:if>
                                      /> 
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="agentPassword">Password</label>
                                      <input type="password" class="form-control" id="agentPassword" name="agentPassword" placeholder="Password"  />
                                    </div>
                                </div>
                            </div>
                            <div class="row">
                              <div class="col-md-12">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                      <label for="agentConfirmPassword">Confirm Password</label>
                                      <input type="password" class="form-control" id="agentConfirmPassword" name="agentConfirmPassword" placeholder="Confirm Password"  />
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
                              <img <c:if test="${agent.photoPath != null}"> src="${pageContext.request.contextPath}/images/uploads/agents/${agent.photoPath}" </c:if>
                               <c:if test="${agent.photoPath == null}"> src="${pageContext.request.contextPath}/images/img/avatar.png"
                    </c:if>
                                class=" img-responsive text-center" style="max-height:220px !important;"/>
                            </div>
                        </div>
                        <div class="col-md-6 col-md-offset-3  col-xs-4 col-xs-offset-4 ">
                            <div class="form-group">
                              <div class="btn-group btn-group-xs">
                                  <div class="btn btn-primary btn-file">
                      Change <span title="Change Profile Picture" class="glyphicon glyphicon-edit"></span> 
                      <input type="file" name="agentPhoto" accept="image/gif, image/jpeg, image/png" id="agentPhoto"  
                             />
                    </div>
                    <input type="hidden" name="agentPhotoHidden" 
                           <c:if test="${agentPhotoHidden == null && agent.photoPath==null}"> value=""
                    </c:if><c:if test="${agentPhotoHidden != null}"> value="${agentPhotoHidden}"
                    </c:if> <c:if test="${agent.photoPath != null}"> value="${agent.photoPath}"
                    </c:if> />
                    
                                  
                              </div>
                                
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
                            <legend style="padding-left:10px !important;">Contact Information</legend>
                    <div class="row">
                        
                        <div class="col-md-12">
                    <div class="row">
                        <div class="col-md-4">
                            
                            <div class="form-group" style="margin-left:14px !important;padding-left:10px !important;">
                            <label for="agentStreet">Street</label>
                                <input type="text" class="form-control" id="agentStreet" name="agentStreet" placeholder="Street"  
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.agentStreet}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.street}"</c:if> "
                                       />
                            </div>
                        </div>
                        
                        <div class="col-md-4">
                            <div class="form-group" style="padding-left:10px !important;">
                            <label for="agentCity">City</label>
                              
                                <input type="text" class="form-control" id="agentCity" name="agentCity" placeholder="City"  
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.agentCity}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.city}"</c:if> "
                                       />
                                  
                                
                            </div>
                        </div>
                        <c:if test="${fn:length(errors) > 0 }"><c:set var="state" value="${param.agentState}" scope="session" /></c:if>
                        <c:if test="${fn:length(errors) <= 0 }"><c:set var="state" value="${agent.state}" scope="session" /></c:if>
                      
                        <div class="col-md-4">
                            <div class="form-group" style="margin-right:24px !important;padding-left:20px !important;"  />
                              <label for="agentState">State</label>
                              <select class="form-control" id="agentState" name="agentState"  >
                                    <option value="">--choose--</option>
                                    
                                    <option value="ABUJA FCT" <c:if test="${state == "ABUJA FCT"}"> <jsp:text>selected</jsp:text> </c:if>>ABUJA FCT</option>
                                    <option value="Abia" <c:if test="${state == "Abia"}"> <jsp:text>selected</jsp:text> </c:if>>ABIA</option>
                                    <option value="Adamawa" <c:if test="${state == "Adamawa"}"> <jsp:text>selected</jsp:text> </c:if>>ADAMAWA</option>
                                    <option value="Akwa Ibom" <c:if test="${state == "Akwa Ibom"}"> <jsp:text>selected</jsp:text> </c:if>>AKWA IBOM</option>
                                    <option value="Anambra" <c:if test="${state == "Anambra"}"> <jsp:text>selected</jsp:text> </c:if>>ANAMBRA</option>
                                    <option value="Bauchi" <c:if test="${state == "Bauchi"}"> <jsp:text>selected</jsp:text> </c:if>>BAUCHI</option>
                                    <option value="Bayelsa" <c:if test="${state == "Bayelsa"}"> <jsp:text>selected</jsp:text> </c:if>>BAYELSA</option>
                                    <option value="Benue" <c:if test="${state == "Benue"}"> <jsp:text>selected</jsp:text> </c:if>>BENUE</option>
                                    <option value="Borno" <c:if test="${state == "Borno"}"> <jsp:text>selected</jsp:text> </c:if>>BORNO</option>
                                    <option value="Cross River" <c:if test="${state == "Cross River"}"> <jsp:text>selected</jsp:text> </c:if>>CROSS RIVER</option>
                                    <option value="Delta" <c:if test="${state == "Delta"}"> <jsp:text>selected</jsp:text> </c:if>>DELTA</option>
                                    <option value="Ebonyi" <c:if test="${state == "Ebonyi"}"> <jsp:text>selected</jsp:text> </c:if>>EBONYI</option>
                                    <option value="Edo" <c:if test="${state == "Edo"}"> <jsp:text>selected</jsp:text> </c:if>>EDO</option>
                                    <option value="Ekiti" <c:if test="${state == "Ekiti"}"> <jsp:text>selected</jsp:text> </c:if>>EKITI</option>
                                    <option value="Enugu" <c:if test="${state == "Enugu"}"> <jsp:text>selected</jsp:text> </c:if>>ENUGU</option>
                                    <option value="Gombe" <c:if test="${state == "Gombe"}"> <jsp:text>selected</jsp:text> </c:if>>GOMBE</option>
                                    <option value="Imo" <c:if test="${state == "Imo"}"> <jsp:text>selected</jsp:text> </c:if>>IMO</option>
                                    <option value="Jigawa" <c:if test="${state == "Jigawa"}"> <jsp:text>selected</jsp:text> </c:if>>JIGAWA</option>
                                    <option value="Kaduna" <c:if test="${state == "Kaduna"}"> <jsp:text>selected</jsp:text> </c:if>>KADUNA</option>
                                    <option value="Kano" <c:if test="${state == "Kano"}"> <jsp:text>selected</jsp:text> </c:if>>KANO</option>
                                    <option value="Katsina" <c:if test="${state == "Katsina"}"> <jsp:text>selected</jsp:text> </c:if>>KATSINA</option>
                                    <option value="Kebbi" <c:if test="${state == "Kebbi"}"> <jsp:text>selected</jsp:text> </c:if>>KEBBI</option>
                                    <option value="Kogi" <c:if test="${state == "Kogi"}"> <jsp:text>selected</jsp:text> </c:if>>KOGI</option>
                                    <option value="Kwara" <c:if test="${state == "Kwara"}"> <jsp:text>selected</jsp:text> </c:if>>KWARA</option>
                                    <option value="Lagos" <c:if test="${state == "Lagos"}"> <jsp:text>selected</jsp:text> </c:if>>LAGOS</option>
                                    <option value="Nassarawa" <c:if test="${state == "Nassarawa"}"> <jsp:text>selected</jsp:text> </c:if>>NASSARAWA</option>
                                    <option value="Niger" <c:if test="${state == "Niger"}"> <jsp:text>selected</jsp:text> </c:if>>NIGER</option>
                                    <option value="Ogun" <c:if test="${state == "Ogun"}"> <jsp:text>selected</jsp:text> </c:if>>OGUN</option>
                                    <option value="Ondo" <c:if test="${state == "Ondo"}"> <jsp:text>selected</jsp:text> </c:if>>ONDO</option>
                                    <option value="Osun" <c:if test="${state == "Osun"}"> <jsp:text>selected</jsp:text> </c:if>>OSUN</option>
                                    <option value="Oyo" <c:if test="${state == "Oyo"}"> <jsp:text>selected</jsp:text> </c:if>>OYO</option>
                                    <option value="Plateau" <c:if test="${state == "Plateau"}"> <jsp:text>selected</jsp:text> </c:if>>PLATEAU</option>
                                    <option value="Rivers" <c:if test="${state == "Rivers"}"> <jsp:text>selected</jsp:text> </c:if>>RIVERS</option>
                                    <option value="Sokoto" <c:if test="${state == "Sokoto"}"> <jsp:text>selected</jsp:text> </c:if>>SOKOTO</option>
                                    <option value="Taraba" <c:if test="${state == "Taraba"}"> <jsp:text>selected</jsp:text> </c:if>>TARABA</option>
                                    <option value="Yobe" <c:if test="${state == "Yobe"}"> <jsp:text>selected</jsp:text> </c:if>>YOBE</option>
                                    <option value="Zamfara" <c:if test="${state == "Zamfara"}"> <jsp:text>selected</jsp:text> </c:if>>ZAMFARA</option>
                              </select>
                            </div>
                        </div>
                        
                        
                        
                    </div>
                        </div>
                        <div class="col-md-12">
                            <div class="col-md-6">
                            <div class="form-group" style="padding-left:10px !important;margin-right:10px !important;">
                            <label for="agentPhone">Phone Number</label>
                               
                                <input type="tel" class="form-control" id="agentPhone" name="agentPhone" placeholder="Phone Number" 
                                       value=<c:if test="${fn:length(errors) > 0 }">"${param.agentPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.phone}"</c:if> "/>
                                    
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
                                  <input type="text" class="form-control" id="agentBankName" name="agentBankName" placeholder="Enter Bank Name"  
                                    value=<c:if test="${fn:length(errors) > 0 }">"${param.agentBankName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.bankName}"</c:if> "     />
                                </div>
                            </div>
                            <div class="col-md-4">
                                <div class="form-group" style="padding-left:10px !important;">
                                <label for="agentBankAccountName">Bank Account Name</label>
                                   
                                    <input type="text" class="form-control" id="agentBankAccountName" name="agentBankAccountName" placeholder="Enter Bank Account Name"  
                                           value=<c:if test="${fn:length(errors) > 0 }">"${param.agentBankAccountName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.bankAcctName}"</c:if> "/>
                                       
                                </div>
                            </div>
                            
                             <div class="col-md-4" >
                                <div class="row" >
                        <div class="form-group" style="padding-left:10px !important;padding-right:25px !important;">
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
                         </div>
                    
                    <div class="box box-default">
                    <div class="row" style="padding-top:10px;">
                    <div class="col-md-12">
                                <fieldset>
                            <legend style="padding-left:20px !important;">Next of Kin</legend>
                            <div class="col-md-12">
                            
                            <div class="row">
                              <div class="col-md-6">
                                    <div class="form-group" style="padding-left:10px !important;">
                                      <label for="agentKinNames">Next of Kin - Name</label>
                                      <input type="text" class="form-control" id="agentKinNames" name="agentKinName" placeholder="Enter Kin Name"  
                                             value=<c:if test="${fn:length(errors) > 0 }">"${param.agentKinName}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.kinName}"</c:if> "/>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group" style="padding-right:10px !important;padding-left:10px !important;">
                                    <label for="agentKinPhone">Next of Kin - Phone Number</label>
                                       
                                        <input type="tel" class="form-control" id="agentKinPhone" name="agentKinPhone" placeholder="Enter Kin Phone Number"  
                                               value=<c:if test="${fn:length(errors) > 0 }">"${param.agentKinPhone}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.kinPhone}"</c:if> "/>
                                            
                                    </div>
                                </div>
                            </div>
                            </div>
                            <div class="col-md-12">
                                <div  class="row">
                             <div class="col-md-4" >
                                    <div class="row" >
                            <div class="form-group" style="padding-left:25px !important;padding-right:20px !important">
                              <label for="agentKinPhoto" style="">Next of Kin - Picture</label>
                              <c:if test="${agent.agentId != ""}"> 
                               <img <c:if test="${agent.kinPhotoPath != null}"> src="${pageContext.request.contextPath}/images/uploads/agents/${agent.kinPhotoPath}" </c:if>
                               
                                class="img-responsive text-center" width="50.33333333%"/>
                                </c:if>
                                    <input type="file" class="form-control" id="agentKinPhoto" name="agentKinPhoto" accept="image/gif, image/jpeg, image/png" 
                                         style="max-height:220px !important;"
                                           />
                                          
                                             <input type="hidden" name="agentKinPhotoHidden" 
                           <c:if test="${agentKinPhotoHidden == null && agent.kinPhotoPath ==null}"> value=""
                    </c:if><c:if test="${agentKinPhotoHidden != null}"> value="${agentKinPhotoHidden}"
                    </c:if> <c:if test="${agent.kinPhotoPath != null}"> value="${agent.kinPhotoPath}"
                    </c:if> />
                            </div>
                                </div>
                            </div>
                                    <div class="col-md-2" style="padding-right:50% !important;"></div>
                                <div class="col-md-6 " >
                                    <div class="row" >
                            <div class="form-group" style="padding-left:25px !important;">
                              <label for="agentKinAddress" style="">Next of Kin - Address</label>
                               <input type="text" class="form-control" id="agentKinAddress" name="agentKinAddress" placeholder="Enter Kin Address" style="  width:100%;"
                                      
                                      value=<c:if test="${fn:length(errors) > 0 }">"${param.agentKinAddress}"</c:if><c:if test="${fn:length(errors) <= 0 }">"${agent.kinAddress}"</c:if> "/><br/>
                            </div>
                                </div>
                            </div>
                                </div>
                            </div>
                                </fieldset>
                    </div>
                            <c:if test="${agent.agentId==null || agent.agentId==''}">
                            <div class="col-md-12" style="text-align:center !important;">
                                Do you agree to the terms of the <a href="#" onclick="$('#agreementStatusModal').modal();">agreement document?</a>
                            <br/>    <input type="radio" class="" name="agreement_document" id="agree" value="agree"  onclick="agreementStatusChecked(this)" <c:if test="${agent.agentId!=null && agent.agentId!=''}">checked</c:if>/> I agree  &nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="agreement_document" id="decline"  onclick="agreementStatusChecked(this)" value="decline"/>I disagree
                            <br/><br/><br/>
                            </c:if>
                            <!--
</div>-->
                         </div>
                  </div><!-- /.box-body -->
                        <input type="hidden" name="agent_id" id="agent_id" value="${agent.agentId}">
                        <input type="hidden" name="id" id="id" value="${agent.agentId}">
                  <div class="box-footer" style="margin-left:10px !important;margin-right:10px !important;margin-top:-20px !important; background-color:transparent;">
                      <input type="submit" class="btn btn-primary" name="agentCreate" value="Save" id="agentCreate"/>
                  </div>
                 
                </div>
                 </form>
              </div><!-- /.box -->
              </div><!-- /.box -->
            </div>
          
          </div>   <!-- /.row -->
          
          
             <div class="modal fade" id="agreementStatusModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
         <div class="vertical-alignment-helper">
          <div class="modal-dialog vertical-align-center">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">NEOFORCE </h4>
            </div>
              
            <div class="modal-body">
              
              <h5>The standard Lorem Ipsum passage, used since the 1500s</h5>
              <p>
"Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."

Section 1.10.32 of "de Finibus Bonorum et Malorum", written by Cicero in 45 BC



"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
              </p>
            </div>
            <div class="modal-footer">
              <button type="button" class="btn btn-default pull-left" data-dismiss="modal"> Cancel</button>
              <button  type="button"  id="agreement" class="btn btn-success" onclick="modal_agree()"><i class="fa fa-check"></i> I agree</button>
            </div>
          </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->