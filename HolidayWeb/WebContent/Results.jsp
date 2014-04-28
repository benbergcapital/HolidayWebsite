<%@ page  pageEncoding="UTF-8"%>
    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Poopies Holiday Optimiser</title>
</head>

    
    <script type='text/javascript' src='https://www.google.com/jsapi'></script>
<script type='text/javascript'>
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
google.setOnLoadCallback(drawChartOutboundFlight);
google.setOnLoadCallback(drawChartInboundFlight);

var Map ={};
var url = "${hotelurlmap}";

//Map['2014-08-14']="https://developers.google.com/chart/";

var url_array = url.split("#");
for (var i = 0; i < url_array.length; i++) {
 //   alert(url_array[i]);
   var part = url_array[i].split(":::");
   Map[part[0]] = part[1];
    
    
}

function drawChart() {
//	alert("${hotelurlmap}");
	 var myObject = "${hotelchart}";
	//   alert (myObject);
	 
	   document.getElementById("HotelName").innerHTML = "${hotelname}";
	  var data = new google.visualization.DataTable(myObject);
	  data.sort([{column: 0, asc:true}, {column: 1}]);
 

  var options = {
    title: "${hotelname}"
  };

 

   
  
  
  var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
  google.visualization.events.addListener(chart, 'select', function(){
	  var selectedItem = chart.getSelection()[0];
	    var date = data.getValue(selectedItem.row,0);
	    alert(date);
	  if (date in Map)
		  {
		  alert(Map[date]);
		  window.location.assign(Map[date]);
		  }
	  else
		  {
		  alert("no url present");
		  }
	    
	  
	  
  });
  
  
  chart.draw(data, options);
}

function drawChartOutboundFlight() {
	
	 var myObject = "${outboundflightchart}";
	   alert (myObject);
	  var data = new google.visualization.DataTable(myObject);
	  data.sort([{column: 0, asc:true}, {column: 1}]);


var options = {
  title: 'Flight Prices'
};

var chart = new google.visualization.LineChart(document.getElementById('outbound_flight_div'));
chart.draw(data, options);
}   
function drawChartInboundFlight() {
	
	 var myObject = "${inboundflightchart}";
	   alert (myObject);
	  var data = new google.visualization.DataTable(myObject);
	  data.sort([{column: 0, desc:true}, {column: 1}]);


var options = {
 title: 'Inbound Flight Prices'
};

var chart = new google.visualization.LineChart(document.getElementById('inbound_flight_div'));
chart.draw(data, options);
} 
  </script>
    
    
    <body>

    <div id='HotelName'></div>
    <div id='chart_div'></div>
   
    <div id='outbound_flight_div'></div>
    <div id='inbound_flight_div'></div>
    </body>
</html>
</body>
</html>