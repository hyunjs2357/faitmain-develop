package com.faitmain.domain.live.domain;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LiveChat {
	private int liveNumber;
	private String writer;
	private String chattingMessage;
	private Timestamp sendDate;
	
}
