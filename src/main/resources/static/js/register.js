$(function() {
    $('#repres').click(function() {
        $.ajax({
            type: 'POST',
            url: 'https://abbey-books.ru/register',
            data: { username: $(this).username.value,
                password: $(this).password.value }
        });
        return false;
    });
})