package com.faitmain.global.util.security;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class SecurityLoginFail implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure( HttpServletRequest request , HttpServletResponse response , AuthenticationException exception ) throws IOException, ServletException{
    	//인증실패시 타는 핸들러 
    	
    	System.out.println("onAuthenticationFailure") ;
        /* BadCredentialsException는 비밀번호가 일치하지 않았을때 */
        /* InternalAuthenticationServiceException는 아이디가 없을때 */
   
        String errorMessage = "";       
        if (exception instanceof UsernameNotFoundException) {      //  계정 없음      //  아이디 못찾아 엉엉   
        	errorMessage = "1";        }  // 아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요. 
        else if (exception instanceof InternalAuthenticationServiceException) {   
        	errorMessage = "2";        
        	// InternalAuthenticationServiceException 계정 존재 x
        	
        	}      			//UsernameNotFoundException
        		   
        	 else if (exception instanceof DisabledException ) {       // 잘못되 접근
         		errorMessage = "3";       			//UsernameNotFoundException
         		
          		} else if(  exception.getMessage().equals("Bad credentials") ){ //일때 
                	errorMessage = "1";          // 아이디 또는 비밀번호가 맞지 않습니다. 다시 확인해 주세요. 

         		}
        System.out.println("errorMessage+ "+ errorMessage);
        System.out.println("exception: " + exception.getMessage());

        
       System.out.println("에러에러");
      // request.getRequestDispatcher("/user/login").forward(request, response); // 로그인 실패시, user/login 페이지로 이동
       response.sendRedirect("/user/login?errorMessage=" + errorMessage);	// redirect

    }
}
