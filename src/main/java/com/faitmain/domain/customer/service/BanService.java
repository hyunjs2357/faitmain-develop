package com.faitmain.domain.customer.service;


import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.faitmain.domain.customer.domain.BanStatus;
import com.faitmain.domain.customer.domain.Customer;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Page;
import com.faitmain.global.common.Paging;
import com.faitmain.global.common.Search;



public interface BanService {
	
//	신고 등록
	public void addReport(BanStatus banStatus, MultipartHttpServletRequest mRequest) throws Exception;
		
//	이미지 등록
	public void addCustomerBoardImage(Image image) throws Exception;
	
//	이미지 수정
	public void updateCustomerBoardImage(Image image) throws Exception;
	
}
//	public BanStatus updateBanStatus(int reportNumber) throws Exception;



