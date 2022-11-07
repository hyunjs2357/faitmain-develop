package com.faitmain.domain.web.service;

import com.faitmain.domain.web.domain.AttachImage;

import java.util.List;

public interface AttachService{

    /* 이미지 데이터 반환 */
    List<AttachImage> getAttachList( int productNumber );

}