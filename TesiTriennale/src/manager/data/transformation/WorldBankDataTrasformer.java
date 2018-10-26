package manager.data.transformation;

import java.util.ArrayList;

import manager.data.extraction.WorldBankDataWrapper;
import manager.data.model.CompareToYear;
import manager.data.model.CountryPovertyData;

public class WorldBankDataTrasformer {

	public WorldBankDataTrasformer() {
		
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
							pd.setSource(WorldBankDataWrapper.SOURCE);
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
							pd.setSource(WorldBankDataWrapper.SOURCE);
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

}
