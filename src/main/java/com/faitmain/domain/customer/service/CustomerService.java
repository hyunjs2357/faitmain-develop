package com.faitmain.domain.customer.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.faitmain.domain.customer.domain.BanStatus;
import com.faitmain.domain.customer.domain.Customer;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Page;
import com.faitmain.global.common.Paging;
import com.faitmain.global.common.Search;



public interface CustomerService {
	
//	게시판 등록
	public int addCustomerBoard(Customer customer) throws Exception;
	
//	게시판 수정
	public int updateCustomerBoard(Customer customer) throws Exception;
	
//	게시판 상세조회	
	public Customer getCustomerBoard(int boardNumber) throws Exception;

//	라이브가이드 상세조회
	public Customer getLiveGuide(char boardType) throws Exception;

//	게시판 삭제	
	public int deleteCustomerBoard(int boardNumber) throws Exception;

//	게시판 리스트 조회	
	public List<Customer> getCustomerBoardList(char boardType, Paging paging) throws Exception;

//	자주묻는질문 리스트 조회
	public List<Customer> getFAQList(String FAQCategoryCode) throws Exception;
	
//	게시판 총 개수
	public int getBoardTotalCount(char boardType, Paging paging) throws Exception;
	
// 게시판 목록(페이징 적용)
	public List<Customer> getListPaging(Paging paging) throws Exception;
	
//	게시판 총 개수
//	public int getFAQTotalCount(String FAQCategoryCode, Paging paging) throws Exception;
	
//	이미지 등록
	public void addCustomerBoardImage(Image image) throws Exception;
	
//	이미지 수정
	public void updateCustomerBoardImage(Image image) throws Exception;


}
//	public BanStatus updateBanStatus(int reportNumber) throws Exception;


//	
//	
//	
//	
//	
//
//	
//	
//	/*
//	 * public void increaseViewCount(int customer_board_number, HttpSession session)
//	 * throws Exception;
//	 */
//}
