<%-- 
    Document   : home
    Created on : Apr 16, 2022, 6:48:43 PM
    Author     : Nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Home Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">

        <link rel="stylesheet" href="css/home.css">
    </head>
    <body>
        <!-- Navigation bar -->
        <%@include file='navbar.jsp'%>

        <p>Users active: <strong>${applicationScope['currentusers']}</strong></p>

        <!-- Welcome message -->
        <div class="jumbotron jumbotron-fluid">
            <div class="container text-center">
                <c:if test="${sessionScope.userAccount != null}">
                    <h1 class="display-4">Welcome ${sessionScope.userAccount.get().firstName}</h1>
                </c:if>
            </div>
        </div>

        <!-- Manage categories button -->
        <c:if test="${sessionScope.userAccount.get().isAdmin == true}">
            <div class="d-flex justify-content-center align-items-center mt-5">
                <a type="button" class="btn btn-dark" href="category">MANAGE CATEGORIES</a>
            </div>
        </c:if>

        <div class="d-flex justify-content-center align-items-center mt-5"> 
            <button class="btn btn-dark">OUR CATEGORIES</button>
        </div>
        <div class="d-flex justify-content-center mt-3"> 
            <span class="text text-center">
                Finding Best Products Now
                <br> in Your Fingertips
            </span>
        </div>
        <div class="row m-2 g-4 justify-content-center">
            <c:forEach var="category" items="${categories}">
                <div class="col-md-3"  onclick="filterProducts(${category.id})">
                    <div class="card p-1 btn btn-dark">
                        <div class="d-flex justify-content-between align-items-center p-2">
                            <div class="flex-column lh-1 imagename"> <span>${category.title}</span></div>
                            <div> <img src="${category.picturePath}" height="100" width="100" /> </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>

        <!-- Add item button -->
        <c:if test="${sessionScope.userAccount.get().isAdmin == true}">
            <div class="d-flex justify-content-center align-items-center mt-5"> 
                <a type="button" class="btn btn-dark" href="product">MANAGE PRODUCTS</a>
            </div>
        </c:if>


        <!-- Items -->
        <div class="d-flex productscontainer justify-content-center m-5">
            <c:forEach var="product" items="${products}">
                <div class="productCard m-2">
                    <c:if test="${product.picturePath == null}">
                        <img class="card-img-top" width="200px" height="200px" src="assets/imgPlaceholder.jpg" alt="Card image cap">
                    </c:if>
                    <c:if test="${product.picturePath != null}">
                        <img class="card-img-top" width="200px" height="200px" src="${product.picturePath}" alt="Card image cap">
                    </c:if>
                    <div class="card-body">
                        <h5 class="card-title">${product.title}</h5>
                        <p class="card-text"><small class="text-muted">${product.price} €</small></p>
                    </div>
                    <div class="d-flex">
                        <button class="btn btn-link px-2"
                                onclick="this.parentNode.querySelector('input[type=number]').stepDown()">
                            <span class="material-icons-outlined">
                                remove
                            </span>
                        </button>

                        <input id="quantity" min="0" name="quantity" value="0" type="number"
                               class="form-control form-control-sm" onkeydown="return false"/>

                        <button class="btn btn-link px-2"
                                onclick="this.parentNode.querySelector('input[type=number]').stepUp()">
                            <span class="material-icons-outlined">
                                add
                            </span>
                        </button>
                        <button onClick='addToCart(${product.id})' class="btn btn-dark">
                            <span class="material-icons-outlined">
                                shopping_cart
                            </span>
                        </button>
                    </div>
                </div>
            </c:forEach>
        </div>


        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

        <script src="javascript/home.js" defer></script>
    </body>
</html>
