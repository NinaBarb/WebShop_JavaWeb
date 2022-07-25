<%-- 
    Document   : cart
    Created on : Apr 20, 2022, 12:25:25 PM
    Author     : Nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">

        <link rel="stylesheet" href="css/cart.css">
    </head>
    <body>
        <div class="card" id="card">
            <div class="row">
                <div class="col-md-8 cart">
                    <div class="title">
                        <div class="row">
                            <div class="col">
                                <h4><b>Shopping Cart</b></h4>
                            </div>
                            <div class="col align-self-center text-right text-muted">${products.size()} items</div>
                            <button class="col btn btn-outline-danger" onclick="clearCart()">Clear All</button>
                        </div>
                    </div>
                    <c:forEach var="product" items="${products}">
                        <div class="row border-top border-bottom cartProducts">
                            <div class="row main align-items-center">
                                <div class="col-2">
                                    <img class="img-fluid" src="${product.getKey().picturePath}">
                                </div>
                                <div class="col">
                                    <div class="row text-muted">${product.getKey().title}</div>
                                </div>
                                <div class="col">
                                    <a onclick="reduceQuantity(${product.getKey().id}, this)">
                                        -
                                    </a>
                                    <input id="quantity" min="0" name="quantity" value=${product.getValue()} type="number"
                                           class="form-control form-control-sm" onkeydown="return false"/>
                                    <a onclick="increaseQuantity(${product.getKey().id}, this)">
                                        +
                                    </a>
                                </div>
                                <div class="col">
                                    &euro; ${product.getKey().price * product.getValue()}
                                    <span class="close" id="${product.getKey().id}" onclick="removeProductFromCart(${product.getKey().id})">&#10005;</span>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                    <a class="back-to-shop" href="home">
                        <i class="material-icons-outlined text-secondary">
                            shopping_bag
                        </i> 
                        Back to shopping
                    </a>
                </div>
                <div class="col-md-4 summary">
                    <div>
                        <h5><b>Summary</b></h5>
                    </div>
                    <hr>
                    <div class="row">
                        <div class="col" style="padding-left:0;">Payment method:</div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio1" value="option1">
                                <label class="form-check-label" for="inlineRadio1">On delivery</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="inlineRadioOptions" id="inlineRadio2" value="option2">
                                <label class="form-check-label" for="inlineRadio2">PayPal</label>
                            </div>
                        </div>
                    </div>
                    <div class="row" style="border-top: 1px solid rgba(0,0,0,.1); padding: 2vh 0;">
                        <div class="col">TOTAL PRICE</div>
                        <div class="col text-right">&euro; ${totalPrice}</div>
                    </div> 
                    <a class="btn" href="checkout" id="checkout">
                        <span class="material-icons-outlined">
                            shopping_cart_checkout
                        </span>
                    </a>
                    <a class="btn" href="deliveryInfo.jsp" id="cashOnDelivery">
                        <span class="material-icons-outlined">
                            shopping_cart_checkout
                        </span>
                    </a>
                </div>
            </div>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

        <script src="javascript/cart.js" defer></script>
    </body>
</html>
