//CARDS
$('.buy').on('click', () => {

    $('.bottom').addClass("clicked");
});

$('.remove').on('click', () => {
    $('.bottom').removeClass("clicked");
});

function filterProducts(id) {
    $.ajax({
        url: 'home',
        type: 'POST',
        dataType: "json",
        data: {
            "id": id,
            "action": "getProductsByCategory"
        },
        success: function (data, textStatus, jqXHR) {
            renderResults(data);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

function renderResults(data) {
    $(".productscontainer").fadeOut(800, function () {
        $(".productscontainer").empty();
        for (var i = 0; i < data.length; i++) {
            $(".productscontainer").append(
                    "<div class='productCard m-2'>"
                    + "<img class='card-img-top' width='200px' height='200px' src='" + data[i].picturePath + "' alt='Card image cap'>"
                    + "<div class='card-body'>"
                    + "<h5 class='card-title'>" + data[i].title + "</h5>"
                    + "<p class='card-text'>"
                    + "<small class='text-muted'>" + data[i].price + " €</small>"
                    + "</p>"
                    + "</div>"
                    + "<div class='d-flex'>"
                        +"<button onClick='addToCart(" + data[i].id + ")' class='btn btn-dark'>"
                            +"<span class='material-icons-outlined'>shopping_cart</span>"
                        +"</button>"
                    +"</div>"
                    + "</div>"
                    ).fadeIn().delay(2000);

        }
    });
}

function addToCart(id) {
    changeCartStyle();
    callAjax(id);
}

function changeCartStyle() {
    $(".iCart").attr('class', "material-icons-outlined text-warning iCart");
    setTimeout(function () {
        $(".iCart").attr("class", " material-icons-outlined text-secondary iCart");
    }, 400);
}

function callAjax(id) {
    $.ajax({
        url: 'home',
        type: 'POST',
        data: {
            "id": id,
            "quantity": $("#quantity").val(),
            "action": "addToCart"
        },
        success: function () {
            console.log("good!");
        },
        error: function () {
            console.log("error");
        }
    });
}