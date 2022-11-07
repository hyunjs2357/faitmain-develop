
/* 주문 요청 */
$(".order_btn").on("click", function(){

    /* 주소 정보 & 받는이*/
    $(".addressInfo_input_div").each(function(i, obj){
        if($(obj).find(".selectAddress").val() === 'T'){
            $("input[name='addressee']").val($(obj).find(".addressee_input").val());
            $("input[name='memberAddr1']").val($(obj).find(".address1_input").val());
            $("input[name='memberAddr2']").val($(obj).find(".address2_input").val());
            $("input[name='memberAddr3']").val($(obj).find(".address3_input").val());
        }
    });

    /* 사용 포인트 */
    $("input[name='usePoint']").val($(".order_point_input").val());

    /* 상품정보 */
    let form_contents = '';
    $(".goods_table_price_td").each(function(index, element){
        let bookId = $(element).find(".individual_bookId_input").val();
        let bookCount = $(element).find(".individual_bookCount_input").val();
        let bookId_input = "<input name='orders[" + index + "].bookId' type='hidden' value='" + bookId + "'>";
        form_contents += bookId_input;
        let bookCount_input = "<input name='orders[" + index + "].bookCount' type='hidden' value='" + bookCount + "'>";
        form_contents += bookCount_input;
    });
    $(".order_form").append(form_contents);

    /* 서버 전송 */
    $(".order_form").submit();

});




function requestPay() {
    IMP.init("imp76668016");
    IMP.request_pay({
        pg: "html5_inicis",
        pay_method: "card",
        merchant_uid: "ORD20180131-0000011",
        name: "노르웨이 회전 의자",
        amount: 1000,
        buyer_email: "gildong@gmail.com",
        buyer_name: "홍길동",
        buyer_tel: "010-4242-4242",
        buyer_addr: "서울특별시 강남구 신사동",
        buyer_postcode: "01181"
    }, function (rsp) { // callback
        if (rsp.success) {
            // 결제 성공 시 로직,
            let imp_uid = rsp.imp_id;
            let merchant_uid = rsp.merchant_uid;
            let paid_amount = rsp.paid_amount;
            let apply_num = rsp.apply_num;

            let data = {
                imp_uid: imp_uid,
                merchant_uid: merchant_uid,
                paid_amount: paid_amount,
                apply_num: apply_num
            };

            $.ajax({
                url: "/order/add",
                data: data,
                success: function () {
                    let msg = "결제가 완료되었습니다.\n";
                    msg += "고유ID: " + imp_uid;
                    msg += "\n상점거래ID : " + merchant_uid;
                    msg += "\n결제금액 : " + paid_amount;
                    msg += "\n 카드승인번호 : " + apply_num;
                    alert(msg);
                    location.href = "index.html";
                }
            })// end ajax
        } else {
            // 결제 실패 시 로직,
            let msg = "결제에 실패하였습니다./n"
            msg = "에러내용: " + rsp.error_msg;
            alert("msg");
        }
    });
}
// 주문번호 만들기
function createOrderNum(){
    const date = new Date();
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, "0");
    const day = String(date.getDate()).padStart(2, "0");

    let orderNum = year + month + day;
    for(let i=0;i<10;i++) {
        orderNum += Math.floor(Math.random() * 8);
    }
    return orderNum;
}

/* 결제 ver 1 */
function payment() {
    const data = {
        // orderNumber : createOrderNum(),
        buyerName: $('input[name="buyerName"]').val(),
        receiverAddress1 : $('input[name="receiverAddress1"]').val(),
        receiverAddress2 : $('input[name="receiverAddress2"]').val(),
        receiverAddress3 : $('input[name="receiverAddress3"]').val(),
        phoneNumber : $('input[name="phoneNumber"]'),
        email : $('input[name="email"]'),
        amount : $('input[name="amount"]').val(),

        // buyerName: "노르웨이 회전 의자",
        // receiverAddress1 : "서울특별시 강남구 신사동",
        // receiverAddress2 : "하나하나둘셋아파트",
        // receiverAddress3 : "01191",
        // phoneNumber : "010-4242-4242",
        // email : "gildong@gmail.com",
        // amount : 1000,
    }
    paymentCard(data)
}



/* 결제 */
function paymentCard() {
    var IMP = window.IMP;
    IMP.init("imp76668016");
    IMP.request_pay({
        pg: "html5_inicis",
        pay_method: "card",
        merchant_uid: "2349234987",
        name: "노르웨이 회전 의자",
        amount: data.amount,
        buyer_email: data.email,
        buyer_name: data.buyerName,
        buyer_tel: data.phoneNumber,
        buyer_addr: data.receiverAddress2+" "+data.receiverAddress3,
        buyer_postcode: data.receiverAddress1
    }, function (rsp) { // callback
        if (rsp.success) {
            // 결제 성공
            data.imp_uid = rsp.imp_uid;
            data.merchant_uid = rsp.merchant_uid;
            paymentComplete()
        } else {
            alert("결제에 실패하였습니다. 에러 내용: " + rsp.error_msg);
        }
    });
}

