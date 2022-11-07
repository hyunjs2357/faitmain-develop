package com.faitmain.domain.user.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.faitmain.domain.live.service.LiveService;
import com.faitmain.domain.order.domain.Order;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.service.ProductService;
import com.faitmain.domain.user.domain.StoreApplicationDocument;
import com.faitmain.domain.user.domain.User;
import com.faitmain.domain.user.service.ApiService;
import com.faitmain.global.util.security.SecurityUserService;
import com.faitmain.domain.user.service.UserSerivce;
import com.faitmain.global.common.MiniProjectPage;
import com.faitmain.global.common.Page;
import com.faitmain.global.common.Paging;
import com.faitmain.global.common.Search;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

@Slf4j
@Controller
@RequestMapping("/user/*")
public class UserController {

	// Field
	@Autowired
	@Qualifier("userServiceImpl")
	private UserSerivce userSerivce;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("liveServiceImpl")
	private LiveService liveService;

	@Autowired
	@Qualifier("apiServiceImpl")
	private ApiService apiServiceImpl;

// 보안을 위해 사용함 
	@Autowired
	private PasswordEncoder pwdEncoder;

	@Autowired
	private SecurityUserService securityUserService;

	public UserController() {
		// log.info( "Controller = {} " , this.getClass() );
	}

	// Authentication 인증 , 접근 주체(Principal) , 권한(Authorization) ,인가(Authorize)
	// 1번
	// 3 Authentication 인터페이스에서 getPrincipal 해서 securityUser에 담기
	
	@GetMapping("test")
	public String test(Authentication authentication, Model model) {
		System.out.println("====test======");

		// Authentication 클래스 안에 getPrincipal 를 사용해서 그 안에 있는 SecurityUserService 를
		// 가져옵니다.
		// SecurityUserService 안에 각종 인증 정보가 들어 있음

		SecurityUserService securityUserService = (SecurityUserService) authentication.getPrincipal();

		model.addAttribute("user", securityUserService.getUser());
		return "/user/test";

	}

 

	// Context Holder 에 들어가서 인증 정보를 가져옴 , User 정보에 접근
	// SecurityContextHolder를 통해 가져오는 방법
	@GetMapping("/getMyInfo1")

	public String getMyInfo(Model model) {
		System.out.println("====getMyInfo1=시작=====");

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = securityUserService.getUser();
		model.addAttribute("user", user);
		return "/user/test";

	}


	// 권한이 맞지 않을때 감
	@GetMapping("accessDenied")
	public String accessDenied() {
		log.info("  accessDenied:: 페이지 ::: 권한이 맞지 않아서 접근 할 수 없습니다.  ");
		return "/user/accessDenied";
	}




	@GetMapping("login")
	public String longin() {
		log.info(" 컨트롤러 탐 login Page로 이동");
		return "/user/login";
	}

	// 이제 스프링 시큐리티가 로그인 가져감
	@PostMapping("login")
	public String longin(User loginuser, HttpSession session, Model model) throws Exception {

		log.info("LostController 탔어용 login Page 도착");
		log.info("받은 유저 user 출력  :: {}", loginuser);
//		   
		String encPwd = pwdEncoder.encode(loginuser.getPassword());
		loginuser.setPassword(encPwd);
		log.info("암호화한 user PW :: {}", loginuser.getPassword());
//		   

		String result = "";
		result = userSerivce.getLogin(loginuser) + ""; // id/ pw 값 있으면 1 없으면 0 ,,
		log.info("받은 유저 result 출력  :: {}", result);

		if (result.equals("1")) { // 1 이면 로그인 된거임
			User user = userSerivce.getUser(loginuser.getId()); // 로그인 된 사람 정보 가져와서 회월탈퇴 값 있는지 검증

			System.out.println("너의 값은 무엇이냐" + user.getWithdrawalStatus());
			if (user.getWithdrawalStatus()) { // true 는 회원 탈퇴
				result = "withdraw"; //

				return result;

			}

			log.info("{}의 로그인이 완료 되었습니다  ", user.getId());
			session.setAttribute("user", user); // user 정보 로그인

			Map<String, Object> map = new HashMap<>();

			map.put("orderName", "product_name DESC");
			map.put("startRowNum", 1);
			map.put("endRowNum", 5);

			map = productService.getProductList(map);
			log.info("after getProductList");

			System.out.println(map);

			map.put("liveList", liveService.getLiveList().get("liveList"));
			System.out.println(map);

			log.info("after getLiveList");

			model.addAttribute("map", map);

			return "/";

		} else {
			log.info("로그인 실패");

		}

		return "/user/login";
	}

	
	
	
	@GetMapping("logout")
	public RedirectView logout(HttpSession session, HttpServletRequest request) {

		log.info("get :: logout    ");
		log.info("  {} 의 로그아웃  ", ((User) request.getSession(true).getAttribute("user")).getId());

		session.invalidate();

		return new RedirectView("/");
	}
	


