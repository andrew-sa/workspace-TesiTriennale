package manager.data.transformation;

import java.util.ArrayList;

import manager.data.extraction.EUDataWrapper;
import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.CompareToYear;
import manager.data.model.CountryNetMigrationData;
import manager.data.model.CountryPopulationData;
import manager.data.model.CountryPovertyData;
import manager.data.model.Data;

public class EUDataTrasformer {

	public EUDataTrasformer() {
		
	}
	
//	public ArrayList<CountryPopulationData> correctCountriesCodesPopulationData(ArrayList<CountryPopulationData> initialData)
//	{
//		ArrayList<CountryPopulationData> data = new ArrayList<>();
//		for (CountryPopulationData pd: initialData)
//		{
//			if (pd.getName().equals(OLD_GREAT_BRITAIN))
//			{
//				pd.setName(NEW_GREAT_BRITAIN);
//				data.add(pd);
//			}
//			else if (pd.getName().equals(OLD_GREECE))
//			{
//				pd.setName(NEW_GREECE);
//				data.add(pd);
//			}
//			else if (pd.getName().equals(GERMANY_INCLUDING_FORMER_GDR) || pd.getName().equals(FRANCE_METROPOLITAN))
//			{
//				//discard data
//			}
//			else
//			{
//				data.add(pd);
//			}
//		}
//		return data;
//	}
//	
//	public ArrayList<CountryPovertyData> correctCountriesCodesPovertyData(ArrayList<CountryPovertyData> initialData)
//	{
//		ArrayList<CountryPovertyData> data = new ArrayList<>();
//		for (CountryPovertyData pd: initialData)
//		{
//			if (pd.getName().equals(OLD_GREAT_BRITAIN))
//			{
//				pd.setName(NEW_GREAT_BRITAIN);
//				data.add(pd);
//			}
//			else if (pd.getName().equals(OLD_GREECE))
//			{
//				pd.setName(NEW_GREECE);
//				data.add(pd);
//			}
//			else if (pd.getName().equals(GERMANY_INCLUDING_FORMER_GDR) || pd.getName().equals(FRANCE_METROPOLITAN))
//			{
//				//discard data
//			}
//			else
//			{
//				data.add(pd);
//			}
//		}
//		return data;
//	}
//	
//	public ArrayList<CountryNetMigrationData> correctCountriesCodesNetMigration(ArrayList<CountryNetMigrationData> initialData)
//	{
//		ArrayList<CountryNetMigrationData> data = new ArrayList<>();
//		for (CountryNetMigrationData nmd: initialData)
//		{
//			if (nmd.getName().equals(OLD_GREAT_BRITAIN))
//			{
//				nmd.setName(NEW_GREAT_BRITAIN);
//				data.add(nmd);
//			}
//			else if (nmd.getName().equals(OLD_GREECE))
//			{
//				nmd.setName(NEW_GREECE);
//				data.add(nmd);
//			}
//			else if (nmd.getName().equals(GERMANY_INCLUDING_FORMER_GDR) || nmd.getName().equals(FRANCE_METROPOLITAN))
//			{
//				//discard data
//			}
//			else
//			{
//				data.add(nmd);
//			}
//		}
//		return data;
//	}
	
	public ArrayList<CountryPopulationData> correctCountriesCodesPopulationData(ArrayList<CountryPopulationData> initialData)
	{
		ArrayList<CountryPopulationData> data = new ArrayList<>();
		for (CountryPopulationData pd: initialData)
		{
			Data trasfromedData = trasfromData(pd);
			if (trasfromedData != null)
			{
				CountryPopulationData pdTrasfromed = (CountryPopulationData) trasfromedData;
				data.add(pdTrasfromed);
			}
		}
		return data;
	}
	
