package coreservlets;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
	//	m.Start();
		
	}
	public static String Test(String arg)
	{
		Main m = new Main();
	
		
		
		
		if (arg.equals("HOTEL"))
		{
		return m.getHotels();
		}
		if (arg.equals("FLIGHTS"))
		{
		//	return m.getFlights();
		}
		if (arg.equals("SINGLE_HOTEL"))
		{
		//	return m.getHotel(arg);
		}
		
		
			return null;
					
		
	}
	
	public static String SingleHotelRequest(String url, String startdate,
			String duration, String variance) {
		Main m = new Main();
		
		return m.getHotel(url,startdate,Integer.parseInt(duration),Integer.parseInt(variance));
		
	}
	
	private String getHotel(String url, String startdate, int duration, int variance)
	{
		BookingHotelScrape b = new BookingHotelScrape();
		HotelObject _HotelQuote = new HotelObject(url);
		try {
			_HotelQuote = b.Scrape(url,startdate,duration,variance);
			String _ChartJson = makeChartHotel(_HotelQuote);
			String _urlMap = getUrlMap(_HotelQuote);
			String result = _HotelQuote.GetHotelName()+";"+_ChartJson+";"+_urlMap;
			System.out.println(result);
			return result;
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	private String getUrlMap(HotelObject _HotelQuote) {
		
		String result ="";
		
		for (Map.Entry<String,String> entry : _HotelQuote.getUrlMap())
		{
		result += entry.getKey()+":::"+entry.getValue()+"#";
		}
			
		return result;
		
		
		
	}
	private String getHotels()
	{
		BookingScrape B = new BookingScrape();
		
		Calendar StartDate = new GregorianCalendar(2014,7,21);
		Calendar StartDate1 = new GregorianCalendar(2014,7,21);
		int Duration = 7; //no. days
		int Variance = 10; //Number of days after start day to check prices
		
		List<HotelObject> _ListOfHotelQuotes = new ArrayList<HotelObject>();

		_ListOfHotelQuotes = B.ScrapeHotels(StartDate,Duration,Variance);
		
	
		String Hotels = makeChartHotels(_ListOfHotelQuotes,StartDate1,Duration);
			return Hotels;
	}
public static String FlightRequest(String startdate, String duration, String variance, String _origin, String _destination)
{
		SkyScannerScrape s = new SkyScannerScrape();

		Main m = new Main();
		
	
	
	Calendar StartDate = new GregorianCalendar(2014,7,21);
	Calendar StartDate1 = new GregorianCalendar(2014,7,21);
	int Duration = 7; //no. days
	int Variance = 10; //Number of days after start day to check prices
	
	List<FlightObject> _ListOfFlightQuotes = new ArrayList<FlightObject>();
	List<FlightObject> _ListOfFlightQuotes2 = new ArrayList<FlightObject>();
	try {
		_ListOfFlightQuotes = s.ScrapeScanner(startdate,Integer.valueOf(duration),Integer.valueOf(variance), _origin, _destination);
		
		
		_ListOfFlightQuotes2 = s.ScrapeScanner(startdate,Integer.valueOf(duration),Integer.valueOf(variance), _destination,_origin);
	//	String Flight = m.makeChart(_ListOfFlightQuotes);
		
		
		
		
		String Flight = m.makeChart(_ListOfFlightQuotes,_ListOfFlightQuotes2);
		
		
		return Flight;
	} catch (java.text.ParseException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		return null;
	}

	
	
	
		
	
	

}
private String makeChartHotel(HotelObject _HotelObject)
{
	

	 LinkedList l_cols = new LinkedList();
        LinkedList l_final = new LinkedList();
        JSONObject obj1 = new JSONObject();
        JSONObject obj_cols_1 = new JSONObject();
        JSONObject obj_cols_2 = new JSONObject();
        JSONObject obj_cols_3 = new JSONObject();
        
        obj_cols_1.put("id", "");
        obj_cols_1.put("label", "Date");
        obj_cols_1.put("type", "string");

        obj_cols_2.put("id", "");
        obj_cols_2.put("label", "Price");
        obj_cols_2.put("type", "number");
        
        obj_cols_3.put("id", "");
        obj_cols_3.put("role", "tooltip");
        obj_cols_3.put("type", "string");

        l_cols.add(obj_cols_1);
        l_cols.add(obj_cols_2);
        l_cols.add(obj_cols_3);
        
        obj1.put("cols", l_cols);
    
        for (Map.Entry<String, Integer> entry: _HotelObject.getPriceMap())
        {

    	LinkedList l1_rows = new LinkedList();
			JSONObject obj_row1 = new JSONObject();
        JSONObject obj_row2 = new JSONObject();
        JSONObject obj_row3 = new JSONObject();

        obj_row1.put("v", entry.getKey());
        obj_row1.put("f", null);
        obj_row2.put("v", entry.getValue());
        obj_row2.put("f", null);
        obj_row3.put("v", _HotelObject.GetTooltip(entry.getKey()));
        obj_row3.put("f", null);

        l1_rows.add(obj_row1);
        l1_rows.add(obj_row2);
        l1_rows.add(obj_row3);
        
        LinkedHashMap m1 = new LinkedHashMap();
        m1.put("c", l1_rows);
        l_final.add(m1);
        }
        obj1.put("rows", l_final);
        System.out.println(obj1.toJSONString());
        String s = JSONObject.escape(obj1.toJSONString());
	return s; 
	 
}




     private String makeChartHotels(List<HotelObject> _ListOfHotelQuotes, Calendar startDate, int duration)
     {
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		String date = sdf.format(startDate.getTime());
    		List<String> _lstDates = new ArrayList<String>();
    		_lstDates.add(date);
    	 LinkedList l_cols = new LinkedList();
	        LinkedList l_final = new LinkedList();
	        JSONObject obj1 = new JSONObject();
	        JSONObject obj_cols_1 = new JSONObject();
	        JSONObject obj_cols_2 = new JSONObject();
	        JSONObject obj_cols_3 = new JSONObject();
	        JSONObject obj_cols_4 = new JSONObject();
	        JSONObject obj_cols_5 = new JSONObject();
	        JSONObject obj_cols_6 = new JSONObject();
	        obj_cols_1.put("id", "");
	        obj_cols_1.put("label", "Hotel");
	        obj_cols_1.put("type", "string");

	        obj_cols_2.put("id", "");
	        obj_cols_2.put("label", date);
	        obj_cols_2.put("type", "number");
	        startDate.add(Calendar.DATE, 1);
	        
	        date = sdf.format(startDate.getTime());
	        _lstDates.add(date);
	        obj_cols_3.put("id", "");
	        obj_cols_3.put("label", date);
	        obj_cols_3.put("type", "number");
	        startDate.add(Calendar.DATE, 1);
	        
	        date = sdf.format(startDate.getTime());
	        _lstDates.add(date);
	        obj_cols_4.put("id", "");
	        obj_cols_4.put("label", date);
	        obj_cols_4.put("type", "number");
	        startDate.add(Calendar.DATE, 1);
	        
	        date = sdf.format(startDate.getTime());
	        _lstDates.add(date);
	        obj_cols_5.put("id", "");
	        obj_cols_5.put("label", date);
	        obj_cols_5.put("type", "number");
	        startDate.add(Calendar.DATE, 1);
	       
	        date = sdf.format(startDate.getTime());
	        _lstDates.add(date);
	        obj_cols_6.put("id", "");
	        obj_cols_6.put("label", date);
	        obj_cols_6.put("type", "number");
	        
	        	        
	        l_cols.add(obj_cols_1);
	        l_cols.add(obj_cols_2);
	        l_cols.add(obj_cols_3);
	        l_cols.add(obj_cols_4);
	        l_cols.add(obj_cols_5);
	        l_cols.add(obj_cols_6);
	        obj1.put("cols", l_cols);
	    
	   
	    	
	     //   for (String _date : _lstDates)
	    //    {
	        	
	          
		        for (HotelObject _H : _ListOfHotelQuotes)
		        {
	
		    	LinkedList l1_rows = new LinkedList();
				JSONObject obj_row1 = new JSONObject();
		        JSONObject obj_row2 = new JSONObject();
		        JSONObject obj_row3 = new JSONObject();
		        JSONObject obj_row4 = new JSONObject();
		        JSONObject obj_row5 = new JSONObject();
		        JSONObject obj_row6 = new JSONObject();
		        
		        obj_row1.put("v", _H.GetHotelName());
		        obj_row1.put("f", null);
		        obj_row2.put("v", _H.GetPrice(_lstDates.get(0)));
		        obj_row2.put("f", null);
		        obj_row3.put("v", _H.GetPrice(_lstDates.get(1)));
		        obj_row3.put("f", null);
		        obj_row4.put("v", _H.GetPrice(_lstDates.get(2)));
		        obj_row4.put("f", null);
		        obj_row5.put("v", _H.GetPrice(_lstDates.get(3)));
		        obj_row5.put("f", null);
		        obj_row6.put("v", _H.GetPrice(_lstDates.get(2)));
		        obj_row6.put("f", null);
		        
		        
		        l1_rows.add(obj_row1);
		        l1_rows.add(obj_row2);
		        l1_rows.add(obj_row3);
		        l1_rows.add(obj_row4);
		        l1_rows.add(obj_row5);
		        l1_rows.add(obj_row6);
		        
		        LinkedHashMap m1 = new LinkedHashMap();
		        m1.put("c", l1_rows);
		        l_final.add(m1);
		        
		    //    startDate.add(Calendar.DATE, 1);
	        }
	        obj1.put("rows", l_final);
	        System.out.println(obj1.toJSONString());
	        String s = JSONObject.escape(obj1.toJSONString());
		return s; 
		
	}
	private String makeChart(List<FlightObject> _ListOfFlightQuotes,List<FlightObject> _ListOfFlightQuotes2)
     {
    	

 		 LinkedList l_cols = new LinkedList();
 	        LinkedList l_final = new LinkedList();
 	        JSONObject obj1 = new JSONObject();
 	        JSONObject obj_cols_1 = new JSONObject();
 	        JSONObject obj_cols_2 = new JSONObject();
 	        JSONObject obj_cols_3 = new JSONObject();
 	        JSONObject obj_cols_4 = new JSONObject();
 	      
 	      	obj_cols_1.put("id", "");
 	        obj_cols_1.put("label", "Date");
 	        obj_cols_1.put("type", "string");

 	        obj_cols_2.put("id", "");
 	        obj_cols_2.put("label", "OutboundPrice");
 	        obj_cols_2.put("type", "number");
 	        
 	       obj_cols_3.put("id", "");
	        obj_cols_3.put("label", "InboundPrice");
	        obj_cols_3.put("type", "number");
	        
	        obj_cols_4.put("id", "");
 	        obj_cols_4.put("label", "TotalPrice");
 	        obj_cols_4.put("type", "number");

 	        l_cols.add(obj_cols_1);
 	        l_cols.add(obj_cols_2);
 	       l_cols.add(obj_cols_3);
 	      l_cols.add(obj_cols_4);
 	        obj1.put("cols", l_cols);
 	    
 	        for (FlightObject _F : _ListOfFlightQuotes)
 	        {

 	    	LinkedList l1_rows = new LinkedList();
  			JSONObject obj_row1 = new JSONObject();
 	        JSONObject obj_row2 = new JSONObject();
 	        JSONObject obj_row3 = new JSONObject();
 	        JSONObject obj_row4= new JSONObject();

 	      
 	        Double OutPrice = _F.getPrice();
 	        Double InPrice = getInboundFlightPrice(_ListOfFlightQuotes2,_F.getDate());
 	   	   Double Total = OutPrice+InPrice;
 	      
 	      
 	      
 	      
 	        obj_row1.put("v", _F.getDate());
 	        obj_row1.put("f", null);
 	        obj_row2.put("v", OutPrice);
 	        obj_row2.put("f", null);
 	        obj_row3.put("v", InPrice);
	        obj_row3.put("f", null);
	        obj_row4.put("v", Total);
 	        obj_row4.put("f", null);

 	        l1_rows.add(obj_row1);
 	        l1_rows.add(obj_row2);
 	        l1_rows.add(obj_row3);
 	   	    l1_rows.add(obj_row4);
 	        LinkedHashMap m1 = new LinkedHashMap();
        	m1.put("c", l1_rows);
             l_final.add(m1);
 	        }
 	        obj1.put("rows", l_final);
 	        System.out.println(obj1.toJSONString());
 	        String s = JSONObject.escape(obj1.toJSONString());
 		return s; 
    	 
     }
	private Double getInboundFlightPrice(List<FlightObject> _ListOfFlightQuotes2, String date) {
		
		  for (FlightObject _F : _ListOfFlightQuotes2)
		  {
			  if (_F.getDate().equals(date))
			  {
			  	return _F.getPrice();
			  }
		  }
		
		
		return 0.0;
	}
	
	

	}
	
	