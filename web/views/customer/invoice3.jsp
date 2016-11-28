<html>
    <head>
    
  </head>
  
  <body style="background-color: #eee; ">
   
      <table id="container" cellpadding="0" cellspacing="0" style="width: 100%; margin:0; padding: 15px; text-align: center; background-color: #eeeeee; ">
          <tr>
              <td id="invoiceholder" style="text-align: center;">
                  <table id="invoice-header" cellpadding="0" cellspacing="0" style="margin:auto;padding: 15px;font:normal normal 13px/20px Arial; background-color: #fff; width: 60%;">
                      
                      <!-----------------   HEADER ---------------------->
                      <tr id="header" style="width: 100%;">
                          <td colspan="3" style="padding-bottom: 10px;">
                            <div style="border-bottom: 1px solid #eee;text-align: center;">
                                <h3>${companyName}</h3>
                                <h4>INVOICE</h4>
                            </div>
                          </td>
                      </tr>
                      
                      
                      <!-----------------   HEADER DETAILS ---------------------->
                      <tr id="header-details" style="width: 100%;">
                          <td style="width:29%;padding-bottom: 10px;">
                              <div style="text-align: left;">
                                  From:<br/>
                                  <strong>${companyName}</strong><br/>
                                  ${addressLine1}<br/>
                                  ${addressLine2} <br/>
                                  Phone: ${phone}<br/>
                                  Email: ${email}
                              </div>
                          </td>
                          
                          
                          <td style="width:29%;padding-bottom: 10px;">
                              <div style="text-align: left;">
                                  To:<br/>
                                  <strong>${customerName}</strong><br/>
                                  ${customerStreet}<br/>
                                  ${customerCity}, ${customerState} <br/>
                                  Phone: ${customerPhone}<br/>
                                  Email: ${customerEmail}
                              </div>
                          </td>
                          
                          
                          <td style="width:29%;padding-bottom: 10px;">
                              <div style="text-align: left;">
                                  <strong>Invoice #: </strong> ${orderId}
                                  <br/><br/>
                                  <strong>Order ID: </strong> ${orderId}
                                  <br/><br/>
                              </div>
                          </td>
                      </tr> <!-- header detaiils -->
                      
                  </table>
                  
                  
                  <table id="invoice-details" cellpadding="0" cellspacing="0" style="margin:auto;padding: 15px;font:normal normal 13px/20px Arial; background-color: #fff;width: 60% !important; overflow: auto;">
                      <!-----------------   ITEMS LIST HEADER ---------------------->
                        <tr style="padding-top: 20px; font: normal bold 14px/20px Arial;">
                              <td style="width:10%;border:1px solid #eee;border-right: 0;padding:10px;text-align: center;">SN</td>
                              <td style="width:30%;border:1px solid #eee;border-right: 0;padding:10px;">Project</td>
                              <td style="width:30%;border:1px solid #eee;border-right: 0;padding:10px;">Item/Product</td>
                              <td style="width:10%;border:1px solid #eee;border-right: 0;padding:10px;text-align: center;">Qty</td>
                              <td style="width:20%;border:1px solid #eee;padding:10px;text-align: center;">Subtotal</td>
                        </tr>
                        
                        
                        
                        ${salesData}
                        
                        
                        
                        
                        <!-----------------   ITEMS LIST CONTENT ---------------------->
                        <!--
                        <tr style="padding-top: 20px; font: normal normal 13px/20px Arial;margin: 0;">
                              <td style="width:10%;border:1px solid #eee;border-right: 0;padding:10px;text-align: center;">SN</td>
                              <td style="width:30%;border:1px solid #eee;border-right: 0;padding:10px;text-align: left;">ProjectProject ProjectProjectProject ProjectProjectProject ProjectProjectProject</td>
                              <td style="width:30%;border:1px solid #eee;border-right: 0;padding:10px;text-align: left;">Item/ProductProductProductProduct ProductProductProductProductProduct</td>
                              <td style="width:10%;border:1px solid #eee;border-right: 0;padding:10px;text-align: center;">Qty</td>
                              <td style="width:20%;border:1px solid #eee;padding:10px;text-align: right;">Subtotal</td>
                        </tr>
                        -->
                        
                        
                        <!-----------------   TOTAL ---------------------->
                        <tr style="padding-top: 20px; font: normal normal 13px/20px Arial;background: #eee;margin: 0;">
                              <td colspan="4" style="width:80%;border-bottom:1px solid #fff;padding:10px;text-align: right;">${total}</td>
                              <td style="width:20%;border-left:1px solid #fff;border-bottom:1px solid #fff;padding:10px;text-align: right;">0.00</td>
                        </tr>
                        
                        <!-----------------   VAT ---------------------->
                        <tr style="padding-top: 20px; font: normal normal 13px/20px Arial;background: #eee;margin: 0;">
                              <td colspan="4" style="width:80%;border-bottom:1px solid #fff;padding:10px;text-align: right;">${vat}</td>
                              <td style="width:20%;border-left:1px solid #fff;border-bottom:1px solid #fff;padding:10px;text-align: right;">0.00</td>
                        </tr>
                        
                        <!-----------------   GATEWAY ---------------------->
                        <tr style="padding-top: 20px; font: normal normal 13px/20px Arial;background: #eee;margin: 0;">
                              <td colspan="4" style="width:80%;border-bottom:1px solid #fff;padding:10px;text-align: right;">${gatewayCharge}</td>
                              <td style="width:20%;border-left:1px solid #fff;border-bottom:1px solid #fff;padding:10px;text-align: right;">0.00</td>
                        </tr>
                        
                        <!-----------------   GRAND TOTAL ---------------------->
                        <tr style="padding-top: 20px; font: normal normal 13px/20px Arial;background: #eee;margin: 0;">
                              <td colspan="4" style="width:80%;border-bottom:1px solid #fff;padding:10px;text-align: right;">Grand Total</td>
                              <td style="width:20%;border-left:1px solid #fff;border-bottom:1px solid #fff;padding:10px;text-align: right;">${grandTotal}</td>
                        </tr>
                  </table>
                  
              </td><!-- centered content holder --> 
          </tr>
          
      </table>

  </body>
</html>