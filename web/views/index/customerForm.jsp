<%-- 
    Document   : customerForm
    Created on : Oct 10, 2017, 11:05:44 AM
    Author     : SWEDGE
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<!--
    Process Step
-->
<div class="stepwizard">
    <div class="stepwizard-row">
        <div class="stepwizard-step">
            <button type="button" id="process-step-1" class="btn btn-primary btn-circle" onclick="return showCustomerReg()">1</button>
            <p>Customer Registration</p>
        </div>
        <div class="stepwizard-step">
            <button type="button" id="process-step-2" class="btn btn-default btn-circle" disabled="disabled" onclick="proceed()">2</button>
            <p>Customer Documents</p>
        </div>
         <div class="stepwizard-step">
            <button type="button" id="process-step-3" class="btn btn-default btn-circle" disabled="disabled" onclick="return showOrderProduct()">3</button>
            <p>Order/Checkout</p>
        </div>
    </div>
</div>
<input type="hidden" name="agent_id" id="agent_id" value='1'/>
<!-- Step One Customer Details -->
<div class="row" id="step1">
    <div class="col-md-12">
        <!-- general form elements -->

        <!-- form start -->
        <div class="box box-primary" id="step1_box">
            <div class="box-header with-border">
                <h3 class="box-title">Customer Registration Form 
                </h3>
            </div><!-- /.box-header -->
            <!-- form start -->
            <div style="background:#ecf0f5 !important;">

                <div class="box-body">
                    <c:if test="${isError}">
                        <div class="row">
                            <div class="col-md-12 ">
                                <p class="bg-danger padding10" style="width:100%; margin:5px auto !important">
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
                                <p class="bg-success padding10" style="width:95%">
                                    <i class="fa fa-check"></i>Saved Successfully
                                    <span class="pull-right">

                                        <a class="btn btn-primary btn-sm margintop5negative" role="button" href="${pageContext.request.contextPath}/Customer">Back to list</a>

                                    </span>
                                </p>
                            </div>
                        </div>
                    </c:if>   

                    <div class="box box-default">
                        <div class="row" style="padding-top:10px;margin: 10px">
                            <div class="col-md-12">
                                <fieldset>
                                    <legend >Personal Information</legend>

                                    <div class="col-md-3">
                                        <div class="form-group" >
                                            <label for="customerTitle">Title*</label>
                                            <c:set var="tTitle" value="${isAdminEdit ? customer.title : (isError ? param.customerTitle : '')}"/>
                                            <select name="customerTitle"  class="form-control" id="customerTitle" >
                                                <option value="select" >--Select--</option>
                                                <option value="Mr" <c:if test="${tTitle == 'Mr'}">selected</c:if>>Mr.</option>
                                                <option value="Mrs" <c:if test="${tTitle == 'Mrs'}">selected</c:if> >Mrs.</option>
                                                <option value="Ms" <c:if test="${tTitle == 'Ms'}">selected</c:if>>Ms.</option>
                                                <option value="Chief" <c:if test="${tTitle == 'Chief'}">selected</c:if>>Chief</option>
                                                <option value="Dr" <c:if test="${tTitle == 'Dr'}">selected</c:if>>Dr.</option>
                                                <option value="Prof" <c:if test="${tTitle == 'Prof'}">selected</c:if>>Prof.</option>
                                                <option value="Alh" <c:if test="${tTitle == 'Alh'}">selected</c:if>>Alh.</option>
                                                </select>
                                            </div>
                                        </div>

                                        <div class="col-md-3">
                                            <div class="form-group" >
                                                <label for="customerFirstname">First Name*</label>
                                                <input type="text"  name="customerFirstname" class="form-control" id="customerFirstname" placeholder="First Name" 

                                                       value=<c:if test="${isError}">"${param.customerFirstname}"</c:if><c:if test="${!isError}">"${customer.firstname}"</c:if> />

                                            </div>
                                        </div>


                                        <div class="col-md-3">
                                            <div class="form-group" >
                                                <label for="customerMiddlename">Middle Name*</label>
                                                <input type="text" class="form-control" id="customerMiddlename" name="customerMiddlename" placeholder="Middle Name"

                                                           value=<c:if test="${isError}">"${param.customerMiddlename}"</c:if><c:if test="${!isError}">"${customer.middlename}"</c:if> />

                                            </div>
                                        </div>

                                        <div class="col-md-3">
                                            <div class="form-group" >
                                                <label for="customerLastname">Last Name*</label>
                                                <input type="text" class="form-control" name="customerLastname" id="customerLastname" placeholder="Last Name" 

                                                           value=<c:if test="${isError}">"${param.customerLastname}"</c:if><c:if test="${!isError}">"${customer.lastname}"</c:if>/>

                                            </div>
                                        </div>


                                        <div class="col-md-3">
                                            <div class="form-group" >
                                                <label for="customerGender" >Gender*</label><br/>
                                                <select  name="customerGender" id="customerGender" class=" form-control"
                                                         >
                                                <c:set var="tGender" value="${isError ? param.customerGender : ''}"/>
                                                <option value="select" >--Select--</option>
                                                <option value="male" <c:if test="${tGender=='male'}" >selected</c:if> >Male</Option> 
                                                <option value="female" <c:if test="${tGender =='female'}" >selected</c:if> >Female</Option> 
                                                </select>
                                            </div>
                                        </div>


                                        <div class="col-md-3">
                                            <div class="form-group" >
                                                <label for="customerMaritalStatus" >Marital Status*</label><br/>
                                                <select  name="customerMaritalStatus" id="customerMaritalStatus" class=" form-control"
                                                         >
                                                <c:set var="mStatus" value="${isError ? param.customerMaritalStatus : ''}"/>
                                                <option value="select">--Select--</option>
                                                <option value="Married" <c:if test="${mStatus == 'Married'}">selected</c:if>  >Married</Option> 
                                                <option value="Single" <c:if test="${mStatus == 'Single'}">selected</c:if>  >Single</Option> 
                                                <option value="Divorced" <c:if test="${mStatus == 'Divorced'}">selected</c:if>  >Divorced</Option> 
                                                </select>
                                            </div>
                                        </div>


                                        <div class="col-md-3">
                                            <div class="form-group" >
                                                <label for="customerDateOfBirth" >Date Of Birth*</label>

                                                <input type="text" name="customerDateOfBirth" id="customerDateOfBirth" placeholder="dd/mm/yy" class="text-black form-control" 

                                                       value=<c:if test="${isError}">"${param.customerDateOfBirth}"</c:if><c:if test="${!isError}">"${customer.dateOfBirth != null?dateFmt.format(customer.dateOfBirth):''}"</c:if> />

                                            </div>
                                        </div>

                                        <div class="col-md-3">
                                            <div class="form-group" >
                                                <label for="customerEmail">Email address*</label>
                                                <input type="email" class="form-control" id="customerEmail" name="customerEmail" placeholder="your@email.com" 

                                                           value=<c:if test="${isError}">"${param.customerEmail}"</c:if><c:if test="${!isError}">"${customer.email}"</c:if>  />
                                            </div>
                                        </div>


                                    </fieldset>
                                </div>

                                <div class="col-md-12">

                                    <fieldset>
                                        <legend >Work Information</legend>
                                        <div class="col-md-4">
                                            <div class="form-group" >
                                                <label for="customerOccupation">Occupation*</label>

                                                <input type="text" class="form-control" name="customerOccupation" id="customerOccupation" placeholder="Occupation" 

                                                           value=<c:if test="${isError}">"${param.customerOccupation}"</c:if><c:if test="${!isError}">"${customer.occupation}"</c:if> />

                                            </div>
                                        </div>
                                        <div class="col-md-4">
                                            <div class="form-group" >
                                                <label for="customerEmployer">Employer*</label>

                                                <input type="text" class="form-control" name="customerEmployer" id="customerEmployer" placeholder="Employer" 

                                                           value=<c:if test="${isError }">"${param.customerEmployer}"</c:if><c:if test="${!isError }">"${customer.employer}"</c:if> />

                                            </div>
                                        </div>

                                        <div class="col-md-4">
                                            <div class="form-group" >
                                                <label for="customerOfficePhone">Office Phone*</label>

                                                <input type="number" class="form-control" name="customerOfficePhone" id="customerOfficePhone" placeholder="Office Phone" 

                                                           value=<c:if test="${isError}">"${param.customerOfficePhone}"</c:if><c:if test="${!isError}">"${customer.officePhone}"</c:if> />

                                            </div>
                                        </div>
                                 <div class="col-md-12">
                                        <div class="form-group" >
                                            <legend >Employer Address</legend>

                                            <div class="col-md-12"> 
                                                <div class="col-md-3">
                                                    <label for ="customerEmployerStreet">Address*</label>
                                                    <input type="text" class="form-control" name="customerEmployerStreet" id="customerEmployerStreet" placeholder="Street" 

                                                           value=<c:if test="${isError}">"${param.customerEmployerStreet}"</c:if><c:if test="${!isError }">"${customer.employerStreet}"</c:if> />

                                                    </div>
                                                    <div class="col-md-3">
                                                        <label for ="customerEmployerCity">City*</label>
                                                        <input type="text" class="form-control" name="customerEmployerCity" id="customerEmployerCity" placeholder="City" 

                                                                   value=<c:if test="${isError}">"${param.customerEmployerCity}"</c:if><c:if test="${!isError}">"${customer.employerCity}"</c:if> />

                                                    </div>
                                                    <div class="col-md-3">
                                                        <label for ="customerEmployerState">State*</label>
                                                        <input type="text" class="form-control" name="customerEmployerState" id="customerEmployerState" placeholder="State" 

                                                                   value=<c:if test="${isError}">"${param.customerEmployerState}"</c:if><c:if test="${!isError}">"${customer.employerState}"</c:if> "/>

                                                    </div>
                                                    <div class="col-md-3">
                                                        <div class="form-group" >
                                                            <label for ="customerEmployerCountry">Country*</label>
                                                            <select class="form-control" name="customerEmployerCountry" id="customerEmployerCountry">
                                                                <option value="select">--Select--</option>
                                                            <c:set var="tcountry" value="${isError ? param.customerEmployerCountry : ''}"/>
                                                            <c:forEach items="${countries}" var="country">
                                                                <option value="${country.getName()}" <c:if test="${country.getName() == tcountry}">selected</c:if> >${country.getName()}</option>
                                                            </c:forEach>
                                                        </select>
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

                        <div class="row" style="padding-top:10px;margin: 10px">
                            <div class="col-md-12" >
                                <fieldset>
                                    <legend >Contact Information</legend>
                                    <div class="row">

                                        <div class="col-md-12">
                                            <div class="row">
                                                <div class="col-md-3">

                                                    <div class="form-group" >
                                                        <label for="customerStreet">Address*</label>
                                                        <input type="text" class="form-control" id="customerStreet" name="customerStreet" placeholder="Street"  

                                                               value=<c:if test="${isError}">"${param.customerStreet}"</c:if><c:if test="${!isError}">"${customer.street}"</c:if> />
                                                        </div>
                                                    </div>

                                                    <div class="col-md-3">
                                                        <div class="form-group" >
                                                            <label for="customerCity">City*</label>

                                                            <input type="text" class="form-control" id="customerCity" name="customerCity" placeholder="City"  

                                                                       value=<c:if test="${isError}">"${param.customerCity}"</c:if><c:if test="${!isError}">"${customer.city}"</c:if> />


                                                        </div>
                                                    </div>

                                                    <div class="col-md-3">
                                                        <div class="form-group" />
                                                        <label for="customerState">State*</label>
                                                        <input class="form-control" id="customerState" name="customerState"  placeholder="State"

                                                                   value=<c:if test="${isError}">"${param.customerState}"</c:if><c:if test="${!isError}">"${customer.state}"</c:if> />
                                                    </div>
                                                </div>

                                                <div class="col-md-3">
                                                    <div class="form-group" >
                                                        <label for="customerCountry">Country*</label>
                                                        <select class="form-control" name="customerCountry" id="customerCountry">
                                                            <option value="select">--Select--</option>
                                                        <c:set var="tcountry" value="${isError ? param.customerCountry : ''}"/>
                                                        <c:forEach items="${countries}" var="country">
                                                            <option value="${country.getName()}" <c:if test="${country.getName() == tcountry}">selected</c:if> >${country.getName()}</option>
                                                        </c:forEach>
                                                    </select>
                                                </div>
                                            </div>  
                                            <div class="col-md-4">
                                                <div class="form-group" >
                                                    <label for="customerPhone">Phone Number*</label>
                                                    <input type="number" class="form-control" id="customerPhone" name="customerPhone" placeholder="Phone Number" 

                                                           value=<c:if test="${isError}">"${param.customerPhone}"</c:if><c:if test="${!isError}">"${customer.phone}"</c:if> />

                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerOtherPhone">Other Phone</label>
                                                        <input type="number" class="form-control" id="customerOtherPhone" name="customerOtherPhone" placeholder="Other Phone" 

                                                                   value=<c:if test="${isError}">"${param.customerOtherPhone}"</c:if><c:if test="${!isError}">"${customer.otherPhone}"</c:if> />

                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerPostalAddress">Postal Address*</label>
                                                        <input type="text" class="form-control" id="customerPostalAddress" name="customerPostalAddress" placeholder="Postal Address" 

                                                                   value=<c:if test="${isError }">"${param.customerPostalAddress}"</c:if><c:if test="${!isError }">"${customer.postalAddress}"</c:if> />
                                                    </div>
                                                </div>   
                                            </div>           
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>

                        <div class="box box-default">
                            <div class="row" style="padding-top:10px;margin: 10px">
                                <div class="col-md-12">
                                    <fieldset>
                                        <legend >Next of Kin</legend>
                                        <div class="col-md-12">

                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerKinNames">Next of Kin Name*</label>
                                                        <input type="text" class="form-control" id="customerKinName" name="customerKinName" placeholder="Enter Kin Name"  

                                                                   value=<c:if test="${isError }">"${param.customerKinName}"</c:if><c:if test="${!isError }">"${customer.kinName}"</c:if> />

                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerKinRelationship">Relationship Of Next Of kin*</label>

                                                        <input type="text" class="form-control" id="customerKinRelationship" name="customerKinRelationship" placeholder="Relationship"  

                                                                   value=<c:if test="${isError }">"${param.customerKinRelationship}"</c:if><c:if test="${!isError }">"${customer.kinRelationship}"</c:if> />

                                                    </div>
                                                </div> 
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerKinEmail">Email Of Next Of kin</label>

                                                        <input type="text" class="form-control" id="customerKinEmail" name="customerKinEmail" placeholder="Email Of Next Of kin"  

                                                                   value=<c:if test="${isError }">"${param.customerKinEmail}"</c:if><c:if test="${!isError }">"${customer.kinEmail}"</c:if> />

                                                    </div>
                                                </div> 
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerKinPhone">Next of Kin Phone Number*</label>

                                                        <input type="number" class="form-control" id="customerKinPhone" name="customerKinPhone" placeholder="Enter Kin Phone Number" 

                                                                   value=<c:if test="${isError }">"${param.customerKinPhone}"</c:if><c:if test="${!isError }">"${customer.kinPhone}"</c:if> />


                                                    </div>
                                                </div>
                                                <div class="col-md-4 " >
                                                    <div class="row" >
                                                        <div class="form-group" >
                                                            <label for="customerKinAddress" >Next of Kin Address*</label>
                                                            <input type="text" class="form-control" id="customerKinAddress" name="customerKinAddress" placeholder="Enter Kin Address" 

                                                                       value=<c:if test="${isError }">"${param.customerKinAddress}"</c:if><c:if test="${!isError }">"${customer.kinAddress}"</c:if> />

                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div><!-- /.box-body -->

                        <div class="box box-default">
                            <div class="row" style="padding-top:10px;margin: 10px">
                                <div class="col-md-12">
                                    <fieldset>
                                        <legend >Bank Information</legend>
                                        <div class="col-md-12">

                                            <div class="row">
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerBanker">Banker*</label>

                                                        <input type="text" class="form-control" id="customerBanker" name="customerBanker" placeholder="Bank Name"  

                                                                   value=<c:if test="${isError }">"${param.customerBanker}"</c:if><c:if test="${!isError }">"${customer.banker}"</c:if> />

                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerAccountName">Account Name*</label>
                                                        <input type="text" class="form-control" id="customerAccountName" name="customerAccountName" placeholder="Account Name"  

                                                                   value=<c:if test="${isError }">"${param.customerAccountName}"</c:if><c:if test="${!isError }">"${customer.accountName}"</c:if> />

                                                    </div>
                                                </div> 
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerAccountNumber">Account Number*</label>

                                                        <input type="number" class="form-control" id="customerAccountNumber" name="customerAccountNumber" placeholder="Account Number"  

                                                                   value=<c:if test="${isError }">"${param.customerAccountNumber}"</c:if><c:if test="${!isError }">"${customer.accountNumber}"</c:if> "/>

                                                    </div>
                                                </div> 

                                            </div>
                                        </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div><!-- /.box-body -->

                        <div class="col-md-12">

                            <div class="col-md-12">
                                <a class="btn btn-primary" href="#" onclick="return validateCustomer(1)" role="button">Proceed <i class="fa fa-long-arrow-right"></i></a>
                            </div>
                        </div>

                    </div>


                </div><!-- /.col-md-4 -->
            </div><!-- /.row -->
        </div> 
    </div><!-- /.box -->

    <!-- Step two . File Upload -->    
    <div class="row" id="step2" style="display:none">
        <div class="col-md-12">
            <!-- general form elements -->

            <!-- form start -->
            <div class="box box-primary" id="step2_box">
                <div class="box-header with-border">
                    <h3 class="box-title">Customer Document
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
                                                                <label for="customerPhoto">Customer Passport</label>
                                                                <input    class="form-control" type="file" name="customerPhoto" accept="image/gif, image/jpeg, image/png, image/bmp" id="customerPhoto" >

                                                            </div>
                                                        </div>
                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="customerKinPhoto">Customer Kin Photo</label>
                                                                <input    class="form-control" type="file" name="customerKinPhoto" accept="image/gif, image/jpeg, image/png, image/bmp" id="customerKinPhoto" >
                                                            </div>
                                                        </div>  
                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="customerPhotoID">Means Of ID (ID card,etc)</label>
                                                                <input    class="form-control" type="file" name="customerPhotoID" accept="image/gif, image/jpeg, image/png, image/bmp" id="customerPhotoID" >

                                                            </div>
                                                        </div>  

                                                        <div class="col-md-6">
                                                            <div class="form-group" >
                                                                <label for="customerBankStandingOrder">Bank Standing Order / Post Dated Cheques </label>
                                                                <input    class="form-control" type="file" name="customerBankStandingOrder" accept="image/gif, image/jpeg, image/png, image/bmp" id="customerBankStandingOrder" >
                                                            </div>    
                                                        </div>

                                                    </div>
                                                </div>

                                            </div>
                                    </fieldset>
                                </div>
                            </div>
                        </div>
                        <div class="col-md-12">
                            <a class="btn btn-primary" href="#" onclick="return validateCustomer(2)" role="button">Proceed <i class="fa fa-long-arrow-right"></i></a>
                        </div>
                    </div>     
                </div><!-- /.col-md-4 -->
            </div><!-- /.row -->
        </div> 
    </div><!-- /.box -->
        <input type="hidden" id="pageContext" value="${pageContext.request.contextPath}" />
        <input type="hidden" value="0" name="customer_id" id="customer_id"/>
