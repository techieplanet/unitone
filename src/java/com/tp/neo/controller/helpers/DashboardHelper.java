/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tp.neo.model.Account;
import com.tp.neo.model.Auditlog;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.Plugin;
import com.tp.neo.model.Project;
import com.tp.neo.model.ProjectUnit;
import com.tp.neo.model.Transaction;
import com.tp.utils.DateFunctions;
import java.time.LocalDate;
import java.time.Month;
import java.time.Period;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author swedge-mac
 */
public class DashboardHelper {
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
    EntityManager em = emf.createEntityManager();
    Query query;
    
    Gson gson = new GsonBuilder().create();
    HashMap<String, Plugin> availablePlugins;
    
    private final int TRACKBACK_DAYS = 150;
    
    public DashboardHelper(){
        emf.getCache().evictAll();
    }
    
    public DashboardHelper(HashMap<String, Plugin> availablePlugins){
        emf.getCache().evictAll();
        this.availablePlugins = availablePlugins;
    }
    
    
    
    public double getTotalDuePayments(){
        double totalDue = 0;
        
        
        em = emf.createEntityManager();
        query = em.createNamedQuery("OrderItem.findByApprovedLodgementItemsSum");
        query.setParameter("aps", 1);
        query.setParameter("item_aps", 1);
        List<Object[]> itemAndAmountList = query.getResultList();
        
        for(Object[] itemAndAmount : itemAndAmountList){
            ////////System.out.println("Inside getduepayemnts");
            OrderItem orderItem = (OrderItem)itemAndAmount[0];
            double paidSum = (Double)itemAndAmount[1];
            
            Date orderItemApprovalDate = orderItem.getApprovalDate();
            
            if(orderItemApprovalDate == null)
            {
                em.getTransaction().begin();
                orderItem.setApprovalStatus((short)0);
                em.persist(orderItem);
                Auditlog audit = new Auditlog();
                audit.setActionName("Order Item Approval");
                audit.setNote("The order item with id " + orderItem.getId() + " Have been set as approved , with a null approval date");
                audit.setLogDate(new Date(System.currentTimeMillis()));
                audit.setUserId(0L);
                audit.setUsertype(1);
                em.persist(audit);
                em.getTransaction().commit();
                continue;
            }
            
            int monthsElapsed = DateFunctions.getNumberOfMonthsBetweenDates(orderItemApprovalDate, new Date());
            double expectedMortgageTotal = orderItem.getUnit().getMonthlyPay() * monthsElapsed;
            
            if(expectedMortgageTotal > paidSum)
                totalDue += expectedMortgageTotal - paidSum;
        }
    
        return totalDue;
    }
    
    
    public HashMap<String, Number> getTotalAgentDebts(){
        double totalDebt = 0;
        int ordersCount = 0;
        Set<Long> ordersSet = new HashSet<Long>();
        
        em = emf.createEntityManager();
        query = em.createNamedQuery("OrderItem.findByApprovedLodgementItemsSum");
        query.setParameter("aps", 1);
        query.setParameter("item_aps", 1);
        List<Object[]> itemAndAmountList = query.getResultList();
        
        for(Object[] itemAndAmount : itemAndAmountList){
            //////////System.out.println("Inside getduepayemnts");
            OrderItem orderItem = (OrderItem)itemAndAmount[0];
            double paidSum = (Double)itemAndAmount[1];
            
            Date orderItemApprovalDate = orderItem.getModifiedDate();
            int monthsElapsed = DateFunctions.getNumberOfMonthsBetweenDates(orderItemApprovalDate, new Date());
            double expectedMortgageTotal = orderItem.getUnit().getMonthlyPay() * monthsElapsed;
            
            if(expectedMortgageTotal > paidSum){
                totalDebt += expectedMortgageTotal - paidSum;
                ordersSet.add(orderItem.getOrder().getId());
            }
        }
    
        HashMap<String, Number> returnMap = new HashMap<String, Number>();
        returnMap.put("totalDebt", totalDebt);
        returnMap.put("customerCount", ordersSet.size());
        
        return returnMap;
    }
    
    
    
    /**
     * This will return the sum of all items that have been sold but the money has not been gotten (includes the due payments)
     * This is the difference of total order items approved (expected) and total lodgements approved (received)
     * @return double
     */
    
