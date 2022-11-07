package com.faitmain.domain.user.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faitmain.domain.user.domain.User;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("apiServiceImpl")
@Transactional
public class ApiServiceImpl implements ApiService{

    @Autowired
    @Qualifier( "userServiceImpl" )
    private UserSerivce userSerivce;
    
	private static final String ADMIN_TOKEN  ="8ca18c1cbc1276e4bb11ec65b02013fb";
	
	public String getKaKaoAccessToken(String authorize_code) throws Exception{


		log.info("토큰 받으러 getKaKaoAccessToken 서비스에 옴 ");
		log.info("받은 인가 코드 getKaKaoAccessToken {} " , authorize_code);

		String access_Token = "";
		String refresh_Token = "";
		//요청하는 인증 URL		
		String reqURL = "https://kauth.kakao.com/oauth/token";  

		try {
			URL url = new URL(reqURL);
            
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			// POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);
			// POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
			StringBuilder sb = new StringBuilder();
			sb.append("grant_type=authorization_code");  // 인가 코드  요청 authorization_code 
            
			sb.append("&client_id=f0b36852932e865ae00c9ff2fcd19874&"); //본인이 발급받은 key
			sb.append("&redirect_uri=http://localhost:8080/user/kakaoLogin"); // 본인이 설정한 주소
            
			sb.append("&code=" + authorize_code); // 인가 코드 까지 삽입
			bw.write(sb.toString());
			bw.flush(); //bw 출력 
            
			// 결과 코드가 200이라면 성공
			int responseCode = conn.getResponseCode();
			log.info("responseCode : {}" , responseCode);
            
			
			// 요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line = "";
			String result = "";
            
			//카카오에서 주는 정보 한줄한줄 읽어서 result에 저장 
			while ((line = br.readLine()) != null) {
				result += line;
			}
			log.info("response body : {}" , result);
            
			// Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
			JsonParser parser = new JsonParser();
			JsonElement element = parser.parse(result);
            
			access_Token = element.getAsJsonObject().get("access_token").getAsString();
			refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();
            
			log.info("access_token : {} " , access_Token);
			log.info("refresh_token :{} " , refresh_Token);
			log.info("== kakao 정보 끝 ==");

			br.close();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return access_Token;
		//통신 결과가 200이어야 OK
		
	}
	public HashMap<String, Object> getKakoUserInfo(String access_Token) throws Exception {
		// 요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
				HashMap<String, Object> userInfo = new HashMap<String, Object>();
				String reqURL = "https://kapi.kakao.com/v2/user/me";
				try {
					URL url = new URL(reqURL);
					HttpURLConnection conn = (HttpURLConnection) url.openConnection();
					conn.setRequestMethod("GET");

					// 요청에 필요한 Header에 포함될 내용
					conn.setRequestProperty("Authorization", "Bearer " + access_Token);

					int responseCode = conn.getResponseCode();
					System.out.println("responseCode : " + responseCode);

					BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

					String line = "";
					String result = "";

					while ((line = br.readLine()) != null) {
						result += line;
					}
					System.out.println("response body : " + result);

					JsonParser parser = new JsonParser();
					JsonElement element = parser.parse(result);

					JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
					JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

		 			String email = kakao_account.getAsJsonObject().get("email").getAsString();

		 			userInfo.put("email", email);

				} catch (IOException e) {
					e.printStackTrace();
				}
				return userInfo;
		
	}
	@Override
	public void kakaoLogin(String authorizedCode) {
 		
//		
//	      // 카카오 OAuth2 를 통해 카카오 사용자 정보 조회
//        KakaoUserInfo userInfo = kakaoOAuth2.getUserInfo(authorizedCode);
//        Long kakaoId = userInfo.getId();
//        String nickname = userInfo.getNickname();
//        String email = userInfo.getEmail();
//
//        // 우리 DB 에서 회원 Id 와 패스워드
//        // 회원 Id = 카카오 nickname
//        String id = nickname;
//        // 패스워드 = 카카오 Id + ADMIN TOKEN
//        String password = kakaoId + ADMIN_TOKEN;
//
//        // DB 에 중복된 Kakao Id 가 있는지 확인
//        User kakaoUser = userSerivce.getUser(id);
//         
//        
//		// 카카오 정보로 회원가입
//        if (kakaoUser == null) {
//            // 패스워드 인코딩
//            String encodedPassword = passwordEncoder.encode(password);
//            // ROLE = 사용자
//            kakaoUser.setRole(password);
//
//            kakaoUser = new User(nickname, encodedPassword, email, role, kakaoId);
//            userRepository.save(kakaoUser);
//        }
//
//        // 로그인 처리
//        Authentication kakaoUsernamePassword = new UsernamePasswordAuthenticationToken(username, password);
//        Authentication authentication = authenticationManager.authenticate(kakaoUsernamePassword);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//출처: https://thalals.tistory.com/237 [힘차게, 열심히 공대생:티스토리]
		
		
		
	}

}
