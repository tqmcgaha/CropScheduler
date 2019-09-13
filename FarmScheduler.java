import java.util.Collections;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;

public class FarmScheduler {
	private List<Crop> crops;

	public FarmScheduler(List<Crop> crops){
		this.crops = crops;	// change this to a set and addAll ??????
		Collections.sort(crops, new LazyProfitComparator());
	}

	public Calendar schedule(Map<String, Integer> wants, int capacity){
		wants = new HashMap<String, Integer>(wants);
		//List<Map<String, Integer>> result = new ArrayList<Map<String, Integer>>(DAYS_IN_SEASON);
		Calendar result = new Calendar();
		CropField field = new CropField(capacity);
		// Inv: spots is sorted
		// iterate through spots, then pick a crop
		// IDEA: Remove things from wants as they become fullfilled
		// make a new data Structure.

		// first go through all of the wants and add those.
		// crops -> wants
		for(String key: wants.keySet()){
			Crop dummy = new Crop(key, -1, -1, -1, -1, false);
			if(!crops.contains(dummy)){
				System.out.println(key + " isn't a legal crop");
			}
		}
		for(Crop crop : crops){
			if(!wants.containsKey(crop.name())){
				continue;
			}
			for(int i = 0; i < wants.get(crop.name()); i++){
				int plantDate = field.plant(crop); 
				if(plantDate == -1){
					break;
				}
				result.plant(plantDate + 1, crop);
				/*
				// store how many we planted in the map
				if(result.get(plantDate) == null){
					result.set(plantDate, new HashMap<String, Integer>());
				}
				Map<String, Integer> dayMap = result.get(plantDate);
				if(!dayMap.containsKey(crop.name())){
					dayMap.put(crop.name(), 0);
				}
				int currentAmt = dayMap.get(crop.name());
				dayMap.put(crop.name(), currentAmt+1);
				*/
			}
		}

		//next populate with whatever precedence we have set
		for(Crop crop : crops){
			while(field.canHarvest(crop)){
				int plantDate = field.plant(crop); 
				if(plantDate == -1){
					break;
				}
				result.plant(plantDate + 1, crop);
				/*
				// store how many we planted in the map
				if(result.get(plantDate) == null){
					result.set(plantDate, new HashMap<String, Integer>());
				}
				Map<String, Integer> dayMap = result.get(plantDate);
				if(!dayMap.containsKey(crop.name())){
					dayMap.put(crop.name(), 0);
				}
				int currentAmt = dayMap.get(crop.name());
				dayMap.put(crop.name(), currentAmt+1);*/
			}
		}
		//System.out.println(result.size());
		return result;
	}

	public List<Crop> getCrops(){
		return new ArrayList<Crop>(this.crops);
	}

/*
	public String scheduleToString(List<Map<String, Integer>> schedule){
		StringBuilder sb = new StringBuilder();
		if(schedule == null){
			return "Invalid schedule";	
		}

		for(int i = 0; i < schedule.size(); i++){
			sb.append("day "+ (i+1) +":\n");
			Map<String, Integer> dayPlans = schedule.get(i);
			if(dayPlans == null){
				//sb.append("\t- nothing to plant\n");
				continue;
			}
			for(String crop : dayPlans.keySet()){
				Integer count = dayPlans.get(crop);
				if(crop == null || count == null || count < 0){
					continue;
				}
				sb.append("\t- plant ");
				sb.append(crop);
				sb.append(" x");
				sb.append(count);
				sb.append("\n");
			}
		}

		return sb.toString();
	}
*/
	private static int intArrMax(int[] arr){
		if(arr == null || arr.length == 0){
			return -1;
		}
		int max = arr[0];
		int index = 0;
		for(int i = 1; i < arr.length; i++){
			if(arr[i] > max){
				max = arr[i];
				index = i;
			}
		}
		return index;
	}

	private static int intArrMin(int[] arr){
		if(arr == null || arr.length == 0){
			return -1;
		}
		int min = arr[0];
		int index = 0;
		for(int i = 1; i < arr.length; i++){
			if(arr[i] < min){
				min = arr[i];
				index = i;
			}
		}
		return index;
	}

	// maximize wants
	private class MaxUseOfFieldComparator implements Comparator<Crop> {
		public int compare(Crop left, Crop right){
			if(left.repeats()){
				if(right.repeats()){
					// both repeat
					return 0;
				} else {
					// left repeats, and right doesn't
					return -1;
				}
			} else {
				if(right.repeats()){
					// right repeats, left doesn't
					return 1;
				} else {
					return right.growthTime() - left.growthTime();
				}
			}
		}
	}

	// maximize wants
	private class LazyProfitComparator implements Comparator<Crop> {
		public int compare(Crop left, Crop right){
			double leftValue = left.profitPerDay();
			double rightValue = right.profitPerDay();
			if(leftValue > rightValue){
				return -1;
			} else if (leftValue < rightValue){
				return 1;
			} else {
				return 0;
			}
		}
	}
}
