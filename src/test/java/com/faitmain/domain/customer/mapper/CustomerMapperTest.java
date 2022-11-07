//package com.faitmain.domain.customer.mapper;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//import java.util.Collections;
//import java.util.List;
//
//
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.util.CollectionUtils;
//
//import com.faitmain.domain.customer.domain.Customer;
//import com.faitmain.domain.user.domain.User;
//import com.faitmain.domain.user.mapper.UserMapper;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.core.io.JsonEOFException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class CustomerMapperTest{
//	
//	@Autowired
//	private CustomerMapper customerMapper;
//	
////	@Autowired
////	private UserMapper userMapper;
//	
////	@Test
////	@DisplayName("addCustomerBoard")
//	public void addCustomerBoardTest() throws Exception{
//		System.out.println("addCustomerBoardTest start");
//		
//		Customer customer = new Customer();
//		User user = new User();
//		user.setId("admin@naver.com");
//		
//		//customer.setBoardNumber(5);
//		customer.setBoardTitle("TITLE 17");
//		customer.setBoardContent("CONTENT 17");
//		customer.setFAQCategoryCode(1);
//		customer.setBoardType('c');
//		customer.setCustomerId(user);
//		
//		System.out.println(customer);
//		
//		int result = customerMapper.addCustomerBoard(customer);
//		System.out.println("result = " + result);
//		
////		customer = customerMapper.getCustomerBoard(7);
////		System.out.println(customer);
////		
////		//assertThat(customer.getBoardNumber()).isEqualTo(5);
////		assertThat(customer.getBoardTitle()).isEqualTo("TITLE 17");
////		assertThat(customer.getBoardContent()).isEqualTo("CONTENT 17");
////		assertThat(customer.getFAQCategoryCode()).isEqualTo(1);
////		assertThat(customer.getBoardType()).isEqualTo('c');
////		assertThat(customer.getCustomerId().getId()).isEqualTo("admin@naver.com");
////		
//		System.out.println("addCustomerBoardTest end");
//	}
//	
////	@Test
//	public void updateCustomerBoardTest() throws Exception{
//		System.out.println("updateCustomerBoardTest start");
//		
//		Customer customer = new Customer();
//		User user = new User();
//		user.setId("admin@naver.com");
//		
//		customer.setBoardNumber(33);
//		customer.setBoardTitle("공지사항3");
//		customer.setBoardContent("공지사항 세번째");
//		customer.setFAQCategoryCode(3);
//		customer.setBoardType('N');
//		customer.setCustomerId(user);
//		customer.setDelete_yn("N");
//		
//		System.out.println(customer);
//		
//		int result = customerMapper.updateCustomerBoard(customer);
//		System.out.println("result = " + result);
//		if(result == 17) {
//			Customer customer1 = customerMapper.getCustomerBoard(17);
//			try {
//				String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(customer1);
//				
//				System.out.println("====================");
//				System.out.println(boardJson);
//				System.out.println("====================");
//			}catch(JsonProcessingException e) {
//				e.printStackTrace();
//			}
//		}
//		
////		Customer updateCustomerBoard = customerMapper.getCustomerBoard(15);
////		System.out.println(updateCustomerBoard);
////		
////		//assertThat(customer.getBoardNumber()).isEqualTo(5);
////		assertThat(updateCustomerBoard.getBoardTitle()).isEqualTo("TITLE correction");
////		assertThat(updateCustomerBoard.getBoardContent()).isEqualTo("CONTENT correction");
////		assertThat(updateCustomerBoard.getFAQCategoryCode()).isEqualTo(3);
////		assertThat(updateCustomerBoard.getBoardType()).isEqualTo('r');
////		assertThat(updateCustomerBoard.getCustomerId().getId()).isEqualTo("admin@naver.com");
////		
////		System.out.println("updateCustomerBoardTest end");
//	}
//	
////	@Test
//	public void getCustomerBoardTest() throws Exception{
//		System.out.println("getCustomerBoardTest start");
//		
//		Customer customer = customerMapper.getCustomerBoard(16);
//		
//		try {
//			String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(customer);
//			
//			System.out.println("=================");
//			System.out.println(boardJson);
//			System.out.println("=================");
//		}catch(JsonEOFException e) {
//			e.printStackTrace();
//		}
////		
////		assertThat(getCustomerBoard.getBoardTitle()).isEqualTo("TITLE correction");
////		assertThat(getCustomerBoard.getBoardContent()).isEqualTo("CONTENT correction");
////		assertThat(getCustomerBoard.getFAQCategoryCode()).isEqualTo(4);
////		assertThat(getCustomerBoard.getBoardType()).isEqualTo('N');
////		assertThat(getCustomerBoard.getCustomerId().getId()).isEqualTo("admin@naver.com");
////		
////		System.out.println("getCustomerBoardTest end");
//				
//	}
//	
////	@Test
//	public void multipleAddTest() throws Exception{
//		System.out.println("multipleAddTest start");
//		
//		Customer customer = new Customer();
//		User user = new User();
//		
//		for(int i =2; i<10; i++) {
//			
//			user.setId("admin@naver.com");
//			customer.setBoardNumber(i);
//			customer.setBoardTitle(i + "번 게시글 제목");
//			customer.setBoardContent(i + "번 게시글 내용");
//			customer.setCustomerId(user);
//			customer.setBoardType('N');
//			customer.setDelete_yn("N");
//			customerMapper.addCustomerBoard(customer);
//		}
//		System.out.println("multipleAddTest end");
//	}
//	
////	@Test
//	public void getCustomerBoardListTest() throws Exception{
//		int boardTotalCount = customerMapper.getBoardTotalCount();
//		
//		if(boardTotalCount > 0) {
//			List<Customer> list = customerMapper.getCustomerBoardList();
//			if(CollectionUtils.isEmpty(list)) {
//				for(Customer customer : list) {
//					System.out.println("============================");
//					System.out.println(customer.getBoardTitle());
//					System.out.println(customer.getBoardContent());
//					System.out.println(customer.getCustomerId());
//					System.out.println(customer.getBoardRegDate());
//					System.out.println("============================");
//				}
//			}
//		}
//	}
//	
////	@Test
//	public void deleteCustomerBoardTest() throws Exception {
//		int result = customerMapper.deleteCustomerBoard(17);
//		if(result == 17) {
//			Customer customer = customerMapper.getCustomerBoard(17);
//			
//			try {
//				String boardJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(customer);
//				
//				System.out.println("=================");
//				System.out.println(boardJson);
//				System.out.println("=================");
//			}catch(JsonEOFException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//
//
//}
