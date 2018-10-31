/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import static com.tp.neo.controller.components.AppController.APP_NAME;
import static com.tp.neo.controller.components.AppController.companyName;
import static com.tp.neo.controller.components.AppController.defaultEmail;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Agent;
import com.tp.neo.model.Company;
import com.tp.neo.model.Customer;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.User;
import com.tp.neo.model.Withdrawal;
import com.tp.neo.model.utils.MailSender;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Swedge
 */
public class EmailHelper {

    /**
     * ********************************* AGENT
     * **********************************
     */
    /*
    This method sends approval or decline email
     */
    public void sendAgentApprovalEmail(Agent agent, int status) {
        //send email 
        String approvalMessage = "Dear %s %s,<br/>"
                + "Your registration as an agent on %s has been approved.<br/>"
                + "You may now login with your email and password used during registration.<br/><br/>"
                + "Thank you for choosing to work with us.";
        String declineMessage = "Dear %s %s,<br/>"
                + "Unfortunately your registration as an agent on %s could not be approved.<br/>";
        String emailSubject = APP_NAME + " Agent Registration Request";
        String message = "";

        if (status == 1)
        {
            message = String.format(approvalMessage, agent.getFirstname(), agent.getLastname(), APP_NAME);
        }
        else if (status == 0)
        {
            message = String.format(declineMessage, agent.getFirstname(), agent.getLastname(), APP_NAME);
        }

        //System.err.println("email " + agent.getEmail() + " email: " + defaultEmail + " subject: " + emailSubject + " message: " + message);
        StringBuilder html = new EmailParser().prepareEmail("Agent Approval", message , "", "");
        new MailSender().sendHtmlEmail(agent.getEmail(), defaultEmail, emailSubject, html);

    }

    protected void sendAgentWalletCreditAlert(Customer customer, OrderItem order ,  double amount) {
        String messageBody = "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                + "<br/>" + "Your wallet has been credited " + String.format("%.2f", amount)
                + "Item: " + order.getUnit().getTitle() + " in " + order.getUnit().getProject().getName() + "."
                + "Customer: " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ","
                + "Number of Units: " + order.getQuantity();

        String emailSubject = APP_NAME + ": New Wallet Credit";
        StringBuilder html = new EmailParser().prepareEmail("Agent Wallet Alert", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, html);
    }

    /**
     * ********************************* ORDERS
     * **********************************
     */
    /**
     * This email goes to the admin when a new order is created in the system
     *
     * @param order the order that was just created
     * @param customer the customer that owns the order
     * @param recipientsList the list of admins that can receive that email. One
     * of them can now process the order
     */
    protected void sendNewOrderEmailToAdmins(ProductOrder order, Customer customer, List<User> recipientsList, String thisOrderPageLink) {

        String messageBody = "Dear Admin,"
                + "<br/>" + "A new order, ID: <b>" + order.getId() + "</b> for customer: <b>" + customer.getFirstname() + " " + customer.getLastname()
                + " has been created and needs approval from you.";

        String emailSubject = APP_NAME + ": New Order Awaiting Approval";
        StringBuilder html = new EmailParser().prepareEmail("New Order Alert", messageBody , thisOrderPageLink, "Goto Order");
        for (int i = 0; i < recipientsList.size(); i++)
        {
            new MailSender().sendHtmlEmail(recipientsList.get(i).getEmail(), defaultEmail, emailSubject, html);
        }

    }

    protected void sendNewOrderEmailToCustomer(Lodgement lodgement, Customer customer) {
        String messageBody = "Dear " + customer.getFirstname() + " " + customer.getLastname() + ","
                + "<br/>" + "Your order of value " + lodgement.getAmount() + " has been received and is being processed.";

        String emailSubject = APP_NAME + ": New Order";
        StringBuilder html = new EmailParser().prepareEmail("New Order", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, html);
    }

    protected void sendNewOrderEmailToAgent(Lodgement lodgement, Customer customer) {
        String messageBody = "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                + "<br/>" + "Your new customer order has been received and is being processed."
                + "<br/>" + "Customer: " + customer.getFirstname() + " " + customer.getLastname()
                + "<br/>" + "Order Amount: " + lodgement.getAmount();

        String emailSubject = APP_NAME + ": New Order";
         StringBuilder html = new EmailParser().prepareEmail("New Order", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, html);
    }

