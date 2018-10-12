package manager.data.model;

public abstract class Data {

	public Data() {
		// TODO Auto-generated constructor stub
	}
	
	public Data(String name, String year) {
		this.name = name;
		this.year = year;
	}
	
	public Data(String name, String year, double value) {
		this.name = name;
		this.year = year;
		this.value = value;
	}
	
	public boolean equals(Object otherObject)
	{
		if (otherObject == null)
		{
			return false;
		}
		if (getClass() != otherObject.getClass())
		{
			return false;
		}
		Data other = (Data) otherObject;
		return name.equals(other.name) && year.equals(other.year);
	}

	public String toString()
	{
		return getClass().getName() + "[name=" + name + ", year=" + year + ", value=" + value + "]";
	}
	
	public String shortToString()
	{
		return getClass().getName() + "[name=" + name + ", year=" + year + "]";
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}

	private String name;
	private String year;
	private double value;

}
