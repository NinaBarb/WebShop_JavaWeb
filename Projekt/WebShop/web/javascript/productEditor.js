var loadFile = function (event) {
    var image = document.getElementById("imgPlaceholder");
    image.src = URL.createObjectURL(event.target.files[0]);
};

const toBase64 = file => new Promise((resolve, reject) => {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => resolve(reader.result);
        reader.onerror = error => reject(error);
    });

let CONFIRMATION = "Are you sure you want to delete this!";

function deleteProduct(id, element) {
    if (confirm(CONFIRMATION) === true) {
        $.ajax({
            url: 'product',
            type: 'POST',
            dataType: "json",
            data: {
                "id": id,
                "action": "delete"
            },
            success: function (data, textStatus, jqXHR) {
                $(element).closest("tr").remove();
                location.reload();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}

var idProduct = null;
function getProduct(id) {
    idProduct = id;
    openModal();
    $.ajax({
        url: 'product',
        type: 'POST',
        dataType: "json",
        data: {
            "id": id,
            "action": "getProduct"
        },
        success: function (data, textStatus, jqXHR) {
            $('#inTitle').val(data.value.title);
            $('#inPrice').val(data.value.price);
            $('#inDescription').val(data.value.description);
            $('#imgPlaceholder').attr('src', data.value.picturePath);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

$(document).ready(function () {
    console.log($('#categories').children("option:selected").val());
    $("#categories").on('change', function () {
        var selectedCountry = $(this).children("option:selected").val();
    });
});

async function addProduct() {
    idProduct = null;
    let file = document.querySelector('#imgPicker').files[0];
    let image = null;
    if (file !== null) {
        image = await toBase64(file);
    }
    $.ajax({
        url: 'product',
        type: 'POST',
        dataType: "json",
        data: {
            "title": $('#inTitle').val(),
            "picturePath": image,
            "description": $('#inDescription').val(),
            "price": $('#inPrice').val(),
            "category": $('#categories').children("option:selected").val(),
            "action": "add"
        },
        success: function (data, textStatus, jqXHR) {
            location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

async function updateProduct(id) {
    console.log($('#inTitle').val());
    let file = document.querySelector('#imgPicker').files[0];
    let image = null;
    if (file !== null) {
        image = await toBase64(file);
    }
    $.ajax({
        url: 'product',
        type: 'POST',
        dataType: "json",
        data: {
            "id": id,
            "title": $('#inTitle').val(),
            "picturePath": image,
            "description": $('#inDescription').val(),
            "price": $('#inPrice').val(),
            "category": $('#categories').children("option:selected").val(),
            "action": "update"
        },
        success: function (data, textStatus, jqXHR) {
            location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

var ok = true;
function addorEditProduct(event) {
    event.preventDefault();
    if (ok) {
        if (idProduct === null) {
            addProduct();
        } else {
            updateProduct(idProduct);
        }
    }
}

function openModal() {
    $('.container').slideDown("slow", function () {
        $('.container').css('display', 'block');
        $('.container').addClass('slideDown');
    });
}

function clearInput() {
    var inputs = document.querySelectorAll('input[type=text]');
    $('.imgPlaceholder').attr('src', './assets/imgPlaceholder.jpg');
    $('#imgPicker').val('');
    inputs.forEach(input => {
        $(input).val('');
    });
}