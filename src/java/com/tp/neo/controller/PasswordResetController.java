/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller;

import com.tp.neo.controller.components.AppController;
import com.tp.neo.controller.helpers.EmailHelper;
import com.tp.neo.interfaces.SystemUser;
import com.tp.neo.model.Company;
import com.tp.neo.model.Customer;
import com.tp.neo.model.PasswordReset;
import com.tp.neo.model.utils.AuthManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.catalina.tribes.util.Arrays;
import org.apache.commons.codec.binary.Base64;

/**
 *
 * @author SWEDGE
 */
@WebServlet(name = "PasswordReset", urlPatterns =
{
    "/PasswordReset"
})
public class PasswordResetController extends AppController {

    private static String VIEW_NAME = "/views/Reset_Password/Reset.jsp";

    private static boolean debug = true;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (debug)
        {
            request.getParameterMap().forEach((String name, String Value[]) ->
            {
                //System.out.println("Parameter Name : " + name + "  Values: " + Arrays.toString(Value));
            });
        }

        //<editor-fold>
        //Check if The t Parameter is present 
        if (request.getParameter("t") != null && !request.getParameter("t").isEmpty())
        {
            //here We Know That the user Just Click the Validation Token Sent 
            //To His/her Email
            List<String> errors = new ArrayList<>();
            if (validate(request, errors))
            {
                //Here We know that the validation is successFull 
                request.setAttribute("form", 2);
                request.setAttribute("t", request.getParameter("t"));
            }
            else
            {
                request.setAttribute("form", 1);
                request.setAttribute("errors", errors);
            }
        }
        else
        {
            //Here We Know that we are still in The first Form
            request.setAttribute("form", 1);
        }
        //</editor-fold>
        request.getRequestDispatcher(VIEW_NAME).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (debug)
        {
            request.getParameterMap().forEach((String name, String Value[]) ->
            {
                //System.out.println("Parameter Name : " + name + "  Values: " + Arrays.toString(Value));
            });
        }

        String form = request.getParameter("form");

        //<editor-fold>
        if (form != null)
        {
            int formID = 0;

            try
            {
                formID = Integer.parseInt(form);
            } catch (Exception e)
            {
                if (debug)
                {
                    e.printStackTrace();
                }
                //Lets Just dump The Hacker
                return;
            }

            if (formID == 1) //Its Good to be specific bcus of security issues
            {
                proceessInputAndSendMail(request, response);
            }
            else if (formID == 2) //Its Good to be specific bcus of security issues ie. this is better than "else" statement only
            {
                proccessPasswordReset(request, response);
            }

        }//</editor-fold>