    public double getTotalOutstandingPayments(){
        query = em.createNamedQuery("OrderItem.findMyTotalOutstandingAmountPerOrderItem");
        query.setParameter("aps", 1);
        List<Object[]>  outstandingItems = query.getResultList();
        
        double totalPayments = 0;
        
        for(Object[] outObjects : outstandingItems){
            totalPayments += (double)outObjects[1];
        }
        
        return totalPayments;
    }
    /*
    public double getTotalOutstandingPayments(){
        query = em.createNamedQuery("OrderItem.findTotalApprovedSum");
        query.setParameter("approvalStatus", 1);
        double approvedOrderItemSum = (Double)query.getSingleResult();
        
        query = em.createNamedQuery("LodgementItem.findTotalApprovedSum");
        query.setParameter("approvalStatus", 1);
        double approvedLodgementItemSum = (Double)query.getSingleResult();
        
        double totalOutstanding = approvedOrderItemSum - approvedLodgementItemSum;
        
        return totalOutstanding;
    }
    */
    
    public double getTotalCommissionsPayable(){
        query = em.createNamedQuery("AgentBalance.findAllBalanceSum");
        double balanceSum = (Double)query.getSingleResult();
        ////////System.out.println("Commissions Payable: " + balanceSum);
        return balanceSum;
    }
    
    
    public double getTotalStockValue(){
        double totalStockValue = 0;
        List<ProjectUnit> units = em.createNamedQuery("ProjectUnit.findAll").getResultList();
        for(ProjectUnit unit : units){
            
            int quantitySold = 0;
            List<OrderItem> items = (List)unit.getOrderItemCollection();
            for(OrderItem item : items){
                if(item.getApprovalStatus() == 0) continue;
                quantitySold += item.getQuantity();
            }
            
            totalStockValue += unit.getCpu() * (unit.getQuantity() - quantitySold);
            
        }

        return totalStockValue;
    }
    
    
    /**
     * This retrieves all the projects and their units. 
     * units are map objects in an ArrayList that are then put into corresponding projects map which in turn are put into a projects ArrayList
     * @return List<HashMap>
     */
    public List<HashMap> getProjectsPerformanceByStockSold(){
        List<Object[]> performance = em.createNativeQuery("SELECT p.id, p.name, pno.puid, pno.puname, pno.sold, pno.stock AS setupstock "
                                                            + "FROM project p LEFT JOIN "
                                                            + "(SELECT pu.title AS puname, pu.id AS puid, project_id, COALESCE(SUM(o.quantity),0) AS sold, pu.quantity as stock FROM project_unit pu LEFT JOIN order_item o ON pu.id = o.unit_id AND approval_status = 1 GROUP BY pu.id)" 
                                                            + "As pno ON p.id = pno.project_id WHERE p.deleted = 0"
                                                            + "ORDER By p.id, puid"
                                                          ).getResultList();
        
        
        //for(Object[] perf : performance){
          //  //////////System.out.println("ID: " + perf[0] + ", Project: " + perf[1] + ", Unit ID: " + perf[2] + ", Unit: " +  perf[3] + ", Quantity: " + perf[4] + ", Stock: " + perf[5]);
        //}
        
        
        int i = 0;
        List<HashMap> projectMapsList = new ArrayList<HashMap>();
        while(i < performance.size()){
            int projectId = (Integer)performance.get(i)[0]; //get first Object[] and the first array element in it
            HashMap projectMap = new HashMap();
            projectMap.put("projectName", performance.get(i)[1]);
            
            int projectSold = 0, projectSetupStock = 0;
            List<HashMap> unitMapsList = new ArrayList<HashMap>();
            
            
            while((Integer)performance.get(i)[0] == projectId){
                if(performance.get(i)[3] != null && performance.get(i)[4] != null){
                    HashMap unitMap = new HashMap();
                    unitMap.put("name", performance.get(i)[3]);
                    unitMap.put("sold", (Long)performance.get(i)[4]);
                    unitMap.put("setupStock", (Integer)performance.get(i)[5]);
                    double percentage = (double)(Long)performance.get(i)[4] / (Integer)performance.get(i)[5] * 100;
                    unitMap.put("percentage", percentage);
                    unitMapsList.add(unitMap);  //add a map to the list for this project. Represents one unit

                    projectSold += (Long)performance.get(i)[4];
                    projectSetupStock += (Integer)performance.get(i)[5];
                }
                else if(performance.get(i)[3] != null){
                    HashMap unitMap = new HashMap();
                    unitMap.put("name", performance.get(i)[3]);
                    unitMap.put("sold", 0);
                    unitMap.put("setupStock", 0);
                    double percentage = 0;
                    unitMap.put("percentage", percentage);
                    unitMapsList.add(unitMap);  //add a map to the list for this project. Represents one unit
                    
                    projectSetupStock += (Integer)performance.get(i)[5];
                }
                
                i++;
                if(i == performance.size()) break;
            }//.inner while
            
            //finish project level data
            projectMap.put("sold", projectSold);
            projectMap.put("setupStock", projectSetupStock);
            double percentage = projectSetupStock !=0 ? (double)projectSold / projectSetupStock * 100 : 0;  //anti division by zero error
           ////////System.out.println("Project Name: " + performance.get(i-1)[1] + " projectSold " + projectSold + " projectSetupStock " + projectSetupStock + " project percentage: " + percentage);
            projectMap.put("projectPercentage", percentage);
            projectMap.put("units", unitMapsList);
            projectMapsList.add(projectMap);
            
            
        }//.outer while
        
//        Gson gson = new GsonBuilder().create();
//        String jsonResponse = gson.toJson(projectMapsList);
//        //////////System.out.println("JSON: " + jsonResponse);        
//        //////////System.out.println("set length: " + performance.size());
        
        
        return projectMapsList;
    }
    
    
    
    
    /**
     * This retrieves all the projects and their units. 
     * units are map objects in an ArrayList that are then put into corresponding projects map which in turn are put into a projects ArrayList
     * @return List<HashMap>
     */
    public List<HashMap> getProjectsPerformanceBySalesQuota(){
        List<Object[]> projectsPerformanceList = em.createNamedQuery("OrderItem.findSalesSumByProject")
                                                                    .setParameter("item_aps", 1)
                                                                    .setParameter("aps", 1)
                                                                    .getResultList();
       ////////System.out.println("Length of projects: " + projectsPerformanceList.size());
        
        //get the totals for all projects
        long totalProjectsSalesQuotaStock = 0;
        double totalProjectsSalesQuotaValue = 0;
        for(Object[] projectDetail : projectsPerformanceList){
            totalProjectsSalesQuotaStock += (Long)projectDetail[1];
            totalProjectsSalesQuotaValue += (double)projectDetail[2];
        }
        
        List<HashMap> projectMapsList = new ArrayList<HashMap>();
        
        for(Object[] projectDetail : projectsPerformanceList){
            Project project = (Project)projectDetail[0];
            Long projectId = project.getId();
            
            //get the units for this project with its their sales stock and value sums
            List<Object[]> unitsPerformanceList = em.createNamedQuery("OrderItem.findSalesSumByProjectUnit")
                                                                            .setParameter("projectId", project.getId())
                                                                            .setParameter("item_aps", 1)
                                                                            .setParameter("aps", 1)
                                                                            .getResultList();
           ////////System.out.println("Length of units: " + unitsPerformanceList.size() + ", project: " + project.getName());
            //get the totals for all units in this project
            long totalUnitsSalesQuotaStock = 0;
            double totalUnitsSalesQuotaValue = 0;
            for(Object[] unitDetail : unitsPerformanceList){
                totalUnitsSalesQuotaStock += (Long)unitDetail[1];
                totalUnitsSalesQuotaValue += (double)unitDetail[2];
            }
            
            List<HashMap> unitMapsList = new ArrayList<HashMap>();  //store all the units for a project here
            
            for(Object[] unitDetail : unitsPerformanceList){
                ProjectUnit unit = (ProjectUnit)unitDetail[0];
                HashMap unitMap = new HashMap();
                unitMap.put("unitName", unit.getTitle());
                
                if(totalUnitsSalesQuotaStock > 0)
                    unitMap.put("stockPercentage", (long)unitDetail[1] / totalUnitsSalesQuotaStock * 100);
                else
                    unitMap.put("stockPercentage", 0);
                if(totalUnitsSalesQuotaStock > 0)
                    unitMap.put("valuePercentage", (double)unitDetail[2] / totalUnitsSalesQuotaValue * 100);
                else
                    unitMap.put("valuePercentage", 0);

                unitMapsList.add(unitMap);
            }
            
            
            HashMap projectMap = new HashMap();
            projectMap.put("projectName", project.getName());
            if(totalProjectsSalesQuotaStock > 0)
                projectMap.put("stockPercentage", (long)projectDetail[1] / totalProjectsSalesQuotaStock * 100);
            else 
                projectMap.put("stockPercentage",0);
            
            if(totalProjectsSalesQuotaValue> 0)
                projectMap.put("valuePercentage", (double)projectDetail[2] / totalProjectsSalesQuotaValue * 100);
            else
                projectMap.put("valuePercentage",0);
            projectMap.put("units", unitMapsList);
            
            //add this project to the projects list
            projectMapsList.add(projectMap);
            
        }
        
//        Gson gson = new GsonBuilder().create();
//        String jsonResponse = gson.toJson(projectMapsList);
//        //////////System.out.println("JSON: " + jsonResponse);        

        return projectMapsList;
    }
    
    
    public String getOrderSummary() throws Exception{
        Date startDate = DateFunctions.getDateAfterSubtractDays(TRACKBACK_DAYS);
        Date endDate = new Date();
        return this.getOrderSummary("day", "DD-Mon-YYYY", startDate, endDate);
    }
    
