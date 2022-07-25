<%-- 
    Document   : purchaseHistory
    Created on : Jul 3, 2022, 11:02:56 PM
    Author     : Nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Purchase history</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">

        <!--DATA TABLE!-->
        <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.12.1/css/jquery.dataTables.css">

    </head>
    <body class="m-5">
        <c:if test="${purchaseHistory.isEmpty()}">
            <h1>You haven't purchased anything yet!</h>
            </c:if>
        <table class="table table-striped table-hover table-bordered" id='purchaseHistoryTable'>
            <thead>
                <tr>
                    <th>Payment type</th>
                    <th>Payer</th>
                    <th>Items</th>
                    <th>Date</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="purchase" items="${purchaseHistory}">
                    <tr>
                        <td>${purchase.paymentType}</td>
                        <td>${purchase.payer}</td>
                        <td>${purchase.items}</td>
                        <td>${purchase.dateTime}</td>
                    </tr>    
                </c:forEach>  
            </tbody>
        </table>
            <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
            <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
            <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

            <!--DATA TABLE!-->
            <script type="text/javascript" charset="utf8" src="https://cdn.datatables.net/1.12.1/js/jquery.dataTables.js"></script>

            <script src="javascript/purchaseHistory.js" defer></script>
            </body>


            </html>
