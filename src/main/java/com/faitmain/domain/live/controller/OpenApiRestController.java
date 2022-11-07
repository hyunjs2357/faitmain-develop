package com.faitmain.domain.live.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faitmain.domain.live.service.LiveService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/openapi/*")
public class OpenApiRestController {

	// Field
		@Autowired
		private LiveService liveService;

		@GetMapping("token")
		public void getAccessToken() throws Exception {
			log.info( "AccessToken = {} ", this.getClass() );
			
			JSONObject result = null;
			StringBuilder sb = new StringBuilder();
			
			 TrustManager[] trustCerts = new TrustManager[]{
		                new X509TrustManager() {
		                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		                        return null;
		                    }
		                    public void checkClientTrusted(
		                        java.security.cert.X509Certificate[] certs, String authType) {
		                    }
		                    public void checkServerTrusted(
		                        java.security.cert.X509Certificate[] certs, String authType) {
		                    }
		                }
		            };
		 
		         SSLContext sc = SSLContext.getInstance("TLSv1.2");
		         sc.init(null, trustCerts, new java.security.SecureRandom());
		         
		         URL url = new URL("https://vchatcloud.com/openapi/token");
		 
		         HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		         conn.setSSLSocketFactory(sc.getSocketFactory());
		         
		         conn.setRequestMethod("GET");
		         
		         conn.setRequestProperty("Content-type", "application/json");
		         conn.setRequestProperty("accept", "*/*");
		         conn.setRequestProperty("api_key", "kmLueZ-chdq38-O7LGgP-Ggd14x-20220604144349");
		         conn.setDoOutput(true);
		         
		         // 데이터 입력 스트림에 답기
		         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		         while(br.ready()) {
		        	 sb.append(br.readLine());
		         }
		         conn.disconnect();
		         
		         result = (JSONObject) new JSONParser().parse(sb.toString());
		         
		         // REST API 호출 상태 출력하기
		         StringBuilder out = new StringBuilder();
		         out.append(result.get("status") + " : " + result.get("status_message")+"\n");
		         
		         // JSON데이터에서 "data"라는 JSONObject를 가져온다.
		         JSONObject data = (JSONObject)result.get("data");
		         String user_email = (String)data.get("user_email");
		         String token = (String)data.get("X-AUTH-TOKEN");
		         
		         System.out.println("data : " + data);
		         System.out.println("email : " + user_email);
		         System.out.println("token : " + token);
		        
		}
		
//		@GetMapping("rooms")
//		public void getRoomsList() throws Exception {
//			log.info( "getRoomsList = {} ", this.getClass() );
//			
//			JSONObject result = null;
//			StringBuilder sb = new StringBuilder();
//			
//			 TrustManager[] trustCerts = new TrustManager[]{
//		                new X509TrustManager() {
//		                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//		                        return null;
//		                    }
//		                    public void checkClientTrusted(
//		                        java.security.cert.X509Certificate[] certs, String authType) {
//		                    }
//		                    public void checkServerTrusted(
//		                        java.security.cert.X509Certificate[] certs, String authType) {
//		                    }
//		                }
//		            };
//		 
//		         SSLContext sc = SSLContext.getInstance("TLSv1.2");
//		         sc.init(null, trustCerts, new java.security.SecureRandom());
//		         
//		         URL url = new URL("https://vchatcloud.com/openapi/v1/rooms");
//		 
//		         HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
//		         conn.setSSLSocketFactory(sc.getSocketFactory());
//		         
//		         conn.setRequestMethod("GET");
//		         
//		         conn.setRequestProperty("Content-type", "application/json");
//		         conn.setRequestProperty("accept", "*/*");
//		         conn.setRequestProperty("api_key", "kmLueZ-chdq38-O7LGgP-Ggd14x-20220604144349");
//		         conn.setRequestProperty("X-AUTH-TOKEN", "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ2Y3h6dmN4ejE1OUBnbWFpbC5jb20iLCJleHAiOjE2NTQ2MDA5MDcsImlhdCI6MTY1NDU4MjkwNywiYXV0aG9yaXRpZXMiOiJbUk9MRV9VU0VSXSJ9.Ahf6tgbqfSdr8kwFLAn7S4srU5Wt0yXrhKwHSCRtKWA");
//		         conn.setDoOutput(true);
//		         
//		         // 데이터 입력 스트림에 답기
//		         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//		         while(br.ready()) {
//		        	 sb.append(br.readLine());
//		         }
//		         conn.disconnect();
//		         
//		         result = (JSONObject) new JSONParser().parse(sb.toString());
//		         
//		         // REST API 호출 상태 출력하기
//		         StringBuilder out = new StringBuilder();
//		         out.append(result.get("status") + " : " + result.get("status_message")+"\n");
//		         
//		         // JSON데이터에서 "data"라는 JSONObject를 가져온다.
//		         JSONArray data = (JSONArray)result.get("list");
//		         JSONObject tmp;
//		         
//		         for(int i=0; i<data.size(); i++) {
//		        	 tmp = (JSONObject)data.get(i);
//		        	 System.out.println("data["+i+"] : "+tmp);
//		         }  
//		}
		
