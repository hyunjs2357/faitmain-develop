
package com.faitmain.domain.customer.controller;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
import com.faitmain.domain.customer.service.CustomerService;
import com.faitmain.domain.order.domain.Order;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.MiniProjectPage;
import com.faitmain.global.common.Page;
import com.faitmain.global.common.Paging;
import com.faitmain.global.common.Search;
import com.faitmain.global.util.UiUtils;
import com.faitmain.global.util.security.SecurityUserService;




@Controller
@RequestMapping("/customer/")
public class CustomerController {

	@Autowired
	@Qualifier("customerServiceImpl")
	private CustomerService customerService;
	

	@GetMapping("openBoardIndex")
	public String openBoardIndex() throws Exception {
		return "customer/customerCenterIndex2";
	}

	@GetMapping("addNotice")
	public String openNotice() throws Exception{
		
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		return "admin/noticeForm";
	}	

	@GetMapping("addLiveGuide")
	public String openGuide() throws Exception{
		
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		return "admin/liveGuideForm";
	}
	
	@GetMapping("addFAQ")
	public String openFAQ() throws Exception{
		
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		return "admin/faqForm";
	}
	
	
//	게시판 등록
	@PostMapping("addBoard")
	public String addCustomerBoard( @ModelAttribute Customer customer, Model model, Paging paging) throws Exception{
		
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		
		customerService.addCustomerBoard(customer);
		
		System.out.println(customer);
		
		String url =  null;
		
		List<Customer> list = customerService.getCustomerBoardList(customer.getBoardType(), paging);
		
		model.addAttribute("boardList", list);
		if(customer.getBoardType() == 'N') {
			url = "redirect:/customer/listBoard?boardType="+customer.getBoardType();
		}else if(customer.getBoardType() == 'L') {
			url = "customer/liveGuideDetail";
		}else if(customer.getBoardType() == 'F') {
			url =  "redirect:/customer/listBoard?boardType="+customer.getBoardType();
		}
		
		return url;

	}


// 게시판 수정
	@GetMapping("updateBoard")
	public String updateCustomerBoard(@ModelAttribute Customer customer, Model model) throws Exception {
		System.out.println("=======update=====");
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		String url = null;
		model.addAttribute("customer", customer);
		System.out.println(customer);
		model.addAttribute("customer", customerService.getCustomerBoard(customer.getBoardNumber()));
		System.out.println(customerService.getCustomerBoard(customer.getBoardNumber()));
		
		if(customer.getBoardType() == 'N') {
			url = "admin/noticeUpdate";
		}else if(customer.getBoardType() == 'L'){
			url = "admin/updateLiveGuide";
		}
		return url;
	}
	
