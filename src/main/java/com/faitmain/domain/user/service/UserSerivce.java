package com.faitmain.domain.user.service;

import com.faitmain.domain.user.domain.StoreApplicationDocument;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Paging;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface UserSerivce{

// 신청서 리스트 
	public List<StoreApplicationDocument> getStoreApplicationDocumentList(Paging paging) throws Exception;
 
// 신청서 총 개수
    /* 주문 총 개수 */
	public int getStoreApplicationDocumentListTotal(Paging paging) throws Exception;

	
 
	// 신청서 리스트 
		public List<User> getUserList(Paging paging) throws Exception;
	 
	// 신청서 총 개수
	    /* 주문 총 개수 */
		public int getUserListTotal(Paging paging) throws Exception;

		
	
	
	
	//  insert 유저
	public int addUser( User user ) throws Exception;

	// insert 스토어
	public int addStore( User user ,  MultipartHttpServletRequest mRequest) throws Exception;

	
    //insert 신청서
	public int AddStoreApplicationDocument( StoreApplicationDocument storeApplicationDocument ) throws Exception;
 
    ////////////////////////////////select////////////////////////////////////////////////////
    //SELECT 로그인
	public int getLogin( User user ) throws Exception;

    //SELECT  유저 상세 조회
	public User getUser( String id ) throws Exception ;
    // SELECT 아이디/PW 찾기 할때 사용하는 findUser
	public int findUser( Map<String, Object> hasMap ) throws Exception;

    // SELECT 아이디/PW 찾기 할때 사용하는 findUser
	public User findGetIdPw( Map<String, Object> hasMap ) throws Exception;


    //SELECT 충복체크
	public int getchechDuplication( Map<String, Object> Map ) throws Exception;

    //SELECT id로 스토어 신청서 넘버 가져오기
	public int getStoreApplicationDocumenNumber( String id ) throws Exception;

    //SELECT 스토어 신청서 넘버로 스토어 가져오기
	public StoreApplicationDocument getStoreApplicationDocument( int StoreApplicationDocumenNumber ) throws Exception;


//    //SELECT 스토어 이미지 조회
//	List<Image> getImageList( int storeApplicationDocumentNumber ) throws Exception;
//
//    //SELECT USER 리스트 조회
//	Map<String, Object> getUserList( Map<String, Object> map ) throws Exception;
//
//    //SELECT 스토어 리스트 조회
//	Map<String, Object> getStoreApplicationDocumentList( Map<String, Object> map ) throws Exception;


	//public List<User> getlist( Map<String, Object> map ) throws Exception;

	
    ////////////////////////////////UPDATE////////////////////////////////////////////////////

    //유저 UPDATE - 유저 상태 update
	public int updateUser( User user ,  MultipartHttpServletRequest mRequest) throws Exception;

	public int updateUser( User user  ) throws Exception;
	
	
    //  UPDATE - 스토어로 업데이트

    int updateUserStore( Map<String, Object> map ) throws Exception;

    // UPDATE Password 재설정
	int updateUserPassword( User user ) throws Exception;

    // UPDATE 스토어문서 상태 examination_status
	int updateStoreApplicationDocument( StoreApplicationDocument storeApplicationDocument ) throws Exception;


    ////////////////////////////////delete////////////////////////////////////////////////////
 	
	

   
    int deleteUser( String id ) throws Exception;


    //문자 보내기
	void sendCertificationSms( String userPhoneNumber , int randomNumber ) throws Exception;

    //문자 인증
	void certifiedPhoneNumber( String userPhoneNumber , int smsCertification ) throws Exception;

    // 카카오 토큰 받기
	String getAccessToken( String authorize_code ) throws Exception;

    //카카오 정보 받아오기
	HashMap<String, Object> getUserInfo( String access_Token ) throws Exception;


}
