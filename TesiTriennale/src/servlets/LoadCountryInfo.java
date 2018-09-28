package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import exceptions.TupleNotFoundException;
import manager.data.dao.CountryDAO;
import manager.data.dao.CountryGDPPerCapitaDataDAO;
import manager.data.dao.CountryNetMigrationDataDAO;
import manager.data.dao.CountryPovertyDataDAO;
import manager.data.extraction.GoogleLatestNewsWrapper;
import manager.data.extraction.WikipediaCountryWrapper;
import manager.data.model.Country;
import manager.data.model.CountryGDPPerCapitaData;
import manager.data.model.CountryNetMigrationData;
import manager.data.model.CountryWikipedia;
import manager.data.model.News;
import manager.data.model.CountryPovertyData;

@WebServlet("/load_country_info")
public class LoadCountryInfo extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoadCountryInfo() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		response.setContentType("text/html");
		String countryCode = request.getParameter("country");
		System.out.println(countryCode);
//		String countryCode = "IT";
		try
		{
			CountryDAO countryDAO = new CountryDAO();
			Country country = countryDAO.read(countryCode);
			
			CountryWikipedia countryWiki = loadCountryWikipedia(country);
			ArrayList<CountryPovertyData> povertyData = loadPovertyData(countryWiki);
			ArrayList<CountryNetMigrationData> netMigrationData = loadNetMigrationData(countryWiki);
			ArrayList<CountryGDPPerCapitaData> gdpPerCapitaData = loadGDPPerCapitaData(countryWiki);
			ArrayList<News> news = loadLatestNews(country);
			
			JSONObject infoJSON = new JSONObject();
			JSONObject wikiJSON = new JSONObject();
			JSONArray povertyArray = new JSONArray();
			JSONArray netMigrationArray = new JSONArray();
			JSONArray gdpPerCapitaArray = new JSONArray();
			JSONArray newsArray = new JSONArray();
			
			wikiJSON.put("name", countryWiki.getName());
			wikiJSON.put("image", countryWiki.getImageURL());
			wikiJSON.put("description", countryWiki.getDescription());
			
			for (CountryPovertyData pd: povertyData)
			{
				JSONObject povertyJSON = new JSONObject();
				povertyJSON.put("year", pd.getYear());
				povertyJSON.put("value", pd.getValue());
				povertyArray.put(povertyJSON);
			}
			
			for (CountryNetMigrationData nmd: netMigrationData)
			{
				JSONObject netMigrationJSON = new JSONObject();
				netMigrationJSON.put("year", nmd.getYear());
				netMigrationJSON.put("value", nmd.getValue());
				netMigrationArray.put(netMigrationJSON);
			}
			
			for (CountryGDPPerCapitaData gdp: gdpPerCapitaData)
			{
				JSONObject gdpPerCapitaJSON = new JSONObject();
				gdpPerCapitaJSON.put("year", gdp.getYear());
				gdpPerCapitaJSON.put("value", gdp.getValue());
				gdpPerCapitaArray.put(gdpPerCapitaJSON);
			}
			
			for (News n: news)
			{
				JSONObject newsJSON = new JSONObject();
				newsJSON.put("title", n.getTitle());
				newsJSON.put("info", n.getInfo());
				newsJSON.put("url", n.getURL());
				newsJSON.put("description", n.getDescription());
				newsArray.put(newsJSON);
			}
			
			infoJSON.put("wiki", wikiJSON);
			infoJSON.put("poverty", povertyArray);
			infoJSON.put("netMigration", netMigrationArray);
			infoJSON.put("gdpPerCapita", gdpPerCapitaArray);
			infoJSON.put("news", newsArray);
			
			System.out.println(infoJSON.toString());
			response.getWriter().write(infoJSON.toString());
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		catch (TupleNotFoundException e)
		{
			e.printStackTrace();
		}
		
	}
	
	private CountryWikipedia loadCountryWikipedia(Country country) throws IOException
	{
		WikipediaCountryWrapper wrapper = new WikipediaCountryWrapper();
		CountryWikipedia countryWiki = wrapper.extractData(country);
		return countryWiki;
	}
	
	private ArrayList<CountryPovertyData> loadPovertyData(Country country) throws SQLException
	{
		CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
		ArrayList<CountryPovertyData> povertyData = povertyDataDAO.readRealValuesOfTheCountry(country);
		return povertyData;
	}
	
	private ArrayList<CountryNetMigrationData> loadNetMigrationData(Country country) throws SQLException
	{
		CountryNetMigrationDataDAO netMigrationDataDAO = new CountryNetMigrationDataDAO();
		ArrayList<CountryNetMigrationData> netMigrationData = netMigrationDataDAO.read(country);
		return netMigrationData;
	}
	
	private ArrayList<CountryGDPPerCapitaData> loadGDPPerCapitaData(Country country) throws SQLException
	{
		CountryGDPPerCapitaDataDAO gdpPerCapitaDataDAO = new CountryGDPPerCapitaDataDAO();
		ArrayList<CountryGDPPerCapitaData> gdpPerCapitaData = gdpPerCapitaDataDAO.readReal(country);
		return gdpPerCapitaData;
	}
	
	private ArrayList<News> loadLatestNews(Country country) throws IOException
	{
		GoogleLatestNewsWrapper wrapper = new GoogleLatestNewsWrapper();
		ArrayList<News> news = wrapper.extractData(country.getName());
		return news;
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
	
//	public static void main(String[] args) throws ServletException, IOException {
//		(new LoadCountryInfo()).doGet(null, null);
//	}
}
