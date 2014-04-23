package coreservlets;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.Select;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Main m = new Main();
		m.Start();
		
	}
	public static String Test(String g)
	{
		Main m = new Main();
		return m.Start();
		
		
	}
	private String Start()
	{
		SkyScannerScrape s = new SkyScannerScrape();
		BookingScrape B = new BookingScrape();
		
		Calendar StartDate = new GregorianCalendar(2014,7,14);
		int Duration = 7; //no. days
		int Variance = 7; //Number of days after start day to check prices
		
		List<FlightObject> _ListOfFlightQuotes = new ArrayList<FlightObject>();
		List<HotelObject> _ListOfHotelQuotes = new ArrayList<HotelObject>();
		_ListOfFlightQuotes = s.ScrapeScanner(StartDate,Duration,Variance);
	//	_ListOfHotelQuotes = B.ScrapeHotels(StartDate,Duration,Variance);
		
		
		
			return makeChart(_ListOfFlightQuotes);
	}
     private String makeChart(List<FlightObject> _ListOfFlightQuotes)
     {
    	

 		 LinkedList l_cols = new LinkedList();
 	        LinkedList l_final = new LinkedList();
 	        JSONObject obj1 = new JSONObject();
 	        JSONObject obj_cols_1 = new JSONObject();
 	        JSONObject obj_cols_2 = new JSONObject();

 	        obj_cols_1.put("id", "");
 	        obj_cols_1.put("label", "Date");
 	        obj_cols_1.put("type", "string");

 	        obj_cols_2.put("id", "");
 	        obj_cols_2.put("label", "Price");
 	        obj_cols_2.put("type", "number");

 	        l_cols.add(obj_cols_1);
 	        l_cols.add(obj_cols_2);

 	        obj1.put("cols", l_cols);
 	    
 	        for (FlightObject _F : _ListOfFlightQuotes)
 	        {

 	    	LinkedList l1_rows = new LinkedList();
  			JSONObject obj_row1 = new JSONObject();
 	        JSONObject obj_row2 = new JSONObject();


 	        obj_row1.put("v", _F.getDate());
 	        obj_row1.put("f", null);
 	        obj_row2.put("v", _F.getPrice());
 	        obj_row2.put("f", null);

 	        l1_rows.add(obj_row1);
 	        l1_rows.add(obj_row2);

 	        LinkedHashMap m1 = new LinkedHashMap();
 	        m1.put("c", l1_rows);
             l_final.add(m1);
 	        }
 	        obj1.put("rows", l_final);
 	        System.out.println(obj1.toJSONString());
 	        String s = JSONObject.escape(obj1.toJSONString());
 		return s; 
    	 
     }

	}
	
	