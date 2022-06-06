// selecting required element
const element = document.querySelector(".pagination ul");
let totalPages = [[${totalPages}]];
let page = [[${currentPage}]];

//calling function with passing parameters and adding inside element which is ul tag
element.innerHTML = createPagination(totalPages, page);
function createPagination(totalPages, page) {

    let liTag = '';
    let active;
    let beforePage = page - 1;
    let afterPage = page + 1;
    if(page > 1){ //show the next button if the page value is greater than 1
        liTag += `<li class="btn prev" onclick="window.location.href='https://abbey-books.ru/authors/page-' + (page - 1) + '/?sortField=id&sortDir=desc'"><span><i class="fas fa-angle-left"></i> Prev</span></li>`;
    }

    if(page > 2){ //if page value is less than 2 then add 1 after the previous button
        liTag += `<li class="first numb" onclick="window.location.href='https://abbey-books.ru/authors/page-1/?sortField=id&sortDir=desc'"><span>1</span></li>`;
        if(page > 3){ //if page value is greater than 3 then add this (...) after the first li or page
            liTag += `<li class="dots"><span>...</span></li>`;
        }
    }

    // how many pages or li show before the current li
    if (page == totalPages && totalPages > 3) {
        beforePage = beforePage - 2;
    } else if (page == totalPages - 1 && totalPages > 3) {
        beforePage = beforePage - 1;
    }
    // how many pages or li show after the current li
    if (page == 1  && totalPages > 3) {
        afterPage = afterPage + 2;
    } else if (page == 2  && totalPages > 3) {
        afterPage  = afterPage + 1;
    }

    for (var plength = beforePage; plength <= afterPage; plength++) {
        if (plength > totalPages) { //if plength is greater than totalPage length then continue
            continue;
        }
        if (plength == 0) { //if plength is 0 than add +1 in plength value
            plength = plength + 1;
            asdkas = plength;
        }
        if (page == plength) { //if page is equal to plength than assign active string in the active variable
            active = "active";
        } else { //else leave empty to the active variable
            active = "";
        }
        liTag += `<li class="numb ${active}" onclick="window.location.href='https://abbey-books.ru/authors/page-' + (${plength}) + '/?sortField=id&sortDir=desc'"<span>${plength}</span></li>`;
    }

    if(page < totalPages - 1){ //if page value is less than totalPage value by -1 then show the last li or page
        if(page < totalPages - 2){ //if page value is less than totalPage value by -2 then add this (...) before the last li or page
            liTag += `<li class="dots"><span>...</span></li>`;
        }
        liTag += `<li class="last numb" onclick="window.location.href='https://abbey-books.ru/authors/page-' + (${totalPages}) + '/?sortField=id&sortDir=desc'"><span>${totalPages}</span></li>`;
    }

    if (page < totalPages) { //show the next button if the page value is less than totalPage(20)
        liTag += `<li class="btn next" onclick="window.location.href='https://abbey-books.ru/authors/page-' + (page + 1) + '/?sortField=id&sortDir=desc'"><span>Next <i class="fas fa-angle-right"></i></span></li>`;
    }
    element.innerHTML = liTag; //add li tag inside ul tag
    return liTag; //reurn the li tag
}