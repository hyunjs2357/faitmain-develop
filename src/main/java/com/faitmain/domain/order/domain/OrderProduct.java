package com.faitmain.domain.order.domain;

import lombok.Data;

@Data
public class OrderProduct{

    /* OREDERPRODUCT PK */
    private int orderProductNumber;

    /* 주문번호 FK */
    private int orderNumber;

    /* 상품이름 */
    private String productName;

    /* 상품 이미지 */
    private String productMainImage;

    /* 상품번호 FK */
    private int productNumber;

    /* 주문수량 */
    private int productOrderCount;

    /* 상품 한 개 가격 */
    private int productPrice;

    /* 상품 한 개 구매시 획득 포인트 */
    private int rewardPoint;

    /* ===================== */
    /* DB에 존재하지 않는 데이터 */
    /* ===================== */

    /* 할인 적용된 가격 */
    private int salePrice;

    /* 총 가격 ( 할인 적용된 가격 * 주문 수량 ) */
    private int totalPrice;

    /* 총 획득 포인트 ( 상품 한 개 구매 시 획득 포인트 * 수량 ) */
    private int totalRewardPoint;


    public void initSaleTotal(){
        this.salePrice = this.productPrice;
        this.totalPrice = this.salePrice * this.productOrderCount;
        this.rewardPoint = ( int ) Math.floor( this.salePrice * 0.05 );
        this.totalRewardPoint = this.rewardPoint * this.productOrderCount;
    }

}
