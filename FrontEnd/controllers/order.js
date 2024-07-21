
let orders=[];
$(document).ready(function() {

   generateOrderId();

});
function generateOrderId() {

    loadOrderAr().then(function (orders) {
        if (orders.length === 0) {
            $("#order-id").val("OID-1");
        } else {
            console.log(orders[orders.length - 1].oid);
            var id = orders[orders.length - 1].oid.split("-")[1];
            var tempId = parseInt(id, 10);
            if (!isNaN(tempId)) {
                tempId = tempId + 1;
                $("#order-id").val("OID-" + tempId);
            } else {
                console.error("Error converting order ID to a number");
            }
        }
    }).catch(function (error) {
        //console.error("Error loading order data:", error);
    });
}

function loadOrderAr() {
    return new Promise(function (resolve, reject) {
        var ar;
        $.ajax({
            url: "http://localhost:4008/backend/order",
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

function searchOrder(id) {
    console.log(id);
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: "http://localhost:8080/BackEnd/order?oid=" + id + "&info=search",
            method: "GET",
            dataType: "json",
            success: function (res) {
                console.log(res);
                resolve(res);
            },
            error: function (ob, textStatus, error) {
                resolve(error);
            }
        });
    });
}

function setCusIds() {
    $("#cmbcId").empty();
    loadCusAr().then(function (cusDB) {
        cusDB.forEach(function (e) {
            let id = e.id;
            let select = `<option selected>${id}</option>`;
            $("#cmbcId").append(select);
        });
    });

}

$("#cmbcId").change(function () {
    $(this).val($(this).val());
    searchCustomer($(this).val()).then(function (customer) {
        $("#cName").val(customer.name);
        $("#cAddress").val(customer.address);

        // setAndTriggerValue($("#cName"), customer.name);
        // setAndTriggerValue($("#cAddress"), customer.address);
        // dateCheck();
    });
});

function loadCusAr(){
    return new Promise(function (resolve, reject) {
        var ar;
        $.ajax({
            url: "http://localhost:8080/BackEnd/customer?option=GETALL",
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



