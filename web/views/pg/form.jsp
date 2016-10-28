<%-- 
    Document   : form
    Created on : Oct 22, 2016, 5:35:32 PM
    Author     : swedge-mac
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        
        <form name="form1" action="https://sandbox.interswitchng.com/webpay/pay" method="post">

            <input name="product_id" type="text" value="6207"/>
            <input name="pay_item_id" type="text" value="101"/>
            <input name="amount" type="text" value="2000000"/>
            <input name="currency" type="text" value="566"/>
            <input name="site_redirect_url" type="text" value="http://techieplanetltd.com/neoforce.php?" />
            <input name="txn_ref" type="text" value="NTEP001" />
            <input name="hash"type="text"value="b92161d23d16bea357a72e77972c290b0a550f9cc2f8f47173d2e6f14ff324f06b8a490f6fee266a07fdb1845d1b31845dcace93de58fbd735d227076a130af0"/>
            <input name="submit" type="submit">
        </form>
        <!--hash order-->
<!--        tnx_ref
        product_id
        pay_item_id
        amount
        site_redirect_url
        <the provided MAC key>

        62051012000000http://techieplanetltd.com/neoforce.php?D3D1D05AFE42AD50818167EAC73C109168A0F108F32645C8B59E897FA930DA44F9230910DAC9E20641823799A107A02068F7BC0F4CC41D2952E249552255710F
-->


        <p>
            The interswitch return value: <c:out value="${qstring}" />
        </p>
    </body>
</html>
