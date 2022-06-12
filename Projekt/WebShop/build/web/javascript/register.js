let strongPassword = new RegExp('(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[^A-Za-z0-9])(?=.{8,})');
let forbiddenChars = new RegExp('[$-/:{-~"^_`\]');
var inputs = document.querySelectorAll('input[type=text]');
var ok = true;

$(() => {
    $('#registrationForm').on('submit', event => {
        event.preventDefault();
        clearErrors();
        passwordCheck();
        htmlInjectionCheck();
        callAjax();
    });
});

String.prototype.applyXSSprotection = function () {
    return this.replace(/</g, "&lt;").replace(/>/g, "&gt;");
};

function passwordCheck() {
    console.log('passwordcheck');
    console.log($('#password').val());
    console.log(strongPassword.test($('#password').val()));
    if (!strongPassword.test($('#password').val())) {
        $('.lblPasswordError').html('Password must contain 8 characters min, at least one uppercase letter,number and special character!');
        ok = false;
    }
    if ($('#password').val() !== $('#passwordConfirmation').val()) {
        $('.lblConfirmPasswordError').html('Passwords do not match!');
        ok = false;
    }
}

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
            url: 'register',
            type: 'POST',
            dataType: "json",
            data: {
                "firstname": $("#firstName").val(),
                "lastname": $("#lastName").val(),
                "email": $("#email").val(),
                "password": $("#password").val(),
                "isAdmin": $("input[name=\"isAdmin\"]:checked").val()
            },
            success: function (data, textStatus, jqXHR) {
                $('.lblError').html(data);
                console.log(data);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(errorThrown);
            }
        });
    }
}

function clearErrors(){
    ok = true;
    $('.lblPasswordError').html('');
    $('.lblConfirmPasswordError').html('');
    inputs.forEach(input => {
        $(input).css('border-color', '#ced4da');
    });
}