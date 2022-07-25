/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.paypal.api.payments.PayerInfo;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.PayPalRESTException;
import enums.PaymentType;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import paypal.PaymentServices;
import utils.HelperFunctions;

/**
 *
 * @author Nina
 */
public class ExecutePaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String paymentId = request.getParameter("paymentId");
            String payerId = request.getParameter("PayerID");
            
            PaymentServices paymentServices = new PaymentServices();
            Payment payment = paymentServices.executePayment(paymentId, payerId);
            PayerInfo payerInfo = payment.getPayer().getPayerInfo();
            Transaction transaction = payment.getTransactions().get(0);
            request.setAttribute("payer", payerInfo);
            request.setAttribute("transaction", transaction);
            HelperFunctions.savePaymentData(request, PaymentType.PAYPAL);
            request.getRequestDispatcher("paymentReceipt.jsp").forward(request, response);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(ExecutePaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
