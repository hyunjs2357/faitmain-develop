package com.faitmain.domain.product.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.domain.Review;
import com.faitmain.domain.product.service.ReviewService;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/review/*")
@Slf4j
public class ReviewRestController {
	
	///Field
	@Autowired
	@Qualifier("reviewServiceImpl")
	private ReviewService reviewService;

	@GetMapping("json/getReview/{reviewNumber}")
	public Review getInquiry(@PathVariable int reviewNumber) throws Exception{
		
		log.info("/json/getReview/ = {}", reviewNumber);
		
		return reviewService.getReview(reviewNumber);
	}
	
	@PostMapping("json/deleteReview")
	@ResponseBody
	public String deleteReview(@RequestParam(value = "reviewNumbers[]") List<Integer> reviewNumbers) throws Exception{
		
		System.out.println(reviewNumbers);
		
		int result = 0;
		for(int reviewNumber : reviewNumbers) {
			result = reviewService.deleteReview(reviewNumber);
		}
		
		return Integer.toString(result);
	}
	
}