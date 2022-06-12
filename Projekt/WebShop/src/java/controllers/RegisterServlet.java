/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.UserAccount;
import repo.RepositoryFactory;
import sql.AccountRepositoryImpl;

/**
 *
 * @author Nina
 */
public class RegisterServlet extends HttpServlet {

    private AccountRepositoryImpl accountRepo;

    private final String ACCOUNT_EXISTS = "This account already exists!";
    private final String SUCCESS = "You have created an account successfully!";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {

        String firstName = request.getParameter("firstname");
        String lastName = request.getParameter("lastname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean isAdmin = Boolean.parseBoolean(request.getParameter("isAdmin"));

        String message = "";

        if (!accountRepo.doesAccountExist(email)) {
            UserAccount userAccount = new UserAccount(firstName, lastName, email, password, isAdmin);
            accountRepo.createAccount(userAccount);
            message = SUCCESS;
        } else {
            message = ACCOUNT_EXISTS;
        }

        String messageJson = new Gson().toJson(message);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(messageJson);
        out.flush();

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        isUserLoggedIn(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            myInit();
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(RegisterServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void myInit() {
        initRepo();
    }

    private void initRepo() {
        accountRepo = RepositoryFactory.getAccountRepository();
    }

    private void isUserLoggedIn(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession();

        if (session.getAttribute("userAccount") != null) {
            response.sendRedirect("home.jsp");
        } else {
            response.sendRedirect("register.jsp");
        }
    }
}
