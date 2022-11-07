// 채널에 접속하기 위해서 사용자의 userNick, userKey와 CMS에서 발급받은 channelKey가 필요합니다.



//해당하는 채팅방의 채널키 가져오기 .22-06-12.
//let query = window.location.search;
//let param = new URLSearchParams(query);
//let roomId = param.get('roomId');

var channelKey = "PaMxkbiFMY-a9vKJcy17y-20220610185606"; // CMS에서 발급받은 키 값, 발급 받은 키 값을 입력해보세요!


function videoInit() {
	// 로컬 접속 종료 시
	channel.on('rtcLocalStreamRemove', function(event){
		let html = $('.my_cam > video');
		html.remove();
	})
  // 로컬 접속 시
  channel.on("rtcLocalStreamAppend", function (event) {
     	myVideo = document.createElement("video");
	    let stream = event.target;
	    myVideo.srcObject = stream;
	    myVideo.setAttribute("autoplay", true);
	    myCam.append(myVideo);
	    channel.setRTCLocalMedia(myVideo);
  });
}

// 공통 코드 =====================================
const vChatCloud = new VChatCloud();

let channel, // joinRoom() 내부에서 채널 객체를 저장할 곳
  userNick = $('#storeNickname').val(), // 접속자의 닉네임, 사용자에게 입력받은 값을 사용해도 된다.
  userKey; // 접속자 고유 키



// rtc
let myCam, remoteCamList;

window.addEventListener("load", function () {
  	// 접속자의 미디어 소스를 보여줄 위치
  
  	 c = $('div.chat_contents').hide();
  	likeInif();
  
  	myCam = $('.my_cam');
  	myCam.attr('name', 'my_cam');

    $('div.bottom div.name').text(userNick);
    
    joinRoom(channelKey,  'xxxxxxxx'.replace(/[xy]/g, function(a, b) { return (b = Math.random() * 16, (a == 'y' ? b & 3 | 8 : b | 0).toString(16)) }), userNick, function(err, history){
    	if(err){
    		openError(err.code, function(){
    			c.hide();
    			vChatCloud.disconnect();
    		});
    		c.show();
    		
    	}else{
    		videoInit();
    		//채팅영역에 글쓰기가 활성화될시 활성화(최신공지 한개만 남기기)
    		let flag = undefined;
    		if(typeof write == 'function') history && history.forEach(function(m){
    			if(m.messageType == 'notice'){
    				if(flag == undefined){
    					flag = true;
    					write(m, 'notice', 'history');
    				}
    			}else{
    				write(m, '', 'history');
    			}
    		});
    			
    		c.show();
    	
   			//이벤트 바인딩 시작
   			chatInit();
   			personalInit();
   			msgInit();
   			getRoomInfo();
   			likeInif();
   		}	
   	});
})
	
function joinRoom(roomId, clientKey, nickName, callback) {
  // vchatcloud 객체
  console.log("clientKey : " + clientKey);
  channel = vChatCloud.joinChannel(
    {
      roomId: roomId,
      clientKey: clientKey,
      nickName: nickName
    },
    function (error, history) {
      $('div#content1 p').remove();
      if (error) {
        if (callback) return callback(error, null);
        return error;
      }
      if (callback) callback(null, history);
      // 채팅영역에 글쓰기가 활성화될시 활성화
      if (typeof write == "function")
        write(
          "실시간 채팅에 오신 것을 환영합니다. 개인정보를 보호하고 커뮤니티 가이드를 준수하는 것을 잊지 마세요!",
          "notice"
        );
    }
  );
}


function getRoomInfo() {
  const api_url = "https://vchatcloud.com/api/openapi/getChatRoomInfo";
  let param = {
    room_id: channelKey
  };
  $.post(
    api_url,
    param,
    function (data) {
      if (data.result_cd == 1) {
        console.log(data);
        // $("#roomNm").append(data.param.room_nm);
      } else {
        console.log("조회 실패");
        oastPopup("조회 실패");
      }
    },
    "json"
  );
}

