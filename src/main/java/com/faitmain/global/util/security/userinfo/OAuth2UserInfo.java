package com.faitmain.global.util.security.userinfo;

import java.util.Map;

public interface OAuth2UserInfo {
 
	    Map<String, Object> getAttributes();
	    String getProviderId();
	    String getProvider();
	    String getEmail();
	    String getName();
	    String getNickname();
	    String getGender();
	 
}
