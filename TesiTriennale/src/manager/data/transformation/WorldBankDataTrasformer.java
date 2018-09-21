package manager.data.transformation;

import java.sql.SQLException;
import java.util.ArrayList;

import manager.data.dao.CountryPovertyDataDAO;
import manager.data.model.CountryPovertyData;

public class WorldBankDataTrasformer {

	public WorldBankDataTrasformer() {
		
	}
	
	public ArrayList<CountryPovertyData> removeDuplicates(ArrayList<CountryPovertyData> initialData) throws SQLException
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
//		CountryPovertyDataDAO povertyDataDAO = new CountryPovertyDataDAO();
//		ArrayList<String> savedCountries = povertyDataDAO.readCountriesOfTheSavedData();
//		for (CountryPovertyData pd: initialData)
//		{
//			if (!savedCountries.contains(pd.getName()))
//			{
//				data.add(pd);
//			}
//		}
		return data;
	}

}
