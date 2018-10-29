package manager.data.model;

import java.sql.Date;

public class Source {

	public Source() {
		// TODO Auto-generated constructor stub
	}
	
	public Source(String name, String dataType, Date date) {
		this.name = name;
		this.dataType = dataType;
		this.date = date;
	}
	
	public Source(String name, String dataType, Date date, int order) {
		this.name = name;
		this.dataType = dataType;
		this.date = date;
		this.order = order;
	}

	public boolean equals(Object otherObject)
	{
		if (otherObject == null)
			return false;
		if (getClass() != otherObject.getClass())
			return false;
		Source other = (Source) otherObject;
		return name.equals(other.name) && dataType.equals(other.dataType);
	}
	
	public String toString() {
		return getClass().getName() + "[name=" + name + ", dataType=" + dataType + ", date=" + date + ", order=" + order + "]";
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
	 * @return the dataType
	 */
	public String getDataType() {
		return dataType;
	}

	/**
	 * @param dataType the dataType to set
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	private String name;
	private String dataType;
	private Date date;
	private int order;

}
