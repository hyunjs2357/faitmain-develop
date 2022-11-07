package com.faitmain.domain.live.domain;

import lombok.Data;

@Data
public class LiveProduct {
	private int liveNumber;
	private int liveProductNumber;
	private int liveReservationNumber;
	private int productNumber;
	private String productMainImage;
	private String productName;
	private String productDetail;
	private int price;
}
