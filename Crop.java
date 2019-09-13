
/**
 * This class represents a Crop in Stardew valley.
 * Some crops have repeatable harvests, others don't.
 * Each crop has a name that identifies them, and crops
 * with the same name will be treated as equivalent.
 *
 */
public class Crop {
	private String name;
	private int growthTime;
	private int regrowthTime;
	private int seedCost;
	private int sellPrice;
	private boolean repeats;
	
	/**
	 * Constructs a new Crop object pased on given values.
	 * @param name the name of the new crop.
	 * @param growthTime how long it takes for the crop to
	 * grow. If the crop is repeatable, this is the time it
	 * takes to get to the point where it begins it's cycle.
	 * @param regrowthTime how long it takes a repeatable
	 * crop to regrow for another harvest. should be 0 for
	 * non-repeatable crops.
	 * @param seedCost the cost of the seed to plant it.
	 * @param sellPrice the price that the harvested crop
	 * sells for.
	 * @param repeats indicates whether or not the crop
	 * can be harvested repeatedly.
	 */
	public Crop(String name, int growthTime, int regrowthTime,
				int seedCost, int sellPrice, boolean repeats){
		this.name = name;
		this.growthTime = growthTime;
		this.regrowthTime = regrowthTime;
		this.seedCost = seedCost;
		this.sellPrice = sellPrice;
		this.repeats = repeats;
	}

	/**
	 * Gets the name of this crop.
	 * @return the name of this crop.
	 */
	public String name(){
		return this.name;
	}

	/**
	 * Gets the time for the crop to grow.
	 * @return the number of nights it takes
	 * before a crop can be harvested for the first time.
	 */
	public int growthTime(){
		return this.growthTime;
	}

	/**
	 * Gets the time for a crop to regrow.
	 * @return the number of nights it takes for
	 * a crop to be repeatable. If the crop is not
	 * repeatable, it should return 0.
	 */
	public int regrowthTime(){
		return this.regrowthTime;
	}

	/**
	 * Gets the cost of seeds for this crop.
	 * @return the cost of seeds for this crop.
	 */
	public int seedCost(){
		return this.seedCost;
	}

	/**
	 * Gets the amount a crop sells for.
	 * @return the amount a crop sells for.
	 */
	public int sellPrice(){
		return this.sellPrice;
	}

	/**
	 * Gets whether or not this crop is repeatedly
	 * harvestable.
	 * @return true iff the crop is repeatable.
	 */
	public boolean repeats(){
		return repeats;
	}

	/**
	 * Gets the effective total lifetime for a crop.
	 * @return the amount of time it takes to get as
	 * many harvests from a crop as possible during a season.
	 */
	public int totalLifetime(){
		if(!repeats){
			return growthTime;
		}
		return SchedulerConstants.DAYS_IN_SEASON - 
			((SchedulerConstants.DAYS_IN_SEASON - this.growthTime) % this.regrowthTime);
	}

	/**
	 * Gets how much profit you will get if you plant this,
	 * accounts for repeated harvests through out a reason.
	 * @return the profit of this crop for planting it,
	 * and accounts for repeatable harvests.
	 */
	public int totalProfit(){
		if(!this.repeats()){
			return this.sellPrice() - this.seedCost();
		}
		int harvests = 1;
		int days = SchedulerConstants.DAYS_IN_SEASON - this.growthTime();
		harvests += days / this.regrowthTime();
		return (harvests * this.sellPrice()) - this.seedCost();
	}

	/**
	 * Gets the profit per day ratio if one were to plant
	 * this crop.
	 * @return the profit per day ratio of this crop. Assumes
	 * repeatable crops get maximum harvests in a season.
	 */
	public double profitPerDay(){
		double growth = this.totalLifetime();
		double profit = this.totalProfit();
		return profit / growth;
	}

	/**
	 * Gets the hashcode for this object.
	 * @return the hashcode for this.
	 */
	public int hashCode(){
		return name.hashCode();
	}

	/**
	 * Tests to see if this is equal to the other object.
	 * @param o the object we are checking for equality.
	 * @return true if o is a crop with the same name as this.
	 */
	public boolean equals(Object o){
		/*if(o instanceof String){
			String str = (String) o;
			return this.name().equals(str);
		}*/
		if(o instanceof Crop){
			Crop c = (Crop) o;
			return this.name().equals(c.name());
		}
		return false;
	}

	/**
	 * The string representation of this crop.
	 * @return the name of this crop.
	 */
	public String toString(){
		return this.name();
	}

	/**
	 * Gets information about this crop.
	 * @return a String containing the name, growth time
	 * regrowth time, seed cost, seed cost, sell price, and 
	 * whether the crop repeats, all on seperate lines.
	 */
	public String info(){
		return "Name: " + name
		+ "\ngrowth time: " + growthTime
		+ "\nregrowth time: " + regrowthTime
		+ "\nseed cost: " + seedCost
		+ "\nsell price: " + sellPrice
		+ "\nrepeats?: " + repeats;
	}
}
