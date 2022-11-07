package com.faitmain.global.util.security;

import com.faitmain.domain.user.domain.User;
import com.faitmain.domain.user.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//UserDetailsService 를 구현 함 
//UserDetailsService는 비지니스 메서드 사용 가능 
@Service
@RequiredArgsConstructor

public class SecurityUserDetailService implements UserDetailsService{
// UserDetailsService , username(id)를 가지고 사용자 정보를 조회하고, sesseion에 저장될 UserDetails를 반환
	
	
    @Autowired
    UserMapper usermapper;

	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
     	//loadUserByUsername는 DB에 접근해서 사용자 정보를 가져오는 역활을 합니다. 
        //여기서 스프링 시큐리티 자체에서 받은 유저 패스워드와 비교하여 로그인 인증을 수행
			//UserDetails에 authorities가 세팅되어 있어야, API별 role이나 권한 체크를 진행할 수 있다. ?? 아 님아? 저 다시해야함?  음,, 다시 해보다 ,,, 잠시 타임 
		
		
		System.out.println("id :: + " + id) ;
		User user = usermapper.getUser(id) ;
		if(user.getId() == null) {
            throw new UsernameNotFoundException("사용자가 존재하지 않음");
            
		}  
		
			SecurityUserService loginDetail = new SecurityUserService(); // DB에 유저 정보를 확인하고 있다면 , loginDetail에 User를 담아서 반환하며 시큐리티 세션에 저장
			if( user.getWithdrawalStatus()) {
			
        		throw new DisabledException("탈퇴한 회원입니다.");

			}
			
			loginDetail.setUser( user ); // 유저 디테일에 user 던짐
             
 	           System.out.println("유저 있음");
        
//		//	            	return loginDetail;
//         
		return new SecurityUserService(user);
	}

	
	
//    @Override
//    public UserDetails loadUserByUsername( String username ) throws UsernameNotFoundException{
//    	//loadUserByUsername는 DB에 접근해서 사용자 정보를 가져오는 역활을 합니다. 
//    	// 회원 찾아주는 로직 
//        try {
//    	System.out.println("username :: + " + username) ;
//    	User user = usermapper.getUser( username );
//    	System.out.println("user :: + " + user) ;
//
//	        if (   user.getId() != null ) { // DB에 유저 정보를 확인하고 있다면 , loginDetail에 User를 담아서 반환하며 시큐리티 세션에 저장  
//	        	
//	        	
//	        
//			//	            LoginService loginDetail = new LoginService(user);
//			//	            loginDetail.setUser( user ); // 유저 디테일에 user 던짐
//			//	            	System.out.println("유저 있음");
//			//	       
//			//	            	return loginDetail;
//             
//	        	
//	        	return  new LoginService(user);
//	        	
//	        		}else {
//	        			
//		            	System.out.println("유저 없");
//	                    throw new UsernameNotFoundException( "유저없음" );
//        			
//	        		}
//	        
//	        
//        }catch ( Exception e ) {
//            throw null ;
//        }
//
//    }
    
    
}
