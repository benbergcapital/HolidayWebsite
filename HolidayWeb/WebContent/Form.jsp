
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>Insert title here</title>
</head>
<body>
<script type='text/javascript' src='https://www.google.com/jsapi'></script>
<script type='text/javascript'>
function isNumberKey(evt){
    var charCode = (evt.which) ? evt.which : event.keyCode
    if (charCode > 31 && (charCode < 48 || charCode > 57))
        return false;
    return true;
}
</script>
<form action="FormEntry" method="post">
Hotel URL: <input type="text" name="url"/><br>
Start Date: <input type="date" name="startdate"/><br>
Duration: <input type="number" name="duration" min="1" max="60" onkeypress="return isNumberKey(event)"/><br>
Variance from Start Date: <input type="number" name="variance" min="1" max="60" onkeypress="return isNumberKey(event)"/><br>
Destination Airport Code IATA: <input type="text" name="destination" maxlength="4"/><br>
<input type="checkbox" name="hotelcheck" value="true">Hotel <input type="checkbox" name="flightcheck" value="true">Flight
<input type="submit">
</form>













</body>
</html>