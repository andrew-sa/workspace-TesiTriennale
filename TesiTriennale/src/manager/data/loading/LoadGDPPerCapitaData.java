package manager.data.loading;

import java.sql.SQLException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;

import manager.data.dao.BoundaryValueDAO;
import manager.data.dao.CountryDAO;
import manager.data.dao.CountryGDPPerCapitaDataDAO;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.Country;
import manager.data.model.CountryGDPPerCapitaData;
import manager.data.model.GDPPerCapitaData;

public class LoadGDPPerCapitaData {

	public LoadGDPPerCapitaData() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadWorldBankData() throws UnirestException, SQLException
	{
		CountryDAO countryDAO = new CountryDAO();
		ArrayList<Country> countries = countryDAO.readCountryForWorldBankOfADataType(GDPPerCapitaData.DATA_TYPE);
		WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
		ArrayList<CountryGDPPerCapitaData> data = wrapper.extractGDPPerCapitaData(countries);
		deleteDataOnDB(WorldBankDataWrapper.SOURCE);
		saveDataOnDB(data);
	}
	
	public void loadBoundaryValues() throws SQLException
	{
		BoundaryValueDAO boundaryValueDAO = new BoundaryValueDAO();
		boundaryValueDAO.delete(GDPPerCapitaData.DATA_TYPE);
		boundaryValueDAO.save(GDPPerCapitaData.DATA_TYPE);
	}
	
	private void saveDataOnDB(ArrayList<CountryGDPPerCapitaData> data) throws SQLException
	{
		CountryGDPPerCapitaDataDAO gdpPerCapitaDAO = new CountryGDPPerCapitaDataDAO();
		gdpPerCapitaDAO.save(data);
	}
	
	private void deleteDataOnDB(String source) throws SQLException
	{
		CountryGDPPerCapitaDataDAO gdpPerCapitaDAO = new CountryGDPPerCapitaDataDAO();
		gdpPerCapitaDAO.delete(source);
	}

	public static void main(String[] args) throws UnirestException, SQLException {
		LoadGDPPerCapitaData loader = new LoadGDPPerCapitaData();
		loader.loadWorldBankData();
		loader.loadBoundaryValues();
	}

}
