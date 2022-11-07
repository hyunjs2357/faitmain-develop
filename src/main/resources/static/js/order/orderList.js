$(document).ready(function () {

    let searchForm = $('#searchForm');
    let moveForm = $('#moveForm');

    /* 작거 검색 버튼 동작 */
    $("#searchForm button").on("click", function (e) {

        e.preventDefault();
        /* 검색 키워드 유효성 검사 */
        if (!searchForm.find("input[name='keyword']").val()) {
            alert("키워드를 입력하십시오");
            return false;
        }
        searchForm.find("input[name='pageNum']").val("1");
        searchForm.submit();

    });
    /* 페이지 이동 버튼 */
    $(".pageMaker_btn a").on("click", function (e) {

        e.preventDefault();
        console.log($(this).attr("href"));
        moveForm.find("input[name='pageNum']").val($(this).attr("href"));
        moveForm.submit();

    });

    $(".delete_btn").on("click", function (e) {

        e.preventDefault();

        let id = $(this).data("orderNumber");

        $("#deleteForm").find("input[name='orderNumber']").val(id);
        $("#deleteForm").submit();
    });

}); // ready