/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Category;
import models.Product;
import repo.RepositoryFactory;
import sql.CategoryRepositoryImpl;
import sql.ProductRepositoryImpl;
import utils.GsonUtils;

/**
 *
 * @author Nina
 */
public class HomeServlet extends HttpServlet {

    private CategoryRepositoryImpl categoryRepo;
    private ProductRepositoryImpl productRepo;
    private List<Product> products;
    private Map<Product, Integer> cartProducts;

    protected void getItems(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        checkForCartProducts(request);
        List<Category> categories = categoryRepo.getCategories();
        products = productRepo.getProducts();
        request.setAttribute("categories", categories);
        request.setAttribute("products", products);
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    private void checkForCartProducts(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("cartProducts") != null) {
            cartProducts = (HashMap<Product, Integer>) session.getAttribute("cartProducts");
        } else {
            cartProducts = new HashMap<>();
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            myInit();
            getItems(request, response);
        } catch (Exception ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String action = request.getParameter("action");
            switch (action) {
                case "getProductsByCategory":
                    getProductsByCategory(request, response);
                    break;
                case "addToCart":
                    addToCart(request);
                    break;
                default:
                    getItems(request, response);
                    break;

            }
        } catch (Exception ex) {
            Logger.getLogger(HomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void myInit() {
        categoryRepo = RepositoryFactory.getCategoryRepository();
        productRepo = RepositoryFactory.getProductRepository();
    }

    private void getProductsByCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int idCategory = Integer.parseInt(request.getParameter("id"));

        products = productRepo.getProductsByCategory(idCategory);
        request.setAttribute("products", products);

        GsonUtils.convertToGson(products, response);
    }

    private void addToCart(HttpServletRequest request) throws IOException, Exception {
        int cartProduct = Integer.parseInt(request.getParameter("id"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        Product product = productRepo.getProduct(cartProduct).get();
        if (cartProducts.containsKey(product)) {
            cartProducts.computeIfPresent(product, (key, val) -> val + quantity);
        } else {
            cartProducts.put(product, quantity);
        }

        HttpSession session = request.getSession();
        session.setAttribute("cartProducts", cartProducts);
    }

}
