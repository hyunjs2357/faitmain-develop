
package com.faitmain.domain.product.mapper;

import java.util.HashMap;
import java.util.Map;

import com.faitmain.domain.cart.mapper.CartMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.faitmain.domain.cart.domain.Cart;
import com.faitmain.domain.product.domain.Product;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CartMapperTest {

	@Autowired
	private CartMapper cartMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	@Test
	@DisplayName("addCart Mapper Test")
	public void addCartTest() throws Exception{
		
		System.out.println("addCartTest start");
		
		Cart cart = new Cart();
		
		Product product = productMapper.getProduct(10016);

		
		System.out.println("addCartTest end");
	}
	
	@Test
	@DisplayName("selectCartList Mapper Test")
	public void getCartListTest() throws Exception{
		
		System.out.println("getCartListTest start");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("userId", "user04@naver.com");
		map.put("startRowNum", 1+"");
		map.put("endRowNum", 5+"");
		
//		List<Cart> carts = cartMapper.selectCartList(map);
		
//		System.out.println(carts);		
		System.out.println("getCartListTest end");
	}
	
	@Test
	@DisplayName("updateCart Mapper Test")
	public void updateCartTest() throws Exception{
		
		System.out.println("updateCartTest start");
		
		Cart cart = new Cart();
		cart.setCartNumber(10007);
		cart.setProductOrderCount(2);
		
		cartMapper.updateCart(cart);
				
		System.out.println("updateCartTest end");
	}
	
	@Test
	@DisplayName("deleteCart Mapper Test")
	public void deleteCartTest() throws Exception{
		
		System.out.println("deleteCartTest start");
		
		cartMapper.deleteCart(10007);
		
		System.out.println("deleteCartTest end");
	}
	
}
