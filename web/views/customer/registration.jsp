<%-- 
    Document   : registration
    Created on : Oct 27, 2016, 11:25:19 AM
    Author     : Prestige
--%>
<%@ include file="../includes/lid.jsp" %>  
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/accounting.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/app.min.js"></script>
 <script type="text/javascript" src="${pageContext.request.contextPath}/js/functions.js"></script> 
 <script src="plugins/bootstrap-wysihtml5/bootstrap3-wysihtml5.all.min.js"></script>
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
                      <h1>Customer Registration Form</h1>
                  </div>
              </div>
          </header>
          <div class="jumbotron" style="background-color: #fff">
<div class="stepwizard">
    <div class="stepwizard-row">
        <div class="stepwizard-step">
            <button type="button" id="process-step-1" class="btn btn-primary btn-circle" onclick="return showCustomerReg()">1</button>
            <p>Register</p>
        </div>
         <div class="stepwizard-step">
            <button type="button" id="process-step-2" class="btn btn-default btn-circle" disabled="disabled" onclick="proceed()" >2</button>
            <p>Documents</p>
        </div>
        <div class="stepwizard-step">
            <button type="button" id="process-step-3" class="btn btn-default btn-circle" disabled="disabled" onclick="return showOrderProduct()">3</button>
            <p>Order/Checkout</p>
        </div>
    </div>
</div>

<form role="form" name="customerRegistration" method="POST" action="CustomerRegistration" enctype="multipart/form-data" onsubmit="return submitForm()">
  
    <input type="hidden" name="customer_id" value="0" />
    <input type="hidden" name="agent_id" id="agent_id" value="1" />
    <input type="hidden" id="pageContext" value="${pageContext.request.contextPath}" />
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
                                                <label for="customerMiddlename">Middle Name</label>
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

                                                <input type="text" class="form-control" name="customerOfficePhone" id="customerOfficePhone" placeholder="Office Phone" 

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
                                                    <input type="text" class="form-control" id="customerPhone" name="customerPhone" placeholder="Phone Number" 

                                                           value=<c:if test="${isError}">"${param.customerPhone}"</c:if><c:if test="${!isError}">"${customer.phone}"</c:if> />

                                                    </div>
                                                </div>
                                                <div class="col-md-4">
                                                    <div class="form-group" >
                                                        <label for="customerOtherPhone">Other Phone</label>
                                                        <input type="text" class="form-control" id="customerOtherPhone" name="customerOtherPhone" placeholder="Other Phone" 

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

                                                        <input type="text" class="form-control" id="customerKinPhone" name="customerKinPhone" placeholder="Enter Kin Phone Number" 

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
                                                    <div class="form-group" style="padding-left:10px !important;">
                                                        <label for="customerBanker">Banker*</label>
                                                       <select name="customerBanker" id="customerBanker" class="form-control">
                                                            <option value="select">--choose--</option>
                                                       <c:forEach items="${Banks}" var="bank">
                                                           <option value="${bank.id}" <c:if test="${customer.banker.id == bank.id.toString() || param.customerBanker == bank.id.toString()}">selected</c:if>>${bank.bankName}</option>
                                                       </c:forEach>
                                                            </select>
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
    
 <div class="row" id="step3" style="display:none">

   <div class="col-md-12">
               
  
         <div class="row">
                  <div class="col-md-12">
                    <div class="row" style="padding-top:10px;">
                    <div class="col-md-12">
                        <input type="hidden" name="dataHidden" id="dataHidden" />
                    	<fieldset>
                        	<legend style="padding-left:20px !important;">Product Details</legend>
                            <div class="col-md-12">
                            	<div class="row">
                                   
                                              
                                   <div class="col-md-3">
                                    	<div class="form-group">
                                            <label for="selectProdcut">Select Product</label>
                                            <select class="form-control select2" id="selectProduct" style="width: 100%;" onchange="getProjectUnits('${pageContext.request.contextPath}', 'Project','')" >
                                                <option value="" selected="selected">-- choose --</option>
                                                
                                              <c:forEach items="${projects}" var="project" >
                                                <option  value="${project.id}">${project.name}</option>
                                              </c:forEach>
                                            </select>
                                        </div> 