function openError(code, callback) {
  let p = $("div.errorpopup").hide();
  if (errMsg[code] == undefined) {
    $("p:nth-child(2)", p).text(code);
  } else {
    $("p:nth-child(2)", p).text(errMsg[code].kor);
  }
  $("a", p)
    .off()
    .click(function () {
      p.hide();
      if (typeof callback == "function") {
        callback();
      }
    });
  p.show();
}

// 마이크 온/오프
function mic_on_off(item) {
  if (channel) {
    var chk = $(item).attr("class");
    var img = $(item).children("img")[0];
    var cam_mic = $("div[name=my_cam]").children("img")[0];
    if (chk == "mic btn_on") {
      // 마이크 끄기
      channel.toggleRTCAudioControl(false);
      $(item).attr("class", "mic btn_off");
      $(img).attr(
        "src",
        "https://www.vchatcloud.com/chat-demo/iframe/iframe_rtc_1/img/webRTC/off_mic.png"
      );
      $(cam_mic).show();
    } else {
      // 마이크 켜기
      channel.toggleRTCAudioControl(true);
      $(item).attr("class", "mic btn_on");
      $(img).attr(
        "src",
        "https://www.vchatcloud.com/chat-demo/iframe/iframe_rtc_1/img/webRTC/on_mic.png"
      );
      $(cam_mic).hide();
    }
  } else {
    // 로그인이 필요함
  }
}

// 카메라 온/오프
function cam_on_off(item) {
  if (channel) {
    var chk = $(item).attr("class");
    var img = $(item).children("img")[0];
    var video = $("div[name=my_cam]").children("div.camvideo")[0];

    if (chk == "cam btn_on") {
      // 카메라 끄기
      channel.toggleRTCVideoControl(false);
      $(item).attr("class", "cam btn_off");
      $(img).attr(
        "src",
        "https://www.vchatcloud.com/chat-demo/iframe/iframe_rtc_1/img/webRTC/off_cam.png"
      );
    } else {
      // 카메라 끄기
      channel.toggleRTCVideoControl(true);
      $(item).attr("class", "cam btn_on");
      $(img).attr(
        "src",
        "https://www.vchatcloud.com/chat-demo/iframe/iframe_rtc_1/img/webRTC/on_cam.png"
      );
      // 카메라 켜짐
    }
  } else {
    // 미 로그인 상태
  }
}

function exit() {
  if (channel) {
    var exit_chk = confirm("종료 하시겠습니까?");
    if (!exit_chk) return;
    $(".cam-footer .cam-btn .mic").off("click.rtc");
    $(".cam-footer .cam-btn .cam").off("click.rtc");
    vChatCloud.disconnect();
    channel = undefined;
  } else {
    // 로그인 되지 않았음!
  }
}

// msgInit() 부분은 login.js에 joinRoom 이 성공한 이후에 실행하도록 한다
function msgInit() {
    // 공지사항 메시지
    channel.onNotifyNotice = function(event) {
        write(event, 'notice')
    }

    // 유저 입장
    channel.onNotifyJoinUser = function(event) {
        write(event, 'join')
    }

    // 유저 나감
    channel.onNotifyLeaveUser = function(event) {
        write(event, 'leave')
    }

    // 유저 추방
    channel.onNotifyKickUser = function(event) {
        write("'<font color='blue'><b>" + event.nickName + "</b></font>' 님이 채팅방에서 추방되었습니다.");
    }

    // 유저 추방 해제
    channel.onNotifyUnkickUser = function(event) {
        write("'<font color='blue'><b>" + event.nickName + "</b></font>' 님이 채팅방에서 추방 해제되었습니다.");
    }

    // 글쓰기 제한
    channel.onNotifyMuteUser = function(event) {
        write("'<font color='blue'><b>" + event.nickName + "</b></font>' 님의 글쓰기가 제한되었습니다.");
    }

    // 글쓰기 제한 해제
    channel.onNotifyUnmuteUser = function(event) {
        write("'<font color='blue'><b>" + event.nickName + "</b></font>' 님의 글쓰기가 제한 해제되었습니다.");
    }
}