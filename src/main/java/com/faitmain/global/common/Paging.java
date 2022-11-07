package com.faitmain.global.common;

import java.util.Arrays;

import lombok.Data;

public class Paging{

    /* 현재 페이지 번호 */
    private int pageNum;

    /* 페이지 표시 개수 */
    private int amount;

    /* 페이지 skip */
    private int skip;

    /* 검색 타입 */
    private String type;

    /* 검색 키워드 */
    private String keyword;

    /* 작가 리스트 */
    private String[] authorArr;

    /* 카테고리 코드 */
    private String cateCode;

    /* 상품 번호(댓글 기능에서 사용) */
    private int productNumber;

    /* PAGING 생성자 */
    public Paging( int pageNum, int amount) {
        this.pageNum = pageNum;
        this.amount = amount;
        this.skip = (pageNum -1) * amount;
    }

    /* PAGING 기본 생성자 */
    public Paging(){
        this(1,10);
        this.skip = 0;
    }

    /* 검색 타입 데이터 배열 변환 */
    public String[] getTypeArr() {
        return type == null? new String[] {}:type.split("");
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
        this.skip = (pageNum - 1) * this.amount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
        this.skip = (this.pageNum - 1) * amount;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }


    public String[] getAuthorArr() {
        return authorArr;
    }

    public void setAuthorArr(String[] authorArr) {
        this.authorArr = authorArr;
    }

    public String getCateCode() {
        return cateCode;
    }

    public void setCateCode(String cateCode) {
        this.cateCode = cateCode;
    }

    public int getProductNumber() {
        return productNumber;
    }

    public void setProductNumber( int productNumber ) {
        this.productNumber = productNumber;
    }

    @Override
    public String toString() {
        return "Paging [pageNum=" + pageNum + ", amount=" + amount + ", skip=" + skip + ", type=" + type
                + ", keyword=" + keyword + ", authorArr=" + Arrays.toString(authorArr) + ", cateCode=" + cateCode
                + ", productNumber=" + productNumber + "]";
    }




}