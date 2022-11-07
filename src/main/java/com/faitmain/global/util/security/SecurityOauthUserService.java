package com.faitmain.global.util.security;

import com.faitmain.domain.user.domain.User;
import com.faitmain.domain.user.mapper.UserMapper;
import com.faitmain.global.util.security.userinfo.NaverUserInfo;
import com.faitmain.global.util.security.userinfo.OAuth2UserInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor

public class SecurityOauthUserService extends DefaultOAuth2UserService{

//후처리	 
//	    @Autowired
//	    @Qualifier( "userServiceImpl" )
//	    private UserSerivce userSerivce;

    //
    @Autowired
    UserMapper usermapper;

//		 @Autowired
//		   PasswordEncoder pwdEncoder;	

    //public OAuth2User loadUser(OAuth2UserRequest userRequest ,    HttpServletResponse response ,HttpSession session) throws OAuth2AuthenticationException, IOException {

    @Override
    public OAuth2User loadUser( OAuth2UserRequest userRequest ) throws OAuth2AuthenticationException{


        OAuth2User oAuth2User = super.loadUser( userRequest );

        String provider = userRequest.getClientRegistration().getRegistrationId();  //네이버 카카오 등등 나옴 
        System.out.println( "provider : " + provider ); // 어디서 왔니
        log.info( "getClientRegistaraton ={}" , userRequest.getClientRegistration() );
        log.info( "getClientRegistaraton ={}" , userRequest.getAccessToken() );
        log.info( "getAttrivutes ={}" , super.loadUser( userRequest ).getAttributes() );

//        리턴받은 네이버가 준 reponse 값 
//       	respone = {  resultcode ==0 ,
//        			message=success,
//        			reponse =response={id=NOSpZNg2hsE6HxUP6TGjrzGmgWDpuoTNeSfrm6NckTc, email=ff@naver.com, name=양지안}}  }  
//        

        OAuth2UserInfo oAuth2UserInfo = null;    //추가
        String provider2 = userRequest.getClientRegistration().getRegistrationId();


        User user = null;
        log.info( "provider2 ={}" , provider2 );
        if ( provider.equals( "naver" ) ) {
            oAuth2UserInfo = new NaverUserInfo( oAuth2User.getAttributes() );   // NaverUserInfo 에 어트리뷰트 던지면 받아서 쇽쇽쇽 값 떠내서 수납해줌

            log.info( "oAuth2UserInfoe ={}    " , oAuth2UserInfo );
            log.info( "네이버 지원 ={}    " );

            String snsUserid = oAuth2UserInfo.getEmail();
            log.info( "snsUser ={}    " , snsUserid );


            String providerId = oAuth2UserInfo.getProviderId();    //수정
            String password = provider + "_" + snsUserid;     // 암호화 예정

            user = usermapper.getUser( snsUserid );

            System.out.println( "user" + user );
            //String userNickName = (oAuth2UserInfo.getProviderId())+ "_"+ ( int ) ( ( Math.random() * ( 9999 - 1000 + 1 ) ) + 1000 ); 
            String userNickName = ( ( int ) ( Math.random() * ( 9999 - 1000 + 1 ) ) + 1000 ) + "_" + ( ( int ) ( Math.random() * ( 9999 - 1000 + 1 ) ) + 1000 );
            log.info( "userNickName ={}    " , userNickName );

            if ( user == null ) {
                System.out.println( "유저 없음" );
                User user2 = new User();
                user2.setId( snsUserid );
                user2.setName( oAuth2UserInfo.getName() );

                user2.setPassword( password );
                user2.setNickname( userNickName );
                user2.setRole( "user" );
                user2.setJoinPath( "NAVER" );

                int result = usermapper.addUser( user2 );

                log.info( "restut {} = 1일때 회원가입 완료" , result );

                user = user2;
            }

        } else if ( provider.equals( "kakao" ) ) {


        }


        log.info( "로그인 완료  ={}    " );


        return new SecurityUserService( user , oAuth2UserInfo );


    }


}
