$(function () {
	
	/****************** 필요 data *****************************/
	var productGroupNumber = $("#productGroupNumber").val();
	var productGroupName = $("#productGroupName").val();
	var productPrice = Number($("#productPrice").val());
	var productMainImage = $("#productMainImage").val();
	var productQuantity = Number($("#productQuantity").val());
	//console.log("productGroupNumber : " + productGroupNumber)
    /*********************************************************/
    
    let quantity = $(".mtext-104.cl3.txt-center.num-product").val(); // 수량 input 값 저장

	/* 수량 +  */
    $(".fs-16.zmdi.zmdi-plus").on("click", function () { 
		console.log("productQuantity : " + productQuantity);
        if (quantity < productQuantity) {
            //$(".quantity_input").val(++quantity);
            console.log("+++ : " + $(".mtext-104.cl3.txt-center.num-product").val());
            $(".mtext-104.cl3.txt-center.num-product").val(++quantity);            
            
            let totalPrice = Number($("#totalPrice").val());
			
			totalPrice += productPrice;
			
			$("#totalPrice").val(totalPrice);
            $("#totalPriceArea").text(totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
            
            //$("#totalPrice").val(Number($("#totalPrice").val()) + productPrice);
            //$("#totalPriceArea").text(($("#totalPrice").val()).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));            
        } else {
            alert("최대 수량입니다!");
        }
    });

	/* 수량 - */
	$(".fs-16.zmdi.zmdi-minus").on("click", function () { 
        if (quantity > 1) {
			console.log("--- : " + $(".mtext-104.cl3.txt-center.num-product").val());
            $(".mtext-104.cl3.txt-center.num-product").val(--quantity);
            //$(".quantity_input").val(--quantity);
            
            let totalPrice = Number($("#totalPrice").val());
			
			totalPrice -= productPrice;
			
			$("#totalPrice").val(totalPrice);
            $("#totalPriceArea").text(totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
            
            //$("#totalPrice").val(Number($("#totalPrice").val()) - productPrice);
            //$("#totalPriceArea").text(($("#totalPrice").val()).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
        }
    });
    
	/* 장바구니 담기 */
    $("#addCart").on("click", function () { 

        console.log("product : " + $("#getProductForm").serialize());
		
		let buyerId = $("input[name=buyerId]").val();
		console.log("buyerId : " + buyerId);
		
		if(buyerId == null){ // 로그인 안했을 때
			
			console.log("buyerId null");
			self.location = "/user/login";
			
		}else{ // 로그인 했을 때
			
			console.log("buyerId exist");
			
			if (Number($("#totalPrice").val()) == '0') { // 옵션 선택 안했을 때
				alert("옵션을 선택해주세요");
				return;
			}

			$.ajax({
				url: "/cart/add", // 주소
				//data: JSON.stringify(jsonData), // 전송 데이터
				data: $("#getProductForm").serialize(),
				type: "POST", // 전송 타입
				dataType: "JSON", // 응답 받을 데이터 타입
				//contentType: "application/json; charset=utf-8",
				success: function (result) {
					
					console.log("result : " + result);
					
					let confirmText;
					
					if (result == 2) {
												
						confirmText = "중복된 상품을 제외하고 장바구니에 추가되었습니다.";
						
					} else {
						
						confirmText = "장바구니에 추가되었습니다.";
						
					}
					/*
					var selectRes = confirm(confirmText + "\n" + "장바구니로 이동하시겠습니까?");				
					
					if(selectRes){ // 장바구니 이동
					
						let buyerId = $("input[name=buyerId]").val();
						
						console.log("buyerId : " + buyerId);
									
						self.location = "/cart/" + buyerId;
						
					}else{ }// 장바구니 이동 취소
					 */
					swal({
						title: confirmText,
						text: "장바구니로 이동하시겠습니까?",
						icon: "success",
						buttons: true,
						dangerMode: true,
					}).then((result) => {
						if (result) {
							
							let buyerId = $("input[name=buyerId]").val();

							console.log("buyerId : " + buyerId);

							self.location = "/cart/" + buyerId;

						} else {
							
						}

					});	
					
				},
				//요청 실패 시 이벤트
				//응답 status code가 2xx가 아닌 경우 
				error: function (jqXHR, textStatus, errorThrown) {
					console.log(jqXHR);  //응답 메시지
					console.log(textStatus); //"error"로 고정인듯함
					console.log(errorThrown);
				}
			});
		}
    });

	/* 주문하기 */
    $("#addOrder").on("click", function () {

		let orderLink = $("input[name=buyerId]").val();
		
		if (orderLink) { //로그인 했을 때
			
			console.log(orderLink);
			
			if (Number($("#totalPrice").val()) == '0') { //옵션 선택 안했을 때
				swal("옵션을 선택해주세요");
				return;
			}
			
			$("#getProductForm").attr("action", "/order/" + orderLink); // 주소 세팅
			$("#getProductForm").submit(); // 주문 페이지로 이동
			
		} else { //로그인 안했을 때
			self.location = "/user/login";
		}
	});
	
	/* kakao 공유하기 */
	console.log("product : " + productMainImage);
	Kakao.init('9e7bfe9bda76f4fc82c74df2aa9c4c98');
	Kakao.isInitialized();
	console.log(Kakao.isInitialized());

	Kakao.Link.createDefaultButton({
		container: '#create-kakao-link-btn',
		objectType: 'commerce',
		content: {
			title: 'Fait Main',
			imageUrl: 'http://192.168.0.90:8080/img/upload/' + productMainImage,
			link: {
				mobileWebUrl: 'http://192.168.0.90:8080/product/getProduct?productNumber=' + productGroupNumber,
				webUrl: 'http://192.168.0.90:8080/product/getProduct?productNumber=' + productGroupNumber,
			},
		},
		commerce: {
			productName: productGroupName,
			regularPrice: productPrice
		},
		buttons: [
			{
				title: '구매하기',
				link: {
					mobileWebUrl: 'http://192.168.0.90:8080/product/getProduct?productNumber=' + productGroupNumber,
					webUrl: 'http://192.168.0.90:8080/product/getProduct?productNumber=' + productGroupNumber,
				},
			},
		],
	})
	
	var optionIndex = 0;
	$("select[name=options]").change(function() {
		console.log($(this).val()); //value값 가져오기
		console.log($("select[name=options] option:selected").attr("value1")); //value값 가져오기
		console.log($("select[name=options] option:selected").text()); //text값 가져오기
		//$("select[name=options] option:selected").attr('disabled', 'true');


		let productNumber = $(this).val();
		let optionQuantity = Number($("select[name=options] option:selected").attr("value1"));
		let productName = $("select[name=options] option:selected").text();
		console.log("optionQuantity : " + optionQuantity);
		var list = [];
		$(".size-203.flex-c-m.respon6").each(function(index, item) {
			list.push($.trim($(item).text()));
		});

		console.log("selectNames : " + list[0]);

		if (list.includes(productName)) {

			alert("이미 선택된 상품입니다");

		} else {
			
			let totalPrice = Number($("#totalPrice").val());
			
			totalPrice += productPrice;
			
			$("#totalPrice").val(totalPrice);			
            $("#totalPriceArea").text(totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
			
			//$("#totalPrice").val(Number($("#totalPrice").val()) + productPrice);
            //$("#totalPriceArea").text(($("#totalPrice").val()).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));	
			//$("#totalPrice").text(Number($.trim($("#totalPrice").text())) + productPrice);

			$("#optionArea").append(
				"<div class='flex-w flex-r-m p-b-10'>"
				
				+	"<div class='size-203 flex-c-m respon6'>" + productName + "</div>"
				
				+	"<div class='size-204 respon6-next'>"
				
				+		"<div class='wrap-num-product flex-w m-r-20 m-tb-10'>"
				
				+			"<div class='btn-num-product-down cl8 hov-btn2 trans-04 flex-c-m'>"
				+				"<i class='fs-16 zmdi zmdi-minus option'></i>"
				+			"</div>"
				
				+			"<input class='mtext-104 cl3 txt-center num-product option' name='orderPageProductList["+ optionIndex + "].productOrderCount' readonly='readonly' type='text' value='1' />"
				
				+			"<div class='btn-num-product-up cl8 hov-btn2 trans-04 flex-c-m'>"
				+				"<i class='fs-16 zmdi zmdi-plus option'></i>"
				+			"</div>"
				
				+ 			"<input type='hidden' value='" + productNumber + "' name='orderPageProductList[" + optionIndex + "].productNumber' />"
				+ 			"<input type='hidden' value='" + productName + "' name='orderPageProductList[" + optionIndex + "].productName'/>"
				+ 			"<input type='hidden' value='" + productPrice + "' name='orderPageProductList[" + optionIndex + "].productPrice'/>"
				+ 			"<input type='hidden' value='" + productMainImage + "' name='orderPageProductList[" + optionIndex + "].productMainImage'/>"
				+ 			"<input type='hidden' value='" + optionQuantity + "' class='optionQuantity' />"
				
				+		"</div>"				
				
				+	"</div>"	
				
				+ 	"<button type='button' style='float:right;' class='optionDelBtn'>" + "삭제" + "</button>"		
				
				+ "</div>"

			);
			optionIndex++;
		}

	});
	
	/* 수량버튼 */
	$(document).on("click", ".fs-16.zmdi.zmdi-plus.option", function() {
		let quantity = Number($(this).parent().parent("div").find(".mtext-104.cl3.txt-center.num-product.option").val());
		let maxQuantity = Number($(this).parent().parent("div").find(".optionQuantity").val());
		//let quantity = Number($(this).parent().find(".quantity_input").val());
		//let maxQuantity = Number($(this).parent("div").find(".optionQuantity").val());
		console.log("quantity : " + quantity);
		console.log("maxQuantity : " + maxQuantity);
		console.log("result : " + (quantity < maxQuantity));
		if (quantity < maxQuantity) {
			$(this).parent().parent("div").find(".mtext-104.cl3.txt-center.num-product.option").val(++quantity);
			console.log("수량 왜 안넘어가 : " + quantity);
			
			let totalPrice = Number($("#totalPrice").val());
			
			totalPrice += productPrice;
			
			$("#totalPrice").val(totalPrice);
            $("#totalPriceArea").text(totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
			//$("#totalPrice").text(Number($.trim($("#totalPrice").text())) + productPrice);
		} else {
			alert("수량 초과");
		}
	});

	$(document).on("click", ".fs-16.zmdi.zmdi-minus.option", function() {
		let quantity = Number($(this).parent().parent("div").find(".mtext-104.cl3.txt-center.num-product.option").val());
		if (quantity > 1) {
			$(this).parent().parent("div").find(".mtext-104.cl3.txt-center.num-product.option").val(--quantity);
			
			let totalPrice = Number($("#totalPrice").val());
			
			totalPrice -= productPrice;
			
			$("#totalPrice").val(totalPrice);
            $("#totalPriceArea").text(totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
            
			//$("#totalPrice").val(Number($("#totalPrice").val()) - productPrice);
            //$("#totalPriceArea").text(($("#totalPrice").val()).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ','));
			//$("#totalPrice").text(Number($.trim($("#totalPrice").text())) - productPrice);
		}
	});

	$(document).on("click", ".optionDelBtn", function() {
		let quantity = Number($(this).parent().parent("div").find(".mtext-104.cl3.txt-center.num-product.option").val());
		//let totalPrice = $.trim($("#totalPrice").text());
		let totalPrice = Number($("#totalPrice").val());
		
		totalPrice = totalPrice - (productPrice * quantity);
		if (totalPrice < 0) {
			totalPrice = 0;
		}
		console.log("totalPrice : " + totalPrice);

		$(this).parent().remove();
		
		$("#totalPrice").val(totalPrice);
		totalPrice != 0 ? $("#totalPriceArea").text(totalPrice.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')) : $("#totalPriceArea").text('0');
		
		optionIndex--;
	});
	
	$("#addInquiry").on("click", function() {
		let orderLink = $("input[name=buyerId]").val();
		
		if (orderLink) { //로그인 했을 때
			
			console.log(orderLink);
			
			self.location = "/inquiry/addInquiry?productNumber=" + productGroupNumber;
			
		} else { //로그인 안했을 때
			self.location = "/user/login";
		}
	});
	
	
	/* 		
	$(".getInquiry").on("click", function() {

		let inquiryNumber = $(this).children("input[type=hidden]").val();
		console.log("inquiryNumber : " + inquiryNumber);

		$.ajax(
			{
				url: "/inquiry/json/getInquiry/" + inquiryNumber,
				method: "GET",
				dataType: "json",
				headers: {
					"Accept": "application/json",
					"Content-Type": "application/json"
				},
				success: function(JSONData, status) {
					console.log("inquiryContent : " + typeof JSONData.inquiryReplyStatus);
					var displayValue = "<h6>"
						+ "<p>문의 제목 : " + JSONData.inquiryTitle + "</p>"
						+ "<p>문의 내용 : " + JSONData.inquiryContent + "</p>"
						+ "<p>문의 id : " + JSONData.userId + "</p>";
					if (JSONData.inquiryReplyStatus) {
						console.log("aaaa ");
						displayValue += "<p>답변 날짜 : " + JSONData.inquiryReplyDate + "</p>"
							+ "<p>답변 내용 : " + JSONData.inquiryReplyContent + "</p>"
					} 

					$("h6").remove();
					$(this).parent().parent().find(".getInquiryArea").html(displayValue);

				}
			})
	});
	
	*/
	
});

function getInquirys(e) {
	let inquiryNumber = $(e).children("input[type=hidden]").val();
	console.log("inquiryNumber : " + inquiryNumber);
	$.ajax(
		{
			url: "/inquiry/json/getInquiry/" + inquiryNumber,
			method: "GET",
			dataType: "json",
			headers: {
				"Accept": "application/json",
				"Content-Type": "application/json"
			},
			success: function(JSONData, status) {
				console.log("inquiryContent : " + typeof JSONData.inquiryReplyStatus);
				var displayValue = "<tr class='table_row getInquiryArea' id='" + JSONData.inquiryNumber + "'>"
					+ "<td colspan='5' style='padding-left:40px;'> Q. " + JSONData.inquiryContent + "<br/>";
				if (JSONData.inquiryReplyStatus) {
					console.log("답변이 존재함");
					displayValue += "A. " + JSONData.inquiryReplyContent + "<br/>"
						+ "   " + JSONData.inquiryReplyDate
						+ "</td>";
				} else {
					displayValue += "</td>";
				}

				displayValue += "</tr>";

				$(".table_row.getInquiryArea").remove();

				console.log("currentTr " + $(e).parent().parent().index());

				$("#inquiryTable tr").eq($(e).parent().parent().index()).after(displayValue);
			}
		});
}