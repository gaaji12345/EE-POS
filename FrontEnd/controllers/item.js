//
//
$('#btnsaveitem').click(function (){
  //  saveItem();
    loadAllItems();
    // btnRowClick();
    // remove();
    // itemIds();

});
//
// function saveItem() {
//     let itemID=$('#txtitemcode').val();
//     let itemName=$('#txtitemname').val();
//     let itemPrice=$('#txtprice').val();
//     let itemQty=$('#txtqty').val();
//
//     let newItem = Object.assign({}, itemObj);
//     newItem.itemId=itemID;
//     newItem.itemName=itemName;
//     newItem.price=itemPrice;
//     newItem.qty=itemQty;
//     if (checkValidtionItem()) {
//
//         items.push(newItem);
//     }else {
//         clearFeilds();
//         loadAllItems();
//     }
//
// }
//
//
//
function loadAllItems(){
    $('#tbItem').empty();
    $.ajax({
        url: "http://localhost:4008/backend/item?option=GETALL",
        method: "GET",
        dataType: "json",//please convert the response into jason
        success: function (resp) {
            for (const item of resp.data) {
                // $("#tbjson").empty();
                console.log(typeof resp);
                let row = `<tr><td>${item.id}</td><td>${item.name}</td><td>${item.price}</td><td>${item.qty}</td></tr>`
                $("#tbItem").append(row);
            }
            rowBack();
        }
    })
}
//
// $('#btnUpdateItem').click(function (){
//
//     let consent=confirm("ARE YOU SHURE NEED TO UPDATE THIS ITEM..?");
//
//     if (consent){
//         for (let i = 0; i < items.length; i++) {
//             if ($('#txtitemcode').val()==items[i].itemId){
//                 items[i].itemId=$('#txtitemcode').val();
//                 items[i].itemNameite=$('#txtitemname').val();
//                 items[i].price=$('#txtprice').val();
//                 items[i].qty=$('#txtqty').val();
//                 //getAllCustomer();
//                 loadAllItems();
//                 //clearCustomerFeilds();
//                 clearFeilds();
//                 alert("UPDATED SUCSUS");
//                 break;
//             }
//         }
//     }
// });
//
// $('#btnDeleteItem').click(function (){
//     let deleteId= $('#txtitemcode').val();
//     if (deleteId) {
//         deleteItem(deleteId);
//         alert("DELETE...!");
//     }else {
//         alert("CHECK AGAIN....!");
//     }
// });
//
// function btnRowClick (){
//     $('#tbItem>tr').click(function (){
//         let id=$(this).children(":eq(0)").text();
//         let name=$(this).children(":eq(1)").text();
//         let price=$(this).children(":eq(2)").text();
//         let qty=$(this).children(":eq(3)").text();
//
//         // console.log(id,name,address,contact);
//
//         $('#txtitemcode').val(id);
//         $('#txtitemname').val(name);
//         $('#txtprice').val(price);
//         $('#txtqty').val(qty);
//
//     });
//
// }
//
// function remove(){
//     $("#tbItem>tr").dblclick(function () {
//         // $("#tb>tr").remove();
//         // alert("fdfdf");
//         $(this).remove();
//
//
//     });
// }
//
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
