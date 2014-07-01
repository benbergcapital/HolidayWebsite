package coreservlets;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;
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
		Logger logger = Logger.getLogger ("");
		logger.setLevel (Level.OFF);
		
		List<HotelObject> _ListOfHotelQuotes = new ArrayList<HotelObject>();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Calendar StartDate = new GregorianCalendar();
		StartDate.setTime(sdf.parse(startdate));
		Calendar EndDate = new GregorianCalendar();
		
		
		EndDate = (Calendar) StartDate.clone();
		EndDate.add(Calendar.DATE, duration);
		HotelObject _HotelQuote = new HotelObject("test");
		List<ThreadWorker> threads = new ArrayList<ThreadWorker>();
		ExecutorService executor = Executors.newFixedThreadPool(5);
		for (int i=0;i<variance;i++)
		{
		System.out.println(StartDate.getTime());
		System.out.println(EndDate.getTime());
	
		 Runnable worker = new ThreadWorker(url,StartDate,EndDate, _HotelQuote);
	
	     System.out.println("Started Thread:" + StartDate);

	     executor.execute(worker);
	    StartDate.add(Calendar.DATE, 1);
		EndDate.add(Calendar.DATE, 1);
		
		}
		 executor.shutdown();
		 while (!executor.isTerminated()) {
	        }
	        System.out.println("Finished all threads");
		/*for (Thread curThread : threads) {
		    try {
			// starting from the first wait for each one to finish.
		    	System.out.println(curThread.getId());
			curThread.join();
		    } catch (InterruptedException e) {
	
		    }
		    */
		//}
	//	System.out.println("All threads joined");
		
		
		return _HotelQuote;
		
	}
	class ThreadWorker extends Thread { 

	
		String url;
		Calendar StartCalendar;
		Calendar EndCalendar;
		HotelObject _HotelQuote;
		
		  public ThreadWorker (String url,Calendar StartCalendar, Calendar EndCalendar, HotelObject _HotelQuote) { 
		    this._HotelQuote = _HotelQuote;
		    this.url = url;
		    this.StartCalendar = (Calendar) StartCalendar.clone();
		    this.EndCalendar = (Calendar) EndCalendar.clone();
		  
		  }

		  public void run() {
			  System.out.println("Starting thread with dates of : "+StartCalendar+" and "+EndCalendar);
			  WebDriver driver = new HtmlUnitDriver();
				driver.manage().deleteAllCookies();
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
				 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				 if (driver.getPageSource().contains("sold out on your dates"))
				 {
					 _HotelQuote.setValue(sdf.format(StartCalendar.getTime()), 0,"Sold out on this date");
					 _HotelQuote.setUrl(sdf.format(StartCalendar.getTime()), url);
				 }
				 else
				 {
				 
				 String _priceElement = driver.findElement(By.className("b-tooltip-with-price-breakdown-tracker")).toString();
				 String _conditionsElement = driver.findElement(By.className("ico_policy_info")).getText();
			//	 System.out.println(_priceElement);
				 
				 Pattern p = Pattern.compile("£\\d*,\\d*|£\\d*");
				 Matcher m = p.matcher(_priceElement);
				 
					
			
					if (m.find())
					{
						 System.out.println(m.group(0).substring(1));
						int price = Integer.valueOf(m.group(0).substring(1));
						 _HotelQuote.setValue(sdf.format(StartCalendar.getTime()),price ,"£"+price+"\n"+_conditionsElement);
						 _HotelQuote.setUrl(sdf.format(StartCalendar.getTime()), url);
					}
				 }
		  } 
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
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 if (driver.getPageSource().contains("sold out on your dates"))
		 {
			 _HotelQuote.setValue(sdf.format(StartCalendar.getTime()), 0,"Sold out on this date");
			 _HotelQuote.setUrl(sdf.format(StartCalendar.getTime()), url);
		 }
		 else
		 {
		 
		 String _priceElement = driver.findElement(By.className("b-tooltip-with-price-breakdown-tracker")).toString();
		 String _conditionsElement = driver.findElement(By.className("ico_policy_info")).getText();
	//	 System.out.println(_priceElement);
		 
		 Pattern p = Pattern.compile("£(\\d*\\.?\\d+?)");
		 Matcher m = p.matcher(_priceElement);
		 
			
	
			if (m.find())
			{
				 System.out.println(m.group(0));
				 _HotelQuote.setValue(sdf.format(StartCalendar.getTime()), Integer.valueOf(m.group(0).substring(1)),_conditionsElement);
				 _HotelQuote.setUrl(sdf.format(StartCalendar.getTime()), url);
			}
		 }
		
	}
	
}
