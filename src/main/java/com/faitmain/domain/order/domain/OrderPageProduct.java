package com.faitmain.domain.order.domain;

import com.faitmain.domain.web.domain.AttachImage;
import lombok.Data;

import java.util.List;

@Data
public class OrderPageProduct{

    /* 뷰로부터 전달받을 값 */
    private int productNumber;

    private int productOrderCount;

    /* DB로부터 꺼내올 값 */
    private String productName;

    private int productPrice;

    /* 만들어 낼 값 */
    private int salePrice;

    private int totalPrice;

    private int point;

    private int totalPoint;

    /* 상품 이미지 */
    private String productMainImage;


    public void initSaleTotal(){
        this.salePrice = this.productPrice;
        this.totalPrice = this.salePrice * this.productOrderCount;
        this.point = ( int ) ( Math.floor( this.salePrice * 0.05 ) );
        this.totalPoint = this.point * this.productOrderCount;
    }


}
