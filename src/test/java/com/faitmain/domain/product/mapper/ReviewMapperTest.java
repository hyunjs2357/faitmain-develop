package com.faitmain.domain.product.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.faitmain.domain.order.mapper.OrderMapper;
import com.faitmain.domain.product.domain.Review;
import com.faitmain.global.common.Search;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ReviewMapperTest {

	@Autowired
	private ReviewMapper reviewMapper;
	
	@Autowired
	private OrderMapper orderMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
//	@Test
	@DisplayName("addReview Mapper Test")
	public void addReviewTest() throws Exception{
		
		System.out.println("addReviewTest start");
		
		Review review = new Review();
//		review.setOrderNumber(1);
		review.setReviewContent("배송이 빨라요~");
		review.setRating(5);
		review.setUserId("user01@naver.com");
		
		reviewMapper.addReview(review);
		
		System.out.println("addReviewTest end");
	}
	
//	@Test
	@DisplayName("getReview Mapper Test")
	public void getReviewTest() throws Exception{
		
		System.out.println("getReviewTest start");
		
		Review review = reviewMapper.getReview(1);
		
		System.out.println("getReviewTest end");
		
	}
	
	@Test
	@DisplayName("getReviewList Mapper Test")
	public void getReviewListTest() throws Exception{
		
		System.out.println("getReviewListTest start");
		/*
		Search search = new Search();
		search.setStartRowNum(1);
		search.setEndRowNum(5);
		*/
		Search search = new Search();
		search.setSearchCondition("productGroupNumber");
		search.setSearchKeyword("10000");
		List<Review> reviews = reviewMapper.getReviewList(search);
		int totalCount = reviewMapper.getTotalCount(search);
		
		System.out.println(reviews);		
		System.out.println(totalCount);
		
		assertThat(totalCount).isEqualTo(0);
		
		System.out.println("getReviewListTest end");
	}
	
//	@Test
	@DisplayName("updateReview Mapper Test")
	public void updateReviewTest() throws Exception{
		
		System.out.println("updateReviewTest start");
		
		Review review = new Review();
		review.setReviewNumber(1);
		review.setReviewContent("감사합니다~");
		review.setReviewImage("review02.jsp");
		review.setRating(5);
		
		reviewMapper.updateReview(review);
				
		System.out.println("updateReviewTest end");
	}
	
//	@Test
	@DisplayName("deleteReview Mapper Test")
	public void deleteReviewTest() throws Exception{
		
		System.out.println("deleteReviewTest start");
		
		reviewMapper.deleteReview(2);
		
		System.out.println("deleteReviewTest end");
	}
}