<!--                                              /.form-group select product -->
                                    </div>
                               
                                   <div class="col-md-3">
                                    	<div class="form-group">
                                          <input type="hidden" id="pUnitId" />
                                            <label for="selectUnit">Select Unit</label>
                                            
                                            <select class="form-control select2" id="selectUnit" style="width: 100%;" onchange="getProjectQuantity('${pageContext.request.contextPath}', 'ProjectUnit')">
                                              <option value="#" selected="selected">-- choose --</option>
                                             
                                            </select>
                                        </div> 
<!--                                              /.form-group  select unit-->
                                    </div>
                                
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="selectQuantity">Select Quantity</label>
                                            
                                            <select class="form-control select2" id="selectQuantity" style="width: 100%;" onchange="calculateProductAmount()">
                                              <option value="#" selected="selected">-- choose --</option>
                                             
                                            </select>
                                        </div> 
<!--                                            /.form-group select quantity -->
                                    </div>
                                </div>
                                    <div class="row">
                                
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productAmount">Amount</label>
                                            <input type="text" class="form-control" id="productAmount" name="productAmount" style="width: 100%;" readonly>
                                            <span id="amountPerUnit">
                                                Amount per Unit: <span id="amountUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="amountTotalUnit"></span>
                                            </span>
                                        </div> 
<!--                                            /.form-group amount -->
                                    </div>
                                              
                               
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMinimumInitialAmount">Initial Amount(N)</label>
                                            <input type="text" class="form-control" id="productMinimumInitialAmount" name="productMinimumInitialAmount" style="width: 100%;"  onkeyup="monthlyPayCalculator()">
                                            <span id="amountPerUnit">
                                                min initial amt /unit: <span id="initialAmountPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="minInitialAmountSpan"></span><br/>
                                            </span>
                                            <span id="productMinimumInitialAmountFormat"></span>
                                        </div> 
<!--                                            /.form-group initial monthly amount -->
                                    </div>
                                    <c:if test="${userTypeId == 1}">    
                                    <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productDiscount">Discount Percentage </label>
                                            <input type="text" class="form-control" id="productDiscount" name="productDiscount" style="width: 100%;"  onchange="monthlyPayCalculator()">
                                        </div> 
<!--                                            /.form-group productDiscount  -->
                                    </div>
                                    </c:if>     
                                     <div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="amountLeft">Balance Payable(N)</label>
                                            <input type="text" class="form-control" id="amountLeft" name="amountLeft" style="width: 100%;" readonly >
                                            <span id="amountPerUnit"></span>
                                            <span id="amountLeftFormat"></span>
                                        </div> 
