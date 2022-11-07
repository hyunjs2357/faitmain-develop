package com.faitmain.domain.live.domain;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Live {
	private int liveNumber;
	private String roomId;
	private String storeId;
	private String liveTitle;
	private String liveIntro;
	private String liveImage;
	private int liveWatcher;
	private boolean chattingStatus; // false 0 : chat off / true 1 : chat on
	private boolean liveStatus;  // false 0 : live off / true 1 : live on
	

	@Builder
	  public Live(int liveNumber, String liveTitle) {
	    this.liveNumber = liveNumber;
	    this.liveTitle = liveTitle;
	  }
}
