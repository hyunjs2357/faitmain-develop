package com.faitmain.global.config;

import com.faitmain.global.util.CartInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers( ResourceHandlerRegistry registry ){
        registry.addResourceHandler( "/summernoteImage/**" )
                .addResourceLocations( "file:///C:/summernote_image/" );
    }
/*
    @Override
    public void addInterceptors( InterceptorRegistry registry ){
        registry.addInterceptor( new CartInterceptor() )
                .addPathPatterns( "/cart/**" )
                .excludePathPatterns( "/cart/add" );

    }


*/    


}


