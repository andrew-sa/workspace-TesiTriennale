package manager.data.model;

public class CountryNetMigrationData extends Data implements NetMigrationData {

	public CountryNetMigrationData() {
		
	}
	
	public CountryNetMigrationData(String name, String year) {
		super(name, year);
	}

	public CountryNetMigrationData(String name, String year, double value) {
		super(name, year, value);
	}
	
	public CountryNetMigrationData(String name, String year, double value, String source) {
		super(name, year, value);
		this.source = source;
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
		return super.toString() + "[source=" + source + "]";
	}
	
	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}
	
	private String source;

}
