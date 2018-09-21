package manager.data.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import manager.data.model.Country;
import manager.data.model.CountryGDPPerCapitaData;
import manager.data.model.CountryNetMigrationData;
import manager.data.model.CountryPopulationData;
import manager.data.model.CountryPovertyData;
import manager.data.model.GDPPerCapitaData;
import manager.data.model.NetMigrationData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;

public class WorldBankDataWrapper {

	public WorldBankDataWrapper() {
		
	}
	
//	public ArrayList<CountryPopulationData> extractPopulationData() throws UnirestException
//	{
//		ArrayList<CountryPopulationData> data = new ArrayList<>();
//		final String restAPI = "https://api.worldbank.org/v2/";
//		final String countrySelector = "countries/";
//		final String dataset = "indicators/SP.POP.TOTL";
//		Map<String, Object> fields = new HashMap<>();
//	    fields.put("format", "json");
//	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
//	    System.out.println(req.getUrl());
//	    JSONArray result = req.asJson().getBody().getArray();
//	    int pages = result.getJSONObject(0).getInt("pages");
//	    for (int i = 1; i <= pages; i++)
//	    {
//	    	fields.put("page", i);
//	    	req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
//	    	JSONArray pageDataArray = req.asJson().getBody().getArray().getJSONArray(1);
//	    	for (int j = 0; j < pageDataArray.length(); j++)
//	    	{
//	    		JSONObject dataObj = pageDataArray.getJSONObject(j);
//	    		if (!"null".equals(dataObj.get("value").toString()))
//	    		{
//	    			String country = dataObj.getJSONObject("country").getString("id");
//	    			String year = dataObj.getString("date");
//	    			double value = Double.valueOf(String.valueOf(dataObj.get("value")));
//	    			System.out.println("[" + country + ", " + year + ", " + value + "]");
//	    			data.add(new CountryPopulationData(country, year, value, SOURCE));
//	    		}
//	    	}
//	    }
//	    return data;
//	}
//	
//	public ArrayList<CountryPovertyData> extractPovertyData() throws UnirestException
//	{
//		ArrayList<CountryPovertyData> data = new ArrayList<>();
////		final String restAPI = "https://api.worldbank.org/v2/countries/indicators/SI.POV.NAHC";
//		final String restAPI = "https://api.worldbank.org/v2/";
//		final String countrySelector = "countries/";
//		final String dataset = "indicators/SI.POV.NAHC";
////		final String startDate = "2000";
////		final String endDate = "2017";
////		final String queryString = "?date=" + endDate + ":" + startDate;
////		HttpRequest req = Unirest.get(restAPI + queryString);
//		Map<String, Object> fields = new HashMap<>();
////	    fields.put("date", LAST_YEAR + ":" + FIRST_YEAR);
//	    fields.put("format", "json");
//	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
//	    System.out.println(req.getUrl());
////	    System.out.println(req.asJson().getBody().getArray());
//	    JSONArray result = req.asJson().getBody().getArray();
//	    int pages = result.getJSONObject(0).getInt("pages");
////	    System.out.println(pages);
//	    for (int i = 1; i <= pages; i++)
//	    {
//	    	fields.put("page", i);
//	    	req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
////	    	System.out.println(req.getUrl());
//	    	JSONArray pageDataArray = req.asJson().getBody().getArray().getJSONArray(1);
////	    	System.out.println(pageData);
//	    	for (int j = 0; j < pageDataArray.length(); j++)
//	    	{
//	    		JSONObject dataObj = pageDataArray.getJSONObject(j);
////	    		System.out.println(dataObj);
////	    		System.out.println(dataObj.get("value"));
//	    		if (!"null".equals(dataObj.get("value").toString()))
//	    		{
////	    			System.out.println("Hey, i'm here!");
//	    			String country = dataObj.getJSONObject("country").getString("id");
//	    			String year = dataObj.getString("date");
//	    			double value = Double.valueOf(String.valueOf(dataObj.get("value")));
////	    			System.out.println("[" + country + ", " + year + ", " + value + "]");
//	    			data.add(new CountryPovertyData(country, year, value, SOURCE));
//	    		}
//	    	}
//	    }
//	    return data;
//	}
//	
//	public ArrayList<CountryNetMigrationData> extractNetMigrationData() throws UnirestException
//	{
//		ArrayList<CountryNetMigrationData> data = new ArrayList<>();
//		final String restAPI = "https://api.worldbank.org/v2/";
//		final String countrySelector = "countries/";
//		final String dataset = "indicators/SM.POP.NETM";
//		Map<String, Object> fields = new HashMap<>();
//	    fields.put("format", "json");
//	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
//	    System.out.println(req.getUrl());
//	    JSONArray result = req.asJson().getBody().getArray();
//	    int pages = result.getJSONObject(0).getInt("pages");
//	    for (int i = 1; i <= pages; i++)
//	    {
//	    	fields.put("page", i);
//	    	req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
//	    	JSONArray pageDataArray = req.asJson().getBody().getArray().getJSONArray(1);
//	    	for (int j = 0; j < pageDataArray.length(); j++)
//	    	{
//	    		JSONObject dataObj = pageDataArray.getJSONObject(j);
//	    		if (!"null".equals(dataObj.get("value").toString()))
//	    		{
//	    			String country = dataObj.getJSONObject("country").getString("id");
//	    			String year = dataObj.getString("date");
//	    			double value = Double.valueOf(String.valueOf(dataObj.get("value")));
//	    			System.out.println("[" + country + ", " + year + ", " + value + "]");
//	    			data.add(new CountryNetMigrationData(country, year, value, SOURCE));
//	    		}
//	    	}
//	    }
//	    return data;
//	}
//	
//	public ArrayList<CountryGDPPerCapitaData> extractGDPPerCapitaData() throws UnirestException
//	{
//		ArrayList<CountryGDPPerCapitaData> data = new ArrayList<>();
//		final String restAPI = "https://api.worldbank.org/v2/";
//		final String countrySelector = "countries/";
//		final String dataset = "indicators/NY.GDP.PCAP.PP.CD";
//		Map<String, Object> fields = new HashMap<>();
//	    fields.put("format", "json");
//	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
//	    System.out.println(req.getUrl());
//	    JSONArray result = req.asJson().getBody().getArray();
//	    int pages = result.getJSONObject(0).getInt("pages");
//	    for (int i = 1; i <= pages; i++)
//	    {
//	    	fields.put("page", i);
//	    	req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
//	    	JSONArray pageDataArray = req.asJson().getBody().getArray().getJSONArray(1);
//	    	for (int j = 0; j < pageDataArray.length(); j++)
//	    	{
//	    		JSONObject dataObj = pageDataArray.getJSONObject(j);
//	    		if (!"null".equals(dataObj.get("value").toString()))
//	    		{
//	    			String country = dataObj.getJSONObject("country").getString("id");
//	    			String year = dataObj.getString("date");
//	    			double value = Double.valueOf(String.valueOf(dataObj.get("value")));
//	    			System.out.println("[" + country + ", " + year + ", " + value + "]");
//	    			data.add(new CountryGDPPerCapitaData(country, year, value, SOURCE));
//	    		}
//	    	}
//	    }
//	    return data;
//	}
	
//	public int extractPopulationData(String countryCode, String year) throws UnirestException
//	{
//		final String restAPI = "https://api.worldbank.org/v2/";
//		final String countrySelector = "countries/" + countryCode + "/";
//		final String dataset = "indicators/SP.POP.TOTL";
//		Map<String, Object> fields = new HashMap<>();
//	    fields.put("date", year);
//	    fields.put("format", "json");
//	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
//		System.out.println(req.getUrl());
//		JSONObject data = req.asJson().getBody().getArray().getJSONArray(1).getJSONObject(0);
//		return data.getInt("value");
//	}
	
//	public ArrayList<CountryNetMigrationData> extractNetMigrationData(ArrayList<Country> countries) throws UnirestException
//	{
//		ArrayList<CountryNetMigrationData> netMigrationData = new ArrayList<>();
//		final String restAPI = "https://api.worldbank.org/v2/";
//		final String countrySelector = "countries/";
//		final String dataset = "indicators/SM.POP.NETM";
//		Map<String, Object> fields = new HashMap<>();
//	    fields.put("date", LAST_YEAR + ":" + FIRST_YEAR);
//	    fields.put("format", "json");
//	    for (Country c: countries)
//	    {
//	    	HttpRequest req = Unirest.get(restAPI + countrySelector + c.getCode() + "/" + dataset).queryString(fields);
//	    	System.out.println(req.getUrl());
//	    	try
//	    	{
//		    	JSONArray array = req.asJson().getBody().getArray().getJSONArray(1);
//				for (int i = 0; i < array.length(); i++)
//				{
//					JSONObject data = array.getJSONObject(i);
//					if (!"null".equals(data.get("value").toString()))
//					{
//						String year = data.getString("date");
//						float value = data.getInt("value");
//						netMigrationData.add(new CountryNetMigrationData(c.getCode(), year, value, WorldBankDataWrapper.SOURCE));
//					}
//				}
//	    	}
//	    	catch (JSONException e)
//	    	{
//	    		System.err.println("Net Migration data for country " + c.getCode() + " are not available");
//	    	}
//	    }
////		System.out.println(netMigrationData);
//		return netMigrationData;
//	}
	
//	public ArrayList<CountryGDPPerCapitaData> extractGDPPerCapitaData(ArrayList<Country> countries) throws UnirestException
//	{
//		ArrayList<CountryGDPPerCapitaData> gdpPerCapitaData = new ArrayList<>();
//		final String restAPI = "https://api.worldbank.org/v2/";
//		final String countrySelector = "countries/";
//		final String dataset = "indicators/NY.GDP.PCAP.PP.CD";
//		Map<String, Object> fields = new HashMap<>();
//	    fields.put("date", LAST_YEAR + ":" + FIRST_YEAR);
//	    fields.put("format", "json");
//	    for (Country c: countries)
//	    {
//	    	HttpRequest req = Unirest.get(restAPI + countrySelector + c.getCode() + "/" + dataset).queryString(fields);
//	    	System.out.println(req.getUrl());
//	    	try
//	    	{
//		    	JSONArray array = req.asJson().getBody().getArray().getJSONArray(1);
//				for (int i = 0; i < array.length(); i++)
//				{
//					JSONObject data = array.getJSONObject(i);
//					if (!"null".equals(data.get("value").toString()))
//					{
//						String year = data.getString("date");
//						float value = (float) data.getDouble("value");
//						gdpPerCapitaData.add(new CountryGDPPerCapitaData(c.getCode(), year, value, WorldBankDataWrapper.SOURCE));
//					}
//				}
//	    	}
//	    	catch (JSONException e)
//	    	{
//	    		System.err.println("GDP Per Capita data for country " + c.getCode() + " are not available");
//	    	}
//	    }
////		System.out.println(netMigrationData);
//		return gdpPerCapitaData;
//	}
	
