/**
 * 
 */

$(document).ready(function() {
    $.ajax({
        url: "http://www.infinitehealthcoaching.com/MatrixActivity-0.0.1/greeting?name=wsgliz"
        //url: "http://localhost:8080/greeting"
       	//url: "http://rest-service.guides.spring.io/greeting"

    	
    }).then(function(data) {
       $('.greeting-id').append(data.id);
       $('.greeting-content').append(data.content);
    });
});

