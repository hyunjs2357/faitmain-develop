//package com.faitmain.domain.user.mapper;
//
//import static org.junit.Assert.assertEquals;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
//import com.faitmain.domain.live.domain.Live;
//import com.faitmain.domain.order.mapper.OrderMapper;
//import com.faitmain.domain.product.domain.Product;
//import com.faitmain.domain.product.mapper.ProductMapper;
//import com.faitmain.domain.user.controller.UserRestController;
//import com.faitmain.domain.user.domain.StoreApplicationDocument;
//import com.faitmain.domain.user.domain.User;
//import com.faitmain.domain.user.service.UserSerivce;
//import com.faitmain.global.common.Image;
//
//import static org.assertj.core.api.Assertions.assertThat;
//@Slf4j
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//public class UserMapperTest {
//
//	
//	
//    @Autowired
//	private UserMapper usermapper;
//
//	 @Test
//	 @DisplayName("getUserTest   Test")
//	 
//	 
//	public void getUserTest() throws Exception {
// 		log.info("getUserTest Start ");
// 		
//		User user = new User();
//		user.setId("store03@naver.com");
//		
//		log.info("user 출력  {} " ,user);
//		user=usermapper.getUser(user.getId()) ;
// 		log.info("getUser :: 후 출력  {} " ,user);
// 		
//		assertThat(user.getName()).isEqualTo("이순신");
//		assertThat(user.getPhoneNumber()).isEqualTo("01022222222");
//		assertThat(user.getJoinPath()).isEqualTo("HOME");
//
// 		log.info("getUserTest end ");
//		
// 	}
//	
// 
//	 
//	// @Test
//	 @DisplayName("getUserUpdateTest   Test")
//	public void getUserUpdateTest() throws Exception {
//  		log.info("getUserUpdateTest start");
//		
//		User user = new User();
//		user.setId("store03@naver.com");
//		user=usermapper.getUser(user.getId()) ;
//		System.out.println(user);
//
//		user.setPhoneNumber("01099999999");
//		user.setStoreIntroduction("스토어 업데이트!!! ");
//		int result = usermapper.updateUser(user);
//		System.out.println("result:: "+ result +" getUser  "+user);
// 
//		
//		assertThat(user.getStoreIntroduction()).isEqualTo("스토어 업데이트!!! ");
// 		log.info("getUserUpdateTest end");
//
// 	}
//	
//	 
//	 //	@Test
//		 @DisplayName("updatUserStore   Test")
//		public void updatUserStore() throws Exception {
//	 		log.info("     updatUserStore start");
// 			
//			User user = new User();
//			user.setId("store03@naver.com");
//			user=usermapper.getUser(user.getId()) ;
// 			log.info("store 권한 올리기전 {} "  ,   user.getRole() );
//			
//			user.setRole("store");
//			Map<String,Object> map = new HashMap<>();
//
//			map.put("role", "store") ;
//			map.put("id", user.getId()) ;
// 			log.info("map 값은 :  {}" ,map);
// 			int   result = usermapper.updateUserStore(map);
//				log.info("##신청서 승인된 User , Role 권한 상승  결과  {} ##" , result);
//			
//			
//			
//			
//			
//		
// 			user=usermapper.getUser(user.getId()) ;
// 	 		log.info(" 나와라  변경된 롤 !  {}" , user.getRole());
// 			assertThat(user.getRole()).isEqualTo("store");
//
//	 		log.info(" updatUserStore end");
//		}
//		 
//		 	//@Test
//			 @DisplayName("findUser   Test")
//			public void findUser() throws Exception {
//				System.out.println("findUser start");
//			
//				HashMap<String,Object> map = new HashMap<>() ;
//				map.put("phoneNumber", "01012345678");
//				map.put("findcondition", "name");
//				map.put("findkeyword", "태지원");
//				
//				int result = usermapper.findUser(map) ;
//				System.out.println("findUse의 결과는  "+ result);
//				System.out.println("findUser end");
//			}
//		
//			//	@Test
//				 @DisplayName("addUser   Test")
//				public void addUser() throws Exception {
//					System.out.println("addUser start");
//				
//					User user = new User();
//					user.setId("test033@naver.com");
//					user.setPassword("test01");
//					user.setGender(null);
//					user.setNickname("테스트1");
//					user.setName("양지원");
//					user.setPhoneNumber("0106511987");
//					
//					user.setJoinPath("HOME");
//
//					user.setRole("user");
//					user.setStoreName(null);
// 					System.out.println(user);
//					int result = usermapper.addUser(user);
//					System.out.println(result+ " : addUser end");
//				}
//				 
//					 	
//	// @Test
//	 @DisplayName("getchechDuplicationCount   Test")
//		public void getchechDuplicationCount() throws Exception {
//			System.out.println("getchechDuplicationCount start");
//				
//			Map<String,Object> hashmap = new HashMap<>() ;
//			hashmap.put("checkcondition", "phone_number") ;
//			hashmap.put("checkkeyword", "0106541987") ;
//			
//			System.out.println("있음 : "+usermapper.getchechDuplicationCount(hashmap));
// 
//			
//			HashMap<String,Object> hashmap1 = new HashMap<>() ;
//			hashmap1.put("checkcondition", "id") ;
//			hashmap1.put("checkkeyword", "us05@naver.com") ;
//			
//			System.out.println("없음 : "+usermapper.getchechDuplicationCount(hashmap1));
// 
//					
//					
//			
//	 }
//					 		 
//	 // @Test
//	 @DisplayName("getStoreApplicationDocument   Test")
//		public void getStoreApplicationDocumenNumber() throws Exception {
//			System.out.println("getStoreApplicationDocumenNumber start");
//			// 이름으로, 스토어 신청서 번호 찾고 , 스토어 신청서 번호로 ,  스토어 신청서 찾아 옴 
//		 
//		 int StoreApplicationNumber= usermapper.getStoreApplicationDocumentNumber("store02@naver.com");
//		 System.out.println("id의 신청서 번호 " + StoreApplicationNumber) ;
//		 
//		 StoreApplicationDocument storeADoc  =usermapper.getStoreApplicationDocument(StoreApplicationNumber) ;
//		 List<Image> list   = usermapper.getImage(StoreApplicationNumber) ;
//			System.out.println(list) ;
//			storeADoc.setProductmanufacturingImage(list);
//		 
//			System.out.println("스토어 친성서 " + storeADoc) ; 
//		 
//			
//			System.out.println("getStoreApplicationDocumenNumber : " +storeADoc);
// 
//					
//			assertThat(storeADoc.getProductDetial()).isEqualTo("우리스토어");
//			assertThat( storeADoc.getId()).isEqualTo("store02@naver.com");
//			assertThat( storeADoc.getRegDate()).isEqualTo("2022-05-30");
//			assertThat( storeADoc.getStoreApplicationDocumentNumber()).isEqualTo(10001);
//
//			
//	 }
// // @Test
//	 @DisplayName("getUserList   Test")
//		public void getUserList() throws Exception {
//			System.out.println("getUserList start");
//			// 이름으로, 스토어 신청서 번호 찾고 , 스토어 신청서 번호로 ,  스토어 신청서 찾아 옴 
//		 
//		 
//			System.out.println("getProductListTest start");
//			
//			Map<String, Object> map = new HashMap<String, Object>();
//			//map.put("orderName", "product_name DESC");
//			map.put("startRowNum", 1+"");
//			map.put("endRowNum", 3+"");
//			
//			List<User> list = usermapper.getUserList(map);
// 			
//			for(User user :list) {
//				System.out.println("user 유저 출력 " + user ) ;
//				
//			}
// 			
//			
//		 
//			
//			System.out.println("Last getUserList : ");
// 
// 
//			
//	 }	
////	  
////	  // @Test //스토어 리스트  뽑기
////	 @DisplayName("getStoreApplicationDocumentList   Test")
////		public void getStoreApplicationDocumentList() throws Exception {
////			System.out.println("getStoreApplicationDocumentList start");
//// 		 
////		 
////			System.out.println("getProductListTest start");
////			
////			Map<String, Object> map = new HashMap<String, Object>();
////			//map.put("orderName", "product_name DESC");
////			map.put("searchCondition", "2");
////			map.put("startRowNum", 1+"");
////			map.put("endRowNum", 3+"");
////			
////			List<StoreApplicationDocument> list = usermapper.getStoreApplicationDocumentList(map);
//// 			
////			System.out.println(list);
////			
////			
////			for(StoreApplicationDocument StoreApplicationDocument :list) {
////				System.out.println("신청서 출력 " + StoreApplicationDocument ) ;
////				
////			}
////		 
////			
////			System.out.println("Last getStoreApplicationDocumentList : ");
//// 
//// 
////			
////	 }	
//	    
//	// @Test //스토어 리스트  뽑기
//	 @DisplayName("updateUser   Test")
//		public void updateUser() throws Exception {
//			System.out.println("updateUser updateUser : ");
//
//			User user = new User();
//			user.setId("test033@naver.com");
//			user = usermapper.getUser(user.getId()) ;
//			
//			System.out.println(user);
//			System.out.println("user 변경전 이름 "  + user.getName()) ;
//			user.setNickname("김평야");
//			int restult =  usermapper.updateUser(user) ;
//			System.out.println("result set " + restult);
//			System.out.println("result set " + usermapper.getUser(user.getId()).getNickname());
//
//			User user1 = usermapper.getUser(user.getId()) ;
//			assertThat(user1.getNickname()).isEqualTo("김평야");
//System.out.println("=======================");
//  
//
//			User user2 = new User();
//			user2.setId("store06@naver.com");
//			user2.setPassword("1111");
//			System.out.println("result set " + usermapper.updateUserPassword(user2));
//
//			
//		
// 
//			
//	 }	 
//	  
//	//  @Test //addImage + getImage
//	 @DisplayName("addImage   Test")
//		public void addImage() throws Exception {
//			System.out.println("addImage start");
// 		 
//			Image image = new Image();
//			image.setImageClassificationNumber(10004);
//			image.setImageName("testImage.png");
//			int result = usermapper.addImage(image) ;
//			
//  			
//			System.out.println(result);
//			
//			
////			
//			Image image1 = new Image();
//			System.out.println (usermapper.getImage(10004)) ;
//			image1 = usermapper.getImage(10004).get(0) ;
//			assertThat(image1.getImageNumber()).isEqualTo(10008);
//
//			System.out.println("Last addImage : " + image1 );
// 
// 
//			
//	 }	 
//	 
//}
////	Logger logger = LoggerFactory.getLogger(ProductMapperTest.class);
//
// 