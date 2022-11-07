package com.faitmain.domain.product.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.faitmain.domain.product.domain.Inquiry;
import com.faitmain.domain.product.service.InquiryService;
import com.faitmain.domain.product.service.ProductService;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.util.security.SecurityUserService;
import com.faitmain.global.common.MiniProjectPage;
import com.faitmain.global.common.Search;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/inquiry/")
@Slf4j
public class InquiryController {
	
	@Autowired
	@Qualifier("inquiryServiceImpl")
	InquiryService inquiryService;
	
	@Autowired
	@Qualifier("productServiceImpl")
	ProductService productService;


	@GetMapping("addInquiry")
	public String addInquiry(@RequestParam("productNumber") int productNumber, HttpServletRequest request, HttpSession session, Model model) throws Exception{
		
		log.info("/inquiry/addInquiry : GET");
		/*
		String id = null;
		if((request.getSession(true).getAttribute("user")) != null){
			id = ((User) request.getSession(true).getAttribute("user")).getId();
			log.info("id = {}", id);
		}else {
			return "redirect:/user/login";
		}
		
		model.addAttribute("userId", id);
		*/		
		model.addAttribute("product", productService.getProduct(productNumber));
		return "/product/addInquiry";
	}
	
	@PostMapping("addInquiry")
	public String addInquiry(@ModelAttribute("inquiry") Inquiry inquiry) throws Exception{
		
		log.info("/inquiry/addInquiry : POST");
		
		log.info("check = {}", inquiry.isSecret());
		log.info("number = {}", inquiry.getInquiryProduct().getProductNumber());
		
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		inquiry.setUserId(user.getId());
		inquiryService.addInquiry(inquiry);
		
		return "redirect:/product/getProduct?productNumber=" + inquiry.getInquiryProduct().getProductNumber();
	}
	
	@GetMapping("getInquiry")
	public String getInquiry(@RequestParam("inquiryNumber") int inquiryNumber, Model model) throws Exception{
		
		log.info("/inquiry/getInquiry");
		
		Inquiry inquiry = inquiryService.getInquiry(inquiryNumber);
		
		model.addAttribute("inquiry", inquiry);
		
		return "forward:/inquiry/getInquiry.jsp";
	}
	
	@RequestMapping(value="getInquiryList")
	public String getInquiryList(@ModelAttribute("search") Search search, @RequestParam("resultJsp") String resultJsp, Model model) throws Exception{
		
		log.info("/inquiry/getInquiryList");
		log.info("search = {}", search);
		log.info("resultJsp = {}", resultJsp);
						
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		
		search.setPageSize(10);
		
		Map<String, Object> map = inquiryService.getInquiryList(search);
		
		MiniProjectPage resultPage = new MiniProjectPage( search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), 4, 10);
		
		log.info("resultPage : " + resultPage);
		
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		/**/		
		return "/product/" + resultJsp;
	}
	
	@GetMapping("updateInquiry")
	public String updateInquiry(@RequestParam("inquiryNumber") int inquiryNumber, @RequestParam("resultJsp") String resultJsp, Model model) throws Exception{
		
		log.info("/inquiry/updateInquiry : GET");
		
		Inquiry inquiry = inquiryService.getInquiry(inquiryNumber);
		
		model.addAttribute("inquiry", inquiry);
		model.addAttribute("resultJsp", resultJsp);
		
		log.info("inquiry : " + inquiry);
		
		return "/product/" + resultJsp;
	}
	
	@PostMapping("updateInquiry")
	public String updateInquiry(@ModelAttribute("inquiry") Inquiry inquiry) throws Exception{
		
		log.info("/inquiry/updateInquiry : POST");
		log.info("inquiry = {}", inquiry);
		log.info("inquiryReplyStatus = {}", inquiry.isInquiryReplyStatus());
		log.info("inquiryReplyContent = {}", inquiry.getInquiryReplyContent());
		
		inquiryService.updateInquiry(inquiry);
		
		String searchParam = "";
		
		if(inquiry.getStoreId() != null && inquiry.getUserId() == null) {
			searchParam = "resultJsp=listInquiryStore&searchCondition=storeId&searchKeyword=" + inquiry.getStoreId();
		}else {
			searchParam = "resultJsp=listInquiryUser&searchCondition=userId&searchKeyword=" + inquiry.getUserId();
		}
		
		return "redirect:/inquiry/getInquiryList?" + searchParam;
	}
	
	@GetMapping("deleteInquiry")
	public String deleteInquiry(@RequestParam("inquiryNumber") int inquiryNumber, @RequestParam("resultJsp") String resultJsp) throws Exception{
		
		log.info("/inquiry/deleteInquiry");
		
		inquiryService.deleteInquiry(inquiryNumber);
		
		return "redirect:/inquiry/listInquiry?resultJsp=" + resultJsp;
	}
	
}