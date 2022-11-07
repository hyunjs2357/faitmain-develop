package com.faitmain.domain.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.faitmain.domain.live.domain.Live;
import com.faitmain.domain.live.domain.LiveProduct;
import com.faitmain.domain.live.domain.LiveReservation;
import com.faitmain.domain.live.service.LiveService;
import com.faitmain.domain.product.service.ProductService;
import com.faitmain.domain.user.domain.User;
import com.faitmain.domain.user.service.UserSerivce;
import com.faitmain.global.util.security.SecurityUserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class WebController {

	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;

	@Autowired
	@Qualifier("liveServiceImpl")
	private LiveService liveService;
	
	@Autowired
	@Qualifier("userServiceImpl")
	private UserSerivce userService;
	
	@GetMapping("/")
	public String main(Model model) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("orderName", "reg_date DESC");
		map.put("startRowNum", 1);
		map.put("endRowNum", 16);

		map = productService.getProductList(map);
		
		
		List<Live> list = (List<Live>) liveService.getLiveList().get("liveList");	
		
		List<Live> userList = new ArrayList<Live>();
		List<User> storeList = new ArrayList<User>();
	
		for(Live lol : list) {
			Live live = lol;
			lol.setLiveWatcher(getLiveUserList(lol.getRoomId()).size()-1);
			userList.add(lol);
			User storeInfo = userService.getUser(lol.getStoreId());
			storeList.add(storeInfo);
		}		
		
		map.put("liveList", userList);
		map.put("store", storeList);


		LiveReservation liveReservation = liveService.getCurrentLiveReservation();

		Live live = null;

		if (liveReservation != null) {
			List<LiveProduct> liveProduct = liveService.getLiveProductList(liveReservation.getLiveReservationNumber());
			live = liveService.getLiveByStoreId(liveReservation.getStore().getId());
			liveReservation.setLiveProduct(liveProduct);
		}
		

		model.addAttribute("map", map);
		model.addAttribute("liveReservation", liveReservation);
		model.addAttribute("live", live);

		System.out.println(model);
		return "index";
	}
	
	@GetMapping("/myPage")
	public String getMyPage() {
		
		SecurityUserService securityUserService = ( SecurityUserService ) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();
		
		if((user.getRole()).equals("admin")) {
			
			return "redirect:/user/getStoreApplicationDocumentList?keyword=ALL";
			
		}else if((user.getRole()).equals("user")) {
			
			return "redirect:/user/getUser?id=" + user.getId();
			
		}else if((user.getRole()).equals("storeX")){
			
			return "redirect:/user/getUser";
			
		}else {
			return "redirect:/product/getProductList?resultJsp=listProductStore";
		}

//		return "myPage";
	}
	
	public JSONArray getLiveUserList(String roomId) throws Exception {

		log.info("Controller = {} ", "/live/getLiveUserList : GET start...");

		log.info("getLiveUserList = {} ", this.getClass());

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
				"https://vchatcloud.com/openapi/v1/users/" + roomId);

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

		log.info("Controller = {} ", "/live/getLiveUserList : GET end...");

		return data;
	}
	
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

}
