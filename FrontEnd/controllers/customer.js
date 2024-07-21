 $(document).ready(function() {

LoadAll();


 });


$('#btnsaveCustomer').click(function (){
 saveCustomer();
  LoadAll();
 // setCusIds();
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
           LoadAll();

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
   // return new Promise(function (resolve, reject) {

        $.ajax({
            url: "http://localhost:4008/backend/customer",
            method: "GET",
            dataType: "json",//please convert the response into jason
            success: function (resp) {
                for (const customer of resp) {
                    // $("#tbjson").empty();
                    console.log(typeof resp);
                    let row = `<tr><td>${customer.id}</td><td>${customer.name}</td><td>${customer.address}</td><td>${customer.salary}</td></tr>`
                    $("#tbCustomer").append(row);
                }
      btnRowClick();
                //rowBack();
            }
        })



}

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




$('#btnUpCustomer').click(function (){
    updateCustomer();

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

// function searchCustomer(id){
//     console.log(id);
//     return new Promise(function (resolve, reject) {
//         $.ajax({
//             url:"http://localhost:4008/backend/customer?customerId="+id+"",
//             method: "GET",
//             dataType:"json",
//             success:function (res) {
//                 console.log(res);
//                 resolve(res);
//             },
//             error:function (ob, textStatus, error) {
//                 resolve(error);
//             }
//         });
//     });
// }

// $('#btnsearchCustomer').click(function (){
//
//     let id = $("#txtCid").val();
//     searchCustomer(id).then(function (res){
//         $("#txtName").val(res.name);
//         $("#txtAddress").val(res.address);
//     });
//
//    // alert("gaaji")
//    // setClBtn();
// });





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



