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
            location.reload();
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
            location.reload();
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
            location.reload();
        },
        error: function () {
            console.log("error");
        }
    });
}

$('#inlineRadio1').on('click', () => {
    $('#cashOnDelivery').css('display', 'block');
    $('#checkout').css('display', 'none');
});
$('#inlineRadio2').on('click', () => {
    $('#cashOnDelivery').css('display', 'none');
    $('#checkout').css('display', 'block');
});