<script>
    $(function () {
        $("#customerDateOfBirth").datepicker({changeMonth: true, changeYear: true, yearRange: "1930:2017", dateFormat: "dd/mm/yy"});
    });

    var formFieldStage1 = [       [ "agent_id", "select", "an Agent for Customer"]
            , ["customerTitle", "select", "Customer Title"]
            , ["customerFirstname", "", "Customer First Name"]
            , ["customerMiddlename", "", "Customer Middle Name"]
            , ["customerLastname", "", "Customer Last Name"]
            , ["customerGender", "select", "Customer Gender"]
            , ["customerMaritalStatus", "select", "Customer Marital Status"]
            , ["customerDateOfBirth", "", "Customer Date Of Birth"]
            , ["customerEmail", "", "Customer Email"]
            , ["customerOccupation", "", "Customer Occupation"]
            , ["customerEmployer", "", "Customer Employer"]
    ,["customerOfficePhone" , "" , "Customer Office Phone"]
    , ["customerEmployerStreet", "", "Customer Employer's Street"]
            , ["customerEmployerCity", "", "Customer Employer's City"]
            , ["customerEmployerState", "", "Employer's State"]
            , ["customerEmployerCountry", "select", "Customer Employer Country"]
            , ["customerStreet", "", "Customer's Address"]
            , ["customerCity", "", "Customer's City"]
            , ["customerState", "", "Customer's State"]
            , ["customerCountry", "select", "Customer's Country"]
            , ["customerPhone", "", "Customer's Phone Number"]
    , ["customerPostalAddress", "", "Customer's  Postal Address"]
    , ["customerKinName", "", "Customer's Next Of Kin Name"]
    , ["customerKinRelationship", "", "Customer's Next Of Kin Relationship"]
    , ["customerKinPhone", "", "Customer's Next Of Kin Phone Number"]
            , ["customerKinAddress", "", "Customer's  Next Of Kin Address"]
            , ["customerBanker", "", "Customer's Banker"]
            , ["customerAccountName", "", "Customer's Account Name"]
    , ["customerAccountNumber", "", "Customer's Account Number"]
    ];

    var formFieldStage2 = [["customerPhoto", "", " Customer's PassPort"]
                , ["customerKinPhoto", "", "Customer's Next Of Kin Passport"],
        ["customerPhotoID", "", "Customer's ID card /Driver Lincense etc"],
        ["customerBankStandingOrder", "", "Customer's Post-dated Cheques/Bank Standing Order"]];
    function   validateCustomer(stage) {

        //Validating Elements on the first stage of validation
        //Array object have the format of {Field_Name , default_Value , error_text}
        var errors = [];
        if (stage == 1)
        {

            $("#customerErrorModal .modal-body").html("");

            for (var i = 0; i < formFieldStage1.length; i++)
            {
                var temp = $("#" + formFieldStage1[i][0]).val();
                if (temp == formFieldStage1[i][1])
                {
                    $("#" + formFieldStage1[i][0]).css("border", "1px solid red");
                    if (formFieldStage1[i][1] == "select") //If The default value is selec i.e Used for drop down
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
                    $("#customerErrorModal .modal-body").append(errorText);
                }
                $("#customerErrorModal").modal();
                return;
            }


            var url = $("#pageContext").val();
            $.ajax({
                url: url + "/Customer?action=email_validation",
                method: 'GET',
                data: {email: $("#customerEmail").val(), type: 'xmlhttp'},
                success: function (data) {
                    removeLoadingState();
                    console.log(data);

                    var res = JSON.parse(data);
                    if (res.code === "-1" || res.code === -1) {
                        errors.push("Email already exist");
                    }

                    if (errors.length > 0)
                    {
                        $("#customerEmail").css("border", "1px solid red");
                        $("#customerErrorModal .modal-body").append(errors[0])
                        $("#customerErrorModal").modal();

                    } else {
                        $("#step1").css("display", "none");
                        $("#step2").css("display", "block");
                        $("#step3").css("display", "none");

                        $("#process-step-1").removeClass('btn-primary').addClass('btn-default');
                        $("#process-step-2").removeClass('btn-default').addClass('btn-primary');
                        $("#process-step-2").removeAttr('disabled');
                        $("#process-step-3").removeClass('btn-primary').addClass('btn-default');
                    }

                },
                error: function (xhr, status_code, status_text) {
                    console.log(status_code + " : " + status_text);
                    $("#customerErrorModal .modal-body").append("Error While validating Email");
                    $("#customerErrorModal").modal();
                    removeLoadingState();
                }
            });

        } else if (stage == 2)
        {
            // validate and setup properly
            $("#customerErrorModal .modal-body").html("");

            for (var i = 0; i < formFieldStage2.length; i++)
            {
                var temp = $("#" + formFieldStage2[i][0]).val();
                if (temp == formFieldStage2[i][1])
                {
                    $("#" + formFieldStage2[i][0]).css("border", "1px solid red");

                    errors.push("Please Input  " + formFieldStage2[i][2]);
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
                    $("#customerErrorModal .modal-body").append(errorText);
                }
                $("#customerErrorModal").modal();
                return;
            }

            $("#step1").css("display", "none");
            $("#step2").css("display", "none");
            $("#step3").css("display", "block");

            $("#process-step-1").removeClass('btn-primary').addClass('btn-default');
            $("#process-step-2").removeClass('btn-primary').addClass('btn-default');
            $("#process-step-3").removeAttr('disabled');
            $("#process-step-3").removeClass('btn-default').addClass('btn-primary');
        }
    }

</script>