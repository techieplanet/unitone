/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.helpers.AccountManager;
import com.tp.neo.controller.helpers.CompanyAccountHelper;
import com.tp.neo.controller.helpers.EmailHelper;
import com.tp.neo.controller.helpers.OrderItemHelper;
import com.tp.neo.controller.helpers.OrderItemObjectsList;
import com.tp.neo.controller.helpers.OrderManager;
import com.tp.neo.controller.helpers.PDFHelper;
import com.tp.neo.exception.SystemLogger;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Account;
import com.tp.neo.model.Agent;
import com.tp.neo.model.AgentProspect;
import com.tp.neo.model.Company;
import com.tp.neo.model.Customer;
import com.tp.neo.model.CustomerAgent;
import com.tp.neo.model.Document;
import com.tp.neo.model.DocumentType;
import com.tp.neo.model.Lodgement;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.ProductOrder;
import com.tp.neo.model.utils.AuthManager;
import com.tp.neo.model.utils.FileUploader;
import com.tp.neo.model.utils.TrailableManager;
import com.tp.neo.service.CountryService;
import com.tp.neo.service.DocumentService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import javax.xml.bind.PropertyException;

/**
 *
 * @author John
 */
@WebServlet(name = "Customer", urlPatterns =
{
    "/Customer"
})
@MultipartConfig
public class CustomerController extends AppController {

    private static String INSERT_OR_EDIT = "/user.jsp";
    private static String CUSTOMER_ADMIN = "/views/customer/admin.jsp";
    private static String CUSTOMER_NEW = "/views/customer/add.jsp";
    private static String CUSTOMER_NEW_PROSPECT = "/views/customer/prospective_customer.jsp";
    private static String LIST_PROSPECT = "/views/customer/list_prospective_customer.jsp";
    private static String CUSTOMER_COMPLETED_PAYMENT = "/views/customer/completed_payment.jsp";
    private static String CUSTOMER_CURRENT_PAYING = "/views/customer/current_paying.jsp";
    private static String CUSTOMER_PROFILE = "/views/customer/profile.jsp";
    private final String ORDER_NOTIFICATION_ROUTE = "/Order?action=notification&id=";
    private Customer customer = new Customer();
    private final static Logger LOGGER
            = Logger.getLogger(Customer.class.getCanonicalName());

    private HashMap<String, String> errorMessages = new HashMap<String, String>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        try
        {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet CustomerController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet CustomerController at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        } finally
        {
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "";

        //Check if request is XMLHttpRequest
        if (action.equals("lodgement_invoice"))
        {
            if (request.getParameter("pdf") != null)
            {
                new PDFHelper().sendPDFInvoice(request, response);
                return;
            }
        }
        if (isAjaxRequest)
        {

            if (action.equalsIgnoreCase("email_validation"))
            {

                validateEmail(request, response);
                return;
            }
            if (action.equalsIgnoreCase("customer_lodgements"))
            {

                getCustomerLodgments(request, response, true);
                return;
            }

        }

        if (super.hasActiveUserSession(request, response))
        {
            if (super.hasActionPermission(new Customer().getPermissionName(action), request, response))
            {
                processGetRequest(request, response);
            }
            else
            {
                super.errorPageHandler("forbidden", request, response);
            }
        }

    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        String requestFrom = request.getParameter("from") != null ? request.getParameter("from") : "";

        /**
         * Check if the Request is coming from a customer registration page, No
         * need to check permission.
         *
         */
        if (!requestFrom.equals(""))
        {
            processInsertRequest(request, response);
        }

        //Ajax Post Request for Password Change
        if (action.equalsIgnoreCase("password_change"))
        {

            changeCustomerPassword(request, response);
            return;
        }

        if (super.hasActiveUserSession(request, response))
        {
            if (super.hasActionPermission(new Customer().getPermissionName(action), request, response))
            {
                processPostRequest(request, response);
            }
            else
            {
                super.errorPageHandler("forbidden", request, response);
            }
        }
    }

