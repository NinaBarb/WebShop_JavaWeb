<%-- 
    Document   : deliveryInfo
    Created on : Jul 5, 2022, 10:38:20 PM
    Author     : Nina
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cash on delivery Page</title>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <link href="https://fonts.googleapis.com/css?family=Material+Icons|Material+Icons+Outlined|Material+Icons+Two+Tone|Material+Icons+Round|Material+Icons+Sharp" rel="stylesheet">
        <link rel="stylesheet" href="css/deliveryInfo.css">
    </head>
    <body >
        <!-- Navigation bar -->
        <%@include file='navbar.jsp'%>
        <div class="container" style="margin-left: 20%;">
            <section id="contact" class="gray-bg padding-top-bottom">
                <div class="container ">
                    <div class="row">
            <h1>Order details</h1>
                        <form id="Highlighted-form" class="col-sm-12 col-sm-offset-6" action="delivery" method="post" novalidate="">
                            <div class="form-group">
                                <label class="control-label" for="contact-name">Name</label>
                                <div class="controls">
                                    <input id="contact-name" required pattern="[^()/><\][\\\x22,;|]+" name="contactName" placeholder="Your name" class="form-control requiredField Highlighted-label" data-new-placeholder="Your name" type="text" data-error-empty="Please enter your name">
                                    
                                </div>
                            </div><!-- End name input -->

                            <div class="form-group">
                                <label class="control-label" for="contact-mail">Email</label>
                                <div class=" controls">
                                    <input id="contact-mail" required pattern="[^()/><\][\\\x22,;|]+" name="email" placeholder="Your email" class="form-control requiredField Highlighted-label" data-new-placeholder="Your email" type="email" data-error-empty="Please enter your email" data-error-invalid="Invalid email address">
                                </div>
                            </div>
                            <!-- End email input -->

                            <div class="form-group">
                                <label class="control-label" for="contact-message">Message</label>
                                <div class="controls">
                                    <textarea id="contact-message" required pattern="[^()/><\][\\\x22,;|]+"  name="comments" placeholder="Your message" class="form-control requiredField Highlighted-label" data-new-placeholder="Your message" rows="6" data-error-empty="Please enter your message"></textarea>
                                    
                                </div>
                            </div>
                            <!-- End textarea -->

                            <div class="form-group">
                                <label class="control-label" for="address">Address</label>
                                <div class="controls">
                                    <input id="address" required pattern="[^()/><\][\\\x22,;|]+"  name="address" placeholder="Your address" class="form-control requiredField Highlighted-label" data-new-placeholder="Your address" type="text" data-error-empty="Please enter your address" data-error-invalid="Invalid address">
                                    
                                </div>
                            </div>
                            <!-- End address -->

                            <div class="form-group">
                                <label class="control-label" for="postCode">Post code</label>
                                <div class="controls">
                                    <input id="postCode" required pattern="[^()/><\][\\\x22,;|]+"  name="postCode" placeholder="Your post code" class="form-control requiredField Highlighted-label" data-new-placeholder="Your post code" type="number" data-error-empty="Please enter your post code" data-error-invalid="Invalid post code">
                                    
                                </div>
                            </div>
                            <!-- End post code -->

                            <div class="form-group">
                                <label class="control-label" for="country">Country</label>
                                <div class="controls">
                                    <input id="country" required pattern="[^()/><\][\\\x22,;|]+" name="country" placeholder="Your country" class="form-control requiredField Highlighted-label" data-new-placeholder="Your country" type="text" data-error-empty="Please enter your country" data-error-invalid="Invalid country">
                                    
                                </div>
                            </div>
                            <!-- End country -->

                            <div class="form-group">
                                <label class="control-label" for="city">City</label>
                                <div class="controls">
                                    <input id="city" required pattern="[^()/><\][\\\x22,;|]+" name="city" placeholder="Your city" class="form-control requiredField Highlighted-label" data-new-placeholder="Your city" type="text" data-error-empty="Please enter your city" data-error-invalid="Invalid city name">
                                    
                                </div>
                            </div>
                            <!-- End city -->

                            <p class="p-2">
                                <button  style="width: 20%" name="submit" type="submit" class="btn btn-dark btn-block" data-error-message="Error!" data-sending-message="Sending..." data-ok-message="Message Sent">
                                    Order
                                </button>
                            </p>
                            <input type="hidden" name="submitted" id="submitted" value="true">	
                        </form>
                        <!-- End Highlighted-form -->
                    </div>	
                </div>	
            </section>
        </div>

        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <script src="https://code.jquery.com/jquery-3.6.0.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" integrity="sha256-/xUj+3OJU5yExlq6GSYGSHk7tPXikynS7ogEvDej/m4=" crossorigin="anonymous"></script>

        <script src="javascript/deliveryInfo.js" defer></script>
    </body>
</html>
