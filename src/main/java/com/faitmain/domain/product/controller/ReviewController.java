package com.faitmain.domain.product.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.faitmain.domain.product.domain.Review;
import com.faitmain.domain.product.service.ReviewService;
import com.faitmain.global.common.MiniProjectPage;
import com.faitmain.global.common.Search;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/review/")
@Slf4j
public class ReviewController {

	@Autowired
	@Qualifier("reviewServiceImpl")
	private ReviewService reviewService;


	@GetMapping("addReview")
	public String addReview(@RequestParam("orderProductNumber") int orderProductNumber, HttpServletRequest request, Model model) throws Exception{
		
		log.info("/review/addReview : GET");		
		
		Review review = reviewService.getOrderProduct(orderProductNumber);
		/*
		if(((User) request.getSession(true).getAttribute("user")) != null) {
			review.setUserId(((User) request.getSession(true).getAttribute("user")).getId());
			log.info("userId = {}", review.getUserId());
		}else {
			return "redirect:/user/login/";
		}
		*/
		// 주문 상품을 확인하고 정보 가져와서 저장
//		OrderProduct product = orderService.;
		model.addAttribute("review", review);
		
		return "/product/addReview";
	}
	
	@PostMapping("addReview")
	public String addReview(@ModelAttribute("review") Review review, MultipartHttpServletRequest mRequest) throws Exception{
		
		log.info("/review/addReview : POST");		
		log.info("review = {}", review);		
				
		reviewService.addReview(review, mRequest);
		
		return "redirect:/order/myList/" + review.getUserId();
	}
	
	@GetMapping("getReview")
	public String getReview(@RequestParam("reviewNumber") int reviewNumber, Model model) throws Exception{
		log.info("/review/getReview");
		
		Review review = reviewService.getReview(reviewNumber);
		
		model.addAttribute("review", review);
		
		return "forward:/review/getReview.jsp";		
	}
	
	@GetMapping("getReviewList")
	public String getReviewList(@ModelAttribute("search") Search search, Model model) throws Exception{
//		@RequestParam("resultJsp") String resultJsp
		log.info("/review/getReviewList");
		
		log.info("search = {}", search);
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		search.setPageSize(10);
		
		Map<String, Object> map = reviewService.getReviewList(search);
		
		MiniProjectPage resultPage = new MiniProjectPage( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), 4, 10);
		
		log.info("resultPage : " + resultPage);
		System.out.println("list : " + map.get("list"));
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		/**/		
//		return "/product/" + resultJsp;
		return "/product/listReviewUser";
	}
	
	@GetMapping("updateReview")
	public String updateReview(@RequestParam("reviewNumber") int reviewNumber, Model model) throws Exception{
		
		log.info("/review/updateReview : GET");
		
		Review review = reviewService.getReview(reviewNumber);
		
		model.addAttribute("review", review);
		
		log.info("review : " + review);		
		
		return "/product/updateReview";
	}
	
	@PostMapping("updateReview")
	public String updateReview(@ModelAttribute("review") Review review, MultipartHttpServletRequest mRequest) throws Exception{
		
		log.info("/review/updateReview : POST");
		
		log.info("review = {}", review);
		
		reviewService.updateReview(review, mRequest);
		
		return "redirect:/review/getReviewList?resultJsp=listReviewUser";
	}
	
	@GetMapping("deleteReview")
	public String deleteReview(@RequestParam("reviewNumber") int reviewNumber) throws Exception{
		
		log.info("/review/deleteReview");
		
		reviewService.deleteReview(reviewNumber);
		
		return "redirect:/review/getReviewList?resultJsp=listReviewUser";	
	}
	
	
	
}