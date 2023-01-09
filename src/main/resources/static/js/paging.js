const totalPage = $("#total-page").val();
const currentPage = $("#current-page").val();

$("#pagination").twbsPagination({
    totalPages: totalPage == 0 ? 1 : totalPage,
    visible: 10,
    startPage: parseInt(currentPage),

    onPageClick: function (event, page) {
        if(currentPage != page) {
            $("#current-page").val(page);
            $("#form-paging").submit();
        }
    }
})