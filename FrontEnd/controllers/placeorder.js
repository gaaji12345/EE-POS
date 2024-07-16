loadAllCustomerIds();
loadAllItems();


function loadAllCustomerIds() {
    $('#cmbcId').empty();
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: "http://localhost:4008/backend/customer?option=GETALL",
        method: "GET",
        dataType: "json",//please convert the response into jason
        success: function (resp) {
            for (const customer of resp.data) {
                 $("#cmbcId").empty();
                Cus += '<option value="' + customer.id + '">' + customer.id+ '</option>';

                console.log(typeof resp);
                $("#cmbcId").append(Cus);
            }
          //  btnRowClick();
            //rowBack();
        }
    });

}

$('#cmbcId').change(function () {
    $.ajax({
        url: "http://localhost:4008/backend/customer?option=GETALL",
        method: "GET",
        dataType: "json",
        success: function (resp) {
            for (const customer of resp.data) {
                $('#cName').val(customer.name);
                $('#cAddress').val(customer.address);
                $('#cSalary').val(customer.salary);
            }
        }
    })
});


function  loadAllItems(){
    $('#cmbIcode').empty();
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: "http://localhost:4008/backend/item?option=GETALL",
        method: "GET",
        dataType: "json",//please convert the response into jason
        success: function (resp) {
            for (const item of resp.data) {
                $("#cmbIcode").empty();
                Cus += '<option value="' + item.id + '">' + item.id+ '</option>';

                console.log(typeof resp);
                $("#cmbIcode").append(Cus);
            }
            //  btnRowClick();
            //rowBack();
        }
    });
}


$('#cmbIcode').change(function () {
    $.ajax({
        url: "http://localhost:4008/backend/item?option=GETALL",
        method: "GET",
        dataType: "json",
        success: function (resp) {
            for (const item of resp.data) {
                $('#itemName').val(item.name);
                $('#itemPrice').val(item.price);
                $('#iqtyOnHand').val(item.qty);
            }
        }
    })
});

function placeOrder() {

    let order = {
        oid: "",
        date: "",
        customerID: "",
        orderdetails: []
    };

    let cusId = $("#cmbcId").val();
    let date = $("#order-date").val();
    let OId = $("#order-id").val();

    $('#order-table>tr').each(function () {
        let code = $(this).children().eq(0).text();
        let qty = $(this).children().eq(3).text();
        let price = $(this).children().eq(2).text();
        let orderDetails = {
            oid: OId,
            code: code,
            qty: parseInt(qty),
            unitPrice: parseFloat(price)
        };

        order.orderdetails.push(orderDetails);
        //orderdetail.push(orderDetails);
    });

    order.oid = OId;
    order.date = date;
    order.customerID = cusId;
    //orders.push(order);

    $.ajax({
        url: "http://localhost:4008/backend/order",
        method: "POST",
        data: JSON.stringify(order),
        contentType: "application/json",
        success: function (res, textStatus, jsXH) {
            console.log(res);
            alert("Order Added Successfully");
           // generateOrderId();
        },
        error: function (ob, textStatus, error) {
            alert(textStatus + " : Error Order Not Added")
        }
    });
}

$("#order-add-item").click(function () {
    let id = $("#cmbIcode").val();
    let name = $("#itemName").val();
    let price = $("#itemPrice").val();
    let qty = $("#orderQty").val();
    let total = parseFloat(price) * parseFloat(qty);
    let allTotal = 0;
    let itemExists = false;

    $('#order-table>tr').each(function (e) {
        let check =$(this).children().eq(0).text();
        if (id === check){
            let liQty = $(this).children().eq(3).text();
            let upQty = parseInt(liQty)+parseInt(qty);
            $(this).children().eq(1).text(name);
            $(this).children().eq(2).text(price);
            $(this).children().eq(3).text(upQty);
            $(this).children().eq(4).text(upQty * parseFloat(price));
            itemExists = true;
            return false;
        }
    });

    if (!itemExists){
        let row = `<tr>
                     <td>${id}</td>
                     <td>${name}</td>
                     <td>${price}</td>
                     <td>${qty}</td>
                     <td>${total}</td>
                    </tr>`;

        $("#order-table").append(row);

    }

    $('#order-table>tr').each(function (e) {
        let full = $(this).children().eq(4).text();
        allTotal += parseFloat(full);
    });

    $("#total").text(allTotal);

    $("#subtotal").text(allTotal);
});





