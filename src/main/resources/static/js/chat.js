function msgInit() {
    // 공지사항 메시지
    channel.onNotifyNotice = function(event) {
        write(event, 'notice')
    }

    // 유저 입장
    channel.onNotifyJoinUser = function(event) {
        write(event, 'join')
        if( event.nickName != "" ){
        write(event.nickName + "님이 입장하셨습니다.");
        }
        call_chlid()
        
    }

    // 유저 나감
    channel.onNotifyLeaveUser = function(event) {
        write(event, 'leave')
        write(event.nickName + "님이 퇴장하셨습니다.");
        call_chlid()

    }

    // 유저 추방
    channel.onNotifyKickUser = function(event) {
        write( event.nickName + "님을 채팅방에서 추방하였습니다." );
        call_chlid()
        console.log("갱신완료")

    }

    // 유저 추방 해제
    channel.onNotifyUnkickUser = function(event) {
        write( event.nickName + "님의 추방 상태를 해제하였습니다.");
    }

    // 글쓰기 제한
    channel.onNotifyMuteUser = function(event) {
        write( event.nickName + "님의 채팅을 제한하였습니다." ) ;
    }

    // 글쓰기 제한 해제
    channel.onNotifyUnmuteUser = function(event) {
        write( event.nickName + "님의 채팅 제한을 해제했습니다.");
    }
}