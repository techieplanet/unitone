/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tp.neo.controller.helpers;

import com.tp.neo.model.Customer;
import com.tp.neo.model.LodgementItem;
import com.tp.neo.model.OrderItem;
import com.tp.neo.model.ProductOrder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author hp
 */
public class OrderItemHelper {

    /**
     *
     * @param List<lodgementItem>
     * @return Double Total amount paid for an OrderItem This method returns the
     * total amount paid for an item, By looping through a collection of
     * LodgementItem
     */
    public Double getTotalItemPaidAmount(List<LodgementItem> lodgementItem) {

        double totalAmount = 0.00;

        for (LodgementItem item : lodgementItem) {
             if(item.getLodgement().getApprovalStatus() == 0)
                 continue;
             
            totalAmount += item.getAmount();
        }

        return totalAmount;
    }

    public String getOrderItemBalance(double amtPayable, int qty, double amountPaid) {

        String amt = String.format("%.2f", (Double) ((amtPayable * qty) - amountPaid));
        return amt;
    }
    
    public double getOrderItemBalance(double amtPayable, int qty, double amountPaid, String returnType) {

        double amt = (Double) ((amtPayable * qty) - amountPaid);
        return amt;
    }

    public String getOrderItemDiscount(double discountPercent, double cpu, int qty) {

        String amt = String.format("%.2f", (Double) ((discountPercent / 100) * (cpu * qty)));
        return amt;
    }

    public String getCompletionDate(OrderItem item, double total_paid) {

        //Get monthly Pay
        double mortgage = item.getUnit().getMonthlyPay();
        double balance = Double.parseDouble(getOrderItemBalance(item.getUnit().getAmountPayable(), item.getQuantity(), total_paid));
        int qty = item.getQuantity();

        double months = Math.ceil(balance / (mortgage * qty));
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");
        String date = "";

        if (months <= 0) {

           //Date d = item.getOrder().getModifiedDate();
            //cal.setTime(d);
            date = "Completed";
        } else {

            cal.add(Calendar.MONTH, (int) months);
            date = sdf.format(cal.getTime());
        }

        System.out.println("Month = " + (int) months);

        return date;
    }

    /**
     * This method returns the payment stage in String type
     *
     * @param item <OrderItem>
     * @param total_paid <double>
     * @return String
     */
    public String getPaymentStage(OrderItem item, double total_paid) {

        double mortgage = item.getUnit().getMonthlyPay();
        Date date = item.getCreatedDate();
        int qty = item.getQuantity();

        double initialDeposit = item.getInitialDep();
        double months = 0;

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

        String dateString = "";

        if ((total_paid - initialDeposit) <= 0) {

            cal.setTime(date);
            return sdf.format(cal.getTime());
        } else {
            months = Math.floor((total_paid - item.getInitialDep()) / (mortgage * qty));
        }

        if (months <= 0) {

            //cal.setTime(item.getOrder().getModifiedDate());
            dateString = "Completed";
        } else {
            cal.setTime(date);
            cal.add(Calendar.MONTH, (int) months);

            dateString = sdf.format(cal.getTime());
        }

        return dateString;

    }

    /**
     * This method returns the payment stage in Date type
     *
     * @param item <OrderItem>
     * @param total_paid <double>
     * @param bool <Boolean>
     * @return Date
     */
    public Date getPaymentStage(OrderItem item, double total_paid, boolean bool) {

        double mortgage = item.getUnit().getMonthlyPay();
        Date date = item.getCreatedDate();
        int qty = item.getQuantity();

        double initialDeposit = item.getInitialDep();
        double months = 0;

        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));

        SimpleDateFormat sdf = new SimpleDateFormat("MMMM yyyy");

        String dateString = "";

        //check if the user has only paid the initial deposit
        if ((total_paid - initialDeposit) <= 0) {

            cal.setTime(date);
            return cal.getTime();
        } else {
            months = Math.floor((total_paid - item.getInitialDep()) / (mortgage * qty));
        }

        if (months <= 0) {

            return cal.getTime();
        } else {
            cal.setTime(date);
            cal.add(Calendar.MONTH, (int) months);

            dateString = sdf.format(cal.getTime());
        }

        return cal.getTime();

    }

    public double getOrderValue(long orderId) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        ProductOrder order = em.find(ProductOrder.class, orderId);

        Query query = em.createNamedQuery("OrderItem.findByOrder").setParameter("order", order);

        List<OrderItem> items = query.getResultList();

        double orderValue = 0;

        for (OrderItem item : items) {

            orderValue += item.getUnit().getAmountPayable() * item.getQuantity();
        }

        return orderValue;
    }

    public boolean isCustomerDefaulter(Customer customer) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("NeoForcePU");
        EntityManager em = emf.createEntityManager();

        Query query = em.createNamedQuery("OrderItem.findByCustomer").setParameter("customer", customer);

        List<OrderItem> items = query.getResultList();

        Calendar calStage = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));
        Calendar calNow = Calendar.getInstance(TimeZone.getTimeZone("Africa/Lagos"));

        for (OrderItem item : items) {

            double totalPaid = getTotalItemPaidAmount((List<LodgementItem>) item.getLodgementItemCollection());

            Date stage = getPaymentStage(item, totalPaid, true);

            calStage.setTime(stage);
            calNow.getTime();

            if (calStage.get(Calendar.YEAR) < calNow.get(Calendar.YEAR)) {
                return true;
            }
            if (calStage.get(Calendar.YEAR) == calNow.get(Calendar.YEAR)
                    && calStage.get(Calendar.MONTH) < calNow.get(Calendar.MONTH)) {

                return true;
            }
        }

        return false;

    }

}