	@GetMapping("addUser")
	public String addUser() {

		log.info("get :: addUser ");

		return "/user/addUser";
	}
	@PostMapping("addUser")
	public RedirectView addUser(@ModelAttribute("user") User user) throws Exception {

		log.info("addUser::들어온 user 결과  ::{}", user);

//        String encPwd = pwdEncoder.encode( user.getPassword() );
//        user.setPassword( encPwd );
		user.setRole("user");

		String encPwd = pwdEncoder.encode(user.getPassword());
		user.setPassword(encPwd);
		log.info("addUser::비밀번호 바꾼후 결과  ::{}", user);

		int result = userSerivce.addUser(user);

		log.info("restut {} = 1일때 회원가입 완료", result);

		return new RedirectView("/");
	}
	
	@GetMapping("addStore")
	public String addStore() {

		log.info("get :: addStore ");

		return "/user/addStore";
	}



 
	@PostMapping("addStore")
	public RedirectView addStore(@ModelAttribute("user") User user, MultipartHttpServletRequest mRequest,
			@ModelAttribute("storeApplicationDocument") StoreApplicationDocument storeApplicatDoc) throws Exception {
		log.info("::시작 :::addStore:: ");
		String encPwd = pwdEncoder.encode(user.getPassword()); // PW 암호화
		user.setPassword(encPwd);
		log.info("addStore::들어온 user 결과  ::{}", user);
		log.info("addStore::들어온 storeApplicationDocument 결과  ::{}", storeApplicatDoc);

		int addStoreresult = userSerivce.addStore(user, mRequest);
		log.info("addStoreresult {}", addStoreresult);

		int storeApplicatDocresult = userSerivce.AddStoreApplicationDocument(storeApplicatDoc);
		log.info("addStoreresult {}", storeApplicatDocresult);
//		   
//	
		log.info("::끝 :::addStore:: ");

		return new RedirectView("/");
	}	
	
	// 스토어 재 신청 Add
		@GetMapping("addStoreApplication")
		public String addStoreApplicationDocument() {
			log.info(" start !!  addStoreApplicationDocument ");

			return "/user/addStoreApplicationDocument";
		}

		// 스토어 재 신청 Add
		@PostMapping("addStoreApplicationDocument")
		public String addStoreApplicationDocument(Model model,
				@ModelAttribute("storeApplicationDocument") StoreApplicationDocument storeApplicationDocument)
				throws Exception {
			log.info(" addStoreApplicationDocument 에 옴 출력하라   {}", storeApplicationDocument);

			int result = userSerivce.AddStoreApplicationDocument(storeApplicationDocument);
			log.info("결과 에  출력하라   {}", result);

			if (result == 1) {

				int addStoreApplicationNumber = userSerivce
						.getStoreApplicationDocumenNumber(storeApplicationDocument.getId());
				storeApplicationDocument.setStoreApplicationDocumentNumber(addStoreApplicationNumber);
				storeApplicationDocument.setExaminationStatus("W"); // 대기
				
				
				log.info("StoreApplicationDocument {}", storeApplicationDocument);

			}
			
			
			
			
			
			securityUserService = (SecurityUserService) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal(); // principal 에 사용자 인증 정보 담음
			User guser = securityUserService.getUser();
			int storeNumber = userSerivce.getStoreApplicationDocumenNumber(guser.getId());
			guser.setStoreApplicationDocumentNumber(storeNumber);
			
			
		 
			
			log.info(" 출력하라 getUSer {}", guser);

			model.addAttribute("getuser", guser);		
			
			

 
			return "/user/getUser";
		}

	 
		
