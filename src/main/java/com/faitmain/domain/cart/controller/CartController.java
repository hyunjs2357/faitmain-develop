package com.faitmain.domain.cart.controller;

import com.faitmain.domain.cart.domain.Cart;
import com.faitmain.domain.cart.service.CartServiceImpl;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.util.security.SecurityUserService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping( "/cart" )
@Slf4j
public class CartController{

    @Autowired
    private CartServiceImpl cartService;

    @PostMapping( "/add" )
    @ResponseBody
    public String addCartPOST( Cart cart ) throws Exception{

        log.info( "cart = {}" , cart );

        int result = cartService.addCart( cart );
        return String.valueOf( result );
    }


    @GetMapping( "/{buyerId}" )
    public String cartPageGET( @PathVariable String buyerId , Model model ) throws Exception{

        model.addAttribute( "cartList" , cartService.getCartList( buyerId ) );
        log.info( "buyerId = {}" , buyerId );
        log.info( "cartList = {}" , cartService.getCartList( buyerId ) );
        return "/cart";
    }

    /* 장바구니 수량 수정 */
    @PostMapping( "/update" )
    public String updateCartPOST( Cart cart ) throws Exception{
        log.info( "updateCartPOST 실행 " );
        cartService.updateProductOrderCount( cart );
        return "redirect:/cart/" + cart.getBuyerId();
    }

    /* 장바구니 수량 수정 */
    @PostMapping("/delete")
    public String deleteCartPOST(Cart cart) {
        cartService.deleteCart(cart.getCartNumber());
        return "redirect:/cart/" + cart.getBuyerId();

    }

}
