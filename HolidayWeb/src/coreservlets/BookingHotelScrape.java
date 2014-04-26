package coreservlets;

import java.text.ParseException;
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

	
	public HotelObject Scrape(String url, String startdate, int duration, int variance) throws ParseException
	{
		List<HotelObject> _ListOfHotelQuotes = new ArrayList<HotelObject>();
		WebDriver driver = new HtmlUnitDriver();
		driver.manage().deleteAllCookies();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar StartDate = new GregorianCalendar();
		StartDate.setTime(sdf.parse(startdate));
		Calendar EndDate = new GregorianCalendar();
		
		
		EndDate = (Calendar) StartDate.clone();
		EndDate.add(Calendar.DATE, duration);
		HotelObject _HotelQuote = new HotelObject("test");
		
		for (int i=0;i<variance;i++)
		{
		System.out.println(StartDate.getTime());
		System.out.println(EndDate.getTime());
				driver.manage().deleteAllCookies();
		//Required otherwise booking.com remembers some things making it difficult to scrape
		
	    GetResultsSet(driver,url,StartDate,EndDate, _HotelQuote);
	    StartDate.add(Calendar.DATE, 1);
		EndDate.add(Calendar.DATE, 1);
		
		}
		int i =0 ;
		return _HotelQuote;
		
	}
	
	
	
	public void GetResultsSet(WebDriver driver, String url,Calendar StartCalendar, Calendar EndCalendar, HotelObject _HotelQuote)
	{
	
		 driver.get(url);
		
		WebElement priceValue = driver.findElement(By.xpath("//meta[@property='og:title']"));
				System.out.println();
		 _HotelQuote.setHotelName(priceValue.getAttribute("content"));
		 
		  WebElement Avail = driver.findElement(By.className("editDatesForm"));
		  Select _ChkInDay = new Select(Avail.findElement(By.name("checkin_monthday")));
	        Select _ChkOutDay = new Select(Avail.findElement(By.name("checkout_monthday")));
	        Select _ChkInMonth = new Select(Avail.findElement(By.name("checkin_year_month")));
	        Select _ChkOutMonth = new Select(Avail.findElement(By.name("checkout_year_month")));

	        System.out.println(Integer.toString(StartCalendar.get(Calendar.DAY_OF_MONTH)));
	        System.out.println(Integer.toString(StartCalendar.get(Calendar.YEAR))+"-"+Integer.toString(StartCalendar.get(Calendar.MONTH)+1));
	        
	        
	        _ChkInDay.selectByValue(Integer.toString(StartCalendar.get(Calendar.DAY_OF_MONTH)));
	        _ChkInMonth.selectByValue(Integer.toString(StartCalendar.get(Calendar.YEAR))+"-"+Integer.toString(StartCalendar.get(Calendar.MONTH)+1));
	      
	        System.out.println(Integer.toString(EndCalendar.get(Calendar.DAY_OF_MONTH)));
	        System.out.println(Integer.toString(EndCalendar.get(Calendar.YEAR))+"-"+Integer.toString(EndCalendar.get(Calendar.MONTH)+1));
	        
	        _ChkOutDay.selectByValue(Integer.toString(EndCalendar.get(Calendar.DAY_OF_MONTH)));
	        _ChkOutMonth.selectByValue(Integer.toString(EndCalendar.get(Calendar.YEAR))+"-"+Integer.toString(EndCalendar.get(Calendar.MONTH)+1));
	       
	       
		 driver.findElement(By.className("avail-date-submit")).click();
		 url= driver.getCurrentUrl();
		 System.out.println(url);
		 
	//	 System.out.println(driver.findElement(By.id("available_rooms_header1")).getText());
		 String res = driver.findElement(By.className("b-tooltip-with-price-breakdown-tracker")).toString();
		 System.out.println(res);
		 
		 Pattern p = Pattern.compile("�(\\d*\\.?\\d+?)");
		 Matcher m = p.matcher(res);
		 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
			if (m.find())
			{
				 System.out.println(m.group(0));
				 _HotelQuote.setValue(sdf.format(StartCalendar.getTime()), Integer.valueOf(m.group(0).substring(1)));
				 _HotelQuote.setUrl(sdf.format(StartCalendar.getTime()), url);
			}
		
	}
	
}
