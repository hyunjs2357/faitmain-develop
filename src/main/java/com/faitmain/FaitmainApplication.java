package com.faitmain;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan(basePackageClasses = FaitmainApplication.class)
@SpringBootApplication
public class FaitmainApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaitmainApplication.class, args);
	}

}
