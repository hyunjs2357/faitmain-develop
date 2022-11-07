package com.faitmain.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.faitmain.domain.product.domain.Review;
import com.faitmain.domain.product.mapper.ReviewMapper;

public class ReviewServiceTest {

	ReviewServiceImpl reviewServiceImpl;
	
	@Mock
	ReviewMapper reviewMapper;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		reviewServiceImpl = new ReviewServiceImpl(reviewMapper);
	}
	
	//add
//	@Test
	@DisplayName("addReview : 리뷰 등록")
	void addReview() throws Exception{
		
		int reviewNumber = 10018;
		String reviewContent = "배송이 빨라요";
		
		Review mockReview = Review.builder().reviewNumber(reviewNumber).reviewContent(reviewContent).build();
		
//		reviewServiceImpl.addReview(mockReview);
		
		verify(reviewMapper).addReview(mockReview);		
		
	}
	
	//get
//	@Test
	@DisplayName("getReview : 리뷰 조회")
	void getReview() throws Exception{
		
		int reviewNumber = 10018;
		String reviewContent = "배송이 빨라요";
		
		Review mockReview = Review.builder().reviewNumber(reviewNumber).reviewContent(reviewContent).build();
		
		given(reviewMapper.getReview(reviewNumber)).willReturn(mockReview);
		
		Review responseReview = reviewServiceImpl.getReview(reviewNumber);
		System.out.println(responseReview);
		assertThat(responseReview.getReviewContent()).isEqualTo(reviewContent);
		
	}
	
	//update
//	@Test
	@DisplayName("updateReview : 리뷰 수정")
	void updateReview() throws Exception{
		
		int reviewNumber = 10018;
		String reviewContent = "배송이 빨라요";
		
		Review mockReview = Review.builder().reviewNumber(reviewNumber).reviewContent(reviewContent).build();
		
//		reviewServiceImpl.addReview(mockReview);
		
		mockReview = Review.builder().reviewNumber(reviewNumber).reviewContent("감사합니다").build();
		
//		reviewServiceImpl.updateReview(mockReview);
		
		given(reviewMapper.getReview(reviewNumber)).willReturn(mockReview);
		
		Review responseReview = reviewServiceImpl.getReview(reviewNumber);
		System.out.println(responseReview);
		assertThat(responseReview.getReviewContent()).isEqualTo("감사합니다");
		
	}
	
	//delete
	@Test
	@DisplayName("deleteReview : 리뷰 삭제")
	void deleteReview() throws Exception{
		
		int reviewNumber = 10018;
		String reviewContent = "배송이 빨라요";
		
		Review mockReview = Review.builder().reviewNumber(reviewNumber).reviewContent(reviewContent).build();
		
		when(reviewMapper.getReview(reviewNumber)).thenReturn(mockReview);
		
		reviewServiceImpl.deleteReview(reviewNumber);
		
		verify(reviewMapper, times(1)).deleteReview(reviewNumber);
		
	}
}
