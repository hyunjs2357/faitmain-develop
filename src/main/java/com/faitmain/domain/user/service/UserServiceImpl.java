package com.faitmain.domain.user.service;

import com.faitmain.domain.user.domain.StoreApplicationDocument;
import com.faitmain.domain.user.domain.User;
import com.faitmain.domain.user.mapper.UserMapper;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Paging;

import lombok.extern.slf4j.Slf4j;
import net.nurigo.java_sdk.api.Message;
import net.nurigo.java_sdk.exceptions.CoolsmsException;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service( "userServiceImpl" )
@Transactional
public class UserServiceImpl implements UserSerivce{

	@Value("${upload-path}")  
	// application.properties  파일올라가는 경로 , 
	private String fileStorageLocation;
	
	
    @Autowired
    private UserMapper userMapper;

//
//	 @Autowired
//	 private PasswordEncoder pwdEncoder;	   
// 


    /* ********************************************** */


    public void setUserMapper( UserMapper userMapper ){
        this.userMapper = userMapper;
    }

    //로그인ㅇ
    public int getLogin( User user ) throws Exception{
        return userMapper.getLogin( user );
    }


    //  insert 유저
    public int addUser( User user ) throws Exception{
        // TODO Auto-generated method stub
        return userMapper.addUser( user );
    }
	public int addStore( User user ,  MultipartHttpServletRequest mRequest) throws Exception{
		
		MultipartFile storeLogo =mRequest.getFile("LogoImage");
		log.info("addStore fileStorageLocation ={}" ,  fileStorageLocation );		
		
		if(!storeLogo.isEmpty()) {
			log.info("addStore  로고사진 " );		
				String storeLogoFileName = addFile(storeLogo) ;
				log.info(":::storeLogoFileName ={}", storeLogoFileName);
				user.setStoreLogoImage(storeLogoFileName);
		}
		log.info("addStore  {} " , user );		
		
		
		return userMapper.addUser(user);
		
	}


    //insert 신청서
    public int AddStoreApplicationDocument( StoreApplicationDocument storeApplicationDocument ) throws Exception{
        // TODO Auto-generated method stub
        return userMapper.addStoreApplicationDocument( storeApplicationDocument );
    }
    //insert  이미지

 

    //SELECT  유저 상세 조회
    public User getUser( String id ) throws Exception{
        return userMapper.getUser( id );
    }


    // SELECT 아이디/PW 찾기 할때 사용하는 findUser
    public int findUser( Map<String, Object> Map ) throws Exception{
         return userMapper.findUser( Map );
    }

    //이것은 아이디,휴대폰,닉네임,스토어네임 중복 체크시 사용
    public int getchechDuplication( Map<String, Object> map ) throws Exception{
        // TODO Auto-generated method stub
        return userMapper.getchechDuplicationCount( map );
    }

    public User findGetIdPw( Map<String, Object> map ) throws Exception{

        return userMapper.findGetIdPw( map );
    }


    //SELECT id로 스토어 신청서 넘버 가져오기
    public int getStoreApplicationDocumenNumber( String id ) throws Exception{

        return userMapper.getStoreApplicationDocumentNumber( id );
    }

    //SELECT 스토어 신청서 넘버로 스토어 가져오기  신청서 + 이미지
    public StoreApplicationDocument getStoreApplicationDocument( int StoreApplicationDocumenNumber )
            throws Exception{
        // TODO Auto-generated method stub

        StoreApplicationDocument storeDoc = userMapper.getStoreApplicationDocument( StoreApplicationDocumenNumber );
//        System.out.println( "Impl" );
//        List<Image> list = userMapper.getImage( StoreApplicationDocumenNumber );
//        System.out.println( list );
//        storeDoc.setProductmanufacturingImage( list );

        System.out.println( "스토어 신청서 출력 출력 " + storeDoc );

        return storeDoc;
    }


 
//
//    //SELECT USER 리스트 조회
//    public Map<String, Object> getUserList( Map<String, Object> map ) throws Exception{
//        List<User> list;
//        list = userMapper.getUserList( map );
//
//        int totalCount = userMapper.getUserTotalCount( map );
//
//
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put( "list" , list );
//        resultMap.put( "totalCount" , new Integer( totalCount ) );
//
//
//        return resultMap;
//
//    }
//
//    //SELECT 스토어 리스트 조회
//    public Map<String, Object> getStoreApplicationDocumentList( Map<String, Object> map ) throws Exception{
//        List<StoreApplicationDocument> list;
//        list = userMapper.getStoreApplicationDocumentALLList( map );
////        int totalCount = userMapper.getStoreApplicationDocumenTotalCount( map );
//
//        Map<String, Object> resultMap = new HashMap<String, Object>();
//        resultMap.put( "list" , list );
////        resultMap.put( "totalCount" , new Integer( totalCount ) );
//
//
//        return resultMap;
//    }


    ////////////////////////////////UPDATE////////////////////////////////////////////////////

    //유저 UPDATE - 유저 상태 update

    public int updateUser( User user,  MultipartHttpServletRequest mRequest ) throws Exception{
    	
		MultipartFile storeLogo =mRequest.getFile("LogoImage");
		log.info("addStore fileStorageLocation ={}" ,  fileStorageLocation );		
		
		if(!storeLogo.isEmpty()) {
			log.info("addStore  로고사진 " );		
				String storeLogoFileName = addFile(storeLogo) ;
				log.info(":::storeLogoFileName ={}", storeLogoFileName);
				user.setStoreLogoImage(storeLogoFileName);
		}
		log.info("addStore  {} " , user );		
		
    	
    	
        return userMapper.updateUser( user );
    }

    
	public int updateUser( User user  ) throws Exception{
		log.info("update Use={} ",  user );		

		  return userMapper.updateUser( user );
		
	}

    
    
    
    

