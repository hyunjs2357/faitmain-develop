const entityMap = {
    '&': '&amp;',
    '<': '&lt;',
    '>': '&gt;',
    '"': '&quot;',
    "'": '&#039;',
    '/': '&#x2F;',
    '`': '&#x60;',
    '=': '&#x3D;',
    '{': '&#x7b;', // hex코드로 적어둠
    '}': '&#x7d;'
}; // hex코드로 적어둠

//태그 제한
function escapeHtml(string) {
    return String(string).replace(/[&<>"']/g, function(s) { return entityMap[s]; });
}

// 채팅 입력
function write(msg, tp, msgtype) {
    let cl = $('div.chat_contents div#content1');
    let cc;
    switch (tp) {
        case 'join':
            /*cc = $('<div>', { class: 'entery' });
            cc.append($('<span>').html('<b>' + escapeHtml(msg.nickName) + '</b>님이 입장하셨습니다.'));*/
            break;
        case 'leave':
            /*cc = $('<div>', { class: 'chatout' });
            cc.append($('<span>').html('<b>' + escapeHtml(msg.nickName) + '</b>님이 나가셨습니다.'));*/
            break;
        case 'notice':
            cc = $('<p>', { class: 'livenotice' });
            cc.append($('<span>').html(typeof msg == 'string' ? msg : msg.message));
            break;
        case 'market':
            cc = $('<p>', { class: 'marketer' });
            cc.append($('<span>').html(typeof msg == 'string' ? msg : msg.message));
            break;
        case 'html':
            cc = $('<P>').css({ "text-align": "center" });
            cc.append($('<span class="name">').html(msg));
            break;
        case 'ManagerWhisper':
            cc = $('<p class="admin_livecommerce">');
            cc.append($('<span class="name">').text(msg.nickName)); /* 0630 추가 */
            cc.append($('<span class="adm_whisper">').text("님의 귓속말"));
            cc.append(escapeHtml(msg.message));
            break;
        case 'allExit':
            cc = $('<div>', { class: 'entery' });
            cc.append($('<span>').html('<b>채팅방을 종료합니다.</b>'));
            break;
        case 'userManager':
            cc = $('<p class="admin_livecommerce">');
            if (typeof msg == 'string') {
                cc.append($('<span class="name">').text(''));
                // cc.append($('<span class="name" href="#!">').text(''));
                cc.append(escapeHtml(msg));
            } else if (typeof msg == 'object' && msg.message) {
                /*if (channel.clientKey != msg.clientKey) {
                    cc.append($('<span class="name" href="#!">').text(msg.nickName).data(msg).on({ click: openLayer }));
                } else {
                    cc.append($('<span class="name" href="#!">').text(msg.nickName));
                }*/
                cc.append($('<span class="name">').text(msg.nickName)); /* 0630 추가 */
                cc.append(escapeHtml(msg.message));
            }
            break;
        default:
            cc = $('<p>');
            if (typeof msg == 'string') {
                cc.append($('<span class="name">').text(''));
                // cc.append($('<span class="name" href="#!">').text(''));
                cc.append(escapeHtml(msg));
            } else if (typeof msg == 'object' && msg.message) {
                /*if (channel.clientKey != msg.clientKey) {
                    cc.append($('<span class="name" href="#!">').text(msg.nickName).data(msg).on({ click: openLayer }));
                } else {
                    cc.append($('<span class="name" href="#!">').text(msg.nickName));
                }*/
                cc.append($('<span class="name">').text(msg.nickName)); /* 0630 추가 */
                cc.append(escapeHtml(msg.message));
            }
    };
    if (msgtype == 'history') {
        cl.prepend(cc);
    } else {
        cl.append(cc);
    }
    $('div.chat_contents').scrollTop(cl.height());

}

$(function() {
    // 이모지 버튼
    /*$('div.bottom div.emoji a').click(function() {
        channel.sendMessage({
            message: $(this).text(),
            mimeType: 'emoji'
        });
    });*/

    // 글자수 제한
    $('#content').keyup(function(e) {
        if ($(this).text().length > 100) {
            openError("글자수는 100자로 이내로 제한됩니다.");
            $(this).text(($(this).text()).substring(0, 100));
        }
        $('#counter').html(($(this).text()).length + '/100');
    });
    $('#content').keyup();

    // 입력창 엔터
    $('#content').keydown(function(e) {
        if (e.keyCode == 13) {
            e.preventDefault();
            channel.sendMessage({
                message: $(this).text(),
                mimeType: "text"
            })
            $(this).text('');
        }
    })

    // 클릭 버튼
    $('#sendCounter').click(function(e) {
        channel.sendMessage({
            message: $('#content').text(),
            mimeType: "text"
        })
        $('#content').text('')
    })

    // 귓속말 팝업 위치
    /*$('.content1 .name').click(function(e) {
        var sWidth = window.innerWidth;
        var sHeight = window.innerHeight;

        var oWidth = $('.popupLayer').width();
        var oHeight = $('.popupLayer').height();

        var fWidth = $("#chat").offset().left;
        var fHeight = $("#chat").offset().top;

        var cHeight = $("#content1").height();

        console.log("cHeight :" + cHeight)

        // 레이어가 나타날 위치를 셋팅한다.
        var divLeft = e.clientX - fWidth;
        var divTop = e.clientY - fHeight;

        // 레이어가 화면 크기를 벗어나면 위치를 바꾸어 배치한다.
        if (divLeft + oWidth > sWidth) divLeft -= oWidth;
        if (divTop + oHeight > sHeight) divTop -= oHeight;

        if (divTop > (cHeight - oHeight)) {
            divTop = divTop - oHeight;
        }

        // 레이어 위치를 바꾸었더니 상단기준점(0,0) 밖으로 벗어난다면 상단기준점(0,0)에 배치하자.
        if (divLeft < 0) divLeft = 0;
        if (divTop < 0) divTop = 0;

        $('.popupLayer').css({
            "top": divTop,
            "left": divLeft,
            "position": "absolute",
            "z-index": 1
        }).show();
    });*/

    // 귓속말 팝업
    /*$(".whisper").click(function() {
        $("#whisper").show();
    })*/

    // 팝업 외 마우스 클릭 시 팝업 닫힘
    /*$(document).mouseup(function(e) {
        var container = $('.popupLayer');
        if (container.has(e.target).length === 0) {
            container.hide();
            $("#whisper").hide();
        }
    });*/

    // 특정 유저로 메시지 전송
    /* var popupLayer = $('div.popupLayer');
     var whisperLayer = $('ul.popup_content li:nth-child(1)', $('div.popupLayer'));

     popupLayer.close = function() {
         $('#whisper input[type=text][name=message]', whisperLayer).val('');
         $("#whisper", this).hide();
         $(this).hide();
     }

     $('button', whisperLayer).click(function(e) {
         channel.sendWhisper({
             message: $('input[type=text][name=message]', whisperLayer).val(),
             receivedClientKey: popupLayer.data()['clientKey']
         }, function(err, msg) {
             if (err)
                 return openError(err.code);
             write(msg, 'whisperto')
         })
         e.preventDefault();
         popupLayer.close();
     });

     // 신고하기
     $('ul.popup_content li:nth-child(2)', popupLayer).click(function(e) {
         // API 콜한다
         // https://vchatcloud.com/api/openapi/insertChatBanUser
         var url = "https://vchatcloud.com/api/openapi/insertChatBanUser";

         var param = {
             "room_id": popupLayer.data()['roomId'],
             "buser_nick": popupLayer.data()['nickName'],
             "buser_msg": popupLayer.data()['message'],
             "buser_chat_id": popupLayer.data()['clientKey']
         };

         console.log(param);

         $.post(url, param, function(data) {
             if (data.result_cd != 1) {
                 console.log("추방 실패")
             }
         }, "json");
         popupLayer.close();
         
     });*/

});

function chatInit() {
    // 신규 메시지 이벤트
    channel.onNotifyMessage = function(event) {
        if (event.grade == 'userManager') {
            write(event, 'userManager')
        } else {
            write(event)
        }
    }

    // 개인 귓속말 메시지 이벤트
    channel.onPersonalWhisper = function(event) {
        if (event.grade == 'userManager') {
            write(event, 'ManagerWhisper')
        } else {
            // write(event, 'whisper')
        }
    }
}

function personalInit() {

    // 중복 로그인시 이벤트
    channel.onPersonalDuplicateUser = function(event) {
        vChatCloud.disconnect();
        openError("중복 로그인으로 인해 로그아웃합니다.", function() {
            $('div.enterypopup').show();
            $('div.chat_field').hide();
        });
    }

    // 글쓰기 제한 이벤트
    channel.onPersonalMuteUser = function(event) {
        openError("글쓰기가 제한되었습니다.")
    }

    // 글쓰기 제한 해제 이벤트
    channel.onPersonalUnmuteUser = function(event) {
        openError("글쓰기 제한이 해제되었습니다.")
    }
}

function msgInit() {
    // 공지사항 메시지
    channel.onNotifyNotice = function(event) {
        write(event, 'notice')
    }

    // 유저 입장
    channel.onNotifyJoinUser = function(event) {
        if (channel.clientKey != event.clientKey) {
            write(event, 'join')
        }
    }

    // 유저 나감
    channel.onNotifyLeaveUser = function(event) {
        write(event, 'leave')
    }

    // 유저 추방
    channel.onNotifyKickUser = function(event) {
        write("'<font color='blue'><b>" + event.nickName + "</b></font>' 님이 채팅방에서 추방되었습니다.", "html");
    }

    // 유저 추방 해제
    channel.onNotifyUnkickUser = function(event) {
        write("'<font color='blue'><b>" + event.nickName + "</b></font>' 님이 채팅방에서 추방 해제되었습니다.", "html");
    }

    // 글쓰기 제한
    channel.onNotifyMuteUser = function(event) {
        write("'<font color='blue'><b>" + event.nickName + "</b></font>' 님의 글쓰기가 제한되었습니다.", "html");
    }

    // 글쓰기 제한 해제
    channel.onNotifyUnmuteUser = function(event) {
        write("'<font color='blue'><b>" + event.nickName + "</b></font>' 님의 글쓰기가 제한 해제되었습니다.", "html");
    }

    // 커스텀 메시지
    channel.onNotifyCustom = function(event) {
        // {"roomId":"QljHXvApdB-hSKvfNLD2y-20200902153935","message":"{\"msg\":\"이벤트에 응모하시겠습니까?\",\"msgType\":\"event\",\"type\":\"popup\"}","nickName":"운영자","clientKey":"","mimeType":"text","messageDt":"20200904162501","messageCount":17}
        console.log('onNotifyCustom', JSON.stringify(event.message))
        let custom = JSON.parse(event.message)
        if (custom.type == "allExit") {
            vChatCloud.disconnect() // 클라이언트에서 채팅방을 나갈수 있도록 한다.
            write(event, 'allExit')
            return;
        }
        try {
            if (event.type == "profile") {
                //profileJson[event.clientKey] = custom.profile
                return;
            }
            if (custom.type == "popup") {
                openPopup(custom.msg, function(val) {
                    console.log(val)
                }, false);
            } else if (custom.type == "market") {
                write(custom.msg, 'market')
            } else {
                openPopup(JSON.stringify(custom), function(val) {
                    console.log(val)
                }, true);
            }
        } catch (e) {
            openPopup(JSON.stringify(event.message), function(val) {
                console.log(val)
            }, true);
        }
    };
}

function openPopup(msg, callback, option) {
    let p = $('div.custompopup').hide();
    $('p:nth-child(1)', p).text(msg);
    $('a:nth-child(2)', p).off().click(function() { p.hide(); if (typeof callback == 'function') { callback("확인") } });
    if (option) {
        $('a:nth-child(3)', p).hide();
    } else {
        $('a:nth-child(3)', p).show();
        $('a:nth-child(3)', p).off().click(function() { p.hide(); if (typeof callback == 'function') { callback("취소") } });
    }
    p.show();
}