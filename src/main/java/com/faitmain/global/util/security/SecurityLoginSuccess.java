package com.faitmain.global.util.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class SecurityLoginSuccess implements AuthenticationSuccessHandler{

    @Override
    public void onAuthenticationSuccess( HttpServletRequest request , HttpServletResponse response , Authentication authentication ) throws IOException, ServletException{
        
    	log.info("onAuthenticationSuccess ::   로그인 성공 " );
    	
    	
  		SecurityUserService securityUserService = ( SecurityUserService )SecurityContextHolder.getContext().getAuthentication().getPrincipal();   //principal 에 사용자 인증 정보 담음

    	
    	
    	if( securityUserService.getUser().getWithdrawalStatus()   ) { // 로그인한 회원이 탈퇴한 회원일때 
        	log.info("onAuthenticationSuccess ::   회원탈퇴 유저 " );

    		SecurityContextHolder.clearContext(); 
        	log.info("onAuthenticationSuccess ::   세션 없앰  " );
        	
    		
    	}
    	
    	
    	
    	
    	
    	
    	
    	
     	
    	response.sendRedirect( "/" );
    }
}
