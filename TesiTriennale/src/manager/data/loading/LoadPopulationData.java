package manager.data.loading;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mashape.unirest.http.exceptions.UnirestException;

import manager.data.dao.BoundaryValueDAO;
import manager.data.dao.CountryDAO;
import manager.data.dao.CountryPopulationDataDAO;
import manager.data.dao.SourceDAO;
import manager.data.extraction.EUDataWrapper;
import manager.data.extraction.USADataWrapper;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.Country;
import manager.data.model.CountryPopulationData;
import manager.data.model.PopulationData;
import manager.data.transformation.EUDataTrasformer;

public class LoadPopulationData {

	public LoadPopulationData() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadUSAData() throws UnirestException, SQLException
	{
		USADataWrapper wrapper = new USADataWrapper();
		ArrayList<CountryPopulationData> data = wrapper.extractPopulationData();
		deleteDataOnDB(USADataWrapper.SOURCE);
		saveDataOnDB(data);
		updateSourceDate(USADataWrapper.SOURCE);
	}
	
	public void loadUSAData(PrintWriter printWriter) throws UnirestException, SQLException
	{
		USADataWrapper wrapper = new USADataWrapper();
		ArrayList<CountryPopulationData> data = wrapper.extractPopulationData(printWriter);
		
		printWriter.println("DELETING (previuos): " + PopulationData.DATA_TYPE + ", " + USADataWrapper.SOURCE);
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
		ArrayList<CountryPopulationData> data = wrapper.extractPopulationData();
		EUDataTrasformer trasformer = new EUDataTrasformer();
		data = trasformer.correctCountriesCodesPopulationData(data);
		deleteDataOnDB(EUDataWrapper.SOURCE);
		saveDataOnDB(data);
		updateSourceDate(EUDataWrapper.SOURCE);
	}
	
	public void loadEUData(PrintWriter printWriter) throws UnirestException, SQLException
	{
		EUDataWrapper wrapper = new EUDataWrapper();
		ArrayList<CountryPopulationData> data = wrapper.extractPopulationData(printWriter);
		EUDataTrasformer trasformer = new EUDataTrasformer();
		data = trasformer.correctCountriesCodesPopulationData(data);
		
		printWriter.println("DELETING (previuos): " + PopulationData.DATA_TYPE + ", " + EUDataWrapper.SOURCE);
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
		ArrayList<Country> countries = countryDAO.readCountryForWorldBankOfADataType(PopulationData.DATA_TYPE);
		WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
		ArrayList<CountryPopulationData> data = wrapper.extractPopulationData(countries);
		deleteDataOnDB(WorldBankDataWrapper.SOURCE);
		saveDataOnDB(data);
		updateSourceDate(WorldBankDataWrapper.SOURCE);
	}
	
	public void loadWorldBankData(PrintWriter printWriter) throws UnirestException, SQLException
	{
		CountryDAO countryDAO = new CountryDAO();
		ArrayList<Country> countries = countryDAO.readCountryForWorldBankOfADataType(PopulationData.DATA_TYPE);
		WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
		ArrayList<CountryPopulationData> data = wrapper.extractPopulationData(countries, printWriter);
		
		printWriter.println("DELETING (previuos): " + PopulationData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
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
		boundaryValueDAO.delete(PopulationData.DATA_TYPE);
		boundaryValueDAO.save(PopulationData.DATA_TYPE);
	}
	
	private void saveDataOnDB(ArrayList<CountryPopulationData> data) throws SQLException
	{
		CountryPopulationDataDAO populationDataDAO = new CountryPopulationDataDAO();
		populationDataDAO.save(data);
	}
	
	private void saveDataOnDB(ArrayList<CountryPopulationData> data, PrintWriter printWriter) throws SQLException
	{
		CountryPopulationDataDAO populationDataDAO = new CountryPopulationDataDAO();
		populationDataDAO.save(data, printWriter);
	}
	
	private void deleteDataOnDB(String source) throws SQLException
	{
		CountryPopulationDataDAO populationDataDAO = new CountryPopulationDataDAO();
		populationDataDAO.delete(source);
	}
	
	private void updateSourceDate(String sourceName) throws SQLException
	{
		SourceDAO sourceDAO = new SourceDAO();
		sourceDAO.update(sourceName, PopulationData.DATA_TYPE);
	}

	public static void main(String[] args) throws UnirestException, SQLException {
		LoadPopulationData loader = new LoadPopulationData();
		loader.loadUSAData();
		loader.loadEUData();
		loader.loadWorldBankData();
		loader.loadBoundaryValues();
	}

}