    /**
     * ********************************* ORDER APPROVALS
     * **********************************
     */
    protected void sendOrderApprovalEmailToCustomer(Customer customer, ProjectUnit unit, double amount) {
        String messageBody = "Dear " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                + "<br/>" + "Your order for " + unit.getTitle() + " in " + unit.getProject().getName() + " has been approved "
                + "and has been advanced by the sum of " + String.format("%.2f", amount) + "."
                + "<br/>"
                + "<br/>" + "Congratulations ";

        String emailSubject = APP_NAME + ": New Order Approval";
        StringBuilder html = new EmailParser().prepareEmail("Order Approval", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, html);
    }

    protected void sendOrderApprovalEmailToAgent(Customer customer, ProjectUnit unit, double amount) {
        String messageBody = "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                + "<br/>" + "An order has been approved for your customer - " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ","
                + "Item: " + unit.getTitle() + " in " + unit.getProject().getName() + "."
                + "Number of Units: " + unit.getQuantity()
                + "This order has been advanced by the sum of " + String.format("%.2f", amount) + "."
                + "<br/>"
                + "<br/>" + "Congratulations ";

        String emailSubject = APP_NAME + ": New Order Approval";
        StringBuilder html = new EmailParser().prepareEmail("Order Approval", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, html);
    }

    /**
     * ********************************* LODGEMENT
     * **********************************
     */
    protected void sendNewLodgementEmailToAdmins(Lodgement lodgement, Customer customer, List<User> recipientsList, String waitingLodgementsPageLink) {
        String messageBody = "Dear Admin,"
                + "<br/>" + "A new lodgement, ID: <b>" + lodgement.getId() + "</b> for customer: <b>" + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ")"
                + " has been created and needs approval.";

        String emailSubject = APP_NAME + ": New Lodgement Awaiting Approval";
        StringBuilder html = new EmailParser().prepareEmail("New Lodgement Alert", messageBody , waitingLodgementsPageLink, "Goto Lodgement");
        for (int i = 0; i < recipientsList.size(); i++)
        {
            new MailSender().sendHtmlEmail(recipientsList.get(i).getEmail(), defaultEmail, emailSubject, html);
        }

    }

    protected void sendNewLodgementEmailToCustomer(Lodgement lodgement, Customer customer) {
        String messageBody = "Dear " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                + "<br/>" + "Your lodgement of value " + lodgement.getAmount() + " has been received and is being processed.";

        String emailSubject = APP_NAME + ": New Lodgement";
        StringBuilder html = new EmailParser().prepareEmail("New Lodgement Alert", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, html);
    }

    protected void sendNewLodgementEmailToAgent(Lodgement lodgement, Customer customer) {
        String messageBody = "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                + "<br/>" + "Your customer's lodgement has been received and is being processed."
                + "<br/>" + "Customer: " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                + "<br/>" + "Amount: " + lodgement.getAmount();

        String emailSubject = APP_NAME + ": New Lodgement";
        StringBuilder html = new EmailParser().prepareEmail("New Lodgement Alert", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, html);
    }

    /**
     * ********************************* LODGEMENT APPROVALS
     * **********************************
     */
    protected void sendLodgementApprovalEmailToCustomer(Customer customer, OrderItem item, double amount) {
        String messageBody = "Dear " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                + "<br/>" + "Your lodgement for " + item.getUnit().getTitle() + " in " + item.getUnit().getProject().getName() + " has been approved "
                + "and your purchase has been advanced by the sum of " + String.format("%.2f", amount) + "."
                + "<br/><br/>" + "Congratulations ";

        String emailSubject = APP_NAME + ": New Lodgement Approval";
        StringBuilder html = new EmailParser().prepareEmail("Lodgement Approval", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, html);
    }

