let forbiddenChars = new RegExp('[_\'$/#&+-]');
var inputs = document.querySelectorAll('input[type=email]');
var ok = true;

$(() => {
    $('#loginForm').on('submit', event => {
        event.preventDefault();
        clearErrors();
        htmlInjectionCheck();
        callAjax();
    });
});

String.prototype.applyXSSprotection = function () {
    return this.replace(/</g, "&lt;").replace(/>/g, "&gt;");
};

function htmlInjectionCheck() {
    console.log('injection check');
    inputs.forEach(input => {
        //$(input).val($(input).val().applyXSSprotection());
        if (forbiddenChars.test($(input).val())) {
            $(input).css('border-color', 'red');
            ok = false;
        }
    });
}

function callAjax() {
    console.log('ajax called');
    if (ok) {
        console.log('ajax ok');
        $.ajax({
            url: 'login',
            type: 'POST',
            dataType: "json",
            data: {
                "email": $("#email").val(),
                "password": $("#password").val()
            },
            success: function (data, textStatus, jqXHR) {
                if (data === "") {
                    window.location.href = 'home';
                }
                $('.lblError').html(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}

function clearErrors() {
    ok = true;
    inputs.forEach(input => {
        $(input).css('border-color', '#ced4da');
    });
}