    public String getOrderSummary(String truncatedBy, String truncationResultFormat, Date startDate, Date endDate) throws Exception{
        //Redeclare here as these variables will not be available in AJAX mode
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String extractor = "";
        if(truncatedBy.equalsIgnoreCase("day"))
            extractor = "doy";
        else if(truncatedBy.equalsIgnoreCase("month"))
            extractor = "month";
        else if(truncatedBy.equalsIgnoreCase("year"))
            extractor = "year";
    
        String query = "SELECT COUNT(DISTINCT(o.order_id)) as orderscount, SUM(o.quantity * u.amount_payable) as ordersvalue, " +
                                                                           "to_char(date_trunc('" + truncatedBy + "', o.modified_date),'" + truncationResultFormat + "') as grouper, " +
                                                                           "EXTRACT(" + extractor + " from o.modified_date) AS orderer " +
                                                                           "FROM order_item o JOIN project_unit u ON o.unit_id = u.id " +
                                                                           "WHERE o.approval_status = 1 AND (date(o.modified_date) >= '" + startDate.toString() + "' AND date(o.modified_date) <= '" + endDate.toString() + "') " +
                                                                           "GROUP BY orderer, grouper ORDER BY orderer";
        ////////////System.out.println("Order query: " + query);
        List<Object[]> summaryObjects = em.createNativeQuery(query).getResultList();
        
        List<HashMap> summaryMapsList = new ArrayList<HashMap>();
        
        for(Object[] summary : summaryObjects){
            HashMap summaryMap = new HashMap();
            summaryMap.put("count", summary[0]);
            summaryMap.put("value", summary[1]);
            summaryMap.put("date", summary[2]);
            summaryMapsList.add(summaryMap);
        }
        
        return gson.toJson(summaryMapsList);
                            
    }
    
    
    
