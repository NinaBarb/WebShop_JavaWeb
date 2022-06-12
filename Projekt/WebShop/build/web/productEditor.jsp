<%-- 
    Document   : productEditor
    Created on : May 18, 2022, 11:55:20 AM
    Author     : Nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Product Editor</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">

        <link rel="stylesheet" href="css/categoryEditor.css">
    </head>
    <body>
        <form  onsubmit="addorEditProduct(event)" >
            <div class="container">
                <div class="row">
                    <div class="col-sm-6">
                        <image src="./assets/imgPlaceholder.jpg" alt="userImage" class="imgPlaceholder" id="imgPlaceholder"/>
                    </div>
                    <div class="col-sm-6">
                        <div class="input-group">
                            <p>Image:</p>
                            <input type="file" id="imgPicker" accept="image/*" onchange="loadFile(event)"></input>
                        </div>
                        <div class="input-group">
                            <p>Title</p>
                            <input type="text" id="inTitle" required maxLength="50" pattern="[^()/><\][\\\x22,;|]+"/>
                        </div>
                        <div class="input-group">
                            <p>Description</p>
                            <input type="text" id="inDescription" required maxLength="500" pattern="[^()/><\][\\\x22,;|]+"/>
                        </div>
                        <div class="input-group">
                            <p>Price</p>
                            <input type="number" id="inPrice" required min="0" inputmode="numeric" pattern="/^\d*\.?\d*$/"/>
                        </div>
                        <div class="input-group">
                            <p>Category</p>
                            <select name="categories" id="categories">
                                <c:forEach var="category" items="${categories}">
                                    <option value=${category.id}>${category.title}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="submit" class="btnSubmit">Submit</button>
                    </div>
                </div>
            </div>
        </form>
        <div class="container-xl">
            <h1 class="lblMessage"></h1>
            <div class="table-responsive">
                <div class="table-wrapper">
                    <div class="table-title">
                        <div class="row">
                            <div class="col-sm-8"><h2>Product <b>Manager</b></h2></div>
                            <div class="col-sm-4">
                                <i onClick="openModal()" class="material-icons-outlined">add_circle</i>
                            </div>
                        </div>
                    </div>
                    <table class="table table-striped table-hover table-bordered">
                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Description</th>
                                <th>Price</th>
                                <th>Actions</th>
                            </tr>
                        </thead>
                        <tbody class="tableBody">
                            <c:forEach var="product" items="${products}">
                                <tr>
                                    <td>${product.id}</td>
                                    <td>${product.title}</td>
                                    <td>slika</td>
                                    <td>${product.description}</td>
                                    <td>${product.price}</td>
                                    <td>
                                        <a href="#" class="edit" onclick="getProduct(${product.id})"><i class="material-icons">&#xE254;</i></a>
                                        <a href="#" class="delete" onClick="deleteProduct(${product.id}, this)"><i class="material-icons">&#xE872;</i></a>
                                    </td>
                                </tr>    
                            </c:forEach>  
                        </tbody>
                    </table>
                    <div class="clearfix">
                        <div class="hint-text">Showing <b>5</b> out of <b>25</b> entries</div>
                        <ul class="pagination">
                            <li class="page-item disabled"><a href="#"><i class="material-icons-outlined">skip_previous</i></a></li>
                            <li class="page-item"><a href="#" class="page-link">1</a></li>
                            <li class="page-item"><a href="#" class="page-link">2</a></li>
                            <li class="page-item active"><a href="#" class="page-link">3</a></li>
                            <li class="page-item"><a href="#" class="page-link">4</a></li>
                            <li class="page-item"><a href="#" class="page-link">5</a></li>
                            <li class="page-item"><a href="#" class="page-link"><i class="material-icons-outlined">skip_next</i></a></li>
                        </ul>
                    </div>
                </div>
            </div>  
        </div>   

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

        <script src="javascript/productEditor.js" defer></script>
    </body>
</html>
