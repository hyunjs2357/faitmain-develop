package com.faitmain.domain.user.domain;


import java.sql.Date;
import java.util.List;

import com.faitmain.global.common.Image;

import lombok.Data;


@Data
public class StoreApplicationDocument {


		private	int storeApplicationDocumentNumber ;
		private	String id ;
		private	String storeName ;
		private	String storeIntroduction ;
		private	String examinationStatus ; // R, A ,W 
		private	String productDetial ;
 		private Date regDate ;
		private String storeLogoImage;
		 
 		
	
	
}
