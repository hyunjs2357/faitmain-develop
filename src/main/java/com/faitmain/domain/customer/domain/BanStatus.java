package com.faitmain.domain.customer.domain;

import java.sql.Date;

import com.faitmain.domain.user.domain.User;

import lombok.Data;


@Data
public class BanStatus {
	
	private int reportNumber;
	private User respondent;
	private String reportTitle;
	private String reportContent;
	private Date reportRegDate;
	private int statusNumber;
	private int periodNumber;
	private Date endDate;
	private String reportImage;
	
	
	
	
}
