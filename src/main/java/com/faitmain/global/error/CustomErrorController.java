package com.faitmain.global.error;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@Controller
public class CustomErrorController implements ErrorController{

    @RequestMapping( "/error" )
    public String error( HttpServletRequest request , Model model ){
        Object status = request.getAttribute( "javax.servlet.error.status_code" );

        if ( status.equals( HttpStatus.NOT_FOUND.value() ) ) {
            return "/error/404";
        } else if ( status.equals( 405 ) ) {
            return "/error/405";
        } else {
            return "/error/500";
        }
    }
}
