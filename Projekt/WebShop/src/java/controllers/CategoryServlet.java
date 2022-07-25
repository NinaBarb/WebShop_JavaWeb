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
import models.Category;
import repo.RepositoryFactory;
import sql.CategoryRepositoryImpl;
import utils.GsonUtils;

/**
 *
 * @author Nina
 */
public class CategoryServlet extends HttpServlet {

    private CategoryRepositoryImpl categoryRepo;

    private final String DELETED = "Category successfully deleted!";
    private final String EXISTS = "Category already exists!";
    private final String DOESNT_EXIST = "Category doesn't exist!";
    private final String SUCCESS = "Category successfully created!";

    protected void getCategories(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        List<Category> categories = categoryRepo.getCategories();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("categoryEditor.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            initRepo();
            getCategories(request, response);
        } catch (Exception ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            switch (action) {
                case "delete":
                    deleteCategory(request, response);
                    break;
                case "add":
                    addCategory(request, response);
                    break;
                case "getCategory":
                    getCategory(request, response);
                    break;
                case "update":
                    updateCategory(request, response);
                    break;
                default:
                    response.sendRedirect("home");
                    break;

            }
        } catch (Exception ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void initRepo() {
        categoryRepo = RepositoryFactory.getCategoryRepository();
    }

    private void deleteCategory(HttpServletRequest request, HttpServletResponse response) {
        try {
            int id = Integer.parseInt(request.getParameter("id"));
            categoryRepo.deleteCategory(id);

            GsonUtils.convertToGson(DELETED, response);
        } catch (Exception ex) {
            Logger.getLogger(CategoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void addCategory(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {

        String title = request.getParameter("title");
        String picturePath = request.getParameter("picturePath");

        if (categoryRepo.doesCategoryExist(title)) {
            GsonUtils.convertToGson(EXISTS, response);
        } else {
            Category category = new Category(title, picturePath);
            categoryRepo.createCategory(category);
            GsonUtils.convertToGson(SUCCESS, response);
        }

    }

    private void getCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));

        Optional<Category> category = categoryRepo.getCategory(id);
        if (category.isPresent()) {
            GsonUtils.convertToGson(category, response);
        }
    }

    private void updateCategory(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int id = Integer.parseInt(request.getParameter("id"));
        String title = request.getParameter("title");
        String picturePath = request.getParameter("picturePath");
        
        if(categoryRepo.getCategory(id).isPresent()){
            Category category = new Category(id, title, picturePath);
            categoryRepo.updateCategory(category);
            GsonUtils.convertToGson(category, response);
        }
        else{
            GsonUtils.convertToGson(DOESNT_EXIST, response);
        }
    }

}
