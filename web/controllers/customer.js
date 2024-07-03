
// let customerObj={
//     customerID:"",
//     customerName:"",
//     customerAddress:"",
//     customerContact:""
// }

//getAllCustomer();

$('#btnsaveCustomer').click(function (){
  saveCustomer();
  LoadAll();
  btnRowClick();
  // doubleClick();
    remove();
    //loadAllCustIds();
   // loadAllCusIds();
    cusIds();


});


function saveCustomer() {
    let cusID = $('#txtCid').val();
    let cusName = $('#txtName').val();
    let cusAddress = $('#txtAddress').val();
    let cusContact = $('#txtContact').val();

    let newCustomer = Object.assign({}, customerObj);
    newCustomer.customerID = cusID;
    newCustomer.customerName = cusName;
    newCustomer.customerAddress = cusAddress;
    newCustomer.customerContact = cusContact;
    if (checkValidtion()) {
        customers.push(newCustomer);
        LoadAll();

    } else{
        clearFeilds();
    }


       // clearCustomerFeilds();

}


function LoadAll() {
    $('#tbCustomer').empty();
    for (let i = 0; i <customers.length ; i++) {

        let id=customers[i].customerID;
        let name=customers[i].customerName;
        let address=customers[i].customerAddress;
        let contact=customers[i].customerContact;

        let row=`<tr>
                    <td>${id}</td>
                    <td>${name}</td>
                    <td>${address}</td>
                    <td>${contact}</td>
                </tr>`
        $("#tbCustomer").append(row);
       // bindCustomerTrEvents();
    }

}

$('#btnUpCustomer').click(function (){

    //
    // let cusid=$('#txtCid').val();
    // let respo=updateCustomer(cusid);
    // if (respo){
    //     alert("Update success");
    // }else {
    //     alert("Update fail..!");
    // }

    let consent=confirm("ARE YOU SHURE NEED TO UPDATE..?");

    if (consent){
        for (let i = 0; i < customers.length; i++) {
            if ($('#txtCid').val()==customers[i].customerID){
                customers[i].customerID=$('#txtCid').val();
                customers[i].customerName=$('#txtName').val();
                customers[i].customerAddress=$('#txtAddress').val();
                customers[i].customerContact=$('#txtContact').val();
                //getAllCustomer();
                LoadAll();
                //clearCustomerFeilds();
                clearFeilds();
                alert("UPDATED SUCSUS");
                break;
            }
        }
    }



});

$('#btnDeleteCustomer').click(function (){
    let deleteId= $('#txtCid').val();
    if (deleteId) {
        deleteCustomer(deleteId);
        alert("DELETE...!");
    }else {
        alert("CHECK AGAIN....!");
    }
});
function btnRowClick (){
    $('#tbCustomer>tr').click(function (){
        let id=$(this).children(":eq(0)").text();
        let name=$(this).children(":eq(1)").text();
        let address=$(this).children(":eq(2)").text();
        let contact=$(this).children(":eq(3)").text();

        // console.log(id,name,address,contact);

        $('#txtCid').val(id);
        $('#txtName').val(name);
        $('#txtAddress').val(address);
        $('#txtContact').val(contact);

    });

}

// function doubleClick(){
//     $('#tbCustomer>tr').dblclick(function (){
//         let id=$(this).children(":eq(0)").text();
//         let name=$(this).children(":eq(1)").text();
//         let address=$(this).children(":eq(2)").text();
//         let contact=$(this).children(":eq(3)").text();
//
//         console.log(id,name,address,contact);
//
//         $('#txtCid').remove(id);
//         $('#txtName').remove(name);
//         $('#txtAddress').remove(address);
//         $('#txtContact').remove(contact);
//
//     });
// }

 function remove(){
     $("#tbCustomer>tr").dblclick(function () {
         // $("#tb>tr").remove();
         // alert("fdfdf");
         $(this).remove();


     });
 }

function searchCustomer(id){
    for (let i=0;i<customers.length;i++){
        if (customers[i].customerID == id){
            return customers[i];
        }
    }
}

$('#btnsearchCustomer').click(function (){
    var  searchId=$("#txtFinds").val();
    var responce=searchCustomer(searchId);
    if (responce){
        $("#txtCid").val(responce.customerID);
        $("#txtName").val(responce.customerName);
        $("#txtAddress").val(responce.customerAddress);
        $("#txtContact").val(responce.customerContact);


    }else {
       clearFeilds();
        alert("No Customer Find...!")
    }
});


function deleteCustomer(cId){
    let customer=searchCustomer(cId);
    if (customer!=null){
        let indexof=customers.indexOf(customer);
        customers.splice(indexof,1);
        LoadAll();
    }
}

function updateCustomer(cId){
    let customer=searchCustomer(cId);
    if (customer!=null){
        customer.customerID=$('#txtCid').val();
        customer.customerName=$('#txtName').val();
        customer.customerAddress=$('#txtAddress').val();
        customer.customerContact=$('#txtContact').val();
       LoadAll;
        return true;
    }else {
        return false;
    }

}

function clearFeilds() {
    $("#txtCid,#txtName,#txtAddress,#txtContact").val("");
    $('#txtCid').focus();
}



