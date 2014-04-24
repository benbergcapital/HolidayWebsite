package coreservlets;

import java.util.Date;
import java.util.HashMap;

public class HotelObject {

	private String _HotelName;
	
	private HashMap<String ,Integer> _PricesMap; 
	
	HotelObject(String name)
	{
		this._HotelName = name;
		_PricesMap = new HashMap<String ,Integer>();
	}
	
	void setValue(String StartDate, int Price)
	{
		_PricesMap.put(StartDate,Price);
		
	}
	
	int GetPrice(String StartDate)
	{
		return _PricesMap.get(StartDate) != null ? _PricesMap.get(StartDate) : 0;		
	}
	String GetHotelName()
	{
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
}