        request.getRequestDispatcher(VIEW_NAME).forward(request, response);
    }

    /**
     * This method validate the user input. return false if the request or
     * details couldn't be found in the database or if there are some errors
     * found while validating . The errors parameter holds all the error which
     * are being detected by the method
     *
     * validation done by this method
     *
     * --User Type --Email --Reset Token --Password --Confirm PassWord Note:
     * this method only validate the field above only if they are in the query
     *
     * @param request
     * @param errors
     * @return
     */
    private boolean validate(HttpServletRequest request, List<String> errors) {

        //Local variable
        //<editor-fold>
        boolean validateSuccess = true;

        EntityManager em = AppController.emf.createEntityManager();
        //get the userTypeid 
        String userType = request.getParameter("userType");

        //get the email
        String email = request.getParameter("email");

        //get the restToken
        String t = request.getParameter("t");

        //get the password
        String password = request.getParameter("password");

        //get the confirm password 
        String confirmPassword = request.getParameter("confirmPassword");

        //User type id
        Long typeID = null;
        //</editor-fold>

        /**
         * ******Lets validate the user email*********
         */
        //<editor-fold>
        {
            if (userType != null)
            {
                if (!userType.isEmpty())
                {
                    try
                    {
                        typeID = Long.valueOf(userType);
                    } catch (Exception e)
                    {
                        if (debug)
                        {
                            e.printStackTrace();
                        }
                        //here we know the user has submitted a bad typeID 
                        errors.add("Invalid User Type");
                        validateSuccess = false;
                    };

                    //Lets check if the typeID selected is not within the range of 1 - 3
                    if (typeID != null)
                    {
                        if (typeID < 1 || typeID > 3)
                        {
                            errors.add("Please Select a User Type");
                            validateSuccess = false;
                        }
                    }
                }
                else
                {
                    validateSuccess = false;
                    errors.add("Please Select a User Type");
                }
            }
        }
        //</editor-fold>

        /**
         * *******We are validating email here *******
         */
        /**
         * ******why we start with a validation test is that this code block
         * depend ****
         */
        /**
         * *******On the first validation which is the user type . so its
         * advisable we just go on this way *******
         */
        //<editor-fold >
        {
            if (validateSuccess
                    && email != null
                    && typeID != null)
            {
                //// Validating Email here 

                if (!email.isEmpty())
                //<editor-fold>
                {
                    email = email.toLowerCase();
                    Query query = null;

                    if (typeID == 1)//Admin User
                    {
                        query = em.createNamedQuery("User.findByEmail");
                    }
                    else if (typeID == 2)//Agent
                    {
                        query = em.createNamedQuery("Agent.findByEmail");
                    }
                    else if (typeID == 3)//Customer
                    {
                        query = em.createNamedQuery("Customer.findByEmail");
                    }

                    query.setParameter("email", email);

                    SystemUser user = null;//(SystemUser)query.getSingleResult();

                    try
                    {
                        user = (SystemUser) query.getSingleResult();
                    } catch (Exception e)
                    {
                        if (debug)
                        {
                            e.printStackTrace();
                        }

                        validateSuccess = false;
                        errors.add("No matching record found , Please try again");
                    }
                }
                //</editor-fold>
                else
                {
                    validateSuccess = false;
                    errors.add("Please enter Email");
                }
            }
        }
        //</editor-fold>

        /**
         * **Lets Move toward to validating the t *****
         */
        //<editor-fold>
        {
            if (t != null)
            {
                if (!t.isEmpty())
                //<editor-fold>
                {
                    t = new String(Base64.decodeBase64(t));
                    PasswordReset pReset = null;

                    //TODO
                    //first decrypt the Token Script from base 64;
                    //Check if the t String is Present in dataBase
                    Query query = em.createNamedQuery("PasswordReset.findByToken");
                    query.setParameter("token", t);

                    try
                    {
                        pReset = (PasswordReset) query.getSingleResult();
                    } catch (Exception e)
                    {
                        if (debug)
                        {
                            e.printStackTrace();
                        }
                        validateSuccess = false;
                        errors.add("This is an Invalid Token");
                    }

                    //let check if the t has expired 
                    if (pReset != null)
                    {
                        //Check if the t is more than 24 hours
                        Long currentTime = System.currentTimeMillis();

                        //Current Date
                        Date cDate = new Date(currentTime);
                        //Past date
                        Date pDate = pReset.getExpiryDate();
                        if (cDate.after(pDate))
                        {
                            //The t has Expired
                            validateSuccess = false;
                            errors.add("Sorry this Token Has Expired . You Can Fill in This Form to get new t");
                            //Automatically make the user go to form one
                            request.setAttribute("form", 1);
                        }
                    }
                } //</editor-fold>
                else
                {
                    validateSuccess = false;
                    //We Just Have to fustrate the Life of an hacker rigth here
                    //So we confuse Him/her for an error
                    errors.add("Sorry There is an error during Validation");
                }
            }
        }//</editor-fold>

        /**
         * **Lets Validate The Password ********
         */
        //<editor-fold>
        {   //this validation depends on the t validation
            if (validateSuccess && password != null)
            {
                if (password.isEmpty())
                {
                    validateSuccess = false;
                    errors.add("Please Enter Password Feild");
                }
                if (confirmPassword != null && confirmPassword.isEmpty())
                {
                    validateSuccess = false;
                    errors.add("Please Enter Confirm Password Feild");
                }

                //Just to prevent NullPointer in Future to come
                if (!password.isEmpty() && confirmPassword != null && !confirmPassword.isEmpty())
                {
                    //if confirm password is null or Empty
                    if (!(confirmPassword != null && !confirmPassword.isEmpty()))
                    {
                        validateSuccess = false;
                        errors.add("Please Enter the Confirm Password Feild");
                    }
                    else // we move forward to check if the password feild is equals to the Connfirm password feild
                    {
                        if (!password.equals(confirmPassword))
                        {
                            // If Not equal
                            validateSuccess = false;
                            errors.add("Passwords does not match");
                        }
                    }
                }
            }
        }//</editor-fold>

        return validateSuccess;
    }

    /**
     * This Method does the task of creating a reset Token and it goes forward
     * to sending the t to the customer Email
     *
     * @param request
     * @param response
     */
    private void proceessInputAndSendMail(HttpServletRequest request, HttpServletResponse response) {
        /**
         * **form 1 processing ***
         */

        List<String> errors = new ArrayList<>();

        if (!validate(request, errors))
        {
            //Then there is a validation Error 
            request.setAttribute("form", 1);
            request.setAttribute("errors", errors);
            return;
        }

        //Now lets Pick the query String that we need
        //Since it's save to work with em now
        
        //get the userTypeid 
        String userType = request.getParameter("userType");
        //get the email
        String email = request.getParameter("email");
        email = email.toLowerCase();
        //Entity Manger
        EntityManager em = AppController.emf.createEntityManager();
        Company company = em.find(Company.class, 1);
        
        SystemUser user = null;
        Query query = null;
        
        switch(Integer.parseInt(userType))
        {
            case 1:
                query = em.createNamedQuery("User.findByEmail");
                break;
            
            case 2:
                query = em.createNamedQuery("Agent.findByEmail");
                break;
                
            case 3:
                query = em.createNamedQuery("Customer.findByEmail");
                break;
        }
       
           user =  (SystemUser) query.setParameter("email", email)
                .getSingleResult();
        
        //Password Reset
        PasswordReset pReset = new PasswordReset();
        //Reset Token
        String rToken = generateResetToken();
        //Expiry Date
        Date exdate = new Date(System.currentTimeMillis() + 86400000);//24 hours time;
        
        //setting up parameters
        pReset.setEmail(email);
        pReset.setExpiryDate(exdate);
        pReset.setToken(rToken);
        pReset.setUserType(Short.parseShort(userType));
        
        String Url = "http://"+request.getServerName()+request.getContextPath()+"/PasswordReset?t="+ Base64.encodeBase64URLSafeString(rToken.getBytes());
        
        EmailHelper.sendPasswordResetLink(email, company.getEmail(), Url, user.getFirstname() , company.getName());
        
        em.getTransaction().begin();
        em.persist(pReset);
        em.getTransaction().commit();
        em.close();
        
        request.setAttribute("form", 4);
        request.setAttribute("succes", true);
    }

    /**
     * This method change the user password in the database with the new
     * password specified
     *
     * @param request
     * @param response
     */
    private void proccessPasswordReset(HttpServletRequest request, HttpServletResponse response){
        try
        {
            /**
             * *******form 2 processing ***********
             */
            List<String> errors = new ArrayList<>();
            
            if (!validate(request, errors))
            {
                //Then there is a validation Error
                //And The form attribute is not set
                if(request.getAttribute("form") == null)
                {
                    request.setAttribute("form", 2);
                     request.setAttribute("errors", errors);
                    request.setAttribute("t", request.getParameter("t"));
                }
                else
                {
                    //Here we know that there is a t error so we go to form one which is already 
                    //Set inside the Validate method 
                     //We proccedd  to form one
                    request.setAttribute("errors", errors);
                }
                
                return;
            }
            
            //Remember to base64 decode the String
            
            //Lets map out the Local variable we will be Using
            //Since its safe to use them now
            //get the restToken
            String t = new String(Base64.decodeBase64(request.getParameter("t")));
            
            //get the password
            String password = request.getParameter("password");
            //localhost/NeoForce/PasswordReset?t=MjA2MTE3MTgxMDQyMDk=
            EntityManager em = AppController.emf.createEntityManager();
            em.getTransaction().begin();
            PasswordReset pReset = (PasswordReset)em.createNamedQuery("PasswordReset.findByToken").setParameter("token", t).getSingleResult();
            
            //Now lets get the User
            SystemUser user = null;
            Query query = null;
            
            switch(pReset.getUserType())
            {
                case 1: //Admin user
                    query = em.createNamedQuery("User.findByEmail");
                    break;
                case 2: //Agent User
                    query = em.createNamedQuery("Agent.findByEmail");
                    break;
                case 3: //Customer
                    query = em.createNamedQuery("Customer.findByEmail");
                    break;
            }
            
            //Set The the date to now so that the user cant use the t anymore
            Date now = new Date(System.currentTimeMillis());
            pReset.setExpiryDate(now);
            
            query.setParameter("email", pReset.getEmail());
            user = (SystemUser) query.getSingleResult();
            user.setPassword(AuthManager.getSaltedHash(password));
            
            //Persist the objects
            em.persist(pReset);
            em.persist(user);
            em.getTransaction().commit();
            request.setAttribute("form", 3);
        } catch (Exception ex)
        {
            Logger.getLogger(PasswordResetController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private String generateResetToken() {
        
        // The Format for reset t i reset_t – 15: ddmmyyhms + 9 random numbers
        //made up of 15 numbers 
        //day month year hour minute sec + extra 9 randon number 
        // I hope you get that -- time for implementation --@{0^0}@
        
         double rand = Math.random();

        String rString = Double.toString(rand).substring(3);
        Date date = new Date(System.currentTimeMillis());
        rString = String.format("%d%d%d%d%d%d%s",date.getDate(),
                 date.getMonth(),
                date.getYear(),
                date.getHours(),
                date.getMinutes(),
                date.getSeconds(),
                rString);
        //Not Properly Implemented but you can help to do it better
        return rString.substring(0, 14);
        }
}