	// userList 유정 리스트 뽑기 
	@GetMapping("getUserlist")
	public String getUserList(Model model, Paging paging) throws Exception {

		
		log.info("getUserlist  도착 !! ");

		log.info("paging {}: ", paging);
		
		if (paging.getKeyword() == null) {
			paging.setKeyword("");
			log.info("paging {}", paging.getKeyword());

		} else if (paging.getKeyword().equals("ALL")) {
			paging.setKeyword("");

		} else {

			paging.setKeyword(paging.getKeyword());
			log.info("paging {}", paging.getKeyword());

		}

		
		List<User> list = userSerivce.getUserList(paging);
	
		log.info("getStoreApplicationDocumentList = {}", list);
		if (!list.isEmpty()) {
			model.addAttribute("userList", list);

			log.info("list! ={}", list);

			model.addAttribute("pageMaker", new Page(paging, userSerivce.getUserListTotal(paging)));
			log.info("pageMaker   pageMaker !! ={}", new Page(paging, userSerivce.getUserListTotal(paging)));

		} else {
			model.addAttribute("listCheck", "empty");
			model.addAttribute("pageMaker", new Page(paging, userSerivce.getUserListTotal(paging)));
			log.info("pageMaker   pageMaker !! ={}", new Page(paging, userSerivce.getUserListTotal(paging)));

		}
		log.info("model = {}", model);

		log.info("getUserlist   끝 !! ={}", list);
	
		
		
		
		
		return "/admin/getUserList";

	}
	
	

	// admin 페이지 리스트 보이기 , 스토어 신청서 리스트
	@GetMapping("getStoreApplicationDocumentList")
	public String getStoreApplicationDocumentList(Model model, Paging paging) throws Exception {

		log.info("getStoreApplicationDocumentList  도착 !! ");

		log.info("paging {}: ", paging);

		// getKeyword 없으면 ,
		if (paging.getKeyword() == null) {
			paging.setKeyword("");
			log.info("paging {}", paging.getKeyword());

		} else if (paging.getKeyword().equals("ALL")) {
			paging.setKeyword("");

		} else {

			paging.setKeyword(paging.getKeyword());
			log.info("paging {}", paging.getKeyword());

		}
		// Map<String, Object> map =
		// userSerivce.getStoreApplicationDocumentList(searchMap);

		List<StoreApplicationDocument> list = userSerivce.getStoreApplicationDocumentList(paging);

		log.info("getStoreApplicationDocumentList = {}", list);
		if (!list.isEmpty()) {
			model.addAttribute("list", list);

			log.info("list! ={}", list);

			model.addAttribute("pageMaker", new Page(paging, userSerivce.getStoreApplicationDocumentListTotal(paging)));
			log.info("pageMaker   pageMaker !! ={}", new Page(paging, userSerivce.getStoreApplicationDocumentListTotal(paging)));

		} else {
			model.addAttribute("listCheck", "empty");
			model.addAttribute("pageMaker", new Page(paging, userSerivce.getStoreApplicationDocumentListTotal(paging)));
			log.info("pageMaker   pageMaker !! ={}", new Page(paging, userSerivce.getStoreApplicationDocumentListTotal(paging)));

		}
		log.info("model = {}", model);

		log.info("getStoreApplicationDocumentList   끝 !! ={}", list);

 

		return "/admin/getStoreApplicationDocumentList";

	}
	// 스토어 신청서 상세 보기
	@GetMapping("getStoreApplicationDocument")
	public String getStoreApplicationDocument(Model model,
			@RequestParam("storeApplicationDocumentNumber") int storeApplicationDocumentNumber) throws Exception {
		log.info(" getStoreApplicationDocument 에 옴 출력하라   {}", storeApplicationDocumentNumber);

		StoreApplicationDocument storeApplicationDocument = userSerivce
				.getStoreApplicationDocument(storeApplicationDocumentNumber);
		log.info("StoreApplicationDocument {}", storeApplicationDocument);

		model.addAttribute("StoreApplicationDocument", storeApplicationDocument);

		return "/user/getStoreApplicationDocument";
	}
	
