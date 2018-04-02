$(function() {
    var currentPage = $("#currentPage").val();
    var countPages = $("#countPages").val();

    if (currentPage === "") {
        currentPage = 1;
    }

    currentPage = Number(currentPage);

    $('.pages').mxpage({
        perPage: 10,
        currentPage: currentPage,
        maxPage: countPages,
        previousText: 'Prev',
        nextText: 'Next',
        frontPageText: 'First',
        lastPageText: 'Last',
        click: function (index, $element) {
            var urlWithParams = new URL(window.location.href);
            urlWithParams.searchParams.set('page', index);
            location.href = urlWithParams;
        }
    });
});