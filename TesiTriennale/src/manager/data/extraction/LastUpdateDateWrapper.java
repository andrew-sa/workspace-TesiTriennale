package manager.data.extraction;

import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

public class LastUpdateDateWrapper {

	public LastUpdateDateWrapper() {
		
	}
	
	public Date lastUpdateDateUSA() throws UnirestException
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
	
	public Date lastUpdateDateEU() throws UnirestException
	{
		final String restAPI = "http://ec.europa.eu/eurostat/wdds/rest/data/v2.1/json/en/";
		final String dataset = "ilc_li02";
		Map<String, Object> fields = new HashMap<>();
		fields.put("INDIC_IL", "LI_R_MD50");
	    fields.put("sex", "T");
	    fields.put("precision", 1);
	    fields.put("sinceTimePeriod", 2000);
	    fields.put("unit", "PC");
	    fields.put("age", "TOTAL");
	    fields.put("filterNonGeo", 1);
	    fields.put("shortLabel", 1);
	    fields.put("unitLabel", "code");
		HttpRequest req = Unirest.get(restAPI + dataset).queryString(fields);
		JSONObject result = req.asJson().getBody().getObject();
		String dateSTR = result.getString("updated");
		Date updateDate = getDate(dateSTR);
//		Date currentDate = getCurrentDate(); 
//		System.out.println(updateDate);
//		System.out.println(currentDate);
//		System.out.println(updateDate.after(currentDate));
		return updateDate;
	}
	
	public Date lastUpdateDateWorldBank() throws UnirestException
	{
		final String restAPI = "https://api.worldbank.org/v2/countries/indicators/SI.POV.NAHC";
		final String startDate = "2000";
		final String endDate = "2017";
		Map<String, Object> fields = new HashMap<>();
	    fields.put("date", endDate + ":" + startDate);
	    fields.put("format", "json");
	    HttpRequest req = Unirest.get(restAPI).queryString(fields);
	    JSONArray result = req.asJson().getBody().getArray();
		String dateSTR = ((JSONObject) result.get(0)).getString("lastupdated");
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
	
//	private Date getCurrentDate()
//	{
//		return new Date(new GregorianCalendar().getTimeInMillis());
//	}
	
//	public static void main(String[] args) throws UnirestException {
//		LastUpdateDateWrapper wrapper = new LastUpdateDateWrapper();
//		wrapper.lastUpdateDateUSA();
//		wrapper.lastUpdateDateEU();
//		wrapper.lastUpdateDateWorldBank();
//	}

}
