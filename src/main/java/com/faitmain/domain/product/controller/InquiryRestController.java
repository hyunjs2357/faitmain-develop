package com.faitmain.domain.product.controller;

import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faitmain.domain.product.domain.Inquiry;
import com.faitmain.domain.product.service.InquiryService;
import com.faitmain.global.common.Search;

import lombok.extern.slf4j.Slf4j;



@RestController
@RequestMapping("/inquiry/*")
@Slf4j
public class InquiryRestController {
	
	///Field
	@Autowired
	@Qualifier("inquiryServiceImpl")
	private InquiryService inquiryService;

	@GetMapping("json/getInquiry/{inquiryNumber}")
	public Inquiry getInquiry(@PathVariable int inquiryNumber) throws Exception{
		
		log.info("/json/getInquiry/" + inquiryNumber);
		
		return inquiryService.getInquiry(inquiryNumber);
	}
	
	@GetMapping("json/getInquiryList")
	public Map<String, Object> getInquiryList(@RequestBody Search search) throws Exception{
		
		log.info("/json/getInquiryList/");
		
		Map<String, Object> map = inquiryService.getInquiryList(search);
		
		return map;
	}
	
	@GetMapping("json/deleteInquiry/{inquiryNumber}")
	public String deleteInquiry(@PathVariable int inquiryNumber) throws Exception{
		
		log.info("/json/deleteInquiry");
		
		inquiryService.deleteInquiry(inquiryNumber);
		
		JSONObject jsonObject = new JSONObject();
//		jsonObject.put("url", "/summernoteImage/" + savedFileName);
        jsonObject.put("responseCode", "success");
		return jsonObject.toJSONString();
	}
	
}