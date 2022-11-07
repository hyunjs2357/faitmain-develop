$(function(){

		$.getJSON( "/live/json/getEnableLiveReservationTime",{
			date: $("#reservationDate").val()
		})
			.done(function(data){
				
				var time = $("#hiddenTime").val();
				console.log(time);
				
				$("#reservationTime").remove();
				
				var resultStr = "<select id='reservationTime' class='form-control' name='reservationTime'>";
				$.each(data, function(index, value){
					resultStr += "<option value='"+value+"'>"+value+"시</option>";
				})
				resultStr += "<option value='"+time+"' selected>"+time+"시</option></select>";
				
				$("#timeDiv").append(resultStr);
			})

	$("#reservationDate").on("change", function(){
		$.getJSON( "/live/json/getEnableLiveReservationTime",{
			date: $("#reservationDate").val()
		})
			.done(function(data){
				
				$("#reservationTime").remove();
				
				var resultStr = "<select id='reservationTime' class='form-control' name='reservationTime'>";
				$.each(data, function(index, value){
					resultStr += "<option value='"+value+"'>"+value+"시</option>";
				})
				resultStr += "</select>";
				
				$("#timeDiv").append(resultStr);
			})
	});
	
	$('.btn').on('click', function(){
	
	var reservationDate = $('#reservationDate').val();
	var reservationTime = $('#reservationTime').val();
	var reservationTitle = $('#reservationTitle').val();
	var liveProduct_arr = [];
	$('input[name=liveProductNum]:checked').each(function(){
		var prodNum = $(this).val();
		liveProduct_arr.push(prodNum);
	})
	
	//reservationDate Validation Check
	if(!reservationDate){
		alert("년월일을 확인해 주세요");
		return;
	}
	
	//reservationTime Validation Check
	if(!reservationTime){
		alert("시작 시간을 확인해 주세요");
		return;
	}
	
	// reservationTitle Validation Check
	if(reservationTitle == null || reservationTitle.length < 1 || reservationTitle.length > 50){
		alert("방송 제목을 확인해 주세요.");
		return;
	}
	
	if(liveProduct_arr.length < 1 || liveProduct_arr.length > 5){
		alert("등록 상품을 확인해 주세요.");
		return;
	}
	
	$("form").attr("method", "POST").attr("action", "/live/updateLiveReservation").submit();
	
});
	
});

