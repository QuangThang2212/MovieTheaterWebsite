$.validator.addMethod('customphone', function(value, element) {

    return this.optional(element) || /(84|0[3|5|7|8|9])+([0-9]{8})\b/.test(value);

}, "Please input your correct phone number");
// $.validator.addMethod('customdate', function(value, element) {
//
//     return this.optional(element) || /^(0?[1-9]|[12][0-9]|3[01])[\/\-](0?[1-9]|1[012])[\/\-]\d{4}$/.test(value);
//
// }, "Please input your correct date of birth");
$(document).ready(function() {
    $(document).on("click", "#register", function() {
        $("#register_form").validate({
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
                confirmPassword: {
                    required: true,
                    minlength: 8,
                    maxlength: 16,
                    equalTo: "#password",
                },
                fullName: {
                    required: true,
                    minlength: 8,
                    maxlength: 20,
                },
                dateOfBirth: {
                    required: true,
                    // customdate: true,
                },
                identityCard: {
                    required: true,
                    minlength: 12,
                    maxlength: 12,
                },
                email: {
                    required: true,
                    maxlength: 30,
                    email: true,
                },
                address: {
                    required: true,
                    minlength: 10,
                    maxlength: 50,
                },
                phoneNumber: {
                    required: true,
                    customphone: true,
                },
                image: {
                    required: true,
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
                confirmPassword: {
                    required: "Enter your confirm password",
                    minlength: "You must input 8 characters at least",
                    maxlength: "Your confirm password must less than 16 characters",
                    equalTo: "Your confirm password is not correct",
                },
                fullName: {
                    required: "Enter your full name",
                    minlength: "You must input 8 characters at least",
                    maxlength: "Your full name must less than 20 characters",
                },
                dateOfBirth: {
                    required: "Enter your date of birth",
                },
                identityCard: {
                    required: "Enter your identity card",
                    minlength: "Identity card must be 12 numbers",
                    maxlength: "Identity card must be 12 numbers",
                },
                email: {
                    required: "Enter your email",
                    maxlength: "Your email must less than 30 characters",
                    email: "Incorrect input for email",
                },
                address: {
                    required: "Enter your address",
                    minlength: "You must input 10 characters at least",
                    maxlength: "Your full name must less than 50 characters",
                },
                phoneNumber: {
                    required: "Enter your phone number",
                },
                image: {
                    required: "Enter your image URL",
                },
            },
        });
        $("#register_form").valid();
    });
});