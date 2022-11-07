package com.faitmain.domain.product.domain;

import java.sql.Date;

import lombok.Builder;
import lombok.Data;

@Data
public class Inquiry {
	
	private int inquiryNumber;
	private String inquiryTitle;
	private String inquiryContent;
	private Date inquiryDate;
	private String userId;
	private String storeId;
	private Product inquiryProduct;
	private boolean inquiryReplyStatus;
	private String inquiryReplyContent;
	private Date inquiryReplyDate;
	private boolean secret;
	
	@Builder
	public Inquiry(int inquiryNumber, String inquiryTitle) {
		this.inquiryNumber = inquiryNumber;
		this.inquiryTitle = inquiryTitle;
	}

	public Inquiry() {
	}

}