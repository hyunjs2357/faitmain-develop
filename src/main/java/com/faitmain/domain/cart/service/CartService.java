package com.faitmain.domain.cart.service;

import java.util.List;

import com.faitmain.domain.cart.domain.Cart;

public interface CartService{

	/* 카트 추가 */
	int addCart( Cart cart ) throws Exception;

	/* 카트 정보 리스트 */
	List<Cart> getCartList( String buyerId ) throws Exception;

	/* 카트 수량 수정 */
	int updateProductOrderCount( Cart cart ) throws Exception;

	/* 카트 삭제 */
	int deleteCart( int cartNumber );


}