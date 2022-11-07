package com.faitmain.domain.product.service;

import java.util.Map;

import com.faitmain.domain.product.domain.Inquiry;
import com.faitmain.global.common.Search;

public interface InquiryService {

	//문의 등록
	public void addInquiry(Inquiry inquiry) throws Exception;
	
	//문의 상세 조회
	public Inquiry getInquiry(int inquiryNumber) throws Exception;
		
	//문의 목록 조회
	public Map<String, Object> getInquiryList(Search search) throws Exception;
	
	//문의 수정
	public void updateInquiry(Inquiry inquiry) throws Exception;
		
	//문의 삭제
	public void deleteInquiry(int inquiryNumber) throws Exception;
}