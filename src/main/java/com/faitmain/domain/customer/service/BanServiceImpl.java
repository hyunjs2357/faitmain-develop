package com.faitmain.domain.customer.service;


import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.faitmain.domain.customer.domain.BanStatus;
import com.faitmain.domain.customer.domain.Customer;
import com.faitmain.domain.customer.mapper.BanMapper;
import com.faitmain.domain.customer.mapper.CustomerMapper;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Page;
import com.faitmain.global.common.Paging;




@Service("banServiceImpl")
@Transactional
public class BanServiceImpl implements BanService{
	
	@Autowired
	private BanMapper banMapper;	//데이터베이스에 접근하는 DAO bean을 선언 
	
	public BanServiceImpl(BanMapper banMapper) {
		this.banMapper = banMapper;
	}

// 신고 등록
	public void addReport(BanStatus banStatus, MultipartHttpServletRequest mRequest) throws Exception{
		MultipartFile file = mRequest.getFile("file");
		
		banMapper.addReport(banStatus);
	}

	
// 이미지 등록
	@Override
	public void addCustomerBoardImage(Image image) throws Exception {
		banMapper.addCustomerBoardImage(image);
	}

// 이미지 수정
	@Override
	public void updateCustomerBoardImage(Image image) throws Exception {
		banMapper.updateCustomerBoardImage(image);
	}
	
}

