$('#customer').css('display','none');

$('#item').css('display','none');

$('#order').css('display','none');



$('#navCustomer').click(function (){
    $('#item').css('display','none');
    $('#customer').css('display','inline-block');
    $('#navMain').css('display','none');
    $('#order').css('display','none');

});

$('#navItem').click(function (){
    $('#customer').css('display','none');
    $('#item').css('display','inline-block');
    $('#navMain').css('display','none');
    $('#order').css('display','none');

});

$('#navOrder').click(function (){
    $('#customer').css('display','none');
    $('#item').css('display','none');
    $('#navMain').css('display','none');
    $('#order').css('display','Inline-block');

});
$('#navHome').click(function (){
    $('#customer').css('display','none');
    $('#navMain').css('display','inline-block');
    $('#item').css('display','none');
    $('#order').css('display','none');

});

$('#img1').click(function (){
    $('#navMain').css('display','none');
    $('#customer').css('display','inline-block');
    $('#item').css('display','none');
    $('#order').css('display','none');

});

$('#img2').click(function (){
    $('#navMain').css('display','none');
    $('#item').css('display','inline-block');
    $('#customer').css('display','none');
    $('#order').css('display','none');

});
$('#img3').click(function (){
    $('#navMain').css('display','none');
    $('#customer').css('display','none');
    $('#item').css('display','none');
    $('#order').css('display','Inline-block');

});



