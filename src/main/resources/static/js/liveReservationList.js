$(document).ready(function(){
	  $( ".carousel .carousel-inner" ).swipe( {
                swipeLeft: function ( event, direction, distance, duration, fingerCount ) {
                    this.parent( ).carousel( 'next' );
                    $('#left').click(); //smartek.fixed.08.09.21 for bootstrap 5
                },
                swipeRight: function ( ) {
                    this.parent( ).carousel( 'prev' );
                    $('#right').click(); //smartek.fixed.08.09.21 for bootstrap 5
                },
                threshold: 0,
                allowPageScroll:"vertical",
                excludedElements: "label, button, input, select, textarea, .noSwipe"
            } );
});