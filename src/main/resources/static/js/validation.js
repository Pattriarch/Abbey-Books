var nameError = document.getElementById('name-error');
var usernameError = document.getElementById('username-error');
var passwordError = document.getElementById('password-error');

function validateName() {
    var name = document.getElementById('register-name').value;

    if (name.length === 0) {
        nameError.innerHTML = 'Необходимо ввести ФИО';
        document.getElementById('register-name').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    if (username.length < 8) {
        nameError.innerHTML = 'ФИО должно содержать не менее 8 символов';
        document.getElementById('register-name').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    if (!name.match(/(?:[A-Za-zА-Яа-я]+ ){2}[A-Za-zА-Яа-я]+/)) {
        nameError.innerHTML = 'Введите полное ФИО';
        document.getElementById('register-name').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    document.getElementById('register-name').style.setProperty('border-color', 'seagreen', 'important');
    nameError.innerHTML = '&nbsp;';
    return true;
}



function validateUsername() {
    var username = document.getElementById('register-username').value;

    if (username.length === 0) {
        usernameError.innerHTML = 'Необходимо ввести имя пользователя';
        document.getElementById('register-username').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    if (username.length < 6) {
        usernameError.innerHTML = 'Имя пользователя должно содержать не менее 6 символов';
        document.getElementById('register-username').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    if (!username.match(/^[A-Za-z0-9]+$/)) {
        usernameError.innerHTML = 'Не используйте запрещенные символы';
        document.getElementById('register-username').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    document.getElementById('register-username').style.setProperty('border-color', 'seagreen', 'important');
    usernameError.innerHTML = '&nbsp;';
    return true;
}

function validatePassword() {
    var password = document.getElementById('register-password').value;

    if (password.length === 0) {
        passwordError.innerHTML = 'Необходимо ввести имя пользователя';
        document.getElementById('register-username').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    if (password.length < 6) {
        passwordError.innerHTML = 'Пароль должен содержать не менее 6 символов';
        document.getElementById('register-username').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    if (password.match(/^[<>${}/]$/)) {
        passwordError.innerHTML = 'Не используйте запрещенные символы';
        document.getElementById('register-username').style.setProperty('border-color', 'red', 'important');
        return false;
    }

    document.getElementById('register-username').style.setProperty('border-color', 'seagreen', 'important');
    passwordError.innerHTML = '&nbsp;';
    return true;
}