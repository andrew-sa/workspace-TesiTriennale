package manager.data.extraction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import manager.data.model.CountryNetMigrationData;
import manager.data.model.CountryPopulationData;
import manager.data.model.CountryPovertyData;
import manager.data.model.NetMigrationData;
import manager.data.model.PopulationData;
import manager.data.model.PovertyData;

public class EUDataWrapper {

	public EUDataWrapper() {
		
	}
	
	public ArrayList<CountryPopulationData> extractPopulationData() throws UnirestException, JSONException
	{
		ArrayList<CountryPopulationData> data = new ArrayList<>();
		
		final String restAPI = "http://ec.europa.eu/eurostat/wdds/rest/data/v2.1/json/en/";
		final String dataset = "demo_pjan";
		Map<String, Object> fields = new HashMap<>();
		fields.put("unit", "NR");
	    fields.put("sex", "T");
	    fields.put("age", "TOTAL");
	    fields.put("filterNonGeo", 1);
	    HttpRequest req = Unirest.get(restAPI + dataset).queryString(fields);
		System.out.println(req.getUrl());
		JSONObject resultObj = req.asJson().getBody().getObject();
		final int yearsSize = Integer.parseInt(resultObj.query("/size/4").toString());
//		System.out.println(yearsSize);
		JSONObject countries = (JSONObject) resultObj.query("/dimension/geo/category/index");
//		System.out.println(countries);
		JSONObject years = (JSONObject) resultObj.query("/dimension/time/category/index");
//		System.out.println(years);
		Iterator<String> c = countries.keys();
//		System.out.println();
		while (c.hasNext())
		{
			String countryCode = c.next();
			LOGGER.info("EXTRACTING: " + countryCode + ", " + PopulationData.DATA_TYPE + ", " + EUDataWrapper.SOURCE);
//			System.out.println(countryCode);
			int countryNumber = countries.getInt(countryCode);
//			System.out.println(countryNumber);
			Iterator<String> y = years.keys();
			while (y.hasNext())
			{
				String year = y.next();
//				System.out.println(year);
				int yearNumber = years.getInt(year);
//				System.out.println(yearNumber);
//				System.out.println("Value position: " + (countryNumber * yearsSize + yearNumber));
				if (resultObj.query("/value/" + (countryNumber * yearsSize + yearNumber)) != null)
				{
					Float value = Float.valueOf(resultObj.query("/value/" + (countryNumber * yearsSize + yearNumber)).toString());
					System.out.println("[" + countryCode + ", " + year + ", " + value + "]");
					data.add(new CountryPopulationData(countryCode, year, value, SOURCE));
				}
			}
//			System.out.println();
		}
		
		return data;
	}

	public ArrayList<CountryPovertyData> extractPovertyData() throws UnirestException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		final String restAPI = "http://ec.europa.eu/eurostat/wdds/rest/data/v2.1/json/en/";
		final String dataset = "ilc_li02";
		Map<String, Object> fields = new HashMap<>();
		fields.put("indic_il", "LI_R_MD50");
	    fields.put("sex", "T");
	    fields.put("precision", 1);
//	    fields.put("sinceTimePeriod", 2000);
	    fields.put("unit", "PC");
	    fields.put("age", "TOTAL");
	    fields.put("filterNonGeo", 1);
	    fields.put("shortLabel", 1);
	    fields.put("unitLabel", "code");
		HttpRequest req = Unirest.get(restAPI + dataset).queryString(fields);
		System.out.println(req.getUrl());
		JSONObject resultObj = req.asJson().getBody().getObject();
		final int yearsSize = Integer.parseInt(resultObj.query("/size/5").toString());
//		System.out.println(yearsSize);
		JSONObject countries = (JSONObject) resultObj.query("/dimension/geo/category/index");
//		System.out.println(countries);
		JSONObject years = (JSONObject) resultObj.query("/dimension/time/category/index");
//		System.out.println(years);
		Iterator<String> c = countries.keys();
//		System.out.println();
		while (c.hasNext())
		{
			String countryCode = c.next();
			LOGGER.info("EXTRACTING: " + countryCode + ", " + PovertyData.DATA_TYPE + ", " + EUDataWrapper.SOURCE);
//			System.out.println(countryCode);
			int countryNumber = countries.getInt(countryCode);
//			System.out.println(countryNumber);
			Iterator<String> y = years.keys();
			while (y.hasNext())
			{
				String year = y.next();
//				System.out.println(year);
				int yearNumber = years.getInt(year);
//				System.out.println(yearNumber);
//				System.out.println("Value position: " + (countryNumber * yearsSize + yearNumber));
				if (resultObj.query("/value/" + (countryNumber * yearsSize + yearNumber)) != null)
				{
					double value = Double.valueOf(resultObj.query("/value/" + (countryNumber * yearsSize + yearNumber)).toString());
					System.out.println("[" + countryCode + ", " + year + ", " + value + "]");
					data.add(new CountryPovertyData(countryCode, year, value, SOURCE));
				}
			}
//			System.out.println();
		}
		return data;
	}
	
