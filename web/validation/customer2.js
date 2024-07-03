let cusIdRegex=/^(C0-)[0-9]{3}$/;
let cusNameRegex=/^[A-Za-z\s]+$/;
let cusAddressRegex=/^[A-Za-z\s]+$/;
let cusContactRegex= /^\d{10}$/;


$("#txtCid,#txtName,#txtAddress,#txtContact").keydown(function (e) {
    if (e.key=="Tab"){
        e.preventDefault();
    }
});

$("#txtCid").keydown(function (e) {
    if (e.key=="Enter"  && cusIdRegex.test($('#txtCid').val())){
        $('#error').css("border"," 2px solid blue");
      //  $('#error').text("wrong");
        $('#txtName').focus();
    }
});

$("#txtCid").keyup(function (e) {
    if (cusIdRegex.test($('#txtCid').val())){
        $('#error').text("RIGHT ID..!").css("background-color","yellow");
        $(this).css('border','2px solid green');
    }else{
        $('#error').text("WRONG TYPE...!");
        $(this).css('border','2px solid red');
    }
});

$("#txtName").keydown(function (e) {
    if (e.key=="Enter"  && cusNameRegex.test($('#txtName').val())){
       // $(this).css("background-color","white","!important");
        $('#txtAddress').focus();
    }
});

$("#txtName").keyup(function (e) {
    if (cusNameRegex.test($('#txtName').val())){
        $('#lblcusname').text("RIGHT INPUT...!");
        $(this).css('border','2px solid green');
    }else{
        // $(this).css("background-color","red","!important");
        $('#lblcusname').text("WRONG INPUT...!");
        $(this).css('border','2px solid red');
    }
});
$("#txtAddress").keydown(function (e) {
    if (e.key=="Enter"  && cusAddressRegex.test($('#txtAddress').val())){
       // $(this).css("background-color","white","!important");
        $('#txtContact').focus();
    }
});

$("#txtAddress").keyup(function (e) {
    if (cusAddressRegex.test($('#txtAddress').val())){
        // $(this).css("background-color","green","!important");
        $('#lblcusaddress').text("RIGHT INPUT...!");
        $(this).css('border','2px solid green');
    }else{
        $('#lblcusaddress').text("WRONG INPUT...!");
        $(this).css('border','2px solid red');
    }
});
$("#txtContact").keydown(function (e) {
    if (e.key=="Enter" && cusContactRegex.test($('#txtContact').val())){
       saveCustomer();
        //chkContact();
    }
});


    $("#txtContact").keyup(function (e) {
        if (cusContactRegex.test($('#txtContact').val())){
            $('#lblcuscontact').text("RIGHT INPUT...!");
            $(this).css('border','2px solid green');
            // $('#btnsaveCustomer').focus();
        }else{
            $('#lblcuscontact').text("WRONG INPUT...!");
            $(this).css('border','2px solid red');
        }


    });



// $("#txtContact").keyup(function (e) {
//     if (e.key=="Enter"){
//         $('#btnsaveCustomer').focus();
//     }
// });




function setButtons(value){
    let b=chkContact();
    if (b){
        $('#btnsaveCustomer').attr('disabled',true);
    }else {
        $('#btnsaveCustomer').attr('disabled',false);
    }
}

function checkValidtion() {
    for (let i = 0; i < customers.length; i++) {
        if ($('#txtCid').val()==customers[i].customerID){
            return false;
        }
    }
    return true;
}