    /*TP: Processes the post requests in general*/
    protected void processPostRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        //  String viewFile = AGENTS_ADMIN;
        Customer customer = new Customer();
        try
        {
            if (request.getParameter("action").equals("update"))
            { //edit mode
                this.processUpdateRequest(request, response);
                return;
            }
            else if (request.getParameter("action").equals("new_prospect"))
            {

                addNewProspect(request, response);
                return;
            }
            else if (request.getParameter("action").equals("edit_prospect"))
            {

                updateProspect(request, response);
                return;
            }
            else
            {
                this.processInsertRequest(request, response);
            }

        } catch (Exception e)
        {
            e.printStackTrace();
            ////System.out.println("inside catch area: " + e.getMessage());
            request.setAttribute("errors", errorMessages);
            SystemLogger.logSystemIssue("Customer", gson.toJson(customer), e.getMessage());
        }

    }

    /*TP: Processes every insert request of request type POST*/
    protected void processInsertRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        errorMessages.clear();

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        final EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        String viewFile = CUSTOMER_NEW;
        final Customer customer = new Customer();

        Company company = em.find(Company.class, 1);
        String customerFileName = "";
        String customerKinFileName = "";

        request.setAttribute("success", false);
        Gson gson = new GsonBuilder().create();

        String requestFrom = request.getAttribute("from") != null ? request.getAttribute("from").toString() : "";

        final SystemUser user = sessionUser;

        try
        {

            validate(customer, request);

           /* //Remember to clear this when done
            //request.getParameterMap().forEach((String attribute, String[] values) ->
            {
                //System.out.print(attribute + "  ");
                for (String value : values)
                {
                    //System.out.print(value + "  ");
                }
                //System.out.println("");
            });*/

            long unixTime = System.currentTimeMillis() / 1000L;

            Agent agent = null;

            if (requestFrom.equalsIgnoreCase("customerRegistrationController"))
            {

                //Assign default companies Agent to the customer
                Query query = em.createNamedQuery("Agent.findByFullname");
                query.setParameter("firstname", "company");
                query.setParameter("lastname", "company");
                agent = (Agent) query.getSingleResult();
            }
            else if (user.getSystemUserTypeId() == 2)
            { //agent
                agent = em.find(Agent.class, user.getSystemUserId());
            }
            else
            {
                long agentId = Long.parseLong(request.getParameter("agent_id"));
                agent = em.find(Agent.class, agentId);
            }

            //customer Personal information
            customer.setTitle(request.getParameter("customerTitle"));
            customer.setFirstname(request.getParameter("customerFirstname"));
            customer.setLastname(request.getParameter("customerLastname"));
            customer.setEmail(request.getParameter("customerEmail").toLowerCase());
            customer.setMiddlename(request.getParameter("customerMiddlename"));
            customer.setGender(request.getParameter("customerGender"));
            customer.setMaritalStatus(request.getParameter("customerMaritalStatus"));

            //LocalDate lDate = LocalDate.parse(request.getParameter("customerDateOfBirth"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date tDate = null;
            try
            {
                tDate = myFormat.parse(request.getParameter("customerDateOfBirth"));
            } catch (ParseException ex)
            {
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }

            customer.setDateOfBirth(tDate);

            //customer.setPassword(AuthManager.getSaltedHash(request.getParameter("customerPassword")));
            //password handling: Assumes only agents and admins will create customers for now.
            String initPass = AuthManager.generateInitialPassword();  //randomly generated password
            customer.setPassword(AuthManager.getSaltedHash(initPass));  //hash the inital password
            /*
            String password = request.getParameter("customerPassword") != null && !((String)request.getParameter("customerPassword")).isEmpty() ?
                               request.getParameter("customerPassword") : "secret";
            customer.setPassword(AuthManager.getSaltedHash(password)); */

            //Customer Contact Location
            customer.setStreet(request.getParameter("customerStreet"));
            customer.setCity(request.getParameter("customerCity"));
            customer.setState(request.getParameter("customerState"));
            customer.setCountry(request.getParameter("customerCountry"));
            customer.setPostalAddress(request.getParameter("customerPostalAddress"));
            customer.setPhone(request.getParameter("customerPhone"));
            customer.setOtherPhone(request.getParameter("customerOtherPhone"));

            //Customer Work Information
            customer.setOccupation(request.getParameter("customerOccupation"));
            customer.setEmployer(request.getParameter("customerEmployer"));
            customer.setOfficePhone(request.getParameter("customerOfficePhone"));

            //Office Address 
            customer.setOfficeStreet(request.getParameter("customerOfficeStreet"));
            customer.setOfficeCity(request.getParameter("customerOfficeCity"));
            customer.setOfficeState(request.getParameter("customerOfficeState"));
            customer.setOfficeCountry(request.getParameter("customerOfficeCountry"));

            //Employer Address
            customer.setEmployerStreet(request.getParameter("customerEmployerStreet"));
            customer.setEmployerCity(request.getParameter("customerEmployerCity"));
            customer.setEmployerState(request.getParameter("customerEmployerState"));
            customer.setEmployerCountry(request.getParameter("customerEmployerCountry"));

            //customer Kin Information
            customer.setKinName(request.getParameter("customerKinName"));
            customer.setKinPhone(request.getParameter("customerKinPhone"));
            customer.setKinAddress(request.getParameter("customerKinAddress"));
            customer.setKinRelationship(request.getParameter("customerKinRelationship"));
            customer.setKinEmail(request.getParameter("customerKinEmail"));

            //Banker Information
            customer.setBanker(request.getParameter("customerBanker"));
            customer.setAccountName(request.getParameter("customerAccountName"));
            customer.setAccountNumber(request.getParameter("customerAccountNumber"));

            if (requestFrom.equalsIgnoreCase("customerRegistrationController"))
            {
                //Assign default created by user
                //customer.setCreatedBy(10);
            }
            else
            {
                customer.setCreatedBy(user.getSystemUserId());
            }
            customer.setCreatedDate(getDateTime().getTime());

            customer.setAgent(agent);

            new TrailableManager(customer).registerInsertTrailInfo((long) 1);
            customer.setDeleted((short) 0);

            //System.out.println("*********************************************");
            //System.out.println("Path : " + request.getPart("customerPhoto").getSubmittedFileName());
            //System.out.println("*********************************************");

            /*
              //handle the pictures now
            if (customerFileName != null && !customerFileName.equals("")) {
                Part filePart = request.getPart("customerPhoto");
                String saveName = "customer_" + unixTime + "." + FileUploader.getSubmittedFileExtension(filePart);
                customer.setPhotoPath(saveName);
                customerFileName = saveName;
                new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true).uploadFile(filePart, "customer", saveName, true);
            } else {
                customer.setPhotoPath("default");
            }

            if (customerKinFileName != null && !customerKinFileName.equals("")) {
                Part filePart = request.getPart("customerKinPhoto");
                String saveName = "customerKin_" + unixTime + "." + FileUploader.getSubmittedFileExtension(filePart);
                customer.setKinPhotoPath(saveName);
                customerKinFileName = saveName;
                new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true).uploadFile(filePart, "customerKin", saveName, true);
            } else {
                customer.setKinPhotoPath("default");
            }
            
             */
            customer.setPhotoPath("default");
            customer.setKinPhotoPath("default");
            //persist only on save mode
            if(!em.getTransaction().isActive())
             em.getTransaction().begin();
            log("Will now persist the customer");
            em.persist(customer);
            em.flush();

            //New Update Handles The Images As Documents
            //We are going to put all the image into an Hash map specifying the title of the Image also
            Map<String, Part> documents = new HashMap<>();
            documents.put("customer_", request.getPart("customerPhoto"));
            documents.put("customerKin_", request.getPart("customerKinPhoto"));
            documents.put("customer_ID_Card_", request.getPart("customerPhotoID"));
            documents.put("customer_Bank_Standing_Order_", request.getPart("customerBankStandingOrder"));

            em.refresh(customer);
            FileUploader fUpload = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);

            documents.forEach((String typeAlias, Part path) ->
            {
                DocumentType doctype = (DocumentType) em
                        .createNamedQuery("DocumentType.findByTypeAlias")
                        .setParameter("typeAlias", typeAlias)
                        .getSingleResult();
                Document doc = new Document();

                String saveName = typeAlias + unixTime + customer.getCustomerId() + "." + FileUploader.getSubmittedFileExtension(path);

                String subdir = "customers";
                String dir = "";

                switch (typeAlias)
                {
                    case "customer_":
                        customer.setPhotoPath(saveName);
                        break;

                    case "customerKin_":
                        customer.setKinPhotoPath(saveName);
                        subdir = subdir + fUpload.getFileSeparator() + "customerkins";
                        dir = "/customerkins";
                        break;

                    case "customer_ID_Card_":
                        subdir = subdir + fUpload.getFileSeparator() + "ids";
                        dir = "/ids";
                        break;

                    case "customer_Bank_Standing_Order_":
                        subdir = subdir + fUpload.getFileSeparator() + "bankstandingorders";
                        dir = "/bankstandingorders";
                        break;
                }

                doc.setDocTypeId(doctype);
                doc.setOwnerId(BigInteger.valueOf(customer.getCustomerId()));
                doc.setOwnerTypeId(customer.getSystemUserTypeId());
                doc.setPath("customers" + dir + "/" + saveName);
                doc.setCreatedDate(getDateTime().getTime());

                em.persist(doctype);
                em.persist(doc);

                try
                {
                    fUpload.uploadFile(path, subdir, saveName, true);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            });

            customerFileName = customer.getPhotoPath();
            customerKinFileName = customer.getKinPhotoPath();
            em.persist(customer);
            em.flush();

            Account account = new AccountManager().createCustomerAccount(customer);
            em.persist(account);
            em.persist(customer);
            em.refresh(customer);
            customer.setAccount(account);

            em.flush();
//         
            CustomerAgent customerAgent = new CustomerAgent();
            customerAgent.setAgentId(agent);
            customerAgent.setCustomer(customer);

            log("Will now persistr the customerAgent");
            em.persist(customerAgent);
            
            OrderManager orderManager = new OrderManager(sessionUser);

            OrderItemObjectsList saleItemObjectList = orderManager.getCartData(request.getParameter("cartDataJson").toString());
            Map<String ,String> requestParameters = getRequestParameters(request);

            List<OrderItem> orderItem = orderManager.prepareOrderItem(saleItemObjectList, agent);
            Lodgement lodgement = orderManager.prepareLodgement(requestParameters, agent);
            
            StringBuilder eMsg = new StringBuilder();
            boolean valid = validateOrder(orderItem , lodgement , eMsg);
            //Remove customer account if the Order is not valid
            if(!valid)
            {
                errorMessages.put("error2", eMsg.toString());
                em.getTransaction().rollback();
                em.close();
                emf.close();
                throw new PropertyException("Error while validating Customer Order");
            }
            
            //done With Validation , We can Now Proceed
            String Url = request.getServerName() + "/" + request.getContextPath();
            new EmailHelper().sendUserWelcomeMessageAndPassword(customer.getEmail(), company.getEmail(), initPass, customer, company, Url);
            em.getTransaction().commit();
            em.close();
            emf.close();

            lodgement.setCustomer(customer);

            if (requestFrom.equalsIgnoreCase("customerRegistrationController"))
            {
                //user = (SystemUser) customer;
            }

            ProductOrder productOrder = orderManager.processOrder(customer, lodgement, orderItem, request.getContextPath());

            if (productOrder != null)
            {
                if (productOrder.getId() != null)
                {
                }
                else
                {
                    //Delete Customer and Customer Account
                }
            }
            else
            {
                //Delete Customer and Customer
            }

            //Check if a customer is the one registering him/her self 
            if (requestFrom.equalsIgnoreCase("customerRegistrationController"))
            {
                if (lodgement.getPaymentMode() == 2)
                {

                    Date date = lodgement.getCreatedDate();
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
                    String dateString = sdf.format(date);

                    List<LodgementItem> LItems = (List) lodgement.getLodgementItemCollection();

                    double vat = 0.00;
                    double gateWayCharge = 0.00;
                    Map map = getInvoicData(LItems, vat, gateWayCharge);

                    viewFile = "/views/customer/gateway.jsp";
                    request.getSession().setAttribute("productOrderInvoice", productOrder);
                    request.getSession().setAttribute("orderItemInvoice", LItems);
                    request.getSession().setAttribute("transactionDate", dateString);
                    request.getSession().setAttribute("customerInvoice", customer);
                    request.getSession().setAttribute("totalInvoice", (Double) map.get("total"));
                    request.getSession().setAttribute("grandTotalInvoice", (Double) map.get("grandTotal"));
                    request.getSession().setAttribute("vatInvoice", vat);
                    request.getSession().setAttribute("gatewayChargeInvoice", gateWayCharge);

                }
                else
                {
                    viewFile = "/views/customer/success.jsp";
                    request.setAttribute("customer", customer);
                }
            }
            else
            {

                //Registration is done by agent/admin, so forward them to customer profile
                //viewFile = "Customer?action=profile&customerId=" + customer.getCustomerId();
                //response.sendRedirect(viewFile);
                //return;
                //request.setAttribute("action","profile");
                //request.setAttribute("customerId", customer.getCustomerId());
                viewFile = CUSTOMER_NEW;

                request.setAttribute("customer", customer);
                request.setAttribute("userType", sessionUser.getSystemUserTypeId());
                request.setAttribute("countries", CountryService.getCountryList());
                request.setAttribute("action", "edit");
                request.setAttribute("registrationSuccess", true);
            }

            request.setAttribute("customerKinPhotoHidden", customerKinFileName);
            request.setAttribute("customerPhotoHidden", customerFileName);
            request.setAttribute("customers", listCustomers());
            //request.setAttribute("success", true);
            request.setAttribute("action", "edit");
            request.setAttribute("registrationSuccess", true);
            request.setAttribute("customer", customer);

            if (sessionUser == null)
            {
                request.setAttribute("userType", 3);
            }
            else
            {
                request.setAttribute("userType", sessionUser.getSystemUserTypeId());
            }

        } catch (PropertyException err)
        {
            if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            err.printStackTrace();
            viewFile = "/views/customer/add.jsp";

            if (requestFrom.equalsIgnoreCase("customerRegistrationController"))
            {
                viewFile = "/views/customer/registration.jsp";
            }

            request.setAttribute("customerKinPhotoHidden", customerKinFileName);
            request.setAttribute("customerPhotoHidden", customerFileName);
            request.setAttribute("customer", customer);
            request.setAttribute("errors", errorMessages);
            request.setAttribute("projects", new ProjectController().listProjects());
            request.setAttribute("userTypeId", userType);
            request.setAttribute("userType", sessionUser.getSystemUserId());
            request.setAttribute("agents", new AgentController().listAgents());
            request.setAttribute("action", "new");
            request.setAttribute("companyAccount", CompanyAccountHelper.getCompanyAccounts());
            request.setAttribute("countries", CountryService.getCountryList());

            SystemLogger.logSystemIssue("Customer", /*gson.toJson(customer)*/ "Process Insert Request", err.getMessage());
        } catch (RollbackException rollExcept)
        {

            em.getTransaction().rollback();

        } catch (Exception e)
        {
             if(em.getTransaction().isActive())
                em.getTransaction().rollback();
            e.printStackTrace();
            viewFile = "/views/customer/add.jsp";

            if (requestFrom.equalsIgnoreCase("customerRegistrationController"))
            {
                viewFile = "/views/customer/registration.jsp";
            }

            request.setAttribute("customerKinPhotoHidden", customerKinFileName);
            request.setAttribute("customerPhotoHidden", customerFileName);
            request.setAttribute("customer", customer);
            errorMessages.put("fatal error", "One or more Fields are invalid");
            request.setAttribute("errors", errorMessages);
            request.setAttribute("projects", new ProjectController().listProjects());
            request.setAttribute("userTypeId", userType);
            request.setAttribute("userType", sessionUser.getSystemUserId());
            request.setAttribute("agents", new AgentController().listAgents());
            request.setAttribute("action", "new");
            request.setAttribute("companyAccount", CompanyAccountHelper.getCompanyAccounts());
            request.setAttribute("countries", CountryService.getCountryList());
            SystemLogger.logSystemIssue("Customer", /*customer.getCustomerId().toString()*/ "Process Insert Request", e.getMessage());
        }

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);
    }

    /*TP: Processes every update request of request type POST*/
    protected void processUpdateRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        Long id = Long.parseLong(request.getParameter("customer_id"));
        Customer customer = em.find(Customer.class, id);
        try
        {
            errorMessages.clear();

            //validate(customer, request);
            final SystemUser user = sessionUser;
            em.getTransaction().begin();
            long unixTime = System.currentTimeMillis() / 1000L;

            //customer Personal information
            customer.setTitle(request.getParameter("customerTitle"));
            customer.setFirstname(request.getParameter("customerFirstname"));
            customer.setLastname(request.getParameter("customerLastname"));
            customer.setEmail(request.getParameter("customerEmail").toLowerCase());
            customer.setMiddlename(request.getParameter("customerMiddlename"));
            customer.setGender(request.getParameter("customerGender"));
            customer.setMaritalStatus(request.getParameter("customerMaritalStatus"));

            //LocalDate lDate = LocalDate.parse(request.getParameter("customerDateOfBirth"), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            SimpleDateFormat myFormat = new SimpleDateFormat("dd/MM/yyyy");

            Date tDate = null;
            try
            {
                tDate = myFormat.parse(request.getParameter("customerDateOfBirth"));
            } catch (ParseException ex)
            {
                myFormat = new SimpleDateFormat("EEE, d MMM yyyy");
                try
                {
                    tDate = myFormat.parse(request.getParameter("customerDateOfBirth"));
                } catch (ParseException e)
                {
                    Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, e);
                }
                Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
            }

            customer.setDateOfBirth(tDate);

            //Customer Contact Location
            customer.setStreet(request.getParameter("customerStreet"));
            customer.setCity(request.getParameter("customerCity"));
            customer.setState(request.getParameter("customerState"));
            customer.setCountry(request.getParameter("customerCountry"));
            customer.setPostalAddress(request.getParameter("customerPostalAddress"));
            customer.setPhone(request.getParameter("customerPhone"));
            customer.setOtherPhone(request.getParameter("customerOtherPhone"));

            //Customer Work Information
            customer.setOccupation(request.getParameter("customerOccupation"));
            customer.setEmployer(request.getParameter("customerEmployer"));
            customer.setOfficePhone(request.getParameter("customerOfficePhone"));

            //Office Address 
            customer.setOfficeStreet(request.getParameter("customerOfficeStreet"));
            customer.setOfficeCity(request.getParameter("customerOfficeCity"));
            customer.setOfficeState(request.getParameter("customerOfficeState"));
            customer.setOfficeCountry(request.getParameter("customerOfficeCountry"));

            //Employer Address
            customer.setEmployerStreet(request.getParameter("customerEmployerStreet"));
            customer.setEmployerCity(request.getParameter("customerEmployerCity"));
            customer.setEmployerState(request.getParameter("customerEmployerState"));
            customer.setEmployerCountry(request.getParameter("customerEmployerCountry"));

            //customer Kin Information
            customer.setKinName(request.getParameter("customerKinName"));
            customer.setKinPhone(request.getParameter("customerKinPhone"));
            customer.setKinAddress(request.getParameter("customerKinAddress"));
            customer.setKinRelationship(request.getParameter("customerKinRelationship"));
            customer.setKinEmail(request.getParameter("customerKinEmail"));

            //Banker Information
            customer.setBanker(request.getParameter("customerBanker"));
            customer.setAccountName(request.getParameter("customerAccountName"));
            customer.setAccountNumber(request.getParameter("customerAccountNumber"));

            customer.setModifiedBy(user.getSystemUserId());

            customer.setModifiedDate(getDateTime().getTime());

            new TrailableManager(customer).registerInsertTrailInfo((long) 1);
            customer.setDeleted((short) 0);

            //New Update Handles The Images As Documents
            //We are going to put all the image into an Hash map specifying the title of the Image also
            Map<String, Part> documents = new HashMap<>();
            documents.put("customer_", request.getPart("customerPhoto"));
            documents.put("customerKin_", request.getPart("customerKinPhoto"));
            documents.put("customer_ID_Card_", request.getPart("customerPhotoID"));
            documents.put("customer_Bank_Standing_Order_", request.getPart("customerBankStandingOrder"));

            FileUploader fUpload = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), true);

            documents.forEach((String typeAlias, Part path) ->
            {

                if (path != null && !path.getSubmittedFileName().trim().isEmpty())
                {
                    DocumentType doctype = (DocumentType) em
                            .createNamedQuery("DocumentType.findByTypeAlias")
                            .setParameter("typeAlias", typeAlias)
                            .getSingleResult();
                    Document doc = new Document();

                    //Remove previous Document of This Type
                    //formal document
                    List<Document> fDoc = em.createNamedQuery("Document.findByOwnerIdDoctypeIdOwnerTypeId")
                            .setParameter("ownerID", customer.getCustomerId())
                            .setParameter("docTypeId", doctype)
                            .setParameter("ownerTypeId", customer.getSystemUserTypeId())
                            .getResultList();
                    if (fDoc != null && !fDoc.isEmpty())
                    {
                        for (Document tDoc : fDoc)
                        {
                            em.remove(tDoc);
                        }
                    }

                    String saveName = typeAlias + unixTime + customer.getCustomerId() + "." + FileUploader.getSubmittedFileExtension(path);

                    String subdir = "customers";
                    String dir = "";

                    switch (typeAlias)
                    {
                        case "customer_":
                            customer.setPhotoPath(saveName);
                            break;

                        case "customerKin_":
                            customer.setKinPhotoPath(saveName);
                            subdir = subdir + fUpload.getFileSeparator() + "customerkins";
                            dir = "/customerkins";
                            break;

                        case "customer_ID_Card_":
                            subdir = subdir + fUpload.getFileSeparator() + "ids";
                            dir = "/ids";
                            break;

                        case "customer_Bank_Standing_Order_":
                            subdir = subdir + fUpload.getFileSeparator() + "bankstandingorders";
                            dir = "/bankstandingorders";
                            break;
                    }

                    doc.setDocTypeId(doctype);
                    doc.setOwnerId(BigInteger.valueOf(customer.getCustomerId()));
                    doc.setOwnerTypeId(customer.getSystemUserTypeId());
                    doc.setPath("customers" + dir + "/" + saveName);
                    doc.setCreatedDate(getDateTime().getTime());

                    em.persist(doctype);
                    em.persist(doc);

                    try
                    {
                        fUpload.uploadFile(path, subdir, saveName, true);
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }
            });

            em.persist(customer);
            em.merge(customer);
            em.getTransaction().commit();
            em.close();
            emf.close();

            //request.getRequestDispatcher("/views/customer/update.jsp").forward(request, response);
            String viewFile = CUSTOMER_NEW;

            request.setAttribute("customer", customer);
            request.setAttribute("userType", sessionUser.getSystemUserTypeId());
            request.setAttribute("countries", CountryService.getCountryList());
            request.setAttribute("action", "edit");
            request.setAttribute("success", true);
            request.getRequestDispatcher(viewFile).forward(request, response);
        }//<editor-fold>catch (PropertyException err) {
        //   err.printStackTrace();
        //  String  viewFile = CUSTOMER_NEW;
        // if (requestFrom.equalsIgnoreCase("customerRegistrationController")) {
        //      viewFile = "/views/customer/registration.jsp";
        //  }
        //            request.setAttribute("customer", customer);
        //            request.setAttribute("errors", errorMessages);
        //            request.setAttribute("userType",sessionUser.getSystemUserTypeId());
        //            request.setAttribute("countries", CountryService.getCountryList());
        //
        //            request.setAttribute("action", "edit");
        //            
        //            request.getRequestDispatcher(viewFile).forward(request, response);
        //            SystemLogger.logSystemIssue("Customer", /*gson.toJson(customer)*/"Process update Request", err.getMessage());
        //        }</editor-fold>
        catch (Exception ex)
        {

            ex.printStackTrace();
            Enumeration it = request.getParameterNames();
            Map<String, String> map = new HashMap();
            while (it.hasMoreElements())
            {
                map.put(it.nextElement().toString(), request.getParameter(it.nextElement().toString()));
            }
            request.setAttribute("data", map);
            request.setAttribute("errors", errorMessages);
            String viewFile = CUSTOMER_NEW;
            request.setAttribute("customer", customer);
            request.setAttribute("userType", sessionUser.getSystemUserTypeId());
            request.setAttribute("countries", CountryService.getCountryList());
            request.getRequestDispatcher(viewFile).forward(request, response);

            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void validateCustomerUpdate(HttpServletRequest request) throws IOException, ServletException, PropertyException {

        errorMessages.clear();

        String lname = request.getParameter("lname");
        String fname = request.getParameter("fname");
        String mname = request.getParameter("mname");
        String email = request.getParameter("email");

        Part customerPhoto = request.getPart("customerPhoto");
        Part customerKinPhoto = request.getPart("customerKinPhoto");

        String street = request.getParameter("street");
        String city = request.getParameter("city");
        String state = request.getParameter("state");
        String phone = request.getParameter("phone");

        String customerKinNames = request.getParameter("customerKinNames");
        String customerKinPhone = request.getParameter("customerKinPhone");
        String customerKinAddress = request.getParameter("customerKinAddress");

        if (lname.equalsIgnoreCase(""))
        {
            errorMessages.put("error1", "Last Name is required");
        }
        if (fname.equalsIgnoreCase(""))
        {
            errorMessages.put("erorr2", "First Name is required");
        }
        if (email.equalsIgnoreCase(""))
        {
            errorMessages.put("error3", "Email is required");
        }
        if (street.equalsIgnoreCase(""))
        {
            errorMessages.put("error4", "Street is required");
        }
        if (city.equalsIgnoreCase(""))
        {
            errorMessages.put("error5", "City is required");
        }
        if (state.equalsIgnoreCase(""))
        {
            errorMessages.put("error6", "State is required");
        }
        if (phone.equalsIgnoreCase(""))
        {
            errorMessages.put("error7", "Phone is required");
        }
        if (customerKinNames.equalsIgnoreCase(""))
        {
            errorMessages.put("error8", "Customer Kin Name is required");
        }
        if (customerKinPhone.equalsIgnoreCase(""))
        {
            errorMessages.put("error9", "Customer Kin Phone No is required");
        }
        if (customerKinAddress.equalsIgnoreCase(""))
        {
            errorMessages.put("error10", "Customer Kin Address is required");
        }

        if (errorMessages.size() > 0)
        {
            throw new PropertyException("Validation Error");
        }
    }

    protected void processGetRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        String viewFile = CUSTOMER_ADMIN;
        String action = request.getParameter("action") != null ? request.getParameter("action") : "";
        ProjectController project = new ProjectController();
        //Project listprojects = project.listProjects();
        int userType = sessionUser.getSystemUserTypeId();

        AgentController agent = new AgentController();

        if (action.equalsIgnoreCase("new"))
        {
            viewFile = CUSTOMER_NEW;
            request.setAttribute("userTypeId", userType);
            request.setAttribute("userType", sessionUser.getSystemUserId());
            request.setAttribute("agents", agent.listAgents());
            request.setAttribute("projects", project.listProjects());
            request.setAttribute("action", "new");
            request.setAttribute("companyAccount", CompanyAccountHelper.getCompanyAccounts());

            request.setAttribute("sideNav", "Customer");
            request.setAttribute("sideNavAction", action);
            request.setAttribute("countries", CountryService.getCountryList());

            RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
            dispatcher.forward(request, response);

            return;
        }
        else if (action.equalsIgnoreCase("new_prospect"))
        {

            viewFile = CUSTOMER_NEW_PROSPECT;

        }
        else if (action.equalsIgnoreCase("list_prospects"))
        {

            viewFile = LIST_PROSPECT;

            request.setAttribute("prospects", getProspectiveCustomers(request));

        }
        else if (action.equalsIgnoreCase("edit_prospect"))
        {
            viewFile = "/views/customer/edit_prospect.jsp";

            request.setAttribute("prospect", getProspect(request));
            action = "lis_prospects";
        }
        else if (action.equalsIgnoreCase("delete"))
        {

            this.delete(Integer.parseInt(request.getParameter("id")));
            action = "";
        }
        else if (action.equalsIgnoreCase("edit"))
        {
            //viewFile = "/views/customer/update.jsp";
            viewFile = CUSTOMER_NEW;

            int id = Integer.parseInt(request.getParameter("customerId"));
            Query jpqlQuery = em.createNamedQuery("Customer.findByCustomerId");
            jpqlQuery.setParameter("customerId", id);
            jpqlQuery.setParameter("deleted", (short) 0);

            List<Customer> customerList = jpqlQuery.getResultList();

            request.setAttribute("customer", customerList.get(0));
            request.setAttribute("userType", sessionUser.getSystemUserTypeId());
            request.setAttribute("countries", CountryService.getCountryList());
            request.setAttribute("action", "edit");
            request.setAttribute("dateFmt", new SimpleDateFormat("EEE, d MMM yyyy"));
            action = "edit";
        }
        else if (action.equalsIgnoreCase("profile"))
        {
            viewFile = "/views/customer/profile.jsp";
//            
            //Check if the user doesnot specify id parameter
            String idString = request.getParameter("customerId");

            Long id;

            if (idString == null)
            {
                //Then we know that the logged in user is trying to view it own profile
                //we can now procceed to use the id stored in the session
                id = sessionUser.getSystemUserId();
            }
            else
            {
                //Here we know we are about to get the profile of a particular user
                id = Long.parseLong(idString);
            }

//            //find by ID
            Query jpqlQuery = em.createNamedQuery("Customer.findByCustomerId");
            jpqlQuery.setParameter("customerId", id);
            jpqlQuery.setParameter("deleted", (short) 0);
            List<Customer> customerList = jpqlQuery.getResultList();
//            
            request.setAttribute("customer", customerList.get(0));
            request.setAttribute("action", "edit");
            request.setAttribute("history", getRequestHistory(request));
            request.setAttribute("userType", sessionUser.getSystemUserTypeId());
            request.setAttribute("orders", getCustomerOrders(request, response, false));
            request.setAttribute("lodgements", getCustomerLodgments(request, response, false));
            request.setAttribute("documents", DocumentService.getCustomerDocuments(id));
            request.setAttribute("dateFmt", new SimpleDateFormat("EEE, d MMM yyyy"));
        }
        else if (action.isEmpty() || action.equalsIgnoreCase("listcustomers"))
        {
            viewFile = CUSTOMER_ADMIN;

            if (sessionUser.getSystemUserTypeId() == 2)
            {
                request.setAttribute("customers", listAgentCustomers());
            }
            else
            {
                request.setAttribute("customers", listCustomers());
            }

            action = "";
        }
        else if (action.equalsIgnoreCase("current"))
        {

            viewFile = CUSTOMER_CURRENT_PAYING;
            request.setAttribute("customers", getCurrentPayingCustomers());
        }
        else if (action.equalsIgnoreCase("completed"))
        {

            viewFile = CUSTOMER_COMPLETED_PAYMENT;
            request.setAttribute("customers", getCompletedPaymentCustomers());
        }
        else if (action.equalsIgnoreCase("lodgement_invoice"))
        {
            action = "";
            getLodgmentInvoice(request, response);
            return;
        }
        else if (action.equalsIgnoreCase("email_lodgement_invoice"))
        {
            action = "";
            new PDFHelper().sendPDFEmail(request, response);
            sendCustomerInvoiceEmail(request, response);
            if (sessionUser.getSystemUserTypeId() == 3)
            {
                AppController.doRedirection(request, response, "/Customer?action=lodgement_invoice&id=" + request.getParameter("id"));
            }
            else
            {
                AppController.doRedirection(request, response, "/Customer");
            }
            return;
        }
        else if (action.equalsIgnoreCase("customer_orders"))
        {
            action = "";
            getCustomerOrders(request, response, true);
            return;
        }

        request.setAttribute("projects", project.listProjects());

        //Keep track of the sideBar
        request.setAttribute("sideNav", "Customer");
        request.setAttribute("sideNavAction", action);

        String imageAccessDirPath = new FileUploader(FileUploader.fileTypesEnum.IMAGE.toString(), false).getAccessDirectoryString();
        request.setAttribute("customerImageAccessDir", imageAccessDirPath + "/customers");
        request.setAttribute("customerKinImageAccessDir", imageAccessDirPath + "/customers/customerkins");
        request.setAttribute("documentDir", imageAccessDirPath + "/");

        RequestDispatcher dispatcher = request.getRequestDispatcher(viewFile);
        dispatcher.forward(request, response);

    }

    /*TP: Listing of customers that exists in the database and are not deleted*/
    public List<Customer> listCustomers() {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery = em.createNamedQuery("Customer.findByDeleted");
        jpqlQuery.setParameter("deleted", 0);
        List<Customer> customerList = jpqlQuery.getResultList();

        return customerList;
    }

    public List<Customer> listAgentCustomers() {

        Agent agent = (Agent) sessionUser;

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        //find by ID
        Query jpqlQuery = em.createNamedQuery("Customer.findByAgent");
        jpqlQuery.setParameter("deleted", 0);
        jpqlQuery.setParameter("agent", agent);
        List<Customer> customerList = jpqlQuery.getResultList();

        return customerList;
    }

    /*TP: Upload of the Customer's picture is done here*/
    private String uploadCustomerPicture(Customer customer, HttpServletRequest request, String customerFileName) throws PropertyException, ServletException, IOException {
        String root = getServletContext().getRealPath("/");
        String path = root + "/images/uploads/customers/";
        long unixTime = System.currentTimeMillis() / 1000L;
        if ((request.getPart("customerPhoto") != null) || (!request.getParameter("customerPhotoHidden").isEmpty() && request.getParameter("customerPhotoHidden") != null))
        {
            if (request.getPart("customerPhoto") != null)
            {
                /*TP: Customer personal file upload*/
                Part customerPartPhoto = request.getPart("customerPhoto");
                String myName = getFileName(customerPartPhoto);

                if (myName == null && !request.getParameter("customerPhotoHidden").isEmpty())
                {

                    customerFileName = request.getParameter("customerPhotoHidden");
                    customer.setPhotoPath(request.getParameter("customerPhotoHidden"));
                }
                else if (myName == null && request.getParameter("customerPhotoHidden").isEmpty())
                {
                    customer.setPhotoPath("default");
                }
                else
                {

                    int fnameLength = myName.length();
                    int startingPoint = fnameLength - 4;
                    myName = myName.substring(startingPoint, fnameLength);
                    customerFileName = "customer_" + unixTime + myName;

                    this.photoImageUpload(customerFileName, path, customerPartPhoto);
                }
            }
            else if (!request.getParameter("customerPhotoHidden").isEmpty())
            {
                customerFileName = request.getParameter("customerPhotoHidden");
                customer.setPhotoPath(request.getParameter("customerPhotoHidden"));
            }

        }
        return customerFileName;

    }

    /*TP: Upload of the Customer's next of kin picture is done here*/
    private String uploadCustomerKinPicture(Customer customer, HttpServletRequest request, String customerKinFileName) throws PropertyException, ServletException, IOException {
        String root = getServletContext().getRealPath("/");
        String path = root + "/images/uploads/customers/";
        long unixTime = System.currentTimeMillis() / 1000L;
        if ((request.getPart("customerKinPhoto") != null) || (!request.getParameter("customerKinPhotoHidden").isEmpty() && request.getParameter("customerKinPhotoHidden") != null))
        {

            if (request.getPart("customerKinPhoto") != null)
            {
                /*TP: Customer Kin personal file upload*/
                Part customerKinPartPhoto = request.getPart("customerKinPhoto");
                String myNameKin = "";
                myNameKin = getFileName(customerKinPartPhoto);
                ////System.out.println("This is the my name of the next of kin we are testing for "+request.getParameter("customerKinPhotoHidden"));
                if (myNameKin == null && (!request.getParameter("customerKinPhotoHidden").isEmpty()))
                {
                    customerKinFileName = request.getParameter("customerKinPhotoHidden");
                    customer.setPhotoPath(request.getParameter("customerKinPhotoHidden"));
                }
                else if (myNameKin == null && request.getParameter("customerKinPhotoHidden").isEmpty())
                {
                    customer.setKinPhotoPath("default");
                }
                else
                {
                    int fnameLengthK = myNameKin.length();
                    int startingPointK = fnameLengthK - 4;
                    myNameKin = myNameKin.substring(startingPointK, fnameLengthK);
                    customerKinFileName = "customerKin_" + unixTime + myNameKin;
                    photoImageUpload(customerKinFileName, path, customerKinPartPhoto);
                }
            }
            else if (!request.getParameter("customerKinPhotoHidden").isEmpty())
            {
                //System.out.println("photo nd hidden photo is empty");
                customerKinFileName = request.getParameter("customerKinPhotoHidden");
                customer.setPhotoPath(request.getParameter("customerKinPhotoHidden"));
            }
        }
        return customerKinFileName;

    }

    /*TP: This is a generic method for image upload*/
    private void photoImageUpload(String customerFileName, String path, Part customerPartPhoto) {
        OutputStream fout = null;
        InputStream filecontent = null;

        String type = customerPartPhoto.getHeader("content-type");
        if (type.equals("image/jpeg") || type.equals("image/png") || type.equals("image/jpg") || type.equals("image/gif") || type.equals("image/bmp"))
        {
            try
            {
                fout = new FileOutputStream(new File(path + customerFileName));

                filecontent = customerPartPhoto.getInputStream();

                int read = 0;
                final byte[] bytes = new byte[32 * 1024];
// 
                while ((read = filecontent.read(bytes)) != -1)
                {
                    fout.write(bytes, 0, read);
                }
                fout.flush();
                fout.close();
            } catch (Exception e)
            {
                errorMessages.put("error7", "Invalid File Format");
            }
        }
        else
        {
            errorMessages.put("error7", "Invalid File Format");
        }
    }

    /*Validate the customers details*/
 /*TP: Validation is done here*/
    private void validate(Customer customer, HttpServletRequest request) throws PropertyException, ServletException, IOException {
        errorMessages.clear();
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        Query jpqlQuery = em.createNamedQuery("Customer.findByEmail");
        jpqlQuery.setParameter("email", request.getParameter("customerEmail"));
        List<Customer> customerDetails = jpqlQuery.getResultList();

        String formFieldStage1[][] =
        {
            {
                "customerTitle", "select", "Customer Title"
            } //<editor-fold>
            ,
                        {
                "customerFirstname", "", "Customer First Name"
            },
                        {
                "customerMiddlename", "", "Customer Middle Name"
            },
                        {
                "customerLastname", "", "Customer Last Name"
            },
                        {
                "customerGender", "select", "Customer Gender"
            },
                        {
                "customerMaritalStatus", "select", "Customer Marital Status"
            },
                        {
                "customerDateOfBirth", "", "Customer Date Of Birth"
            },
                        {
                "customerEmail", "", "Customer Email"
            },
                        {
                "customerOccupation", "", "Customer Occupation"
            },
                        {
                "customerEmployer", "", "Customer Employer"
            },
                        {
                "customerOfficePhone", "", "Customer Office Phone"
            },
                        {
                "customerOfficeStreet", "", "Customer Office Street"
            },
                        {
                "customerOfficeCity", "", "Customer Office City"
            },
                        {
                "customerOfficeState", "", "Customer Office State"
            },
                        {
                "customerOfficeCountry", "select", "Customer Office Country"
            },
                        {
                "customerEmployerStreet", "", "Customer Employer Street"
            },
                        {
                "customerEmployerCity", "", "Customer Employer City"
            },
                        {
                "customerEmployerState", "", "Employer State"
            },
                        {
                "customerEmployerCountry", "select", "Customer Employer Country"
            },
                        {
                "customerStreet", "", "Customer Street"
            },
                        {
                "customerCity", "", "Customer City"
            },
                        {
                "customerState", "", "Customer State"
            },
                        {
                "customerCountry", "select", "Customer Country"
            },
                        {
                "customerPhone", "", "Customer Phone"
            } //,["customerOtherPhone" , "select" , "Customer Country"]
            ,
                        {
                "customerPostalAddress", "", "Customer  Postal Address"
            },
                        {
                "customerKinName", "", "Customer Next Of Kin Name"
            },
                        {
                "customerKinRelationship", "", "Customer Next Of Kin Relationship"
            },
                        {
                "customerKinEmail", "", "Customer Next Of Kin Email"
            },
                        {
                "customerKinPhone", "", "Customer Next Of Kin Phone Number"
            },
                        {
                "customerKinAddress", "", "Customer  Next Of Kin Address"
            },
                        {
                "customerBanker", "", "Customer Banker"
            },
                        {
                "customerAccountName", "", "Customer Account Name"
            },
                        {
                "customerAccountNumber", "", "Customer Account Number"
            }
        };
        //</editor-fold>

        String formFieldStage2[][] =
        {
            {
                "customerPhoto", "", " Customer PassPort"
            } //<editor-fold>
            ,
                        {
                "customerKinPhoto", "", "Customer Next Of Kin Passport"
            },
            {
                "customerPhotoID", "", "Customer ID card /Driver Lincense etc"
            },
            {
                "customerBankStandingOrder", "", "Customer Post-dated Cheques/Bank Standing Order"
            }
        }; //</editor-fold>
        int error = 0;
        for (String field[] : formFieldStage1)
        {
            String parameter = request.getParameter(field[0]);
            if (parameter != null)
            {
                if (parameter.isEmpty() || parameter.equals("select"))
                {
                    if (field[1].isEmpty())
                    {
                        errorMessages.put("errors" + error, "Please Enter Value For " + field[2]);
                    }
                    else if (field[1].equals("select"))
                    {
                        errorMessages.put("errors" + error, "Please Select Value For " + field[2]);
                    }
                    error++;
                }
            }
        }

        for (String field[] : formFieldStage2)
        {
            String fileName = request.getPart(field[0]).getSubmittedFileName();
            if (fileName != null)
            {
                if (fileName.isEmpty())
                {
                    errorMessages.put("errors" + error, "Please Upload File For " + field[2]);
                    error++;
                }
            }
        }

        if (!customerDetails.isEmpty())
        {
            errorMessages.put("errors" + error, "Email Already Exit");
            error++;
        }

        //Validate Every Image File the User Upload
        boolean validateFile = FileUploader.validateFile(request.getParts());
        if (!validateFile)
        {
            errorMessages.put("errors" + error, "One or More Upload Files are Invalid (Please Upload Image File With Max Size Of 2MB)");
        }

        if (!(errorMessages.isEmpty()))
        {
            throw new PropertyException("");
        }
    }

    /*TP: Getting the file name of an uploaded image*/
    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
        for (String content : part.getHeader("content-disposition").split(";"))
        {
            if (content.trim().startsWith("filename"))
            {
//                    return content.substring(
//                    content.indexOf('=') + 1).trim().replace("\"", "");
                //System.out.println("Content = " + content.indexOf("="));
                String filename = content.substring(content.indexOf("=") + 2, content.length() - 1);

                String filenameCleaned = filename.trim().replace("\"", "");

                if (filenameCleaned.equals(""))
                {
                    return null;
                }
                else
                {
                    return filenameCleaned;
                }
            }
        }

        return null;
    }

    /*TP: Deletion of a customer is done here*/
    public void delete(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        customer = em.find(Customer.class, new Long(id));
        int i = 1;
        short y = (short) i;
        em.getTransaction().begin();
        customer.setDeleted(y);
        em.persist(customer);
        em.getTransaction().commit();

        em.close();
        emf.close();

    }

    public void deleteAccount() {

    }

    public List<Customer> getCurrentPayingCustomers() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        String queryString = (sessionUser.getSystemUserTypeId() == 1) ? "ProductOrder.findByALLCurrentPayingCustomer" : "ProductOrder.findByCurrentPayingCustomer";

        Query JPQL = em.createNamedQuery(queryString);

        if (sessionUser.getSystemUserTypeId() == 2)
        {
            Agent agent = em.find(Agent.class, sessionUser.getSystemUserId());
            JPQL.setParameter("agent", agent);
        }

        List<Customer> customers = JPQL.getResultList();

        em.close();
        emf.close();

        return customers;
    }

    public List<Customer> getCompletedPaymentCustomers() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        String queryString = (sessionUser.getSystemUserTypeId() == 1) ? "ProductOrder.findByALLCompletedPaymentCustomer" : "ProductOrder.findByCompletedPaymentCustomer";

        Query JPQL = em.createNamedQuery(queryString);

        if (sessionUser.getSystemUserTypeId() == 2)
        {
            Agent agent = em.find(Agent.class, sessionUser.getSystemUserId());
            JPQL.setParameter("agent", agent);
        }

        List<Customer> customers = JPQL.getResultList();

        em.close();
        emf.close();

        return customers;
    }

    public static Calendar getDateTime() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        return calendar;
    }

    private Map getInvoicData(List<LodgementItem> LItems, double vat, double gateWayCharge) {

        double total = 0.00;
        double grandTotal = 0.00;

        for (LodgementItem LI : LItems)
        {

            double rewardAmount = LI.getRewardAmount() != null ? LI.getRewardAmount() : 0;
            total += LI.getAmount() + rewardAmount;
        }

        grandTotal = total + vat + gateWayCharge;

        Map<String, Double> map = new HashMap();
        map.put("total", total);
        map.put("grandTotal", grandTotal);

        return map;
    }

    private void validateEmail(HttpServletRequest request, HttpServletResponse response) {

        try
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();

            String email = request.getParameter("email") != null ? request.getParameter("email").toLowerCase() : "";

            if (email == null)
            {
                return;
            }
            Query query = em.createNamedQuery("Customer.findByEmail");
            query.setParameter("email", email);

            List<Customer> customer = query.getResultList();

            //System.out.println("Customer count : " + customer.size());

            Integer code = customer.size() == 0 ? 1 : -1;

            Gson gson = new GsonBuilder().create();

            Map<String, String> map = new HashMap();
            map.put("code", code.toString());

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(gson.toJson(map));
            response.getWriter().flush();
            response.getWriter().close();
        } catch (IOException ex)
        {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void changeCustomerPassword(HttpServletRequest request, HttpServletResponse response) {

        try
        {
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
            EntityManager em = emf.createEntityManager();

            Map<String, String> resMap = new HashMap();

            long id = Long.parseLong(request.getParameter("id"));

            String oldPassword = request.getParameter("old_password");
            String pwd1 = request.getParameter("pwd1");
            String pwd2 = request.getParameter("pwd2");

            //System.out.println("Old Password : " + oldPassword + " Id : " + id);
            em.getTransaction().begin();

            Customer customer = em.find(Customer.class, id);

            if (AuthManager.check(oldPassword, customer.getPassword()))
            {

                if (pwd1.equals(pwd2) && !pwd1.equals(""))
                {
                    customer.setPassword(AuthManager.getSaltedHash(pwd1));
                    resMap.put("success", "Password changed successfully");
                }
                else
                {
                    resMap.put("error", "Password and Re-enter password do not match");
                }

            }
            else
            {
                resMap.put("error", "Invalid old password");
            }

            if (customer != null)
            {
                em.merge(customer);

            }

            em.getTransaction().commit();
            em.close();
            emf.close();

            Gson gson = new GsonBuilder().create();

            String json = gson.toJson(resMap);

            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(json);
            response.getWriter().flush();
            response.getWriter().close();

        } catch (Exception ex)
        {
            Logger.getLogger(CustomerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private String getCustomerLodgments(HttpServletRequest request, HttpServletResponse response, boolean isAjax) throws IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        long id;

        if (isAjax)
        {
            id = Long.parseLong(request.getParameter("id"));
        }
        else
        {
            id = Long.parseLong(request.getParameter("customerId"));
        }

        Customer customer = em.find(Customer.class, id);

        Query query = em.createNamedQuery("Lodgement.findByCustomerApproval");
        query.setParameter("customer", customer);
        query.setParameter("approvalStatus", 1);

        List<Lodgement> lodgementList = query.getResultList();

        String customerName = customer.getFullName();

        List<Map> LodgementListMap = new ArrayList();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd");

        for (Lodgement l : lodgementList)
        {

            Map<String, Object> map = new HashMap();

            double loyaltyAmt = l.getRewardAmount();
            double amount = l.getAmount() + loyaltyAmt;

            map.put("id", l.getId());
            map.put("amount", String.format("%.2f", amount));
            map.put("date", sdf.format(l.getLodgmentDate()));
            map.put("depositorName", l.getDepositorName() != null ? l.getDepositorName() : "");
            map.put("depositorAcctName", l.getOriginAccountName() != null ? l.getOriginAccountName() : "");
            map.put("depositorAcctNo", l.getOriginAccountNumber() != null ? l.getOriginAccountNumber() : "");
            map.put("transactionId", l.getTransactionId() != null ? l.getTransactionId() : "");

            String paymentMode = "";
            String status = "";

            short mode = l.getPaymentMode();

            switch (mode)
            {
                case 1:
                    paymentMode = "Bank Deposit";
                    break;
                case 2:
                    paymentMode = "Credit/Debit Card";
                    break;
                case 3:
                    paymentMode = "Cash/Cheque";
                    break;
                case 4:
                    paymentMode = "Bank Transfer";
                    break;
            }

            short s = l.getApprovalStatus();

            switch (s)
            {
                case 0:
                    status = "pending";
                    break;
                case 1:
                    status = "approved";
                    break;
                default:
                    status = "declined";
            }

            map.put("paymentMode", paymentMode);
            map.put("status", status);
            map.put("companyAcctName", l.getCompanyAccountId().getAccountName());
            map.put("Orders", getLodgementOrderItems(l.getId()));

            LodgementListMap.add(map);
        }

        Map<String, Object> responseMap = new HashMap();

        responseMap.put("lodgements", LodgementListMap);
        responseMap.put("customerName", customerName);

        Gson gson = new GsonBuilder().create();

        String jsonResponse = gson.toJson(responseMap);

        //System.out.println("Lodgements Json = " + jsonResponse);

        String payLoad = "";
        if (isAjax)
        {
            response.setContentType("text/plain");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
            response.getWriter().close();
        }
        else
        {
            payLoad = jsonResponse;
        }

        return payLoad;
    }

    public List<List<Map>> getLodgementOrderItems(long lodgmentId) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        String sql = "SELECT OI "
                + " FROM OrderItem OI where OI.id in (select LI.item.id from LodgementItem LI where LI.lodgement.id = :lid)";

        TypedQuery<OrderItem> query = em.createQuery(sql, OrderItem.class).setParameter("lid", lodgmentId);

        List<OrderItem> orderItemList = query.getResultList();

        Set<Long> set = new HashSet<Long>();

        for (OrderItem item : orderItemList)
        {

            set.add(item.getOrder().getId());

        }

        Long[] orderId = set.toArray(new Long[set.size()]);
        OrderItemHelper itemHelper = new OrderItemHelper();

        //Each list item represents an order containing List of OrderItem Map
        List<List<Map>> groupedOrderItem = new ArrayList();

        for (Long oid : orderId)
        {

            //check if order value has being set
            boolean isOrderValueSet = false;

            List<Map> mapList = new ArrayList();

            for (OrderItem item : orderItemList)
            {

                if (oid == item.getOrder().getId())
                {

                    Map<String, String> map = new HashMap();
                    Double total_paid = itemHelper.getTotalItemPaidAmount((List) item.getLodgementItemCollection());

                    if (!isOrderValueSet)
                    {
                        map.put("orderValue", String.format("%.2f", itemHelper.getOrderValue(oid)));
                        isOrderValueSet = true;
                    }

                    map.put("quantity", item.getQuantity().toString());
                    map.put("initialDeposit", String.format("%.2f", item.getInitialDep()));
                    map.put("cpu", String.format("%.2f", item.getUnit().getCpu()));
                    map.put("title", item.getUnit().getTitle());
                    map.put("discount", itemHelper.getOrderItemDiscount(item.getUnit().getDiscount(), item.getUnit().getCpu(), item.getQuantity()));
                    map.put("total_paid", String.format("%.2f", total_paid));
                    map.put("project_name", item.getUnit().getProject().getName());
                    map.put("balance", itemHelper.getOrderItemBalance(item.getUnit().getAmountPayable(), item.getQuantity(), total_paid));
                    map.put("completionDate", itemHelper.getCompletionDate(item, total_paid));
                    map.put("paymentStage", itemHelper.getPaymentStage(item, total_paid));
                    map.put("advance", String.format("%.2f", ((total_paid - item.getInitialDep()) % item.getUnit().getMonthlyPay())));
                    map.put("monthly", String.format("%.2f", (item.getUnit().getMonthlyPay())));

                    Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
                    cal.setTime(item.getCreatedDate());

                    SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

                    String dateString = sdf.format(cal.getTime());

                    map.put("startDate", dateString);

                    mapList.add(map);
                }
            }

            groupedOrderItem.add(mapList);
        }

        return groupedOrderItem; // return list of orderItem grouped by Order
    }

    public void getLodgmentInvoice(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        long lodgement_id = Long.parseLong(request.getParameter("id"));

        Lodgement lodgement = em.find(Lodgement.class, lodgement_id);

        Company company = em.find(Company.class, 1);

        List<LodgementItem> LItems = (List) lodgement.getLodgementItemCollection();

        Date date = lodgement.getCreatedDate();
        //SimpleDateFormat sdf = new SimpleDateFormat("EEE, d MMM yyyy");
        //String dateString = sdf.format(date);

        double vat = 0.00;
        double gateWayCharge = 0.00;
        Map map = getInvoicData(LItems, vat, gateWayCharge);

        String viewFile = "/views/customer/invoice.jsp";
        request.setAttribute("print", 1);
        request.setAttribute("lodgement", lodgement);
        request.setAttribute("orderItemInvoice", LItems);
        request.setAttribute("transactionDate", date);
        request.setAttribute("customerInvoice", lodgement.getCustomer());
        request.setAttribute("totalInvoice", (Double) map.get("total"));
        request.setAttribute("grandTotalInvoice", (Double) map.get("grandTotal"));
        request.setAttribute("vatInvoice", vat);
        request.setAttribute("gatewayChargeInvoice", gateWayCharge);
        request.setAttribute("companyName", company.getName());
        request.setAttribute("companyAddress", company.getAddressLine1() + " , " + company.getAddressLine2());
        request.setAttribute("companyPhone", company.getPhone());
        request.setAttribute("companyEmail", company.getEmail());
        request.setAttribute("companyLogo", company.getLogoPath());

        request.getRequestDispatcher(viewFile).forward(request, response);
    }

    public void sendCustomerInvoiceEmail(HttpServletRequest request, HttpServletResponse response) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        long lodgement_id = Long.parseLong(request.getParameter("id"));

        Lodgement lodgement = em.find(Lodgement.class, lodgement_id);
        Customer customer = lodgement.getCustomer();
        List<LodgementItem> LItems = (List) lodgement.getLodgementItemCollection();

        Date date = lodgement.getCreatedDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
        String dateString = sdf.format(date);

        double vat = 0.00;
        double gateWayCharge = 0.00;

        String htmlOutput = prepareInvoiceEmail(lodgement, customer, LItems, vat, gateWayCharge);

        // new MailSender().sendHtmlEmail("prestigegodson@gmail.com", "godson.ositadinma@techieplanetltd.com", "Lodgment", htmlOutput);
        /**
         * Copy HTML content into Text File
         */
        FileWriter writer = null;
        try
        {
            File file = new File("htmloutput.txt");

            //FileOutputStream fout = new FileOutputStream(file);
            writer = new FileWriter(file);
            writer.write(htmlOutput);
            writer.close();
        } catch (FileNotFoundException fnfe)
        {
            //System.out.println("Exception : " + fnfe.getMessage());
        } catch (IOException ioe)
        {
            //System.out.println("Exception : " + ioe.getMessage());
        } finally
        {
            if (writer != null)
            {
                try
                {
                    writer.close();
                } catch (IOException ex)
                {
                    //System.out.println("Unable To Close File : " + ex.getMessage());
                }
            }
        }

    }

    public String prepareInvoiceEmail(Lodgement lodgement, Customer customer, List<LodgementItem> LItems, double vat, double gateWayCharge) {

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        Date date = cal.getTime();

        String html = "";

        html += "<table width='100%' height='100%'  border='0'>";

        html += "<tr><td align='center' style='background-color:#eee'>";

        html += "<table width='80%' style='background-color:#fff; margin-top:20px;margin-bottom:20px;padding:20px'>";
        html += "<tr>";
        html += "<td colspan='4' style='text-align:right;border-bottom: solid 2px #ccc;'>Sent : " + date.toString() + "</td>";
        html += "</tr>";

        html += "<tr>";
        html += "<td>";
        html += "<b>From</b> <br />";
        html += super.companyName + " <br />";
        html += super.companyAddress + " <br />";
        html += "Phone: " + super.companyPhone + " <br />";
        html += "Email: " + super.companyEmail;
        html += "</td>";

        html += "<td>";
        html += "<b>To</b> <br />";
        html += customer.getFullName() + " <br />";
        html += customer.getStreet() + ", " + customer.getState() + " <br />";
        html += "Phone: " + customer.getPhone() + " <br />";
        html += "Email: " + customer.getEmail();
        html += "</td>";

        html += "<td colspan='2' valign='top'><b>Invoice #</b>" + lodgement.getId() + "<br /> Transaction Date: " + lodgement.getCreatedDate() + "</td>";
        html += "</tr>";

        //Prepare the OrderItems Table
        html += "<tr>";
        html += "<td colspan='4'>";

        html += "<table width=100% style='margin-top:40px'>";
        html += "<tr>";
        html += "<th style='border: solid 1px #ccc;background-color:#000;color:#fff'>S/N</th>  <th style='border: solid 1px #ccc;background-color:#000;color:#fff'>Description</th> <th style='border: solid 1px #ccc;background-color:#000;color:#fff'>Qty</th>  <th style='border: solid 1px #ccc;background-color:#000;color:#fff'>Subtotal</th>";
        html += "</tr>";

        int count = 1;
        double total = 0;
        for (LodgementItem LI : LItems)
        {
            double reward = LI.getRewardAmount() != null ? LI.getRewardAmount() : 0;
            double amount = LI.getAmount() + reward;

            html += "<tr>";
            html += "<td style='border: solid 1px #ccc;'>";
            html += count;
            html += "</td>";
            html += "<td style='border: solid 1px #ccc;'>";
            html += LI.getItem().getUnit().getProject().getName() + " - " + LI.getItem().getUnit().getTitle();
            html += "</td>";
            html += "<td style='border: solid 1px #ccc;'>";
            html += LI.getItem().getQuantity();
            html += "</td>";
            html += "<td style='border: solid 1px #ccc;'>";
            html += String.format("%s %,.2f", "N", amount);
            html += "</td>";

            html += "</tr>";

            total += amount;
            count++;
        }
        html += "<tfoot style='background-color:#F3F5F6;'>";
        html += "<tr>";
        html += "<td colspan='3' style='text-align:right;border: solid 1px #ccc;'>Total: </td>";
        html += "<td style='border: solid 1px #ccc;'>" + String.format("%s %,.2f", "N", total) + "</td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td colspan='3' style='text-align:right;border: solid 1px #ccc;'>VAT: </td>";
        html += "<td style='border: solid 1px #ccc;'>" + String.format("%s %,.2f", "N", vat) + "</td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td colspan='3' style='text-align:right;border: solid 1px #ccc;'>GateWay Charge: </td>";
        html += "<td style='border: solid 1px #ccc;'>" + String.format("%s %,.2f", "N", gateWayCharge) + "</td>";
        html += "</tr>";
        html += "<tr>";
        html += "<td colspan='3' style='text-align:right;border: solid 1px #ccc;'>Grand Total: </td>";
        html += "<td style='border: solid 1px #ccc;'>" + String.format("%s %,.2f", "N", total + vat + gateWayCharge) + "</td>";
        html += "</tr>";
        html += "</tfoot>";

        html += "</table>";
        html += "</td>";
        html += "</tr>";
        html += "</table>";

        html += "</td></tr>";

        html += "</table>";

        return html;

    }

    public List<Map> getCustomerOrders(HttpServletRequest request, HttpServletResponse response, boolean returnJson) throws IOException {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        String id = request.getParameter("id") != null ? request.getParameter("id") : request.getParameter("customerId");
        Long customerId = Long.parseLong(id);

        Customer customer = em.find(Customer.class, customerId);
        Query query = em.createNamedQuery("ProductOrder.findByCustomer");

        List<ProductOrder> orders = query.setParameter("customerId", customer).getResultList();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");

        List<Map> ordersMapList = new ArrayList();

        for (ProductOrder order : orders)
        {

            Map<String, Object> orderMap = new HashMap();
            List<OrderItem> items = em.createNamedQuery("OrderItem.findByOrder").setParameter("order", order).getResultList();

            orderMap.put("order_date", sdf.format(order.getCreatedDate()));
            List<Map> itemMapList = new ArrayList();

            for (OrderItem item : items)
            {

                Map<String, String> map = new HashMap();

                map.put("project_name", item.getUnit().getProject().getName());
                map.put("unit_name", item.getUnit().getTitle());
                map.put("qty", item.getQuantity().toString());
                map.put("cpu", String.format("%.2f", item.getUnit().getAmountPayable()));

                itemMapList.add(map);
            }

            orderMap.put("items", itemMapList);

            ordersMapList.add(orderMap);
        }

        if (!returnJson)
        {
            return ordersMapList;
        }

        Gson gson = new GsonBuilder().create();

        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(gson.toJson(ordersMapList));
        response.getWriter().flush();
        response.getWriter().close();

        return ordersMapList;
    }

    private void addNewProspect(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try
        {

            validateAgentProspect(request);

            emf = Persistence.createEntityManagerFactory("NeoForcePU");
            em = emf.createEntityManager();

            //Check if email already exists
            Query query = em.createNamedQuery("AgentProspect.findByEmail").setParameter("email", request.getParameter("email"));
            List<AgentProspect> prospect = query.getResultList();

            if (prospect.size() > 0)
            {
                errorMessages.put("Email", "Email alredy exists for " + prospect.get(0).getFullName());
                throw new PropertyException("Email Already Exist");
            }

            em.getTransaction().begin();

            AgentProspect customer = new AgentProspect();
            customer.setFirstName(request.getParameter("fname"));
            customer.setMiddleName(request.getParameter("mname"));
            customer.setLastName(request.getParameter("lname"));
            customer.setEmail(request.getParameter("email"));
            customer.setState(request.getParameter("state"));
            customer.setCity(request.getParameter("city"));
            customer.setStreet(request.getParameter("street"));
            customer.setPhoneNo(request.getParameter("phone"));
            customer.setCompany(request.getParameter("customer_comapany_name"));
            customer.setPost(request.getParameter("customer_post"));

            customer.setAgent((Agent) sessionUser);

            em.persist(customer);

            em.getTransaction().commit();

            request.setAttribute("success", "Prospective client details has been registered successfully");

        } catch (PropertyException pe)
        {

            //System.out.println("PropertyException : " + pe.getMessage());
            request.setAttribute("errors", errorMessages);
        } catch (RollbackException rbe)
        {
            //System.out.println("Transaction Rolled Back : " + rbe.getMessage());

        } finally
        {

            if (em != null)
            {
                em.close();
            }

            if (emf != null)
            {
                emf.close();
            }

            String viewFile = CUSTOMER_NEW_PROSPECT;
            request.getRequestDispatcher(viewFile).forward(request, response);
        }
    }

    private void updateProspect(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        EntityManagerFactory emf = null;
        EntityManager em = null;

        try
        {

            validateAgentProspect(request);

            emf = Persistence.createEntityManagerFactory("NeoForcePU");
            em = emf.createEntityManager();

            long prospectId = Long.parseLong(request.getParameter("id"));

            em.getTransaction().begin();

            AgentProspect customer = em.find(AgentProspect.class, prospectId);

            customer.setFirstName(request.getParameter("fname"));
            customer.setMiddleName(request.getParameter("mname"));
            customer.setLastName(request.getParameter("lname"));
            customer.setEmail(request.getParameter("email"));
            customer.setState(request.getParameter("state"));
            customer.setCity(request.getParameter("city"));
            customer.setStreet(request.getParameter("street"));
            customer.setPhoneNo(request.getParameter("phone"));
            customer.setCompany(request.getParameter("customer_comapany_name"));
            customer.setPost(request.getParameter("customer_post"));

            customer.setAgent((Agent) sessionUser);

            em.merge(customer);

            em.getTransaction().commit();

            request.setAttribute("prospect", em.find(AgentProspect.class, customer.getId()));
            request.setAttribute("success", "Prospective client details has been updated successfully");

        } catch (PropertyException pe)
        {

            //System.out.println("PropertyException : " + pe.getMessage());
            request.setAttribute("errors", errorMessages);
        } catch (RollbackException rbe)
        {
            //System.out.println("Transaction Rolled Back : " + rbe.getMessage());

        } finally
        {

            if (em != null)
            {
                em.close();
            }

            if (emf != null)
            {
                emf.close();
            }

            String viewFile = "/views/customer/edit_prospect.jsp";
            request.getRequestDispatcher(viewFile).forward(request, response);
        }

    }

    private void validateAgentProspect(HttpServletRequest request) throws PropertyException {

        errorMessages.clear();

        if (request.getParameter("fname").isEmpty())
        {
            errorMessages.put("First Name", "First name is required");
        }

        if (request.getParameter("lname").isEmpty())
        {
            errorMessages.put("Last Name", "Last name is required");
        }

        if (request.getParameter("street").isEmpty())
        {
            errorMessages.put("Street", "Street is required");
        }

        if (request.getParameter("city").isEmpty())
        {
            errorMessages.put("City", "City is required");
        }

        if (request.getParameter("state").isEmpty())
        {
            errorMessages.put("State", "State is required");
        }

        if (request.getParameter("phone").isEmpty())
        {
            errorMessages.put("Phone", "Phone is required");
        }

        if (request.getParameter("email").isEmpty())
        {
            errorMessages.put("Email", "Email is required");
        }

        if (request.getParameter("customer_comapany_name").isEmpty())
        {
            errorMessages.put("Company Name", "Company name is required");
        }

        if (request.getParameter("customer_post").isEmpty())
        {
            errorMessages.put("Customer Post", "Customer post is required");
        }

        if (!errorMessages.isEmpty())
        {

            throw new PropertyException("Prospective Customer validation error");
        }

    }

    private List<AgentProspect> getProspectiveCustomers(HttpServletRequest request) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        Agent agent = em.find(Agent.class, sessionUser.getSystemUserId());

        Query query = em.createNamedQuery("AgentProspect.findByAgent").setParameter("agent", agent);

        List<AgentProspect> prospects = query.getResultList();
        //System.out.println("Prospects count : " + prospects.size());

        //em.close();
        return prospects;
    }

    private AgentProspect getProspect(HttpServletRequest request) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        long id = Long.parseLong(request.getParameter("id"));

        AgentProspect pc = em.find(AgentProspect.class, id);
        em.close();

        return pc;
    }

    /**
     * @param HttpServletRequest
     * @return Map This method returns the parameters of an HTTP request as a
     * Map object
     */
    public Map<String , String> getRequestParameters(HttpServletRequest request) {
        Map<String, String> map = new HashMap();
        Enumeration params = request.getParameterNames();
        while (params.hasMoreElements())
        {
            String elem = params.nextElement().toString();
            map.put(elem, request.getParameter(elem));
        }

        return map;
    }

    private String getRequestHistory(HttpServletRequest request) {

        String url = request.getHeader("referer");
        //System.out.println("History : " + url);
        return url;
    }

    /*TP: Getting the customer Id for public use*/
    public Long getSystemUserId() {
        return customer.getCustomerId();
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

   private boolean validateOrder(List<OrderItem> orderItems , Lodgement lodgement, StringBuilder errorMSG){
      
       //Validating if the initial payment specified by user is lesser that what is required
       Double customerTotalInitialPayment = 0.0;
       
       for(OrderItem item : orderItems)
       {
          customerTotalInitialPayment += item.getInitialDep();
          if(item.getInitialDep() < item.getUnit().getLeastInitDep())
          {
              errorMSG.append("One or more Order have an Invalid Initial deposit amount");
              return false;
          }
       }
       
       if(lodgement.getAmount() < customerTotalInitialPayment)
       {
           errorMSG.append("The Lodgement Amount is invalid");
           return false;
       }
       
       
      return true;
  }
}
