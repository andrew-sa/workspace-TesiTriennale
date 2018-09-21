package manager.data.transformation;

import java.sql.SQLException;
import java.util.ArrayList;

import manager.data.model.CountryPovertyData;

public class USADataTrasformer {

	public USADataTrasformer() {
		// TODO Auto-generated constructor stub
	}
	
	public ArrayList<CountryPovertyData> removeDuplicates(ArrayList<CountryPovertyData> initialData) throws SQLException
	{
		ArrayList<CountryPovertyData> result = new ArrayList<>();
		for (CountryPovertyData pd: initialData)
		{
			if (!result.contains(pd))
			{
				result.add(pd);
			}
		}
		return result;
	}

}
