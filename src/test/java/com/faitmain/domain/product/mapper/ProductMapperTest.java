package com.faitmain.domain.product.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Search;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductMapperTest {
	
	@Autowired
	private ProductMapper productMapper;
	
	//insert
//	@Test
	@DisplayName("addProduct Mapper Test")
	public void addProductTest() throws Exception{
		
		System.out.println("addProductTest start");
		
		Product product = new Product();
		
		product.setProductName("오레오마카롱");
		product.setProductPrice(3800);
		
		User store = new User();
		store.setId("store01@naver.com");
		
		product.setStore(store);
		product.setProductMainImage("oreo.jpg");
		product.setCategoryCode("01");
		product.setDeliveryCharge(3000);
		product.setProductQuantity(30);
		
		productMapper.addProduct(product);
		
		System.out.println("insert 완료 : " + product.getProductGroupNumber());
		
		Product resultProduct = productMapper.getProduct(10016);
		assertThat(resultProduct.getProductNumber()).isEqualTo(10016);
		System.out.println("addProductTest end");
		
	}
	
	//insert
//	@Test
	@DisplayName("addImage Mapper Test")
	public void addImageTest() throws Exception{
		
		System.out.println("addImageTest start");
		
		Product product = new Product();
		
		product.setProductName("오레오마카롱");
		product.setProductPrice(3800);
		
		User store = new User();
		store.setId("store01@naver.com");
		
		product.setStore(store);
		product.setProductMainImage("oreo.jpg");
		product.setCategoryCode("01");
		product.setDeliveryCharge(3000);
		product.setProductQuantity(30);
		
		productMapper.addProduct(product);
		
		System.out.println("insert 완료");
		
		Product resultProduct = productMapper.getProduct(10016);
		assertThat(resultProduct.getProductNumber()).isEqualTo(10016);
		System.out.println("addProductTest end");
		
	}
	
//	get product
//	@Test
	@DisplayName("getProduct Mapper Test")
	public void getProductTest() throws Exception{
		
		System.out.println("getProductTest start");
		
		Product product = productMapper.getProduct(10000);
		
		System.out.println(product);
		
		assertThat(product.getProductPrice()).isEqualTo(5000);
		assertThat(product.getStore().getId()).isEqualTo("store03@naver.com");
		
		System.out.println("getProductTest end");
		
	}
	
	@Test
	@DisplayName("getProductList Mapper Test")
	public void getProductListTest() throws Exception{
		
		System.out.println("getProductListTest start");
		
		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("searchOrderName", "product_name DESC");
		map.put("startRowNum", 1);
		map.put("endRowNum", 5);
		
		List<Product> list = productMapper.getProductList(map);
		assertThat(list.get(0).getProductName()).isEqualTo("프랑스에서 만든 마카롱 10개 세트");	
		System.out.println(list);
		System.out.println("getProductListTest end");
		
	}
	
	//update
//	@Test
	@DisplayName("updateProduct Mapper Test")
	public void updateProductTest() throws Exception{
		
		System.out.println("updateProductTest start");
		
		Product product = new Product();
		product.setProductNumber(10016);
		product.setProductName("딸기맛마카롱");
		product.setProductMainImage("akzkfhd.png");
		product.setProductPrice(3000);
		product.setProductDetail("마카롱 10세트 딸기맛 특별 할인");
		product.setCategoryCode("01");
		product.setProductQuantity(5);
		product.setProductStatus("01");
		product.setProductGroupNumber(0);
		
		productMapper.updateProduct(product);
		
		Product resultProduct = productMapper.getProduct(10016);
		assertThat(resultProduct.getProductName()).isEqualTo("딸기맛마카롱");
		
		System.out.println("updateProductTest end");
		
	}
	
	//update productStatus
//	@Test
	@DisplayName("updateProductStatus Mapper Test")
	public void updateProductStatusTest() throws Exception{
		
		System.out.println("updateProductStatusTest start");
		
		Product product = new Product();
		product.setProductNumber(10013);
		product.setProductStatus("02");
		productMapper.updateProductStatus(product);
		
		System.out.println("updateProductStatusTest start");
		
	}
	
	//delete
//	@Test
	@DisplayName("deleteProduct Mapper Test")
	public void deleteProductTest() throws Exception{
		
		System.out.println("deleteProductTest start");
		
		productMapper.deleteProduct(10016);
		
		System.out.println("deleteProductTest end");
		
	}
	
}