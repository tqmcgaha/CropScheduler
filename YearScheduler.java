
/**
 * This class will read in crops and plan for a whole year.
 * Users can configure what year it is, what special seeds
 * they have and other things.
 */
public class YearScheduler {
	// fields
	
	// spring, summer, and fall fields
	private CropField spField;
	private CropField suField;
	private CropField auField;

	// special seeds
	// contains how many Sweet Gem Berry, 
	// Ancient Seeds, and Strawberry seeds you have.

	// what year it is
	private int year;

	public YearScheduler(int year);

	public YearScheduler();
}
