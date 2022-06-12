/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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
public class ProductServlet extends HttpServlet {

    private ProductRepositoryImpl productRepo;
    private CategoryRepositoryImpl categoryRepo;

    private final String DELETED = "Product successfully deleted!";
    private final String EXISTS = "Product already exists!";
    private final String DOESNT_EXIST = "Product doesn't exist!";
    private final String SUCCESS = "Product successfully created!";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        List<Product> products = productRepo.getProducts();
        List<Category> categories = categoryRepo.getCategories();
        request.setAttribute("products", products);
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("productEditor.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            checkUser(request, response);
            myInit();
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "delete":
                    deleteProduct(request, response);
                    break;
                case "add":
                    addProduct(request, response);
                    break;
                case "getProduct":
                    getProduct(request, response);
                    break;
                case "update":
                    updateProduct(request, response);
                    break;
                default:
                    response.sendRedirect("home");
                    break;
            }
        } catch (Exception ex) {
            Logger.getLogger(ProductServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void myInit() {
        productRepo = RepositoryFactory.getProductRepository();
        categoryRepo = RepositoryFactory.getCategoryRepository();
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        productRepo.deleteProduct(id);

        GsonUtils.convertToGson(DELETED, response);
    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Double price = Double.parseDouble(request.getParameter("price"));
        String picturePath = request.getParameter("picturePath");
        int categoryId = Integer.parseInt(request.getParameter("category"));

        if (productRepo.doesProductExist(title)) {
            GsonUtils.convertToGson(EXISTS, response);
        } else {
            Product product = new Product(title, description, price, picturePath);
            productRepo.createProduct(product, categoryId);
            GsonUtils.convertToGson(SUCCESS, response);
        }
    }

    private void getProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        Optional<Product> product = productRepo.getProduct(id);
        if (product.isPresent()) {
            GsonUtils.convertToGson(product, response);
        }
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        Double price = Double.parseDouble(request.getParameter("price"));
        String picturePath = request.getParameter("picturePath");

        if (productRepo.getProduct(id).isPresent()) {
            Product product = new Product(title, description, price, picturePath);
            productRepo.updateProduct(product);
            GsonUtils.convertToGson(product, response);
        } else {
            GsonUtils.convertToGson(DOESNT_EXIST, response);
        }
    }

    private void checkUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        /*if (session.getAttribute("userAccount") == null
        && !((Optional<UserAccount>) session.getAttribute("userAccount"))
        .get().isIsAdmin()) {
        response.sendRedirect("home");
        }*/
    }

}
