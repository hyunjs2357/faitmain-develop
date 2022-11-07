//package com.faitmain.domain.customer.service;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.faitmain.domain.customer.domain.Customer;
//import com.faitmain.domain.user.domain.User;
//
//@SpringBootTest
//@Transactional
//class CustomerServiceTest{
//	
//	@Autowired
//	CustomerServiceImpl customerServiceImpl;
//	
//	//@Test
//	public void addCustomerBoardTest() throws Exception{
//		
//		Customer customer = new Customer();
//		User user = new User();
//		user.setId("admin@naver.com");
//		
//		customer.setBoardNumber(2);
//		customer.setBoardTitle("TITLE 2");
//		customer.setBoardContent("CONTENT 2");
//		customer.setFAQCategoryCode(2);
//		customer.setBoardType('n');
//		customer.setCustomerId(user);
//		
//		System.out.println(customer);
//		
//		customerServiceImpl.addCustomerBoard(customer);
//	}
//	
//	@Test
//	public void updateCustomerBoardTest() throws Exception{
//		System.out.println("updateCustomerBoardTest start");
//		
//		Customer customer = new Customer();
//		User user = new User();
//		user.setId("admin@naver.com");
//		
//		customer.setBoardNumber(6);
//		customer.setBoardTitle("TITLE update");
//		customer.setBoardContent("CONTENT update");
//		customer.setFAQCategoryCode(2);
//		customer.setBoardType('n');
//		customer.setCustomerId(user);
//		
//		System.out.println(customer);
//		
//		int result = customerServiceImpl.updateCustomerBoard(customer);
//		System.out.println("result = " + result);
//		
//		Customer updateCustomerBoard = customerServiceImpl.getCustomerBoard(6);
//		System.out.println(updateCustomerBoard);
//		
//		//assertThat(customer.getBoardNumber()).isEqualTo(5);
//		assertThat(updateCustomerBoard.getBoardTitle()).isEqualTo("TITLE update");
//		assertThat(updateCustomerBoard.getBoardContent()).isEqualTo("CONTENT update");
//		assertThat(updateCustomerBoard.getFAQCategoryCode()).isEqualTo(2);
//		assertThat(updateCustomerBoard.getBoardType()).isEqualTo('n');
//		assertThat(updateCustomerBoard.getCustomerId().getId()).isEqualTo("admin@naver.com");
//		
//		System.out.println("updateCustomerBoardTest end");
//		
//		
//	}
//	
//	//@Test
//	public void getCustomerBoardTest() throws Exception{
//		
//	}
//}
