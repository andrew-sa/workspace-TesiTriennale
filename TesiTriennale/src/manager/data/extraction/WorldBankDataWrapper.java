package manager.data.extraction;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import manager.data.model.CompareToYear;
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
			    			data.add(new CountryPopulationData(country, year, value, SOURCE, false));
			    			
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
	
	public ArrayList<CountryPopulationData> extractPopulationData(ArrayList<Country> countries, PrintWriter printWriter) throws UnirestException
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
	    		printWriter.println("EXTRACTING: " + c.getCode() + ", " + PopulationData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
	    		printWriter.flush();
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
			    			data.add(new CountryPopulationData(country, year, value, SOURCE, false));
			    			
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
		    	ArrayList<CountryPovertyData> countryData = new ArrayList<>();
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
//			    			data.add(new CountryPovertyData(country, year, value, SOURCE, false));
			    			countryData.add(new CountryPovertyData(country, year, value, SOURCE, false));
			    		}
			    	}
			    	fields.remove("page");
			    }
			    ArrayList<CountryPovertyData> allCountryData = calculateInterpolatedValus(countryData);
			    System.out.println("Data WITHOUT interpolated values: " + countryData);
			    System.out.println("Data WITH interpolated values: " + allCountryData);
			    data.addAll(allCountryData);
		    }
		    catch (JSONException e)
	    	{
		    	LOGGER.warning(PovertyData.DATA_TYPE + " data for country " + c.getCode() + " are not available, from " + WorldBankDataWrapper.SOURCE);
//	    		System.err.println("Povery data for country " + c.getCode() + " are not available");
	    	}
	    }
	    return data;
	}
	
	public ArrayList<CountryPovertyData> extractPovertyData(ArrayList<Country> countries, PrintWriter printWriter) throws UnirestException
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
		    	ArrayList<CountryPovertyData> countryData = new ArrayList<>();
		    	LOGGER.info("EXTRACTING: " + c.getCode() + ", " + PovertyData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
		    	printWriter.println("EXTRACTING: " + c.getCode() + ", " + PovertyData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
		    	printWriter.flush();
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
//			    			data.add(new CountryPovertyData(country, year, value, SOURCE, false));
			    			countryData.add(new CountryPovertyData(country, year, value, SOURCE, false));
			    		}
			    	}
			    	fields.remove("page");
			    }
			    ArrayList<CountryPovertyData> allCountryData = calculateInterpolatedValus(countryData);
			    System.out.println("Data WITHOUT interpolated values: " + countryData);
			    System.out.println("Data WITH interpolated values: " + allCountryData);
			    data.addAll(allCountryData);
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
	
	public ArrayList<CountryNetMigrationData> extractNetMigrationData(ArrayList<Country> countries, PrintWriter printWriter) throws UnirestException
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
		    	printWriter.println("EXTRACTING: " + c.getCode() + ", " + NetMigrationData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
		    	printWriter.flush();
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
			    			data.add(new CountryGDPPerCapitaData(country, year, value, SOURCE, false));
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
	
	public ArrayList<CountryGDPPerCapitaData> extractGDPPerCapitaData(ArrayList<Country> countries, PrintWriter printWriter) throws UnirestException
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
		    	printWriter.println("EXTRACTING: " + c.getCode() + ", " + GDPPerCapitaData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
		    	printWriter.flush();
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
			    			data.add(new CountryGDPPerCapitaData(country, year, value, SOURCE, false));
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
	
	private ArrayList<CountryPovertyData> calculateInterpolatedValus(ArrayList<CountryPovertyData> realData)
	{
		if (realData.size() < 2)
		{
			return realData;
		}
		else
		{
			realData.sort(new CompareToYear());
			ArrayList<CountryPovertyData> data = new ArrayList<>();
			final String country = realData.get(0).getName();
			int i;
			for (i = 0; i < realData.size() - 1; i++)
			{
				data.add(realData.get(i));
				int firstYear = Integer.valueOf(realData.get(i).getYear());
				int secondYear = Integer.valueOf(realData.get(i + 1).getYear());
				if (secondYear - firstYear > 1)
				{
					double hole = secondYear - firstYear;
					if (realData.get(i).getValue() < realData.get(i + 1).getValue())
					{
						double gap = realData.get(i + 1).getValue() - realData.get(i).getValue();
						double annualGap = gap / hole;
						for (int j = 1; j < hole; j++)
						{
							CountryPovertyData pd = new CountryPovertyData();
							pd.setName(country);
							pd.setYear(String.valueOf(firstYear + j));
							pd.setValue(realData.get(i).getValue() + annualGap * j);
							pd.setSource(WorldBankDataWrapper.SOURCE);
							pd.setCalculated(true);
							System.out.println(pd);
							data.add(pd);
						}
					}
					else
					{
						double gap = realData.get(i).getValue() - realData.get(i + 1).getValue();
						double annualGap = gap / hole;
						for (int j = 1; j < hole; j++)
						{
							CountryPovertyData pd = new CountryPovertyData();
							pd.setName(country);
							pd.setYear(String.valueOf(firstYear + j));
							pd.setValue(realData.get(i).getValue() - annualGap * j);
							pd.setSource(WorldBankDataWrapper.SOURCE);
							pd.setCalculated(true);
							System.out.println(pd);
							data.add(pd);
						}
					}
				}
			}
			data.add(realData.get(i));
			return data;
		}
	}
	
//	private ArrayList<Data> calculateInterpolatedValus(ArrayList<Data> realData)
//	{
//		if (realData.size() < 2)
//		{
//			return realData;
//		}
//		else
//		{
//			ArrayList<Data> data = new ArrayList<>();
//			if (realData.get(0) instanceof CountryPopulationData)
//			{
//				
//			}
//			else if (realData.get(0) instanceof CountryPovertyData)
//			{
//				data.add(realData.get(0));
//				for (int i = 0; i < realData.size() - 1; i++)
//				{
//					int firstYear = Integer.valueOf(realData.get(i + 1).getYear());
//					if (Integer.realData.get(i + 1).getYear() - realData.get(i).getYear() > 1)
//					{
//						
//					}
//				}
//			}
//			else if (realData.get(0) instanceof CountryGDPPerCapitaData)
//			{
//				
//			}
//			else
//			{
//				data.addAll(realData);
//			}
//			return data;
//		}
//	}
	
	public Date lastUpdateDate(String dataType) throws UnirestException
	{
		switch (dataType)
		{
			case PopulationData.DATA_TYPE:
				return populationLastUpdateDate();
			case PovertyData.DATA_TYPE:
				return povertyLastUpdateDate();
			case NetMigrationData.DATA_TYPE:
				return netMigrationLastUpdateDate();
			case GDPPerCapitaData.DATA_TYPE:
				return gdpPerCapitaDataLastUpdateDate();
			default:
				return null;
		}
	}
	
	public Date populationLastUpdateDate() throws UnirestException
	{
		final String restAPI = "https://api.worldbank.org/v2/";
		final String countrySelector = "countries/";
		final String dataset = "indicators/SP.POP.TOTL";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("format", "json");
	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
	    JSONArray result = req.asJson().getBody().getArray();
		String dateSTR = ((JSONObject) result.get(0)).getString("lastupdated");
		Date updateDate = getDate(dateSTR);
		return updateDate;
	}
	
	public Date povertyLastUpdateDate() throws UnirestException
	{
		final String restAPI = "https://api.worldbank.org/v2/";
		final String countrySelector = "countries/";
		final String dataset = "indicators/SI.POV.NAHC";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("format", "json");
	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
	    JSONArray result = req.asJson().getBody().getArray();
		String dateSTR = ((JSONObject) result.get(0)).getString("lastupdated");
		Date updateDate = getDate(dateSTR);
		return updateDate;
	}
	
	public Date netMigrationLastUpdateDate() throws UnirestException
	{
		final String restAPI = "https://api.worldbank.org/v2/";
		final String countrySelector = "countries/";
		final String dataset = "indicators/SM.POP.NETM";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("format", "json");
	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
	    JSONArray result = req.asJson().getBody().getArray();
		String dateSTR = ((JSONObject) result.get(0)).getString("lastupdated");
		Date updateDate = getDate(dateSTR);
		return updateDate;
	}
	
	public Date gdpPerCapitaDataLastUpdateDate() throws UnirestException
	{
		final String restAPI = "https://api.worldbank.org/v2/";
		final String countrySelector = "countries/";
		final String dataset = "indicators/NY.GDP.PCAP.PP.CD";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("format", "json");
	    HttpRequest req = Unirest.get(restAPI + countrySelector + dataset).queryString(fields);
	    JSONArray result = req.asJson().getBody().getArray();
		String dateSTR = ((JSONObject) result.get(0)).getString("lastupdated");
		Date updateDate = getDate(dateSTR);
		return updateDate;
	}
	
	private Date getDate(String dateSTR)
	{
		String[] dateComponents = dateSTR.split("-");
		Date date = new Date(new GregorianCalendar(Integer.valueOf(dateComponents[0]), Integer.valueOf(dateComponents[1]) - 1, Integer.valueOf(dateComponents[2])).getTimeInMillis());
		return date;
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
