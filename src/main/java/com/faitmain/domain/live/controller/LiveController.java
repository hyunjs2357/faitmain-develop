package com.faitmain.domain.live.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.faitmain.domain.live.domain.Live;
import com.faitmain.domain.live.domain.LiveProduct;
import com.faitmain.domain.live.domain.LiveReservation;
import com.faitmain.domain.live.domain.LiveUserStatus;
import com.faitmain.domain.live.service.LiveService;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.service.ProductService;
import com.faitmain.domain.user.domain.User;
import com.faitmain.domain.user.service.UserSerivce;
import com.faitmain.global.util.security.SecurityUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/live/")
public class LiveController {

	// Field
	@Autowired
	@Qualifier("liveServiceImpl")
	private LiveService liveService;

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("userServiceImpl")
	private UserSerivce userService;

	@GetMapping("liveRoom")
	public String getLiveRoomList(Model model) throws Exception {

		String token = getToken();

		System.out.println("/live/getLiveRoomList : GET start...");
		log.info("Controller = {} ", "/live/liveRoomList : GET start...");

		log.info("getRoomsList = {} ", this.getClass());

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/v1/rooms");

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setRequestMethod("GET");

		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
		conn.setRequestProperty("X-AUTH-TOKEN", token);
		conn.setDoOutput(true);

		// 데이터 입력 스트림에 답기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();

		result = (JSONObject) new JSONParser().parse(sb.toString());

		// REST API 호출 상태 출력하기
		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

		// JSON데이터에서 "data"라는 JSONObject를 가져온다.
		JSONArray data = (JSONArray) result.get("list");
		JSONObject tmp;
		for (int i = 0; i < data.size(); i++) {
			tmp = (JSONObject) data.get(i);
			System.out.println("data[" + i + "] : " + tmp);
		}
		System.out.println("data : " + data);
		model.addAttribute("json", data);

		log.info("Controller = {} ", "/live/liveRoomList : GET end...");

		return "/live/liveList";
	}

	// 토큰 발급 메서드
	public String getToken() throws Exception {

		log.info("getToken Method start...");

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/token");

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());

		conn.setRequestMethod("GET");
		conn.setRequestProperty("API_KEY", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");

		// 데이터 입력 스트림에 담기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();

		log.info("data from v.chatServer : {}", br);

		result = (JSONObject) new JSONParser().parse(sb.toString());

		// REST API 호출 상태 출력하기
		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

		// JSON데이터에서 "data"라는 JSONObject를 가져온다.
		JSONObject data = (JSONObject) result.get("data");
		String dataa = (String) data.get("X-AUTH-TOKEN");
		long Code = (long) result.get("result_cd");

		log.info("X-AUTH-TOKEN : {}", dataa);
		log.info("result_cd : {}", Code);
		log.info("getToken Method end...");

		return dataa;
	}

