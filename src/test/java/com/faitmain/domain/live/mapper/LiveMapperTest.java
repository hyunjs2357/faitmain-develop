package com.faitmain.domain.live.mapper;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.faitmain.domain.live.domain.Live;
import com.faitmain.domain.live.domain.LiveChat;
import com.faitmain.domain.live.domain.LiveProduct;
import com.faitmain.domain.live.domain.LiveReservation;
import com.faitmain.domain.live.domain.LiveUserStatus;
import com.faitmain.domain.product.domain.Product;
import com.faitmain.domain.product.mapper.ProductMapper;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class LiveMapperTest {
	
	@Autowired
	private LiveMapper liveMapper;
	
	@Autowired
	private ProductMapper productMapper;
	
	/*================================= Live ===================================== */
	//@Test
	//@DisplayName("addLive Mapper Test")
	public void addLiveTest() throws Exception {
		System.out.println("addLiveTest start");
		
		Live live = new Live();
		
		live.setStoreId("store03@naver.com");
		live.setLiveTitle("liveTitleTest");
		live.setLiveIntro("liveIntroTest");
		live.setLiveImage("liveImageTest");
		live.setLiveStatus(true);
		live.setChattingStatus(true);
		System.out.println(live);
		
		int result = liveMapper.addLive(live);
		System.out.println("result = " + result);
		
		live = liveMapper.getLive(10005);
		System.out.println(live);
		
		assertThat(live.getStoreId()).isEqualTo("store03@naver.com");
		assertThat(live.getLiveTitle()).isEqualTo("liveTitleTest");
		assertThat(live.getLiveIntro()).isEqualTo("liveIntroTest");
		assertThat(live.getLiveImage()).isEqualTo("liveImageTest");
		
		System.out.println("addLiveTest end");
	}
	
	//@Test
	public void updateLiveTest() throws Exception {
		System.out.println("updateLiveTest start");
		
		Live live = new Live();
		
		live.setLiveNumber(10003);
		live.setStoreId("store03@naver.com");
		live.setLiveTitle("updateliveTitleTest");
		live.setLiveIntro("updateliveIntroTest");
		live.setLiveImage("updateliveImageTest");
		live.setLiveStatus(true);
		live.setChattingStatus(true);
		
		int result = liveMapper.updateLive(live);
		System.out.println("result = " + result);
		
		Live updateLive = liveMapper.getLive(10003);
		
		assertThat(updateLive.getStoreId()).isEqualTo("store03@naver.com");
		assertThat(updateLive.getLiveTitle()).isEqualTo("updateliveTitleTest");
		assertThat(updateLive.getLiveIntro()).isEqualTo("updateliveIntroTest");
		assertThat(updateLive.getLiveImage()).isEqualTo("updateliveImageTest");
		
		
		System.out.println("updateLiveTest end");
	}
	
	
	//@Test
	public void getLiveTest() throws Exception {
		System.out.println("getLiveTest start");
			
		Live getLive = liveMapper.getLive(10001);
			
		assertThat(getLive.getStoreId()).isEqualTo("store01@naver.com");
		assertThat(getLive.getLiveTitle()).isEqualTo("live_title 02");
		assertThat(getLive.getLiveIntro()).isEqualTo("live_intro 02");
		assertThat(getLive.getLiveImage()).isEqualTo("live_image 02");
			
		System.out.println("getLiveTest end");
	}
	
	//@Test
	public void getLiveListTest() throws Exception {
		System.out.println("getLiveListTest start");
				
		
		
		System.out.println("getLiveListTest end");
	}
		
	/*================================= Live Chat ====================================== */
	
	
	//@Test
	public void addLiveChatTest() throws Exception {
		System.out.println("addLiveChatTest start");
		
		LiveChat liveChat = new LiveChat();
		
		liveChat.setLiveNumber(10001);
		liveChat.setWriter("user02@naver.com");
		liveChat.setChattingMessage("addLiveChatTest");
		
		assertThat(liveMapper.addLiveChat(liveChat) == 1);
		
		System.out.println("addLiveChatTest end");
	}
	
	//@Test
	public void getLiveChatListTest() throws Exception {
		System.out.println("getLiveChatListTest start");
		
		
		
		System.out.println("getLiveChatListTest end");
	}
	
	/*================================= Live Product ====================================== */
	
	//@Test
	public void addLiveProductTest() throws Exception{
		System.out.println("addLiveProductTest start");
		
		LiveProduct liveProduct = new LiveProduct();
		Product product = new Product();
		
		product = productMapper.getProduct(10011);
		
		liveProduct.setLiveNumber(10001);
		liveProduct.setLiveReservationNumber(0);
		liveProduct.setProductNumber(product.getProductNumber());
		liveProduct.setProductMainImage(product.getProductMainImage());
		
		assertThat(liveMapper.addLiveProduct(liveProduct) == 1);
		
		System.out.println("addLiveProductTest end");
	}
	
	//@Test
	public void getLiveProductTest() throws Exception{
		System.out.println("getLiveProductTest start");
			
		LiveProduct liveProduct = new LiveProduct();
		
		liveProduct = liveMapper.getLiveProduct(10012);
		
		assertThat(liveProduct.getLiveProductNumber() == 10001);
		assertThat(liveProduct.getLiveNumber() == 10001);
		assertThat(liveProduct.getLiveReservationNumber() == 0);
		assertThat(liveProduct.getProductNumber() == 10011);
		assertThat(liveProduct.getProductMainImage()).isEqualTo("product_main_image.jpg");
				
		System.out.println("getLiveProductTest end");
	}
	
	//@Test
	public void getLiveProductListTest() throws Exception{
		System.out.println("getLiveProductListTest start");
				
				
		System.out.println("getLiveProductListTest end");
	}
	
	//@Test
	public void deleteLiveProductTest() throws Exception{
		System.out.println("deleteLiveProductTest start");
		
		assertThat(liveMapper.deleteLiveProduct(10013) == 1);
		
		System.out.println("deleteLiveProductTest end");
	}
	
	/*================================= Live Reservation ====================================== */
	
	//@Test
	public void addLiveReservationTest() throws Exception{
		System.out.println("addLiveReservationTest start");
		
		LiveReservation liveReservation = new LiveReservation();
		
//		liveReservation.setStoreId("store03@naver.com");
//		liveReservation.setReservationDate("2022-07-14 09:00");
		
		assertThat(liveMapper.addLiveReservation(liveReservation) == 1);
		
		System.out.println("addLiveReservationTest end");
	}
	
	/*================================= Live User Status ====================================== */
	
	//@Test
//	public void addLiveUserStatusTest() throws Exception{
//		System.out.println("addLiveUserStatusTest start");
//			
//		LiveUserStatus liveUserStatus = new LiveUserStatus();
//		
//		liveUserStatus.setLiveNumber(10001);
//		liveUserStatus.setId("user02@naver.com");
//			
//		assertThat(liveMapper.addLiveUserStatus(liveUserStatus) == 1);
//			
//		System.out.println("addLiveUserStatusTest end");
//	}
	
//	@Test
//	public void updateLiveUserStatusTest() throws Exception{
//		System.out.println("updateLiveUserStatusTest start");
				
//		LiveUserStatus liveUserStatus = new LiveUserStatus();
			
//		liveUserStatus.setLiveNumber(10002);
//		liveUserStatus.setId("user03@naver.com");
//		liveUserStatus.setAlarmStatus(true);
//		liveUserStatus.setKickStatus(true);
//		liveUserStatus.setDumbStatus(true);
				
//		int result = liveMapper.updateLiveUserStatus(liveUserStatus);
//		System.out.println("result = " + result);
//		
//		LiveUserStatus updateLiveUserStatus = liveMapper.getLiveUserStatus(liveUserStatus);
//		
//		assertThat(updateLiveUserStatus.getLiveNumber()==10002);
////		assertThat(updateLiveUserStatus.getId()).isEqualTo("user03@naver.com");
////				
//		System.out.println("updateLiveUserStatusTest end");
//	}
	
	//@Test
	public void getLiveUserStatusListTest() throws Exception{
		System.out.println("getLiveUserStatusListTest start");
		
		
		System.out.println("getLiveUserStatusListTest end");
	}
}