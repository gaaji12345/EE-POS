
$(document).ready(function() {

loadAItems()

});
$('#btnsaveitem').click(function (){
   saveItem();
    loadAItems();


});
//
function saveItem() {
    var data= $("#itemForm").serialize();
    console.log(data);
    $.ajax({
        url:"http://localhost:4008/backend/item",
        method:"POST",
        data:data,
        success:function (res){
            if (res.status == 200){
                alert(res.message);
              loadAItems();

            }else {
                alert(res.data)
            }
        },
        error:function (ob,txtStatus,error){
            alert(txtStatus);

        }
    })

}

function loadAItems(){
    $('#tbItem').empty();
    $.ajax({
        url: "http://localhost:4008/backend/item",
        method: "GET",
        dataType: "json",//please convert the response into jason
        success: function (resp) {
            for (const item of resp.data) {
                // $("#tbjson").empty();
                console.log(typeof resp);
                let row = `<tr><td>${item.code}</td><td>${item.name}</td><td>${item.price}</td><td>${item.qty}</td></tr>`
                $("#tbItem").append(row);
            }
            rowBack();
        }
    })
}

function  rowBack(){
    $("#tbItem>tr").click(function (){
        let id= $(this).children().eq(0).text();
        let desc= $(this).children().eq(1).text();
        let price= $(this).children().eq(2).text();
        let qty= $(this).children().eq(3).text();

        $("#txtitemcode").val(id);
        $("#txtitemname").val(desc);
        $("#txtprice").val(price);
        $("#txtqty").val(qty);



    })
}

//
$('#btnUpdateItem').click(function (){

    let consent=confirm("ARE YOU SHURE NEED TO UPDATE THIS ITEM..?");

    if (consent) {
      itemUpdate();
    }else {
        alert("NO Try Again..!")
    }
});
//
$('#btnDeleteItem').click(function (){
  let cnform=confirm("ARE YOU SHURE YOU NEED TO DELETE THIS..?")

    if (cnform){
        itemDelete();
    }else {
        alert("Try Again...!");
    }
});

 function  itemUpdate(){
     var itemOb={
         code: $('#txtitemcode').val(),
         desc:$('#txtitemname').val(),
         price: $('#txtprice').val(),
         qty:$('#txtqty').val()
     }

     // console.log(formdata);
     $.ajax({
         url:"http://localhost:4008/backend/item",
         method:"PUT",
         contentType:"application/json",//request contetnt type json
         data:JSON.stringify(itemOb),
         success:function (res){
             if (res.status=200){
                 alert(res.message)
                 loadAllItems();
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

 function itemDelete(){

     let itemId = $("#txtitemcode").val();
     $.ajax({
         url:"http://localhost:4008/backend/item?itemCode="+itemId,
         method:"DELETE",
         // data:data ,
         success:function (res){
             console.log(res)
             if (res.status==200){
                 alert(res.message);
             loadAItems();

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
 }
// function searchItem(id){
//     for (let i=0;i<items.length;i++){
//         if (items[i].itemId == id){
//             return items[i];
//         }
//     }
// }
//
// $('#btnitemsearch').click(function (){
//     var  searchId=$("#findItem").val();
//     var responce=searchItem(searchId);
//     if (responce){
//         $("#txtitemcode").val(responce.itemId);
//         $("#txtitemname").val(responce.itemName);
//         $("#txtprice").val(responce.price);
//         $("#txtqty").val(responce.qty);
//
//
//     }else {
//         clearFeilds();
//         alert("No Item Find...!")
//     }
// });
//
// function deleteItem(iId){
//     let item=searchItem(iId);
//     if (item!=null){
//         let indexof=items.indexOf(item);
//         items.splice(indexof,1);
//        loadAllItems();
//     }
// }
//
//
// function clearFeilds() {
//     $("#txtitemcode,#txtitemname,#txtprice,#txtqty").val("");
//     $('#txtitemcode').focus();
// }
//
//
//
