<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
<script type='text/javascript' src='https://www.google.com/jsapi'></script>
<script type='text/javascript'>
google.load("visualization", "1", {packages:["corechart"]});
google.setOnLoadCallback(drawChart);
function drawChart() {
	
	 var myObject = "<%=coreservlets.Main.Test("GETORDERS")%>";
	   alert (myObject);
	  var data = new google.visualization.DataTable(myObject);
	
 

  var options = {
    title: 'Company Performance'
  };

  var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
  chart.draw(data, options);
}
	



</script>
</head>
<body>
<div id='chart_div'></div>
</body>
</html>