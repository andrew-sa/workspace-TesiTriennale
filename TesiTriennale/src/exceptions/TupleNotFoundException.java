package exceptions;

public class TupleNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TupleNotFoundException()
	{
		super("The tuple is not present in the database");
	}
	
	public TupleNotFoundException(String msg)
	{
		super(msg);
	}
	
}
