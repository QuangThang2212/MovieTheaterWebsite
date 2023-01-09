// window.addEventListener( "pageshow", function ( event ) {
//     var historyTraversal = event.persisted ||
//         ( typeof window.performance != "undefined" &&
//             window.performance.navigation.type === 2 );
//     if ( historyTraversal ) {
//         // Handle page restore.
//         window.location.reload();
//     }
// });
if(performance.navigation.type == 2){
    window.location.reload(true);
}
function changeChairStatus(id_back,id_label,_this) {

    var input = document.getElementById(_this);
    var idBack = document.getElementById(id_back);
    var idLabel = document.getElementById(id_label);

    if(id_label.includes('normal')){
        idBack.style.backgroundColor = input.checked ?  "rgb(133, 131, 131)" :"rgb(52, 159, 52)";
        idLabel.style.backgroundColor = input.checked ? "rgb(183, 183, 183)" : "rgb(104, 209, 104)";
    }else{
        idBack.style.backgroundColor = input.checked ?  "rgb(49, 150, 194)" :"rgb(52, 159, 52)";
        idLabel.style.backgroundColor = input.checked ? "rgb(84, 212, 248)" : "rgb(104, 209, 104)";
    }
    // (id_label.includes('vip'))
}

var count=0;
var submitButton = document.getElementById("submit_button");
var submitWarn = document.getElementById("selection_warn");
function status(x){
    if(x == 0){
        submitButton.setAttribute('type', 'button');
        submitButton.style.backgroundColor= "rgb(133, 131, 131)";
        submitWarn.innerHTML = 'Please select number of seat greater than 0';
        submitWarn.style.display = 'block';
    }else{
        if(x != count){
            submitWarn.style.display = 'block';
            submitWarn.innerHTML = 'Please choose Number of Quantity equal with number of selection seat';
            submitButton.setAttribute('type', 'button');
            submitButton.style.backgroundColor= "rgb(133, 131, 131)";
        }else{
            submitWarn.innerHTML= '';
            submitWarn.style.display = 'none';
            submitButton.setAttribute('type', 'submit');
            submitButton.style.backgroundColor= "#007bff";
        }
    }
    console.log('select  '+x);
}
function totalNumber(id_label){
    var label = document.getElementById(id_label).style.backgroundColor;
    var number = document.getElementById("selection");
    var x = number.value;
    if(label === "rgb(104, 209, 104)"){
        count=count+1;
    }
    if(label === "rgb(183, 183, 183)" || label === "rgb(84, 212, 248)"){
        if(count>0){
            count=count-1;
        }
    }
    console.log('count  '+count);
    status(x);
}


submitButton.setAttribute('type', 'button');
submitButton.style.backgroundColor= "rgb(133, 131, 131)";
submitWarn.style.display = 'none';

var numberb = document.getElementById("selection");
numberb.value=0;
numberb.addEventListener("change", function (){
    var x = numberb.value;
    status(x);
})

if (sessionStorage.getItem("memberBooking")) {
    sessionStorage.removeItem("memberBooking");
    window.location.reload(true); // force refresh page1
}


