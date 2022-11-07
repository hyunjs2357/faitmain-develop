package com.faitmain.domain.product.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.faitmain.domain.product.domain.Review;
import com.faitmain.domain.product.mapper.ReviewMapper;
import com.faitmain.global.common.Search;

import lombok.RequiredArgsConstructor;

@Service("reviewServiceImpl")
@Transactional
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

	@Value("${upload-path}")
	private String fileStorageLocation;	
	
	@Autowired
	private ReviewMapper reviewMapper;
	
	public ReviewServiceImpl(ReviewMapper reviewMapper) {
		this.reviewMapper = reviewMapper;
	}
	
	@Override
	public void addReview(Review review, MultipartHttpServletRequest mRequest) throws Exception {
		MultipartFile reviewFile = mRequest.getFile("reviewImg");
				
		if(!reviewFile.isEmpty()) {
			
			System.out.println("reviewFile");
			String fileName = storeFile(reviewFile);
			review.setReviewImage(fileName);
		}
		
		reviewMapper.addReview(review);		
	}

	@Override
	public Review getReview(int reviewNumber) throws Exception {
		return reviewMapper.getReview(reviewNumber);
	}

	@Override
	public Map<String, Object> getReviewList(Search search) throws Exception {
		
		List<Review> list = reviewMapper.getReviewList(search);
				
		int totalCount = reviewMapper.getTotalCount(search);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("list", list);
		resultMap.put("totalCount", new Integer(totalCount));
				
		return resultMap;
	}

	@Override
	public void updateReview(Review review, MultipartHttpServletRequest mRequest) throws Exception {
		MultipartFile reviewFile = mRequest.getFile("reviewImg");
		
		if(!reviewFile.isEmpty()) {
			
			System.out.println("reviewFile");
			String fileName = storeFile(reviewFile);
			review.setReviewImage(fileName);
		}
		
		reviewMapper.updateReview(review);		
	}

	@Override
	public int deleteReview(int reviewNumber) throws Exception {
		return reviewMapper.deleteReview(reviewNumber);		
	}

	@Override
	public Review getOrderProduct(int orderProductNumber) throws Exception {
		return reviewMapper.getOrderProduct(orderProductNumber);
	}	
	
	public String storeFile(MultipartFile file) throws Exception{
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss_");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		String timeStamp = sdf.format(timestamp);
		
		String originalFileName = file.getOriginalFilename();	//오리지날 파일명
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
		String fileName =  timeStamp + UUID.randomUUID() + extension;
		
		Path targetLocation = (Paths.get(fileStorageLocation).toAbsolutePath().normalize()).resolve(fileName);
		System.out.println("targetLocation : " + targetLocation);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		
		return fileName;
	}


}