package manager.data.loading;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import org.json.JSONException;

import com.mashape.unirest.http.exceptions.UnirestException;

import manager.data.dao.BoundaryValueDAO;
import manager.data.dao.CountryDAO;
import manager.data.dao.CountryPovertyDataDAO;
import manager.data.dao.RegionPovertyDataDAO;
import manager.data.extraction.EUDataWrapper;
import manager.data.extraction.USADataWrapper;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.Country;
import manager.data.model.CountryPovertyData;
import manager.data.model.PovertyData;
import manager.data.model.RegionPovertyData;
import manager.data.transformation.EUDataTrasformer;
import manager.data.transformation.USADataTrasformer;

public class LoadPovertyData {

	public LoadPovertyData() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadUSAData() throws UnirestException, SQLException
	{
		USADataWrapper wrapper = new USADataWrapper();
		ArrayList<CountryPovertyData> data = wrapper.extractPovertyData();
		USADataTrasformer trasformer = new USADataTrasformer();
		data = trasformer.removeDuplicates(data);
		deleteDataOnDB(USADataWrapper.SOURCE);
		saveDataOnDB(data);
	}
	
	public void loadEUData() throws UnirestException, SQLException
	{
		EUDataWrapper wrapper = new EUDataWrapper();
		ArrayList<CountryPovertyData> data = wrapper.extractPovertyData();
		EUDataTrasformer trasformer = new EUDataTrasformer();
		data = trasformer.correctCountriesCodesPovertyData(data);
		deleteDataOnDB(EUDataWrapper.SOURCE);
		saveDataOnDB(data);
	}
	
	public void loadWorldBankData() throws UnirestException, SQLException
	{
		CountryDAO countryDAO = new CountryDAO();
		ArrayList<Country> countries = countryDAO.readCountryForWorldBankOfADataType(PovertyData.DATA_TYPE);
		WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
		ArrayList<CountryPovertyData> data = wrapper.extractPovertyData(countries);
//		WorldBankDataTrasformer trasformer = new WorldBankDataTrasformer();
//		data = trasformer.removeDuplicates(data);
		deleteDataOnDB(WorldBankDataWrapper.SOURCE);
		saveDataOnDB(data);
	}
	
	public void loadBoundaryValues() throws SQLException
	{
		BoundaryValueDAO boundaryValueDAO = new BoundaryValueDAO();
		boundaryValueDAO.delete(PovertyData.DATA_TYPE);
		boundaryValueDAO.save(PovertyData.DATA_TYPE);
	}
	
	public void loadRegionsData() throws SQLException
	{
	/*	ArrayList<RegionPovertyData> regionsData = new ArrayList<>();
		final int lastYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);
		for (int i = FIRST_YEAR; i <= lastYear; i++)
		{
//			float worldNumerator = 0.0f;
//			float worldTotalPopulation = 0.0f;
//			int regionsAvailable = 0;
			for (int j = 0; j < REGIONS.length; j++)
			{
				CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
				ArrayList<CountryPovertyData> data = povertyDataDAO.readByRegionAndYear(REGIONS[j], String.valueOf(i));
				float numerator = 0.0f;
				float totalPopulation = 0.0f;
				for (CountryPovertyData pd: data)
				{
					switch (pd.getSource())
					{
						case USADataWrapper.SOURCE:
						{
							try
							{
								USADataWrapper wrapper = new USADataWrapper();
								float population = wrapper.extractPopulationData(pd.getYear());
								numerator += population * pd.getValue();
								totalPopulation += population;
							}
							catch (UnirestException e)
							{
								numerator += 0.0f;
								totalPopulation += 0.0f;
							}
							break;
						}
						case EUDataWrapper.SOURCE:
						{
							try
							{
								EUDataWrapper wrapper = new EUDataWrapper();
								float population = wrapper.extractPopulationData(pd.getName(), pd.getYear());
								numerator += population * pd.getValue();
								totalPopulation += population;
							}
							catch (UnirestException | JSONException e)
							{
								numerator += 0.0f;
								totalPopulation += 0.0f;
							}
							break;
						}
						case WorldBankDataWrapper.SOURCE:
						{
							try
							{
								WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
								float population = wrapper.extractPopulationData(pd.getName(), pd.getYear());
								numerator += population * pd.getValue();
								totalPopulation += population;
							}
							catch (UnirestException e)
							{
								numerator += 0.0f;
								totalPopulation += 0.0f;
							}
							break;
						}
						default:
							break;
					}
				}
				if (numerator != 0.0f)
				{
//					regionsAvailable++;
					regionsData.add(new RegionPovertyData(REGIONS[j], String.valueOf(i), (numerator / totalPopulation)));
//					worldNumerator += ((numerator / totalPopulation) * totalPopulation);
//					worldTotalPopulation += totalPopulation;
				}
//				System.out.println("[" + REGIONS[i] + ", " + j + ", " + (numerator / totalPopulation) + "]");
			}
//			if ((regionsAvailable == (REGIONS.length - 1)) && worldNumerator != 0.0f)
//			{
//				regionsData.add(new RegionPovertyData(REGIONS[REGIONS.length - 1], String.valueOf(i), (worldNumerator / worldTotalPopulation)));
//			}
		}
		RegionPovertyDataDAO regionPovertyDataDAO = new RegionPovertyDataDAO();
		regionPovertyDataDAO.delete();
		regionPovertyDataDAO.save(regionsData);*/
	}
	
	private void saveDataOnDB(ArrayList<CountryPovertyData> data) throws SQLException
	{
		CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
		povertyDataDAO.save(data);
	}
	
	private void deleteDataOnDB(String source) throws SQLException
	{
		CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
		povertyDataDAO.delete(source);
	}
	
	public static void main(String[] args) throws UnirestException, SQLException {
		LoadPovertyData loadPovertyData = new LoadPovertyData();
		loadPovertyData.loadUSAData();
		loadPovertyData.loadEUData();
		loadPovertyData.loadWorldBankData();
		loadPovertyData.loadBoundaryValues();
//		loadPovertyData.loadRegionsData();
	}

	private static final String[] REGIONS = {"Africa", "Europe", "Americas", "Asia", "Oceania"/*, "World"*/};
	private static final int FIRST_YEAR = 2000;
	
}
