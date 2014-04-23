package coreservlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class GetListOfHotels {


	public GetListOfHotels(String SearchResults,Calendar calendar,List<HotelObject> _ListOfHotelQuotes)
	{
		
		
        Document doc_curr;
		try {
			doc_curr = Jsoup.connect(SearchResults).get();
		
		Element content_curr = doc_curr.getElementById("search_results_table");
		Elements links_curr = content_curr.getElementsByClass("sr_item_content");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		
		
		
		for (Element link1 : links_curr) {
			Elements _name = link1.getElementsByClass("hotel_name_link");
			Elements _price = link1.getElementsByClass("price");
			
			
			String intValue = _price.text().replaceAll("[^0-9]", ""); 
			
			boolean _HotelExists =false;
			for(HotelObject _H : _ListOfHotelQuotes )
			{
				if (_H.GetHotelName().equals(_name.text()))
				{
					_HotelExists =true;
					if(!_H.DatePricePairAlreadyExists(sdf.format(calendar.getTime())))
					{
					_H.setValue(sdf.format(calendar.getTime()), Integer.valueOf(intValue));
					}
					else
					{
					//_H.setValue(sdf.format(calendar.getTime()), Integer.valueOf(intValue));	
					}
				}
				
			}
			if(!_HotelExists)
			{
			HotelObject _o = new HotelObject(_name.text());
			_o.setValue(sdf.format(calendar.getTime()), Integer.valueOf(intValue));
			
			_ListOfHotelQuotes.add(_o);
			}
			
		}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        
        
		
		
		
	}
	
	
}
