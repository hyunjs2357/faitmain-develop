package com.faitmain.infrastructure.bootpay.service;

import com.faitmain.global.common.request.*;
import kr.co.bootpay.Bootpay;
import kr.co.bootpay.model.response.ResDefault;

public class RequestService{

    static Bootpay bootpay;


    public static void requestLink() {
        bootpay = new Bootpay( "62a0dc64e38c3000235ae07c" , "4Y1WvfDT+s/jgW4nHDZWshrtDEAagO7qEz5PAxMTCoI=" );
        Payload payload = new Payload();
        payload.orderId = "1234";
        payload.price = 1000;
        payload.name = "테스트 결제";
        payload.pg = "nicepay";

        try {
            ResDefault res = bootpay.requestLink(payload);
            System.out.println(res.toJson());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
