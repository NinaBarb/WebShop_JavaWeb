<%-- 
    Document   : navbar
    Created on : Apr 17, 2022, 11:18:58 PM
    Author     : Nina
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <link rel="stylesheet" href="css/navbar.css">
    </head>
    <body>
        <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="javascript:void(0)">WebShop</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#mynavbar">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="mynavbar">
                    <ul class="navbar-nav me-auto">
                        <li class="nav-item">
                            <a class="nav-link" href="home">Home</a>
                        </li>
                        <li class="nav-item">
                            <c:if test="${sessionScope.userAccount.get() == null}">
                                <a class="nav-link" href="login">LogIn</a>
                            </c:if>
                            <c:if test="${sessionScope.userAccount.get() != null}">
                                <a class="nav-link" href="myProfile.jsp">MyProfile</a>
                            </c:if>
                        </li>
                    </ul>
                    <form class="d-flex">
                        <a class="btn btnCart" href='cart'>
                            <i class="material-icons-outlined text-secondary iCart">
                                shopping_cart
                            </i>                 
                        </a>
                        <c:if test="${sessionScope.userAccount.get() != null}">
                            <a class="btn btnCart" href="history">
                                <i class="material-icons-outlined text-secondary">
                                    fact_check
                                </i>                 
                            </a>
                            
                            <a class="btn btnCart" href="logout">
                                <i class="material-icons-outlined text-secondary">
                                    logout
                                </i>                 
                            </a>
                        </c:if>
                    </form>
                </div>
            </div>
        </nav>

    </body>
</html>