	public ArrayList<CountryPovertyData> correctCountriesCodesPovertyData(ArrayList<CountryPovertyData> initialData)
	{
		ArrayList<CountryPovertyData> data = new ArrayList<>();
		for (CountryPovertyData pd: initialData)
		{
			Data trasfromedData = trasfromData(pd);
			if (trasfromedData != null)
			{
				CountryPovertyData pdTrasfromed = (CountryPovertyData) trasfromedData;
				data.add(pdTrasfromed);
//				System.out.println(pdTrasfromed);
			}
		}
		return data;
	}
	
	public ArrayList<CountryNetMigrationData> correctCountriesCodesNetMigration(ArrayList<CountryNetMigrationData> initialData)
	{
		ArrayList<CountryNetMigrationData> data = new ArrayList<>();
		for (CountryNetMigrationData nmd: initialData)
		{
			Data trasfromedData = trasfromData(nmd);
			if (trasfromedData != null)
			{
				CountryNetMigrationData nmdTrasfromed = (CountryNetMigrationData) trasfromedData;
				data.add(nmdTrasfromed);
			}
		}
		return data;
	}
	
	private Data trasfromData(Data d)
	{
		if (d.getName().equals(OLD_GREAT_BRITAIN))
		{
			d.setName(NEW_GREAT_BRITAIN);
		}
		else if (d.getName().equals(OLD_GREECE))
		{
			d.setName(NEW_GREECE);
		}
		else if (d.getName().equals(GERMANY_INCLUDING_FORMER_GDR) || d.getName().equals(FRANCE_METROPOLITAN))
		{
			//discard data
			return null;
		}
		return d;
	}
	
	public ArrayList<CountryPovertyData> calculateInterpolatedValus(ArrayList<CountryPovertyData> realData)
	{
		if (realData.size() < 2)
		{
			return realData;
		}
		else
		{
			realData.sort(new CompareToYear());
			ArrayList<CountryPovertyData> data = new ArrayList<>();
			final String country = realData.get(0).getName();
			int i;
			for (i = 0; i < realData.size() - 1; i++)
			{
				data.add(realData.get(i));
				int firstYear = Integer.valueOf(realData.get(i).getYear());
				int secondYear = Integer.valueOf(realData.get(i + 1).getYear());
				if (secondYear - firstYear > 1)
				{
					double hole = secondYear - firstYear;
					if (realData.get(i).getValue() < realData.get(i + 1).getValue())
					{
						double gap = realData.get(i + 1).getValue() - realData.get(i).getValue();
						double annualGap = gap / hole;
						for (int j = 1; j < hole; j++)
						{
							CountryPovertyData pd = new CountryPovertyData();
							pd.setName(country);
							pd.setYear(String.valueOf(firstYear + j));
							pd.setValue(realData.get(i).getValue() + annualGap * j);
							pd.setSource(EUDataWrapper.SOURCE);
							pd.setCalculated(true);
							System.out.println(pd);
							data.add(pd);
						}
					}
					else
					{
						double gap = realData.get(i).getValue() - realData.get(i + 1).getValue();
						double annualGap = gap / hole;
						for (int j = 1; j < hole; j++)
						{
							CountryPovertyData pd = new CountryPovertyData();
							pd.setName(country);
							pd.setYear(String.valueOf(firstYear + j));
							pd.setValue(realData.get(i).getValue() - annualGap * j);
							pd.setSource(EUDataWrapper.SOURCE);
							pd.setCalculated(true);
							System.out.println(pd);
							data.add(pd);
						}
					}
				}
			}
			data.add(realData.get(i));
			return data;
		}
	}
	
	public static void main(String[] args) {
		
	}

	private static final String OLD_GREAT_BRITAIN = "UK";
	private static final String NEW_GREAT_BRITAIN = "GB";
	private static final String OLD_GREECE = "EL";
	private static final String NEW_GREECE = "GR";
	private static final String GERMANY_INCLUDING_FORMER_GDR = "DE_TOT";
	private static final String FRANCE_METROPOLITAN = "FX";
	
}
