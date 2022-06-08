document.addEventListener('DOMContentLoaded', () => {
    const thumbnails = document.querySelectorAll('.thumbnail');

    thumbnails.forEach(thumbnail => {
        thumbnail.addEventListener('click', () => {
            if (!$(thumbnail).hasClass("show")) {
                const src = $(thumbnail).attr('src');

                $(".slider").addClass(".zoom");

                console.log(src);


                $("#myImage").animate({opacity: 0.2}, "slow", function() {
                    $("#myImage").attr({"src": src}).animate({opacity: 1});
                })

                $("#myImageFull").attr({"src": src});

                setTimeout(() => {
                    $(".slider").removeClass("zoom");
                }, 510);

                let thumbnail_active = document.querySelector(".thumbnail.show");
                $(thumbnail_active).removeClass("show");

                $(thumbnail).addClass("show");

                thumbnail_active = thumbnail;
            }
        })
    })
});

const modal = document.querySelector('.book-view');
const previews = document.querySelector(".slider");
const original = document.querySelector(".full-img");
const imgText = document.querySelector(".caption");

previews.addEventListener("click", () => {
    modal.classList.add("open");
    original.classList.add("open");
});

modal.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal12')) {
        modal.classList.remove('open');
        original.classList.remove("open");
    }
});

var token = $("meta[name='_csrf']").attr("content");
var header = $("meta[name='_csrf_header']").attr("content");
$(document).ajaxSend(function(e, xhr, options) {
    xhr.setRequestHeader(header, token);
});