

$('#btnsaveCustomer').click(function (){
 saveCustomer();
  LoadAll();
});


function saveCustomer() {
    var data= $("#customerForm").serialize();
    console.log(data);
    $.ajax({
        url:"http://localhost:4008/backend/customer",
        method:"POST",
        data:data,
        success:function (res){
            if (res.status == 200){
                alert(res.message);
                loadAllCustomer();

            }else {
                alert(res.data)
            }
            // console.log(res);
        },
        error:function (ob,txtStatus,error){
            alert(txtStatus);

        }
    })

}


function LoadAll() {
    $('#tbCustomer').empty();

    $.ajax({
        url:"http://localhost:4008/backend/customer?option=GETALL",
        method:"GET",
        dataType:"json",//please convert the response into jason
        success:function (resp){
            for (const customer of resp.data){
                // $("#tbjson").empty();
                console.log( typeof resp);
                let row=`<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`
                $("#tbCustomer").append(row);
            }
            rowBack();
        }
    })

    function rowBack(){
        $("#tbCustomer>tr").click(function (){
            let id= $(this).children().eq(0).text();
            let name= $(this).children().eq(1).text();
            let address= $(this).children().eq(2).text();
            let salary= $(this).children().eq(3).text();

            $("#txtCid").val(id);
            $("#txtName").val(name);
            $("#txtAddress").val(address);
            $("#txtContact").val(salary);



        })


    }


}

// $('#btnUpCustomer').click(function (){
//
//     let consent=confirm("ARE YOU SHURE NEED TO UPDATE..?");
//
//     if (consent){
//         for (let i = 0; i < customers.length; i++) {
//             if ($('#txtCid').val()==customers[i].customerID){
//                 customers[i].customerID=$('#txtCid').val();
//                 customers[i].customerName=$('#txtName').val();
//                 customers[i].customerAddress=$('#txtAddress').val();
//                 customers[i].customerContact=$('#txtContact').val();
//                 //getAllCustomer();
//                 LoadAll();
//                 //clearCustomerFeilds();
//                 clearFeilds();
//                 alert("UPDATED SUCSUS");
//                 break;
//             }
//         }
//     }
//
//
//
// });

// $('#btnDeleteCustomer').click(function (){
//
//     let deleteId= $('#txtCid').val();
//     if (deleteId) {
//         deleteCustomer(deleteId);
//         alert("DELETE...!");
//     }else {
//         alert("CHECK AGAIN....!");
//     }
// });
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
    // for (let i=0;i<customers.length;i++){
    //     if (customers[i].customerID == id){
    //         return customers[i];
    //     }
    // }
}

$('#btnsearchCustomer').click(function (){
    // var  searchId=$("#txtFinds").val();
    // var responce=searchCustomer(searchId);
    // if (responce){
    //     $("#txtCid").val(responce.customerID);
    //     $("#txtName").val(responce.customerName);
    //     $("#txtAddress").val(responce.customerAddress);
    //     $("#txtContact").val(responce.customerContact);
    //
    //
    // }else {
    //    clearFeilds();
    //     alert("No Customer Find...!")
    // }
});


function deleteCustomer(cId){
    // let customer=searchCustomer(cId);
    // if (customer!=null){
    //     let indexof=customers.indexOf(customer);
    //     customers.splice(indexof,1);
    //     LoadAll();
    // }
}

function updateCustomer(cId){
    // let customer=searchCustomer(cId);
    // if (customer!=null){
    //     customer.customerID=$('#txtCid').val();
    //     customer.customerName=$('#txtName').val();
    //     customer.customerAddress=$('#txtAddress').val();
    //     customer.customerContact=$('#txtContact').val();
    //    LoadAll;
    //     return true;
    // }else {
    //     return false;
    // }

}

function clearFeilds() {
    $("#txtCid,#txtName,#txtAddress,#txtContact").val("");
    $('#txtCid').focus();
}



