function testContent(book_id) {
    $.ajax({
        url: '/addToCart/' + book_id,
        type: 'POST',
        success:function(data){
            $('#bottom_holder' + book_id).animate({opacity: 0.2}, 300, function () {
                $('#bottom_holder' + book_id).load("https://abbey-books.ru/books/" + book_id + ' #bottom_holder' + book_id + '>*').animate({opacity: 1});
            });
        }
    });
    return false;
}