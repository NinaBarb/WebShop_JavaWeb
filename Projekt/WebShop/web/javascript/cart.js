function clearCart() {
    $.ajax({
        url: 'cart',
        type: 'POST',
        dataType: "json",
        data: {
            "action": "clearCart"
        },
        success: function () {
            $("#card").load(" #card > *");
            console.log("Cart cleared!");
        },
        error: function () {
            console.log("error");
        }
    });
}

function removeProductFromCart(id) {
    $.ajax({
        url: 'cart',
        type: 'POST',
        dataType: "json",
        data: {
            "id": id,
            "action": "removeProductfromCart"
        },
        success: function () {
            $("#" + id).closest(".cartProducts").remove();
        },
        error: function () {
            console.log("error");
        }
    });
}

function reduceQuantity(id, element) {
    element.parentNode.querySelector('input[type=number]').stepDown();
    $.ajax({
        url: 'cart',
        type: 'POST',
        dataType: "json",
        data: {
            "id": id,
            "action": "reduceQuantity"
        },
        success: function (response) {
            if (response === 0) {
                $(element).closest(".cartProducts").remove();
            }
        },
        error: function () {
            console.log("error");
        }
    });
}

function increaseQuantity(id, element) {
    element.parentNode.querySelector('input[type=number]').stepUp();
    $.ajax({
        url: 'cart',
        type: 'POST',
        data: {
            "id": id,
            "action": "increaseQuantity"
        },
        success: function () {
            console.log("Product quantity increased!");
        },
        error: function () {
            console.log("error");
        }
    });
}