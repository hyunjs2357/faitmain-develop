/* IAMPORT 에서 사용할 최종 결제금액 전역변수 */
let finalTotalPriceToAPI;
let finalTotalPricePrev;

$(document).ready(function () {

    /* 주문 조합정보란 최신화 */
    setTotalInfo();

    /* 포인트 입력 */
    //0 이상 & 최대 포인트 수 이하
    $(".order_point_input").on("propertychange change keyup paste input", function () {

        /* 상수 선언 & 회원이 갖고있는 포인트 대입 */
        const maxPoint = parseInt('${buyer.totalPoint')

        /* 사용자가 입력한 값 */
        let inputValue = parseInt($(this).val());
        console.log(inputValue)

        if (inputValue < 0) {
            $(this).val(0);
        } else if (inputValue > maxPoint) {
            $(this).val(maxPoint);
        } else if (inputValue > finalTotalPricePrev) {
            $(this).val(finalTotalPricePrev)
        }

        /* 주문 조합정보란 최신화 */
        setTotalInfo();
    });

    /* 포인트 모두사용 취소 버튼
     * Y: 모두사용 상태 / N : 모두 취소 상태
     */
    $(".order_point_input_btn").on("click", function () {

        const maxPoint = parseInt($('.totalPoint').val())
        console.log(maxPoint)

        let state = $(this).data("state");

        let point = parseInt($('.myPoint').text());
        console.log(point + 1)

        if (state === 'N') {
            console.log("n동작");
            /* 모두사용 */

            //값 변경
            if (maxPoint > finalTotalPricePrev) {
                $(".order_point_input").val(finalTotalPricePrev);
            }

            point = maxPoint - $('.order_point_input').val() ;
            $(".myPoint").text(point);

            //글 변경
            $(".order_point_input_btn_Y").css("display", "inline-block");
            $(".order_point_input_btn_N").css("display", "none");

        } else if (state === 'Y') {
            console.log("y동작");
            /* 취소 */

            $(".myPoint").text(parseInt($('.order_point_input').val()) + point);

            //값 변경
            $(".order_point_input").val(0);

            //글 변경
            $(".order_point_input_btn_Y").css("display", "none");
            $(".order_point_input_btn_N").css("display", "inline-block");
        }


        /* 주문 조합정보란 최신화 */
        setTotalInfo();
    });

    /* 주문 요청 */
    $(".order_btn").on("click", function () {

        /* 주소 정보 & 받는이*/
        $(".addressInfo_input_div").each(function (i, obj) {
            if ($(obj).find(".selectAddress").val() === 'T') {
                $("input[name='receiverName']").val($(obj).find(".address0_input").val());
                $("input[name='receiverAddress1']").val($(obj).find(".address1_input").val());
                $("input[name='receiverAddress2']").val($(obj).find(".address2_input").val());
                $("input[name='receiverAddress3']").val($(obj).find(".address3_input").val());
                $("input[name='receiverPhone']").val($(obj).find(".address5_input").val());
            }
        });

        /* 사용 포인트 */
        $("input[name='usingPoint']").val($(".order_point_input").val());

        /* 상품정보 */
        let form_contents;
        $(".goods_table_price_td").each(function (index, element) {

            let productNumber = $(element).find(".individual_productNumber_input").val();
            let productOrderCount = $(element).find(".individual_productOrderCount_input").val();

            let productNumber_input = "<input name='orderProductList[" + index + "].productNumber' type='hidden' value='" + productNumber + "'>";
            let productOrderCount_input = "<input name='orderProductList[" + index + "].productOrderCount' type='hidden' value='" + productOrderCount + "'>";

            form_contents += productNumber_input;
            form_contents += productOrderCount_input;

        });

        $(".order_form").append(form_contents);

        /* 정보 */
        const data = {
            amount: finalTotalPriceToAPI,
            orderNumber: new Date().getUTCMilliseconds(),
            receiverPhone: $("#receiverPhone").val(),
            name: " ",
            // receiverName: $("#buyerName").val(),
            // receiverAddress1: $("#receiverAddress1").val(),
            // receiverAddress2: $("#receiverAddress2").val(),
            // receiverAddress3: $("#receiverAddress3").val(),
            // productNumber: $("#productNumber").val(),
            // buyerId: $("#buyerId").val(),
        }

        var IMP = window.IMP;
        IMP.init("imp76668016");
        IMP.request_pay({
            pay_method: "card",
            amount: data.amount,
            merchant_uid: data.orderNumber,
            buyer_tel: data.receiverPhone,
            name: data.name,
            // buyer_name: data.receiverName,
            // buyer_addr: data.receiverAddress2 + " " + data.receiverAddress3,
            // buyer_postcode: data.receiverAddress1
        }, function (rsp) { // callback
            if (rsp.success) {
                // 결제 성공
                data.impUid = rsp.imp_uid;
                data.merchant_uid = rsp.merchant_uid;
                data.amount = rsp.amount;


                let impUid = "<input id='impUid' name='impUid' type='hidden' value='" + rsp.imp_uid + "'>";
                let merchantUid = "<input id='orderNumber' name='orderNumber' type='hidden' value='" + rsp.merchant_uid + "'>";
                // let amount = "<input id='orderFinalSalePrice' name='orderFinalSalePrice' type='hidden' value='" + rsp.amount + "'>";


                let appendData = impUid + merchantUid;

                $('.order_form').append(appendData);

                /* 서버 전송 */
                $(".order_form").submit();

            } else {
                alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
            }
        });

    });
});

