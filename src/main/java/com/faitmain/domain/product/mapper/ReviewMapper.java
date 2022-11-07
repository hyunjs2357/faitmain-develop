package com.faitmain.domain.product.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.faitmain.domain.product.domain.Review;
import com.faitmain.global.common.Search;

@Mapper
public interface ReviewMapper {
	
	public Review getOrderProduct(int orderProductNumber) throws Exception;
	
	//INSERT - 리뷰 등록
	public void addReview(Review review) throws Exception;
	
	//SELECT - 리뷰 상세 조회
	public Review getReview(int reviewNumber) throws Exception;
	
	//SELECT - 리뷰 목록 조회
	public List<Review> getReviewList(Search search) throws Exception;
	
	//SELECT - 리뷰 count
	public int getTotalCount(Search search) throws Exception;
	
	//UPDATE - 리뷰 수정
	public void updateReview(Review review) throws Exception;
	
	//DELETE - 리뷰 삭제
	public int deleteReview(int reviewNumber) throws Exception;
	
}