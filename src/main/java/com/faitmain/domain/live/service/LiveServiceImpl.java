package com.faitmain.domain.live.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.faitmain.domain.live.domain.Live;
import com.faitmain.domain.live.domain.LiveChat;
import com.faitmain.domain.live.domain.LiveProduct;
import com.faitmain.domain.live.domain.LiveReservation;
import com.faitmain.domain.live.domain.LiveUserStatus;
import com.faitmain.domain.live.mapper.LiveMapper;
import com.faitmain.domain.user.domain.User;
import com.faitmain.domain.user.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.exception.NurigoMessageNotReceivedException;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.response.MultipleDetailMessageSentResponse;
import net.nurigo.sdk.message.service.DefaultMessageService;

@Slf4j
@Service("liveServiceImpl")
@Transactional
public class LiveServiceImpl implements LiveService {

	@Autowired
	private LiveMapper liveMapper;

	@Autowired
	private UserMapper userMapper;

	private DefaultMessageService messageService;

//	public void setLiveMapper(LiveMapper liveMapper) {
//		this.liveMapper = liveMapper;
//	}

	// live
	public int addLive(Live live) throws Exception {
		return liveMapper.addLive(live);
	}

	public int updateLive(Live live) throws Exception {
		return liveMapper.updateLive(live);
	}

	public int updateLiveStatusCode(int liveNumber) throws Exception {
		Live live = liveMapper.getLive(liveNumber);
		return liveMapper.updateLiveStatusCode(live);
	}

	public Live getLive(int liveNumber) throws Exception {
		return liveMapper.getLive(liveNumber);
	}

	public Live getLiveByStoreId(String storeId) throws Exception {
		return liveMapper.getLiveByStoreId(storeId);
	}

	public Live getLiveNumberByRoomId(String roomId) throws Exception {
		return liveMapper.getLiveNumberByRoomId(roomId);
	}

	public Map<String, Object> getLiveList() throws Exception {
		log.info("Impl getLiveList start...");
		List<Live> list = liveMapper.getLiveList();

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("liveList", list);
		log.info("Impl getLiveList end...");
		return map;
	}

	// liveChat
	public int addLiveChat(LiveChat liveChat) throws Exception {

		return liveMapper.addLiveChat(liveChat);
	}

	public Map<String, Object> getLiveChatList(LiveChat liveChat) throws Exception {

		List<LiveChat> list = liveMapper.getLiveChatList(liveChat);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list);

