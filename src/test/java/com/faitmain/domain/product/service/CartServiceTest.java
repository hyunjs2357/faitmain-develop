/*
package com.faitmain.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import com.faitmain.domain.cart.service.CartServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.faitmain.domain.cart.domain.Cart;
import com.faitmain.domain.cart.mapper.CartMapper;

public class CartServiceTest {

	CartServiceImpl cartServiceImpl;
	
	@Mock
	CartMapper cartMapper;
	
	@BeforeEach
	void setup() {
		MockitoAnnotations.initMocks(this);
		cartServiceImpl = new CartServiceImpl(cartMapper);
	}
	
	//add
//	@Test
	@DisplayName("addCart : 장바구니 등록")
	void addCart() throws Exception{
		
		int cartNumber = 10018;
		int cartQuantity = 3;
		
		Cart mockCart = Cart.builder().cartNumber(cartNumber).cartQuantity(cartQuantity).build();
		
		cartServiceImpl.addCart(mockCart);
		
		verify(cartMapper).addCart(mockCart);		
		
	}
	
	//update
//	@Test
	@DisplayName("updateCart : 장바구니 수정")
	void updateCart() throws Exception{
		
		int cartNumber = 10018;
		int cartQuantity = 3;
		
		Cart mockCart = Cart.builder().cartNumber(cartNumber).cartQuantity(cartQuantity).build();
		
		cartServiceImpl.addCart(mockCart);
		
		mockCart = Cart.builder().cartNumber(cartNumber).cartQuantity(1).build();
		
		cartServiceImpl.updateCart(mockCart);
		
//		verify(cartMapper).updateCart(mockCart);
	}
	
	//delete
	@Test
	@DisplayName("deleteCart : 장바구니 삭제")
	void deleteCart() throws Exception{
		
		int cartNumber = 10018;
		int cartQuantity = 3;
		
		Cart mockCart = Cart.builder().cartNumber(cartNumber).cartQuantity(cartQuantity).build();
		
//		when(cartMapper.selectCartList(cartNumber)).thenReturn(mockCart);
		
//		cartServiceImpl.deleteCart(cartNumber);
		
//		verify(cartMapper, times(1)).deleteCart(cartNumber);
		
	}
	
}
*/