    //패스워드 재설정
    public int updateUserPassword( User user ) throws Exception{
     	
 
        
    	
        return userMapper.updateUserPassword( user );
    }
    //  UPDATE - 스토어로 업데이트

    public int updateUserStore( Map<String, Object> map ) throws Exception{
         return userMapper.updateUserStore( map );
    }

    // UPDATE 스토어문서 상태 examination_status
    public int updateStoreApplicationDocument( StoreApplicationDocument storeApplicationDocument ) throws Exception{
         return userMapper.updateStoreApplicationDocument( storeApplicationDocument );
    }


//    // 거의 안쓸듯
//    public void deleteUser( String id ) throws Exception{
//
//        userMapper.deleteUser( id );
//    }


    public int deleteUser( String id ) throws Exception{

       return userMapper.deleteUser( id );


    }


    //네이버 로그인시 토큰 가져와
    public String getAccessToken( String authorize_code ) throws Exception{
         return null;
    }

    //네이버 로그인시 아이디에 대한 정보를 가져옴
    public HashMap<String, Object> getUserInfo( String access_Token ) throws Exception{
         return null;
    }


    //인증 문자 보내기
    public void sendCertificationSms( String userPhoneNumber , int randomNumber ) throws Exception{

        log.info( "certifiedPhoneNumber 에옴  {}" , userPhoneNumber );
        log.info( "randomNumber 에옴  {}" , randomNumber );


        //나의 API 키
//        String api_key = "NCSX1AN2GVPGAKYQ";
//        String api_secret = "VU56XMOI4OLSANYT4OD1LQJUVNOSS9KN";
        //친구가 공유한  API 키
      String api_key = "NCSFLNAKPLATWT5U";
      String api_secret = "UQHE4HDGLZ99FWYC4YHSECRYKMLHGVZI";
        Message coolsms = new Message( api_key , api_secret );
        HashMap<String, String> map = new HashMap<String, String>();
        map.put( "to" , userPhoneNumber );
        // 수신전화번호
        map.put( "from" , "01080077545" );
        // 발신전화번호. 테스트시에는 발신,수신 둘다 본인 번호로 하면 됨
        map.put( "type" , "SMS" );
        map.put( "text" , "[Fait-Main] 인증번호는" + "[" + randomNumber + "]" + "입니다." );
        // 문자 내용 입력
        map.put( "app_version" , "test app 1.2" );
        // application name and version
        try {
            JSONObject obj = coolsms.send( map );
            System.out.println( obj.toString() );

        } catch ( CoolsmsException e ) {
            System.out.println( e.getMessage() );
            System.out.println( e.getCode() );
            e.printStackTrace();
        }


    }

    //문자 인증
	@Override
	public void certifiedPhoneNumber(String userPhoneNumber, int smsCertification) throws Exception {
 		
	}
    
	public String addFile(MultipartFile file) throws Exception {
		
	// SimpleDateFormat 형식 지정  
		//System.currentTimeMillis() 사용해서 현재 시간 출력 
		SimpleDateFormat time = new SimpleDateFormat ( "yyyyMMddHHmmss");
		String timeStamp = time.format (System.currentTimeMillis()) ;
 
		String originalFileName = file.getOriginalFilename() ;// 원래 파일명
		String extension = originalFileName.substring(originalFileName.lastIndexOf(".")) ; 
		// 확장자  가져오기  원래 파일 이름 에서 뒤 . 기분으로 잘라서 확장자 리턴 
		
		String fileName = timeStamp + UUID.randomUUID()+ extension;
		//   String safeFile = fileStorageLocation + fileName;
		  // file.transferTo(new File(safeFile));
	 
            // inputStream을 가져와서
            // targetLocation (저장위치)로 파일을 쓴다.
            // copy의 옵션은 기존에 존재하면 REPLACE(대체한다), 오버라이딩 한다
     
		
		// Path 는 지경지정
		// toAbsolutePath 메서드는 상대 경로를 절대 경로로 변경
		// resolve는 고정 경로에 지정 경로를 추가
		Path targetLocation = (Paths.get(fileStorageLocation).toAbsolutePath()).resolve(fileName);
		System.out.println("targetLocation : " + targetLocation);
		Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
		// file.getInputStream()  에 있는 것을 복사
		
        ///    File destination = new File(fileStorageLocation + File.separator + fileName );
        //    file.transferTo(destination); // 사진을 메모리에 저장 함 
           
 		log.info("full 파일 이름 = {}" ,fileName);
		
		
		return fileName ;
	}

	 
 /*
	public List<User> getlist(Map<String, Object> map) throws Exception {
 		return userMapper.getlist(map);
	}
*/


	@Override
	public List<StoreApplicationDocument> getStoreApplicationDocumentList(Paging paging) throws Exception {
 		return userMapper.getStoreApplicationDocumentList(paging);
	}

	public int getStoreApplicationDocumentListTotal(Paging paging) throws Exception {
 		return userMapper.getStoreApplicationDocumentListTotal(paging);
  

	}

	@Override
	public List<User> getUserList(Paging paging) throws Exception {
 		return userMapper.getUserList(paging);
	}

	@Override
	public int getUserListTotal(Paging paging) throws Exception {
 		return userMapper.getUserListTotal(paging);
	}

	 

}