		return map;
	}

	// liveProduct
	public int addLiveProduct(LiveProduct liveProduct) throws Exception {
		return liveMapper.addLiveProduct(liveProduct);
	}

	public LiveProduct getLiveProduct(int liveProductNumber) throws Exception {
		return liveMapper.getLiveProduct(liveProductNumber);
	}

	public List<LiveProduct> getLiveProductList(int liveReservationNumber) throws Exception {
		return liveMapper.getLiveProductList(liveReservationNumber);
	}

	public List<LiveProduct> getLiveProductListByLiveNumber(int liveNumber) throws Exception {
		return liveMapper.getLiveProductListByLiveNumber(liveNumber);
	}

	public int deleteLiveProduct(int liveNumber) throws Exception {
		return liveMapper.deleteLiveProduct(liveNumber);
	}

	public int deleteLiveProductByReservationNumber(int reservationNumber) throws Exception {
		return liveMapper.deleteLiveProductByReservationNumber(reservationNumber);
	}

	// liveReservation
	public int addLiveReservation(LiveReservation liveReservation) throws Exception {
		return liveMapper.addLiveReservation(liveReservation);
	}

	public LiveReservation getLiveReservation(int liveReservationNumber) throws Exception {
		return liveMapper.getLiveReservation(liveReservationNumber);
	}

	public LiveReservation getCurrentLiveReservation() throws Exception {

		LiveReservation liveReservation = new LiveReservation();

		// 현재 날짜
		LocalDate now = LocalDate.now();

		Date date = java.sql.Date.valueOf(now);
		log.info("date : {}", date);

		// 현재 시간
		LocalTime nowTime = LocalTime.now();

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");

		int time = Integer.parseInt(nowTime.format(formatter));
		log.info("time : {}", time);

		liveReservation.setReservationDate(date);
		liveReservation.setReservationTime(time);

		return liveMapper.getCurrentLiveReservation(liveReservation);
	}

	public LiveReservation getLiveReservationByStoreId(String storeId) throws Exception {

		LiveReservation liveReservation = new LiveReservation();

		User user = new User();
		user.setId(storeId);

		// 현재 날짜
		LocalDate now = LocalDate.now();
		Date date = java.sql.Date.valueOf(now);
		log.info("date = {}", date);

		// 현재 시간
		LocalTime nowTime = LocalTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH");
		int time = Integer.parseInt(nowTime.format(formatter));
		log.info("time = {}", time);

		liveReservation.setReservationDate(date);
		liveReservation.setReservationTime(time);
		liveReservation.setStore(user);

		return liveMapper.getLiveReservationByStoreId(liveReservation);
	}

	public List<LiveReservation> getLiveReservationCal() throws Exception {
		return liveMapper.getLiveReservationCal();
	}

	public List<LiveReservation> getLiveReservationList(String date) throws Exception {
		return liveMapper.getLiveReservationList(date);
	}

	public int updateLiveReservation(LiveReservation liveReservation) throws Exception {
		return liveMapper.updateLiveReservation(liveReservation);
	}

	public int deleteLiveReservation(int liveReservationNumber) throws Exception {
		return liveMapper.deleteLiveReservation(liveReservationNumber);
	}

	// liveUserStatus
	public int addLiveUserStatus(LiveUserStatus liveUserStatus) throws Exception {

//		liveUserStatus.getId();// 수정해야댐 수정해야댐 수정해야댐 무지성으로 걍 박아놓은거임
		return liveMapper.addLiveUserStatus(liveUserStatus);
	}

	public int addLiveUserKick(int liveNumber, String storeId) throws Exception {

		return liveMapper.addLiveUserKick(liveNumber, storeId);
	}

	public int updateLiveUserStatus(LiveUserStatus liveUserStatus) throws Exception {
		return liveMapper.updateLiveUserStatus(liveUserStatus);
	}

	public LiveUserStatus getLiveUserStatus(LiveUserStatus liveUserStatus) throws Exception {
		return liveMapper.getLiveUserStatus(liveUserStatus);
	}

	public Map<String, Object> getStoreLiveUserStatusList(int liveNumber) throws Exception {

		List<LiveUserStatus> list = liveMapper.getStoreLiveUserStatusList(liveNumber);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list);

		return map;
	}

	public Map<String, Object> getUserLiveUserStatusList(LiveUserStatus liveUserStatus) throws Exception {

		List<LiveUserStatus> list = liveMapper.getUserLiveUserStatusList(liveUserStatus);

		Map<String, Object> map = new HashMap<String, Object>();

		map.put("list", list);

		return map;
	}

	@Override
	public void sendSMS(Live live) throws Exception {
		this.messageService = NurigoApp.INSTANCE.initialize("NCSFLNAKPLATWT5U", "UQHE4HDGLZ99FWYC4YHSECRYKMLHGVZI",
				"https://api.coolsms.co.kr");

		User store = userMapper.getUser(live.getStoreId());

		log.info("store = {}", store);

		List<LiveUserStatus> liveUserStatus = (List<LiveUserStatus>) (getStoreLiveUserStatusList(live.getLiveNumber()))
				.get("list");

		log.info("liveUserStatus = {}", liveUserStatus);

		ArrayList<Message> messageList = new ArrayList<>();
		Message message = null;
		User user = null;

		for (LiveUserStatus obj : liveUserStatus) {
			user = userMapper.getUser(obj.getId());

			message = new Message();

			message.setFrom("01080077545");
			message.setTo(user.getPhoneNumber());
			message.setText(store.getStoreName() + "님 방송이 시작되었습니다.");

			messageList.add(message);
		}

		try {
			// send 메소드로 단일 Message 객체를 넣어도 동작합니다!
			MultipleDetailMessageSentResponse response = this.messageService.send(messageList);

			// 중복 수신번호를 허용하고 싶으실 경우 위 코드 대신 아래코드로 대체해 사용해보세요!
			// MultipleDetailMessageSentResponse response =
			// this.messageService.send(messageList, true);

			System.out.println(response);

		} catch (NurigoMessageNotReceivedException exception) {
			System.out.println(exception.getFailedMessageList());
			System.out.println(exception.getMessage());
		} catch (Exception exception) {
			System.out.println(exception.getMessage());
		}

	}

}
