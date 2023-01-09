
jQuery.validator.addMethod("greaterThan",
    function(value, element, params) {

        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) > new Date($(params).val());
        }

        return isNaN(value) && isNaN($(params).val())
            || (Number(value) > Number($(params).val()));
    },'Must be greater than {0}.');


$(document).ready(function(){
        $("#promotionForm").validate({
            rules:{
                title:{
                    required: true,
                },
                startTime:{
                    required: true,
                },
                endTime:{
                    required: true,
                    greaterThan: "#startTime",
                },
                discountLevel:{
                    required: true,
                    digits: true,
                    range: [1,100],


                }
            },

            messages:{
                title:{
                    required: "Title cannot empty !"
                },
                startTime:{
                    required: "Start Time cannot empty !"
                },
                endTime:{
                    required: "End Time cannot empty !",
                    greaterThan: "End Time must be greater than Start Time",
                },
                discountLevel:{
                    required: "Discount cannot empty !",
                    digits: "Discount must be positive integer !",
                    range: "Please enter a value between 1 and 100",

                },
            },

            errorPlacement: function(error, element){
                if(element.attr("name") == "title"){
                    error.appendTo("#title_mess");
                }else if(element.attr("name") == "startTime"){
                    error.appendTo("#startTime_mess");
                }else if(element.attr("name") == "endTime"){
                    error.appendTo("#endTime_mess");
                }else if(element.attr("name") == "discountLevel"){
                    error.appendTo("#discount_mess");
                }
                else{
                    error.insertAfter(element);
                }
            },
        });
    $(document).on("click", "#addButton",function(){
        let result = $("#promotionForm").valid();
    });
})