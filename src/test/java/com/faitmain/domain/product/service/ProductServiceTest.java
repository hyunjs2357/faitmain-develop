package com.faitmain.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.swing.filechooser.FileSystemView;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.mapper.ProductMapper;

public class ProductServiceTest {
	
	ProductServiceImpl productServiceImpl;
	
	@Mock
	ProductMapper productMapper;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		productServiceImpl = new ProductServiceImpl(productMapper);
	}
	
	//add
	//@Test
	@DisplayName("addProduct : 상품 등록")
	void addProduct() throws Exception{
		
		int productNumber = 10018;
		String productName = "아이스크림";
		
		Product mockProduct = Product.builder().productNumber(productNumber).productName(productName).build();
		
//		productServiceImpl.addProduct(mockProduct);
		
		verify(productMapper).addProduct(mockProduct);		
		
	}
	
	//get
//	@Test
	@DisplayName("getProduct : 상품 조회")
	void getProduct() throws Exception{
		
		int productNumber = 10018;
		String productName = "아이스크림"; 
		
		Product mockProduct = Product.builder().productNumber(productNumber).productName(productName).build();
		
		given(productMapper.getProduct(productNumber)).willReturn(mockProduct);
		
		Product responseProduct = productServiceImpl.getProduct(productNumber);
		System.out.println(responseProduct);
		assertThat(responseProduct.getProductName()).isEqualTo(productName);
		
	}
	
	//update
//	@Test
	@DisplayName("updateProduct : 상품 수정")
	void updateProduct() throws Exception{
		
		int productNumber = 10018;
		String productName = "아이스크림"; 
		
		Product mockProduct = Product.builder().productNumber(productNumber).productName(productName).build();
		
//		productServiceImpl.addProduct(mockProduct);
		
		mockProduct = Product.builder().productNumber(productNumber).productName("abc").build();
		
//		productServiceImpl.updateProduct(mockProduct);
		
		given(productMapper.getProduct(productNumber)).willReturn(mockProduct);
		
		Product responseProduct = productServiceImpl.getProduct(productNumber);
		System.out.println(responseProduct);

		assertThat(responseProduct.getProductName()).isEqualTo("abc");
		
	}
	
	//delete
//	@Test
	@DisplayName("deleteProduct : 상품 삭제")
	void deleteProduct() throws Exception{
		
		String rootPath = FileSystemView.getFileSystemView().getHomeDirectory().toString();
		System.out.println(rootPath);
		
		int productNumber = 10018;
		String productName = "아이스크림"; 
		
		Product mockProduct = Product.builder().productNumber(productNumber).productName(productName).build();
		
		when(productMapper.getProduct(productNumber)).thenReturn(mockProduct);
		
		productServiceImpl.deleteProduct(productNumber);
		
		verify(productMapper, times(1)).deleteProduct(productNumber);
		
	}


}
