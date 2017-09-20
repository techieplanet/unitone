<div class="row margintop10">

                                <div class="col-md-5 col-xs-offset-2">
                                    <p>
                                            <c:if test="${sessionScope.user.getSystemUserTypeId() == 2}">
                                                        <!--<div class="col-md-3">-->
                                                            <a href="#" class="pull-right bold" onclick="agentProfile.showPasswordModal(event)" style="text-decoration: none; border-bottom: 1px dotted blue;">Change password</a>
                                                        <!--</div>-->
                                                    </c:if>
                                        </p>
                                    <table class="table table-striped table-profile">
                                        
                                        <thead>
                                            <tr class="highlight">
                                                <c:if test="${!agent.isCorporate()}">
                                                <th class="text-right"><strong>Full Name</strong></th>
                                                </c:if>
                                        <c:if test="${agent.isCorporate()}">
                                             <th class="text-right"><strong>Company Name</strong></th>
                                        </c:if>
                                                <th>
                                                    ${agent.getFullName()}
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:if test="${agent.isCorporate()}">
                                            <tr class="">
                                                <td class="field"><strong>RC Number</strong></td>
                                                <td>${agent.getRCNumber()}</td>
                                            </tr>
                                            </c:if>
                                            <tr class="">
                                                <td class="field"><strong>Email</strong></td>
                                                <td><i class="fa fa-envelope-o"></i> ${agent.getEmail()} </td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Mobile</strong></td>
                                                <td><i class="fa fa-mobile-phone"></i> ${agent.getPhone()}</td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Address</strong></td>
                                                <td>${agent.getStreet()}</td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>City</strong></td>
                                                <td>${agent.getCity()}</td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>State</strong></td>
                                                <td>${agent.getState()}</td>
                                            </tr>
                                            <br/>
                                            <c:if test="${!agent.isCorporate()}">
                                            <tr class="borderbottom">
                                                <td class="field" colspan="2" style="text-align: left">
                                                    <h3>Next of Kin Information</h3>
                                                </td>
                                            </tr>
                                            <tr class="divider">
                                                <td colspan="2"></td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Name</strong></td>
                                                <td>${agent.getKinName()}</td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Relationship</strong></td>
                                                <td>${agent.getKinRelationship()}</td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Mobile</strong></td>
                                                <td><i class="fa fa-mobile-phone"></i> ${agent.getKinPhone()}</td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Address</strong></td>
                                                <td>${agent.getKinAddress()}</td>
                                            </tr>
                                            </c:if>
                                            <tr class="borderbottom">
                                                <td class="field" colspan="2" style="text-align: left">
                                                    <h3>Bank Information</h3>
                                                </td>
                                            </tr>
                                            <tr class="divider">
                                                <td colspan="2"></td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Bank Name</strong></td>
                                                <td>${agent.getBank().getBankName()}</td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Account name</strong></td>
                                                <td>${agent.getBankAcctName()}</td>
                                            </tr>
                                            <tr>
                                                <td class="field"><strong>Account Number</strong></td>
                                                <td>${agent.getBankAcctNumber()}</td>
                                            </tr>
                                        </tbody>
                                    </table>

                                </div>
                                            
                                <div class="col-md-2 col-md-offset-1 margintop40">
                                    <!--<h4>Agent Picture</h4>-->
                                    <!--<img src="${agentImageAccessDir}/${agent.photoPath}" class="img img-responsive img-thumbnail" />-->
                                </div>

                            </div>