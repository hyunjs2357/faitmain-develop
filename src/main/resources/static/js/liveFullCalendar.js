document.addEventListener('DOMContentLoaded', function () {
	        $(function () {
	            var request = $.ajax({
	                url: "/live/json/getLiveReservationCal", // 변경하기
	                method: "GET",
	                dataType: "json"
	            });
	
	            request.done(function (data) {
	                console.log(data); // log 로 데이터 찍어주기.
	
	                var calendarEl = document.getElementById('calendar');
	
	                var calendar = new FullCalendar.Calendar(calendarEl, {
	                    initialView: 'dayGridMonth',
	                    headerToolbar: {
	                        left: 'prev,next today',
	                        center: 'title',
	                        right: 'dayGridMonth'
	                    },
	                    editable: true,
	                    droppable: true, // this allows things to be dropped onto the calendar
	                    drop: function (arg) {
	                        // is the "remove after drop" checkbox checked?
	                        if (document.getElementById('drop-remove').checked) {
	                            // if so, remove the element from the "Draggable Events" list
	                            arg.draggedEl.parentNode.removeChild(arg.draggedEl);
	                        }
	                    },
	                    dateClick: function(info){
	                    
	                    	console.info(info);
	                    	
	                    	alert('Date : ' + info.dateStr);
	                    	
	                    	$("input[name='date']").val(info.dateStr);
	                    	$("form").attr("method", "GET").attr("action", "/live/getLiveReservationList").submit();
	                    	
	                    },
	
	                    	
	                  
	           
	                    events: data
	                    
	                });
	
	                calendar.render();
	            });
	
	            request.fail(function( jqXHR, textStatus ) {
	                alert( "Request failed: " + textStatus );
	            });
	        });
	
	    });
	
	