    public String getLodgementSummary() throws Exception{
        Date startDate = DateFunctions.getDateAfterSubtractDays(TRACKBACK_DAYS);
        Date endDate = new Date();
        return this.getLodgementSummary("day", "DD-Mon-YYYY", startDate, endDate);
    }
    
    
    public String getLodgementSummary(String truncatedBy, String truncationResultFormat, Date startDate, Date endDate) throws Exception{
        //Redeclare here as these variables will not be available in AJAX mode
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();
        
        String loyaltyQueryHook = "";
        //if( availablePlugins.containsKey("loyalty")) 
            loyaltyQueryHook = "SUM(l.reward_amount) as rvalue ";
        
        String query = "SELECT COUNT(DISTINCT(l.lodgement_id)) as lcount, SUM(l.amount+ l.reward_amount) as lvalue, " +
                       "to_char(date_trunc('" + truncatedBy + "', l.modified_date),'" + truncationResultFormat + "') as grouper " +
                       (!loyaltyQueryHook.isEmpty()? ", " + loyaltyQueryHook : " "  )+ " " +
                       "FROM lodgement_item l " +
                       "WHERE l.approval_status = 1 AND (date(l.modified_date) >= '" + startDate.toString() + "' AND date(l.modified_date) <= '" + endDate.toString() + "') " +
                       "GROUP BY grouper";
        
        List<Object[]> summaryObjects = null;
        
        try{
           summaryObjects =  em.createNativeQuery(query).getResultList();
        } catch(Exception e){
            e.printStackTrace();
            return " ";
        }
        
        List<HashMap> summaryMapsList = new ArrayList<HashMap>();
        
        for(Object[] summary : summaryObjects){
            HashMap summaryMap = new HashMap();
            summaryMap.put("count", summary[0]);
            summaryMap.put("value", summary[1]);
            summaryMap.put("date", summary[2]);
            //if(availablePlugins.containsKey("loyalty")) 
                summaryMap.put("loyalty_value", summary[3]);
            summaryMapsList.add(summaryMap);
        }
        
        return gson.toJson(summaryMapsList);
        
    }
    
    
    /**
     * Get the top locations where agents are performing
     */
    public List<HashMap> getTopFiveAgentLocations(){
        List<Object[]> locationObjects = em.createNamedQuery("Agent.findByTopSellingLocations").getResultList();
        HashMap agentMap = new HashMap();
        List<HashMap> agentMapList = new ArrayList();
        for(int i=0; i < locationObjects.size(); i++){
            if(i > 4) break;
            agentMap.put("count", locationObjects.get(i)[0]);
            agentMap.put("state", locationObjects.get(i)[1]);
            agentMapList.add(agentMap);
        }
        
        return agentMapList;
    }
    
