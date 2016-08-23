<!DOCTYPE html>
<!--
To change this license header, choose License Headers in Project Properties.
To change this template file, choose Tools | Templates
and open the template in the editor.
-->

<html>
    <head>
        <title>TODO supply a title</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.css" type="text/css" />
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/custom.css" type="text/css" />
        
    </head>
    <body>
        <div class="container-fluid">
            <div class="row">

                <div class="col-md-12 homebackdrop"><img class="img-responsive" src="${pageContext.request.contextPath}/images/home-bg-with-neo.png"></div>

            </div>
            
            <div class="row" style="margin-top: 10px;">
                <div class="col-md-2">&nbsp;</div>
                <div class="col-md-5" style="padding-top: 70px;">
                    <h4 class="blockquote">
                        NEOFORCE is an advanced Sales Force Enterprise Portal Solution developed for 
                        organizations to manage mass subscription and instalmental sales schemes.
                    </h4>
                </div>
                <div class="col-md-3 bgeee paddingtop20">
                    <p class="bold">Sign In</p>

                    <form class="borderbottom paddingbottom10" action="${pageContext.request.contextPath}/Login" method="POST">
                            <div class="form-group">
                                <label>Login as</label>
                                <select name="usertype" id="usertype" class="form-control select2" style="">
                                  <option value="0">--Select--</option>
                                  <option value="ADMIN">Admin Member</option>
                                  <option value="AGENT">Agent</option>
                                  <option value="CUSTOMER">Customer</option>
                                </select>
                            </div>
                            <div class="form-group">
                              <label for="email">Email Address</label>
                              <input type="email" class="form-control" name="email" id="email" placeholder="Email">
                            </div>
                            <div class="form-group">
                              <label for="password">Password</label>
                              <input type="password" class="form-control" name="password" id="password" placeholder="Password">

                            </div>
                            
                            <button type="submit" class="btn btn-primary">Sign In</button>
                    </form>
                    <p><a class="" href="">Forgot Password</a></p>
                </div>
                <div class="col-md-2">&nbsp;</div>
            </div>
        </div>
        
        
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery-1.11.1.min.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    </body>
</html>
