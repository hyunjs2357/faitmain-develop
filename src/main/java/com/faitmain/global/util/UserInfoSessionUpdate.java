//package com.faitmain.global.util;
//
//import com.faitmain.global.util.security.SecurityUserService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpSession;
//
//@Service
//public class UserInfoSessionUpdate{
//
//    @Autowired
//    private AuthenticationManager authenticationManager;
//
//    public void sessionUpdate( String value , String valueType , SecurityUserService sus ){
//        SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        if ( valueType.equals( "nickname" ) ) {
//            securityUserService.getUser().setNickname( value );
//        } else if ( valueType.equals( "password" ) ) {
//            securityUserService.getUser().setPassword( value );
//        } else if ( valueType.equals( "point" ) ) {
//            int point = securityUserService.getUser().getTotalPoint() + Integer.parseInt( value );
//        }
//
//        /* 세션 등록 */
//        Authentication authentication = authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken( sus.getUsername() , sus.getPassword()) );
//        SecurityContextHolder.getContext().setAuthentication( authentication );
//
//
//    }
//}
