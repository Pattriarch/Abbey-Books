function refreshNew() {

    document.querySelector(".dropdownn").style.pointerEvents = "none";

    // if (window.location.href != "http://localhost:8080/catalog/1/?sortField=id&sortDir=desc") {
    show('Сначала новые')
    $("#refreshDiv").animate({opacity: 0.2}, "slow", function() {
        $('#refreshDiv').load("http://localhost:8080/catalog/1/?sortField=id&sortDir=desc" +  ' #refreshDiv>*').animate({opacity: 1});
    });
    // window.location.href.searchParams.set('sortField', 'name')
    // }

    console.log(1);

    setTimeout(function run() {
        document.querySelector(".dropdownn").style.pointerEvents = "auto";
    }, 1500)
}

function refreshAlphabiteDesc() {

    document.querySelector(".dropdownn").style.pointerEvents = "none";

    if (window.location.href != "http://localhost:8080/catalog/1/?sortField=name&sortDir=desc") {
        show('По алфавиту Я-А')
        $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
            $('#refreshDiv').load("http://localhost:8080/catalog/1/?sortField=name&sortDir=desc" + ' #refreshDiv>*').animate({opacity: 1});
        });
    }

    setTimeout(function run() {
        document.querySelector(".dropdownn").style.pointerEvents = "auto";
    }, 1500)
}

function refreshAlphabiteAsc() {
    document.querySelector(".dropdownn").style.pointerEvents = "none";

    if (window.location.href != "http://localhost:8080/catalog/1/?sortField=name&sortDir=asc") {
        show('По алфавиту А-Я')
        $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
            $('#refreshDiv').load("http://localhost:8080/catalog/1/?sortField=name&sortDir=asc" + ' #refreshDiv>*').animate({opacity: 1});
        });
    }

    setTimeout(function run() {
        document.querySelector(".dropdownn").style.pointerEvents = "auto";
    }, 1500)
}

function refreshYearOfPublishingDesc() {
    document.querySelector(".dropdownn").style.pointerEvents = "none";

    if (window.location.href != "http://localhost:8080/catalog/1/?sortField=yearOfPublishing&sortDir=desc") {
        show('По году выхода (по убыванию)')
        $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
            $('#refreshDiv').load("http://localhost:8080/catalog/1/?sortField=yearOfPublishing&sortDir=desc" + ' #refreshDiv>*').animate({opacity: 1});
        });
    }

    setTimeout(function run() {
        document.querySelector(".dropdownn").style.pointerEvents = "auto";
    }, 1500)
}

function refreshYearOfPublishingAsc() {
    document.querySelector(".dropdownn").style.pointerEvents = "none";

    if (window.location.href != "http://localhost:8080/catalog/1/?sortField=yearOfPublishing&sortDir=asc") {
        show('По году выхода (по возрастанию)')
        $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
            $('#refreshDiv').load("http://localhost:8080/catalog/1/?sortField=yearOfPublishing&sortDir=asc" + ' #refreshDiv>*').animate({opacity: 1});
        });
    }

    setTimeout(function run() {
        document.querySelector(".dropdownn").style.pointerEvents = "auto";
    }, 1500)
}

function refreshPriceDesc() {
    document.querySelector(".dropdownn").style.pointerEvents = "none";

    if (window.location.href != "http://localhost:8080/catalog/1/?sortField=price&sortDir=desc") {
        show('По цене (по убыванию)')
        $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
            $('#refreshDiv').load("http://localhost:8080/catalog/1/?sortField=price&sortDir=desc" + ' #refreshDiv>*').animate({opacity: 1});
        });
    }

    setTimeout(function run() {
        document.querySelector(".dropdownn").style.pointerEvents = "auto";
    }, 1500)
}

function refreshPriceAsc() {
    document.querySelector(".dropdownn").style.pointerEvents = "none";

    if (window.location.href != "http://localhost:8080/catalog/1/?sortField=price&sortDir=asc") {
        show('По цене (по возрастанию)')
        $("#refreshDiv").animate({opacity: 0.2}, "slow", function () {
            $('#refreshDiv').load("http://localhost:8080/catalog/1/?sortField=price&sortDir=asc" + ' #refreshDiv>*').animate({opacity: 1});
        });
    }

    setTimeout(function run() {
        document.querySelector(".dropdownn").style.pointerEvents = "auto";
    }, 1500)
}