LoadAllCustomerIds();
function LoadAllCustomerIds() {
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


