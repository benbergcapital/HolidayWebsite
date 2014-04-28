package coreservlets;

import java.util.Date;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class HotelObject {

	private String _HotelName;
	
	private HashMap<String ,Integer> _PricesMap; 
	private HashMap<String ,String> _UrlMap;
	private HashMap<String ,String> _TooltipMap; 
	HotelObject(String name)
	{
		this._HotelName = name;
		_PricesMap = new HashMap<String ,Integer>();
		_UrlMap = new HashMap<String, String>();
		_TooltipMap = new HashMap<String, String>();
	}
	
	void setValue(String StartDate, int Price, String tooltip)
	{
		_PricesMap.put(StartDate,Price);
		_TooltipMap.put(StartDate,tooltip);
		
	}
	void setValue(String StartDate, int Price)
	{
		_PricesMap.put(StartDate,Price);
		
		
	}
	
	int GetPrice(String StartDate)
	{
		return _PricesMap.get(StartDate) != null ? _PricesMap.get(StartDate) : 0;		
	}
	String GetTooltip(String StartDate)
	{
		return _TooltipMap.get(StartDate) != null ? _TooltipMap.get(StartDate) : "";		
	}
	
	String GetHotelName()
	{
		_HotelName = _HotelName.replace("?", "&#9734");
		return _HotelName;		
	}
	boolean DatePricePairAlreadyExists(String date)
	{
		Object o = _PricesMap.get(date);
		if(o != null)
		{
			return true;
		}
		else
		{
			return false;
		}
		
			
	}
	int GetCheapestPrice()
	{
		int result = 0;
		
		for (Integer value : _PricesMap.values())
		{
			if (result ==0);
			{
				result = value;
			}
			if (value < result)
			{
				result = value;
			}
		}
		
		return result;
	}
	int GetExpensivePrice()
	{
		int result = 0;
		
		for (Integer value : _PricesMap.values())
		{
			if (value > result)
			{
				result = value;
			}
		}
		
		return result;
	}
	public String GetDateFromPrice(int _CheapestPrice) 
	{
		for (String key : _PricesMap.keySet())
		{
		if (_PricesMap.get(key)==_CheapestPrice)
		{
			return key;
		}
			
		}
		
		return null;
	}
	public Set<Entry<String, Integer>> getPriceMap()
	{
		return this._PricesMap.entrySet();
	}
	public void setHotelName(String name)
	{
		this._HotelName = name;
	}
	public void setUrl(String StartDate,String url)
	{
		_UrlMap.put(StartDate,url);
		
	}

	public  Set<Entry<String, String>> getUrlMap() {
		return this._UrlMap.entrySet();
		
		}

	
}
