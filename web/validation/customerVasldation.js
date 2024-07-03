$("#txtCid").focus();

const custIdRegEx=/^(C000-)[0-9]{1,3}$/;
const custNameRegEx=/^[A-z]{5,20}$/;
const custAddressRegEx=/^[0-9]{A-z. ,}{7,}$/;
const custContact=/^[0-9]{1,}[.]?[0-9]{1,2}$/;


let customerValidations=[];

customerValidations.push({reg:custIdRegEx,field:$('#txtCid'),error:'Customer-ID pattern is Wrong :C00-001'});
customerValidations.push({reg:custNameRegEx,field:$('#txtName'),error:'Customer Name Pattern is wrong :A-z 5-20'});
customerValidations.push({reg:custAddressRegEx,field:$('#txtAddress'),error:'Customer-Address Pattern is Wrong'});
customerValidations.push({reg:custContact,field:$('#txtContact'),error:'Customer-Contact pattern is Wrong '});

$('#txtCid,#txtName,#txtAddress,#txtContact').on('keydown',function (event){
   if (event. key =='Tab'){
       event.preventDefault();
   }
});

$('#txtCid,#txtName,#txtAddress,#txtContact').on('keyup',function (event){
   checkValidity();
});

$('#txtCid,#txtName,#txtAddress,#txtContact').on('blur',function (event){
    checkValidity();
});

$('#txtCid').on('keydown',function (event){
    if (event.key=="Enter" && check(custIdRegEx,$('#txtCid'))){
        $('#txtName').focus();
    }else {
        focusText($('#txtCid'));
    }

});

$('#txtName').on('keydown',function (event){
    if (event.key=="Enter" && check(custNameRegEx,$('#txtName'))) {
        $('#txtAddress').focus();
    }
});

$('#txtAddress').on('keydown',function (event){
    if (event.key=="Enter" && check(custAddressRegEx,$('#txtAddress'))) {
        $('#txtContact').focus();
    }

});


function checkValidity(){//one by get and test
    let errorCount=0;
    for (let validation of customerValidations) {
        if (validation.reg.test(validation.field.val())) {
          textSuccess(validation.field,"");
        }else {
            errorCount=errorCount+1;
            setTextError(validation.field,validation.error);
        }

    }
    setButtonState(errorCount);
}


function check(regex,txtField){
    let inputValue=txtField.val();
    return regex.test(inputValue) ? true : false;
}

function setTextError(txtField,error){
    if (txtField.val().length<=0){
        defaultText(txtField,"");
    }else {
        txtField.css('border','2px solid red');
        txtField.parent().children('span').text(error);
    }
}

function textSuccess(txtField,error){
    if (txtField.val().length<=0){
        defaultText(txtField,"");
    }else {
        txtField.parent().children('span').text(error);
    }
}

function defaultText(txtField,error){
    txtField.css("border","2px solid green");
    txtField.parent().children('span').text(error);

}

function focusText(txtField){
    txtField.focus();//resuse
}

function setButtonState(value){
    if (value>0){
        $('#btnsaveCustomer').attr('disabled',true);
    }else {
        $('#btnsaveCustomer').attr('disabled',false);
    }
}

