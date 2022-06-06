const verifyCode = document.querySelector('#verifyCode');
const verifyButton = document.querySelector('#verifyButton');

verifyCode.addEventListener("input", validate);

function validate(){
    if(verifyCode.value === ""){
        verifyButton.setAttribute("disabled","disabled");
    } else {
        verifyButton.removeAttribute("disabled");
    }
}