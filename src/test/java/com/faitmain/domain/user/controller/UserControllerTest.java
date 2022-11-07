package com.faitmain.domain.user.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.faitmain.domain.user.service.UserSerivce;
import com.faitmain.domain.user.service.UserServiceImpl;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	
    @Mock
    private UserSerivce userService;

    @InjectMocks
    private UserController userController;
    
    
    
    
	
}