<!--                                                  /.form-group initial monthly amount -->
                                    </div>
                               <input type="hidden" id="editMode" value="" />
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMaximumDuration">Payment Duration</label>
                                            <div class="row">
                                            	<div class="col-md-12">
                                            		<select class="form-control select2"  id="productMaximumDuration" style="width: 100%;" onchange="monthlyPayCalculator('exsiting')">
                                                      <option value="#" selected="selected">-- choose --</option>
                                                    
                                                    </select>
                                                </div>
                                            </div>
                                            <span id="amountPerUnit>
                                                max payment duration /unit: <span id="payDurationPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="payDurationPerQuantity"></span>
                                            </span>
                                        </div> <!-- /.form-group Duration -->
                                    </div>
                               
                                	<div class="col-md-2">
                                    	<div class="form-group">
                                            <label for="productMinimumMonthlyPayment">Monthly Payment(N)</label>
                                            <input type="text" class="form-control" id="productMinimumMonthlyPayment" name="productMinimumMonthlyPayment" style="width: 100%;" onkeyup="calculateDurationFromMonthlyPay()">
                                            <span id="finalAmount" style="display:block"></span>
                                            <span id="amountPerUnit">
                                                min monthly pay / unit: <span id="monthlyPayPerUnit"></span><br/>
                                                This Sale (x<span id="qty"></span>):  <span id="monthlyPayPerQuantity"></span>
                                            </span>
                                        </div> <!--/.form-group amount -->
                                    </div>
                               
                                    <c:if test="${userTypeId != null && userTypeId < 3 }">     
                                          <div class="col-md-2">
                                              <div class="form-group">
                                                  <label>
                                                      Commission(%)
                                                  </label>
                                                  <input 
                                                      type="text" 
                                                      class="form-control" 
                                                      value="0" 
                                                      name="commp" 
                                                      id="commp" 
                                                      <c:if test="${userTypeId != 1}">readonly="readonly"</c:if> 
                                                  />
                                                   <span >This is the commission payable to an agent</span>
                                              </div>
                                          </div>
                                    </c:if>
                                                
                                </div>
                                              
                                
                                <div class="row">
                                      
                                      <div class="col-md-2">
                                              <div class="form-group">
                                                  <label>
                                                      Notification Day
                                                  </label>
                                                  <input type="number" class="form-control" min="1" max="31" value="1" name="day_of_notification" id="day_of_notification"/>
                                              <span class="productSpan">Select the day of the month to receive monthly notification</span>
                                              </div>
                                      </div>
                                      
                                  </div>               
                                              
                                              
                  <div class="row">
                        <div class="col-md-12 box-footer">
                            <div class="row">
                                <div class="col-md-4">
                                <div id="errorText" style="color:#722F37 !important; font-weight:bold !important;"></div>
                                </div>
                                <div class="col-md-2 pull-right">
                                   <div id="addToCartLabel"  style="margin: 0 auto !important;" >
                                    	<div class="form-group">
                                            <a class="btn btn-success" name="addToCart" id="addToCart" href="#" onClick=" return addToCart(event)" ><i class="fa fa-cart-plus"></i> Add to Cart</a>
                                        </div> 
                                   </div>
                               </div>
                           </div>
                        </div>
                  </div>
                            </div> <!--/.col-md-12 -->
                    	</fieldset>
                  	</div> <!--/.col-md-12 -->
                 	</div> <!--/.row -->
                  </div> <!-- /.col-md-4 -->
                  
                 </div> 
                  
                  
                  
                  
                  
  <!-- 
    *****************************************
    Product Cart starts here
    *****************************************
  -->
 <div class="row">
  <div class="col-md-12">
      <input type="hidden" name="dataHidden" id="dataHidden" />
    <div class="row" style="padding-top:10px;">
    <div class="col-md-12" id="shoppingCart">
      <div class="panel panel-default panel-primary">
          <div class="panel-heading">
              <div class="panel-title"><i class="fa fa-cart-plus fa-2x"></i> Product Cart </div>
          </div>
          <div class="panel-body"> 
                <div class="row" >
                 <div class="col-md-12">
                 <table id="productCart" class="table table-bordered table-striped table-hover" style="text-align:right !important;">
                    <thead>
                      <tr>
                       <th>Product</th>
                        <th>Product Unit</th>
                        <th>Quantity</th>
                        <th>Amount</th>
                        <th>Initial Amount</th>
                        <th>Amount to Pay</th>
                        <th>Duration</th>
                        <th>Monthly Pay</th>
                        <th>Transaction Deduction</th>
                        
                        <th style="width: 80px">Action</th>
                        
                      </tr>
                    </thead>
                    <tfoot style="text-align:right !important;color:green !important; font-weight:bold !important;">
                      <tr>
                        <th colspan="8" align="right" style="text-align:right !important;">Total</th>
                        
                        <th style="text-align:right !important;" align="right"><span id="cartSum">0</span></th>
                        
                        <th></th>
                        
                      </tr>
                    </tfoot>
                    <tbody>
                       
                            
                        
                   </tbody>
                   
                  </table>
                     
                  <input type="hidden" id="CartActualSum" value="" />              
                 </div>                   
                 <!-- 
                   ***************************
                   Checkout Button starts Here
                   ***************************
                 -->
                 <div class="col-md-2 col-md-offset-10">
                     <div class="form-group">
                        <a href="#" class="btn" name="checkOutToPay" id="checkOutToPay" onClick="return checkOutOfCart();"><i class="fa fa-cart-plus"></i>Checkout</a>
                    </div> 

                 </div>
                 
                 <!--
                  ****************************
                    Checkout Button ends Here
                  ****************************
                 -->
                </div>
          </div>
      </div>
    </div> 
                        
         <div class="col-md-12" id="paymentCheckout">

                <fieldset>
        <legend style="padding-left:20px !important;">Check Out</legend>

            <div class="col-md-12" >

                <!-- Start of Payment Method Container -->
                <div class="row" > 
                    <div class="col-md-12">
                        <span style="color:green;font-weight:bold;">You'd be paying <span id='paySum'></span></span>
                        <div class="form-group">
                            <label for="paymentMethod">Payment method:</label><br/>
                            <input type="radio" name="paymentMethod" value="1" id="bankdep" onclick="showNecessaryMenu(1)"/>&nbsp;<label for="bankdep" style="display:inline !important;">Bank Deposit</label>&nbsp;&nbsp;&nbsp;&nbsp;

                        <%//    <input type="radio" name="paymentMethod" value="2" id="paywithcard" onclick="showNecessaryMenu(2)"/>&nbsp; <label for="paywithcard" style="display:inline !important;cursor:pointer !important;">Credit/Debit Card <img src="${pageContext.request.contextPath}/images/img/paywithcard.png" /></label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;%>

                            <input type="radio" name="paymentMethod" value="3" id="paywithcash" onclick="showNecessaryMenu(3)"/>&nbsp;<label for="paywithcash" style="display:inline !important;"> Cash</label>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <input type="radio" name="paymentMethod" value="4" id="bankTransfer" onclick="showNecessaryMenu(4)"/>&nbsp; <label for="bankTransfer" style="display:inline !important;cursor:pointer !important;">Bank Transfer </label>
                        </div>
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="companyAccount">Company Account</label>
                            <select name="companyAccount" id="companyAccount" class="form-control select2" style="width: 100%;">
                                <option value="">--Select Account--</option>
                                <c:forEach items="${companyAccount}" var="CA">
                                    <option value="${CA.getId()}">${CA.getAccountName()}</option>
                                </c:forEach>
                            </select>
                        </div> 
                    </div>
                </div>
                <!-- End of Payment Method Container -->        

                 <!-- Pay via Bank Deposit Div Container -->            
                <div class='row' id='pwBankdeposit'>



                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="depositorsName">Depositor's Name</label>
                            <input type="text" class="form-control" id="depositorsName" name="depositorsName" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="tellerNumber">Teller Number</label>
                            <input type="text" class="form-control" id="tellerNumber" name="tellerNumber" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="tellerAmount">Amount</label>
                            <input type="text" class="form-control amount-box" id="tellerAmount" name="tellerAmount" style="width: 100%;">
                        </div>      
                    </div>

                    <div class="col-md-2">
                        <div class="form-group" style="padding-top:25px !important;">
                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge"/>
                        </div>      
                    </div>

                </div>
                <!-- End of Pay Via Bank Deposit Div Container -->


                <!-- Pay with Cash Div Container -->
                <div class='row' id='pwCash'>
                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="cashAmount">Amount</label>
                            <input type="text" class="form-control amount-box" id="cashAmount" name="cashAmount" style="width: 100%;">
                        </div>      
                    </div>
                    <div class="col-md-2">
                        <div class="form-group" style="padding-top:25px !important;">
                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge" style="vertical-align:bottom !important;"/>
                        </div>      
                    </div>
                </div>
                <!-- End of Pay with Cash Div Container -->



                 <!-- Pay with Card Div Container -->
           <% /*     <div class='row' id='pwCard'>


                        <div class="col-md-2">
                            <div class="form-group">
                                <label for="tellerNumber">Amount</label>
                                <input type="text" class="amount-box form-control" name="cardAmount" />
                            </div>
                            <div class="form-group">
                                <label for="tellerNumber">Click to proceed to payment</label>
                                <!--<a href="${pageContext.request.contextPath}/images/img/webpay.png" target="_blank" class="btn btn-success"><i class="fa fa-angle-double-right"></i> Pay Now</a>
                                --> <button type="submit"  name="Pay" class="btn btn-success"  style="vertical-align:bottom !important;"><i class="fa fa-angle-double-right"></i> Pay Now</button>
                            </div> 
                        </div>
                </div> */%>
                <!-- End of Pay with Card Div Container -->

                 <!-- Pay with Card Div Container -->
                <div class='row' id='pwBankTransfer'>
                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="bankName">Depositor's Bank Name</label>
                            <input type="text" class="form-control" id="transfer_bankName" name="transfer_bankName" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="accountNo">Depositor's Account No</label>
                            <input type="text" class="form-control" id="transfer_accountNo" name="transfer_accountNo" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-3">
                        <div class="form-group">
                            <label for="accountNo">Depositor's Account Name</label>
                            <input type="text" class="form-control" id="transfer_accountName" name="transfer_accountName" style="width: 100%;">
                        </div> 
                    </div>

                    <div class="col-md-2">
                        <div class="form-group">
                            <label for="tellerAmount">Amount</label>
                            <input type="text" class="form-control amount-box" id="transfer_amount" name="transfer_amount" style="width: 100%;">
                        </div>      
                    </div>

                    <div class="col-md-1">
                        <div class="form-group" style="padding-top:25px !important;">
                            <input type="submit"  name="Pay" class="btn btn-success" value="Lodge"/>
                        </div>      
                    </div>
                </div>
                <!-- End of Pay with Cash Div Container -->

              </div>
        </fieldset>
                                
         </div>
         </div>
                                 
   </div> <!--/.row -->
 </div> <!--/.box box-default -->
           
          <!-- 
            *****************************************
            Product Cart Ends here
            *****************************************
          -->

    
      <input type="hidden" name="cartDataJson" id="cartDataJson" />
      

  </div><!-- /.box -->
  
  <div class="col-md-12">
      <a class="btn btn-primary" href="#" onclick="return showCustomerReg()" role="button"><i class="fa fa-long-arrow-left"></i> Customer registration</a>
  </div>
  
