package com.faitmain.domain.user.domain;


import lombok.Data;

import java.sql.Date;


@Data
public class User {

	private Integer userNumber;

	private String id;

	private String password;

	private String gender;

	private String userAddress1;

	private String userAddress2;

	private String userAddress3;

	private String nickname;

	private String phoneNumber;

	private String name;

	private Date regDate;

	public String getRole(){
		return role;
	}

	private String joinPath;

	private Integer bookNumber;

	private Integer totalPoint;

	private String storeLogoImage;
				 
	private String storeIntroduction;

	private String role;

	private String storeName;

	private Boolean withdrawalStatus;  // on 탈퇴 , off 탈퇴 상퇴 x 

	private int storeApplicationDocumentNumber;

}





 
