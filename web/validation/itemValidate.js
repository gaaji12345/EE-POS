const itemCode = /^(I00-)[0-9]{3}$/;
const itemName = /^[A-Za-z ]{3,}$/;
const itemQty = /^[0-9]+$/;
const iPrice= /^[0-9]{2,}([.][0-9]{2})?$/;

$("#txtitemcode,#txtitemname,#txtprice,#txtqty").keydown(function (e) {
    if (e.key=="Tab"){
        e.preventDefault();
    }
});

$("#txtitemcode").keydown(function (e) {
    if (e.key=="Enter"  && itemCode.test($('#txtitemcode').val())){
        $('#errors').css("border"," 2px solid blue");
        //  $('#error').text("wrong");
        $('#txtitemname').focus();
    }
});

$("#txtitemcode").keyup(function (e) {
    if (itemCode.test($('#txtitemcode').val())){
        $('#errors').text("RIGHT ID..!").css("background-color","yellow");
        $(this).css('border','2px solid green');
    }else{
        $('#errors').text("WRONG TYPE...!");
        $(this).css('border','2px solid red');
    }
});

$("#txtitemname").keydown(function (e) {
    if (e.key=="Enter"  && itemName.test($('#txtitemname').val())){
        $('#txtprice').focus();
    }
});

$("#txtName").keyup(function (e) {
    if (itemName.test($('#txtName').val())){
        $('#lblcusname').text("RIGHT INPUT...!");
        $(this).css('border','2px solid green');
    }else{
        // $(this).css("background-color","red","!important");
        $('#lblcusname').text("WRONG INPUT...!");
        $(this).css('border','2px solid red');
    }
});

$("#txtprice").keydown(function (e) {
    if (e.key=="Enter"  && iPrice.test($('#txtAddress').val())){
        // $(this).css("background-color","white","!important");
        $('#txtqty').focus();
    }
});

$("#txtprice").keyup(function (e) {
    if (iPrice.test($('#txtprice').val())){
        // $(this).css("background-color","green","!important");
        $('#lblitemprice').text("RIGHT INPUT...!");
        $(this).css('border','2px solid green');
    }else{
        $('#lblitemprice').text("WRONG INPUT...!");
        $(this).css('border','2px solid red');
    }
});

$("#txtqty").keydown(function (e) {
    if (e.key=="Enter" && itemQty.test($('#txtContact').val())){
        saveItem();
        //chkContact();
    }
});

$("#txtqty").keyup(function (e) {
    if (itemQty.test($('#txtqty').val())){
        $('#lblitemqty').text("RIGHT INPUT...!");
        $(this).css('border','2px solid green');
        // $('#btnsaveCustomer').focus();
    }else{
        $('#lblitemqtye').text("WRONG INPUT...!");
        $(this).css('border','2px solid red');
    }


});

function checkValidtionItem() {
    for (let i = 0; i < items.length; i++) {
        if ($('#txtitemcode').val()==items[i].itemId){
            return false;
        }
    }
    return true;
}



