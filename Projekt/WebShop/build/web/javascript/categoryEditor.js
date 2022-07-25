var loadFile = function (event) {
    console.log(event);
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

function deleteCategory(id, element) {
    if (confirm(CONFIRMATION) === true) {
        $.ajax({
            url: 'category',
            type: 'POST',
            dataType: "json",
            data: {
                "id": id,
                "action": "delete"
            },
            success: function (data, textStatus, jqXHR) {
                $(element).closest("tr").remove();
                clearInput();
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}

var idCategory = null;
function getCategory(id) {
    idCategory = id;
    openModal();
    $.ajax({
        url: 'category',
        type: 'POST',
        dataType: "json",
        data: {
            "id": id,
            "action": "getCategory"
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data.value.id);
            $('#inTitle').val(data.value.title);
            $('#imgPlaceholder').attr('src', data.value.picturePath);
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

async function addCategory() {
    idCategory = null;
    let file = document.querySelector('#imgPicker').files[0];
    let image = null;
    if (file !== null) {
        image = await toBase64(file);
    }
    $.ajax({
        url: 'category',
        type: 'POST',
        dataType: "json",
        data: {
            "title": $('#inTitle').val(),
            "action": "add",
            "picturePath": image
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            window.location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

async function updateCategory(id) {
    let file = document.querySelector('#imgPicker').files[0];
    let image = null;
    if (file !== null) {
        image = await toBase64(file);
    }
    $.ajax({
        url: 'category',
        type: 'POST',
        dataType: "json",
        data: {
            "id": id,
            "title": $('#inTitle').val(),
            "picturePath": image,
            "action": "update"
        },
        success: function (data, textStatus, jqXHR) {
            console.log(data);
            location.reload();
        },
        error: function (jqXHR, textStatus, errorThrown) {
            console.log(errorThrown);
        }
    });
}

var ok = true;
function addorEditCategory(event) {
    event.preventDefault();
    if (ok) {
        if (idCategory === null) {
            addCategory();
        } else {
            updateCategory(idCategory);
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
    inputs.forEach(input => {
        $(input).val('');
    });
}