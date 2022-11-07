package com.faitmain.domain.web.domain;

import lombok.Data;

@Data
public class AttachImage{

    /* 경로 */
    private String uploadPath;

    /* uuid */
    private String uuid;

    /* 파일 이름 */
    private String fileName;

    /* 상품 PK */
    private int productNumber;
}
