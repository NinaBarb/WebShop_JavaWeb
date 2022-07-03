/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.paypal.base.rest.PayPalRESTException;
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
import models.OrderDetail;
import models.Product;
import models.UserAccount;
import paypal.PaymentServices;

/**
 *
 * @author Nina
 */
public class CheckoutServlet extends HttpServlet {
    
    private Map<Product, Integer> cartProducts = new HashMap<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        /*HttpSession session = request.getSession();
        if (session.getAttribute("cartProducts") != null) {
        cartProducts = (HashMap<Product, Integer>) session.getAttribute("cartProducts");
        multiplyPriceQuantity();
        request.setAttribute("products", cartProducts);
        request.setAttribute("totalPrice", getTotalPrice());
        }*/
        
        try {
            String product = request.getParameter("product");
            String subtotal = request.getParameter("subtotal");
            String shipping = request.getParameter("shipping");
            String tax = request.getParameter("tax");
            String total = request.getParameter("total");
            String quantity = request.getParameter("quantity");
            
            OrderDetail orderDetail = new OrderDetail(product, quantity, subtotal, shipping, tax, total);
            
            HttpSession session = request.getSession();
            Optional<UserAccount> payer = (Optional<UserAccount>) session.getAttribute("userAccount");
            
            PaymentServices paymentServices = new PaymentServices();
            String approvalLink = paymentServices.authorizePayment(orderDetail, payer.get());
            response.sendRedirect(approvalLink);
        } catch (PayPalRESTException ex) {
            request.setAttribute("errorMessage", ex.getMessage());
            ex.printStackTrace();
            request.getRequestDispatcher("error.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("checkout.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    
    private void multiplyPriceQuantity() {
        for (Map.Entry<Product, Integer> product : cartProducts.entrySet()) {
            product.getKey().setPrice(product.getValue() * product.getKey().getPrice());
        }
    }

    private double getTotalPrice() {
        double totalPrice = 0;
        for (Map.Entry<Product, Integer> product : cartProducts.entrySet()) {
            totalPrice += product.getValue() * product.getKey().getPrice();
        }
        return totalPrice;
    }

}
