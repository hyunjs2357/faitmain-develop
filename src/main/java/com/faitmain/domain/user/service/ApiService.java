package com.faitmain.domain.user.service;

import java.util.HashMap;

public interface ApiService {

	// 카카오 토큰 받기
	public String getKaKaoAccessToken(String authorize_code) throws Exception ;
	
	//카카오 정보 받아오기
	
	public HashMap<String, Object> getKakoUserInfo(String access_Token) throws Exception ;

     public void kakaoLogin(String authorizedCode) ;
 	
}
