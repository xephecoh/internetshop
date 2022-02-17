$("#user-form").submit(function(event){
    event.preventDefault();
    var $form = $(this);
    var productId = $form.find('input[name="id"]').val();
    var url = 'http://localhost:8080/update';
    var productName = $form.find('input[name="name"]').val();
    var productPrice = $form.find('input[name="price"]').val();

    $.ajax({
        type : 'PUT',
        url : url,
        contentType: 'application/json',
        data : JSON.stringify({name: productName, price: productPrice, id: productId }),
        error: function(xhr, status, error){
            $('#msg').html('<span style=\'color:red;\'>'+error+'</span>')
        }
    });
});