package com.faitmain.domain.cart.service;

import com.faitmain.domain.cart.domain.Cart;
import com.faitmain.domain.cart.mapper.CartMapper;
import com.faitmain.domain.order.domain.OrderPageProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CartServiceImpl implements CartService{

    @Autowired
    private CartMapper cartMapper;

    @Override
    public int addCart( Cart cart ) throws Exception{

        int i = 0;

        for ( OrderPageProduct opp : cart.getOrderPageProductList() ) {
            cart.setProductNumber( opp.getProductNumber() );

            /* 장바구니 데이터 체크*/
            Cart checkCart = cartMapper.selectCart( cart );
            if ( checkCart == null ) {
                log.info( "장바구니에 추가" );
                cart.setProductOrderCount( opp.getProductOrderCount() );
                i = cartMapper.addCart( cart );
            } else {
                log.info( "이미 장바구니에 있는 상품" );
                i = 2;
            }

        }

        return i;

    }

    @Override
    public List<Cart> getCartList( String buyerId ) throws Exception{

        List<Cart> cartList = cartMapper.selectCartList( buyerId );

        for ( Cart cart : cartList ) {
            /* 총합 정보 초기화 */
            cart.initSaleTotal();
            /* 이미지 정보 */
            cart.setProductMainImage( cart.getProductMainImage() );
        }
        return cartList;
    }


    /* 카트 수량 수정 */
    @Override
    public int updateProductOrderCount( Cart cart ) throws Exception{

        return cartMapper.updateCart( cart );
    }

    /* 카트 삭제 */
    @Override
    public int deleteCart( int cartNumber ){
        return cartMapper.deleteCart( cartNumber );
    }


}