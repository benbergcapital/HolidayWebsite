package coreservlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.*;

public class SkyScannerScrape {

	public List<FlightObject> ScrapeScanner(Calendar StartDate, int duration, int variance)
	{
		
		List<FlightObject> _ListOfFlightQuotes = new ArrayList<FlightObject>();
		
		Calendar EndDate = new GregorianCalendar();
		EndDate = (Calendar) StartDate.clone();
		EndDate.add(Calendar.DATE, duration);
		
		
		for (int i=0;i<variance;i++)
		{
		StartDate.add(Calendar.DATE, 1);
		EndDate.add(Calendar.DATE, 1);
		System.out.println(StartDate.getTime());
		System.out.println(EndDate.getTime());
		String _sessionUrl = GetSession(StartDate);
		_ListOfFlightQuotes.add(GetResultsSet(_sessionUrl,StartDate, _ListOfFlightQuotes));
	      
		}
		
		
	return _ListOfFlightQuotes;
		
		
		
		
		
		
		
		
	}
	private String GetSession(Calendar StartDate)
	{
		try{
		String SessionHeader =  null;
		
		while (SessionHeader==null)
		{
		
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String _outDate = sdf.format(StartDate.getTime());
		
		
		

		HttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost("http://partners.api.skyscanner.net/apiservices/pricing/v1.0");

		//String _outDate = "2014-08-12";
		
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("apikey", "prtl6749387986743898559646983194"));
		params.add(new BasicNameValuePair("country", "GB"));
		params.add(new BasicNameValuePair("currency", "GBP"));
		params.add(new BasicNameValuePair("locale", "en-GB"));
		params.add(new BasicNameValuePair("originplace", "LON"));
		params.add(new BasicNameValuePair("destinationplace", "TFS"));
		params.add(new BasicNameValuePair("outbounddate", _outDate));
	//	params.add(new BasicNameValuePair("inbounddate", "2014-08-14"));
		params.add(new BasicNameValuePair("locationschema", "Iata"));
		
		
		
		

			httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		

		//Execute and get the response.
		HttpResponse response = httpclient.execute(httppost);
		
		Header[] headers = response.getAllHeaders();
		
		System.out.println(response.getStatusLine());
		for (Header header : headers) {
			System.out.println("Key : " + header.getName() 
			      + " ,Value : " + header.getValue());
			if (header.getName().equals("Location"))
			{
				SessionHeader =  response.getFirstHeader("Location").getValue();
				
			}
		}
		if (SessionHeader ==null)
		{
			Thread.sleep(7000);
		}
		else
		{

			return SessionHeader+"?stops=0&outbounddepartstarttime=10:00&outbounddepartendtime=15:00&apiKey=prtl6749387986743898559646983194";	
		}
		}
		
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		return null;
		
	}
	
	
	private FlightObject GetResultsSet(String url, Calendar StartDate,List<FlightObject> _ListOfFlightQuotes)
	{
		try{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String _outDate = sdf.format(StartDate.getTime());
		
		
	//	  Document doc_curr;
	//		String urlr = "http://partners.api.skyscanner.net/apiservices/pricing/v1.0/f7a25f197dd24b62873749050ea175e1_elhhpiln_2443884DF09B24C74670EF5297AC955A?stops=0&outbounddepartstarttime=10:00&outbounddepartendtime=15:00&apiKey=prtl6749387986743898559646983194";
		//		doc_curr = Jsoup.connect("http://partners.api.skyscanner.net/apiservices/pricing/v1.0/908ae930a8b84613bf10c0cdb88be6a3_elhhpiln_FEF17D54C0028DB9344D569BA84138C9?stops=0&outbounddepartstarttime=10:00&outbounddepartendtime=15:00&apiKey=prtl6749387986743898559646983194").get();
		
			
			HttpClient client = new DefaultHttpClient();
			HttpGet request = new HttpGet(url);
			HttpResponse response2 = client.execute(request);

			// Get the response
			BufferedReader rd = new BufferedReader
			  (new InputStreamReader(response2.getEntity().getContent()));
			    
			
			
			String line = 	IOUtils.toString(rd);
			 System.out.println(line);
			
			
			 String Query = line.substring(line.indexOf("Query")-1,line.indexOf("}", line.indexOf("Query"))+1);
			 String Itineries = line.substring(line.indexOf("Itineraries")-1,line.indexOf("}}]",line.indexOf("Itineraries"))+3);
			 String Legs = line.substring(line.indexOf("Legs"),line.indexOf("}]",line.indexOf("Legs"))+2);
			 String Carriers = line.substring(line.indexOf("Carriers"),line.indexOf("}]",line.indexOf("Carriers"))+2);
			
			 
			 
			 System.out.println(Itineries);
				
		//	 String OutBoundLegID = Itineries.substring(Itineries.indexOf("OutboundLegId")+16,Itineries.indexOf("\","));
		//	 String InboundLegID = Itineries.substring(Itineries.indexOf("InboundLegId")+16,Itineries.indexOf("\",", Itineries.indexOf("InboundLegId")));
			 String Price = Itineries.substring(Itineries.indexOf("Price")+7,Itineries.indexOf("}",Itineries.indexOf("Price")));
			
		//	 String Leg = Legs.substring(Legs.indexOf("[{")+1,Legs.indexOf("},")+1);
		//	 Leg = Leg.replace("[]", "[0]");
		//	 System.out.println(Leg);
	/*		 if (Leg.contains(LegID))
			 {
				 JsonParserFactory factory=JsonParserFactory.getInstance();
					 JSONParser parser=factory.newJsonParser();
					 Map jsonMap=parser.parseJson(Leg);
					
					String  Time = jsonMap.get("Departure").toString();
					System.out.println(Time);
			 }
		*/	 
			 FlightObject _fo = new FlightObject(_outDate,Price);
			 
			 return _fo;
			 
			
			 
			 // String Departure = Legs.substring(Legs.indexOf(LegID))
			 
		//			 String test = "{\"Id\":\"13771-1408121135-FR-0-16866-1408121610\",\"OriginStation\":13771,\"DestinationStation\":16866,\"Departure\":\"2014-08-12T11:35:00\",\"Arrival\":\"2014-08-12T16:10:00\"}";
		//			 
		//	 JsonParserFactory factory=JsonParserFactory.getInstance();
		//	 JSONParser parser=factory.newJsonParser();
		//	 Map jsonMap=parser.parseJson(test);
			
			
			
			
			
			
			
			
		/*	
			WebDriver driver = new HtmlUnitDriver();
			driver.get(urlr);
			String _Result = driver.getPageSource();
			
			
			int Index = _Result.indexOf("Price");
			while(Index >= 0)
			{
			System.out.println(_Result.substring(Index+7, _Result.indexOf("}",Index)));
			Index = _Result.indexOf("Price",Index+1);
			}
			
			 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();  
			    DocumentBuilder builder;  
			    try  
			    {  
			        builder = factory.newDocumentBuilder();  
			        org.w3c.dom.Document document = builder.parse( new InputSource( new StringReader( _Result ) ) );  
			    } catch (Exception e) {  
			        e.printStackTrace();  
			    } 
			
			
        */
        
		}
		catch (Exception e)
		{
			System.out.println(e.toString());
			return null;
		}
		
	

		
	
		
	
		}
}

