package manager.data.extraction;

import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import manager.data.model.CountryPopulationData;
import manager.data.model.CountryPovertyData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;

public class USADataWrapper {

	public USADataWrapper() {
		
	}
	
	public ArrayList<CountryPopulationData> extractPopulationData() throws UnirestException
	{
		ArrayList<CountryPopulationData> data = new ArrayList<>();
		final int lastYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);
		
		final String restAPI = "https://api.census.gov/";
		final String datasets = "data/timeseries/idb/5year";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("get", "POP");
	    fields.put("time", "to " + lastYear);
	    fields.put("FIPS", COUNTRY);
	    fields.put("key", "9fc8f3d6cec8faa8b9e4ea28ecd91e324c643295");
		HttpRequest req = Unirest.get(restAPI + datasets).queryString(fields);
		System.out.println(req.getUrl());
		JSONArray result = req.asJson().getBody().getArray();
		LOGGER.info("EXTRACTING: " + COUNTRY + ", " + PopulationData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
		for (int i = 1; i < result.length(); i++)
		{
			String year = result.getJSONArray(i).getString(2);
			double value = Double.valueOf(result.getJSONArray(i).getString(0));
//			System.out.println("[" + country + ", " + year + ", " + value + "]");
			data.add(new CountryPopulationData(COUNTRY, year, value, SOURCE, false));
		}
		return data;
	}
	
	public ArrayList<CountryPopulationData> extractPopulationData(PrintWriter printWriter) throws UnirestException
	{
		ArrayList<CountryPopulationData> data = new ArrayList<>();
		final int lastYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);
		
		final String restAPI = "https://api.census.gov/";
		final String datasets = "data/timeseries/idb/5year";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("get", "POP");
	    fields.put("time", "to " + lastYear);
	    fields.put("FIPS", COUNTRY);
	    fields.put("key", "9fc8f3d6cec8faa8b9e4ea28ecd91e324c643295");
		HttpRequest req = Unirest.get(restAPI + datasets).queryString(fields);
		System.out.println(req.getUrl());
		JSONArray result = req.asJson().getBody().getArray();
		LOGGER.info("EXTRACTING: " + COUNTRY + ", " + PopulationData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
		printWriter.println("EXTRACTING: " + COUNTRY + ", " + PopulationData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
		printWriter.flush();
		for (int i = 1; i < result.length(); i++)
		{
			String year = result.getJSONArray(i).getString(2);
			double value = Double.valueOf(result.getJSONArray(i).getString(0));
//			System.out.println("[" + country + ", " + year + ", " + value + "]");
			data.add(new CountryPopulationData(COUNTRY, year, value, SOURCE, false));
		}
		return data;
	}
	
	public ArrayList<CountryPovertyData> extractPovertyData() throws UnirestException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		final int lastYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);
		
		final String restAPI = "https://api.census.gov/";
		final String datasets = "data/timeseries/poverty/histpov2";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("get", "PCTPOV");
//	    fields.put("time", "from 2000");
	    fields.put("time", "to " + lastYear);
//	    fields.put("RACE", 1);
	    fields.put("key", "9fc8f3d6cec8faa8b9e4ea28ecd91e324c643295");
		HttpRequest req = Unirest.get(restAPI + datasets).queryString(fields);
//		System.out.println(req.asJson().getBody().getArray());
		JSONArray result = req.asJson().getBody().getArray();
		LOGGER.info("EXTRACTING: " + COUNTRY + ", " + PovertyData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
		for (int i = 1; i < result.length(); i++)
		{
			String year = result.getJSONArray(i).getString(1);
			double value = Double.valueOf(result.getJSONArray(i).getString(0));
//			System.out.println("[" + country + ", " + year + ", " + value + "]");
			data.add(new CountryPovertyData(COUNTRY, year, value, SOURCE, false));
		}
		return data;
	}
	
