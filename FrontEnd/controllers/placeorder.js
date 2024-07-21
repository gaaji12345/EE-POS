loadAllCustomerIds();
loadAllItems();


function loadAllCustomerIds() {
    $('#cmbcId').empty();
    // return new Promise(function (resolve, reject) {
    var Cus = '';
    $.ajax({
        url: "http://localhost:4008/backend/customer",
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
        url: "http://localhost:4008/backend/customer",
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
        url: "http://localhost:4008/backend/item",
        method: "GET",
        dataType: "json",//please convert the response into jason
        success: function (resp) {
            for (const item of resp.data) {
                $("#cmbIcode").empty();
                Cus += '<option value="' + item.code + '">' + item.code+ '</option>';

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
        url: "http://localhost:4008/backend/item",
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

$("#txtDiscount").on("keydown keyup input", function (e) {
    let total = parseFloat($("#total").text());
    if (total > 0) {
        let discount = $(this).val();
        let fullAm = (total / 100 * discount);
        total -= fullAm;
        $("#subtotal").text(total);
       // setAndTriggerValue($("#subtotal"), total);
    }

});

$("#txtCash").on("keydown keyup input", function () {
   // cashValidate();
    setBalance();
});

function setBalance() {
    let subtotalText = $("#subtotal").text();
    let cashText = $("#txtCash").val();
    let subtotal = parseFloat(subtotalText);
    let cash = parseFloat(cashText);
    if (!isNaN(subtotal) && !isNaN(cash)) {
        let balance = cash - subtotal;
        $("#txtBalance").val(balance.toFixed(2));
    } else {
        $("#txtBalance").val("0");
    }
}

$("#order-date").on("input", function () {
    dateCheck();
});

function dateCheck() {
    let val = $("#order-date").val();
    if (val==""){
        $("#order-date").css("border", "2px solid red");
        return false
    }else {
        $("#order-date").css("border", "2px solid green");
        return true;
    }
}
$("#btnSubmitOrder").click(function () {
    let oId = $("#order-id").val();
    // if (itemValidate()) {
    if (!searchOrder(oId)) {
        //  if (cashValidate()) {
        if (dateCheck()) {
            placeOrder();
            alert("Order Place Successfully");
            //clearAll();
            generateOrderId();
        } else {
            alert("Insert Date!");
        }
    } else {
        alert("Order Already Registered");
    }

});

function loadOrderDetailAr() {
    return new Promise(function (resolve, reject) {
        var ar;
        $.ajax({
            url: "http://localhost:8080/BackEnd/orderDetails",
            method: "GET",
            success: function (res) {
                console.log(res);
                ar = res;
                resolve(ar);
            },
            error: function (error) {
                reject(error);
            }
        });
    });
}

$("#order-id").on("keydown", async function (e) {
    $("#order-table").empty();
    if (e.keyCode === 13) {
        let id = $("#order-id").val();
        let order = await searchOrder(id);
      //  orders.find(function (order) {
        if (Object.keys(order).length !== 0) {
                $("#order-table").empty();
            let date = order.date;
            let cusId = order.id;

            let customer = await searchCustomer(cusId);

                    if (Object.keys(customer).length !== 0) {
                        let cusName = customer.name;
                        let address = customer.address;

                        $("#cmbcId").val(cusId);
                        $("#cName").val(cusName);
                        $("#cAddress").val(address);
                        $("#order-date").val(date);
                    }

                let code;
                let qty;
                let unitPrice;
                let itemName;

            loadOrderDetailAr().then(async function (detail) {
                if (detail.length !== 0) {
                    for (var info of detail) {
                        if (info.oid == id) {
                            console.log(info.oid, info.itmCode, info.itmQTY, info.itmPrice);
                            code = info.itmCode;
                            qty = info.itmQTY;
                            unitPrice = info.itmPrice;
                           // let res = await searchItem(code);

                            if (Object.keys(res).length !== 0) {
                                itemName = res.itmName;
                                console.log(itemName);
                            }

                            let total = parseFloat(unitPrice) * parseFloat(qty);
                            let row = `<tr>
                     <td>${code}</td>
                     <td>${itemName}</td>
                     <td>${unitPrice}</td>
                     <td>${qty}</td>
                     <td>${total}</td>
                    </tr>`;
                            $("#order-table").append(row);

                        } else {
                            alert("Order not Registered");
                        }

                }
            }
        });
    }
  }
});










