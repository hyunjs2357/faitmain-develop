function openProductView(productNumber){
	var win = window.open('/product/getProduct?productNumber='+productNumber, '_self');
	win.focus();
}

$(function() {
			
			let channelKey = $('#channelKey').val();
			let storeId = $('#storeId').val();
			
			vChatCloud = new VChatCloud();
			
			let remoteCam;
		    let channel, // joinRoom() 내부에서 채널 객체를 저장할 곳
		    userNick = 'xxxxxxxx'.replace(/[xy]/g, function(a, b) { return (b = Math.random() * 16, (a == 'y' ? b & 3 | 8 : b | 0).toString(16)) }); // 접속자의 닉네임, 사용자에게 입력받은 값을 사용해도 된다.
		    // 접속자 고유 키
			
		    function videoInit() {
					
		    	
		    	
		    	  // 로컬 접속 시
		    	channel.on("rtcRemoteStreamAppend", function (event) {
		    			 console.log(event.clientKey);
		    			 storeId = $('#storeId').val();
		    			if(event.clientKey == storeId){
				    	   	remoteVideo = document.createElement("video");
				    	    let stream = event.target;
				    	    remoteVideo.srcObject = stream;
				    	    remoteVideo.setAttribute("autoplay", true);
				    	    remoteCam.append(remoteVideo);
				    	    channel.setRTCRemoteMedia(remoteVideo);
				    		    
				    	    console.log('videoInit clear');
			    	    }
		    	 });
		    }

		    	// 공통 코드 =====================================
				
		    	// rtc
		    	

		    	window.addEventListener("load", function (){
		    	  	// 접속자의 미디어 소스를 보여줄 위치
		    	  
		    	  	console.log('addEventListener start');
		    	  	
		    	  	remoteCam = $('#remote_cam');
		    	  	remoteCam.attr('name', 'remote_cam');

					console.log(channelKey);
					console.log(storeId);
					
		    	    joinRoom(channelKey,  userNick, userNick, function(err, history){
		    	    	if(err){
		    	    		vChatCloud.disconnect();
		    	    		var src = '/img/intro/liveddd.jpg';
		    	    		remoteCam.append('<img src="'+src+'" style="width:904px; height:659px;"/>')
		    	    		$('article.title').hide();
		    	    			
		    	    		console.log('joinRoom error');
		    	    		
		    	    	}else{
		    	    		console.log('joinRoom clear');
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
		    	    		
		    	    		//이벤트 바인딩 시작
		    	    		
		    	    		getRoomInfo();
		    		
		    	    	};
		    	   		
		    		});
		    		
		    	});

		    	function joinRoom(roomId, clientKey, nickName, callback) {
		    	  // vchatcloud 객체
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
		    	        $("#roomNm").append(data.param.room_nm);
		    	      } else {
		    	        console.log("조회 실패");
		    	        oastPopup("조회 실패");
		    	      }
		    	    },
		    	    "json"
		    	  );
		    	}
		    
	
	/*===========================================================================================================*/
		    
	"use strict";

    /*[ Load page ]
    ===========================================================*/
    $(".animsition").animsition({
        inClass: 'fade-in',
        outClass: 'fade-out',
        inDuration: 1500,
        outDuration: 800,
        linkElement: '.animsition-link',
        loading: true,
        loadingParentElement: 'html',
        loadingClass: 'animsition-loading-1',
        loadingInner: '<div class="loader05"></div>',
        timeout: false,
        timeoutCountdown: 5000,
        onLoadEvent: true,
        browser: [ 'animation-duration', '-webkit-animation-duration'],
        overlay : false,
        overlayClass : 'animsition-overlay-slide',
        overlayParentElement : 'html',
        transition: function(url){ window.location.href = url; }
    });
    
    /*[ Back to top ]
    ===========================================================*/
    var windowH = $(window).height()/2;

    $(window).on('scroll',function(){
        if ($(this).scrollTop() > windowH) {
            $("#myBtn").css('display','flex');
        } else {
            $("#myBtn").css('display','none');
        }
    });

    $('#myBtn').on("click", function(){
        $('html, body').animate({scrollTop: 0}, 300);
    });


    /*==================================================================
    [ Fixed Header ]*/
    var headerDesktop = $('.container-menu-desktop');
    var wrapMenu = $('.wrap-menu-desktop');

    if($('.top-bar').length > 0) {
        var posWrapHeader = $('.top-bar').height();
    }
    else {
        var posWrapHeader = 0;
    }
    

    if($(window).scrollTop() > posWrapHeader) {
        $(headerDesktop).addClass('fix-menu-desktop');
        $(wrapMenu).css('top',0); 
    }  
    else {
        $(headerDesktop).removeClass('fix-menu-desktop');
        $(wrapMenu).css('top',posWrapHeader - $(this).scrollTop()); 
    }

    $(window).on('scroll',function(){
        if($(this).scrollTop() > posWrapHeader) {
            $(headerDesktop).addClass('fix-menu-desktop');
            $(wrapMenu).css('top',0); 
        }  
        else {
            $(headerDesktop).removeClass('fix-menu-desktop');
            $(wrapMenu).css('top',posWrapHeader - $(this).scrollTop()); 
        } 
    });


    /*==================================================================
    [ Menu mobile ]*/
    $('.btn-show-menu-mobile').on('click', function(){
        $(this).toggleClass('is-active');
        $('.menu-mobile').slideToggle();
    });

    var arrowMainMenu = $('.arrow-main-menu-m');

    for(var i=0; i<arrowMainMenu.length; i++){
        $(arrowMainMenu[i]).on('click', function(){
            $(this).parent().find('.sub-menu-m').slideToggle();
            $(this).toggleClass('turn-arrow-main-menu-m');
        })
    }

    $(window).resize(function(){
        if($(window).width() >= 992){
            if($('.menu-mobile').css('display') == 'block') {
                $('.menu-mobile').css('display','none');
                $('.btn-show-menu-mobile').toggleClass('is-active');
            }

            $('.sub-menu-m').each(function(){
                if($(this).css('display') == 'block') { console.log('hello');
                    $(this).css('display','none');
                    $(arrowMainMenu).removeClass('turn-arrow-main-menu-m');
                }
            });
                
        }
    });


    /*==================================================================
    [ Show / hide modal search ]*/
    $('.js-show-modal-search').on('click', function(){
        $('.modal-search-header').addClass('show-modal-search');
        $(this).css('opacity','0');
    });

    $('.js-hide-modal-search').on('click', function(){
        $('.modal-search-header').removeClass('show-modal-search');
        $('.js-show-modal-search').css('opacity','1');
    });

    $('.container-search-header').on('click', function(e){
        e.stopPropagation();
    });


    /*==================================================================
    [ Isotope ]*/
    var $topeContainer = $('.isotope-grid');
    var $filter = $('.filter-tope-group');

    // filter items on button click
    $filter.each(function () {
        $filter.on('click', 'button', function () {
            var filterValue = $(this).attr('data-filter');
            $topeContainer.isotope({filter: filterValue});
        });
        
    });

    // init Isotope
    $(window).on('load', function () {
        var $grid = $topeContainer.each(function () {
            $(this).isotope({
                itemSelector: '.isotope-item',
                layoutMode: 'fitRows',
                percentPosition: true,
                animationEngine : 'best-available',
                masonry: {
                    columnWidth: '.isotope-item'
                }
            });
        });
    });

    var isotopeButton = $('.filter-tope-group button');

    $(isotopeButton).each(function(){
        $(this).on('click', function(){
            for(var i=0; i<isotopeButton.length; i++) {
                $(isotopeButton[i]).removeClass('how-active1');
            }

            $(this).addClass('how-active1');
        });
    });

    /*==================================================================
    [ Filter / Search product ]*/
    $('.js-show-filter').on('click',function(){
        $(this).toggleClass('show-filter');
        $('.panel-filter').slideToggle(400);

        if($('.js-show-search').hasClass('show-search')) {
            $('.js-show-search').removeClass('show-search');
            $('.panel-search').slideUp(400);
        }    
    });

    $('.js-show-search').on('click',function(){
        $(this).toggleClass('show-search');
        $('.panel-search').slideToggle(400);

        if($('.js-show-filter').hasClass('show-filter')) {
            $('.js-show-filter').removeClass('show-filter');
            $('.panel-filter').slideUp(400);
        }    
    });




    /*==================================================================
    [ Cart ]*/
    $('.js-show-cart').on('click',function(){
        $('.js-panel-cart').addClass('show-header-cart');
    });

    $('.js-hide-cart').on('click',function(){
        $('.js-panel-cart').removeClass('show-header-cart');
    });

    /*==================================================================
    [ Cart ]*/
    $('.js-show-sidebar').on('click',function(){
        $('.js-sidebar').addClass('show-sidebar');
    });

    $('.js-hide-sidebar').on('click',function(){
        $('.js-sidebar').removeClass('show-sidebar');
    });

    /*==================================================================
    [ +/- num product ]*/
    $('.btn-num-product-down').on('click', function(){
        var numProduct = Number($(this).next().val());
        if(numProduct > 0) $(this).next().val(numProduct - 1);
    });

    $('.btn-num-product-up').on('click', function(){
        var numProduct = Number($(this).prev().val());
        $(this).prev().val(numProduct + 1);
    });

    /*==================================================================
    [ Rating ]*/
    $('.wrap-rating').each(function(){
        var item = $(this).find('.item-rating');
        var rated = -1;
        var input = $(this).find('input');
        $(input).val(0);

        $(item).on('mouseenter', function(){
            var index = item.index(this);
            var i = 0;
            for(i=0; i<=index; i++) {
                $(item[i]).removeClass('zmdi-star-outline');
                $(item[i]).addClass('zmdi-star');
            }

            for(var j=i; j<item.length; j++) {
                $(item[j]).addClass('zmdi-star-outline');
                $(item[j]).removeClass('zmdi-star');
            }
        });

        $(item).on('click', function(){
            var index = item.index(this);
            rated = index;
            $(input).val(index+1);
        });

        $(this).on('mouseleave', function(){
            var i = 0;
            for(i=0; i<=rated; i++) {
                $(item[i]).removeClass('zmdi-star-outline');
                $(item[i]).addClass('zmdi-star');
            }

            for(var j=i; j<item.length; j++) {
                $(item[j]).addClass('zmdi-star-outline');
                $(item[j]).removeClass('zmdi-star');
            }
        });
    });
    
    /*==================================================================
    [ Show modal1 ]*/
    $('.js-show-modal1').on('click',function(e){
        e.preventDefault();
        $('.js-modal1').addClass('show-modal1');
    });

    $('.js-hide-modal1').on('click',function(){
        $('.js-modal1').removeClass('show-modal1');
    });	
		
});