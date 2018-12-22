function deleteItem(phoneId) {
    document.getElementById('delete'+phoneId).removeAttribute("disabled")
    document.getElementById('delete'+phoneId).value = phoneId;
}
function update() {
    [].forEach.call(document.getElementsByClassName("quantityField"), function (element) {

        document.getElementById("updateSubmit").appendChild(element.cloneNode());
    });
}