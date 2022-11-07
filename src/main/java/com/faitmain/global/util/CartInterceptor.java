package com.faitmain.global.util;

import com.faitmain.domain.user.domain.User;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class CartInterceptor implements HandlerInterceptor{

    @Override
    public boolean preHandle( HttpServletRequest request , HttpServletResponse response , Object handler ) throws Exception{

        HttpSession session = request.getSession();

        User user = ( User ) session.getAttribute( "user" );

        if ( user == null ) {
            response.sendRedirect( "/index" );
            return false;
        } else {
            return true;
        }
    }
}
