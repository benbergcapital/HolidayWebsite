<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>

<style>
#HotelName
{
font-family:"Times New Roman";
font-size:20px;
font-weight:bold;
}
</style>


<script type='text/javascript' src='https://www.google.com/jsapi'></script>
<script type='text/javascript'>
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
google.setOnLoadCallback(drawChartFlight);
function drawChart() {
	
	 var myObject = "<%=coreservlets.Main.Test("SINGLE_HOTEL")%>";
	   alert (myObject);
	   var res = myObject.split(";");
	   document.getElementById("HotelName").innerHTML = res[0];
	  var data = new google.visualization.DataTable(res[1]);
	
 

  var options = {
    title: 'Hotel Prices'
  };

  var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
  chart.draw(data, options);
}
function drawChartFlight() {
	
	 var myObject = "<%=coreservlets.Main.Test("FLIGHT")%>";
	   alert (myObject);
	  var data = new google.visualization.DataTable(myObject);
	


 var options = {
   title: 'Flight Prices'
 };

 var chart = new google.visualization.LineChart(document.getElementById('chart_flight_div'));
 chart.draw(data, options);
}



</script>
</head>
<body>
Hotel Prices. All Hotels are 4* and have Free Wifi. 
<div id='HotelName'>\2605 Hotel Prices. All Hote  &#9734 </div>
<div id='chart_div'></div>
Flight Prices one way (return coming!!) . All flights depart between 10am and 3pm.
<div id='chart_flight_div'></div>
</body>
</html>