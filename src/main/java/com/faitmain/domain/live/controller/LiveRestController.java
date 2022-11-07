package com.faitmain.domain.live.controller;

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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faitmain.domain.live.domain.Live;
import com.faitmain.domain.live.domain.LiveChat;
import com.faitmain.domain.live.domain.LiveReservation;
import com.faitmain.domain.live.domain.LiveUserStatus;
import com.faitmain.domain.live.service.LiveService;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.util.security.SecurityUserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/live/*")
public class LiveRestController {

	// Field
	@Autowired
	@Qualifier("liveServiceImpl")
	private LiveService liveService;

	// METHOD
	// LIVE
	@GetMapping("json/getLiveList")
	public Map<String, Object> getLiveList() throws Exception {
		System.out.println("/live/json/getLiveList : GET start...");

		Map<String, Object> map = liveService.getLiveList();

		System.out.println("/live/json/getLiveList : GET end...");
		return map;
	}

	@PostMapping("json/liveManageTab")
	public JSONArray getLiveUserList(HttpServletRequest req, Model model) throws Exception {

		log.info("Controller = {} ", "/live/getLiveUserList : GET start...");

		log.info("getLiveUserList = {} ", this.getClass());

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
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

		JSONObject tmp = null;
		for (int i = 0; i < data.size(); i++) {
			tmp = (JSONObject) data.get(i);
			System.out.println("data[" + i + "] : " + tmp);
		}
		System.out.println("data : " + data);

		log.info("Controller = {} ", "/live/getLiveUserList : GET end...");

		return data;
	}

	@PostMapping("json/sanctionUserList")
	public JSONArray kickUserList(HttpServletRequest req, Model model) throws Exception {

		log.info("Controller = {} ", "/live/getLiveUserList : GET start...");

		log.info("getLiveUserList = {} ", this.getClass());

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
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
				"https://vchatcloud.com/openapi/v1/exiles/" + liveService.getLiveByStoreId(user.getId()).getRoomId());

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

		System.out.println("data : " + data);

		log.info("Controller = {} ", "/live/getLiveUserList : GET end...");

		return data;

	}

	@PostMapping("json/muteUserList")
	public JSONArray muteUserList(HttpServletRequest req, Model model) throws Exception {

		log.info("Controller = {} ", "/live/getLiveUserList : GET start...");

		log.info("getLiveUserList = {} ", this.getClass());

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
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
				"https://vchatcloud.com/openapi/v1/mute/" + liveService.getLiveByStoreId(user.getId()).getRoomId());

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

		JSONObject tmp = null;
		for (int i = 0; i < data.size(); i++) {
			tmp = (JSONObject) data.get(i);
			System.out.println("data[" + i + "] : " + tmp);
		}

		log.info("Controller = {} ", "/live/getLiveUserList : GET end...");

		return data;
	}

	@PostMapping("json/updateLive")
	public Live updateLive(@RequestBody Live live) throws Exception {
		System.out.println("/live/json/updateLive : POST start...");

		System.out.println("result = " + liveService.updateLive(live));

		System.out.println("/live/json/updateLive : POST end...");

		return live;
	}

	@PostMapping("json/sendMessage")
	public void sendMessage(@RequestBody LiveChat liveChat) throws Exception {
		System.out.println("/live/json/sendMessage : POST start...");

		System.out.println("result : " + liveService.addLiveChat(liveChat));

		System.out.println("/live/json/sendMessage : POST end...");
	}

	// LIVE RESERVATION
	@GetMapping("json/getLiveReservationCal")
	public List<Map<String, Object>> getLiveReservationCal() throws Exception {
		log.info("/live/json/getLiveReservationCal : GET start...");

		List<LiveReservation> list = liveService.getLiveReservationCal();

		log.info("{}", list);

		JSONObject jsonObj = new JSONObject();
		JSONArray jsonArr = new JSONArray();

		Map<String, Object> map = new HashMap<>();

		for (LiveReservation obj : list) {
			map.put("title", "예약 수 : " + obj.getTitle());
			map.put("start", obj.getReservationDate());

			jsonObj = new JSONObject(map);
			jsonArr.add(jsonObj);
		}

		log.info("jsonArrCheck: {}", jsonArr);

		log.info("/live/json/getLiveReservationCal : GET end...");
		return jsonArr;
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

	// 유저 강제퇴장
	@GetMapping("json/kickUser/{roomId}/{clientKey}")
	public void kickUser(@PathVariable("roomId") String roomId, @PathVariable("clientKey") List<String> clientKey)

			throws Exception {

		log.info("editRoom = {} ", this.getClass());

		// DB에 강제퇴장 내용 등록

//		LiveUserStatus live = new LiveUserStatus();
//		for (String id : clientKey) {
//			live.setLiveNumber(liveService.getLiveNumberByRoomId(roomId).getLiveNumber());
//			live.setId(id);
//			live.setKickStatus(1);
//
//			liveService.addLiveUserStatus(live);
//
//			System.out.println(live);
//		}

		String token = getToken();

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

		for (String client : clientKey) {

			URL url = new URL("https://vchatcloud.com/openapi/v1/exiles/" + roomId + "/" + client);

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod("POST");

			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
			conn.setRequestProperty("X-AUTH-TOKEN", token);
			conn.setDoOutput(true);

			// 데이터 입력 스트림에 담기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while (br.ready()) {
				sb.append(br.readLine());
			}

			conn.disconnect();

		}
	}

	@GetMapping("json/cancleKickUser/{roomId}/{clientKey}")
	public void cancleKickUser(@PathVariable("roomId") String roomId, @PathVariable("clientKey") List<String> clientKey)
			throws Exception {

		log.info("editRoom = {} ", this.getClass());

		// DB에 강제퇴장 내용 등록

		String token = getToken();

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

		for (String client : clientKey) {

			URL url = new URL("https://vchatcloud.com/openapi/v1/exiles/" + roomId + "/" + client);

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod("PUT");

			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
			conn.setRequestProperty("X-AUTH-TOKEN", token);
			conn.setDoOutput(true);

			// 데이터 입력 스트림에 담기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while (br.ready()) {
				sb.append(br.readLine());
			}

			conn.disconnect();

		}
	}

	// 유저 채팅제한 풀기
	@GetMapping("json/cancleMuteUser/{roomId}/{clientKey}")
	public void cancleMuteUser(@PathVariable("roomId") String roomId, @PathVariable("clientKey") List<String> clientKey)
			throws Exception {

		log.info("editRoom = {} ", this.getClass());

		// DB에 강제퇴장 내용 등록

		String token = getToken();

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

		for (String client : clientKey) {

			URL url = new URL("https://vchatcloud.com/openapi/v1/mute/" + roomId + "/" + client);

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod("PUT");

			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
			conn.setRequestProperty("X-AUTH-TOKEN", token);
			conn.setDoOutput(true);

			// 데이터 입력 스트림에 담기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while (br.ready()) {
				sb.append(br.readLine());
			}

			conn.disconnect();

		}
	}

	// 유저 채팅제한
	@GetMapping("json/muteUser/{roomId}/{clientKey}")
	public void muteUser(@PathVariable("roomId") String roomId, @PathVariable("clientKey") List<String> clientKey)

			throws Exception {

		log.info("editRoom = {} ", this.getClass());
		System.out.println("방송 정보 수정");

		String token = getToken();

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

		for (String client : clientKey) {

			URL url = new URL("https://vchatcloud.com/openapi/v1/mute/" + roomId + "/" + client);

			HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
			conn.setSSLSocketFactory(sc.getSocketFactory());

			conn.setRequestMethod("POST");

			conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("api_key", "cjnipw-Z5WmzV-1fC64X-AaOxWY-20220610111801");
			conn.setRequestProperty("X-AUTH-TOKEN", token);
			conn.setDoOutput(true);

			// 데이터 입력 스트림에 담기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			while (br.ready()) {
				sb.append(br.readLine());
			}

			conn.disconnect();

		}
	}

	@GetMapping("json/getEnableLiveReservationTime")
	public List<Integer> getEnableLiveReservationTime(String date) throws Exception {
		log.info("addLiveReservation GET : start...");

		List<LiveReservation> list = liveService.getLiveReservationList(date);

		List<Integer> timeList = new ArrayList<>();
		boolean flag = true;

		for (int i = 0; i < 24; i++) {
			flag = true;
			for (LiveReservation obj : list) {
				if (i == obj.getReservationTime()) {
					flag = false;
				}
			}
			if (flag) {
				timeList.add(i);
			}
		}

		log.info("timeList = {}", timeList);

		log.info("addLiveReservation GET : end...");
		return timeList;
	}

	@PostMapping("json/updateAlarm")
	public Map<String, Object> updateAlarm(@RequestBody Live live) throws Exception {
		log.info("updateAlarm POST : start...");

		log.info("live = {}", live);

		live = liveService.getLiveNumberByRoomId(live.getRoomId());

		SecurityUserService securityUserService = (SecurityUserService) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal(); // principal 에 사용자 인증 정보 담음
		User user = (User) securityUserService.getUser();

		LiveUserStatus liveUserStatus = new LiveUserStatus();
		liveUserStatus.setId(user.getId());
		liveUserStatus.setNickName(user.getNickname());
		liveUserStatus.setLiveNumber(live.getLiveNumber());

		if (liveService.getLiveUserStatus(liveUserStatus) == null) {
			liveUserStatus.setAlarmStatus(1);
			liveService.addLiveUserStatus(liveUserStatus);
		} else {
			liveUserStatus = liveService.getLiveUserStatus(liveUserStatus);

			if (liveUserStatus.getAlarmStatus() == 0) {
				liveUserStatus.setAlarmStatus(1);
			} else {
				liveUserStatus.setAlarmStatus(0);
			}

			liveService.updateLiveUserStatus(liveUserStatus);
		}

		Map<String, Object> map = new HashMap<>();
		map.put("liveUserStatus", liveUserStatus);

		log.info("updateAlarm POST : end...");

		return map;
	}

}
