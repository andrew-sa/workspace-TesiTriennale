package manager.data.model;

public class RegionNetMigrationData extends Data implements NetMigrationData {

	public RegionNetMigrationData() {
		
	}

	public RegionNetMigrationData(String name, String year, double value) {
		super(name, year, value);
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
