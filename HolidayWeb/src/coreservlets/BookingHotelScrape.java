package coreservlets;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;

public class BookingHotelScrape {

	
	public void Scrape()
	{
		List<HotelObject> _ListOfHotelQuotes = new ArrayList<HotelObject>();
		WebDriver driver = new HtmlUnitDriver();
		driver.manage().deleteAllCookies();
		Calendar StartDate = new GregorianCalendar(2014,7,21);
		Calendar EndDate = new GregorianCalendar();
		EndDate = (Calendar) StartDate.clone();
		EndDate.add(Calendar.DATE, 7);
		HotelObject _HotelQuote = new HotelObject("test");
		
		for (int i=0;i<7;i++)
		{
		StartDate.add(Calendar.DATE, 1);
		EndDate.add(Calendar.DATE, 1);
		System.out.println(StartDate.getTime());
		System.out.println(EndDate.getTime());
		
		driver.manage().deleteAllCookies();
		//Gets the prices and populates the List
		
		
		
	    GetResultsSet(driver,StartDate,EndDate, _HotelQuote);
	      
		
		}
		int i =0 ;
		
		
	}
	
	
	
	public void GetResultsSet(WebDriver driver2, Calendar StartCalendar, Calendar EndCalendar, HotelObject _HotelQuote)
	{
		WebDriver driver = new HtmlUnitDriver();
		 driver.get("http://www.booking.com/hotel/es/noelia-sur.en-us.html?sid=b59276a8d43bd6c9ed45489edac9b0e9;dcid=1;checkin=2014-05-03;checkout=2014-05-08;ucfs=1;srfid=1d472e0b6c2a573343061771b05b205ba4ad8806X2");
		
		  WebElement Avail = driver.findElement(By.className("editDatesForm"));
		  Select _ChkInDay = new Select(Avail.findElement(By.name("checkin_monthday")));
	        Select _ChkOutDay = new Select(Avail.findElement(By.name("checkout_monthday")));
	        Select _ChkInMonth = new Select(Avail.findElement(By.name("checkin_year_month")));
	        Select _ChkOutMonth = new Select(Avail.findElement(By.name("checkout_year_month")));

	        
	       
	        _ChkInDay.selectByValue(Integer.toString(StartCalendar.get(Calendar.DAY_OF_MONTH)));
	        _ChkInMonth.selectByValue(Integer.toString(StartCalendar.get(Calendar.YEAR))+"-"+Integer.toString(StartCalendar.get(Calendar.MONTH)));
	      
	      
	        
	        _ChkOutDay.selectByValue(Integer.toString(EndCalendar.get(Calendar.DAY_OF_MONTH)));
	        _ChkOutMonth.selectByValue(Integer.toString(EndCalendar.get(Calendar.YEAR))+"-"+Integer.toString(EndCalendar.get(Calendar.MONTH)));
	    
		 driver.findElement(By.className("avail-date-submit")).click();
		 System.out.println(driver.getCurrentUrl());
		 
	//	 System.out.println(driver.findElement(By.id("available_rooms_header1")).getText());
		 String res = driver.findElement(By.className("b-tooltip-with-price-breakdown-tracker")).toString();
		 System.out.println(res);
		 
		 Pattern p = Pattern.compile("£(\\d*\\.?\\d+?)");
		 Matcher m = p.matcher(res);
		 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
			if (m.find())
			{
				 System.out.println(m.group(0));
				 _HotelQuote.setValue(sdf.format(StartCalendar.getTime()), Integer.valueOf(m.group(0).substring(1)));
			}
		
	}
	
}