	// 방송 시작
	@PostMapping("create")
	public String createRoom(HttpServletRequest req, @RequestParam("roomName") String liveTitle, Model model)
			throws Exception {

		log.info("createRoom = {} ", this.getClass());

		String token = getToken();

		// User user = (User) session.getAttribute("user");

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();

		Live validation = liveService.getLiveByStoreId(user.getId());

		String[] liveProducts = req.getParameterValues("liveProduct");

		System.out.println(liveTitle);

		for (String product : liveProducts) {
			log.info("liveProduct = {}", product);
		}

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		validation = liveService.getLiveByStoreId(user.getId());

		if (validation == null) {

			TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
				public java.security.cert.X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}

				public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
				}
			} };

			SSLContext sc = SSLContext.getInstance("TLSv1.2");
			sc.init(null, trustCerts, new java.security.SecureRandom());

			URL url = new URL("https://vchatcloud.com/openapi/v1/rooms");

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod("POST");

			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");

			conn.setRequestProperty("X-AUTH-TOKEN", token);
			conn.setDoOutput(true);

			String Data = "roomName=" + liveTitle + "&maxUser=5&webrtc=91";

			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(Data);
			wr.flush();

			// 데이터 입력 스트림에 담기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while (br.ready()) {
				sb.append(br.readLine());
			}
			conn.disconnect();

			try {
				Thread.sleep(2000);
				System.out.println("타이머 1 초 대기.");
			} catch (InterruptedException e) {
				System.out.println("타이머 끝");
			}

			result = (JSONObject) new JSONParser().parse(sb.toString());

			// REST API 호출 상태 출력하기
			StringBuilder out = new StringBuilder();
			out.append(result.get("status") + " : " + result.get("status_message") + "\n");
			log.info("status / status_message : {}", out);

			// JSON데이터에서 "data"라는 JSONObject를 가져온다.
			JSONObject data = (JSONObject) result.get("data");
			String roomId = (String) data.get("roomId");
			long Code = (long) result.get("result_cd");

			log.info("roomId = {}", roomId);
			log.info("result_cd = {}", Code);

			if (Code != 1) {
				log.info("에러 발생! result_cd : {}", Code);

			} else {

				// 라이브 방송 등록 후 DB에 데이터 입력
				// 라이브

				Live live = new Live();

				live.setRoomId(roomId);
				live.setStoreId(user.getId());
				live.setLiveTitle(liveTitle);
				live.setLiveIntro(liveTitle);
				live.setLiveImage("라이브 대표사진.png");

				liveService.addLive(live);

				model.addAttribute("Live", live);

				System.out.println("라이브 방송 정보 : "
						+ liveService.getLive(liveService.getLiveByStoreId(user.getId()).getLiveNumber()));

				// 라이브 판매 상품
				live = liveService.getLiveByStoreId(user.getId());

				LiveProduct liveProduct = new LiveProduct();

				for (String product : liveProducts) {
					liveProduct.setLiveNumber(live.getLiveNumber());
					liveProduct.setLiveReservationNumber(0);
					liveProduct.setProductNumber(Integer.parseInt(product));
					liveProduct.setProductMainImage(
							productService.getLiveProduct(Integer.parseInt(product)).getProductMainImage());
					liveProduct
							.setProductName(productService.getLiveProduct(Integer.parseInt(product)).getProductName());
					liveProduct.setProductDetail(
							productService.getLiveProduct(Integer.parseInt(product)).getProductDetail());
					liveProduct.setPrice(productService.getLiveProduct(Integer.parseInt(product)).getProductPrice());

					liveService.addLiveProduct(liveProduct);
				}
			}

		} else {

			log.info("room already exist");

			editRoom(req, liveTitle, token, model);
		}
		List<LiveProduct> list = liveService
				.getLiveProductListByLiveNumber(liveService.getLiveByStoreId(user.getId()).getLiveNumber());
		model.addAttribute("listProduct", list);

		String roomId = liveService.getLiveByStoreId(user.getId()).getRoomId();

		log.info("채널키 파라미터 체크 {} : ", roomId);

		model.addAttribute("channelKey", roomId);

		log.info("model status : " + model);

		return "/live/live";

	}

	// 방송 정보 수정

	public String editRoom(HttpServletRequest req, String liveTitle, String token, Model model)

			throws Exception {

		log.info("editRoom = {} ", this.getClass());
		System.out.println("방송 정보 수정");

		// User user = (User) session.getAttribute("user");

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();

		Live live = liveService.getLiveByStoreId(user.getId());

		String[] liveProducts = req.getParameterValues("liveProduct");

		System.out.println(liveTitle);

		for (String product : liveProducts) {
			log.info("product : {}", product);
		}

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/v1/rooms/" + live.getRoomId());

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());

		conn.setRequestMethod("POST");

		conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
		conn.setRequestProperty("X-AUTH-TOKEN", token);
		conn.setDoOutput(true);

		String Data = "maxUser=5&roomName=" + liveTitle + "&roomStatus=A&webrtc=91";

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(Data);
		wr.flush();

		// 데이터 입력 스트림에 담기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();
		System.out.println("br" + br);
		System.out.println("wr" + wr);
		result = (JSONObject) new JSONParser().parse(sb.toString());

		// REST API 호출 상태 출력하기
		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

		// JSON데이터에서 "data"라는 JSONObject를 가져온다.
		Long data = (Long) result.get("result_cd");
		System.out.println(data);

		if (data == 1) {

			// 라이브 방송 등록 후 DB에 데이터 입력
			// 라이브

			live.setLiveTitle(liveTitle);
			live.setLiveIntro(liveTitle);
			live.setLiveStatus(true);

			liveService.updateLive(live);

			model.addAttribute("live", live);

			System.out.println(
					"라이브 방송 정보 : " + liveService.getLive(liveService.getLiveByStoreId(user.getId()).getLiveNumber()));

			liveService.deleteLiveProduct(live.getLiveNumber());

			LiveProduct liveProduct = new LiveProduct();

			for (String product : liveProducts) {
				liveProduct.setLiveNumber(live.getLiveNumber());
				liveProduct.setLiveReservationNumber(0);
				liveProduct.setProductNumber(Integer.parseInt(product));
				liveProduct.setProductMainImage(
						productService.getLiveProduct(Integer.parseInt(product)).getProductMainImage());
				liveProduct.setProductName(productService.getLiveProduct(Integer.parseInt(product)).getProductName());
				liveProduct
						.setProductDetail(productService.getLiveProduct(Integer.parseInt(product)).getProductDetail());
				liveProduct.setPrice(productService.getLiveProduct(Integer.parseInt(product)).getProductPrice());

				liveService.addLiveProduct(liveProduct);
			}
		} else {
			System.out.println("오류남");
		}

		String roomId = liveService.getLiveByStoreId(user.getId()).getRoomId();

		log.info("채널키 파라미터 체크 = {}", roomId);

		model.addAttribute("channelKey", roomId);

		log.info("editRoom live = {}", live);

		liveService.sendSMS(live);

		return "live/live";

	}

	@GetMapping("addLiveView")
	public String addLiveView(Model model) throws Exception {

		System.out.println("/live/addLiveView : GET start...");
		log.info("Controller = {} ", "/live/addLiveView : GET start...");

		// User user = (User) session.getAttribute("user");

		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증
																									// 정보 담음
		SecurityUserService securityUserService = (SecurityUserService) principal;
		User user = (User) securityUserService.getUser();

		Map<String, Object> map = productService.getProductListByStoreId(user.getId());

		model.addAttribute("map", map);

		System.out.println(map);

		log.info("Controller = {} ", "/live/addLiveView : GET end...");

		return "live/addLiveView";
	}

	@GetMapping("addLive")
	public String addLive() throws Exception {

		System.out.println("/live/addLive : GET start...");
		log.info("Controller = {} ", "/live/addLive : GET start...");

		log.info("Controller = {} ", "/live/addLiveView : GET end...");

		return "/live/addLive";
	}

	@GetMapping("returnIndex")
	public String returnIndex() throws Exception {
		return "/live/returnIndex";
	}

	@GetMapping("watchLive/{liveNumber}")
	public String watchLive(Authentication authentication, Model model, @PathVariable int liveNumber) throws Exception {

		log.info("watchLive() : GET start...");

		Live live = liveService.getLive(liveNumber);

		List<LiveProduct> list = liveService.getLiveProductListByLiveNumber(live.getLiveNumber());

		System.out.println("찍먹 : " + list);
		model.addAttribute("live", live);
		model.addAttribute("listProduct", list);

		log.info("live = " + model.getAttribute("live"));
		log.info("listProduct = " + model.getAttribute("listProduct"));
		return "live/watchLive";
	}

	@GetMapping("addLiveUserStatus")
	public void addLiveUserStatus(@ModelAttribute("liveUsrStatus") LiveUserStatus liveUserStatus) throws Exception {

		System.out.println("/live/addSanctionUser : GET start...");

		liveService.addLiveUserStatus(liveUserStatus);

		System.out.println("/live/addSanctionUser : GET end...");

	}

	@GetMapping("getLiveReservationCal")
	public String getLiveReservationCal() {
		log.info("getLiveReservationCal() : GET start... ");

		// 단순 네비게이션

		log.info("getLiveReservationCal() : GET end... ");
		return "live/liveReservationCal";
	}

	@GetMapping("getLiveReservationList")
	public String getLiveReservationList(@RequestParam String date, Model model) throws Exception {
		log.info("getLiveReservationList() : GET start... ");

		log.info("date : " + date);

		List<LiveReservation> list = liveService.getLiveReservationList(date);
		log.info("list : {}", list);
		List<LiveReservation> resultList = new ArrayList<LiveReservation>();

		for (LiveReservation obj : list) {
			obj.setLiveProduct(liveService.getLiveProductList(obj.getLiveReservationNumber()));
			obj.setStore(userService.getUser(obj.getStore().getId()));

			resultList.add(obj);
		}

		// 결과 확인용 for문
		for (LiveReservation obj : resultList) {
			log.info("resultList : {}", obj);
		}

		model.addAttribute("date", date);
		model.addAttribute("list", resultList);

		log.info("getLiveReservationList() : GET end... ");
		return "/live/liveReservationList";
	}

	@GetMapping("liveManageTab")
	public String getLiveUserList(HttpServletRequest req, Model model) throws Exception {

		log.info("Controller = {} ", "/live/getLiveUserList : GET start...");

		log.info("getLiveUserList = {} ", this.getClass());

		// User user = (User) session.getAttribute("user");
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		SecurityUserService securityUserService = (SecurityUserService) principal;
		User user = (User) securityUserService.getUser();

		JSONObject result = null;
		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL(
				"https://vchatcloud.com/openapi/v1/users/" + liveService.getLiveByStoreId(user.getId()).getRoomId());

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setRequestMethod("GET");
		conn.setRequestProperty("accept", "*/*");
		conn.setRequestProperty("API_KEY", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
		conn.setRequestProperty("X-AUTH-TOKEN", getToken());
		conn.setDoOutput(true);

		// 데이터 입력 스트림에 답기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();

		result = (JSONObject) new JSONParser().parse(sb.toString());

		// REST API 호출 상태 출력하기
		StringBuilder out = new StringBuilder();
		out.append(result.get("status") + " : " + result.get("status_message") + "\n");

		// JSON데이터에서 "data"라는 JSONObject를 가져온다.
		JSONArray data = (JSONArray) result.get("list");
		System.out.println("옴뇸뇸" + data);
		JSONObject tmp;
		for (int i = 0; i < data.size(); i++) {
			tmp = (JSONObject) data.get(i);
			System.out.println("data[" + i + "] : " + tmp);
		}
		System.out.println("userList : " + data);

		model.addAttribute("userList", data);

		log.info("Controller = {} ", "/live/getLiveUserList : GET end...");

		return "/live/liveManageTab";
	}

	@GetMapping("liveStatusUpdate")
	public RedirectView liveStatusUpdate() throws Exception {

//		Live live = liveService.getLiveByStoreId(((User) session.getAttribute("user")).getId());

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		Live live = liveService.getLiveByStoreId(securityUserService.getUser().getId());

		log.info("live : {}", live);

		String token = getToken();
		log.info("token : {}", token);

		StringBuilder sb = new StringBuilder();

		TrustManager[] trustCerts = new TrustManager[] { new X509TrustManager() {
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
			}
		} };

		SSLContext sc = SSLContext.getInstance("TLSv1.2");
		sc.init(null, trustCerts, new java.security.SecureRandom());

		URL url = new URL("https://vchatcloud.com/openapi/v1/rooms/" + live.getRoomId());

		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setSSLSocketFactory(sc.getSocketFactory());
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("API_KEY", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
		conn.setRequestProperty("X-AUTH-TOKEN", token);
		conn.setDoOutput(true);

		// conn body에 데이터 담기
		String Data = "roomName=" + live.getLiveTitle() + "&roomType=화상&maxUser=5&roomStatus=s&rtcStat=Y&gTransStat=N";

		OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
		wr.write(Data);
		wr.flush();

		// 데이터 입력 스트림에 답기
		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		while (br.ready()) {
			sb.append(br.readLine());
		}
		conn.disconnect();

		live.setLiveStatus(false);

		liveService.updateLive(live);

		return new RedirectView("/");
	}

	@GetMapping("addLiveReservation")
	public RedirectView addLiveReservation(Model model) throws Exception {
		log.info("addLiveReservation GET : start...");

		// User user = (User) session.getAttribute("user");
		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		User user = (User) securityUserService.getUser();

		LiveReservation liveReservation = liveService.getLiveReservationByStoreId(user.getId());

		log.info("liveReservation = {}", liveReservation);

		if (liveReservation == null) { // 프리미엄 라이브 예약을 하지 않았을 경우

			log.info("addLiveReservation GET : end...");
			return new RedirectView("/live/addLiveReservationView");
		} else { // 프리미엄 라이브 예약을 했을 경우
			return new RedirectView("/live/getLiveReservationList?date=" + liveReservation.getReservationDate());
		}
	}

	@GetMapping("addLiveReservationView")
	public String addLiveReservationView(Model model) throws Exception {
//		User user = (User) session.getAttribute("user");
//		Map<String, Object> ProductList = productService.getProductListByStoreId(user.getId());
		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();

		Map<String, Object> ProductList = productService.getProductListByStoreId(securityUserService.getUser().getId());

		model.addAttribute("list", ProductList.get("list"));
		return "/live/addLiveReservationView";
	}

	@PostMapping("addLiveReservation")
	public RedirectView addLiveReservation(LiveReservation liveReservation, @RequestParam String[] liveProductNum,
			Model model) throws Exception {
		log.info("addLiveReservation POST : start...");

//		User user = (User) session.getAttribute("user");
//		liveReservation.setStore(user);

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		liveReservation.setStore(securityUserService.getUser());

		liveService.addLiveReservation(liveReservation);

		log.info("PK value = {}", liveReservation.getLiveReservationNumber());

		LiveProduct liveProduct = new LiveProduct();
		liveProduct.setLiveReservationNumber(liveReservation.getLiveReservationNumber());

		for (int i = 0; i < liveProductNum.length; i++) {
			Product prod = productService.getLiveProduct(Integer.parseInt(liveProductNum[i]));
			liveProduct.setProductNumber(prod.getProductNumber());
			liveProduct.setProductName(prod.getProductName());
			liveProduct.setProductMainImage(prod.getProductMainImage());
			liveProduct.setProductDetail(prod.getProductDetail());
			liveProduct.setPrice(prod.getProductPrice());

			liveService.addLiveProduct(liveProduct);
		}

		log.info("addLiveReservation POST : end...");
		return new RedirectView("/live/getLiveReservationList?date=" + liveReservation.getReservationDate());
	}

	@GetMapping("updateLiveReservation/{liveProductNum}/{date}")
	public String updateLiveReservation(@PathVariable String liveProductNum, @PathVariable String date, Model model)
			throws Exception, Exception {
		log.info("updateLiveReservation GET start...");

		LiveReservation liveReservation = liveService.getLiveReservation(Integer.parseInt(liveProductNum));
		log.info("liveReservation = {}", liveReservation);

		List<LiveProduct> liveProductList = liveService
				.getLiveProductListByLiveNumber(liveReservation.getLiveReservationNumber());
		log.info("liveProductList = {}", liveProductList);

		Map<String, Object> productList = productService.getProductListByStoreId(liveReservation.getStore().getId());

		model.addAttribute("liveReservation", liveReservation);
		model.addAttribute("liveProductList", liveProductList);
		model.addAttribute("list", productList.get("list"));

		log.info("updateLiveReservation GET end...");
		return "/live/updateLiveReservationView";
	}

	@PostMapping("updateLiveReservation")
	public RedirectView updateLiveReservation(LiveReservation liveReservation, @RequestParam String[] liveProductNum,
			Model model) throws Exception {
		log.info("updateLiveReservation POST start...");

//		User user = (User) session.getAttribute("user");
//
//		liveReservation.setStore(user);

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		liveReservation.setStore(securityUserService.getUser());

		log.info("liveReservation = {}", liveReservation);

		liveService.updateLiveReservation(liveReservation);

		liveService.deleteLiveProductByReservationNumber(liveReservation.getLiveReservationNumber());

		LiveProduct liveProduct = new LiveProduct();
		liveProduct.setLiveReservationNumber(liveReservation.getLiveReservationNumber());

		for (int i = 0; i < liveProductNum.length; i++) {
			Product prod = productService.getLiveProduct(Integer.parseInt(liveProductNum[i]));
			liveProduct.setProductNumber(prod.getProductNumber());
			liveProduct.setProductName(prod.getProductName());
			liveProduct.setProductMainImage(prod.getProductMainImage());
			liveProduct.setProductDetail(prod.getProductDetail());
			liveProduct.setPrice(prod.getProductPrice());

			liveService.addLiveProduct(liveProduct);
		}

		log.info("PK value = {}", liveReservation.getLiveReservationNumber());

		log.info("updateLiveReservation POST end...");
		return new RedirectView("/live/getLiveReservationList?date=" + liveReservation.getReservationDate());
	}

	@GetMapping("deleteLiveReservation/{liveProductNum}/{date}")
	public RedirectView deleteLiveReservation(@PathVariable String liveProductNum, @PathVariable String date)
			throws Exception {
		log.info("deleteLiveReservation GET start...");

		liveService.deleteLiveReservation(Integer.parseInt(liveProductNum));

		liveService.deleteLiveProductByReservationNumber(Integer.parseInt(liveProductNum));
		// liveService.deleteLive
		log.info("deleteLiveReservation GET end...");
		return new RedirectView("/live/getLiveReservationList?date=" + date);
	}

	@GetMapping("getStoreAlarmList")
	public String getStoreAlarmList(Authentication authentication, Model model) throws Exception {
		log.info("getStoreAlarmList GET start...");

		SecurityUserService securityUser = (SecurityUserService) authentication.getPrincipal();
		User user = (User) securityUser.getUser();

		int liveNumver = (liveService.getLiveByStoreId(user.getId())).getLiveNumber();

		Map<String, Object> map = liveService.getStoreLiveUserStatusList(liveNumver);

		log.info("map = {}", map);

		model.addAttribute("map", map);

		log.info("getStoreAlarmList GET end...");
		return "/live/getStoreAlarmList";
	}

	@GetMapping("getUserAlarmList")
	public String getUserAlarmList(Authentication authentication, Model model) throws Exception {
		log.info("getUserAlarmList GET start...");

		SecurityUserService securityUser = (SecurityUserService) authentication.getPrincipal();
		User user = (User) securityUser.getUser();

		LiveUserStatus liveUserStatus = new LiveUserStatus();
		liveUserStatus.setId(user.getId());

		Map<String, Object> map = liveService.getUserLiveUserStatusList(liveUserStatus);
		List<User> storeList = new ArrayList<>();
		Live live = new Live();
		User store = new User();

		for (LiveUserStatus list : (List<LiveUserStatus>) map.get("list")) {
			live = liveService.getLive(list.getLiveNumber());
			store = userService.getUser(live.getStoreId());

			storeList.add(store);
		}

		map.put("storeList", storeList);

		log.info("map = {}", map);

		model.addAttribute("map", map);

		log.info("getUserAlarmList GET end...");
		return "/live/getUserAlarmList";
	}

	@PostMapping("updateAlarmList")
	public RedirectView updateAlarmList(@RequestParam List<String> liveNumber, Authentication authentication)
			throws Exception {
		log.info("updateAlarmList GET start...");

		log.info("List liveNumber = {}", liveNumber);

		SecurityUserService securityUser = (SecurityUserService) authentication.getPrincipal();
		User user = (User) securityUser.getUser();

		LiveUserStatus liveUserStatus = new LiveUserStatus();
		liveUserStatus.setId(user.getId());

		for (String str : liveNumber) {
			liveUserStatus.setLiveNumber(Integer.parseInt(str));
			liveUserStatus = liveService.getLiveUserStatus(liveUserStatus);
			liveUserStatus.setAlarmStatus(0);

			liveService.updateLiveUserStatus(liveUserStatus);
		}

		log.info("updateAlarmList GET end...");
		return new RedirectView("/live/getUserAlarmList");
	}

}