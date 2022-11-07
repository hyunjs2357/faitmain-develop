$(document).ready(function () {

    /* 종합 정보 섹션 정보 삽입 */
    setTotalInfo();

    /* 체크여부에따른 종합 정보 변화 */
    $(".individual_cart_checkbox").on("change", function () {
        /* 총 주문 정보 세팅(배송비, 총 가격, 마일리지, 물품 수, 종류) */
        setTotalInfo($(".cart_info_td"));
    });

    /* 체크박스 전체 선택 */
    $(".all_check_input").on("click", function(){
        /* 체크박스 체크/해제 */
        if($(".all_check_input").prop("checked")){
            $(".individual_cart_checkbox").prop("checked", true);
        } else{
            $(".individual_cart_checkbox").prop("checked", false);
        }

        /* 총 주문 정보 세팅(배송비, 총 가격, 마일리지, 물품 수, 종류) */
        setTotalInfo($(".cart_info_td"));

    });

    /* 수량버튼 */
    $(".plus_btn").on("click", function () {
        let quantity = $(this).parent("div").find("input").val();
        $(this).parent("div").find("input").val(++quantity);
    });
    $(".minus_btn").on("click", function () {
        let quantity = $(this).parent("div").find("input").val();
        if (quantity > 1) {
            $(this).parent("div").find("input").val(--quantity);
        }
    });

    /* 수량 수정 버튼 */
    $(".quantity_modify_btn").on("click", function () {
        let cartNumber = $(this).data("cartNumber");
        let productOrderCount = $(this).parent("td").find("input").val();
        $(".update_cartNumber").val(cartNumber);
        $(".update_productOrderCount").val(productOrderCount);
        $(".quantity_update_form").submit();
    });

    /* 장바구니 삭제 버튼 */
    $(".delete_btn").on("click", function (e) {
        console.log("삭제버튼 : " + e);
        e.preventDefault();
        const cartNumber = $(this).data("cartNumber");
        $(".delete_cartNumber").val(cartNumber);
        $(".quantity_delete_form").submit();
    });

    /* 주문 페이지 이동 */
    $(".order_btn").on("click", function () {

        let form_contents = '';
        let orderNumber = 0;

        $(".cart_info_td").each(function (index, element) {

            if ($(element).find(".individual_cart_checkbox").is(":checked") === true) {	//체크여부

                let productNumber = $(element).find(".individual_productNumber_input").val();
                let productOrderCount = $(element).find(".individual_productOrderCount_input").val();
                let productMainImage = $(element).find(".individual_productMainImage_input").val();

                let productNumber_input = "<input name='orderPageProductList[" + index + "].productNumber' type='hidden' value='" + productNumber + "'>";
                let productOrderCount_input = "<input name='orderPageProductList[" + index + "].productOrderCount' type='hidden' value='" + productOrderCount + "'>";
                let productMainImage_input = "<input name='orderPageProductList[" + index + "].productMainImage' type='hidden' value='" + productMainImage + "'>";

                form_contents += productNumber_input;
                form_contents += productOrderCount_input;
                form_contents += productMainImage_input;

                orderNumber += 1;

            }
        });
        $(".order_form").html(form_contents);
        $(".order_form").submit();

    });

});


/* 총 주문 정보 세팅(배송비, 총 가격, 마일리지, 물품 수, 종류) */
function setTotalInfo(){

    let totalPrice = 0;				// 총 가격
    let totalCount = 0;				// 총 갯수
    let totalKind = 0;				// 총 종류
    let totalPoint = 0;				// 총 마일리지
    let deliveryPrice = 0;			// 배송비
    let finalTotalPrice = 0; 		// 최종 가격(총 가격 + 배송비)

    $(".cart_info_td").each(function(index, element){

        if($(element).find(".individual_cart_checkbox").is(":checked") === true){	//체크여부
            // 총 가격
            totalPrice += parseInt($(element).find(".individual_totalPrice_input").val());
            // 총 갯수
            totalCount += parseInt($(element).find(".individual_productOrderCount_input").val());
            // 총 종류
            totalKind += 1;
            // 총 마일리지
            totalPoint += parseInt($(element).find(".individual_totalPoint_input").val());
        }
    });


    /* 배송비 결정 */
    if(totalPrice >= 30000){
        deliveryPrice = 0;
    } else if(totalPrice == 0){
        deliveryPrice = 0;
    } else {
        deliveryPrice = 3000;
    }

    finalTotalPrice = totalPrice + deliveryPrice;

    /* ※ 세자리 컴마 Javscript Number 객체의 toLocaleString() */

    // 총 가격
    $(".totalPrice_span").text(totalPrice.toLocaleString());
    // 총 갯수
    $(".totalCount_span").text(totalCount);
    // 총 종류
    $(".totalKind_span").text(totalKind);
    // 총 마일리지
    $(".totalPoint_span").text(totalPoint.toLocaleString());
    // 배송비
    $(".delivery_price").text(deliveryPrice);
    // 최종 가격(총 가격 + 배송비)
    $(".finalTotalPrice_span").text(finalTotalPrice.toLocaleString());
}

