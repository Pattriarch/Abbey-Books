function show(anything) {
    document.querySelector('.textBox').value = anything;
}

let dropdown = document.querySelector('.dropdownn');
dropdown.onclick = function() {
    dropdown.classList.toggle('active');
}

function refreshNew() {
    show('Сначала новые')
    $("#refreshDiv").animate({opacity: 0.2}, "slow", function() {
        $('#refreshDiv').load("https://abbey-books.ru/authors/" + authorId + "/page-1/?sortField=id&sortDir=desc" +  ' #refreshDiv>*').animate({opacity: 1});
    });
}

function refreshAlphabiteDesc() {
    show('По алфавиту Я-А')
    $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
        $('#refreshDiv').load("https://abbey-books.ru/authors/" + authorId + "/page-1/?sortField=name&sortDir=desc" + ' #refreshDiv>*').animate({opacity: 1});
    });
}

function refreshAlphabiteAsc() {
    show('По алфавиту А-Я')
    $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
        $('#refreshDiv').load("https://abbey-books.ru/authors/" + authorId + "/page-1/?sortField=name&sortDir=asc" + ' #refreshDiv>*').animate({opacity: 1});
    });
}

function refreshYearOfPublishingDesc() {
    show('По году выхода (по убыванию)')
    $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
        $('#refreshDiv').load("https://abbey-books.ru/authors/" + authorId + "/page-1/?sortField=yearOfPublishing&sortDir=desc" + ' #refreshDiv>*').animate({opacity: 1});
    });
}

function refreshYearOfPublishingAsc() {
    show('По году выхода (по возрастанию)')
    $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
        $('#refreshDiv').load("https://abbey-books.ru/authors/" + authorId + "/page-1/?sortField=yearOfPublishing&sortDir=asc" + ' #refreshDiv>*').animate({opacity: 1});
    });
}

function refreshPriceDesc() {
    show('По цене (по убыванию)')
    $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
        $('#refreshDiv').load("https://abbey-books.ru/authors/" + authorId + "/page-1/?sortField=price&sortDir=desc" + ' #refreshDiv>*').animate({opacity: 1});
    });
}

function refreshPriceAsc() {
    show('По цене (по возрастанию)')
    $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
        $('#refreshDiv').load("https://abbey-books.ru/authors/" + authorId + "/page-1/?sortField=price&sortDir=asc" + ' #refreshDiv>*').animate({opacity: 1});
    });
}