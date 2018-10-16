package manager.data.loading;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;

import manager.data.dao.BoundaryValueDAO;
import manager.data.dao.CountryDAO;
import manager.data.dao.CountryPovertyDataDAO;
import manager.data.dao.SourceDAO;
import manager.data.extraction.EUDataWrapper;
import manager.data.extraction.USADataWrapper;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.Country;
import manager.data.model.CountryPovertyData;
import manager.data.model.PovertyData;
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
		updateSourceDate(USADataWrapper.SOURCE);
	}
	
	public void loadUSAData(PrintWriter printWriter) throws UnirestException, SQLException
	{
		USADataWrapper wrapper = new USADataWrapper();
		ArrayList<CountryPovertyData> data = wrapper.extractPovertyData(printWriter);
		USADataTrasformer trasformer = new USADataTrasformer();
		data = trasformer.removeDuplicates(data);
		
		printWriter.println("DELETING (previuos): " + PovertyData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
		printWriter.flush();
		deleteDataOnDB(USADataWrapper.SOURCE);
		
//		printWriter.println("SAVING (all): " + PopulationData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
//		printWriter.flush();
		saveDataOnDB(data, printWriter);
		
		updateSourceDate(USADataWrapper.SOURCE);
	}
	
	public void loadEUData() throws UnirestException, SQLException
	{
		EUDataWrapper wrapper = new EUDataWrapper();
		ArrayList<CountryPovertyData> data = wrapper.extractPovertyData();
		EUDataTrasformer trasformer = new EUDataTrasformer();
		data = trasformer.correctCountriesCodesPovertyData(data);
		deleteDataOnDB(EUDataWrapper.SOURCE);
		saveDataOnDB(data);
		updateSourceDate(EUDataWrapper.SOURCE);
	}
	
	public void loadEUData(PrintWriter printWriter) throws UnirestException, SQLException
	{
		EUDataWrapper wrapper = new EUDataWrapper();
		ArrayList<CountryPovertyData> data = wrapper.extractPovertyData(printWriter);
		EUDataTrasformer trasformer = new EUDataTrasformer();
		data = trasformer.correctCountriesCodesPovertyData(data);
		
		printWriter.println("DELETING (previuos): " + PovertyData.DATA_TYPE + ", " + EUDataWrapper.SOURCE);
		printWriter.flush();
		deleteDataOnDB(EUDataWrapper.SOURCE);
		
//		printWriter.println("SAVING (all): " + PopulationData.DATA_TYPE + ", " + EUDataWrapper.SOURCE);
//		printWriter.flush();
		saveDataOnDB(data, printWriter);
		
		updateSourceDate(EUDataWrapper.SOURCE);
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
		updateSourceDate(WorldBankDataWrapper.SOURCE);
	}
	
	public void loadWorldBankData(PrintWriter printWriter) throws UnirestException, SQLException
	{
		CountryDAO countryDAO = new CountryDAO();
		ArrayList<Country> countries = countryDAO.readCountryForWorldBankOfADataType(PovertyData.DATA_TYPE);
		WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
		ArrayList<CountryPovertyData> data = wrapper.extractPovertyData(countries, printWriter);

		printWriter.println("DELETING (previuos): " + PovertyData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
		printWriter.flush();
		deleteDataOnDB(WorldBankDataWrapper.SOURCE);
		
//		printWriter.println("SAVING (all): " + PopulationData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
//		printWriter.flush();
		saveDataOnDB(data, printWriter);
		
		updateSourceDate(WorldBankDataWrapper.SOURCE);
	}
	
	public void loadBoundaryValues() throws SQLException
	{
		BoundaryValueDAO boundaryValueDAO = new BoundaryValueDAO();
		boundaryValueDAO.delete(PovertyData.DATA_TYPE);
		boundaryValueDAO.save(PovertyData.DATA_TYPE);
	}
	
	private void saveDataOnDB(ArrayList<CountryPovertyData> data) throws SQLException
	{
		CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
		povertyDataDAO.save(data);
	}
	
	private void saveDataOnDB(ArrayList<CountryPovertyData> data, PrintWriter printWriter) throws SQLException
	{
		CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
		povertyDataDAO.save(data, printWriter);
	}
	
	private void deleteDataOnDB(String source) throws SQLException
	{
		CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
		povertyDataDAO.delete(source);
	}
	
	private void updateSourceDate(String sourceName) throws SQLException
	{
		SourceDAO sourceDAO = new SourceDAO();
		sourceDAO.update(sourceName, PovertyData.DATA_TYPE);
	}
	
	public static void main(String[] args) throws UnirestException, SQLException {
		LoadPovertyData loadPovertyData = new LoadPovertyData();
		loadPovertyData.loadUSAData();
		loadPovertyData.loadEUData();
		loadPovertyData.loadWorldBankData();
		loadPovertyData.loadBoundaryValues();
	}
	
}