// 계산 완료
function paymentComplete(data) {

    $.ajax({
        url: "/order/complete",
        method: "POST",
        data: data,
    })
        .done(function (result) {
            messageSend();
            swal({
                text: result,
                closeOnClickOutside: false
            })
                .then(function () {
                    location.replace("/orderList");
                })
        }) // done
        .fail(function () {
            alert("에러");
            location.replace("/");
        })
}




//
// $(document).ready(function () {
//     /* 주문 조합정보란 최신화 */
//     setTotalInfo();
//     /* 이미지 삽입 */
//     $(".image_wrap").each(function (i, obj) {
//         const bobj = $(obj);
//         if (bobj.data("bookid")) {
//             const uploadPath = bobj.data("path");
//             const uuid = bobj.data("uuid");
//             const fileName = bobj.data("filename");
//             const fileCallPath = encodeURIComponent(uploadPath + "/s_" + uuid + "_" + fileName);
//             $(this).find("img").attr('src', '/display?fileName=' + fileCallPath);
//         } else {
//             $(this).find("img").attr('src', '/resources/img/goodsNoImage.png');
//         }
//     });
// });


/* 포인트 입력 */
//0 이상 & 최대 포인트 수 이하
$(".order_point_input").on("propertychange change keyup paste input", function () {
    const maxPoint = parseInt('${memberInfo.point}');

    let inputValue = parseInt($(this).val());

    if (inputValue < 0) {
        $(this).val(0);
    } else if (inputValue > maxPoint) {
        $(this).val(maxPoint);
    }

    /* 주문 조합정보란 최신화 */
    setTotalInfo();

});
/* 포인트 모두사용 취소 버튼
 * Y: 모두사용 상태 / N : 모두 취소 상태
 */
$(".order_point_input_btn").on("click", function () {
    const maxPoint = parseInt('${memberInfo.point}');

    let state = $(this).data("state");

    if (state == 'N') {
        console.log("n동작");
        /* 모두사용 */
        //값 변경
        $(".order_point_input").val(maxPoint);
        //글 변경
        $(".order_point_input_btn_Y").css("display", "inline-block");
        $(".order_point_input_btn_N").css("display", "none");
    } else if (state == 'Y') {
        console.log("y동작");
        /* 취소 */
        //값 변경
        $(".order_point_input").val(0);
        //글 변경
        $(".order_point_input_btn_Y").css("display", "none");
        $(".order_point_input_btn_N").css("display", "inline-block");
    }

    /* 주문 조합정보란 최신화 */
    setTotalInfo();

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
        totalCount += parseInt($(element).find(".individual_bookCount_input").val());
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

    finalTotalPrice = totalPrice + deliveryPrice;

    /* 사용 포인트 */
    usePoint = $(".order_point_input").val();

    finalTotalPrice = totalPrice - usePoint;

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


/* 주문 요청 */
$(".order_btn").on("click", function () {
    /* 주소 정보 & 받는이*/
    $(".addressInfo_input_div").each(function (i, obj) {
        if ($(obj).find(".selectAddress").val() === 'T') {
            $("input[name='addressee']").val($(obj).find(".addressee_input").val());
            $("input[name='memberAddr1']").val($(obj).find(".address1_input").val());
            $("input[name='memberAddr2']").val($(obj).find(".address2_input").val());
            $("input[name='memberAddr3']").val($(obj).find(".address3_input").val());
        }
    });

    /* 사용 포인트 */
    $("input[name='usePoint']").val($(".order_point_input").val());

    /* 상품정보 */
    let form_contents = '';
    $(".goods_table_price_td").each(function (index, element) {
        let bookId = $(element).find(".individual_bookId_input").val();
        let bookCount = $(element).find(".individual_bookCount_input").val();
        let bookId_input = "<input name='orders[" + index + "].bookId' type='hidden' value='" + bookId + "'>";
        form_contents += bookId_input;
        let bookCount_input = "<input name='orders[" + index + "].bookCount' type='hidden' value='" + bookCount + "'>";
        form_contents += bookCount_input;
    });
    $(".order_form").append(form_contents);

    /* 서버 전송 */
    $(".order_form").submit();

});




/* 주소입력란 버튼 동작(숨김, 등장) */
function showAdress(className) {
    /* 컨텐츠 동작 */
    /* 모두 숨기기 */
    $(".addressInfo_input_div").css('display', 'none');
    /* 컨텐츠 보이기 */
    $(".addressInfo_input_div_" + className).css('display', 'block');

    /* 버튼 색상 변경 */
    /* 모든 색상 동일 */
    $(".address_btn").css('backgroundColor', '#555');
    /* 지정 색상 변경 */
    $(".address_btn_" + className).css('backgroundColor', '#3c3838');
    /* selectAddress T/F */
    /* 모든 selectAddress F만들기 */
    $(".addressInfo_input_div").each(function (i, obj) {
        $(obj).find(".selectAddress").val("F");
    });
    /* 선택한 selectAdress T만들기 */
    $(".addressInfo_input_div_" + className).find(".selectAddress").val("T");

}
