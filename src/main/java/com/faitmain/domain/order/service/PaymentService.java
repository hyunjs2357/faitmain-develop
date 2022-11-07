package com.faitmain.domain.order.service;

import java.io.IOException;
import java.net.MalformedURLException;

public interface PaymentService{

    String getToken() throws IOException;

    int paymentInfo( String imp_uid , String access_token ) throws IOException;

    void paymentCancel( String access_token , String imp_uid , int amount ) throws MalformedURLException, IOException;
}