//		@GetMapping("create")
//		public String createRoom() throws Exception {
//			log.info( "createRoom = {} ", this.getClass() );
//			
//			JSONObject result = null;
//			StringBuilder sb = new StringBuilder();
//			
//			 TrustManager[] trustCerts = new TrustManager[]{
//		                new X509TrustManager() {
//		                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
//		                        return null;
//		                    }
//		                    public void checkClientTrusted(
//		                        java.security.cert.X509Certificate[] certs, String authType) {
//		                    }
//		                    public void checkServerTrusted(
//		                        java.security.cert.X509Certificate[] certs, String authType) {
//		                    }
//		                }
//		            };
//		 
//		         SSLContext sc = SSLContext.getInstance("TLSv1.2");
//		         sc.init(null, trustCerts, new java.security.SecureRandom());
//		         
//		         URL url = new URL("https://vchatcloud.com/openapi/v1/rooms");
//		 
//		         HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
//		         conn.setSSLSocketFactory(sc.getSocketFactory());
//		         
//		         conn.setRequestMethod("POST");
//		         
//		         conn.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
//		         conn.setRequestProperty("accept", "*/*");
//		         conn.setRequestProperty("api_key", "kmLueZ-chdq38-O7LGgP-Ggd14x-20220604144349");
//		         conn.setRequestProperty("X-AUTH-TOKEN", "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ2Y3h6dmN4ejE1OUBnbWFpbC5jb20iLCJleHAiOjE2NTQ3NTQ3MzQsImlhdCI6MTY1NDczNjczNCwiYXV0aG9yaXRpZXMiOiJbUk9MRV9VU0VSXSJ9.jzg1AiWUKgzvquyXb--qNKS7QrN1fnhRvslfqGMOUPI");
//		         conn.setDoOutput(true);
//		        
//		         String Data = "maxUser=8&roomName=roomNameTest전창&webrtc=";
//		         
////		         JSONObject Data = new JSONObject();
////		         Data.put("maxUser", "5");
////		         Data.put("roomName", "CreateRoomTest");
////		         System.out.println("JSONData : " + Data.toString());
//		        
//		         OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
//		         wr.write(Data);
//		         wr.flush();
//		         
//		         // 데이터 입력 스트림에 담기
//		         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
//		         while(br.ready()) {
//		        	 sb.append(br.readLine());
//		         }
//		         conn.disconnect();
//		         System.out.println("br" + br);
//		         System.out.println("wr" + wr);
//		         result = (JSONObject) new JSONParser().parse(sb.toString());
//		         
//		         // REST API 호출 상태 출력하기
//		         StringBuilder out = new StringBuilder();
//		         out.append(result.get("status") + " : " + result.get("status_message")+"\n");
//		         
//		         // JSON데이터에서 "data"라는 JSONObject를 가져온다.
//		         JSONObject data = (JSONObject) result.get("data");
//		         int Code = (int)result.get("result_cd");
//		         System.out.println("Code : " + Code);
//		         System.out.println("data : " + data);
//		         
//		         return "view/live/live";
//		         
//		}
		
		
		@GetMapping("roomInfo")
		public void getRoomInfo() throws Exception {
			log.info( "RoomInfo = {} ", this.getClass() );
			
			JSONObject result = null;
			StringBuilder sb = new StringBuilder();
			
			 TrustManager[] trustCerts = new TrustManager[]{
		                new X509TrustManager() {
		                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
		                        return null;
		                    }
		                    public void checkClientTrusted(
		                        java.security.cert.X509Certificate[] certs, String authType) {
		                    }
		                    public void checkServerTrusted(
		                        java.security.cert.X509Certificate[] certs, String authType) {
		                    }
		                }
		                
		            };
		 
		         SSLContext sc = SSLContext.getInstance("TLSv1.2");
		         sc.init(null, trustCerts, new java.security.SecureRandom());
		         
		         String strURL = "https://vchatcloud.com/openapi/v1/rooms/" + "IGufQKZlwk-3tvnTr3ytM-20220607050349";
		         
		         URL url = new URL(strURL);
		 
		         HttpsURLConnection conn = (HttpsURLConnection)url.openConnection();
		         conn.setSSLSocketFactory(sc.getSocketFactory());
		         
		         conn.setRequestMethod("GET");
		         
		         conn.setRequestProperty("Content-type", "application/json");
		         conn.setRequestProperty("accept", "*/*");
		         conn.setRequestProperty("api_key", "kmLueZ-chdq38-O7LGgP-Ggd14x-20220604144349");
		         conn.setRequestProperty("X-AUTH-TOKEN", "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJ2Y3h6dmN4ejE1OUBnbWFpbC5jb20iLCJleHAiOjE2NTQ1ODE3ODMsImlhdCI6MTY1NDU2Mzc4MywiYXV0aG9yaXRpZXMiOiJbUk9MRV9VU0VSXSJ9.etBsah9fsIR8eG_Bi32GhtSGsjfRv1Z4YMstfu0ADk8");
		         conn.setDoOutput(true);
		         
		         // 데이터 입력 스트림에 답기
		         BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
		         while(br.ready()) {
		        	 sb.append(br.readLine());
		         }
		         conn.disconnect();
		         
		         result = (JSONObject) new JSONParser().parse(sb.toString());
		         
		         // REST API 호출 상태 출력하기
		         StringBuilder out = new StringBuilder();
		         out.append(result.get("status") + " : " + result.get("status_message")+"\n");
		         
		         // JSON데이터에서 "data"라는 JSONObject를 가져온다.
		         JSONObject data = (JSONObject)result.get("data");
		         
		         System.out.println("data : " + data);
		}
		
		
		
}
