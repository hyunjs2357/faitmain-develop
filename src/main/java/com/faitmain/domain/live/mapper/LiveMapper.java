package com.faitmain.domain.live.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.faitmain.domain.live.domain.Live;
import com.faitmain.domain.live.domain.LiveChat;
import com.faitmain.domain.live.domain.LiveProduct;
import com.faitmain.domain.live.domain.LiveReservation;
import com.faitmain.domain.live.domain.LiveUserStatus;

@Mapper
public interface LiveMapper {

	// live
	public int addLive(Live live) throws Exception;

	public int updateLive(Live live) throws Exception;

	public int updateLiveStatusCode(Live live) throws Exception;

	public Live getLive(int liveNumber) throws Exception;

	public Live getLiveByStoreId(String storId) throws Exception;

	public Live getLiveNumberByRoomId(String roomId) throws Exception;

	public List<Live> getLiveList() throws Exception;

	// liveChat
	public int addLiveChat(LiveChat liveChat) throws Exception;

	public List<LiveChat> getLiveChatList(LiveChat liveChat) throws Exception;

	// liveProduct
	public int addLiveProduct(LiveProduct liveProduct) throws Exception;

	public LiveProduct getLiveProduct(int liveProductNumber) throws Exception;

	public List<LiveProduct> getLiveProductList(int liveReservationNumber) throws Exception;

	public List<LiveProduct> getLiveProductListByLiveNumber(int liveNumber) throws Exception;

	public int deleteLiveProduct(int liveNumber) throws Exception;

	public int deleteLiveProductByReservationNumber(int reservationNumber) throws Exception;

	// liveReservation
	public int addLiveReservation(LiveReservation liveReservation) throws Exception;

	public LiveReservation getLiveReservation(int liveReservationNumber) throws Exception;

	public LiveReservation getCurrentLiveReservation(LiveReservation liveReservation) throws Exception;

	public LiveReservation getLiveReservationByStoreId(LiveReservation liveReservation) throws Exception;

	public List<LiveReservation> getLiveReservationCal() throws Exception;

	public List<LiveReservation> getLiveReservationList(String date) throws Exception;

	public int updateLiveReservation(LiveReservation liveReservation) throws Exception;

	public int deleteLiveReservation(int liveReservationNumber) throws Exception;

	// liveUserStatus
	public int addLiveUserStatus(LiveUserStatus liveUserStatus) throws Exception;

	public int addLiveUserKick(int liveNumber, String storeId) throws Exception;

	public int updateLiveUserStatus(LiveUserStatus liveUserStatus) throws Exception;

	public LiveUserStatus getLiveUserStatus(LiveUserStatus liveUserStatus) throws Exception;

	public List<LiveUserStatus> getStoreLiveUserStatusList(int liveNumber) throws Exception;

	public List<LiveUserStatus> getUserLiveUserStatusList(LiveUserStatus liveUserStatus) throws Exception;

}