	public ArrayList<CountryPovertyData> extractPovertyData(PrintWriter printWriter) throws UnirestException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		final int lastYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);
		
		final String restAPI = "https://api.census.gov/";
		final String datasets = "data/timeseries/poverty/histpov2";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("get", "PCTPOV");
//	    fields.put("time", "from 2000");
	    fields.put("time", "to " + lastYear);
//	    fields.put("RACE", 1);
	    fields.put("key", "9fc8f3d6cec8faa8b9e4ea28ecd91e324c643295");
		HttpRequest req = Unirest.get(restAPI + datasets).queryString(fields);
//		System.out.println(req.asJson().getBody().getArray());
		JSONArray result = req.asJson().getBody().getArray();
		LOGGER.info("EXTRACTING: " + COUNTRY + ", " + PovertyData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
		printWriter.println("EXTRACTING: " + COUNTRY + ", " + PovertyData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
		printWriter.flush();
		for (int i = 1; i < result.length(); i++)
		{
			String year = result.getJSONArray(i).getString(1);
			double value = Double.valueOf(result.getJSONArray(i).getString(0));
//			System.out.println("[" + country + ", " + year + ", " + value + "]");
			data.add(new CountryPovertyData(COUNTRY, year, value, SOURCE, false));
		}
		return data;
	}
	
//	public int extractPopulationData(String year) throws UnirestException
//	{
//		final String restAPI = "https://api.census.gov/";
//		final String datasets = "data/timeseries/idb/5year";
//		Map<String, Object> fields = new HashMap<>();
//	    fields.put("get", "POP");
//	    fields.put("time", year);
//	    fields.put("FIPS", "US");
//	    fields.put("key", "9fc8f3d6cec8faa8b9e4ea28ecd91e324c643295");
//		HttpRequest req = Unirest.get(restAPI + datasets).queryString(fields);
////		System.out.println(req.getUrl());
//		JSONArray data = (JSONArray) req.asJson().getBody().getArray().get(1);
//		return data.getInt(0);
//	}
	
//	public static void main(String[] args) throws UnirestException {
//		System.out.println((new USADataWrapper()).extractPopulationData());
//	}
	
	public Date lastUpdateDate(String dataType) throws UnirestException
	{
		switch (dataType)
		{
			case PopulationData.DATA_TYPE:
				return populationLastUpdateDate();
			case PovertyData.DATA_TYPE:
				return povertyLastUpdateDate();
			default:
				return null;
		}
	}
	
	public Date populationLastUpdateDate() throws UnirestException
	{
		final String restAPI = "https://api.census.gov/";
		final String datasets = "data/timeseries/idb/5year";
		HttpRequest req = Unirest.get(restAPI + datasets);
		JSONObject result = req.asJson().getBody().getObject();
		String dateSTR = ((JSONObject) ((JSONArray) result.get("dataset")).get(0)).getString("modified");
		Date updateDate = getDate(dateSTR);
		return updateDate;
	}
	
	public Date povertyLastUpdateDate() throws UnirestException
	{
		final String restAPI = "https://api.census.gov/";
		final String datasets = "data/timeseries/poverty/histpov2";
		HttpRequest req = Unirest.get(restAPI + datasets);
		JSONObject result = req.asJson().getBody().getObject();
		String dateSTR = ((JSONObject) ((JSONArray) result.get("dataset")).get(0)).getString("modified");
		Date updateDate = getDate(dateSTR);
//		Date currentDate = getCurrentDate(); 
//		System.out.println(updateDate);
//		System.out.println(currentDate);
//		System.out.println(updateDate.after(currentDate));
		return updateDate;
	}
	
	private Date getDate(String dateSTR)
	{
		String[] dateComponents = dateSTR.split("-");
		Date date = new Date(new GregorianCalendar(Integer.valueOf(dateComponents[0]), Integer.valueOf(dateComponents[1]) - 1, Integer.valueOf(dateComponents[2])).getTimeInMillis());
		return date;
	}
	
	private static final Logger LOGGER = Logger.getLogger("updating");
	
	public static final String SOURCE = "census";
	private static final String COUNTRY = "US";

}