	// 유저 상세 정보
	// //id가 있으면 list에서 온거 , 아니면 내 정보 조회에서 온 것
//getUser 유저 상세    // 변자기돈 getUSer시 아이디 날라가기 
	@GetMapping("getUser")
	public String getUser(Model model, @RequestParam(value = "id", required = false) String id
	// , @AuthenticationPrincipal SecurityUserService securityUserService
	) throws Exception {

		log.info(" GestUSer에 옴 출력하라 id  {}", id);
		User user = null;

		if (id == null) {

			securityUserService = (SecurityUserService) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal(); // principal 에 사용자 인증 정보 담음
			user = securityUserService.getUser();

			log.info(" 로그인한 유저   ={} ", securityUserService.getUser());

		} else {
			user = userSerivce.getUser(id);

		}

		if (user.getRole().equals("store") || user.getRole().equals("storeX")) {
			// 롤이 스토어면 스토어 넘버 가져와서 user에 넣어라
			int storeNumber = userSerivce.getStoreApplicationDocumenNumber(user.getId());
			user.setStoreApplicationDocumentNumber(storeNumber);
		}

		log.info(" 출력하라 getUSer {}", user);

		model.addAttribute("getuser", user);
		return "/user/getUser";
	}

//로그인 ajax로 바꾸면서 RestController 탐	   
	/*
	 * @PostMapping( "login" ) public RedirectView longin( RedirectAttributes model
	 * , @ModelAttribute("user") User loginuser, HttpSession session) throws
	 * Exception {
	 * 
	 * log.info("Post login Page 도착"); log.info("user 출력  :: {}" , loginuser);
	 * 
	 * int result = 0; result = userSerivce.getLogin(loginuser) ; // id/ pw 값 있으면 1
	 * 없으면 0 ,,
	 * 
	 * if(result == 1) { User user = userSerivce.getUser(loginuser.getId()) ;
	 * log.info("{}의 로그인이 완료 되었습니다  " , user.getId()); session.setAttribute("user",
	 * user) ; // user 정보 u로그인 }else { log.info("로그인 실패");
	 * 
	 * } log.info("Post login 끝 ");
	 * 
	 * Map<String, Object> map = new HashMap<String, Object>();
	 * 
	 * map.put("orderName", "product_name DESC"); map.put("startRowNum", 1);
	 * map.put("endRowNum", 5);
	 * 
	 * map = productService.getProductList(map); log.info("after getProductList");
	 * 
	 * map.put("liveList", liveService.getLiveList().get("liveList"));
	 * log.info("after getLiveList");
	 * 
	 * model.addFlashAttribute("map", map);
	 * 
	 * return new RedirectView("/"); }
	 * 
	 */


	// find Id  
	@GetMapping("findId")
	public String findId(@ModelAttribute("user") User user) {

		log.info("###Stat###findId ={} ##", user);

		return ("/user/findUserId");

	}

//    
//    // find Id  
	@GetMapping("findPassword")
	public String findPassword() {

		log.info("###Stat###findUserPassword ={} ##");

		return ("/user/findUserPassword");

	}
	// 스토어 재 신청 Add
	@GetMapping("deleteUser")
	public String deleteUser() throws Exception {
		System.out.println("GET deleteUser");

		return "/user/deleteUser";
	}



	

/* rest로감
	@PostMapping("deleteUser")
	public RedirectView deleteUser(@ModelAttribute("user") User user,
			@AuthenticationPrincipal SecurityUserService securityUserService) throws Exception {
		System.out.println("POST withdrawUser");

		log.info("::POST withdrawUserwithdrawUser ={}", user.getId());
		int result = userSerivce.deleteUser(user.getId());
		SecurityContextHolder.clearContext();
		System.out.println("컨텍스트 홀더 날림");

		return new RedirectView("/");
	}
*/
	

	// UpdatePassword
	@GetMapping("updatePassword")
	public String updatePassword(@RequestParam(value = "id", required = false) String id, Model model) {
		log.info("##updatePassword {} ##");

		if (id == null) {
			SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
					.getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
			id = securityUserService.getUser().getId();

		}
		log.info("updatePassword id :: {}  " + id);

		model.addAttribute("id", id);
		return ("/user/updatePassword");

	}

	
	
	@PostMapping(value = "updateUser")
	public String updateUser(Model model, User user, MultipartHttpServletRequest mRequest,
			@AuthenticationPrincipal SecurityUserService securityUserService) throws Exception {

		log.info(":::storeLogoFileName ={}", user);
//		 
// 		MultipartFile storeLogo =mRequest.getFile("LogoImage");
//		log.info(":::storeLogoFileName ={}", storeLogo);
//   	log.info("ajax Updtae에 옴  user 값은 = {}" , user) ;
		// 아직 checkDuplication 없음
		int result = 0;
		// log.info("updateUser :: user 출력 {} " , user );
		result = userSerivce.updateUser(user);

		log.info("updateUser :: result 출력  = {} ", result);
		user = userSerivce.getUser(user.getId());

 

		if (user.getRole().equals("store") || user.getRole().equals("storeX")) {
			// 롤이 스토어면 스토어 넘버 가져와서 user에 넣어라
			int storeNumber = userSerivce.getStoreApplicationDocumenNumber(user.getId());
			user.setStoreApplicationDocumentNumber(storeNumber);
		}

		///////////////// 날리기전 //////////////////////
		System.out.println("  #Authentication : " + SecurityContextHolder.getContext().getAuthentication());

		/////////////////// SecurityContextHolder 날림 ///////////////////////////////////
		SecurityContextHolder.clearContext();

		SecurityUserService securityUser = new SecurityUserService(user);
		System.out.println("  #updateUserDetails : " + securityUser);

		/////////////////// SecurityContextHolder 세팅 ///////////////////////////////////

		Authentication authRequest = new UsernamePasswordAuthenticationToken(securityUser, null,
				securityUser.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authRequest);

		System.out.println("  #updateUserDetails : " + authRequest);

		log.info(" 출력하라 getUSer {}", user);

		model.addAttribute("getuser", user);
		return "/user/getUser";
	}


