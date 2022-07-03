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
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.PaymentHistory;
import models.Product;
import models.UserAccount;
import paypal.PaymentServices;
import repo.RepositoryFactory;
import sql.HistoryRepositoryImpl;

/**
 *
 * @author Nina
 */
public class ExecutePaymentServlet extends HttpServlet {
    private HistoryRepositoryImpl historyRepo;
    private Map<Product, Integer> cartProducts = new HashMap<>();

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
            savePaymentData(request);
            request.getRequestDispatcher("paymentReceipt.jsp").forward(request, response);
        } catch (PayPalRESTException ex) {
            Logger.getLogger(ExecutePaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void savePaymentData(HttpServletRequest request) {
        try {
            initRepo();
            HttpSession session = request.getSession();
            cartProducts = (HashMap<Product, Integer>) session.getAttribute("cartProducts");
            
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Product, Integer> product : cartProducts.entrySet()) {
                sb.append(product.getKey().getTitle() + "(" + product.getValue() + ")");
            }
            
            Optional<UserAccount> userAccount = (Optional<UserAccount>) session.getAttribute("userAccount");
            
            PaymentHistory paymentHistory = new PaymentHistory(PaymentType.PAYPAL, userAccount.get().getId(), sb.toString());
            historyRepo.createPaymentHistory(paymentHistory);
        } catch (Exception ex) {
            Logger.getLogger(ReviewPaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() {
        historyRepo = RepositoryFactory.getLoginHistoryRepository();
    }
}
