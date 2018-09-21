package manager.data.extraction;

import org.json.JSONObject;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;

import manager.data.model.Country;

public class CountryAdditionalFieldsWrapper {

	public CountryAdditionalFieldsWrapper() {
		// TODO Auto-generated constructor stub
	}
	
	public void extractData(Country country) throws UnirestException
	{
		final String restAPI = "https://restcountries.eu/rest/v2/";
		final String apiEndPoint = "alpha/" + country.getCode();
		HttpRequest req = Unirest.get(restAPI + apiEndPoint);
		JSONObject resultObj = req.asJson().getBody().getObject();
//		String name = resultObj.getString("name");
		String region = resultObj.getString("region");
//		country.setName(name);
		country.setRegion(region);
	}

}
