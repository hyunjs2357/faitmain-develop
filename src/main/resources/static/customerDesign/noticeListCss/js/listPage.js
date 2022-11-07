 
$(document).ready(function () {

	let form = $("#infoForm");
	let mForm = $("#modifyForm"); 
    let searchForm = $("#searchForm");
    let moveForm = $("#moveForm");	
 /* 페이지 이동 버튼 */
   	 $(".pageInfo a").on("click", function(e){
 		
 		e.preventDefault();
         moveForm.find("input[name='pageNum']").val($(this).attr("href"));
         moveForm.attr("action", "/customer/listBoard");
         moveForm.submit();
 	});
	
	 /* 게시판 검색 버튼 동작 */
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
/* 삭제 버튼 */
    $("#delete_btn").on("click", function (e) {
 		form.attr("action", "/customer/deleteBoard");
 		form.attr("method", "post");
 		form.submit();
    });
    
/* 목록 페이지 이동 버튼 */  
    $("#list_btn").on("click", function(e){
		form.find("#boardNumber").remove();
		form.attr("action", "/customer/listBoard");
		form.submit();
	});
/* 수정 하기 버튼 */
	$("#modify_btn").on("click", function(e){
		form.attr("action", "/customer/updateBoard");
		form.submit();
	});
/* 취소 버튼 */
    $("#cancel_btn").on("click", function(e){
        form.attr("action", "/customer/detailBoard");
        form.submit();
    });   	
	
/*	$(".move").on("click", function(e){
		e.preventDefault();
		
		moveForm.append("<input type='hidden' name='boardNumber' th:value='"+$(this).attr("th:href")+"'>");
		mveForm.attr("action", "/customer/detailBoard");
		moveForm.submit();
	});*/o
	

    
      
}); // ready

$(document).ready(function(){
	
	let result = '<th:value="${result}"/>';
	
	checkAlert(result);
	console.log(result);
	
	function checkAlert(result){
		
		if(result === ''){
			return;
		}
		
		if(result === "enroll success"){
			alert("등록되었습니다.");
		}
		
		if(result === "update success"){
			alert("수정되었습니다.");
		}
		
		if(result === "delete success"){
			alert("삭제되었습니다.");
		}
	}
});