package com.faitmain.domain.product.domain;

import java.sql.Date;
import java.util.List;

import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Image;

import lombok.Builder;
import lombok.Data;

@Data
public class Product {

	/* Product */
	private int productNumber;
	private String productName;
	private User store;
	private int productPrice;
	private String productMainImage;
	private List<Image> productExtraImage;
	private String productDetail;
	private int productQuantity;
	private String categoryCode;
	private String productStatus;
	private Date productRegDate;
	private int deliveryCharge;
	private int productGroupNumber;
	private List<Product> productOptions;
	
	/* Review & Inquiry */	
	private List<Review> reviewList;
	private int reviewCount;
	
	private List<Inquiry> inquiryList;
	private int inquiryCount;
	
	@Builder
	public Product(int productNumber, String productName) {
	  this.productNumber = productNumber;
	  this.productName = productName;
	}
	
	public Product() {
	
	}
	
}