	public ArrayList<CountryPopulationData> extractPopulationData(ArrayList<Country> countries) throws UnirestException
	{
		ArrayList<CountryPopulationData> data = new ArrayList<>();
		final String restAPI = "https://api.worldbank.org/v2/";
		final String countrySelector = "countries/";
		final String dataset = "indicators/SP.POP.TOTL";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("format", "json");
	    for (Country c: countries)
	    {
	    	HttpRequest req = Unirest.get(restAPI + countrySelector + c.getCode() + SEPARATOR + dataset).queryString(fields);
	    	System.out.println(req.getUrl());
	    	try
	    	{
	    		LOGGER.info("EXTRACTING: " + c.getCode() + ", " + PopulationData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
		    	JSONArray result = req.asJson().getBody().getArray();
		    	int pages = result.getJSONObject(0).getInt("pages");
		    	for (int i = 1; i <= pages; i++)
		    	{
			    	fields.put("page", i);
			    	req = Unirest.get(restAPI + countrySelector + c.getCode() + SEPARATOR + dataset).queryString(fields);
			    	JSONArray pageDataArray = req.asJson().getBody().getArray().getJSONArray(1);
			    	for (int j = 0; j < pageDataArray.length(); j++)
			    	{
			    		JSONObject dataObj = pageDataArray.getJSONObject(j);
			    		if (!"null".equals(dataObj.get("value").toString()))
			    		{
			    			String country = dataObj.getJSONObject("country").getString("id");
			    			String year = dataObj.getString("date");
			    			double value = Double.valueOf(String.valueOf(dataObj.get("value")));
			    			System.out.println("[" + country + ", " + year + ", " + value + "]");
			    			data.add(new CountryPopulationData(country, year, value, SOURCE));
			    		}
			    	}
			    	fields.remove("page");
		    	}
	    	}
	    	catch (JSONException e)
	    	{
	    		LOGGER.warning(PopulationData.DATA_TYPE + " data for country " + c.getCode() + " are not available, from " + WorldBankDataWrapper.SOURCE);
//	    		System.err.println("Population data for country " + c.getCode() + " are not available");
	    	}
	    }
	    return data;
	}
	
	public ArrayList<CountryPovertyData> extractPovertyData(ArrayList<Country> countries) throws UnirestException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
//		final String restAPI = "https://api.worldbank.org/v2/countries/indicators/SI.POV.NAHC";
		final String restAPI = "https://api.worldbank.org/v2/";
		final String countrySelector = "countries/";
		final String dataset = "indicators/SI.POV.NAHC";
//		final String startDate = "2000";
//		final String endDate = "2017";
//		final String queryString = "?date=" + endDate + ":" + startDate;
//		HttpRequest req = Unirest.get(restAPI + queryString);
		Map<String, Object> fields = new HashMap<>();
//	    fields.put("date", LAST_YEAR + ":" + FIRST_YEAR);
	    fields.put("format", "json");
	    for (Country c: countries)
	    {
	    	HttpRequest req = Unirest.get(restAPI + countrySelector + c.getCode() + SEPARATOR + dataset).queryString(fields);
		    System.out.println(req.getUrl());
		    try
		    {
		    	LOGGER.info("EXTRACTING: " + c.getCode() + ", " + PovertyData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
//			    System.out.println(req.asJson().getBody().getArray());
			    JSONArray result = req.asJson().getBody().getArray();
			    int pages = result.getJSONObject(0).getInt("pages");
//			    System.out.println(pages);
			    for (int i = 1; i <= pages; i++)
			    {
			    	fields.put("page", i);
			    	req = Unirest.get(restAPI + countrySelector + c.getCode() + SEPARATOR + dataset).queryString(fields);
//			    	System.out.println(req.getUrl());
			    	JSONArray pageDataArray = req.asJson().getBody().getArray().getJSONArray(1);
//			    	System.out.println(pageData);
			    	for (int j = 0; j < pageDataArray.length(); j++)
			    	{
			    		JSONObject dataObj = pageDataArray.getJSONObject(j);
//			    		System.out.println(dataObj);
//			    		System.out.println(dataObj.get("value"));
			    		if (!"null".equals(dataObj.get("value").toString()))
			    		{
//			    			System.out.println("Hey, i'm here!");
			    			String country = dataObj.getJSONObject("country").getString("id");
			    			String year = dataObj.getString("date");
			    			double value = Double.valueOf(String.valueOf(dataObj.get("value")));
			    			System.out.println("[" + country + ", " + year + ", " + value + "]");
			    			data.add(new CountryPovertyData(country, year, value, SOURCE));
			    		}
			    	}
			    	fields.remove("page");
			    }
		    }
		    catch (JSONException e)
	    	{
		    	LOGGER.warning(PovertyData.DATA_TYPE + " data for country " + c.getCode() + " are not available, from " + WorldBankDataWrapper.SOURCE);
//	    		System.err.println("Povery data for country " + c.getCode() + " are not available");
	    	}
	    }
	    return data;
	}
	
	public ArrayList<CountryNetMigrationData> extractNetMigrationData(ArrayList<Country> countries) throws UnirestException
	{
		ArrayList<CountryNetMigrationData> data = new ArrayList<>();
		final String restAPI = "https://api.worldbank.org/v2/";
		final String countrySelector = "countries/";
		final String dataset = "indicators/SM.POP.NETM";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("format", "json");
	    for (Country c: countries)
	    {
	    	HttpRequest req = Unirest.get(restAPI + countrySelector + c.getCode() + SEPARATOR + dataset).queryString(fields);
		    System.out.println(req.getUrl());
		    try
		    {
		    	LOGGER.info("EXTRACTING: " + c.getCode() + ", " + NetMigrationData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
			    JSONArray result = req.asJson().getBody().getArray();
			    int pages = result.getJSONObject(0).getInt("pages");
			    for (int i = 1; i <= pages; i++)
			    {
			    	fields.put("page", i);
			    	req = Unirest.get(restAPI + countrySelector + c.getCode() + SEPARATOR + dataset).queryString(fields);
			    	JSONArray pageDataArray = req.asJson().getBody().getArray().getJSONArray(1);
			    	for (int j = 0; j < pageDataArray.length(); j++)
			    	{
			    		JSONObject dataObj = pageDataArray.getJSONObject(j);
			    		if (!"null".equals(dataObj.get("value").toString()))
			    		{
			    			String country = dataObj.getJSONObject("country").getString("id");
			    			String year = dataObj.getString("date");
			    			double value = Double.valueOf(String.valueOf(dataObj.get("value")));
			    			System.out.println("[" + country + ", " + year + ", " + value + "]");
			    			data.add(new CountryNetMigrationData(country, year, value, SOURCE));
			    		}
			    	}
			    	fields.remove("page");
			    }
	    	}
		    catch (JSONException e)
	    	{
		    	LOGGER.warning(NetMigrationData.DATA_TYPE + " data for country " + c.getCode() + " are not available, from " + WorldBankDataWrapper.SOURCE);
//	    		System.err.println("Net Migration data for country " + c.getCode() + " are not available");
	    	}
	    }
	    return data;
	}
	
	public ArrayList<CountryGDPPerCapitaData> extractGDPPerCapitaData(ArrayList<Country> countries) throws UnirestException
	{
		ArrayList<CountryGDPPerCapitaData> data = new ArrayList<>();
		final String restAPI = "https://api.worldbank.org/v2/";
		final String countrySelector = "countries/";
		final String dataset = "indicators/NY.GDP.PCAP.PP.CD";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("format", "json");
	    for (Country c: countries)
	    {
	    	HttpRequest req = Unirest.get(restAPI + countrySelector + c.getCode() + SEPARATOR + dataset).queryString(fields);
		    System.out.println(req.getUrl());
		    try
		    {
		    	LOGGER.info("EXTRACTING: " + c.getCode() + ", " + GDPPerCapitaData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
			    JSONArray result = req.asJson().getBody().getArray();
			    int pages = result.getJSONObject(0).getInt("pages");
			    for (int i = 1; i <= pages; i++)
			    {
			    	fields.put("page", i);
			    	req = Unirest.get(restAPI + countrySelector + c.getCode() + SEPARATOR + dataset).queryString(fields);
			    	JSONArray pageDataArray = req.asJson().getBody().getArray().getJSONArray(1);
			    	for (int j = 0; j < pageDataArray.length(); j++)
			    	{
			    		JSONObject dataObj = pageDataArray.getJSONObject(j);
			    		if (!"null".equals(dataObj.get("value").toString()))
			    		{
			    			String country = dataObj.getJSONObject("country").getString("id");
			    			String year = dataObj.getString("date");
			    			double value = Double.valueOf(String.valueOf(dataObj.get("value")));
			    			System.out.println("[" + country + ", " + year + ", " + value + "]");
			    			data.add(new CountryGDPPerCapitaData(country, year, value, SOURCE));
			    		}
			    	}
			    	fields.remove("page");
			    }
		    }
		    catch (JSONException e)
	    	{
		    	LOGGER.warning(GDPPerCapitaData.DATA_TYPE + " data for country " + c.getCode() + " are not available, from " + WorldBankDataWrapper.SOURCE);
//	    		System.err.println("GDP Per Capita data for country " + c.getCode() + " are not available");
	    	} 
	    }
	    return data;
	}
	
//	public static void main(String[] args) throws UnirestException {
//		ArrayList<Country> c = new ArrayList<>();
//		c.add(new Country("BI", null));
//		c.add(new Country("CD", null));
//		c.add(new Country("DP", null));
//		WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
//		wrapper.extractPopulationData(c);
//		wrapper.extractPovertyData(c);
//		wrapper.extractNetMigrationData(c);
//		wrapper.extractGDPPerCapitaData(c);
//	}
	
	private static final Logger LOGGER = Logger.getLogger("updating");
	
	public static final String SOURCE = "worldbank";
//	private static final String FIRST_YEAR = "2000";
//	private static final String LAST_YEAR = "2017";
	private static final String SEPARATOR = "/";

}
