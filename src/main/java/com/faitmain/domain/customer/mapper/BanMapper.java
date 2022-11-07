package com.faitmain.domain.customer.mapper;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.faitmain.domain.customer.domain.BanStatus;

import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Page;
import com.faitmain.global.common.Paging;
import com.faitmain.global.common.Search;


@Mapper
public interface BanMapper {
	//Ban
	
//	신고 등록
	public void addReport(BanStatus banStatus) throws Exception;

	
//	이미지 등록
	public void addCustomerBoardImage(Image image) throws Exception;
	
//	이미지 수정
	public void updateCustomerBoardImage(Image image) throws Exception;
//	BanStatus
//	public int updateBanStatus(BanStatus banStatus) throws Exception;
	
	
	
}

