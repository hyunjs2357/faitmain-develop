	// 좋아요 가져오기
	function getStatus() {
	
	    const url = "https://vchatcloud.com/api/openapi/getLike";
	    let param = {
	        //"room_id": channel.roomId
	        "room_id": channelKey
	    };
	
	
	    $.post(url, param, function(data) {
	        if (data.result_cd == 1) {
	            $('#likeCounter').html(data.like_cnt);
	        } else {
	            console.log("조회 실패")
	        }
	    }, "json");
	}

	//let like_interval;

	// 좋아요 동기화
	function likeInif() {
	
	    //getStatus();
	    //like_interval = setInterval(getLike, 5 * 60 * 1000);
	
		$('#sendLike').click(function(e) {
			
			var obj = { "roomId" : channelKey };
		
			$.ajax({
				url : "/live/json/updateAlarm",
				type : "POST",
				data : JSON.stringify(obj),
				dataType : "JSON",
				contentType : "application/json; charset=utf-8",
			
				success: function(response){
					console.log("[requestPostBodyJson] : [success]");
				},
			
				error: function(xhr){
					console.log("[requestPostBodyJson] : [error] : " + JSON.stringify(xhr));
				},
			
				complete: function(data, textStatus) {
					console.log("[requestPostBodyJson] : [complete] : " + textStatus);
				}
			});
			
		})
	}