/* 총 주문 정보 세팅(배송비, 총 가격, 마일리지, 물품 수, 종류) */
function setTotalInfo() {
    let totalPrice = 0;				// 총 가격
    let totalCount = 0;				// 총 갯수
    let totalKind = 0;				// 총 종류
    let totalPoint = 0;				// 총 마일리지
    let deliveryPrice = 0;			// 배송비
    let usePoint = 0;				// 사용 포인트(할인가격)
    let finalTotalPrice = 0; 		// 최종 가격(총 가격 + 배송비)

    $(".goods_table_price_td").each(function (index, element) {
        // 총 가격
        totalPrice += parseInt($(element).find(".individual_totalPrice_input").val());
        // 총 갯수
        totalCount += parseInt($(element).find(".individual_productOrderCount_input").val());
        // 총 종류
        totalKind += 1;
        // 총 마일리지
        totalPoint += parseInt($(element).find(".individual_totalPoint_input").val());
    });

    /* 배송비 결정 */
    if (totalPrice >= 30000) {
        deliveryPrice = 0;
    } else if (totalPrice == 0) {
        deliveryPrice = 0;
    } else {
        deliveryPrice = 3000;
    }

    /* 사용 포인트 */
    usePoint = $(".order_point_input").val();
    finalTotalPricePrev = totalPrice + deliveryPrice;

    finalTotalPrice = totalPrice + deliveryPrice - usePoint;
    finalTotalPriceToAPI = finalTotalPrice

    /* 값 삽입 */
    // 총 가격
    $(".totalPrice_span").text(totalPrice.toLocaleString());
    // 총 갯수
    $(".goods_kind_div_count").text(totalCount);
    // 총 종류
    $(".goods_kind_div_kind").text(totalKind);
    // 총 마일리지
    $(".totalPoint_span").text(totalPoint.toLocaleString());
    // 배송비
    $(".delivery_price_span").text(deliveryPrice.toLocaleString());
    // 최종 가격(총 가격 + 배송비)
    $(".finalTotalPrice_span").text(finalTotalPrice.toLocaleString());
    // 할인가(사용 포인트)
    $(".usePoint_span").text(usePoint.toLocaleString());

}


/* 주소입력란 버튼 숨김 & 표시 */
function showAddress(className) {

    /* 컨텐츠 동작 */
    /* 모두 숨기기 */
    $(".addressInfo_input_div").css('display', 'none');

    /* 컨텐츠 보이기 */
    $(".addressInfo_input_div_" + className).css('display', 'block');

    /* 버튼 색상 변경 */
    /* 모든 색상 동일 */
    //$(".address.btn").css('background-color','#f3f3f3');
    $(".address_btn").css({'background-color':'#f3f3f3', 'border':'1px solid #cfcfcf'});

    /* 지정 색상 변경*/
    //$(".address.btn_" + className).css('backgroundColor', '#3c3838');
	$(".address_btn.address_btn_" + className).css({"background-color":"white", 'border-bottom':'1px solid white'});
	
    /* selectAddress T/F */
    /* 모든 selectAddress F만들기 */
    $(".addressInfo_input_div").each(function (i, obj) {
        $(obj).find(".selectAddress").val("F");
    });

    /* 선택한 selectAddress T만들기 */
    $(".addressInfo_input_div_" + className).find(".selectAddress").val("T");
}



/* 다음 주소 연동 */
function execution_daum_address() {
    console.log("동작");
    new daum.Postcode({
        oncomplete: function (data) {
            // 팝업에서 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분입니다.

            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
            // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로, 이를 참고하여 분기 한다.
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
            if (data.userSelectedType === 'R') {
                // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                if (data.bname !== '' && /[동|로|가]$/g.test(data.bname)) {
                    extraAddr += data.bname;
                }
                // 건물명이 있고, 공동주택일 경우 추가한다.
                if (data.buildingName !== '' && data.apartment === 'Y') {
                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                }
                // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                if (extraAddr !== '') {
                    extraAddr = ' (' + extraAddr + ')';
                }
                // 추가해야할 코드
                // 주소변수 문자열과 참고항목 문자열 합치기
                addr += extraAddr;

            } else {
                addr += ' ';
            }

            // 제거해야할 코드
            // 우편번호와 주소 정보를 해당 필드에 넣는다.
            $(".address1_input").val(data.zonecode);
            $(".address2_input").val(addr);
            // 커서를 상세주소 필드로 이동한다.
            $(".address3_input").attr("readonly", false);
            $(".address3_input").focus();


        }
    }).open();

}