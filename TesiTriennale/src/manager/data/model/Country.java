package manager.data.model;

public class Country {

	public Country() {
	}
	
	public Country(String code, String name) {
		this.code = code;
		this.name = name;
		this.region = null;
	}
	
	public Country(String code, String name, String region)
	{
		this.code = code;
		this.name = name;
		this.region = region;
	}

	public boolean equals(Object otherObject)
	{
		if (otherObject == null)
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		Country other = (Country) otherObject;
		return code.equals(other.code) && name.equals(other.name);
	}

	
	public String toString()
	{
		return getClass().getName() + "[code=" + code + ", name=" + name + ", region=" + region + "]";
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	private String code;
	private String name;
	private String region;

}
