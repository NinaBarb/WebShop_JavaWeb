/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.LoginHistory;
import repo.RepositoryFactory;
import sql.HistoryRepositoryImpl;

/**
 *
 * @author Nina
 */
public class HistoryServlet extends HttpServlet {
    
    private HistoryRepositoryImpl historyRepo;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, Exception {
        List<LoginHistory> loginHistoryList = historyRepo.getLoginHistory();
        request.setAttribute("LoginHistory", loginHistoryList);
        request.getRequestDispatcher("history.jsp").forward(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            myInit();
            processRequest(request, response);
        } catch (Exception ex) {
            Logger.getLogger(HistoryServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }

    private void myInit() {
        historyRepo = RepositoryFactory.getLoginHistoryRepository();
    }
}