</div><!-- /.box -->
                    
      
      <!--
      <div class="row">
          
          <div class="box-footer" style="background-color:transparent;">
                      <input type="submit" class="btn btn-primary" name="customerCreate" value="Save"/>
          </div>
          
      </div>
      -->
</form>     
            
          </div>  
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
               
         
      <!--MODAL-->
      <div class="modal fade" id="deleteModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
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
       
      
      <!--MODAL-->
      <div class="modal fade" id="deleteModalCart" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog">
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
      
      <!--MODAL-->
      <div class="modal fade" id="customerErrorModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
         <div class="vertical-alignment-helper">
          <div class="modal-dialog vertical-align-center">
          <div class="modal-content">
            <div class="modal-header">
              <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
              <h4 class="modal-title">RPL</h4>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
              <button id="ok" type="button" data-dismiss="modal" class="btn btn-primary">OK</button>
            </div>
          </div>
          </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
      </div><!-- /.modal -->
  
    
     <script>
    $(function () {
        $("#customerDateOfBirth").datepicker({changeMonth: true, changeYear: true, yearRange: "1930:2017", dateFormat: "dd/mm/yy"});
    });
    
    function submitForm(){
           
           var submitOk = true;
           
           var payment_mode = $('input:radio[name=paymentMethod]:checked').val();
           
           var companyAccount = $("#companyAccount").val();
           
           if(companyAccount == ""){
               
               alert("Please select company account");
               submitOk = false;
           } 
           else if(payment_mode == 1){
               
               var depositorsName = $("#depositorsName").val();
               var tellerNumber = $("#tellerNumber").val();
                       
               if( $.trim(depositorsName) == ""){
                   alert("Please Enter depositors name");
                   submitOk = false;
               }
               else if($.trim(tellerNumber) == ""){
                   alert("Please enter teller number");
                   submitOk = false;
               }
           }
           else if(payment_mode == 4){
               
               var transfer_bankName = $("#transfer_bankName").val();
               var transfer_accountNo = $("#transfer_accountNo").val();
               var transfer_accountName = $("#transfer_accountName").val();
               
               if($.trim(transfer_bankName) == ""){
                   alert("Please enter Bank Name");
                   submitOk = false;
               }
               else if($.trim(transfer_accountNo) == ""){
                   alert("Please enter account number");
                   submitOk = false;
               }
               else if($.trim(transfer_accountName) == ""){
                   alert("Please enter account Name");
                   submitOk = false;
               }
               
           }
           
           return submitOk;
       }
       

    var formFieldStage1 = [       [ "agent_id", "select", "an Agent for Customer"]
            , ["customerTitle", "select", "Customer Title"]
            , ["customerFirstname", "", "Customer First Name"]
            <%//, ["customerMiddlename", "", "Customer Middle Name"]%>
            , ["customerLastname", "", "Customer Last Name"]
            , ["customerGender", "select", "Customer Gender"]
            , ["customerMaritalStatus", "select", "Customer Marital Status"]
            , ["customerDateOfBirth", "", "Customer Date Of Birth"]
            , ["customerEmail", "", "Customer Email"]
            , ["customerOccupation", "", "Customer Occupation"]
            , ["customerEmployer", "", "Customer Employer"]
    ,["customerOfficePhone" , "" , "Customer Office Phone"]
       <% /*                         ,["customerOfficeStreet" , "" , "Customer Office Street"]
                                ,["customerOfficeCity" , "" , "Customer Office City"]
                                ,["customerOfficeState" , "" , "Customer Office State"]
                                ,["customerOfficeCountry" , "select" , "Customer Office Country"] */%>
    , ["customerEmployerStreet", "", "Customer Employer Street"]
            , ["customerEmployerCity", "", "Customer Employer City"]
            , ["customerEmployerState", "", "Employer State"]
            , ["customerEmployerCountry", "select", "Customer Employer Country"]
            , ["customerStreet", "", "Customer Address"]
            , ["customerCity", "", "Customer City"]
            , ["customerState", "", "Customer State"]
            , ["customerCountry", "select", "Customer Country"]
            , ["customerPhone", "", "Customer Phone"]
    <%//,["customerOtherPhone" , "select" , "Customer Country"]%>
    , ["customerPostalAddress", "", "Customer  Postal Address"]
    , ["customerKinName", "", "Customer Next Of Kin Name"]
    , ["customerKinRelationship", "", "Customer Next Of Kin Relationship"]
    <%//,["customerKinEmail" , "" , "Customer Next Of Kin Email"]%>
    , ["customerKinPhone", "", "Customer Next Of Kin Phone Number"]
            , ["customerKinAddress", "", "Customer  Next Of Kin Address"]
            , ["customerBanker", "select", "Customer Banker"]
            , ["customerAccountName", "", "Customer Account Name"]
    , ["customerAccountNumber", "", "Customer Account Number"]
    ];

    var formFieldStage2 = [["customerPhoto", "", " Customer PassPort"]
                , ["customerKinPhoto", "", "Customer Next Of Kin Passport"],
        ["customerPhotoID", "", "Customer ID card /Driver Lincense etc"],
        ["customerBankStandingOrder", "", "Customer Post-dated Cheques/Bank Standing Order"]];
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

                    errors.push("Please Input File For " + formFieldStage2[i][2]);
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
   
  </body>

</html>
  

