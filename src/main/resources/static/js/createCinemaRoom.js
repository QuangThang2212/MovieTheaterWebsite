$(document).ready(function(){

    $.validator.addMethod("valueNotEquals", function(value, element, arg){
        return arg !== value;
    }, "Value must not equal arg."),

        $("#createCinemaRoomInfor").validate({
            rules:{
                roomID:{
                    required: true,
                },
                cinemaRoomName:{
                    required: true,
                },
                seatQuantity:{
                    required: true,
                },
                seatDetail:{
                    required: true,
                },

            },

            messages:{
                roomID:{
                    required: "roomID should not be blank"
                },
                cinemaRoomName:{
                    required: "cinemaRoomName should not be blank"
                },
                seatQuantity:{
                    required: "seatQuantity should not be blank",
                },
                seatDetail:{
                    required: "seatDetail should not be blank",
                },


            },

            errorPlacement: function(error, element){
                if(element.attr("name") == "roomID"){
                    error.appendTo("#roomID_mess");
                }else if(element.attr("name") == "cinemaRoomName"){
                    error.appendTo("#cinemaRoomName_mess");
                }else if(element.attr("name") == "seatQuantity"){
                    error.appendTo("#seatQuantity_mess");
                }else if(element.attr("name") == "seatDetail") {
                    error.appendTo("#seatDetail_mess");
                }
                else{
                    error.insertAfter(element);
                }
            },
        });

    $(document).on("click", "#buttonAdd",function(){
        let result = $("#createCinemaRoomInfor").valid();
    });

    $(document).on("click", "#buttonBack",function(){
        let result = $("#createCinemaRoomInfor").get(0).reset();
    });

})