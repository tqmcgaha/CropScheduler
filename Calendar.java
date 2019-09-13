import java.util.Map;
import java.util.HashMap;

public class Calendar {
	private Map<Integer, Map<String, Integer>> plantDates;
	private Map<Integer, Map<String, Integer>> harvestDates;

	public Calendar(){
		plantDates = new HashMap<Integer, Map<String, Integer>>();
		harvestDates = new HashMap<Integer, Map<String, Integer>>();
	}

	public boolean plant(int day, Crop crop, int count){
		if(day+crop.growthTime() > SchedulerConstants.DAYS_IN_SEASON || day < 1){
			return false;
		}
		if(!plantDates.containsKey(day)){
			plantDates.put(day, new HashMap<String, Integer>());
		}
		Map<String, Integer> plantDay = plantDates.get(day);
		int prev = 0;
		if(plantDay.containsKey(crop.name())){
			prev = plantDay.get(crop.name());
		}
		plantDay.put(crop.name(), count+prev);

		for(int i = day + crop.growthTime(); i <= SchedulerConstants.DAYS_IN_SEASON; i += crop.regrowthTime()){
			System.out.println("HERE");
			if(!harvestDates.containsKey(i)){
				harvestDates.put(i, new HashMap<String, Integer>());
			}
			Map<String, Integer> harvestDay = harvestDates.get(i);
			int prevHarvest = 0;
			if(harvestDay.containsKey(crop.name())){
				prevHarvest = harvestDay.get(crop.name());
			}
			harvestDay.put(crop.name(), count+prevHarvest);
			if(!crop.repeats()){
				break;
			}
		}
		return true;
	}

	public boolean plant(int day, Crop crop){
		return this.plant(day, crop, 1);
	}

	public String toString(){
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i <= SchedulerConstants.DAYS_IN_SEASON; i++){
			sb.append("day "+ i +":\n");
			Map<String, Integer> dayPlans = this.plantDates.get(i);
			if(dayPlans != null){
				//sb.append("\t- nothing to plant\n");
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
			dayPlans = this.harvestDates.get(i);
			if(dayPlans == null){
				continue;
			}
			for(String crop : dayPlans.keySet()){
				Integer count = dayPlans.get(crop);
				if(crop == null || count == null || count < 0){
					continue;
				}
				sb.append("\t- harvest ");
				sb.append(crop);
				sb.append(" x");
				sb.append(count);
				sb.append("\n");
			}
		}
		return sb.toString();
	}
}
