var sortBy = function (what) {
    var compare = '${param.sortBy}';
    if (what === compare)
        what += '_desc';
    $('#sortByInput').val(what);
    $('#sortByForm').submit();
};