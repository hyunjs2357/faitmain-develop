//package com.faitmain.domain.live.service;
//
//
// 
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.mockito.Mockito.verify;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import com.faitmain.domain.live.domain.Live;
//import static org.mockito.BDDMockito.given;
//import com.faitmain.domain.live.mapper.LiveMapper;
// 
//class LiveServiceTest {
//
//	LiveServiceImpl liveServiceImpl;
//	
//	@Mock
//	LiveMapper liveMapper;
//
//	@BeforeEach
//	void setup() {
//		MockitoAnnotations.initMocks(this);
//		liveServiceImpl = new LiveServiceImpl(liveMapper);
//	}
//	
//	//add
//	@Test
//	@DisplayName("addLive : 라이브 생성")
//	void insert() throws Exception {
//		int liveNumber = 10000;
//		String liveTitle = "반갑습니다";
//		
//		Live mockLive = Live.builder().liveNumber(liveNumber).liveTitle(liveTitle).build();
//
//        liveServiceImpl.addLive(mockLive);
//
//        verify(liveMapper).addLive(mockLive);
//		
//	}
//	
//	//get
//	@Test
//	@DisplayName("getLive : 라이브 상세조회")
//	void detail() throws Exception {
//		int liveNumber = 10000;
//		String liveTitle = "반갑습니다";
//		
//		Live mockLive = Live.builder().liveNumber(liveNumber).liveTitle(liveTitle).build();
//		
//		given(liveMapper.getLive(liveNumber)).willReturn(mockLive);
//		
//		Live responseLive = liveServiceImpl.getLive(liveNumber);
//		System.out.println(responseLive);
//		assertThat(responseLive.getLiveNumber()).isEqualTo(liveNumber);
//		
//	}
//	
//	//update
//	@Test
//	@DisplayName("updateLive : 라이브 업데이트")
//	void update() throws Exception {
//		int liveNumber = 10000;
//		String liveTitle = "반갑습니다";
//				
//		Live mockLive = Live.builder().liveNumber(liveNumber).liveTitle(liveTitle).build();
//
//        liveServiceImpl.addLive(mockLive);
//        
//        System.out.println(liveServiceImpl.addLive(mockLive));
//        
//        mockLive = Live.builder().liveNumber(liveNumber).liveTitle("뽀롤롤로").build();
//        
//        liveServiceImpl.updateLive(mockLive);
//        
//        Live responseLive = liveServiceImpl.getLive(liveNumber);
//        System.out.println(responseLive);
//        assertThat(responseLive.getLiveTitle()).isEqualTo("뽀롤롤로");
//        
//	}
//}
