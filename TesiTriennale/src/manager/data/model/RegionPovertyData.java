package manager.data.model;

public class RegionPovertyData extends Data implements PovertyData {

	public RegionPovertyData() {
		super();
	}
	
	public RegionPovertyData(String region, String year, double value) {
		super(region, year, value);
	}
	
	public boolean equals(Object otherObject)
	{
		if (!super.equals(otherObject))
		{
			return false;
		}
		return true;
	}

	public String toString()
	{
		return super.toString();
	}

}
