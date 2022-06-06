$(function() {
    $('input[type=radio]').change(function() {
        $.ajax({
            url: '/books/' + [[${book.id}]] + '/rate',
            type: 'POST',
            data: { rate: $(this).val() },
            success:function(data){
                $("#book-rating").load(location.href + " #book-rating");
            }
        });
        return false;
    });
});

$('#deleteApi').submit(function(event){
    event.preventDefault();

    $.ajax({
        url: '/books/' + [[${book.id}]],
        type: 'DELETE',
        success: function(result) {
            window.location.href = "https://abbey-books.ru/catalog/1";
        }
    })
});

$('#putApi').submit(function(event){
    event.preventDefault();

    $.ajax({
        url: '/books/update/' + [[${book.id}]],
        type: 'GET',
        fail(e) {
            console.log(e);
        }
    })
});