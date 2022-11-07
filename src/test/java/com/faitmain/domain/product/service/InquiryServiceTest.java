package com.faitmain.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.faitmain.domain.product.domain.Inquiry;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.mapper.InquiryMapper;

public class InquiryServiceTest {
	
	InquiryServiceImpl inquiryServiceImpl;
	
	@Mock
	InquiryMapper inquiryMapper;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		inquiryServiceImpl = new InquiryServiceImpl(inquiryMapper);
	}
	
	//add
//	@Test
	@DisplayName("addInquiry : 문의 등록")
	void addInquiry() throws Exception{
		
		int inquiryNumber = 10018;
		String inquiryTitle = "배송문의";
		
		Inquiry mockInquiry = Inquiry.builder().inquiryNumber(inquiryNumber).inquiryTitle(inquiryTitle).build();
		
		inquiryServiceImpl.addInquiry(mockInquiry);
		
		verify(inquiryMapper).addInquiry(mockInquiry);		
		
	}

	//get
//	@Test
	@DisplayName("getInquiry : 문의 조회")
	void getInquiry() throws Exception{
		
		int inquiryNumber = 10018;
		String inquiryTitle = "배송문의";
		
		Inquiry mockInquiry = Inquiry.builder().inquiryNumber(inquiryNumber).inquiryTitle(inquiryTitle).build();
		
		inquiryServiceImpl.addInquiry(mockInquiry);
		
		given(inquiryMapper.getInquiry(inquiryNumber)).willReturn(mockInquiry);
		
		Inquiry responseInquiry = inquiryServiceImpl.getInquiry(inquiryNumber);
		System.out.println(responseInquiry);
		
		assertThat(responseInquiry.getInquiryTitle()).isEqualTo(inquiryTitle);
		
	}
	
	//update
//	@Test
	@DisplayName("updateInquiry : 문의 수정")
	void updateProduct() throws Exception{
		
		int inquiryNumber = 10018;
		String inquiryTitle = "배송문의";
		
		Inquiry mockInquiry = Inquiry.builder().inquiryNumber(inquiryNumber).inquiryTitle(inquiryTitle).build();
		
		inquiryServiceImpl.addInquiry(mockInquiry);
		
		mockInquiry = Inquiry.builder().inquiryNumber(inquiryNumber).inquiryTitle("상품문의").build();
		
		inquiryServiceImpl.updateInquiry(mockInquiry);
		
		given(inquiryMapper.getInquiry(inquiryNumber)).willReturn(mockInquiry);
		
		Inquiry responseInquiry = inquiryServiceImpl.getInquiry(inquiryNumber);
		System.out.println(responseInquiry);
		
		assertThat(responseInquiry.getInquiryTitle()).isEqualTo("상품문의");
		
	}
	
	//delete
	@Test
	@DisplayName("deleteInquiry : 문의 삭제")
	void deleteInquiry() throws Exception{
		
		int inquiryNumber = 10018;
		String inquiryTitle = "배송문의";
		
		Inquiry mockInquiry = Inquiry.builder().inquiryNumber(inquiryNumber).inquiryTitle(inquiryTitle).build();
		
		when(inquiryMapper.getInquiry(inquiryNumber)).thenReturn(mockInquiry);
		
		inquiryServiceImpl.deleteInquiry(inquiryNumber);
		
		verify(inquiryMapper, times(1)).deleteInquiry(inquiryNumber);
		
	}
	
}
