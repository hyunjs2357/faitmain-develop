
package com.faitmain.domain.customer.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.maven.shared.invoker.SystemOutLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.faitmain.domain.customer.domain.BanStatus;
import com.faitmain.domain.customer.domain.Customer;
import com.faitmain.domain.customer.service.BanService;
import com.faitmain.domain.customer.service.CustomerService;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.MiniProjectPage;
import com.faitmain.global.common.Page;
import com.faitmain.global.common.Paging;
import com.faitmain.global.common.Search;
import com.faitmain.global.util.UiUtils;
import com.faitmain.global.util.security.SecurityUserService;

import lombok.extern.log4j.Log4j;


@Controller
@RequestMapping("/customer/")
@Log4j
public class BanController extends UiUtils {

	@Autowired
	@Qualifier("banServiceImpl")
	private BanService banService;
	
	@GetMapping("addReport")
	public String openReport(Model model) throws Exception{	
		
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		log.info("ReportForm");
		model.addAttribute("msg", "로그인 해주세요.");

		model.addAttribute("url", "addReport");
		return "customer/reportForm";
	}
	
	@GetMapping("listReport")
	public void reportList() {
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		log.info("ReportList");
	}
	
//	신고 등록 기능
	@PostMapping("addReport")
	public String addReport(@ModelAttribute BanStatus banStatus, MultipartHttpServletRequest mRequest) throws Exception{
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		
		log.info("BanStatus : " + banStatus);
		
		banService.addReport(banStatus, mRequest);
		return null;
	}




//	게시판 목록	(페이징 적용, 게시판 타입별, 라이브가이드 카테고리별 조회 적용)	
//	@GetMapping("listBoard")
//	public String getCustomerBoardList(@ModelAttribute Customer customer, Model model, 
//									   @ModelAttribute Paging paging) throws Exception {
//		
//		System.out.println("=======list==========");
////		model.addAttribute("boardList", customerService.getCustomerBoardList(customer.getBoardType()));
//		List<Customer> boardList = customerService.getCustomerBoardList(customer.getBoardType(), paging);
//		System.out.println(customer.getBoardType());
//		int total = customerService.getBoardTotalCount(customer.getBoardType(), paging);
//        
//        Page page = new Page(paging, total);
//		model.addAttribute("boardList", boardList);
//		model.addAttribute("page", customerService.getListPaging(paging));
//		model.addAttribute("page", paging.getKeyword());
//		System.out.println(boardList);
//		System.out.println(paging.getKeyword());
//		String url =  null;
//       
//		if(customer.getBoardType() == 'N') {
//			
//			url= "customer/noticeList";
//						
//		}else if(customer.getBoardType() == 'L') {
//			
//			url = "admin/liveGuideList";
//			
//		}else if(customer.getBoardType() == 'F'){	
//			
//			url="customer/faqList";
//		
//		}
//		
//		
////		int total = customerService.getBoardTotalCount(customer.getBoardType(), paging);
////        
////        Page page = new Page(paging, total);
//       
////        System.out.println("==-==-===-=");
////     
////        model.addAttribute("page", page);
////		
////        System.out.println(page);
//        
//		return url;
//		
//	}
	

	
//	
//	@RequestMapping("detailBoard")
//	public  String getCustomerBoard(@RequestParam Integer boardNumber, Model model, Paging paging) throws Exception{
//	
//		System.out.println("boardNumber = "+boardNumber);
//		System.out.println("앙 찍먹따리 : " + paging);
//		System.out.println("============= detail ===============");
//			
//		model.addAttribute("customer", customerService.getCustomerBoard(boardNumber));
//		model.addAttribute("page", paging);
//		System.out.println(boardNumber);
//		
//
//		return "customer/noticeDetail";
//	}
//	

	
//	@ResponseBody
//	@PostMapping("deleteGuide")
//	public int deleteLiveGuide(@ModelAttribute Customer customer, 
//								@RequestParam(value = "chbox[]") List<String> chArr, Model model) throws Exception {
//		
//			
//			int result = 0;
//			int bno = 0;
//			
//			for(String i : chArr) {
//				bno = Integer.parseInt(i);
//				customer.setBoardNumber(bno);
//				customerService.deleteCustomerBoard(customer.getBoardNumber());
//			}
//			result = 1;
//			
//			return 1;
//		
//	}
//	
//
//	
//	
//	
//}	




////	@PostMapping("deleteNotice")
////	public String deleteNotice(@RequestParam(value = "boardNumber", required = false) Integer boardNumber) {
////		if (boardNumber == null) {
////			return "redirect:/customer/list";
////		}
////		try {
////			boolean isDeleted = customerService.deleteCustomerBoard(boardNumber);
////			if (isDeleted == false) {
////
////			}
////		} catch (DataAccessException e) {
////
////		} catch (Exception e) {
////
////		}
////		return "redirect:/customer/list";
////
////	}
//	
//
//	
//
}