	@GetMapping("kakaoLogin")
 
	public String kakaoLogin(@RequestParam(value = "code", required = false) String code, Model model,
			HttpSession session) throws Exception {

		// 사용자 로그인 및 동의 후 , 인가 코드를 발급받아 302 redirect를 통해 , 이 메소드 도착함

		log.info("##kakaoLogin## 페이지 도착 ");

		log.info("##code {} ##", code); // 코드 출력

		// 사용자 정보를 가져오기 위하여 code로 access 토큰 가져오기 !
		String access_Token = apiServiceImpl.getKaKaoAccessToken(code);
		log.info("##access_Token 가져옴 ::  {} ##", access_Token);

		// 카카오 getUserInfo 에서 access_Token를 통하여 userInfo 가져오기
		Map<String, Object> userInfo = apiServiceImpl.getKakoUserInfo(access_Token);
		log.info("##access_Token {} ##", access_Token);
		log.info("##email {} ##", userInfo.get("email"));

		String kakaouserId = (String) userInfo.get("email");
		User user = new User();
		user.setId(kakaouserId);
		user.setPassword("12345"); // 카카오 로그인시 비밀번호
		if (userSerivce.getLogin(user) == 0) { // 카카로 로그인 ID가 우리 사이트에 존재 x
			log.info("로그인한 카카오 아이디가 존재 하지 않습니다. ");

			if (userSerivce.getUser(user.getId()) != null) {
				log.info("이미 가입된 아이디가 있습니다. ");

			}

			model.addAttribute("kakaouserId", kakaouserId);

			return ("/user/kakaoAdd");

		} else {
			// 카카오 로그인시 ID가 우리 사이트에 존재 할때

			// Model 과 View 연결
			user = userSerivce.getUser(user.getId());
			session.setAttribute("user", user);

		} // 존재 할때

		Map<String, Object> map = new HashMap<>();

		map.put("orderName", "product_name DESC");
		map.put("startRowNum", 1);
		map.put("endRowNum", 5);

		map = productService.getProductList(map);
		log.info("after getProductList");

		System.out.println(map);

		map.put("liveList", liveService.getLiveList().get("liveList"));
		System.out.println(map);

		log.info("after getLiveList");

		model.addAttribute("map", map);

		// return new RedirectView("/");
		return "/";

	}
/*
	// RedirectView longin( RedirectAttributes model ,
	// kakao회원 추가 가입
	
	@PostMapping("addSnsUser")
	public RedirectView addSnsUser(@RequestParam(value = "code", required = false) String code,
			RedirectAttributes model, @ModelAttribute("user") User kuser, HttpSession session) throws Exception {
//        log.info( "##code {} ##" , code );
//        log.info( "##kuser {} ##" , kuser );
//
//        kuser.setRole( "user" );
//
//        kuser.setPassword( "12345" ); //  패스워드 고정
//        kuser.setJoinPath( "KAKAO" ); // 카카오는 KAKAO로 고정 , 자사는 HOME 로 고정, 네이버 NAVER

		log.info("##kuser {}  sns USER 추가 창에 왔습니다. ##");
		log.info("##kuser {}  sns USER 추가 창에 왔습니다. ##", kuser);

		int result = userSerivce.addUser(kuser);
		log.info("##kakaoUser 결과  {} ##", result);

		kuser = userSerivce.getUser(kuser.getId());
		log.info("##kuser {}  getUSer 함  ##", kuser);

		if (result == 1) {

			SecurityUserService securityUserService = new SecurityUserService(kuser); // 디테일 섭스를 다시 만들어서 주입

			Authentication authentication = new UsernamePasswordAuthenticationToken(securityUserService, null,
					securityUserService.getAuthorities());
			SecurityContext securityContext = SecurityContextHolder.getContext(); // SecurityContextHolder 안에 있는 컨텍스트에
																					// 접근

			log.info("##authentication  ##", authentication);

		}

		log.info("회원가입이 완료 되었습니다. ");

		Map<String, Object> map = new HashMap<>();

		map.put("orderName", "product_name DESC");
		map.put("startRowNum", 1);
		map.put("endRowNum", 5);

		map = productService.getProductList(map);
		log.info("after getProductList");

		map.put("liveList", liveService.getLiveList().get("liveList"));
		log.info("after getLiveList");

		model.addFlashAttribute("map", map);

		return new RedirectView("/");

	}

 */


}