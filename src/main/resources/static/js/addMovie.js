jQuery.validator.addMethod("greaterThan",
    function(value, element, params) {

        if (!/Invalid|NaN/.test(new Date(value))) {
            return new Date(value) > new Date($(params).val());
        }

        return isNaN(value) && isNaN($(params).val())
            || (Number(value) > Number($(params).val()));
    },'Must be greater than {0}.');

$(document).ready(function(){

    $.validator.addMethod("valueNotEquals", function(value, element, arg){
        return arg !== value;
    }, "Value must not equal arg."),

        $("#movieInfor").validate({
            rules:{
                nameEnglish:{
                    required: true,
                },
                nameVietnam:{
                    required: true,
                },
                fromDate:{
                    required: true,
                },
                toDate:{
                    required: true,
                    greaterThan: "#fromDate",
                },
                actor:{
                    required: true,
                },
                company:{
                    required: true,
                },
                director:{
                    required: true,
                },
                duration:{
                    required: true,
                    range: [1,300],
                },
                version:{
                    required: true,
                },
                type:{
                    required: true,
                },
                room:{
                    valueNotEquals: "default",
                },
                times:{
                    required: true,
                },
                content:{
                    required: true,
                },
                image:{
                    required: true,
                }




            },

            messages:{
                nameEnglish:{
                    required: "Name should not be blank"
                },
                nameVietnam:{
                    required: "Name should not be blank"
                },
                fromDate:{
                    required: "From date should not be blank",
                },
                toDate:{
                    required: "To date should not be blank",
                },
                actor:{
                    required: "Actor should not be blank",
                },
                company:{
                    required: "Company should not be blank",
                },
                director:{
                    required: "Director should not be blank",
                },
                duration:{
                    required: "Duration should not be blank",
                    range: "Please enter a value > 0 and < 300",
                },
                version:{
                    required: "Version should not be blank",
                },
                type:{
                    required: "Type should not be blank",
                },
                room:{
                    valueNotEquals: "Choose room",
                },
                times:{
                    required: "Times should not be blank",
                },
                content:{
                    required: "Content should not be blank",
                },
                image:{
                    required: "Image should not be blank",
                }

            },

            errorPlacement: function(error, element){
                if(element.attr("name") == "nameEnglish"){
                    error.appendTo("#nameEnglish_mess");
                }else if(element.attr("name") == "nameVietnam"){
                    error.appendTo("#nameVietnam_mess");
                }else if(element.attr("name") == "fromDate"){
                    error.appendTo("#fromDate_mess");
                }else if(element.attr("name") == "toDate"){
                    error.appendTo("#toDate_mess");
                }else if(element.attr("name") == "actor"){
                    error.appendTo("#actor_mess");
                }else if(element.attr("name") == "company"){
                    error.appendTo("#company_mess");
                }else if(element.attr("name") == "director"){
                    error.appendTo("#director_mess");
                }else if(element.attr("name") == "duration"){
                    error.appendTo("#duration_mess");
                }else if(element.attr("name") == "version"){
                    error.appendTo("#vertion_mess");
                }else if(element.attr("name") == "type"){
                    error.appendTo("#type_mess");
                }else if(element.attr("name") == "room"){
                    error.appendTo("#room_mess");
                }else if(element.attr("name") == "times"){
                    error.appendTo("#times_mess");
                }else if(element.attr("name") == "content"){
                    error.appendTo("#content_mess");
                }else if(element.attr("name") == "image"){
                    error.appendTo("#image_mess");
                }
                else{
                    error.insertAfter(element);
                }
            },
        });

    $(document).on("click", "#buttonAdd",function(){
        let result = $("#movieInfor").valid();
    });

    // $(document).on("click", "#buttonBack",function(){
    //     let result = $("#movieInfor").get(0).reset();
    // });

})