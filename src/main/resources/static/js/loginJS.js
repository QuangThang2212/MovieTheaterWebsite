$(document).ready(function() {
    $(document).on("click", "#login", function() {
        $("#login_form").validate({
            rules: {
                userName: {
                    required: true,
                    maxlength: 20,
                    minlength: 4,
                },
                password: {
                    required: true,
                    minlength: 8,
                    maxlength: 16,
                },
            },
            messages: {
                userName: {
                    required: "Enter your username",
                    minlength: "You must input 4 characters at least",
                    maxlength: "Your username must less than 20 characters",
                },
                password: {
                    required: "Enter your password",
                    minlength: "You must input 8 characters at least",
                    maxlength: "Your password must less than 16 characters",
                },
            },
        });
        $("#login_form").valid();
    });
});