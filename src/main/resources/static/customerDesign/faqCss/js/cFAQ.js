/*<p>Pagination part? borrowed from <a href='/JoshBlackwood/'>Joshua Blackwood</a>'s Pen <a href='/JoshBlackwood/pen/yoLBJ/'>yoLBJ</a>.</p>*/

var accordWithPage =  function() {
  
 var faqDiv = $('#faq-links div');
  

$(function () {
  
faqDiv.on("click", function() {
    
  var hideSec = 'faq-hide';  
      var $this = $(this),
      $id = $this.attr('id'),
      $class = '.' + $('.about-' + $id).attr('class').replace(hideSec, '');

  $('#faq-wrapper').addClass(hideSec);
  $('.about-' + $id).removeClass(hideSec);
  $('div[class*=about]').not($class).addClass(hideSec);
     
 });

});
$(function () {
  
        var select = 'faq-selected';      
  
        faqDiv.click(function () {

        if ($(this).hasClass(select)) {
            $(this).removeClass(select);
        } else {
            $('#faq-links .faq-selected').removeClass(select);
            $(this).addClass(select);             
        }
    }); //faq link selected
});

  
  
//Accordion
  
$(function () {
  
  var expand = 'expanded';
  var content = $('.faq-content');
        //FAQ Accordion
        $('.faq-accordion > li > a').click(function (e) {
        		e.preventDefault();
        if ($(this).hasClass(expand)) {
            $(this).removeClass(expand);
          $('.faq-accordion > li > a > div').not(this).css('opacity', '1');//returns li back to normal state
            $(this).parent().children('ul').stop(true, true).slideUp();
        } else {
            $('.faq-accordion > li > a > div').not(this).css('opacity', '0.5');//dims inactive li
            $('.faq-accordion > li > a.expanded').removeClass(expand);
            $(this).addClass(expand);
            content.filter(":visible").slideUp();
            $(this).parent().children('ul').stop(true, true).slideDown();
        }
    }); //accordion function

    content.hide();

}); 
  
}

accordWithPage();

$(function () {
   $("#faq-links div").click(function () {
    $('.slide-left').fadeOut( "slow", "linear" );
     $('.slide-left').fadeIn( "slow", "linear" );
    }); //faq link fade in and out
  }); /*//document ready*/
  
  
$(document).ready(function () {

	let form = $("#infoForm");

    let moveForm = $("#moveForm");	
 /* 페이지 이동 버튼 */
   	 $(".pageInfo a").on("click", function(e){
 		
 		e.preventDefault();
         moveForm.find("input[name='pageNum']").val($(this).attr("href"));
         moveForm.attr("action", "/customer/listBoard");
         moveForm.submit();
 	});

    
/* 목록 페이지 이동 버튼 */  
    $("#list_btn").on("click", function(e){
		form.find("#boardNumber").remove();
		form.attr("action", "/customer/listBoard");
		form.submit();
	});
});