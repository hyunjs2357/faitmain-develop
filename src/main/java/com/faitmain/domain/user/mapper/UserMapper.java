package com.faitmain.domain.user.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.faitmain.domain.user.domain.StoreApplicationDocument;
import com.faitmain.domain.user.domain.User;
import com.faitmain.global.common.Image;
import com.faitmain.global.common.Paging;
import com.faitmain.global.common.Search;


@Mapper

public interface UserMapper{

	//유저 insert 
	int addUser( User user );

	//insert 신청서
	int addStoreApplicationDocument( StoreApplicationDocument storeApplicationDocument );



	int getLogin( User user );

	//SELECT  유저 상세 조회 
	User getUser( String id );

 
	//SELECT 아이디/PW 찾기 할때 사용하는 findUser
	int findUser( Map<String, Object> hashMap );

	User findGetIdPw( Map<String, Object> map );

	//SELECT 충복체크
	int getchechDuplicationCount( Map<String, Object> map );

	//SELECT id로 스토어 신청서번호 가져오기
	int getStoreApplicationDocumentNumber( String id );

	//SELECT 스토어 신청서번호로, 스토어 가져오기
	StoreApplicationDocument getStoreApplicationDocument( int StoreApplicationDocumenNumber );


	//유저 UPDATE - 유저 상태 update
	int updateUser( User user );

	//  UPDATE - 스토어로 업데이트
	int updateUserStore( Map<String, Object> map );

	// UPDATE Password 재설정
	int updateUserPassword( User user );

	// UPDATE 스토어문서 상태 examination_status
	int updateStoreApplicationDocument( StoreApplicationDocument storeApplicationDocument );


	//update 회원탈퇴시 false ,true값 비교
	int deleteUser( String id );

 
	//StoreApplicationDocument List  LIST에 사용 
	List<StoreApplicationDocument> getStoreApplicationDocumentList(Paging paging);
	
	int getStoreApplicationDocumentListTotal(Paging paging);

	//User List 

	List<User> getUserList(Paging paging);

	int getUserListTotal(Paging paging);


//	 //토탈카운트
//	 int getUserTotalCount(Search search) ;
 
	/*	
	//SELECT USER 리스트 조회
	List<User> getUserList( Map<String, Object> map );

	
	 List<User> getlist(Map<String, Object> map ) ;	


	//SELECT 스토어 신청서 total Count
	int getStoreApplicationDocumenTotalCount( Map<String, Object> map );

	//SELECT 스토어 리스트 조회
	List<StoreApplicationDocument> getStoreApplicationDocumentALLList( Map<String, Object> map );

	//SELECT  유저 total Count
	int getUserTotalCount( Map<String, Object> map );

*/
}
