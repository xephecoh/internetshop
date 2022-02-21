$("#deleteForm").submit(function(event){
    event.preventDefault();
    const $form = $(this);
    const productId = $form.find('input[name="id"]').val();
    const url = 'http://localhost:8081/delete';

    $.ajax({
        type : 'DELETE',
        url : url,
        contentType: 'text/html',
        data : {id: productId },
        error : function(xhr, status, error){
            $('#msg').html('<span style=\'color:red;\'>'+error+'</span>')
        }
    });
});