    protected void sendLodgementApprovalEmailToAgent(Customer customer, OrderItem item, double amount) {
        String messageBody = "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                + "<br/>" + "A lodgement has been approved for your customer - " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ")"
                + "Item: " + item.getUnit().getTitle() + " in " + item.getUnit().getProject().getName() + "."
                + "Number of Units: " + item.getUnit().getQuantity()
                + "This sale has been advanced by the sum of " + String.format("%.2f", amount) + "."
                + "<br/><br/>" + "Congratulations ";

        String emailSubject = APP_NAME + ": New Lodgement Approval";
        StringBuilder html = new EmailParser().prepareEmail("Lodgement Approval", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, html);
    }

    /**
     * ********************************* LODGEMENT DECLINE
     * **********************************
     */
    protected void sendLodgementDeclineEmailToCustomer(Customer customer, Lodgement lodgement, double amount) {
        String messageBody = "Dear " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + "),"
                + "<br/>" + "Your lodgement of " + lodgement.getAmount() + " on " + lodgement.getCreatedDate() + " has been declined. "
                + "<br/><br/>Please contact your agent or our customer care for further details.";

        String emailSubject = APP_NAME + ": Lodgement Decline";
        StringBuilder html = new EmailParser().prepareEmail("Lodgement Decline", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, html);
    }

    protected void sendLodgementDeclineEmailToAgent(Customer customer, Lodgement lodgement, double amount) {
        String messageBody = "Dear " + customer.getAgent().getFirstname() + " " + customer.getAgent().getLastname() + " (" + customer.getAgent().getAccount().getAccountCode() + "),"
                + "<br/>" + "A lodgement of your customer -  " + customer.getFirstname() + " " + customer.getLastname() + " (" + customer.getAccount().getAccountCode() + ") has been declined."
                + "<br/><br/>Please contact your agent or our customer care for further details.";

        String emailSubject = APP_NAME + ": New Lodgement Approval";
        StringBuilder html = new EmailParser().prepareEmail("Lodgement Decline", messageBody , "", "");
        new MailSender().sendHtmlEmail(customer.getAgent().getEmail(), defaultEmail, emailSubject, html);
    }

    /**
     * ********************************** WITHDRAWAL
     * *******************************
     */
    protected void sendWithdrawalRequestEmailToAgent(Withdrawal w) {
        String messageBody = "Dear " + w.getAgent().getFirstname() + " " + w.getAgent().getLastname() + " (" + w.getAgent().getAccount().getAccountCode() + "),"
                + "<br/>" + "Your withdrawal request has been received and is being processed."
                + "<br/>Amount: " + w.getAmount();

        String emailSubject = APP_NAME + ": New Withdrawal Request";
        StringBuilder html = new EmailParser().prepareEmail("Withdrawal Request", messageBody , "", "");
        new MailSender().sendHtmlEmail(w.getAgent().getEmail(), defaultEmail, emailSubject, html);
    }

    protected void sendWithdrawalRequestEmailToAdmin(Withdrawal w, List<User> recipientsList, String withdrawalPageLink) {
        String messageBody = "New withdrawal request waiting for approval."
                + "<br/>" + "Agent: " + w.getAgent().getFirstname() + " " + w.getAgent().getLastname() + " (" + w.getAgent().getAccount().getAccountCode() + "),"
                + "<br/>Amount: " + w.getAmount();

        String emailSubject = APP_NAME + ": New Withdrawal Request";
        StringBuilder html = new EmailParser().prepareEmail("Withdrawal Request", messageBody , withdrawalPageLink, "Goto Withdrawal");
        for (int i = 0; i < recipientsList.size(); i++)
        {
            new MailSender().sendHtmlEmail(recipientsList.get(i).getEmail(), defaultEmail, emailSubject, html);
        }
    }

    /**
     * ************************ REMINDER ALERT
     * ***************************************
     */
    protected void sendReminderAlert(List customerAndItemsList, int dueDays) {
        String orderItemsString = "<ul>";
        for (Object customerAndItems : customerAndItemsList)
        {
            HashMap customerAndItemsMap = (HashMap) customerAndItems;
            Customer customer = (Customer) customerAndItemsMap.get("customer");
            List<OrderItem> orderItemsList = (List) customerAndItemsMap.get("order_items");

            for (Object itemListElement : orderItemsList)
            {
                HashMap itemMap = (HashMap) itemListElement;
                OrderItem item = (OrderItem) itemMap.get("order_item");
                double balance = (Double) itemMap.get("balance");
                orderItemsString += "<li>" + item.getUnit().getTitle() + " (" + item.getUnit().getProject().getName() + "): " + String.format("%.2f", balance) + "</li>";
            }

            orderItemsString += "</ul>";

            String messageBody = "Dear Customer (" + customer.getFirstname() + " " + customer.getLastname() + "(" + customer.getAccount().getAccountCode() + ")"
                    + "<br/>Reminder on your orders with payment due in " + dueDays + "."
                    + "<br/>" + orderItemsString
                    + "<br/><br/>" + "Please take necessary actions.";

            String emailSubject = APP_NAME + ": Payment Due in " + dueDays;
            StringBuilder html = new EmailParser().prepareEmail("Withdrawal Request", messageBody , "", "");
            new MailSender().sendHtmlEmail(customer.getEmail(), defaultEmail, emailSubject, html);

        }
    }

    /**
     * ********************************** REFERRAL CODE SHARING
     * *******************************
     */
    protected void sendReferralCodeEmail(String recipientEmail, Agent agent, String refLink) {
        String messageBody = "Good day,"
                + "<br/>" + "Please join me as an agent with " + companyName + "."
                + "<br/>" + "You stand a chance to make a lot of money selling properties.";
        
        String emailSubject = "Agent Referral Code - " + agent.getAccount().getAccountCode();
        StringBuilder html = new EmailParser().prepareEmail("Withdrawal Request", messageBody , refLink, "Register");
        //System.out.println("recipientEmail: " + recipientEmail);
        new MailSender().sendHtmlEmail(recipientEmail, agent.getEmail(), emailSubject, html);
    }

    
    /**
     * ****************************** WELCOME MESSAGE TO Agent EMAIL ON
     * REGISTERING ************************************************
     */
    /**
     * ******************************* SEND LOGIN DETAILS TO CUSTOMER MAIL
     * ***********************************************************
     */
    public void sendUserWelcomeMessageAndPassword(String recipentEmail, String companyEmail, String password , SystemUser user , Company company , String loginUrl) {
        String messageBody = "<!DOCTYPE html>"
                + "<html>"
                + "<head>"
                + "<title></title>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />"
                + "<style type=\"text/css\">"
                + "    /* FONTS */"
                + "    @media screen {"
                + "        @font-face {"
                + "          font-family: 'Lato';"
                + "          font-style: normal;"
                + "          font-weight: 400;"
                + "          src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');"
                + "        }"
                + "        "
                + "        @font-face {"
                + "          font-family: 'Lato';"
                + "          font-style: normal;"
                + "          font-weight: 700;"
                + "          src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');"
                + "        }"
                + "        "
                + "        @font-face {"
                + "          font-family: 'Lato';"
                + "          font-style: italic;"
                + "          font-weight: 400;"
                + "          src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');"
                + "        }"
                + "        "
                + "        @font-face {"
                + "          font-family: 'Lato';"
                + "          font-style: italic;"
                + "          font-weight: 700;"
                + "          src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');"
                + "        }"
                + "    }"
                + "    "
                + "    /* CLIENT-SPECIFIC STYLES */"
                + "    body, table, td, a { -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }"
                + "    table, td { mso-table-lspace: 0pt; mso-table-rspace: 0pt; }"
                + "    img { -ms-interpolation-mode: bicubic; }"
                + ""
                + "    /* RESET STYLES */"
                + "    img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; }"
                + "    table { border-collapse: collapse !important; }"
                + "    body { height: 100% !important; margin: 0 !important; padding: 0 !important; width: 100% !important; }"
                + ""
                + "    /* iOS BLUE LINKS */"
                + "    a[x-apple-data-detectors] {"
                + "        color: inherit !important;"
                + "        text-decoration: none !important;"
                + "        font-size: inherit !important;"
                + "        font-family: inherit !important;"
                + "        font-weight: inherit !important;"
                + "        line-height: inherit !important;"
                + "    }"
                + "    "
                + "    /* MOBILE STYLES */"
                + "    @media screen and (max-width:600px){"
                + "        h1 {"
                + "            font-size: 32px !important;"
                + "            line-height: 32px !important;"
                + "        }"
                + "    }"
                + ""
                + "    /* ANDROID CENTER FIX */"
                + "    div[style*=\"margin: 16px 0;\"] { margin: 0 !important; }"
                + "</style>"
                + "</head>"
                + "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">"
                + ""
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "    <!-- LOGO -->"
                + "    <tr>"
                + "        <td bgcolor=\"#3c8dbc\" align=\"center\">"
                + "            <!--[if (gte mso 9)|(IE)]>"
                + "            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">"
                + "            <tr>"
                + "            <td align=\"center\" valign=\"top\" width=\"600\">"
                + "            <![endif]-->"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >"
                + "                <tr>"
                + "                    <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\">   "
                + "                    </td>"
                + "                </tr>"
                + "            </table>"
                + "            <!--[if (gte mso 9)|(IE)]>"
                + "            </td>"
                + "            </tr>"
                + "            </table>"
                + "            <![endif]-->"
                + "        </td>"
                + "    </tr>"
                + "    <!-- HERO -->"
                + "    <tr>"
                + "        <td bgcolor=\"#3c8dbc\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
                + "            <!--[if (gte mso 9)|(IE)]>"
                + "            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">"
                + "            <tr>"
                + "            <td align=\"center\" valign=\"top\" width=\"600\">"
                + "            <![endif]-->"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >"
                + "                <tr>"
                + "                    <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">"
                + "                      <h1 style=\"font-size: 48px; font-weight: 400; margin: 0;\">Welcome!</h1>"
                + "                    </td>"
                + "                </tr>"
                + "            </table>"
                + "            <!--[if (gte mso 9)|(IE)]>"
                + "            </td>"
                + "            </tr>"
                + "            </table>"
                + "            <![endif]-->"
                + "        </td>"
                + "    </tr>"
                + "    <!-- COPY BLOCK -->"
                + "    <tr>"
                + "        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
                + "            <!--[if (gte mso 9)|(IE)]>"
                + "            <table align=\"center\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\">"
                + "            <tr>"
                + "            <td align=\"center\" valign=\"top\" width=\"600\">"
                + "            <![endif]-->"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >"
                + "              <!-- COPY -->"
                + "              <tr>"
                + "                <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >"
                + "                    <p>"
                + "                        Dear "+user.getFirstname() + ""
                + "                    </p>"
                + "                  <p style=\"margin: 0;\">You have been successfully registered on "+ company.getName() + " Neo Force platform. Your password is " + password + ". You may now login with your email and the password on http://"+loginUrl+".</p>"
                + "                </td>"
                + "              </tr>"
                + "              <!-- BULLETPROOF BUTTON -->"
                + "              <tr>"
                + "                <td bgcolor=\"#ffffff\" align=\"left\">"
                + "                  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                + "                    <tr>"
                + "                      <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">"
                + "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                + "                          <tr>"
                + "                              <td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#3c8dbc\"><a href=\"http://"+loginUrl+"\" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #3c8dbc; display: inline-block;\">Log In</a></td>"
                + "                          </tr>"
                + "                        </table>"
                + "                      </td>"
                + "                    </tr>"
                + "                  </table>"
                + "                </td>"
                + "              </tr>"
                + "              "
                + "              <tr>"
                + "                <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 0px 30px 40px 30px; border-radius: 0px 0px 4px 4px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >"
                + "  <p>Looking forward to doing business with you. </p>"
                + "<p style=\"margin-top: 10px;\">Thank you</p>"
                + "                    <p style=\"text-align: right\">"
                + "                           NeoForce SFA "
                + "                        </p>"
                + "                </td>"
                + "              </tr>"
                + "            </table>"
                + "            <!--[if (gte mso 9)|(IE)]>"
                + "            </td>"
                + "            </tr>"
                + "            </table>"
                + "            <![endif]-->"
                + "        </td>"
                + "    </tr>"
                + "    "
                + "</table>"
                + "</body>"
                + "</html>";

        String emailSubject = "Welcome To " + company.getName() + " NeoForce SFA Platform";
        new MailSender().sendHtmlEmail(recipentEmail, companyEmail, emailSubject, messageBody);
    }

    /**
     * ****************************** WELCOME MESSAGE TO Agent EMAIL ON
     * REGISTERING ************************************************
     */
    /**
     * ******************************* SEND LOGIN DETAILS TO CUSTOMER MAIL
     * ***********************************************************
     */
    public static void sendPasswordResetLink(String recipentEmail, String companyEmail, String token, String firstName, String companyName) {

        String messageBody = "<!DOCTYPE html>" //<editor-fold>
                + "<html>"
                + "<head>"
                + "<title></title>"
                + "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"
                + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">"
                + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />"
                + "<style type=\"text/css\">"
                + "    /* FONTS */"
                + "    @media screen {"
                + "        @font-face {"
                + "          font-family: 'Lato';"
                + "          font-style: normal;"
                + "          font-weight: 400;"
                + "          src: local('Lato Regular'), local('Lato-Regular'), url(https://fonts.gstatic.com/s/lato/v11/qIIYRU-oROkIk8vfvxw6QvesZW2xOQ-xsNqO47m55DA.woff) format('woff');"
                + "        }"
                + "        "
                + "        @font-face {"
                + "          font-family: 'Lato';"
                + "          font-style: normal;"
                + "          font-weight: 700;"
                + "          src: local('Lato Bold'), local('Lato-Bold'), url(https://fonts.gstatic.com/s/lato/v11/qdgUG4U09HnJwhYI-uK18wLUuEpTyoUstqEm5AMlJo4.woff) format('woff');"
                + "        }"
                + "        "
                + "        @font-face {"
                + "          font-family: 'Lato';"
                + "          font-style: italic;"
                + "          font-weight: 400;"
                + "          src: local('Lato Italic'), local('Lato-Italic'), url(https://fonts.gstatic.com/s/lato/v11/RYyZNoeFgb0l7W3Vu1aSWOvvDin1pK8aKteLpeZ5c0A.woff) format('woff');"
                + "        }"
                + "        "
                + "        @font-face {"
                + "          font-family: 'Lato';"
                + "          font-style: italic;"
                + "          font-weight: 700;"
                + "          src: local('Lato Bold Italic'), local('Lato-BoldItalic'), url(https://fonts.gstatic.com/s/lato/v11/HkF_qI1x_noxlxhrhMQYELO3LdcAZYWl9Si6vvxL-qU.woff) format('woff');"
                + "        }"
                + "    }"
                + "    "
                + "    /* CLIENT-SPECIFIC STYLES */"
                + "    body, table, td, a { -webkit-text-size-adjust: 100%; -ms-text-size-adjust: 100%; }"
                + "    table, td { mso-table-lspace: 0pt; mso-table-rspace: 0pt; }"
                + "    img { -ms-interpolation-mode: bicubic; }"
                + ""
                + "    /* RESET STYLES */"
                + "    img { border: 0; height: auto; line-height: 100%; outline: none; text-decoration: none; }"
                + "    table { border-collapse: collapse !important; }"
                + "    body { height: 100% !important; margin: 0 !important; padding: 0 !important; width: 100% !important; }"
                + ""
                + "    /* iOS BLUE LINKS */"
                + "    a[x-apple-data-detectors] {"
                + "        color: inherit !important;"
                + "        text-decoration: none !important;"
                + "        font-size: inherit !important;"
                + "        font-family: inherit !important;"
                + "        font-weight: inherit !important;"
                + "        line-height: inherit !important;"
                + "    }"
                + "    "
                + "    /* MOBILE STYLES */"
                + "    @media screen and (max-width:600px){"
                + "        h1 {"
                + "            font-size: 32px !important;"
                + "            line-height: 32px !important;"
                + "        }"
                + "    }"
                + ""
                + "    /* ANDROID CENTER FIX */"
                + "    div[style*=\"margin: 16px 0;\"] { margin: 0 !important; }"
                + "</style>"
                + "</head>"
                + "<body style=\"background-color: #f4f4f4; margin: 0 !important; padding: 0 !important;\">"
                + ""
                + "<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">"
                + "    <!-- LOGO -->"
                + "    <tr>"
                + "        <td bgcolor=\"#7c72dc\" align=\"center\">"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >"
                + "                <tr>"
                + "                    <td align=\"center\" valign=\"top\" style=\"padding: 40px 10px 40px 10px;\">"
                + "                       "
                + "                    </td>"
                + "                </tr>"
                + "            </table>"
                + "        </td>"
                + "    </tr>"
                + "    <!-- HERO -->"
                + "    <tr>"
                + "        <td bgcolor=\"#7c72dc\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >"
                + "                <tr>"
                + "                    <td bgcolor=\"#ffffff\" align=\"center\" valign=\"top\" style=\"padding: 40px 20px 20px 20px; border-radius: 4px 4px 0px 0px; color: #111111; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 48px; font-weight: 400; letter-spacing: 4px; line-height: 48px;\">"
                + "                      <h1 style=\"font-size: 48px; font-weight: 400; margin: 0;\">Trouble signing in?</h1>"
                + "                    </td>"
                + "                </tr>"
                + "            </table>"
                + "        </td>"
                + "    </tr>"
                + "    <!-- COPY BLOCK -->"
                + "    <tr>"
                + "        <td bgcolor=\"#f4f4f4\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >"
                + "              <!-- COPY -->"
                + "              <tr>"
                + "                <td bgcolor=\"#ffffff\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #666666; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >"
                + "                  <p style=\"margin: 0;\">Dear " + firstName + ","
                + "You are getting this email because you have initiated a password reset process on " + companyName + " NeoForce SFA Platform."
                + " To complete the password reset process, click the button below</p>"
                + "                </td>"
                + "              </tr>"
                + "              <!-- BULLETPROOF BUTTON -->"
                + "              <tr>"
                + "                <td bgcolor=\"#ffffff\" align=\"left\">"
                + "                  <table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                + "                    <tr>"
                + "                      <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 20px 30px 60px 30px;\">"
                + "                        <table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">"
                + "                          <tr>"
                + "                              <td align=\"center\" style=\"border-radius: 3px;\" bgcolor=\"#7c72dc\"><a href=\"" + token + "\" target=\"_blank\" style=\"font-size: 20px; font-family: Helvetica, Arial, sans-serif; color: #ffffff; text-decoration: none; color: #ffffff; text-decoration: none; padding: 15px 25px; border-radius: 2px; border: 1px solid #7c72dc; display: inline-block;\">Reset Password</a></td>"
                + "                          </tr>"
                + "                        </table>"
                + "                      </td>"
                + "                    </tr>"
                + "                  </table>"
                + "                </td>"
                + "              </tr>"
                + "            </table>"
                + "        </td>"
                + "    </tr>"
                 + "    <tr>"
                + "        <td bgcolor=\"#ffffff\" align=\"center\" style=\"padding: 0px 10px 0px 10px;\">"
                + "            <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"max-width: 600px;\" >"
                + "              <!-- COPY -->"
                + "              <tr>"
                + "                <td bgcolor=\"#a6a6a6\" align=\"left\" style=\"padding: 20px 30px 40px 30px; color: #ffffff; font-family: 'Lato', Helvetica, Arial, sans-serif; font-size: 18px; font-weight: 400; line-height: 25px;\" >"
                + "                  <p style=\"margin: 0;\">If the link does not work, copy and paste the url below into your browser address bar.</p>"
                + "<p> " + token+ " </p>"
                +"<p>Please note that this link expires in 24 hours.</p>"
                +"<p>Regards</p>"
                + "                </td>"
                + "              </tr>"
                + "            </table>"
                + "        </td>"
                + "    </tr>"
                + "        </td>"
                + "    </tr>"
                + "</table>"
                + " "
                + "</body>"
                + "</html>";
        //</editor-fold>

        String emailSubject = "NeoForce - Password Reset";
        new MailSender().sendHtmlEmail(recipentEmail, companyEmail, emailSubject, messageBody);
    }

}
