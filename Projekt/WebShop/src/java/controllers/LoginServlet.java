/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.LoginHistory;
import models.UserAccount;
import repo.RepositoryFactory;
import sql.AccountRepositoryImpl;
import sql.HistoryRepositoryImpl;

/**
 *
 * @author Nina
 */
public class LoginServlet extends HttpServlet {

    private AccountRepositoryImpl accountRepo;
    private HistoryRepositoryImpl historyRepo;

    private final String INCORRECT = "Incorrect username or password!";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        String message = "";
        Optional<UserAccount> userAccount = accountRepo.getAccount(email, password);
        if (userAccount.isPresent()) {
            HttpSession session = request.getSession();
            session.setAttribute("userAccount", userAccount);
            addToLoginHistory(request, userAccount);
        } else {
            message = INCORRECT;
        }

        String messageJson = new Gson().toJson(message);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(messageJson);
        out.flush();
    }

    private static String getClientIp(HttpServletRequest request) {

        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress == null) {
            ipAddress = request.getRemoteAddr();
        }

        return ipAddress;
    }

    private void addToLoginHistory(HttpServletRequest request, Optional<UserAccount> userAccount) throws Exception {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        LoginHistory history = new LoginHistory(
                date,
                getClientIp(request),
                userAccount.get().getFirstName(),
                userAccount.get().getLastName(),
                userAccount.get().getEmail());
        historyRepo.createLoginHistory(history);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect("login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            myInit();
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void myInit() {
        accountRepo = RepositoryFactory.getAccountRepository();
        historyRepo = RepositoryFactory.getLoginHistoryRepository();
    }
}
