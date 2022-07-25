/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import controllers.ReviewPaymentServlet;
import enums.PaymentType;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import models.PaymentHistory;
import models.Product;
import models.UserAccount;
import repo.RepositoryFactory;
import sql.HistoryRepositoryImpl;

/**
 *
 * @author Nina
 */
public class HelperFunctions {

    private static HistoryRepositoryImpl historyRepo;
    private static Map<Product, Integer> cartProducts = new HashMap<>();

    public static void savePaymentData(HttpServletRequest request, PaymentType paymentType) {
        try {
            initRepo();
            HttpSession session = request.getSession();
            cartProducts = (HashMap<Product, Integer>) session.getAttribute("cartProducts");

            StringBuilder sb = new StringBuilder();
            for (Map.Entry<Product, Integer> product : cartProducts.entrySet()) {
                sb.append(product.getKey().getTitle() + "(" + product.getValue() + ")");
            }

            Optional<UserAccount> userAccount = (Optional<UserAccount>) session.getAttribute("userAccount");

            PaymentHistory paymentHistory = new PaymentHistory(paymentType, userAccount.get().getId(), sb.toString(), new Date(System.currentTimeMillis()));
            historyRepo.createPaymentHistory(paymentHistory);
        } catch (Exception ex) {
            Logger.getLogger(ReviewPaymentServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void initRepo() {
        historyRepo = RepositoryFactory.getLoginHistoryRepository();
    }

}
