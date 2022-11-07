 
$(document).ready(function () {

	let form = $("#infoForm");

    let moveForm = $("#moveForm");	
 /* 페이지 이동 버튼 */
   	 $(".pageInfo a").on("click", function(e){
 		
 		e.preventDefault();
         moveForm.find("input[name='pageNum']").val($(this).attr("href"));
         moveForm.attr("action", "/customer/listBoard");
         moveForm.submit();
 	});

    
/* 목록 페이지 이동 버튼 */  
    $("#list_btn").on("click", function(e){
		form.find("#boardNumber").remove();
		form.attr("action", "/customer/listBoard");
		form.submit();
	});
  	
	
/*	$(".move").on("click", function(e){
		e.preventDefault();
		
		moveForm.append("<input type='hidden' name='boardNumber' th:value='"+$(this).attr("th:href")+"'>");
		mveForm.attr("action", "/customer/detailBoard");
		moveForm.submit();
	});*/o
	

    
      
}); // ready


