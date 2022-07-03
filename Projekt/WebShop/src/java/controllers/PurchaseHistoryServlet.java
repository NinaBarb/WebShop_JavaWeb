/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.PaymentHistory;
import models.UserAccount;
import repo.RepositoryFactory;
import sql.HistoryRepositoryImpl;

/**
 *
 * @author Nina
 */
public class PurchaseHistoryServlet extends HttpServlet {

    private HistoryRepositoryImpl historyRepo;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        initRepo();
        HttpSession session = request.getSession();
        Optional<UserAccount> userAccount = (Optional<UserAccount>) session.getAttribute("userAccount");
        if (userAccount.get().isIsAdmin()) {
            getPurchaseHistory_ADMIN(request, response);
        } else {
            getPurchaseHistory_USER(userAccount, request, response);
        }
    }

    private void getPurchaseHistory_USER(Optional<UserAccount> userAccount, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<PaymentHistory> paymentHistory_USER = historyRepo.getPaymentHistory_USER(userAccount.get().getId());
            request.setAttribute("purchaseHistory", paymentHistory_USER);
            request.getRequestDispatcher("purchaseHistory.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PurchaseHistoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() {
        historyRepo = RepositoryFactory.getLoginHistoryRepository();
    }

    private void getPurchaseHistory_ADMIN(HttpServletRequest request, HttpServletResponse response){
        try {
            List<PaymentHistory> paymentHistory_ADMIN = historyRepo.getPaymentHistory_ADMIN();
            request.setAttribute("purchaseHistory", paymentHistory_ADMIN);
            request.getRequestDispatcher("purchaseHistory.jsp").forward(request, response);
        } catch (Exception ex) {
            Logger.getLogger(PurchaseHistoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