	@PostMapping("updateBoard")
	public String updateCustomerBoard(@ModelAttribute Customer customer, RedirectAttributes rttr) throws Exception {
		System.out.println("=======udpatePOST========");
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		String url = null;
		
		customerService.updateCustomerBoard(customer);
		
		System.out.println(customer);
		rttr.addFlashAttribute("result", "update success");
		if(customer.getBoardType() == 'N') {
			url =  "redirect:/customer/listBoard?boardType="+ customer.getBoardType();
		}else if(customer.getBoardType() == 'L')
			url = "redirect:/customer/listBoard?boardType="+ customer.getBoardType();
		
		return url;
	}


//	게시판 목록	(페이징 적용, 게시판 타입별, 라이브가이드 카테고리별 조회 적용)	
	@GetMapping("listBoard")
	public String getCustomerBoardList(@ModelAttribute Customer customer, Model model, 
									   @ModelAttribute Paging paging) throws Exception {
		
		System.out.println("=======list==========");
//		model.addAttribute("boardList", customerService.getCustomerBoardList(customer.getBoardType()));
		List<Customer> boardList = customerService.getCustomerBoardList(customer.getBoardType(), paging);
		System.out.println(customer.getBoardType());
	
		int total = customerService.getBoardTotalCount(customer.getBoardType(), paging);
        System.out.println(paging);
        Page page = new Page(paging, total);
		model.addAttribute("boardList", boardList);
		System.out.println("boardList ="+boardList);
		
		model.addAttribute("pageMaker", customerService.getListPaging(paging));
		System.out.println("pageMaker ="+customerService.getListPaging(paging));
		
		model.addAttribute("pageMaker", page);
	
		System.out.println(page);
		
		String url =  null;
       
		if(customer.getBoardType() == 'N') {
			
			url= "customer/noticeList";
						
		}else if(customer.getBoardType() == 'L') {
			SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
			User user = (User) securityUserService.getUser();
			url = "admin/liveGuideList";
			
		}else if(customer.getBoardType() == 'F') {
			url = "customer/faqList";
		}
		return url;
		
	}

//	@GetMapping("detailBoard")
//	public  String getCustomerBoard(@RequestParam Integer boardNumber, Model model, Paging paging) throws Exception{
//	
//		System.out.println("boardNumber = "+boardNumber);
//		System.out.println("앙 찍먹따리 : " + paging);
//		System.out.println("============= detail ===============");
//			
//		model.addAttribute("customer", customerService.getCustomerBoard(boardNumber));
//		model.addAttribute("pageMaker", paging);
//		System.out.println(boardNumber);
//		System.out.println(paging);
//		
//
//		return "customer/noticeDetail";
//	}
	
	@GetMapping("detailBoard")
	public  String getCustomerBoard(@ModelAttribute Customer customer, Model model, Paging paging) throws Exception{
	
		System.out.println("boardNumber = "+customerService.getCustomerBoard(customer.getBoardNumber()));
		System.out.println("앙 찍먹따리 : " + paging);
		System.out.println("============= detail ===============");
		customerService.getLiveGuide(customer.getBoardType());
		model.addAttribute("customer", customerService.getCustomerBoard(customer.getBoardNumber()));
//		model.addAttribute("customer", customerService.getCustomerBoard(customer.getBoardType()));
		model.addAttribute("pageMaker", paging);
		System.out.println(customer.getBoardType());
		System.out.println(customerService.getCustomerBoard(customer.getBoardNumber()));
		System.out.println(paging);
		
		String url = null;
		if(customer.getBoardType() == 'N') {
			url = "customer/noticeDetail";
		}else if(customer.getBoardType() == 'L') {
			url = "customer/liveGuideDetail";
		}
		
		return url;
	}
	
	
	

	@GetMapping("detailGuide")
	public String openGuideDetail(@RequestParam(value="boardType", required=false)char boardType, Model model) throws Exception{
		System.out.println("=============");
		
		Customer customer = customerService.getLiveGuide(boardType);
		
		System.out.println(boardType);
		
		model.addAttribute("customer", customer);
		
		System.out.println(customer);
		
		String url = null;
		if(boardType == 'L') {
		 url = "customer/liveGuideDetail";
		}
		return url;
	}
	

	
	@PostMapping("deleteBoard")
	public String deleteCustomerBoard(@ModelAttribute Customer customer, Model model, RedirectAttributes rttr) throws Exception {
		
		System.out.println("=========delete===========");
		
		customerService.deleteCustomerBoard(customer.getBoardNumber());
		
		System.out.println(customer.getBoardNumber());
		
		rttr.addFlashAttribute("result", "delete success");
	
		
		return "redirect:/customer/listBoard?boardType="+customer.getBoardType();
			
		
	}		
	
	@ResponseBody
	@PostMapping("deleteGuide")
	public int deleteLiveGuide(@ModelAttribute Customer customer, 
								@RequestParam(value = "chbox[]") List<String> chArr, Model model) throws Exception {
		
			
			int result = 0;
			int bno = 0;
			
			for(String i : chArr) {
				bno = Integer.parseInt(i);
				customer.setBoardNumber(bno);
				customerService.deleteCustomerBoard(customer.getBoardNumber());
			}
			result = 1;
			
			return 1;
		
	}
		
}	



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

//}