//	public int extractPopulationData(String countryCode, String year) throws UnirestException, JSONException
//	{
//		final String restAPI = "http://ec.europa.eu/eurostat/wdds/rest/data/v2.1/json/en/";
//		final String dataset = "demo_pjan";
//		Map<String, Object> fields = new HashMap<>();
//		fields.put("geo", countryCode);
//		fields.put("time", year);
//		fields.put("unit", "NR");
//	    fields.put("sex", "T");
//	    fields.put("age", "TOTAL");
//	    HttpRequest req = Unirest.get(restAPI + dataset).queryString(fields);
//		System.out.println(req.getUrl());
//		JSONObject resultOBJ = req.asJson().getBody().getObject();
//		JSONObject values = resultOBJ.getJSONObject("value");
//		return values.getInt("0");
//	}
	
	public ArrayList<CountryNetMigrationData> extractNetMigrationData() throws UnirestException
	{
		LOGGER.info("EXTRACTING: " + "All EU countries" + ", " + NetMigrationData.DATA_TYPE + ", " + EUDataWrapper.SOURCE);
		ArrayList<CountryNetMigrationData> netMigrationData = new ArrayList<>();
		ArrayList<CountryNetMigrationData> immigrationData = extractImmigrationData();
		ArrayList<CountryNetMigrationData> emigrationData = extractEmigrationData();
		for (CountryNetMigrationData id: immigrationData)
		{
			boolean found = false;
			int i = 0;
			while (!found && i < emigrationData.size())
			{
				CountryNetMigrationData ed = emigrationData.get(i);
				if (id.equals(ed))
				{
					found = true;
//					float population = extractPopulationData(id.getName(), id.getYear());
					double netMigrationValue = id.getValue() - ed.getValue();
//					System.out.println("[" + id.getName() + ", " + id.getYear() + ", " + netMigrationValue + "]");
					netMigrationData.add(new CountryNetMigrationData(id.getName(), id.getYear(), netMigrationValue, EUDataWrapper.SOURCE));
				}
				i++;
			}
		}
//		System.out.println(netMigrationData);
		return netMigrationData;
	}
	
	private ArrayList<CountryNetMigrationData> extractImmigrationData() throws UnirestException
	{
		ArrayList<CountryNetMigrationData> data = new ArrayList<>();
		final String restAPI = "http://ec.europa.eu/eurostat/wdds/rest/data/v2.1/json/en/";
		final String dataset = "migr_imm8";
		Map<String, Object> fields = new HashMap<>();
//		fields.put("sinceTimePeriod", 2000);
		fields.put("unit", "NR");
	    fields.put("sex", "T");
	    fields.put("age", "TOTAL");
	    fields.put("agedef", "REACH");
	    HttpRequest req = Unirest.get(restAPI + dataset).queryString(fields);
	    JSONObject resultObj = req.asJson().getBody().getObject();
		final int yearsSize = Integer.parseInt(resultObj.query("/size/5").toString());
//		System.out.println(yearsSize);
		JSONObject countries = (JSONObject) resultObj.query("/dimension/geo/category/index");
//		System.out.println(countries);
		JSONObject years = (JSONObject) resultObj.query("/dimension/time/category/index");
//		System.out.println(years);
		Iterator<String> c = countries.keys();
//		System.out.println();
		while (c.hasNext())
		{
			String countryCode = c.next();
//			System.out.println(countryCode);
			int countryNumber = countries.getInt(countryCode);
//			System.out.println(countryNumber);
			Iterator<String> y = years.keys();
			while (y.hasNext())
			{
				String year = y.next();
//				System.out.println(year);
				int yearNumber = years.getInt(year);
//				System.out.println(yearNumber);
//				System.out.println("Value position: " + (countryNumber * yearsSize + yearNumber));
				if (resultObj.query("/value/" + (countryNumber * yearsSize + yearNumber)) != null)
				{
					double value = Double.valueOf(resultObj.query("/value/" + (countryNumber * yearsSize + yearNumber)).toString());
//					System.out.println("[" + countryCode + ", " + year + ", " + value + "]");
					data.add(new CountryNetMigrationData(countryCode, year, value));
				}
			}
//			System.out.println();
		}
		return data;
	}
	
	private ArrayList<CountryNetMigrationData> extractEmigrationData() throws UnirestException
	{
		ArrayList<CountryNetMigrationData> data = new ArrayList<>();
		final String restAPI = "http://ec.europa.eu/eurostat/wdds/rest/data/v2.1/json/en/";
		final String dataset = "migr_emi2";
		Map<String, Object> fields = new HashMap<>();
//		fields.put("sinceTimePeriod", 2000);
		fields.put("unit", "NR");
	    fields.put("sex", "T");
	    fields.put("age", "TOTAL");
	    fields.put("agedef", "REACH");
	    HttpRequest req = Unirest.get(restAPI + dataset).queryString(fields);
	    JSONObject resultObj = req.asJson().getBody().getObject();
		final int yearsSize = Integer.parseInt(resultObj.query("/size/5").toString());
//		System.out.println(yearsSize);
		JSONObject countries = (JSONObject) resultObj.query("/dimension/geo/category/index");
//		System.out.println(countries);
		JSONObject years = (JSONObject) resultObj.query("/dimension/time/category/index");
//		System.out.println(years);
		Iterator<String> c = countries.keys();
//		System.out.println();
		while (c.hasNext())
		{
			String countryCode = c.next();
//			System.out.println(countryCode);
			int countryNumber = countries.getInt(countryCode);
//			System.out.println(countryNumber);
			Iterator<String> y = years.keys();
			while (y.hasNext())
			{
				String year = y.next();
//				System.out.println(year);
				int yearNumber = years.getInt(year);
//				System.out.println(yearNumber);
//				System.out.println("Value position: " + (countryNumber * yearsSize + yearNumber));
				if (resultObj.query("/value/" + (countryNumber * yearsSize + yearNumber)) != null)
				{
					double value = Double.valueOf(resultObj.query("/value/" + (countryNumber * yearsSize + yearNumber)).toString());
//					System.out.println("[" + countryCode + ", " + year + ", " + value + "]");
					data.add(new CountryNetMigrationData(countryCode, year, value));
				}
			}
//			System.out.println();
		}
		return data;
	}
	
//	public static void main(String[] args) throws UnirestException {
////		try {
////			System.out.println((new EUDataWrapper()).extractPopulationData("XK", "2014"));
////		} catch (UnirestException | JSONException e) {
////			System.out.println("HI!");
////			e.printStackTrace();
////		}
//		(new EUDataWrapper()).extractNetMigrationData();
//	}
	
	private static final Logger LOGGER = Logger.getLogger("updating");
	
	public static final String SOURCE = "eurostat";

}
