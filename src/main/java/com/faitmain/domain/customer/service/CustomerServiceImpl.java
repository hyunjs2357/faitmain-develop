package com.faitmain.domain.customer.service;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.faitmain.domain.customer.domain.BanStatus;
import com.faitmain.domain.customer.domain.Customer;
import com.faitmain.domain.customer.mapper.CustomerMapper;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Page;
import com.faitmain.global.common.Paging;




@Service("customerServiceImpl")
@Transactional
public class CustomerServiceImpl implements CustomerService{
	
	@Autowired
	private CustomerMapper customerMapper;	//데이터베이스에 접근하는 DAO bean을 선언 
	
	public CustomerServiceImpl(CustomerMapper customerMapper) {
		this.customerMapper = customerMapper;
	}


//	게시판 등록
	@Override
	public int addCustomerBoard(Customer customer) throws Exception {
		
		return customerMapper.addCustomerBoard(customer);
	}

//	게시판 수정	
	@Override
	public int updateCustomerBoard(Customer customer) throws Exception{ 
		 return customerMapper.updateCustomerBoard(customer);
	  
	}
	
//	게시판 삭제
	@Override
	public int deleteCustomerBoard(int boardNumber) throws Exception{
		return customerMapper.deleteCustomerBoard(boardNumber);
	}
	
//	게시판 상세조회
	@Override
	public Customer getCustomerBoard(int boardNumber) throws Exception {
		return customerMapper.getCustomerBoard(boardNumber);
	}
	
//	FAQ 리스트 조회(카테고리 이용)	
	@Override
	public List<Customer> getFAQList(String FAQCategoryCode) throws Exception{
		
		return customerMapper.getFAQCategoryList(FAQCategoryCode);
	}
	
//	라이브가이드 상세조회	
	@Override
	public Customer getLiveGuide(char boardType) throws Exception{
		return customerMapper.getLiveGuide(boardType);
	}

	
// 게시판 삭제(Enum 활용)
//	@Override
//	public boolean deleteCustomerBoard(int boardNumber) throws Exception {
//		int queryResult = 0;
//		
//		Customer customer = customerMapper.getCustomerBoard(boardNumber);
//		
//		if(customer != null && "N".equals(customer.getDeleteYn())) {		// 삭제여부 - 'Y' : 삭제 x, 'N' : 삭제
//			queryResult = customerMapper.deleteCustomerBoard(boardNumber);
//		}
//		
//		return (queryResult == 1) ? true : false;

//	}
	
// 게시판 조회	
	@Override
	public List<Customer> getCustomerBoardList(char boardType, Paging paging) throws Exception {
						
		return customerMapper.getCustomerBoardList(boardType, paging); 
	}
	
// 게시물 총 개수
	public int getBoardTotalCount(char boardType, Paging paging) throws Exception{
		return customerMapper.getBoardTotalCount(boardType, paging);
	}
	
// 게시판 목록(페이징 적용)
	public List<Customer> getListPaging(Paging paging) throws Exception{
		return customerMapper.getListPaging(paging);
	}
	
// 이미지 등록
	@Override
	public void addCustomerBoardImage(Image image) throws Exception {
		customerMapper.addCustomerBoardImage(image);
	}

// 이미지 수정
	@Override
	public void updateCustomerBoardImage(Image image) throws Exception {
		customerMapper.updateCustomerBoardImage(image);
	}
	
	
}