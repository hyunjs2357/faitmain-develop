package com.faitmain.domain.order.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderCancel{

    private String buyerId;

    private int OrderNumber;

    private String keyword;

    private int PageAmount;

    private int PageNumber;

    /* IAMPORT 결제번호 */
    private String impUid = "";
}
