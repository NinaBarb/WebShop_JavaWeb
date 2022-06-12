/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Product;
import utils.GsonUtils;

/**
 *
 * @author Nina
 */
public class CartServlet extends HttpServlet {

    private Map<Product, Integer> cartProducts = new HashMap<>();

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        if (session.getAttribute("cartProducts") != null) {
            cartProducts = (HashMap<Product, Integer>) session.getAttribute("cartProducts");
            multiplyPriceQuantity();
            request.setAttribute("products", cartProducts);
            request.setAttribute("totalPrice", getTotalPrice());
        }
        request.getRequestDispatcher("cart.jsp").forward(request, response);
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

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "clearCart":
                    clearCart(request, response);
                    break;
                case "removeProductfromCart":
                    removeProductfromCart(request, response);
                    break;
                case "reduceQuantity":
                    reduceQuantity(request, response);
                    break;
                case "increaseQuantity":
                    increaseQuantity(request, response);
                    break;
                default:
                    response.sendRedirect("home");
                    break;

            }
        } catch (IOException ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(CartServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void clearCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        cartProducts.clear();

        GsonUtils.convertToGson("", response);
    }

    private void removeProductfromCart(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        for (Map.Entry<Product, Integer> product : cartProducts.entrySet()) {
            if (product.getKey().getId() == id) {
                cartProducts.remove(product.getKey(), product.getValue());
                GsonUtils.convertToGson("", response);
                return;
            }
        }
    }

    private void reduceQuantity(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        for (Map.Entry<Product, Integer> product : cartProducts.entrySet()) {
            if (product.getKey().getId() == id) {
                product.setValue(product.getValue() - 1);
                if (product.getValue() == 0) {
                    cartProducts.remove(product.getKey(), product.getValue());
                    GsonUtils.convertToGson(0, response);
                }
            }
        }
        GsonUtils.convertToGson("", response);
    }

    private void increaseQuantity(HttpServletRequest request, HttpServletResponse response) {
        int id = Integer.parseInt(request.getParameter("id"));

        for (Map.Entry<Product, Integer> product : cartProducts.entrySet()) {
            if (product.getKey().getId() == id) {
                product.setValue(product.getValue() + 1);
                return;
            }
        }
    }
}
