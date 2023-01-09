function changeChairStatus(id_back,id_label,_this) {

    var input = document.getElementById(_this);
    var idBack = document.getElementById(id_back);
    var idLabel = document.getElementById(id_label);

    if(id_label.includes('normal')){
        idBack.style.backgroundColor = input.checked ?  "rgb(133, 131, 131)" :"rgb(52, 159, 52)";
        idLabel.style.backgroundColor = input.checked ? "#bfc2c5" : "rgb(104, 209, 104)";
    }else{
        idBack.style.backgroundColor = input.checked ?  "rgb(49, 150, 194)" :"rgb(52, 159, 52)";
        idLabel.style.backgroundColor = input.checked ? "rgb(84, 212, 248)" : "rgb(104, 209, 104)";
    }
    // (id_label.includes('vip'))
}