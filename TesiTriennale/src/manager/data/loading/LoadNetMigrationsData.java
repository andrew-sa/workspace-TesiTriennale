package manager.data.loading;

import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.GregorianCalendar;

import com.mashape.unirest.http.exceptions.UnirestException;

import manager.data.dao.CountryDAO;
import manager.data.dao.CountryNetMigrationDataDAO;
import manager.data.dao.RegionNetMigrationDAO;
import manager.data.dao.SourceDAO;
import manager.data.extraction.EUDataWrapper;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.Country;
import manager.data.model.CountryNetMigrationData;
import manager.data.model.NetMigrationData;
import manager.data.model.PopulationData;
import manager.data.model.RegionNetMigrationData;
import manager.data.transformation.EUDataTrasformer;

public class LoadNetMigrationsData {

	public LoadNetMigrationsData() {
		// TODO Auto-generated constructor stub
	}
	
	public void loadEUData() throws UnirestException, SQLException
	{
		EUDataWrapper wrapper = new EUDataWrapper();
		ArrayList<CountryNetMigrationData> data = wrapper.extractNetMigrationData();
		EUDataTrasformer trasformer = new EUDataTrasformer();
		data = trasformer.correctCountriesCodesNetMigration(data);
		deleteDataOnDB(EUDataWrapper.SOURCE);
		saveDataOnDB(data);
		updateSourceDate(EUDataWrapper.SOURCE);
	}
	
	public void loadEUData(PrintWriter printWriter) throws UnirestException, SQLException
	{
		EUDataWrapper wrapper = new EUDataWrapper();
		ArrayList<CountryNetMigrationData> data = wrapper.extractNetMigrationData(printWriter);
		EUDataTrasformer trasformer = new EUDataTrasformer();
		data = trasformer.correctCountriesCodesNetMigration(data);
		
		printWriter.println("DELETING (previuos): " + NetMigrationData.DATA_TYPE + ", " + EUDataWrapper.SOURCE);
		printWriter.flush();
		deleteDataOnDB(EUDataWrapper.SOURCE);
		
//		printWriter.println("SAVING (all): " + NetMigrationData.DATA_TYPE + ", " + EUDataWrapper.SOURCE);
//		printWriter.flush();
		saveDataOnDB(data, printWriter);
		
		updateSourceDate(EUDataWrapper.SOURCE);
	}
	
	public void loadWorldBankData() throws UnirestException, SQLException
	{
		CountryDAO countryDAO = new CountryDAO();
		ArrayList<Country> countries = countryDAO.readCountryForWorldBankOfADataType(NetMigrationData.DATA_TYPE);
		WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
		ArrayList<CountryNetMigrationData> data = wrapper.extractNetMigrationData(countries);
		deleteDataOnDB(WorldBankDataWrapper.SOURCE);
		saveDataOnDB(data);
		updateSourceDate(WorldBankDataWrapper.SOURCE);
	}
	
	public void loadWorldBankData(PrintWriter printWriter) throws UnirestException, SQLException
	{
		CountryDAO countryDAO = new CountryDAO();
		ArrayList<Country> countries = countryDAO.readCountryForWorldBankOfADataType(NetMigrationData.DATA_TYPE);
		WorldBankDataWrapper wrapper = new WorldBankDataWrapper();
		ArrayList<CountryNetMigrationData> data = wrapper.extractNetMigrationData(countries, printWriter);
		
		printWriter.println("DELETING (previuos): " + NetMigrationData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
		printWriter.flush();
		deleteDataOnDB(WorldBankDataWrapper.SOURCE);
		
//		printWriter.println("SAVING (all): " + NetMigrationData.DATA_TYPE + ", " + WorldBankDataWrapper.SOURCE);
//		printWriter.flush();
		saveDataOnDB(data, printWriter);
		
		updateSourceDate(WorldBankDataWrapper.SOURCE);
	}
	
//	public void loadRegionsData() throws SQLException
//	{
//		ArrayList<RegionNetMigrationData> regionsData = new ArrayList<>();
//		final int lastYear = (new GregorianCalendar()).get(GregorianCalendar.YEAR);
//		for (int i = 0; i < REGIONS.length; i++)
//		{
//			CountryNetMigrationDataDAO netMigrationDAO = new CountryNetMigrationDataDAO();
//			ArrayList<CountryNetMigrationData> data = netMigrationDAO.readByRegion(REGIONS[i]);
//			int j = FIRST_YEAR;
//			while (data.size() > 0 && j <= lastYear)
//			{
//				float yearValue = 0.0f;
//				String year = String.valueOf(j);
//				ArrayList<CountryNetMigrationData> dataToDelete = new ArrayList<>();
//				for (CountryNetMigrationData nmd: data)
//				{
//					if (nmd.getYear().equals(year))
//					{
//						yearValue += nmd.getValue();
//						dataToDelete.add(nmd);
//					}
//				}
//				if (yearValue != 0.0f)
//				{
//					regionsData.add(new RegionNetMigrationData(REGIONS[i], year, yearValue));
////					System.out.println(REGIONS[i] + ", " + year + ", " + yearValue);
////					System.out.println(data.size());
//					data.removeAll(dataToDelete);
////					System.out.println(data.size());
//				}
//				j++;
//			}
//		}
//		RegionNetMigrationDAO regionNetMigrationDAO = new RegionNetMigrationDAO();
//		regionNetMigrationDAO.delete();
//		regionNetMigrationDAO.save(regionsData);
//	}
	
	private void saveDataOnDB(ArrayList<CountryNetMigrationData> data) throws SQLException
	{
		CountryNetMigrationDataDAO netMigrationDAO = new CountryNetMigrationDataDAO();
		netMigrationDAO.save(data);
	}
	
	private void saveDataOnDB(ArrayList<CountryNetMigrationData> data, PrintWriter printWriter) throws SQLException
	{
		CountryNetMigrationDataDAO netMigrationDAO = new CountryNetMigrationDataDAO();
		netMigrationDAO.save(data, printWriter);
	}
	
	private void deleteDataOnDB(String source) throws SQLException
	{
		CountryNetMigrationDataDAO netMigrationDAO = new CountryNetMigrationDataDAO();
		netMigrationDAO.delete(source);
	}
	
	private void updateSourceDate(String sourceName) throws SQLException
	{
		SourceDAO sourceDAO = new SourceDAO();
		sourceDAO.update(sourceName, NetMigrationData.DATA_TYPE);
	}
	
	public static void main(String[] args) throws UnirestException, SQLException {
		LoadNetMigrationsData loader = new LoadNetMigrationsData();
		loader.loadEUData();
		loader.loadWorldBankData();
//		loader.loadRegionsData();
	}
	
	private static final String[] REGIONS = {"Africa", "Europe", "Americas", "Asia", "Oceania"/*, "World"*/};
	private static final int FIRST_YEAR = 2000;

}
