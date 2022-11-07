 
$(document).ready(function () {

	let form = $("#infoForm");			// 페이지 이동 form(list page 이동)
	let mForm = $("#modifyForm"); 		// 페이지 데이터 수정 form
   
	 /* 목록 페이지 이동 버튼 */
    $("#list_btn").on("click", function(e){
        form.find("#boardNumber").remove();
        form.attr("action", "/customer/listBoard");
        form.submit();
    });
    
    /* 수정 하기 버튼 */
    $("#modify_btn").on("click", function(e){
        mForm.submit();
    });
    
    /* 취소 버튼 */
    $("#cancel_btn").on("click", function(e){
        form.attr("action", "/customer/detailBoard");
        form.submit();
    });    
}); // ready