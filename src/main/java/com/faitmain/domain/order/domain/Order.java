package com.faitmain.domain.order.domain;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.sql.Date;
import java.util.List;

@Setter
@Getter
@ToString
@RequiredArgsConstructor
@AllArgsConstructor
@Slf4j
public class Order{

    /* IAMPORT 결제번호 */
    private String impUid = "";

    /* 주문번호 */
    private int orderNumber;

    /* 구매자 PK */
    private String buyerId;

    /* 주문상태 */
    private String orderStatus;

    /* 배송비 */
    private int deliveryCharge;

    /* 주문날짜 */
    private Date orderDate;

    /* 수취인 이름 */
    private String receiverName;

    /* 수취인 전화번호 */
    private String receiverPhone;

    /* 수취인 우편번호 */
    private String receiverAddress1;

    /* 수취인 기본주소 */
    private String receiverAddress2;

    /* 수취인 상세주소 */
    private String receiverAddress3;

    /* 사용 포인트 */
    private int usingPoint;

    /* =====================  */
    /* DB에 존재하지 않는 데이터   */
    /* =====================  */

    /* 주문상품 */
    private List<OrderProduct> orderProductList;

    /* 판매가 (모든 상품) */
    private int orderSalePrice;

    /* 적립 포인트 */
    private int orderRewardPoint;

    /* 최종 판매 비용 */
    private int orderFinalSalePrice;


    public void getOrderPriceInfo(){

        log.info( "/* 실제 계산 금액 가져오기 */" );

        /* 상품 비용 & 적립포인트 */
        for ( OrderProduct op : orderProductList ) {
            orderSalePrice += op.getTotalPrice();
            orderRewardPoint += op.getTotalRewardPoint();
        }

        /* 배송비용 */
        if ( orderSalePrice >= 30000 ) {
            deliveryCharge = 0;
        } else {
            deliveryCharge = 3000;
        }
        /* 최종비용 */
        orderFinalSalePrice = orderSalePrice + deliveryCharge - usingPoint;
        log.info( "orderFinalSalePrice = {}",orderFinalSalePrice );
    }




/*
    private int deliveryTrackingNumber;

    private String deliveryCompanyCode;

    private String paymentOption;


    private Date orderClaimRequestDate;

    private Date orderClaimResponseDate;

    private int orderClaimReason;
*/

}