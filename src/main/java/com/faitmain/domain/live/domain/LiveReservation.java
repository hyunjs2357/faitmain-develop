package com.faitmain.domain.live.domain;

import java.sql.Date;
import java.util.List;

import com.faitmain.domain.user.domain.User;

import lombok.Data;

@Data
public class LiveReservation {
	private int liveReservationNumber;
	private User store;
	private Date reservationDate;
	private List<LiveProduct> liveProduct;
	private String title;
	private int reservationTime;
}
