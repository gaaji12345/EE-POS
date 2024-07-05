 $(document).ready(function() {

LoadAll();

 });

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

$('#btnUpCustomer').click(function (){
    updateCustomer();

    // let consent=confirm("ARE YOU SHURE NEED TO UPDATE..?");
    //
    // if (consent){
    //   updateCustomer();
    // }else {
    //     alert("Not Allowed..!");
    // }



});

$('#btnDeleteCustomer').click(function (){

    let customerID = $("#txtCid").val();
    $.ajax({
        url:"http://localhost:4008/backend/customer?cusID="+customerID,
        method:"DELETE",
        // data:data ,
        success:function (res){
            console.log(res)
            if (res.status==200){
                alert(res.message);
                loadAllCustomer();

            }else if (res.status==400){
                alert(res.data);
            }else {
                alert(res.data)
            }

        },
        error:function (ob,status,t){
            console.log(ob);
            console.log(status);
            console.log(t);

        }
    })
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
    // for (let i=0;i<customers.length;i++){
    //     if (customers[i].customerID == id){
    //         return customers[i];
    //     }
    // }
}

$('#btnsearchCustomer').click(function (){

});


function deleteCustomer(cId){
    // let customer=searchCustomer(cId);
    // if (customer!=null){
    //     let indexof=customers.indexOf(customer);
    //     customers.splice(indexof,1);
    //     LoadAll();
    // }
}

function updateCustomer(){
    var cusOb={
        id: $('#txtCid').val(),
        name:$('#txtName').val(),
        address: $('#txtAddress').val(),
        salary:$('#txtContact').val()
    }

    // console.log(formdata);
    $.ajax({
        url:"http://localhost:4008/backend/customer",
        method:"PUT",
        contentType:"application/json",//request contetnt type json
        data:JSON.stringify(cusOb),
        success:function (res){
            if (res.status=200){
                alert(res.message)
                loadAllCustomer();
            }else if (res.status==400){
                alert(res.message)
            }else {
                alert(res.data)
            }

        },
        error:function (ob,txtStatus,error){
            alert(txtStatus);
            console.log(ob.responseText)
        }
    })

}

function clearFeilds() {
    $("#txtCid,#txtName,#txtAddress,#txtContact").val("");
    $('#txtCid').focus();
}