    public double getTotalIncome(){
        Account income = (Account)(Account)em
                        .createNamedQuery("Account.findByAccountCode")
                        .setParameter("accountCode", "INCOME")
                        .getSingleResult();
        double credit =  getTransactionSum(income.getTransactionCollection());
        double debit = getTransactionSum(income.getTransactionCollection1());
        
        if(debit > 0)
            return credit - debit;
        else
            return credit;
    }
    
    public double getTotalWithHoldingTax(){
         Account withHoldingTax = (Account)(Account)em
                        .createNamedQuery("Account.findByAccountCode")
                        .setParameter("accountCode", "AGENT_WITHHOLDING")
                        .getSingleResult();
        
        double credit =  getTransactionSum(withHoldingTax.getTransactionCollection());
        double debit = getTransactionSum(withHoldingTax.getTransactionCollection1());
        
        if(debit > 0)
            return credit - debit;
        else
            return credit;
    }
    
    public double getTotalAnnualMaintenance(){
        Account annualMaintenance = (Account)(Account)em
                        .createNamedQuery("Account.findByAccountCode")
                        .setParameter("accountCode", "AGENT_ANNUAL_MAINTENANCE")
                        .getSingleResult();
        double credit =  getTransactionSum(annualMaintenance.getTransactionCollection());
        double debit = getTransactionSum(annualMaintenance.getTransactionCollection1());
        
        if(debit > 0)
            return credit - debit;
        else
            return credit;
    }
    
    public double getTotalVat(){
        Account VAT = (Account)(Account)em
                        .createNamedQuery("Account.findByAccountCode")
                        .setParameter("accountCode", "VAT")
                        .getSingleResult();
       double credit =  getTransactionSum(VAT.getTransactionCollection());
        double debit = getTransactionSum(VAT.getTransactionCollection1());
        
        if(debit > 0)
            return credit - debit;
        else
            return credit;
    }
    
    public double getTransactionSum(Collection<Transaction> transactions){
        double sum = 0;
        for(Transaction t : transactions)
        {
            sum += t.getAmount();
        }
        
        return sum;
    }
    
    public static  void main(String[] args){
         
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        //////////System.out.println("Year: " + calendar.get(Calendar.YEAR));
        
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2013, 0, 20);
        //////////System.out.printf("Year %s, Month %s, Day %s: \n", calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH), calendar2.get(Calendar.DAY_OF_MONTH));
        
        LocalDate l = LocalDate.of(2015, Month.OCTOBER, 25);
        //////////System.out.println(l.toString());

        LocalDate l2 = LocalDate.of(calendar2.get(Calendar.YEAR), calendar2.get(Calendar.MONTH)+1, calendar2.get(Calendar.DAY_OF_MONTH));
        //////////System.out.println(l2.toString());
        
        Period period  = Period.between(l, l2);
        //////////System.out.println("Period Months: " + Math.abs(period.toTotalMonths()));
        
    }

}