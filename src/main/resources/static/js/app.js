// document.addEventListener('DOMContentLoaded', () => {
//     const thumbnails = document.querySelectorAll('.thumbnail');
//
//     thumbnails.forEach(thumbnail => {
//         thumbnail.addEventListener('click', () => {
//             if (!$(thumbnail).hasClass("show")) {
//                 const id = $(thumbnail).attr('id');
//                 const src = id;
//                 console.log(src)
//
//                 $(".slider").addClass(".zoom");
//                 $(".slider").animate({opacity: 0.2}, "slow", function() {
//                     $(this).css({'background-image': 'url(' + src + ')'}).animate({opacity: 1});
//                 })
//
//                 console.log(src);
//
//                 setTimeout(() => {
//                     $(".slider").removeClass("zoom");
//                 }, 510);
//
//                 let thumbnail_active = document.querySelector(".thumbnail.show");
//                 $(thumbnail_active).removeClass("show");
//
//                 $(thumbnail).addClass("show");
//
//                 thumbnail_active = thumbnail;
//
//                 console.log(src);
//             }
//         })
//     })
// })