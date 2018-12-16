function deleteItem(phoneId) {
    document.getElementById('delete'+phoneId).removeAttribute("disabled")
    document.getElementById('delete'+phoneId).value = phoneId;
}