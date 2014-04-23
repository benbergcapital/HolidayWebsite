package coreservlets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class BookingScrape {
	
	public List<HotelObject> ScrapeHotels(Calendar StartDate, int duration, int variance)
	{
	
	List<HotelObject> _ListOfHotelQuotes = new ArrayList<HotelObject>();
	WebDriver driver = new HtmlUnitDriver();
	driver.manage().deleteAllCookies();
	
	Calendar EndDate = new GregorianCalendar();
	EndDate = (Calendar) StartDate.clone();
	EndDate.add(Calendar.DATE, duration);
	for (int i=0;i<variance;i++)
	{
	StartDate.add(Calendar.DATE, 1);
	EndDate.add(Calendar.DATE, 1);
	System.out.println(StartDate.getTime());
	System.out.println(EndDate.getTime());
	
	driver.manage().deleteAllCookies();
	//Gets the prices and populates the List
    GetResultsSet(driver,StartDate,EndDate, _ListOfHotelQuotes);
      
	}
    

	
  	for(HotelObject _H : _ListOfHotelQuotes )
		{
	   		System.out.println(_H.GetHotelName());
	   		int _CheapestPrice = _H.GetCheapestPrice();
	   		int _ExpensivePrice = _H.GetExpensivePrice();
	   		System.out.println("Cheapest Price : £"+_CheapestPrice+" starting on : "+_H.GetDateFromPrice(_CheapestPrice));
	   		System.out.println("Expensive Price : £"+_ExpensivePrice+" starting on : "+_H.GetDateFromPrice(_ExpensivePrice));
		
	   		
		}
     
     return _ListOfHotelQuotes;

}

private void GetResultsSet(WebDriver driver, Calendar StartCalendar, Calendar EndCalendar,List<HotelObject> _ListOfHotelQuotes)
{
	
	 driver.get("http://www.booking.com");
	
	  WebElement _Destination = driver.findElement(By.name("ss"));
        Select _ChkInDay = new Select(driver.findElement(By.name("checkin_monthday")));
        Select _ChkOutDay = new Select(driver.findElement(By.name("checkout_monthday")));
        Select _ChkInMonth = new Select(driver.findElement(By.name("checkin_year_month")));
        Select _ChkOutMonth = new Select(driver.findElement(By.name("checkout_year_month")));

        
        
        _ChkInDay.selectByValue(Integer.toString(StartCalendar.get(Calendar.DAY_OF_MONTH)));
        _ChkInMonth.selectByValue(Integer.toString(StartCalendar.get(Calendar.YEAR))+"-"+Integer.toString(StartCalendar.get(Calendar.MONTH)));
      
      
        
        _ChkOutDay.selectByValue(Integer.toString(EndCalendar.get(Calendar.DAY_OF_MONTH)));
        _ChkOutMonth.selectByValue(Integer.toString(EndCalendar.get(Calendar.YEAR))+"-"+Integer.toString(EndCalendar.get(Calendar.MONTH)));
        _Destination.sendKeys("Faro");
  
        _Destination.submit();

        // Check the title of the page
        System.out.println(driver.getCurrentUrl());
     //   System.out.println("Page title is: " + driver.getPageSource());

        WebElement _DisambigDestination = driver.findElement(By.className("destination_name"));
        String link = _DisambigDestination.getAttribute("href");
        
       String URL =  link+";nflt=hotelfacility%3D96%3Bclass%3D4%3B;unchecked_filter=facilities;order=price_for_two";

       GetHotelPrices(URL,StartCalendar,_ListOfHotelQuotes);

 
       
}
		
		
private void GetHotelPrices(String _CurrentURL,Calendar StartCalendar,List<HotelObject>_ListOfHotelQuotes)
{
		
	 GetListOfHotels _getHotelsClass = new GetListOfHotels(_CurrentURL,StartCalendar,_ListOfHotelQuotes);
}
	


}
