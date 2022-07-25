/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import enums.PaymentType;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import utils.HelperFunctions;

/**
 *
 * @author Nina
 */
public class deliveryInfoServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HelperFunctions.savePaymentData(request, PaymentType.CASH_ON_DELIVERY);
        request.getSession().invalidate();
        response.sendRedirect("success.jsp");
    }
}
